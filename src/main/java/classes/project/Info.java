package classes.project;

import animatefx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Info implements Initializable {
    private static Stage stg;
    @FXML
    private Button exitButton;
    @FXML
    private Label moderatorLabel;
    @FXML
    private ImageView alonaImage;
    @FXML
    private Pane aboutPane;

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

    public void exitButton_Click(ActionEvent actionEvent) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        alonaImage.setVisible(false);
        moderatorLabel.setVisible(false);
        AnimationFX animateAbout = new Pulse(aboutPane);
        animateAbout.setOnFinished(actionEvent2 -> {
            alonaImage.setVisible(true);
            moderatorLabel.setVisible(true);
            AnimationFX animatePicture = new SlideInLeft(alonaImage);
            AnimationFX animateLabel = new SlideInLeft(moderatorLabel);
            animatePicture.play();
            animateLabel.play();
        });
        animateAbout.play();
    }
}
