package main.gui.game_view.component;

import javafx.scene.shape.Circle;
import main.gui.AnimationService;

/**
 * @author Luca
 * @author Andrea
 */
public class GraphicFamilyMember extends Circle {
    private static final int RADIUS = 15;
    private int id;

    public GraphicFamilyMember(int id) {
        super(RADIUS, AnimationService.getColorById(id));
        this.id = id;
    }
}
