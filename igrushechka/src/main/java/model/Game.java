package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Game {
    public Game(User p1, User p2) {
        this.p1 = p1;
        this.p2 = p2;
    }
    private int p1Y;
    private int p2Y;
    private User p1;
    private User p2;
    private int bY;
    private int bX;
}
