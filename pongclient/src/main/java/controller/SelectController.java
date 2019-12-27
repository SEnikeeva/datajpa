package controller;

import helper.StartTask;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.util.Duration;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.*;

@Data
@NoArgsConstructor
public class SelectController implements Initializable{

    public ListView gamers;
    public Button start;
    public Button request;
    private PrintWriter out;
    private BufferedReader in;
    private Socket socket;
    private boolean gameStarted;
    Timer timer = new Timer();

    public SelectController(Socket socket) {
        this.socket = socket;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<String> users = new ArrayList<>();
        try {
            String[] jar = in.readLine().split(":");
            for (int i = 1; i < jar.length; i++) {
                users.add(jar[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        gamers.setItems(FXCollections.observableList(users));
        gamers.setCellFactory(ComboBoxListCell.forListView(users));

       StartTask task = new StartTask(this);
        if (!gameStarted) {
            timer.scheduleAtFixedRate(task, 1000, 1000);
        }
    }

    @FXML
    private void start(ActionEvent actionEvent) {
        start.getScene().getWindow().hide();
        new GameController().initialize();
    }

    @FXML
    private void request(ActionEvent actionEvent) {
        request.getScene().getWindow().hide();
        gameStarted = true;
        out.println("start game:" + gamers.getSelectionModel().getSelectedItem().toString());
        new GameWithHuman().start(socket);
    }

}
