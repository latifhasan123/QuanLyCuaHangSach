package GUI;

import BUS.NhaCungCapBUS;
import DTO.NhaCungCapDTO;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

public class NhaCungCapGUI extends JPanel {

    private NhaCungCapBUS nccBUS = new NhaCungCapBUS();
    private JTable table;
    private DefaultTableModel model;
    private JTextField txtTen, txtDiaChi, txtSdt, txtEmail, txtTimKiem;
    private JButton btnThem, btnSua, btnXoa, btnLamMoi, btnLoc, btnLamMoiLoc;

    private final Color COLOR_BG = new Color(248, 244, 236);     
    private final Color COLOR_PINK_LIGHT = new Color(255, 143, 183); 
    private final Color COLOR_PINK_BOLD = new Color(232, 60, 145); 
    private final Color COLOR_PURPLE_DARK = new Color(67, 51, 76);  

    public NhaCungCapGUI() {
        initCustomComponents();
        loadData();
    }

    private void initCustomComponents() {
        setLayout(new BorderLayout(0, 0));
        setBackground(COLOR_BG);

        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setBackground(COLOR_PINK_BOLD);
        pnlHeader.setPreferredSize(new Dimension(0, 50));
        JLabel lblTitle = new JLabel("QUẢN LÝ NHÀ CUNG CẤP");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        pnlHeader.add(lblTitle, BorderLayout.CENTER);

        JPanel pnlBody = new JPanel(new BorderLayout(0, 15));
        pnlBody.setBackground(COLOR_BG);
        pnlBody.setBorder(new EmptyBorder(20, 30, 20, 30));

        JPanel pnlInput = new JPanel(new GridLayout(2, 2, 40, 15));
        pnlInput.setBackground(COLOR_BG);
        pnlInput.setBorder(BorderFactory.createTitledBorder(
                new LineBorder(COLOR_PURPLE_DARK, 1), " THÔNG TIN CHI TIẾT ", 
                TitledBorder.LEFT, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 14), COLOR_PURPLE_DARK));
        pnlInput.setBorder(BorderFactory.createCompoundBorder(pnlInput.getBorder(), new EmptyBorder(15, 20, 15, 20)));

        pnlInput.add(createInputGroup("Tên Nhà Cung Cấp", txtTen = new JTextField()));
        pnlInput.add(createInputGroup("Số Điện Thoại", txtSdt = new JTextField()));
        pnlInput.add(createInputGroup("Địa Chỉ", txtDiaChi = new JTextField()));
        pnlInput.add(createInputGroup("Email", txtEmail = new JTextField()));

