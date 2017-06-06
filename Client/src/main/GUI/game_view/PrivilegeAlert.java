package main.GUI.game_view;

import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.stage.StageStyle;
import main.api.types.ResourceType;
import main.client.AbstractClient;

import java.rmi.RemoteException;

/**
 * @author Luca
 * @author Andrea
 */
public class PrivilegeAlert extends Alert{
    private AbstractClient client;
    private double WIDTH = 1000, HEGHT = 100;
    private double xOffset;
    private double yOffset;

    public PrivilegeAlert() {
        super(Alert.AlertType.NONE);
        client = AbstractClient.getInstance();
        createAlert();
    }

    private void createAlert() {
        setTitle("PRIVILEGE ALERT");
        setHeaderText("You've got a privilege, on what you want to convert?");
        setContentText("Choose your option.");
        initStyle(StageStyle.UNDECORATED);
        setResizable(false);

        DialogPane pane = getDialogPane();
        pane.getStylesheets().add(getClass().getResource("res/style.css").toExternalForm());
        pane.setId("privilegeAlert");
        pane.setPrefSize(WIDTH, HEGHT);
        pane.setCursor(Cursor.HAND);

        //drag and drop
        pane.setCursor(Cursor.CLOSED_HAND);
        pane.setOnMousePressed(event -> {
            xOffset = getX() -event.getScreenX();
            yOffset = getY() -event.getScreenY();
            pane.setCursor(Cursor.CLOSED_HAND);
        } );

        pane.setOnMouseDragged(event -> {
            setX(event.getScreenX() + xOffset);
            setY(event.getScreenY() + yOffset);
        });

        ButtonType woodStoneButton = new ButtonType("One Wood One Stone");
        ButtonType faithButton = new ButtonType("Faith");
        ButtonType coinsButton = new ButtonType("Two Coins");
        ButtonType militaryButton = new ButtonType("Two Military Points");
        ButtonType servantsButton = new ButtonType("Two Servants");
        //manca la conversione in un legno e una pietra

        getButtonTypes().setAll(woodStoneButton, servantsButton, coinsButton, militaryButton, faithButton);
        showAndWait().ifPresent(response -> {
            try {
                if (response == woodStoneButton) {
                    client.convertPrivilege(1, ResourceType.WOOD);
                    client.convertPrivilege(1, ResourceType.STONE);
                }
                else if (response == servantsButton) {
                    client.convertPrivilege(2, ResourceType.SERVANTS);
                }
                else if (response == coinsButton){
                    client.convertPrivilege(2, ResourceType.COINS);
                }
                else if (response == militaryButton) {
                    client.convertPrivilege(2, ResourceType.MILITARY);
                }
                else if (response == faithButton) {
                    client.convertPrivilege(1, ResourceType.FAITH);
                }
            }
            catch (RemoteException e) {
                e.printStackTrace();
            }
        });
    }
}
