package test.controller.board;

import main.api.exceptions.LorenzoException;
import main.api.types.CardType;
import main.api.types.ResourceType;
import main.model.board.Card;
import main.model.effects.Effect;
import main.model.effects.FixedIncrementEffect;
import main.model.fields.Field;
import main.model.fields.Resource;
import main.servergame.rmi.PlayerRMI;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

/**
 * @author lampa
 */
public class CardTest {
    @Test
    public void getType() throws Exception {
        Card c = new Card(CardType.TERRITORY, "valle", null, null, null, 1);
        assertEquals(CardType.TERRITORY, c.getType());
    }

    @Test
    public void getName() throws Exception {
        Card c = new Card(CardType.TERRITORY, "valle", null, null, null, 1);
        assertEquals("valle", c.getName());
    }

    @Test
    public void getCosts() throws Exception {
        List<Field> costs = new ArrayList<>();
        costs.add(new Resource(2, ResourceType.COINS));
        Card c = new Card(CardType.TERRITORY, "valle", costs, null, null, 1);
        assertEquals(costs, c.getCosts());
    }

    @Test
    public void getQuickEffects() throws Exception {

    }

    @Test
    public void getPermanentEffects() throws Exception {
    }

    @Test(expected = LorenzoException.class)
    public void getPlayer() throws Exception {
        PlayerRMI p1 = new PlayerRMI("andrea");
        p1.createPersonalBoard(1);
        List<Field> costs = new ArrayList<>();
        costs.add(new Resource(-5, ResourceType.COINS));
        costs.add(new Resource(-5, ResourceType.WOOD));
        Card c = new Card(CardType.TERRITORY, "valle", costs, null, null, 1);
        c.setPlayer(p1);
        assertEquals(null, c.getPlayer());
    }

    @Test(expected = LorenzoException.class)
    public void setPlayer() throws Exception {
        PlayerRMI p1 = new PlayerRMI("andrea");
        p1.createPersonalBoard(1);
        List<Field> costs = new ArrayList<>();
        costs.add(new Resource(-5, ResourceType.COINS));
        costs.add(new Resource(-5, ResourceType.WOOD));
        Card c = new Card(CardType.TERRITORY, "valle", costs, null, null, 1);
        c.setPlayer(p1);
    }

    @Test
    public void activeCosts() throws Exception {
        PlayerRMI p1 = new PlayerRMI("andrea");
        p1.createPersonalBoard(1);
        List<Field> costs = new ArrayList<>();
        costs.add(new Resource(-1, ResourceType.COINS));
        costs.add(new Resource(-1, ResourceType.WOOD));
        Card c = new Card(CardType.TERRITORY, "valle", costs, null, null, 1);
        c.setPlayer(p1);
        assertEquals(Optional.of(4), Optional.of(p1.getPersonalBoard().getQtaResources().get(ResourceType.COINS)));
        assertEquals(Optional.of(1), Optional.of(p1.getPersonalBoard().getQtaResources().get(ResourceType.WOOD)));
    }

    @Test
    public void activeQuickEffects() throws Exception {
        PlayerRMI p1 = new PlayerRMI("andrea");
        p1.createPersonalBoard(1);
        List<Effect> effectList = new ArrayList<>();
        Effect effect = new FixedIncrementEffect(new Resource(3, ResourceType.COINS));
        effectList.add(effect);
        Card c = new Card(CardType.TERRITORY, "valle", null, effectList, null, 1);
        c.setPlayer(p1);
        c.activeQuickEffects();
        assertEquals(Optional.of(8), Optional.of(p1.getPersonalBoard().getQtaResources().get(ResourceType.COINS)));
        assertEquals(Optional.of(3), Optional.of(p1.getPersonalBoard().getQtaResources().get(ResourceType.SERVANTS)));
    }

    @Test
    public void activePermanentEffects() throws Exception {
    }

}