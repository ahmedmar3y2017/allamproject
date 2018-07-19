package sample.app.nakl;

import com.jfoenix.controls.JFXButton;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.util.Callback;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import sample.app.AddClient.mainAddClient;
import sample.app.Entities.Clients;
import sample.app.Entities.Maintable;
import sample.app.Transactions.ClientDao.clientDao;
import sample.app.Transactions.DBConnection;
import sample.app.Transactions.MainTableDao.mainTableDao;
import sample.app.dialogs.dialog;
import sample.shared.AutoCompleteComboBoxListener;
import sample.shared.Validation.Validation;

import javax.swing.text.TableView;
import java.io.*;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by ahmed mar3y on 23/04/2018.
 */
public class naklController implements Initializable {

    @FXML
    private HBox hbox3;

    @FXML
    private Separator hr;


    @FXML
    private TextField carNum;

    @FXML
    private TextField fromCity;
    @FXML
    private TextArea notes;

    @FXML
    private HBox hbox2, tableHbox;


    @FXML
    private TextField office;

    @FXML
    private TextField ohda;

    @FXML
    private TextField bolisa;
    @FXML
    private TextField agz;

    @FXML
    private ComboBox type;

    @FXML
    private TreeTableView<NaklTable> table;

    @FXML
    private TreeTableColumn<NaklTable, String> tableDate;
    @FXML
    private TreeTableColumn<NaklTable, String> tableType;
    @FXML
    private TreeTableColumn<NaklTable, String> tableBolisa;

    @FXML
    private TreeTableColumn<NaklTable, String> tableCarNum;

    @FXML
    private TreeTableColumn<NaklTable, Double> tableWeight;

    @FXML
    private TreeTableColumn<NaklTable, Double> tableNawlon;

    @FXML
    private TreeTableColumn<NaklTable, Double> tableOhda;

    @FXML
    private TreeTableColumn<NaklTable, Double> tableAgz;
    @FXML
    private TreeTableColumn<NaklTable, Double> tableTotal;


    @FXML
    private TreeTableColumn<NaklTable, Double> tableMezan;


    @FXML
    private TreeTableColumn<NaklTable, Double> tableOffice;

    @FXML
    private TreeTableColumn<NaklTable, Double> tableClear;

    @FXML
    private TreeTableColumn<NaklTable, String> tableBian;

    @FXML
    private TreeTableColumn<NaklTable, String> tableNotes;
    @FXML
    private TreeTableColumn<NaklTable, String> tableClientName;

    @FXML
    private HBox hbox1;


    @FXML
    private TextField nawlon;

    @FXML
    private DatePicker date;

    @FXML
    private Button addClient;

    @FXML
    private ComboBox clientName;

    @FXML
    private HBox hbox4;

    @FXML
    private JFXButton update;

    @FXML
    private JFXButton save;


    @FXML
    private TextField weight;

    @FXML
    private TextField toCity;

    @FXML
    private JFXButton updateTable;

    @FXML
    private JFXButton delete;

    @FXML
    private Pane lastHbox;


    @FXML
    private HBox hboxbtns;

    List<Clients> clientsList;
    List<String> clientsList_Names;
    List<Integer> clientsList_Ids;

    List<Maintable> maintables;

    ObservableList<NaklTable> NaklTable_data = FXCollections.observableArrayList();
    private NaklTable naklTable;


    @FXML
    void printAction(ActionEvent event) throws JRException, FileNotFoundException {
        //check table data

        if (NaklTable_data.isEmpty()) {
            dialog dialog = new dialog(Alert.AlertType.WARNING, "خطأ", "لايوجد كشف نقل اليوم");


        } else {

            Date date = new Date();
            HashMap<String, Object> hm = new HashMap<>();
            hm.put("date", new SimpleDateFormat("yyyy/MM/dd").format(date));
            hm.put("logoPath", getClass().getResource("C:\\jasperreports\\logo.png"));

//            JasperCompileManager.compileReportToFile("src/main/jasperreports/mainreport.jrxml", "src/main/jasperreports/mainreport.jasper");

//            JasperReport jr = JasperCompileManager.compileReport("src/main/jasperreports/mainreport.jrxml");

            InputStream url7 = new FileInputStream(new File("C:\\jasperreports\\mainreport.jrxml"));
//            InputStream url7 = getClass().getResourceAsStream("src/main/jasperreports/mainreport.jrxml");
            JasperDesign dis = JRXmlLoader.load(url7);
            JasperReport jr = JasperCompileManager.compileReport(dis);
            JasperPrint jp = JasperFillManager.fillReport(jr, hm, DBConnection.getConnection());

            JasperViewer jasperViewer = new JasperViewer(jp, false);
            jasperViewer.setVisible(true);
            jasperViewer.setTitle("كشف النقل");
        }


    }


