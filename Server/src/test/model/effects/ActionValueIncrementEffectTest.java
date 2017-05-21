package test.model.effects;

import main.api.types.FamilyMemberType;
import main.model.action_spaces.Action;
import main.model.action_spaces.singleActionSpaces.HarvestActionSpace;
import main.model.action_spaces.singleActionSpaces.ProductionActionSpace;
import main.model.effects.development_effects.ActionValueModifyingEffect;
import main.servergame.rmi.PlayerRMI;
import org.junit.Test;

import java.rmi.RemoteException;

import static org.junit.Assert.assertEquals;

/**
 * @author Luca
 * @author Andrea
 *
 */
public class ActionValueIncrementEffectTest {
    @Test
    public void active1() throws Exception {
        PlayerRMI p = new PlayerRMI("andrea");
        p.createPersonalBoard(1);
        HarvestActionSpace has = new HarvestActionSpace(1);
        p.getPersonalBoard().setCurrentAction(new Action(has, 3, p.getFamilyMember(FamilyMemberType.ORANGE_DICE), p));
        ActionValueModifyingEffect avie = new ActionValueModifyingEffect(new ProductionActionSpace(1), 2);
        avie.active(p);
        assertEquals(3, p.getPersonalBoard().getCurrentAction().getValue());
    }

    @Test
    public void active2() throws RemoteException {
        PlayerRMI p = new PlayerRMI("andrea");
        p.createPersonalBoard(1);
        HarvestActionSpace has = new HarvestActionSpace(1);
        p.getPersonalBoard().setCurrentAction(new Action(has, 3, p.getFamilyMember(FamilyMemberType.ORANGE_DICE), p));
        ActionValueModifyingEffect avie = new ActionValueModifyingEffect(new HarvestActionSpace(1), 2);
        avie.active(p);
        assertEquals(5, p.getPersonalBoard().getCurrentAction().getValue());
    }

}