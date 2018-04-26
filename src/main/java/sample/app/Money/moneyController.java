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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.util.Callback;
import sample.app.AddBank.mainAddBank;
import sample.app.Entities.Bank;
import sample.app.Transactions.BankDao.bankDao;
import sample.shared.AutoCompleteComboBoxListener;
import sample.shared.Validation.Validation;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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

    List<Bank> bankList;
    List<String> bank_Names;
    List<Integer> bank_Ids;


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
    void bankDeleteAction(ActionEvent event) {

    }

    @FXML
    void bankSaveAction(ActionEvent event) {

    }

    @FXML
    void bankSearchAction(ActionEvent event) {

    }

    @FXML
    void bankUpdateAction(ActionEvent event) {

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

    class BankTable extends RecursiveTreeObject<BankTable> {
        StringProperty banktableNotes;
        DoubleProperty banktableMoney;
        StringProperty banktableType;
        StringProperty banktableDate;
        StringProperty banktableName;
        IntegerProperty id;

        public BankTable(int id, String banktableName, String banktableDate, String banktableType, Double banktableMoney, String banktableNotes) {
            this.id = new SimpleIntegerProperty(id);
            this.banktableType = new SimpleStringProperty(banktableType);
            this.banktableDate = new SimpleStringProperty(banktableDate);
            this.banktableName = new SimpleStringProperty(banktableName);
            this.banktableNotes = new SimpleStringProperty(banktableNotes);
            this.banktableMoney = new SimpleDoubleProperty(banktableMoney);

        }
    }


}
