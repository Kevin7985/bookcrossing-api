package ru.ist.users;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ist.users.model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByLoginAndPassword(String login, String password);
}
