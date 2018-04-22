package sample.app.main;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import sample.app.Entities.Clients;
import sample.app.Tab1.tab1PageController;
import sample.app.Transactions.ClientDao.clientDao;
import sample.shared.Validation.Validation;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Created by ahmed mar3y on 08/04/2018.
 */
public class MainController implements Initializable {


    ObservableList<NaklTable> NaklTable_data = FXCollections.observableArrayList();

    // --------------------- tabs -----------------------
    @FXML
    private JFXTabPane tabPane;

    @FXML
    private Tab nakl;

    @FXML
    private Tab kashfHesab;

    @FXML
    private Tab accounts;

    @FXML
    private Tab money;

    @FXML
    private Tab clients;


    @FXML
    private TreeTableView<NaklTable> tableview;
    @FXML
    private TreeTableColumn<NaklTable, String> NaklTable_date;
    @FXML
    private TreeTableColumn<NaklTable, Double> NaklTable_bolisa;
    @FXML
    private TreeTableColumn<NaklTable, Double> NaklTable_carNum;
    @FXML
    private TreeTableColumn<NaklTable, Double> NaklTable_weight;
    @FXML
    private TreeTableColumn<NaklTable, Double> NaklTable_nawlon;
    @FXML
    private TreeTableColumn<NaklTable, Double> NaklTable_ohda;
    @FXML
    private TreeTableColumn<NaklTable, Double> NaklTable_agz;
    @FXML
    private TreeTableColumn<NaklTable, Double> NaklTable_added;

    @FXML
    private TreeTableColumn<NaklTable, Double> NaklTable_mezan;


    @FXML
    private TreeTableColumn<NaklTable, Double> NaklTable_discount;


    @FXML
    private TreeTableColumn<NaklTable, Double> NaklTable_office;


    @FXML
    private TreeTableColumn<NaklTable, Double> NaklTable_clear;
    @FXML
    private TreeTableColumn<NaklTable, String> NaklTable_type;
    @FXML
    private TreeTableColumn<NaklTable, String> NaklTable_notes;


    @FXML
    private JFXButton update;

    @FXML
    private JFXButton delete;

    @FXML
    private DatePicker from;

    @FXML
    private DatePicker to;

    @FXML
    private DatePicker date;

    @FXML
    private TextField bolisa;

    @FXML
    private TextField carNum;

    @FXML
    private TextField weight;

    @FXML
    private TextField nawlon;

    @FXML
    private TextField ohda;

    @FXML
    private TextField added;

    @FXML
    private TextField mezan;

    @FXML
    private TextField discount;

    @FXML
    private TextField officeNum;

    @FXML
    private TextField clear;

    @FXML
    private Button addclient;

    @FXML
    private JFXButton save;

    @FXML
    private ComboBox clientName;

    @FXML
    private JFXDrawer drawer;

    @FXML
    private JFXHamburger ham1;

    // nakl variable
    List<Clients> clientsList;
    List<String> clientsList_Names;
    List<Integer> clientsList_Ids;


    // ----------------------------------------- Methods  -------------------------------------------

    // nakl TabPane

    @FXML
    void addclientAction(ActionEvent event) {

    }

    @FXML
    void deleteAction(ActionEvent event) {

    }

    @FXML
    void saveAction(ActionEvent event) {

    }

