package sample.app.Home;

import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class homeController implements Initializable {

    AnchorPane homepage;
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
    private JFXButton update;

    @FXML
    private JFXButton save;

    @FXML
    private JFXButton deleteTable;

    @FXML
    private JFXButton updateTable;

    @FXML
    void MainButtonAction(ActionEvent event) throws IOException {

        if (event.getSource() == nakl) {

            homepage = FXMLLoader.load(getClass().getResource("/fxml/supplier.fxml"));

        }
        if (event.getSource() == kashfHesab) {

            homepage = FXMLLoader.load(getClass().getResource("/fxml/supplier.fxml"));

        }
        if (event.getSource() == accounts) {

            homepage = FXMLLoader.load(getClass().getResource("/fxml/supplier.fxml"));

        }
        if (event.getSource() == money) {
            homepage = FXMLLoader.load(getClass().getResource("/fxml/supplier.fxml"));


        }
        if (event.getSource() == clients) {

            homepage = FXMLLoader.load(getClass().getResource("/fxml/supplier.fxml"));

        }
        setNode(homepage);


    }

    @FXML
    void deleteTableAction(ActionEvent event) {

    }

    @FXML
    void saveAction(ActionEvent event) {

    }

    @FXML
    void updateAction(ActionEvent event) {

    }

    @FXML
    void updateTableAction(ActionEvent event) {

    }

    private void setNode(Node node) {
//        holderPane.getChildren().clear();
//        holderPane.getChildren().add((Node) node);

        FadeTransition ft = new FadeTransition(Duration.millis(1500));

        ft.setNode(node);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);

        ft.setAutoReverse(false);
        ft.play();
    }

    private void createPage() throws IOException {
        try {
            homepage = FXMLLoader.load(getClass().getResource("/fxml/Employee.fxml"));
            setNode(homepage);
        } catch (IOException ex) {
            ex.printStackTrace();
        }


    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            createPage();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
