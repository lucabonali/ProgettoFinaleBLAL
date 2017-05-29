package main.gui.start_screen;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import main.GUILauncher;
import main.gui.music.Music;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StartScreenController implements Initializable {
    @FXML private Button closeButton;
    @FXML private Label labelClick;
    @FXML private ImageView testa;
    @FXML private Image testina;
    @FXML private Pane rootPane;
    private double xOffset,yOffset;

    private Music audio;



    ScaleTransition scaleTransition ;
    FadeTransition fadeOut, fadeIn;

    Image image;

    public void startTransition(MouseEvent mouseEvent) {
        scaleTransition.play();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        scaleTransition = new ScaleTransition(Duration.millis(1500),testa);
        scaleTransition.setToY(2);
        scaleTransition.setToX(2);
        scaleTransition.autoReverseProperty();
        scaleTransition.setOnFinished(e -> {
            try {
                audio.play(audio.getPath()+"Occhiolino.wav");
                changePhoto();
            } catch (InterruptedException | IOException e1) {
                e1.printStackTrace();
            } catch (UnsupportedAudioFileException e1) {
                e1.printStackTrace();
            } catch (LineUnavailableException e1) {
                e1.printStackTrace();
            }
        });
        image = new Image(getClass().getResourceAsStream("res/LorenzoIlMagnificochiuso.png"));
        fadeIn = new FadeTransition(Duration.millis(500),labelClick);
        fadeOut = new FadeTransition(Duration.millis(500),labelClick);
        fadeOut.setFromValue(0.5);
        fadeOut.setToValue(1.0);
        fadeIn.setFromValue(0.5);
        fadeOut.setToValue(1.0);
        fadeOut.play();
        fadeIn.setOnFinished( e -> fadeOut.play());
        fadeOut.setOnFinished(e -> fadeIn.play());

        rootPane.setOnMousePressed(event -> {
            xOffset = GUILauncher.getPrimaryStage().getX() -event.getScreenX();
            yOffset = GUILauncher.getPrimaryStage().getY() -event.getScreenY();
            rootPane.setCursor(Cursor.CLOSED_HAND);
        } );

        rootPane.setOnMouseDragged(event -> {
            GUILauncher.getPrimaryStage().setX(event.getScreenX() + xOffset);
            GUILauncher.getPrimaryStage().setY(event.getScreenY() + yOffset);
        });
        rootPane.setOnMouseReleased(event -> rootPane.setCursor(Cursor.DEFAULT));

        playAudio();

    }

    private void playAudio() {
        audio = new Music();
    }


    private void changePhoto() throws InterruptedException, IOException {
        testa.setImage(image);

        changeScene();
    }

    private void changeScene() throws IOException, InterruptedException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("main/gui/login/login_view.fxml"));
        Parent window = (Pane) fxmlLoader.load();
        Platform.runLater(()->{
            Stage stage = (Stage)  labelClick.getScene().getWindow();
            Scene scene = new Scene(window);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            stage.setScene(scene);
            stage.centerOnScreen();
            //stage.sizeToScene();
            stage.setTitle("LORENZO IL MAGNIFICO!!");
        });
    }

    public void changeCursor(MouseEvent mouseEvent) {
        labelClick.setCursor(Cursor.HAND);
    }

    public void closeWindow(ActionEvent actionEvent) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}
