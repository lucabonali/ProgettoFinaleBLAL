package main.GUI.game_view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import main.CLI.InterfaceController;
import main.GUI.Service;
import main.GUI.game_mode_selection.GameModeSelectionView;
import main.GUI.game_view.component.*;
import main.GUI.game_view.component.action_spaces.*;
import main.GUILauncher;
import main.api.messages.MessageAction;
import main.api.messages.MessageNewAction;
import main.api.types.*;
import main.client.AbstractClient;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
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
public class GUIController implements InterfaceController {
    private static final String EXTENSION = ".png";
    public static final int CARD_HEIGHT = 126;
    public static final int CARD_WIDTH = 108;
    private AbstractClient client;
    private LorenzoAnimation lorenzoAnimation;

    @FXML private ImageView lorenzoCenter;

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

    @FXML private TabPane infoTabPane;

    @FXML private GridPane excomGridPane;

    @FXML private ToolBar toolbar1;
    @FXML private ToolBar toolbar2;

    @FXML private GridPane personalGridPane;
    @FXML private GridPane myPersonalBoardGridPane;
    @FXML private HBox personalHBox;
    @FXML private AnchorPane anchorPane;
    @FXML private GridPane orderGridPane;

    @FXML private TextField servantsToPayTextField;


    private ToggleGroup toggleGroup = new ToggleGroup();

    private Map<Integer, PersonalBoardController> opponentPersonalBoardControllerMap = new HashMap<>();

    private MessagesController messagesController;
    private PersonalBoardController personalBoardController;

    private Map<CardType, GridPane> gridPaneSpacesTowersMap = new HashMap<>();
    private Map<MarketActionType, GridPane> gridPaneSpacesMarketMap = new HashMap<>();


    private Map<ResourceType,PersonalDisc> personalDiscs = new HashMap<>(); //mappa dei dischetti dei punti personali
    private Map<FamilyMemberType,GuiFamilyMember> personalFamilyMembersMap = new HashMap<>(); //mappa dei familiari personali

    private Map<Integer, Map<ResourceType, PersonalDisc>> opponentDiscs = new HashMap<>(); //mappa dei dischetti avversari
    private Map<Integer, Map<FamilyMemberType,GuiFamilyMember>> opponentsFamilyMembersMap = new HashMap<>();
    private Map<Integer, Map<FamilyMemberType, Pane>> paneMap = new HashMap<>();

    private Dice blackDice, whiteDice, orangeDice; //dadi

    //mappe degli spazi azione
    private Map<ActionSpacesType, ActionSpaceInterface> actionSpacesMap = new HashMap<>();
    private Map<CardType, ActionSpaceInterface[]> towerMap = new HashMap<>();
    private Map<MarketActionType, ActionSpaceInterface> marketMap = new HashMap<>();

    //lista delle immagini
    private List<Card> imageList = new ArrayList<>();
    private List<ExcomCard> excomImageList = new ArrayList<>();

    private double xOffset;
    private double yOffset;

