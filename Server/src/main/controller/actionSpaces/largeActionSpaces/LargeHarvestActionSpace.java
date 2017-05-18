package main.controller.actionSpaces.largeActionSpaces;

import main.api.exceptions.NewActionException;
import main.controller.actionSpaces.Action;
import main.controller.effects.Effect;
import main.controller.effects.FixedIncrementEffect;
import main.api.exceptions.LorenzoException;
import main.controller.fields.Resource;
import main.api.types.ResourceType;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lampa
 */
public class LargeHarvestActionSpace extends LargeActionSpace{
    private List<Effect> bonusEffectList;

    public LargeHarvestActionSpace(int value) {
        super(value+3);
        initializeBonus();
    }

    private void initializeBonus() {
        bonusEffectList = new ArrayList<>();
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
        if(getValue() > action.getValue())
            throw new LorenzoException("la tua azione non ha abbastanza forza!!");

        addFamilyMember(action.getFamilyMember());
        for (Effect effect : bonusEffectList){
            effect.active(action.getPlayer());
        }

        //attivo gli effetti delle carte territorio
        action.getPlayer().getPersonalBoard().activeTerritoriesEffects(action);
    }
}
