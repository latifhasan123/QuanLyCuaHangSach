/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.text.DecimalFormat;
import DAO.SachDAO;
import DTO.SachDTO;
import BUS.ClientGioHangBUS;
import BUS.KhachHangBUS;
import java.awt.event.*;

public class PanelChiTietSach extends JPanel {
    private Color mainPink = Color.decode("#E889A9");
    private Color darkPurple = Color.decode("#5A4664");
    private DecimalFormat df = new DecimalFormat("#,###");
    private int quantity = 1;
    
    public PanelChiTietSach(SachDTO sach) {
        setLayout(new BorderLayout());
        // 1. ĐỔI NỀN TỔNG THỂ THÀNH MÀU XÁM NHẠT (Khớp với thanh công cụ)
        setBackground(Color.decode("#F5F5F5")); 
        setBorder(new EmptyBorder(20, 70, 40, 70)); // Canh lề ngoài cho cân đối
        
        // --- NÚT QUAY VỀ TRANG CHỦ (Nằm trần trên nền xám) ---
        JPanel pnlTop = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        pnlTop.setOpaque(false); // XÓA NỀN TRẮNG ĐỂ LỘ NỀN XÁM
        pnlTop.setBorder(new EmptyBorder(0, 0, 15, 0));
        
        JButton btnBack = new JButton("Quay về Trang chủ") {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Boolean.TRUE.equals(getClientProperty("hover")) ? mainPink.darker() : mainPink);
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
            ((ClientMainFrame) SwingUtilities.getWindowAncestor(this)).switchPanel(new PanelTrangChu((ClientMainFrame) SwingUtilities.getWindowAncestor(this)));
        });
        pnlTop.add(btnBack);
        add(pnlTop, BorderLayout.NORTH);
        
        // --- 2. WRAPPER CARD: KHUNG TRẮNG BO GÓC CHỨA SẢN PHẨM ---
        JPanel mainContent = new JPanel(new GridLayout(1, 2, 40, 0)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // Vẽ nền trắng cho toàn bộ thẻ
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 40, 40);
                // Vẽ viền ngoài xám mờ để thẻ tách biệt hẳn với nền xám
                g2.setColor(Color.decode("#E0E0E0"));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 40, 40);
                g2.dispose();
            }
        };
        // CỰC KỲ QUAN TRỌNG: Làm trong suốt 4 góc nhọn bên ngoài để lộ nền xám
        mainContent.setOpaque(false); 
        // Lề bên trong thẻ trắng (Khoảng cách từ viền trắng dội vào trong nội dung)
        mainContent.setBorder(new EmptyBorder(40, 50, 40, 50));

        // --- KHỐI KHUNG ẢNH NGHỆ THUẬT - FIX DỨT ĐIỂM ---
        // --- KHỐI KHUNG ẢNH NGHỆ THUẬT - BẢN THU GỌN (FIX MẤT PHẦN DƯỚI) ---
        JPanel pnlLeft = new JPanel(new GridBagLayout()); 
        pnlLeft.setBackground(Color.WHITE);

        JPanel pnlImageFrame = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Vẽ nền màu kem (OldLace)
                g2.setColor(Color.decode("#FDF5E6")); 
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);
                
                // Vẽ viền hồng bo tròn sắc nét
                g2.setColor(mainPink);
                g2.setStroke(new BasicStroke(2.2f));
                g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 30, 30);
                
                g2.dispose();
            }
        };
        pnlImageFrame.setOpaque(false);
        // Thu gọn Dimension: 380x510 (giảm gần 100px chiều cao so với trước)
        pnlImageFrame.setPreferredSize(new Dimension(360, 480)); 
        pnlImageFrame.setBorder(new EmptyBorder(20, 20, 20, 20)); 

        JLabel lblImage = new JLabel("", SwingConstants.CENTER);
        try {
            String tenAnh = sach.getHinhAnh() != null ? sach.getHinhAnh().trim() : "";
            java.net.URL imgURL = getClass().getResource("/images/" + tenAnh);
            
            if (imgURL != null) {
                ImageIcon icon = new ImageIcon(imgURL);
                // Scale ảnh xuống 340x470 để nằm lọt thỏm trong khung 380x510
                Image scaledImg = icon.getImage().getScaledInstance(320, 440, Image.SCALE_SMOOTH);
                lblImage.setIcon(new ImageIcon(scaledImg));
            }
        } catch (Exception e) {}
        
        pnlImageFrame.add(lblImage, BorderLayout.CENTER);
        pnlLeft.add(pnlImageFrame);
        mainContent.add(pnlLeft);
        
        pnlImageFrame.add(lblImage, BorderLayout.CENTER);
        pnlLeft.add(pnlImageFrame);
        
        mainContent.add(pnlLeft); // Thêm cái khung này vào màn hình chính // Thêm cái khung này vào màn hình chính

        // ======================== CỘT PHẢI: THÔNG TIN CHI TIẾT ========================
        // ======================== CỘT PHẢI: THÔNG TIN CHI TIẾT ========================
        JPanel pnlInfo = new JPanel();
        pnlInfo.setLayout(new BoxLayout(pnlInfo, BoxLayout.Y_AXIS));
        pnlInfo.setBackground(Color.WHITE);
        pnlInfo.setBorder(new EmptyBorder(0, 50, 0, 0));

        // 1. Tên sách (Đã gỡ Badge Chính Hãng)
        JLabel lblTen = new JLabel("<html><body style='width: 450px'>" + sach.getTenSach() + "</body></html>");
        lblTen.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblTen.setForeground(darkPurple);
        lblTen.setAlignmentX(Component.LEFT_ALIGNMENT);

        // 2. Giá tiền (Đã gỡ Đánh giá sao)
        JLabel lblGia = new JLabel(df.format(sach.getGiaBan()) + " VND");
        lblGia.setFont(new Font("Segoe UI", Font.BOLD, 36));
        lblGia.setForeground(mainPink);
        lblGia.setAlignmentX(Component.LEFT_ALIGNMENT);

        // 3. Bảng thông số kỹ thuật (Đã gỡ Tình trạng)
        // 3. Bảng thông số kỹ thuật (Cập nhật logic Còn hàng / Hết hàng)
        JPanel pnlSpecs = new JPanel(new GridLayout(0, 1, 0, 10));
        pnlSpecs.setOpaque(false);
        pnlSpecs.setAlignmentX(Component.LEFT_ALIGNMENT);
        pnlSpecs.setBorder(new EmptyBorder(20, 0, 20, 0));
        
        pnlSpecs.add(createSpecRow("Tác giả:", "Nhiều tác giả", darkPurple)); 
        pnlSpecs.add(createSpecRow("Thể loại:", sach.getTenLoai(), darkPurple)); 
        
        // Logic Đổi màu tình trạng
        String statusText = sach.getSoLuongTon() > 0 ? "Còn hàng" : "Hết hàng";
        Color statusColor = sach.getSoLuongTon() > 0 ? Color.decode("#27ae60") : Color.decode("#e74c3c");
        pnlSpecs.add(createSpecRow("Tình trạng:", statusText, statusColor));

        // 4. Cụm chọn số lượng (Fix dứt điểm lỗi ...)
        // 4. Cụm chọn số lượng (Fix dứt điểm lỗi mất viền và dấu ...)
        // 4. CỤM SỐ LƯỢNG (BẢN CHỐT - ÉP CHÍNH GIỮA, CHỐNG RỚT)
        // 4. CỤM SỐ LƯỢNG (BẢN KHÓA CỨNG KÍCH THƯỚC - CHỐNG CẮT VIỀN 100%)
        JLabel lblQtyTitle = new JLabel("Số lượng:");
        lblQtyTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblQtyTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        // FIX TẬN GỐC: Thêm đệm dọc 5px (vgap) để chừa khoảng trống an toàn
        JPanel pnlQtyWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
        pnlQtyWrapper.setOpaque(false);
        pnlQtyWrapper.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // KHÓA CỨNG CHIỀU CAO WRAPPER LÀ 50PX (Ngăn chặn BoxLayout ép xuống)
        Dimension wrapDim = new Dimension(400, 50);
        pnlQtyWrapper.setPreferredSize(wrapDim);
        pnlQtyWrapper.setMinimumSize(wrapDim);
        pnlQtyWrapper.setMaximumSize(wrapDim);

        JPanel pnlQtyPill = new JPanel(new GridBagLayout()) { 
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(1, 1, getWidth()-3, getHeight()-3, 38, 38); 
                g2.setColor(Color.LIGHT_GRAY);
                g2.setStroke(new BasicStroke(1.5f)); // Làm đậm viền lên một chút cho rõ nét
                g2.drawRoundRect(1, 1, getWidth()-3, getHeight()-3, 38, 38);
                g2.dispose();
            }
        };
        pnlQtyPill.setOpaque(false);
        
        // KHÓA CỨNG CHIỀU CAO PILL LÀ 40PX (Nằm gọn bên trong Wrapper 50px)
        Dimension pillDim = new Dimension(140, 40);
        pnlQtyPill.setPreferredSize(pillDim); 
        pnlQtyPill.setMinimumSize(pillDim);
        pnlQtyPill.setMaximumSize(pillDim);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridy = 0; 
        gbc.weighty = 1.0; 

        JButton btnMinus = createQtyBtn("-");
        gbc.gridx = 0; gbc.weightx = 0.3;
        pnlQtyPill.add(btnMinus, gbc);

        JTextField txtQty = new JTextField(String.valueOf(quantity));
        txtQty.setHorizontalAlignment(SwingConstants.CENTER);
        txtQty.setBorder(null); txtQty.setOpaque(false);
        txtQty.setFont(new Font("Segoe UI", Font.BOLD, 18));
        gbc.gridx = 1; gbc.weightx = 0.4;
        pnlQtyPill.add(txtQty, gbc);

        JButton btnPlus = createQtyBtn("+");
        gbc.gridx = 2; gbc.weightx = 0.3;
        pnlQtyPill.add(btnPlus, gbc);

        btnMinus.addActionListener(e -> { if(quantity > 1) { quantity--; txtQty.setText(String.valueOf(quantity)); }});
        btnPlus.addActionListener(e -> { quantity++; txtQty.setText(String.valueOf(quantity)); });

        pnlQtyWrapper.add(pnlQtyPill);

        // 5. Mô tả sách (giữ nguyên scrollMoTa hiện tại của Sếp)
        JLabel lblMoTaTitle = new JLabel("Mô tả sản phẩm");
        lblMoTaTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblMoTaTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        String moTaValue = (sach.getMoTa() != null && !sach.getMoTa().isEmpty()) ? sach.getMoTa() : "Chưa có mô tả chi tiết cho cuốn sách này.";
        JTextArea txtMoTa = new JTextArea(moTaValue);
        txtMoTa.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtMoTa.setForeground(Color.decode("#636E72"));
        txtMoTa.setLineWrap(true); txtMoTa.setWrapStyleWord(true);
        txtMoTa.setEditable(false); txtMoTa.setBackground(Color.WHITE);
        
        JScrollPane scrollMoTa = new JScrollPane(txtMoTa);
        scrollMoTa.setBorder(null); 
        scrollMoTa.setAlignmentX(Component.LEFT_ALIGNMENT);
        scrollMoTa.setMaximumSize(new Dimension(550, 150)); 
        scrollMoTa.setPreferredSize(new Dimension(550, 150));
        scrollMoTa.getVerticalScrollBar().setPreferredSize(new Dimension(6, 0));
        scrollMoTa.getVerticalScrollBar().setUnitIncrement(16);

        // 6. Nút bấm hành động (TĂNG CHIỀU CAO LÊN 55PX CHO "MẬP")
        JPanel pnlBtns = new JPanel(new GridLayout(1, 2, 20, 0));
        pnlBtns.setOpaque(false);
        pnlBtns.setMaximumSize(new Dimension(500, 55)); // Mập lên rõ rệt
        pnlBtns.setPreferredSize(new Dimension(500, 55));
        pnlBtns.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton btnAdd = new JButton("THÊM VÀO GIỎ") {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Boolean.TRUE.equals(getClientProperty("hover")) ? mainPink.darker() : mainPink);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 40, 40);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btnAdd.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setFocusPainted(false); btnAdd.setContentAreaFilled(false); btnAdd.setBorderPainted(false);
        btnAdd.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAdd.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btnAdd.putClientProperty("hover", true); btnAdd.repaint(); }
            public void mouseExited(MouseEvent e) { btnAdd.putClientProperty("hover", false); btnAdd.repaint(); }
        });

        JButton btnBuy = new JButton("MUA NGAY") {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color btnColor = Color.decode("#FF4757"); 
                g2.setColor(Boolean.TRUE.equals(getClientProperty("hover")) ? btnColor.darker() : btnColor);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 40, 40);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btnBuy.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnBuy.setForeground(Color.WHITE);
        btnBuy.setFocusPainted(false); btnBuy.setContentAreaFilled(false); btnBuy.setBorderPainted(false);
        btnBuy.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnBuy.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btnBuy.putClientProperty("hover", true); btnBuy.repaint(); }
            public void mouseExited(MouseEvent e) { btnBuy.putClientProperty("hover", false); btnBuy.repaint(); }
        });
        btnBuy.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnBuy.setForeground(Color.WHITE);
        btnBuy.setFocusPainted(false); btnBuy.setContentAreaFilled(false); btnBuy.setBorderPainted(false);
        btnBuy.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnBuy.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) { btnBuy.putClientProperty("hover", true); btnBuy.repaint(); }
            public void mouseExited(java.awt.event.MouseEvent e) { btnBuy.putClientProperty("hover", false); btnBuy.repaint(); }
        });

        btnAdd.addActionListener(e -> {
            if (KhachHangBUS.currentCustomer == null) {
                ClientMainFrame parentFrame = (ClientMainFrame) SwingUtilities.getWindowAncestor(this);
                parentFrame.showLoginWarning(); 
                return;
            }
            ClientGioHangBUS bus = new ClientGioHangBUS();
            bus.themVaoGio(sach, Integer.parseInt(txtQty.getText()));
            JOptionPane.showMessageDialog(this, "Đã thêm vào giỏ hàng!");
        });

        btnBuy.addActionListener(e -> {
            if (KhachHangBUS.currentCustomer == null) {
                ClientMainFrame parentFrame = (ClientMainFrame) SwingUtilities.getWindowAncestor(this);
                parentFrame.showLoginWarning(); 
                return;
            }
            new ClientGioHangBUS().themVaoGio(sach, Integer.parseInt(txtQty.getText()));
            ((ClientMainFrame) SwingUtilities.getWindowAncestor(this)).switchPanel(new PanelGioHang());
        });

        pnlBtns.add(btnAdd); pnlBtns.add(btnBuy);

        // ADD TẤT CẢ VÀO INFO
        pnlInfo.add(lblTen); 
        pnlInfo.add(Box.createVerticalStrut(10)); // Giảm khoảng cách xuống còn 10px
        pnlInfo.add(lblGia); 
        pnlInfo.add(Box.createVerticalStrut(0));  // Ép thông số lại gần giá hơn
        pnlInfo.add(pnlSpecs); 
        pnlInfo.add(Box.createVerticalStrut(0));  
        pnlInfo.add(lblQtyTitle); 
        pnlInfo.add(Box.createVerticalStrut(5));  // Khoảng cách ô số lượng
        pnlInfo.add(pnlQtyWrapper); 
        pnlInfo.add(Box.createVerticalStrut(20)); // Khoảng cách tới Mô tả
        pnlInfo.add(lblMoTaTitle); 
        pnlInfo.add(Box.createVerticalStrut(10));
        pnlInfo.add(scrollMoTa); 
        pnlInfo.add(Box.createVerticalGlue()); // "Lò xo" đàn hồi đẩy 2 nút bấm xuống dưới cùng hoặc lấp đầy chỗ trống
        pnlInfo.add(pnlBtns);

        mainContent.add(pnlInfo);
        add(mainContent, BorderLayout.CENTER);
    }

    // ======================== HELPER METHODS ========================
    // ======================== HELPER METHODS ========================
    private JPanel createSpecRow(String label, String val, Color valColor) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        row.setOpaque(false);
        JLabel lblL = new JLabel(label);
        lblL.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblL.setForeground(Color.GRAY);
        lblL.setPreferredSize(new Dimension(120, 20));
        
        JLabel lblV = new JLabel(val);
        lblV.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblV.setForeground(valColor); // Nhận màu động từ dữ liệu
        
        row.add(lblL); row.add(lblV);
        return row;
    }

    private JButton createQtyBtn(String text) {
        JButton b = new JButton(text);
        b.setFont(new Font("Segoe UI", Font.BOLD, 20)); // Dấu + to rõ
        b.setMargin(new Insets(0, 0, 0, 0)); // Xóa sạch lề
        b.setBorder(null); // Xóa viền để không chiếm diện tích
        b.setContentAreaFilled(false);
        b.setFocusPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return b;
    }

    private void styleModernBtn(JButton b, Color bg, Color border, boolean isOutline) {
        b.setFont(new Font("Segoe UI", Font.BOLD, 16));
        b.setFocusPainted(false); b.setContentAreaFilled(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.setForeground(isOutline ? border : Color.WHITE);
        b.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) { b.setOpaque(true); b.setBackground(border.darker()); b.setForeground(Color.WHITE); b.repaint(); }
            public void mouseExited(java.awt.event.MouseEvent e) { b.setOpaque(!isOutline); b.setBackground(bg); b.setForeground(isOutline ? border : Color.WHITE); b.repaint(); }
        });
        if(!isOutline) { b.setOpaque(true); b.setBackground(bg); b.setBorder(new LineBorder(border, 2, true)); }
        else { b.setBorder(new LineBorder(border, 2, true)); }
    }
}