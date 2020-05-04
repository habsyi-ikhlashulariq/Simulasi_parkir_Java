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

    public class tbKeluar1 extends Konek{
    private String sql = "";
    private ResultSet result;
    private String data[] = new String[8]; 
    
    public tbKeluar1() throws SQLException{
        super.crateStatement();
    }
    
    public void update(String No_parkir) throws SQLException {
        sql = "UPDATE data SET keluar=NOW() WHERE No_parkir='"+No_parkir+"'";
        statement.executeUpdate(sql);
    }
    public void update1(String No_parkir, int Lama) throws SQLException {
        sql = "UPDATE data SET Lama='"+Lama+"' WHERE No_parkir='"+No_parkir+"'";
        statement.executeUpdate(sql);
    }
    public void update2(String No_parkir, int Total,int Bayar,int Kembalian) throws SQLException {
        sql = "UPDATE data SET Total='"+Total+"',Bayar='"+Bayar+"',Kembalian='"+Kembalian+"' WHERE No_parkir='"+No_parkir+"'";
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
        return new javax.swing.table.DefaultTableModel(new Object [][]{},new String [] {"No_Parkir", "No_Polisi","Nama", "Jenis_kendaraan","tarif","masuk","keluar","Lama"}) {     
            boolean[] canEdit = new boolean[]{
                false, false, false,false,false,false,false,false
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
                data[6] = result.getString("keluar");
                data[7] = result.getString("Lama");
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
