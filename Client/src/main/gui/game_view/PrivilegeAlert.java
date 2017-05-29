package main.gui.game_view;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import main.api.types.ResourceType;
import main.client.AbstractClient;

import java.rmi.RemoteException;

/**
 * @author Luca
 * @author Andrea
 */
public class PrivilegeAlert extends Alert{
    private AbstractClient client;

    public PrivilegeAlert() {
        super(Alert.AlertType.NONE);
        client = AbstractClient.getInstance();
        createAlert();
    }

    private void createAlert() {
        setTitle("Privilege Alert");
        setHeaderText("You've got a privilege, on what you want to convert?");
        setContentText("Choose your option.");

        ButtonType faithButton = new ButtonType("Faith");
        ButtonType coinsButton = new ButtonType("Two Coins");
        ButtonType militaryButton = new ButtonType("Two Military Points");
        ButtonType servantsButton = new ButtonType("Two Servants");
        //manca la conversione in un legno e una pietra

        getButtonTypes().setAll(servantsButton, coinsButton, militaryButton, faithButton);
        showAndWait().ifPresent(response -> {
            try {
                if (response == servantsButton) {
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
