package main.model.action_spaces.large_action_spaces;

import main.game_server.exceptions.NewActionException;
import main.model.action_spaces.Action;
import main.model.effects.development_effects.Effect;
import main.model.effects.development_effects.FixedIncrementEffect;
import main.game_server.exceptions.LorenzoException;
import main.model.fields.Resource;
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
        bonusEffectList.add(new FixedIncrementEffect(
                new Resource(1, ResourceType.PRIVILEGE)
        ));
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
