package main.gui.game_view.component;

/**
 * @author Luca
 * @author Andrea
 */
public class PersonalVictoryDisc extends PersonalDisc{
    private static int NUMBER_OF_DISC = 0;
    private final double INC_X = 45.6 , INC_Y = 32;
    private final double INC_FINAL_X = 52;
    private final double START_X = 44;
    private final double FINAL_X;
    private final double START_Y;
    private final double INIT_Y = 20;
    private final double FINAL_Y = INIT_Y + (INC_Y*29);

    private int currentPosition = 0;

    public PersonalVictoryDisc(int id) {
        super(id);
        START_Y = 18 - (NUMBER_OF_DISC*6);
        FINAL_X = START_X + (INC_X*19) + INC_FINAL_X + (NUMBER_OF_DISC*6);
        NUMBER_OF_DISC++;
        setTranslateX(START_X);
        setTranslateY(START_Y);
    }

    /**
     * mi setta la posizione del dischetto
     * @param pos la posizione che altro non è che il numero di punti.
     */
    @Override
    public void setCurrentPosition(int pos) {
        currentPosition = pos;
        double x = 0;
        double y = 0;
        if (pos < 19) {
            x = START_X + (INC_X*pos);
            y = START_Y;
        }
        else if (pos == 20) {
            x = FINAL_X;
            y = INIT_Y;
        }
        else if (pos < 49) {
            x = FINAL_X;
            y = INIT_Y + (INC_Y*(pos-20));
        }
        setTranslateX(x);
        setTranslateY(y);
    }
}
