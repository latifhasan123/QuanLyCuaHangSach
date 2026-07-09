package gui;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import BUS.ClientGioHangBUS;
import BUS.DonHangBUS;
import DTO.DonHangDTO;
import BUS.KhachHangBUS;
import DTO.SachDTO;
import java.util.ArrayList;

public class PanelThanhToan extends JPanel {
    private Color mainPink = Color.decode("#E889A9");
    private Color hoverPink = Color.decode("#D67897");
    private Color lightPink = Color.decode("#FFF0F5");
    private Color darkPurple = Color.decode("#5A4664");
    private Color borderColor = Color.decode("#DCDDE1");
    private DecimalFormat df = new DecimalFormat("#,###");

    private JTextField txtEmail, txtHoTen, txtSdt, txtDiaChi;
    private JComboBox<String> cmbTinh, cmbPhuong;
    private JTextArea txtGhiChu;
    private JRadioButton rdoCOD;
    
    private ClientGioHangBUS gioHangBUS = new ClientGioHangBUS();
    private DonHangBUS donHangBUS = new DonHangBUS();  

    public PanelThanhToan() {
        setLayout(new BorderLayout());
        setBackground(Color.decode("#F5F5F5")); 
        setBorder(new EmptyBorder(20, 70, 40, 70));

        // --- 1. NÚT QUAY LẠI GIỎ HÀNG ---
        JPanel pnlTop = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        pnlTop.setOpaque(false);
        pnlTop.setBorder(new EmptyBorder(0, 0, 20, 0));

        JButton btnBack = new JButton("Quay lại Giỏ hàng") {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Boolean.TRUE.equals(getClientProperty("hover")) ? hoverPink : mainPink);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btnBack.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnBack.setForeground(Color.WHITE);
        btnBack.setPreferredSize(new Dimension(180, 40));
        btnBack.setFocusPainted(false); btnBack.setContentAreaFilled(false); btnBack.setBorderPainted(false);
        btnBack.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnBack.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btnBack.putClientProperty("hover", true); btnBack.repaint(); }
            public void mouseExited(MouseEvent e) { btnBack.putClientProperty("hover", false); btnBack.repaint(); }
        });
        btnBack.addActionListener(e -> {
            ClientMainFrame parentFrame = (ClientMainFrame) SwingUtilities.getWindowAncestor(this);
            parentFrame.switchPanel(new PanelGioHang());
        });
        pnlTop.add(btnBack);
        add(pnlTop, BorderLayout.NORTH);

        // --- 2. BỐ CỤC CHÍNH 2 CỘT ---
        JPanel pnlMain = new JPanel(new BorderLayout(30, 0));
        pnlMain.setOpaque(false);

        // ================= TRÁI: THÔNG TIN NHẬN HÀNG (CARD UI) =================
        JPanel pnlLeftCard = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);
                g2.setColor(Color.decode("#E0E0E0"));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);
                g2.dispose();
            }
        };
        pnlLeftCard.setOpaque(false);
        pnlLeftCard.setBorder(new EmptyBorder(30, 40, 30, 40));

        JPanel pnlLeftContent = new JPanel();
        pnlLeftContent.setLayout(new BoxLayout(pnlLeftContent, BoxLayout.Y_AXIS));
        pnlLeftContent.setOpaque(false);

        JLabel lblTitle = new JLabel("Thanh Toán Đơn Hàng");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitle.setForeground(darkPurple);
        lblTitle.setBorder(new EmptyBorder(0, 0, 20, 0));
        // FIX LỖI: Căn trái tuyệt đối cho Tiêu đề
        lblTitle.setAlignmentX(Component.LEFT_ALIGNMENT); 
        pnlLeftContent.add(lblTitle);

        addSectionTitle(pnlLeftContent, "1. Thông tin người nhận");
        txtHoTen = createInputField(pnlLeftContent, "Họ và tên người nhận");
        txtSdt = createInputField(pnlLeftContent, "Số điện thoại");
        txtEmail = createInputField(pnlLeftContent, "Email (Tùy chọn)");

        if (KhachHangBUS.currentCustomer != null) {
            txtHoTen.setText(KhachHangBUS.currentCustomer.getHoTen());
            txtSdt.setText(KhachHangBUS.currentCustomer.getSoDienThoai());
            txtEmail.setText(KhachHangBUS.currentCustomer.getEmail());
            txtHoTen.setForeground(Color.BLACK);
            txtSdt.setForeground(Color.BLACK);
            txtEmail.setForeground(Color.BLACK);
        }

        pnlLeftContent.add(Box.createVerticalStrut(10));
        addSectionTitle(pnlLeftContent, "2. Địa chỉ giao hàng");

        cmbTinh = new JComboBox<>(new String[]{"TP Hồ Chí Minh"});
        addComboField(pnlLeftContent, "Thành phố / Tỉnh", cmbTinh);
        
        cmbPhuong = new JComboBox<>(new String[]{
            "Phường Sài Gòn", "Phường Tân Định", "Phường Bến Thành", "Phường Cầu Ông Lãnh",
            "Phường Gò Vấp", "Phường Bình Thạnh", "Phường Phú Nhuận", "Phường Tân Bình"
        });
        addComboField(pnlLeftContent, "Quận / Phường", cmbPhuong);
        
        txtDiaChi = createInputField(pnlLeftContent, "Số nhà, Tên đường, Phường/Xã");

        pnlLeftContent.add(Box.createVerticalStrut(10));
        JLabel lblGhiChu = new JLabel("Ghi chú cho đơn hàng (Tùy chọn)");
        lblGhiChu.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblGhiChu.setForeground(Color.DARK_GRAY);
        // FIX LỖI: Căn trái cho nhãn Ghi chú
        lblGhiChu.setAlignmentX(Component.LEFT_ALIGNMENT); 
        pnlLeftContent.add(lblGhiChu);
        pnlLeftContent.add(Box.createVerticalStrut(5));
        
        txtGhiChu = new JTextArea();
        txtGhiChu.setRows(3);
        txtGhiChu.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        txtGhiChu.setLineWrap(true); txtGhiChu.setWrapStyleWord(true);
        txtGhiChu.setBorder(null); txtGhiChu.setOpaque(false);
        
        // Bọc JTextArea vào khung bo tròn
        JPanel ghiChuWrapper = createRoundedWrapper();
        ghiChuWrapper.add(txtGhiChu, BorderLayout.CENTER);
        // FIX LỖI: Căn trái và khóa chiều cao cho Wrapper Ghi chú
        ghiChuWrapper.setAlignmentX(Component.LEFT_ALIGNMENT);
        ghiChuWrapper.setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));
        pnlLeftContent.add(ghiChuWrapper);

        pnlLeftContent.add(Box.createVerticalStrut(30));
        addSectionTitle(pnlLeftContent, "3. Phương thức thanh toán");
        
        JPanel pnlPay = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlPay.setBackground(lightPink);
        pnlPay.setBorder(new LineBorder(mainPink, 1, true));
        pnlPay.setAlignmentX(Component.LEFT_ALIGNMENT); 
        rdoCOD = new JRadioButton("  Thanh toán tiền mặt khi nhận hàng (COD)");
        rdoCOD.setOpaque(false);
        rdoCOD.setFont(new Font("Segoe UI", Font.BOLD, 15));
        rdoCOD.setForeground(darkPurple);
        rdoCOD.setSelected(true);
        pnlPay.add(rdoCOD);
        pnlLeftContent.add(pnlPay);

        // Thanh cuộn trái
        JScrollPane scrollLeft = new JScrollPane(pnlLeftContent);
        scrollLeft.getVerticalScrollBar().setUnitIncrement(20);
        scrollLeft.setBorder(null); scrollLeft.setOpaque(false); scrollLeft.getViewport().setOpaque(false);
        scrollLeft.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollLeft.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollLeft.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0)); // Tàng hình
        pnlLeftCard.add(scrollLeft, BorderLayout.CENTER);
        pnlMain.add(pnlLeftCard, BorderLayout.CENTER);

        // ================= PHẢI: TÓM TẮT ĐƠN HÀNG (CARD UI) =================
        JPanel pnlRight = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);
                g2.setColor(Color.decode("#E0E0E0"));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);
                g2.dispose();
            }
        };
        pnlRight.setOpaque(false);
        pnlRight.setPreferredSize(new Dimension(450, 0)); 
        pnlRight.setBorder(new EmptyBorder(30, 30, 30, 30));

        JLabel lblBillTitle = new JLabel("Tóm tắt đơn hàng");
        lblBillTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblBillTitle.setForeground(darkPurple);
        lblBillTitle.setBorder(new EmptyBorder(0, 0, 20, 0));
        pnlRight.add(lblBillTitle, BorderLayout.NORTH);

        JPanel pnlItems = new JPanel();
        pnlItems.setLayout(new BoxLayout(pnlItems, BoxLayout.Y_AXIS));
        pnlItems.setBackground(Color.WHITE);
        
        for (SachDTO s : gioHangBUS.getDanhSachUnique()) {
            JPanel row = new JPanel(new BorderLayout(15, 0));
            row.setBackground(Color.WHITE);
            row.setBorder(new EmptyBorder(10, 0, 10, 0));
            row.setAlignmentX(Component.LEFT_ALIGNMENT);
            
            JLabel lblImg = new JLabel();
            lblImg.setPreferredSize(new Dimension(50, 70));
            try {
                String tenAnh = s.getHinhAnh() != null ? s.getHinhAnh().trim() : "";
                java.net.URL imgURL = getClass().getResource("/images/" + tenAnh);
                if (imgURL != null) {
                    Image img = new ImageIcon(imgURL).getImage().getScaledInstance(50, 70, Image.SCALE_SMOOTH);
                    lblImg.setIcon(new ImageIcon(img));
                }
            } catch (Exception e) {}
            
            JPanel pnlInfo = new JPanel(new GridLayout(2, 1));
            pnlInfo.setOpaque(false);
            JLabel name = new JLabel("<html><div style='width:160px;'>" + s.getTenSach() + "</div></html>");
            name.setFont(new Font("Segoe UI", Font.PLAIN, 15));
            JLabel qty = new JLabel("Số lượng: " + s.getSoLuongTon());
            qty.setFont(new Font("Segoe UI", Font.BOLD, 13));
            qty.setForeground(Color.GRAY);
            pnlInfo.add(name); pnlInfo.add(qty);
            
            JLabel price = new JLabel(df.format(s.getGiaBan() * s.getSoLuongTon()) + "đ");
            price.setFont(new Font("Segoe UI", Font.BOLD, 16));
            price.setForeground(darkPurple);
            
            row.add(lblImg, BorderLayout.WEST);
            row.add(pnlInfo, BorderLayout.CENTER);
            row.add(price, BorderLayout.EAST);
            pnlItems.add(row);
            
            JSeparator sep = new JSeparator();
            sep.setForeground(Color.decode("#F0F0F0"));
            pnlItems.add(sep);
        }
        
        // Giấu thanh cuộn danh sách món hàng
        JScrollPane scrollItems = new JScrollPane(pnlItems);
        scrollItems.setBorder(null); scrollItems.getViewport().setBackground(Color.WHITE);
        
        // FIX LỖI: Gọi đúng scrollItems để tăng tốc độ cuộn
        scrollItems.getVerticalScrollBar().setUnitIncrement(20); 
        
        scrollItems.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollItems.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollItems.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0)); // Tàng hình
        pnlRight.add(scrollItems, BorderLayout.CENTER);

        JPanel pnlSummary = new JPanel();
        pnlSummary.setLayout(new BoxLayout(pnlSummary, BoxLayout.Y_AXIS));
        pnlSummary.setBackground(Color.WHITE);
        pnlSummary.setBorder(new EmptyBorder(20, 0, 0, 0));

        pnlSummary.add(createSummaryRow("Tạm tính", df.format(gioHangBUS.tinhTongTien()) + "đ", Font.PLAIN, darkPurple));
        pnlSummary.add(Box.createVerticalStrut(10));
        pnlSummary.add(createSummaryRow("Phí vận chuyển", "Miễn phí", Font.PLAIN, Color.decode("#34C759")));
        pnlSummary.add(Box.createVerticalStrut(15));
        
        JSeparator sep2 = new JSeparator();
        sep2.setForeground(borderColor);
        pnlSummary.add(sep2);
        pnlSummary.add(Box.createVerticalStrut(15));

        pnlSummary.add(createSummaryRow("Tổng cộng", df.format(gioHangBUS.tinhTongTien()) + "đ", Font.BOLD, Color.RED));
        pnlSummary.add(Box.createVerticalStrut(25));

        // Nút bấm thanh toán mập mạp
        JButton btnDatHang = new JButton("XÁC NHẬN ĐẶT HÀNG"){
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Boolean.TRUE.equals(getClientProperty("hover")) ? hoverPink : mainPink);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 40, 40);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btnDatHang.setContentAreaFilled(false); btnDatHang.setBorderPainted(false); btnDatHang.setFocusPainted(false);
        btnDatHang.setForeground(Color.WHITE);
        btnDatHang.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btnDatHang.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnDatHang.setMaximumSize(new Dimension(Integer.MAX_VALUE, 55));
        btnDatHang.setPreferredSize(new Dimension(Integer.MAX_VALUE, 55));
        btnDatHang.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDatHang.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btnDatHang.putClientProperty("hover", true); btnDatHang.repaint(); }
            public void mouseExited(MouseEvent e) { btnDatHang.putClientProperty("hover", false); btnDatHang.repaint(); }
        });
        
        btnDatHang.addActionListener(e -> {
            ArrayList<SachDTO> dsMua = gioHangBUS.getDanhSachUnique(); 
            if (dsMua.isEmpty()) return;
            
            String hoTen = txtHoTen.getText().trim();
            String sdt = txtSdt.getText().trim();
            String diaChiCuThe = txtDiaChi.getText().trim();
            String phuong = cmbPhuong.getSelectedItem().toString();
            String tinh = cmbTinh.getSelectedItem().toString();
            
            if (hoTen.isEmpty() || sdt.isEmpty() || diaChiCuThe.isEmpty()) {
                ThongBao.show(this, "Vui lòng nhập đầy đủ thông tin giao hàng!"); 
                return;
            }

            String diaChiDayDu = diaChiCuThe + ", " + phuong + ", " + tinh;
            double tongTienHang = gioHangBUS.tinhTongTien();
            
            DonHangDTO donHang = new DonHangDTO();
            donHang.setTongTien(tongTienHang);
            donHang.setThanhTien(tongTienHang); 
            donHang.setDiaChiGiao(diaChiDayDu);
            donHang.setTenNguoiNhan(hoTen);
            donHang.setSdtNhan(sdt);
            
            int maKH = KhachHangBUS.currentCustomer.getMaID();
            int maDHVuaTao = donHangBUS.luuDonHang(donHang, maKH, dsMua);

            if (maDHVuaTao > 0) { 
                int diemDuocCong = (int) (tongTienHang / 1000); 
                new KhachHangBUS().capNhatThongTinSauDatHang(maKH, diaChiDayDu, diemDuocCong);
                gioHangBUS.clear(); 
                ThongBao.showDatHangThanhCong(this, maDHVuaTao, diemDuocCong);
            } else {
                ThongBao.show(this, "Có lỗi xảy ra. Vui lòng kiểm tra lại!"); 
            }
        });

        pnlSummary.add(btnDatHang);
        pnlRight.add(pnlSummary, BorderLayout.SOUTH);
        pnlMain.add(pnlRight, BorderLayout.EAST);
        
        add(pnlMain, BorderLayout.CENTER);
    } 

    // ================= CÁC HÀM HỖ TRỢ UI =================
    private void addSectionTitle(JPanel pnl, String title) {
        JLabel lbl = new JLabel(title);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lbl.setForeground(darkPurple);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT); 
        pnl.add(lbl); pnl.add(Box.createVerticalStrut(15));
    }

    // Tạo Wrapper bo tròn cho Text Field
