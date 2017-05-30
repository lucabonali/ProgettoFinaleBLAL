package main;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.rmi.NotBoundException;


/**
 * @author Luca
 * @author Andrea
 */
public class GUILauncher extends Application {
    private static Stage primaryStage;
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
     * @param stage finestra iniziale
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("main/GUI/start_screen/start_screen.fxml"));
        stage.setOnCloseRequest(event -> System.exit(0));
        stage.centerOnScreen();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(false);
        stage.setScene(new Scene(root, 356, 542));
        stage.setTitle("Lorenzo Il Magnifico");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("res/Icona_testa.png")));
        stage.show();

    }

   public static Stage getPrimaryStage(){
        return primaryStage;
    }


}
