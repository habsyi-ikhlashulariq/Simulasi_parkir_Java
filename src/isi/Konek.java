/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isi;

import com.mysql.jdbc.Connection;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Habsyi Ikhlashul A
 */
public class Konek {
  private Connection connection;
  private Properties myPanel;
  private String msg,msgTitle,lib,driver,drString,database,user,password;
  protected java.sql.Statement statement;
  private int msgTipe;
    
    public Konek(){
        try {
             myPanel = new Properties();
         myPanel.load(new FileInputStream("conf.inii"));
         driver = myPanel.getProperty("DBDriver");
         database = "jdbc:mysql://"+myPanel.getProperty("DBHost")+":"
                 +myPanel.getProperty("DBPort")+"/"+myPanel.getProperty("DBDatabase");
         user = myPanel.getProperty("DBUsername");
         password = myPanel.getProperty("DBPassword");
         
            try {
                Class.forName(driver);
                
                try {
                    connection=(Connection)DriverManager.getConnection(database,user,password);
                } catch (SQLException ex) {
                     msg = "Error: Koneksi Gagal !"+ex.getMessage();
                 msgTitle = "Peringatan";
                 msgTipe = JOptionPane.ERROR_MESSAGE;
                 Logger.getLogger(Konek.class.getName()).log(Level.SEVERE, null, ex);
             }
                  msg = "Succes : Koneksi Berhasil";
                  msgTitle = "Informasi";
                  msgTipe = JOptionPane.INFORMATION_MESSAGE;
            } catch (ClassNotFoundException cnfe) {
                  msg = "Error :Class tidak ditemukan";
             msgTitle = "Peringatan !!";
             msgTipe = JOptionPane.ERROR_MESSAGE;
            }
        } catch (IOException e) {
             msg = "Error : Configurasi tidak dapat dibuka";
         msgTitle = "Peringatan !!";
         msgTipe = JOptionPane.ERROR_MESSAGE;
        }
    }
    public void getMesage(){
     JOptionPane.showMessageDialog(null, msg, msgTitle, msgTipe);
 }
 public void crateStatement() throws SQLException{
        statement = connection.createStatement();
}
    
}
