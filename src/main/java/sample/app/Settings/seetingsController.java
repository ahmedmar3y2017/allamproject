package sample.app.Settings;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import sample.app.Entities.Users;
import sample.app.Transactions.UserDao.userDao;
import sample.app.dialogs.dialog;
import sample.app.login.LoginController;
import sample.app.login.MainStart;

import java.awt.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class seetingsController implements Initializable {
    @FXML
    private Accordion accordion;

    @FXML
    private TitledPane pane1;

    @FXML
    private Pane mainPane;

    @FXML
    private Label label;

    @FXML
    private JFXButton logoutBtn;

    @FXML
    private JFXButton closeBtn;

    @FXML
    private TitledPane pane22;

    @FXML
    private Pane pane2;

    @FXML
    private HBox hbox1;

    @FXML
    private TextField currentPhoneNumber;

    @FXML
    private TextField newPhoneNumber;

    @FXML
    private HBox hbox2;

    @FXML
    private PasswordField currentPassword;

    @FXML
    private HBox hbox3;

    @FXML
    private PasswordField newPassword;

    @FXML
    private HBox hbox4;

    @FXML
    private PasswordField newConfirmPassword;

    @FXML
    private HBox hbox5;

    @FXML
    private JFXButton savePassword;

    @FXML
    private JFXButton savePhone;

    @FXML
    private TitledPane pane3;

    @FXML
    private Pane mainPane2 , infoPane , phonPane;

    @FXML
    private Label label2;

    @FXML
    private JFXButton backup;


    @FXML
    void savePasswordAction(ActionEvent event) {


        String oldPass = currentPassword.getText();
        String newPass = newPassword.getText();
        String confirmNew = newConfirmPassword.getText();
        if (
                oldPass.trim().isEmpty()
                        || newPass.trim().isEmpty()
                        || confirmNew.trim().isEmpty()

                ) {
            dialog dialog = new dialog(Alert.AlertType.ERROR, "خطأ", "ادخل جميع البيانات");


        } else {

            if (!newPass.equalsIgnoreCase(confirmNew)) {

                dialog dialog = new dialog(Alert.AlertType.ERROR, "خطأ", "الرقم السرى القديم لا يطابق الجديد");


            } else {
                Users usersSelected = userDao.SelectUserById(LoginController.idEmployee);

                if (usersSelected != null && usersSelected.getPassword().equalsIgnoreCase(oldPass)) {

                    Users users = userDao.UpdatePassword(LoginController.idEmployee, newPass);
                    if (users == null) {
                        dialog dialog = new dialog(Alert.AlertType.ERROR, "خطأ", "خطأ فى تعديل الرقم السرى");


                    } else {

                        this.currentPassword.setText("");
                        this.newConfirmPassword.setText("");
                        this.newPassword.setText("");

                        dialog dialog = new dialog(Alert.AlertType.CONFIRMATION, "تم", "تم تعديل الرقم السرى");


                    }

                } else {
                    dialog dialog = new dialog(Alert.AlertType.ERROR, "خطأ", "الرقم السرى القديم خطأ");

                }
            }
        }


    }

    @FXML
    void savePhoneAction(ActionEvent event) {


        String currentPhone = currentPhoneNumber.getText();
        String newPhone = newPhoneNumber.getText();
        if (currentPhone.trim().isEmpty()
                || newPhone.trim().isEmpty()) {
            dialog dialog = new dialog(Alert.AlertType.ERROR, "خطأ", "ادخل جميع البيانات");


        } else {
            Users usersSelected = userDao.SelectUserById(LoginController.idEmployee);

            if (usersSelected != null && usersSelected.getPhone().equalsIgnoreCase(currentPhone)) {

                Users users = userDao.UpdatePhone(LoginController.idEmployee, newPhone);


                if (users == null) {
                    dialog dialog = new dialog(Alert.AlertType.ERROR, "خطأ", "خطأ فى تعديل رقم الموبايل");


                } else {

                    this.currentPhoneNumber.setText("");
                    this.newPhoneNumber.setText("");

                    dialog dialog = new dialog(Alert.AlertType.CONFIRMATION, "تم", "تم تعديل رقم الموبايل");


                }

            } else {
                dialog dialog = new dialog(Alert.AlertType.ERROR, "خطأ", "الموبايل القديم خطأ");

            }
        }


    }

    @Override

    public void initialize(URL location, ResourceBundle resources) {
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        double screenWidth = primaryScreenBounds.getWidth() - 186;

        accordion.setPrefWidth(screenWidth);
//        accordion.setPrefHeight(primaryScreenBounds.getHeight());

        double width = primaryScreenBounds.getWidth() - 250;
        phonPane.setPrefWidth(screenWidth / 2 );
        infoPane.setLayoutX(phonPane.getPrefWidth() + 10);
        System.out.println(phonPane.getPrefWidth());
        label.setLayoutX(width / 2);

        label2.setLayoutX((width / 2) - 160);
        logoutBtn.setLayoutX(width / 2);
        backup.setLayoutX((width / 2) - 130);
        closeBtn.setLayoutX(width / 2);
        pane2.setPrefWidth(screenWidth);

//        hbox1.setPrefWidth(primaryScreenBounds.getWidth() - 185);
//        hbox2.setPrefWidth(primaryScreenBounds.getWidth() - 185);
//        hbox3.setPrefWidth(primaryScreenBounds.getWidth() - 185);
//        hbox4.setPrefWidth(primaryScreenBounds.getWidth() - 185);
//        hbox5.setPrefWidth(primaryScreenBounds.getWidth() - 185);


        accordion.setExpandedPane(pane1);


    }


    public void logoutAction(javafx.event.ActionEvent actionEvent) throws IOException {

        // load
//        FXMLLoader load = new FXMLLoader();
//
//        Parent v = null;
//        try {
//            v = load.load(getClass().getResource("/Fxml/Login.fxml"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        ((Stage) this.closeBtn.getScene().getWindow()).close();
        MainStart mainStart = new MainStart();

    }


    public void closeAction(javafx.event.ActionEvent actionEvent) {
        System.exit(0);
    }


}
