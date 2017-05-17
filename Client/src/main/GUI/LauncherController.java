package main.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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

    public void submit(ActionEvent actionEvent) {
        if((checkPassword() && checkUserName()))
            launchAlert("Campo Password vuoto!!");
        else
            launchAlert("Campo vuoto!!");

    }

    public boolean checkPassword() {
        if(password.getText().equals(null)){
            return false;
        }
        return true;

    }

    public boolean checkUserName() {
        if (!userName.getText().equals(null)) {
            return false;
        }
        return true;

    }
    private void launchAlert(String s) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(s);
            alert.show();
        }


    public void showButton(MouseEvent mouseEvent) {
        buttonLogin.setOpacity(1);
    }

    public void hideButton(MouseEvent mouseEvent) {
        buttonLogin.setOpacity(0.45);
    }
}
