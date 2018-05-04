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
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import sample.app.AddBank.mainAddBank;
import sample.app.Entities.Bank;
import sample.app.Entities.BankAccount;
import sample.app.Entities.Safe;
import sample.app.Entities.Users;
import sample.app.Transactions.BankAccountDao.bankAccountDao;
import sample.app.Transactions.BankDao.bankDao;
import sample.app.Transactions.DBConnection;
import sample.app.Transactions.SafeDao.safeDao;
import sample.app.dialogs.dialog;
import sample.app.login.LoginController;
import sample.shared.AutoCompleteComboBoxListener;
import sample.shared.Validation.Validation;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Created by ahmed mar3y on 22/04/2018.
 */
public class moneyController implements Initializable {

    @FXML
    private JFXButton bankPrint;

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
    private Button addBank;
    ToggleGroup groupbank = new ToggleGroup();
    ToggleGroup groupMoney = new ToggleGroup();

    List<Bank> bankList;
    List<String> bank_Names;
    List<Integer> bank_Ids;
    private BankTable bankSelected;
    private List<BankAccount> bankAccounts;


    // --------------------------------- Money Second Tab Variables  -----------------------

    int userId = 1;

    @FXML
    private JFXTextArea moneyNotes;

    @FXML
    private JFXButton moneySave;

    @FXML
    private JFXButton moneyUpdate;

    @FXML
    private TextField moneyMoney;

    @FXML
    private JFXRadioButton moneySahb;

    @FXML
    private JFXRadioButton moneyEdaa;

    @FXML
    private DatePicker moneyDate;
    @FXML
    private JFXButton moneySearch;

    @FXML
    private DatePicker moneyTo;

    @FXML
    private DatePicker moneyFrom;
    @FXML
    private TextField moneyTotalMoney;

    @FXML
    private JFXButton moneyDelete;

    @FXML
    private JFXButton moneyPrint;
    @FXML
    private TreeTableView<MoneyTable> moneyTable;

    @FXML
    private TreeTableColumn<MoneyTable, String> moneyTableUserName;

    @FXML
    private TreeTableColumn<MoneyTable, String> moneyTableType;

    @FXML
    private TreeTableColumn<MoneyTable, Double> moneyTableMoney;

    @FXML
    private TreeTableColumn<MoneyTable, String> moneyTableNotes;
    @FXML
    private TreeTableColumn<MoneyTable, String> moneyTableDate;
    ObservableList<MoneyTable> money_data = FXCollections.observableArrayList();

    List<Safe> saveList;
    private MoneyTable moneySelected;

    @FXML
    void moneyPrintAction(ActionEvent event) throws JRException {

        LocalDate sDate = this.moneyFrom.getValue();
        Date StartDate = java.util.Date.from(Instant.from(sDate.atStartOfDay(ZoneId.systemDefault())));
        LocalDate eDate = this.moneyTo.getValue();
        Date EndDate = java.util.Date.from(Instant.from(eDate.atStartOfDay(ZoneId.systemDefault())));


        HashMap<String, Object> hm = new HashMap<>();
        hm.put("datefrom", new SimpleDateFormat("yyyy-MM-dd").format(StartDate));
        hm.put("dateto", new SimpleDateFormat("yyyy-MM-dd").format(EndDate));
        hm.put("total", moneyTotalMoney.getText());           // الرصيدالمتاح
        hm.put("logoPath", getClass().getResource("C:\\jasperreports\\logo.png"));


        JasperReport jr = JasperCompileManager.compileReport("C:\\jasperreports\\safe.jrxml");
        JasperPrint jp = null;
        try {
            jp = JasperFillManager.fillReport(jr, hm, DBConnection.getConnection());
        } catch (JRException e) {
            e.printStackTrace();
        }
        JasperViewer jasperViewer = new JasperViewer(jp, false);
        jasperViewer.setVisible(true);
        jasperViewer.setTitle("كشف حساب المصاريف");


    }


