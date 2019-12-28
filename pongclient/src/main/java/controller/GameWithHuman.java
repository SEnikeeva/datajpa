package controller;

import helper.Constants;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

import static helper.Constants.*;
import static helper.Constants.BALL_R;

public class GameWithHuman {

    private StackPane gamePane;
    private Canvas canvas;
    private GraphicsContext context;
    private int ballYSpeed = 1;
    private int ballXSpeed = 1;
    private boolean role;
    private double playerOneYPos = HEIGHT / 2;
    private double playerTwoYPos = HEIGHT / 2;
    private double ballXPos = WIDTH / 2;
    private double ballYPos = HEIGHT / 2;
    private int scoreP1 = 0;
    private int scoreP2 = 0;
    private boolean gameStarted = false;
    private int playerOneXPos = 0;
    private double playerTwoXPos = WIDTH - PLAYER_WIDTH;
    private PrintWriter out;
    private BufferedReader in;
    public void start(Socket socket) {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Stage stage = new Stage();

        stage.setTitle("PONG");
        canvas = new Canvas(WIDTH, Constants.HEIGHT);
        context = canvas.getGraphicsContext2D();

        gamePane = new StackPane(canvas);
        Timeline tl = new Timeline(new KeyFrame(Duration.millis(10), e -> run(context)));
        tl.setCycleCount(Timeline.INDEFINITE);

        canvas.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                playerOneYPos  = event.getY();
                out.println("y:" + event.getY());
                try {
                    playerTwoYPos =Integer.parseInt(in.readLine().split(":")[1]) ;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        canvas.setOnMouseClicked(e ->  gameStarted = true);
        stage.setScene(new Scene(new StackPane(canvas)));
        stage.show();
        tl.play();
    }

    private void run(GraphicsContext gc) {
        out.println("coordinates:" + ballXPos + ":" + ballYPos);
        String[] coordinates;
        try {
            coordinates = in.readLine().split(":");
            role = Boolean.parseBoolean(coordinates[0]);
            playerTwoYPos =Integer.parseInt(coordinates[1]);
            ballXPos = role? Integer.parseInt(coordinates[2]): 800 -Integer.parseInt(coordinates[2]) ;
            ballYPos = Integer.parseInt(coordinates[3]);


        } catch (IOException e) {
            e.printStackTrace();
        }
        gc.setFill(Color.BLUEVIOLET);
        gc.fillRect(0, 0, WIDTH, HEIGHT);

        gc.setFill(Color.CORNFLOWERBLUE);
        gc.setFont(Font.font(25));

        if (role) {
            if(gameStarted) {

                ballXPos += ballXSpeed;
                ballYPos += ballYSpeed;

                if((ballYPos > HEIGHT || ballYPos < 0)) ballYSpeed *=-1;

                if(ballXPos < playerOneXPos - PLAYER_WIDTH) {
                    scoreP2++;
                    gameStarted = false;
                    //out.println("stop");
                }

                if(ballXPos > playerTwoXPos + PLAYER_WIDTH) {
                    scoreP1++;
                    gameStarted = false;
                }

                if( ((ballXPos + BALL_R > playerTwoXPos) && ballYPos >= playerTwoYPos && ballYPos <= playerTwoYPos + PLAYER_HEIGHT) ||
                        ((ballXPos < playerOneXPos + PLAYER_WIDTH) && ballYPos >= playerOneYPos && ballYPos <= playerOneYPos + PLAYER_HEIGHT)) {
                    ballYSpeed += 1 * Math.signum(ballYSpeed);
                    ballXSpeed += 1 * Math.signum(ballXSpeed);
                    ballXSpeed *= -1;
                }


            } else {
                String text;
                gc.setStroke(Color.WHITE);
                gc.setTextAlign(TextAlignment.CENTER);
                if (role) {
                    text = "Click for start";
                }
                else text = "Click for start and watch in player's one screen";
                gc.strokeText(text, WIDTH / 2, HEIGHT / 2);

                ballYPos = HEIGHT / 2;
                ballXPos = WIDTH / 2;
                if (role) {
                    ballXSpeed = new Random().nextInt(2) == 0 ? 1: -1;
                    ballYSpeed = new Random().nextInt(2) == 0 ? 1: -1;
                }

            }
        } else {
            if (!gameStarted) {
                String text = "Click for start and watch in player's one screen";
                gc.strokeText(text, WIDTH / 2, HEIGHT / 2);
            } else  {

                if(ballXPos < playerOneXPos - PLAYER_WIDTH) {
                    scoreP2++;
                    gameStarted = false;
                }

                if(ballXPos > playerTwoXPos + PLAYER_WIDTH) {
                    scoreP1++;
                    gameStarted = false;
                }
            }

        }
        gc.fillOval(ballXPos, ballYPos, BALL_R, BALL_R);

        gc.fillText(scoreP1 + "\t\t\t\t\t\t\t\t" + scoreP2, WIDTH / 2, 100);

        gc.fillRect(playerTwoXPos, playerTwoYPos, PLAYER_WIDTH, PLAYER_HEIGHT);
        gc.fillRect(playerOneXPos, playerOneYPos, PLAYER_WIDTH, PLAYER_HEIGHT);
    }
}
