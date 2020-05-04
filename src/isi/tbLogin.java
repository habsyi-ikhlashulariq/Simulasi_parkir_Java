/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isi;

import java.awt.Component;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JTable;
import static javax.swing.SwingConstants.LEFT;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author habsy
 */
public class tbLogin extends Konek{
   private String sql="";
   private ResultSet result;
  
   
   public tbLogin() throws SQLException{
       super.crateStatement();
   }
   
   public ResultSet getUserAndPassword(String username)throws SQLException{
       result= statement.executeQuery("select * From login where username='"+username+"'");
       return result;
   }
   public void insert(String username, String password) throws SQLException {
        sql = "INSERT INTO login(username,password)VALUES('"+username+"','"+password+"')";
        statement.executeUpdate(sql);
    }
}
