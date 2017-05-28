package main.gui.game_view;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import main.gui.music.Music;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

/**
 * @author Andrea
 * @author Luca
 */
public class MessagesController {
    @FXML private Label messagesLabel;
    @FXML private Label lorenzoTalks;
    private Music audio;

    private StringProperty message;

    public void setMessage(String msg){
        Platform.runLater(()->message.setValue(msg));
        playAudio();
    }

    private void playAudio() {
        try {
            audio.play(audio.getPath()+"notificationBird.wav");
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
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

        audio = new Music();
    }
}
