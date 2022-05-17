/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes.project;

import com.opencsv.exceptions.CsvValidationException;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ResourceBundle;

public class FXMLDocumentController implements Initializable {
    public Button addBT;
    public Button Close;
    public Button back;
    @FXML
    private GridPane cardHolder;
    private static Stage stg;
    ObservableList<CustomerCard> list = FXCollections.observableArrayList();

    String x = "C:\\Users\\97252\\Desktop\\Password-Manager\\src\\main\\resources\\images\\ICON\\";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO


        double r = 40;
        addBT.setShape(new Circle(r));
        addBT.setMinSize(2 * r, 2 * r);
        addBT.setMaxSize(2 * r, 2 * r);
        Close.setStyle("-fx-background-color: rgba(255,255,255,0)");
        back.setStyle("-fx-background-color: rgba(255,255,255,0)");


        list.add(new CustomerCard("Gmail", "Username", x + "gmail.png", "https:www.gmail.com/"));
        list.add(new CustomerCard("Facebook", "9654645630", x + "facebook.png", "https://www.facebook.com/"));
        list.add(new CustomerCard("Skype", "9654645630", x + "skype.png", "https:www.skype.com/"));
        list.add(new CustomerCard("instagram", "bfgjg", x + "instagram.png", "https://www.instagram.com/"));
        list.add(new CustomerCard("tumblr", "9654645630", x + "tumblr.png", "https://www.tumblr.com/"));


        cardHolder.setAlignment(Pos.CENTER);
        cardHolder.setVgap(25.00);
        cardHolder.setHgap(25.00);
        cardHolder.setStyle("-fx-padding:10px;-fx-border-color:transparent");

        onSearch();
    }

    @FXML
    public void onSearch() {
        cardHolder.getChildren().clear();
        int count = 0, i = 0;
        while (count < list.stream().count()) {
            for (int j = 0; j < 4; j++) {
                cardHolder.add(list.get(count), j, i);
                count++;
                if (count >= list.stream().count())
                    break;
            }
            i++;
        }

    }

    public void onAddButtonClick(ActionEvent actionEvent) {
        list.add(new CustomerCard("Gmail", "Username", x + "gmail.png", "https:www.gmail.com/"));
        onSearch();
    }

    public void onBackButtonClick(ActionEvent actionEvent) throws IOException {
        Main m = new Main();
        m.changeScene("Home.fxml");
    }

    public void onCloseButtonClick(ActionEvent actionEvent) {
        Platform.exit();
        System.exit(0);
    }

    public void loadData(ActionEvent actionEvent) throws IOException, CsvValidationException, NoSuchAlgorithmException, SQLException {
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
