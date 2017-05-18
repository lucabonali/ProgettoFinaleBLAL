package test.controller.board;

import main.api.exceptions.LorenzoException;
import main.api.exceptions.NewActionException;
import main.api.types.CardType;
import main.api.types.FamilyMemberType;
import main.api.types.ResourceType;
import main.controller.actionSpaces.Action;
import main.controller.actionSpaces.singleActionSpaces.HarvestActionSpace;
import main.controller.board.Card;
import main.controller.board.FamilyMember;
import main.controller.board.PersonalBoard;
import main.controller.effects.Effect;
import main.controller.effects.FixedIncrementEffect;
import main.controller.fields.Resource;
import main.servergame.rmi.PlayerRMI;
import org.junit.Test;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author lampa
 */
public class PersonalBoardTest {
    @Test
    public void personalBoardConstructor() {
        int id = 1;
        PersonalBoard pb = new PersonalBoard(id);
    }


    @Test
    public void getCurrentAction() {
    }

    @Test
    public void getCardsList() throws RemoteException {
        PlayerRMI p = new PlayerRMI("giorigo");
        p.createPersonalBoard(1);
        assertTrue(p.getPersonalBoard().getCardsList(CardType.TERRITORY).size() == 0);
    }

    @Test
    public void modifyResources1() {
        int id = 4;
        PersonalBoard pb = new PersonalBoard(id);
        pb.modifyResources(new Resource(8, ResourceType.COINS));
        assertTrue((16) == pb.getQtaResources().get(ResourceType.COINS));
    }

    @Test
    public void modifyResources2() {
        int id = 4;
        PersonalBoard pb = new PersonalBoard(id);
        pb.modifyResources(new Resource(8, ResourceType.WOOD));
        assertTrue((8) == pb.getQtaResources().get(ResourceType.COINS));
        assertTrue((10) == pb.getQtaResources().get(ResourceType.WOOD));
    }

    @Test
    public void checkResources() {
        int id = 4;
        PersonalBoard pb = new PersonalBoard(id);
        assertFalse(pb.checkResources(new Resource(4, ResourceType.WOOD)));
        assertTrue(pb.checkResources(new Resource(3, ResourceType.COINS)));
        assertEquals(Optional.of(2), Optional.of(pb.getQtaResources().get(ResourceType.WOOD)));
    }

    @Test
    public void getFamilyMember() {
        int id = 4;
        PersonalBoard pb = new PersonalBoard(id);
        FamilyMember f = pb.getFamilyMember(FamilyMemberType.BLACK_DICE);
        assertTrue(f.getType() == FamilyMemberType.BLACK_DICE);
    }

    @Test
    public void getQtaResources() {
        int id = 4;
        PersonalBoard personalBoard = new PersonalBoard(id);
        Map<ResourceType, Integer> map = personalBoard.getQtaResources();
        assertTrue(0 == map.get(ResourceType.VICTORY));
    }

    @Test
    public void activeTerritoriesEffects() throws RemoteException, NewActionException, LorenzoException {
        PlayerRMI p = new PlayerRMI("andrea");
        p.createPersonalBoard(1);
        List<Effect> ef1 = new ArrayList<>();
        ef1.add(new FixedIncrementEffect(new Resource(5, ResourceType.WOOD)));
        Card card = new Card(CardType.TERRITORY, "valle", null, null, ef1, 1);
        card.setPlayer(p);
        assertTrue(p.getPersonalBoard().getCardsList(CardType.TERRITORY).size() == 1);
        HarvestActionSpace has = new HarvestActionSpace(1);
        p.getPersonalBoard().activeTerritoriesEffects(new Action(has, 6, p.getFamilyMember(FamilyMemberType.BLACK_DICE), p));
        assertEquals(Optional.of(7), Optional.of(p.getPersonalBoard().getQtaResources().get(ResourceType.WOOD)));
    }

    @Test
    public void activeBuildingsEffects() {
    }

    @Test
    public void activeCharacterEffects() {
    }

    @Test
    public void setDiceValues() {
        int id = 4;
        PersonalBoard pb = new PersonalBoard(id);
        pb.setDiceValues(2, 3, 4);
        assertTrue(pb.getFamilyMember(FamilyMemberType.ORANGE_DICE).getValue() == 2);
        assertFalse(pb.getFamilyMember(FamilyMemberType.BLACK_DICE).getValue() == 5);
    }

    @Test
    public void calculateVictoryPoints() throws RemoteException, NewActionException {
        PersonalBoard pb = new PersonalBoard(3);
        //2 wood, 2 stone, 3 servants e 4+3 coins, totale=14 --> 2 victory
        assertEquals(2, pb.calculateVictoryPoints());
    }

    @Test
    public void removeAllFamilyMembers() {
        PersonalBoard pb = new PersonalBoard(1);
        FamilyMember f = pb.getFamilyMember(FamilyMemberType.ORANGE_DICE);
        f.setPositioned(true);
        pb.removeAllFamilyMembers();
        assertFalse(f.isPositioned());
    }

}