    @FXML
    void bankPrintAction(ActionEvent event) throws JRException {

        LocalDate sDate = this.bankFromSearch.getValue();
        Date StartDate = java.util.Date.from(Instant.from(sDate.atStartOfDay(ZoneId.systemDefault())));
        LocalDate eDate = this.bankToSearch.getValue();
        Date EndDate = java.util.Date.from(Instant.from(eDate.atStartOfDay(ZoneId.systemDefault())));

        int bankIndex = bankNameSearch.getSelectionModel().getSelectedIndex();
        int bankId = bank_Ids.get(bankIndex);

        System.err.println(bankIndex + "    " + bankId);

        HashMap<String, Object> hm = new HashMap<>();
        hm.put("datefrom", new SimpleDateFormat("yyyy-MM-dd").format(StartDate));
        hm.put("dateto", new SimpleDateFormat("yyyy-MM-dd").format(EndDate));
        hm.put("total", bankMoneyTable.getText());           // الرصيدالمتاح
        hm.put("bankid", bankId);
        hm.put("logoPath", getClass().getResource("C:\\jasperreports\\logo.png"));


        JasperReport jr = JasperCompileManager.compileReport("C:\\jasperreports\\bank.jrxml");
        JasperPrint jp = null;
        try {
            jp = JasperFillManager.fillReport(jr, hm, DBConnection.getConnection());
        } catch (JRException e) {
            e.printStackTrace();
        }
        JasperViewer jasperViewer = new JasperViewer(jp, false);
        jasperViewer.setVisible(true);
        jasperViewer.setTitle("كشف حساب البنك");


    }


