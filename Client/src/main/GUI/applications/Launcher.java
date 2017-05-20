package main.GUI.applications;

/**
 *
 * Created by Luca,Andrea on 16/05/2017.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.NotBoundException;


public class Launcher extends Application {
    /**
     * Metodo Main del clientGame, che lancia il metodo start di questa classe
     * @param args
     * @throws IOException
     * @throws NotBoundException
     */
    public static void main(String[] args) throws IOException, NotBoundException {
        launch(args);
    }


    /**
     * Metodo che visualizza la schermata di login del gioco
     * @param primaryStage finestra iniziale
     * @throws IOException
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("fxml/launcherView.fxml"));
        primaryStage.centerOnScreen();
        primaryStage.setScene(new Scene(root, 600, 350));
        primaryStage.setTitle("Lorenzo Il Magnifico");
        primaryStage.show();
    }


}
