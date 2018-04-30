package sample.app.login;


import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.hibernate.SessionFactory;
import sample.app.Entities.Users;
import sample.app.Home.mainHome;
import sample.app.Transactions.UserDao.userDao;
import sample.shared.HibernateUtil.HibernateUtil;

public class LoginController implements Initializable {

    @FXML
    private HBox root;
    @FXML
    private VBox rightBox;

    @FXML
    private JFXTextField usernameField;
    @FXML
    private JFXPasswordField passwordField;

    JFXSnackbar errorMsg;
    public static int idEmployee;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        errorMsg = new JFXSnackbar(rightBox);

        // Create two threads:
        Thread thread1 = new Thread() {
            public void run() {
                //splash Screen about 5 seconds
//                if (!MaterialLogin.isSplashLoaded) {
//                    loadSplashScreen();
//                }
            }
        };

        Thread thread2 = new Thread() {
            public void run() {
                SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

            }
        };

// Start the downloads.
        thread1.start();
        thread2.start();

// Wait for them both to finish
        try {
            thread1.join();
            thread2.join();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    @FXML
    private void btnLogin() {


        try {
            loginFunction();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (usernameField.getText().isEmpty()) {
            errorMsg.show("Username is empty !", 1500);
            return;
        }
        if (passwordField.getText().isEmpty()) {
            errorMsg.show("Password is empty !", 1500);
            return;
        }
        System.out.println("Username : " + usernameField.getText());
        System.out.println("Password : " + passwordField.getText());

        errorMsg.show("Success !", 2000);

    }

    @FXML
    private void btnExit() {
        Platform.exit();
    }

    private void loginFunction() throws IOException {

        List<Users> employees = userDao.SelectAllUsers();
        if (employees.isEmpty()) {

            // insert into table database employee

            Users employees1 = userDao.SaveUsers(new
                    Users(
                    "admin",
                    "admin", "admin"));


        }
        idEmployee = userDao.SelectUsers(usernameField.getText().toString(), passwordField.getText().toString());
        if (idEmployee != 0) {

            errorMsg.show("Success !", 2000);
            // close this Stage
            ((Stage) this.usernameField.getScene().getWindow()).close();

            mainHome mainHome = new mainHome();


        } else {
            errorMsg.show("اسم المستخدم او الباسورد خطأ", 2000);

        }


    }

    @FXML
    void treeViewKeyPressed(KeyEvent event) {


        // check SupplierSavedIdAccount

        if (event.getCode().equals(KeyCode.ENTER)) {
            try {
                loginFunction();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        //... other keyevents

    }

    private void loadSplashScreen() {
        try {
            Main.isSplashLoaded = true;

            StackPane pane = FXMLLoader.load(getClass().getResource(("Fxml/SplashFXML.fxml")));
            root.getChildren().setAll(pane);

            FadeTransition fadeIn = new FadeTransition(Duration.seconds(3), pane);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.setCycleCount(1);

            FadeTransition fadeOut = new FadeTransition(Duration.seconds(3), pane);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);
            fadeOut.setCycleCount(1);

            fadeIn.play();

            fadeIn.setOnFinished((e) -> {
                fadeOut.play();
            });

            fadeOut.setOnFinished((e) -> {
                try {
                    AnchorPane parentContent = FXMLLoader.load(getClass().getResource(("FXMLDocument.fxml")));
                    root.getChildren().setAll(parentContent);

                } catch (IOException ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
