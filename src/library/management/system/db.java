/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.management.system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import javax.swing.*;

/**
 *
 * @author Indika
 */
public class db {
    public static Connection connect() {
        Connection con = null;
        Statement stmt;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/lms", "root", "");

            return con;
        } catch (Exception e) {
            System.out.println(e);
            return null;
      //   JOptionPane.showMessageDialog(this, e);
        }

    }
}
