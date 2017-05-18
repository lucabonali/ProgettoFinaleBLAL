package test.controller.board;

import main.api.exceptions.LorenzoException;
import main.api.types.CardType;
import main.api.types.ResourceType;
import main.controller.board.Card;
import main.controller.fields.Field;
import main.controller.fields.Resource;
import main.servergame.Game;
import main.servergame.rmi.PlayerRMI;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Luca
 * @author Andrea
 */
class CardTest {
    @Test
    void getType() {
        Card c = new Card(CardType.TERRITORY, "valle", null, null, null, 1);
        assertEquals(CardType.TERRITORY, c.getType());
    }

    @Test
    void getName() {
    }

    @Test
    void getCosts() {
        List<Field> costs = new ArrayList<>();
        costs.add(new Resource(2, ResourceType.COINS));
        Card c = new Card(CardType.TERRITORY, "valle", costs, null, null, 1);
        assertEquals(costs, c.getCosts());
    }

    @Test
    void getQuickEffects() {
    }

    @Test
    void getPermanentEffects() {
    }

    @Test
    void getPlayer() {
    }

    @Test
    void setPlayer() {
    }

    @Test
    void getPeriod() throws RemoteException {Game g = new Game();
        PlayerRMI p1 = new PlayerRMI("andrea");
        p1.setGame(g);
        PlayerRMI p2 = new PlayerRMI("luca");
        p2.setGame(g);
        g.addPlayer(p1);
        g.addPlayer(p2);
    }

    @Test
    void activeCosts() throws RemoteException, LorenzoException {
        PlayerRMI p1 = new PlayerRMI("andrea");
        p1.createPersonalBoard(1);
        List<Field> costs = new ArrayList<>();
        costs.add(new Resource(2, ResourceType.WOOD));
        Card c = new Card(CardType.TERRITORY, "valle", costs, null, null, 1);
        c.setPlayer(p1);
        c.activeCosts();
        //assertTrue(4 == p1.getPersonalBoard().getQtaResources().get(ResourceType.WOOD));
        assertEquals(java.util.Optional.of(4), p1.getPersonalBoard().getQtaResources().get(ResourceType.WOOD));
    }

    @Test
    void activeQuickEffects() {
    }

    @Test
    void activePermanentEffects() {
    }

}