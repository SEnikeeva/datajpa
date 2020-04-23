package ru.itis.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.dto.MessageDto;
import ru.itis.dto.RoomMessagesDto;
import ru.itis.dto.RoomDto;
import ru.itis.model.Room;
import ru.itis.repository.MessageRepository;
import ru.itis.repository.RoomRepository;
import ru.itis.service.RoomService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
class RoomServiceImpl implements RoomService {


    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Override
    public void createRoom(RoomDto dto) {
        roomRepository.save(Room.builder()
                .name(dto.getName())
                .build());
    }

    @Override
    public List<RoomDto> getRooms() {
        return roomRepository.findAll().stream()
                .map(RoomDto::from)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public RoomMessagesDto getRoom(String roomId) {
        Room room = roomRepository.findByName(roomId).orElseThrow(IllegalAccessError::new);
        List<MessageDto> messages = messageRepository.findByRoom(room).stream()
                .map(MessageDto::from)
                .collect(Collectors.toList());

        return RoomMessagesDto.builder()
                .name(room.getName())
                .messages(messages)
                .build();
    }
}
