package main.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import javax.swing.text.html.ImageView;

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

    public void submit(ActionEvent actionEvent) {
        if(socket.isSelected() ) {
            //socketConnection();
        }
        if(RMI.isSelected()) {
            //RMI connection();
        }
        else
            launchAlert("Non hai selezionato il metodo di connessione");


    }

    public void checkPassword() {

    }

    public void checkUserName() {
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
