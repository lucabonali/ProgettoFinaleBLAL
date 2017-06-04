package new_test.model.board;

import main.api.types.CardType;
import main.api.types.FamilyMemberType;
import main.api.types.ResourceType;
import main.model.action_spaces.Action;
import main.model.board.DevelopmentCard;
import main.model.board.PersonalBoard;
import main.model.effects.development_effects.Effect;
import main.model.effects.development_effects.FixedIncrementEffect;
import main.model.fields.Field;
import main.model.fields.Resource;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author lampa
 */
public class PersonalBoardTest {
    private PersonalBoard personalBoard;
    private DevelopmentCard card;
    private Action territoryAction;
    private Action buildingAction;

    @Before
    public void setup() {
        personalBoard = new PersonalBoard(1);
        List<Field> cost = new ArrayList<>();
        cost.add(new Resource(-3, ResourceType.SERVANTS));
        List<Effect> quick = new ArrayList<>();
        quick.add(new FixedIncrementEffect(new Resource(4, ResourceType.STONE)));
        List<Effect> permanent = new ArrayList<>();
        permanent.add(new FixedIncrementEffect(new Resource(2, ResourceType.COINS)));
        card = new DevelopmentCard(CardType.TERRITORY, "valle", cost, quick, permanent, 1);
    }


    @Test
    public void setDiceValues() throws Exception {
        personalBoard.setDiceValues(5,4,3);
        assertEquals(5L, personalBoard.getFamilyMember(FamilyMemberType.ORANGE_DICE).getValue());
        assertEquals(4L, personalBoard.getFamilyMember(FamilyMemberType.WHITE_DICE).getValue());
        assertEquals(3L, personalBoard.getFamilyMember(FamilyMemberType.BLACK_DICE).getValue());
    }

    @Test
    public void addCard() throws Exception {
        personalBoard.addCard(card);
        assertEquals(card, personalBoard.getCardsList(card.getType()).get(0));
    }

    @Test
    public void modifyResources() throws Exception {
        personalBoard.modifyResources(new Resource(5, ResourceType.SERVANTS));
        personalBoard.modifyResources(new Resource(-1, ResourceType.WOOD));
        assertEquals(8L, personalBoard.getQtaResources().get(ResourceType.SERVANTS).intValue());
        assertEquals(1L, personalBoard.getQtaResources().get(ResourceType.WOOD).intValue());
    }

    @Test
    public void resetResource() throws Exception {
        personalBoard.resetResource(ResourceType.SERVANTS);
        assertEquals(0, personalBoard.getQtaResources().get(ResourceType.SERVANTS).intValue());
    }

    @Test
    public void checkResources() throws Exception {
        assertTrue(personalBoard.checkResources(new Resource(3, ResourceType.SERVANTS)));
        assertTrue(personalBoard.checkResources(new Resource(2, ResourceType.WOOD)));
    }

    @Test
    public void getFamilyMember() throws Exception {
        assertEquals(FamilyMemberType.ORANGE_DICE, personalBoard.getFamilyMember(FamilyMemberType.ORANGE_DICE).getType());
    }

    @Test
    public void removeAllFamilyMembers() throws Exception {
        personalBoard.getFamilyMember(FamilyMemberType.ORANGE_DICE).setPositioned(true);
        personalBoard.removeAllFamilyMembers();
        assertFalse(personalBoard.getFamilyMember(FamilyMemberType.ORANGE_DICE).isPositioned());
    }


    @Test
    public void getQtaResources() throws Exception {
        assertEquals(2L, personalBoard.getQtaResources().get(ResourceType.WOOD).intValue());
    }

    @Test
    public void getPersonalCardsMap() throws Exception {
        personalBoard.addCard(card);
        assertEquals(card.getName(), personalBoard.getPersonalCardsMap().get(card.getType()).get(0));
    }

    @Test
    public void activeTerritoriesEffects() throws Exception {
    }

    @Test
    public void activeBuildingsEffects() throws Exception {
    }

    @Test
    public void activeCharacterEffects() throws Exception {
    }

    @Test
    public void calculateVictoryPoints() throws Exception {
    }
}