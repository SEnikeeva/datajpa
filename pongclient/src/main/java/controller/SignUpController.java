package controller;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController implements Initializable{
    public TextField login;
    public PasswordField password;
    public Button signUp;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public SignUpController(Socket socket) {
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

        signUp.setOnAction(event -> {
            out.println("new user:" + login.getText() + ":" + password.getText());
            out.println("select");
            signUp.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/select.fxml"));
            loader.setControllerFactory(c -> new SelectController(socket));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Stage stage = new Stage();
            stage.setScene(new Scene(loader.getRoot()));
            stage.showAndWait();
        });
    }
}
