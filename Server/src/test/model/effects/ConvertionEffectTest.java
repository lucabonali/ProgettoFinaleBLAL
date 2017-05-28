package test.model.effects;

import main.api.types.ResourceType;
import main.model.effects.development_effects.ConvertionEffect;
import main.model.fields.Field;
import main.model.fields.Resource;
import main.game_server.rmi.PlayerRMI;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

/**
 * @author Luca
 * @author Andrea
 */
public class ConvertionEffectTest {
    @Test
    public void active1() throws Exception {
        List<Field> listToIncrement = new ArrayList<>();
        List<Field> listToDecrement = new ArrayList<>();
        Resource res1 = new Resource(5, ResourceType.WOOD);
        Resource res2 = new Resource(2, ResourceType.SERVANTS);
        listToIncrement.add(res1);
        listToIncrement.add(res2);
        Resource res3 = new Resource(2, ResourceType.COINS);
        listToDecrement.add(res3);
        ConvertionEffect effect = new ConvertionEffect(listToIncrement, listToDecrement);
        PlayerRMI p = new PlayerRMI("giacomo");
        p.createPersonalBoard(1);
        effect.active(p);
        assertEquals(Optional.of(5), Optional.of(p.getPersonalBoard().getQtaResources().get(ResourceType.SERVANTS)));
        assertEquals(Optional.of(7), Optional.of(p.getPersonalBoard().getQtaResources().get(ResourceType.WOOD)));
    }

    @Test
    public void active2() throws Exception {
        List<Field> listToIncrement = new ArrayList<>();
        List<Field> listToDecrement = new ArrayList<>();
        Resource res1 = new Resource(5, ResourceType.WOOD);
        Resource res2 = new Resource(2, ResourceType.SERVANTS);
        listToIncrement.add(res1);
        listToIncrement.add(res2);
        Resource res3 = new Resource(8, ResourceType.COINS);
        listToDecrement.add(res3);
        ConvertionEffect effect = new ConvertionEffect(listToIncrement, listToDecrement);
        PlayerRMI p = new PlayerRMI("giacomo");
        p.createPersonalBoard(1);
        effect.active(p);
        assertEquals(Optional.of(3), Optional.of(p.getPersonalBoard().getQtaResources().get(ResourceType.SERVANTS)));
        assertEquals(Optional.of(2), Optional.of(p.getPersonalBoard().getQtaResources().get(ResourceType.WOOD)));
    }

    @Test
    public void createInstance() throws Exception {
    }

}