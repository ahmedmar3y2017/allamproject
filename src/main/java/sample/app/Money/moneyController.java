package sample.app.Money;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.*;
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
import sample.app.AddBank.mainAddBank;
import sample.app.Entities.Bank;
import sample.app.Entities.BankAccount;
import sample.app.Transactions.BankAccountDao.bankAccountDao;
import sample.app.Transactions.BankDao.bankDao;
import sample.app.dialogs.dialog;
import sample.shared.AutoCompleteComboBoxListener;
import sample.shared.Validation.Validation;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Created by ahmed mar3y on 22/04/2018.
 */
public class moneyController implements Initializable {
    ObservableList<BankTable> bank_data = FXCollections.observableArrayList();
    @FXML
    private JFXButton refreshBank;
    @FXML
    private TextField bankMoneyTable;
    @FXML
    private TabPane tabPane;

    @FXML
    private Tab tab1;

    @FXML
    private Pane tapPane1;

    @FXML
    private Pane infoPane;

    @FXML
    private JFXRadioButton bankSahb;

    @FXML
    private ToggleGroup Type_of_Bank;

    @FXML
    private JFXRadioButton bankEdaa;

    @FXML
    private DatePicker bankDate;

    @FXML
    private JFXTextArea bankNotes;

    @FXML
    private JFXButton bankSave;

    @FXML
    private JFXButton bankUpdate;

    @FXML
    private ComboBox bankName;

    @FXML
    private HBox hbox1;

    @FXML
    private JFXButton bankSearch;

    @FXML
    private DatePicker bankToSearch;

    @FXML
    private DatePicker bankFromSearch;

    @FXML
    private ComboBox bankNameSearch;

    @FXML
    private HBox lasthbox;

    @FXML
    private TextField bankMoney;

    @FXML
    private JFXButton bankDelete;

    @FXML
    private HBox tableHbox;

    @FXML
    private TreeTableView<BankTable> bankTable;
    @FXML
    private TreeTableColumn<BankTable, String> tableBankName;
    @FXML
    private TreeTableColumn<BankTable, String> tableBankNumber;
    @FXML
    private TreeTableColumn<BankTable, String> tableBankDate;

    @FXML
    private TreeTableColumn<BankTable, String> tableBankType;

    @FXML
    private TreeTableColumn<BankTable, Double> tableNankMoney;

    @FXML
    private TreeTableColumn<BankTable, String> tableBankNotes;

    @FXML
    private Tab tab2;

    @FXML
    private Pane tapPane2;

    @FXML
    private Pane infoPane2;

    @FXML
    private HBox hbox12;

    @FXML
    private ToggleGroup Type_of_Bank1;

    @FXML
    private HBox lasthbox2;

    @FXML
    private JFXButton printBtn1;

    @FXML
    private HBox tableHbox2;

    @FXML
    private TreeTableView<?> table2;

    @FXML
    private Button addBank;
    ToggleGroup groupbank = new ToggleGroup();

    List<Bank> bankList;
    List<String> bank_Names;
    List<Integer> bank_Ids;
    private BankTable bankSelected;

    @FXML
    void refreshBankAction(ActionEvent event) {

        resetFields();
        // set disble
        bankUpdate.setDisable(true);
        bankSave.setDisable(false);

        // clear from table
        bank_data.clear();
        final TreeItem<BankTable> Client_root = new RecursiveTreeItem<BankTable>(bank_data, RecursiveTreeObject::getChildren);
        bankTable.setRoot(Client_root);
    }

    @FXML
    void bankNameMouseClick(MouseEvent event) {
        bankList = bankDao.SelectAllBank();
        if (bankList != null) {
            bank_Names = bankList.stream().map(ee -> ee.getName()).collect(Collectors.toList());
            bank_Ids = bankList.stream().map(ee -> ee.getId()).collect(Collectors.toList());
            bankName.getItems().setAll(bank_Names);
            bankNameSearch.getItems().setAll(bank_Names);


        }


    }

