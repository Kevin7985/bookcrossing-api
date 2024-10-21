package ru.ist.users;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
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

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final RedisTemplate<String, String> redis;
    private final UserRepository userRepository;
    private final MapperService mapperService;

    @Override
    public UserDto createUser(NewUserDto userDto) {
        User user = mapperService.toUser(userDto);
        user = userRepository.save(user);

        return mapperService.toUserDto(user);
    }

    @Override
    public UserLoginDto authUser(LogDataDto logDataDto) {
        logDataDto.setPassword(PasswordHash.hash(logDataDto.getPassword()));

        User user = userRepository.findByLoginAndPassword(logDataDto.getLogin(), logDataDto.getPassword())
                .orElseThrow(() -> new UserNotFoundException("Неверный логин или пароль"));

        UserLoginDto userLoginDto = new UserLoginDto(user.getId(), StringGenerator.generateToken(), Duration.ofHours(24).toSeconds());
        redis.opsForValue().set("auth_token_" + userLoginDto.getToken(), userLoginDto.getUserId().toString(), Duration.ofHours(24));

        return userLoginDto;
    }
}
