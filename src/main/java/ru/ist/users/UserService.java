package ru.ist.users;

import org.springframework.security.core.Authentication;
import ru.ist.users.dto.LogDataDto;
import ru.ist.users.dto.NewUserDto;
import ru.ist.users.dto.UserDto;
import ru.ist.users.dto.UserLoginDto;

import java.util.UUID;

public interface UserService {
    UserLoginDto authUser(LogDataDto logDataDto);
    UserDto createUser(NewUserDto userDto);
    UserDto getUserById(UUID userId);
    void deleteUserById(Authentication auth, UUID userId);
}
