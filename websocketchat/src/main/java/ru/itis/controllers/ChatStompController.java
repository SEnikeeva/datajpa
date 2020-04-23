package ru.itis.controllers;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import ru.itis.dto.MessageDto;
import ru.itis.dto.SendMessageDto;
import ru.itis.service.MessageService;
import ru.itis.service.UserService;

@Controller
@AllArgsConstructor
public class ChatStompController {

    private final MessageService messageService;
    @Autowired
    private UserService service;

    @MessageMapping("/chat/{user}/{room}")
    @SendTo("/topic/chat/{user}/{room}")
    public MessageDto sendMessage(@DestinationVariable String room, SendMessageDto dto, @DestinationVariable String user) {
        return messageService.send(room, dto, service.getUser(user));

    }
}
