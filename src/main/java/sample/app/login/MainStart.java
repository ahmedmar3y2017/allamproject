package sample.app.login;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

/**
 * Created by ahmed mar3y on 01/05/2018.
 */
public class MainStart {


    public MainStart() throws IOException {

        Stage primaryStage = new Stage();
//        Parent root = FXMLLoader.load(getClass().getResource("/Fxml/Login.fxml"));

        FXMLLoader loader = new FXMLLoader();
        loader.setController(new LoginController());
        loader.setLocation(getClass().getResource("/Fxml/Login.fxml"));
        Parent root = loader.load();


        Scene scene = new Scene(root);
        // on stage event close
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {

                Platform.exit();
                System.exit(0);

            }
        });

        primaryStage.setTitle("Login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
