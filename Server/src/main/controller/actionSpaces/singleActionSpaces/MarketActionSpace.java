package main.controller.actionSpaces.singleActionSpaces;

import main.controller.actionSpaces.Action;
import main.api.LorenzoException;
import main.controller.types.MarketActionType;

/**
 * @author Luca
 * @author Andrea
 *
 * classe che mi identifica il mercato, il quale potrà essere
 * di 4 tipi identificati da una enumerazione
 */
public class MarketActionSpace extends ActionSpace {
    private MarketActionType type;

    public MarketActionSpace(MarketActionType marketActionType){
        super(1);
        this.type = marketActionType;
    }

    public MarketActionType getType() {
        return type;
    }

    @Override
    public void doAction(Action action) throws LorenzoException {
        if (getActionValue() > action.getValue())
            throw new LorenzoException("non hai abbastanza forza per effettuare l'azione");

        setFamilyMember(action.getFamilyMember());
        getEffect().active(action.getFamilyMember().getPersonalBoard());
    }
}
