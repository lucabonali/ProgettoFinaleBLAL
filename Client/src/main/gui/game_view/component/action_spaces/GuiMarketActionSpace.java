package main.gui.game_view.component.action_spaces;

import main.api.types.ActionSpacesType;
import main.api.types.MarketActionType;
import main.client.AbstractClient;

/**
 * @author Luca
 * @author Andrea
 */
public class GuiMarketActionSpace extends SingleActionSpace {
    private MarketActionType marketActionType;

    public GuiMarketActionSpace(ActionSpacesType actionSpacesType, MarketActionType marketActionType) {
        super(actionSpacesType);
        this.marketActionType = marketActionType;
    }

    @Override
    public void setCurrentActionSpace() {
        AbstractClient.getInstance().setActionSpacesType(getType());
        AbstractClient.getInstance().setMarketActionType(marketActionType);
    }
}
