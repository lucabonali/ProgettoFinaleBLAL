package main.CLI;

import main.api.messages.MessageAction;
import main.api.types.*;
import main.api.types.ResourceType;
import main.client.AbstractClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author Andrea
 * @author Luca
 */
public class CLIController implements InterfaceController, Runnable {
    public static final String RESET = "\u001B[0m";
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";
    public static final String BLACK_BACKGROUND = "\u001B[40m";
    public static final String RED_BACKGROUND = "\u001B[41m";
    public static final String GREEN_BACKGROUND = "\u001B[42m";
    public static final String YELLOW_BACKGROUND = "\u001B[43m";
    public static final String BLUE_BACKGROUND = "\u001B[44m";
    public static final String PURPLE_BACKGROUND = "\u001B[45m";
    public static final String CYAN_BACKGROUND = "\u001B[46m";
    public static final String WHITE_BACKGROUND = "\u001B[47m";

    private AbstractClient client;
    private BufferedReader in;
    private String userName, password;
    private static Map<Integer,MenuHandler> menuChoices;
    private static Map<Integer,GameHandler > gameMap;
    private boolean isGameStarted = false;
    private List<String> boardCards; // sono tutte le carte del tabellone
    private List<String> excomCards;
    private CLICards cliCards;
    private int personalId;
    private int black = 0,orange = 0 ,white = 0;
    private ActionSpacesType actionSpacesType;
    private Map<ActionSpacesType, ActionSpaceHandler> actionSpaceMap;




    public CLIController(){
        this.in = new BufferedReader(new InputStreamReader(System.in));
        this.client = AbstractClient.getInstance();
        cliCards = new CLICards();
    }

    @Override
    public void setBoardCards(List<String> namesList) {
        this.boardCards = namesList;
    }

    @Override
    public void removeDrawnCards(Map<CardType, List<String>> nameCards) {

    }

    /**
     * metodo chiamato dal server che setta il valore dei dadi una volta che il primo giocatore li ha tirati
     * @param orange
     * @param white
     * @param black
     */
    @Override
    public void setDices(int orange, int white, int black) {
        this.black = black;
        this.white = white;
        this.orange = orange;
    }

