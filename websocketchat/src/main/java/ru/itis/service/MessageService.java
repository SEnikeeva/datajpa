package ru.itis.service;


import ru.itis.dto.MessageDto;
import ru.itis.dto.SendMessageDto;
import ru.itis.model.User;

public interface MessageService {

    MessageDto send(String roomId, SendMessageDto dto, User user);
}
