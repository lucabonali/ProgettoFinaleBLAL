package main.CLI;

import com.sun.org.apache.regexp.internal.RE;
import main.api.types.*;
import main.client.AbstractClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private AbstractClient client;
    private BufferedReader in;
    private String userName, password;
    private static Map<Integer,MenuHandler> menuChoices;
    private static Map<Integer,GameHandler > gameMap;
    private boolean isGameStarted = false;
    private List<String> boardCards; // sono tutte le carte
    private List<String> excomCards;
    private CLICards cliCards;



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

    @Override
    public void setDices(int orange, int white, int black) {

    }

    @Override
    public void sendDices() {

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

    @Override
    public void showDices() {

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

    }

    @Override
    public void actionDoAction() throws RemoteException {

    }

    @Override
    public void actionDoNewAction() throws RemoteException {

    }

    @Override
    public void startGame(int id) {
        isGameStarted = true;
    }

    @Override
    public void notifyMessage(String msg) {

    }

    @Override
    public void showExcomCards(List<String> codeList) {
        excomCards = codeList ;
    }

    @Override
    public void surrender() {
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

    }

    public void initialize() throws InterruptedException {
        client = AbstractClient.getInstance();
    }


    @Override
    public void run() {
        initializeMenuChoices();

        //Ciclo con dentro lo switch incredibile
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
                if(choice >= 0 && choice <= 6 )
                   break;

            } catch (IOException e) {
                System.out.println(" Please, insert a correct option. ");
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
        System.out.println("Credits");
    }

    private void options() {
        System.out.println("Options");
    }

    private void fourPlayerGame() {
        System.out.println("fourPlayers");
    }

    private void threePlayerGame() {
        System.out.println("threePlayers");
    }

    /**
     * metodo che lancia la creazione di una partita con 2 players
     */
    private void twoPlayersGame() {
        System.out.println(RED + "---- TWO PLAYERS ----" + RESET);
        try{
            client = AbstractClient.getInstance();
            client.startGame(2);
            new Thread(() ->{
                waitGame();
            }).start();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * metodo che lancia la creazione di una partita con un numero di giocatori random
     */
    private void randomGame() {
        System.out.println(RED + "---- RANDOM GAME ----"+ RESET);
        try {
            client = AbstractClient.getInstance();
            client.startGame(1);
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
        System.out.print(GREEN +"--- Loading Game ");

        while(!isGameStarted){
            System.out.print(" . ");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        game();
    }

    /**
     * metodo che stampa il menu di gioco
     */
    private void game(){
        System.out.println();
        System.out.println(RED +"---- GAME STARTED ----" +RESET);

        initializeGameMenu();
        while(true){
            System.out.println(CYAN + "-------------------------" + RESET);
            System.out.println(" 1 - SHOW BOARD ");
            System.out.println(" 2 - SHOW PERSONAL BOARD ");
            System.out.println(" 3 - SHOW OPPONENTS PERSONAL BOARD ");
            System.out.println(" 4 - DO ACTION ");
            System.out.println(" 5 - END TURN ");
            System.out.println(" 0 - SURRENDER ");
            System.out.println(CYAN + "-------------------------" + CYAN);

            int choice = 0;

            try {
                choice = Integer.parseInt(in.readLine()) ;
                handleGame(choice);
            } catch (IOException e) {
                System.out.println(" Please, insert a correct option. ");
            }
        }

    }

    /**
     * metodo exit che fa uscire dal menu principale e termina il programma
     */
    public void exit(){
        System.out.println(RED + " GOODBYE :) "  + RESET );
        System.exit(0);
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
    }

    private void showOpponentsPersonalBoard() {
    }

    private void showPersonalBoard() {
        System.out.println("----- Personal Development Cards -----");


    }

    private void showBoard() {
        System.out.println(RED + "----- Development Cards -----" + RESET);
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
            System.out.println(GREEN + " "+i+"^ Period : " + cliCards.getExcomCards(excomCards.get(i)) + RESET);
        }
        System.out.println(RED +"---------------------------------" +RESET);


    }


    public void handleMenu(Object object) {
        MenuHandler handler = menuChoices.get(object);
        if (handler != null) {
            handler.menuHandle();
        }

    }

    public void handleGame(Object object){
        GameHandler handler = gameMap.get(object);
        if (handler != null){
            handler.gameHandle();
        }
    }

    /**
     * interfaccia che semplifica la gestione del menu e puramente funzionale
     */
    private interface MenuHandler{
        void menuHandle();
    }

    public interface GameHandler{
        void gameHandle();
    }



}
