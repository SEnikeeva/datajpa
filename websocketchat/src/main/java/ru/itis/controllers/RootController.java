package ru.itis.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;

@Controller
@AllArgsConstructor
public class RootController {

    @GetMapping("/")
    public ModelAndView getPage(@CookieValue(value = "curr_user", required = false) Cookie cookie) {
        if (cookie != null) {
            return new ModelAndView("home", "authenticated", true);
        }
        return new ModelAndView("home", "authenticated", false);
    }
}
