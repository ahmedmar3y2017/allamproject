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
    private HBox hbox1 ,hbox12, lasthbox ,lasthbox2, tableHbox , tableHbox2;

    @FXML
    private TabPane tabPane;

    @FXML
    private Pane infoPane , infoPane2;

    @FXML
    private TreeTableView table , table2;
    @FXML
    private TreeTableColumn Col_phone , Col_Address , Col_name;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // --------------- set size ---------------------------
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        double screenWidth = primaryScreenBounds.getWidth()-180 ;
        hbox1.setPrefWidth(screenWidth);
        hbox12.setPrefWidth(screenWidth);

        tabPane.setPrefWidth(primaryScreenBounds.getWidth() - 185);
        tabPane.setPrefHeight(primaryScreenBounds.getHeight());
        tabPane.setTabMinWidth(screenWidth / 2 );

        infoPane.setPrefWidth(screenWidth / 4 +  10 );
        infoPane2.setPrefWidth(screenWidth / 4 +  10 );

        tableHbox.setPrefWidth(screenWidth - infoPane.getPrefWidth() - 40  );
        tableHbox2.setPrefWidth(screenWidth - infoPane2.getPrefWidth() - 40  );

        table.setPrefHeight(primaryScreenBounds.getHeight() - 250);
        table2.setPrefHeight(primaryScreenBounds.getHeight() - 250);
//        System.out.println(table.getPrefWidth() + "  "  + table2.getPrefWidth() );
        lasthbox.setLayoutY(primaryScreenBounds.getHeight() - 150);
        lasthbox2.setLayoutY(primaryScreenBounds.getHeight() - 150);




    }
}
