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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.util.Callback;
import sample.app.AddClient.mainAddClient;
import sample.app.Entities.Clients;
import sample.app.Entities.Maintable;
import sample.app.Transactions.ClientDao.clientDao;
import sample.app.Transactions.MainTableDao.mainTableDao;
import sample.app.dialogs.dialog;
import sample.app.main.MainController;
import sample.shared.AutoCompleteComboBoxListener;
import sample.shared.Validation.Validation;

import javax.swing.text.TableView;
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
 * Created by ahmed mar3y on 23/04/2018.
 */
public class naklController implements Initializable {

    @FXML
    private HBox hbox3;

    @FXML
    private TextField clear;

    @FXML
    private TextField added;

    @FXML
    private TextField carNum;

    @FXML
    private TextField fromCity;

    @FXML
    private HBox hbox2;

    @FXML
    private TextField office;

    @FXML
    private TextField ohda;

    @FXML
    private TextField bolisa;

    @FXML
    private TextField type;

    @FXML
    private TreeTableView<NaklTable> table;

    @FXML
    private TreeTableColumn<NaklTable, String> tableDate;

    @FXML
    private TreeTableColumn<NaklTable, Double> tableBolisa;

    @FXML
    private TreeTableColumn<NaklTable, Double> tableCarNum;

    @FXML
    private TreeTableColumn<NaklTable, Double> tableWeight;

    @FXML
    private TreeTableColumn<NaklTable, Double> tableNawlon;

    @FXML
    private TreeTableColumn<NaklTable, Double> tableOhda;

    @FXML
    private TreeTableColumn<NaklTable, Double> tableAgz;

    @FXML
    private TreeTableColumn<NaklTable, Double> tableAdded;

    @FXML
    private TreeTableColumn<NaklTable, Double> tableMezan;

    @FXML
    private TreeTableColumn<NaklTable, Double> tableDiscount;

    @FXML
    private TreeTableColumn<NaklTable, Double> tableOffice;

    @FXML
    private TreeTableColumn<NaklTable, Double> tableClear;

    @FXML
    private TreeTableColumn<NaklTable, String> tableBian;

    @FXML
    private TreeTableColumn<NaklTable, String> tableNotes;

    @FXML
    private HBox hbox1;

    @FXML
    private TextField discount;

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
    private TextField mezan;

    @FXML
    private TextField weight;

    @FXML
    private TextField toCity;

    @FXML
    private JFXButton updateTable;

    @FXML
    private JFXButton print;

    @FXML
    private Pane lastHbox;

    List<Clients> clientsList;
    List<String> clientsList_Names;
    List<Integer> clientsList_Ids;

    List<Maintable> maintables;

    ObservableList<NaklTable> NaklTable_data = FXCollections.observableArrayList();


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
    void printAction(ActionEvent event) {

    }

    @FXML
    void saveAction(ActionEvent event) {

        // save Function
        String clientName = this.clientName.getValue().toString();
        String type = this.type.getText();
        String to = this.toCity.getText();
        String from = this.fromCity.getText();
        LocalDate date = this.date.getValue();
        String bolisa = this.bolisa.getText();
        String carNum = this.carNum.getText();
        String weight = this.weight.getText();
        String nawlon = this.nawlon.getText();
        String ohda = this.ohda.getText();
        String added = this.added.getText();
        String mezan = this.mezan.getText();
        String discount = this.discount.getText();
        String office = this.office.getText();
        String clear = this.clear.getText();

        if (clientName.trim().isEmpty()
                || type.trim().isEmpty()
                || to.trim().isEmpty()
                || from.trim().isEmpty()
                || bolisa.trim().isEmpty()
                || carNum.trim().isEmpty()
                || weight.trim().isEmpty()
                || nawlon.trim().isEmpty()
                || ohda.trim().isEmpty()
                || added.trim().isEmpty()
                || mezan.trim().isEmpty()
                || discount.trim().isEmpty()
                || office.trim().isEmpty()
                || clear.trim().isEmpty()
                || date == null
                ) {


            dialog dialog = new dialog(Alert.AlertType.ERROR, "خطأ", "ادخل جميع البيانات");

        } else {

            // select Client
            int clientIndex = this.clientName.getSelectionModel().getSelectedIndex();
            int clientId = clientsList_Ids.get(clientIndex);
            // calculate total

            double total = (Double.parseDouble(weight) * Double.parseDouble(nawlon)) -
                    Double.parseDouble(ohda) +
                    Double.parseDouble(office) +
                    Double.parseDouble(added);

            Date date1 = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Maintable maintable = new Maintable(date1,
                    Double.parseDouble(bolisa),
                    Double.parseDouble(carNum),
                    Double.parseDouble(weight),
                    Double.parseDouble(nawlon),
                    Double.parseDouble(ohda),
                    Double.parseDouble(added),
                    Double.parseDouble(mezan),
                    Double.parseDouble(discount),
                    Double.parseDouble(office),
                    total,
                    type,
                    from,
                    to,
                    new Clients(clientId)
            );

            Maintable maintable1 = mainTableDao.SaveMaintable(maintable);
            if (maintable1 == null) {
                dialog dialog = new dialog(Alert.AlertType.ERROR, "خطأ", "خطأ فى الحفظ فى الداتابيز ");


            } else {

                // insert into table design
                NaklTable_data.add(new NaklTable(maintable1.getId(), new SimpleDateFormat("dd-MM-yyyy").format(maintable1.getDate()), maintable1.getPolesa(), maintable1.getCarNumber(), maintable1.getAmount(), maintable1.getNowlon(), maintable1.getOhda(), 0.0, maintable1.getAdded(), maintable1.getMezan(), maintable1.getDiscount(), maintable1.getOffice(), maintable1.getTotal(), maintable1.getType(), "Notes"));
                final TreeItem<NaklTable> root = new RecursiveTreeItem<NaklTable>(NaklTable_data, RecursiveTreeObject::getChildren);
                table.setRoot(root);
                table.setShowRoot(false);


            }


        }


    }

