package test.model.effects;

import main.game_server.exceptions.NewActionException;
import main.model.effects.development_effects.ActionEffect;
import main.game_server.rmi.PlayerRMI;
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