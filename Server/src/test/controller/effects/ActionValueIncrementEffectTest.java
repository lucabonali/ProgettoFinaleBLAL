package test.controller.effects;

import main.api.types.FamilyMemberType;
import main.controller.actionSpaces.Action;
import main.controller.actionSpaces.singleActionSpaces.HarvestActionSpace;
import main.controller.effects.ActionValueIncrementEffect;
import main.servergame.rmi.PlayerRMI;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Luca
 * @author Andrea
 *
 */
public class ActionValueIncrementEffectTest {
    @Test
    public void active() throws Exception {
        PlayerRMI p = new PlayerRMI("andrea");
        p.createPersonalBoard(1);
        HarvestActionSpace has = new HarvestActionSpace(1);
        p.getPersonalBoard().setCurrentAction(new Action(has, 3, p.getFamilyMember(FamilyMemberType.ORANGE_DICE), p));
        ActionValueIncrementEffect avie = new ActionValueIncrementEffect(new HarvestActionSpace(1), 2);
        avie.active(p);
        assertEquals(5, p.getPersonalBoard().getCurrentAction().getValue());
    }

}