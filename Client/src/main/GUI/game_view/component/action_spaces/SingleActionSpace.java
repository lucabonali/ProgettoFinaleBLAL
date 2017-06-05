package main.GUI.game_view.component.action_spaces;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import main.GUI.game_view.component.GuiFamilyMember;
import main.api.types.ActionSpacesType;
import main.client.AbstractClient;

/**
 * @author Andrea
 * @author Luca
 */
public class SingleActionSpace extends AnchorPane implements ActionSpaceInterface{
    private static Pane pane = new Pane();
    private static final double WIDTH = 64, HEIGHT = 38;
    private GuiFamilyMember familyMember;
    private ActionSpacesType type;
    private GridPane container;


    public SingleActionSpace(ActionSpacesType type, GridPane container) {
        this.type = type;
        this.container = container;
        setCursor(Cursor.HAND);
        setOnMouseClicked(event -> setCurrentActionSpace());
        container.setAlignment(Pos.CENTER);
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
        setFamilyMember(familyMember);
        if (!container.getChildren().contains(this.familyMember)) {
            Platform.runLater(() -> container.add(this.familyMember, 0, 0));
        }
    }

    void setFamilyMember(GuiFamilyMember familyMember) {
        this.familyMember = familyMember;
    }

    GuiFamilyMember getFamilyMember() {
        return this.familyMember;
    }

    /**
     * rimuove tutti i familiari, in questo caso uno solo
     */
    @Override
    public void removeAllFamilyMembers() {
        Platform.runLater(() -> {
            getContainer().getChildren().remove(familyMember);
            familyMember = null;
        });
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
