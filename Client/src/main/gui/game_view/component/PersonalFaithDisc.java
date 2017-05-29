package main.gui.game_view.component;

/**
 * @author Luca
 * @author Andrea
 */
public class PersonalFaithDisc extends PersonalDisc{
    private static int NUMBER_OF_DISC = 0;
    private final double FINAL_Y;
    private final double START_X = 83;
    private final double INC_X = 44;
    private final double INC_PLUS_X = 65;
    private int currentPosition;

    public PersonalFaithDisc(int id) {
        super(id);
        FINAL_Y = 734 + (NUMBER_OF_DISC * 4);
        setTranslateY(FINAL_Y);
        setTranslateX(START_X);
        NUMBER_OF_DISC++;
    }

    /**
     * mi setta la posizione del mio dischetto
     * @param pos la posizione che altro non è che il numero di punti.
     */
    @Override
    public void setCurrentPosition(int pos) {
        currentPosition = pos;
        double x;
        if (pos == 3 || pos == 4 || pos == 5 || pos == 6) {
            x = START_X + (INC_X * 2) + (INC_PLUS_X * (pos-3));
        }
        else if (pos < 3){
            x = START_X + INC_X*pos;
        }
        else {
            x = START_X + (INC_X * (pos - 4)) + (INC_PLUS_X * 4);
        }
        setTranslateX(x);
    }
}
