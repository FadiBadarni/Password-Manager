package classes.project;

import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class CustomerCard extends Pane {
    protected final ImageView photo;
    protected final Label appName;
    protected final Label username;

    public CustomerCard(String appName, String UserName, String icon, String link, String password, FXMLDocumentController fx) throws FileNotFoundException {

        photo = new ImageView();
        this.appName = new Label();
        username = new Label();

        setPrefHeight(180.0);
        setPrefWidth(180.0);
        setStyle("-fx-background-color:rgba(255,255,255,0); -fx-border-radius: 10px; -fx-background-radius: 10px;");
        this.appName.setStyle("-fx-font-family: \"Berlin Sans FB\"; -fx-font-size: 18px;-fx-text-fill: white;");
        this.username.setStyle("-fx-font-family: \"Berlin Sans FB\"; -fx-font-size: 15px;-fx-text-fill: white;");

        DropShadow dropShadow = new DropShadow();
        dropShadow.setHeight(3);
        dropShadow.setWidth(3);
        dropShadow.setBlurType(BlurType.TWO_PASS_BOX);
        setEffect(dropShadow);
        try {
            photo.setImage(new Image(icon));
        } catch (RuntimeException e) {
            photo.setImage(new Image("C:\\Users\\97252\\Desktop\\Password-Manager\\src\\main\\resources\\images\\ICON\\instagram.png"));
        }

        photo.setLayoutX(30);
        photo.setLayoutY(10.0);
        photo.setFitHeight(120);
        photo.setFitWidth(120);

        this.appName.setAlignment(javafx.geometry.Pos.CENTER);
        this.appName.setContentDisplay(ContentDisplay.CENTER);
        this.appName.setLayoutX(-10);
        this.appName.setLayoutY(130);
        this.appName.setPrefHeight(26.0);
        this.appName.setPrefWidth(200.0);
        this.appName.setText(appName);
        this.appName.setFont(new Font(17.0));

        this.username.setAlignment(javafx.geometry.Pos.CENTER);
        username.setContentDisplay(ContentDisplay.CENTER);
        username.setLayoutX(25);
        username.setLayoutY(155);
        username.setPrefHeight(19.0);
        username.setPrefWidth(115.0);
        username.setText("Username -> " + UserName);
        username.setFont(new Font(13.0));

        setOnMouseClicked(e -> {
            fx.show(appName, UserName, icon, link, password);
        });

        getChildren().addAll(photo, this.appName, username);

    }
}