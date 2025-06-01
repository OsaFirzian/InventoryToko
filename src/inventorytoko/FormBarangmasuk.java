/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package inventorytoko;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.Timer;
import javax.swing.JOptionPane;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.text.ParseException;
import javax.swing.table.DefaultTableModel;
import koneksi.koneksi;

/**
 *
 * @author OsaFirzian
 */
public class FormBarangmasuk extends javax.swing.JFrame {
    
//        private String username; // Tambahkan field untuk menyimpan username
        private String loggedInUsername; // Untuk menyimpan username yang login
        private String loggedInRole;     // Untuk menyimpan role yang login

        //Buat Jam
    private void showTanggalDanJam() {
        // Format tanggal
        SimpleDateFormat tglFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat jamFormat = new SimpleDateFormat("HH:mm:ss");

        // Timer berjalan setiap detik
        Timer timer = new Timer(1000, (e) -> {
            Date now = new Date();
            labelTanggal.setText(tglFormat.format(now));
            labelJam.setText(jamFormat.format(now));
        });
        timer.start();
    }
    
    private void displayUserAndRole() {
        
        labelUser.setText("User: " + loggedInUsername + "   Role: " + loggedInRole); 
        // Pastikan Anda memiliki JLabel dengan nama lblUserInfo di desain FormDashboard Anda.
    } 
    
    
    protected void kosong(){
        textNamabarang.setText("");
        textJumlah.setText("");
    }
    
    
    private DefaultTableModel tabmode;
    
    protected void datatable(){
        Object[] Baris ={"Kode Barang","Nama Barang","Stok Barang"};
        tabmode = new DefaultTableModel(null, Baris);
        tableBarangMasuk.setModel(tabmode);
        String sql = "select * from stokbarang";
                
       try{
           java.sql.Statement stat = conn.createStatement();
           ResultSet hasil = stat.executeQuery(sql);
           
           while(hasil.next()) {
               String a = hasil.getString("kode_barang");
               String b = hasil.getString("nama_barang");
               String c = hasil.getString("stok_barang");
               
//               System.out.println(a + " " + b + " " + c); // debug
               
               String[] data = {a,b,c};
               tabmode.addRow(data);
           }
       } catch (Exception e){
            e.printStackTrace(); // Ini akan mencetak detail error ke konsol NetBeans (Output Window)
            JOptionPane.showMessageDialog(this, "Error memuat data: " + e.getMessage(), "Error Database", JOptionPane.ERROR_MESSAGE);
       }
    }
    
    private Connection conn = new koneksi().connect();
    
