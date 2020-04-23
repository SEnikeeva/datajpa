package ru.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.model.Room;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomDto {
    private String name;
    public static RoomDto from(Room room) {
        return builder()
                .name(room.getName())
                .build();
    }
}
