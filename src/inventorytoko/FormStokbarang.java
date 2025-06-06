    /*
     * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
     * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
     */
    package inventorytoko;
    import java.sql.Connection;
    import java.sql.ResultSet;
    import java.sql.PreparedStatement;
    import java.text.SimpleDateFormat;
    import java.util.Date;
    import java.util.Set;
    import javax.swing.Timer;
    import koneksi.koneksi;
    import javax.swing.JOptionPane;
    import javax.swing.JTable;
    import javax.swing.table.DefaultTableModel;//di add karena form ini terdapat table
    import javax.swing.event.DocumentEvent;
    import javax.swing.event.DocumentListener;

    /**
     *
     * @author OsaFirzian
     */
    public class FormStokbarang extends javax.swing.JFrame {

    //private String username; // Tambahkan field untuk menyimpan username
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
    
    
    
    //method untuk menampilkan data pada table di database ke table di JFORM
    
    private Connection conn = new koneksi().connect(); //Untuk Konek ke database
    private DefaultTableModel tabmode; //Deklarasi
    
    protected void datatable(){
        Object[] Baris ={"Kode Barang","Nama Barang","Stok Barang"};
        tabmode = new DefaultTableModel(null, Baris);
        tableStokbarang.setModel(tabmode);
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
    
    
    private JTable tableStokBarang;

    //Buat Search Barang
    private void searchBarang() {
    String keyword = textSearchbar.getText();
    DefaultTableModel model = new DefaultTableModel();
    model.addColumn("Kode Barang");
    model.addColumn("Nama Barang");
    model.addColumn("Stok Barang");

    try {
        String sql = "SELECT * FROM stokbarang WHERE kode_barang LIKE ? OR nama_barang LIKE ?";
        Connection conn = new koneksi().connect(); // Pastikan class koneksi ada
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, "%" + keyword + "%");
        pst.setString(2, "%" + keyword + "%");

        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getString("kode_barang"),
                rs.getString("nama_barang"),
                rs.getInt("stok_barang")
            });
        }

            tableStokbarang.setModel(model); // Sesuaikan nama tabel kamu
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error saat mencari data: " + e.getMessage());
        }
    }
    
    
    
    /**
     * Creates new form TampilanUtama
     */
    public FormStokbarang() {
        initComponents();
        showTanggalDanJam();
       
    }
    
    public FormStokbarang(String username, String role) {
        initComponents();
//        this.username = username;
        showTanggalDanJam();
        
        this.loggedInUsername = username;
        this.loggedInRole = role;
        displayUserAndRole();
        datatable();
        
            textSearchbar.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                searchBarang();
            }

            public void removeUpdate(DocumentEvent e) {
                searchBarang();
            }

            public void changedUpdate(DocumentEvent e) {
                searchBarang();
            }
        });
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
        containerTable = new javax.swing.JScrollPane();
        tableStokbarang = new javax.swing.JTable();
        textSearchbar = new javax.swing.JTextField();
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

        containerTable.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 3, true));

        tableStokbarang.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        tableStokbarang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Kode Barang", "Nama Barang", "Stok Barang"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableStokbarang.setRowHeight(25);
        tableStokbarang.setRowMargin(5);
        containerTable.setViewportView(tableStokbarang);
        if (tableStokbarang.getColumnModel().getColumnCount() > 0) {
            tableStokbarang.getColumnModel().getColumn(0).setResizable(false);
            tableStokbarang.getColumnModel().getColumn(0).setPreferredWidth(75);
            tableStokbarang.getColumnModel().getColumn(1).setResizable(false);
            tableStokbarang.getColumnModel().getColumn(1).setPreferredWidth(300);
            tableStokbarang.getColumnModel().getColumn(2).setResizable(false);
            tableStokbarang.getColumnModel().getColumn(2).setPreferredWidth(50);
        }

        baseBG.add(containerTable, new org.netbeans.lib.awtextra.AbsoluteConstraints(182, 230, 570, 340));

        textSearchbar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        textSearchbar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        baseBG.add(textSearchbar, new org.netbeans.lib.awtextra.AbsoluteConstraints(184, 190, 180, 25));

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
        Title.setText("STOK BARANG");
        baseBG.add(Title, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 120, -1, -1));

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
            java.util.logging.Logger.getLogger(FormStokbarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormStokbarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormStokbarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormStokbarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormStokbarang().setVisible(true);
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
    private javax.swing.JScrollPane containerTable;
    private javax.swing.JLabel exitbtn;
    private javax.swing.JLabel labelJam;
    private javax.swing.JLabel labelTanggal;
    private javax.swing.JLabel labelUser;
    private javax.swing.JTable tableStokbarang;
    private javax.swing.JTextField textSearchbar;
    // End of variables declaration//GEN-END:variables
}
