package main.GUI.login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import main.GUI.game_mode_selection.GameModeSelectionView;
import main.GUILauncher;
import main.client.AbstractClient;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.rmi.NotBoundException;

/**
 * @author Luca
 * @author Andrea
 */
public class LoginController {
    @FXML private TextField username;
    @FXML private PasswordField password;
    @FXML private Button buttonLogin;
    @FXML private RadioButton RMI,socket;
    @FXML private Pane rootPane;
    @FXML private ToolBar toolbar;
    @FXML private Button closeButton;

    private double xOffset, yOffset;
    /**
     * Metodo che gestisce l' evento sul bottone di login, e lancia alert se non si riempie uno dei campi
     * @param actionEvent
     * @throws IOException
     * @throws NotBoundException
     */
    @FXML
    public void submit(ActionEvent actionEvent) throws IOException, NotBoundException, InterruptedException, UnsupportedAudioFileException, LineUnavailableException {
        if(!checkFields()){
            launchAlert("Non hai inserito username e password!!!!");
        }
        else{
            if(socket.isSelected() ) {
                AbstractClient clientTmp = AbstractClient.createInstance(false, username.getText(), password.getText());
                if (clientTmp.login()) {
                    //passi alla schermata successiva
                    clientTmp.setLogged();
                    GameModeSelectionView.createGameModeSelectionView();
                }
                else
                    launchAlert("password errata!!");

            }
            else if(RMI.isSelected()) {
                AbstractClient clientTmp = AbstractClient.createInstance(true, username.getText(), password.getText());
                if (clientTmp.login()) {
                    //passi alla schermata successiva
                    clientTmp.setLogged();
                    GameModeSelectionView.createGameModeSelectionView();
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
     * controlla che i due campi username e password non siano vuoti
     * @return true se entrambi i campi sono pieni, false altrimenti
     */
    private boolean checkFields() {
        return !(username.getText().isEmpty() || password.getText().isEmpty());
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

    public void initialize() {
        buttonLogin.setCursor(Cursor.HAND);
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

    public void closeWindow(ActionEvent actionEvent) {
        GUILauncher.getPrimaryStage().close();
    }
}
