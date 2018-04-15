package sample.app.Tab1;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by ahmed mar3y on 08/04/2018.
 */
public class tab1PageController implements Initializable {

    @FXML
    private ComboBox bankName;

    @FXML
    private Button addTwoBank;

    @FXML
    private JFXRadioButton bankSahb;

    @FXML
    private JFXRadioButton bankEda;

    @FXML
    private TextArea bankNotes;

    @FXML
    private DatePicker bankDate;

    @FXML
    private JFXButton addBank;

    @FXML
    private JFXButton updateBank;

    @FXML
    private JFXTreeTableView bankTable;

    @FXML
    private TextField bankCurrentMoney;

    @FXML
    private JFXButton deleteBank;

    @FXML
    private JFXButton searchBank;

    @FXML
    private ComboBox<?> searchBankName;

    @FXML
    private DatePicker searchToBank;

    @FXML
    private DatePicker searchFromBank;

    @FXML
    private Tab moneyTable;

    @FXML
    private JFXRadioButton moneySahb;

    @FXML
    private JFXRadioButton moneyEda;

    @FXML
    private TextArea moneyNotes;

    @FXML
    private JFXButton addMoney;

    @FXML
    private JFXButton updateMoney;

    @FXML
    private TextField moneyMoney;

    @FXML
    private JFXButton searchMoney;

    @FXML
    private DatePicker searchToMoney;

    @FXML
    private DatePicker searchFromMoney;

    @FXML
    private TextField moneyCurrentMoney;

    @FXML
    private JFXButton deleteMoney;

    @FXML
    void addBankAction(ActionEvent event) {

    }

    @FXML
    void addMoneyAction(ActionEvent event) {

    }

    @FXML
    void addTwoBankAction(ActionEvent event) {

    }

    @FXML
    void deleteBankAction(ActionEvent event) {

    }

    @FXML
    void deleteMoneyAction(ActionEvent event) {

    }

    @FXML
    void searchBankAction(ActionEvent event) {

    }

    @FXML
    void searchMoneyAction(ActionEvent event) {

    }

    @FXML
    void updateBankAction(ActionEvent event) {

    }

    @FXML
    void updateMoneyAction(ActionEvent event) {

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Init");
    }
}
