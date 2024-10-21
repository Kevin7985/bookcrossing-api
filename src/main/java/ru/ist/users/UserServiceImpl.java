package ru.ist.users;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ist.service.MapperService;
import ru.ist.users.dto.NewUserDto;
import ru.ist.users.dto.UserDto;
import ru.ist.users.model.User;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final MapperService mapperService;

    @Override
    public UserDto createUser(NewUserDto userDto) {
        User user = mapperService.toUser(userDto);
        user = userRepository.save(user);

        return mapperService.toUserDto(user);
    }
}
