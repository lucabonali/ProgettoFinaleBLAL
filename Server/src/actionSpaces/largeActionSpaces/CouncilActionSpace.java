package actionSpaces.largeActionSpaces;

import actionSpaces.Action;
import effects.Effect;
import effects.FixedIncrementEffect;
import api.LorenzoException;
import fields.Resource;
import types.ResourceType;

import java.util.List;

/**
 * @author Luca
 * @author Andrea
 *
 * classe che mi identifica il palazzo del consiglio
 */
public class CouncilActionSpace extends LargeActionSpace {
    private List<Effect> bonusEffectList;

    public CouncilActionSpace(int value){
        super(value);
        initializeBonus();
    }

    private void initializeBonus() {
        bonusEffectList.add(new FixedIncrementEffect(
                new Resource(1, ResourceType.COINS)
        ));
        //dovrei inserire il privilegio del consiglio
    }

    @Override
    public void doAction(Action action) throws LorenzoException {
        if (getValue() > action.getValue())
            throw new LorenzoException("non hai abbastanza forza!!");

        addFamilyMember(action.getFamilyMember());
        for (Effect effect : bonusEffectList) {
            effect.active(action.getFamilyMember().getPersonalBoard());
        }
    }


}
