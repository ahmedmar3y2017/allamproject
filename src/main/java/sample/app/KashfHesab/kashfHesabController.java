package sample.app.KashfHesab;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
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
import sample.app.nakl.naklController;
import sample.shared.AutoCompleteComboBoxListener;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Created by ahmed mar3y on 22/04/2018.
 */
public class kashfHesabController implements Initializable {
    List<Clients> clientsList;
    List<String> clientsList_Names;
    List<Integer> clientsList_Ids;
    List<Maintable> maintables;

    @FXML
    private Pane kashfHesab;

    @FXML
    private HBox tableHbox, hbox1;

    @FXML
    private JFXButton search;


    @FXML
    private DatePicker from;

    @FXML
    private Button addClient;

    @FXML
    private ComboBox clientName;

    @FXML
    private TreeTableView<HesabTable> table;

    @FXML
    private TreeTableColumn<HesabTable, String> tableDate;
    @FXML
    private TreeTableColumn<HesabTable, String> tableType;
    @FXML
    private TreeTableColumn<HesabTable, String> tableBolisa;

    @FXML
    private TreeTableColumn<HesabTable, String> tableCarNum;

    @FXML
    private TreeTableColumn<HesabTable, Double> tableWeight;

    @FXML
    private TreeTableColumn<HesabTable, Double> tableNawlon;

    @FXML
    private TreeTableColumn<HesabTable, Double> tableOhda;

    @FXML
    private TreeTableColumn<HesabTable, Double> tableAgz;


    @FXML
    private TreeTableColumn<HesabTable, Double> tableMezan;


    @FXML
    private TreeTableColumn<HesabTable, Double> tableOffice;

    @FXML
    private TreeTableColumn<HesabTable, Double> tableClear;
    @FXML
    private TreeTableColumn<HesabTable, Double> tableTotal;

    @FXML
    private TreeTableColumn<HesabTable, String> tableBian;

    @FXML
    private TreeTableColumn<HesabTable, String> tableNotes;

    @FXML
    private HBox lasthbox;

    @FXML
    private JFXButton printBtn;

    @FXML
    private TextField totalMoney;
    ObservableList<HesabTable> HesabTable_data = FXCollections.observableArrayList();

