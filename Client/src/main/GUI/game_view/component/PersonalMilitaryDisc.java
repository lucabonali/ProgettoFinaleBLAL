package main.GUI.game_view.component;

import javafx.scene.layout.AnchorPane;

/**
 * @author Luca
 * @author Andrea
 */
public class PersonalMilitaryDisc extends PersonalDisc{
    private static int NUMBER_OF_DISC = 0;
    private final double START_Y = 908;
    private final double FINAL_X;
    private final double INC_Y = 32;
    private int currentPosition;

    public PersonalMilitaryDisc(int id, AnchorPane container) {
        super(id, container);
        FINAL_X = 908 + (NUMBER_OF_DISC*4);
        setTranslateX(FINAL_X);
        setTranslateY(START_Y);
        NUMBER_OF_DISC++;
    }

    /**
     * mi setta la posizione del mio dischetto
     * @param pos la posizione che altro non è che il numero di punti.
     */
    @Override
    public void setCurrentPosition(int pos) {
        setTranslateY(START_Y -(INC_Y * pos));
    }
}
