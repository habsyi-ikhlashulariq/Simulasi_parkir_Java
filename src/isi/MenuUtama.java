/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isi;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 *
 * @author Habsyi Ikhlashul A
 */
public class MenuUtama extends javax.swing.JFrame {
    private Masuk tbmasuk;
    private Keluar1 tbkeluar;
    Connection con = null;
    Statement st = null;
    ResultSet r = null;
    String sql = null;

    /**
     * Creates new form MenuUtama
     */
    public MenuUtama() {
        initComponents();
        setLocationRelativeTo(null);
    }
     public void showMasuk() {
        if(tbmasuk == null){
            Masuk mlebu = new Masuk();
            this.add(mlebu);
            mlebu.show();
        }
    }
     public void showKeluar() {
         if (tbkeluar == null){
             Keluar1 metu = new Keluar1();
         this.add(metu);
         metu.show();
         }         
     }
      public void Masuk(){
          BufferedWriter writer = null;
         try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/parkir1db", "root", "");
            st = con.createStatement();
            sql = "SELECT * FROM data;";
            ResultSet rs = st.executeQuery(sql);
            writer = new BufferedWriter(new FileWriter("laporan.txt"));
            writer.write("No Parkir   No Polisi    Nama   Jenis Kendaraan       Masuk ");
            while (rs.next()) {
                String No_par= rs.getString(1);
                String No_pol= rs.getString(2);
                String Nama= rs.getString(3);
                String Jenis= rs.getString(4);
                //String tarif= rs.getString(5);
                String masuk= rs.getString(6);
                
                writer.newLine();
                writer.write(No_par+"   ");
                writer.write("   "+No_pol+"     ");
                writer.write(Nama+"      ",0,4);
                writer.write("      "+Jenis+"        ");
                //writer.write(tarif+"   ");
                writer.write(masuk+"          ");
            }
            writer.close();
            Desktop.getDesktop().open(new File("laporan.txt"));
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Keluar1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MenuUtama.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
             try {
                 writer.close();
             } catch (IOException e) {
                 Logger.getLogger(MenuUtama.class.getName()).log(Level.SEVERE, null, e);
             }
  
         }
      }
      public void Keluar(){
           BufferedWriter writer = null;
         try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/parkir1db", "root", "");
            st = con.createStatement();
            sql = "SELECT * FROM data;";
            ResultSet rs = st.executeQuery(sql);
            writer = new BufferedWriter(new FileWriter("laporan1.txt"));
            writer.write("No Parkir   No Polisi    Nama   Jenis Kendaraan        Masuk                  Keluar          Lama");
            while (rs.next()) {
                String No_par= rs.getString(1);
                String No_pol= rs.getString(2);
                String Nama= rs.getString(3);
                String Jenis= rs.getString(4);
                //String tarif= rs.getString(5);
                String masuk= rs.getString(6);
                String keluar= rs.getString(7);
                String lama= rs.getString(8);
                
                writer.newLine();
                writer.write(No_par+"   ");
                writer.write("   "+No_pol+"     ");
                writer.write(Nama+"     ",0,4);
                writer.write("       "+Jenis+"      ");
                //writer.write(tarif+"   ");
                writer.write(masuk+" ");
                writer.write(" "+keluar+"                             ",0,22);
                writer.write("  "+lama);
            }
            writer.close();
            Desktop.getDesktop().open(new File("laporan1.txt"));
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Keluar1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MenuUtama.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
             try {
                 writer.close();
             } catch (IOException e) {
                 Logger.getLogger(MenuUtama.class.getName()).log(Level.SEVERE, null, e);
             }
  
         }
      }
      public void trans(){
           BufferedWriter writer = null;
         try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/parkir1db", "root", "");
            st = con.createStatement();
            sql = "SELECT * FROM transaksi;";
            ResultSet rs = st.executeQuery(sql);
            writer = new BufferedWriter(new FileWriter("laporan2.txt"));
            writer.write("NO    Total   Bayar   Kembalian");
            while (rs.next()) {
                String No= rs.getString(1);
                String total= rs.getString(2);
                String uang= rs.getString(3);
                String sisa= rs.getString(4);
                
                writer.newLine();
                writer.write(No+"   ");
                writer.write("  "+total+"     ",0,8);
                writer.write("  "+uang+"     ",0,8);
                writer.write("       "+sisa+"      ",0,8);
                
            }
            writer.close();
            Desktop.getDesktop().open(new File("laporan2.txt"));
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Keluar1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MenuUtama.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
             try {
                 writer.close();
             } catch (IOException e) {
                 Logger.getLogger(MenuUtama.class.getName()).log(Level.SEVERE, null, e);
             }
  
         }
      }
      public void logout(){
          int confr = JOptionPane.showConfirmDialog(null, "Apakah Anda Ingin Keluar??", "Peringatan", JOptionPane.YES_OPTION, JOptionPane.WARNING_MESSAGE);
                    if (confr == 0) {
                    System.exit(0);}
      }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        M_Masuk = new javax.swing.JMenuItem();
        M_Keluar = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        L_Masuk = new javax.swing.JMenuItem();
        L_Keluar = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Parkir Sederhana");

        jMenu1.setText("Master");
        jMenu1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jMenu1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu1ActionPerformed(evt);
            }
        });

        M_Masuk.setText("Kendaraan Masuk");
        M_Masuk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                M_MasukActionPerformed(evt);
            }
        });
        jMenu1.add(M_Masuk);

        M_Keluar.setText("Kendaraan Keluar");
        M_Keluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                M_KeluarActionPerformed(evt);
            }
        });
        jMenu1.add(M_Keluar);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Laporan");
        jMenu2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jMenu2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu2ActionPerformed(evt);
            }
        });

        L_Masuk.setText("Kendaraan Masuk");
        L_Masuk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                L_MasukActionPerformed(evt);
            }
        });
        jMenu2.add(L_Masuk);

        L_Keluar.setText("Kendaraan Keluar");
        L_Keluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                L_KeluarActionPerformed(evt);
            }
        });
        jMenu2.add(L_Keluar);

        jMenuItem2.setText("Transaksi");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem2);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Lainnya");
        jMenu3.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        jMenuItem1.setText("Keluar");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem1);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 279, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenu1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenu1ActionPerformed

    private void jMenu2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenu2ActionPerformed

    private void M_MasukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_M_MasukActionPerformed
        // TODO add your handling code here:
        showMasuk();
    }//GEN-LAST:event_M_MasukActionPerformed

    private void L_MasukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_L_MasukActionPerformed
        // TODO add your handling code here:
        Masuk();
    }//GEN-LAST:event_L_MasukActionPerformed

    private void M_KeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_M_KeluarActionPerformed
        // TODO add your handling code here:
        showKeluar();
    }//GEN-LAST:event_M_KeluarActionPerformed

    private void L_KeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_L_KeluarActionPerformed
        // TODO add your handling code here:
        Keluar();
    }//GEN-LAST:event_L_KeluarActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        logout();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        trans();
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MenuUtama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MenuUtama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MenuUtama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MenuUtama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MenuUtama().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem L_Keluar;
    private javax.swing.JMenuItem L_Masuk;
    private javax.swing.JMenuItem M_Keluar;
    private javax.swing.JMenuItem M_Masuk;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    // End of variables declaration//GEN-END:variables
}
