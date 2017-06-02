package main.GUI.game_view.component;

import javafx.application.Platform;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import main.GUI.Service;

/**
 * @author Luca
 * @author Andrea
 */
public abstract class PersonalDisc extends Circle{
    private static final int RADIUS = 10;
    private int id;
    private AnchorPane container;

    public PersonalDisc(int id, AnchorPane container) {
        super(RADIUS, Service.getColorById(id));
        this.id = id;
        this.container = container;
        Platform.runLater(() -> container.getChildren().add(this));
    }

    /**
     * mi setta la posizione del mio dischetto
     * @param pos la posizione che altro non è che il numero di punti.
     */
    public abstract void setCurrentPosition(int pos);

    /**
     * mi rimuove il dischetto dal suo container
     */
    public void remove() {
        Platform.runLater(() -> container.getChildren().remove(this));
    };
}
