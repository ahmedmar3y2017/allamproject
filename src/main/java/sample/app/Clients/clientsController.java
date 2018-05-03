package sample.app.Clients;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.util.Callback;
import sample.app.Entities.Clients;
import sample.app.Transactions.ClientDao.clientDao;
import sample.app.dialogs.dialog;
import sample.shared.AutoCompleteComboBoxListener;
import sample.shared.Validation.Validation;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Created by ahmed mar3y on 22/04/2018.
 */
public class clientsController implements Initializable {
    ObservableList<ClientTable> client_data = FXCollections.observableArrayList();

    @FXML
    private JFXButton displayAll;

    @FXML
    private ComboBox clientSearchName;
    @FXML
    private JFXButton refresh;

    @FXML
    private Pane kashfHesab;

    @FXML
    private HBox hbox1;

    @FXML
    private JFXButton update;

    @FXML
    private JFXButton save;

    @FXML
    private TextField clientAddress;

    @FXML
    private TextField clientPhone;

    @FXML
    private TextField clientName;

    @FXML
    private TreeTableView<ClientTable> table;

    @FXML
    private TreeTableColumn<ClientTable, String> tableName;

    @FXML
    private TreeTableColumn<ClientTable, String> tableAddress;

    @FXML
    private TreeTableColumn<ClientTable, String> tablePhone;

    @FXML
    private HBox lasthbox, tableHbox;

    @FXML
    private Pane infoPane;

    @FXML
    private JFXButton delete;
    private ClientTable clientTable;
    private List<Clients> clientsList;

    @FXML
    void displayAllAction(ActionEvent event) {
        // select all clients
        List<Clients> clients = clientDao.SelectAllClients();
        if (!clients.isEmpty()) {
            client_data.clear();
            clients.forEach(clients1 -> {

                client_data.add(new ClientTable(clients1.getId(), clients1.getName(), clients1.getAddress(), clients1.getPhone()));

            });
            final TreeItem<ClientTable> Client_root = new RecursiveTreeItem<ClientTable>(client_data, RecursiveTreeObject::getChildren);
            table.setRoot(Client_root);

        }


    }


    @FXML
    void clientNameMouseClick(MouseEvent event) {

        // select All Clients
        clientsList = clientDao.SelectAllClients();
        if (clientsList != null) {

            clientSearchName.getItems().setAll(clientsList.stream().map(ee -> ee.getName()).collect(Collectors.toList()));

        }

    }


    @FXML
    void deleteAction(ActionEvent event) {

        deleteFunction();

    }

    @FXML
    void treeViewKeyPressed(KeyEvent event) {


        // check SupplierSavedIdAccount

        if (event.getCode().equals(KeyCode.DELETE)) {

            deleteFunction();
        }

        //... other keyevents

    }

    private void deleteFunction() {

        RecursiveTreeItem item = (RecursiveTreeItem) table.getSelectionModel().getSelectedItem();

        if (item == null) {
            dialog dialog = new dialog(Alert.AlertType.ERROR, "خطأ", "اختر من الجدول للمسح");


        } else {
            ClientTable ct = (ClientTable) item.getValue();

            Clients clients = clientDao.DeleteClients(ct.id.get());

            if (clients == null) {
                // done
                // delete from table
                boolean t = client_data.remove(ct);

                if (t) {
                    dialog dialog = new dialog(Alert.AlertType.CONFIRMATION, "تم", "تم المسح بنجاح");


                }

            } else {
                dialog dialog = new dialog(Alert.AlertType.ERROR, "خطأ", "خطأ فى الميح من الداتابيز");

            }


        }

    }

    @FXML
    void refreshAction(ActionEvent event) {
        resetfields();
        this.update.setDisable(true);
        this.save.setDisable(false);

        // set search null
        clientSearchName.setValue("");

        // clear table
        client_data.clear();
        final TreeItem<ClientTable> Client_root = new RecursiveTreeItem<ClientTable>(client_data, RecursiveTreeObject::getChildren);
        table.setRoot(Client_root);

    }

    @FXML
    void saveAction(ActionEvent event) {

        String name = clientName.getText();
        String address = clientAddress.getText();
        String phone = clientPhone.getText();
        if (name.trim().isEmpty()
                || address.trim().isEmpty()
                || phone.trim().isEmpty()

                ) {

            dialog dialog = new dialog(Alert.AlertType.ERROR, "خطأ", "ادخل جميع البيانات");


        } else {

            Clients clients = new Clients(name, phone, address);
            Clients clients1 = clientDao.SaveClients(clients);
            if (clients1 == null) {
                dialog dialog = new dialog(Alert.AlertType.ERROR, "خطأ", "خطأ فى الحفظ فى الداتابيز ");
            } else {

                resetfields();

                dialog dialog = new dialog(Alert.AlertType.CONFIRMATION, "تم", "تم اضافه العميل ");

            }


        }


    }

    private void resetfields() {
        this.clientName.setText("");
        this.clientPhone.setText("");
        this.clientAddress.setText("");


    }

