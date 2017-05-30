package main.GUI.game_view.component;

import javafx.application.Platform;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * @author Andrea
 * @author Luca
 */
public class ExcommunicatingCube extends Rectangle{
    private static final int WIDTH = 10, HEIGHT = 10;
    private final int START=30;
    private final int INC=10;
    private static int COUNTER_1=0, COUNTER_2=0, COUNTER_3=0;
    private int period;
    private AnchorPane pane;

    public ExcommunicatingCube(Color color, GridPane container, int period) {
        super(WIDTH, HEIGHT, color);
        this.period = period;
        pane = (AnchorPane) container.getChildren().get(period-1);
        setPosition();
    }

    private void setPosition() {
        setTranslateX(30);
        int y = START;
        switch (period) {
            case 1:
                y += (COUNTER_1*INC);
                COUNTER_1++;
                break;
            case 2:
                y += (COUNTER_2*INC);
                COUNTER_2++;
                break;
            case 3:
                y += (COUNTER_3*INC);
                COUNTER_3++;
                break;
        }
        setTranslateY(y);
        Platform.runLater(() -> pane.getChildren().add(this));
    }
}
