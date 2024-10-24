package ru.ist.users;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.ist.exceptions.baseExceptions.ForbiddenException;
import ru.ist.service.MapperService;
import ru.ist.users.dto.*;
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

    private void checkUserAuth(Authentication auth, UUID userId) {
        UUID authId = UUID.fromString((String) auth.getPrincipal());
        if (!authId.equals(userId)) {
            throw new ForbiddenException("Данное действие недоступно с данным токеном");
        }
    }

    @Override
    public UserDto createUser(NewUserDto userDto) {
        User user = mapperService.toUser(userDto);
        user = userRepository.save(user);

        redis.opsForValue().set("cache_user_" + user.getId().toString(), gson.toJson(mapperService.toUserDto(user)), Duration.ofMinutes(30));
        return mapperService.toUserDto(user);
    }

    @Override
    public UserLoginDto authUser(LogDataDto logDataDto) {
        logDataDto.setPassword(PasswordHash.hash(logDataDto.getPassword()));

        User user = userRepository.findByLoginAndPassword(logDataDto.getLogin(), logDataDto.getPassword())
                .orElseThrow(() -> new UserNotFoundException("Неверный логин или пароль"));

        UserLoginDto userLoginDto = new UserLoginDto(user.getId(), StringGenerator.generateToken(), Duration.ofHours(24).toSeconds());
        redis.opsForValue().set("auth_token_" + userLoginDto.getToken(), userLoginDto.getUserId().toString(), Duration.ofHours(24));
        redis.opsForValue().set("cache_user_" + user.getId().toString(), gson.toJson(mapperService.toUserDto(user)), Duration.ofMinutes(30));

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

            redis.opsForValue().set("cache_user_" + user.getId().toString(), gson.toJson(mapperService.toUserDto(user)), Duration.ofMinutes(30));

            return mapperService.toUserDto(user);
        }
    }

    @Override
    public UserDto updateUserById(Authentication auth, UUID userId, UpdateUserDto userDto) {
        checkUserAuth(auth, userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Пользователь с данным ID не найден"));

        user.setName(userDto.getName() == null ? user.getName() : userDto.getName());
        user.setAbout(userDto.getAbout() == null ? user.getAbout() : userDto.getAbout());
        user.setAvatar(userDto.getAvatar() == null ? user.getAvatar() : userDto.getAvatar());
        userRepository.save(user);

        redis.opsForValue().getAndDelete("cache_user_" + userId);
        return getUserById(userId);
    }

    @Override
    public UserDto updatePasswordByUserId(Authentication auth, UUID userId, UpdateUserPasswordDto userDto) {
        checkUserAuth(auth, userId);

        userDto.setOldPassword(PasswordHash.hash(userDto.getOldPassword()));
        userDto.setNewPassword(PasswordHash.hash(userDto.getNewPassword()));

        User user = userRepository.findByIdAndPassword(userId, userDto.getOldPassword())
                        .orElseThrow(() -> new UserNotFoundException("Старый пароль неверный"));

        user.setPassword(userDto.getNewPassword());
        userRepository.save(user);

        return getUserById(userId);
    }

    @Override
    public void deleteUserById(Authentication auth, UUID userId) {
        checkUserAuth(auth, userId);

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
