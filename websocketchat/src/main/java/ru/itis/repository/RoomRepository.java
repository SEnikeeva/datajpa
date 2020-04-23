package ru.itis.repository;

import ru.itis.model.Room;

import java.util.List;
import java.util.Optional;

public interface RoomRepository {

    Optional<Room> findByName(String token);

    List<Room> findAll();

    Room save(Room room);
}
