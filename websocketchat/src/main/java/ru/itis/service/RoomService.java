package ru.itis.service;


import ru.itis.dto.RoomMessagesDto;
import ru.itis.dto.RoomDto;

import java.util.List;

public interface RoomService {

    void createRoom(RoomDto dto);

    List<RoomDto> getRooms();

    RoomMessagesDto getRoom(String roomId);
}
