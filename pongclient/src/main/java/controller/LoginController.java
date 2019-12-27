package controller;

import dto.ProtocolDto;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private Button signUp;
    @FXML
    private Button signIn;
    @FXML
    private TextField password;
    @FXML
    private TextField login;

    private DataInputStream in;
    private PrintWriter out;
    private Socket socket;

    public LoginController(ProtocolDto protocolDto) {
        socket  = null;
        try {
            socket = new Socket(protocolDto.getHost(), protocolDto.getPort());
            in = new DataInputStream(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        signUp.setOnAction(event -> {
            signUp.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/signup.fxml"));
            loader.setControllerFactory(c -> new SignUpController(socket));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.getRoot()));
            stage.showAndWait();
        });
        login.setOnMouseClicked(event -> {
            if(login.getText().equals("login"))
                login.clear();
        });

        password.setOnMouseClicked(event -> {
            password.clear();
        });

        signIn.setOnAction(event -> {
            out.println("user:" + login.getText() + ":" + password.getText());
            try {
                String inputLine = in.readLine();
                if (inputLine.equals("error")) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Wrong password or username");
                    alert.showAndWait();
                    password.clear();
                }
                else {
                    out.println("select");
                    signIn.getScene().getWindow().hide();
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/view/select.fxml"));
                    loader.setControllerFactory(c -> new SelectController(socket));
                    loader.load();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(loader.getRoot()));
                    stage.showAndWait();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
    }
}
