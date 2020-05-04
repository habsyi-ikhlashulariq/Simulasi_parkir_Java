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
public class Tb_transaksi extends Konek{
    private String sql = "";
    private ResultSet result;
    private String data[] = new String[15]; 
    
    public Tb_transaksi() throws SQLException{
        super.crateStatement();
    }
    public void insert(String id_transaksi,int Total,int Bayar,int Kembalian)throws SQLException{
        sql="INSERT INTO transaksi (id_transaksi,Total,Bayar,Kembalian) VALUES ('"+id_transaksi+"','"+Total+"','"+Bayar+"','"+Kembalian+"')";
        statement.executeUpdate(sql);
    }
    public void update(String id_transaksi, int Total,int Bayar ,int Kembalian) throws SQLException {
        sql = "UPDATE transaksi SET id_transaksir='"+id_transaksi+"',Total='"+Total+"',Bayar='"+Bayar+"',Kembalian='"+Kembalian+"' WHERE id_transaksi='"+id_transaksi+"'";
        statement.executeUpdate(sql);
    }
   
    public void delete(String id_transaksi) throws SQLException {
        sql = " DELETE FROM transaksi WHERE No_parkir='"+id_transaksi+"' ";
        statement.execute(sql);
    }
   
    public ResultSet viewData() throws SQLException {
        sql = "SELECT d.No_parkir AS NoParkir , d.No_polisi AS NoPolisi,d.nama AS Nama, d.Jenis_kendaraan As Kendaraan,"
                + "d.tarif AS Tarif,d.masuk AS Masuk,d.keluar AS Keluar,d.Lama AS lama, "
                + "t.id_transaksi AS Id, t.Total AS total, t.Bayar AS bayar, t.Kembalian AS kembalian FROM data d,transaksi t WHERE t.id_transaksi=d.No_parkir ORDER BY id_transaksi ASC";
        return result = statement.executeQuery(sql);
    }
    public ResultSet viewData(String id_transaksi) throws SQLException {
        sql = "SELECT d.No_parkir AS NoParkir , d.No_polisi AS NoPolisi,d.nama AS Nama, d.Jenis_kendaraan As Kendaraan,"
                + "d.tarif AS Tarif,d.masuk AS Masuk,d.keluar AS Keluar,d.Lama AS lama, "
                + "t.id_transaksi AS Id, t.Total AS total, t.Bayar AS bayar, t.Kembalian AS kembalian FROM data d,transaksi t WHERE t.id_transaksi=d.No_parkir AND t.id_transaksi='"+id_transaksi+"' ORDER BY No_parkir ASC";
        return result = statement.executeQuery(sql);
    }
    
    public javax.swing.table.DefaultTableModel defaultTabelModel() {
        return new javax.swing.table.DefaultTableModel(new Object [][]{},new String [] {"No_Parkir", "No_Polisi","Nama", "Jenis_kendaraan","tarif","masuk","keluar","Lama","Total","Bayar","Kembalian"}) {     
            boolean[] canEdit = new boolean[]{
                false, false, false,false,false,false,false,false,false,false,false
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
                data[8] = result.getString("Total");
                data[9] = result.getString("Bayar");
                data[10] = result.getString("Kembalian");
                data[11] = result.getString("id_transaksi");
                data[12] = result.getString("Total");
                data[13] = result.getString("Bayar");               
                data[14] = result.getString("Kembalian");
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
}