    @FXML
    void addBankAction(ActionEvent event) {

        // open Another Stage
        try {
            mainAddBank mainAddBank = new mainAddBank();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @FXML
    void bankDisplayAllAction(ActionEvent event) {
        // get all Banks Account

        List<BankAccount> bankAccounts = bankAccountDao.SelectAllBankAccount();

        if (!bankAccounts.isEmpty()) {
            bank_data.clear();

            bankAccounts.forEach(ee -> {

                bank_data.add(new BankTable(ee.getId(), ee.getBankid().getName(), ee.getBankid().getNumber(), ee.getDate().toString(), ee.getType(), ee.getMoney(), ee.getNotes()));

            });
            final TreeItem<BankTable> Client_root = new RecursiveTreeItem<BankTable>(bank_data, RecursiveTreeObject::getChildren);
            bankTable.setRoot(Client_root);

        }

    }


    @FXML
    void treeViewKeyPressed(KeyEvent event) {


        // check SupplierSavedIdAccount

        if (event.getCode().equals(KeyCode.DELETE)) {

            deleteFunction();
        }

        //... other keyevents

    }

    @FXML
    void bankDeleteAction(ActionEvent event) {

        deleteFunction();

    }

    private void deleteFunction() {
        RecursiveTreeItem item = (RecursiveTreeItem) bankTable.getSelectionModel().getSelectedItem();

        if (item == null) {
            dialog dialog = new dialog(Alert.AlertType.ERROR, "خطأ", "اختر من الجدول للمسح");


        } else {
            BankTable ct = (BankTable) item.getValue();
            BankAccount bankAccount = bankAccountDao.DeleteBankAccount(ct.id.get());
            if (bankAccount == null) {
                // done
                // delete from table
                boolean t = bank_data.remove(ct);

                if (t) {

                    final TreeItem<BankTable> Client_root = new RecursiveTreeItem<BankTable>(bank_data, RecursiveTreeObject::getChildren);
                    bankTable.setRoot(Client_root);

                    dialog dialog = new dialog(Alert.AlertType.CONFIRMATION, "تم", "تم المسح بنجاح");


                }

            } else {
                dialog dialog = new dialog(Alert.AlertType.ERROR, "خطأ", "خطأ فى الميح من الداتابيز");

            }


        }

    }

    @FXML
    void bankSaveAction(ActionEvent event) {


        // add
        boolean empty = bankName.getSelectionModel().isEmpty();
        boolean type = groupbank.getSelectedToggle().isSelected();
        String money = bankMoney.getText();
        LocalDate date = bankDate.getValue();
        String notes = bankNotes.getText();

        if (empty
                || !type
                || money.trim().isEmpty()
                || date == null

                ) {
            dialog dialog = new dialog(Alert.AlertType.ERROR, "خطأ", "ادخل جميع البيانات");


        } else {
            int bankIndex = this.bankName.getSelectionModel().getSelectedIndex();
            int bankId = bank_Ids.get(bankIndex);

            BankAccount bankAccount = new BankAccount(((RadioButton) groupbank.getSelectedToggle()).getText(),
                    Double.parseDouble(money),
                    Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant()),
                    notes,
                    new Bank(bankId));

            BankAccount bankAccount1 = bankAccountDao.SaveBankAccount(bankAccount);
            if (bankAccount1 == null) {
                dialog dialog = new dialog(Alert.AlertType.ERROR, "خطأ", "خطأ فى الحفظ فى الداتابيز ");


            } else {
                resetFields();
                dialog dialog = new dialog(Alert.AlertType.CONFIRMATION, "تم", "تمت اضافه الحساب  ");


            }

        }


    }

    @FXML
    void bankSearchAction(ActionEvent event) {

    }

    @FXML
    void bankUpdateAction(ActionEvent event) {

        if (bankSelected != null) {


            boolean empty = bankName.getSelectionModel().isEmpty();
            boolean type = groupbank.getSelectedToggle().isSelected();
            String money = bankMoney.getText();
            LocalDate date = bankDate.getValue();
            String notes = bankNotes.getText();

            if (empty
                    || !type
                    || money.trim().isEmpty()
                    || date == null

                    ) {
                dialog dialog = new dialog(Alert.AlertType.ERROR, "خطأ", "ادخل جميع البيانات");


            } else {
                int bankIndex = this.bankName.getSelectionModel().getSelectedIndex();
                int bankId = bank_Ids.get(bankIndex);

                BankAccount bankAccount = new BankAccount(((RadioButton) groupbank.getSelectedToggle()).getText(),
                        Double.parseDouble(money),
                        Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant()),
                        notes,
                        new Bank(bankId));
                bankAccount.setId(bankSelected.id.get());

                BankAccount bankAccount1 = bankAccountDao.UpdateBankAccount(bankSelected.id.get(), bankAccount);

                if (bankAccount1 == null) {
                    dialog dialog = new dialog(Alert.AlertType.ERROR, "خطأ", "خطأ فى التعديل فى الداتابيز ");


                } else {

                    // remove from table
                    boolean t = bank_data.remove(bankSelected);
                    if (t) {
                        // insert into table design
//
//     new SimpleDateFormat("dd-MM-yyyy").format(maintable1.getDate())
                        bank_data.add(new BankTable(bankAccount1.getId(), bankAccount1.getBankid().getName(), bankAccount1.getBankid().getNumber(), new SimpleDateFormat("dd-MM-yyyy").format(bankAccount1.getDate()), bankAccount1.getType(), bankAccount1.getMoney(), bankAccount1.getNotes()));
                        final TreeItem<BankTable> root = new RecursiveTreeItem<BankTable>(bank_data, RecursiveTreeObject::getChildren);
                        bankTable.setRoot(root);
                        bankTable.setShowRoot(false);
// reset values
                        bankSelected = null;
                        resetFields();
                        // set disabled
                        bankUpdate.setDisable(true);
                        bankSave.setDisable(false);


                    }


                }


            }
        } else {
            dialog dialog = new dialog(Alert.AlertType.ERROR, "خطأ", "اختر من الجدول للتعديل");

        }


    }


