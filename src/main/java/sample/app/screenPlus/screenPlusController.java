package sample.app.screenPlus;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.util.Callback;
import sample.app.Entities.ScreenPlus;
import sample.app.Transactions.ScreenplusDao.screenPlusDao;
import sample.app.dialogs.dialog;
import sample.shared.Validation.Validation;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class screenPlusController implements Initializable {
    private screenPlusTable screenTable;
    @FXML
    private JFXButton showToday;
    @FXML
    private TextField searchBolisa;

    @FXML
    private JFXButton search;


    List<ScreenPlus> screenPluses;

    @FXML
    private JFXButton refresh;


    @FXML
    private JFXButton delete;


    @FXML
    private JFXButton add;

    @FXML
    private JFXButton update;
    @FXML
    private HBox hbox2;

    @FXML
    private DatePicker date;

    @FXML
    private TextField bolisa;

    @FXML
    private TextField carNum;

    @FXML
    private TextField driverName;

    @FXML
    private TreeTableView<screenPlusTable> table;

    @FXML
    private TreeTableColumn<screenPlusTable, String> tableDate;

    @FXML
    private TreeTableColumn<screenPlusTable, String> tableBolisa;

    @FXML
    private TreeTableColumn<screenPlusTable, String> tableCarNum;

    @FXML
    private TreeTableColumn<screenPlusTable, String> tableDriverName;

    @FXML
    private TreeTableColumn<screenPlusTable, Double> tableWeight;

    @FXML
    private TreeTableColumn<screenPlusTable, String> tableNotes;

    @FXML
    private Pane lastHbox;

    @FXML
    private HBox hbox1 ,  hbox3 , tableHbox  ;



    @FXML
    private TextField weight;

    @FXML
    private TextField notes;
    ObservableList<screenPlusTable> screenPlusTable_data = FXCollections.observableArrayList();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // set disabled
        update.setDisable(true);
        add.setDisable(false);
        //set date on init
        String newstring = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        date.setValue(LOCAL_DATE(newstring));





//        set size  Ashraf

        // --------------- set size ---------------------------
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        hbox1.setPrefWidth(primaryScreenBounds.getWidth() - 250);
        hbox2.setPrefWidth(primaryScreenBounds.getWidth() - 310);
        hbox3.setPrefWidth(primaryScreenBounds.getWidth() - 250);

        hbox3.setLayoutX( (primaryScreenBounds.getWidth() -  650  ) / 2  );
        tableHbox.setPrefWidth(primaryScreenBounds.getWidth() - 250);
//
        lastHbox.setLayoutY(primaryScreenBounds.getHeight() - 75);
//        hbox4.setLayoutY(table.getPrefHeight());
        table.setPrefHeight(primaryScreenBounds.getHeight() - 300);
        tableBolisa.setPrefWidth( (table.getPrefWidth() / 5 )  - 10);
        tableCarNum.setPrefWidth(table.getPrefWidth() / 5 );

        tableWeight.setPrefWidth(table.getPrefWidth() / 5 );
        tableDriverName.setPrefWidth(table.getPrefWidth() / 5 );
        tableDate.setPrefWidth(table.getPrefWidth() / 5 );



        double screenPlus = primaryScreenBounds.getWidth() - 360;
//       System.out.println("Kashfe hsab width"  + kashfHesabWid);









        // init table design

        tableBolisa.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<screenPlusTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<screenPlusTable, String> param) {
                return param.getValue().getValue().bolisa;
            }

        });
        tableCarNum.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<screenPlusTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<screenPlusTable, String> param) {
                return param.getValue().getValue().carNum;
            }

        });
        tableDriverName.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<screenPlusTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<screenPlusTable, String> param) {
                return param.getValue().getValue().driverName;
            }

        });
        tableDate.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<screenPlusTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<screenPlusTable, String> param) {
                return param.getValue().getValue().date;
            }

        });

        tableWeight.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<screenPlusTable, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(TreeTableColumn.CellDataFeatures<screenPlusTable, Double> param) {
                return param.getValue().getValue().weight.asObject();
            }

        });


        tableNotes.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<screenPlusTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<screenPlusTable, String> param) {
                return param.getValue().getValue().notes;
            }

        });


        // select all today
        screenPluses = screenPlusDao.SelectAllToday();
        screenPluses.stream().forEach(screenPlus1 -> {

            screenPlusTable_data.add(new screenPlusTable(screenPlus1.getId(),
                    new SimpleDateFormat("dd-MM-yyyy").format(screenPlus1.getDate()), screenPlus1.getBolisa(), screenPlus1.getCarNum(), screenPlus1.getDriverName(), screenPlus1.getWeight(), screenPlus1.getNotes()));


        });


        final TreeItem<screenPlusTable> root = new RecursiveTreeItem<screenPlusTable>(screenPlusTable_data, RecursiveTreeObject::getChildren);
        table.setRoot(root);
        table.setShowRoot(false);


        // double click Event
        table.setRowFactory(tv -> {
            TreeTableRow<screenPlusTable> row = new TreeTableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY
                        && event.getClickCount() == 2) {
                    System.out.println("Click 2");
                    selectionUpdateTableAction();
                    update.setDisable(false);
                    add.setDisable(true);
//                    MyDataType clickedRow = row.getItem();
//                    printRow(clickedRow);
                }
            });
            return row;
        });


        // select all from database and display into these frame

        this.weight.setTextFormatter(Validation.DoubleValidation());
