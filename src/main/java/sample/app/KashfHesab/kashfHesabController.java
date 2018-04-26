package sample.app.KashfHesab;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.TreeTableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by ahmed mar3y on 22/04/2018.
 */
public class kashfHesabController implements Initializable {

    @FXML
    private HBox hbox1 , lasthbox;

    @FXML
    private Pane kashfHesab;

    @FXML
    TreeTableView table;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // --------------- set size ---------------------------
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        hbox1.setPrefWidth(primaryScreenBounds.getWidth() - 185);
        table.setPrefWidth(primaryScreenBounds.getWidth() - 200);

        double kashfHesabWid = primaryScreenBounds.getWidth() - 360 ;
//        System.out.println("Kashfe hsab width"  + kashfHesabWid);
        table.setPrefHeight( primaryScreenBounds.getHeight() - 200 ) ;
        lasthbox.setLayoutX( kashfHesabWid/ 2 );
        lasthbox.setLayoutY( primaryScreenBounds.getHeight() - 100);
    }
    @FXML
    void printAction(){}
}
