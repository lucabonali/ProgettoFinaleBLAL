package main.GUI.game_view.component.action_spaces;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import main.GUI.game_view.component.GuiFamilyMember;
import main.api.types.ActionSpacesType;
import main.client.AbstractClient;

/**
 * @author Andrea
 * @author Luca
 */
public class SingleActionSpace extends AnchorPane implements ActionSpaceInterface{
    private static final double WIDTH = 64, HEIGHT = 38;
    private GuiFamilyMember familyMember;
    private ActionSpacesType type;
    private Rectangle rectangle;
    private GridPane container;

    public SingleActionSpace(ActionSpacesType type, GridPane container) {
        this.type = type;
        this.container = container;
        setCursor(Cursor.HAND);
        setOnMouseClicked(event -> setCurrentActionSpace());
//        createPane();
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

    public GridPane getContainer() {
        return container;
    }

    /**
     * aggiunge un familiare allo spazio azione
     * @param familyMember familiare da aggiungere
     */
    @Override
    public void addFamilyMember(GuiFamilyMember familyMember) {
        this.familyMember = familyMember;
        Platform.runLater(() -> container.add(familyMember, 0 ,0));
        container.setAlignment(Pos.CENTER);
//        familyMember.setTranslateX(30);
//        familyMember.setTranslateX(-216);
//        familyMember.setTranslateY(18);
    }

    public void setFamilyMember(GuiFamilyMember familyMember) {
        this.familyMember = familyMember;
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
