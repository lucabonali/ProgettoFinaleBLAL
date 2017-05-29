package main.CLI;

import javafx.scene.layout.GridPane;
import main.api.types.*;
import main.client.AbstractClient;
import main.gui.game_view.Dice;
import main.gui.game_view.component.Card;
import main.gui.game_view.component.action_spaces.ActionSpaceInterface;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Andrea
 * @author Luca
 */
public class CLIController implements InterfaceGuiCli  {
    private AbstractClient client;

    @Override
    public void initializeHarvestProduction() {

    }

    @Override
    public void initializeTowers(CardType cardType, GridPane gridPaneTower) {

    }

    @Override
    public void initializeMarket(MarketActionType marketType, GridPane gridPaneMarket) {

    }

    @Override
    public void initializeImageViewCards() {

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
}