    /**
     * mi inizializza gli spazi azione di raccolta e produzione sia larghi
     * che singlo e del palazzo del consiglio
     */
    private void initializeHarvestProduction() {
        //raccolta singolo
        SingleActionSpace harvest = new SingleActionSpace(ActionSpacesType.SINGLE_HARVEST, singleHarvest);
        singleHarvest.add(harvest, 0, 0);
        actionSpacesMap.put(harvest.getType(), harvest);
        //raccolta larga
        LargeActionSpace lHarvest = new LargeActionSpace(ActionSpacesType.LARGE_HARVEST, largeHarvest);
        largeHarvest.add(lHarvest, 0, 0);
        actionSpacesMap.put(lHarvest.getType(), lHarvest);
        //produzione singolo
        SingleActionSpace production = new SingleActionSpace(ActionSpacesType.SINGLE_PRODUCTION, singleProduction);
        singleProduction.add(production, 0, 0);
        actionSpacesMap.put(production.getType(), production);
        //produzione larga
        LargeActionSpace lProduction = new LargeActionSpace(ActionSpacesType.LARGE_PRODUCTION, largeProduction);
        largeProduction.add(lProduction, 0, 0);
        actionSpacesMap.put(lProduction.getType(), lProduction);
        //palazzo del consiglio
        CouncilActionSpace councilActionSpace = new CouncilActionSpace(ActionSpacesType.COUNCIL, council);
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
            GuiFloorActionSpace actionSpace = new GuiFloorActionSpace(ActionSpacesType.TOWERS, cardType, floor, gridCounter, gridPaneSpacesTowersMap.get(cardType));
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
        GuiMarketActionSpace actionSpace = new GuiMarketActionSpace(ActionSpacesType.MARKET, marketType, gridPaneSpacesMarketMap.get(marketType));
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
     * mi inizializza le imagView delle tessere scomunica
     */
    private void initializeImageViewExcomCards() {
        for (int i=0; i<3; i++) {
            ExcomCard excomCard = new ExcomCard(excomGridPane, i+1);
            excomCard.setOnMouseEntered(event -> Service.zoomIn(excomCard));
            excomCard.setOnMouseExited(event -> Service.zoomOut(excomCard));
            excomCard.setPreserveRatio(false);
            excomImageList.add(excomCard);
        }
    }

    /**
     * mi aggiunge il drag and drop della finestra sulla toolbar in alto
     * @param toolbar toolabr della finestra
     */
    private void addToolbarDragAndDrop(ToolBar toolbar) {
        toolbar.setCursor(Cursor.CLOSED_HAND);
        toolbar.setOnMousePressed(event -> {
            xOffset = GUILauncher.getPrimaryStage().getX() -event.getScreenX();
            yOffset = GUILauncher.getPrimaryStage().getY() -event.getScreenY();
            toolbar.setCursor(Cursor.CLOSED_HAND);
        } );

        toolbar.setOnMouseDragged(event -> {
            GUILauncher.getPrimaryStage().setX(event.getScreenX() + xOffset);
            GUILauncher.getPrimaryStage().setY(event.getScreenY() + yOffset);
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
    @Override
    public void setBoardCards(List<String> namesList) {
        Platform.runLater(() ->{
            for (int i=0; i<namesList.size(); i++) {
                imageList.get(i).setImage(new Image(getClass().getResource("res/cards/"+namesList.get(i)+EXTENSION).toExternalForm()), namesList.get(i));
            }
        });
        //rimuovo i familiari dalle torri
        towerMap.forEach(((cardType, actionSpaceInterfaces) -> {
            for (ActionSpaceInterface actionSapce : actionSpaceInterfaces) {
                actionSapce.removeAllFamilyMembers();
            }
        }));
        //rimuovo i familiari dal mercato
        marketMap.forEach(((marketActionType, actionSpaceInterface) -> actionSpaceInterface.removeAllFamilyMembers()));
        //rimuovo i familiari da tutti gli altri spazi azione
        actionSpacesMap.forEach((actionSpacesType, actionSpaceInterface) -> actionSpaceInterface.removeAllFamilyMembers());
    }

    /**
     * mi rimuove le carte che sono già state pescate
     * @param personalCardsMap mappa delle liste dei nomi delle carte che il giocatore possiede
     */
    @Override
    public void removeDrawnCards(Map<CardType, List<String>> personalCardsMap) {
        Platform.runLater(() -> personalCardsMap.forEach(((cardType, namesList) -> namesList.forEach(name -> imageList.forEach(card -> card.remove(name))))));
    }

    /**
     * mi aggiorna la lista delle mi carte
     * @param personalCardsMap mappa delle mie carte
     */
    @Override
    public void updateMyCards(Map<CardType, List<String>> personalCardsMap) {
        personalBoardController.updateCards(personalCardsMap);
    }

    /**
     * mi scomunica
     * @param period periodo della scomunica
     */
    @Override
    public void excommunicate(int id, int period) {
        new ExcommunicatingCube(Service.getColorById(id), excomGridPane, period);
    }

    /**
     * mi fa tornare al menu precedente
     */
    @Override
    public void backToMenu() {
        try {
            GameModeSelectionView.createGameModeSelectionView();
        }
        catch (InterruptedException | IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    /**
     * mi capisce dove deve mettere il familiare di un avversario, e quale
     * @param id id del giocatore che ha mosso
     * @param msgAction messaggio dell'azione
     */
    @Override
    public void updateOpponentFamilyMemberMove(int id, MessageAction msgAction) {
        GuiFamilyMember familyMember = opponentsFamilyMembersMap.get(id).get(msgAction.getFamilyMemberType());
        familyMember.removeMouseClicked();
        familyMember.setDisable(true);
        familyMember.setOpacity(1);
        if (msgAction.getActionSpacesType() == ActionSpacesType.TOWERS)
            towerMap.get(msgAction.getCardType())[msgAction.getNumFloor()]
                    .addFamilyMember(familyMember);
        else if (msgAction.getActionSpacesType() == ActionSpacesType.MARKET)
            marketMap.get(msgAction.getMarketActionType()).addFamilyMember(familyMember);
        else
            actionSpacesMap.get(msgAction.getActionSpacesType()).addFamilyMember(familyMember);
    }

    /**
     * mi setta l'ordine dei giocatori
     * @param orderList lista di interi(id)
     */
    @Override
    public void showOrderList(List<Integer> orderList) {
        Platform.runLater(() -> {
            orderGridPane.getChildren().clear();
            int counter = 0;
            for (Integer id: orderList) {
                PersonalOrderDisc pod = new PersonalOrderDisc(id);
                orderGridPane.add(pod, 0, counter);
                counter++;
            }
        });
    }

    /**
     * mi rende visibili i dadi, e posso tirarli
     */
    @Override
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
            blackDice.setY(700);
            whiteDice.setX(400);
            whiteDice.setY(700);
            orangeDice.setX(500);
            orangeDice.setY(700);
        });
    }

    /**
     * mi setta i dadi, in base ai valori ricevuti dal server
     * @param orange
     * @param white
     * @param black
     */
    @Override
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
    @Override
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
    @Override
    public void createDiscs(int id) {
        personalDiscs.put(ResourceType.VICTORY, new PersonalVictoryDisc(id, anchorPane));
        personalDiscs.put(ResourceType.MILITARY, new PersonalMilitaryDisc(id, anchorPane));
        personalDiscs.put(ResourceType.FAITH, new PersonalFaithDisc(id, anchorPane));
    }

    /**
     * mi genera e posiziona i dischetti dei giocatori avversari
     * @param id id del giocatore avversario
     */
    @Override
    public void createOpponentDiscs(int id) {
        Tab tab = new Tab("opponent " + id);
        Platform.runLater(() -> {
            infoTabPane.getTabs().add(tab);
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("main/GUI/game_view/personal_board_view.fxml"));
                Parent personalBoard = fxmlLoader.load();
                opponentPersonalBoardControllerMap.put(id, fxmlLoader.getController());
                tab.setContent(personalBoard);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        });
        Map<FamilyMemberType, Pane> paneMap = new HashMap<>();
        this.paneMap.put(id, paneMap);
        Map<FamilyMemberType, GuiFamilyMember> familyMap = new HashMap<>();
        familyMap.put(FamilyMemberType.ORANGE_DICE, new GuiFamilyMember(id, FamilyMemberType.ORANGE_DICE));
        familyMap.put(FamilyMemberType.WHITE_DICE, new GuiFamilyMember(id, FamilyMemberType.WHITE_DICE));
        familyMap.put(FamilyMemberType.BLACK_DICE, new GuiFamilyMember(id, FamilyMemberType.BLACK_DICE));
        familyMap.put(FamilyMemberType.NEUTRAL_DICE, new GuiFamilyMember(id, FamilyMemberType.NEUTRAL_DICE));
        opponentsFamilyMembersMap.put(id, familyMap);
        Map<ResourceType, PersonalDisc> map = new HashMap<>();
        map.put(ResourceType.VICTORY, new PersonalVictoryDisc(id, anchorPane));
        map.put(ResourceType.MILITARY, new PersonalMilitaryDisc(id, anchorPane));
        map.put(ResourceType.FAITH, new PersonalFaithDisc(id, anchorPane));
        opponentDiscs.put(id, map);
    }


    /**
     * metodo che mi crea e mi rende visibili i familiari
     * @param id id del giocatore, sulla base del quale si ricava il colore
     */
    @Override
    public void createFamilyMembers(int id){
        personalFamilyMembersMap.put(FamilyMemberType.ORANGE_DICE, new GuiFamilyMember(id, FamilyMemberType.ORANGE_DICE));
        personalFamilyMembersMap.put(FamilyMemberType.BLACK_DICE, new GuiFamilyMember(id, FamilyMemberType.BLACK_DICE));
        personalFamilyMembersMap.put(FamilyMemberType.WHITE_DICE, new GuiFamilyMember(id, FamilyMemberType.WHITE_DICE));
        personalFamilyMembersMap.put(FamilyMemberType.NEUTRAL_DICE, new GuiFamilyMember(id, FamilyMemberType.NEUTRAL_DICE));
        personalFamilyMembersMap.forEach(((familyMemberType, familyMember) -> familyMember.setToggleGroup(toggleGroup)));
    }

    /**
     * mi riposiziona nella posizione di partenza i miei familiari
     */
    @Override
    public void relocateFamilyMembers() {
        Platform.runLater(() -> personalFamilyMembersMap.forEach(((type, guiFamilyMember) -> {
            if (!personalHBox.getChildren().contains(guiFamilyMember)) {
                personalHBox.getChildren().add(guiFamilyMember);
                guiFamilyMember.setToggleGroup(toggleGroup);
                guiFamilyMember.setDisable(false);
            }
        })));
    }

    /**
     * mi sposta il mio familiare nello spazio azione corretto
     * @param actionSpacesType codice spazio azione
     * @param cardType codice torre
     * @param numFloor codice piano
     * @param marketActionType codice mercato
     * @param familyMemberType codice familiare
     */
    @Override
    public void moveFamilyMember(ActionSpacesType actionSpacesType, CardType cardType, int numFloor, MarketActionType marketActionType, FamilyMemberType familyMemberType) {
        Platform.runLater(() ->{
            GuiFamilyMember familyMember = personalFamilyMembersMap.get(familyMemberType);
            if (personalHBox.getChildren().contains(familyMember)) {
                familyMember.removeMouseClicked();
                familyMember.setDisable(true);
                familyMember.setOpacity(1);
                personalHBox.getChildren().remove(familyMember);
                switch (actionSpacesType) {
                    case TOWERS:
                        towerMap.get(cardType)[numFloor].addFamilyMember(familyMember);
                        break;
                    case MARKET:
                        marketMap.get(marketActionType).addFamilyMember(familyMember);
                        break;
                    default:
                        actionSpacesMap.get(actionSpacesType).addFamilyMember(familyMember);
                        break;
                }
            }
        });
    }

    /**
     * notifica sul controller relativo ai messsaggi
     * @param msg
     */
    @Override
    public void notifyMessage(String msg) {
        messagesController.setMessage(msg);
    }

    @Override
    public void showExcomCards(List<String> codeList) {
        Platform.runLater(()->{
            for (int i=0; i<codeList.size(); i++) {
                excomImageList.get(i).setImage(
                        new Image(getClass().getResource("res/excom_cards/" + codeList.get(i) + EXTENSION).toExternalForm()), codeList.get(i));
            }
        });
    }

    @Override
    public void surrender() {
        try {
            client.surrender();
            backToMenu();
        }
        catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * mi modifica i punti del giocatore, cioè mi sposta i dischetti relativi a me stesso
     * @param qtaResourcesMap mappa delle qta delle risorse
     */
    @Override
    public void modifyResources(Map<ResourceType, Integer> qtaResourcesMap) {
        Map<ResourceType, Integer> resourceMap = new HashMap<>();
        Map<ResourceType, Integer> pointMap = new HashMap<>();
        qtaResourcesMap.forEach(((resourceType, integer) -> {
            if (resourceType == ResourceType.COINS || resourceType == ResourceType.WOOD || resourceType == ResourceType.STONE || resourceType == ResourceType.SERVANTS)
                resourceMap.put(resourceType, integer);
            else
                pointMap.put(resourceType,integer);
        }));
        Platform.runLater(() -> pointMap.forEach(((resourceType, points) -> personalDiscs.get(resourceType).setCurrentPosition(points))));
        personalBoardController.modifyResources(resourceMap);
    }

    /**
     * mi modifica i punti di un avversario cioè mi sposta i dischetti relativi ad un giocatore avversario
     * @param map mappa dei valori
     */
    @Override
    public void modifyOpponentPoints(Map<ResourceType, Integer> map, int id) {
        Platform.runLater(() -> map.forEach(((resourceType, points) -> opponentDiscs.get(id).get(resourceType).setCurrentPosition(points))));
    }

    /**
     * mi rende visibile l'alert realtivo alla scelta nella fase scomunica
     */
    @Override
    public void showExcommunicatingAlert() {
        Platform.runLater(ExcommunicationAlert::new);
    }

    /**
     * mi rende visibile l'alert relativo alla scelta di conversione del privilegio
     */
    @Override
    public void showPrivilegeAlert() {
        Platform.runLater(PrivilegeAlert::new);
    }

    @Override
    public void showGameEndedAlert(String msg) {
        Platform.runLater(() -> new GameEndedAlert(msg, this));
    }

    @Override
    public void updateOpponentPersonalBoard(Map<CardType, List<String>> personalcardsMap, Map<ResourceType, Integer> resourcesMap, int id) {
        opponentPersonalBoardControllerMap.get(id).updateCards(personalcardsMap);
        opponentPersonalBoardControllerMap.get(id).modifyResources(resourcesMap);
    }

    @Override
    public void opponentSurrender(int surrenderId) {
        notifyMessage("opponent " + surrenderId + " give up!!");
        opponentDiscs.get(surrenderId).forEach((type, personalDisc) -> personalDisc.remove());
    }

    @FXML
    public void endMoveAction() throws RemoteException {
        client.endMove();
    }

    @FXML
    public void actionDoAction() throws RemoteException {
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
    public void actionDoNewAction() throws RemoteException {
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

    @Override
    public void exit() throws InterruptedException {
        try {
            client.exit();
        }
        catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startGame(int id) {
        createDiscs(id);
        createFamilyMembers(id);
        relocateFamilyMembers();
        personalBoardController.startGame(id);
        messagesController.setMessage("La partita è iniziata");
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
        initializeImageViewExcomCards();
        initializeDices();

//        lorenzoAnimation = new LorenzoAnimation(lorenzoCenter, "Hi, i' m Lorenzo , the Magnificent!!");
//        lorenzoAnimation.startGameAnimation();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("main/GUI/game_view/message_view.fxml"));
            Parent messagesServer = fxmlLoader.load();
            messagesController = fxmlLoader.getController();
            personalGridPane.add(messagesServer, 0, 2);
            fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("main/GUI/game_view/personal_board_view.fxml"));
            Parent personalBoard = fxmlLoader.load();
            personalBoardController = fxmlLoader.getController();
            myPersonalBoardGridPane.add(personalBoard, 0, 0);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        addToolbarDragAndDrop(toolbar1);
        addToolbarDragAndDrop(toolbar2);
    }

}
