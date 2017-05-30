package main.GUI.game_mode_selection;

import javafx.beans.binding.Bindings;
import javafx.scene.Cursor;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * @author Luca
 * @author Andrea
 */
public class GameModeItem extends Pane {
    private Text text;

    private Effect shadow = new DropShadow(5, Color.BLACK);
    private Effect blur = new BoxBlur(1,1,3);

    public GameModeItem(String name){
        Polygon bg = new Polygon(
                0, 0,
                200, 0,
                215, 15,
                200, 30,
                0, 30
        );
        bg.setStroke(Color.color(1,1,1, 0.75));
        bg.setEffect(new GaussianBlur());

        bg.fillProperty().bind(
                Bindings.when(pressedProperty())
                        .then(Color.color(1,1,1,0.75))
                        .otherwise(Color.color(0,0,0, 0.25)));
        bg.setCursor(Cursor.HAND);

        text = new Text(name);
        text.setTranslateX(5);
        text.setTranslateY(20);
        text.setFont(Font.loadFont(getClass().getResource("res/Penumbra-HalfSerif-Std_35114.ttf").toExternalForm(), 12));
        text.setFill(Color.WHITE);

        text.effectProperty().bind(
                Bindings.when(hoverProperty())
                        .then(shadow)
                        .otherwise(blur)
        );
        text.setCursor(Cursor.HAND);

        getChildren().addAll(bg, text);
    }

    public void setOnAction(Runnable action){
        setOnMouseClicked(event -> action.run());
    }
}
