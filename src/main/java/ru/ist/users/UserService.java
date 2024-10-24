package ru.ist.users;

import org.springframework.security.core.Authentication;
import ru.ist.users.dto.*;

import java.util.UUID;

public interface UserService {
    UserLoginDto authUser(LogDataDto logDataDto);
    UserDto createUser(NewUserDto userDto);
    UserDto getUserById(UUID userId);
    UserDto updatePasswordByUserId(Authentication auth, UUID userId, UpdateUserPasswordDto userDto);
    void deleteUserById(Authentication auth, UUID userId);
}
