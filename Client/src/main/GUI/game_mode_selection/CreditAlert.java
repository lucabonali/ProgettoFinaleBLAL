package main.GUI.game_mode_selection;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;

/**
 * @author Andrea
 * @author Luca
 */
public class CreditAlert extends Alert {
    private double WIDTH = 320, HEGHT = 155;
    ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);


    public CreditAlert() {
        super(AlertType.NONE);
        this.setOnCloseRequest(e -> this.close());
        initializeAlert();
    }

    private void initializeAlert() {
        DialogPane pane = getDialogPane();
        pane.getStylesheets().add(getClass().getResource("res/style.css").toExternalForm());
        pane.setId("privilegeAlert");
        pane.setPrefSize(WIDTH, HEGHT);

        getButtonTypes().setAll(okButton);
        showAndWait().ifPresent(response -> {
               if (response == okButton) {
                    this.close();
               }
        });

    }


}
