package ru.ist.users;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.ist.users.dto.NewUserDto;
import ru.ist.users.dto.UserDto;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "Методы для работы с пользователями")
public class UserController {
    private final UserService userService;

    @PostMapping("/auth")
    public void loginUser() {

    }

    @PostMapping("/register")
    public UserDto createUser(@RequestBody NewUserDto userDto) {
        return userService.createUser(userDto);
    }

    @GetMapping("/{user_id}")
    public UserDto getUserById(@PathVariable UUID user_id) {
        return null;
    }

    @PatchMapping("/{user_id}")
    public UserDto updateUserById(@PathVariable UUID user_id) {
        return null;
    }

    @DeleteMapping("/{user_id}")
    public void deleteUserById(@PathVariable UUID user_id) {

    }
}
