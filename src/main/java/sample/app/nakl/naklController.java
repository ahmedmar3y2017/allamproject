package sample.app.nakl;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.TreeTableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;

import javax.swing.text.TableView;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by ahmed mar3y on 23/04/2018.
 */
public class naklController implements Initializable {
  @FXML
    private HBox hbox1 , hbox2 , hbox3 , hbox4;

    @FXML
    private TreeTableView table;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

        hbox1.setPrefWidth(primaryScreenBounds.getWidth() - 185 );
        hbox2.setPrefWidth(primaryScreenBounds.getWidth() - 185 );

        hbox3.setPrefWidth(primaryScreenBounds.getWidth() - 185 );
        hbox4.setPrefWidth(primaryScreenBounds.getWidth() - 185 );

        table.setPrefWidth(primaryScreenBounds.getWidth() - 185);
        System.out.println(hbox1.getPrefWidth()+" Hbob2");

    }
}
