package client;

import controller.LoginController;
import dto.AuthorizationDto;
import dto.ProtocolDto;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.Optional;

public class MainClient extends Application {

    private static Stage primaryStage;
    static public Stage getStage() {
        return primaryStage;
    }
    private void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }
    private Pane rootLayout;

    @Override
    public void start(Stage primaryStage) throws Exception {
        setPrimaryStage(primaryStage);
        primaryStage.setTitle("Log in");
        initRootLayout();
    }
    public void initRootLayout() {
        Dialog<ProtocolDto> dialog = new Dialog<>();
        dialog.setTitle("Connection");
        dialog.setResizable(true);
        Label label1 = new Label("Host: ");
        Label label2 = new Label("Port: ");
        TextField text1 = new TextField();
        TextField text2 = new TextField();
        GridPane grid = new GridPane();
        grid.add(label1, 1, 1);
        grid.add(text1, 2, 1);
        grid.add(label2, 1, 2);
        grid.add(text2, 2, 2);
        dialog.getDialogPane().setContent(grid);
        ButtonType buttonTypeOk = new ButtonType("Connect", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
        //dialog.show();
        dialog.setResultConverter(new Callback<ButtonType, ProtocolDto>() {
            @Override
            public ProtocolDto call(ButtonType b) {
                if (b == buttonTypeOk) {
                    return new ProtocolDto(text1.getText(), Integer.parseInt(text2.getText()));
                }
                return null;
            }
        });
        Optional<ProtocolDto> result = dialog.showAndWait();
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainClient.class.getResource("/view/login.fxml"));
            loader.setControllerFactory(c -> new LoginController(result.get()));
            rootLayout = loader.load();
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
