package sample.app.AddClient;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import sample.app.Entities.Clients;
import sample.app.Transactions.ClientDao.clientDao;
import sample.app.dialogs.dialog;
import sample.shared.AutoCompleteComboBoxListener;
import sample.shared.Validation.Validation;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by ahmed mar3y on 24/04/2018.
 */
public class addClientController implements Initializable {
    @FXML
    private TextField clientName;

    @FXML
    private TextField phone;

    @FXML
    private TextField address;

    @FXML
    private JFXButton addClient;

    @FXML
    void addClientAction(ActionEvent event) {

        String name = this.clientName.getText();
        String phone = this.phone.getText();
        String address = this.address.getText();

        if (name.trim().isEmpty()
                || phone.trim().isEmpty()
                || address.trim().isEmpty()

                ) {
            dialog dialog = new dialog(Alert.AlertType.ERROR, "خطأ", "ادخل جميع البيانات");


        } else {

            //check Phone
            Clients clientPhone = clientDao.checkClientByPhone(phone);
            if (clientPhone == null) {
                Clients clients = clientDao.SaveClients(new Clients(name, phone, address));
                if (clients == null) {


                    dialog dialog = new dialog(Alert.AlertType.ERROR, "خطأ", "خطا فى الحفظ فى الداتابيز ");


                } else {
                    resetFields();

                    dialog dialog = new dialog(Alert.AlertType.CONFIRMATION, "تم", "تم اضافه العميل");


                }

            } else {
                this.phone.requestFocus();
                dialog dialog = new dialog(Alert.AlertType.ERROR, "خطأ", "العميل موجود من قبل ");

            }

        }


    }

    private void resetFields() {
        this.phone.setText("");
        this.address.setText("");
        this.clientName.setText("");

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Validation.phoneValidation(this.phone);

    }
}
