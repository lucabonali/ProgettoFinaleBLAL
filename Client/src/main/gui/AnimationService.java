package main.gui;

import javafx.animation.ScaleTransition;
import javafx.scene.Cursor;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 * @author Andrea
 * @author Luca
 *
 * classe di servizio per alcuni tipi di animazioni, riutlizzati di frequente
 */
public class AnimationService {

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
        st.play();
    }
}
