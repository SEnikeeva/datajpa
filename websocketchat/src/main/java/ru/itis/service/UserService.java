package ru.itis.service;

import ru.itis.model.User;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public interface UserService {
    Optional<User> checkPassword(String login, String password);

    User getUser(Cookie cookie);

    User getUser(String cookie);
}
