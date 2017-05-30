package main.GUI.game_view.component.action_spaces;

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


    public GuiFloorActionSpace(ActionSpacesType type, CardType cardType, int numFloor) {
        super(type);
        this.cardType = cardType;
        this.numFloor = numFloor;
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
}
