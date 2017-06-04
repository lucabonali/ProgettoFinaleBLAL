package new_test.model.board;

import main.api.types.CardType;
import main.api.types.ResourceType;
import main.game_server.AbstractPlayer;
import main.game_server.exceptions.LorenzoException;
import main.game_server.rmi.PlayerRMI;
import main.model.board.DevelopmentCard;
import main.model.effects.development_effects.AreaActivationEffect;
import main.model.effects.development_effects.Effect;
import main.model.effects.development_effects.FixedIncrementEffect;
import main.model.fields.Field;
import main.model.fields.Resource;
import org.junit.Before;
import org.junit.Test;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Andrea
 * @author Luca
 */
public class DevelopmentCardTest {
    private AbstractPlayer player;
    private DevelopmentCard characterCard;
    private DevelopmentCard territoryCard;

    @Before
    public void setup() throws RemoteException {
        player = new PlayerRMI("andrea");
        player.createPersonalBoard(1);List<Field> cost = new ArrayList<>();
        cost.add(new Resource(-3, ResourceType.SERVANTS));
        List<Effect> quick = new ArrayList<>();
        quick.add(new FixedIncrementEffect(new Resource(4, ResourceType.STONE)));
        List<Effect> permanent = new ArrayList<>();
        permanent.add(new FixedIncrementEffect(new Resource(2, ResourceType.COINS)));
        characterCard = new DevelopmentCard(CardType.CHARACTER, "lorenzone", cost, quick, permanent, 1);
        cost = new ArrayList<>();
        cost.add(new Resource(7, ResourceType.COINS));
        quick = new ArrayList<>();
        quick.add(new FixedIncrementEffect(new Resource(3, ResourceType.WOOD)));
        permanent = new ArrayList<>();
        permanent.add(new AreaActivationEffect(new FixedIncrementEffect(new Resource(2, ResourceType.SERVANTS)), 3));
        territoryCard = new DevelopmentCard(CardType.TERRITORY, "valle", cost, quick, permanent, 1);
    }

    @Test
    public void getPlayer() throws Exception {
        assertNull(characterCard.getPlayer());
        characterCard.setPlayer(player);
        assertNotNull(characterCard.getPlayer());
    }

    @Test
    public void setPlayer() throws Exception {
        characterCard.setPlayer(player);
        assertEquals(0L, player.getPersonalBoard().getQtaResources().get(ResourceType.SERVANTS).intValue());
        assertNotNull(player.getPersonalBoard().getCardsList(CardType.CHARACTER).get(0));
    }

    @Test (expected = LorenzoException.class)
    public void setPlayerError() throws LorenzoException {
        territoryCard.setPlayer(player);
    }

    @Test (expected = LorenzoException.class)
    public void checkDrawn() throws LorenzoException {
        characterCard.setPlayer(player);
        characterCard.checkDrawn();
    }

    @Test
    public void activeQuickEffects() throws Exception {
        //devo creare la partita
    }

    @Test
    public void activePermanentEffects() throws Exception {
        //devo creare la partita
    }

}