package ru.ist.users;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.ist.users.dto.*;

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

    @GetMapping("/{userId}")
    @SecurityRequirement(name = "Bearer Authentication")
    public UserDto getUserById(Authentication auth, @PathVariable UUID userId) {
        return userService.getUserById(userId);
    }

    @PatchMapping("/{userId}")
    @SecurityRequirement(name = "Bearer Authentication")
    public UserDto updateUserById(Authentication auth, @PathVariable UUID userId) {
        return null;
    }

    @PatchMapping("/{userId}/change-password")
    @SecurityRequirement(name = "Bearer Authentication")
    public UserDto updateUserPasswordById(
            Authentication auth,
            @PathVariable UUID userId,
            @RequestBody UpdateUserPasswordDto userDto
    ) {
        return userService.updatePasswordByUserId(auth, userId, userDto);
    }

    @DeleteMapping("/{userId}")
    @SecurityRequirement(name = "Bearer Authentication")
    public void deleteUserById(Authentication auth, @PathVariable UUID userId) {
        userService.deleteUserById(auth, userId);
    }
}
