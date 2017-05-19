package main.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import main.client.MainClient;

import javax.swing.text.html.ImageView;
import java.io.IOException;
import java.rmi.NotBoundException;

/**
 * Created by Luca on 16/05/2017.
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
     * Metodo che gestisce l' evento sul bottone di login,
     * @param actionEvent
     * @throws IOException
     * @throws NotBoundException
     */
    public void submit(ActionEvent actionEvent) throws IOException, NotBoundException {
        checkFields();
        if(socket.isSelected() ) {
            new MainClient(0, userName.getText(), password.getText()).startClient();
        }
        if(RMI.isSelected()) {
            new MainClient(1, userName.getText(), password.getText()).startClient();
        }
        else {
            launchAlert("Non hai selezionato il metodo di connessione");
        }


    }

    private void checkFields() {


    }



    private void launchAlert(String s) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Seleziona RMI o socket per continuare");
            alert.setTitle(s);
            alert.show();
        }


    public void showButton(MouseEvent mouseEvent) {
        buttonLogin.setOpacity(1);
    }

    public void hideButton(MouseEvent mouseEvent) {
        buttonLogin.setOpacity(0.45);
    }

    public void select(ActionEvent actionEvent) {
        toggleGroup = new ToggleGroup();
        socket.setToggleGroup(toggleGroup);
        RMI.setToggleGroup(toggleGroup);
    }
}
