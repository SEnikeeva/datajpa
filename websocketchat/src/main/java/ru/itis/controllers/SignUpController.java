package ru.itis.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itis.dto.SignInDto;
import ru.itis.model.User;
import ru.itis.service.SignUpService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
@AllArgsConstructor
public class SignUpController {

    private final SignUpService signUpService;


    @GetMapping("/signUp")
    public String getSignUpPage() {
        return "signUp";
    }

    @PostMapping("/signUp")
    public String signUp(HttpServletResponse httpServletResponse,
                         @RequestParam(value = "login") String login,
                         @RequestParam(value = "password") String password) {
        User user = signUpService.signUp(new SignInDto(login, password));
        Cookie cookie = new Cookie("curr_user", user.getLogin());
        httpServletResponse.addCookie(cookie);

        return "redirect:/";
    }

}
