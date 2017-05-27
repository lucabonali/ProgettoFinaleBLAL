package main.model.action_spaces.large_action_spaces;

import main.servergame.exceptions.NewActionException;
import main.model.action_spaces.Action;
import main.model.effects.development_effects.Effect;
import main.model.effects.development_effects.FixedIncrementEffect;
import main.servergame.exceptions.LorenzoException;
import main.model.fields.Resource;
import main.api.types.ResourceType;

import java.rmi.RemoteException;
import java.util.ArrayList;
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
        bonusEffectList = new ArrayList<>();
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
