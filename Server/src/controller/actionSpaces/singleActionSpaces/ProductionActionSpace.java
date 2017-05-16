package controller.actionSpaces.singleActionSpaces;

import controller.actionSpaces.Action;
import controller.effects.Effect;
import controller.effects.FixedIncrementEffect;
import api.LorenzoException;
import controller.fields.Resource;
import controller.types.ResourceType;

import java.util.List;

/**
 * @author lampa
 */
public class ProductionActionSpace extends ActionSpace{
    private List<Effect> bonusEffectList;

    public ProductionActionSpace(int value){
        super(value);
        initializeBonus();
    }

    /**
     * metodo di servizio che mi inizializza il bonus della zona produzione
     */
    private void initializeBonus() {
        bonusEffectList.add(new FixedIncrementEffect(
                new Resource(1, ResourceType.MILITARY)
        ));
        bonusEffectList.add(new FixedIncrementEffect(
                new Resource(1, ResourceType.COINS)
        ));
    }

    /**
     * metodo di servizio che richiamo quando creo lo spazio azione raccolta
     */
    private void initializeHarvestBonus(){
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

        //devo attivare gli effetti delle carte edificio
    }
}
