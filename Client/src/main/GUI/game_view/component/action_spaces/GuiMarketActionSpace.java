package main.GUI.game_view.component.action_spaces;

import javafx.scene.layout.GridPane;
import main.GUI.game_view.GUIController;
import main.api.types.ActionSpacesType;
import main.api.types.MarketActionType;
import main.client.AbstractClient;

/**
 * @author Luca
 * @author Andrea
 */
public class GuiMarketActionSpace extends SingleActionSpace {
    private MarketActionType marketActionType;

    public GuiMarketActionSpace(ActionSpacesType actionSpacesType, MarketActionType marketActionType, GridPane container, GUIController guiController) {
        super(actionSpacesType, container, guiController);
        this.marketActionType = marketActionType;
    }

    @Override
    public void setCurrentActionSpace() {
        AbstractClient.getInstance().setActionSpacesType(getType());
        AbstractClient.getInstance().setMarketActionType(marketActionType);
        AbstractClient.getInstance().encodingAndSendingMessage(getGuiController().getServantsToPay());
    }
}
