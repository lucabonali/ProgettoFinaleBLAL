package main.GUI;

import javafx.animation.ScaleTransition;
import javafx.scene.Cursor;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import main.api.types.ActionSpacesType;
import main.api.types.CardType;
import main.api.types.FamilyMemberType;

/**
 * @author Andrea
 * @author Luca
 *
 * classe di servizio per alcuni tipi di animazioni, riutlizzati di frequente
 */
public class Service {
    private static final char CHAR_TERRITORY = 't';
    private static final char CHAR_CHARACTERS = 'y';
    private static final char CHAR_BUILDINGS = 'b';
    private static final char CHAR_VENTURES = 'x';
    private static final char CHAR_PRODUCTION = 'e';
    private static final char CHAR_HARVEST = 'h';
    private static final char CHAR_TOWER_ACTION = 'a';

    /**
     * mi fa lo zoom della carta quando ci vado sopra col puntatore
     */
    public static void zoomIn(ImageView img) {
        img.setCursor(Cursor.HAND);
        img.toFront();
        ScaleTransition st = new ScaleTransition(Duration.millis(500), img);
        st.setToY(2);
        st.setToX(1.5);
        st.play();
    }

    /**
     * mi rimpicciolisce la carta qunado esco da essa col puntatore
     */
    public static void zoomOut(ImageView img) {
        ScaleTransition st = new ScaleTransition(Duration.millis(500), img);
        st.setToY(1);
        st.setToX(1);
        st.setOnFinished(event -> img.toBack());
        st.play();
    }

    /**
     * mi ritorna il colore del mio familiare in funzione del mio id
     * @param id id del giocatore
     * @return colore
     */
    public static Color getColorById(int id) {
        switch (id) {
            case 1:
                return Color.RED;
            case 2:
                return Color.GREEN;
            case 3:
                return Color.BLUE;
            case 4:
                return Color.YELLOW;
        }
        return null;
    }

    /**
     * mi ritorna il colore del mio familiare sotto forma di stringa
     * @param id id del giocatore
     * @return stringa del colore
     */
    public static String getStringColorById(int id) {
        switch (id) {
            case 1:
                return "red";
            case 2:
                return "green";
            case 3:
                return "blue";
            case 4:
                return "yellow";
        }
        return null;
    }

    public static Color getColorByFamilyMemberType(FamilyMemberType type) {
        switch (type) {
            case ORANGE_DICE:
                return Color.ORANGE;
            case BLACK_DICE:
                return Color.BLACK;
            case WHITE_DICE:
                return Color.WHITE;
            case NEUTRAL_DICE:
                return Color.GRAY;
        }
        return null;
    }

    public static ActionSpacesType getActionSpaceType(char codeAction) {
        switch (codeAction) {
            case CHAR_HARVEST:
                return ActionSpacesType.SINGLE_HARVEST;
            case CHAR_PRODUCTION:
                return ActionSpacesType.SINGLE_PRODUCTION;
            default: //azione su qualsiasi torre
                return ActionSpacesType.TOWERS;
        }
    }

    public static CardType getCardType(char codeAction) {
        switch (codeAction) {
            case CHAR_BUILDINGS:
                return CardType.BUILDING;
            case CHAR_CHARACTERS:
                return CardType.CHARACTER;
            case CHAR_TERRITORY:
                return CardType.TERRITORY;
            case CHAR_VENTURES:
                return CardType.VENTURES;
            default:
                return null;
        }
    }
}
