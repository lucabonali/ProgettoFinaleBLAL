package main;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.NotBoundException;


/**
 * @author Luca
 * @author Andrea
 */
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
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("main/resources/views/login_view.fxml"));
        primaryStage.setOnCloseRequest(event -> System.exit(0));
        primaryStage.centerOnScreen();
        primaryStage.setScene(new Scene(root, 600, 350));
        primaryStage.setTitle("Lorenzo Il Magnifico");
        primaryStage.show();
    }


}
