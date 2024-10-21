package ru.ist.users.dto;

import org.springframework.stereotype.Component;
import ru.ist.users.model.User;
import ru.ist.utils.PasswordHash;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

@Component
public class UserMapper {
    public User toUser(NewUserDto userDto) {
        return new User(
                null,
                userDto.getLogin(),
                userDto.getEmail(),
                PasswordHash.hash(userDto.getPassword()),
                userDto.getName(),
                "",
                ""
        );
    }

    public UserDto toUserDto(User user) {
        return new UserDto(
                user.getId(),
                user.getLogin(),
                user.getEmail(),
                user.getName(),
                user.getAbout(),
                user.getAvatar()
        );
    }
}