    @FXML
    void updateAction(ActionEvent event) {

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {



//        SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
//        selectionModel.select(nakl); //select by object


        // load
        FXMLLoader load = new FXMLLoader();

        Parent v = null;
        try {
            v = load.load(getClass().getResource("/Fxml/vbox.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        drawer.setSidePane(v);

        HamburgerBackArrowBasicTransition hab2 = new HamburgerBackArrowBasicTransition(ham1);
        hab2.setRate(-1);
        ham1.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {
            hab2.setRate(hab2.getRate() * -1);

            hab2.play();
            if (drawer.isShown()) {
                drawer.close();
            } else {

                drawer.open();

            }

        });


        tabPane.getSelectionModel().selectedItemProperty().
                addListener(new ChangeListener<Tab>() {
                    @Override
                    public void changed(ObservableValue<? extends Tab> ov, Tab t, Tab t1) {
                        if (t1.getId().equals("nakl")) {
                            System.out.println("nakl");
                            // select all cliets from table client
                            clientsList = clientDao.SelectAllClients();
                            clientsList_Names = clientsList.stream().map(map -> map.getName()).collect(Collectors.toList());
                            clientsList_Ids = clientsList.stream().map(map -> map.getId()).collect(Collectors.toList());
                            // set to combobox Values
                            clientName.getItems().setAll(clientsList_Names);



                        }
                        if (t1.getId().equals("kashfHesab")) {
                            System.out.println("kashfHesab");

                        }
                        if (t1.getId().equals("accounts")) {
                            System.out.println("accounts");

                        }
                        if (t1.getId().equals("money")) {
                            System.out.println("money");

                        }
                        if (t1.getId().equals("clients")) {
                            System.out.println("clients");

                        }
                    }
                });


//       ******************************* Main************************************


        NaklTable_date.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<NaklTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<NaklTable, String> param) {
                return param.getValue().getValue().date;
            }
        });


        NaklTable_bolisa.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<NaklTable, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(TreeTableColumn.CellDataFeatures<NaklTable, Double> param) {
                return param.getValue().getValue().bolisa.asObject();
            }

        });

        NaklTable_carNum.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<NaklTable, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(TreeTableColumn.CellDataFeatures<NaklTable, Double> param) {
                return param.getValue().getValue().carNum.asObject();
            }

        });

        NaklTable_weight.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<NaklTable, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(TreeTableColumn.CellDataFeatures<NaklTable, Double> param) {
                return param.getValue().getValue().weight.asObject();
            }

        });

        NaklTable_nawlon.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<NaklTable, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(TreeTableColumn.CellDataFeatures<NaklTable, Double> param) {
                return param.getValue().getValue().nawlon.asObject();
            }

        });

        NaklTable_ohda.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<NaklTable, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(TreeTableColumn.CellDataFeatures<NaklTable, Double> param) {
                return param.getValue().getValue().ohda.asObject();
            }

        });

        NaklTable_agz.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<NaklTable, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(TreeTableColumn.CellDataFeatures<NaklTable, Double> param) {
                return param.getValue().getValue().agz.asObject();
            }

        });

        NaklTable_added.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<NaklTable, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(TreeTableColumn.CellDataFeatures<NaklTable, Double> param) {
                return param.getValue().getValue().added.asObject();
            }

        });

        NaklTable_mezan.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<NaklTable, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(TreeTableColumn.CellDataFeatures<NaklTable, Double> param) {
                return param.getValue().getValue().mezan.asObject();
            }

        });

        NaklTable_discount.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<NaklTable, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(TreeTableColumn.CellDataFeatures<NaklTable, Double> param) {
                return param.getValue().getValue().discount.asObject();
            }

        });

        NaklTable_office.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<NaklTable, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(TreeTableColumn.CellDataFeatures<NaklTable, Double> param) {
                return param.getValue().getValue().office.asObject();
            }

        });

        NaklTable_clear.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<NaklTable, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(TreeTableColumn.CellDataFeatures<NaklTable, Double> param) {
                return param.getValue().getValue().clear.asObject();
            }

        });

        NaklTable_type.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<NaklTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<NaklTable, String> param) {
                return param.getValue().getValue().type;
            }

        });


        NaklTable_notes.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<NaklTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<NaklTable, String> param) {
                return param.getValue().getValue().notes;
            }

        });

        NaklTable_data.add(new NaklTable(1, "a", 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "ss", "ss"));
        final TreeItem<NaklTable> root = new RecursiveTreeItem<NaklTable>(NaklTable_data, RecursiveTreeObject::getChildren);
//        tableview.getColumns().setAll(NaklTable_date, NaklTable_bolisa, NaklTable_carNum, NaklTable_weight, NaklTable_nawlon, NaklTable_ohda, NaklTable_agz, NaklTable_added, NaklTable_mezan, NaklTable_discount, NaklTable_office, NaklTable_clear, NaklTable_type, NaklTable_notes);
        tableview.setRoot(root);
        tableview.setShowRoot(false);


