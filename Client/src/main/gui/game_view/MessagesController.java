package main.gui.game_view;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.gui.music.Music;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.Random;

/**
 * @author Andrea
 * @author Luca
 */
public class MessagesController {
    @FXML private ImageView lory;
    @FXML private Label messagesLabel;
    @FXML private Label lorenzoTalks;
    private Music audio;
    private Image lorenzo,lorenzoSemi,lorenzoAperta;

    private StringProperty message;
    private String EXTENSION = ".wav";

    public void setMessage(String msg){
        Platform.runLater(()->message.setValue(msg));
        playAudio();
    }

    private void playAudio() {
        try {
            audio.play(audio.getPath()+"whistle"+EXTENSION);
            talk();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }

    private void talk() {
        int n1 = 1 + new Random().nextInt(6);
        int n2 = 1 + new Random().nextInt(6);
        int n3 = 1 + new Random().nextInt(6);
        try {
            audio.play(audio.getPath() + "lorenzoTalks/talk" + n1 + EXTENSION);
            audio.play(audio.getPath() + "lorenzoTalks/talk" + n2 + EXTENSION);
            audio.play(audio.getPath() + "lorenzoTalks/talk" + n3 + EXTENSION);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        }

        new Animation().start();
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

        lorenzo = new Image(getClass().getResourceAsStream("res/lorenzo.png"));
        lorenzoSemi = new Image(getClass().getResourceAsStream("res/lorenzoSemi.png"));
        lorenzoAperta = new Image(getClass().getResourceAsStream("res/lorenzoAperta.png"));


        audio = new Music();
    }


    private void moveMouth() {
        int number = 1 + new Random().nextInt(3);
        if(number == 1)
            lory.setImage(lorenzo);
        if(number == 2 )
            lory.setImage(lorenzoSemi);
        if(number == 3)
            lory.setImage(lorenzoAperta);

    }


    private class Animation extends Thread{

        public void run(){
            for(int i = 0 ; i<10 ; i++){
                moveMouth();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            lory.setImage(lorenzo);
        }

    }




}