    @FXML
    void moneySaveAction(ActionEvent event) {


        // add action

        String money = moneyMoney.getText();
        LocalDate date = moneyDate.getValue();

        if (money.trim().isEmpty()
//                || type
                || date == null

                ) {


            dialog dialog = new dialog(Alert.AlertType.ERROR, "خطأ", "ادخل جميع البيانات");


        } else {
            Safe safe = new Safe(((RadioButton) groupMoney.getSelectedToggle()).getText(),
                    Double.parseDouble(money),
                    moneyNotes.getText(),
                    Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant()),
                    new Users(userId));

            Safe safe1 = safeDao.SaveSafe(safe);
            if (safe1 == null) {
                dialog dialog = new dialog(Alert.AlertType.ERROR, "خطأ", "خطأ فى الحفظ فى الداتابيز ");


            } else {
//                resetFields();
                resetMoneyFields();
                dialog dialog = new dialog(Alert.AlertType.CONFIRMATION, "تم", "تمت الاضافه  ");
            }
        }
    }

    private void resetMoneyFields() {

        this.moneyMoney.setText("");
        // set init date
        //set date on init
        String newstring = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        moneyDate.setValue(LOCAL_DATE(newstring));
        this.moneyNotes.setText("");


    }

    @FXML
    void moneySearchAction(ActionEvent event) {


        LocalDate from = moneyFrom.getValue();
        LocalDate to = moneyTo.getValue();

        saveList = safeDao.SelectAllSafe();

        if (!saveList.isEmpty()) {
            money_data.clear();

            if (to != null && from != null) {
                moneyPrint.setDisable(false);
                final double[] totalSahb = {0.0};
                final double[] totalEdaa = {0.0};

                Date fromDate = Date.from(from.atStartOfDay(ZoneId.systemDefault()).toInstant());
                Date toDate = Date.from(to.atStartOfDay(ZoneId.systemDefault()).toInstant());
                saveList.stream().filter(safe -> {

                    if ((safe.getDate().after(fromDate) || safe.getDate().compareTo(fromDate) == 0) && (safe.getDate().before(toDate) || safe.getDate().compareTo(toDate) == 0)) {


                        return true;
                    }
                    return false;

                }).forEach(safe -> {
                    if (safe.getType().equals("سحب")) {

                        totalSahb[0] += safe.getMoney();
                    }
                    if (safe.getType().equals("إيداع")) {

                        totalEdaa[0] += safe.getMoney();
                    }
                    money_data.add(new MoneyTable(safe.getId(), safe.getUsersid().getName(), safe.getType(), safe.getDate().toString(), safe.getMoney(), safe.getNotes()));

                });

                moneyTotalMoney.setText((totalEdaa[0] - totalSahb[0]) + "");

            }
            if (to == null && from != null) {
                moneyPrint.setDisable(true);

                final double[] totalSahb = {0.0};
                final double[] totalEdaa = {0.0};

                Date fromDate = Date.from(from.atStartOfDay(ZoneId.systemDefault()).toInstant());
                saveList.stream().filter(safe -> {

                    if ((safe.getDate().after(fromDate) || safe.getDate().compareTo(fromDate) == 0)) {


                        return true;
                    }
                    return false;

                }).forEach(safe -> {
                    if (safe.getType().equals("سحب")) {

                        totalSahb[0] += safe.getMoney();
                    }
                    if (safe.getType().equals("إيداع")) {

                        totalEdaa[0] += safe.getMoney();
                    }
                    money_data.add(new MoneyTable(safe.getId(), safe.getUsersid().getName(), safe.getType(), safe.getDate().toString(), safe.getMoney(), safe.getNotes()));

                });

                moneyTotalMoney.setText((totalEdaa[0] - totalSahb[0]) + "");

            }
            if (to != null && from == null) {
                moneyPrint.setDisable(true);

                final double[] totalSahb = {0.0};
                final double[] totalEdaa = {0.0};

                Date toDate = Date.from(to.atStartOfDay(ZoneId.systemDefault()).toInstant());
                saveList.stream().filter(safe -> {

                    if ((safe.getDate().before(toDate) || safe.getDate().compareTo(toDate) == 0)) {


                        return true;
                    }
                    return false;

                }).forEach(safe -> {
                    if (safe.getType().equals("سحب")) {

                        totalSahb[0] += safe.getMoney();
                    }
                    if (safe.getType().equals("إيداع")) {

                        totalEdaa[0] += safe.getMoney();
                    }
                    money_data.add(new MoneyTable(safe.getId(), safe.getUsersid().getName(), safe.getType(), safe.getDate().toString(), safe.getMoney(), safe.getNotes()));

                });

                moneyTotalMoney.setText((totalEdaa[0] - totalSahb[0]) + "");

            }

            final TreeItem<MoneyTable> Client_root = new RecursiveTreeItem<MoneyTable>(money_data, RecursiveTreeObject::getChildren);
            moneyTable.setRoot(Client_root);

        }

    }

    @FXML
    void moneyUpdateAction(ActionEvent event) {

        if (moneySelected != null) {
            // add action

            String money = moneyMoney.getText();
            LocalDate date = moneyDate.getValue();

            if (money.trim().isEmpty()
//                || type
                    || date == null

                    ) {


                dialog dialog = new dialog(Alert.AlertType.ERROR, "خطأ", "ادخل جميع البيانات");


            } else {
                Safe safe = new Safe(((RadioButton) groupMoney.getSelectedToggle()).getText(),
                        Double.parseDouble(money),
                        moneyNotes.getText(),
                        Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant()),
                        new Users(userId));
                safe.setId(moneySelected.getId());

                Safe safe1 = safeDao.UpdateSafe(moneySelected.getId(), safe);

                if (safe1 == null) {
                    dialog dialog = new dialog(Alert.AlertType.ERROR, "خطأ", "خطأ فى التعديل فى الداتابيز ");


                } else {


                    boolean t = money_data.remove(moneySelected);
                    if (t) {

                        resetMoneyFields();

                        //add to table
                        money_data.add(new MoneyTable(safe1.getId(),
                                safe1.getUsersid().getName(),
                                safe1.getType(),
                                safe1.getDate().toString(),
                                safe1.getMoney(),
                                safe1.getNotes()));
                        final TreeItem<MoneyTable> Client_root = new RecursiveTreeItem<MoneyTable>(money_data, RecursiveTreeObject::getChildren);
                        moneyTable.setRoot(Client_root);


                        moneySelected = null;
                        resetFields();
                        // set disabled
                        moneyUpdate.setDisable(true);
                        moneySave.setDisable(false);


                    }

                }

            }


        } else

        {
            dialog dialog = new dialog(Alert.AlertType.ERROR, "خطأ", "اختر من الجدول للتعديل");


        }

    }

    @FXML
    void moneyDisplayAllAction(ActionEvent event) {
        saveList = safeDao.SelectAllSafe();

        if (!saveList.isEmpty()) {
            money_data.clear();
            final double[] totalSahb = {0.0};
            final double[] totalEdaa = {0.0};

            saveList.forEach(ee -> {

                if (ee.getType().equals("سحب")) {

                    totalSahb[0] += ee.getMoney();
                }
                if (ee.getType().equals("إيداع")) {

                    totalEdaa[0] += ee.getMoney();
                }

                money_data.add(new MoneyTable(ee.getId(),
                        ee.getUsersid().getName(),
                        ee.getType(),
                        ee.getDate().toString(), ee.getMoney(),
                        ee.getNotes()));
            });
            moneyTotalMoney.setText((totalEdaa[0] - totalSahb[0]) + "");

            final TreeItem<MoneyTable> Client_root = new RecursiveTreeItem<MoneyTable>(money_data, RecursiveTreeObject::getChildren);
            moneyTable.setRoot(Client_root);

        }


    }

    @FXML
    void moneyDeleteAction(ActionEvent event) {
/*
*
*
*
* */

        deleteMoneyFunction();
    }

    @FXML
    void moneykeyEnterEvent(KeyEvent event) {
        if (event.getCode().equals(KeyCode.DELETE)) {

            deleteMoneyFunction();
        }

    }

    public void deleteMoneyFunction() {
        RecursiveTreeItem item = (RecursiveTreeItem) moneyTable.getSelectionModel().getSelectedItem();

        if (item == null) {
            dialog dialog = new dialog(Alert.AlertType.ERROR, "خطأ", "اختر من الجدول للمسح");


        } else {
            MoneyTable ct = (MoneyTable) item.getValue();
            Safe bankAccount = safeDao.DeleteSafe(ct.id.get());
            if (bankAccount == null) {
                // done
                // delete from table
                boolean t = money_data.remove(ct);

                if (t) {

                    final TreeItem<MoneyTable> Client_root = new RecursiveTreeItem<MoneyTable>(money_data, RecursiveTreeObject::getChildren);
                    moneyTable.setRoot(Client_root);

                    dialog dialog = new dialog(Alert.AlertType.CONFIRMATION, "تم", "تم المسح بنجاح");


                }

            } else {
                dialog dialog = new dialog(Alert.AlertType.ERROR, "خطأ", "خطأ فى الميح من الداتابيز");

            }


        }

    }


    @FXML
    void moneyRefreshAction(ActionEvent event) {

        // reset Fields
        resetMoneyFields();
        moneyFrom.setValue(null);
        moneyTo.setValue(null);


        moneyUpdate.setDisable(true);
        moneySave.setDisable(false);

        // clear table
        money_data.clear();
        final TreeItem<MoneyTable> money_root = new RecursiveTreeItem<MoneyTable>(money_data, RecursiveTreeObject::getChildren);
        moneyTable.setRoot(money_root);


    }


    @FXML
    void refreshBankAction(ActionEvent event) {

        resetFields();
        // set disble
        bankUpdate.setDisable(true);
        bankSave.setDisable(false);

        // clear search Data
        bankNameSearch.setValue("");
        bankFromSearch.setValue(null);
        bankToSearch.setValue(null);


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
        final double[] totalSahb = {0.0};
        final double[] totalEdaa = {0.0};


        bankAccounts = bankAccountDao.SelectAllBankAccount();

        if (!bankAccounts.isEmpty()) {
            bank_data.clear();

            bankAccounts.forEach(ee -> {
                if (ee.getType().equals("سحب")) {

                    totalSahb[0] += ee.getMoney();
                }
                if (ee.getType().equals("إيداع")) {

                    totalEdaa[0] += ee.getMoney();
                }
                bank_data.add(new BankTable(ee.getId(), ee.getBankid().getName(), ee.getBankid().getNumber(), ee.getDate().toString(), ee.getType(), ee.getMoney(), ee.getNotes()));

            });
            bankMoneyTable.setText((totalEdaa[0] - totalSahb[0]) + "");

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


        // search Method

        boolean emptyName = bankNameSearch.getSelectionModel().isEmpty();
        LocalDate from = bankFromSearch.getValue();
        LocalDate to = bankToSearch.getValue();
        bankAccounts = bankAccountDao.SelectAllBankAccount();
        if (!bankAccounts.isEmpty()) {
            bank_data.clear();

            // name Only
            if (!emptyName && from == null && to == null) {
                bankPrint.setDisable(true);
                final double[] totalSahb = {0.0};
                final double[] totalEdaa = {0.0};


                String name = bankNameSearch.getSelectionModel().getSelectedItem().toString();

                bankAccounts.stream().filter(bankAccount -> {
                    if (bankAccount.getBankid().getName().equals(name)) {
                        return true;

                    }

                    return false;
                }).forEach(bankAccount -> {
                    if (bankAccount.getType().equals("سحب")) {

                        totalSahb[0] += bankAccount.getMoney();
                    }
                    if (bankAccount.getType().equals("إيداع")) {

                        totalEdaa[0] += bankAccount.getMoney();
                    }
                    bank_data.add(new BankTable(bankAccount.getId(), bankAccount.getBankid().getName(), bankAccount.getBankid().getNumber(), bankAccount.getDate().toString(), bankAccount.getType(), bankAccount.getMoney(), bankAccount.getNotes()));


                });

                bankMoneyTable.setText((totalEdaa[0] - totalSahb[0]) + "");
            }
            if (!emptyName && from != null && to == null) {
                final double[] totalSahb = {0.0};
                final double[] totalEdaa = {0.0};
                bankPrint.setDisable(true);


                String name = bankNameSearch.getSelectionModel().getSelectedItem().toString();
                Date fromDate = Date.from(from.atStartOfDay(ZoneId.systemDefault()).toInstant());

                bankAccounts.stream().filter(bankAccount -> {
                    if (bankAccount.getBankid().getName().equals(name) && (bankAccount.getDate().after(fromDate) || bankAccount.getDate().compareTo(fromDate) == 0)) {
                        return true;

                    }

                    return false;
                }).forEach(bankAccount -> {
                    if (bankAccount.getType().equals("سحب")) {

                        totalSahb[0] += bankAccount.getMoney();
                    }
                    if (bankAccount.getType().equals("إيداع")) {

                        totalEdaa[0] += bankAccount.getMoney();
                    }
                    bank_data.add(new BankTable(bankAccount.getId(), bankAccount.getBankid().getName(), bankAccount.getBankid().getNumber(), bankAccount.getDate().toString(), bankAccount.getType(), bankAccount.getMoney(), bankAccount.getNotes()));


                });

                bankMoneyTable.setText((totalEdaa[0] - totalSahb[0]) + "");

            }
            if (!emptyName && from == null && to != null) {
                final double[] totalSahb = {0.0};
                final double[] totalEdaa = {0.0};
                bankPrint.setDisable(true);


                String name = bankNameSearch.getSelectionModel().getSelectedItem().toString();
                Date toDate = Date.from(to.atStartOfDay(ZoneId.systemDefault()).toInstant());

                bankAccounts.stream().filter(bankAccount -> {
                    if (bankAccount.getBankid().getName().equals(name) && (bankAccount.getDate().before(toDate) || bankAccount.getDate().compareTo(toDate) == 0)) {
                        return true;

                    }

                    return false;
                }).forEach(bankAccount -> {
                    if (bankAccount.getType().equals("سحب")) {

                        totalSahb[0] += bankAccount.getMoney();
                    }
                    if (bankAccount.getType().equals("إيداع")) {

                        totalEdaa[0] += bankAccount.getMoney();
                    }
                    bank_data.add(new BankTable(bankAccount.getId(), bankAccount.getBankid().getName(), bankAccount.getBankid().getNumber(), bankAccount.getDate().toString(), bankAccount.getType(), bankAccount.getMoney(), bankAccount.getNotes()));


                });

                bankMoneyTable.setText((totalEdaa[0] - totalSahb[0]) + "");

            }

            if (!emptyName && from != null && to != null) {
                final double[] totalSahb = {0.0};
                final double[] totalEdaa = {0.0};
                this.bankPrint.setDisable(false);

                String name = bankNameSearch.getSelectionModel().getSelectedItem().toString();
                Date toDate = Date.from(to.atStartOfDay(ZoneId.systemDefault()).toInstant());
                Date fromDate = Date.from(from.atStartOfDay(ZoneId.systemDefault()).toInstant());

                bankAccounts.stream().filter(bankAccount -> {
                    if (bankAccount.getBankid().getName().equals(name) &&
                            (bankAccount.getDate().before(toDate) || bankAccount.getDate().compareTo(toDate) == 0) &&
                            (bankAccount.getDate().after(fromDate) || bankAccount.getDate().compareTo(fromDate) == 0)
                            ) {
                        return true;

                    }

                    return false;
                }).forEach(bankAccount -> {
                    if (bankAccount.getType().equals("سحب")) {

                        totalSahb[0] += bankAccount.getMoney();
                    }
                    if (bankAccount.getType().equals("إيداع")) {

                        totalEdaa[0] += bankAccount.getMoney();
                    }
                    bank_data.add(new BankTable(bankAccount.getId(), bankAccount.getBankid().getName(), bankAccount.getBankid().getNumber(), bankAccount.getDate().toString(), bankAccount.getType(), bankAccount.getMoney(), bankAccount.getNotes()));


                });

                bankMoneyTable.setText((totalEdaa[0] - totalSahb[0]) + "");

            }

            if (emptyName && from != null && to != null) {
                final double[] totalSahb = {0.0};
                final double[] totalEdaa = {0.0};
                bankPrint.setDisable(true);


                Date toDate = Date.from(to.atStartOfDay(ZoneId.systemDefault()).toInstant());
                Date fromDate = Date.from(from.atStartOfDay(ZoneId.systemDefault()).toInstant());

                bankAccounts.stream().filter(bankAccount -> {
                    if (
                            (bankAccount.getDate().before(toDate) || bankAccount.getDate().compareTo(toDate) == 0) &&
                                    (bankAccount.getDate().after(fromDate) || bankAccount.getDate().compareTo(fromDate) == 0)
                            ) {
                        return true;

                    }

                    return false;
                }).forEach(bankAccount -> {
                    if (bankAccount.getType().equals("سحب")) {

                        totalSahb[0] += bankAccount.getMoney();
                    }
                    if (bankAccount.getType().equals("إيداع")) {

                        totalEdaa[0] += bankAccount.getMoney();
                    }
                    bank_data.add(new BankTable(bankAccount.getId(), bankAccount.getBankid().getName(), bankAccount.getBankid().getNumber(), bankAccount.getDate().toString(), bankAccount.getType(), bankAccount.getMoney(), bankAccount.getNotes()));


                });

                bankMoneyTable.setText((totalEdaa[0] - totalSahb[0]) + "");

            }
            if (emptyName && from != null && to == null) {
                final double[] totalSahb = {0.0};
                final double[] totalEdaa = {0.0};

                bankPrint.setDisable(true);

                Date fromDate = Date.from(from.atStartOfDay(ZoneId.systemDefault()).toInstant());

                bankAccounts.stream().filter(bankAccount -> {
                    if (
                            (bankAccount.getDate().after(fromDate) || bankAccount.getDate().compareTo(fromDate) == 0)
                            ) {
                        return true;

                    }

                    return false;
                }).forEach(bankAccount -> {
                    if (bankAccount.getType().equals("سحب")) {

                        totalSahb[0] += bankAccount.getMoney();
                    }
                    if (bankAccount.getType().equals("إيداع")) {

                        totalEdaa[0] += bankAccount.getMoney();
                    }
                    bank_data.add(new BankTable(bankAccount.getId(), bankAccount.getBankid().getName(), bankAccount.getBankid().getNumber(), bankAccount.getDate().toString(), bankAccount.getType(), bankAccount.getMoney(), bankAccount.getNotes()));


                });
                bankMoneyTable.setText((totalEdaa[0] - totalSahb[0]) + "");


            }
            if (emptyName && from == null && to != null) {
                final double[] totalSahb = {0.0};
                final double[] totalEdaa = {0.0};
                bankPrint.setDisable(true);


                Date toDate = Date.from(to.atStartOfDay(ZoneId.systemDefault()).toInstant());

                bankAccounts.stream().filter(bankAccount -> {
                    if (
                            (bankAccount.getDate().before(toDate) || bankAccount.getDate().compareTo(toDate) == 0)
                            ) {
                        return true;

                    }

                    return false;
                }).forEach(bankAccount -> {
                    if (bankAccount.getType().equals("سحب")) {

                        totalSahb[0] += bankAccount.getMoney();
                    }
                    if (bankAccount.getType().equals("إيداع")) {

                        totalEdaa[0] += bankAccount.getMoney();
                    }
                    bank_data.add(new BankTable(bankAccount.getId(), bankAccount.getBankid().getName(), bankAccount.getBankid().getNumber(), bankAccount.getDate().toString(), bankAccount.getType(), bankAccount.getMoney(), bankAccount.getNotes()));


                });
                bankMoneyTable.setText((totalEdaa[0] - totalSahb[0]) + "");


            }


            final TreeItem<BankTable> Client_root = new RecursiveTreeItem<BankTable>(bank_data, RecursiveTreeObject::getChildren);
            bankTable.setRoot(Client_root);

        }

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
        this.bankPrint.setDisable(true);
        moneyPrint.setDisable(true);

        this.userId = LoginController.idEmployee;

// ------------ set gardio Button  ----------------
        bankEdaa.setToggleGroup(groupbank);
        bankSahb.setToggleGroup(groupbank);

        moneySahb.setToggleGroup(groupMoney);
        moneyEdaa.setToggleGroup(groupMoney);


        // set disble
        bankUpdate.setDisable(true);
        bankSave.setDisable(false);
        moneyUpdate.setDisable(true);
        moneySave.setDisable(false);

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
        moneyDate.setValue(LOCAL_DATE(newstring));

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

        tableHbox.setPrefWidth(screenWidth - infoPane.getPrefWidth() - 20);
        tableHbox2.setPrefWidth(screenWidth - infoPane2.getPrefWidth() - 20);

        bankTable.setPrefHeight(primaryScreenBounds.getHeight() - 250);
        moneyTable.setPrefHeight(primaryScreenBounds.getHeight() - 250);
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


        // -----------------  init Money Table ----------------------------

        moneyTableUserName.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<MoneyTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<MoneyTable, String> param) {
                return param.getValue().getValue().userName;
            }
        });
        moneyTableType.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<MoneyTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<MoneyTable, String> param) {
                return param.getValue().getValue().monetType;
            }
        });
        moneyTableType.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<MoneyTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<MoneyTable, String> param) {
                return param.getValue().getValue().monetType;
            }
        });
        moneyTableDate.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<MoneyTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<MoneyTable, String> param) {
                return param.getValue().getValue().moneyDate;
            }
        });
        moneyTableNotes.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<MoneyTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<MoneyTable, String> param) {
                return param.getValue().getValue().moneyNotes;
            }
        });
        moneyTableMoney.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<MoneyTable, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(TreeTableColumn.CellDataFeatures<MoneyTable, Double> param) {
                return param.getValue().getValue().moneyMoney.asObject();
            }
        });

        final TreeItem<MoneyTable> money_root = new RecursiveTreeItem<MoneyTable>(money_data, RecursiveTreeObject::getChildren);
        moneyTable.setRoot(money_root);
        moneyTable.setShowRoot(false);

        // double click Event
        moneyTable.setRowFactory(tv -> {
            TreeTableRow<MoneyTable> row = new TreeTableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY
                        && event.getClickCount() == 2) {
                    selectionUpdateTableMoneyAction();
                    moneyUpdate.setDisable(false);
                    moneySave.setDisable(true);
//                    MyDataType clickedRow = row.getItem();
//                    printRow(clickedRow);
                }
            });
            return row;
        });


        // --------- format ----------
        this.bankMoney.setTextFormatter(Validation.DoubleValidation());
        this.bankMoneyTable.setTextFormatter(Validation.DoubleValidation());
        this.moneyMoney.setTextFormatter(Validation.DoubleValidation());


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

    private void selectionUpdateTableMoneyAction() {

        RecursiveTreeItem item = (RecursiveTreeItem) moneyTable.getSelectionModel().getSelectedItem();
        if (item != null) {
            MoneyTable ct = (MoneyTable) item.getValue();
            this.moneySelected = ct;
// set radio Button
            if (ct.monetType.get().equals("سحب"))
                moneySahb.setSelected(true);
            else
                moneyEdaa.setSelected(true);

            this.moneyMoney.setText(ct.moneyMoney.get() + "");
            this.moneyDate.setValue(LOCAL_DATEParse(ct.moneyDate.get()));
            this.moneyNotes.setText(ct.moneyNotes.get());


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

    class MoneyTable extends RecursiveTreeObject<MoneyTable> {
        StringProperty moneyNotes;
        DoubleProperty moneyMoney;
        StringProperty monetType;
        StringProperty moneyDate;
        StringProperty userName;
        IntegerProperty id;

        public MoneyTable(int id, String userName, String monetType, String moneyDate, Double moneyMoney, String moneyNotes) {
            this.id = new SimpleIntegerProperty(id);
            this.monetType = new SimpleStringProperty(monetType);
            this.moneyDate = new SimpleStringProperty(moneyDate);
            this.userName = new SimpleStringProperty(userName);
            this.moneyNotes = new SimpleStringProperty(moneyNotes);
            this.moneyMoney = new SimpleDoubleProperty(moneyMoney);

        }

        public String getMoneyNotes() {
            return moneyNotes.get();
        }

        public StringProperty moneyNotesProperty() {
            return moneyNotes;
        }

        public void setMoneyNotes(String moneyNotes) {
            this.moneyNotes.set(moneyNotes);
        }

        public double getMoneyMoney() {
            return moneyMoney.get();
        }

        public DoubleProperty moneyMoneyProperty() {
            return moneyMoney;
        }

        public void setMoneyMoney(double moneyMoney) {
            this.moneyMoney.set(moneyMoney);
        }

        public String getMonetType() {
            return monetType.get();
        }

        public StringProperty monetTypeProperty() {
            return monetType;
        }

        public void setMonetType(String monetType) {
            this.monetType.set(monetType);
        }

        public String getMoneyDate() {
            return moneyDate.get();
        }

        public StringProperty moneyDateProperty() {
            return moneyDate;
        }

        public void setMoneyDate(String moneyDate) {
            this.moneyDate.set(moneyDate);
        }

        public String getUserName() {
            return userName.get();
        }

        public StringProperty userNameProperty() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName.set(userName);
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
    }


}