    // localdate Formatter

    public static final LocalDate LOCAL_DATE(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse(dateString, formatter);
        return localDate;
    }

    public static final LocalDate LOCAL_DATEParse(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(dateString, formatter);
        return localDate;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
// ------------ set gardio Button  ----------------
        bankEdaa.setToggleGroup(groupbank);
        bankSahb.setToggleGroup(groupbank);

        // set disble
        bankUpdate.setDisable(true);
        bankSave.setDisable(false);


// set combobox
        bankList = bankDao.SelectAllBank();
        if (bankList != null) {
            bank_Names = bankList.stream().map(ee -> ee.getName()).collect(Collectors.toList());
            bank_Ids = bankList.stream().map(ee -> ee.getId()).collect(Collectors.toList());
            bankName.getItems().setAll(bank_Names);
            bankNameSearch.getItems().setAll(bank_Names);


        }

        //set date on init
        String newstring = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        bankDate.setValue(LOCAL_DATE(newstring));


        // --------------- set size ---------------------------
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        double screenWidth = primaryScreenBounds.getWidth() - 180;
        hbox1.setPrefWidth(screenWidth);
        hbox12.setPrefWidth(screenWidth);

        tabPane.setPrefWidth(primaryScreenBounds.getWidth() - 185);
        tabPane.setPrefHeight(primaryScreenBounds.getHeight());
        tabPane.setTabMinWidth(screenWidth / 2);

        infoPane.setPrefWidth(screenWidth / 4 + 10);
        infoPane2.setPrefWidth(screenWidth / 4 + 10);

        tableHbox.setPrefWidth(screenWidth - infoPane.getPrefWidth() - 40);
        tableHbox2.setPrefWidth(screenWidth - infoPane2.getPrefWidth() - 40);

        bankTable.setPrefHeight(primaryScreenBounds.getHeight() - 250);
        table2.setPrefHeight(primaryScreenBounds.getHeight() - 250);
//        System.out.println(table.getPrefWidth() + "  "  + table2.getPrefWidth() );
        lasthbox.setLayoutY(primaryScreenBounds.getHeight() - 150);
        lasthbox2.setLayoutY(primaryScreenBounds.getHeight() - 150);


        // ------------------ init table --------------------


        tableBankName.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<BankTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<BankTable, String> param) {
                return param.getValue().getValue().banktableName;
            }
        });
        tableBankNumber.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<BankTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<BankTable, String> param) {
                return param.getValue().getValue().banktableNumber;
            }
        });
        tableBankDate.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<BankTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<BankTable, String> param) {
                return param.getValue().getValue().banktableDate;
            }
        });
        tableBankType.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<BankTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<BankTable, String> param) {
                return param.getValue().getValue().banktableType;
            }
        });
        tableBankNotes.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<BankTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<BankTable, String> param) {
                return param.getValue().getValue().banktableNotes;
            }
        });


        tableNankMoney.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<BankTable, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(TreeTableColumn.CellDataFeatures<BankTable, Double> param) {
                return param.getValue().getValue().banktableMoney.asObject();
            }
        });

        final TreeItem<BankTable> Client_root = new RecursiveTreeItem<BankTable>(bank_data, RecursiveTreeObject::getChildren);
        bankTable.setRoot(Client_root);
        bankTable.setShowRoot(false);


        // double click Event
        bankTable.setRowFactory(tv -> {
            TreeTableRow<BankTable> row = new TreeTableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY
                        && event.getClickCount() == 2) {
                    System.out.println("Click 2");
                    selectionUpdateTableBankAction();
                    bankUpdate.setDisable(false);
                    bankSave.setDisable(true);
//                    MyDataType clickedRow = row.getItem();
//                    printRow(clickedRow);
                }
            });
            return row;
        });


        // --------- format ----------
        this.bankMoney.setTextFormatter(Validation.DoubleValidation());
        this.bankMoneyTable.setTextFormatter(Validation.DoubleValidation());


        // combobox
        this.bankName.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                if (!newPropertyValue) {

                    if (!bankName.getItems().contains(bankName.getValue())) {
                        bankName.getEditor().clear();
                        bankName.setStyle(" -fx-border-width: 120%;   -fx-border-color: red;");

                    }

                }
                if (newPropertyValue) {

                    bankName.setStyle("    -fx-border-color: transparent;");


                }

            }
        });

        // combobox
        this.bankNameSearch.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                if (!newPropertyValue) {

                    if (!bankNameSearch.getItems().contains(bankNameSearch.getValue())) {
                        bankNameSearch.getEditor().clear();
                        bankNameSearch.setStyle(" -fx-border-width: 120%;   -fx-border-color: red;");

                    }

                }
                if (newPropertyValue) {

                    bankNameSearch.setStyle("    -fx-border-color: transparent;");


                }

            }
        });

        new AutoCompleteComboBoxListener<>(this.bankName);
        new AutoCompleteComboBoxListener<>(this.bankNameSearch);


    }

    private void selectionUpdateTableBankAction() {

        RecursiveTreeItem item = (RecursiveTreeItem) bankTable.getSelectionModel().getSelectedItem();
        if (item != null) {
            BankTable ct = (BankTable) item.getValue();
            this.bankSelected = ct;
            this.bankName.setValue(ct.banktableName.get());
// set radio Button
            if (ct.banktableType.get().equals("سحب"))
                bankSahb.setSelected(true);
            else
                bankEdaa.setSelected(true);

            this.bankMoney.setText(ct.banktableMoney.get() + "");
            this.bankDate.setValue(LOCAL_DATEParse(ct.banktableDate.get()));
            this.bankNotes.setText(ct.banktableNotes.get());


        } else {
            dialog dialog = new dialog(Alert.AlertType.ERROR, "خطأ", "اختر من الجدول للتعديل");

        }


    }

    public void resetFields() {

        this.bankName.setValue("");
        this.bankMoney.setText("");
        // set init date
        //set date on init
        String newstring = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        bankDate.setValue(LOCAL_DATE(newstring));
        this.bankNotes.setText("");


    }


    class BankTable extends RecursiveTreeObject<BankTable> {
        StringProperty banktableNotes;
        StringProperty banktableNumber;
        DoubleProperty banktableMoney;
        StringProperty banktableType;
        StringProperty banktableDate;
        StringProperty banktableName;
        IntegerProperty id;

        public BankTable(int id, String banktableName, String banktableNumber, String banktableDate, String banktableType, Double banktableMoney, String banktableNotes) {
            this.id = new SimpleIntegerProperty(id);
            this.banktableType = new SimpleStringProperty(banktableType);
            this.banktableDate = new SimpleStringProperty(banktableDate);
            this.banktableName = new SimpleStringProperty(banktableName);
            this.banktableNotes = new SimpleStringProperty(banktableNotes);
            this.banktableMoney = new SimpleDoubleProperty(banktableMoney);
            this.banktableNumber = new SimpleStringProperty(banktableNumber);

        }
    }


}
