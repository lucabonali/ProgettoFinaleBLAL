package main;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.NotBoundException;


/**
 * @author Luca
 * @author Andrea
 */
public class Launcher extends Application {
    /**
     * Metodo Main del client, che lancia il metodo start di questa classe
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
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("main/gui/startScreen/start_screen.fxml"));
        primaryStage.setOnCloseRequest(event -> System.exit(0));
        primaryStage.centerOnScreen();
        primaryStage.setScene(new Scene(root, 285, 435));
        primaryStage.setTitle("Lorenzo Il Magnifico");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("res/Icona_testa.png")));
        primaryStage.show();
    }


}
