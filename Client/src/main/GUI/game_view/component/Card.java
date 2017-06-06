package main.GUI.game_view.component;

import javafx.animation.FadeTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 * @author Luca
 * @author Andrea
 */
public class Card extends ImageView{
    private String name;
    private Image image;

    public Card() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setImage(Image image, String name) {
        this.image = image;
        this.name = name;
        setOpacity(0.3);
        startShowAnimation();
    }

    public void remove(String nameToRemove) {
        if (name.equals(nameToRemove)){
            setImage(null);
            this.image = null;
        }
    }

    /**
     * animazione che mi fa comparire la carta
     */
    private void startShowAnimation() {
        FadeTransition fadeIn = new FadeTransition(Duration.millis(2500), this);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        setImage(image);
        fadeIn.play();
    }

}
