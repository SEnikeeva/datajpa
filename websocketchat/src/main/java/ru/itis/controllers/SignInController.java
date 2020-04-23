package ru.itis.controllers;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itis.dto.SignInDto;
import ru.itis.model.User;
import ru.itis.service.SignInService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
@AllArgsConstructor
public class SignInController {

    private final SignInService signInService;

    @GetMapping("/signIn")
    public String getSignInPage() {
        return "signIn";
    }

    @PostMapping("/signIn")
    public String signIn(HttpServletResponse httpServletResponse,
                         @RequestParam(value = "login") String login,
                         @RequestParam(value = "password") String password) {
        User user = signInService.signIn(new SignInDto(login, password));
        Cookie cookie = new Cookie("curr_user", user.getLogin());
        httpServletResponse.addCookie(cookie);

        return "redirect:/";
    }

}
