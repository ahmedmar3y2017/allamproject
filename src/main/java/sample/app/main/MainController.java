package sample.app.main;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.input.MouseEvent;
import sample.app.Tab1.tab1PageController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by ahmed mar3y on 08/04/2018.
 */
public class MainController implements Initializable {

    @FXML
    private JFXHamburger ham1;
    @FXML
    private JFXDrawer drawer;

    @FXML
    private Tab tab1;


    @FXML
    private tab1PageController tab1PageController;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

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

    }
}
