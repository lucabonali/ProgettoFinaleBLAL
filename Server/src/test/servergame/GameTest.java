package test.servergame;

import main.servergame.Game;
import main.servergame.rmi.PlayerRMI;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Luca
 * @author Andrea
 */
public class GameTest {
    @Test
    public void addPlayer() throws Exception {
        Game g = new Game();
        PlayerRMI playerRMI = new PlayerRMI("andrea");
        g.addPlayer(playerRMI);
        playerRMI.setGame(g);
    }

    @Test
    public void shotDice() throws Exception {

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
    public void doAction() throws Exception {

    }

}