package test.controller.effects;

import main.api.exceptions.NewActionException;
import main.model.effects.ActionEffect;
import main.servergame.rmi.PlayerRMI;
import org.junit.Test;

/**
 * @author Luca
 * @author Andrea
 */
public class ActionEffectTest {
    @Test(expected = NewActionException.class)
    public void active() throws Exception {
        PlayerRMI p = new PlayerRMI("luca");
        ActionEffect ae = new ActionEffect('a', 4);
        ae.active(p);
    }

    @Test
    public void createInstance() throws Exception {
    }

}