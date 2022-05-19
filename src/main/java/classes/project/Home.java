package classes.project;

import animatefx.animation.*;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.*;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;

public class Home implements Initializable {
    public Pane addPane, mainPane, show_pane;
    public ScrollPane listPane;
    public ImageView icon_show, image;
    public Button openBrowserButton, editButton, closeButton, addBT, backButton, saveEditButton;
    @FXML
    private javafx.scene.control.TextArea passwordHintField;
    @FXML
    private javafx.scene.control.TextField nameField, usernameField, passwordField, username_show, password_show, hintField;
    @FXML
    private Label nameLabel;
    @FXML
    private Text username;
    @FXML
    private GridPane cardHolder;
    private static Stage stg;
    ObservableList<ApplicationCard> list = FXCollections.observableArrayList();
    boolean refresh = true;
    private String link;

    public Home() {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        RotateTransition rt = new RotateTransition(Duration.millis(6500), image);
        rt.setByAngle(360);
        rt.setCycleCount(Animation.INDEFINITE);
        rt.setInterpolator(Interpolator.LINEAR);
        rt.play();

        addPane.setVisible(false);
        show_pane.setVisible(false);
        listPane.setVisible(true);
        editButton.setVisible(false);
        openBrowserButton.setVisible(false);
        saveEditButton.setVisible(false);

        AnimationFX animateExit = new RotateInDownRight(mainPane);
        animateExit.setOnFinished(actionEvent2 -> {
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


            onSearch();
        });
        animateExit.play();


    }

    @FXML
    public void onSearch() {
        cardHolder.getChildren().clear();
        int count = 0, i = 0;
        while (count < list.stream().count()) {
            AnimationFX animateCards = new JackInTheBox(cardHolder);
            animateCards.play();
            for (int j = 0; j < 5; j++) {
                cardHolder.add(list.get(count), j, i);
                count++;
                if (count >= list.stream().count()) break;
            }
            i++;
        }
    }

    public void onAddButtonClick(ActionEvent actionEvent) throws IOException {
        username_show.setDisable(true);
        password_show.setDisable(true);
        hintField.setDisable(true);
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
        AnimationFX animateAddButton = new Tada(addBT);
        AnimationFX animateAddPane = new FadeIn(addPane);
        addPane.setVisible(!addPane.isVisible());
        animateAddButton.play();
        animateAddPane.play();
    }

    public void onCloseButtonClick(ActionEvent actionEvent) {
        AnimationFX animateExit = new FadeOut(mainPane);
        animateExit.setOnFinished(actionEvent2 -> {
            System.exit(0);
        });
        animateExit.play();
    }

    public HashSet<String[]> update(String user) throws SQLException {
        HashSet<String[]> hashSet = new HashSet<>();
        String[] s;
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
            } else {
                while (resultSet.next()) {
                    s = new String[5];
                    s[0] = resultSet.getString("application_name");
                    s[1] = resultSet.getString("password");
                    s[2] = resultSet.getString("password_hint");
                    s[3] = resultSet.getString("username");
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
            username.setText(s);
            try {
                list.clear();
                int i = 0;
                HashSet<String[]> data = update(s);
                for (String[] text : data) {
                    list.add(new ApplicationCard(text[0], text[3], text[0] + ".png", text[2], text[1], this));
                }
                onSearch();
            } catch (SQLException | FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            refresh = false;
        }
    }

    public void saveButton_Click(ActionEvent actionEvent) {
        if (passwordField.getText().length() < 8) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please Set A Longer Password");
            alert.showAndWait();
        }
        if (usernameField.getText().length() < 4) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please Set A Longer Password");
            alert.showAndWait();
        } else {
            String encryptedPassword = AES.encrypt(passwordField.getText());
            try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javafx", "root", "root"); PreparedStatement psInsert = connection.prepareStatement("INSERT INTO credentials " + "(application_name, username, password, password_hint, owner) " + "VALUES (?, ?, ?, ?, ?)")) {

                psInsert.setString(1, nameField.getText());
                psInsert.setString(2, usernameField.getText());
                psInsert.setString(3, encryptedPassword);
                psInsert.setString(4, passwordHintField.getText());
                psInsert.setString(5, username.getText());

                psInsert.executeUpdate();
                refresh = true;

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void show(String appName, String username, String icon, String hint, String password) {
        AnimationFX animateShowPane = new Tada(show_pane);
        animateShowPane.play();
        openBrowserButton.setVisible(true);
        if (show_pane.isVisible()) {
            listPane.setTranslateX(0);
            listPane.setScaleX(1);
            addPane.setVisible(false);

            editButton.setVisible(false);
            openBrowserButton.setVisible(false);
        } else {
            listPane.setScaleX(0.7);
            listPane.setTranslateX(170);
            addPane.setVisible(false);
            username_show.setDisable(true);
            password_show.setDisable(true);
            hintField.setDisable(true);
        }
        show_pane.setVisible(!show_pane.isVisible());
        editButton.setVisible(true);
        openBrowserButton.setVisible(true);

        username_show.setText(username);
        password_show.setText(AES.decrypt(password));
        nameLabel.setText(appName);
        hintField.setText(hint);


        icon = icon.substring(0, 1).toUpperCase() + icon.substring(1);
        File imageFile = new File("src/main/resources/images/Icons/" + icon);
        Image image = new Image(imageFile.toURI().toString());
        try {
            icon_show.setImage(image);
        } catch (RuntimeException e) {
            icon_show.setImage(new Image("src/main/resources/images/Icons/locked.png"));
        }
        link = "www." + appName + ".com";

    }

    public void onOpenLinkButtonClick(ActionEvent actionEvent) {
        try {
            Desktop.getDesktop().browse(new URL("http://" + link).toURI());
        } catch (IOException | URISyntaxException a) {
            a.printStackTrace();
        }
    }

    public void onEditButtonClick(ActionEvent actionEvent) {
        password_show.setDisable(false);
        hintField.setDisable(false);
        saveEditButton.setVisible(true);
    }

    public void onBackButtonClick(ActionEvent actionEvent) throws IOException {
        Main m = new Main();
        m.changeScene("Main.fxml");
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


    public void saveEditButton_Click(ActionEvent actionEvent) {
        String encryptedPassword = AES.encrypt(password_show.getText());
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javafx", "root", "root"); PreparedStatement psInsert = connection.prepareStatement("UPDATE credentials SET password_hint = ?, password = ? WHERE username = ?")) {
            psInsert.setString(1, hintField.getText());
            psInsert.setString(2, encryptedPassword);
            psInsert.setString(3, username_show.getText());

            psInsert.executeUpdate();
            refresh = true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        saveEditButton.setVisible(false);
    }
}
