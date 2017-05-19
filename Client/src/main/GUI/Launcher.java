package main.GUI; /**
 * Created by Luca,Andrea on 16/05/2017.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.client.MainClient;

import java.io.IOException;
import java.rmi.NotBoundException;


public class Launcher extends Application {
    public static void main(String[] args) throws IOException, NotBoundException {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("launcherView.fxml"));
        primaryStage.centerOnScreen();
        primaryStage.setScene(new Scene(root, 600, 350));
        primaryStage.setTitle("Lorenzo Il Magnifico");
        primaryStage.show();


    }


}
