package main.GUI.game_view.component.action_spaces;

import javafx.application.Platform;
import javafx.scene.layout.GridPane;
import main.GUI.game_view.GUIController;
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
    private AbstractClient client;

    public GuiFloorActionSpace(ActionSpacesType type, CardType cardType, int numFloor, int gridPosition, GridPane container , GUIController guiController) {
        super(type, container, guiController);
        this.cardType = cardType;
        this.numFloor = numFloor;
        this.gridPosition = gridPosition;
        this.client = AbstractClient.getInstance();
    }


    /**
     * mi setta l'azione corrente sul controller principale
     */
    @Override
    public void setCurrentActionSpace() {
        client.setActionSpacesType(getType());
        client.setCardType(cardType);
        client.setNumFloor(numFloor);
        client.encodingAndSendingMessage(getGuiController().getServantsToPay());
    }

    @Override
    public void addFamilyMember(GuiFamilyMember familyMember) {
        setFamilyMember(familyMember);
        if (!getContainer().getChildren().contains(getFamilyMember())) {
            Platform.runLater(() -> getContainer().add(getFamilyMember(), 0, gridPosition));
        }
    }

    @Override
    public void removeAllFamilyMembers() {
        Platform.runLater(() -> {
            getContainer().getChildren().remove(getFamilyMember());
            setFamilyMember(null);
        });
    }
}
