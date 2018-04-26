package sample.app.Money;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by ahmed mar3y on 22/04/2018.
 */
public class moneyController implements Initializable {
    @FXML
    private HBox hbox1 , lasthbox , tableHbox;

    @FXML
    private TabPane tabPane;

    @FXML
    private Pane infoPane;

    @FXML
    private TreeTableView table;
    @FXML
    private TreeTableColumn Col_phone , Col_Address , Col_name;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // --------------- set size ---------------------------
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        double screenWidth = primaryScreenBounds.getWidth()-180 ;
        hbox1.setPrefWidth(screenWidth);

        tabPane.setPrefWidth(primaryScreenBounds.getWidth() - 185);
        tabPane.setPrefHeight(primaryScreenBounds.getHeight());
        tabPane.setTabMinWidth(screenWidth / 2 );
//
        infoPane.setPrefWidth(screenWidth / 4 +  10 );


        tableHbox.setPrefWidth(screenWidth - infoPane.getPrefWidth() - 40  );

        table.setPrefHeight(primaryScreenBounds.getHeight() - 250);

        lasthbox.setLayoutY(primaryScreenBounds.getHeight() - 150);





    }
}
