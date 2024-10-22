package ru.ist.users;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.ist.exceptions.baseExceptions.ForbiddenException;
import ru.ist.service.MapperService;
import ru.ist.users.dto.LogDataDto;
import ru.ist.users.dto.NewUserDto;
import ru.ist.users.dto.UserDto;
import ru.ist.users.dto.UserLoginDto;
import ru.ist.users.exception.UserNotFoundException;
import ru.ist.users.model.User;
import ru.ist.utils.PasswordHash;
import ru.ist.utils.StringGenerator;

import java.time.Duration;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final Gson gson;
    private final RedisTemplate<String, String> redis;
    private final UserRepository userRepository;
    private final MapperService mapperService;

    @Override
    public UserDto createUser(NewUserDto userDto) {
        User user = mapperService.toUser(userDto);
        user = userRepository.save(user);

        redis.opsForValue().set("cache_user_" + user.getId().toString(), gson.toJson(mapperService.toUserDto(user)), Duration.ofMinutes(10));
        return mapperService.toUserDto(user);
    }

    @Override
    public UserLoginDto authUser(LogDataDto logDataDto) {
        logDataDto.setPassword(PasswordHash.hash(logDataDto.getPassword()));

        User user = userRepository.findByLoginAndPassword(logDataDto.getLogin(), logDataDto.getPassword())
                .orElseThrow(() -> new UserNotFoundException("Неверный логин или пароль"));

        UserLoginDto userLoginDto = new UserLoginDto(user.getId(), StringGenerator.generateToken(), Duration.ofHours(24).toSeconds());
        redis.opsForValue().set("auth_token_" + userLoginDto.getToken(), userLoginDto.getUserId().toString(), Duration.ofHours(24));
        redis.opsForValue().set("cache_user_" + user.getId().toString(), gson.toJson(mapperService.toUserDto(user)), Duration.ofMinutes(10));

        return userLoginDto;
    }

    @Override
    public UserDto getUserById(UUID userId) {
        String userJson = redis.opsForValue().get("cache_user_" + userId.toString());
        if (userJson != null) {
            return gson.fromJson(userJson, UserDto.class);
        } else {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new UserNotFoundException("Пользователь с данным ID не найден"));

            return mapperService.toUserDto(user);
        }
    }

    @Override
    public void deleteUserById(Authentication auth, UUID userId) {
        UUID authId = UUID.fromString((String) auth.getPrincipal());
        if (!authId.equals(userId)) {
            throw new ForbiddenException("Данное действие недоступно с данным токеном");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Пользователь с данным ID не найден"));

        redis.keys("auth_token_bookcrossing.*").stream().forEach(key -> {
            String id = redis.opsForValue().get(key);
            if (id.equals(userId.toString())) {
                redis.opsForValue().getAndDelete(key);
            }
        });

        redis.opsForValue().getAndDelete("cache_user_" + userId);

        userRepository.deleteById(userId);
    }
}