    @FXML
    void addClientAction(ActionEvent event) throws IOException {

        mainAddClient mainAddClient = new mainAddClient();

    }
//
//    @FXML
//    void clientNameAction(ActionEvent event) {
//
//
//    }

    @FXML
    void clientNameMouseClick(MouseEvent event) {

        // select All Clients
        clientsList = clientDao.SelectAllClients();
        if (clientsList != null) {
            clientsList_Names = clientsList.stream().map(ee -> ee.getName()).collect(Collectors.toList());
            clientsList_Ids = clientsList.stream().map(ee -> ee.getId()).collect(Collectors.toList());
            clientName.getItems().setAll(clientsList_Names);

        }

    }


    @FXML
    void deleteAction(ActionEvent event) {
        deleteFunction();


    }

    @FXML
    void refreshFields(ActionEvent event) {


        // refresh All Fields

        bolisa.setText("");
        carNum.setText("");
        weight.setText("0.0");
        nawlon.setText("0.0");
        ohda.setText("0.0");
//        mezan.setText("0.0");
//        office.setText("0.0");

//        fromCity.setText("");
//        toCity.setText("");
        clientName.setValue(null);


        // set init date
        //set date on init
        String newstring = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        date.setValue(LOCAL_DATE(newstring));


        // set disabled
        update.setDisable(true);
        save.setDisable(false);


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
            NaklTable ct = (NaklTable) item.getValue();
            Maintable maintable = mainTableDao.DeleteMaintable(ct.getId());
            if (maintable == null) {
                // done
                // delete from table
                boolean t = NaklTable_data.remove(ct);

                if (t) {
                    final TreeItem<NaklTable> root = new RecursiveTreeItem<NaklTable>(NaklTable_data, RecursiveTreeObject::getChildren);
                    table.setRoot(root);


                    dialog dialog = new dialog(Alert.AlertType.CONFIRMATION, "تم", "تم المسح بنجاح");


                }

            } else {
                dialog dialog = new dialog(Alert.AlertType.ERROR, "خطأ", "خطأ فى الميح من الداتابيز");

            }


        }
    }

    @FXML
    void saveAction(ActionEvent event) {

        // save Function
        boolean clientName = this.clientName.getSelectionModel().isEmpty();
//        String type = this.type.getValue().toString();
        String to = this.toCity.getText();
        String from = this.fromCity.getText();
        LocalDate date = this.date.getValue();
        String bolisa = this.bolisa.getText();
        String carNum = this.carNum.getText();
        String weight = this.weight.getText();
        String nawlon = this.nawlon.getText();
        String ohda = this.ohda.getText();
//        String mezan = this.mezan.getText();
        String office = this.office.getText();
        String agz = this.agz.getText();
        String notes = this.notes.getText();

        if (clientName
                || date == null
                ) {


            dialog dialog = new dialog(Alert.AlertType.ERROR, "خطأ", "ادخل جميع البيانات");

        } else {
            String type = "";
            if (!this.type.getSelectionModel().isEmpty()) {

                type = this.type.getValue().toString();
            }
            // select Client
            int clientIndex = this.clientName.getSelectionModel().getSelectedIndex();
            int clientId = clientsList_Ids.get(clientIndex);


            // calculated
            double mmezan = Double.parseDouble(weight) - Double.parseDouble(agz);
            double ttotal = mmezan * Double.parseDouble(nawlon);
            double cclear = ttotal - Double.parseDouble(ohda) - Double.parseDouble(office);

            Date date1 = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Maintable maintable = new Maintable(
                    date1,
                    bolisa,
                    carNum,
                    Double.parseDouble(weight),
                    Double.parseDouble(nawlon),
                    Double.parseDouble(ohda),
                    mmezan,
                    Double.parseDouble(office),
                    Double.parseDouble(new DecimalFormat(".##").format(ttotal)),
                    type,
                    from,
                    to,
                    Double.parseDouble(agz),
                    cclear, notes,
                    new Clients(clientId)
            );

            Maintable maintable1 = mainTableDao.SaveMaintable(maintable);
            if (maintable1 == null) {
                dialog dialog = new dialog(Alert.AlertType.ERROR, "خطأ", "خطأ فى الحفظ فى الداتابيز ");


            } else {

                // insert into table design
                NaklTable_data.add(new NaklTable(maintable1.getId(), new SimpleDateFormat("dd-MM-yyyy").format(maintable1.getDate()),
                        maintable1.getType(),
                        maintable1.getPolesa(),
                        maintable1.getCarNumber(),
                        maintable1.getAmount(),
                        maintable1.getNowlon(), maintable1.getOhda(),
                        maintable1.getAgz(), maintable1.getMezan(), maintable1.getOffice(),
                        maintable1.getSafy(), maintable1.getCityFrom() + "-" + maintable1.getCityTo(),
                        maintable1.getNotes(), maintable1.getTotal(), this.clientName.getValue().toString()));
                final TreeItem<NaklTable> root = new RecursiveTreeItem<NaklTable>(NaklTable_data, RecursiveTreeObject::getChildren);
                table.setRoot(root);
                table.setShowRoot(false);
                // reset values
                resetFields();

            }


        }


    }

    private void resetFields() {

//        this.clientName.setValue("");
//        this.type.setText("");
//        this.fromCity.setText("");
//        this.toCity.setText("");

        bolisa.setText("");
        carNum.setText("");
        weight.setText("0.0");
        nawlon.setText("0.0");
        ohda.setText("0.0");
//        mezan.setText("0.0");
//        office.setText("0.0");
        agz.setText("0.0");

        // set init date
        String newstring = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        this.date.setValue(LOCAL_DATE(newstring));

    }

    @FXML
    void updateAction(ActionEvent event) {

        if (naklTable != null) {


            // save Function
            boolean clientName = this.clientName.getSelectionModel().isEmpty();
            String to = this.toCity.getText();
            String from = this.fromCity.getText();
            LocalDate date = this.date.getValue();
            String bolisa = this.bolisa.getText();
            String carNum = this.carNum.getText();
            String weight = this.weight.getText();
            String nawlon = this.nawlon.getText();
            String ohda = this.ohda.getText();
            String agz = this.agz.getText();
            String office = this.office.getText();
            String notes = this.notes.getText();

            if (clientName

                    || date == null
                    ) {


                dialog dialog = new dialog(Alert.AlertType.ERROR, "خطأ", "ادخل جميع البيانات");

            } else {
                String type = "";
                if (!this.type.getSelectionModel().isEmpty()) {

                    type = this.type.getValue().toString();
                }
                // select Client
                int clientIndex = this.clientName.getSelectionModel().getSelectedIndex();
                int clientId = clientsList_Ids.get(clientIndex);
                // calculated
                double mmezan = Double.parseDouble(weight) - Double.parseDouble(agz);
                double ttotal = mmezan * Double.parseDouble(nawlon);
                double cclear = ttotal - Double.parseDouble(ohda) - Double.parseDouble(office);

                Date date1 = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
                Maintable maintable = new Maintable(
                        date1,
                        bolisa,
                        carNum,
                        Double.parseDouble(weight),
                        Double.parseDouble(nawlon),
                        Double.parseDouble(ohda),
                        mmezan,
                        Double.parseDouble(office),
                        Double.parseDouble(new DecimalFormat(".##").format(ttotal)),
                        type,
                        from,
                        to,
                        Double.parseDouble(agz),
                        cclear, notes,
                        new Clients(clientId)
                );

                maintable.setId(naklTable.getId());


                Maintable maintable1 = mainTableDao.UpdateMaintable(naklTable.getId(), maintable);

                if (maintable1 == null) {
                    dialog dialog = new dialog(Alert.AlertType.ERROR, "خطأ", "خطأ فى التعديل فى الداتابيز ");


                } else {

                    // remove from table
                    boolean t = NaklTable_data.remove(naklTable);
                    if (t) {
                        // insert into table design

                        // insert into table design
                        NaklTable_data.add(new NaklTable(maintable1.getId(), new SimpleDateFormat("dd-MM-yyyy").format(maintable1.getDate()),
                                maintable1.getType(),
                                maintable1.getPolesa(),
                                maintable1.getCarNumber(),
                                maintable1.getAmount(),
                                maintable1.getNowlon(), maintable1.getOhda(),
                                maintable1.getAgz(), maintable1.getMezan(), maintable1.getOffice(),
                                maintable1.getSafy(), maintable1.getCityFrom() + "-" + maintable1.getCityTo(),
                                maintable1.getNotes(), maintable1.getTotal(), this.clientName.getValue().toString()));

                        final TreeItem<NaklTable> root = new RecursiveTreeItem<NaklTable>(NaklTable_data, RecursiveTreeObject::getChildren);
                        table.setRoot(root);
                        table.setShowRoot(false);
// reset values
                        naklTable = null;
                        resetFields();
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

    @FXML
    void updateTableAction(ActionEvent event) {
        selectionUpdateTableAction();
        update.setDisable(false);
        save.setDisable(true);


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


        // set type to combobox
        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "قمح", "أذره", "صويا", "فول", "جيلاتين", "dg"
                );
        type.setItems(options);


        // select All Clients
        clientsList = clientDao.SelectAllClients();
        if (clientsList != null) {
            clientsList_Names = clientsList.stream().map(ee -> ee.getName()).collect(Collectors.toList());
            clientsList_Ids = clientsList.stream().map(ee -> ee.getId()).collect(Collectors.toList());
            clientName.getItems().setAll(clientsList_Names);

        }


        // set disabled
//        update.setDisable(true);
//        save.setDisable(false);


        // --------------- set size ---------------------------
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

        hbox1.setPrefWidth(primaryScreenBounds.getWidth() - 185);
        hbox2.setPrefWidth(primaryScreenBounds.getWidth() - 185);
        hbox3.setPrefWidth(primaryScreenBounds.getWidth() - 185);
        hbox4.setPrefWidth(primaryScreenBounds.getWidth() - 185);
        hboxbtns.setPrefWidth(primaryScreenBounds.getWidth() / 2);
        hboxbtns.setLayoutY(hbox4.getLayoutY() + 50);

        double kashfHesabWid = primaryScreenBounds.getWidth() - 320;
        double kashfHesabHeight = hbox1.getLayoutY() + hbox2.getLayoutY() + hbox3.getLayoutY() + hbox4.getLayoutY() + 50;

        tableHbox.setPrefWidth(primaryScreenBounds.getWidth() - 185);

        tableDate.setPrefWidth(tableHbox.getPrefWidth() / 15);
        tableClientName.setPrefWidth(tableHbox.getPrefWidth() / 15);

        tableNawlon.setPrefWidth(tableHbox.getPrefWidth() / 15);
        tableBian.setPrefWidth(tableHbox.getPrefWidth() / 15);
        tableBolisa.setPrefWidth(tableHbox.getPrefWidth() / 15);
        tableCarNum.setPrefWidth(tableHbox.getPrefWidth() / 15);
        tableClear.setPrefWidth(tableHbox.getPrefWidth() / 15);
        tableTotal.setPrefWidth(tableHbox.getPrefWidth() / 15);
        tableType.setPrefWidth( (tableHbox.getPrefWidth() / 15) + 10  ) ;
        tableAgz.setPrefWidth( tableHbox.getPrefWidth() / (15   ) - 10 );
        tableOhda.setPrefWidth(tableHbox.getPrefWidth() / 15);
        tableOffice.setPrefWidth(tableHbox.getPrefWidth() / 15);
        tableMezan.setPrefWidth(tableHbox.getPrefWidth() / 15);

        tableNotes.setPrefWidth( (tableHbox.getPrefWidth() / 15 ) + 5  );

        tableWeight.setPrefWidth(tableHbox.getPrefWidth() / (15   ) - 10 );

        hboxbtns.setLayoutX((kashfHesabWid / 2) - 50);

//        hboxbtns.setLayoutY(hbox4.getLayoutY() + 50  );


        table.setPrefWidth(primaryScreenBounds.getWidth() - 200);

        lastHbox.setLayoutY(primaryScreenBounds.getHeight() - 75);
//        table.setPrefHeight(primaryScreenBounds.getHeight() - 100);
//        tableHbox.setLayoutY(primaryScreenBounds.getHeight() + 150);
        table.setPrefHeight(primaryScreenBounds.getHeight() - 350);


        hr.setPrefWidth((primaryScreenBounds.getWidth() - 200));


        //set date on init
        String newstring = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        date.setValue(LOCAL_DATE(newstring));

        // ---------------------- init table --------------------------
        maintables = mainTableDao.SelectAllMaintableToday();
        maintables.stream().forEach(maintable1 -> {

            NaklTable_data.add(new NaklTable(maintable1.getId(),
                    new SimpleDateFormat("dd-MM-yyyy").format(maintable1.getDate()), maintable1.getType(),
                    maintable1.getPolesa(), maintable1.getCarNumber(), maintable1.getAmount(),
                    maintable1.getNowlon(), maintable1.getOhda(), maintable1.getAgz(), maintable1.getMezan(),
                    maintable1.getOffice(), maintable1.getSafy()
                    , maintable1.getCityFrom() + " - " + maintable1.getCityTo(), maintable1.getNotes(),
                    maintable1.getTotal(), maintable1.getClientsid().getName()));


        });

        tableDate.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<NaklTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<NaklTable, String> param) {
                return param.getValue().getValue().date;
            }
        });

        tableClientName.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<NaklTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<NaklTable, String> param) {
                return param.getValue().getValue().clientName;
            }
        });

        tableType.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<NaklTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<NaklTable, String> param) {
                return param.getValue().getValue().type;
            }
        });


        tableBolisa.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<NaklTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<NaklTable, String> param) {
                return param.getValue().getValue().bolisa;
            }

        });

        tableCarNum.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<NaklTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<NaklTable, String> param) {
                return param.getValue().getValue().carNum;
            }

        });

        tableWeight.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<NaklTable, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(TreeTableColumn.CellDataFeatures<NaklTable, Double> param) {
                return param.getValue().getValue().weight.asObject();
            }

        });
        tableTotal.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<NaklTable, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(TreeTableColumn.CellDataFeatures<NaklTable, Double> param) {
                return param.getValue().getValue().total.asObject();
            }

        });

        tableNawlon.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<NaklTable, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(TreeTableColumn.CellDataFeatures<NaklTable, Double> param) {
                return param.getValue().getValue().nawlon.asObject();
            }

        });

        tableOhda.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<NaklTable, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(TreeTableColumn.CellDataFeatures<NaklTable, Double> param) {
                return param.getValue().getValue().ohda.asObject();
            }

        });

        tableAgz.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<NaklTable, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(TreeTableColumn.CellDataFeatures<NaklTable, Double> param) {
                return param.getValue().getValue().agz.asObject();
            }

        });


        tableMezan.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<NaklTable, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(TreeTableColumn.CellDataFeatures<NaklTable, Double> param) {
                return param.getValue().getValue().mezan.asObject();
            }

        });


        tableOffice.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<NaklTable, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(TreeTableColumn.CellDataFeatures<NaklTable, Double> param) {
                return param.getValue().getValue().office.asObject();
            }

        });

        tableClear.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<NaklTable, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(TreeTableColumn.CellDataFeatures<NaklTable, Double> param) {
                return param.getValue().getValue().clear.asObject();
            }

        });

        tableBian.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<NaklTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<NaklTable, String> param) {
                return param.getValue().getValue().bian;
            }

        });


        tableNotes.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<NaklTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<NaklTable, String> param) {
                return param.getValue().getValue().notes;
            }

        });

        // select All From Database ------------


