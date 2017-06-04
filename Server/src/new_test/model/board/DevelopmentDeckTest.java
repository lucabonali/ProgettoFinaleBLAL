package new_test.model.board;

import main.api.types.CardType;
import main.model.board.DevelopmentCard;
import main.model.board.DevelopmentDeck;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Andrea
 * @author Luca
 */
public class DevelopmentDeckTest {
    private DevelopmentDeck developmentDeck;

    @Before
    public void setup() {
        developmentDeck = new DevelopmentDeck();
    }

    @Test
    public void drawCards() throws Exception {
        List<DevelopmentCard> territories = developmentDeck.drawCards(1, 1, CardType.TERRITORY);
        List<DevelopmentCard> characters = developmentDeck.drawCards(1, 2, CardType.CHARACTER);
        List<DevelopmentCard> buildings = developmentDeck.drawCards(2, 2, CardType.BUILDING);
        List<DevelopmentCard> ventures = developmentDeck.drawCards(3, 2, CardType.VENTURES);
        for (DevelopmentCard card : territories) {
            assertEquals(CardType.TERRITORY, card.getType());
        }
        for (DevelopmentCard card : characters) {
            assertEquals(CardType.CHARACTER, card.getType());
        }
        for (DevelopmentCard card : buildings) {
            assertEquals(CardType.BUILDING, card.getType());
        }
        for (DevelopmentCard card : ventures) {
            assertEquals(CardType.VENTURES, card.getType());
        }
    }
}