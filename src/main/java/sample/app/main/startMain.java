package sample.app.main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

/**
 * Created by ahmed mar3y on 08/04/2018.
 */
public class startMain extends Application {

    @Override
    public void start(Stage s) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Main.fxml"));
        Region contentRootRegion = (Region) loader.load();

        //Set a default "standard" or "100%" resolution
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

        double origW = primaryScreenBounds.getWidth();
        double origH = primaryScreenBounds.getHeight();

        //If the Region containing the GUI does not already have a preferred width and height, set it.
        //But, if it does, we can use that setting as the "standard" resolution.
        contentRootRegion.setPrefWidth(origW);

        origW = contentRootRegion.getPrefWidth();


        contentRootRegion.setPrefHeight(origH);
        origH = contentRootRegion.getPrefHeight();

        System.out.println(origW + "  " + origH);
        //Wrap the resizable content in a non-resizable container (Group)
        Group group = new Group(contentRootRegion);
        //Place the Group in a StackPane, which will keep it centered
        StackPane rootPane = new StackPane();
        rootPane.getChildren().add(group);

        s.setTitle("Home");
        //Create the scene initally at the "100%" size
        Scene scene = new Scene(rootPane);
        scene.getStylesheets().add("pane/style.css");
        //Bind the scene's width and height to the scaling parameters on the group
        group.scaleXProperty().bind(scene.widthProperty().divide(origW));
        group.scaleYProperty().bind(scene.heightProperty().divide(origH));
        //Set the scene to the window (stage) and show it

        s.setScene(scene);
        s.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
