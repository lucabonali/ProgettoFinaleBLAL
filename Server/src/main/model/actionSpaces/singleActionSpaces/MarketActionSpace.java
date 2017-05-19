package main.model.actionSpaces.singleActionSpaces;

import main.api.exceptions.NewActionException;
import main.model.actionSpaces.Action;
import main.api.exceptions.LorenzoException;
import main.api.types.MarketActionType;

import java.rmi.RemoteException;

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
    public void doAction(Action action) throws LorenzoException, RemoteException, NewActionException {
        if (getMinValue() > action.getValue())
            throw new LorenzoException("non hai abbastanza forza per effettuare l'azione");

        setFamilyMember(action.getFamilyMember());
        getEffect().active(action.getPlayer());
    }
}
