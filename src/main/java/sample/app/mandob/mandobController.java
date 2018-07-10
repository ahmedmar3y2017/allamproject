package sample.app.mandob;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class mandobController implements Initializable {

    @FXML
    private HBox hbox1;

    @FXML
    private TextField ohda;

    @FXML
    private TextField masrof;

    @FXML
    private DatePicker date;

    @FXML
    private HBox hbox2;

    @FXML
    private JFXButton add;

    @FXML
    private JFXButton update;

    @FXML
    private JFXButton refresh;

    @FXML
    private HBox hbox3;

    @FXML
    private DatePicker datesearch;

    @FXML
    private JFXButton search;

    @FXML
    private JFXButton showToday;

    @FXML
    private HBox tableHbox;

    @FXML
    private TreeTableView<?> table;

    @FXML
    private TreeTableColumn<?, ?> tableDate;

    @FXML
    private TreeTableColumn<?, ?> tableOhda;

    @FXML
    private TreeTableColumn<?, ?> tableMasrof;

    @FXML
    private TreeTableColumn<?, ?> tableRest;

    @FXML
    private HBox lastHbox;

    @FXML
    private JFXButton delete;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void addAction(ActionEvent event) {

    }

    @FXML
    void deleteAction(ActionEvent event) {

    }

    @FXML
    void refreshAction(ActionEvent event) {

    }

    @FXML
    void searchAction(ActionEvent event) {

    }

    @FXML
    void showTodayAction(ActionEvent event) {

    }

    @FXML
    void treeViewKeyPressed(KeyEvent event) {

    }

    @FXML
    void updateAction(ActionEvent event) {

    }


}
