package ru.ist.authors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.ist.authors.model.Author;

import java.util.UUID;

public interface AuthorRepository extends JpaRepository<Author, UUID> {
    @Query("select a from Author a" +
            " where upper(concat(a.name, ' ', a.patronymic, ' ', a.surname)) like upper(concat('%', ?1, '%'))"
    )
    Page<Author> search(String text, Pageable pageable);
}
