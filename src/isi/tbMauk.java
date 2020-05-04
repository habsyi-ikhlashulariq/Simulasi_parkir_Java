/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isi;

import java.awt.Component;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Date;
import java.text.SimpleDateFormat;
import javax.swing.JTable;
import static javax.swing.SwingConstants.LEFT;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Habsyi Ikhlashul A
 */
public class tbMauk extends Konek{
    private String sql = "";
    private ResultSet result;
    private String data[] = new String[6]; 
    
    public tbMauk() throws SQLException{
        super.crateStatement();
    }
public void insert(String No_parkir, String No_polisi,String nama, String Jenis_kendaraan,int tarif,String masuk) throws SQLException {
        sql = "INSERT INTO data(No_parkir,No_polisi,nama, Jenis_kendaraan,tarif,masuk)VALUES('"+No_parkir+"','"+No_polisi+"','"+nama+"','"+Jenis_kendaraan+"','"+tarif+"', NOW())";
        statement.executeUpdate(sql);
    }

    public void update(String No_parkir, String No_polisi,String nama, String Jenis_kendaraan,int tarif,  String masuk) throws SQLException {
        sql = "UPDATE data SET No_parkir='"+No_parkir+"', nama='"+nama+"',No_polisi='"+No_polisi+"',Jenis_kendaraan='"+Jenis_kendaraan+"',tarif='"+tarif+"', masuk=NOW() WHERE No_parkir='"+No_parkir+"'";
        statement.executeUpdate(sql);
    }
    public void delete(String No_parkir) throws SQLException {
        sql = " DELETE FROM data WHERE No_parkir='"+No_parkir+"' ";
        statement.execute(sql);
    }
   
    public ResultSet viewData() throws SQLException {
        sql = "SELECT * FROM data ORDER BY No_parkir ASC";
        return result = statement.executeQuery(sql);
    }
    
    public javax.swing.table.DefaultTableModel defaultTabelModel() {
        return new javax.swing.table.DefaultTableModel(new Object [][]{},new String [] {"No_Parkir", "No_Polisi","Nama", "Jenis_kendaraan","tarif","masuk"}) {     
            boolean[] canEdit = new boolean[]{
                false, false, false,false,false,false,false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        };
    }
    
    public void setWidthColumn(javax.swing.JTable tb, int lebar[]) {
        tb.setAutoResizeMode(tb.AUTO_RESIZE_OFF);
        int kolom = tb.getColumnCount();
        for (int i = 0; i < kolom; i++) {
            javax.swing.table.TableColumn tbc = tb.getColumnModel().getColumn(i);
            tbc.setPreferredWidth(lebar[i]);
        }
    }

    public void initTable(javax.swing.table.DefaultTableModel tblModel) {
        try {
            viewData();
            while (result.next()) {
                data[0] = result.getString("No_parkir");
                data[1] = result.getString("No_polisi");
                data[2] = result.getString("nama");               
                data[3] = result.getString("Jenis_kendaraan");
                data[4] = result.getString("tarif");
                data[5] = result.getString("masuk");
                tblModel.addRow(data);
            }
        } catch (Exception exc) {
            System.err.println(exc.getMessage());
        }
    }
    public void setColAlign(javax.swing.JTable tb, int col, int aligns) {
//tb.setAutoResizeMode(tb.AUTO_RESIZE_OFF);
        TableCellRenderer align = (TableCellRenderer) new ColumnAlign(aligns);
        javax.swing.table.TableColumn tbc = tb.getColumnModel().getColumn(col);
        tbc.setCellRenderer(align);
    }


    private class ColumnAlign extends DefaultTableCellRenderer {

        private int align = LEFT;

        private ColumnAlign(int align) {
            this.align = align;
        }

        @Override
        public Component getTableCellRendererComponent(JTable table,
                Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            setHorizontalAlignment(this.align);
            return this;
        }
    }

    public ResultSet viewData(String No_parkir) throws SQLException {
        sql = "SELECT * FROM data WHERE No_parkir='"+No_parkir+"' ORDER BY No_parkir ASC";
        return result = statement.executeQuery(sql);
    }
}
