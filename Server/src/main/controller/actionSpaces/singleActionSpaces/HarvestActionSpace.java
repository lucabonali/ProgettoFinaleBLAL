package main.controller.actionSpaces.singleActionSpaces;

import main.controller.actionSpaces.Action;
import main.controller.effects.Effect;
import main.controller.effects.FixedIncrementEffect;
import main.api.LorenzoException;
import main.controller.fields.Resource;
import main.controller.types.ResourceType;

import java.util.List;

/**
 * @author Luca
 * @author Andrea
 *
 * classe che mi identifica una zona del mio tabellone
 * essa può essere di raccolta o di produzione (si usa l'enumerazione)
 */
public class HarvestActionSpace extends ActionSpace {
    private List<Effect> bonusEffectList;

    public HarvestActionSpace(int value){
        super(value);
        initializeBonus();
    }

    /**
     * metodo di servizio che mi inizializza il bonus della zona produzione
     */
    private void initializeBonus() {
        bonusEffectList.add(new FixedIncrementEffect(
                new Resource(1, ResourceType.WOOD)
        ));
        bonusEffectList.add(new FixedIncrementEffect(
                new Resource(1, ResourceType.STONE)
        ));
        bonusEffectList.add(new FixedIncrementEffect(
                new Resource(1, ResourceType.SERVANTS)
        ));
    }

    @Override
    public void doAction(Action action) throws LorenzoException {
        if (getActionValue() > action.getValue())
            throw new LorenzoException("non hai abbastanza forza per eseguire l'azione");

        setFamilyMember(action.getFamilyMember());
        for(Effect effect : bonusEffectList)
            effect.active(action.getFamilyMember().getPersonalBoard());

        //devo attivare gli effetti delle carte territorio
    }

}
