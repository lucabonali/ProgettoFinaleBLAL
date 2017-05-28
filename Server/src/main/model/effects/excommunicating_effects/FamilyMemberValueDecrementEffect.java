package main.model.effects.excommunicating_effects;

import main.game_server.exceptions.NewActionException;
import main.api.types.FamilyMemberType;
import main.model.action_spaces.Action;
import main.model.effects.development_effects.Effect;
import main.game_server.AbstractPlayer;

import java.rmi.RemoteException;

/**
 * @author Luca
 * @author Andrea
 */
public class FamilyMemberValueDecrementEffect implements Effect {
    @Override
    public void active(AbstractPlayer player) throws RemoteException, NewActionException {
        Action action = player.getPersonalBoard().getCurrentAction();
        if (action.getFamilyMember().getType() != FamilyMemberType.NEUTRAL_DICE)
            player.getPersonalBoard().getCurrentAction().modifyValue(-1);
    }
}
