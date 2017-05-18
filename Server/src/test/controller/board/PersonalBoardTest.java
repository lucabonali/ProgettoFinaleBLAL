package test.controller.board;

import main.api.exceptions.NewActionException;
import main.api.types.FamilyMemberType;
import main.api.types.ResourceType;
import main.controller.board.FamilyMember;
import main.controller.board.PersonalBoard;
import main.controller.fields.Resource;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.*;

/**
 * @author lampa
 */
class PersonalBoardTest {
    @Test
    void personalBoardConstructor() {
        int id = 1;
        PersonalBoard pb = new PersonalBoard(id);
    }


    @Test
    void getCurrentAction() {
    }

    @Test
    void getCardsList() {
    }

    @Test
    void modifyResources1() {
        int id = 4;
        PersonalBoard pb = new PersonalBoard(id);
        pb.modifyResources(new Resource(8, ResourceType.COINS));
        assertTrue((16) == pb.getQtaResources().get(ResourceType.COINS));
    }

    @Test
    void modifyResources2() {
        int id = 4;
        PersonalBoard pb = new PersonalBoard(id);
        pb.modifyResources(new Resource(8, ResourceType.WOOD));
        assertTrue((8) == pb.getQtaResources().get(ResourceType.COINS));
        assertTrue((10) == pb.getQtaResources().get(ResourceType.WOOD));
    }

    @Test
    void checkResources() {
        int id = 4;
        PersonalBoard pb = new PersonalBoard(id);
        assertFalse(pb.checkResources(new Resource(4, ResourceType.WOOD)));
        assertTrue(pb.checkResources(new Resource(3, ResourceType.COINS)));
        assertEquals(Optional.of(2), Optional.of(pb.getQtaResources().get(ResourceType.WOOD)));
    }

    @Test
    void getFamilyMember() {
        int id = 4;
        PersonalBoard pb = new PersonalBoard(id);
        FamilyMember f = pb.getFamilyMember(FamilyMemberType.BLACK_DICE);
        assertTrue(f.getType() == FamilyMemberType.BLACK_DICE);
    }

    @Test
    void getQtaResources() {
        int id = 4;
        PersonalBoard personalBoard = new PersonalBoard(id);
        Map<ResourceType, Integer> map = personalBoard.getQtaResources();
        assertTrue(0 == map.get(ResourceType.VICTORY));
    }

    @Test
    void activeTerritoriesEffects() {
    }

    @Test
    void activeBuildingsEffects() {
    }

    @Test
    void activeCharacterEffects() {
    }

    @Test
    void setDiceValues() {
        int id = 4;
        PersonalBoard pb = new PersonalBoard(id);
        pb.setDiceValues(2,3,4);
        assertTrue(pb.getFamilyMember(FamilyMemberType.ORANGE_DICE).getValue() == 2);
        assertFalse(pb.getFamilyMember(FamilyMemberType.BLACK_DICE).getValue() == 5);
    }

    @Test
    void calculateVictoryPoints() throws RemoteException, NewActionException {
        PersonalBoard pb = new PersonalBoard(3);
        //2 wood, 2 stone, 3 servants e 4+3 coins, totale=14 --> 2 victory
        assertEquals(2, pb.calculateVictoryPoints());
    }

    @Test
    void removeAllFamilyMembers() {
        PersonalBoard pb = new PersonalBoard(1);
        FamilyMember f = pb.getFamilyMember(FamilyMemberType.ORANGE_DICE);
        f.setPositioned(true);
        pb.removeAllFamilyMembers();
        assertFalse(f.isPositioned());
    }

}