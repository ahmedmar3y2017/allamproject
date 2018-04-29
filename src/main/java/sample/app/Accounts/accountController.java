package sample.app.Accounts;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by ahmed mar3y on 22/04/2018.
 */
public class accountController implements Initializable {

    @FXML
    private TextField discount;

    @FXML
    private TextField nawlon;

    @FXML
    private DatePicker date;

    @FXML
    private Button addClient;

    @FXML
    private ComboBox clientName;

    @FXML
    private HBox hbox1 , hbox2 , hbox3 ,hbox4;

    @FXML
    private JFXButton update;

    @FXML
    private JFXButton save;

    @FXML
    private TextField mezan;

    @FXML
    private TextField weight;

    @FXML
    private TextField toCity;

    @FXML
    private JFXButton updateTable;

    @FXML
    private JFXButton delete;

    @FXML
    private Pane lastHbox;

    @FXML
    private TreeTableView table;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // --------------- set size ---------------------------
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

        hbox1.setPrefWidth(primaryScreenBounds.getWidth() - 185);
        hbox2.setPrefWidth(primaryScreenBounds.getWidth() - 185);
        hbox3.setPrefWidth(primaryScreenBounds.getWidth() - 185);
        hbox4.setPrefWidth(primaryScreenBounds.getWidth() - 185);
        table.setPrefWidth(primaryScreenBounds.getWidth() - 200);

        lastHbox.setLayoutY(primaryScreenBounds.getHeight() - 75);
        table.setPrefHeight(primaryScreenBounds.getHeight() - 300);


    }
}
