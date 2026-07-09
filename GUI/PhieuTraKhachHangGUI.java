package GUI;

import BUS.PhieuTraKhachHangBUS;
import BUS.ChiTietPhieuTraKhachHangBUS;
import DTO.PhieuTraKhachHangDTO;
import DTO.ChiTietPhieuTraKhachHangDTO;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;

public class PhieuTraKhachHangGUI extends JPanel {
    private PhieuTraKhachHangBUS ptkhBUS = new PhieuTraKhachHangBUS();
    private ChiTietPhieuTraKhachHangBUS ctptkhBUS = new ChiTietPhieuTraKhachHangBUS();
    private ArrayList<ChiTietPhieuTraKhachHangDTO> listCT = new ArrayList<>();

    private JTable tblGioHang;
    private DefaultTableModel modelGioHang;
    private JTextField txtMaHD, txtMaSach, txtSoLuong, txtGiaHoan;
    private JButton btnThem, btnXacNhan;

    private final Color COLOR_BG = new Color(248, 244, 236);
    private final Color COLOR_PINK_BOLD = new Color(232, 60, 145);
    private final Color COLOR_PURPLE_DARK = new Color(67, 51, 76);

    public PhieuTraKhachHangGUI() {
        initCustomComponents();
    }

    private void initCustomComponents() {
        setLayout(new BorderLayout());
        setBackground(COLOR_BG);

        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setBackground(COLOR_PINK_BOLD);
        pnlHeader.setPreferredSize(new Dimension(0, 60));
        JLabel lblTitle = new JLabel("TIẾP NHẬN HÀNG TRẢ TỪ KHÁCH HÀNG");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        pnlHeader.add(lblTitle, BorderLayout.CENTER);

        JPanel pnlMain = new JPanel();
        pnlMain.setLayout(new BoxLayout(pnlMain, BoxLayout.X_AXIS));
        pnlMain.setBackground(COLOR_BG);
        pnlMain.setBorder(new EmptyBorder(30, 30, 30, 30));

        JPanel pnlLeft = new JPanel(new BorderLayout(0, 20));
        pnlLeft.setBackground(COLOR_BG);
        pnlLeft.setMaximumSize(new Dimension(400, 1000));
        pnlLeft.setPreferredSize(new Dimension(400, 0));

        JPanel pnlForm = new JPanel(new GridLayout(4, 1, 0, 15));
        pnlForm.setBackground(COLOR_BG);
        pnlForm.setBorder(BorderFactory.createTitledBorder(new LineBorder(COLOR_PURPLE_DARK), 
                " THÔNG TIN TRẢ HÀNG ", TitledBorder.LEFT, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 14), COLOR_PURPLE_DARK));
        
        pnlForm.add(createInputRow("MÃ HÓA ĐƠN:", txtMaHD = new JTextField()));
        pnlForm.add(createInputRow("MÃ SÁCH:", txtMaSach = new JTextField()));
        pnlForm.add(createInputRow("SỐ LƯỢNG TRẢ:", txtSoLuong = new JTextField()));
        pnlForm.add(createInputRow("GIÁ HOÀN LẠI:", txtGiaHoan = new JTextField()));

        btnThem = createStyledButton("THÊM VÀO DANH SÁCH", COLOR_PINK_BOLD);
        pnlLeft.add(pnlForm, BorderLayout.NORTH);
        pnlLeft.add(btnThem, BorderLayout.SOUTH);

        JPanel pnlRight = new JPanel(new BorderLayout(0, 20));
        pnlRight.setBackground(COLOR_BG);
        pnlRight.setBorder(BorderFactory.createTitledBorder(new LineBorder(COLOR_PURPLE_DARK), 
                " CHI TIẾT DANH SÁCH HOÀN TRẢ ", TitledBorder.LEFT, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 14), COLOR_PURPLE_DARK));

        modelGioHang = new DefaultTableModel(new String[]{"MÃ SÁCH", "SỐ LƯỢNG", "GIÁ HOÀN", "THÀNH TIỀN"}, 0);
        tblGioHang = new JTable(modelGioHang);
        tblGioHang.setRowHeight(35);
        tblGioHang.getTableHeader().setBackground(new Color(230, 220, 200));
        tblGioHang.getTableHeader().setForeground(COLOR_PURPLE_DARK);
        tblGioHang.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));

        btnXacNhan = createStyledButton("XÁC NHẬN NHẬP KHO HÀNG TRẢ", COLOR_BG);
        btnXacNhan.setBorder(new LineBorder(COLOR_PURPLE_DARK, 2));

        pnlRight.add(new JScrollPane(tblGioHang), BorderLayout.CENTER);
        pnlRight.add(btnXacNhan, BorderLayout.SOUTH);

        pnlMain.add(pnlLeft);
        pnlMain.add(Box.createRigidArea(new Dimension(30, 0)));
        pnlMain.add(pnlRight);

        add(pnlHeader, BorderLayout.NORTH);
        add(pnlMain, BorderLayout.CENTER);

        btnThem.addActionListener(e -> {
            try {
                int sl = Integer.parseInt(txtSoLuong.getText());
                long gia = Long.parseLong(txtGiaHoan.getText());
                String check = ctptkhBUS.validateDetail(sl, gia);
                
                if(check.equals("OK")) {
                    long tt = ctptkhBUS.tinhThanhTien(sl, gia);
                    modelGioHang.addRow(new Object[]{txtMaSach.getText().toUpperCase(), sl, String.format("%,d", gia), String.format("%,d", tt)});
                    listCT.add(new ChiTietPhieuTraKhachHangDTO(0, Integer.parseInt(txtMaSach.getText()), sl, gia));
                } else {
                    JOptionPane.showMessageDialog(this, check);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập số nguyên hợp lệ");
            }
        });

        btnXacNhan.addActionListener(e -> {
            if (listCT.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Danh sách trống");
                return;
            }
            PhieuTraKhachHangDTO pt = new PhieuTraKhachHangDTO();
            pt.setMaHD(Integer.parseInt(txtMaHD.getText()));
            pt.setMaNV(1);
            
            String res = ptkhBUS.tiepNhanTraHang(pt, listCT);
            JOptionPane.showMessageDialog(this, res);
            
            modelGioHang.setRowCount(0);
            listCT.clear();
        });
    }

    private JPanel createInputRow(String label, JTextField tf) {
        JPanel p = new JPanel(new BorderLayout(15, 0));
        p.setBackground(COLOR_BG);
        JLabel lbl = new JLabel(label); lbl.setPreferredSize(new Dimension(130, 0));
        lbl.setForeground(COLOR_PURPLE_DARK);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        tf.setBorder(BorderFactory.createCompoundBorder(new LineBorder(COLOR_PURPLE_DARK), BorderFactory.createEmptyBorder(5, 7, 5, 7)));
        p.add(lbl, BorderLayout.WEST); p.add(tf, BorderLayout.CENTER);
        return p;
    }

    private JButton createStyledButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setBackground(bg);
        btn.setForeground(COLOR_PURPLE_DARK);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btn.setPreferredSize(new Dimension(0, 55));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(new LineBorder(COLOR_PURPLE_DARK, 1));
        return btn;
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        JFrame frame = new JFrame("Quản Lý Cửa Hàng Sách");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 750);
        frame.add(new PhieuTraKhachHangGUI());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
