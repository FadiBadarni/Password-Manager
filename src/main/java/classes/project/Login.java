package classes.project;

import animatefx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ResourceBundle;

public class Login implements Initializable {
    @FXML
    public TextField usernameField, errorField;
    @FXML
    private PasswordField passwordField;
    private static Stage stg;
    @FXML
    private Button createAccountButton;
    @FXML
    private ImageView image;
    @FXML
    private Pane loginPane;
    Encryptor encryptor = new Encryptor();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        image.setVisible(false);
        createAccountButton.setVisible(false);
        AnimationFX animateStart = new SlideInRight(loginPane);
        animateStart.setOnFinished(actionEvent2 -> {
            AnimationFX animateImage = new Flash(image);
            AnimationFX animateRegister = new Flash(createAccountButton);

            animateImage.setOnFinished(actionEvent1 -> {
                createAccountButton.setVisible(true);
                animateRegister.play();
            });
            image.setVisible(true);
            animateImage.play();
        });
        animateStart.play();
    }

    @FXML
    void loginButton_Click(ActionEvent event) throws IOException, NoSuchAlgorithmException, SQLException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javafx", "root", "root");
            preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
            preparedStatement.setString(1, usernameField.getText());
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                AnimationFX animateError = new Shake(errorField);
                animateError.play();
                errorField.setText("User With This Name Does Not Exist.");
            } else {
                while (resultSet.next()) {
                    String retrievedPassword = resultSet.getString("password");
                    if (retrievedPassword.equals(encryptor.encryptString(passwordField.getText()))) {
                        Node node = (Node) event.getSource();
                        Stage stage = (Stage) node.getScene().getWindow();
                        stage.setUserData(usernameField.getText());
                        AnimationFX animateExitEntry = new RotateOut(loginPane);
                        animateExitEntry.setOnFinished(actionEvent2 -> {
                            Main m = new Main();
                            try {
                                m.changeScene("Home.fxml");
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });
                        animateExitEntry.play();
                    } else {
                        AnimationFX animateError = new Shake(errorField);
                        animateError.play();
                        errorField.setText("The Username Or The Password Is Incorrect.");
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    public void returnButton_Click(ActionEvent actionEvent) throws IOException {
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

    public void registerButton_Click(ActionEvent actionEvent) throws IOException {
        Main m = new Main();
        m.changeScene("Register.fxml");
    }


}