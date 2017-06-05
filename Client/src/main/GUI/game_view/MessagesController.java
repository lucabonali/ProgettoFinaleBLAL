package main.GUI.game_view;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import main.GUI.music.Music;

/**
 * @author Andrea
 * @author Luca
 */
public class MessagesController {

    @FXML private ImageView lory;
    @FXML private Label messagesLabel;
    @FXML private Label lorenzoTalks;
    @FXML private TextFlow textFlow;
    @FXML private BorderPane rootPane;

    private Text text;
    private Music audio;


    private StringProperty message;
    private String EXTENSION = ".wav";

    public void setMessage(String msg){
        //Platform.runLater(()->message.setValue(msg));
        Platform.runLater(() -> {
            message.setValue(msg);
            playAnimation(msg);
        });
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
        textFlow.setOnMouseEntered(event -> {
            textFlow.setStyle("-fx-border-color: yellow;");
        });
        textFlow.setOnMouseExited(event -> {
            textFlow.setStyle("-fx-border-color: black;");
        });
        text = new Text();
        text.textProperty().bind(message);
        text.setTextAlignment(TextAlignment.CENTER);
        text.setTextOrigin(VPos.CENTER);
        text.setFill(Color.BLACK);
        text.setFont(Font.font("sans-serif", FontWeight.BOLD, 30));
        text.setWrappingWidth(400);
        textFlow.setTextAlignment(TextAlignment.CENTER);
        textFlow.getChildren().add(text);
        //lorenzoTalks.setText("Hi i'm Lorenzo!!");
    }
}
