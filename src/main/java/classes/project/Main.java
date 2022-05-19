package classes.project;

import animatefx.animation.*;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class Main extends Application implements Initializable {
    private static Stage stg;
    @FXML
    private MediaView video;
    @FXML
    private Pane parentContainer;
    @FXML
    private Button loginButton, registerButton, aboutButton, quitButton, exitButton;
    @FXML
    private ImageView image;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stg = primaryStage;
        primaryStage.setResizable(false);
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Main.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.setFill(Color.TRANSPARENT);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setTitle("Password Manager");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void changeScene(String fxml) throws IOException {
        Parent pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxml)));
        stg.getScene().setRoot(pane);
        stg.sizeToScene();
    }

    public void loginButton_Click(ActionEvent actionEvent) throws IOException {
        AnimationFX animateLogin = new SlideOutRight(parentContainer);
        animateLogin.setOnFinished(actionEvent2 -> {
            Main m = new Main();
            try {
                m.changeScene("Login.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        animateLogin.play();
    }

    public void registerButton_Click(ActionEvent actionEvent) {
        AnimationFX animateExit = new SlideOutRight(parentContainer);
        animateExit.setOnFinished(actionEvent2 -> {
            Main m = new Main();
            try {
                m.changeScene("Register.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        animateExit.play();
    }

    public void aboutButton_Click(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        stage.setResizable(false);
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Info.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.setFill(Color.TRANSPARENT);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    public void quitButton_Click(ActionEvent actionEvent) throws IOException {
        AnimationFX animateExit = new FadeOut(parentContainer);
        animateExit.setOnFinished(actionEvent2 -> {
            System.exit(0);
        });
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        AnimationFX animateEntry = new Wobble(parentContainer);
        loginButton.setVisible(false);
        registerButton.setVisible(false);
        aboutButton.setVisible(false);
        quitButton.setVisible(false);
        exitButton.setVisible(false);
        image.setVisible(false);
        animateEntry.setOnFinished(actionEvent1 -> {
            Image img3 = new Image(new File("src/main/resources/images/exitIcon.png").toURI().toString());
            ImageView view3 = new ImageView(img3);
            view3.setFitHeight(60);
            view3.setPreserveRatio(true);
            exitButton.setGraphic(view3);
            exitButton.setVisible(true);
            AnimationFX animateExitButton = new Pulse(exitButton);
            animateExitButton.play();

            RotateTransition rt = new RotateTransition(Duration.millis(6000), image);
            rt.setByAngle(360);
            rt.setCycleCount(Animation.INDEFINITE);
            rt.setInterpolator(Interpolator.LINEAR);
            rt.play();

            AnimationFX animateLogin = new Flash(loginButton);
            AnimationFX animateRegister = new Flash(registerButton);
            AnimationFX animateInfo = new Flash(aboutButton);
            AnimationFX animateExit = new Flash(quitButton);
            AnimationFX animateImage = new Pulse(image);
            animateLogin.setOnFinished(actionEvent -> {
                animateRegister.setOnFinished(actionEvent2 -> {
                    animateInfo.setOnFinished(actionEvent3 -> {
                        animateExit.setOnFinished(actionEvent4 -> {
                            image.setVisible(true);
                            animateImage.play();
                        });
                        quitButton.setVisible(true);
                        animateExit.play();
                    });
                    aboutButton.setVisible(true);
                    animateInfo.play();
                });
                registerButton.setVisible(true);
                animateRegister.play();
            });
            loginButton.setVisible(true);
            animateLogin.play();

        });
        animateEntry.play();


    }
    public void exitButton_Click(ActionEvent actionEvent) {
        AnimationFX animateExit = new FadeOut(parentContainer);
        animateExit.setOnFinished(actionEvent2 -> {
            System.exit(0);
        });
        animateExit.play();
    }
}
