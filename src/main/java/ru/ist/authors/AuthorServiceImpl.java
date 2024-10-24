package ru.ist.authors;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import ru.ist.authors.dto.AuthorDto;
import ru.ist.authors.dto.NewAuthorDto;
import ru.ist.authors.model.Author;
import ru.ist.service.MapperService;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final Gson gson;
    private final RedisTemplate<String, String> redis;
    private final MapperService mapperService;
    private final AuthorRepository authorRepository;

    @Override
    public AuthorDto addAuthor(NewAuthorDto authorDto) {
        Author author = mapperService.toAuthor(authorDto);
        author = authorRepository.save(author);

        redis.opsForValue().set("cache_author_" + author.getId().toString(), gson.toJson(mapperService.toAuthorDto(author)), Duration.ofMinutes(30));
        return mapperService.toAuthorDto(author);
    }
}
