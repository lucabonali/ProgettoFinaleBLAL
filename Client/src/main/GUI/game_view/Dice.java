package main.GUI.game_view;

import javafx.animation.*;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.util.Random;


/**
 * @author Luca
 * @author Andrea
 *
 * mi rappresenta il singolo dado
 */
public class Dice extends ImageView {
    private String url = "res/dices/";
    private GridPane container;
    private double xOffset, yOffset;
    private double startX, startY;
    private DoubleProperty property = new SimpleDoubleProperty();
    private int number;
    private GUIController controller;
    private boolean isRolled = false;
    private String name;

    public Dice(String name, Image image, GridPane container, GUIController controller) {
        this(name, container, controller);
        setImage(image);
    }

    public Dice(String name, GridPane container, GUIController controller) {
        super();
        this.name = name;
        this.url = url + name + "/dado";
        this.container = container;
        this.controller = controller;
        setFitWidth(65);
        setFitHeight(60);
        rollDice();
    }

    public int getNum() {
        return number;
    }

    /**
     * tira il dado
     */
    private void rollDice() {
        int number = 1 + new Random().nextInt(6);
        this.number = number;
        setImage(new Image(getClass().getResource(url + number + ".png").toExternalForm()));
    }

    public Dice setNumber(int number) {
        this.number = number;
        return placeDice(new Image(getClass().getResource(url + number + ".png").toExternalForm()), container, controller);
    }

    /**
     * inizializza i listener sul dado per le animazioni
     */
    public void initializeDiceListeners() {
        setCursor(Cursor.CLOSED_HAND);
        setOnMousePressed(event -> {
            startX = event.getX();
            startY = event.getY();
            xOffset = startX - getX();
            yOffset = startY - getY();
            setCursor(Cursor.CLOSED_HAND);
        } );

        setOnMouseDragged(event -> {
            setX(event.getX() - xOffset);
            setY(event.getY() - yOffset);
        });
        setOnMouseReleased(this::addAnimaion);

        property.addListener((observable, oldValue, newValue) -> rollDice());
    }

    private void addAnimaion(MouseEvent event){
        property.setValue(0);
        //translate
        TranslateTransition translate = new TranslateTransition(Duration.millis(500), this);
        double finalX = event.getX() - startX;
        double finalY = event.getY() - startY;
        translate.setToX(finalX*2);
        translate.setToY(finalY*2);
        translate.play();

        //timeline
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(400),
                new KeyValue(property, 5)));

        //rotate
        RotateTransition rotate = new RotateTransition(Duration.millis(500), this);
        rotate.setByAngle(180);
        rotate.setCycleCount(1);
        rotate.setAutoReverse(true);

        //parallel
        ParallelTransition parallel = new ParallelTransition(this, translate, timeline, rotate);
        parallel.setOnFinished(event1 -> {
            try {
                Thread.sleep(1000);
                setVisible(false);
                isRolled = true;
                controller.sendDices();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        parallel.play();
    }

    public boolean isRolled() {
        return isRolled;
    }

    public void remove() {
        if (container.getChildren().contains(this))
            container.getChildren().remove(this);
    }

    /**
     * mi piazza il dado e me lo rende immodificabile
     */
    public Dice placeDice(Image image, GridPane container, GUIController controller) {
        Dice dice = new Dice(name, image, container, controller);
        container.add(dice, 0, 0);
        dice.setCursor(Cursor.DEFAULT);
        dice.setOnMousePressed(null);
        dice.setOnMouseDragged(null);
        dice.setOnMouseReleased(null);
        return dice;
    }
}
