package main.controller.board;

import main.api.exceptions.LorenzoException;
import main.api.exceptions.NewActionException;
import main.api.messages.MessageGame;
import main.api.types.ActionSpacesType;
import main.api.types.CardType;
import main.api.types.MarketActionType;
import main.api.types.ResourceType;
import main.controller.actionSpaces.Action;
import main.controller.actionSpaces.ActionSpaceInterface;
import main.controller.actionSpaces.largeActionSpaces.CouncilActionSpace;
import main.controller.actionSpaces.largeActionSpaces.LargeHarvestActionSpace;
import main.controller.actionSpaces.largeActionSpaces.LargeProductionActionSpace;
import main.controller.actionSpaces.singleActionSpaces.HarvestActionSpace;
import main.controller.actionSpaces.singleActionSpaces.MarketActionSpace;
import main.controller.actionSpaces.singleActionSpaces.ProductionActionSpace;
import main.servergame.AbstractPlayer;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Luca
 * @author Andrea
 *
 * rappresenta il tabellone della partita.
 */
public class Board {
    private int numPlayers;

    private Map<CardType, Tower> towerMap;
    private HarvestActionSpace harvestActionSpace;
    private ProductionActionSpace productionActionSpace;
    private LargeHarvestActionSpace largeHarvestActionSpace;
    private LargeProductionActionSpace largeProductionActionSpace;
    private Map<MarketActionType, MarketActionSpace> marketActionSpaceMap;
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

    //rimuovere
    public Map<MarketActionType, MarketActionSpace> getMarketActionSpaceMap(){
        return marketActionSpaceMap;
    }

    //rimuovere
    public LargeProductionActionSpace getLargeProductionActionSpace() {
        return largeProductionActionSpace;
    }

    //rimuovere
    public LargeHarvestActionSpace getLargeHarvestActionSpace() {
        return largeHarvestActionSpace;
    }

    /**
     * Metodo che inizializza gli spazi azione
     */
    private void initializeActionSpaces() {
        marketActionSpaceMap = new HashMap<>();
        harvestActionSpace = new HarvestActionSpace(1);
        productionActionSpace = new ProductionActionSpace(1);
        councilActionSpace = new CouncilActionSpace(1);
        marketActionSpaceMap.put(MarketActionType.YELLOW,new MarketActionSpace(MarketActionType.YELLOW));
        marketActionSpaceMap.put(MarketActionType.PURPLE,new MarketActionSpace(MarketActionType.PURPLE));
        if (numPlayers > 2) {
            largeHarvestActionSpace = new LargeHarvestActionSpace(1);
            largeProductionActionSpace = new LargeProductionActionSpace(1);
        }
        if(numPlayers > 3) {
            marketActionSpaceMap.put(MarketActionType.BLUE,new MarketActionSpace(MarketActionType.BLUE));
            marketActionSpaceMap.put(MarketActionType.GRAY,new MarketActionSpace(MarketActionType.GRAY));
        }
    }

    /**
     * Metodo che inizializza le torri
     */
    private void initializeTowers() {
        towerMap = new HashMap<>();
        towerMap.put(CardType.TERRITORY, new Tower(CardType.TERRITORY, ResourceType.WOOD));
        towerMap.put(CardType.CHARACTER, new Tower(CardType.CHARACTER, ResourceType.STONE));
        towerMap.put(CardType.BUILDING, new Tower(CardType.BUILDING, ResourceType.MILITARY));
        towerMap.put(CardType.VENTURES, new Tower(CardType.VENTURES, ResourceType.COINS));
    }

    private void initializeDecks(){
        excomCardDeck = new ExcomCardDeck();
        deck = new Deck();
    }


    public void initializeTurn(int period, int turn){
        towerMap.forEach(((cardType, tower) -> tower.removeFamilyMembers()));
        harvestActionSpace.removeFamilyMember();
        largeHarvestActionSpace.removeFamilyMembers();
        productionActionSpace.removeFamilyMember();
        largeProductionActionSpace.removeFamilyMembers();
        marketActionSpaceMap.forEach(((marketActionType, marketActionSpace) -> marketActionSpace.removeFamilyMember()));
        councilActionSpace.removeFamilyMembers();
        setCards(period,turn);
    }

    private void setCards(int period, int turn) {
        towerMap.get(CardType.TERRITORY).setCards(deck.drawCards(period,turn,CardType.TERRITORY));
        towerMap.get(CardType.CHARACTER).setCards(deck.drawCards(period,turn,CardType.CHARACTER));
        towerMap.get(CardType.BUILDING).setCards(deck.drawCards(period,turn,CardType.BUILDING));
        towerMap.get(CardType.VENTURES).setCards(deck.drawCards(period,turn,CardType.VENTURES));
    }

    public List<FamilyMember> getOrder() {
        return councilActionSpace.getFamilyMembers();
    }

    public Action getCurrentAction() {
        return currentAction;
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
                return marketActionSpaceMap.get(msg.getMarketActionType());
            case TOWERS:
                int numFloor = msg.getNumFloor();
                return towerMap.get(msg.getCardType()).getFloor(numFloor);
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
