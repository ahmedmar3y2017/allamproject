package sample.app.Settings;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import sample.app.Entities.Users;
import sample.app.Transactions.UserDao.userDao;
import sample.app.dialogs.dialog;
import sample.app.login.LoginController;
import sample.app.login.MainStart;
import sample.shared.Validation.Validation;

import javax.swing.*;
import java.awt.*;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;


public class seetingsController implements Initializable {
    // **************************************
    ObservableList<userTable> user_data = FXCollections.observableArrayList();
    @FXML
    private JFXButton refreshTable;
    @FXML
    private TreeTableView<userTable> tableUser;

    @FXML
    private TreeTableColumn<userTable, String> tableUserName;

    @FXML
    private TreeTableColumn<userTable, String> tableMobile;

    @FXML
    private TreeTableColumn<userTable, String> tablePassword;

    @FXML
    private JFXButton deleteUser;

    @FXML
    private JFXButton updateUser;

    @FXML
    private JFXButton addUser;

    @FXML
    private TextField userName;

    @FXML
    private TextField userMobile;

    @FXML
    private PasswordField userPassword;


    // *-**************************************
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
    private Pane mainPane2, infoPane, phonPane ,tablePane , userPane;

    @FXML
    private Label label2;

    @FXML
    private JFXButton backup;
    userTable userSelected;

    @FXML
    void refreshTableAction(ActionEvent event) {
        resetFields();
        this.updateUser.setDisable(true);
        this.addUser.setDisable(false);
    }

    @FXML
    void addUserAction(ActionEvent event) {

        String name = userName.getText();
        String phone = userMobile.getText();
        String pass = userPassword.getText();

        if (name.trim().isEmpty()
                || phone.trim().isEmpty()
                || pass.trim().isEmpty()
                ) {
            dialog dialog = new dialog(Alert.AlertType.ERROR, "خطأ", "ادخل جميع البيانات");


        } else {

            Users users = new Users(name, phone, pass);

            Users users1 = userDao.SaveUsers(users);
            if (users1 == null) {
                dialog dialog = new dialog(Alert.AlertType.ERROR, "خطأ", "خطأ فى الحفظ فى الداتابيز ");


            } else {

                //add to table
                user_data.add(new userTable(users1.getId(), users1.getName(), users1.getPhone(), users1.getPassword()));


                final TreeItem<userTable> Client_root = new RecursiveTreeItem<userTable>(user_data, RecursiveTreeObject::getChildren);
                tableUser.setRoot(Client_root);


                resetFields();
                dialog dialog = new dialog(Alert.AlertType.CONFIRMATION, "تم", "تمت الاضافه  ");


            }


        }


    }

    public void resetFields() {
        this.userPassword.setText("");
        this.userMobile.setText("");
        this.userName.setText("");
    }

    @FXML
    void UserkeyEnterEvent(KeyEvent event) {
        if (event.getCode().equals(KeyCode.DELETE)) {

            deleteUserFunction();
        }

    }

    @FXML
    void deleteUserAction(ActionEvent event) {
        deleteUserFunction();
    }

    public void deleteUserFunction() {

        RecursiveTreeItem item = (RecursiveTreeItem) tableUser.getSelectionModel().getSelectedItem();

        if (item == null) {
            dialog dialog = new dialog(Alert.AlertType.ERROR, "خطأ", "اختر من الجدول للمسح");


        } else {
            userTable ct = (userTable) item.getValue();

            Users users = userDao.UpdateActive(ct.getId());
            if (users == null) {

                boolean t = user_data.remove(ct);

                if (t) {

                    final TreeItem<userTable> Client_root = new RecursiveTreeItem<userTable>(user_data, RecursiveTreeObject::getChildren);
                    tableUser.setRoot(Client_root);

                    dialog dialog = new dialog(Alert.AlertType.CONFIRMATION, "تم", "تم المسح بنجاح");

                }

            }


        }

    }

    @FXML
    void updateUserAction(ActionEvent event) {


        if (userSelected != null) {


            String name = userName.getText();
            String phone = userMobile.getText();
            String pass = userPassword.getText();

            if (name.trim().isEmpty()
                    || phone.trim().isEmpty()
                    || pass.trim().isEmpty()
                    ) {
                dialog dialog = new dialog(Alert.AlertType.ERROR, "خطأ", "ادخل جميع البيانات");


            } else {

                Users users = new Users(name, phone, pass);
                users.setId(userSelected.getId());
                users.setActive(true);
                users.setType("user");

                Users users1 = userDao.UpdateUsers(userSelected.getId(), users);
                if (users1 == null) {
                    dialog dialog = new dialog(Alert.AlertType.ERROR, "خطأ", "خطأ فى التعديل فى الداتابيز ");


                } else {

                    boolean t = user_data.remove(userSelected);
                    if (t) {

                        user_data.add(new userTable(users1.getId(), users1.getName(), users1.getPhone(), users1.getPassword()));


                        final TreeItem<userTable> Client_root = new RecursiveTreeItem<userTable>(user_data, RecursiveTreeObject::getChildren);
                        tableUser.setRoot(Client_root);

                        resetFields();
                        updateUser.setDisable(true);
                        addUser.setDisable(false);


                    }

                }
            }


        } else {
            dialog dialog = new dialog(Alert.AlertType.ERROR, "خطأ", "اختر من الجدول للتعديل");


        }


    }

