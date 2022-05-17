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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 *
 * @author SUWIMA
 */
public class FXMLDocumentController implements Initializable {
    public Button addBT;
    public Button Close;
    public Button back;
    @FXML
    private GridPane cardHolder;
    private static Stage stg;
    ObservableList<CustomerCard> list = FXCollections.observableArrayList();

    String x="C:/Users/abada/IdeaProjects/Password-Manager-1/src/main/resources/images/ICON/";
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO


        double r=40;
        addBT.setShape(new Circle(r));
        addBT.setMinSize(2*r, 2*r);
        addBT.setMaxSize(2*r, 2*r);
        Close.setStyle("-fx-background-color: rgba(255,255,255,0)");
        back.setStyle("-fx-background-color: rgba(255,255,255,0)");


        list.add(new CustomerCard( "Gmail", "Username", x+"gmail.png","https:www.gmail.com/"));
       list.add(new CustomerCard( "Facebook", "9654645630",  x+"facebook.png","https://www.facebook.com/"));
        list.add(new CustomerCard( "Skype", "9654645630",  x+"skype.png","https:www.skype.com/"));
        list.add(new CustomerCard( "instagram", "bfgjg",  x+"instagram.png","https://www.instagram.com/"));
        list.add(new CustomerCard( "tumblr", "9654645630",  x+"tumblr.png","https://www.tumblr.com/"));



        cardHolder.setAlignment(Pos.CENTER);
        cardHolder.setVgap(25.00);
        cardHolder.setHgap(25.00);
        cardHolder.setStyle("-fx-padding:10px;-fx-border-color:transparent");

        onSearch();
    }

    @FXML
    public void onSearch() {
        cardHolder.getChildren().clear();
        int count = 0,i=0;
        while (count<list.stream().count())
        {
            for (int j = 0; j < 4; j++) {
                cardHolder.add(list.get(count), j, i);
                count++;
                if(count>=list.stream().count())
                    break;
            }
            i++;
        }

    }

    public void onAddButtonClick(ActionEvent actionEvent) {
        list.add(new CustomerCard( "Gmail", "Username", x+"gmail.png","https:www.gmail.com/"));
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
