package main.GUI.game_view.component.action_spaces;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import main.GUI.game_view.component.GuiFamilyMember;
import main.api.types.ActionSpacesType;
import main.api.types.CardType;
import main.client.AbstractClient;

/**
 * @author Luca
 * @author Andrea
 */
public class GuiFloorActionSpace extends SingleActionSpace {
    private CardType cardType;
    private int numFloor;
    private int gridPosition;


    public GuiFloorActionSpace(ActionSpacesType type, CardType cardType, int numFloor,int gridPosition, GridPane container) {
        super(type, container);
        this.cardType = cardType;
        this.numFloor = numFloor;
        this.gridPosition = gridPosition;
    }


    /**
     * mi setta l'azione corrente sul controller principale
     */
    @Override
    public void setCurrentActionSpace() {
        AbstractClient.getInstance().setActionSpacesType(getType());
        AbstractClient.getInstance().setCardType(cardType);
        AbstractClient.getInstance().setNumFloor(numFloor);
    }

    @Override
    public void addFamilyMember(GuiFamilyMember familyMember) {
        setFamilyMember(familyMember);
        getContainer().setAlignment(Pos.CENTER);
        if (!getContainer().getChildren().contains(familyMember))
            Platform.runLater(() -> getContainer().add(familyMember, 0 ,gridPosition));
    }
}
