package main.gui.game_view.component;

import javafx.scene.shape.Circle;
import main.gui.AnimationService;

/**
 * @author Luca
 * @author Andrea
 */
public abstract class PersonalDisc extends Circle{
    private static final int RADIUS = 10;
    private int id;

    public PersonalDisc(int id) {
        super(RADIUS, AnimationService.getColorById(id));
        this.id = id;
    }

    /**
     * mi setta la posizione del mio dischetto
     * @param pos la posizione che altro non è che il numero di punti.
     */
    public abstract void setCurrentPosition(int pos);
}
