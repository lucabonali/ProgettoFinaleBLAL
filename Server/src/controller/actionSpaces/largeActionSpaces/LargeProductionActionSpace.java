package controller.actionSpaces.largeActionSpaces;

import controller.actionSpaces.Action;
import controller.effects.Effect;
import controller.effects.FixedIncrementEffect;
import api.LorenzoException;
import controller.fields.Resource;
import controller.types.ResourceType;

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
    public void doAction(Action action) throws LorenzoException {
        if(getValue() > action.getValue())
            throw new LorenzoException("la tua azione non ha abbastanza forza!!");

        addFamilyMember(action.getFamilyMember());
        for (Effect effect : bonusEffectList){
            effect.active(action.getFamilyMember().getPersonalBoard());
        }
        //devo anche attivare gli effetti delle carte edificio
    }

}