    @FXML
    void backupAction(ActionEvent event) {
        try {
            dataBackup();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } catch (URISyntaxException ex) {
            ex.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void dataBackup() throws IOException, InterruptedException, URISyntaxException {
        String dbName = "allam";
        String dbUser = "root";
        String dbPass = "root";
        JFrame parentFrame = new JFrame();
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to save");
        int userSelection = fileChooser.showSaveDialog(parentFrame);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String jarDir = fileToSave.getAbsolutePath();
            String executeCmd = "C:\\Program Files\\MySQL\\MySQL Server 5.7\\bin\\mysqldump -u " + dbUser + " -h localhost --port=3306" + " -p" + dbPass
                    + " " + dbName + " -r " + jarDir + ".sql\"";
            System.out.println(executeCmd);
            Process runtimeProcess = Runtime.getRuntime().exec(executeCmd);
            int processComplete = runtimeProcess.waitFor();
            System.out.println(processComplete);
        }
    }

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
        // set disabled
        this.updateUser.setDisable(true);
        this.addUser.setDisable(false);

        // validate Numbers
        Validation.phoneValidation(this.currentPhoneNumber);
        Validation.phoneValidation(this.newPhoneNumber);
        Validation.phoneValidation(this.userMobile);

        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        double screenWidth = primaryScreenBounds.getWidth() - 186;

        accordion.setPrefWidth(screenWidth);
//        accordion.setPrefHeight(primaryScreenBounds.getHeight());

        double width = primaryScreenBounds.getWidth() - 250;
        phonPane.setPrefWidth(screenWidth / 2);
        infoPane.setLayoutX(phonPane.getPrefWidth() + 10);

        userPane.setPrefWidth(screenWidth / 2);
        tablePane.setLayoutX(userPane.getPrefWidth() + 10);

        tableUser.setPrefWidth(screenWidth -  userPane.getPrefWidth() -20 );
        tableUserName.setPrefWidth(tableUser.getPrefWidth() / 3 );
        tableMobile.setPrefWidth(tableUser.getPrefWidth() / 3 );
        tablePassword.setPrefWidth(tableUser.getPrefWidth() / 3 );

        infoPane.setLayoutX(phonPane.getPrefWidth() + 10);
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


        // --------------------------------- init table user ---------------------------
        tableUserName.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<userTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<userTable, String> param) {
                return param.getValue().getValue().username;
            }
        });
        tableMobile.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<userTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<userTable, String> param) {
                return param.getValue().getValue().phone;
            }
        });
        tablePassword.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<userTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<userTable, String> param) {
                return param.getValue().getValue().password;
            }
        });

        //display All into table
        java.util.List<Users> usersList = userDao.SelectAllUsers();

        if (!usersList.isEmpty()) {
            usersList.stream().filter(users -> {

                if (users.getId() != LoginController.idEmployee) {

                    return true;
                }

                return false;
            }).collect(Collectors.toList()).forEach(users -> {
                user_data.add(new userTable(users.getId(), users.getName(), users.getPhone(), users.getPassword()));


            });

        }


        final TreeItem<userTable> Client_root = new RecursiveTreeItem<userTable>(user_data, RecursiveTreeObject::getChildren);
        tableUser.setRoot(Client_root);
        tableUser.setShowRoot(false);

        // double click Event
        tableUser.setRowFactory(tv -> {
            TreeTableRow<userTable> row = new TreeTableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY
                        && event.getClickCount() == 2) {
                    System.out.println("Click 2");
                    selectionUpdateAction();
                    updateUser.setDisable(false);
                    addUser.setDisable(true);
//                    MyDataType clickedRow = row.getItem();
//                    printRow(clickedRow);
                }
            });
            return row;
        });
    }

    public void selectionUpdateAction() {


        RecursiveTreeItem item = (RecursiveTreeItem) tableUser.getSelectionModel().getSelectedItem();
        if (item != null) {
            userTable ct = (userTable) item.getValue();
            this.userSelected = ct;
            this.userName.setText(ct.getUsername());
            this.userMobile.setText(ct.getPhone());
            this.userPassword.setText(ct.getPassword());


        } else {
            dialog dialog = new dialog(Alert.AlertType.ERROR, "خطأ", "اختر من الجدول للتعديل");

        }

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


    class userTable extends RecursiveTreeObject<userTable> {
        IntegerProperty id;

        StringProperty username;
        StringProperty phone;
        StringProperty password;

        public userTable(int id, String username, String phone, String password) {
            this.id = new SimpleIntegerProperty(id);
            this.username = new SimpleStringProperty(username);
            this.phone = new SimpleStringProperty(phone);
            this.password = new SimpleStringProperty(password);


        }


        public int getId() {
            return id.get();
        }

        public IntegerProperty idProperty() {
            return id;
        }

        public void setId(int id) {
            this.id.set(id);
        }

        public String getUsername() {
            return username.get();
        }

        public StringProperty usernameProperty() {
            return username;
        }

        public void setUsername(String username) {
            this.username.set(username);
        }

        public String getPhone() {
            return phone.get();
        }

        public StringProperty phoneProperty() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone.set(phone);
        }

        public String getPassword() {
            return password.get();
        }

        public StringProperty passwordProperty() {
            return password;
        }

        public void setPassword(String password) {
            this.password.set(password);
        }
    }

}
