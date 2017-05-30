package main.gui.game_view;

import javafx.animation.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import main.gui.music.Music;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.Random;


/**
 * Classe che racchiude il comportamento di lorenzo
 * @author Andrea
 * @author Luca
 */
public class LorenzoAnimation {
    private ImageView lory;
    private String message;
    private RotateTransition rotateTransition;
    private ScaleTransition scaleTransition;
    private TranslateTransition translateTransition;
    private ParallelTransition messageViewTransition;
    private Music audio;
    private Image lorenzo, lorenzoSemi, lorenzoAperta;
    private String EXTENSION = ".wav";
    private Talk lorenzoTalk;
    private boolean isStartGame = false;


    public LorenzoAnimation(ImageView lorenzoImageView, String message) {
        this.lory = lorenzoImageView;
        this.message = message;
        audio = new Music();
        lorenzo = new Image(getClass().getResourceAsStream("res/lorenzo.png"));
        lorenzoSemi = new Image(getClass().getResourceAsStream("res/lorenzoSemi.png"));
        lorenzoAperta = new Image(getClass().getResourceAsStream("res/lorenzoAperta.png"));
    }

    public void startGameAnimation() {
        isStartGame = true;
        rotateTransition = new RotateTransition(Duration.millis(1500));
        scaleTransition = new ScaleTransition(Duration.millis(1500));
        rotateTransition.setNode(lory);
        rotateTransition.setByAngle(1080);
        scaleTransition.setFromX(0.5);
        scaleTransition.setFromY(0.5);
        scaleTransition.setToX(3);
        scaleTransition.setToY(3);
        ParallelTransition parallelTransition = new ParallelTransition(lory, rotateTransition, scaleTransition);
        parallelTransition.play();
        parallelTransition.setOnFinished(event -> {
            startTalkAnimation();
            goToMessageView();
        } );

    }

    private void goToMessageView() {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setToValue(0.0);
        translateTransition = new TranslateTransition(Duration.millis(2000));
        translateTransition.setToY(+100);
        translateTransition.setToX(+100);
        messageViewTransition = new ParallelTransition(lory, translateTransition,fadeTransition);
        messageViewTransition.setOnFinished(event -> lory.getParent().getChildrenUnmodifiable().remove(lory));
    }


    /**
     * Farà anche comparire il "fumetto" di fianco
     */
    public void startTalkAnimation() {
        messageViewAppearing();
        new Talk().start();
    }

    private void messageViewAppearing() {
    FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000));
    fadeTransition.setToValue(1.0);
    fadeTransition.setNode(lory);
    fadeTransition.play();
    }


    private void moveMouth() {
        int number = 1 + new Random().nextInt(3);
        if (number == 1)
            lory.setImage(lorenzo);
        if (number == 2)
            lory.setImage(lorenzoSemi);
        if (number == 3)
            lory.setImage(lorenzoAperta);
    }

    private void talkSound() {
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


    public void setStartGame(boolean b) {
        isStartGame = b;
    }


    private class Animation extends Thread {
        public void run() {
            for (int i = 0; i < 10; i++) {
                moveMouth();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            lory.setImage(lorenzo);

            if (isStartGame == true){
                messageViewTransition.play();
                isStartGame = false;
            }

        }
    }

    public class Talk extends Thread {
        public void run() {
            try {
                audio.play(audio.getPath() + "whistle" + EXTENSION);
                Thread.sleep(700);
                talkSound();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }





}