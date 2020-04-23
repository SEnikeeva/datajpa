package ru.itis.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.dto.SignInDto;
import ru.itis.model.User;
import ru.itis.repository.UserRepository;
import ru.itis.service.SignInService;
import ru.itis.service.UserService;

@Service
@AllArgsConstructor
class SignInServiceImpl implements SignInService {


    @Autowired
    private UserService service;

    @Autowired
    private UserRepository userRepository;


    @Override
    public User signIn(SignInDto dto) {
        if (service.checkPassword(dto.getLogin(), dto.getPassword()).isPresent())
            return userRepository.findByLogin(dto.getLogin()).get();
        return null;
    }
}
