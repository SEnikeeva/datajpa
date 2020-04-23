package ru.itis.controllers;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itis.dto.RoomDto;
import ru.itis.service.RoomService;
import ru.itis.service.UserService;

import javax.servlet.http.Cookie;

@Controller
@AllArgsConstructor
public class ChatController {

    @Autowired
    private RoomService roomService;
    @Autowired
    private UserService service;


    @GetMapping("/chat")
    public String getRooms(ModelMap map, @CookieValue(value = "curr_user", required = false) Cookie cookie) {
        if(cookie != null) {
            map.put("user", service.getUser(cookie).getLogin());
            map.put("rooms", roomService.getRooms());
            return "rooms";
        }
        throw new IllegalStateException();
    }

    @GetMapping("/chat/{user}/{room}")
    public String getChat( @PathVariable String room, ModelMap map,  @CookieValue(value = "curr_user", required = false) Cookie cookie) {
        if(cookie != null) {
            map.put("room", roomService.getRoom(room));
            map.put("user", service.getUser(cookie).getLogin());
            return "chat";
        }
        throw new IllegalStateException();

    }

    @PostMapping("/chat")
    public String createRoom(RoomDto dto, @CookieValue(value = "curr_user", required = false) Cookie cookie) {
        if(cookie != null) {
            roomService.createRoom(dto);
            return "redirect:/chat";
        }
        throw new IllegalStateException();

    }


}
