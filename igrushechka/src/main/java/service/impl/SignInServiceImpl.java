package service.impl;

import dto.AuthorizationDto;
import model.User;
import repository.UserRepository;
import service.SignInService;
import service.UserService;

import java.util.Optional;

public class SignInServiceImpl implements SignInService {

    private UserRepository userRepository;
    private UserService service;

    @Override
    public AuthorizationDto signIn(String login, String password) {
        /*String login = user.get().getUsername();
        String password = user.get().getPassword();*/
        if (!service.checkUser(login)) {
            return null;
        }
        else {
            if (service.checkPassword(login, password)) {
                User user1 = userRepository.findByLogin(login);
                return AuthorizationDto.from(user1);
            } else {
                return null;
            }
        }
    }
}
