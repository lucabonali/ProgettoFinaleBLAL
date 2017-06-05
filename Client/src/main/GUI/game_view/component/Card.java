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
        setOpacity(0);
        new Thread(this::startShowAnimation).start();
        this.name = name;
    }

    public void remove(String nameToRemove) {
        if (name.equals(nameToRemove)){
            new Thread(this::startHideAnimation).start();
        }
    }

    /**
     * animazione che mi fa comparire la carta
     */
    private void startShowAnimation() {
        FadeTransition fadeIn = new FadeTransition(Duration.millis(3000), this);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        setImage(image);
        fadeIn.play();
    }

    /**
     * animazione che mi fa dissolvere la carta
     */
    private void startHideAnimation() {
        FadeTransition fadeOut = new FadeTransition(Duration.millis(3000), this);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setOnFinished(event -> setImage(null));
        fadeOut.play();
    }
}
