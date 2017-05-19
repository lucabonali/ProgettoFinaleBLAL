package test.controller.effects;

import main.api.types.CardType;
import main.api.types.ResourceType;
import main.model.board.Card;
import main.model.effects.VariableIncrementEffect;
import main.model.fields.Resource;
import main.servergame.socket.PlayerSocket;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

/**
 * @author lampa
 */
public class VariableIncrementEffectTest {
    @Test
    public void active() throws Exception {
        VariableIncrementEffect ef1 = new VariableIncrementEffect(new Resource(5, ResourceType.WOOD), CardType.TERRITORY);
        VariableIncrementEffect ef2 = new VariableIncrementEffect(new Resource(-2, ResourceType.SERVANTS), CardType.BUILDING);
        Card card = new Card(CardType.TERRITORY, "valle", null, null, null, 1);
        PlayerSocket p = new PlayerSocket("luca");
        p.createPersonalBoard(1);
        card.setPlayer(p);
        ef1.active(p);
        ef2.active(p);
        assertEquals(Optional.of(7), Optional.of(p.getPersonalBoard().getQtaResources().get(ResourceType.WOOD)));
        assertEquals(Optional.of(3), Optional.of(p.getPersonalBoard().getQtaResources().get(ResourceType.SERVANTS)));
    }

    @Test
    public void createInstance() throws Exception {
    }

}