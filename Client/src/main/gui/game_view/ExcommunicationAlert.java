package main.gui.game_view;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import main.client.AbstractClient;

import java.rmi.RemoteException;

/**
 * @author Luca
 * @author Andrea
 */
public class ExcommunicationAlert extends Alert{
    private AbstractClient client;

    public ExcommunicationAlert() {
        super(AlertType.NONE);
        client = AbstractClient.getInstance();
        createAlert();
    }

    private void createAlert() {
        setTitle("Excommunication Alert");
        setHeaderText("It is the excommunicating turn, what do you want to do?");
        setContentText("Choose your option.");

        ButtonType excommunicationButton = new ButtonType("Excommunicate");
        ButtonType giveSupportButton = new ButtonType("Give Support");

        getButtonTypes().setAll(excommunicationButton, giveSupportButton);
        showAndWait().ifPresent(response -> {
            try {
                if (response == excommunicationButton) {
                    client.excommunicationChoice(true);

                }
                else {
                    client.excommunicationChoice(false);
                }
            }
            catch (RemoteException e) {
                e.printStackTrace();
            }
        });
    }
}
