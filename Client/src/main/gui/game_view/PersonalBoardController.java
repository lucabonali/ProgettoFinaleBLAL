package main.gui.game_view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import main.api.types.CardType;
import main.api.types.ResourceType;
import main.gui.AnimationService;

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
    @FXML private GridPane buildingsGridPane;
    @FXML private GridPane territoriesGridPane;

    private GameController gameController;

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
     * @param personalcardsMap mappa delle carte
     */
    public void updateCards(Map<CardType, List<String>> personalcardsMap) {
        Platform.runLater(() -> {
            personalcardsMap.forEach(((cardType, strings) -> {
                strings.forEach((cardName -> {

                }));
            }));
        });
    }

    /**
     * mi setta il gameController principale, in modo che esso possa comunicare
     * @param gameController controller
     */
    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    /**
     * informa che la partita è iniziata e mi inizializza la risorsa relativa alle monete, in funcione
     * dell'id del giocatore passato come parametro
     * @param id ide del giocatore
     */
    public void startGame(int id) {
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

    private void initializeCardsImage(CardType cardType, GridPane gridPane) {
        List<ImageView> list = new ArrayList<>();
        cardsMap.put(cardType, list);
        for (int i=0; i<6; i++) {
            ImageView img = new ImageView();
            list.add(img);
            img.setFitWidth(111);
            img.setFitHeight(128);
            img.setOnMouseEntered(event -> AnimationService.zoomIn(img));
            img.setOnMouseExited(event -> AnimationService.zoomOut(img));
            gridPane.add(img, i, 0);
        }
    }

    public void initialize() {
        initializeCardsImage(CardType.TERRITORY, territoriesGridPane);
        initializeCardsImage(CardType.BUILDING, buildingsGridPane);
        cardsMap.get(CardType.TERRITORY).get(2).setImage(new Image(getClass().getResource("res/cards/ambasciatore"+EXTENSION).toExternalForm()));
        cardsMap.get(CardType.BUILDING).get(5).setImage(new Image(getClass().getResource("res/cards/cava_di_ghiaia"+EXTENSION).toExternalForm()));
        qtaResourceLabelMap.put(ResourceType.COINS, coinsLabel);
        qtaResourceLabelMap.put(ResourceType.WOOD, woodLabel);
        qtaResourceLabelMap.put(ResourceType.STONE, stoneLabel);
        qtaResourceLabelMap.put(ResourceType.SERVANTS, servantsLabel);
    }
}
