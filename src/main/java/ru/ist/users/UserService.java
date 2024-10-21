package ru.ist.users;

import ru.ist.users.dto.NewUserDto;
import ru.ist.users.dto.UserDto;

public interface UserService {
    UserDto createUser(NewUserDto userDto);
}
