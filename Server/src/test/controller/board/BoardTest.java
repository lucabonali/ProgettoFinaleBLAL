package test.controller.board;

import main.api.types.MarketActionType;
import main.controller.board.Board;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * @author Luca
 * @author Andrea
 */
public class BoardTest {
    @Test
    public void board() {
        Board b = new Board(2);
        assertTrue(b.getLargeHarvestActionSpace()==null);
        assertTrue(b.getMarketActionSpaceMap().get(MarketActionType.YELLOW) != null);
        assertTrue(b.getMarketActionSpaceMap().get(MarketActionType.BLUE) == null);
    }

    @Test
    public void initializeTurn() throws Exception {
    }

    @Test
    public void getOrder() throws Exception {
    }

    @Test
    public void getCurrentAction() throws Exception {
    }

    @Test
    public void doAction() throws Exception {
    }

}