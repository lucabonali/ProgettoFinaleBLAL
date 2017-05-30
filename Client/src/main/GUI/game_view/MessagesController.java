package main.GUI.game_view;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import main.GUI.music.Music;

/**
 * @author Andrea
 * @author Luca
 */
public class MessagesController {

    @FXML private ImageView lory;
    @FXML private Label messagesLabel;
    @FXML private Label lorenzoTalks;
    private Music audio;


    private StringProperty message;
    private String EXTENSION = ".wav";

    public void setMessage(String msg){
        Platform.runLater(()->message.setValue(msg));
        playAnimation(msg);
    }

    /**
     * metodo che inizializza l' animazione di lorenzo che parla
     */
    private void playAnimation(String msg) {
        LorenzoAnimation lorenzoAnimation = new LorenzoAnimation(lory , msg );
        lorenzoAnimation.startTalkAnimation();
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
        lorenzoTalks.setText("Hi i'm Lorenzo!!");
    }
}
