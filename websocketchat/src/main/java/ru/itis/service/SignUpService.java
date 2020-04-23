package ru.itis.service;

import ru.itis.dto.SignInDto;
import ru.itis.model.User;

public interface SignUpService {

    User signUp(SignInDto dto);
}
