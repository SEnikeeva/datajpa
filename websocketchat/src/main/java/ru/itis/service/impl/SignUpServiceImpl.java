package ru.itis.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.dto.SignInDto;
import ru.itis.model.User;
import ru.itis.repository.UserRepository;
import ru.itis.service.SignUpService;

@Service
@AllArgsConstructor
class SignUpServiceImpl implements SignUpService {



    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public User signUp(SignInDto dto) {

        return userRepository.save( User.builder().login(dto.getLogin())
                .password(encoder.encode(dto.getPassword()))
                .build());
    }
}
