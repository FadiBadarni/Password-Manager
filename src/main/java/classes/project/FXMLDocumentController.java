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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.ResourceBundle;

public class FXMLDocumentController implements Initializable {
    public Button addBT;
    public Button Close;
    public Button back;
    @FXML
    private Text username;
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

    public void onAddButtonClick(ActionEvent actionEvent) throws IOException {

        Stage stage = new Stage();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AddPassword.fxml")));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void onBackButtonClick(ActionEvent actionEvent) throws IOException {
        Main m = new Main();
        m.changeScene("Home.fxml");
    }

    public void onCloseButtonClick(ActionEvent actionEvent) {
        Platform.exit();
        System.exit(0);
    }

    public HashSet<String[]> update(String user) throws SQLException {

        HashSet<String[]> hashSet = new HashSet<>();
        String[] s = new String[5];
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javafx", "root", "root");
            preparedStatement = connection.prepareStatement("SELECT * FROM  credentials WHERE owner = ?");
            preparedStatement.setString(1, user);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                //errorField.setText("Incorrect Credentials.");
            } else {
                while (resultSet.next()) {
                    s = new String[5];
                    s[0] = resultSet.getString("application_name");
                    s[1] = resultSet.getString("login_url");
                    s[2] = resultSet.getString("password");
                    s[3] = resultSet.getString("password_hint");
                    s[4] = resultSet.getString("username");
                    hashSet.add(s);
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
        return hashSet;
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
    public void onMouseMoved(MouseEvent mouseEvent) {

        Node node = (Node) mouseEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        String s=(String)stage.getUserData();
        username.setText(s);

        try {
            HashSet<String[]> data=  update(s);
            for(String[] text:data)
            {
                list.add(new CustomerCard( text[0], text[4], x+"gmail.png",text[1]));
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    public void onClose_AddButtonClick(ActionEvent actionEvent) {
    }

    public void saveButton_Click(ActionEvent actionEvent) {
    }

}
