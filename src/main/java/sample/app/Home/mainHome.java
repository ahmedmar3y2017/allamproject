package sample.app.Home;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

/**
 * Created by ahmed mar3y on 22/04/2018.
 */
public class mainHome {


    public mainHome() throws IOException {
        Stage primaryStage = new Stage();
//        Parent root = FXMLLoader.load(getClass().getResource("/Fxml/List.fxml"));

        FXMLLoader loader = new FXMLLoader();
        loader.setController(new homeController());
        loader.setLocation(getClass().getResource("/Fxml/List.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        // on stage event close
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {

                Platform.exit();
                System.exit(0);

            }
        });

        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

        //set Stage boundaries to visible bounds of the main screen
        primaryStage.setX(primaryScreenBounds.getMinX());
        primaryStage.setY(primaryScreenBounds.getMinY());
        primaryStage.setWidth(primaryScreenBounds.getWidth());
        primaryStage.setHeight(primaryScreenBounds.getHeight());
        primaryStage.setResizable(false);

        primaryStage.setTitle("الصفحه الرئيسية");
        primaryStage.setScene(scene);
        primaryStage.show();

    }


//
//    @Override
//    public void start(Stage primaryStage) throws IOException {
//        Parent root = FXMLLoader.load(getClass().getResource("/Fxml/List.fxml"));
//
//        Scene scene = new Scene(root);
//        // on stage event close
//        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
//            public void handle(WindowEvent we) {
//
//                Platform.exit();
//                System.exit(0);
//
//            }
//        });
//
//        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
//
//        //set Stage boundaries to visible bounds of the main screen
//        primaryStage.setX(primaryScreenBounds.getMinX());
//        primaryStage.setY(primaryScreenBounds.getMinY());
//        primaryStage.setWidth(primaryScreenBounds.getWidth());
//        primaryStage.setHeight(primaryScreenBounds.getHeight());
//        primaryStage.setResizable(false);
//
//        primaryStage.setTitle("الصفحه الرئيسية");
//        primaryStage.setScene(scene);
//        primaryStage.show();
//    }
//
//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String[] args) {
//        launch(args);
//    }
}
