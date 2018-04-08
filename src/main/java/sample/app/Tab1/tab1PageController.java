package sample.app.Tab1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by ahmed mar3y on 08/04/2018.
 */
public class tab1PageController implements Initializable {


    @FXML
    private Button button;

    @FXML
    void buttonAction(ActionEvent event) {
        System.out.println("ButtonClick");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Init");
    }
}