    @FXML
    void addClientAction(ActionEvent event) {


        //open another stage
        try {
            mainAddClient mainAddClient = new mainAddClient();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

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
    void printAction(ActionEvent event) throws JRException {

        // check Search By All
        System.out.println("Print Action");

        LocalDate sDate = this.from.getValue();
        Date StartDate = java.util.Date.from(Instant.from(sDate.atStartOfDay(ZoneId.systemDefault())));
//        LocalDate eDate = this.to.getValue();
//        Date EndDate = java.util.Date.from(Instant.from(eDate.atStartOfDay(ZoneId.systemDefault())));

        int clientIndex = clientName.getSelectionModel().getSelectedIndex();
        int clientId = clientsList_Ids.get(clientIndex);

        HashMap<String, Object> hm = new HashMap<>();
        hm.put("datefrom", new SimpleDateFormat("yyyy-MM-dd").format(StartDate));
//        hm.put("dateto", new SimpleDateFormat("yyyy-MM-dd").format(EndDate));
        hm.put("clientid", clientId);
        hm.put("total", totalMoney.getText());            // الصافى
        hm.put("logoPath", getClass().getResource("C:\\jasperreports\\logo.png"));


        // url


        JasperReport jr = JasperCompileManager.compileReport("C:\\jasperreports\\mainclient.jrxml");
        JasperPrint jp = null;
        try {
            jp = JasperFillManager.fillReport(jr, hm, DBConnection.getConnection());
        } catch (JRException e) {
            e.printStackTrace();
        }
        JasperViewer jasperViewer = new JasperViewer(jp, false);
        jasperViewer.setVisible(true);
        jasperViewer.setTitle("كشف حساب عميل");


    }

    @FXML
    void refreshAction(ActionEvent event) {

        this.clientName.setValue("");
        this.from.setValue(null);
//        this.to.setValue(null);

        // clear table
        HesabTable_data.clear();
        final TreeItem<HesabTable> Client_root = new RecursiveTreeItem<HesabTable>(HesabTable_data, RecursiveTreeObject::getChildren);
        table.setRoot(Client_root);

        totalMoney.setText("0.0");

    }

    @FXML
    void searchAction(ActionEvent event) {


        // search Method

        boolean emptyName = clientName.getSelectionModel().isEmpty();
        LocalDate from = this.from.getValue();
//        LocalDate to = this.to.getValue();
        maintables = mainTableDao.SelectAllMaintable();
        totalMoney.setText("0.0");
        if (!maintables.isEmpty()) {
            HesabTable_data.clear();

            // name Only
            if (!emptyName && from == null) {
                printBtn.setDisable(true);

                final double[] sum_clear = {0.0};

                String name = clientName.getSelectionModel().getSelectedItem().toString();

                maintables.stream().filter(maintable1 -> {
                    if (maintable1.getClientsid().getName().equals(name)) {
                        return true;

                    }

                    return false;
                }).forEach(maintable1 -> {
                    sum_clear[0] += maintable1.getSafy();

                    HesabTable_data.add(new HesabTable(maintable1.getId(), maintable1.getDate().toString(),
                            maintable1.getType(), maintable1.getPolesa(), maintable1.getCarNumber(),
                            maintable1.getAmount(), maintable1.getNowlon(), maintable1.getOhda(),
                            maintable1.getAgz(), maintable1.getMezan(), maintable1.getOffice(), maintable1.getSafy(),
                            maintable1.getCityFrom() + " - " + maintable1.getCityTo(), maintable1.getNotes(),
                            maintable1.getTotal(), maintable1.getClientsid().getName()));


                });

                totalMoney.setText(sum_clear[0] + "");


            }
            if (!emptyName && from != null) {
                final double[] sum_clear = {0.0};
                printBtn.setDisable(false);

                String name = clientName.getSelectionModel().getSelectedItem().toString();
                Date fromDate = Date.from(from.atStartOfDay(ZoneId.systemDefault()).toInstant());

                maintables.stream().filter(maintable1 -> {
                    if (maintable1.getClientsid().getName().equals(name) && maintable1.getDate().compareTo(fromDate) == 0) {
                        return true;

                    }

                    return false;
                }).forEach(maintable1 -> {
                    sum_clear[0] += maintable1.getSafy();

                    HesabTable_data.add(new HesabTable(maintable1.getId(), maintable1.getDate().toString(),
                            maintable1.getType(), maintable1.getPolesa(), maintable1.getCarNumber(),
                            maintable1.getAmount(), maintable1.getNowlon(), maintable1.getOhda(),
                            maintable1.getAgz(), maintable1.getMezan(), maintable1.getOffice(), maintable1.getSafy(),
                            maintable1.getCityFrom() + " - " + maintable1.getCityTo(), maintable1.getNotes(),
                            maintable1.getTotal(), maintable1.getClientsid().getName()));

                });
                totalMoney.setText(sum_clear[0] + "");


            }


            if (emptyName && from != null) {
                final double[] sum_clear = {0.0};
                printBtn.setDisable(true);

                Date fromDate = Date.from(from.atStartOfDay(ZoneId.systemDefault()).toInstant());

                maintables.stream().filter(maintable1 -> {
                    if (
                            (maintable1.getDate().compareTo(fromDate) == 0)
                            ) {
                        return true;

                    }

                    return false;
                }).forEach(maintable1 -> {
                    sum_clear[0] += maintable1.getSafy();

                    HesabTable_data.add(new HesabTable(maintable1.getId(), maintable1.getDate().toString(),
                            maintable1.getType(), maintable1.getPolesa(), maintable1.getCarNumber(),
                            maintable1.getAmount(), maintable1.getNowlon(), maintable1.getOhda(),
                            maintable1.getAgz(), maintable1.getMezan(), maintable1.getOffice(), maintable1.getSafy(),
                            maintable1.getCityFrom() + " - " + maintable1.getCityTo(), maintable1.getNotes(),
                            maintable1.getTotal(), maintable1.getClientsid().getName()));

                });
                totalMoney.setText(sum_clear[0] + "");


            }


            final TreeItem<HesabTable> Client_root = new RecursiveTreeItem<HesabTable>(HesabTable_data, RecursiveTreeObject::getChildren);
            table.setRoot(Client_root);

        }

    }

    @FXML
    void totalMoneyAction(ActionEvent event) {

    }

    @FXML
    void displayAllAction(ActionEvent event) {
        // get all and display into table
        totalMoney.setText("0.0");
        printBtn.setDisable(true);
        clientName.setValue("");
        from.setValue(null);
//        to.setValue(null);


        //start
        final double[] sum_clear = {0.0};
        HesabTable_data.clear();
        maintables = mainTableDao.SelectAllMaintable();
        maintables.stream().forEach(maintable1 -> {
            sum_clear[0] += maintable1.getTotal();

            HesabTable_data.add(new HesabTable(maintable1.getId(), maintable1.getDate().toString(),
                    maintable1.getType(), maintable1.getPolesa(), maintable1.getCarNumber(),
                    maintable1.getAmount(), maintable1.getNowlon(), maintable1.getOhda(),
                    maintable1.getAgz(), maintable1.getMezan(), maintable1.getOffice(), maintable1.getSafy(),
                    maintable1.getCityFrom() + " - " + maintable1.getCityTo(), maintable1.getNotes(),
                    maintable1.getTotal(), maintable1.getClientsid().getName()));

        });
        totalMoney.setText(sum_clear[0] + "");

        final TreeItem<HesabTable> root = new RecursiveTreeItem<HesabTable>(HesabTable_data, RecursiveTreeObject::getChildren);
        table.setRoot(root);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        printBtn.setDisable(true);


        // select All Clients
        clientsList = clientDao.SelectAllClients();
        if (clientsList != null) {
            clientsList_Names = clientsList.stream().map(ee -> ee.getName()).collect(Collectors.toList());
            clientsList_Ids = clientsList.stream().map(ee -> ee.getId()).collect(Collectors.toList());
            clientName.getItems().setAll(clientsList_Names);

        }


        // --------------- set size ---------------------------
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        hbox1.setPrefWidth(primaryScreenBounds.getWidth() - 185);
        tableHbox.setPrefWidth(primaryScreenBounds.getWidth() - 200);
//        table.setPrefHeight(primaryScreenBounds.getHeight() - 500);

        tableHbox.setPrefHeight(primaryScreenBounds.getHeight() - 200);

        double kashfHesabWid = primaryScreenBounds.getWidth() - 360;
//        System.out.println("Kashfe hsab width"  + kashfHesabWid);
//        table.setPrefHeight(primaryScreenBounds.getHeight() - 300);
        lasthbox.setLayoutX(kashfHesabWid / 2);
        lasthbox.setLayoutY(primaryScreenBounds.getHeight() - 100);



        tableDate.setPrefWidth(tableHbox.getPrefWidth() / 14);
        tableNawlon.setPrefWidth(tableHbox.getPrefWidth() / 14);
        tableBian.setPrefWidth(tableHbox.getPrefWidth() / 14);
        tableBolisa.setPrefWidth(tableHbox.getPrefWidth() / 14);
        tableCarNum.setPrefWidth(tableHbox.getPrefWidth() / 14);
        tableClear.setPrefWidth(tableHbox.getPrefWidth() / 14);
        tableTotal.setPrefWidth(tableHbox.getPrefWidth() / 14);
        tableType.setPrefWidth( (tableHbox.getPrefWidth() / 14) + 5  ) ;
        tableAgz.setPrefWidth(tableHbox.getPrefWidth() / 14);
        tableOhda.setPrefWidth(tableHbox.getPrefWidth() / 14);
        tableOffice.setPrefWidth(tableHbox.getPrefWidth() / 14);
        tableMezan.setPrefWidth(tableHbox.getPrefWidth() / 14);
        tableNotes.setPrefWidth(tableHbox.getPrefWidth() / 14);
        tableWeight.setPrefWidth(tableHbox.getPrefWidth() / (14 + 5  ));


        // ---------------- init table ------------------

        tableDate.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<HesabTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<HesabTable, String> param) {
                return param.getValue().getValue().date;
            }
        });