    @FXML
    void updateAction(ActionEvent event) {

    }

    @FXML
    void updateTableAction(ActionEvent event) {

    }
    // localdate Formatter

    public static final LocalDate LOCAL_DATE(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse(dateString, formatter);
        return localDate;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        // --------------- set size ---------------------------
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

        hbox1.setPrefWidth(primaryScreenBounds.getWidth() - 185);
        hbox2.setPrefWidth(primaryScreenBounds.getWidth() - 185);
        hbox3.setPrefWidth(primaryScreenBounds.getWidth() - 185);
        hbox4.setPrefWidth(primaryScreenBounds.getWidth() - 185);
        table.setPrefWidth(primaryScreenBounds.getWidth() - 200);

        lastHbox.setLayoutY(primaryScreenBounds.getHeight() - 75);
//        hbox4.setLayoutY(table.getPrefHeight());
        table.setPrefHeight(primaryScreenBounds.getHeight() - 300);
        System.out.println(lastHbox.getPrefHeight());
        System.out.println("table height " + table.getPrefHeight());
        //set date on init
        String newstring = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        date.setValue(LOCAL_DATE(newstring));
        // ---------------------- init table --------------------------
        maintables = mainTableDao.SelectAllMaintableToday();
        maintables.stream().forEach(maintable1 -> {

            NaklTable_data.add(new NaklTable(maintable1.getId(), maintable1.getDate().toString(), maintable1.getPolesa(), maintable1.getCarNumber(), maintable1.getAmount(), maintable1.getNowlon(), maintable1.getOhda(), 0.0, maintable1.getAdded(), maintable1.getMezan(), maintable1.getDiscount(), maintable1.getOffice(), maintable1.getTotal(), maintable1.getType(), "Notes"));


        });

        tableDate.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<NaklTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<NaklTable, String> param) {
                return param.getValue().getValue().date;
            }
        });


        tableBolisa.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<NaklTable, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(TreeTableColumn.CellDataFeatures<NaklTable, Double> param) {
                return param.getValue().getValue().bolisa.asObject();
            }

        });

        tableCarNum.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<NaklTable, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(TreeTableColumn.CellDataFeatures<NaklTable, Double> param) {
                return param.getValue().getValue().carNum.asObject();
            }

        });

        tableWeight.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<NaklTable, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(TreeTableColumn.CellDataFeatures<NaklTable, Double> param) {
                return param.getValue().getValue().weight.asObject();
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

        tableAdded.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<NaklTable, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(TreeTableColumn.CellDataFeatures<NaklTable, Double> param) {
                return param.getValue().getValue().added.asObject();
            }

        });

        tableMezan.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<NaklTable, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(TreeTableColumn.CellDataFeatures<NaklTable, Double> param) {
                return param.getValue().getValue().mezan.asObject();
            }

        });

        tableDiscount.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<NaklTable, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(TreeTableColumn.CellDataFeatures<NaklTable, Double> param) {
                return param.getValue().getValue().discount.asObject();
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
                return param.getValue().getValue().type;
            }

        });


        tableNotes.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<NaklTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<NaklTable, String> param) {
                return param.getValue().getValue().notes;
            }

        });

        // select All From Database ------------


        NaklTable_data.add(new NaklTable(1, "a", 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "ss", "ss"));
        final TreeItem<NaklTable> root = new RecursiveTreeItem<NaklTable>(NaklTable_data, RecursiveTreeObject::getChildren);
        table.setRoot(root);
        table.setShowRoot(false);


        // validate TextFields
        this.bolisa.setTextFormatter(Validation.DoubleValidation());
        this.carNum.setTextFormatter(Validation.DoubleValidation());
        this.weight.setTextFormatter(Validation.DoubleValidation());
        this.nawlon.setTextFormatter(Validation.DoubleValidation());
        this.ohda.setTextFormatter(Validation.DoubleValidation());
        this.added.setTextFormatter(Validation.DoubleValidation());
        this.mezan.setTextFormatter(Validation.DoubleValidation());
        this.discount.setTextFormatter(Validation.DoubleValidation());
        this.office.setTextFormatter(Validation.DoubleValidation());
        this.clear.setTextFormatter(Validation.DoubleValidation());
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


    class NaklTable extends RecursiveTreeObject<NaklTable> {

        SimpleStringProperty date;

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
        SimpleStringProperty type;
        SimpleStringProperty notes;
        SimpleIntegerProperty id;

        public NaklTable(int id, String date, double bolisa, double carNum, double weight, double nawlon, double ohda, double agz, double added, double mezan, double discount, double office, double clear, String type, String notes) {
            this.date = new SimpleStringProperty(date);
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

            this.type = new SimpleStringProperty(type);
            this.notes = new SimpleStringProperty(notes);
            this.id = new SimpleIntegerProperty(id);
        }


        public String getDate() {
            return date.get();
        }

        public StringProperty dateProperty() {
            return date;
        }

        public void setDate(String date) {
            this.date.set(date);
        }

        public double getBolisa() {
            return bolisa.get();
        }

        public DoubleProperty bolisaProperty() {
            return bolisa;
        }

        public void setBolisa(double bolisa) {
            this.bolisa.set(bolisa);
        }

        public double getCarNum() {
            return carNum.get();
        }

        public DoubleProperty carNumProperty() {
            return carNum;
        }

        public void setCarNum(double carNum) {
            this.carNum.set(carNum);
        }

        public double getWeight() {
            return weight.get();
        }

        public DoubleProperty weightProperty() {
            return weight;
        }

        public void setWeight(double weight) {
            this.weight.set(weight);
        }

        public double getNawlon() {
            return nawlon.get();
        }

        public DoubleProperty nawlonProperty() {
            return nawlon;
        }

        public void setNawlon(double nawlon) {
            this.nawlon.set(nawlon);
        }

        public double getOhda() {
            return ohda.get();
        }

        public DoubleProperty ohdaProperty() {
            return ohda;
        }

        public void setOhda(double ohda) {
            this.ohda.set(ohda);
        }

        public double getAgz() {
            return agz.get();
        }

        public DoubleProperty agzProperty() {
            return agz;
        }

        public void setAgz(double agz) {
            this.agz.set(agz);
        }

        public DoubleProperty getAdded() {
            return added;
        }

        public DoubleProperty addedProperty() {
            return added;
        }

        public void setAdded(double added) {
            this.added.set(added);
        }

        public double getMezan() {
            return mezan.get();
        }

        public DoubleProperty mezanProperty() {
            return mezan;
        }

        public void setMezan(double mezan) {
            this.mezan.set(mezan);
        }

        public double getDiscount() {
            return discount.get();
        }

        public DoubleProperty discountProperty() {
            return discount;
        }

        public void setDiscount(double discount) {
            this.discount.set(discount);
        }

        public double getOffice() {
            return office.get();
        }

        public DoubleProperty officeProperty() {
            return office;
        }

        public void setOffice(double office) {
            this.office.set(office);
        }

        public double getClear() {
            return clear.get();
        }

        public DoubleProperty clearProperty() {
            return clear;
        }

        public void setClear(double clear) {
            this.clear.set(clear);
        }

        public String getType() {
            return type.get();
        }

        public StringProperty typeProperty() {
            return type;
        }

        public void setType(String type) {
            this.type.set(type);
        }

        public String getNotes() {
            return notes.get();
        }

        public StringProperty notesProperty() {
            return notes;
        }

        public void setNotes(String notes) {
            this.notes.set(notes);
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
