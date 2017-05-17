package main.controller.board;

import main.api.types.ActionSpacesType;
import main.api.exceptions.NewActionException;
import main.controller.actionSpaces.Action;
import main.controller.actionSpaces.ActionSpaceInterface;
import main.controller.actionSpaces.largeActionSpaces.CouncilActionSpace;
import main.controller.actionSpaces.largeActionSpaces.LargeHarvestActionSpace;
import main.controller.actionSpaces.largeActionSpaces.LargeProductionActionSpace;
import main.controller.actionSpaces.singleActionSpaces.HarvestActionSpace;
import main.controller.actionSpaces.singleActionSpaces.MarketActionSpace;
import main.controller.actionSpaces.singleActionSpaces.ProductionActionSpace;
import main.api.messages.MessageGame;
import main.api.exceptions.LorenzoException;
import main.api.types.CardType;
import main.api.types.MarketActionType;
import main.game.AbstractPlayer;

import java.rmi.RemoteException;
import java.util.List;

/**
 * @author Luca
 * @author Andrea
 *
 * rappresenta il tabellone della partita.
 */
public class Board {
    private int numPlayers;

    private Tower territoryTower, characterTower, buildingTower, venturesTower;
    private HarvestActionSpace harvestActionSpace;
    private ProductionActionSpace productionActionSpace;
    private LargeHarvestActionSpace largeHarvestActionSpace;
    private LargeProductionActionSpace largeProductionActionSpace;
    private MarketActionSpace yellowMarket, purpleMarket, blueMarket, grayMarket;
    private CouncilActionSpace councilActionSpace;

    private ExcomCardDeck excomCardDeck;
    private Deck deck;

    private Action currentAction;


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
        harvestActionSpace = new HarvestActionSpace(1);
        productionActionSpace = new ProductionActionSpace(1);
        councilActionSpace = new CouncilActionSpace(1);
        yellowMarket = new MarketActionSpace(MarketActionType.YELLOW);
        purpleMarket = new MarketActionSpace(MarketActionType.PURPLE);
        if (numPlayers > 2) {
            largeHarvestActionSpace = new LargeHarvestActionSpace(1);
            largeProductionActionSpace = new LargeProductionActionSpace(1);
        }
        if(numPlayers > 3) {
            blueMarket = new MarketActionSpace(MarketActionType.BLUE);
            grayMarket = new MarketActionSpace(MarketActionType.GRAY);
        }
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


    public void initializeTurn(int period, int turn){
        territoryTower.removeFamilyMembers();
        characterTower.removeFamilyMembers();
        buildingTower.removeFamilyMembers();
        venturesTower.removeFamilyMembers();
        harvestActionSpace.removeFamilyMember();
        largeHarvestActionSpace.removeFamilyMembers();
        productionActionSpace.removeFamilyMember();
        largeProductionActionSpace.removeFamilyMembers();
        yellowMarket.removeFamilyMember();
        purpleMarket.removeFamilyMember();
        blueMarket.removeFamilyMember();
        grayMarket.removeFamilyMember();
        councilActionSpace.removeFamilyMembers();
        setCards(period,turn);
    }

    private void setCards(int period, int turn) {
        territoryTower.setCards(deck.drawCards(period,turn,CardType.TERRITORY));
        characterTower.setCards(deck.drawCards(period,turn,CardType.CHARACTER));
        buildingTower.setCards(deck.drawCards(period,turn,CardType.BUILDING));
        venturesTower.setCards(deck.drawCards(period,turn,CardType.VENTURES));
    }

    public List<FamilyMember> getOrder() {
        return councilActionSpace.getFamilyMembers();
    }


    /**
     * mi crea l'azione da messaggio codificato e dopodiché mi esegue l'azione
     * sullo spazio azione corretto
     * @param msg messaggio a decodificare
     * @param familyMember familiare
     * @throws LorenzoException in caso la mosssa non vada abuon fine
     */
    public void doAction(MessageGame msg, AbstractPlayer player, FamilyMember familyMember) throws LorenzoException, RemoteException, NewActionException {
        ActionSpaceInterface actionSpace = convertActionMessage(msg);
        if (actionSpace == null)
            throw new LorenzoException("codice spazio azione errato");
        currentAction = new Action(actionSpace, familyMember.getValue(), familyMember, player);
        currentAction.commitAction();
    }


    /**
     * metodo che in base al messaggio come parametro mi ritorna
     * lo spazio azione corretto del mio tabellone
     * @param msg messaggio da convertire
     * @return lo spazioe azione corretto.
     */
    private ActionSpaceInterface convertActionMessage(MessageGame msg) {
        ActionSpaceInterface actionSpace;
        ActionSpacesType code = msg.getActionSpacesType();
        switch (code) {
            case MARKET:
                switch (msg.getMarketActionType()){
                    case BLUE:
                        return blueMarket;
                    case GRAY:
                        return grayMarket;
                    case PURPLE:
                        return purpleMarket;
                    case YELLOW:
                        return yellowMarket;
                    default:
                        return null;
                }
            case TOWERS:
                int numFloor = msg.getNumFloor();
                switch (msg.getCardType()){
                    case BUILDING:
                        return buildingTower.getFloor(numFloor);
                    case CHARACTER:
                        return characterTower.getFloor(numFloor);
                    case VENTURES:
                        return venturesTower.getFloor(numFloor);
                    case TERRITORY:
                        return territoryTower.getFloor(numFloor);
                    default:
                        return null;
                }
            case COUNCIL:
                return councilActionSpace;
            case SINGLE_HARVEST:
                return harvestActionSpace;
            case LARGE_HARVEST:
                return largeHarvestActionSpace;
            case SINGLE_PRODUCTION:
                return productionActionSpace;
            case LARGE_PRODUCTION:
                return largeProductionActionSpace;
            default:
                return null;
        }
    }
}