    private void SaveBarangmasuk() {
        String namaBarang = textNamabarang.getText();
        int jumlah = Integer.parseInt(textJumlah.getText());

        try {
            // Cek apakah barang dengan nama tersebut sudah ada
            String sql = "SELECT stok_barang FROM stokbarang WHERE nama_barang = ?";
            PreparedStatement cekStmt = conn.prepareStatement(sql);
            cekStmt.setString(1, namaBarang);
            ResultSet cekRs = cekStmt.executeQuery();

            if (cekRs.next()) {
                // Barang sudah ada, update stok
                int stokLama = cekRs.getInt("stok_barang");
                int stokBaru = stokLama + jumlah;

                String updateSQL = "UPDATE stokbarang SET stok_barang = ? WHERE nama_barang = ?";
                PreparedStatement updateStmt = conn.prepareStatement(updateSQL);
                updateStmt.setInt(1, stokBaru);
                updateStmt.setString(2, namaBarang);
                updateStmt.executeUpdate();

                JOptionPane.showMessageDialog(null, "Stok barang berhasil diperbarui.");
            } else {
                // Ambil kode_barang terakhir dari database
                String getLastCodeSQL = "SELECT kode_barang FROM stokbarang ORDER BY kode_barang DESC LIMIT 1";
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(getLastCodeSQL);

                String kodeBarang = "ID000"; // Default pertama kali
                if (rs.next()) {
                    String lastKode = rs.getString("kode_barang");
                    try {
                        int number = Integer.parseInt(lastKode.substring(2)) + 1;
                        kodeBarang = String.format("ID%03d", number);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Format kode_barang sebelumnya tidak valid.");
                        return;
                    }
                }

                // Insert barang baru
                String insertSQL = "INSERT INTO stokbarang (kode_barang, nama_barang, stok_barang) VALUES (?, ?, ?)";
                PreparedStatement insertStmt = conn.prepareStatement(insertSQL);
                insertStmt.setString(1, kodeBarang);
                insertStmt.setString(2, namaBarang);
                insertStmt.setInt(3, jumlah);
                insertStmt.executeUpdate();

                JOptionPane.showMessageDialog(null, "Data barang berhasil disimpan.");
            }

            kosong(); // Clear input field
            textNamabarang.requestFocus();
            datatable(); // Refresh tabel
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Data Gagal Disimpan: " + e);
        }
    }

    
    private void editBarangMasuk(){
        try {
        int selectedRow = tableBarangMasuk.getSelectedRow(); // ambil baris yang diklik
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Pilih data yang ingin diedit.");
            return;
        }
            String kodeBarang = tableBarangMasuk.getValueAt(selectedRow, 0).toString();
            String sql = "update stokbarang set nama_barang=?,stok_barang=? WHERE kode_barang = ?";
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, textNamabarang.getText());
            stat.setString(2, textJumlah.getText());
            stat.setString(3, kodeBarang); // pastikan ini diisi dari data yg diklik di tabel
            