//        this.carNum.setTextFormatter(Validation.IntegerValidation());
//        this.bolisa.setTextFormatter(Validation.IntegerValidation());

        System.out.println("Init This screen ");
    }

    private void selectionUpdateTableAction() {

        RecursiveTreeItem item = (RecursiveTreeItem) table.getSelectionModel().getSelectedItem();
        if (item != null) {
            screenPlusTable ct = (screenPlusTable) item.getValue();
            this.screenTable = ct;
            this.date.setValue(LOCAL_DATE(ct.date.get()));
            this.bolisa.setText(ct.bolisa.get() + "");
            this.carNum.setText(ct.carNum.get() + "");
            this.weight.setText(ct.weight.get() + "");
            this.notes.setText(ct.notes.get() + "");
            this.driverName.setText(ct.driverName.get() + "");


        } else {
            dialog dialog = new dialog(Alert.AlertType.ERROR, "ط®ط·ط£", "ط§ط®طھط± ظ…ظ† ط§ظ„ط¬ط¯ظˆظ„ ظ„ظ„طھط¹ط¯ظٹظ„");

        }


    }

    @FXML
    void addAction(ActionEvent event) throws ParseException {

        String driName = driverName.getText();
        LocalDate date = this.date.getValue();
        String wei = weight.getText();
        String bolis = bolisa.getText();
        String carNu = carNum.getText();
        String note = notes.getText();

        if (date == null
                ) {


            dialog dialog = new dialog(Alert.AlertType.ERROR, "ط®ط·ط£", "ط§ط¯ط®ظ„ ط§ظ„طھط§ط±ظٹط® ط§ظˆظ„ط§");

        } else {


            ScreenPlus screenPlus = new ScreenPlus(Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant()),
                    bolis,
                    carNu,
                    driName,
                    Double.parseDouble(wei),
                    note
            );


            // save into database

            ScreenPlus screenPlus1 = screenPlusDao.SaveScreenPlus(screenPlus);
            if (screenPlus1 == null) {
                dialog dialog = new dialog(Alert.AlertType.ERROR, "ط®ط·ط£", "ط®ط·ط£ ظپظ‰ ط§ظ„ط­ظپط¸ ظپظ‰ ط§ظ„ط¯ط§طھط§ط¨ظٹط² ");


            } else {

// new SimpleDateFormat("dd-MM-yyyy").format(maintable1.getDate())
                // insert into table design
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                String screenDate = sdf.format(screenPlus1.getDate());
                String nowDate = sdf.format(new Date());
                Date date1 = sdf.parse(screenDate);
                Date date2 = sdf.parse(nowDate);
                System.out.println(date1 + "    " + date2);

                if (date1.compareTo(date2) == 0) {

                    screenPlusTable_data.add(new screenPlusTable(screenPlus1.getId(), screenDate
                            , screenPlus1.getBolisa(), screenPlus1.getCarNum(), screenPlus1.getDriverName(), screenPlus1.getWeight(), screenPlus1.getNotes()));
                    final TreeItem<screenPlusTable> root = new RecursiveTreeItem<screenPlusTable>(screenPlusTable_data, RecursiveTreeObject::getChildren);
                    table.setRoot(root);
                }


                // reset values
                resetFields();

            }


        }


    }

    private void resetFields() {
        // reset all fields
        bolisa.setText("");
        carNum.setText("");
        weight.setText("0.0");
        notes.setText("");

    }


    @FXML
    void updateAction(ActionEvent event) {

        if (screenTable != null) {

            String driName = driverName.getText();
            LocalDate date = this.date.getValue();
            String wei = weight.getText();
            String bolis = bolisa.getText();
            String carNu = carNum.getText();
            String note = notes.getText();

            if (date == null
                    ) {


                dialog dialog = new dialog(Alert.AlertType.ERROR, "ط®ط·ط£", "ط§ط¯ط®ظ„ ط§ظ„طھط§ط±ظٹط® ط§ظˆظ„ط§");

            } else {
                ScreenPlus screenPlus = new ScreenPlus(Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant()),
                        bolis,
                        carNu,
                        driName,
                        Double.parseDouble(wei),
                        note
                );
                screenPlus.setId(screenTable.id.get());

                ScreenPlus screenPlus1 = screenPlusDao.UpdateScreenPlus(screenTable.id.get(), screenPlus);

                if (screenPlus1 == null) {
                    dialog dialog = new dialog(Alert.AlertType.ERROR, "ط®ط·ط£", "ط®ط·ط£ ظپظ‰ ط§ظ„طھط¹ط¯ظٹظ„ ظپظ‰ ط§ظ„ط¯ط§طھط§ط¨ظٹط² ");


                } else {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                    String screenDate = sdf.format(screenPlus1.getDate());
                    // remove from table
                    boolean t = screenPlusTable_data.remove(screenTable);
                    if (t) {
                        // insert into table design

                        screenPlusTable_data.add(new screenPlusTable(screenPlus1.getId(), screenDate
                                , screenPlus1.getBolisa(), screenPlus1.getCarNum(), screenPlus1.getDriverName(), screenPlus1.getWeight(), screenPlus1.getNotes()));
                        final TreeItem<screenPlusTable> root = new RecursiveTreeItem<screenPlusTable>(screenPlusTable_data, RecursiveTreeObject::getChildren);
                        table.setRoot(root);
// reset values
                        screenTable = null;
                        resetFields();
                        // set disabled
                        update.setDisable(true);
                        add.setDisable(false);

                    }
                }


            }

        } else {
            dialog dialog = new dialog(Alert.AlertType.ERROR, "ط®ط·ط£", "ط§ط®طھط± ظ…ظ† ط§ظ„ط¬ط¯ظˆظ„ ظ„ظ„طھط¹ط¯ظٹظ„");

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

    @FXML
    void treeViewKeyPressed(KeyEvent event) {


        // check SupplierSavedIdAccount

        if (event.getCode().equals(KeyCode.DELETE)) {

            deleteFunction();

        }

        //... other keyevents

    }

    @FXML
    void deleteAction(ActionEvent event) {


        deleteFunction();


    }

    private void deleteFunction() {

        RecursiveTreeItem item = (RecursiveTreeItem) table.getSelectionModel().getSelectedItem();

        if (item == null) {
            dialog dialog = new dialog(Alert.AlertType.ERROR, "ط®ط·ط£", "ط§ط®طھط± ظ…ظ† ط§ظ„ط¬ط¯ظˆظ„ ظ„ظ„ظ…ط³ط­");


        } else {
            screenPlusTable ct = (screenPlusTable) item.getValue();
            ScreenPlus maintable = screenPlusDao.DeleteScreenPlus(ct.id.get());
            if (maintable == null) {
                // done
                // delete from table
                boolean t = screenPlusTable_data.remove(ct);

                if (t) {
                    final TreeItem<screenPlusTable> root = new RecursiveTreeItem<screenPlusTable>(screenPlusTable_data, RecursiveTreeObject::getChildren);
                    table.setRoot(root);


                    dialog dialog = new dialog(Alert.AlertType.CONFIRMATION, "طھظ…", "طھظ… ط§ظ„ظ…ط³ط­ ط¨ظ†ط¬ط§ط­");


                }

            } else {
                dialog dialog = new dialog(Alert.AlertType.ERROR, "ط®ط·ط£", "ط®ط·ط£ ظپظ‰ ط§ظ„ظ…ظٹط­ ظ…ظ† ط§ظ„ط¯ط§طھط§ط¨ظٹط²");

            }


        }
    }


    @FXML
    void refreshAction(ActionEvent event) {
        resetFields();
        driverName.setText("");

        //set date on init
        String newstring = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        date.setValue(LOCAL_DATE(newstring));


        // set disabled
        update.setDisable(true);
        add.setDisable(false);
    }

    @FXML
    void searchAction(ActionEvent event) {

        String bolisaSearch = searchBolisa.getText();


        if (bolisaSearch.trim().isEmpty()) {
            dialog dialog = new dialog(Alert.AlertType.ERROR, "ط®ط·ط£", "ط§ط¯ط®ظ„ ط±ظ‚ظ… ط§ظ„ط¨ظˆظ„ظٹطµظ‡ ظ„ظ„ط¨ط­ط«");


        } else {

            // Select From Database

            List<ScreenPlus> screenPluses = screenPlusDao.SelectAllScreenPlusByBolisa(bolisaSearch);
            if (screenPluses.size() <= 0) {
                dialog dialog = new dialog(Alert.AlertType.WARNING, "", "ظ„ط§ طھظˆط¬ط¯ ظ†طھط§ط¦ط¬");

            } else {
                // clear all table

                screenPlusTable_data.clear();

                screenPluses.stream().forEach(screenPlus1 -> {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                    String screenDate = sdf.format(screenPlus1.getDate());
                    screenPlusTable_data.add(new screenPlusTable(screenPlus1.getId(), screenDate
                            , screenPlus1.getBolisa(), screenPlus1.getCarNum(), screenPlus1.getDriverName(), screenPlus1.getWeight(), screenPlus1.getNotes()));


                });
                final TreeItem<screenPlusTable> root = new RecursiveTreeItem<screenPlusTable>(screenPlusTable_data, RecursiveTreeObject::getChildren);
                table.setRoot(root);

            }

        }


    }

    @FXML
    void showTodayAction(ActionEvent event) {
        // select all today
        screenPlusTable_data.clear();
        screenPluses = screenPlusDao.SelectAllToday();
        screenPluses.stream().forEach(screenPlus1 -> {

            screenPlusTable_data.add(new screenPlusTable(screenPlus1.getId(),
                    new SimpleDateFormat("dd-MM-yyyy").format(screenPlus1.getDate()), screenPlus1.getBolisa(), screenPlus1.getCarNum(), screenPlus1.getDriverName(), screenPlus1.getWeight(), screenPlus1.getNotes()));


        });


        final TreeItem<screenPlusTable> root = new RecursiveTreeItem<screenPlusTable>(screenPlusTable_data, RecursiveTreeObject::getChildren);
        table.setRoot(root);
    }

    class screenPlusTable extends RecursiveTreeObject<screenPlusTable> {

        SimpleStringProperty date;
        SimpleStringProperty driverName;
        SimpleStringProperty bolisa;
        SimpleStringProperty carNum;
        SimpleDoubleProperty weight;
        SimpleStringProperty notes;
        SimpleIntegerProperty id;

        public screenPlusTable(int id, String date, String bolisa, String carNum, String driverName, double weight, String notes) {
            this.date = new SimpleStringProperty(date);
            this.driverName = new SimpleStringProperty(driverName);
            this.bolisa = new SimpleStringProperty(bolisa);
            this.carNum = new SimpleStringProperty(carNum);
            this.weight = new SimpleDoubleProperty(weight);
            this.notes = new SimpleStringProperty(notes);
            this.id = new SimpleIntegerProperty(id);

        }
    }


}
