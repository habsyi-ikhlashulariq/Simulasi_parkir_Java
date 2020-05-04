/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isi;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
public class Masuk extends javax.swing.JInternalFrame {

    private tbMauk tblMasuk;
    private String No_parkir;
    private javax.swing.table.DefaultTableModel tblModel;
    Connection con = null;
    Statement st = null;
    ResultSet rs = null;
    String sql = null;

    /**
     * Creates new form Masuk
     */
    public Masuk() {
        initComponents();

        try {
            tblMasuk = new tbMauk();
            Tanggal();
            jam();
            refreshTabel();
            //txtJamMasuk.setText();
        } catch (SQLException ex) {
            Logger.getLogger(Masuk.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     private void validasi(String No_parkir) {
        try {
            ResultSet resultValidasi = tblMasuk.viewData(No_parkir);
            if (resultValidasi.first()) {
                int confr = JOptionPane.showConfirmDialog(null, "KodeBarang sudah ada ! \nApakah ingin mengubah data?", "Peringatan", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (confr == 0) {
                    txtNoPar.setText(resultValidasi.getString("No_parkir"));
                    txtNopol.setText(resultValidasi.getString("No_polisi"));
                    txtJamMasuk.setText(resultValidasi.getString("masuk"));
                    uu.setText(resultValidasi.getString("tarif"));
                     Object pilih = jComboBox1.getSelectedItem();
        if (pilih.equals("Roda 2")) {
            jComboBox1.setSelectedIndex(1);
            lbltarif.setVisible(true);
            uu.setVisible(true);
            lbltarif.setEnabled(false);
            lbltarif.setText("Sepeda, Sepeda Motor");
            uu.setText("Rp 2000");
        }
        if (pilih.equals("Roda 3")) {
            jComboBox1.setSelectedIndex(2);
            lbltarif.setVisible(true);
            uu.setVisible(true);
            lbltarif.setEnabled(false);
            lbltarif.setText("Bajaj, Bemo");
            uu.setText("Rp 3000");
        }
        if (pilih.equals("Roda 4")) {
            jComboBox1.setSelectedIndex(3);
            lbltarif.setVisible(true);
            uu.setVisible(true);
            lbltarif.setEnabled(false);
            lbltarif.setText("Mobil, Truk");
            uu.setText("Rp 4000");
        } 
                } else {
                    txtClear();
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Masuk.class.getName()).log(Level.SEVERE, null,
                    ex);
        }
    }

    public void refreshTabel() {
        tblModel = tblMasuk.defaultTabelModel();
        tabel.setModel(tblModel);
        tblMasuk.initTable(tblModel);
        txtNopol.requestFocus();
        visibleno();
        kode();
    }
      public void kode() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/parkir1db", "root", "");
            st = con.createStatement();
            sql = "SELECT MAX(Right(No_parkir,2)) AS no FROM data;";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                if(rs.first() == false){
                    txtNoPar.setText("PRK001");
                }else{
                    rs.last();
                    int nomor=rs.getInt(1)+1;
                    String no=String.valueOf(nomor);
                    int id=no.length();
                    for(int a = 0; a < 3 - id ;a++){
                    no="0"+no;
                }txtNoPar.setText("PRK"+no);
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Keluar1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
      
    
    private void txtClear() {
        txtNoPar.setText(null);
        txtNopol.setText(null);
        jComboBox1.setSelectedIndex(0);
        uu.setText(null);
        lbltarif.setText(null);
        txtNama.setText(null);
    }
    public void visibleyes(){
    uu.setVisible(true);
    lbltarif.setVisible(true);
    jLabel4.setVisible(true);
    txtJamMasuk.setVisible(true);
    jm.setVisible(true);
}
    public void visibleno(){
        jm.setVisible(false);
        uu.setVisible(false);
        lbltarif.setVisible(false);
        jLabel4.setVisible(false);
        txtJamMasuk.setVisible(false);
        txtNoPar.setEnabled(false);
        }
    public void tampilData() {
        visibleyes();
        int row = tabel.getSelectedRow();
        txtNoPar.setText(tblModel.getValueAt(row, 0).toString());
        txtNopol.setText(tblModel.getValueAt(row, 1).toString());
        txtNama.setText(tblModel.getValueAt(row, 2).toString());
        jComboBox1.setSelectedItem(tblModel.getValueAt(row, 3).toString());
        uu.setText(tblModel.getValueAt(row, 4).toString());
        txtJamMasuk.setText(tblModel.getValueAt(row, 5).toString());
        txtNoPar.setEnabled(false);
    }

    public void Tanggal() {
        java.util.Date sekarang = new java.util.Date();
        java.text.SimpleDateFormat ka = new java.text.SimpleDateFormat("dd-MM-yyyy");
        //lbltgl.setText(ka.format("Tanggal : "+sekarang));
        lbltgl.setText(ka.format(sekarang));
    }

    public void jam() {
        ActionListener taks = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                String nol_jam = "";
                String nol_menit = "";
                String nol_detik = "";
                Date dt = new Date();
                int nilai_jam = dt.getHours();
                int nilai_menit = dt.getMinutes();
                int nilai_detik = dt.getSeconds();
                if (nilai_jam <= 9) {
                    nol_jam = "0";
                }
                if (nilai_menit <= 9) {
                    nol_menit = "0";
                }
                if (nilai_detik <= 9) {
                    nol_detik = "0";
                }
                String jam = nol_jam + Integer.toString(nilai_jam);
                String menit = nol_menit + Integer.toString(nilai_menit);
                String detik = nol_detik + Integer.toString(nilai_detik);
                lbljam.setText(jam + ":" + menit + ":" + detik);
                //txtJamMasuk.setText("Jam : "+jam +":" + menit + ":" + detik);
            }
        };
        new Timer(1000, taks).start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabel = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtNoPar = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtNopol = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtJamMasuk = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtNama = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        lbljam = new javax.swing.JLabel();
        lbltgl = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        uu = new javax.swing.JTextField();
        lbltarif = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jm = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Parkir Sederhana");
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        tabel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7"
            }
        ));
        tabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelMouseClicked(evt);
            }
        });
        tabel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tabelKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tabel);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Data Masuk", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tempus Sans ITC", 1, 14))); // NOI18N

        jLabel1.setText("No Par");

        jLabel2.setText("No Polisi");

        jLabel4.setText("Jam Masuk");

        jLabel6.setText("Nama");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel6))
                        .addGap(31, 31, 31)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNopol, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                            .addComponent(txtNoPar)
                            .addComponent(txtNama)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(txtJamMasuk)))
                .addGap(16, 16, 16))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtNoPar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtNopol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtJamMasuk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jButton1.setText("Baru");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Simpan");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Hapus");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        lbljam.setFont(new java.awt.Font("Tempus Sans ITC", 1, 24)); // NOI18N
        lbljam.setText("jLabel7");

        lbltgl.setFont(new java.awt.Font("Tempus Sans ITC", 1, 24)); // NOI18N
        lbltgl.setText("jLabel8");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel5.setText("FORM MASUK");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Jenis Kendaraan", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tempus Sans ITC", 1, 14))); // NOI18N

        uu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uuActionPerformed(evt);
            }
        });

        lbltarif.setText("jLabel7");

        jLabel3.setText("Kendaraan");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", "Roda 2", "Roda 3", "Roda 4" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jm.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jm.setText("/ Jam");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbltarif, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBox1, 0, 151, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(uu, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jm)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(13, 13, 13)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(uu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jm))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbltarif)
                .addContainerGap(33, Short.MAX_VALUE))
        );

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel7.setText("Tanggal :");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel8.setText("Jam :");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(jButton1)
                        .addGap(71, 71, 71)
                        .addComponent(jButton2)
                        .addGap(83, 83, 83)
                        .addComponent(jButton3)
                        .addGap(147, 147, 147))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lbljam, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(22, 22, 22)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lbltgl, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(jScrollPane1))
                        .addContainerGap())))
            .addGroup(layout.createSequentialGroup()
                .addGap(178, 178, 178)
                .addComponent(jLabel5)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lbltgl))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lbljam, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1)
                            .addComponent(jButton2)
                            .addComponent(jButton3))))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
        Object pilih = jComboBox1.getSelectedItem();
        if (pilih.equals("Roda 2")) {
            jComboBox1.setSelectedIndex(1);
            lbltarif.setVisible(true);
            uu.setVisible(true);
            jm.setVisible(true);
            lbltarif.setEnabled(false);
            lbltarif.setText("Sepeda, Sepeda Motor");
            uu.setText("2000");
            uu.setEnabled(false);
            jm.setEnabled(false);
        }
        if (pilih.equals("Roda 3")) {
            jComboBox1.setSelectedIndex(2);
            lbltarif.setVisible(true);
            uu.setVisible(true);
            jm.setVisible(true);
            lbltarif.setEnabled(false);
            lbltarif.setText("Bajaj, Bemo");
            uu.setText("3000");
            uu.setEnabled(false);
            jm.setEnabled(false);
        }
        if (pilih.equals("Roda 4")) {
            jComboBox1.setSelectedIndex(3);
            lbltarif.setVisible(true);
            jm.setEnabled(false);
            uu.setVisible(true);
            jm.setVisible(true);
            lbltarif.setEnabled(false);
            lbltarif.setText("Mobil, Truk");
            uu.setText("4000");
            uu.setEnabled(false);
        }
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        txtClear();
        refreshTabel();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        try {
            tblMasuk.insert(txtNoPar.getText(), txtNopol.getText(),txtNama.getText(), jComboBox1.getSelectedItem().toString(),Integer.parseInt(uu.getText()) , txtJamMasuk.getText());
            refreshTabel();
            txtClear();
            kode();
            JOptionPane.showMessageDialog(null, "Data Berhasil Masuk", "Pemberitahuan", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            Logger.getLogger(Masuk.class.getName()).log(Level.SEVERE, null, ex);
            try {
                if (ex.getErrorCode() == 1062) {
                    int confr = JOptionPane.showConfirmDialog(null, "Kode Barang sudah ada ! \nApakah ingin mengubah data?", "Peringatan", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                    if (confr == 0) {
                        tblMasuk.update(txtNoPar.getText(),
                                txtNopol.getText(),txtNama.getText(), jComboBox1.getSelectedItem().toString(),Integer.parseInt(uu.getText()),txtJamMasuk.getText());
                        refreshTabel();
                    } else {
                        txtClear();
                    }
                }
            } catch (SQLException ex1) {
                Logger.getLogger(Masuk.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        try {
            int confr = JOptionPane.showConfirmDialog(null, "Apakah data dengan kode "+txtNoPar.getText()+" akan dihapus?", "Konfirmasi", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (confr == 0) {
                tblMasuk.delete(txtNoPar.getText());
                txtClear();
                refreshTabel();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Masuk.class.getName()).log(Level.SEVERE, null, ex);

        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        // TODO add your handling code here:
        
    }//GEN-LAST:event_formMouseClicked

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        // TODO add your handling code here:
        refreshTabel();
    }//GEN-LAST:event_formComponentShown

    private void tabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelMouseClicked
        // TODO add your handling code here:
        tampilData();
    }//GEN-LAST:event_tabelMouseClicked

    private void tabelKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelKeyPressed
        // TODO add your handling code here:
        tampilData();
    }//GEN-LAST:event_tabelKeyPressed

    private void uuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_uuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_uuActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel jm;
    private javax.swing.JLabel lbljam;
    private javax.swing.JLabel lbltarif;
    private javax.swing.JLabel lbltgl;
    private javax.swing.JTable tabel;
    private javax.swing.JTextField txtJamMasuk;
    private javax.swing.JTextField txtNama;
    private javax.swing.JTextField txtNoPar;
    private javax.swing.JTextField txtNopol;
    private javax.swing.JTextField uu;
    // End of variables declaration//GEN-END:variables
}
