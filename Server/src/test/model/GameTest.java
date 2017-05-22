package test.model;

import main.api.exceptions.LorenzoException;
import main.api.messages.MessageGame;
import main.api.messages.MessageGameType;
import main.api.types.FamilyMemberType;
import main.model.Game;
import main.servergame.rmi.PlayerRMI;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Luca
 * @author Andrea
 */
public class GameTest {
    @Test
    public void getCurrentPlayer() throws Exception {
        Game g = new Game();
        PlayerRMI p1 = new PlayerRMI("gg");
        PlayerRMI p2 = new PlayerRMI("lol");
        p1.setGame(g);
        g.addPlayer(p1);
        p2.setGame(g);
        g.addPlayer(p2);
        Thread.sleep(6000);
        assertEquals(p1, g.getCurrentPlayer());
        assertNotEquals(p2, g.getCurrentPlayer());
    }

    @Test
    public void addPlayer() throws Exception {
        Game g = new Game();
        PlayerRMI playerRMI = new PlayerRMI("andrea");
        g.addPlayer(playerRMI);
        playerRMI.setGame(g);
        assertEquals(1, g.getId(playerRMI));
    }

    @Test
    public void shotDice() throws Exception {
        Game g = new Game();
        PlayerRMI p1 = new PlayerRMI("gg");
        PlayerRMI p2 = new PlayerRMI("lol");
        p1.setGame(g);
        g.addPlayer(p1);
        p2.setGame(g);
        g.addPlayer(p2);
        Thread.sleep(6000);
        p1.shotDice(4,5,6);
        assertEquals(4, p2.getFamilyMember(FamilyMemberType.ORANGE_DICE).getValue());
    }

    @Test
    public void getId() throws Exception {
        Game g = new Game();
        PlayerRMI p1 = new PlayerRMI("andrea");
        p1.setGame(g);
        PlayerRMI p2 = new PlayerRMI("luca");
        p2.setGame(g);
        g.addPlayer(p1);
        g.addPlayer(p2);
        Thread.sleep(7000);
        assertEquals(1, g.getId(g.getCurrentPlayer()));
    }

    @Test
    public void isFull() throws Exception {
        Game g = new Game();
        PlayerRMI p1 = new PlayerRMI("andrea");
        g.addPlayer(p1);
        p1.setGame(g);
        PlayerRMI p2 = new PlayerRMI("luca");
        g.addPlayer(p2);
        p2.setGame(g);
        Thread.sleep(6000);
        assertTrue(g.isFull());
    }

    @Test
    public void excommunicatePlayer() throws Exception {
    }

    @Test
    public void giveChurchSupport() throws Exception {
    }

    @Test(expected = LorenzoException.class)
    public void doAction() throws Exception {
        Game g = new Game();
        PlayerRMI p1 = new PlayerRMI("andrea");
        g.addPlayer(p1);
        p1.setGame(g);
        MessageGame msg = new MessageGame(MessageGameType.ACTION);
        msg.setFamilyMemberType(FamilyMemberType.ORANGE_DICE);
        p1.doAction(msg);
    }

    @Test
    public void endMove() throws Exception {
    }

    @Test
    public void removePlayer() throws Exception {
    }

}