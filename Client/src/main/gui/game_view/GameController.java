package main.gui.game_view;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import main.api.types.CardType;
import main.clientGame.AbstractClient;

import java.util.HashMap;
import java.util.List;
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

    private Map<CardType,String[]> cards = new HashMap<>();

    private void initializeTower(GridPane tower, List<String> cards) {
        for (int i=0; i<4; i++) {
            ImageView img = new ImageView();
            //immagini 90x110
            img.setFitWidth(90);
            img.setFitHeight(110);
            tower.add(img, 0, i);
        }
    }

    private ObjectProperty<ImageView> getImage(String cardName) {
        Image image = new Image(getClass().getResource("res/cards/" + cardName + ".png").toExternalForm());

        return new SimpleObjectProperty<>(new ImageView(image));
    }

    public void initialize() {
        client = AbstractClient.getInstance();
        cards.put(CardType.TERRITORY, new String[4]);
        cards.put(CardType.CHARACTER, new String[4]);
        cards.put(CardType.BUILDING, new String[4]);
        cards.put(CardType.VENTURES, new String[4]);
    }
}
