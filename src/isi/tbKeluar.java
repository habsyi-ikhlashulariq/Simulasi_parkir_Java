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
import javax.swing.JTable;
import static javax.swing.SwingConstants.LEFT;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Habsyi Ikhlashul A
 */
public class tbKeluar extends Konek{
    private String sql = "";
    private ResultSet result;
    private String data[] = new String[7];

public void tbKeluar() throws SQLException{
    super.crateStatement();
}
public void insert(String No_parkir,String id_keluar,String keluar, String lama) throws SQLException {
        sql = "INSERT INTO tb_keluar(No_parkir,id_keluar, keluar , lama)VALUES('"+No_parkir+"','"+id_keluar+"',"+keluar+"','"+lama+"')";
        statement.executeUpdate(sql);
    }

    public void update(String id_keluar, String keluar, String lama) throws SQLException {
        sql = "UPDATE tb_keluar SET id_keluar='"+id_keluar+"' keluar='"+keluar+"',lama='"+lama+"' WHERE id_keluar='"+id_keluar+"' ";
        statement.executeUpdate(sql);
    }
    public void delete(String id_keluar) throws SQLException {
        sql = "DELETE FROM data WHERE id_keluar='"+id_keluar+"'";
        statement.executeUpdate(sql);
    }

    public ResultSet viewData() throws SQLException {
        sql = "SELECT k.id_keluar AS Keluar, m.No_parkir AS Noparkir, m.No_polisi AS Nopolisi,m.nama AS Nama"
                + "m.Jenis_kendaraan AS JenisKendaraan,m.tarif AS tarif , m.masuk AS JamMasuk, k.keluar AS JamKeluar,"
                + " k.lama AS Lama , k.tarif AS Tarif FROM tb_keluar k, data m WHERE k.id_keluar=m.No_parkir ORDER BY No_parkir ASC";
        return result = statement.executeQuery(sql);
    }
    public ResultSet viewData(String No_parkir) throws SQLException {
        sql = "SELECT k.id_keluar AS Keluar, m.No_parkir AS Noparkir, m.No_polisi AS Nopolisi, m.nama AS Nama"
                + "m.Jenis_kendaraan AS JenisKendaraan,m.tarif AS tarif , m.masuk AS JamMasuk, k.keluar AS JamKeluar,"
                + " k.lama AS Lama , k.tarif AS Tarif FROM tb_keluar k, data m WHERE k.id_keluar=m.No_parkir "
                + "AND No_parkir='"+No_parkir+"'ORDER BY No_parkir ASC";
        return result = statement.executeQuery(sql);
    }
    public javax.swing.table.DefaultTableModel defaultTabelModel() {
        return new javax.swing.table.DefaultTableModel(new Object [][]{},new String [] {"No parkir","No Polisi",
            "Jenis Kendaraan","Tarif","Jam Masuk","Jam keluar", "lama parkir"}) {     
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false, false, false
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
                data[2] = result.getString("Jenis_kendaraan");
                data[3] = result.getString("tarif");
                data[4] = result.getString("masuk");
                data[5] = result.getString("keluar");
                data[6] = result.getString("lama");
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