        JPanel pnlSearch = new JPanel(new GridBagLayout());
        pnlSearch.setBackground(COLOR_BG);
        pnlSearch.setBorder(BorderFactory.createTitledBorder(
                new LineBorder(COLOR_PURPLE_DARK, 1), " TÌM KIẾM NÂNG CAO ", 
                TitledBorder.LEFT, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 14), COLOR_PURPLE_DARK));
        pnlSearch.setBorder(BorderFactory.createCompoundBorder(pnlSearch.getBorder(), new EmptyBorder(10, 20, 10, 20)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.weightx = 0;
        JLabel lblTimKiem = new JLabel("Từ khóa chung:");
        lblTimKiem.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblTimKiem.setForeground(COLOR_PURPLE_DARK);
        pnlSearch.add(lblTimKiem, gbc);

        gbc.gridx = 1; gbc.weightx = 1.0;
        txtTimKiem = new JTextField();
        txtTimKiem.setPreferredSize(new Dimension(0, 35));
        txtTimKiem.setBorder(new LineBorder(COLOR_PURPLE_DARK, 1));
        pnlSearch.add(txtTimKiem, gbc);

        gbc.weightx = 0;
        gbc.gridx = 2;
        btnLoc = createStyledButton("Lọc Dữ Liệu", COLOR_BG, COLOR_PURPLE_DARK);
        pnlSearch.add(btnLoc, gbc);

        gbc.gridx = 3;
        btnLamMoiLoc = createStyledButton("Làm Mới Bộ Lọc", COLOR_BG, COLOR_PURPLE_DARK);
        pnlSearch.add(btnLamMoiLoc, gbc);

        // ĐÃ THÊM: Cột ID ẩn để chuẩn hóa thao tác Update/Delete
        String[] header = {"ID", "Mã NCC", "Tên Nhà Cung Cấp", "Địa Chỉ", "SĐT", "Email"};
        model = new DefaultTableModel(header, 0);
        table = new JTable(model);
        table.setRowHeight(35);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setSelectionBackground(COLOR_PINK_LIGHT);
        table.setSelectionForeground(COLOR_PURPLE_DARK);
        table.setGridColor(new Color(230, 230, 230));

        // Ẩn cột ID đi cho đẹp
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setWidth(0);

        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.getTableHeader().setBackground(COLOR_PINK_LIGHT);
        table.getTableHeader().setForeground(COLOR_PURPLE_DARK);
        table.getTableHeader().setPreferredSize(new Dimension(0, 40));
        table.getTableHeader().setBorder(new LineBorder(COLOR_PURPLE_DARK));

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(new LineBorder(COLOR_PURPLE_DARK, 1));

        JPanel pnlBtns = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        pnlBtns.setBackground(COLOR_BG);

        btnThem = createStyledButton("Thêm Mới", COLOR_BG, COLOR_PURPLE_DARK);
        btnSua = createStyledButton("Cập Nhật", COLOR_BG, COLOR_PURPLE_DARK);
        btnXoa = createStyledButton("Xóa", COLOR_BG, COLOR_PURPLE_DARK);
        btnLamMoi = createStyledButton("Làm Mới", COLOR_BG, COLOR_PURPLE_DARK);

        pnlBtns.add(btnThem); pnlBtns.add(btnSua); pnlBtns.add(btnXoa); pnlBtns.add(btnLamMoi);

        JPanel pnlCenter = new JPanel(new BorderLayout(0, 10));
        pnlCenter.setBackground(COLOR_BG);
        pnlCenter.add(pnlInput, BorderLayout.NORTH);
        pnlCenter.add(pnlSearch, BorderLayout.CENTER);

        pnlBody.add(pnlCenter, BorderLayout.NORTH);
        pnlBody.add(scroll, BorderLayout.CENTER);
        pnlBody.add(pnlBtns, BorderLayout.SOUTH);

        add(pnlHeader, BorderLayout.NORTH);
        add(pnlBody, BorderLayout.CENTER);

        // ================= GẮN SỰ KIỆN =================
        btnLoc.addActionListener(e -> searchData());
        btnLamMoiLoc.addActionListener(e -> { txtTimKiem.setText(""); loadData(); });
        btnThem.addActionListener(e -> addNCC());
        btnSua.addActionListener(e -> updateNCC());
        btnXoa.addActionListener(e -> deleteNCC());
        btnLamMoi.addActionListener(e -> refreshFields());
        
        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                // Đã lùi index vì có cột ID ẩn
                txtTen.setText(model.getValueAt(row, 2).toString());
                txtDiaChi.setText(model.getValueAt(row, 3).toString());
                txtSdt.setText(model.getValueAt(row, 4).toString());
                txtEmail.setText(model.getValueAt(row, 5).toString());
            }
        });
    }

    private JPanel createInputGroup(String labelText, JTextField tf) {
        JPanel pnl = new JPanel(new BorderLayout(0, 5));
        pnl.setBackground(COLOR_BG);
        JLabel lbl = new JLabel(labelText);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lbl.setForeground(COLOR_PURPLE_DARK);
        tf.setPreferredSize(new Dimension(0, 32));
        tf.setBorder(new LineBorder(COLOR_PURPLE_DARK, 1));
        pnl.add(lbl, BorderLayout.NORTH);
        pnl.add(tf, BorderLayout.CENTER);
        return pnl;
    }

    private JButton createStyledButton(String text, Color bg, Color fg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setForeground(fg);
        btn.setBackground(bg);
        btn.setPreferredSize(new Dimension(140, 40));
        btn.setFocusPainted(false);
        btn.setBorder(new LineBorder(COLOR_PURPLE_DARK, 1));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void searchData() {
        String key = txtTimKiem.getText().toLowerCase();
        model.setRowCount(0);
        // ĐÃ SỬA: Dùng hàm timKiem của BUS
        ArrayList<NhaCungCapDTO> list = nccBUS.timKiem(key);
        for (NhaCungCapDTO ncc : list) {
            model.addRow(new Object[]{ncc.getMaID(), ncc.getMaNCC(), ncc.getTenNCC(), ncc.getDiaChi(), ncc.getSoDienThoai(), ncc.getEmail()});
        }
    }

    private void loadData() {
        model.setRowCount(0);
        // ĐÃ SỬA: Dùng hàm getListNCC
        ArrayList<NhaCungCapDTO> list = nccBUS.getListNCC();
        if (list != null) {
            for (NhaCungCapDTO ncc : list) {
                model.addRow(new Object[]{ncc.getMaID(), ncc.getMaNCC(), ncc.getTenNCC(), ncc.getDiaChi(), ncc.getSoDienThoai(), ncc.getEmail()});
            }
        }
    }

    private void refreshFields() {
        txtTen.setText(""); txtDiaChi.setText(""); txtSdt.setText(""); txtEmail.setText("");
        table.clearSelection(); loadData();
    }

    private void addNCC() {
        NhaCungCapDTO ncc = new NhaCungCapDTO();
        ncc.setTenNCC(txtTen.getText());
        ncc.setDiaChi(txtDiaChi.getText());
        ncc.setSoDienThoai(txtSdt.getText());
        ncc.setEmail(txtEmail.getText());
        
        // ĐÃ SỬA: Dùng themNCC() trả về boolean
        if (nccBUS.themNCC(ncc)) {
            JOptionPane.showMessageDialog(this, "Thêm Nhà cung cấp thành công!");
            refreshFields();
        } else {
            JOptionPane.showMessageDialog(this, "Lỗi! Không thể thêm mới.");
        }
    }

    private void updateNCC() {
        int row = table.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Chọn dòng trên bảng để sửa!"); return; }
        
        int id = Integer.parseInt(model.getValueAt(row, 0).toString()); // Lấy ID ẩn
        
        NhaCungCapDTO ncc = new NhaCungCapDTO();
        ncc.setMaID(id);
        ncc.setTenNCC(txtTen.getText());
        ncc.setDiaChi(txtDiaChi.getText());
        ncc.setSoDienThoai(txtSdt.getText());
        ncc.setEmail(txtEmail.getText());
        
        // ĐÃ SỬA: Dùng suaNCC() trả về boolean
        if (nccBUS.suaNCC(ncc)) {
            JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
            loadData();
        } else {
            JOptionPane.showMessageDialog(this, "Lỗi! Không thể cập nhật.");
        }
    }

    private void deleteNCC() {
        int row = table.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Chọn dòng cần xóa!"); return; }
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            int id = Integer.parseInt(model.getValueAt(row, 0).toString()); // Lấy ID ẩn
            
            // ĐÃ SỬA: Dùng xoaNCC() trả về boolean
            if (nccBUS.xoaNCC(id)) {
                JOptionPane.showMessageDialog(this, "Đã xóa thành công!");
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi! Có thể Nhà cung cấp này đã có Phiếu nhập nên không thể xóa.");
            }
        }
    }
}