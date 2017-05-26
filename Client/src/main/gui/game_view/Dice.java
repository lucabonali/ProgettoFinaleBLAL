package main.gui.game_view;

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
 */
public class Dice extends ImageView {
    private String url = "res/dices/";
    private GridPane container;
    private double xOffset, yOffset;
    private double startX, startY;
    private DoubleProperty property = new SimpleDoubleProperty();
    private boolean positioned = false;
    private int number;
    private GameController controller;
    private String name;

    public Dice(Image image, GridPane container, GameController controller) {
        super(image);
        this.container = container;
        this.controller = controller;
        setFitWidth(65);
        setFitHeight(60);
    }

    public Dice() {
        super();
        setFitWidth(65);
        setFitHeight(60);
    }

    public Dice(String name, GridPane container, GameController controller) {
        super();
        this.url = url + name + "/dado";
        this.name = name;
        this.container = container;
        this.controller = controller;
        setFitWidth(65);
        setFitHeight(60);
        rollDice();
        initializeDiceListeners();
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

    public void setNumber(int number) {
        this.number = number;
        placeDice(new Image(getClass().getResource(url + number + ".png").toExternalForm()), container, controller);
    }

    private void initializeDiceListeners() {
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

        property.addListener((observable, oldValue, newValue) -> {
            rollDice();
            System.out.println(newValue);
        });
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
                switch (name){
                    case "orange":
                        controller.setOrangeRoll();
                        break;
                    case "white":
                        controller.setWhiteRoll();
                        break;
                    case "black":
                        controller.setBlackRoll();
                        break;
                }
                controller.sendDices();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        parallel.play();
    }

    /**
     * mi piazza il dado e me lo rende immodificabile
     */
    public Dice placeDice(Image image, GridPane container, GameController controller) {
        Dice dice = new Dice(image, container, controller);
        container.add(dice, 0, 0);
        dice.setCursor(Cursor.DEFAULT);
        dice.setOnMousePressed(null);
        dice.setOnMouseDragged(null);
        dice.setOnMouseReleased(null);
        return dice;
    }
}
