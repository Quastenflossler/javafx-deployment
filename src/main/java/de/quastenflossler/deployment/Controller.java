package de.quastenflossler.deployment;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import javax.inject.Named;

@Named(value = Controller.RESOURCE_NAME)
public class Controller {

    public static final String RESOURCE_NAME = "Controller";

    @FXML
    public TextField friendTextField;

    public void handleMagicAction() {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Some magic happened here");
        alert.setHeaderText(null);

        String message = friendTextField.getText();
        message = message + " is awesome! :)";
        alert.setContentText(message);

        alert.showAndWait();
    }
}
