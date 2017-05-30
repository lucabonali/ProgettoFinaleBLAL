package main.CLI;

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
    private AbstractClient client;
    private BufferedReader in;
    private String userName, password;
    private static Map<Integer,MenuHandler> menuChoices;


    public CLIController(){
        this.in = new BufferedReader(new InputStreamReader(System.in));
        this.client = AbstractClient.getInstance();
    }

    @Override
    public void setBoardCards(List<String> namesList) {

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
    public void modifyPoints(Map<ResourceType, Integer> map) {

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

    public void initialize() throws InterruptedException {
        client = AbstractClient.getInstance();
    }


    @Override
    public void run() {
        initializeMenuChoices();

        //Ciclo con dentro lo switch incredibile
        while(true){
            System.out.println("-------------- LORENZO IL MAGNIFICO --------------");
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

    private void twoPlayersGame() {
        System.out.println("twoPlayers");
    }


    private void randomGame() {
        System.out.println("---- RANDOM GAME ----");
        try {
            client.startGame(1);
            new Thread(() -> {
                game();
            }).start();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void game() {
    }





    public void exit(){
        System.out.println("exit");


    }






    public void handleMenu(Object object) {
        MenuHandler handler = menuChoices.get(object);
        if (handler != null) {
            handler.handle();
        }

    }

    /**
     * interfaccia che semplifica la gestione del menu e puramente funzionale
     */
    private interface MenuHandler{
        void handle();
    }



}
