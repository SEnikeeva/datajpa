package ru.itis.repository;

import ru.itis.model.User;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findByLogin(String login);

    User save(User user);
}
