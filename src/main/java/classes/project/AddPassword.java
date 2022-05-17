package classes.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javax.xml.bind.DatatypeConverter;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ResourceBundle;

import static classes.project.Asymmetric.*;

public class AddPassword implements Initializable {
    @FXML
    private Button returnButton;
    @FXML
    private TextArea passwordHintField;
    @FXML
    private TextField nameField, usernameField, passwordField, loginUrlField;
    @FXML
    private Label infoLabel;
    KeyPair keypair = generateRSAKkeyPair();

    public AddPassword() throws Exception {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void returnButton_Click(ActionEvent actionEvent) throws IOException {
        Main m = new Main();
        m.changeScene("Home.fxml");
    }

    public void saveButton_Click(ActionEvent actionEvent) throws Exception {

        byte[] cipherText = do_RSAEncryption(passwordHintField.getText(), keypair.getPrivate());

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javafx", "root", "root");
             PreparedStatement psInsert = connection.prepareStatement("INSERT INTO credentials " +
                     "(application_name, username, password, login_url, password_hint, owner) " +
                     "VALUES (?, ?, ?, ?, ?, ?)")) {

            psInsert.setString(1, nameField.getText());
            psInsert.setString(2, usernameField.getText());
            psInsert.setString(3, passwordField.getText());
            psInsert.setString(4, loginUrlField.getText());
            psInsert.setString(5, DatatypeConverter.printHexBinary(cipherText));
            psInsert.setString(6, do_RSADecryption(cipherText, keypair.getPublic()));



            psInsert.executeUpdate();

            Main m = new Main();
            m.changeScene("Home.fxml");

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
