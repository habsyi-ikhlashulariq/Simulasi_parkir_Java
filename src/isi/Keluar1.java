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
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author habsy
 */
public class Keluar1 extends javax.swing.JInternalFrame {

    private tbKeluar1 tblKeluar;
    private Tb_transaksi tblTransaksi;
    private String No_parkir;
    private javax.swing.table.DefaultTableModel tblModel;
    private String data[] = new String[8];
    Connection con = null;
    Statement st = null;
    ResultSet r = null;
    String sql = null;

    /**
     * Creates new form Keluar1
     */
    public Keluar1() {
        initComponents();
        try {
            tblKeluar = new tbKeluar1();
            tblTransaksi = new Tb_transaksi();
            //refreshTabel();
            settable();
            Visiblef();
            Tanggal();
            jam();
        } catch (Exception ex) {
            Logger.getLogger(Keluar1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void viewdata(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/parkir1db", "root", "");
            st = con.createStatement();
            if(txtNoPar.getText().length()==0){
              ResultSet sq = st.executeQuery("SELECT * FROM data");
              this.settable();
            }else if(txtNoPar.getText().length() != 0){
                ResultSet sq = st.executeQuery("SELECT * FROM data WHERE No_parkir like'"+txtNoPar.getText()+"%'");
              this.settable();
            }
        } catch (ClassNotFoundException | SQLException ex) {
              Logger.getLogger(Keluar1.class.getName()).log(Level.SEVERE, null, ex);
        }       
    }
    public void settable(){
        int a= 100;
        Object [][] data= new Object[a][8];
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/parkir1db", "root", "");
            st = con.createStatement();
            sql = "SELECT * FROM data WHERE No_parkir like'"+txtNoPar.getText()+"%';";
            ResultSet rs = st.executeQuery(sql);
            
            int i=0;
            
            while (rs.next()){
              data[i][0] = rs.getString("No_parkir");
                data[i][1] = rs.getString("No_polisi");
                data[i][2] = rs.getString("nama");               
                data[i][3] = rs.getString("Jenis_kendaraan");
                data[i][4] = rs.getString("tarif");
                data[i][5] = rs.getString("masuk");
                data[i][6] = rs.getString("keluar");
                data[i][7] = rs.getString("Lama");
                i++;
            }
            String[] setJudul={"No_Parkir", "No_Polisi","Nama", "Jenis_kendaraan","tarif","masuk","keluar","Lama"};
            tabel.setModel(new DefaultTableModel(data,setJudul));
            data= new Object[i][8];
        } catch (Exception e) {
        }
    }
    public void Tanggal() {
        java.util.Date sekarang = new java.util.Date();
        java.text.SimpleDateFormat ka = new java.text.SimpleDateFormat("dd-MM-yyyy");
        //lbltgl.setText(ka.format("Tanggal : "+sekarang));
        lblTgl.setText(ka.format(sekarang));
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
                lblJam.setText(jam + ":" + menit + ":" + detik);
                //txtJamMasuk.setText("Jam : "+jam +":" + menit + ":" + detik);
            }
        };
        new Timer(1000, taks).start();
    }

    public void Visiblef() {
        txtJamKeluar.setVisible(false);
        jLabel7.setVisible(false);
        jLabel8.setVisible(false);
        txtLama.setVisible(false);
        btnHitung.setVisible(false);
        txtid.setVisible(false);
        btnKeluar.setVisible(true);
        txtNoPar.requestFocus();
    }

    public void Visiblet() {
        txtJamKeluar.setVisible(true);
        jLabel7.setVisible(true);
        jLabel8.setVisible(true);
        txtLama.setVisible(true);
        btnHitung.setVisible(true);
        btnKeluar.setVisible(false);
        Kode();

    }

    public void hitung() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/parkir1db", "root", "");
            st = con.createStatement();
            sql = "SELECT timediff(keluar,masuk),datediff(keluar,masuk) from data where No_parkir='" + txtNoPar.getText() + "';";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                int time = rs.getInt(1);
                int day = rs.getInt(2);
                String jam = (String) String.valueOf(time);
                String hari = (String) String.valueOf(day);
                txtLama.setText(jam);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Keluar1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void Kode() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/parkir1db", "root", "");
            st = con.createStatement();
            sql = "SELECT COUNT(*)FROM transaksi;";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                int no = rs.getInt(1);
                int nomor = no + 1;
                String id_transaksi = (String) String.valueOf(nomor);
                txtid.setText(id_transaksi);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Keluar1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /*public void refreshTabel() {
        tblModel = tblKeluar.defaultTabelModel();
        tabel.setModel(tblModel);
        tblKeluar.initTable(tblModel);
        viewdata();
    }*/

    public void setNo_parkir(String No_parkir) {
        try {
            ResultSet dataBarang = tblKeluar.viewData(No_parkir);
            dataBarang.next();
            txtNoPol.setText(dataBarang.getString("No_polisi"));
            txtJenisKen.setText(dataBarang.getString("Jenis_kendaraan"));
            txtTarif.setText(dataBarang.getString("tarif"));
            txtJamMasuk.setText(dataBarang.getString("masuk"));
            //txtUang.requestFocus();
        } catch (SQLException ex) {
            Logger.getLogger(Keluar1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setNo_par(String No_parkir) {
        try {
            ResultSet Data = tblKeluar.viewData(No_parkir);
            Data.next();
            jLabel9.setText(Data.getString("nama"));
        } catch (SQLException ex) {
            jLabel9.setText("");
        }
    }

    public void tampilData() {
        Visiblet();
        int row = tabel.getSelectedRow();
        txtNoPar.setText(tabel.getValueAt(row, 0).toString());
        txtNoPol.setText(tabel.getValueAt(row, 1).toString());
        jLabel9.setText(tabel.getValueAt(row, 2).toString());
        txtJenisKen.setText(tabel.getValueAt(row, 3).toString());
        txtTarif.setText(tabel.getValueAt(row, 4).toString());
        txtJamMasuk.setText(tabel.getValueAt(row, 5).toString());
        txtJamKeluar.setText(tabel.getValueAt(row, 6).toString());
        //txtLama.setText(tblModel.getValueAt(row, 7).toString());
        //txtJamKeluar.setText(tblModel.getValueAt(row, 7).toString());
        txtNoPar.setEnabled(false);
    }

    public void total() {
        int Tarif = Integer.parseInt(txtTarif.getText());
        int Lama = Integer.parseInt(txtLama.getText());
        int total = Tarif * Lama;
        txtTotal.setText("" + total);
    }

    public void txtClear() {
        txtNoPar.setEnabled(true);
        txtNoPar.setText(null);
        txtNoPol.setText(null);
        txtJenisKen.setText(null);
        txtTarif.setText(null);
        txtTotal.setText(null);
        txtUang.setText(null);
        txtSisa.setText(null);
        txtJamMasuk.setText(null);
        jLabel9.setText(null);
        Visiblef();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tabel = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtNoPol = new javax.swing.JTextField();
        txtJenisKen = new javax.swing.JTextField();
        txtTarif = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        txtJamMasuk = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtJamKeluar = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtLama = new javax.swing.JTextField();
        btnHitung = new javax.swing.JButton();
        btnKeluar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        txtUang = new javax.swing.JTextField();
        txtSisa = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        txtid = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        lblTgl = new javax.swing.JLabel();
        lblJam = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtNoPar = new javax.swing.JTextField();
        btnCari = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Parkir Sederhana");

        tabel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8"
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

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Data Masuk", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tempus Sans ITC", 1, 18))); // NOI18N

        jLabel2.setText("No Pol");

        jLabel3.setText("Kendaraaan");

        jLabel4.setText("Tarif");

        txtNoPol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNoPolActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel16.setText("/ Jam");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2))
                        .addGap(39, 39, 39)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNoPol)
                            .addComponent(txtJenisKen)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txtTarif, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel16)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtNoPol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtJenisKen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtTarif, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addGap(37, 37, 37))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Waktu", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tempus Sans ITC", 1, 18))); // NOI18N

        jLabel5.setText("Jam MasuK");

        jLabel7.setText("Jam Keluar");

        jLabel8.setText("Lama ");

        btnHitung.setText("Lama");
        btnHitung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHitungActionPerformed(evt);
            }
        });

        btnKeluar.setText("Keluar");
        btnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKeluarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtJamKeluar, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btnKeluar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnHitung))
                    .addComponent(txtLama)
                    .addComponent(txtJamMasuk))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtJamMasuk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtJamKeluar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnHitung)
                    .addComponent(btnKeluar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtLama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Transaksi", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tempus Sans ITC", 1, 18))); // NOI18N

        jLabel10.setText("Total");

        jLabel11.setText("Uang");

        jLabel12.setText("Sisa");

        txtUang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtUangKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(18, 18, 18)
                        .addComponent(txtTotal))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(jLabel12))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtUang)
                            .addComponent(txtSisa))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtUang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtSisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jButton1.setText("Simpan");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Hapus");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Tambah");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        txtid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtidActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel13.setText("FORM KELUAR");

        lblTgl.setFont(new java.awt.Font("Tempus Sans ITC", 1, 18)); // NOI18N
        lblTgl.setText("jLabel14");

        lblJam.setFont(new java.awt.Font("Tempus Sans ITC", 1, 18)); // NOI18N
        lblJam.setText("jLabel14");

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel14.setText("Tanggal :");

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel15.setText("Jam :");

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Telusuri Data", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tempus Sans ITC", 1, 18))); // NOI18N

        jLabel1.setText("No Par");

        txtNoPar.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtNoParCaretUpdate(evt);
            }
        });
        txtNoPar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNoParKeyReleased(evt);
            }
        });

        btnCari.setText("Telusuri");
        btnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCari)
                    .addComponent(txtNoPar, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtNoPar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCari))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(jButton3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton1)
                                .addGap(42, 42, 42)
                                .addComponent(jButton2))
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblJam, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtid, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblTgl, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(194, 194, 194)
                .addComponent(jLabel13)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblJam)
                        .addComponent(jLabel15)
                        .addComponent(lblTgl)))
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(9, 9, 9)
                .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKeluarActionPerformed
        // TODO add your handling code here:
        try {
            tblKeluar.update(txtNoPar.getText());
            //refreshTabel();
            //txtClear();
            viewdata();
        } catch (SQLException ex) {
            Logger.getLogger(Masuk.class.getName()).log(Level.SEVERE, null, ex);
            try {
                if (ex.getErrorCode() == 1062) {
                    int confr = JOptionPane.showConfirmDialog(null, "Kode Barang sudah ada ! \nApakah ingin mengubah data?", "Peringatan", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                    if (confr == 0) {
                        tblKeluar.update(txtNoPar.getText());

                        //refreshTabel();
                        viewdata();
                    } else {
                        txtClear();
                    }
                }
            } catch (SQLException ex1) {
                Logger.getLogger(Masuk.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }//GEN-LAST:event_btnKeluarActionPerformed

    private void txtNoParKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNoParKeyReleased
        // TODO add your handling code here:
        viewdata();
        setNo_par(txtNoPar.getText());
    }//GEN-LAST:event_txtNoParKeyReleased

    private void btnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariActionPerformed
        // TODO add your handling code here:
        setNo_parkir(txtNoPar.getText());
    }//GEN-LAST:event_btnCariActionPerformed

    private void tabelKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelKeyPressed
        // TODO add your handling code here:
        tampilData();
    }//GEN-LAST:event_tabelKeyPressed

    private void tabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelMouseClicked
        // TODO add your handling code here:
        tampilData();
        /*int row=tabel.getSelectedRow();
        String no=(String) tabel.getValueAt(row,0);
        txtNoPar.setText(no);*/
    }//GEN-LAST:event_tabelMouseClicked

    private void btnHitungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHitungActionPerformed
        // TODO add your handling code here:
        try {
            hitung();
            txtUang.requestFocus();
            tblKeluar.update1(txtNoPar.getText(), Integer.parseInt(txtLama.getText()));
            //refreshTabel();
            viewdata();
            total();
            //txtClear();
        } catch (SQLException ex) {
            Logger.getLogger(Masuk.class.getName()).log(Level.SEVERE, null, ex);
            try {
                if (ex.getErrorCode() == 1062) {
                    int confr = JOptionPane.showConfirmDialog(null, "Kode Barang sudah ada ! \nApakah ingin mengubah data?", "Peringatan", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                    if (confr == 0) {
                        tblKeluar.update1(txtNoPar.getText(), Integer.parseInt(txtLama.getText()));
                        //refreshTabel();
                        viewdata();
                    } else {
                        txtClear();
                    }
                }
            } catch (SQLException ex1) {
                Logger.getLogger(Masuk.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }//GEN-LAST:event_btnHitungActionPerformed

    private void txtUangKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUangKeyReleased
        // TODO add your handling code here:
        int Kembalian = Integer.parseInt(txtUang.getText()) - Integer.parseInt(txtTotal.getText());
        txtSisa.setText(Integer.toString(Kembalian));
    }//GEN-LAST:event_txtUangKeyReleased

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
     
        txtClear();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        try {
            int confr = JOptionPane.showConfirmDialog(this, "Apakah Data dengan Kode" + txtNoPar.getText() + "akan Dihapus??", "Konfirmasi", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
            if (confr == 0) {
                tblKeluar.delete(txtNoPar.getText());
                txtClear();
                //refreshTabel();
                viewdata();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Keluar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        try {
                    
            tblTransaksi.insert(txtid.getText(), Integer.parseInt(txtTotal.getText()), Integer.parseInt(txtUang.getText()), Integer.parseInt(txtSisa.getText()));
            JOptionPane.showMessageDialog(null, "Data Dengan Kode " + txtNoPar.getText() + " Berhasil Keluar", "Pemberitahuan", JOptionPane.INFORMATION_MESSAGE);
                        //refreshTabel(); 
                        viewdata();
                         txtClear();
                        
            
        } catch (SQLException ex) {
            Logger.getLogger(Masuk.class.getName()).log(Level.SEVERE, null, ex);
            try {
                if (ex.getErrorCode() == 1062) {
                    int confr = JOptionPane.showConfirmDialog(null, "Kode Barang sudah ada ! \nApakah ingin mengubah data?", "Peringatan", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                    if (confr == 0) {
                        tblTransaksi.update(txtid.getText(), Integer.parseInt(txtTotal.getText()), Integer.parseInt(txtUang.getText()), Integer.parseInt(txtSisa.getText()));

                        //refreshTabel();
                        viewdata();
                    } else {
                        txtClear();
                    }
                }
            } catch (SQLException ex1) {
                Logger.getLogger(Masuk.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtidActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtidActionPerformed

    private void txtNoPolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNoPolActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNoPolActionPerformed

    private void txtNoParCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtNoParCaretUpdate
        // TODO add your handling code here:
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/parkir1db", "root", "");
            st = con.createStatement();
            sql = "Select * from data Where No_parkir='" + txtNoPar.getText() + "';";
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                jLabel9.setText("nama");
                btnCari.setText("Telusuri");
                btnCari.setEnabled(true);
            } else {
                
                jLabel9.setText("Tidak Terdeteksi");
                btnCari.setText("Kosong");
                btnCari.setEnabled(false);
            }
            st.close();
            rs.close();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Masuk.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_txtNoParCaretUpdate


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCari;
    private javax.swing.JButton btnHitung;
    private javax.swing.JButton btnKeluar;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblJam;
    private javax.swing.JLabel lblTgl;
    private javax.swing.JTable tabel;
    private javax.swing.JTextField txtJamKeluar;
    private javax.swing.JTextField txtJamMasuk;
    private javax.swing.JTextField txtJenisKen;
    private javax.swing.JTextField txtLama;
    private javax.swing.JTextField txtNoPar;
    private javax.swing.JTextField txtNoPol;
    private javax.swing.JTextField txtSisa;
    private javax.swing.JTextField txtTarif;
    private javax.swing.JTextField txtTotal;
    private javax.swing.JTextField txtUang;
    private javax.swing.JTextField txtid;
    // End of variables declaration//GEN-END:variables
}
