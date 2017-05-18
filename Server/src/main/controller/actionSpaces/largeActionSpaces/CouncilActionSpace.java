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
        bonusEffectList = new ArrayList<>();
        bonusEffectList.add(new FixedIncrementEffect(
                new Resource(1, ResourceType.COINS)
        ));
        //dovrei inserire il privilegio del consiglio
    }

    @Override
    public void doAction(Action action) throws LorenzoException, RemoteException, NewActionException {
        if (getValue() > action.getValue())
            throw new LorenzoException("non hai abbastanza forza!!");

        addFamilyMember(action.getFamilyMember());
        for (Effect effect : bonusEffectList) {
            effect.active(action.getPlayer());
        }
    }


}
