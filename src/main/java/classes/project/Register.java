package classes.project;

import com.opencsv.exceptions.CsvValidationException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import java.sql.*;

import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.io.*;
import java.net.URL;
import java.util.concurrent.ThreadLocalRandom;

public class Register implements Initializable {
    @FXML
    private TextField usernameField, passwordField, emailField, confirmEmailField, firstnameField, lastnameField, textField,errorField;
    @FXML
    private Button generateButton, registerButton, eyeButton, eyeButton2;
    private static Stage stg;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image img = null, img2;
        img = new Image(new File("src/main/resources/images/aqefsfh.png").toURI().toString());
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

//        String[] header = {"Username", "First Name", "Last Name", "Email", "Password"};
//        try (PrintWriter printWriter = new PrintWriter(new FileWriter("C:/Users/97252/Desktop/test.csv"))) {
//            printWriter.write("header" + "\n");
//        } catch (IOException e) {
//        }

    }

    public void registerButton_Click(ActionEvent actionEvent) throws IOException, CsvValidationException, NoSuchAlgorithmException, SQLException {
        Encryptor encryptor = new Encryptor();
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckUserExists = null;
        ResultSet resultSet = null;

        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javafx", "root","root");
            psCheckUserExists = connection.prepareStatement("SELECT * FROM users WHERE Username = ?");
            psCheckUserExists.setString(1, usernameField.getText());
            resultSet = psCheckUserExists.executeQuery();

            if(resultSet.isBeforeFirst()){
                errorField.setText("User Already Exists");
            }
            else{
                psInsert = connection.prepareStatement("INSERT INTO users " +
                        "(First_Name, Last_Name, Username, Email, Password) " +
                        "VALUES (?, ?, ?, ?, ?)");

                psInsert.setString(1,firstnameField.getText());
                psInsert.setString(2,lastnameField.getText());
                psInsert.setString(3,usernameField.getText());
                psInsert.setString(4,emailField.getText());
                psInsert.setString(5,encryptor.encryptString(passwordField.getText()));

                psInsert.executeUpdate();



                Main m = new Main();
                m.changeScene("Home.fxml");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            if(resultSet != null){
                resultSet.close();
            }
            if(psCheckUserExists != null){
                psCheckUserExists.close();
            }
            if(psInsert != null){
                psInsert.close();
            }
            if(connection != null){
                connection.close();
            }
        }


//        if (checkField(firstnameField) &&
//                checkField(lastnameField) &&
//                checkField(emailField) &&
//                checkField(confirmEmailField) &&
//                checkField(usernameField) &&
//                checkField(usernameField)) {
//            if ((Objects.equals(emailField.getText(), confirmEmailField.getText()))) {
//                if (validateUserInput()) {
//                    //Validation Passed.
//                    writeToFile();
//                }
//            }
//        }
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

    public boolean validateUserInput() {
        String line = "";
        String splitBy = ",";
        try {
            BufferedReader br = new BufferedReader(new FileReader("data.csv"));
            int counter = 0;
            while ((line = br.readLine()) != null) {
                if (counter == 0) {
                    counter = 1;
                    continue;
                }
                String[] data = line.split(splitBy);
                String result = data[0].replaceAll("^\"|\"$", "");
                String result2 = data[3].replaceAll("^\"|\"$", "");
                if (result.equals(usernameField.getText())) {
                    return false;
                }
                if (result2.equals(emailField.getText())) {
                    return false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public void writeToFile() throws IOException, NoSuchAlgorithmException {
        Encryptor encryptor = new Encryptor();
        CSVWriter writer = new CSVWriter(new FileWriter("data.csv", true));
        String[] record = {usernameField.getText(), firstnameField.getText(), lastnameField.getText(), emailField.getText(), encryptor.encryptString(passwordField.getText())};
        writer.writeNext(record);
        writer.close();
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
