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
    void naklAction(ActionEvent event) {
        System.out.println("DOne");
    }

    @FXML
    void MainButtonAction(ActionEvent event) throws IOException {

        if (event.getSource() == nakl) {
            System.out.println("nakl");

            holderPane = FXMLLoader.load(getClass().getResource("/fxml/nakl.fxml"));


        }
        if (event.getSource() == kashfHesab) {
            System.out.println("kashfHesab");

            holderPane = FXMLLoader.load(getClass().getResource("/fxml/kashfHesab.fxml"));

        }
        if (event.getSource() == accounts) {
            System.out.println("accounts");

            holderPane = FXMLLoader.load(getClass().getResource("/fxml/accounts.fxml"));

        }
        if (event.getSource() == money) {
            System.out.println("money");

            holderPane = FXMLLoader.load(getClass().getResource("/fxml/money.fxml"));


        }
        if (event.getSource() == clients) {
            System.out.println("clients");

            holderPane = FXMLLoader.load(getClass().getResource("/fxml/clients.fxml"));

        }
        setNode(holderPane);


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
            holderPane = FXMLLoader.load(getClass().getResource("/fxml/nakl.fxml"));


            holderPane.setPrefWidth(primaryScreenBounds.getWidth() - vbox.getPrefWidth());


            System.out.println("Holder : " + holderPane.getPrefWidth());
            setNode(holderPane);
        } catch (IOException ex) {
            ex.printStackTrace();
        }


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
