package actionSpaces.largeActionSpaces;

import actionSpaces.Action;
import effects.Effect;
import effects.FixedIncrementEffect;
import api.LorenzoException;
import fields.Resource;
import types.ResourceType;

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
        if(getValue() > action.getValue())
            throw new LorenzoException("la tua azione non ha abbastanza forza!!");

        addFamilyMember(action.getFamilyMember());
        for (Effect effect : bonusEffectList){
            effect.active(action.getFamilyMember().getPersonalBoard());
        }
        //devo anche attivare gli effetti delle carte territorio
    }
}
