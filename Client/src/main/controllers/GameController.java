package main.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import main.api.exceptions.LorenzoException;
import main.api.exceptions.NewActionException;
import main.clientGame.AbstractClient;

import java.rmi.RemoteException;

/**
 * @author Luca
 * @author Andrea
 */
public class GameController {
    private AbstractClient client;

    @FXML private Button doActionButton,endMoveButton;


    @FXML
    private void doAction() throws RemoteException, LorenzoException {
        client.doAction();
    }

    public void showMessage(String message) {
        Platform.runLater(()->{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(message);
            alert.setTitle("Mossa non valida!!");
            alert.show();
        });
    }

    public void initialize() {
        client = AbstractClient.getInstance();
    }

    public void endMove(ActionEvent actionEvent) throws RemoteException, NewActionException {
        client.endMove();
    }
}
