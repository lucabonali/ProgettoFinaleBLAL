package main.CLI;

import main.api.types.ActionSpacesType;
import main.api.types.CardType;
import main.api.types.FamilyMemberType;
import main.api.types.MarketActionType;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Andrea
 * @author Luca
 */
class GameMenu {
    private CLIController cliController;
    private Map<Integer, ActionSpaceHandler> actionSpaceMap;
    private Map<Integer,MenuHandler> menuChoices;
    private Map<Integer,CardType> towerMap;
    private Map<Integer,GameHandler> gameMap;
    private Map<Integer,MarketActionType> marketMap;
    private Map<Integer,FamilyMemberType> familyMemberTypeMap;
    private Map<ActionSpacesType,Map<Integer,FamilyMemberType>> singleActionSpaceOccMap;
    private Map<ActionSpacesType,Map<Integer,List<FamilyMemberType>>> largeActionSpaceOccMap;
    private Map<MarketActionType,Map<Integer,FamilyMemberType>> marketOccMap;

    /**
     * costanti del menu di gioco
     */
    static final int SURRENDER = 0;
    static final int SHOW_BOARD = 1;
    static final int SHOW_PERSONAL_BOARD = 2;
    static final int SHOW_OPPONENTS_PERSONAL_BOARD = 3;
    static final int DO_ACTION = 4;
    static final int END_TURN = 5;
    /**
     * Costanti del del menu
     */
    static final int MENU_EXIT = 0;

    static final int RANDOM = 1;
    static final int TWO_PALYERS = 2;
    static final int THREE_PLAYERS = 3;
    static final int FOUR_PLAYERS = 4;
    static final int OPTIONS = 5;
    static final int CREDITS = 6;
    /**
     * costanti degli spazi azione
     */
    static final int TOWERS = 1;

    static final int SINGLE_PRODUCTION = 2;
    static final int LARGE_PRODUCTION = 3;
    static final int SINGLE_HARVEST = 4;
    static final int LARGE_HARVEST = 5;
    static final int COUNCIL = 6;
    static final int MARKET = 7;
    /**
     * costanti delle torri
     */
    static final int GREEN = 1;

    static final int BLUE = 1;
    static final int YELLOW = 1;
    static final int PURPLE = 1;
    /**
     * costanti del market
     */
    static final int YELLOW_MARKET = 1;

    static final int PURPLE_MARKET = 2;
    static final int GRAY_MARKET = 3;
    static final int BLUE_MARKET = 4;
    public GameMenu(CLIController cliController){
        this.cliController = cliController;
        initializeGameMenu();
        initializeMenuChoices();
        initizlizeActionSpacesMenu();
        initializeTowerMap();
        initizlizeFamilyMembers();
        initializeMarketMap();
        initizliazeOccActionSpaces();
    }

    /**
     * metodo che iniializz lo stato degli spazi azione (se e da chi sono occupati)
     */
    private void initizliazeOccActionSpaces() {
        singleActionSpaceOccMap = new HashMap<>();
        singleActionSpaceOccMap.put(ActionSpacesType.SINGLE_HARVEST,new HashMap<>());
        singleActionSpaceOccMap.put(ActionSpacesType.SINGLE_PRODUCTION,new HashMap<>());

        largeActionSpaceOccMap = new HashMap<>();
        largeActionSpaceOccMap.put(ActionSpacesType.LARGE_PRODUCTION,new HashMap<>());
        largeActionSpaceOccMap.put(ActionSpacesType.LARGE_HARVEST, new HashMap<>());
        largeActionSpaceOccMap.put(ActionSpacesType.COUNCIL,new HashMap<>());

        marketOccMap = new HashMap<>();
        marketOccMap.put(MarketActionType.YELLOW,new HashMap<>());
        marketOccMap.put(MarketActionType.PURPLE,new HashMap<>());
        marketOccMap.put(MarketActionType.BLUE,new HashMap<>());
        marketOccMap.put(MarketActionType.GRAY,new HashMap<>());
    }

    public Map<ActionSpacesType, Map<Integer, FamilyMemberType>> getSingleActionSpaceOccMap() {
        return singleActionSpaceOccMap;
    }

    public Map<ActionSpacesType, Map<Integer, List<FamilyMemberType>>> getLargeActionSpaceOccMap() {
        return largeActionSpaceOccMap;
    }

