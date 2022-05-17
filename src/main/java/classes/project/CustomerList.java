package classes.project;

import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

public class CustomerList extends Pane {
    protected final ImageView photo;
    protected final Label appName;
    protected final Label username;

    public CustomerList(String appName, String icon, FXMLDocumentController fx) {

        photo = new ImageView();
        this.appName = new Label();
        username = new Label();


        setPrefHeight(70.0);
        setPrefWidth(70.0);
        setStyle("-fx-background-color:#FFF; -fx-border-radius: 10px; -fx-background-radius: 10px;");

        DropShadow dropShadow = new DropShadow();
        dropShadow.setHeight(3);
        dropShadow.setWidth(3);
        dropShadow.setBlurType(BlurType.TWO_PASS_BOX);
        setEffect(dropShadow);
        photo.setImage(new Image(icon));
        photo.setLayoutX(10);
        photo.setLayoutY(5.0);
        photo.setFitHeight(50);
        photo.setFitWidth(50);


        this.appName.setAlignment(javafx.geometry.Pos.CENTER);
        this.appName.setContentDisplay(ContentDisplay.CENTER);
        this.appName.setLayoutX(0);
        this.appName.setLayoutY(55.0);
        this.appName.setPrefHeight(13.0);
        this.appName.setPrefWidth(100.0);
        this.appName.setText(appName);
        this.appName.setFont(new Font(10.0));




        setOnMouseClicked(e -> {
            fx.listicon(appName,icon);
//
        });

        getChildren().addAll(photo, this.appName, username);

    }
}