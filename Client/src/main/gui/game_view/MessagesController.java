package main.gui.game_view;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * @author Andrea
 * @author Luca
 */
public class MessagesController {
    @FXML private Label messagesLabel;
    @FXML private Label lorenzoTalks;

    private StringProperty message;

    public void setMessage(String msg) {
        Platform.runLater(()->message.setValue(msg));
    }

    public void initialize() {
        message = new SimpleStringProperty();
        messagesLabel.textProperty().bind(message);
        messagesLabel.setOnMouseEntered(event -> {
            //fare qualcosa
        });
        messagesLabel.setOnMouseExited(event -> {
           //fare qualcos'altro
        });
        //messagesLabel.setEffect(new GaussianBlur(20));
        lorenzoTalks.setText("Ciao, sono Lorenzo!!");



    }
}