    public Map<MarketActionType, Map<Integer, FamilyMemberType>> getMarketOccMap() {
        return marketOccMap;
    }
    /**
     * interfaccie che semplificano la gestione del menu e puramente funzionali
     */
    public Map<Integer, MarketActionType> getMarketMap() {
        return marketMap;
    }

    private void initializeMarketMap() {
        marketMap = new HashMap<>();
        marketMap.put(YELLOW_MARKET,MarketActionType.YELLOW);
        marketMap.put(PURPLE_MARKET,MarketActionType.PURPLE);
        marketMap.put(GRAY_MARKET,MarketActionType.GRAY);
        marketMap.put(BLUE_MARKET,MarketActionType.BLUE);
    }

    private void initializeTowerMap() {
        towerMap = new HashMap<>();
        towerMap.put(1,CardType.TERRITORY);
        towerMap.put(2,CardType.CHARACTER);
        towerMap.put(3,CardType.BUILDING);
        towerMap.put(4,CardType.VENTURES);
    }


    public Map<Integer, CardType> getTowerMap() {
        return towerMap;
    }

    private void initizlizeFamilyMembers() {
        familyMemberTypeMap = new HashMap<>();
        familyMemberTypeMap.put(1,FamilyMemberType.BLACK_DICE);
        familyMemberTypeMap.put(2,FamilyMemberType.WHITE_DICE);
        familyMemberTypeMap.put(3,FamilyMemberType.ORANGE_DICE);
        familyMemberTypeMap.put(4,FamilyMemberType.NEUTRAL_DICE);
    }

    public Map<Integer, FamilyMemberType> getFamilyMemberTypeMap() {
        return familyMemberTypeMap;
    }


    private void initizlizeActionSpacesMenu(){
        actionSpaceMap = new HashMap<>();
        actionSpaceMap.put(TOWERS, cliController::selectTowerAndFloor);
        actionSpaceMap.put(SINGLE_PRODUCTION, cliController::singleProduction);
        actionSpaceMap.put(LARGE_PRODUCTION, cliController::largeProduction);
        actionSpaceMap.put(SINGLE_HARVEST,cliController::singleHarvest);
        actionSpaceMap.put(LARGE_HARVEST,cliController::largeHarvest);
        actionSpaceMap.put(COUNCIL,cliController::council);
        actionSpaceMap.put(MARKET,cliController::market);
    }

    /**
     * metodo che inizializza il menù della partita
     */
    private void initializeMenuChoices() {
        menuChoices = new HashMap<>();
        menuChoices.put(MENU_EXIT ,  cliController::exit);
        menuChoices.put(RANDOM, cliController::randomGame);
        menuChoices.put(TWO_PALYERS, cliController::twoPlayersGame);
        menuChoices.put(THREE_PLAYERS, cliController::threePlayerGame);
        menuChoices.put(FOUR_PLAYERS, cliController::fourPlayerGame);
        menuChoices.put(OPTIONS, cliController::options);
        menuChoices.put(CREDITS, cliController::credits);
    }

    /**
     * metodo che inizializza le scelte del menu di gioco
     */
    private void initializeGameMenu() {
        gameMap = new HashMap<>();
        gameMap.put(SURRENDER , cliController::surrender);
        gameMap.put(SHOW_BOARD, cliController::showBoard);
        gameMap.put(SHOW_PERSONAL_BOARD, cliController::showPersonalBoard);
        gameMap.put(SHOW_OPPONENTS_PERSONAL_BOARD, cliController::showOpponentsPersonalBoard);
        gameMap.put(DO_ACTION, cliController::actionDoAction);
        gameMap.put(END_TURN, cliController::endMoveAction);
    }

    public void handleMenu(Object object) throws InterruptedException {
        MenuHandler handler = menuChoices.get(object);
        if (handler != null) {
            handler.menuHandle();
        }

    }

    public void handleGame(Object object) throws RemoteException {
        GameHandler handler =  gameMap.get(object);
        if (handler != null){
            handler.gameHandle();
        }
    }

    public void actionSpaceHandle(Object object){
        ActionSpaceHandler handler = actionSpaceMap.get(object);
        if(handler != null){
            handler.actionSpaceHandle();
        }
    }

    private interface MenuHandler{
        void menuHandle() throws InterruptedException;
    }

    private interface GameHandler{
        void gameHandle() throws RemoteException;
    }

    private interface ActionSpaceHandler{
        void actionSpaceHandle();
    }


}
