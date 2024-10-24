package ru.ist.authors;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import ru.ist.authors.dto.AuthorDto;
import ru.ist.authors.dto.NewAuthorDto;
import ru.ist.authors.exception.AuthorNotFoundException;
import ru.ist.authors.model.Author;
import ru.ist.service.MapperService;
import ru.ist.utils.Pagination;
import ru.ist.utils.model.Response;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    @Override
    public Response<AuthorDto> searchAuthors(Integer from, Integer size) {
        List<AuthorDto> authorList = new ArrayList<>();
        Pageable pageable;
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        Pagination pager = new Pagination(from, size);
        Page<Author> page;

        for (int i = pager.getPageStart(); i < pager.getPagesAmount(); i++) {
            pageable = PageRequest.of(i, pager.getPageSize(), sort);
            page = authorRepository.findAll(pageable);
            authorList.addAll(page.stream()
                    .map(mapperService::toAuthorDto)
                    .toList()
            );
        }

        return new Response<>(authorRepository.count(), authorList);
    }

    @Override
    public AuthorDto getAuthorById(UUID authorId) {
        String authorJson = redis.opsForValue().get("cache_author_" + authorId.toString());
        if (authorJson != null) {
            return gson.fromJson(authorJson, AuthorDto.class);
        } else {
            Author author = authorRepository.findById(authorId)
                    .orElseThrow(() -> new AuthorNotFoundException("Автор с данным id не найден"));

            redis.opsForValue().set("cache_author_" + author.getId().toString(), gson.toJson(mapperService.toAuthorDto(author)), Duration.ofMinutes(30));
            return mapperService.toAuthorDto(author);
        }
    }

    @Override
    public void deleteAuthorById(UUID authorId) {
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new AuthorNotFoundException("Автор с данным id не найден"));

        redis.opsForValue().getAndDelete("cache_author_" + authorId);
        authorRepository.deleteById(authorId);
    }
}
