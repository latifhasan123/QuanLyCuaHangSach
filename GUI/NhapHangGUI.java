package GUI;

import BUS.PhieuNhapBUS;
import BUS.NhaCungCapBUS;
import DAO.SachDAO;
import DTO.PhieuNhapDTO;
import DTO.ChiTietPhieuNhapDTO;
import DTO.NhaCungCapDTO;
import DTO.SachDTO;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class NhapHangGUI extends JPanel {
    private PhieuNhapBUS pnBUS = new PhieuNhapBUS();
    private SachDAO sachDAO = new SachDAO();
    private NhaCungCapBUS nccBUS = new NhaCungCapBUS();
    
    private JTable tblGioHang;
    private DefaultTableModel modelGioHang;
    private JTextField txtMaSach, txtTenSach, txtSoLuong, txtDonGia, txtTongTien;
    private JComboBox<String> cbNCC, cbNV;
    private JButton btnThemGio, btnXoaDong, btnXacNhan;

    private final Color COLOR_BG = new Color(248, 244, 236);      
    private final Color COLOR_PINK_BOLD = new Color(232, 60, 145);  
    private final Color COLOR_PURPLE_DARK = new Color(67, 51, 76);  
    
    public NhapHangGUI() {
        initCustomComponents();
        loadComboBoxData();
    }
    
    private void initCustomComponents() {
        setLayout(new BorderLayout(10, 10));
        setBackground(COLOR_BG);
        
        JLabel lblTitle = new JLabel("NHẬP HÀNG VÀO KHO", JLabel.CENTER);
        lblTitle.setOpaque(true); lblTitle.setBackground(COLOR_PINK_BOLD);
        lblTitle.setForeground(Color.WHITE); lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setBorder(new EmptyBorder(15, 0, 15, 0));
        add(lblTitle, BorderLayout.NORTH);
        
        String[] cols = {"Mã Sách", "Tên Sách", "Số Lượng", "Giá Nhập", "Thành Tiền"};
        modelGioHang = new DefaultTableModel(cols, 0) { @Override public boolean isCellEditable(int r, int c) { return false; } };
        tblGioHang = new JTable(modelGioHang);
        tblGioHang.setRowHeight(35);
        tblGioHang.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JTableHeader header = tblGioHang.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(COLOR_PURPLE_DARK); header.setForeground(Color.WHITE);
        add(new JScrollPane(tblGioHang), BorderLayout.CENTER);
        
        JPanel pnlInput = new JPanel(new GridLayout(8, 1, 10, 10));
        pnlInput.setBackground(COLOR_BG);
        pnlInput.setBorder(BorderFactory.createTitledBorder(new LineBorder(COLOR_PURPLE_DARK, 1), "THÔNG TIN NHẬP", TitledBorder.LEFT, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 14), COLOR_PURPLE_DARK));
        pnlInput.setPreferredSize(new Dimension(300, 0));
        
        cbNV = new JComboBox<>(new String[]{"13 - Admin"});
        cbNCC = new JComboBox<>();
        txtMaSach = new JTextField();
        txtTenSach = new JTextField(); txtTenSach.setEditable(false);
        txtSoLuong = new JTextField();
        txtDonGia = new JTextField(); txtDonGia.setEditable(false);
        
        pnlInput.add(createInputRow("Nhân Viên:", cbNV));
        pnlInput.add(createInputRow("Nhà Cung Cấp:", cbNCC));
        pnlInput.add(createInputRow("Mã Sách:", txtMaSach));
        pnlInput.add(createInputRow("Tên Sách:", txtTenSach));
        pnlInput.add(createInputRow("Số Lượng:", txtSoLuong));
        pnlInput.add(createInputRow("Giá Nhập:", txtDonGia));
        
        btnThemGio = createStyledButton("Thêm Vào Phiếu", COLOR_PINK_BOLD);
        btnXoaDong = createStyledButton("Xóa Khỏi Phiếu", Color.RED);
        pnlInput.add(btnThemGio);
        pnlInput.add(btnXoaDong);
        
        add(pnlInput, BorderLayout.WEST);
        
        JPanel pnlBottom = new JPanel(new BorderLayout());
        pnlBottom.setBackground(COLOR_BG);
        pnlBottom.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        txtTongTien = new JTextField("0 VNĐ");
        txtTongTien.setEditable(false);
        txtTongTien.setFont(new Font("Segoe UI", Font.BOLD, 18));
        txtTongTien.setForeground(Color.RED);
        txtTongTien.setHorizontalAlignment(JTextField.RIGHT);
        txtTongTien.setBorder(null); txtTongTien.setBackground(COLOR_BG);
        
        btnXacNhan = createStyledButton("XÁC NHẬN TẠO PHIẾU NHẬP KHO", COLOR_PURPLE_DARK);
        btnXacNhan.setPreferredSize(new Dimension(300, 45));
        
        pnlBottom.add(txtTongTien, BorderLayout.CENTER);
        pnlBottom.add(btnXacNhan, BorderLayout.EAST);
        add(pnlBottom, BorderLayout.SOUTH);
        
        addEvents();
    }
    
    private JPanel createInputRow(String label, JComponent comp) {
        JPanel p = new JPanel(new BorderLayout(5, 0));
        p.setBackground(COLOR_BG);
        JLabel lbl = new JLabel(label);
        lbl.setPreferredSize(new Dimension(100, 0));
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lbl.setForeground(COLOR_PURPLE_DARK);
        p.add(lbl, BorderLayout.WEST); p.add(comp, BorderLayout.CENTER);
        return p;
    }

    private JButton createStyledButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setBackground(bg); btn.setForeground(Color.WHITE); 
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false); btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
    
    private void loadComboBoxData() {
        for (NhaCungCapDTO ncc : nccBUS.getListNCC()) {
            if(ncc.getTrangThai().equals("HoatDong")) { cbNCC.addItem(ncc.getMaID() + " - " + ncc.getTenNCC()); }
        }
    }
    
    private void addEvents() {
        txtMaSach.addKeyListener(new KeyAdapter() {
            @Override public void keyReleased(KeyEvent e) {
                try {
                    int id = Integer.parseInt(txtMaSach.getText().trim());
                    SachDTO s = sachDAO.findByID(id);
                    if(s != null) {
                        txtTenSach.setText(s.getTenSach());
                        txtDonGia.setText(String.format("%.0f", s.getGiaNhap()));
                    } else { txtTenSach.setText("Không tìm thấy"); txtDonGia.setText(""); }
                } catch(Exception ex) { txtTenSach.setText(""); txtDonGia.setText(""); }
            }
        });
        
        btnThemGio.addActionListener(e -> {
            try {
                String maSach = txtMaSach.getText().trim();
                String tenSach = txtTenSach.getText();
                int sl = Integer.parseInt(txtSoLuong.getText().trim());
                double gia = Double.parseDouble(txtDonGia.getText().trim().replace(",", ""));
                if(sl <= 0) throw new Exception();
                
                boolean found = false;
                for (int i = 0; i < modelGioHang.getRowCount(); i++) {
                    if (modelGioHang.getValueAt(i, 0).toString().equals(maSach)) {
                        int oldSl = Integer.parseInt(modelGioHang.getValueAt(i, 2).toString());
                        modelGioHang.setValueAt(oldSl + sl, i, 2);
                        modelGioHang.setValueAt(String.format("%,.0f", (oldSl + sl) * gia), i, 4);
                        found = true; break;
                    }
                }
                if (!found) { modelGioHang.addRow(new Object[]{maSach, tenSach, sl, String.format("%,.0f", gia), String.format("%,.0f", sl * gia)}); }
                tinhTongTien();
            } catch(Exception ex) { JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng Mã sách và Số lượng > 0!"); }
        });
        
        btnXoaDong.addActionListener(e -> {
            int row = tblGioHang.getSelectedRow();
            if(row >= 0) { modelGioHang.removeRow(row); tinhTongTien(); }
        });
        
        btnXacNhan.addActionListener(e -> {
            if (modelGioHang.getRowCount() == 0) { JOptionPane.showMessageDialog(this, "Giỏ hàng trống!"); return; }
            try {
                int maNV = Integer.parseInt(cbNV.getSelectedItem().toString().split(" - ")[0]);
                int maNCC = Integer.parseInt(cbNCC.getSelectedItem().toString().split(" - ")[0]);
                double tongTienPhieu = 0;
                for (int i = 0; i < modelGioHang.getRowCount(); i++) { tongTienPhieu += Double.parseDouble(modelGioHang.getValueAt(i, 4).toString().replace(",", "")); }
                
                PhieuNhapDTO pn = new PhieuNhapDTO();
                pn.setMaNV(maNV); pn.setMaNCC(maNCC); pn.setTongTien(tongTienPhieu); pn.setTrangThai("HoanThanh");
                
                ArrayList<ChiTietPhieuNhapDTO> dsCT = new ArrayList<>();
                for (int i = 0; i < modelGioHang.getRowCount(); i++) {
                    ChiTietPhieuNhapDTO ct = new ChiTietPhieuNhapDTO();
                    ct.setMaSach(modelGioHang.getValueAt(i, 0).toString());
                    ct.setSoLuong(Integer.parseInt(modelGioHang.getValueAt(i, 2).toString()));
                    ct.setGiaNhap(Double.parseDouble(modelGioHang.getValueAt(i, 3).toString().replace(",", ""))); // ĐÃ SỬA
                    dsCT.add(ct);
                }
                
                if (pnBUS.taoPhieuNhapHoanChinh(pn, dsCT)) {
                    JOptionPane.showMessageDialog(this, "Nhập hàng thành công!");
                    modelGioHang.setRowCount(0); tinhTongTien();
                    txtMaSach.setText(""); txtTenSach.setText(""); txtSoLuong.setText(""); txtDonGia.setText("");
                } else { JOptionPane.showMessageDialog(this, "Lỗi khi lưu phiếu nhập!"); }
            } catch(Exception ex) { ex.printStackTrace(); }
        });
    }
    
    private void tinhTongTien() {
        double tong = 0;
        for (int i = 0; i < modelGioHang.getRowCount(); i++) { tong += Double.parseDouble(modelGioHang.getValueAt(i, 4).toString().replace(",", "")); }
        txtTongTien.setText(String.format("%,.0f VNĐ", tong));
    }
}