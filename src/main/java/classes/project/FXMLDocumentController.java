/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes.project;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.xml.bind.DatatypeConverter;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.KeyPair;
import java.sql.*;
import java.util.HashSet;
import java.util.ResourceBundle;

import static classes.project.Asymmetric.do_RSAEncryption;
import static classes.project.Asymmetric.generateRSAKkeyPair;

public class FXMLDocumentController implements Initializable {
    public Pane addPane;
    public ScrollPane listPane;
    public Pane mainPane;
    public Pane show_pane;
    public ImageView icon_show;
    public Button openLink;
    public Button edit;
    public TextField username_show;
    public TextField password_show;
    public TextField link_show;
    public VBox vbox_add;
    @FXML
    private javafx.scene.control.TextArea passwordHintField;
    @FXML
    private javafx.scene.control.TextField nameField, usernameField, passwordField, loginUrlField;
    @FXML
    private Label infoLabel;
    KeyPair keypair = generateRSAKkeyPair();
    public Button addBT;
    public Button closeButton;
    public Button backButton;
    @FXML
    private Text username;
    @FXML
    private GridPane cardHolder, cardHolder1;
    private static Stage stg;
    ObservableList<CustomerCard> list = FXCollections.observableArrayList();
    boolean refresh = true;
    private String link;

    String x = "C:\\Users\\97252\\Desktop\\Password-Manager\\src\\main\\resources\\images\\ICON";

    String[] icon = {"facebook.png", "gmail.png", "instagram.png"};

    public FXMLDocumentController() throws Exception {
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Image img;
        img = new Image(new File("src/main/resources/images/backArrow.png").toURI().toString());
        ImageView view = new ImageView(img);
        view.setFitHeight(50);
        view.setPreserveRatio(true);
        backButton.setGraphic(view);
        Image img2;
        img2 = new Image(new File("src/main/resources/images/plusIcon.png").toURI().toString());
        ImageView view2 = new ImageView(img2);
        view2.setFitHeight(100);
        view2.setPreserveRatio(true);
        addBT.setGraphic(view2);
        Image img3 = new Image(new File("src/main/resources/images/exitIcon.png").toURI().toString());
        ImageView view3 = new ImageView(img3);
        view3.setFitHeight(60);
        view3.setPreserveRatio(true);
        closeButton.setGraphic(view3);

        closeButton.setStyle("-fx-background-color: rgba(255,255,255,0)");
        backButton.setStyle("-fx-background-color: rgba(255,255,255,0)");

        cardHolder.setAlignment(Pos.CENTER);
        cardHolder.setVgap(30.00);
        cardHolder.setHgap(30.00);
        cardHolder.setStyle("-fx-padding:10px;-fx-border-color:transparent");
        addPane.setVisible(false);
        show_pane.setVisible(false);
        listPane.setVisible(true);
        //  listPane.resize(mainPane.getWidth(), mainPane.getHeight());

        onSearch();
    }

    @FXML
    public void onSearch() {
        cardHolder.getChildren().clear();
        int count = 0, i = 0;
        while (count < list.stream().count()) {
            for (int j = 0; j < 5; j++) {
                cardHolder.add(list.get(count), j, i);
                count++;
                if (count >= list.stream().count())
                    break;
            }
            i++;
        }

    }

    public void onAddButtonClick(ActionEvent actionEvent) throws IOException {
        username_show.setDisable(true);
        password_show.setDisable(true);
        link_show.setDisable(true);
        if (addPane.isVisible()) {
            listPane.setTranslateX(0);
            listPane.setScaleX(1);
            listPane.setScaleY(1);
            show_pane.setVisible(false);

            Image img3 = new Image(new File("src/main/resources/images/plusIcon.png").toURI().toString());
            ImageView view3 = new ImageView(img3);
            view3.setFitHeight(100);
            view3.setPreserveRatio(true);
            addBT.setGraphic(view3);
        } else {
            listPane.setScaleX(0.7);
            listPane.setTranslateX(170);
            listPane.setScaleY(1);
            listPane.setTranslateY(0);
            show_pane.setVisible(false);

            Image img2 = new Image(new File("src/main/resources/images/minusIcon.png").toURI().toString());
            ImageView view2 = new ImageView(img2);
            view2.setFitHeight(100);
            view2.setPreserveRatio(true);
            addBT.setGraphic(view2);
        }
        addPane.setVisible(!addPane.isVisible());


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
            hashSet.clear();
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

        if (refresh) {
            Node node = (Node) mouseEvent.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            String s = (String) stage.getUserData();
            username.setText("Nice To See You! " + s);

            try {
                list.clear();
                int i = 0;
                HashSet<String[]> data = update(s);
                for (String[] text : data) {
                    list.add(new CustomerCard(text[0], text[4], x + text[0] + ".png", text[1], text[2], this));
                }
                onSearch();
            } catch (SQLException | FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            refresh = false;
        }

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
            psInsert.setString(6, username.getText());

            psInsert.executeUpdate();
            refresh = true;

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void show(String appName, String username, String icon, String link, String password) {

        edit.setText("Edit");

        if (show_pane.isVisible()) {
            listPane.setTranslateX(0);
            listPane.setScaleX(1);
            addPane.setVisible(false);
        } else {
            listPane.setScaleX(0.7);
            listPane.setTranslateX(170);
            addPane.setVisible(false);
        }
        show_pane.setVisible(!show_pane.isVisible());

        username_show.setText(username);
        password_show.setText(password);


        try {
            icon_show.setImage(new Image(icon));
        } catch (RuntimeException e) {
            icon_show.setImage(new Image("C:\\Users\\97252\\Desktop\\Password-Manager\\src\\main\\resources\\images\\ICON\\instagram.png"));
        }
        link_show.setText(link);
        this.link = link;

    }

    public void onOpenLinkButtonClick(ActionEvent actionEvent) {
        try {
            Desktop.getDesktop().browse(new URL(link).toURI());
        } catch (IOException | URISyntaxException a) {
            a.printStackTrace();
        }
    }


    public void onEditButtonClick(ActionEvent actionEvent) throws Exception {
        if (edit.getText().equals("Edit")) {
            username_show.setDisable(false);
            password_show.setDisable(false);
            link_show.setDisable(false);
            edit.setText("Save");
        } else {
            byte[] cipherText = do_RSAEncryption(passwordHintField.getText(), keypair.getPrivate());

            try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javafx", "root", "root");
                 PreparedStatement psInsert = connection.prepareStatement("INSERT INTO credentials " +
                         "(application_name, username, password, login_url, password_hint, owner) " +
                         "VALUES (?, ?, ?, ?, ?, ?)")) {

                psInsert.setString(1, "nameField.getText()");
                psInsert.setString(2, username_show.getText());
                psInsert.setString(3, "passwordField.getText()");
                psInsert.setString(4, "loginUrlField.getText()");
                psInsert.setString(5, "DatatypeConverter.printHexBinary(cipherText)");
                psInsert.setString(6, username.getText());

                psInsert.executeUpdate();
                refresh = true;

            } catch (SQLException e) {
                e.printStackTrace();
            }
            username_show.setDisable(true);
            password_show.setDisable(true);
            link_show.setDisable(true);
            edit.setText("Edit");

        }
    }

    public void listicon(String appName, String icon) {
    }

    public void onBackButtonClick(ActionEvent actionEvent) throws IOException {
        Main m = new Main();
        m.changeScene("Main.fxml");
    }
}