    @FXML
    void updateAction(ActionEvent event) {
        if (clientTable != null) {

            String name = clientName.getText();
            String address = clientAddress.getText();
            String phone = clientPhone.getText();
            if (name.trim().isEmpty()
                    || address.trim().isEmpty()
                    || phone.trim().isEmpty()

                    ) {

                dialog dialog = new dialog(Alert.AlertType.ERROR, "خطأ", "ادخل جميع البيانات");


            } else {

                Clients clients = new Clients(name, phone, address);
                clients.setId(clientTable.id.get());
                // client dao update
                Clients clients1 = clientDao.UpdateClients(clientTable.id.get(), clients);

                if (clients1 == null) {
                    dialog dialog = new dialog(Alert.AlertType.ERROR, "خطأ", "خطأ فى الحفظ فى الداتابيز ");
                } else {

                    // remove from table
                    boolean t = client_data.remove(clientTable);
                    if (t) {
                        // insert into table design

                        client_data.add(new ClientTable(clients1.getId(), clients1.getName(), clients1.getAddress(), clients1.getPhone()));
                        final TreeItem<ClientTable> root = new RecursiveTreeItem<ClientTable>(client_data, RecursiveTreeObject::getChildren);
                        table.setRoot(root);
                        table.setShowRoot(false);
// reset values
                        clientTable = null;
                        resetfields();
                        // set disabled
                        update.setDisable(true);
                        save.setDisable(false);

                    }

                }
            }

        } else {


            dialog dialog = new dialog(Alert.AlertType.ERROR, "خطأ", "اختر من الجدول للتعديل");


        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // set disabled
        this.update.setDisable(true);
        this.save.setDisable(false);

        // --------------- set size ---------------------------
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        double screenWidth = primaryScreenBounds.getWidth() - 180;

        kashfHesab.setPrefWidth(screenWidth);
        kashfHesab.setPrefHeight(primaryScreenBounds.getHeight());

        infoPane.setPrefWidth(screenWidth / 4 + 10);


        tableHbox.setPrefWidth(screenWidth - infoPane.getPrefWidth() - 80);

        table.setPrefHeight(primaryScreenBounds.getHeight() - 150);

        tablePhone.setPrefWidth(table.getPrefWidth() / 3 - 10);
        tableAddress.setPrefWidth(table.getPrefWidth() / 3 - 10);
        tableName.setPrefWidth(table.getPrefWidth() / 3);

        lasthbox.setLayoutY(primaryScreenBounds.getHeight() - 90);


        tableName.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ClientTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ClientTable, String> param) {
                return param.getValue().getValue().clientName;
            }
        });
        tableAddress.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ClientTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ClientTable, String> param) {
                return param.getValue().getValue().clientAddress;
            }
        });
        tablePhone.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ClientTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ClientTable, String> param) {
                return param.getValue().getValue().clientPhone;
            }
        });

//        client_data.add(new ClientTable(1, "a", "a", "a"));
        final TreeItem<ClientTable> Client_root = new RecursiveTreeItem<ClientTable>(client_data, RecursiveTreeObject::getChildren);
        table.setRoot(Client_root);
        table.setShowRoot(false);
        // double click Event
        table.setRowFactory(tv -> {
            TreeTableRow<ClientTable> row = new TreeTableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY
                        && event.getClickCount() == 2) {
                    System.out.println("Click 2");
                    selectionUpdateTableAction();
                    update.setDisable(false);
                    save.setDisable(true);
//                    MyDataType clickedRow = row.getItem();
//                    printRow(clickedRow);
                }
            });
            return row;
        });


        // ----------------- combobox search Item Select Event -----------------
        clientSearchName.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String t, String t1) {
                if (!t1.trim().isEmpty()) {

                    client_data.clear();

                    clientsList.stream().filter(clients1 -> {
                        if (clients1.getName().equals(t1)) {

                            return true;
                        }
                        return false;

                    }).forEach(clients1 -> {

                        client_data.add(new ClientTable(clients1.getId(), clients1.getName(), clients1.getAddress(), clients1.getPhone()));


                    });
                    final TreeItem<ClientTable> Client_root = new RecursiveTreeItem<ClientTable>(client_data, RecursiveTreeObject::getChildren);
                    table.setRoot(Client_root);


                }
            }
        });


        // combobox
        this.clientSearchName.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                if (!newPropertyValue) {

                    if (!clientSearchName.getItems().contains(clientSearchName.getValue())) {
                        clientSearchName.getEditor().clear();
                        clientSearchName.setStyle(" -fx-border-width: 120%;   -fx-border-color: red;");

                    }

                }
                if (newPropertyValue) {

                    clientSearchName.setStyle("    -fx-border-color: transparent;");


                }

            }
        });


        new AutoCompleteComboBoxListener<>(this.clientSearchName);
        Validation.phoneValidation(this.clientPhone);


    }

    private void selectionUpdateTableAction() {

        RecursiveTreeItem item = (RecursiveTreeItem) table.getSelectionModel().getSelectedItem();
        if (item != null) {
            ClientTable ct = (ClientTable) item.getValue();
            this.clientTable = ct;
            this.clientName.setText(ct.clientName.get());
            this.clientAddress.setText(ct.clientAddress.get());
            this.clientPhone.setText(ct.clientPhone.get());


        }
    }

    class ClientTable extends RecursiveTreeObject<ClientTable> {

        StringProperty clientName;
        StringProperty clientAddress;
        StringProperty clientPhone;
        IntegerProperty id;

        public ClientTable(int id, String clientName, String clientAddress, String clientPhone) {
            this.id = new SimpleIntegerProperty(id);
            this.clientName = new SimpleStringProperty(clientName);
            this.clientAddress = new SimpleStringProperty(clientAddress);
            this.clientPhone = new SimpleStringProperty(clientPhone);
        }
    }


}
