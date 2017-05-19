package main.GUI.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import main.client.MainClient;

import javax.swing.text.html.ImageView;
import java.io.IOException;
import java.rmi.NotBoundException;

/**
 * Created by Luca, Andrea on 16/05/2017.
 */
public class LauncherController {
    @FXML
    private ImageView imageLorenzo;
    @FXML
    private TextField userName;
    @FXML
    private PasswordField password;
    @FXML
    private Button buttonLogin;
    @FXML
    private RadioButton RMI,socket;
    private ToggleGroup toggleGroup;

    @FXML
    /**
     * Metodo che gestisce l' evento sul bottone di login, e lancia alert se non si riempie uno dei campi
     * @param actionEvent
     * @throws IOException
     * @throws NotBoundException
     */
    public void submit(ActionEvent actionEvent) throws IOException, NotBoundException {
        if(!checkFields()){
            launchAlert("UserName o password non validi!!");
            return;
        }
        if(socket.isSelected() ) {
            new MainClient(0, userName.getText(), password.getText()).startClient();

        }
        if(RMI.isSelected()) {
            new MainClient(1, userName.getText(), password.getText()).startClient();
        }
        else if(!(socket.isSelected() || RMI.isSelected())) {
            launchAlert("Non hai selezionato il metodo di connessione");
        }
    }

    /**
     * controlla che i due campi userName e password non siano vuoti
     * @return true se entrambi i campi sono pieni, false altrimenti
     */
    private boolean checkFields() {
        if(userName.getText().isEmpty() || password.getText().isEmpty()){
            return false;
        }
        return true;
    }


    /**
     * Metodo che visualizza a schermo un alert se i campi non sono pieni
     * @param s
     */
    private void launchAlert(String s) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(s);
            alert.setTitle("Errore di login");
            alert.show();
        }


    public void showButton(MouseEvent mouseEvent) {
        buttonLogin.setOpacity(1);
    }

    public void hideButton(MouseEvent mouseEvent) {
        buttonLogin.setOpacity(0.45);
    }

    /**
     * Metodo attivato quando il giocatore sceglie un tipo di connessione, rendendo mutua la scelta tra una e l' altra
     * @param actionEvent
     */
    public void select(ActionEvent actionEvent) {
        toggleGroup = new ToggleGroup();
        socket.setToggleGroup(toggleGroup);
        RMI.setToggleGroup(toggleGroup);
    }
}
