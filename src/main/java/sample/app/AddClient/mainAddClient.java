package sample.app.AddClient;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by ahmed mar3y on 24/04/2018.
 */
public class mainAddClient {


    public mainAddClient() throws IOException {

        Stage stage = new Stage();
//        Parent root = FXMLLoader.load(getClass().getResource("/Fxml/AddClient.fxml"));

        FXMLLoader loader = new FXMLLoader();
        loader.setController(new addClientController());
        loader.setLocation(getClass().getResource("/Fxml/AddClient.fxml"));
        Parent root = loader.load();



        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        // for fade out
//        stage.initStyle(StageStyle.TRANSPARENT); //Removes window decorations
        scene.setFill(Color.TRANSPARENT); //Makes scene background transparent

        stage.initModality(Modality.APPLICATION_MODAL);

        stage.showAndWait();

    }

}
