package main.CLI;

import main.api.types.*;
import main.client.AbstractClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

/**
 * @author Andrea
 * @author Luca
 */
public class CLIController implements InterfaceGuiCli, Runnable {
    private AbstractClient client;
    private BufferedReader in;
    private String userName, password;


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

    @Override
    public void initialize() throws InterruptedException {
        client = AbstractClient.getInstance();
    }


    @Override
    public void run() {
        //Ciclo con dentro lo switch incredibile
        while(true){

        }
    }
}
