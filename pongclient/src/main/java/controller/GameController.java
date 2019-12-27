package controller;

import helper.Constants;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Random;

import static helper.Constants.*;

public class GameController {


    private int ballYSpeed = 1;
    private int ballXSpeed = 1;
    private double playerOneYPos = HEIGHT / 2;
    private double playerTwoYPos = HEIGHT / 2;
    private double ballXPos = WIDTH / 2;
    private double ballYPos = HEIGHT / 2;
    private int scoreP1 = 0;
    private int scoreP2 = 0;
    private boolean gameStarted;
    private int playerOneXPos = 0;
    private double playerTwoXPos = WIDTH - PLAYER_WIDTH;


    public void initialize()
    {
        Stage stage = new Stage();

        stage.setTitle("PONG");
        Canvas canvas = new Canvas(WIDTH, Constants.HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Timeline tl = new Timeline(new KeyFrame(Duration.millis(10), e -> run(gc)));
        tl.setCycleCount(Timeline.INDEFINITE);

        canvas.setOnMouseMoved(e ->  playerOneYPos  = e.getY());
        canvas.setOnMouseClicked(e ->  gameStarted = true);
        stage.setScene(new Scene(new StackPane(canvas)));
        stage.show();
        tl.play();
    }

    private void run(GraphicsContext gc) {
        gc.setFill(Color.BLUEVIOLET);
        gc.fillRect(0, 0, WIDTH, HEIGHT);

        gc.setFill(Color.WHITE);
        gc.setFont(Font.font(25));

        if(gameStarted) {

            ballXPos+=ballXSpeed;
            ballYPos+=ballYSpeed;

            playerTwoYPos = ballYPos - PLAYER_HEIGHT / 2;

            gc.fillOval(ballXPos, ballYPos, BALL_R, BALL_R);


        } else {
            gc.setStroke(Color.WHITE);
            gc.setTextAlign(TextAlignment.CENTER);
            gc.strokeText("Click for start", WIDTH / 2, HEIGHT / 2);

            ballXPos = WIDTH / 2;
            ballYPos = HEIGHT / 2;

            ballXSpeed = new Random().nextInt(2) == 0 ? 1: -1;
            ballYSpeed = new Random().nextInt(2) == 0 ? 1: -1;
        }

        if(ballYPos > HEIGHT || ballYPos < 0) ballYSpeed *=-1;

        if(ballXPos < playerOneXPos - PLAYER_WIDTH) {
            scoreP2++;
            gameStarted = false;
        }

        if(ballXPos > playerTwoXPos + PLAYER_WIDTH) {
            scoreP1++;
            gameStarted = false;
        }

        if( ((ballXPos + BALL_R > playerTwoXPos) && ballYPos >= playerTwoYPos && ballYPos <= playerTwoYPos + PLAYER_HEIGHT) ||
                ((ballXPos < playerOneXPos + PLAYER_WIDTH) && ballYPos >= playerOneYPos && ballYPos <= playerOneYPos + PLAYER_HEIGHT)) {
            ballYSpeed += 1 * Math.signum(ballYSpeed);
            ballXSpeed += 1 * Math.signum(ballXSpeed);
            //ballXSpeed += ballXSpeed > 0 ? 1 : -1;
            ballXSpeed *= -1;
            //ballYSpeed *= -1;
            //ballYSpeed *= Math.abs(ballYSpeed) <= 1 ? ballYSpeed * (new Random().nextInt(2) + 1) : (int) Math.signum(1.0 * ballYSpeed);
        }


        gc.fillText(scoreP1 + "\t\t\t\t\t\t\t\t" + scoreP2, WIDTH / 2, 100);

        gc.fillRect(playerTwoXPos, playerTwoYPos, PLAYER_WIDTH, PLAYER_HEIGHT);
        gc.fillRect(playerOneXPos, playerOneYPos, PLAYER_WIDTH, PLAYER_HEIGHT);
    }
}
