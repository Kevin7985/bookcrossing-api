package ru.ist.users;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.ist.users.dto.LogDataDto;
import ru.ist.users.dto.NewUserDto;
import ru.ist.users.dto.UserDto;
import ru.ist.users.dto.UserLoginDto;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "Методы для работы с пользователями")
public class UserController {
    private final UserService userService;

    @PostMapping("/auth")
    public UserLoginDto loginUser(@RequestBody LogDataDto logDataDto) {
        return userService.authUser(logDataDto);
    }

    @PostMapping("/register")
    public UserDto createUser(@RequestBody NewUserDto userDto) {
        return userService.createUser(userDto);
    }

    @GetMapping("/{user_id}")
    @SecurityRequirement(name = "Bearer Authentication")
    public UserDto getUserById(Authentication auth, @PathVariable UUID user_id) {
        return userService.getUserById(user_id);
    }

    @PatchMapping("/{user_id}")
    @SecurityRequirement(name = "Bearer Authentication")
    public UserDto updateUserById(Authentication auth, @PathVariable UUID user_id) {
        return null;
    }

    @PatchMapping("/{user_id}/change-password")
    @SecurityRequirement(name = "Bearer Authentication")
    public UserDto updateUserPasswordById(Authentication auth, @PathVariable UUID user_id) {
        return null;
    }

    @DeleteMapping("/{user_id}")
    @SecurityRequirement(name = "Bearer Authentication")
    public void deleteUserById(Authentication auth, @PathVariable UUID user_id) {
        userService.deleteUserById(auth, user_id);
    }
}
