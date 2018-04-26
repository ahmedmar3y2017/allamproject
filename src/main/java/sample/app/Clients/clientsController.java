package sample.app.Clients;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by ahmed mar3y on 22/04/2018.
 */
public class clientsController implements Initializable {


    @FXML
    private HBox hbox1 , lasthbox;

    @FXML
    private TreeTableView table;
    @FXML
    private TreeTableColumn Col_phone , Col_Address , Col_name;


    @Override
    public void initialize(URL location, ResourceBundle resources) {


        // --------------- set size ---------------------------
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        hbox1.setPrefWidth(primaryScreenBounds.getWidth() - 185);
        lasthbox.setLayoutY(primaryScreenBounds.getHeight() - 100);
        table.setPrefWidth(primaryScreenBounds.getWidth() - 200);
        table.setPrefHeight(primaryScreenBounds.getHeight() - 200);

        Col_phone.setPrefWidth(table.getPrefWidth() / 3 );
        Col_Address.setPrefWidth(table.getPrefWidth() / 3 );
        Col_name.setPrefWidth(table.getPrefWidth() / 3 );



    }
}
