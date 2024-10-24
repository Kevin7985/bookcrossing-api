package ru.ist.authors;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ist.authors.model.Author;

import java.util.UUID;

public interface AuthorRepository extends JpaRepository<Author, UUID> { }