    /**
     * metodo che comunica al server che i dadi sono stati tirati, passandogli i valori
     */
    @Override
    public void sendDices() {
        int orange = new Random().nextInt(5)+1;
        int white = new Random().nextInt(5)+1;
        int black = new Random().nextInt(5)+1;
        System.out.println(WHITE + "DICES ROLLED : " + RESET);
        System.out.println(WHITE_BACKGROUND + BLACK + " BLACK DICE :" + black + RESET);
        System.out.println(BLACK_BACKGROUND + WHITE + " WHITE DICE :" + white + RESET);
        System.out.println(RED_BACKGROUND + YELLOW + " ORANGE DICE :" + orange + RESET);
        try {
            client.shotDice(orange , white , black);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createOpponentDiscs(int id) {

    }

    @Override
    public void createFamilyMembers(int id) {

    }

    @Override
    public void relocateFamilyMembers() {

    }

    @Override
    public void moveFamilyMember(ActionSpacesType actionSpacesType, CardType cardType, int numFloor, MarketActionType marketActionType, FamilyMemberType familyMemberType) {

    }

    @Override
    public void modifyResources(Map<ResourceType, Integer> map) {

    }

    @Override
    public void modifyOpponentPoints(Map<ResourceType, Integer> map, int id) {

    }

    /**
     * metodo che comunica al player che deve tirare i dadi
     */
    @Override
    public void showDices() {

        System.out.println(" ---- Press 0 to roll the dices ---- ");
        while (true) {
            try {
                int roll = Integer.parseInt(in.readLine());
                if (roll == 0) {
                    sendDices();
                    game(false);
                    break;
                } else
                    System.out.println("Please, insert 0 to roll the dices");

            } catch (IOException | NumberFormatException e) {
                System.out.println("Please, insert 0 to roll the dices");

            }
        }
    }

    @Override
    public void createDiscs(int id) {

    }

    @Override
    public void showExcommunicatingAlert() {

    }

    @Override
    public void showPrivilegeAlert() {

    }

    @Override
    public void endMoveAction() throws RemoteException {
        client.endMove();
    }

    /**
     * metodo che compie un' azione su uno spazio azione attraverso il posizionamento di un familliare
     * @throws RemoteException
     */
    @Override
    public void actionDoAction() throws RemoteException {
        System.out.println(GREEN + " Please, select the type of the Action Space" + RESET );
        initizlizeActionSpacesMenu();
        showActionSpacesTypeMenu();

    }

    private void initizlizeActionSpacesMenu(){
        actionSpaceMap = new HashMap<>();
        actionSpaceMap.put(ActionSpacesType.TOWERS, this::selectTowerAndFloor);
        actionSpaceMap.put(ActionSpacesType.SINGLE_PRODUCTION, this::singleProduction);
        actionSpaceMap.put(ActionSpacesType.LARGE_PRODUCTION,this::largeProduction);
        actionSpaceMap.put(ActionSpacesType.SINGLE_HARVEST,this::singleHarvest);
        actionSpaceMap.put(ActionSpacesType.LARGE_HARVEST,this::largeHarvest);
        actionSpaceMap.put(ActionSpacesType.COUNCIL,this::council);
        actionSpaceMap.put(ActionSpacesType.MARKET,this::market);
    }


    // metodi che identificano e differenziano le operazioni sugli spazi azione
    private void market() {
    }

    private void council() {
    }

    private void largeHarvest() {
    }

    private void singleHarvest() {
    }

    private void largeProduction() {
    }

    private void singleProduction() {
    }

    private void selectTowerAndFloor() {
        int choiceTower = 1;
        int choiceFloor = 1;

        System.out.println(RED +" SELECT A TOWER :" + RESET);
        while(true) {
            System.out.println(GREEN + " 1 - Green tower, territories ");
            System.out.println(BLUE + " 2 - Blue tower, territories ");
            System.out.println(YELLOW + " 3 - Yellow tower, territories ");
            System.out.println(PURPLE + " 4 - Purple tower, territories ");

            try {
                choiceTower = Integer.parseInt(in.readLine());
            } catch (IOException | NumberFormatException e) {
                System.out.println(" Please, insert a correct number ");
            }
        }
    }

    /**
     * metodo che mostra il menu dei tipi di spazi azione su cui eseguire un' azione
     */
    private void showActionSpacesTypeMenu() {
        while(true){
            System.out.println( RED + " ACTION SPACES TYPES " + RESET);
            System.out.println(WHITE +" 1 - TOWERS ");
            System.out.println(" 2 - SINGLE PRODUCTION ");
            System.out.println(" 3 - LARGE PRODUCTION ");
            System.out.println(" 4 - SINGLE HARVEST ");
            System.out.println(" 5 - LARGE HARVEST ");
            System.out.println(" 6 - COUNCIL ");
            System.out.println(" 7 - MARKET " + RESET);

            int choice = 0;

            try {
                choice = Integer.parseInt(in.readLine()) ;
                handleMenu(choice);
                if(choice >= 0 && choice <= 7 )
                    break;
            } catch (IOException | NumberFormatException e) {
                System.out.println(" Please, insert a correct option. ");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }



        }
    }

    @Override
    public void actionDoNewAction() throws RemoteException {

    }

    @Override
    public void exit() throws InterruptedException {

    }

    @Override
    public void startGame(int id) {
        isGameStarted = true;
    }

    @Override
    public void notifyMessage(String msg) {
        System.out.println();
        System.out.println(WHITE_BACKGROUND + BLACK + "GAME MESSAGE : "+msg +RESET);
    }

    @Override
    public void showExcomCards(List<String> codeList) {
        excomCards = codeList ;
    }

    @Override
    public void surrender() {
        System.out.println("---- ARE YOU SURE YOU WANNA SURRENDER ? (yes - no) ----");
        try {
            String surrenderChoice = in.readLine();
            if(surrenderChoice.equals("yes"))
                backToMenu();
            else if(surrenderChoice.equals("no"))
                game(true);
        } catch (IOException e) {
            System.out.println(" Please, insert a correct option. ");
        }

        try {
            client.surrender();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateMyCards(Map<CardType, List<String>> personalCardsMap) {

    }

    @Override
    public void excommunicate(int id, int period) {

    }

    @Override
    public void backToMenu() {
        new Thread(this).start();
    }

    @Override
    public void updateOpponentFamilyMemberMove(int id, MessageAction msgAction) {

    }

    public void initialize() throws InterruptedException {
        client = AbstractClient.getInstance();
    }

    @Override
    public void run() {
        initializeMenuChoices();

        while(true){
            System.out.println(RED + "-------------- LORENZO IL MAGNIFICO --------------" + RESET);
            System.out.println(" 1 - RANDOM GAME ");
            System.out.println(" 2 - TWO PLAYERS ");
            System.out.println(" 3 - THREE PLAYERS ");
            System.out.println(" 4 - FOUR PLAYER ");
            System.out.println(" 5 - OPTIONS ");
            System.out.println(" 6 - CREDITS ");
            System.out.println(" 0 - EXIT ");

            int choice = 0;

            try {
                choice = Integer.parseInt(in.readLine()) ;
                handleMenu(choice);
                if(choice >= 0 && choice <= 4 )
                   break;
            } catch (IOException | NumberFormatException e) {
                System.out.println(" Please, insert a correct option. ");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * metodo che inizializza il menù della partita
     */
    private void initializeMenuChoices() {
        menuChoices = new HashMap<>();
        menuChoices.put(MenuCostants.MENU_EXIT ,  this::exit);
        menuChoices.put(MenuCostants.RANDOM, this::randomGame);
        menuChoices.put(MenuCostants.TWO_PALYERS, this::twoPlayersGame);
        menuChoices.put(MenuCostants.THREE_PLAYERS, this::threePlayerGame);
        menuChoices.put(MenuCostants.FOUR_PLAYERS, this::fourPlayerGame);
        menuChoices.put(MenuCostants.OPTIONS, this::options);
        menuChoices.put(MenuCostants.CREDITS, this::credits);
    }

    private void credits() {
        System.out.println("\n\n\n\n");

        System.out.println(BLUE_BACKGROUND + BLACK + " ----- CREDITS -----" +RESET);
        System.out.println("\nAUTHORS : (In alphabetic order, no discrimination ;) )\n");
        System.out.println(RED +"------ BONALI LUCA ------" +RESET);
        System.out.println(BLUE +"------ LAMPARELLI ANDREA ------" +RESET);
        System.out.println("\n\n\n\n");
        System.out.println("And that' s it, there's no other Credits, Thank you very much.");

    }

    private void options() {
        System.out.println("Options");
    }

    private void fourPlayerGame() {
        System.out.println(RED + "---- FOUR PLAYERS ----" + RESET);
        createGame(4);
    }

    private void threePlayerGame() {
        System.out.println(RED + "---- THREE PLAYERS ----" + RESET);
    createGame(3);
    }

    private void twoPlayersGame() {
        System.out.println(RED + "---- TWO PLAYERS ----" + RESET);
        createGame(2);
    }

    private void randomGame(){
        System.out.println(RED + "---- RANDOM GAME ----"+ RESET);
        createGame(1);
    }

    /**
     * crea una partita in base alla modalità che gli viene passata in input
     * @param modality di gioco ( random, two player ecc)
     */
    private void createGame(int modality) {
        try {
            client = AbstractClient.getInstance();
            client.startGame(modality);
            new Thread(() -> {
                waitGame();
            }).start();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * metodo che attende la inizializzazione di una partita, e la connessione di un numero sufficente di giocatori
     */
    private void waitGame() {
        System.out.print(GREEN + "--- Loading Game ");

        while (!isGameStarted) {
            System.out.print(" . ");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        personalId = client.getId();
        if (personalId != 1){
            game(false);
        }
    }

    /**
     * metodo che inizializza le scelte del menu di gioco
     */
    private void initializeGameMenu() {
        gameMap = new HashMap<>();
        gameMap.put(GameCostants.SURRENDER , this::surrender);
        gameMap.put(GameCostants.SHOW_BOARD, this::showBoard);
        gameMap.put(GameCostants.SHOW_PERSONAL_BOARD, this::showPersonalBoard);
        gameMap.put(GameCostants.SHOW_OPPONENTS_PERSONAL_BOARD, this::showOpponentsPersonalBoard);
        gameMap.put(GameCostants.DO_ACTION, this::actionDoAction);
        gameMap.put(GameCostants.END_TURN, this::endMoveAction);
    }

    /**
     * metodo che stampa il menu di gioco
     */
    private void game(boolean returned){
        if(!returned){
            System.out.println();
            System.out.println(RED + "---- GAME STARTED ----" + RESET);
            initializeGameMenu();
        }
        while(true){
            System.out.println(CYAN + "-------------------------" + RESET);
            System.out.println(" 1 - SHOW BOARD ");
            System.out.println(" 2 - SHOW PERSONAL BOARD ");
            System.out.println(" 3 - SHOW OPPONENTS PERSONAL BOARD ");
            System.out.println(" 4 - DO ACTION ");
            System.out.println(" 5 - END TURN ");
            System.out.println(" 0 - SURRENDER ");
            System.out.println(CYAN + "-------------------------" + RESET);

            int choice = 0;

            try {
                choice = Integer.parseInt(in.readLine()) ;
                if(choice == 0) {
                    handleGame(choice);
                    break;
                }
                else
                    handleGame(choice);
            } catch (IOException | NumberFormatException e) {
                System.out.println(" Please, insert a correct option. ");
            }
        }

    }

    /**
     * metodo che mostra le plancie degli altri giocatori
     */
    private void showOpponentsPersonalBoard() {

        for(int i = 0; i<client.getOpponentsIdList().size() ; i++){
            if(client.getOpponentsIdList().get(i) != personalId) {
                System.out.println(WHITE +" ID PLAYER: "+ client.getOpponentsIdList().get(i));
                showCorrectPersonalBoard(false, client.getOpponentsIdList().get(i));
            }
        }

        for(int i = 0; i < client.getOpponentsIdList().size() ; i++) {
            if(client.getOpponentsIdList().get(i) != personalId) {
                System.out.println(WHITE + " ID PLAYER: " + client.getOpponentsIdList().get(i));
                showPersonalCardsList(false, client.getOpponentsIdList().get(i));
            }
        }
    }


    private void showPersonalBoard(){
        showCorrectPersonalBoard(true ,0);
    }

    /**
     * metodo che mostra la propria plancia, quindi risorse e carte personali sviluppo
     */
    private void showCorrectPersonalBoard(boolean personal, int id) {
        Map<ResourceType,Integer> qtaResourceMap;
        if(personal)
            qtaResourceMap = client.getQtaResourcesMap();
        else{
            qtaResourceMap = client.getOpponentQtaResourcesMap(id);
        }

        System.out.println(RED +"----- Personal Resources -----" +RESET);
        System.out.println(GREEN + " ---  " + ResourceType.WOOD + " : " +qtaResourceMap.get(ResourceType.WOOD) + RESET);
        System.out.println(WHITE +" ---  " + ResourceType.STONE + " : " +qtaResourceMap.get(ResourceType.STONE) + RESET);
        System.out.println(PURPLE +" ---  " + ResourceType.SERVANTS + " : " +qtaResourceMap.get(ResourceType.SERVANTS) + RESET);
        System.out.println(YELLOW +" ---  " + ResourceType.COINS + " : " +qtaResourceMap.get(ResourceType.COINS) + RESET);
        System.out.println(RED +" ---  " + ResourceType.FAITH + " : " +qtaResourceMap.get(ResourceType.FAITH) + RESET);
        System.out.println(WHITE +" ---  " + ResourceType.MILITARY + " : " +qtaResourceMap.get(ResourceType.MILITARY) + RESET);
        System.out.println(YELLOW +" ---  " + ResourceType.VICTORY + " : " +qtaResourceMap.get(ResourceType.VICTORY) + RESET);

        System.out.println(RED +"----- Personal Development Cards -----" +RESET);
        if(personal)
            showPersonalCardsList(true , 0);
    }

    /**
     * metodo di supporto a showCorrectPersonalBoard che stampa la lista delle carte sulla propria plancia
     */
    private void showPersonalCardsList(boolean personal, int id) {
        Map<CardType, List<String>> cardList;
        if(personal)
            cardList = client.getMyCardsList();
        else{
            cardList = client.getOpponentsCardsMap().get(id);
        }


        System.out.println(GREEN + " ---- Territories ---- " );
        if(cardList.get(CardType.TERRITORY) != null) {
            for (int i = 0; i < cardList.get(CardType.TERRITORY).size(); i++) {
                System.out.print(GREEN + "Name : " + cardList.get(CardType.TERRITORY).get(i) + " --- Description : ");
                System.out.print(cliCards.getTerritoryCardList().get(cardList.get(CardType.TERRITORY).get(i)));
            }
        }
        System.out.println(CYAN + " ---- Characters ---- " );
        if(cardList.get(CardType.CHARACTER) != null) {
            for (int i = 0; i < cardList.get(CardType.CHARACTER).size(); i++) {
                System.out.print(CYAN + "Name : " + cardList.get(CardType.CHARACTER).get(i) + " --- Description : ");
                System.out.print(cliCards.getTerritoryCardList().get(cardList.get(CardType.CHARACTER).get(i)));
            }
        }
        System.out.println(YELLOW + " ---- Buildings ---- " );
        if(cardList.get(CardType.BUILDING) != null) {
            for (int i = 0; i < cardList.get(CardType.BUILDING).size(); i++) {
                System.out.print(YELLOW + "Name : " + cardList.get(CardType.BUILDING).get(i) + " --- Description : ");
                System.out.print(cliCards.getTerritoryCardList().get(cardList.get(CardType.BUILDING).get(i)));
            }
        }
        System.out.println(PURPLE + " ---- Ventures ---- " );
        if(cardList.get(CardType.VENTURES) != null) {
            for (int i = 0; i < cardList.get(CardType.VENTURES).size(); i++) {
                System.out.print(PURPLE + "Name : " + cardList.get(CardType.VENTURES).get(i) + " --- Description : ");
                System.out.print(cliCards.getTerritoryCardList().get(cardList.get(CardType.VENTURES).get(i)));
            }
        }
    }

    /**
     * metodo che stampa lo stato del tabellone
     *
     */
    private void showBoard() {
        System.out.println(RED + "----- Towers Development Cards -----" + RESET);
        for(int i = 0; i<boardCards.size(); i++) {
            String card = boardCards.get(i);
            if(i<4) {
                System.out.print(GREEN +"Name : " + card + " --- Description : ");
                System.out.print(cliCards.getTerritoryCardList().get(card));
                System.out.println(" Action Space Value : " + cliCards.getActionSpaceValue(i) +RESET );
            }
            else if(i<8){
                System.out.print(CYAN + "Name : " + card + " --- Description : ");
                System.out.print(cliCards.getCharacterCardList().get(card));
                System.out.println(" Action Space Value : " + cliCards.getActionSpaceValue(i) +RESET );
            }
            else if(i<12){
                System.out.print(YELLOW + "Name : " + card + " --- Description : ");
                System.out.print(cliCards.getBuildingsCardList().get(card));
                System.out.println(" Action Space Value : " + cliCards.getActionSpaceValue(i) +RESET );
            }
            else{
                System.out.print(PURPLE + "Name : " + card + " --- Description : ");
                System.out.print(cliCards.getVenturesCardList().get(card));
                System.out.println(" Action Space Value : " + cliCards.getActionSpaceValue(i) + RESET );
            }
        }

        System.out.println(RED +"----- Excommunicating Cards -----" +RESET );
        for(int i = 0; i < 3; i++){
            System.out.println(GREEN + " "+(i+1)+"^ Period : " + cliCards.getExcomCards(excomCards.get(i)) + RESET);
        }
        System.out.println(RED +"---------------------------------" +RESET);
        if(black != 0){
            System.out.println(WHITE_BACKGROUND + BLACK + " BLACK DICE :" + black + RESET);
            System.out.println(BLACK_BACKGROUND + WHITE + " WHITE DICE :" + white + RESET);
            System.out.println(RED_BACKGROUND + YELLOW + " ORANGE DICE :" + orange + RESET);
        }
        else{
            System.out.println(RED +"Dices not rolled yet :(" + RESET);
        }
        showActionSpaces();

    }

    /**
     * metodo che mostra gli spazi azione e se sono occupati
     */
    private void showActionSpaces() {

    }


    public void handleMenu(Object object) throws InterruptedException {
        MenuHandler handler = menuChoices.get(object);
        if (handler != null) {
            handler.menuHandle();
        }

    }

    public void handleGame(Object object) throws RemoteException {
        GameHandler handler = gameMap.get(object);
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
    /**
     * interfaccie che semplificano la gestione del menu e puramente funzionali
     */
    private interface MenuHandler{
        void menuHandle() throws InterruptedException;
    }

    public interface GameHandler{
        void gameHandle() throws RemoteException;
    }

    public interface ActionSpaceHandler{
        void actionSpaceHandle();
    }


}
