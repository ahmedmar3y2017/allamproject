/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.app.Transactions;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author AbdelRahman
 */
public class DBConnection {
    private static String driver = "com.mysql.jdbc.Driver";
    private static String url = "jdbc:mysql://localhost:3306/allam";
    private static String username = "root";
    private static String password = "root";
    private static Connection connection;

    private static void connect() {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, username,"root");
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Error in Connection" + ex);
            JOptionPane.showMessageDialog(null, "Error In Connection");
            
//            new com.souvenir.design.app_dialogs.DB_Connection_error(null, true).setVisible(true);
        }
    }

    public static Connection getConnection() {
        if (connection == null) {
            connect();
        }
        return connection;
    }

    public static void disconnect() {
        try {
            if (connection != null) {
                DBConnection.connection.close();
            }
        } catch (SQLException ex) {
        }
    }
}
