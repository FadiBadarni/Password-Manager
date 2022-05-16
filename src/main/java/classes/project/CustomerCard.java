package classes.project;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Modality;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import static com.sun.javafx.scene.control.skin.Utils.getResource;

public class CustomerCard extends Pane {

    protected final ImageView photo;
    protected final Label appName;


    protected final Label username;




    public CustomerCard(String appName, String UserName, String icon,String link) {

        photo = new ImageView();
        this.appName = new Label();
        username = new Label();


     //   setId(Id + "");
        setPrefHeight(240.0);
        setPrefWidth(240.0);
        setStyle("-fx-background-color:#FFF; -fx-border-radius: 10px; -fx-background-radius: 10px;");

        DropShadow dropShadow = new DropShadow();
        dropShadow.setHeight(3);
        dropShadow.setWidth(3);
        dropShadow.setBlurType(BlurType.TWO_PASS_BOX);
        setEffect(dropShadow);
        photo.setImage(new Image(icon));
        photo.setLayoutX(70);
        photo.setLayoutY(10.0);
        photo.setFitHeight(100);
        photo.setFitWidth(100);
//        photo.setRadius(45.0);
//        photo.setStroke(javafx.scene.paint.Color.valueOf("#d7d7d7"));
//        photo.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);

        this.appName.setAlignment(javafx.geometry.Pos.CENTER);
        this.appName.setContentDisplay(ContentDisplay.CENTER);
        this.appName.setLayoutX(0);
        this.appName.setLayoutY(110.0);
        this.appName.setPrefHeight(26.0);
        this.appName.setPrefWidth(200.0);
        this.appName.setText(appName);
        this.appName.setFont(new Font(17.0));


        this.username.setAlignment(javafx.geometry.Pos.CENTER);
        username.setContentDisplay(ContentDisplay.CENTER);
        username.setLayoutX(0);
        username.setLayoutY(150.0);
        username.setPrefHeight(19.0);
        username.setPrefWidth(115.0);
        username.setText(UserName);
        username.setFont(new Font(13.0));


        setOnMouseClicked(e -> {
            try {
                Desktop.getDesktop().browse(new URL(link).toURI());
            } catch (IOException | URISyntaxException a) {
                a.printStackTrace();
            }
        });

        getChildren().addAll(photo, this.appName, username);

    }
}