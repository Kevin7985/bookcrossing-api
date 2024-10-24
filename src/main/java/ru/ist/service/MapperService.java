package ru.ist.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ist.authors.dto.AuthorDto;
import ru.ist.authors.dto.AuthorMapper;
import ru.ist.authors.dto.NewAuthorDto;
import ru.ist.authors.model.Author;
import ru.ist.users.dto.NewUserDto;
import ru.ist.users.dto.UserDto;
import ru.ist.users.dto.UserMapper;
import ru.ist.users.model.User;

@Service
@RequiredArgsConstructor
public class MapperService {
    private final UserMapper userMapper;
    private final AuthorMapper authorMapper;

    public User toUser(NewUserDto userDto) {
        return userMapper.toUser(userDto);
    }

    public UserDto toUserDto(User user) {
        return userMapper.toUserDto(user);
    }

    public Author toAuthor(NewAuthorDto authorDto) {
        return authorMapper.toAuthor(authorDto);
    }

    public AuthorDto toAuthorDto(Author author) {
        return authorMapper.toAuthorDto(author);
    }
}
