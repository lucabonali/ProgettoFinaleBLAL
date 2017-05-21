package main.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.clientGame.AbstractClient;

import java.io.IOException;

/**
 * @author Luca
 * @author Andrea
 */
public class GameSelectionController {
    private AbstractClient client;

    @FXML private Button startGameButton;

    @FXML
    private void startGame() throws IOException {
        client.startGame();
        startGameMode();
    }

    private void startGameMode() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("main/resources/views/game_view.fxml"));
        Parent window = (Pane) fxmlLoader.load();
        GameController controller = fxmlLoader.getController();
        AbstractClient.getInstance().setGameController(controller); //setto il controller
        Platform.runLater(()->{
            Stage stage = (Stage) startGameButton.getScene().getWindow();
            Scene scene = new Scene(window);
            stage.setScene(scene);
            stage.setTitle("LORENZO IL MAGNIFICO");
        });
    }

    public void initialize() {
        client = AbstractClient.getInstance();
    }
}
