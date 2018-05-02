package sample.app.login;

import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class Main extends Application {

    public static boolean isSplashLoaded;

    @Override
    public void start(Stage primaryStage) throws IOException {
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

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
