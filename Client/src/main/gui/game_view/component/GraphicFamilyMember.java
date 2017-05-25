package main.gui.game_view.component;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * @author Luca
 * @author Andrea
 */
public class GraphicFamilyMember extends Circle {
    private static final int RADIUS = 15;

    public GraphicFamilyMember(Color color) {
        super(RADIUS, color);
    }
}
