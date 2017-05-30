package main.gui.game_view.component;

import javafx.animation.FadeTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 * @author Andrea
 * @author Luca
 */
public class ExcomCard extends ImageView{
    private String code;

    public ExcomCard() {
        super();
        setFitWidth(70);
        setFitHeight(102);
    }

    public String getCode() {
        return code;
    }

    public void setImage(Image image, String code) {
        setOpacity(0);
        new Thread(() -> {
            setImage(image);
            startShowAnimation();
        }).start();
        this.code = code;
    }

    /**
     * animazione che mi fa comparire la carta
     */
    private void startShowAnimation() {
        FadeTransition fadeIn = new FadeTransition(Duration.millis(6000), this);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();
    }
}