//        NaklTable_data.add(new NaklTable(1, "a", 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "ss", "ss"));
        final TreeItem<NaklTable> root = new RecursiveTreeItem<NaklTable>(NaklTable_data, RecursiveTreeObject::getChildren);
        table.setRoot(root);
        table.setShowRoot(false);

        //
        // double click Event
        table.setRowFactory(tv -> {
            TreeTableRow<NaklTable> row = new TreeTableRow<>();
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


        // validate TextFields
//        this.bolisa.setTextFormatter(Validation.DoubleValidation());
//        this.carNum.setTextFormatter(Validation.DoubleValidation());
        this.weight.setTextFormatter(Validation.DoubleValidation());
        this.agz.setTextFormatter(Validation.DoubleValidation());
        this.nawlon.setTextFormatter(Validation.DoubleValidation());
        this.ohda.setTextFormatter(Validation.DoubleValidation());
//        this.mezan.setTextFormatter(Validation.DoubleValidation());
        this.office.setTextFormatter(Validation.DoubleValidation());
        // combobox
        this.clientName.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                if (!newPropertyValue) {

                    if (!clientName.getItems().contains(clientName.getValue())) {
                        clientName.getEditor().clear();
                        clientName.setStyle(" -fx-border-width: 120%;   -fx-border-color: red;");

                    }

                }
                if (newPropertyValue) {

                    clientName.setStyle("    -fx-border-color: transparent;");


                }

            }
        });


        new AutoCompleteComboBoxListener<>(this.clientName);


    }

    public List<String> getTokensWithCollection(String str) {
        List<String> list = Collections.list(new StringTokenizer(str, "-")).stream()
                .map(token -> (String) token)
                .collect(Collectors.toList());
        if (list.size() == 0) {


            list.add("");
            list.add("");
        }
        return list;
    }

    private void selectionUpdateTableAction() {

        RecursiveTreeItem item = (RecursiveTreeItem) table.getSelectionModel().getSelectedItem();
        if (item != null) {
            NaklTable ct = (NaklTable) item.getValue();
            this.naklTable = ct;
            this.type.setValue(ct.getType());
            this.fromCity.setText(getTokensWithCollection(ct.getBian()).get(0) + "");
            this.toCity.setText(getTokensWithCollection(ct.getBian()).get(1) + "");
            this.date.setValue(LOCAL_DATE(ct.getDate()));
            this.bolisa.setText(ct.getBolisa() + "");
            this.carNum.setText(ct.getCarNum() + "");
            this.weight.setText(ct.getWeight() + "");
            this.nawlon.setText(ct.getNawlon() + "");
            this.ohda.setText(ct.getOhda() + "");
            this.agz.setText(ct.getAgz() + "");
            this.office.setText(ct.getOffice() + "");
            this.clientName.getSelectionModel().select(ct.getClientName());


        } else {
            dialog dialog = new dialog(Alert.AlertType.ERROR, "خطأ", "اختر من الجدول للتعديل");

        }


    }


    class NaklTable extends RecursiveTreeObject<NaklTable> {

        SimpleStringProperty date;
        SimpleStringProperty clientName;

        SimpleStringProperty bolisa;
        SimpleStringProperty carNum;
        SimpleDoubleProperty weight;
        SimpleDoubleProperty nawlon;
        SimpleDoubleProperty ohda;
        SimpleDoubleProperty agz;
        SimpleDoubleProperty mezan;
        SimpleDoubleProperty office;
        SimpleDoubleProperty clear;
        SimpleDoubleProperty total;
        SimpleStringProperty bian;
        SimpleStringProperty type;
        SimpleStringProperty notes;
        SimpleIntegerProperty id;

        public NaklTable(int id, String date, String type, String bolisa, String carNum, double weight, double nawlon, double ohda, double agz, double mezan, double office, double clear, String bian, String notes, double total, String clientName) {
            this.date = new SimpleStringProperty(date);
            this.type = new SimpleStringProperty(type);
            this.bolisa = new SimpleStringProperty(bolisa);
            this.carNum = new SimpleStringProperty(carNum);
            this.weight = new SimpleDoubleProperty(weight);
            this.nawlon = new SimpleDoubleProperty(nawlon);
            this.ohda = new SimpleDoubleProperty(ohda);
            this.agz = new SimpleDoubleProperty(agz);
            this.mezan = new SimpleDoubleProperty(mezan);
            this.office = new SimpleDoubleProperty(office);
            this.clear = new SimpleDoubleProperty(clear);
            this.total = new SimpleDoubleProperty(total);

            this.bian = new SimpleStringProperty(bian);
            this.notes = new SimpleStringProperty(notes);
            this.id = new SimpleIntegerProperty(id);
            this.clientName = new SimpleStringProperty(clientName);
        }


        public double getTotal() {
            return total.get();
        }

        public SimpleDoubleProperty totalProperty() {
            return total;
        }

        public void setTotal(double total) {
            this.total.set(total);
        }

        public String getDate() {
            return date.get();
        }

        public SimpleStringProperty dateProperty() {
            return date;
        }

        public void setDate(String date) {
            this.date.set(date);
        }

        public String getBolisa() {
            return bolisa.get();
        }

        public SimpleStringProperty bolisaProperty() {
            return bolisa;
        }

        public void setBolisa(String bolisa) {
            this.bolisa.set(bolisa);
        }

        public String getCarNum() {
            return carNum.get();
        }

        public SimpleStringProperty carNumProperty() {
            return carNum;
        }

        public void setCarNum(String carNum) {
            this.carNum.set(carNum);
        }

        public double getWeight() {
            return weight.get();
        }

        public SimpleDoubleProperty weightProperty() {
            return weight;
        }

        public void setWeight(double weight) {
            this.weight.set(weight);
        }

        public double getNawlon() {
            return nawlon.get();
        }

        public SimpleDoubleProperty nawlonProperty() {
            return nawlon;
        }

        public void setNawlon(double nawlon) {
            this.nawlon.set(nawlon);
        }

        public double getOhda() {
            return ohda.get();
        }

        public SimpleDoubleProperty ohdaProperty() {
            return ohda;
        }

        public void setOhda(double ohda) {
            this.ohda.set(ohda);
        }

        public double getAgz() {
            return agz.get();
        }

        public SimpleDoubleProperty agzProperty() {
            return agz;
        }

        public void setAgz(double agz) {
            this.agz.set(agz);
        }


        public double getMezan() {
            return mezan.get();
        }

        public SimpleDoubleProperty mezanProperty() {
            return mezan;
        }

        public void setMezan(double mezan) {
            this.mezan.set(mezan);
        }


        public double getOffice() {
            return office.get();
        }

        public SimpleDoubleProperty officeProperty() {
            return office;
        }

        public void setOffice(double office) {
            this.office.set(office);
        }

        public double getClear() {
            return clear.get();
        }

        public SimpleDoubleProperty clearProperty() {
            return clear;
        }

        public void setClear(double clear) {
            this.clear.set(clear);
        }

        public String getBian() {
            return bian.get();
        }

        public SimpleStringProperty bianProperty() {
            return bian;
        }

        public void setBian(String bian) {
            this.bian.set(bian);
        }

        public String getType() {
            return type.get();
        }

        public SimpleStringProperty typeProperty() {
            return type;
        }

        public void setType(String type) {
            this.type.set(type);
        }

        public String getNotes() {
            return notes.get();
        }

        public SimpleStringProperty notesProperty() {
            return notes;
        }

        public void setNotes(String notes) {
            this.notes.set(notes);
        }

        public int getId() {
            return id.get();
        }

        public SimpleIntegerProperty idProperty() {
            return id;
        }

        public void setId(int id) {
            this.id.set(id);
        }

        public String getClientName() {
            return clientName.get();
        }

        public SimpleStringProperty clientNameProperty() {
            return clientName;
        }

        public void setClientName(String clientName) {
            this.clientName.set(clientName);
        }
    }


}
