package test.controller.fields;

import main.api.types.ResourceType;
import main.model.effects.development_effects.EffectsCreator;
import main.model.fields.Resource;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author lampa
 */
class ResourceTest {
    @Test
    void getQta() {
        Resource res = new Resource(10, ResourceType.COINS);
        assertEquals(10, res.getQta());
    }

    @Test
    void getType() {
        Resource res = new Resource(10, ResourceType.COINS);
        assertEquals(ResourceType.COINS, res.getType());
    }

    @Test
    void modify1() {
        Resource res = new Resource(10, ResourceType.COINS);
        res.modify(new Resource(-5, ResourceType.COINS));
        assertEquals(5, res.getQta());
    }

    @Test
    void modify2() {
        Resource res = new Resource(10, ResourceType.COINS);
        res.modify(new Resource(-5, ResourceType.WOOD));
        assertEquals(10, res.getQta());
    }

    @Test
    void subtract() {
    }

    @Test
    void setType() {
    }

    @Test
    void createResource1() {
        String cod = "2" + EffectsCreator.CHAR_COIN;
        Resource res = Resource.createResource(cod, false);
        //assertEquals(2, res.getQta());
        assertEquals(ResourceType.COINS, res.getType());
    }

    @Test
    void createResource2() {
        String cod = "2";
        cod += EffectsCreator.CHAR_COIN;
        Resource res = Resource.createResource(cod, true);
        assertEquals(-2, res.getQta());
    }

}