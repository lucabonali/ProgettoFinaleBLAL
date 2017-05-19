package test.controller.actionSpaces;

import main.api.exceptions.LorenzoException;
import main.api.exceptions.NewActionException;
import main.api.types.CardType;
import main.api.types.FamilyMemberType;
import main.api.types.ResourceType;
import main.model.actionSpaces.Action;
import main.model.actionSpaces.largeActionSpaces.LargeHarvestActionSpace;
import main.model.board.Card;
import main.model.effects.AreaActivationEffect;
import main.model.effects.Effect;
import main.model.effects.FixedIncrementEffect;
import main.model.fields.Resource;
import main.servergame.rmi.PlayerRMI;
import org.junit.Test;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

/**
 * @author lampa
 */
public class LargeHarvestActionSpaceTest {
    @Test
    public void doAction() throws Exception {
        LargeHarvestActionSpace lhas = new LargeHarvestActionSpace(1);
        PlayerRMI player = new PlayerRMI("andrea");
        player.createPersonalBoard(1);
        Action action = new Action(lhas, 5, player.getFamilyMember(FamilyMemberType.ORANGE_DICE), player);
        AreaActivationEffect areaActivationEffect = new AreaActivationEffect(
                                    new FixedIncrementEffect(
                                            new Resource(5, ResourceType.WOOD)), 3);
        List<Effect> effectList = new ArrayList<>();
        effectList.add(areaActivationEffect);
        Card card = new Card(CardType.TERRITORY, "zappa", null, null, effectList, 1);
        card.setPlayer(player); //pesco la carta al giocatore
        lhas.doAction(action);
        assertEquals(Optional.of(8), Optional.of(player.getPersonalBoard().getQtaResources().get(ResourceType.WOOD)));
        assertEquals(Optional.of(3), Optional.of(player.getPersonalBoard().getQtaResources().get(ResourceType.STONE)));
        assertEquals(Optional.of(4), Optional.of(player.getPersonalBoard().getQtaResources().get(ResourceType.SERVANTS)));
    }

    @Test
    public void doAction2() throws LorenzoException, RemoteException, NewActionException {
        LargeHarvestActionSpace lhas = new LargeHarvestActionSpace(1);
        PlayerRMI player = new PlayerRMI("luca");
        player.createPersonalBoard(1);
        Action action = new Action(lhas, 4, player.getFamilyMember(FamilyMemberType.ORANGE_DICE), player);
        AreaActivationEffect areaActivationEffect = new AreaActivationEffect(
                new FixedIncrementEffect(
                        new Resource(5, ResourceType.WOOD)), 5);
        List<Effect> effectList = new ArrayList<>();
        effectList.add(areaActivationEffect);
        Card card = new Card(CardType.TERRITORY, "zappa", null, null, effectList, 1);
        card.setPlayer(player); //pesco la carta al giocatore
        lhas.doAction(action);
        assertEquals(Optional.of(3), Optional.of(player.getPersonalBoard().getQtaResources().get(ResourceType.WOOD)));
    }

}