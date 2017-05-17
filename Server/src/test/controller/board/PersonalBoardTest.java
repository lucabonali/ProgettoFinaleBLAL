package test.controller.board;

import main.api.types.ResourceType;
import main.controller.board.PersonalBoard;
import main.controller.fields.Resource;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static junit.framework.Assert.assertTrue;

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
    void getResourceList() {

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
    }

    @Test
    void getFamilyMember() {
    }

    @Test
    void getQtaResources() {
        int id = 4;
        PersonalBoard personalBoard = new PersonalBoard(id);
        Map<ResourceType, Integer> map = personalBoard.getQtaResources();
        assertTrue(8 == map.get(ResourceType.COINS));
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
    }

    @Test
    void calculateVictoryPoints() {
    }

}