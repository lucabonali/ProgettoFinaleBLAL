package board;

import actionSpaces.Action;
import actionSpaces.ActionSpaceInterface;
import actionSpaces.largeActionSpaces.CouncilActionSpace;
import actionSpaces.largeActionSpaces.LargeHarvestActionSpace;
import actionSpaces.largeActionSpaces.LargeProductionActionSpace;
import actionSpaces.singleActionSpaces.HarvestActionSpace;
import actionSpaces.singleActionSpaces.MarketActionSpace;
import actionSpaces.singleActionSpaces.ProductionActionSpace;
import api.MessageGame;
import api.LorenzoException;
import types.CardType;
import types.MarketActionType;

import java.util.List;

import static actionSpaces.largeActionSpaces.LargeActionSpace.COD_COUNCIL;
import static actionSpaces.largeActionSpaces.LargeActionSpace.COD_L_HAR_PROD;
import static actionSpaces.singleActionSpaces.ActionSpace.*;

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
    public void doAction(MessageGame msg, FamilyMember familyMember) throws LorenzoException {
        ActionSpaceInterface actionSpace = convertActionMessage(msg);
        currentAction = new Action(actionSpace, familyMember.getValue(), familyMember);
        currentAction.commitAction();
    }


    /**
     * SI PUO' FARE MEGLIO!!!!!!!!
     * metodo che mi converte un messaggio d'azione in un'azione vera e propria
     * @param msg messaggio da convertire
     * @return Action, cioè l'azione
     */
    private ActionSpaceInterface convertActionMessage(MessageGame msg) {
        ActionSpaceInterface actionSpace;
        char[] code = msg.getActionSpaceCode().toCharArray();
        switch (code[0]) {
            case COD_HAR_PROD:
                char zoneType = code[1];
                switch (zoneType) {
                    case 'h':
                        actionSpace = harvestActionSpace;
                        break;
                    case 'p':
                        actionSpace = productionActionSpace;
                        break;
                    default:
                        //di default council
                        actionSpace = councilActionSpace;
                        break;
                }
                break;
            case COD_FLOOR:
                char tower = code[1];
                int numFloor = Integer.parseInt(msg.getActionSpaceCode().substring(2,3));
                switch (tower) {
                    case '1':
                        actionSpace = territoryTower.getFloor(numFloor);
                        break;
                    case '2':
                        actionSpace = characterTower.getFloor(numFloor);
                        break;
                    case '3':
                        actionSpace = buildingTower.getFloor(numFloor);
                        break;
                    case '4':
                        actionSpace = venturesTower.getFloor(numFloor);
                        break;
                    default:
                        //di default council
                        actionSpace = councilActionSpace;
                        break;
                }
                break;
            case COD_MARKET:
                char which = code[1];
                switch (which) {
                    case '1':
                        actionSpace = yellowMarket;
                        break;
                    case '2':
                        actionSpace = purpleMarket;
                        break;
                    case '3':
                        actionSpace = blueMarket;
                        break;
                    case '4':
                        actionSpace = grayMarket;
                        break;
                    default:
                        //di default council
                        actionSpace = councilActionSpace;
                        break;
                }
                break;
            case COD_L_HAR_PROD:
                char lZoneType = code[1];
                switch (lZoneType) {
                    case 'h':
                        actionSpace = harvestActionSpace;
                        break;
                    case 'p':
                        actionSpace = productionActionSpace;
                        break;
                    default:
                        //di default council
                        actionSpace = councilActionSpace;
                        break;
                }
                break;
            case COD_COUNCIL:
                actionSpace = councilActionSpace;
                break;
            default:
                //di default council
                actionSpace = councilActionSpace;
                break;
        }
        return actionSpace;
    }


}
