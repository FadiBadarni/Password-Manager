package classes.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddPassword implements Initializable {
    @FXML
    private Button returnButton;
    @FXML
    private TextArea passwordHintField;
    @FXML
    private TextField nameField,usernameField,passwordField,loginUrlField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void returnButton_Click(ActionEvent actionEvent) throws IOException {
        Main m = new Main();
        m.changeScene("Home.fxml");
    }

    public void saveButton_Click(ActionEvent actionEvent) {

    }
}
