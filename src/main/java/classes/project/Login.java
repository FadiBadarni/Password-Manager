package classes.project;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;

public class Login {
    @FXML
    public TextField usernameField, errorField;
    @FXML
    private PasswordField passwordField;
    private static Stage stg;
    File file = new File("data.csv");
    HashMap<String, String> loginInfo = new HashMap<>();
    Encryptor encryptor = new Encryptor();

    @FXML
    void loginButton_Click(ActionEvent event) throws IOException, NoSuchAlgorithmException, SQLException {
//        String password = getPassword();
//        updateLoginUsernamesAndPasswords();
//        String encryptedPassword = encryptor.encryptString(passwordField.getText());
//
//        if (encryptor.encryptString(password).equals(encryptedPassword)) {
//            Main m = new Main();
//            m.changeScene("Home.fxml");
//        } else {
//            System.out.println("Error login!");
//        }

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
                    System.out.println(resultSet.getString("password"));
                    if (retrievedPassword.equals(encryptor.encryptString(passwordField.getText()))) {
                        Main m = new Main();
                        m.changeScene("Home.fxml");
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

    private String getPassword() {
        return passwordField.getText();
    }

    @FXML
    void registerButton_Click(ActionEvent event) throws IOException, NoSuchAlgorithmException {
        writeToFile();
    }

    private void updateLoginUsernamesAndPasswords() throws IOException {
        Scanner scanner = new Scanner(file);
        loginInfo.clear();
        loginInfo = new HashMap<>();
        while (scanner.hasNext()) {
            String[] usernameAndPassword = scanner.nextLine().split(",");
            loginInfo.put(usernameAndPassword[0], usernameAndPassword[4]);
        }
    }

    private void writeToFile() throws IOException, NoSuchAlgorithmException {
        String username = usernameField.getText();
        String password = getPassword();
        BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
        writer.write(username + "," + encryptor.encryptString(password) + "\n");
        writer.close();
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


}