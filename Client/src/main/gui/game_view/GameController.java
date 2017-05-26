package main.gui.game_view;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import main.Launcher;
import main.api.messages.MessageAction;
import main.api.types.ActionSpacesType;
import main.api.types.CardType;
import main.api.types.FamilyMemberType;
import main.api.types.MarketActionType;
import main.client.AbstractClient;
import main.gui.AnimationService;
import main.gui.game_view.component.*;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @FXML private GridPane blackDicePane;
    @FXML private GridPane whiteDicePane;
    @FXML private GridPane orangeDicePane;

    @FXML private ToolBar toolbar1;
    @FXML private ToolBar toolbar2;

    @FXML private GridPane personalGridPane;
    @FXML private GridPane myPersonalBoardGridPane;
    @FXML private AnchorPane anchorPane;

    //Da cancellare era per prova
    @FXML private Button rollButton;

    private Dice blackDice, whiteDice, orangeDice;

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

    /**
     * mi inizializza gli spazi azione di raccolta e produzione sia larghi
     * che singlo e del palazzo del consiglio
     */
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

    /**
     * mi inizializza gli spazi azione delle torri
     * @param type tipo di torre
     * @param gridPaneTower contenitore che mi identifica la posizione sul tabellone
     */
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

    /**
     * mi inizializza il mercato
     * @param type tipo di mercato
     * @param gridPaneMarket contenitore sulla quale verrà inserito lo spazio azione
     */
    private void initializeMarket(MarketActionType type, GridPane gridPaneMarket) {
        gridPaneSpacesMarketMap.put(type, gridPaneMarket);
        SingleActionSpace actionSpace = new SingleActionSpace(ActionSpacesType.MARKET);
        marketMap.put(type, actionSpace);
        gridPaneSpacesMarketMap.get(type).add(actionSpace, 0, 0);
    }


    /**
     * mi inizializza le imageView e me le posiziona sui gridPane corretti
     * e mi aggiunge gli effetti di zoom.
     */
    private void initializeImageViewCards() {
        for (int i=0; i<16; i++) {
            ImageView img = new ImageView();
            img.setOnMouseEntered(event -> AnimationService.zoomIn(img));
            img.setOnMouseExited(event -> AnimationService.zoomOut(img));
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

    /**
     * mi aggiunge il drag and drop della finestra sulla toolbar in alto
     * @param toolbar
     */
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

    /**
     * inizializza i miei dadi
     * @throws InterruptedException
     */
    private void initializeDices() throws InterruptedException {
        blackDice = new Dice("black", blackDicePane, this);
        whiteDice = new Dice("white", whiteDicePane, this);
        orangeDice = new Dice("orange", orangeDicePane, this);
    }

    /**
     * mi rende visibili i dadi, e posso tirarli
     */
    public void showDices() {
        Platform.runLater(() -> {
            anchorPane.getChildren().addAll(blackDice, whiteDice, orangeDice);
            blackDice.setX(300);
            blackDice.setY(500);
            whiteDice.setX(400);
            whiteDice.setY(500);
            orangeDice.setX(500);
            orangeDice.setY(500);
        });
    }

    /**
     * mi setta i dadi, in base ai valori ricevuti dal server
     * @param orange
     * @param white
     * @param black
     */
    public void setDices(int orange, int white, int black) {
        Platform.runLater(() -> {
            orangeDice.setNumber(orange);
            whiteDice.setNumber(white);
            blackDice.setNumber(black);
        });
    }

    /**
     * mi invia il risulato dei dadi, appena lanciati, al server
     */
    void sendDices(){
        if (blackDice.isRolled() && whiteDice.isRolled()&& orangeDice.isRolled()){
            try {
                client.shotDice(orangeDice.getNum(), whiteDice.getNum(), blackDice.getNum());
            }
            catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void endMoveAction(ActionEvent event) throws RemoteException {
        client.endMove();
    }

    @FXML
    public void actionDoAction(ActionEvent event) throws RemoteException {
        MessageAction msg = new MessageAction(ActionSpacesType.TOWERS, CardType.VENTURES, 3, FamilyMemberType.ORANGE_DICE);
        client.doAction(msg);
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
            personalGridPane.add(messagesServer, 0, 2);
            fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("main/gui/game_view/personal_board_view.fxml"));
            Parent personalBoard = fxmlLoader.load();
            //client.setPersonalBoardController(fxmlLoader.getController());
            myPersonalBoardGridPane.add(personalBoard, 0, 0);
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
