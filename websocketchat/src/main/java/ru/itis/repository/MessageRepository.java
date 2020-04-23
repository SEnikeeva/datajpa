package ru.itis.repository;

import ru.itis.model.Message;
import ru.itis.model.Room;

import java.util.List;

public interface MessageRepository {

    Message save(Message message);

    List<Message> findByRoom(Room room);
}
