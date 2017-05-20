package main.GUI.applications;

/**
 * Launcher della finestra che segnalerà la ricerca di una partita, ci sarà scritto ricerca partita in corso,
 * quando trovata segnalerà partità trovata, e quando la partita comincierà, aprirà la finestra principale della partita
 * @author Andrea, Luca
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;

public class GameSelection extends Application {

    public void launchGameSelection() {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("fxml/GameSelectonView.fxml"));


    }
}
