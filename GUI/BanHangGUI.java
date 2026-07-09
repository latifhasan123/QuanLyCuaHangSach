package GUI; 

import BUS.BanHangBUS;
import BUS.KhachHangBUS;
import BUS.SachBUS;
import bus.KhuyenMaiBUS;
import dto.ChiTietHoaDonDTO;
import DTO.KhachHangDTO;
import DTO.SachDTO;
import DTO.NhanVienDTO;
import dto.KhuyenMaiDTO;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;
import java.time.LocalDate;

public class BanHangGUI extends JPanel {

    // --- BẢNG MÀU CHUẨN ---
    private final Color COLOR_CREAM = Color.decode("#F8F4EC");
    private final Color COLOR_LIGHT_PINK = Color.decode("#FF8FB7");
    private final Color COLOR_MAGENTA = Color.decode("#E83C91");
    private final Color COLOR_DARK = Color.decode("#43334C");
    private final Color COLOR_WHITE = Color.WHITE;

    private NhanVienDTO currentUser; 
    private BanHangBUS banHangBUS = new BanHangBUS();
    private KhachHangBUS khachHangBUS = new KhachHangBUS();
    private SachBUS sachBUS = new SachBUS();
    private KhuyenMaiBUS kmBUS = new KhuyenMaiBUS(); // ĐÃ THÊM: Kéo BUS Khuyến Mãi vào
    
    private List<ChiTietHoaDonDTO> dsGioHang = new ArrayList<>();
    private Integer currentCustomerId = null; 
    private KhuyenMaiDTO appliedKM = null; // ĐÃ THÊM: Lưu mã Khuyến mãi đang áp dụng

    private JTable tblCart, tblBooks;
    private DefaultTableModel cartModel, bookModel;
    private JLabel lblTongTien, lblTenKhach, lblTienGiam, lblTongTienHang;
    
    private JTextField txtMaSach, txtTenSach, txtGiaTu, txtGiaDen, txtTonKhoTu, txtTonKhoDen;
    private JTextField txtSoLuong, txtSDT, txtMaKM; // Thêm ô nhập Mã KM

    private BigDecimal tongTienHang = BigDecimal.ZERO;
    private BigDecimal tienGiamGia = BigDecimal.ZERO;

    private DecimalFormat df = new DecimalFormat("#,### VNĐ");