        tableType.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<HesabTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<HesabTable, String> param) {
                return param.getValue().getValue().type;
            }
        });


        tableBolisa.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<HesabTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<HesabTable, String> param) {
                return param.getValue().getValue().bolisa;
            }

        });

        tableCarNum.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<HesabTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<HesabTable, String> param) {
                return param.getValue().getValue().carNum;
            }

        });

        tableWeight.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<HesabTable, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(TreeTableColumn.CellDataFeatures<HesabTable, Double> param) {
                return param.getValue().getValue().weight.asObject();
            }

        });

        tableNawlon.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<HesabTable, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(TreeTableColumn.CellDataFeatures<HesabTable, Double> param) {
                return param.getValue().getValue().nawlon.asObject();
            }

        });

        tableOhda.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<HesabTable, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(TreeTableColumn.CellDataFeatures<HesabTable, Double> param) {
                return param.getValue().getValue().ohda.asObject();
            }

        });

        tableAgz.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<HesabTable, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(TreeTableColumn.CellDataFeatures<HesabTable, Double> param) {
                return param.getValue().getValue().agz.asObject();
            }

        });


        tableMezan.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<HesabTable, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(TreeTableColumn.CellDataFeatures<HesabTable, Double> param) {
                return param.getValue().getValue().mezan.asObject();
            }

        });


        tableOffice.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<HesabTable, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(TreeTableColumn.CellDataFeatures<HesabTable, Double> param) {
                return param.getValue().getValue().office.asObject();
            }

        });

        tableClear.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<HesabTable, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(TreeTableColumn.CellDataFeatures<HesabTable, Double> param) {
                return param.getValue().getValue().clear.asObject();
            }

        });
        tableTotal.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<HesabTable, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(TreeTableColumn.CellDataFeatures<HesabTable, Double> param) {
                return param.getValue().getValue().total.asObject();
            }

        });

        tableBian.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<HesabTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<HesabTable, String> param) {
                return param.getValue().getValue().bian;
            }

        });


        tableNotes.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<HesabTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<HesabTable, String> param) {
                return param.getValue().getValue().notes;
            }

        });


