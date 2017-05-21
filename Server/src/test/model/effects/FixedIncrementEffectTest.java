package test.model.effects;

import main.api.types.ResourceType;
import main.model.effects.development_effects.FixedIncrementEffect;
import main.model.fields.Resource;
import main.servergame.socket.PlayerSocket;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

/**
 * @author Luca
 * @author Andrea
 */
public class FixedIncrementEffectTest {
    @Test
    public void active() throws Exception {
        FixedIncrementEffect ef1 = new FixedIncrementEffect(new Resource(5, ResourceType.COINS));
        FixedIncrementEffect ef2 = new FixedIncrementEffect(new Resource(-2, ResourceType.SERVANTS));
        PlayerSocket p = new PlayerSocket("luca");
        p.createPersonalBoard(1);
        ef1.active(p);
        ef2.active(p);
        assertEquals(Optional.of(10), Optional.of(p.getPersonalBoard().getQtaResources().get(ResourceType.COINS)));
        assertEquals(Optional.of(1), Optional.of(p.getPersonalBoard().getQtaResources().get(ResourceType.SERVANTS)));
    }

    @Test
    public void createInstance() throws Exception {
    }

}