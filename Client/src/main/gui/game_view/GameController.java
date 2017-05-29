package main.gui.game_view;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import main.Launcher;
import main.api.messages.MessageAction;
import main.api.messages.MessageNewAction;
import main.api.types.*;
import main.client.AbstractClient;
import main.gui.Service;
import main.gui.game_view.component.*;
import main.gui.game_view.component.action_spaces.*;

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
    @FXML private HBox personalHBox;
    @FXML private AnchorPane anchorPane;

    @FXML private TextField servantsToPayTextField;

    private Map<CardType, GridPane> gridPaneSpacesTowersMap = new HashMap<>();
    private Map<MarketActionType, GridPane> gridPaneSpacesMarketMap = new HashMap<>();

    private Map<Integer, Map<ResourceType, PersonalDisc>> opponentDiscs = new HashMap<>(); //mappa dei dischetti avversari

    private Map<ResourceType,PersonalDisc> personalDiscs = new HashMap<>(); //mappa dei dischetti dei punti personali
    private Map<FamilyMemberType,GuiFamilyMember> personalFamilyMembers = new HashMap<>(); //mappa dei familiari personali
    private Dice blackDice, whiteDice, orangeDice; //dadi

    //mappe degli spazi azione
    private Map<ActionSpacesType, ActionSpaceInterface> actionSpacesMap = new HashMap<>();
    private Map<CardType, ActionSpaceInterface[]> towerMap = new HashMap<>();
    private Map<MarketActionType, ActionSpaceInterface> marketMap = new HashMap<>();

    //lista delle immagini
    private List<Card> imageList = new ArrayList<>();
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
     * @param cardType tipo di torre
     * @param gridPaneTower contenitore che mi identifica la posizione sul tabellone
     */
    private void initializeTowers(CardType cardType, GridPane gridPaneTower) {
        GuiFloorActionSpace[] array = new GuiFloorActionSpace[4];
        towerMap.put(cardType, array);
        gridPaneSpacesTowersMap.put(cardType, gridPaneTower);
        int gridCounter=0;
        for (int floor=3; floor>=0; floor--) {
            GuiFloorActionSpace actionSpace = new GuiFloorActionSpace(ActionSpacesType.TOWERS, cardType, floor);
            array[floor] = actionSpace;
            gridPaneSpacesTowersMap.get(cardType).add(actionSpace, 0, gridCounter);
            gridCounter++;
        }
    }

    /**
     * mi inizializza il mercato
     * @param marketType tipo di mercato
     * @param gridPaneMarket contenitore sulla quale verrà inserito lo spazio azione
     */
    private void initializeMarket(MarketActionType marketType, GridPane gridPaneMarket) {
        gridPaneSpacesMarketMap.put(marketType, gridPaneMarket);
        GuiMarketActionSpace actionSpace = new GuiMarketActionSpace(ActionSpacesType.MARKET, marketType);
        marketMap.put(marketType, actionSpace);
        gridPaneSpacesMarketMap.get(marketType).add(actionSpace, 0, 0);
    }


    /**
     * mi inizializza le imageView e me le posiziona sui gridPane corretti
     * e mi aggiunge gli effetti di zoom.
     */
    private void initializeImageViewCards() {
        for (int i=0; i<16; i++) {
            Card card = new Card();
            card.setOnMouseEntered(event -> Service.zoomIn(card));
            card.setOnMouseExited(event -> Service.zoomOut(card));
            card.setFitHeight(CARD_HEIGHT);
            card.setFitWidth(CARD_WIDTH);
            card.setPreserveRatio(false);
            imageList.add(card);
            if (i>=0 && i<4) {
                territoriesTower.add(card,0, i);
            }
            else if (i>=4 && i<8) {
                charactersTower.add(card,0, i-4);
            }
            else if (i>=8 && i<12) {
                buildingsTower.add(card,0, i-8);
            }
            else if (i>=12 && i<16) {
                venturesTower.add(card,0, i-12);
            }
        }
    }

    /**
     * mi aggiunge il drag and drop della finestra sulla toolbar in alto
     * @param toolbar toolabr della finestra
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
     * inizializza i miei dadi
     */
    private void initializeDices(){
        blackDice = new Dice("black", blackDicePane, this);
        whiteDice = new Dice("white", whiteDicePane, this);
        orangeDice = new Dice("orange", orangeDicePane, this);
    }


    /**
     * mi setta la lista delle carte sulle torri del tabellone
     * @param namesList lista dei nomi delle carte
     */
    public void setBoardCards(List<String> namesList) {
        for (int i=0; i<namesList.size(); i++) {
            imageList.get(i).setImage(new Image(getClass().getResource("res/cards/"+namesList.get(i)+EXTENSION).toExternalForm()), namesList.get(i));
        }
    }

    /**
     * mi rimuove le carte che sono già state pescate
     * @param nameCards
     */
    public void removeDrawnCards(Map<CardType, List<String>> nameCards) {
        nameCards.forEach(((cardType, namesList) -> {
            namesList.forEach(name -> {
                imageList.forEach(card -> card.remove(name));
            });
        }));
    }

    /**
     * mi rende visibili i dadi, e posso tirarli
     */
    public void showDices() {
        Platform.runLater(() -> {
            blackDice.remove();
            blackDice.initializeDiceListeners();
            orangeDice.remove();
            orangeDice.initializeDiceListeners();
            whiteDice.remove();
            whiteDice.initializeDiceListeners();
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
            orangeDice = orangeDice.setNumber(orange);
            whiteDice = whiteDice.setNumber(white);
            blackDice = blackDice.setNumber(black);
        });
    }

    /**
     * mi invia il risulato dei dadi, appena lanciati, al server
     */
    public void sendDices(){
        if (blackDice.isRolled() && whiteDice.isRolled()&& orangeDice.isRolled()){
            try {
                client.shotDice(orangeDice.getNum(), whiteDice.getNum(), blackDice.getNum());
            }
            catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * mi crea i dischetti del colore in base al''id
     * @param id id del giocatore
     */
    public void createDiscs(int id) {
        personalDiscs.put(ResourceType.VICTORY, new PersonalVictoryDisc(id));
        personalDiscs.put(ResourceType.MILITARY, new PersonalMilitaryDisc(id));
        personalDiscs.put(ResourceType.FAITH, new PersonalFaithDisc(id));
        Platform.runLater(() -> personalDiscs.forEach(((resourceType, personalDisc) -> anchorPane.getChildren().add(personalDisc))));
    }

    /**
     * mi genera e posiziona i dischetti dei giocatori avversari
     * @param id id del giocatore avversario
     */
    public void createOpponentDiscs(int id) {
        Map<ResourceType, PersonalDisc> map = new HashMap<>();
        map.put(ResourceType.VICTORY, new PersonalVictoryDisc(id));
        map.put(ResourceType.MILITARY, new PersonalMilitaryDisc(id));
        map.put(ResourceType.FAITH, new PersonalFaithDisc(id));
        opponentDiscs.put(id, map);
        Platform.runLater(() -> map.forEach(((resourceType, opponentDisc) -> anchorPane.getChildren().add(opponentDisc))));
    }


    /**
     * metodo che mi crea e mi rende visibili i familiari
     * @param id id del giocatore, sulla base del quale si ricava il colore
     */
    public void createFamilyMembers(int id){
        personalFamilyMembers.put(FamilyMemberType.ORANGE_DICE, new GuiFamilyMember(id, FamilyMemberType.ORANGE_DICE));
        personalFamilyMembers.put(FamilyMemberType.BLACK_DICE, new GuiFamilyMember(id, FamilyMemberType.BLACK_DICE));
        personalFamilyMembers.put(FamilyMemberType.WHITE_DICE, new GuiFamilyMember(id, FamilyMemberType.WHITE_DICE));
        personalFamilyMembers.put(FamilyMemberType.NEUTRAL_DICE, new GuiFamilyMember(id, FamilyMemberType.NEUTRAL_DICE));
    }

    /**
     * mi riposiziona nella posizione di partenza i miei familiari
     */
    public void relocateFamilyMembers() {
        Platform.runLater(() -> personalFamilyMembers.forEach(((type, guiFamilyMember) -> {
            if (!personalHBox.getChildren().contains(guiFamilyMember))
                personalHBox.getChildren().add(guiFamilyMember);
        })));
    }

    /**
     * mi sposta il mio familiare nello spazio azione corretto
     * @param actionSpacesType codice spazio azion
     * @param cardType codice torre
     * @param numFloor codice piano
     * @param marketActionType codice mercato
     * @param familyMemberType codice familiare
     */
    public void moveFamilyMember(ActionSpacesType actionSpacesType, CardType cardType, int numFloor, MarketActionType marketActionType, FamilyMemberType familyMemberType) {
        Platform.runLater(() ->{
//            switch (actionSpacesType) {
//                case TOWERS:
//                    towerMap.get(cardType)[numFloor].addFamilyMember(personalFamilyMembers.get(familyMemberType));
//                    break;
//                case MARKET:
//                    marketMap.get(marketActionType).addFamilyMember(personalFamilyMembers.get(familyMemberType));
//                    break;
//                default:
//                    actionSpacesMap.get(actionSpacesType).addFamilyMember(personalFamilyMembers.get(familyMemberType));
//                    break;
//            }
            personalHBox.getChildren().remove(personalFamilyMembers.get(familyMemberType));
        });
    }

    /**
     * mi modifica i punti del giocatore, cioè mi sposta i dischetti relativi a me stesso
     * @param map mappa dei valori
     */
    public void modifyPoints(Map<ResourceType, Integer> map) {
        Platform.runLater(() -> map.forEach(((resourceType, points) -> personalDiscs.get(resourceType).setCurrentPosition(points))));
    }

    /**
     * mi modifica i punti di un avversario cioè mi sposta i dischetti relativi ad un giocatore avversario
     * @param map mappa dei valori
     */
    public void modifyOpponentPoints(Map<ResourceType, Integer> map, int id) {
        Platform.runLater(() -> map.forEach(((resourceType, points) -> opponentDiscs.get(id).get(resourceType).setCurrentPosition(points))));
    }

    /**
     * mi rende visibile l'alert realtivo alla scelta nella fase scomunica
     */
    public void showExcommunicatingAlert() {
        Platform.runLater(ExcommunicationAlert::new);
    }

    /**
     * mi rende visibile l'alert relativo alla scelta di conversione del privilegio
     */
    public void showPrivilegeAlert() {
        Platform.runLater(PrivilegeAlert::new);
    }

    @FXML
    public void endMoveAction(ActionEvent event) throws RemoteException {
        client.endMove();
    }

    @FXML
    public void actionDoAction(ActionEvent event) throws RemoteException {
        int servantsToPay;
        try{
            servantsToPay = Integer.parseInt(servantsToPayTextField.getText());
        }
        catch (NumberFormatException e) {
            servantsToPay = 0;
        }
        MessageAction msg = client.encondingMessageAction();
        if (msg != null)
            client.doAction(msg, servantsToPay);
    }

    @FXML
    public void actionDoNewAction(ActionEvent event) throws RemoteException {
        int servantsToPay;
        try{
            servantsToPay = Integer.parseInt(servantsToPayTextField.getText());
        }
        catch (NumberFormatException e) {
            servantsToPay = 0;
        }
        MessageNewAction msg = client.encondingMessageNewAction();
        if (msg != null)
            client.doNewAction(msg, servantsToPay);
    }

    /**
     * inizializza il tabellone
     * @throws InterruptedException
     */
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
            client.setMessagesController(fxmlLoader.getController());
            personalGridPane.add(messagesServer, 0, 2);
            fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("main/gui/game_view/personal_board_view.fxml"));
            Parent personalBoard = fxmlLoader.load();
            client.setPersonalBoardController(fxmlLoader.getController());
            myPersonalBoardGridPane.add(personalBoard, 0, 0);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        addToolbarDragAndDrop(toolbar1);
        addToolbarDragAndDrop(toolbar2);
    }
}
