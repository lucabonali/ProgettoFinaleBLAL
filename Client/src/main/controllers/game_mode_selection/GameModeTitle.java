package main.controllers.game_mode_selection;

import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * @author Luca
 * @author Andrea
 */
public class GameModeTitle extends Pane{
    private Text text;

    public GameModeTitle(String title){
        StringBuilder spread = new StringBuilder();
        for(char c : title.toCharArray()){
            spread.append(c).append(" ");
        }

        text = new Text(spread.toString());
        text.setFont(Font.loadFont(getClass().getResource("res/Penumbra-HalfSerif-Std_35114.ttf").toExternalForm(), 48));
        text.setFill(Color.WHITE);
        setEffect(new DropShadow(30, Color.BLACK));

        getChildren().addAll(text);
    }

    public double getTitleWidth(){
        return text.getLayoutBounds().getWidth();
    }

    public double getTitleHeight(){
        return text.getLayoutBounds().getHeight();
    }
}