    public BanHangGUI(NhanVienDTO user) {
        this.currentUser = user;
        initUI();
        loadDanhSachSach();
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));
        setBackground(COLOR_CREAM);
        setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setBackground(COLOR_CREAM);
        JLabel lblTitle = new JLabel("BÁN HÀNG TẠI QUẦY (POS)", JLabel.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(COLOR_CREAM);
        lblTitle.setOpaque(true);
        lblTitle.setBackground(COLOR_MAGENTA);
        lblTitle.setBorder(new EmptyBorder(10, 0, 10, 0));
        pnlHeader.add(lblTitle, BorderLayout.CENTER);
        add(pnlHeader, BorderLayout.NORTH);

        JPanel mainContent = new JPanel(new BorderLayout(20, 0));
        mainContent.setBackground(COLOR_CREAM);
        mainContent.add(createLeftPanel(), BorderLayout.CENTER);
        mainContent.add(createRightPanel(), BorderLayout.EAST);
        
        add(mainContent, BorderLayout.CENTER);
    }

    // ==========================================
    // KHUNG BÊN TRÁI: TÌM KIẾM VÀ DANH SÁCH SÁCH (Giữ nguyên)
    // ==========================================
    private JPanel createLeftPanel() {
        JPanel leftPanel = new JPanel(new BorderLayout(0, 15));
        leftPanel.setBackground(COLOR_CREAM);

        JPanel pnlFilter = new JPanel(new GridLayout(3, 1, 5, 5));
        pnlFilter.setBackground(COLOR_WHITE);
        pnlFilter.setBorder(BorderFactory.createTitledBorder(
            new LineBorder(COLOR_DARK, 1), " TÌM KIẾM & LỌC SÁCH NÂNG CAO ", 
            TitledBorder.LEFT, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 14), COLOR_DARK));

        JPanel pnlRow1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        pnlRow1.setBackground(COLOR_WHITE);
        pnlRow1.add(createLabel("Mã sách:"));
        txtMaSach = new JTextField(12); txtMaSach.setPreferredSize(new Dimension(120, 30));
        pnlRow1.add(txtMaSach);
        pnlRow1.add(Box.createRigidArea(new Dimension(15, 0)));
        pnlRow1.add(createLabel("Tên sách:"));
        txtTenSach = new JTextField(18); txtTenSach.setPreferredSize(new Dimension(180, 30));
        pnlRow1.add(txtTenSach);

        JPanel pnlRow2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        pnlRow2.setBackground(COLOR_WHITE);
        pnlRow2.add(createLabel("Giá từ:"));
        txtGiaTu = new JTextField(8); txtGiaTu.setPreferredSize(new Dimension(80, 30));
        pnlRow2.add(txtGiaTu);
        pnlRow2.add(createLabel("đến:"));
        txtGiaDen = new JTextField(8); txtGiaDen.setPreferredSize(new Dimension(80, 30));
        pnlRow2.add(txtGiaDen);
        pnlRow2.add(Box.createRigidArea(new Dimension(5, 0)));
        pnlRow2.add(createLabel("Tồn kho từ:"));
        txtTonKhoTu = new JTextField(5); txtTonKhoTu.setPreferredSize(new Dimension(50, 30));
        pnlRow2.add(txtTonKhoTu);
        pnlRow2.add(createLabel("đến:"));
        txtTonKhoDen = new JTextField(5); txtTonKhoDen.setPreferredSize(new Dimension(50, 30));
        pnlRow2.add(txtTonKhoDen);

        JPanel pnlRow3 = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        pnlRow3.setBackground(COLOR_WHITE);
        JButton btnFilter = createButton("Áp dụng Lọc", COLOR_DARK, 150);
        btnFilter.addActionListener(e -> performSearch());
        JButton btnReset = createButton("Làm Mới", COLOR_LIGHT_PINK, 120);
        btnReset.addActionListener(e -> {
            txtMaSach.setText(""); txtTenSach.setText(""); 
            txtGiaTu.setText(""); txtGiaDen.setText("");
            txtTonKhoTu.setText(""); txtTonKhoDen.setText("");
            loadDanhSachSach();
        });
        pnlRow3.add(btnFilter); pnlRow3.add(btnReset);

        pnlFilter.add(pnlRow1);
        pnlFilter.add(pnlRow2);
        pnlFilter.add(pnlRow3);
        leftPanel.add(pnlFilter, BorderLayout.NORTH);

        String[] bookCols = {"Mã Sách", "Tên Sách", "Giá Bán", "Tồn Kho"};
        bookModel = new DefaultTableModel(bookCols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tblBooks = new JTable(bookModel);
        styleTable(tblBooks);

        tblBooks.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) handlingAddToCart();
            }
        });

        JScrollPane scrollBooks = new JScrollPane(tblBooks);
        scrollBooks.setBorder(new LineBorder(COLOR_DARK, 1));
        scrollBooks.getViewport().setBackground(COLOR_WHITE);
        leftPanel.add(scrollBooks, BorderLayout.CENTER);

        JPanel pnlAddAction = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        pnlAddAction.setBackground(COLOR_CREAM);
        pnlAddAction.add(createLabel("Nhập số lượng mua:"));
        
        txtSoLuong = new JTextField("1", 5);
        txtSoLuong.setFont(new Font("Segoe UI", Font.BOLD, 18));
        txtSoLuong.setPreferredSize(new Dimension(70, 45));
        txtSoLuong.setHorizontalAlignment(JTextField.CENTER);
        pnlAddAction.add(txtSoLuong);
        
        JButton btnAddCart = createButton("THÊM VÀO GIỎ MÓN ĐANG CHỌN", COLOR_MAGENTA, 280);
        btnAddCart.setPreferredSize(new Dimension(280, 45));
        btnAddCart.addActionListener(e -> handlingAddToCart());
        pnlAddAction.add(btnAddCart);
        leftPanel.add(pnlAddAction, BorderLayout.SOUTH);

        return leftPanel;
    }

    // ==========================================
    // KHUNG BÊN PHẢI: TÍCH HỢP "LINH HỒN" KHUYẾN MÃI
    // ==========================================
    private JPanel createRightPanel() {
        JPanel rightPanel = new JPanel(new BorderLayout(0, 20));
        rightPanel.setPreferredSize(new Dimension(480, 0));
        rightPanel.setBackground(COLOR_CREAM);

        JPanel pnlCartWrap = new JPanel(new BorderLayout());
        pnlCartWrap.setBackground(COLOR_WHITE);
        pnlCartWrap.setBorder(BorderFactory.createTitledBorder(
            new LineBorder(COLOR_DARK, 1), " CHI TIẾT GIỎ HÀNG ",
            TitledBorder.LEFT, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 14), COLOR_DARK));

        String[] cartCols = {"Sách", "SL", "Đơn Giá", "Thành Tiền"};
        cartModel = new DefaultTableModel(cartCols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tblCart = new JTable(cartModel);
        styleTable(tblCart);
        JScrollPane scrollCart = new JScrollPane(tblCart);
        scrollCart.getViewport().setBackground(COLOR_WHITE);
        pnlCartWrap.add(scrollCart, BorderLayout.CENTER);

        JButton btnXoa = new JButton("Bỏ món đang chọn khỏi giỏ");
        btnXoa.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnXoa.setForeground(Color.RED);
        btnXoa.setContentAreaFilled(false);
        btnXoa.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnXoa.addActionListener(e -> {
            int row = tblCart.getSelectedRow();
            if (row != -1) { dsGioHang.remove(row); capNhatBangGioHang(); }
            else JOptionPane.showMessageDialog(this, "Vui lòng chọn món cần xóa khỏi giỏ!");
        });
        pnlCartWrap.add(btnXoa, BorderLayout.SOUTH);
        rightPanel.add(pnlCartWrap, BorderLayout.CENTER);

        // --- FORM THANH TOÁN & KHUYẾN MÃI ---
        JPanel checkoutPanel = new JPanel();
        checkoutPanel.setLayout(new BoxLayout(checkoutPanel, BoxLayout.Y_AXIS));
        checkoutPanel.setBackground(COLOR_WHITE);
        checkoutPanel.setBorder(new CompoundBorder(
                new LineBorder(COLOR_DARK, 1), 
                new EmptyBorder(15, 20, 15, 20)));

        // 1. Dòng SĐT Khách
        JPanel pnlKH = new JPanel(new BorderLayout(10, 0));
        pnlKH.setBackground(COLOR_WHITE);
        pnlKH.setMaximumSize(new Dimension(500, 40));
        pnlKH.add(createLabel("SĐT Khách:"), BorderLayout.WEST);
        txtSDT = new JTextField(); txtSDT.setFont(new Font("Segoe UI", Font.BOLD, 16));
        JButton btnCheck = createButton("Kiểm tra", COLOR_DARK, 100);
        btnCheck.addActionListener(e -> checkCustomer());
        pnlKH.add(txtSDT, BorderLayout.CENTER);
        pnlKH.add(btnCheck, BorderLayout.EAST);

        lblTenKhach = new JLabel("Khách hàng: Vãng lai (Khách Lẻ)");
        lblTenKhach.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        lblTenKhach.setForeground(Color.GRAY);
        lblTenKhach.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 2. Dòng Nhập Mã Khuyến Mãi
        JPanel pnlKM = new JPanel(new BorderLayout(10, 0));
        pnlKM.setBackground(COLOR_WHITE);
        pnlKM.setMaximumSize(new Dimension(500, 40));
        pnlKM.add(createLabel("Mã Ưu Đãi:"), BorderLayout.WEST);
        txtMaKM = new JTextField(); txtMaKM.setFont(new Font("Segoe UI", Font.BOLD, 16));
        txtMaKM.setForeground(COLOR_MAGENTA);
        JButton btnApplyKM = createButton("Áp dụng", COLOR_MAGENTA, 100);
        btnApplyKM.addActionListener(e -> applyKhuyenMai());
        pnlKM.add(txtMaKM, BorderLayout.CENTER);
        pnlKM.add(btnApplyKM, BorderLayout.EAST);

        // 3. Tóm tắt Tiền
        lblTongTienHang = new JLabel("Tiền hàng: 0 VNĐ");
        lblTongTienHang.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblTongTienHang.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblTienGiam = new JLabel("Giảm giá: -0 VNĐ");
        lblTienGiam.setFont(new Font("Segoe UI", Font.ITALIC, 16));
        lblTienGiam.setForeground(Color.RED);
        lblTienGiam.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblTongTien = new JLabel("0 VNĐ", SwingConstants.CENTER);
        lblTongTien.setFont(new Font("Segoe UI", Font.BOLD, 38));
        lblTongTien.setForeground(COLOR_MAGENTA);
        lblTongTien.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnPay = new JButton("XÁC NHẬN THANH TOÁN");
        btnPay.setMaximumSize(new Dimension(Integer.MAX_VALUE, 55));
        btnPay.setBackground(new Color(46, 204, 113)); 
        btnPay.setForeground(COLOR_WHITE);
        btnPay.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btnPay.setFocusPainted(false);
        btnPay.setBorderPainted(false); 
        btnPay.setOpaque(true); 
        btnPay.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnPay.addActionListener(e -> handlingPayment());

        checkoutPanel.add(pnlKH);
        checkoutPanel.add(Box.createVerticalStrut(5));
        checkoutPanel.add(lblTenKhach);
        checkoutPanel.add(Box.createVerticalStrut(15));
        checkoutPanel.add(pnlKM);
        checkoutPanel.add(Box.createVerticalStrut(15));
        checkoutPanel.add(new JSeparator());
        checkoutPanel.add(Box.createVerticalStrut(10));
        checkoutPanel.add(lblTongTienHang);
        checkoutPanel.add(lblTienGiam);
        checkoutPanel.add(Box.createVerticalStrut(5));
        checkoutPanel.add(new JLabel("KHÁCH CẦN TRẢ", SwingConstants.CENTER) {{
            setFont(new Font("Segoe UI", Font.BOLD, 14)); setAlignmentX(Component.CENTER_ALIGNMENT);
        }});
        checkoutPanel.add(lblTongTien);
        checkoutPanel.add(Box.createVerticalStrut(15));
        checkoutPanel.add(btnPay);

        rightPanel.add(checkoutPanel, BorderLayout.SOUTH);
        return rightPanel;
    }

    // ==========================================
    // CÁC HÀM HỖ TRỢ HIỂN THỊ UI
    // ==========================================
    private JLabel createLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lbl.setForeground(COLOR_DARK);
        return lbl;
    }

    private JButton createButton(String text, Color bg, int width) {
        JButton btn = new JButton(text);
        btn.setBackground(bg); 
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setPreferredSize(new Dimension(width, 35));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void styleTable(JTable table) {
        table.setRowHeight(38);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setSelectionBackground(COLOR_LIGHT_PINK);
        table.setSelectionForeground(COLOR_DARK);
        table.setShowVerticalLines(false);
        
        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(230, 230, 230)); 
        header.setForeground(Color.BLACK);
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setPreferredSize(new Dimension(0, 40));
        header.setOpaque(true);
        ((DefaultTableCellRenderer) header.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

    // ==========================================
    // NGHIỆP VỤ LOGIC BÁN HÀNG VÀ KHUYẾN MÃI
    // ==========================================
    
    // --- LÕI SỨC MẠNH: TÍNH TOÁN TIỀN GIẢM ---
    private void tinhTienVaKhuyenMai() {
        tongTienHang = BigDecimal.ZERO;
        tienGiamGia = BigDecimal.ZERO;

        // 1. Tính tổng tiền hàng
        for (ChiTietHoaDonDTO ct : dsGioHang) {
            BigDecimal thanhTien = ct.getThanhTien();
            if (thanhTien == null) {
                thanhTien = ct.getDonGia().multiply(new BigDecimal(String.valueOf(ct.getSoLuong())));
            }
            tongTienHang = tongTienHang.add(thanhTien);
        }

        // 2. Kiểm tra điều kiện Khuyến mãi
        if (appliedKM != null) {
            if (tongTienHang.compareTo(appliedKM.getDonHangToiThieu()) >= 0) {
                // Đủ điều kiện: Tính tiền giảm
                if (appliedKM.getPhanTramGiam() != null && appliedKM.getPhanTramGiam().compareTo(BigDecimal.ZERO) > 0) {
                    // Giảm theo %
                    BigDecimal phanTram = appliedKM.getPhanTramGiam().divide(new BigDecimal("100"));
                    tienGiamGia = tongTienHang.multiply(phanTram);
                } else if (appliedKM.getSoTienGiam() != null && appliedKM.getSoTienGiam().compareTo(BigDecimal.ZERO) > 0) {
                    // Giảm thẳng tiền mặt
                    tienGiamGia = appliedKM.getSoTienGiam();
                }
                
                // Tránh việc giảm lố tổng tiền hàng (khách mua 50k, voucher trừ 100k)
                if (tienGiamGia.compareTo(tongTienHang) > 0) {
                    tienGiamGia = tongTienHang;
                }
            } else {
                // Rớt điều kiện (Ví dụ khách lỡ tay xóa bớt sách trong giỏ)
                appliedKM = null;
                txtMaKM.setText("");
                JOptionPane.showMessageDialog(this, "⚠️ Đã hủy mã KM vì tổng tiền không đủ yêu cầu tối thiểu!");
            }
        }

        // 3. Chốt số xuất lên giao diện
        BigDecimal thanhTienCuoiCung = tongTienHang.subtract(tienGiamGia);
        if(thanhTienCuoiCung.compareTo(BigDecimal.ZERO) < 0) thanhTienCuoiCung = BigDecimal.ZERO;

        lblTongTienHang.setText("Tiền hàng: " + df.format(tongTienHang));
        lblTienGiam.setText("Giảm giá: -" + df.format(tienGiamGia));
        lblTongTien.setText(df.format(thanhTienCuoiCung));
    }

    private void applyKhuyenMai() {
        String code = txtMaKM.getText().trim();
        if (code.isEmpty()) {
            appliedKM = null;
            tinhTienVaKhuyenMai();
            JOptionPane.showMessageDialog(this, "Đã gỡ bỏ mã khuyến mãi!");
            return;
        }

        KhuyenMaiDTO foundKM = null;
        for (KhuyenMaiDTO km : kmBUS.getAll()) {
            if (km.getMaCode().equalsIgnoreCase(code)) {
                foundKM = km;
                break;
            }
        }

        if (foundKM == null || !foundKM.getTrangThai().name().equals("HoatDong")) {
            JOptionPane.showMessageDialog(this, "❌ Mã khuyến mãi không tồn tại hoặc đã hết hạn!");
            appliedKM = null;
        } else if (tongTienHang.compareTo(foundKM.getDonHangToiThieu()) < 0) {
            JOptionPane.showMessageDialog(this, "❌ Đơn hàng chưa đạt giá trị tối thiểu (" + df.format(foundKM.getDonHangToiThieu()) + ") để áp dụng mã này!");
            appliedKM = null;
        } else {
            appliedKM = foundKM;
            JOptionPane.showMessageDialog(this, "🎉 Áp dụng mã khuyến mãi thành công!");
        }
        tinhTienVaKhuyenMai(); // Cập nhật lại số tiền
    }

    private void capNhatBangGioHang() {
        cartModel.setRowCount(0);
        for (ChiTietHoaDonDTO ct : dsGioHang) {
            BigDecimal thanhTien = ct.getThanhTien();
            if (thanhTien == null) {
                thanhTien = ct.getDonGia().multiply(new BigDecimal(String.valueOf(ct.getSoLuong())));
            }
            cartModel.addRow(new Object[]{ ct.getTenSach(), ct.getSoLuong(), df.format(ct.getDonGia()), df.format(thanhTien) });
        }
        tinhTienVaKhuyenMai(); // Cứ mỗi lần giỏ hàng thay đổi là phải tính lại tiền
    }

    private void handlingPayment() {
        if (dsGioHang.isEmpty()) { JOptionPane.showMessageDialog(this, "Giỏ hàng đang trống! Vui lòng chọn sách."); return; }
        
        double tong = tongTienHang.doubleValue();
        double tienGiam = tienGiamGia.doubleValue();
        double finalThanhTien = tong - tienGiam;

        int maNV = (currentUser != null && currentUser.getMaID() > 0) ? currentUser.getMaID() : 13; 
        
        // Lấy Mã ID của Khuyến mãi để lưu vào Database (nếu có)
        Integer maKMId = (appliedKM != null) ? appliedKM.getMaKM() : null;

        // ĐÃ SỬA: Bơm đủ thông số vào hàm thanh toán
        String ketQua = banHangBUS.thanhToanHoaDon(maNV, currentCustomerId, maKMId, dsGioHang, tong, tienGiam, finalThanhTien);
        
        if (ketQua.startsWith("Thành công")) {
            JOptionPane.showMessageDialog(this, "✅ " + ketQua);
            dsGioHang.clear();
            appliedKM = null; // Xóa mã đang áp dụng
            txtMaKM.setText("");
            capNhatBangGioHang();
            loadDanhSachSach();
            txtSDT.setText("");
            lblTenKhach.setText("Khách hàng: Vãng lai (Khách Lẻ)");
            lblTenKhach.setForeground(Color.GRAY);
            lblTenKhach.setFont(new Font("Segoe UI", Font.ITALIC, 14));
            currentCustomerId = null;
        } else {
            JOptionPane.showMessageDialog(this, "❌ " + ketQua);
        }
    }

    // Các hàm search & add giữ nguyên như cũ
    private void performSearch() {
        String ma = txtMaSach.getText().trim().toLowerCase();
        String ten = txtTenSach.getText().trim().toLowerCase();
        String giaTuStr = txtGiaTu.getText().trim();
        String giaDenStr = txtGiaDen.getText().trim();
        String tkTuStr = txtTonKhoTu.getText().trim();
        String tkDenStr = txtTonKhoDen.getText().trim();

        double giaTu = 0, giaDen = Double.MAX_VALUE;
        int tkTu = 0, tkDen = Integer.MAX_VALUE;

        try {
            if (!giaTuStr.isEmpty()) giaTu = Double.parseDouble(giaTuStr);
            if (!giaDenStr.isEmpty()) giaDen = Double.parseDouble(giaDenStr);
            if (!tkTuStr.isEmpty()) tkTu = Integer.parseInt(tkTuStr);
            if (!tkDenStr.isEmpty()) tkDen = Integer.parseInt(tkDenStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Khoảng Giá hoặc Tồn kho phải là Chữ Số!");
            return;
        }

        bookModel.setRowCount(0);
        List<SachDTO> list = sachBUS.getAllSach(); 
        if (list != null) {
            for (SachDTO s : list) {
                if (s.getTrangThai() != null && s.getTrangThai().equals("DangBan")) {
                    boolean matchMa = ma.isEmpty() || s.getMaSach().toLowerCase().contains(ma);
                    boolean matchTen = ten.isEmpty() || s.getTenSach().toLowerCase().contains(ten);
                    boolean matchGia = s.getGiaBan() >= giaTu && s.getGiaBan() <= giaDen;
                    boolean matchTK = s.getSoLuongTon() >= tkTu && s.getSoLuongTon() <= tkDen;

                    if (matchMa && matchTen && matchGia && matchTK) {
                        bookModel.addRow(new Object[]{ s.getMaSach(), s.getTenSach(), df.format(s.getGiaBan()), s.getSoLuongTon() });
                    }
                }
            }
        }
    }

    private void loadDanhSachSach() {
        bookModel.setRowCount(0);
        List<SachDTO> list = sachBUS.getAllSach();
        if (list != null) {
            for (SachDTO s : list) {
                if (s.getTrangThai() != null && s.getTrangThai().equals("DangBan")) {
                    bookModel.addRow(new Object[]{ s.getMaSach(), s.getTenSach(), df.format(s.getGiaBan()), s.getSoLuongTon() });
                }
            }
        }
    }

    private void checkCustomer() {
        String phone = txtSDT.getText().trim();
        if(phone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập SĐT để kiểm tra!");
            return;
        }
        KhachHangDTO kh = khachHangBUS.getKhachHangByPhone(phone); 
        if (kh != null) {
            currentCustomerId = kh.getMaID(); 
            lblTenKhach.setText("Khách hàng: " + kh.getHoTen()); 
            lblTenKhach.setForeground(COLOR_MAGENTA);
            lblTenKhach.setFont(new Font("Segoe UI", Font.BOLD, 15));
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy khách hàng trong hệ thống!");
            currentCustomerId = null; 
            lblTenKhach.setText("Khách hàng: Vãng lai (Khách Lẻ)");
            lblTenKhach.setForeground(Color.GRAY);
            lblTenKhach.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        }
    }

    private void handlingAddToCart() {
        int row = tblBooks.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 cuốn sách trên bảng để thêm vào giỏ!");
            return;
        }
        String maSach = tblBooks.getValueAt(row, 0).toString();
        try {
            int sl = Integer.parseInt(txtSoLuong.getText());
            if (sl <= 0) {
                JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn 0!");
                return;
            }
            if (banHangBUS.themVaoGioHang(maSach, sl, dsGioHang)) {
                capNhatBangGioHang();
                txtSoLuong.setText("1"); 
            } else { JOptionPane.showMessageDialog(this, "Số lượng trong kho không đủ để bán!"); }
        } catch (Exception e) { JOptionPane.showMessageDialog(this, "Số lượng nhập vào không hợp lệ!"); }
    }
}