// Tạo Wrapper bo tròn cho Text Field (Đã thêm viền đậm nét và sắc sảo hơn)
    // Tạo Wrapper bo tròn cho Text Field (BẢN UPDATE: Viền HỒNG đậm nét, sang chảnh)
    private JPanel createRoundedWrapper() {
        JPanel wrapper = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Vẽ nền trắng cho ô
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 15, 15);
                
                // 🔥 ĐỘT PHÁ SẮC ĐẸP: Đổi viền sang HỒNG ĐẬM và nét vẽ dày hơn
                g2.setColor(mainPink); // Sử dụng màu hồng chuẩn của dự án
                g2.setStroke(new BasicStroke(1.5f));  // Tăng độ dày viền lên 1.5 để nổi bật
                g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 15, 15);
                
                g2.dispose();
            }
        };
        wrapper.setOpaque(false);
        wrapper.setBorder(new EmptyBorder(10, 15, 10, 15));
        return wrapper;
    }

    private JTextField createInputField(JPanel pnl, String labelText) {
        JPanel outer = new JPanel(new BorderLayout(0, 8));
        outer.setOpaque(false);
        outer.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        outer.setAlignmentX(Component.LEFT_ALIGNMENT); 
        
        JLabel lbl = new JLabel(labelText);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lbl.setForeground(Color.DARK_GRAY);
        outer.add(lbl, BorderLayout.NORTH);

        JTextField txt = new JTextField();
        txt.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        txt.setBorder(null); txt.setOpaque(false);
        
        JPanel wrapper = createRoundedWrapper();
        wrapper.add(txt, BorderLayout.CENTER);
        outer.add(wrapper, BorderLayout.CENTER);
        
        pnl.add(outer); pnl.add(Box.createVerticalStrut(15));
        return txt;
    }
    
    private void addComboField(JPanel pnl, String labelText, JComboBox<String> cmb) {
        JPanel outer = new JPanel(new BorderLayout(0, 8));
        outer.setOpaque(false);
        outer.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        outer.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel lbl = new JLabel(labelText);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lbl.setForeground(Color.DARK_GRAY);
        outer.add(lbl, BorderLayout.NORTH);

        cmb.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        cmb.setBackground(Color.WHITE);
        cmb.setBorder(null); // Gỡ viền vuông
        
        JPanel wrapper = createRoundedWrapper();
        wrapper.setBorder(new EmptyBorder(5, 10, 5, 10));
        wrapper.add(cmb, BorderLayout.CENTER);
        outer.add(wrapper, BorderLayout.CENTER);
        
        pnl.add(outer); pnl.add(Box.createVerticalStrut(15));
    }

    private JPanel createSummaryRow(String title, String value, int fontStyle, Color valColor) {
        JPanel row = new JPanel(new BorderLayout());
        row.setBackground(Color.WHITE);
        row.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", fontStyle, 16));
        lblTitle.setForeground(darkPurple);
        
        JLabel lblValue = new JLabel(value);
        lblValue.setFont(new Font("Segoe UI", fontStyle, (fontStyle == Font.BOLD) ? 22 : 16));
        lblValue.setForeground(valColor);
        
        row.add(lblTitle, BorderLayout.WEST);
        row.add(lblValue, BorderLayout.EAST);
        return row;
    }
}