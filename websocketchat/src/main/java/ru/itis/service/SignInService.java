package ru.itis.service;

import ru.itis.dto.SignInDto;
import ru.itis.model.User;

public interface SignInService {

    User signIn(SignInDto dto);
}
