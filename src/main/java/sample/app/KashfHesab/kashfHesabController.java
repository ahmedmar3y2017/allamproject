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
import sample.app.AddClient.mainAddClient;
import sample.app.Entities.Clients;
import sample.app.Entities.Maintable;
import sample.app.Transactions.ClientDao.clientDao;
import sample.app.Transactions.MainTableDao.mainTableDao;
import sample.app.nakl.naklController;
import sample.shared.AutoCompleteComboBoxListener;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
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
    private HBox hbox1;

    @FXML
    private JFXButton search;

    @FXML
    private DatePicker to;

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
    private TreeTableColumn<HesabTable, Double> tableBolisa;

    @FXML
    private TreeTableColumn<HesabTable, Double> tableCarNum;

    @FXML
    private TreeTableColumn<HesabTable, Double> tableWeight;

    @FXML
    private TreeTableColumn<HesabTable, Double> tableNawlon;

    @FXML
    private TreeTableColumn<HesabTable, Double> tableOhda;

    @FXML
    private TreeTableColumn<HesabTable, Double> tableAgz;

    @FXML
    private TreeTableColumn<HesabTable, Double> tableAdded;

    @FXML
    private TreeTableColumn<HesabTable, Double> tableMezan;

    @FXML
    private TreeTableColumn<HesabTable, Double> tableDiscount;

    @FXML
    private TreeTableColumn<HesabTable, Double> tableOffice;

    @FXML
    private TreeTableColumn<HesabTable, Double> tableClear;

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
    void printAction(ActionEvent event) {

    }

    @FXML
    void refreshAction(ActionEvent event) {

        this.clientName.setValue("");
        this.from.setValue(null);
        this.to.setValue(null);

        // clear table
        HesabTable_data.clear();
        final TreeItem<HesabTable> Client_root = new RecursiveTreeItem<HesabTable>(HesabTable_data, RecursiveTreeObject::getChildren);
        table.setRoot(Client_root);

    }

    @FXML
    void searchAction(ActionEvent event) {


        // search Method

        boolean emptyName = clientName.getSelectionModel().isEmpty();
        LocalDate from = this.from.getValue();
        LocalDate to = this.to.getValue();
        maintables = mainTableDao.SelectAllMaintable();
        if (!maintables.isEmpty()) {
            HesabTable_data.clear();

            // name Only
            if (!emptyName && from == null && to == null) {

                String name = clientName.getSelectionModel().getSelectedItem().toString();

                maintables.stream().filter(maintable1 -> {
                    if (maintable1.getClientsid().getName().equals(name)) {
                        return true;

                    }

                    return false;
                }).forEach(maintable1 -> {

                    HesabTable_data.add(new HesabTable(maintable1.getId(), maintable1.getDate().toString(), maintable1.getType(), maintable1.getPolesa(), maintable1.getCarNumber(), maintable1.getAmount(), maintable1.getNowlon(), maintable1.getOhda(), 0.0, maintable1.getAdded(), maintable1.getMezan(), maintable1.getDiscount(), maintable1.getOffice(), maintable1.getTotal(), maintable1.getCityFrom() + " - " + maintable1.getCityTo(), "Notes", maintable1.getClientsid().getName()));


                });


            }
            if (!emptyName && from != null && to == null) {

                String name = clientName.getSelectionModel().getSelectedItem().toString();
                Date fromDate = Date.from(from.atStartOfDay(ZoneId.systemDefault()).toInstant());

                maintables.stream().filter(maintable1 -> {
                    if (maintable1.getClientsid().getName().equals(name) && (maintable1.getDate().after(fromDate) || maintable1.getDate().compareTo(fromDate) == 0)) {
                        return true;

                    }

                    return false;
                }).forEach(maintable1 -> {

                    HesabTable_data.add(new HesabTable(maintable1.getId(), maintable1.getDate().toString(), maintable1.getType(), maintable1.getPolesa(), maintable1.getCarNumber(), maintable1.getAmount(), maintable1.getNowlon(), maintable1.getOhda(), 0.0, maintable1.getAdded(), maintable1.getMezan(), maintable1.getDiscount(), maintable1.getOffice(), maintable1.getTotal(), maintable1.getCityFrom() + " - " + maintable1.getCityTo(), "Notes", maintable1.getClientsid().getName()));


                });


            }
            if (!emptyName && from == null && to != null) {

                String name = clientName.getSelectionModel().getSelectedItem().toString();
                Date toDate = Date.from(to.atStartOfDay(ZoneId.systemDefault()).toInstant());

                maintables.stream().filter(maintable1 -> {
                    if (maintable1.getClientsid().getName().equals(name) && (maintable1.getDate().before(toDate) || maintable1.getDate().compareTo(toDate) == 0)) {
                        return true;

                    }

                    return false;
                }).forEach(maintable1 -> {

                    HesabTable_data.add(new HesabTable(maintable1.getId(), maintable1.getDate().toString(), maintable1.getType(), maintable1.getPolesa(), maintable1.getCarNumber(), maintable1.getAmount(), maintable1.getNowlon(), maintable1.getOhda(), 0.0, maintable1.getAdded(), maintable1.getMezan(), maintable1.getDiscount(), maintable1.getOffice(), maintable1.getTotal(), maintable1.getCityFrom() + " - " + maintable1.getCityTo(), "Notes", maintable1.getClientsid().getName()));


                });


            }

            if (!emptyName && from != null && to != null) {

                String name = clientName.getSelectionModel().getSelectedItem().toString();
                Date toDate = Date.from(to.atStartOfDay(ZoneId.systemDefault()).toInstant());
                Date fromDate = Date.from(from.atStartOfDay(ZoneId.systemDefault()).toInstant());

                maintables.stream().filter(maintable1 -> {
                    if (maintable1.getClientsid().getName().equals(name) &&
                            (maintable1.getDate().before(toDate) || maintable1.getDate().compareTo(toDate) == 0) &&
                            (maintable1.getDate().after(fromDate) || maintable1.getDate().compareTo(fromDate) == 0)
                            ) {
                        return true;

                    }

                    return false;
                }).forEach(maintable1 -> {

                    HesabTable_data.add(new HesabTable(maintable1.getId(), maintable1.getDate().toString(), maintable1.getType(), maintable1.getPolesa(), maintable1.getCarNumber(), maintable1.getAmount(), maintable1.getNowlon(), maintable1.getOhda(), 0.0, maintable1.getAdded(), maintable1.getMezan(), maintable1.getDiscount(), maintable1.getOffice(), maintable1.getTotal(), maintable1.getCityFrom() + " - " + maintable1.getCityTo(), "Notes", maintable1.getClientsid().getName()));


                });


            }

            if (emptyName && from != null && to != null) {

                Date toDate = Date.from(to.atStartOfDay(ZoneId.systemDefault()).toInstant());
                Date fromDate = Date.from(from.atStartOfDay(ZoneId.systemDefault()).toInstant());

                maintables.stream().filter(maintable1 -> {
                    if (
                            (maintable1.getDate().before(toDate) || maintable1.getDate().compareTo(toDate) == 0) &&
                                    (maintable1.getDate().after(fromDate) || maintable1.getDate().compareTo(fromDate) == 0)
                            ) {
                        return true;

                    }

                    return false;
                }).forEach(maintable1 -> {

                    HesabTable_data.add(new HesabTable(maintable1.getId(), maintable1.getDate().toString(), maintable1.getType(), maintable1.getPolesa(), maintable1.getCarNumber(), maintable1.getAmount(), maintable1.getNowlon(), maintable1.getOhda(), 0.0, maintable1.getAdded(), maintable1.getMezan(), maintable1.getDiscount(), maintable1.getOffice(), maintable1.getTotal(), maintable1.getCityFrom() + " - " + maintable1.getCityTo(), "Notes", maintable1.getClientsid().getName()));


                });


            }
            if (emptyName && from != null && to == null) {

                Date fromDate = Date.from(from.atStartOfDay(ZoneId.systemDefault()).toInstant());

                maintables.stream().filter(maintable1 -> {
                    if (
                            (maintable1.getDate().after(fromDate) || maintable1.getDate().compareTo(fromDate) == 0)
                            ) {
                        return true;

                    }

                    return false;
                }).forEach(maintable1 -> {

                    HesabTable_data.add(new HesabTable(maintable1.getId(), maintable1.getDate().toString(), maintable1.getType(), maintable1.getPolesa(), maintable1.getCarNumber(), maintable1.getAmount(), maintable1.getNowlon(), maintable1.getOhda(), 0.0, maintable1.getAdded(), maintable1.getMezan(), maintable1.getDiscount(), maintable1.getOffice(), maintable1.getTotal(), maintable1.getCityFrom() + " - " + maintable1.getCityTo(), "Notes", maintable1.getClientsid().getName()));


                });


            }
            if (emptyName && from == null && to != null) {

                Date toDate = Date.from(to.atStartOfDay(ZoneId.systemDefault()).toInstant());

                maintables.stream().filter(maintable1 -> {
                    if (
                            (maintable1.getDate().before(toDate) || maintable1.getDate().compareTo(toDate) == 0)
                            ) {
                        return true;

                    }

                    return false;
                }).forEach(maintable1 -> {

                    HesabTable_data.add(new HesabTable(maintable1.getId(), maintable1.getDate().toString(), maintable1.getType(), maintable1.getPolesa(), maintable1.getCarNumber(), maintable1.getAmount(), maintable1.getNowlon(), maintable1.getOhda(), 0.0, maintable1.getAdded(), maintable1.getMezan(), maintable1.getDiscount(), maintable1.getOffice(), maintable1.getTotal(), maintable1.getCityFrom() + " - " + maintable1.getCityTo(), "Notes", maintable1.getClientsid().getName()));


                });


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
        System.out.println("Done");
        HesabTable_data.clear();
        maintables = mainTableDao.SelectAllMaintable();
        maintables.stream().forEach(maintable1 -> {

            HesabTable_data.add(new HesabTable(maintable1.getId(), maintable1.getDate().toString(), maintable1.getType(), maintable1.getPolesa(), maintable1.getCarNumber(), maintable1.getAmount(), maintable1.getNowlon(), maintable1.getOhda(), 0.0, maintable1.getAdded(), maintable1.getMezan(), maintable1.getDiscount(), maintable1.getOffice(), maintable1.getTotal(), maintable1.getCityFrom() + " - " + maintable1.getCityTo(), "Notes", maintable1.getClientsid().getName()));


        });
        final TreeItem<HesabTable> root = new RecursiveTreeItem<HesabTable>(HesabTable_data, RecursiveTreeObject::getChildren);
        table.setRoot(root);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

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
        table.setPrefWidth(primaryScreenBounds.getWidth() - 200);

        double kashfHesabWid = primaryScreenBounds.getWidth() - 360;
//        System.out.println("Kashfe hsab width"  + kashfHesabWid);
        table.setPrefHeight(primaryScreenBounds.getHeight() - 200);
        lasthbox.setLayoutX(kashfHesabWid / 2);
        lasthbox.setLayoutY(primaryScreenBounds.getHeight() - 100);

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


        tableBolisa.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<HesabTable, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(TreeTableColumn.CellDataFeatures<HesabTable, Double> param) {
                return param.getValue().getValue().bolisa.asObject();
            }

        });

        tableCarNum.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<HesabTable, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(TreeTableColumn.CellDataFeatures<HesabTable, Double> param) {
                return param.getValue().getValue().carNum.asObject();
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

        tableAdded.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<HesabTable, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(TreeTableColumn.CellDataFeatures<HesabTable, Double> param) {
                return param.getValue().getValue().added.asObject();
            }

        });

        tableMezan.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<HesabTable, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(TreeTableColumn.CellDataFeatures<HesabTable, Double> param) {
                return param.getValue().getValue().mezan.asObject();
            }

        });

        tableDiscount.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<HesabTable, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(TreeTableColumn.CellDataFeatures<HesabTable, Double> param) {
                return param.getValue().getValue().discount.asObject();
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

    @FXML
    void printAction() {
    }


    class HesabTable extends RecursiveTreeObject<HesabTable> {

        SimpleStringProperty date;
        SimpleStringProperty clientName;

        SimpleDoubleProperty bolisa;
        SimpleDoubleProperty carNum;
        SimpleDoubleProperty weight;
        SimpleDoubleProperty nawlon;
        SimpleDoubleProperty ohda;
        SimpleDoubleProperty agz;
        SimpleDoubleProperty added;
        SimpleDoubleProperty mezan;
        SimpleDoubleProperty discount;
        SimpleDoubleProperty office;
        SimpleDoubleProperty clear;
        SimpleStringProperty bian;
        SimpleStringProperty type;
        SimpleStringProperty notes;
        SimpleIntegerProperty id;

        public HesabTable(int id, String date, String type, double bolisa, double carNum, double weight, double nawlon, double ohda, double agz, double added, double mezan, double discount, double office, double clear, String bian, String notes, String clientName) {
            this.date = new SimpleStringProperty(date);
            this.type = new SimpleStringProperty(type);
            this.bolisa = new SimpleDoubleProperty(bolisa);
            this.carNum = new SimpleDoubleProperty(carNum);
            this.weight = new SimpleDoubleProperty(weight);
            this.nawlon = new SimpleDoubleProperty(nawlon);
            this.ohda = new SimpleDoubleProperty(ohda);
            this.agz = new SimpleDoubleProperty(agz);
            this.added = new SimpleDoubleProperty(added);
            this.mezan = new SimpleDoubleProperty(mezan);
            this.discount = new SimpleDoubleProperty(discount);
            this.office = new SimpleDoubleProperty(office);
            this.clear = new SimpleDoubleProperty(clear);

            this.bian = new SimpleStringProperty(bian);
            this.notes = new SimpleStringProperty(notes);
            this.id = new SimpleIntegerProperty(id);
            this.clientName = new SimpleStringProperty(clientName);
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

        public double getBolisa() {
            return bolisa.get();
        }

        public SimpleDoubleProperty bolisaProperty() {
            return bolisa;
        }

        public void setBolisa(double bolisa) {
            this.bolisa.set(bolisa);
        }

        public double getCarNum() {
            return carNum.get();
        }

        public SimpleDoubleProperty carNumProperty() {
            return carNum;
        }

        public void setCarNum(double carNum) {
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

        public double getAdded() {
            return added.get();
        }

        public SimpleDoubleProperty addedProperty() {
            return added;
        }

        public void setAdded(double added) {
            this.added.set(added);
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

        public double getDiscount() {
            return discount.get();
        }

        public SimpleDoubleProperty discountProperty() {
            return discount;
        }

        public void setDiscount(double discount) {
            this.discount.set(discount);
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
