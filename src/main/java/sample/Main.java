package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.shared.HibernateUtil.HibernateUtil;

public class Main {

    public static void main(String[] args) {
        HibernateUtil.getSessionFactory();
    }
}
