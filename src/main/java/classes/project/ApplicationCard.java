package classes.project;

import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;

public class ApplicationCard extends Pane {
    protected final ImageView photo;
    protected final Label appName;
    protected final Label username;

    public ApplicationCard(String appName, String UserName, String icon, String hint, String password, Home fx) throws FileNotFoundException {
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

        icon = icon.substring(0, 1).toUpperCase() + icon.substring(1);
        File imageFile = new File("src/main/resources/images/Icons/" + icon);
        Image image = new Image(imageFile.toURI().toString());
        try {
            photo.setImage(image);
        } catch (RuntimeException e) {
            imageFile = new File("src/main/resources/images/Icons/locked.png");
            image = new Image(imageFile.toURI().toString());
            photo.setImage(image);
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
        username.setPrefWidth(130);
        username.setText("Username - " + UserName);
        username.setFont(new Font(13.0));

        String finalIcon = icon;
        setOnMouseClicked(e -> {
            fx.show(appName, UserName, finalIcon, hint, password);

        });

        getChildren().addAll(photo, this.appName, username);

    }
}