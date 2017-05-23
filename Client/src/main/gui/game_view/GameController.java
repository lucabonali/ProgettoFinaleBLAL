package main.gui.game_view;

import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.GridPane;
import main.api.types.ActionSpacesType;
import main.api.types.CardType;
import main.api.types.MarketActionType;
import main.clientGame.AbstractClient;
import main.gui.game_view.component.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Luca
 * @author Andrea
 */
public class GameController {
    private AbstractClient client;

    @FXML private GridPane territoriesTower;
    @FXML private GridPane territoryTowersActionSpaces;
    @FXML private GridPane charactersTower;
    @FXML private GridPane characterTowersActionSpaces;
    @FXML private GridPane buildingsTower;
    @FXML private GridPane buildingTowersActionSpaces;
    @FXML private GridPane venturesTower;
    @FXML private GridPane venturesTowersActionSpaces;
    @FXML private GridPane council;
    @FXML private GridPane largeHarvest;
    @FXML private GridPane largeProduction;
    @FXML private GridPane singleHarvest;
    @FXML private GridPane singleProduction;
    @FXML private GridPane yellowMarket;
    @FXML private GridPane purpleMarket;
    @FXML private GridPane blueMarket;
    @FXML private GridPane greyMarket;
    @FXML private GridPane blackDice;
    @FXML private GridPane whiteDice;
    @FXML private GridPane orangeDice;

    //prova
    @FXML private ColorPicker colorPicker;

    private Map<CardType,String[]> cards = new HashMap<>();

    private Map<CardType, GridPane> gridPaneSpacesTowersMap = new HashMap<>();
    private Map<MarketActionType, GridPane> gridPaneSpacesMarketMap = new HashMap<>();

    //mappe degli spazi azione
    private Map<ActionSpacesType, ActionSpaceInterface> actionSpacesMap = new HashMap<>();
    private Map<CardType, ActionSpaceInterface> towerMap = new HashMap<>();
    private Map<MarketActionType, ActionSpaceInterface> marketMap = new HashMap<>();

    private void initializeHarvestProduction() {
        //raccolta singolo
        SingleActionSpace harvest = new SingleActionSpace(ActionSpacesType.SINGLE_HARVEST);
        harvest.setOnMouseClicked(event -> {
            GraphicFamilyMember fm = new GraphicFamilyMember(colorPicker.getValue());
            harvest.addFamilyMember(fm);
        });
        singleHarvest.add(harvest, 0, 0);
        actionSpacesMap.put(harvest.getType(), harvest);
        //raccolta larga
        LargeActionSpace lHarvest = new LargeActionSpace(ActionSpacesType.LARGE_HARVEST);
        lHarvest.setOnMouseClicked(event -> {
            GraphicFamilyMember fm = new GraphicFamilyMember(colorPicker.getValue());
            lHarvest.addFamilyMember(fm);
        });
        largeHarvest.add(lHarvest, 0, 0);
        actionSpacesMap.put(lHarvest.getType(), lHarvest);
        //produzione singolo
        SingleActionSpace production = new SingleActionSpace(ActionSpacesType.SINGLE_PRODUCTION);
        production.setOnMouseClicked(event -> {
            GraphicFamilyMember fm = new GraphicFamilyMember(colorPicker.getValue());
            production.addFamilyMember(fm);
        });
        singleProduction.add(production, 0, 0);
        actionSpacesMap.put(production.getType(), production);
        //produzione larga
        LargeActionSpace lProduction = new LargeActionSpace(ActionSpacesType.LARGE_PRODUCTION);
        lProduction.setOnMouseClicked(event -> {
            GraphicFamilyMember fm = new GraphicFamilyMember(colorPicker.getValue());
            lProduction.addFamilyMember(fm);
        });
        largeProduction.add(lProduction, 0, 0);
        actionSpacesMap.put(lProduction.getType(), lProduction);
        //palazzo del consiglio
        CouncilActionSpace councilActionSpace = new CouncilActionSpace(ActionSpacesType.COUNCIL);
        councilActionSpace.setOnMouseClicked(event -> {
            GraphicFamilyMember fm = new GraphicFamilyMember(colorPicker.getValue());
            councilActionSpace.addFamilyMember(fm);
        });
        council.add(councilActionSpace, 0, 0);
        actionSpacesMap.put(councilActionSpace.getType(), councilActionSpace);
    }

    private void initializeTowers(CardType type, GridPane gridPaneTower) {
        for (int i=0; i<4; i++) {
            gridPaneSpacesTowersMap.put(type, gridPaneTower);
            SingleActionSpace actionSpace = new SingleActionSpace(ActionSpacesType.TOWERS);
            actionSpace.setOnMouseClicked(event -> {
                GraphicFamilyMember fm = new GraphicFamilyMember(colorPicker.getValue());
                actionSpace.addFamilyMember(fm);
            });
            actionSpace.setOnMousePressed(event -> actionSpace.removeAllFamilyMembers());
            towerMap.put(type, actionSpace);
            gridPaneSpacesTowersMap.get(type).add(actionSpace, 0, i);
        }
    }

    private void initializeMarket(MarketActionType type, GridPane gridPaneMarket) {
        gridPaneSpacesMarketMap.put(type, gridPaneMarket);
        SingleActionSpace actionSpace = new SingleActionSpace(ActionSpacesType.MARKET);
        actionSpace.setOnMouseClicked(event -> {
            GraphicFamilyMember fm = new GraphicFamilyMember(colorPicker.getValue());
            actionSpace.addFamilyMember(fm);
        });
        marketMap.put(type, actionSpace);
        gridPaneSpacesMarketMap.get(type).add(actionSpace, 0, 0);
    }

    public void initialize() {
        client = AbstractClient.getInstance();
        initializeTowers(CardType.TERRITORY, territoryTowersActionSpaces);
        initializeTowers(CardType.CHARACTER, characterTowersActionSpaces);
        initializeTowers(CardType.BUILDING, buildingTowersActionSpaces);
        initializeTowers(CardType.VENTURES, venturesTowersActionSpaces);
        initializeMarket(MarketActionType.YELLOW, yellowMarket);
        initializeMarket(MarketActionType.PURPLE, purpleMarket);
        initializeMarket(MarketActionType.BLUE, blueMarket);
        initializeMarket(MarketActionType.GRAY, greyMarket);
        initializeHarvestProduction();
    }
}
