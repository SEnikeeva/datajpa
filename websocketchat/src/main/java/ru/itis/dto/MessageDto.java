package ru.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.model.Message;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto {
    private String sender;
    private String text;
    private String time;

    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static MessageDto from(Message message) {
        return MessageDto.builder()
                .sender(message.getSender().getLogin())
                .text(message.getText())
                .time(dateFormat.format(message.getTime()))
                .build();
    }
}
