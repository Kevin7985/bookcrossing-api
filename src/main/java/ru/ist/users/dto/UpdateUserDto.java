package ru.ist.users.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.ist.users.exception.UserValidationException;

import java.util.UUID;

@Data
public class UpdateUserDto {
    private final String name;
    private final String about;
    private final String avatar;

    public UpdateUserDto(String name, String about, String avatar) {
        if (name != null && (name.length() < 1 || name.length() > 512)) {
            throw new UserValidationException("Имя пользователя не может быть пустым или длинее 512 символов");
        }
        this.name = name;
        this.about = about;
        this.avatar = avatar;
    }
}
