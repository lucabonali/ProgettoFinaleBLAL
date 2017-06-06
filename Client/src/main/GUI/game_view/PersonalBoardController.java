package main.GUI.game_view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import main.api.types.CardType;
import main.api.types.ResourceType;
import main.GUI.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Luca
 * @author Andrea
 *
 */
public class PersonalBoardController {
    private static final String EXTENSION = ".png";
    @FXML private Label coinsLabel;
    @FXML private Label woodLabel;
    @FXML private Label stoneLabel;
    @FXML private Label servantsLabel;
    @FXML private Rectangle coinsGroup;
    @FXML private Rectangle woodGroup;
    @FXML private Rectangle stoneGroup;
    @FXML private Rectangle servantsGroup;

    @FXML private AnchorPane rootPane;

    @FXML private GridPane cardsGridPane;
    @FXML private GridPane buildingsGridPane;
    @FXML private GridPane territoriesGridPane;
    @FXML private GridPane charactersGridPane;
    @FXML private GridPane venturesGridPane;

    private GUIController GUIController;

    private Map<CardType, List<ImageView>> cardsMap = new HashMap<>();
    private Map<ResourceType, Label> qtaResourceLabelMap = new HashMap<>();

    /**
     * mi modifica le risorse
     * @param resourceMap mappa delle quantità
     */
    public void modifyResources(Map<ResourceType, Integer> resourceMap) {
        Platform.runLater(() -> {
            resourceMap.forEach(((resourceType, integer) -> qtaResourceLabelMap.get(resourceType).setText(integer+"")));
        });
    }

    /**
     * mi aggiorna le carte che ho nella mia plancia
     * @param newCardsMap mappa delle carte
     */
    public void updateCards(Map<CardType, List<String>> newCardsMap) {
        Platform.runLater(() -> {
            newCardsMap.forEach(((cardType, nameList) -> {
                for (int counter=0; counter<nameList.size(); counter++){
                    cardsMap.get(cardType).get(counter).setImage(new Image(getClass().getResource("res/cards/"+nameList.get(counter)+EXTENSION).toExternalForm()));
                }
            }));
        });
    }

    public void setBackgroundColor(int id) {
        rootPane.setStyle("-fx-background-color: " + Service.getStringColorById(id));
    }

    /**
     * mi setta il GUIController principale, in modo che esso possa comunicare
     * @param GUIController controller
     */
    public void setGUIController(GUIController GUIController) {
        this.GUIController = GUIController;
    }

    /**
     * informa che la partita è iniziata e mi inizializza la risorsa relativa alle monete, in funcione
     * dell'id del giocatore passato come parametro
     * @param username username del giocatore
     * @param id ide del giocatore
     */
    void startGame(int id, String username) {
        setBackgroundColor(id);
        int num = Integer.parseInt(qtaResourceLabelMap.get(ResourceType.COINS).getText()) + id;
        Platform.runLater(() -> qtaResourceLabelMap.get(ResourceType.COINS).setText(num + ""));
    }

    @FXML
    void showResourceValues(MouseEvent event) {
        Rectangle group = (Rectangle) event.getSource();
        if (group == coinsGroup) {
            coinsLabel.setOpacity(1);
        } else if (group == woodGroup) {
            woodLabel.setOpacity(1);
        }
        else if (group == stoneGroup) {
            stoneLabel.setOpacity(1);
        }
        else if (group == servantsGroup){
            servantsLabel.setOpacity(1);
        }
    }

    @FXML
    void hideResourceValue(MouseEvent event) {
        coinsLabel.setOpacity(0);
        woodLabel.setOpacity(0);
        stoneLabel.setOpacity(0);
        servantsLabel.setOpacity(0);
    }


    @FXML
    public void showLabel(MouseEvent mouseEvent) {
        Label lbl = (Label)mouseEvent.getSource();
        lbl.setOpacity(1);
    }

    /**
     * mi ritona la quantità del tipo di risorsa passato come parametro
     * @param type tipo di risorsa
     * @return quantità
     */
    public int getQtaResource(ResourceType type) {
        return Integer.parseInt(qtaResourceLabelMap.get(type).getText());
    }

    private void initializeCardsImage(CardType cardType) {
        List<ImageView> list = new ArrayList<>();
        cardsMap.put(cardType, list);
        for (int column=0; column<6; column++) {
            ImageView img = new ImageView();
            list.add(img);
            img.setFitWidth(112);
            img.setFitHeight(130);
            if (column==0 && cardType != CardType.VENTURES) {
                img.setOnMouseEntered(event -> Service.zoomInSx(img));
                img.setOnMouseExited(event -> Service.zoomOutBoard(img));
            }
            else if (column==5 && cardType != CardType.VENTURES) {
                img.setOnMouseEntered(event -> Service.zoomInDx(img));
                img.setOnMouseExited(event -> Service.zoomOutBoard(img));
            }
            else if (column==0) { //caso ventures
                img.setOnMouseEntered(event -> Service.zoomInUpperSx(img));
                img.setOnMouseExited(event -> Service.zoomOutBoard(img));
            }
            else if (column == 5) { //caso ventures
                img.setOnMouseEntered(event -> Service.zoomInUpperDx(img));
                img.setOnMouseExited(event -> Service.zoomOutBoard(img));
            }
            else if (cardType == CardType.VENTURES) {
                img.setOnMouseEntered(event -> Service.zoomInUpper(img));
                img.setOnMouseExited(event -> Service.zoomOut(img));
            }
            else {
                img.setOnMouseEntered(event -> Service.zoomIn(img));
                img.setOnMouseExited(event -> Service.zoomOut(img));
            }
            int row = 0;
            switch (cardType) {
                case TERRITORY:
                    row = 3;
                    break;
                case CHARACTER:
                    row = 1;
                    break;
                case BUILDING:
                    row = 2;
                    break;
                case VENTURES:
                    row = 0;
                    break;
            }
            cardsGridPane.add(img, column, row);
        }
    }

    public void initialize() {
        initializeCardsImage(CardType.TERRITORY);
        initializeCardsImage(CardType.CHARACTER);
        initializeCardsImage(CardType.BUILDING);
        initializeCardsImage(CardType.VENTURES);
        qtaResourceLabelMap.put(ResourceType.COINS, coinsLabel);
        qtaResourceLabelMap.put(ResourceType.WOOD, woodLabel);
        qtaResourceLabelMap.put(ResourceType.STONE, stoneLabel);
        qtaResourceLabelMap.put(ResourceType.SERVANTS, servantsLabel);
    }
}
