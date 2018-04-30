package sample.app.Settings;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.WindowEvent;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class seetingsController implements Initializable {
    @FXML
    Accordion accordion ;

    @FXML
    Pane mainPane , pane2;
    @FXML
    Label label;

    @FXML
    JFXButton logoutBtn , closeBtn;

    @FXML
    HBox hbox1 , hbox2  , hbox3 , hbox4 , hbox5 ;
    @FXML
    private TitledPane pane1,pane22;

    @Override

    public void initialize(URL location, ResourceBundle resources) {
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        double screenWidth = primaryScreenBounds.getWidth() - 186;

        accordion.setPrefWidth(screenWidth);
//        accordion.setPrefHeight(primaryScreenBounds.getHeight());

     double   width = primaryScreenBounds.getWidth() - 250;
            label.setLayoutX( width / 2 );
        logoutBtn.setLayoutX( width/ 2 );
        closeBtn.setLayoutX( width/ 2 );
      pane2.setPrefWidth(screenWidth);

        hbox1.setPrefWidth(primaryScreenBounds.getWidth() - 185);
        hbox2.setPrefWidth(primaryScreenBounds.getWidth() - 185);
        hbox3.setPrefWidth(primaryScreenBounds.getWidth() - 185);
        hbox4.setPrefWidth(primaryScreenBounds.getWidth() - 185);
        hbox5.setPrefWidth(primaryScreenBounds.getWidth() - 185);



        accordion.setExpandedPane(pane1);



    }




    public void logoutAction(javafx.event.ActionEvent actionEvent) {

        // load
        FXMLLoader load = new FXMLLoader();

        Parent v = null;
        try {
            v = load.load(getClass().getResource("/Fxml/Login.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }




    }


    public void closeAction(javafx.event.ActionEvent actionEvent) {
        System.exit(0);
    }


}
