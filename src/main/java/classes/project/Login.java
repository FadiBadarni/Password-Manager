package classes.project;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.Node;


import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.*;


public class Login {
    @FXML
    public TextField usernameField, errorField;
    @FXML
    private PasswordField passwordField;
    private static Stage stg;
    Encryptor encryptor = new Encryptor();

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
                errorField.setText("Incorrect Credentials.");
            } else {
                while (resultSet.next()) {
                    String retrievedPassword = resultSet.getString("password");
                    if (retrievedPassword.equals(encryptor.encryptString(passwordField.getText()))) {
                        Main m = new Main();
                        m.changeScene("FXMLDocument.fxml");
                    } else {
                        errorField.setText("Passwords Mismatch.");
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


    public void gologinButton_Click(ActionEvent actionEvent) throws IOException {
        if (usernameField.getText().length() != 0) {

            Node node = (Node) actionEvent.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            try {
                stage.setUserData(usernameField.getText());
                Main m = new Main();
                m.changeScene("FXMLDocument.fxml");
            } catch (IOException e) {
                System.err.printf("Error: %s%n", e.getMessage());
            }
        }

    }

    public void registerButton_Click(ActionEvent actionEvent) {
    }
}