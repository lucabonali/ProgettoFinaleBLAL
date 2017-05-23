package main.gui.game_view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * @author lampa
 */
public class GameTest extends Application{
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("main/gui/game_view/game_view_2.fxml"));
        Parent window = (Pane) fxmlLoader.load();
        primaryStage.setScene(new Scene(window));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
