package main.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import main.clientGame.AbstractClient;
import main.controllers.game_mode_selection.GameModeSelectionView;

import javax.swing.text.html.ImageView;
import java.io.IOException;
import java.rmi.NotBoundException;

/**
 * @author Luca
 * @author Andrea
 */
public class LoginController {
    @FXML private ImageView imageLorenzo;
    @FXML private TextField userName;
    @FXML private PasswordField password;
    @FXML private Button buttonLogin;
    @FXML private RadioButton RMI,socket;


    /**
     * Metodo che gestisce l' evento sul bottone di login, e lancia alert se non si riempie uno dei campi
     * @param actionEvent
     * @throws IOException
     * @throws NotBoundException
     */
    @FXML
    public void submit(ActionEvent actionEvent) throws IOException, NotBoundException {
        if(!checkFields()){
            launchAlert("Non hai inserito username e password!!!!");
        }
        else{
            if(socket.isSelected() ) {
                AbstractClient clientTmp = AbstractClient.createInstance(false, userName.getText(), password.getText());
                if (clientTmp.login()) {
                    //passi alla schermata successiva
                    clientTmp.setLogged();
                    startGameSelectionView();
                }
                else
                    launchAlert("password errata!!");

            }
            else if(RMI.isSelected()) {
                AbstractClient clientTmp = AbstractClient.createInstance(true, userName.getText(), password.getText());
                if (clientTmp.login()) {
                    //passi alla schermata successiva
                    clientTmp.setLogged();
                    startGameSelectionView();
                }
                else
                    launchAlert("password errata!!");
            }
            else if(!(socket.isSelected() || RMI.isSelected())) {
                launchAlert("Non hai selezionato il metodo di connessione");
            }
        }
    }

    /**
     * controlla che i due campi userName e password non siano vuoti
     * @return true se entrambi i campi sono pieni, false altrimenti
     */
    private boolean checkFields() {
        return !(userName.getText().isEmpty() || password.getText().isEmpty());
    }


    /**
     * Metodo che visualizza a schermo un alert se i campi non sono pieni
     * @param content contenuto del messaggio
     */
    private void launchAlert(String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(content);
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
        ToggleGroup toggleGroup = new ToggleGroup();
        socket.setToggleGroup(toggleGroup);
        RMI.setToggleGroup(toggleGroup);
    }

    public void startGameSelectionView() throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("main/resources/views/game_selection_view.fxml"));
        //Parent window = (Pane) fxmlLoader.load();
        //GameSelectionController gsController = fxmlLoader.getController();
        //AbstractClient.getInstance().setGameSelectionController(gsController); //setto il model
        GameModeSelectionView nextView = new GameModeSelectionView();
        Parent window = nextView.createContent();
        Platform.runLater(()->{
            Stage stage = (Stage) userName.getScene().getWindow();
            Scene scene = new Scene(window, 1000, 620);
            stage.setScene(scene);
            stage.setTitle("GAME SELECTION");
            stage.centerOnScreen();
        });
    }

    public void initialize() {

    }
}
