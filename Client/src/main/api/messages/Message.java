package main.api.messages;

import main.api.types.ActionSpacesType;
import main.api.types.CardType;
import main.api.types.MarketActionType;

/**
 * @author Luca
 * @author Andrea
 */
public interface Message {

    ActionSpacesType getActionSpacesType();

    CardType getCardType();

    int getNumFloor();

    MarketActionType getMarketActionType();
}
