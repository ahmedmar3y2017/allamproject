package sample.app.mandob;

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
import javafx.stage.Screen;
import javafx.util.Callback;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;
import sample.app.Entities.mandob;
import sample.app.Transactions.DBConnection;
import sample.app.Transactions.MandobDao.mandobDao;
import sample.app.dialogs.dialog;
import sample.shared.Validation.Validation;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public class mandobController implements Initializable {

    List<mandob> mandobPluses;
    private mandobTable mandobTableObject;

    @FXML
    private HBox hbox1;

    @FXML
    private TextField ohda;

    @FXML
    private TextField masrof;

    @FXML
    private DatePicker date;

    @FXML
    private HBox hbox2;

    @FXML
    private Separator hr;

    @FXML
    private JFXButton add;

    @FXML
    private JFXButton update;

    @FXML
    private JFXButton refresh;

    @FXML
    private HBox hbox3;

    @FXML
    private DatePicker datesearch;

    @FXML
    private JFXButton search;

    @FXML
    private JFXButton showToday;

    @FXML
    private HBox tableHbox;

    @FXML
    private TreeTableView<mandobTable> table;

    @FXML
    private TreeTableColumn<mandobTable, String> tableDate;

    @FXML
    private TreeTableColumn<mandobTable, Double> tableOhda;

    @FXML
    private TreeTableColumn<mandobTable, Double> tableMasrof;

    @FXML
    private TreeTableColumn<mandobTable, Double> tableRest;

    @FXML
    private HBox lastHbox;

    @FXML
    private JFXButton delete;
    ObservableList<mandobTable> mandobTable_data = FXCollections.observableArrayList();


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


        hbox1.setPrefWidth(primaryScreenBounds.getWidth() - 180);
//        hbox2.setPrefWidth(primaryScreenBounds.getWidth() - 180);
        hbox2.setLayoutX((primaryScreenBounds.getWidth() - 650) / 2);
        hr.setPrefWidth((primaryScreenBounds.getWidth() - 200));

        hbox3.setLayoutX((primaryScreenBounds.getWidth() - 750) / 2);
        tableHbox.setPrefWidth(primaryScreenBounds.getWidth() - 200);
        hbox3.setLayoutX((primaryScreenBounds.getWidth() - 650) / 2);
        tableHbox.setPrefWidth(primaryScreenBounds.getWidth() - 250);
        hbox3.setLayoutX((primaryScreenBounds.getWidth() - 650) / 2);
        tableHbox.setPrefWidth(primaryScreenBounds.getWidth() - 250);
//
        lastHbox.setLayoutY(primaryScreenBounds.getHeight() - 75);
//        hbox4.setLayoutY(table.getPrefHeight());
        tableHbox.setPrefHeight(primaryScreenBounds.getHeight() - 350);
        tableDate.setPrefWidth((table.getPrefWidth() / 4) - 10);
        tableOhda.setPrefWidth(table.getPrefWidth() / 4);

        tableRest.setPrefWidth(table.getPrefWidth() / 4);
        tableMasrof.setPrefWidth(table.getPrefWidth() / 4);


        // init table design
        tableDate.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<mandobTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<mandobTable, String> param) {
                return param.getValue().getValue().date;
            }

        });


        tableMasrof.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<mandobTable, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(TreeTableColumn.CellDataFeatures<mandobTable, Double> param) {
                return param.getValue().getValue().masrof.asObject();
            }

        });
        tableOhda.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<mandobTable, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(TreeTableColumn.CellDataFeatures<mandobTable, Double> param) {
                return param.getValue().getValue().ohda.asObject();
            }

        });
        tableRest.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<mandobTable, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(TreeTableColumn.CellDataFeatures<mandobTable, Double> param) {
                return param.getValue().getValue().rest.asObject();
            }

        });
        // select all today
        mandobPluses = mandobDao.SelectAllToday();
        mandobPluses.stream().forEach(mandob1 -> {

            mandobTable_data.add(new mandobTable(mandob1.getId(),
                    new SimpleDateFormat("dd-MM-yyyy").format(mandob1.getDate()),
                    mandob1.getOhda()
                    , mandob1.getMasrof(),
                    mandob1.getRest()));


        });


        final TreeItem<mandobTable> root = new RecursiveTreeItem<mandobTable>(mandobTable_data, RecursiveTreeObject::getChildren);
        table.setRoot(root);
        table.setShowRoot(false);


        // double click Event
        table.setRowFactory(tv -> {
            TreeTableRow<mandobTable> row = new TreeTableRow<>();
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


// validation
        this.ohda.setTextFormatter(Validation.DoubleValidation());
        this.masrof.setTextFormatter(Validation.DoubleValidation());


    }

    private void selectionUpdateTableAction() {

        RecursiveTreeItem item = (RecursiveTreeItem) table.getSelectionModel().getSelectedItem();
        if (item != null) {
            mandobTable ct = (mandobTable) item.getValue();
            this.mandobTableObject = ct;
            this.date.setValue(LOCAL_DATE(ct.date.get()));
            this.ohda.setText(ct.ohda.get() + "");
            this.masrof.setText(ct.masrof.get() + "");


        } else {
            dialog dialog = new dialog(Alert.AlertType.ERROR, "خطأ", "اختر اولا من الجدول ");

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
    void addAction(ActionEvent event) throws ParseException {

        LocalDate date = this.date.getValue();
        String mmasrof = masrof.getText();
        String oohda = ohda.getText();


        if (date == null || mmasrof.trim().isEmpty() || oohda.trim().isEmpty()
                ) {


            dialog dialog = new dialog(Alert.AlertType.ERROR, "خطأ", "ادخل جميع البيانات ");

        } else {
            mandob mandob = new mandob(Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant()),
                    Double.parseDouble(oohda),
                    Double.parseDouble(mmasrof),
                    Double.parseDouble(oohda) - Double.parseDouble(mmasrof));

            // save into database

            mandob mandob1 = mandobDao.Savemandob(mandob);
            if (mandob1 == null) {
                dialog dialog = new dialog(Alert.AlertType.ERROR, "خطأ", "خطأ فى الحفظ فى الداتابيز");


            } else {


// new SimpleDateFormat("dd-MM-yyyy").format(maintable1.getDate())
                // insert into table design
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                String screenDate = sdf.format(mandob1.getDate());
                String nowDate = sdf.format(new Date());
                Date date1 = sdf.parse(screenDate);
                Date date2 = sdf.parse(nowDate);
                System.out.println(date1 + "    " + date2);

                if (date1.compareTo(date2) == 0) {


                    mandobTable_data.add(new mandobTable(mandob1.getId(),
                            new SimpleDateFormat("dd-MM-yyyy").format(mandob1.getDate()),
                            mandob1.getOhda()
                            , mandob1.getMasrof(),
                            mandob1.getRest()));


                    final TreeItem<mandobTable> root = new RecursiveTreeItem<mandobTable>(mandobTable_data, RecursiveTreeObject::getChildren);
                    table.setRoot(root);
                }


                // reset values
                resetFields();


            }


        }


    }

    private void deleteFunction() {

        RecursiveTreeItem item = (RecursiveTreeItem) table.getSelectionModel().getSelectedItem();

        if (item == null) {
            dialog dialog = new dialog(Alert.AlertType.ERROR, "خطأ", "اختر من الجدول للحذف");


        } else {
            mandobTable ct = (mandobTable) item.getValue();
            mandob maintable = mandobDao.Deletemandob(ct.id.get());
            if (maintable == null) {
                // done
                // delete from table
                boolean t = mandobTable_data.remove(ct);

                if (t) {
                    final TreeItem<mandobTable> root = new RecursiveTreeItem<mandobTable>(mandobTable_data, RecursiveTreeObject::getChildren);
                    table.setRoot(root);


                    dialog dialog = new dialog(Alert.AlertType.CONFIRMATION, "تم", "تم الحذف");


                }

            } else {
                dialog dialog = new dialog(Alert.AlertType.ERROR, "خطأ", "خطأ فى الحذف ");

            }


        }
    }

    private void resetFields() {
        // reset all fields
        ohda.setText("0.0");
        masrof.setText("0.0");
        // init date
        //set date on init
//        String newstring = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
//        date.setValue(LOCAL_DATE(newstring));

    }

    @FXML
    void deleteAction(ActionEvent event) {
        deleteFunction();

    }

    @FXML
    void refreshAction(ActionEvent event) {

        resetFields();

        //set date on init
        String newstring = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        date.setValue(LOCAL_DATE(newstring));


        // set disabled
        update.setDisable(true);
        add.setDisable(false);


    }

    @FXML
    void searchAction(ActionEvent event) {
        LocalDate localDate = datesearch.getValue();
        System.out.println("Search ");

        if (localDate == null) {
            dialog dialog = new dialog(Alert.AlertType.ERROR, "خطأ", "حدد التاريخ اولا");


        } else {
            Date ddate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

            // Select From Database

            List<mandob> screenPluses = mandobDao.SelectAllmandobByDate(ddate);
            if (screenPluses.size() <= 0) {
                dialog dialog = new dialog(Alert.AlertType.WARNING, "خطأ", "لا توجدنتائج");

            } else {
                // clear all table

                mandobTable_data.clear();

                screenPluses.stream().forEach(mandob1 -> {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                    String screenDate = sdf.format(mandob1.getDate());
                    mandobTable_data.add(new mandobTable(mandob1.getId(),
                            new SimpleDateFormat("dd-MM-yyyy").format(mandob1.getDate()),
                            mandob1.getOhda()
                            , mandob1.getMasrof(),
                            mandob1.getRest()));
                });
                final TreeItem<mandobTable> root = new RecursiveTreeItem<mandobTable>(mandobTable_data, RecursiveTreeObject::getChildren);
                table.setRoot(root);

            }

        }


    }

    @FXML
    void printAction(ActionEvent event) throws JRException {

        LocalDate sDate = this.datesearch.getValue();
        if (sDate == null) {
            dialog dialog = new dialog(Alert.AlertType.ERROR, "خطأ", "حدد تاريخ من البحث للطباعه");


        } else {
            Date StartDate = java.util.Date.from(Instant.from(sDate.atStartOfDay(ZoneId.systemDefault())));


            HashMap<String, Object> hm = new HashMap<>();
            hm.put("datefrom", new SimpleDateFormat("yyyy-MM-dd").format(StartDate));
            hm.put("logoPath", getClass().getResource("C:\\jasperreports\\logo.png"));


            // url

            JasperReport jr = JasperCompileManager.compileReport("C:\\jasperreports\\mandob.jrxml");
            JasperPrint jp = null;
            try {
                jp = JasperFillManager.fillReport(jr, hm, DBConnection.getConnection());
            } catch (JRException e) {
                e.printStackTrace();
            }
            JasperViewer jasperViewer = new JasperViewer(jp, false);
            jasperViewer.setVisible(true);
            jasperViewer.setTitle("مندوب");

        }
        System.out.println("Print Action");

    }


    @FXML
    void showTodayAction(ActionEvent event) {


        // select all today
        mandobTable_data.clear();
        mandobPluses = mandobDao.SelectAllToday();
        mandobPluses.stream().forEach(mandob1 -> {


            mandobTable_data.add(new mandobTable(mandob1.getId(),
                    new SimpleDateFormat("dd-MM-yyyy").format(mandob1.getDate()),
                    mandob1.getOhda()
                    , mandob1.getMasrof(),
                    mandob1.getRest()));


        });


        final TreeItem<mandobTable> root = new RecursiveTreeItem<mandobTable>(mandobTable_data, RecursiveTreeObject::getChildren);
        table.setRoot(root);


    }

    @FXML
    void treeViewKeyPressed(KeyEvent event) {
        if (event.getCode().equals(KeyCode.DELETE)) {

            deleteFunction();

        }
    }


    @FXML
    void updateAction(ActionEvent event) {


        if (mandobTableObject != null) {

            LocalDate date = this.date.getValue();
            String oohda = ohda.getText();
            String mmasrof = masrof.getText();


            if (date == null || mmasrof.trim().isEmpty() || oohda.trim().isEmpty()
                    ) {

                dialog dialog = new dialog(Alert.AlertType.ERROR, "خطأ", "ادخل جميع البيانات");

            } else {
                mandob mandob = new mandob(Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant()),
                        Double.parseDouble(oohda),
                        Double.parseDouble(mmasrof),
                        Double.parseDouble(oohda) - Double.parseDouble(mmasrof));

                mandob.setId(mandobTableObject.id.get());

                mandob mandob1 = mandobDao.Updatemandob(mandobTableObject.id.get(), mandob);

                if (mandob1 == null) {
                    dialog dialog = new dialog(Alert.AlertType.ERROR, "خطأ", "خطأ فى التعديل");


                } else {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                    String screenDate = sdf.format(mandob1.getDate());
                    // remove from table
                    boolean t = mandobTable_data.remove(mandobTableObject);
                    if (t) {
                        // insert into table design

                        mandobTable_data.add(new mandobTable(mandob1.getId(),
                                new SimpleDateFormat("dd-MM-yyyy").format(mandob1.getDate()),
                                mandob1.getOhda()
                                , mandob1.getMasrof(),
                                mandob1.getRest()));


                        final TreeItem<mandobTable> root = new RecursiveTreeItem<mandobTable>(mandobTable_data, RecursiveTreeObject::getChildren);
                        table.setRoot(root);
// reset values
                        mandobTableObject = null;
                        resetFields();
                        // set disabled
                        update.setDisable(true);
                        add.setDisable(false);

                    }
                }


            }

        } else {
            dialog dialog = new dialog(Alert.AlertType.ERROR, "خطأ", "اختر من الجدول للتعديل");

        }


    }


    class mandobTable extends RecursiveTreeObject<mandobTable> {

        SimpleStringProperty date;
        SimpleDoubleProperty masrof;
        SimpleDoubleProperty ohda;
        SimpleDoubleProperty rest;

        SimpleIntegerProperty id;

        public mandobTable(int id, String date, double ohda, double masrof, double rest) {
            this.date = new SimpleStringProperty(date);
            this.masrof = new SimpleDoubleProperty(masrof);
            this.ohda = new SimpleDoubleProperty(ohda);
            this.rest = new SimpleDoubleProperty(rest);
            this.id = new SimpleIntegerProperty(id);

        }
    }

}