            stat.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Berhasil diubah");
            kosong();
            textNamabarang.requestFocus();
            datatable();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Data Gagal Diubah "+e);
        } 
    }
    
    private void deleteBarang(){
        
        int selectedRow = tableBarangMasuk.getSelectedRow(); // ambil baris yang diklik
        if (selectedRow == -1) {
                JOptionPane.showMessageDialog(null, "Pilih baris yang ingin dihapus terlebih dahulu.");
                return;
            }

            String kodeBarang = tableBarangMasuk.getValueAt(selectedRow, 0).toString(); // Ambil kode_barang dari kolom 0

        int confirm = JOptionPane.showConfirmDialog(null,"Apakah Anda yakin ingin menghapus data dengan kode: " + kodeBarang + "?",
                "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                String sql = "DELETE FROM stokbarang WHERE kode_barang = ?";
                PreparedStatement stat = conn.prepareStatement(sql);
                stat.setString(1, kodeBarang);
                stat.executeUpdate();

                JOptionPane.showMessageDialog(null, "Data berhasil dihapus.");
                kosong(); // method untuk membersihkan input field
                datatable(); // reload tabel
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Gagal menghapus data: " + e.getMessage());
            }
        }
    }
    
    
    /**
     * Creates new form TampilanUtama
     */
    public FormBarangmasuk() {
        initComponents();
        showTanggalDanJam();
    }
    
    public FormBarangmasuk(String username, String role) {
        initComponents();
//        this.username = username;
        showTanggalDanJam();
        datatable();
        this.loggedInUsername = username;
        this.loggedInRole = role;
        displayUserAndRole();
        
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        baseBG = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableBarangMasuk = new javax.swing.JTable();
        textNamabarang = new javax.swing.JTextField();
        labelNamabarang = new javax.swing.JLabel();
        labelJumlahbarang = new javax.swing.JLabel();
        textJumlah = new javax.swing.JTextField();
        buttonDelete = new javax.swing.JButton();
        buttonEdit = new javax.swing.JButton();
        buttonSave = new javax.swing.JButton();
        Laporan = new javax.swing.JButton();
        DataPenyewa = new javax.swing.JButton();
        BarangKeluar = new javax.swing.JButton();
        BarangMasuk = new javax.swing.JButton();
        StokBarang = new javax.swing.JButton();
        Dashboard = new javax.swing.JButton();
        labelJam = new javax.swing.JLabel();
        labelTanggal = new javax.swing.JLabel();
        labelUser = new javax.swing.JLabel();
        Title = new javax.swing.JLabel();
        exitbtn = new javax.swing.JLabel();
        background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        setSize(new java.awt.Dimension(800, 600));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        baseBG.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                baseBGMouseDragged(evt);
            }
        });
        baseBG.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                baseBGMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                baseBGMousePressed(evt);
            }
        });
        baseBG.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tableBarangMasuk.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        tableBarangMasuk.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Kode Barang", "Nama Barang", "Stok Barang"
            }
        ));
        tableBarangMasuk.setRowHeight(25);
        tableBarangMasuk.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableBarangMasukMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableBarangMasuk);

        baseBG.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(232, 180, 470, 180));

        textNamabarang.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        baseBG.add(textNamabarang, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 390, 300, 30));

        labelNamabarang.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        labelNamabarang.setText("Nama Barang");
        baseBG.add(labelNamabarang, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 390, 150, 30));

        labelJumlahbarang.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        labelJumlahbarang.setText("Jumlah Barang");
        baseBG.add(labelJumlahbarang, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 440, 150, 30));

        textJumlah.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        baseBG.add(textJumlah, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 440, 300, 30));

        buttonDelete.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        buttonDelete.setText("Delete");
        buttonDelete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buttonDeleteMouseClicked(evt);
            }
        });
        buttonDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonDeleteActionPerformed(evt);
            }
        });
        baseBG.add(buttonDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 520, 120, 35));

        buttonEdit.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        buttonEdit.setText("Edit");
        buttonEdit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buttonEditMouseClicked(evt);
            }
        });
        buttonEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonEditActionPerformed(evt);
            }
        });
        baseBG.add(buttonEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 520, 120, 35));

        buttonSave.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        buttonSave.setText("Save");
        buttonSave.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buttonSaveMouseClicked(evt);
            }
        });
        buttonSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSaveActionPerformed(evt);
            }
        });
        baseBG.add(buttonSave, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 520, 120, 35));

        Laporan.setBackground(new java.awt.Color(254, 129, 0));
        Laporan.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        Laporan.setForeground(new java.awt.Color(255, 255, 255));
        Laporan.setText("Laporan");
        Laporan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Laporan.setBorderPainted(false);
        Laporan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Laporan.setFocusPainted(false);
        Laporan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LaporanMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                LaporanMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                LaporanMouseExited(evt);
            }
        });
        baseBG.add(Laporan, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 380, 135, 35));

        DataPenyewa.setBackground(new java.awt.Color(254, 129, 0));
        DataPenyewa.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        DataPenyewa.setForeground(new java.awt.Color(255, 255, 255));
        DataPenyewa.setText("Data Penyewa");
        DataPenyewa.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        DataPenyewa.setBorderPainted(false);
        DataPenyewa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        DataPenyewa.setFocusPainted(false);
        DataPenyewa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                DataPenyewaMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                DataPenyewaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                DataPenyewaMouseExited(evt);
            }
        });
        baseBG.add(DataPenyewa, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 330, 135, 35));

        BarangKeluar.setBackground(new java.awt.Color(254, 129, 0));
        BarangKeluar.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        BarangKeluar.setForeground(new java.awt.Color(255, 255, 255));
        BarangKeluar.setText("Barang Keluar");
        BarangKeluar.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        BarangKeluar.setBorderPainted(false);
        BarangKeluar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BarangKeluar.setFocusPainted(false);
        BarangKeluar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BarangKeluarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                BarangKeluarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                BarangKeluarMouseExited(evt);
            }
        });
        baseBG.add(BarangKeluar, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 280, 135, 35));

        BarangMasuk.setBackground(new java.awt.Color(254, 129, 0));
        BarangMasuk.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        BarangMasuk.setForeground(new java.awt.Color(255, 255, 255));
        BarangMasuk.setText("Barang Masuk");
        BarangMasuk.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        BarangMasuk.setBorderPainted(false);
        BarangMasuk.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BarangMasuk.setFocusPainted(false);
        BarangMasuk.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BarangMasukMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                BarangMasukMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                BarangMasukMouseExited(evt);
            }
        });
        baseBG.add(BarangMasuk, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 230, 135, 35));

        StokBarang.setBackground(new java.awt.Color(254, 129, 0));
        StokBarang.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        StokBarang.setForeground(new java.awt.Color(255, 255, 255));
        StokBarang.setText("Stok Barang");
        StokBarang.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        StokBarang.setBorderPainted(false);
        StokBarang.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        StokBarang.setFocusPainted(false);
        StokBarang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                StokBarangMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                StokBarangMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                StokBarangMouseExited(evt);
            }
        });
        baseBG.add(StokBarang, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 180, 135, 35));

        Dashboard.setBackground(new java.awt.Color(254, 129, 0));
        Dashboard.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        Dashboard.setForeground(new java.awt.Color(255, 255, 255));
        Dashboard.setText("Dashboard");
        Dashboard.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Dashboard.setBorderPainted(false);
        Dashboard.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Dashboard.setFocusPainted(false);
        Dashboard.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                DashboardMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                DashboardMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                DashboardMouseExited(evt);
            }
        });
        baseBG.add(Dashboard, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 130, 135, 35));

        labelJam.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        labelJam.setText("HH:mm:ss");
        baseBG.add(labelJam, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 110, 70, 15));

        labelTanggal.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        labelTanggal.setText("yyyy-MM-dd");
        baseBG.add(labelTanggal, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 110, 90, 15));

        labelUser.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        labelUser.setText("User");
        baseBG.add(labelUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 130, 160, 15));

        Title.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        Title.setForeground(new java.awt.Color(51, 51, 51));
        Title.setText("BARANG MASUK");
        baseBG.add(Title, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 118, -1, -1));

        exitbtn.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        exitbtn.setForeground(new java.awt.Color(255, 255, 255));
        exitbtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/exit.png"))); // NOI18N
        exitbtn.setText("Exit");
        exitbtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        exitbtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                exitbtnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                exitbtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                exitbtnMouseExited(evt);
            }
        });
        baseBG.add(exitbtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 530, 90, 30));

        background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/bg2.jpg"))); // NOI18N
        baseBG.add(background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 600));

        getContentPane().add(baseBG, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 600));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    
    // Button Exit
    private void exitbtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitbtnMouseClicked
        FormLogin login = new FormLogin();
        login.setVisible(true);
        this.dispose(); 
    }//GEN-LAST:event_exitbtnMouseClicked

    private void baseBGMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_baseBGMouseClicked
        // TODO  your handling code here:
    }//GEN-LAST:event_baseBGMouseClicked

    
    //Biar Panelnya bisa di drag
    //Panel Utama
    int a, b;
    private void baseBGMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_baseBGMousePressed
        a = evt.getX();
        b = evt.getY();
    }//GEN-LAST:event_baseBGMousePressed

    private void baseBGMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_baseBGMouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - a, y - b);
    }//GEN-LAST:event_baseBGMouseDragged
    
    //Buat Efek Hover
    //Exit Button
    private void exitbtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitbtnMouseEntered
        exitbtn.setFont(new java.awt.Font("sansserif", 1, 18));
        exitbtn.setForeground(new java.awt.Color(255, 179, 71));
        exitbtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/exit-hover.png")));
    }//GEN-LAST:event_exitbtnMouseEntered

    private void exitbtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitbtnMouseExited
        exitbtn.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        exitbtn.setForeground(new java.awt.Color(255, 255, 255));
        exitbtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/exit.png")));
    }//GEN-LAST:event_exitbtnMouseExited
    
    //Buat Efek Hover
    //Dashboard
    private void DashboardMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DashboardMouseEntered
        Dashboard.setBackground(new java.awt.Color(255, 179, 71));
        Dashboard.setForeground(new java.awt.Color(0, 0, 0));
    }//GEN-LAST:event_DashboardMouseEntered

    private void DashboardMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DashboardMouseExited
        Dashboard.setBackground(new java.awt.Color(254, 129, 0));
        Dashboard.setForeground(new java.awt.Color(255, 255, 255));
    }//GEN-LAST:event_DashboardMouseExited

    
    //Buat Efek Hover
    //Stok Barang
    private void StokBarangMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_StokBarangMouseEntered
        StokBarang.setBackground(new java.awt.Color(255, 179, 71));
        StokBarang.setForeground(new java.awt.Color(0, 0, 0));
    }//GEN-LAST:event_StokBarangMouseEntered

    private void StokBarangMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_StokBarangMouseExited
        StokBarang.setBackground(new java.awt.Color(254, 129, 0));
        StokBarang.setForeground(new java.awt.Color(255, 255, 255));
    }//GEN-LAST:event_StokBarangMouseExited

    
    //Buat Efek Hover
    //Barang Masuk
    private void BarangMasukMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BarangMasukMouseEntered
        BarangMasuk.setBackground(new java.awt.Color(255, 179, 71));
        BarangMasuk.setForeground(new java.awt.Color(0, 0, 0));
    }//GEN-LAST:event_BarangMasukMouseEntered

    private void BarangMasukMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BarangMasukMouseExited
        BarangMasuk.setBackground(new java.awt.Color(254, 129, 0));
        BarangMasuk.setForeground(new java.awt.Color(255, 255, 255));
    }//GEN-LAST:event_BarangMasukMouseExited
    
    
    //Buat Efek Hover
    //Barang Keluar
    private void BarangKeluarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BarangKeluarMouseEntered
        BarangKeluar.setBackground(new java.awt.Color(255, 179, 71));
        BarangKeluar.setForeground(new java.awt.Color(0, 0, 0));
    }//GEN-LAST:event_BarangKeluarMouseEntered

    private void BarangKeluarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BarangKeluarMouseExited
        BarangKeluar.setBackground(new java.awt.Color(254, 129, 0));
        BarangKeluar.setForeground(new java.awt.Color(255, 255, 255));
    }//GEN-LAST:event_BarangKeluarMouseExited
    
    
    //Buat Efek Hover
    //Data Penyewa
    private void DataPenyewaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DataPenyewaMouseEntered
        DataPenyewa.setBackground(new java.awt.Color(255, 179, 71));
        DataPenyewa.setForeground(new java.awt.Color(0, 0, 0));
    }//GEN-LAST:event_DataPenyewaMouseEntered

    private void DataPenyewaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DataPenyewaMouseExited
        DataPenyewa.setBackground(new java.awt.Color(254, 129, 0));
        DataPenyewa.setForeground(new java.awt.Color(255, 255, 255));
    }//GEN-LAST:event_DataPenyewaMouseExited
    
    
    //Buat Efek Hover
    //Data Laporan
    private void LaporanMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LaporanMouseEntered
        Laporan.setBackground(new java.awt.Color(255, 179, 71));
        Laporan.setForeground(new java.awt.Color(0, 0, 0));
    }//GEN-LAST:event_LaporanMouseEntered

    private void LaporanMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LaporanMouseExited
        Laporan.setBackground(new java.awt.Color(254, 129, 0));
        Laporan.setForeground(new java.awt.Color(255, 255, 255));
    }//GEN-LAST:event_LaporanMouseExited

    
    //Perpindahan Antar Dashboard
    private void StokBarangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_StokBarangMouseClicked
   
    //Buat menyimpan nilai User dan Role agar tidak hilang saat berpindah JForm
    new FormStokbarang(this.loggedInUsername, this.loggedInRole).setVisible(true);
    
    this.dispose(); // Menutup form sekarang
    }//GEN-LAST:event_StokBarangMouseClicked

    private void DashboardMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DashboardMouseClicked
   
    //Buat menyimpan nilai User dan Role agar tidak hilang saat berpindah JForm
    new FormDashboard(this.loggedInUsername, this.loggedInRole).setVisible(true);
    
    this.dispose();
    }//GEN-LAST:event_DashboardMouseClicked

    private void BarangMasukMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BarangMasukMouseClicked
   
    //Buat menyimpan nilai User dan Role agar tidak hilang saat berpindah JForm
    new FormBarangmasuk(this.loggedInUsername, this.loggedInRole).setVisible(true);
    
    this.dispose();
    }//GEN-LAST:event_BarangMasukMouseClicked

    private void BarangKeluarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BarangKeluarMouseClicked

        //Buat menyimpan nilai User dan Role agar tidak hilang saat berpindah JForm
        new FormBarangkeluar(this.loggedInUsername, this.loggedInRole).setVisible(true);

        this.dispose();
    }//GEN-LAST:event_BarangKeluarMouseClicked

    private void DataPenyewaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DataPenyewaMouseClicked

        //Buat menyimpan nilai User dan Role agar tidak hilang saat berpindah JForm
        new FormDatapenyewa(this.loggedInUsername, this.loggedInRole).setVisible(true);

        this.dispose();
    }//GEN-LAST:event_DataPenyewaMouseClicked

    private void LaporanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LaporanMouseClicked

        //Buat menyimpan nilai User dan Role agar tidak hilang saat berpindah JForm
        new FormLaporan(this.loggedInUsername, this.loggedInRole).setVisible(true);

        this.dispose();
    }//GEN-LAST:event_LaporanMouseClicked

    private void buttonSaveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonSaveMouseClicked
        // Memanggil method saat tombol diklik
        SaveBarangmasuk();
    }//GEN-LAST:event_buttonSaveMouseClicked

    private void buttonSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSaveActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buttonSaveActionPerformed

    private void buttonEditMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonEditMouseClicked
        // TODO add your handling code here:
        editBarangMasuk();
    }//GEN-LAST:event_buttonEditMouseClicked

    private void buttonEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonEditActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buttonEditActionPerformed

    private void tableBarangMasukMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableBarangMasukMouseClicked
        // TODO add your handling code here:
        int bar = tableBarangMasuk.getSelectedRow();
        String a = tabmode.getValueAt(bar, 1).toString();
        String b = tabmode.getValueAt(bar, 2).toString();
        
        textNamabarang.setText(a);
        textJumlah.setText(b);
    }//GEN-LAST:event_tableBarangMasukMouseClicked

    private void buttonDeleteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonDeleteMouseClicked
        // TODO add your handling code here:
        deleteBarang();
    }//GEN-LAST:event_buttonDeleteMouseClicked

    private void buttonDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonDeleteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buttonDeleteActionPerformed
    
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
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FormBarangmasuk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormBarangmasuk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormBarangmasuk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormBarangmasuk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormBarangmasuk().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BarangKeluar;
    private javax.swing.JButton BarangMasuk;
    private javax.swing.JButton Dashboard;
    private javax.swing.JButton DataPenyewa;
    private javax.swing.JButton Laporan;
    private javax.swing.JButton StokBarang;
    private javax.swing.JLabel Title;
    private javax.swing.JLabel background;
    private javax.swing.JPanel baseBG;
    private javax.swing.JButton buttonDelete;
    private javax.swing.JButton buttonEdit;
    private javax.swing.JButton buttonSave;
    private javax.swing.JLabel exitbtn;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelJam;
    private javax.swing.JLabel labelJumlahbarang;
    private javax.swing.JLabel labelNamabarang;
    private javax.swing.JLabel labelTanggal;
    private javax.swing.JLabel labelUser;
    private javax.swing.JTable tableBarangMasuk;
    private javax.swing.JTextField textJumlah;
    private javax.swing.JTextField textNamabarang;
    // End of variables declaration//GEN-END:variables
}
