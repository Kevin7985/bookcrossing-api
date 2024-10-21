package ru.ist.users;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ist.users.model.User;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> { }
