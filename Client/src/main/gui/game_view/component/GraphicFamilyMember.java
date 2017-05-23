package main.gui.game_view.component;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * @author Luca
 * @author Andrea
 */
public class GraphicFamilyMember extends Circle {
    public static final int RADIUS = 10;
    private Color color;

    public GraphicFamilyMember(Color color) {
        this.color = color;
        createFamilyMember();
    }

    private void createFamilyMember() {
        setRadius(10);
        setFill(color);
    }
}
