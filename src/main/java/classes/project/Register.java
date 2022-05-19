package classes.project;

import animatefx.animation.AnimationFX;
import animatefx.animation.SlideInRight;
import animatefx.animation.SlideOutLeft;
import animatefx.animation.SlideOutRight;
import com.opencsv.exceptions.CsvValidationException;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;

import java.sql.*;


import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.io.*;
import java.net.URL;
import java.util.concurrent.ThreadLocalRandom;

public class Register implements Initializable {
    @FXML
    private TextField usernameField, passwordField, emailField, confirmEmailField, firstnameField, lastnameField, textField, errorField;
    @FXML
    private Button generateButton, registerButton, eyeButton, eyeButton2;
    private static Stage stg;
    @FXML
    private ImageView image;
    @FXML
    private Pane mainPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        AnimationFX animateEnter = new SlideInRight(mainPane);
        animateEnter.play();

        RotateTransition rt = new RotateTransition(Duration.millis(6000), image);
        rt.setByAngle(360);
        rt.setCycleCount(Animation.INDEFINITE);
        rt.setInterpolator(Interpolator.LINEAR);
        rt.play();

        Image img = null, img2;
        img = new Image(new File("src/main/resources/images/ViewPasswordEye.png").toURI().toString());
        img2 = new Image(new File("src/main/resources/images/hidePasswordIcon.png").toURI().toString());
        ImageView view = new ImageView(img);
        ImageView view2 = new ImageView(img2);
        view.setFitHeight(30);
        view2.setFitHeight(30);
        view.setPreserveRatio(true);
        view2.setPreserveRatio(true);
        eyeButton.setGraphic(view);
        eyeButton2.setGraphic(view2);
        passwordField.textProperty().bindBidirectional(textField.textProperty());
        eyeButton2.setVisible(false);

    }

    public void registerButton_Click(ActionEvent actionEvent) throws IOException, CsvValidationException, NoSuchAlgorithmException, SQLException {
        Encryptor encryptor = new Encryptor();
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckUserExists = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javafx", "root", "root");
            psCheckUserExists = connection.prepareStatement("SELECT * FROM users WHERE Username = ?");
            psCheckUserExists.setString(1, usernameField.getText());
            resultSet = psCheckUserExists.executeQuery();
            if (resultSet.isBeforeFirst()) {
                errorField.setText("Username Already Exists");
            } else if (checkField(firstnameField) && checkField(lastnameField) && checkField(emailField) && checkField(passwordField) && Objects.equals(confirmEmailField.getText(), emailField.getText())) {
                psInsert = connection.prepareStatement("INSERT INTO users " + "(First_Name, Last_Name, Username, Email, Password) " + "VALUES (?, ?, ?, ?, ?)");

                psInsert.setString(1, firstnameField.getText());
                psInsert.setString(2, lastnameField.getText());
                psInsert.setString(3, usernameField.getText());
                psInsert.setString(4, emailField.getText());
                psInsert.setString(5, encryptor.encryptString(passwordField.getText()));

                psInsert.executeUpdate();

                Node node = (Node) actionEvent.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                stage.setUserData(usernameField.getText());
                Main m = new Main();
                m.changeScene("Home.fxml");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (psCheckUserExists != null) {
                psCheckUserExists.close();
            }
            if (psInsert != null) {
                psInsert.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    public boolean checkField(TextField field) {
        if (field.getText().length() == 0) {
            field.setStyle("-fx-border-color: red ; -fx-border-width: 2px");
            new animatefx.animation.RubberBand(field).play();
            return false;
        } else {
            field.setStyle(null);
            return true;
        }
    }

    public void generateButton_Click(ActionEvent actionEvent) {
        int randomNum = ThreadLocalRandom.current().nextInt(2, 5);
        CharacterRule LCR = new CharacterRule(EnglishCharacterData.LowerCase);
        LCR.setNumberOfCharacters(randomNum);
        CharacterRule UCR = new CharacterRule(EnglishCharacterData.UpperCase);
        UCR.setNumberOfCharacters(randomNum);
        CharacterRule DR = new CharacterRule(EnglishCharacterData.Digit);
        DR.setNumberOfCharacters(randomNum);
        CharacterRule SR = new CharacterRule(EnglishCharacterData.Special);
        SR.setNumberOfCharacters(randomNum);
        PasswordGenerator passGen = new PasswordGenerator();
        String password = passGen.generatePassword(randomNum * 4, SR, LCR, UCR, DR);
        passwordField.setText(password);
    }

    public void eyeButton_Click(ActionEvent actionEvent) {
        textField.toFront();
        eyeButton2.toFront();
        eyeButton2.setVisible(true);
    }

    public void eyeButton2_Click(ActionEvent actionEvent) {
        passwordField.toFront();
        eyeButton.toFront();
        eyeButton.setVisible(true);
    }

    public void returnButton_Click(ActionEvent actionEvent) throws IOException {
        AnimationFX animateReturn = new SlideOutLeft(mainPane);
        animateReturn.setOnFinished(actionEvent2 -> {
            Main m = new Main();
            try {
                m.changeScene("Main.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        animateReturn.play();
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
