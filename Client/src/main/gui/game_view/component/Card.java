package main.gui.game_view.component;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @author Luca
 * @author Andrea
 */
public class Card extends ImageView{
    private String name;

    public Card() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setImage(Image image, String name) {
        setImage(image);
        this.name = name;
    }

    public void remove(String nameToRemove) {
        if (name.equals(nameToRemove)){
            setImage(null);
        }
    }
}
