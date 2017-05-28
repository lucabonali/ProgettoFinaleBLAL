package main.model.action_spaces.single_action_spaces;

import main.api.types.MarketActionType;
import main.api.types.ResourceType;
import main.game_server.exceptions.LorenzoException;
import main.game_server.exceptions.NewActionException;
import main.model.action_spaces.Action;
import main.model.effects.development_effects.Effect;
import main.model.effects.development_effects.FixedIncrementEffect;
import main.model.fields.Resource;

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
    private Effect additionalEffect;

    public MarketActionSpace(MarketActionType marketActionType){
        super(1);
        this.type = marketActionType;
        //aggiungo gli effetti rapidi in baso al tipo di mercato
        Resource resource = null;
        Resource additionalResource = null;
        switch (marketActionType) {
            case YELLOW:
                resource = new Resource(5, ResourceType.COINS);
                break;
            case PURPLE:
                resource = new Resource(5, ResourceType.SERVANTS);
                break;
            case BLUE:
                resource = new Resource(3, ResourceType.MILITARY);
                additionalResource = new Resource(2, ResourceType.COINS);
                break;
            case GRAY:
                resource = new Resource(1, ResourceType.PRIVILEGE);
                additionalResource = new Resource(1, ResourceType.PRIVILEGE);
                break;
        }
        setEffect(new FixedIncrementEffect(resource));
        if (additionalResource != null)
            additionalEffect = new FixedIncrementEffect(additionalResource);
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
        if (additionalEffect != null)
            additionalEffect.active(action.getPlayer());
    }
}
