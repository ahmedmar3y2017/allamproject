package sample.app.Home;

import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.util.Duration;
import org.hibernate.SessionFactory;
import sample.app.Accounts.accountController;
import sample.app.Clients.clientsController;
import sample.app.KashfHesab.kashfHesabController;
import sample.app.Money.moneyController;
import sample.app.Settings.seetingsController;
import sample.app.nakl.naklController;
import sample.app.screenPlus.screenPlusController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class homeController implements Initializable {
    @FXML
    private VBox vbox;

    @FXML
    private Pane homepage;


    Pane holderPane;

    @FXML
    private JFXButton nakl;

    @FXML
    private JFXButton kashfHesab;

    @FXML
    private JFXButton accounts;

    @FXML
    private JFXButton money;

    @FXML
    private JFXButton clients;

    @FXML
    private JFXButton setting;
    Object ppanel;

    Boolean selected = false, selectedSetting = false;

    @FXML
    void naklAction(ActionEvent event) {
        System.out.println("DOne");
    }

    @FXML
    void MainButtonAction(ActionEvent event) throws IOException {

        if (event.getSource() == nakl && ppanel != nakl) {
            setting.getStyleClass().remove("activeOne");
            nakl.getStyleClass().add("activeOne");

            money.getStyleClass().remove("activeOne");
            accounts.getStyleClass().remove("activeOne");

            kashfHesab.getStyleClass().remove("activeOne");

            ppanel = event.getSource();
            FXMLLoader loader = new FXMLLoader();
            loader.setController(new naklController());
            loader.setLocation(getClass().getResource("/Fxml/nakl.fxml"));
            holderPane = loader.load();


            setNode(holderPane);
        }
        if (event.getSource() == kashfHesab && ppanel != kashfHesab) {
            setting.getStyleClass().remove("activeOne");
            kashfHesab.getStyleClass().add("activeOne");

            money.getStyleClass().remove("activeOne");
            accounts.getStyleClass().remove("activeOne");

            nakl.getStyleClass().remove("activeOne");
            ppanel = event.getSource();

            FXMLLoader loader = new FXMLLoader();
            loader.setController(new kashfHesabController());
            loader.setLocation(getClass().getResource("/Fxml/kashfHesab.fxml"));
            holderPane = loader.load();


            setNode(holderPane);
        }
        if (event.getSource() == accounts && ppanel != accounts) {
            setting.getStyleClass().remove("activeOne");
            accounts.getStyleClass().add("activeOne");

            money.getStyleClass().remove("activeOne");
            accounts.getStyleClass().remove("activeOne");
            kashfHesab.getStyleClass().remove("activeOne");
            nakl.getStyleClass().remove("activeOne");

            ppanel = event.getSource();


            FXMLLoader loader = new FXMLLoader();
            loader.setController(new screenPlusController());
            loader.setLocation(getClass().getResource("/Fxml/screenPlus.fxml"));
            holderPane = loader.load();


            setNode(holderPane);
        }
        if (event.getSource() == money && ppanel != money) {
            setting.getStyleClass().remove("activeOne");
            money.getStyleClass().add("activeOne");

            clients.getStyleClass().remove("activeOne");
            accounts.getStyleClass().remove("activeOne");
            kashfHesab.getStyleClass().remove("activeOne");
            nakl.getStyleClass().remove("activeOne");

            ppanel = event.getSource();


            FXMLLoader loader = new FXMLLoader();
            loader.setController(new moneyController());
            loader.setLocation(getClass().getResource("/Fxml/money.fxml"));
            holderPane = loader.load();


            setNode(holderPane);

        }
        if (event.getSource() == clients && ppanel != clients) {

            setting.getStyleClass().remove("activeOne");
            clients.getStyleClass().add("activeOne");

            money.getStyleClass().remove("activeOne");
            accounts.getStyleClass().remove("activeOne");
            kashfHesab.getStyleClass().remove("activeOne");
            nakl.getStyleClass().remove("activeOne");


            ppanel = event.getSource();

            FXMLLoader loader = new FXMLLoader();
            loader.setController(new clientsController());
            loader.setLocation(getClass().getResource("/Fxml/clients.fxml"));
            holderPane = loader.load();

            setNode(holderPane);

        }

        if (event.getSource() == setting && ppanel != setting) {
            selected = true;
            selectedSetting = false;
            setting.getStyleClass().add("activeOne");


            clients.getStyleClass().remove("activeOne");
            money.getStyleClass().remove("activeOne");
            accounts.getStyleClass().remove("activeOne");
            kashfHesab.getStyleClass().remove("activeOne");
            nakl.getStyleClass().remove("activeOne");

            ppanel = event.getSource();

            FXMLLoader loader = new FXMLLoader();
            loader.setController(new seetingsController());
            loader.setLocation(getClass().getResource("/Fxml/seetings.fxml"));
            holderPane = loader.load();


            setNode(holderPane);
        }


    }

    @FXML
    void settingButtonAction(ActionEvent event) {

    }

    @FXML
    void active(JFXButton btn) {
        btn.getStyleClass().removeAll("activeOne");
        //In this way you're sure you have no styles applied to your object button
        btn.getStyleClass().add("activeOne");
        //then you specify the class you would give to the button


    }

    private void setNode(Node node) {
        homepage.getChildren().clear();
        homepage.getChildren().add((Node) node);

        FadeTransition ft = new FadeTransition(Duration.millis(1000));

        ft.setNode(node);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);

        ft.setAutoReverse(false);
        ft.play();
    }

    //
    private void createPage() throws IOException {
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setController(new naklController());
            loader.setLocation(getClass().getResource("/Fxml/nakl.fxml"));
            holderPane = loader.load();


            holderPane.setPrefWidth(primaryScreenBounds.getWidth() - vbox.getPrefWidth());


            System.out.println("Holder : " + holderPane.getPrefWidth());
            setNode(holderPane);
        } catch (IOException ex) {
            ex.printStackTrace();
        }


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ppanel = nakl;

// start database
        SessionFactory sessionFactory = sample.shared.HibernateUtil.HibernateUtil.getSessionFactory();


        // width +height
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        homepage.setPrefWidth(primaryScreenBounds.getWidth() - vbox.getPrefWidth());

        System.out.println(primaryScreenBounds.getWidth());
        System.out.println(vbox.getPrefWidth());
        System.out.println(homepage.getPrefWidth());
        System.out.println(homepage.getMaxWidth());


        try {
            createPage();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
//        System.out.println(holderPane.getPrefWidth());

    }
}
