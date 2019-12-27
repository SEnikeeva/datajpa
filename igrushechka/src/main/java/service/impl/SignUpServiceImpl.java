package service.impl;

import context.Component;
import lombok.NoArgsConstructor;
import model.User;
import repository.UserRepository;
import service.SignUpService;

import java.util.Optional;

@NoArgsConstructor
public class SignUpServiceImpl implements SignUpService, Component {
    private UserRepository userRepository;

    @Override
    public void signUp(String login, String password) {
        userRepository.save(new User(0,  login, password, 0));
    }
}
