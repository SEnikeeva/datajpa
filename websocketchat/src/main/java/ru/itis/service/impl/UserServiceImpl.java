package ru.itis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.itis.model.User;
import ru.itis.repository.UserRepository;
import ru.itis.service.UserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder encoder;

    @Override
    public Optional<User> checkPassword(String login, String password) {
        if ( userRepository.findByLogin(login).isPresent()) {
            User user = userRepository.findByLogin(login).orElseThrow(IllegalArgumentException::new);
            if (encoder.matches(password, user.getPassword())) {
                return Optional.of(user);
            } else
                return Optional.empty();
        }
        else return Optional.empty();
    }


    @Override
    public User getUser(Cookie cookie) {
        return userRepository.findByLogin(cookie.getValue()).get();
    }


    @Override
    public User getUser(String cookie) {
        return userRepository.findByLogin(cookie
        ).get();
    }
}
