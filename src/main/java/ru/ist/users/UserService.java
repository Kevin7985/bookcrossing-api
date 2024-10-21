package ru.ist.users;

import ru.ist.users.dto.LogDataDto;
import ru.ist.users.dto.NewUserDto;
import ru.ist.users.dto.UserDto;
import ru.ist.users.dto.UserLoginDto;

public interface UserService {
    UserLoginDto authUser(LogDataDto logDataDto);
    UserDto createUser(NewUserDto userDto);
}