//        text validation

        this.bolisa.setTextFormatter(Validation.DoubleValidation());
        this.carNum.setTextFormatter(Validation.DoubleValidation());
        this.weight.setTextFormatter(Validation.DoubleValidation());
        this.nawlon.setTextFormatter(Validation.DoubleValidation());
        this.ohda.setTextFormatter(Validation.DoubleValidation());
        this.added.setTextFormatter(Validation.DoubleValidation());
        this.mezan.setTextFormatter(Validation.DoubleValidation());
        this.discount.setTextFormatter(Validation.DoubleValidation());
        this.officeNum.setTextFormatter(Validation.DoubleValidation());
        this.clear.setTextFormatter(Validation.DoubleValidation());

        // -------------------------------------------- account -------------------------------


        JFXTreeTableColumn<NaklTable, String> Account_date = new JFXTreeTableColumn<>("التاريخ");
        Account_date.setPrefWidth(93);
        Account_date.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<NaklTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<NaklTable, String> param) {
                return param.getValue().getValue().date;
            }
        });

        JFXTreeTableColumn<NaklTable, Double> Account_bolisa = new JFXTreeTableColumn<>("البوليصه");
        Account_bolisa.setPrefWidth(88);
        Account_bolisa.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<NaklTable, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(TreeTableColumn.CellDataFeatures<NaklTable, Double> param) {
                return param.getValue().getValue().bolisa.asObject();
            }

        });
        JFXTreeTableColumn<NaklTable, Double> Account_carNum = new JFXTreeTableColumn<>("السياره");
        Account_carNum.setPrefWidth(88);
        Account_carNum.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<NaklTable, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(TreeTableColumn.CellDataFeatures<NaklTable, Double> param) {
                return param.getValue().getValue().carNum.asObject();
            }

        });
        JFXTreeTableColumn<NaklTable, Double> Account_weight = new JFXTreeTableColumn<>("الوزن");
        Account_weight.setPrefWidth(88);
        Account_weight.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<NaklTable, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(TreeTableColumn.CellDataFeatures<NaklTable, Double> param) {
                return param.getValue().getValue().weight.asObject();
            }

        });
        JFXTreeTableColumn<NaklTable, Double> Account_nawlon = new JFXTreeTableColumn<>("النولون");
        Account_nawlon.setPrefWidth(88);
        Account_nawlon.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<NaklTable, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(TreeTableColumn.CellDataFeatures<NaklTable, Double> param) {
                return param.getValue().getValue().nawlon.asObject();
            }

        });
        JFXTreeTableColumn<NaklTable, Double> Account_ohda = new JFXTreeTableColumn<>("عهده");
        Account_ohda.setPrefWidth(88);
        Account_ohda.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<NaklTable, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(TreeTableColumn.CellDataFeatures<NaklTable, Double> param) {
                return param.getValue().getValue().ohda.asObject();
            }

        });
        JFXTreeTableColumn<NaklTable, Double> Account_agz = new JFXTreeTableColumn<>("عجز");
        Account_agz.setPrefWidth(88);
        Account_agz.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<NaklTable, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(TreeTableColumn.CellDataFeatures<NaklTable, Double> param) {
                return param.getValue().getValue().agz.asObject();
            }

        });
        JFXTreeTableColumn<NaklTable, Double> Account_added = new JFXTreeTableColumn<>("اضافه");
        Account_added.setPrefWidth(88);
        Account_added.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<NaklTable, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(TreeTableColumn.CellDataFeatures<NaklTable, Double> param) {
                return param.getValue().getValue().added.asObject();
            }

        });

        JFXTreeTableColumn<NaklTable, Double> Account_mezan = new JFXTreeTableColumn<>("الميزان");
        Account_mezan.setPrefWidth(88);
        Account_mezan.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<NaklTable, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(TreeTableColumn.CellDataFeatures<NaklTable, Double> param) {
                return param.getValue().getValue().mezan.asObject();
            }

        });
        JFXTreeTableColumn<NaklTable, Double> Account_discount = new JFXTreeTableColumn<>("خصم");
        Account_discount.setPrefWidth(88);
        Account_discount.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<NaklTable, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(TreeTableColumn.CellDataFeatures<NaklTable, Double> param) {
                return param.getValue().getValue().discount.asObject();
            }

        });
        JFXTreeTableColumn<NaklTable, Double> Account_office = new JFXTreeTableColumn<>("مكتب");
        Account_office.setPrefWidth(88);
        Account_office.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<NaklTable, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(TreeTableColumn.CellDataFeatures<NaklTable, Double> param) {
                return param.getValue().getValue().office.asObject();
            }

        });
        JFXTreeTableColumn<NaklTable, Double> Account_clear = new JFXTreeTableColumn<>("صافى");
        Account_clear.setPrefWidth(88);
        Account_clear.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<NaklTable, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(TreeTableColumn.CellDataFeatures<NaklTable, Double> param) {
                return param.getValue().getValue().clear.asObject();
            }

        });
        JFXTreeTableColumn<NaklTable, String> Account_type = new JFXTreeTableColumn<>("بيان");
        Account_type.setPrefWidth(88);
        Account_type.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<NaklTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<NaklTable, String> param) {
                return param.getValue().getValue().type;
            }

        });

        JFXTreeTableColumn<NaklTable, String> Account_notes = new JFXTreeTableColumn<>("ملاحظات");
        Account_notes.setPrefWidth(88);
        Account_notes.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<NaklTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<NaklTable, String> param) {
                return param.getValue().getValue().notes;
            }

        });

        account_data.add(new NaklTable(1, "a", 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "ss", "ss"));
        final TreeItem<NaklTable> Account_root = new RecursiveTreeItem<NaklTable>(account_data, RecursiveTreeObject::getChildren);
        accounTableview.getColumns().setAll(Account_date, Account_bolisa, Account_carNum, Account_weight, Account_nawlon, Account_ohda, Account_agz, Account_added, Account_mezan, Account_discount, Account_office, Account_clear, Account_type, Account_notes);
        accounTableview.setRoot(Account_root);
        accounTableview.setShowRoot(false);

        // ********************** ******************** client *********************
        JFXTreeTableColumn<ClientTable, String> ClientTable_name = new JFXTreeTableColumn<>("اسم العميل");
        ClientTable_name.setPrefWidth(265);
        ClientTable_name.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ClientTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ClientTable, String> param) {
                return param.getValue().getValue().clientName;
            }
        });
        JFXTreeTableColumn<ClientTable, String> ClientTable_address = new JFXTreeTableColumn<>("العنوان");
        ClientTable_address.setPrefWidth(265);
        ClientTable_address.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ClientTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ClientTable, String> param) {
                return param.getValue().getValue().clientAddress;
            }
        });
        JFXTreeTableColumn<ClientTable, String> ClientTable_phone = new JFXTreeTableColumn<>("رقم التليفون");
        ClientTable_phone.setPrefWidth(265);
        ClientTable_phone.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ClientTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ClientTable, String> param) {
                return param.getValue().getValue().clientAddress;
            }
        });

        client_data.add(new ClientTable(1, "a", "a", "a"));
        final TreeItem<ClientTable> Client_root = new RecursiveTreeItem<ClientTable>(client_data, RecursiveTreeObject::getChildren);
        client_tableView.getColumns().setAll(ClientTable_name, ClientTable_address, ClientTable_phone);
        tableview.setEditable(true);

        client_tableView.setRoot(Client_root);
        client_tableView.setShowRoot(false);


    }


    // -------------------------------------------- account -------------------------------

    @FXML
    private DatePicker accountfromSearch;

    @FXML
    private DatePicker accounttoSearch;

    @FXML
    private ComboBox accountClientName;

    @FXML
    private JFXButton accountSearch;

    @FXML
    private JFXTreeTableView accounTableview;

    @FXML
    private TextField accountClear;

    @FXML
    private JFXButton accountPrint;


    ObservableList<NaklTable> account_data = FXCollections.observableArrayList();

    @FXML
    void accountClientNameAction(ActionEvent event) {

    }

    @FXML
    void accountSearchAction(ActionEvent event) {

    }

    @FXML
    void accountfromSearchAction(ActionEvent event) {

    }

    @FXML
    void accounttoSearchAction(ActionEvent event) {

    }


    // *********************** clients ********************************
    ObservableList<ClientTable> client_data = FXCollections.observableArrayList();

    @FXML
    private TextField client_clientName;

    @FXML
    private TextField client_phone;

    @FXML
    private TextField client_address;

    @FXML
    private JFXButton client_Add;

    @FXML
    private JFXButton client_Update;

    @FXML
    private JFXTreeTableView client_tableView;

    @FXML
    private JFXButton client_delete;

    @FXML
    void client_AddAction(ActionEvent event) {

    }

    @FXML
    void client_UpdateAction(ActionEvent event) {

    }

    @FXML
    void client_deleteAction(ActionEvent event) {

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
