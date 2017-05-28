package test.model;

import main.api.types.FamilyMemberType;
import main.model.Game;
import main.game_server.rmi.PlayerRMI;
import org.junit.Test;

import java.rmi.RemoteException;

import static org.junit.Assert.*;

/**
 * @author Luca
 * @author Andrea
 */
public class GameTest {
    @Test
    public void getCurrentPlayer() throws Exception {
        Game g = new Game(1);
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
        Game g = new Game(1);
        PlayerRMI playerRMI = new PlayerRMI("andrea");
        g.addPlayer(playerRMI);
        playerRMI.setGame(g);
        assertEquals(1, g.getId(playerRMI));
    }

    @Test
    public void shotDice() throws RemoteException, InterruptedException {
        Game g = new Game(1);
        PlayerRMI p1 = new PlayerRMI("gg");
        PlayerRMI p2 = new PlayerRMI("lol");
        p1.setGame(g);
        g.addPlayer(p1);
        p2.setGame(g);
        g.addPlayer(p2);
        Thread.sleep(6000);
        g.shotDice(p1,4,5,6);
        assertEquals(4, p2.getFamilyMember(FamilyMemberType.ORANGE_DICE).getValue());
    }

    @Test
    public void getId() throws Exception {
        Game g = new Game(1);
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
    public void isFull() throws RemoteException, InterruptedException {
        Game g = new Game(1);
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
    public void excommunicatePlayer() {

    }

    @Test
    public void giveChurchSupport() {

    }

    @Test
    public void doAction() throws Exception {

    }

    @Test
    public void endMove() {
    }

    @Test
    public void removePlayer() {
    }

}