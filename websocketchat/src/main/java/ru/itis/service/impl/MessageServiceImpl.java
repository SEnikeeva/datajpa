package ru.itis.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.dto.MessageDto;
import ru.itis.dto.SendMessageDto;
import ru.itis.model.Message;
import ru.itis.model.Room;
import ru.itis.model.User;
import ru.itis.repository.MessageRepository;
import ru.itis.repository.RoomRepository;
import ru.itis.service.MessageService;

import java.util.Date;

@Service
@AllArgsConstructor
class MessageServiceImpl implements MessageService {

    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private MessageRepository messageRepository;


    @Override
    @Transactional
    public MessageDto send(String roomId, SendMessageDto dto, User user) {
        Room room = roomRepository.findByName(roomId).orElseThrow(IllegalAccessError::new);

        Message message = messageRepository.save(Message.builder()
                .room(room)
                .sender(user)
                .text(dto.getText())
                .time(new Date())
                .build());

        return MessageDto.from(message);
    }
}
