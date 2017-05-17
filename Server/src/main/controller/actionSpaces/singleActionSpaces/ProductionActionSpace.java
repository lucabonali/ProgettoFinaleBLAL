package main.controller.actionSpaces.singleActionSpaces;

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
    public void doAction(Action action) throws LorenzoException, RemoteException, NewActionException {
        if (getActionValue() > action.getValue())
            throw new LorenzoException("non hai abbastanza forza per eseguire l'azione");

        setFamilyMember(action.getFamilyMember());
        for(Effect effect : bonusEffectList)
            effect.active(action.getPlayer());


        //attivo gli effetti delle carte territorio
        action.getPlayer().getPersonalBoard().activeBuildingsEffects(action);
    }
}
