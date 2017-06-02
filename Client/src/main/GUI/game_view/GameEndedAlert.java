package main.GUI.game_view;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * @author Andrea
 * @author Luca
 */
public class GameEndedAlert extends Alert{
    private String msg;
    private GUIController guiController;

    public GameEndedAlert(String msg, GUIController guiController) {
        super(AlertType.NONE);
        this.msg = msg;
        this.guiController = guiController;
        createAlert();
    }

    private void createAlert() {
        setTitle("Game Ended Alert");
        setHeaderText("this game is over");
        setContentText(msg);
        getButtonTypes().addAll(ButtonType.OK);
        showAndWait().ifPresent((buttonType -> {
            if (buttonType == ButtonType.OK)
                guiController.backToMenu();
        }));
    }
}
