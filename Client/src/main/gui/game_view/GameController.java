package main.gui.game_view;

import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import main.Launcher;
import main.api.types.ActionSpacesType;
import main.api.types.CardType;
import main.api.types.MarketActionType;
import main.client.AbstractClient;
import main.gui.game_view.component.*;

import java.io.IOException;
import java.util.*;

/**
 * @author Luca
 * @author Andrea
 */
public class GameController {
    private static final String EXTENSION = ".png";
    public static final int CARD_HEIGHT = 126;
    public static final int CARD_WIDTH = 108;
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

    @FXML private ToolBar toolbar1;
    @FXML private ToolBar toolbar2;

    @FXML private GridPane personalGridPane;
    @FXML private AnchorPane anchorPane;

    //Da cancellare era per prova
    @FXML private Button rollButton;

    private ImageView black, white, orange;

    private DoubleProperty imageWidthProperty;
    private DoubleProperty imageHeightProperty;

    //prova
    @FXML private ColorPicker colorPicker;

    private Map<CardType,String[]> cards = new HashMap<>();

    private Map<CardType, GridPane> gridPaneSpacesTowersMap = new HashMap<>();
    private Map<MarketActionType, GridPane> gridPaneSpacesMarketMap = new HashMap<>();

    //mappe degli spazi azione
    private Map<ActionSpacesType, ActionSpaceInterface> actionSpacesMap = new HashMap<>();
    private Map<CardType, ActionSpaceInterface[]> towerMap = new HashMap<>();
    private Map<MarketActionType, ActionSpaceInterface> marketMap = new HashMap<>();

    //lista delle immagini
    private List<ImageView> imageList = new ArrayList<>();
    private double xOffset;
    private double yOffset;

    private void initializeHarvestProduction() {
        //raccolta singolo
        SingleActionSpace harvest = new SingleActionSpace(ActionSpacesType.SINGLE_HARVEST);
        singleHarvest.add(harvest, 0, 0);
        actionSpacesMap.put(harvest.getType(), harvest);
        //raccolta larga
        LargeActionSpace lHarvest = new LargeActionSpace(ActionSpacesType.LARGE_HARVEST);
        largeHarvest.add(lHarvest, 0, 0);
        actionSpacesMap.put(lHarvest.getType(), lHarvest);
        //produzione singolo
        SingleActionSpace production = new SingleActionSpace(ActionSpacesType.SINGLE_PRODUCTION);
        singleProduction.add(production, 0, 0);
        actionSpacesMap.put(production.getType(), production);
        //produzione larga
        LargeActionSpace lProduction = new LargeActionSpace(ActionSpacesType.LARGE_PRODUCTION);
        largeProduction.add(lProduction, 0, 0);
        actionSpacesMap.put(lProduction.getType(), lProduction);
        //palazzo del consiglio
        CouncilActionSpace councilActionSpace = new CouncilActionSpace(ActionSpacesType.COUNCIL);
        council.add(councilActionSpace, 0, 0);
        actionSpacesMap.put(councilActionSpace.getType(), councilActionSpace);
    }

    private void initializeTowers(CardType type, GridPane gridPaneTower) {
        SingleActionSpace[] array = new SingleActionSpace[4];
        towerMap.put(type, array);
        for (int i=0; i<4; i++) {
            gridPaneSpacesTowersMap.put(type, gridPaneTower);
            SingleActionSpace actionSpace = new SingleActionSpace(ActionSpacesType.TOWERS);
            array[i] = actionSpace;
            gridPaneSpacesTowersMap.get(type).add(actionSpace, 0, i);
        }
    }

    private void initializeMarket(MarketActionType type, GridPane gridPaneMarket) {
        gridPaneSpacesMarketMap.put(type, gridPaneMarket);
        SingleActionSpace actionSpace = new SingleActionSpace(ActionSpacesType.MARKET);
        marketMap.put(type, actionSpace);
        gridPaneSpacesMarketMap.get(type).add(actionSpace, 0, 0);
    }


    private void initializeImageViewCards() {
        for (int i=0; i<16; i++) {
            ImageView img = new ImageView();
            img.setOnMouseEntered(this::zoomIn);
            img.setOnMouseExited(this::zoomOut);
            img.setFitHeight(CARD_HEIGHT);
            img.setFitWidth(CARD_WIDTH);
            img.setPreserveRatio(false);
            imageList.add(img);
            if (i>=0 && i<4) {
                territoriesTower.add(img,0, i);
            }
            else if (i>=4 && i<8) {
                charactersTower.add(img,0, i-4);
            }
            else if (i>=8 && i<12) {
                buildingsTower.add(img,0, i-8);
            }
            else if (i>=12 && i<16) {
                venturesTower.add(img,0, i-12);
            }
        }
    }

