package ru.ist.users.dto;

import org.springframework.stereotype.Component;
import ru.ist.users.model.User;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

@Component
public class UserMapper {
    public User toUser(NewUserDto userDto) {
        StringBuffer sb = new StringBuffer();

        try {
            byte[] passwordBytes = userDto.getPassword().getBytes(StandardCharsets.UTF_8);
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] md5Bytes = md.digest(passwordBytes);

            for (int i = 0; i < md5Bytes.length; i++) {
                sb.append(Integer.toHexString((md5Bytes[i] & 0xFF) | 0x100).substring(1,3));
            }
        } catch (Exception ignored) { }

        return new User(
                null,
                userDto.getLogin(),
                userDto.getEmail(),
                sb.toString(),
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
