package main.gui.game_view.component.action_spaces;

import javafx.beans.binding.Bindings;
import javafx.scene.Cursor;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import main.api.types.ActionSpacesType;
import main.client.AbstractClient;
import main.gui.game_view.component.GuiFamilyMember;

/**
 * @author Andrea
 * @author Luca
 */
public class SingleActionSpace extends Pane implements ActionSpaceInterface{
    private static final double WIDTH = 64, HEIGHT = 38;
    private GuiFamilyMember familyMember;
    private ActionSpacesType type;
    private Rectangle rectangle;

    public SingleActionSpace(ActionSpacesType type) {
        this.type = type;
        setMaxSize(WIDTH, HEIGHT);
        createPane();
    }

    private void createPane() {
        rectangle = new Rectangle(WIDTH, HEIGHT);
        rectangle.setArcHeight(HEIGHT/2);
        rectangle.setArcWidth(WIDTH/2);
        rectangle.fillProperty().bind(
                Bindings.when(hoverProperty())
                        .then(Color.color(1,1,1, 0.75))
                        .otherwise(Color.color(1,1,1,0.25))
        );
        rectangle.setCursor(Cursor.HAND);
        rectangle.setOnMouseClicked(event -> setCurrentActionSpace());
        getChildren().add(rectangle);
    }

    /**
     * aggiunge un familiare allo spazio azione
     * @param familyMember familiare da aggiungere
     */
    @Override
    public void addFamilyMember(GuiFamilyMember familyMember) {
        this.familyMember = familyMember;
        familyMember.setTranslateX(30);
        familyMember.setTranslateY(18);
        getChildren().add(familyMember);
    }

    /**
     * rimuove tutti i familiari, in questo caso uno solo
     */
    @Override
    public void removeAllFamilyMembers() {
        getChildren().remove(familyMember);
        familyMember = null;
    }

    /**
     * permette di ottenere il tipo dello spazio azione
     * @return ActionSpacesType
     */
    @Override
    public ActionSpacesType getType() {
        return type;
    }

    /**
     * mi setta lo spazio azione corrente
     */
    @Override
    public void setCurrentActionSpace() {
        AbstractClient.getInstance().setActionSpacesType(type);
    }
}