    private void addToolbarDragAndDrop(ToolBar toolbar) {
        toolbar.setCursor(Cursor.CLOSED_HAND);
        toolbar.setOnMousePressed(event -> {
            xOffset = Launcher.getPrimaryStage().getX() -event.getScreenX();
            yOffset = Launcher.getPrimaryStage().getY() -event.getScreenY();
            toolbar.setCursor(Cursor.CLOSED_HAND);
        } );

        toolbar.setOnMouseDragged(event -> {
            Launcher.getPrimaryStage().setX(event.getScreenX() + xOffset);
            Launcher.getPrimaryStage().setY(event.getScreenY() + yOffset);
        });
    }

    /**
     * mi setta la lista delle carte sulle torri del tabellone
     * @param namesList lista dei nomi delle carte
     */
    public void setBoardCards(List<String> namesList) {
        for (int i=0; i<namesList.size(); i++) {
            imageList.get(i).setImage(new Image(getClass().getResource("res/cards/"+namesList.get(i)+EXTENSION).toExternalForm()));
        }
    }

    private void zoomIn(MouseEvent mouseEvent) {
        ImageView img = (ImageView) mouseEvent.getSource();
        img.setCursor(Cursor.HAND);
        img.toFront();
        ScaleTransition st = new ScaleTransition(Duration.millis(500), img);
        st.setToY(2);
        st.setToX(1.5);
        st.play();
    }

    private void zoomOut(MouseEvent mouseEvent) {
        ImageView img = (ImageView) mouseEvent.getSource();
        ScaleTransition st = new ScaleTransition(Duration.millis(500), img);
        st.setToY(1);
        st.setToX(1);
        st.play();
    }

    private void initializeDices() throws InterruptedException {
        black = new ImageView();
        white = new ImageView();
        orange = new ImageView();
        black.setImage(new Image(getClass().getResourceAsStream("res/Dadi/Nero/dado1.png")));
        white.setImage(new Image(getClass().getResourceAsStream("res/Dadi/Bianco/dado1.png")));
        orange.setImage(new Image(getClass().getResourceAsStream("res/Dadi/Arancio/dado1.png")));
        rollDice();
        anchorPane.getChildren().addAll(black,white,orange);
        black.setX(300);
        black.setY(500);
        white.setX(400);
        white.setY(500);
        orange.setX(500);
        orange.setY(500);
        initializeDiceListeners();
    }

    private void initializeDiceListeners() {
        TranslateTransition diceTransition = new TranslateTransition(Duration.millis(1000));

        black.setOnMousePressed(e -> {
            diceTransition.setNode(black);
            black.setCursor(Cursor.CLOSED_HAND);
        });

    }

    public void rollDice() throws InterruptedException {


        int faceBlack, faceWhite, faceOrange;
        faceBlack  = 1 + new Random().nextInt(6);
        faceWhite  = 1 + new Random().nextInt(6);
        faceOrange  = 1 + new Random().nextInt(6);
        black.setImage(new Image(getClass().getResourceAsStream("res/Dadi/Nero/dado" + faceBlack + ".png")));
        white.setImage(new Image(getClass().getResourceAsStream("res/Dadi/Bianco/dado" + faceWhite + ".png")));
        orange.setImage(new Image(getClass().getResourceAsStream("res/Dadi/Arancio/dado" + faceOrange + ".png")));
    }


    public void initialize() throws InterruptedException {
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
        initializeImageViewCards();
        initializeDices();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("main/gui/game_view/message_view.fxml"));
            Parent messagesServer = fxmlLoader.load();
            //client.setMessagesController(fxmlLoader.getController());
            personalGridPane.add(messagesServer, 0, 1);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        addToolbarDragAndDrop(toolbar1);
        addToolbarDragAndDrop(toolbar2);


        PersonalVictoryDisc p1 = new PersonalVictoryDisc(Color.BLACK);
        PersonalVictoryDisc p2 = new PersonalVictoryDisc(Color.RED);
        PersonalVictoryDisc p3 = new PersonalVictoryDisc(Color.GREEN);
        PersonalVictoryDisc p4 = new PersonalVictoryDisc(Color.BLUE);
        anchorPane.getChildren().add(p1);
        anchorPane.getChildren().add(p2);
        anchorPane.getChildren().add(p3);
        anchorPane.getChildren().add(p4);
        p1.setCurrentPosition(12);
        p2.setCurrentPosition(15);
        p3.setCurrentPosition(33);
        p4.setCurrentPosition(41);
    }
}
