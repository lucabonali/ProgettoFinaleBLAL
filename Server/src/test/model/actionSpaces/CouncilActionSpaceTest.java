package test.model.actionSpaces;

import main.api.exceptions.LorenzoException;
import main.api.types.FamilyMemberType;
import main.api.types.ResourceType;
import main.model.action_spaces.Action;
import main.model.action_spaces.largeActionSpaces.CouncilActionSpace;
import main.servergame.rmi.PlayerRMI;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

/**
 * @author Luca
 * @author Andrea
 */
public class CouncilActionSpaceTest {
    @Test
    public void doAction1() throws Exception {
        CouncilActionSpace c = new CouncilActionSpace(1);
        PlayerRMI p = new PlayerRMI("luca");
        p.createPersonalBoard(1);
        c.doAction(new Action(c, 5, p.getFamilyMember(FamilyMemberType.BLACK_DICE), p));
        assertEquals(Optional.of(6), Optional.of(p.getPersonalBoard().getQtaResources().get(ResourceType.COINS)));
    }

    @Test(expected = LorenzoException.class)
    public void doAction2() throws Exception {
        CouncilActionSpace c = new CouncilActionSpace(1);
        PlayerRMI p = new PlayerRMI("luca");
        p.createPersonalBoard(1);
        c.doAction(new Action(c, 0, p.getFamilyMember(FamilyMemberType.NEUTRAL_DICE), p));
    }
}