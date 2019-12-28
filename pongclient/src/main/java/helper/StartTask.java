package helper;

import controller.GameWithHuman;
import controller.SelectController;
import javafx.application.Platform;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.TimerTask;


@AllArgsConstructor
@NoArgsConstructor
public class StartTask extends TimerTask {
    //private Socket socket;
    private SelectController controller;


    @Override
    public void run() {
        Platform.runLater(() -> {
            PrintWriter out;
            BufferedReader in;
            try {
                out = new PrintWriter(controller.getSocket().getOutputStream(), true);
                out.println("should i start?");
                in = new BufferedReader(new InputStreamReader(controller.getSocket().getInputStream()));
                String inputLine = in.readLine();
                if (inputLine.equals("let it snow")) {
                    controller.setGameStarted(true);
                    new GameWithHuman().start(controller.getSocket());
                    controller.start.getScene().getWindow().hide();
                    controller.getTimer().cancel();
                    controller.getTimer().purge();
                }
                if (controller.isGameStarted()) {
                    controller.getTimer().cancel();
                    controller.getTimer().purge();
                }
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }

        });
    }
}
