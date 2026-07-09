package GUI;

import BUS.PhanQuyenBUS;
import DTO.PhanQuyenDTO;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class PhanQuyenGUI extends JPanel {

    private final Color COLOR_CREAM = Color.decode("#F8F4EC");
    private final Color COLOR_MAGENTA = Color.decode("#E83C91");
    private final Color COLOR_DARK = Color.decode("#43334C");

    private JTable tblQuyen;
    private DefaultTableModel tableModel;
    
    private PhanQuyenBUS pqBus = new PhanQuyenBUS();
    private ArrayList<PhanQuyenDTO> dsQuyen = new ArrayList<>();
    
    // Class phụ trợ để map chính xác Số (Lưu DB) với Chữ (Hiển thị)
    class QuyenItem {
        int value;
        String label;
        public QuyenItem(int value, String label) { this.value = value; this.label = label; }
        @Override public String toString() { return label; }
    }

    private JComboBox<QuyenItem> cbxThongKe, cbxBanHang, cbxNhapHang, cbxSach, cbxThuocTinh, 
                      cbxHoaDon, cbxPhieuNhap, cbxPhieuDoiTra, cbxNhanVien, 
                      cbxKhachHang, cbxKhuyenMai, cbxNCC, cbxTaiKhoan, cbxPhanQuyen;
    
    private JLabel lblTenQuyenDangChon;

    public PhanQuyenGUI() {
        setLayout(new BorderLayout(15, 15));
        setBackground(COLOR_CREAM);
        setBorder(new EmptyBorder(15, 15, 15, 15));

        initHeader();
        initLeftPanel();   
        initRightPanel();  
        
        loadDataToTable();
    }

    private void initHeader() {
        JLabel lblTitle = new JLabel("MA TRẬN PHÂN QUYỀN HỆ THỐNG", JLabel.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(COLOR_CREAM);
        lblTitle.setOpaque(true);
        lblTitle.setBackground(COLOR_MAGENTA);
        lblTitle.setBorder(new EmptyBorder(15, 0, 15, 0));
        add(lblTitle, BorderLayout.NORTH);
    }

    private void initLeftPanel() {
        JPanel pnlLeft = new JPanel(new BorderLayout(10, 10));
        pnlLeft.setBackground(COLOR_CREAM);
        pnlLeft.setPreferredSize(new Dimension(300, 0));
        pnlLeft.setBorder(createTitledBorder(" NHÓM NGƯỜI DÙNG "));

        tableModel = new DefaultTableModel(new String[]{"ID", "Chức Vụ"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tblQuyen = new JTable(tableModel);
        tblQuyen.setRowHeight(40);
        tblQuyen.setFont(new Font("Segoe UI", Font.BOLD, 15));
        tblQuyen.setSelectionBackground(new Color(255, 143, 183)); 
        tblQuyen.setSelectionForeground(COLOR_DARK);
        
        JTableHeader header = tblQuyen.getTableHeader();
        header.setForeground(COLOR_DARK);
        header.setFont(new Font("Segoe UI", Font.BOLD, 15));

        tblQuyen.getColumnModel().getColumn(0).setMaxWidth(50);
        tblQuyen.getColumnModel().getColumn(0).setMinWidth(50);
        
        tblQuyen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) { hienThiChiTietQuyen(); }
        });

        pnlLeft.add(new JScrollPane(tblQuyen), BorderLayout.CENTER);
        add(pnlLeft, BorderLayout.WEST);
    }

    private void initRightPanel() {
        JPanel pnlRight = new JPanel(new BorderLayout(15, 15));
        pnlRight.setBackground(COLOR_CREAM);
        pnlRight.setBorder(createTitledBorder(" CHI TIẾT CẤP ĐỘ QUYỀN HẠN "));

        JPanel pnlComboboxes = new JPanel(new GridLayout(7, 2, 20, 15));
        pnlComboboxes.setBackground(COLOR_CREAM);
        pnlComboboxes.setBorder(new EmptyBorder(20, 40, 20, 20));

        // ===============================================================
        // KHỞI TẠO COMBOBOX GỌN GÀNG, KHÔNG DÙNG DẤU NGOẶC
        // ===============================================================
        cbxThongKe = new JComboBox<>();
        cbxThongKe.addItem(new QuyenItem(0, "Không có quyền"));
        cbxThongKe.addItem(new QuyenItem(5, "Toàn quyền"));

        cbxBanHang = new JComboBox<>();
        cbxBanHang.addItem(new QuyenItem(0, "Không có quyền"));
        cbxBanHang.addItem(new QuyenItem(5, "Toàn quyền"));

        cbxNhapHang = new JComboBox<>();
        cbxNhapHang.addItem(new QuyenItem(0, "Không có quyền"));
        cbxNhapHang.addItem(new QuyenItem(5, "Toàn quyền"));

        cbxSach = new JComboBox<>();
        cbxSach.addItem(new QuyenItem(0, "Không có quyền"));
        cbxSach.addItem(new QuyenItem(1, "Chỉ xem"));
        cbxSach.addItem(new QuyenItem(5, "Toàn quyền"));

        cbxThuocTinh = new JComboBox<>();
        cbxThuocTinh.addItem(new QuyenItem(0, "Không có quyền"));
        cbxThuocTinh.addItem(new QuyenItem(1, "Chỉ xem"));
        cbxThuocTinh.addItem(new QuyenItem(5, "Toàn quyền"));

        cbxHoaDon = new JComboBox<>();
        cbxHoaDon.addItem(new QuyenItem(0, "Không có quyền"));
        cbxHoaDon.addItem(new QuyenItem(2, "Xem và In"));
        cbxHoaDon.addItem(new QuyenItem(5, "Toàn quyền"));

        cbxPhieuNhap = new JComboBox<>();
        cbxPhieuNhap.addItem(new QuyenItem(0, "Không có quyền"));
        cbxPhieuNhap.addItem(new QuyenItem(2, "Xem và In"));
        cbxPhieuNhap.addItem(new QuyenItem(5, "Toàn quyền"));

        cbxPhieuDoiTra = new JComboBox<>();
        cbxPhieuDoiTra.addItem(new QuyenItem(0, "Không có quyền"));
        cbxPhieuDoiTra.addItem(new QuyenItem(1, "Chỉ xem"));
        cbxPhieuDoiTra.addItem(new QuyenItem(3, "Xem và Thêm"));
        cbxPhieuDoiTra.addItem(new QuyenItem(5, "Toàn quyền"));

        cbxNhanVien = new JComboBox<>();
        cbxNhanVien.addItem(new QuyenItem(0, "Không có quyền"));
        cbxNhanVien.addItem(new QuyenItem(1, "Chỉ xem"));
        cbxNhanVien.addItem(new QuyenItem(5, "Toàn quyền"));

        cbxKhachHang = new JComboBox<>();
        cbxKhachHang.addItem(new QuyenItem(0, "Không có quyền"));
        cbxKhachHang.addItem(new QuyenItem(4, "Xem, Thêm và Sửa"));
        cbxKhachHang.addItem(new QuyenItem(5, "Toàn quyền"));

        cbxKhuyenMai = new JComboBox<>();
        cbxKhuyenMai.addItem(new QuyenItem(0, "Không có quyền"));
        cbxKhuyenMai.addItem(new QuyenItem(1, "Chỉ xem"));
        cbxKhuyenMai.addItem(new QuyenItem(5, "Toàn quyền"));

        cbxNCC = new JComboBox<>();
        cbxNCC.addItem(new QuyenItem(0, "Không có quyền"));
        cbxNCC.addItem(new QuyenItem(3, "Xem và Thêm"));
        cbxNCC.addItem(new QuyenItem(5, "Toàn quyền"));

        cbxTaiKhoan = new JComboBox<>();
        cbxTaiKhoan.addItem(new QuyenItem(0, "Không có quyền"));
        cbxTaiKhoan.addItem(new QuyenItem(5, "Toàn quyền"));

        cbxPhanQuyen = new JComboBox<>();
        cbxPhanQuyen.addItem(new QuyenItem(0, "Không có quyền"));
        cbxPhanQuyen.addItem(new QuyenItem(5, "Toàn quyền"));

        // Đổ vào giao diện - Đã bỏ các ngoặc đơn ở tiêu đề
        pnlComboboxes.add(createItemBlock("1. Báo cáo & Thống Kê:", cbxThongKe));   
        pnlComboboxes.add(createItemBlock("2. Bán Hàng & Duyệt Đơn:", cbxBanHang));
        pnlComboboxes.add(createItemBlock("3. Nhập Hàng:", cbxNhapHang));  
        pnlComboboxes.add(createItemBlock("4. Danh Mục Kho Sách:", cbxSach));
        pnlComboboxes.add(createItemBlock("5. Thuộc tính Sách:", cbxThuocTinh)); 
        pnlComboboxes.add(createItemBlock("6. Lịch sử Hóa Đơn:", cbxHoaDon));
        pnlComboboxes.add(createItemBlock("7. Lịch sử Phiếu Nhập:", cbxPhieuNhap)); 
        pnlComboboxes.add(createItemBlock("8. Quản lý Phiếu Đổi Trả:", cbxPhieuDoiTra));
        pnlComboboxes.add(createItemBlock("9. Quản lý Nhân Viên:", cbxNhanVien));  
        pnlComboboxes.add(createItemBlock("10. Quản lý Khách Hàng:", cbxKhachHang));
        pnlComboboxes.add(createItemBlock("11. Khuyến Mãi:", cbxKhuyenMai)); 
        pnlComboboxes.add(createItemBlock("12. Đối tác Nhà Cung Cấp:", cbxNCC));
        pnlComboboxes.add(createItemBlock("13. Tài Khoản Đăng Nhập:", cbxTaiKhoan));  
        pnlComboboxes.add(createItemBlock("14. Thiết lập Phân Quyền:", cbxPhanQuyen));

        lblTenQuyenDangChon = new JLabel("Vui lòng chọn một chức vụ bên trái...", JLabel.CENTER);
        lblTenQuyenDangChon.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTenQuyenDangChon.setForeground(COLOR_MAGENTA);
        lblTenQuyenDangChon.setBorder(new EmptyBorder(15, 0, 5, 0));

        JButton btnSave = new JButton(" XÁC NHẬN LƯU PHÂN QUYỀN ");
        btnSave.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnSave.setBackground(COLOR_DARK);
        btnSave.setForeground(Color.WHITE);
        btnSave.setFocusPainted(false);
        btnSave.setBorderPainted(false);        
        btnSave.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSave.setPreferredSize(new Dimension(0, 50));
        
        btnSave.addActionListener(e -> savePermissions());

        pnlRight.add(lblTenQuyenDangChon, BorderLayout.NORTH);
        pnlRight.add(new JScrollPane(pnlComboboxes), BorderLayout.CENTER);
        pnlRight.add(btnSave, BorderLayout.SOUTH);

        add(pnlRight, BorderLayout.CENTER);
    }

    private JPanel createItemBlock(String title, JComboBox<QuyenItem> cbx) {
        JPanel pnl = new JPanel(new BorderLayout(5, 5));
        pnl.setBackground(COLOR_CREAM);
        JLabel lbl = new JLabel(title);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lbl.setForeground(COLOR_DARK);
        cbx.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        cbx.setBackground(Color.WHITE);
        pnl.add(lbl, BorderLayout.NORTH);
        pnl.add(cbx, BorderLayout.CENTER);
        return pnl;
    }

    private void loadDataToTable() {
        tableModel.setRowCount(0); 
        dsQuyen = pqBus.getAll(); 
        if (dsQuyen != null) {
            for (PhanQuyenDTO pq : dsQuyen) {
                tableModel.addRow(new Object[]{ pq.getMaID(), pq.getTenQuyen() });
            }
        }
    }

    // Hàm phụ trợ tự động chọn dòng ComboBox dựa trên Mã số Quyền
    private void setSelectedValue(JComboBox<QuyenItem> cbx, int valueToSelect) {
        for (int i = 0; i < cbx.getItemCount(); i++) {
            if (cbx.getItemAt(i).value == valueToSelect) {
                cbx.setSelectedIndex(i);
                return;
            }
        }
        cbx.setSelectedIndex(0); // Nếu ko có thì chọn dòng đầu tiên (Không có quyền)
    }

    private void hienThiChiTietQuyen() {
        int row = tblQuyen.getSelectedRow();
        if (row < 0) return;
        int maID = Integer.parseInt(tblQuyen.getValueAt(row, 0).toString()); 
        lblTenQuyenDangChon.setText("ĐANG THIẾT LẬP QUYỀN CHO: " + tblQuyen.getValueAt(row, 1).toString().toUpperCase());

        for (PhanQuyenDTO pq : dsQuyen) {
            if (pq.getMaID() == maID) {
                setSelectedValue(cbxThongKe, pq.getQlThongKe());
                setSelectedValue(cbxBanHang, pq.getQlBanHang());
                setSelectedValue(cbxNhapHang, pq.getQlNhapHang());
                setSelectedValue(cbxSach, pq.getQlSach());
                setSelectedValue(cbxThuocTinh, pq.getQlThuocTinh());
                setSelectedValue(cbxHoaDon, pq.getQlHoaDon());
                setSelectedValue(cbxPhieuNhap, pq.getQlPhieuNhap());
                setSelectedValue(cbxPhieuDoiTra, pq.getQlPhieuDoiTra());
                setSelectedValue(cbxNhanVien, pq.getQlNhanVien());
                setSelectedValue(cbxKhachHang, pq.getQlKhachHang());
                setSelectedValue(cbxKhuyenMai, pq.getQlKhuyenMai());
                setSelectedValue(cbxNCC, pq.getQlNCC());
                setSelectedValue(cbxTaiKhoan, pq.getQlTaiKhoan());
                setSelectedValue(cbxPhanQuyen, pq.getQlPhanQuyen());
                break;
            }
        }
    }

    private void savePermissions() {
        int row = tblQuyen.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn chức vụ bên trái trước khi lưu!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int maID = Integer.parseInt(tblQuyen.getValueAt(row, 0).toString());

        PhanQuyenDTO pq = new PhanQuyenDTO();
        pq.setMaID(maID);
        
        // Lấy giá trị nguyên (0, 1, 2, 3, 4, 5) đính kèm trong Object để gán xuống Database
        pq.setQlThongKe(((QuyenItem) cbxThongKe.getSelectedItem()).value);
        pq.setQlBanHang(((QuyenItem) cbxBanHang.getSelectedItem()).value);
        pq.setQlNhapHang(((QuyenItem) cbxNhapHang.getSelectedItem()).value);
        pq.setQlSach(((QuyenItem) cbxSach.getSelectedItem()).value);
        pq.setQlThuocTinh(((QuyenItem) cbxThuocTinh.getSelectedItem()).value);
        pq.setQlHoaDon(((QuyenItem) cbxHoaDon.getSelectedItem()).value);
        pq.setQlPhieuNhap(((QuyenItem) cbxPhieuNhap.getSelectedItem()).value);
        pq.setQlPhieuDoiTra(((QuyenItem) cbxPhieuDoiTra.getSelectedItem()).value);
        pq.setQlNhanVien(((QuyenItem) cbxNhanVien.getSelectedItem()).value);
        pq.setQlKhachHang(((QuyenItem) cbxKhachHang.getSelectedItem()).value);
        pq.setQlKhuyenMai(((QuyenItem) cbxKhuyenMai.getSelectedItem()).value);
        pq.setQlNCC(((QuyenItem) cbxNCC.getSelectedItem()).value);
        pq.setQlTaiKhoan(((QuyenItem) cbxTaiKhoan.getSelectedItem()).value);
        pq.setQlPhanQuyen(((QuyenItem) cbxPhanQuyen.getSelectedItem()).value);

        boolean success = pqBus.luuQuyen(pq); 
        if(success) {
            JOptionPane.showMessageDialog(this, "Lưu mức độ phân quyền thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            loadDataToTable(); 
        } else {
            JOptionPane.showMessageDialog(this, "Lưu thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private TitledBorder createTitledBorder(String title) {
        TitledBorder b = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(COLOR_DARK), title);
        b.setTitleFont(new Font("Segoe UI", Font.BOLD, 14));
        b.setTitleColor(COLOR_DARK);
        return b;
    }
}