package sample.app.AddBank;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import sample.app.Entities.Bank;
import sample.app.Transactions.BankDao.bankDao;
import sample.app.dialogs.dialog;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by ahmed mar3y on 26/04/2018.
 */
public class addBankController implements Initializable {
    @FXML
    private TextField bankName;

    @FXML
    private TextField bankAddress;

    @FXML
    private JFXButton addBank;

    @FXML
    private TextField bankNumber;

    @FXML
    void addBankAction(ActionEvent event) {

        String name = bankName.getText();
        String address = bankAddress.getText();
        String number = bankNumber.getText();

        if (name.trim().isEmpty()
                || address.trim().isEmpty()
                || number.trim().isEmpty()

                ) {
            dialog dialog = new dialog(Alert.AlertType.ERROR, "خطأ", "ادخل جميع البيانات");


        } else {

            Bank bank = new Bank(name, address, number);

            Bank bank1 = bankDao.SaveBank(bank);
            if (bank1 == null) {
                dialog dialog = new dialog(Alert.AlertType.ERROR, "خطأ", "خطا فى الحفظ فى الداتابيز ");


            } else {
                dialog dialog = new dialog(Alert.AlertType.CONFIRMATION, "تم", "تم حفظ البنك بنجاح");

            }


        }


    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
