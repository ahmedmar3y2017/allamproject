package sample.app.Clients;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.util.Callback;
import sample.app.Entities.Clients;
import sample.app.Transactions.ClientDao.clientDao;
import sample.app.dialogs.dialog;
import sample.app.main.MainController;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by ahmed mar3y on 22/04/2018.
 */
public class clientsController implements Initializable {
    ObservableList<ClientTable> client_data = FXCollections.observableArrayList();
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
    private HBox lasthbox;

    @FXML
    private JFXButton delete;
    private ClientTable clientTable;

    @FXML
    void deleteAction(ActionEvent event) {

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
// set diabled
        this.update.setDisable(true);
        this.save.setDisable(false);

        // --------------- set size ---------------------------
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        hbox1.setPrefWidth(primaryScreenBounds.getWidth() - 185);
        lasthbox.setLayoutY(primaryScreenBounds.getHeight() - 100);
        table.setPrefWidth(primaryScreenBounds.getWidth() - 200);
        table.setPrefHeight(primaryScreenBounds.getHeight() - 200);

        tablePhone.setPrefWidth(table.getPrefWidth() / 3);
        tableAddress.setPrefWidth(table.getPrefWidth() / 3);
        tableName.setPrefWidth(table.getPrefWidth() / 3);


        // select all clients
        List<Clients> clients = clientDao.SelectAllClients();
        if (!clients.isEmpty()) {
            clients.forEach(clients1 -> {

                client_data.add(new ClientTable(clients1.getId(), clients1.getName(), clients1.getAddress(), clients1.getPhone()));

            });


        }

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
