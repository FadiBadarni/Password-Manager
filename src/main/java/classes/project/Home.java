package classes.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Home implements Initializable {
    private static Stage stg;
    @FXML
    private Button homeButton, quitButton, addPasswordButton, credentialsButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void homeButton_Click(ActionEvent actionEvent) {

    }

    public void addPasswordButton_Click(ActionEvent actionEvent) throws IOException {
        Main m = new Main();
        m.changeScene("AddPassword.fxml");
    }

    public void credentialsButton_Click(ActionEvent actionEvent) {

    }

    public void quitButton_Click(ActionEvent actionEvent) throws IOException {
        Main m = new Main();
        m.changeScene("Main.fxml");
    }


    public void panePressed(MouseEvent mouseEvent) {
        stg = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        Delta.x = stg.getX() - mouseEvent.getScreenX();
        Delta.y = stg.getY() - mouseEvent.getScreenY();
    }

    public void paneDragged(MouseEvent mouseEvent) {
        stg = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stg.setX(Delta.x + mouseEvent.getScreenX());
        stg.setY(Delta.y + mouseEvent.getScreenY());
    }


}
