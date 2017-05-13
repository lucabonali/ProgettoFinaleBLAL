package board;

import actionSpaces.*;

/**
 * @author Luca
 * @author Andrea
 *
 * rappresenta il tabellone della partita.
 */
public class Board {
    private int numPlayers;

    private Tower territoryTower, characterTower, buildingTower, venturesTower;
    private HarProdActionSpace harActionSpace, prodActionSpace;
    private LHarProdActionSpace lHarActionSpace, lProdActionSpace;
    private MarketActionSpace yellowMarket, purpleMarket, blueMarket, grayMarket;
    private CouncilActionSpace councilActionSpace;

    private ExcomCardDeck excomCardDeck;
    private Deck deck;



    public Board(int numPlayers){
        initializeTowers();
        this.numPlayers = numPlayers;
        initializeActionSpaces();
        initializeDecks();
    }

    /**
     * Metodo che inizializza gli spazi azione
     */
    private void initializeActionSpaces() {
        harActionSpace = new HarProdActionSpace(1, AreaType.HARVEST);
        prodActionSpace = new HarProdActionSpace(1, AreaType.PRODUCTION);
        if (numPlayers > 2) {
            lHarActionSpace = new LHarProdActionSpace(1, AreaType.HARVEST);
            lProdActionSpace = new LHarProdActionSpace(1, AreaType.PRODUCTION);
        }
        yellowMarket = new MarketActionSpace(MarketActionType.YELLOW);
        purpleMarket = new MarketActionSpace(MarketActionType.PURPLE);
        if(numPlayers > 3) {
            blueMarket = new MarketActionSpace(MarketActionType.BLUE);
            grayMarket = new MarketActionSpace(MarketActionType.GRAY);
        }
        councilActionSpace = new CouncilActionSpace(1);
    }

    /**
     * Metodo che inizializza le torri
     */
    private void initializeTowers() {
        territoryTower = new Tower(CardType.TERRITORY);
        characterTower = new Tower(CardType.CHARACTER);
        buildingTower = new Tower(CardType.BUILDING);
        venturesTower = new Tower(CardType.VENTURES);
    }

    private void initializeDecks(){
        excomCardDeck = new ExcomCardDeck();
        deck = new Deck();
    }

}
