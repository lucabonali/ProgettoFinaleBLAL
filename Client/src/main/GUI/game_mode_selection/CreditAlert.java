package main.GUI.game_mode_selection;

import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;

/**
 * @author Andrea
 * @author Luca
 */
public class CreditAlert extends Alert {


    public CreditAlert(AlertType alertType) {
        super(alertType);
        initializeAlert();
        this.show();
        
    }

    private void initializeAlert() {

        DialogPane pane = getDialogPane();
        pane.getStylesheets().add(getClass().getResource("res/style.css").toExternalForm());
        pane.setId("privilegeAlert");
    }


}
