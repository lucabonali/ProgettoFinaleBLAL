package main.controller.actionSpaces.largeActionSpaces;

import main.api.exceptions.NewActionException;
import main.controller.actionSpaces.Action;
import main.controller.effects.Effect;
import main.controller.effects.FixedIncrementEffect;
import main.api.exceptions.LorenzoException;
import main.controller.fields.Resource;
import main.api.types.ResourceType;

import java.rmi.RemoteException;
import java.util.List;

/**
 * @author Luca
 * @author Andrea
 *
 * classe che mi identifica le zone multiple del mio tabellone
 * che possono essere di produzione e di raccolta
 */
public class LargeProductionActionSpace extends LargeActionSpace {
    private List<Effect> bonusEffectList;

    public LargeProductionActionSpace(int value){
        super(value+3);
        initializeBonus();
    }

    /**
     * metodo che mi inizializza il bonus della zona produzione
     */
    private void initializeBonus() {
        bonusEffectList.add(new FixedIncrementEffect(
                new Resource(1, ResourceType.MILITARY)
        ));
        bonusEffectList.add(new FixedIncrementEffect(
                new Resource(1, ResourceType.COINS)
        ));
    }

    @Override
    public void doAction(Action action) throws LorenzoException, RemoteException, NewActionException {
        if(getValue() > action.getValue())
            throw new LorenzoException("la tua azione non ha abbastanza forza!!");

        addFamilyMember(action.getFamilyMember());
        for (Effect effect : bonusEffectList){
            effect.active(action.getPlayer());
        }

        //attivo gli effetti delle carte territorio
        action.getPlayer().getPersonalBoard().activeBuildingsEffects(action);
    }

}