//        HesabTable_data.add(new HesabTable(1, "a", 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "ss", "ss"));
        final TreeItem<HesabTable> root = new RecursiveTreeItem<HesabTable>(HesabTable_data, RecursiveTreeObject::getChildren);
        table.setRoot(root);
        table.setShowRoot(false);

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


    class HesabTable extends RecursiveTreeObject<HesabTable> {

        SimpleStringProperty date;
        SimpleStringProperty clientName;

        SimpleStringProperty bolisa;
        SimpleStringProperty carNum;
        SimpleDoubleProperty weight;
        SimpleDoubleProperty nawlon;
        SimpleDoubleProperty ohda;
        SimpleDoubleProperty agz;
        //        SimpleDoubleProperty added;
        SimpleDoubleProperty mezan;
        //        SimpleDoubleProperty discount;
        SimpleDoubleProperty office;
        SimpleDoubleProperty clear;
        SimpleDoubleProperty total;
        SimpleStringProperty bian;
        SimpleStringProperty type;
        SimpleStringProperty notes;
        SimpleIntegerProperty id;

        public HesabTable(int id, String date, String type, String bolisa, String carNum, double weight, double nawlon, double ohda, double agz, double mezan, double office, double clear, String bian, String notes, double total, String clientName) {
            this.date = new SimpleStringProperty(date);
            this.type = new SimpleStringProperty(type);
            this.bolisa = new SimpleStringProperty(bolisa);
            this.carNum = new SimpleStringProperty(carNum);
            this.weight = new SimpleDoubleProperty(weight);
            this.nawlon = new SimpleDoubleProperty(nawlon);
            this.ohda = new SimpleDoubleProperty(ohda);
            this.agz = new SimpleDoubleProperty(agz);
//            this.added = new SimpleDoubleProperty(added);
            this.mezan = new SimpleDoubleProperty(mezan);
//            this.discount = new SimpleDoubleProperty(discount);
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
