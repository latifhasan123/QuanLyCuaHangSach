package gui;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import DTO.SachDTO;
import BUS.ClientGioHangBUS;

public class PanelGioHang extends JPanel {
    private JPanel pnlListItems;
    private JLabel lblTongTien;
    private JLabel lblCount;
    private DecimalFormat df = new DecimalFormat("#,###");
    private ClientGioHangBUS gioHangBUS = new ClientGioHangBUS();
    
    // Bảng màu chuẩn của Trạm Đọc
    private Color mainPink = Color.decode("#E889A9");
    private Color hoverPink = Color.decode("#D67897");

    public PanelGioHang() {
        setLayout(new BorderLayout());
        setBackground(Color.decode("#F5F5F5")); 
        setBorder(new EmptyBorder(20, 70, 40, 70)); // Căn lề như trang Chi Tiết

        // --- 1. NÚT QUAY VỀ TRANG CHỦ (GÓC TRÁI) ---
        JPanel pnlTop = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        pnlTop.setOpaque(false);
        pnlTop.setBorder(new EmptyBorder(0, 0, 20, 0));

        JButton btnBack = new JButton("Quay về Trang chủ") {
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
            parentFrame.switchPanel(new PanelTrangChu(parentFrame));
        });
        pnlTop.add(btnBack);
        add(pnlTop, BorderLayout.NORTH);

        // --- 2. MAIN CONTENT (CHỨA GIỎ HÀNG VÀ TỔNG TIỀN) ---
        JPanel mainContent = new JPanel(new BorderLayout(30, 0));
        mainContent.setOpaque(false);

        // --- CỘT TRÁI: DANH SÁCH SẢN PHẨM ---
        JPanel pnlLeft = new JPanel(new BorderLayout());
        pnlLeft.setOpaque(false);
        
        JPanel pnlHeaderLeft = new JPanel(new BorderLayout());
        pnlHeaderLeft.setOpaque(false);
        pnlHeaderLeft.setBorder(new EmptyBorder(0, 0, 20, 0));
        
        JLabel title = new JLabel("Giỏ hàng của bạn", SwingConstants.LEFT);
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        title.setForeground(Color.decode("#5A4664")); // Tím than chuẩn web
        
        lblCount = new JLabel("Bạn đang có 0 sản phẩm trong giỏ hàng");
        lblCount.setFont(new Font("Segoe UI", Font.ITALIC, 16));
        lblCount.setForeground(Color.GRAY);
        lblCount.setBorder(new EmptyBorder(10, 0, 0, 0));
        
        pnlHeaderLeft.add(title, BorderLayout.NORTH);
        pnlHeaderLeft.add(lblCount, BorderLayout.SOUTH);
        pnlLeft.add(pnlHeaderLeft, BorderLayout.NORTH);
        
        pnlListItems = new JPanel();
        pnlListItems.setLayout(new BoxLayout(pnlListItems, BoxLayout.Y_AXIS));
        pnlListItems.setBackground(Color.decode("#F5F5F5")); // Nền tiệp màu hệ thống
        
        JScrollPane scroll = new JScrollPane(pnlListItems);
        scroll.setBorder(null); 
        scroll.getViewport().setBackground(Color.decode("#F5F5F5"));
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        // 🔥 KHÓA CHẶT THANH CUỘN NGANG ĐỂ KHÔNG BAO GIỜ BỊ XUẤT HIỆN NỮA
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); 
        
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        scroll.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0)); // Tàng hình thanh cuộn dọc
        pnlLeft.add(scroll, BorderLayout.CENTER);

        // --- CỘT PHẢI: THÔNG TIN ĐƠN HÀNG (CARD UI BO GÓC TRẮNG) ---
        JPanel pnlRightContainer = new JPanel(new BorderLayout());
        pnlRightContainer.setOpaque(false);
        pnlRightContainer.setPreferredSize(new Dimension(400, 0)); 
        pnlRightContainer.setBorder(new EmptyBorder(85, 0, 0, 0)); // Đẩy card xuống ngang hàng với list sách

        JPanel pnlRight = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);
                g2.setColor(Color.decode("#E0E0E0"));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);
                g2.dispose();
            }
        };
        pnlRight.setLayout(new BoxLayout(pnlRight, BoxLayout.Y_AXIS));
        pnlRight.setOpaque(false);
        pnlRight.setBorder(new EmptyBorder(40, 30, 40, 30));
        
        JLabel lblSummaryTitle = new JLabel("Thông tin đơn hàng");
        lblSummaryTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblSummaryTitle.setForeground(Color.decode("#5A4664"));
        lblSummaryTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JPanel pnlTotal = new JPanel(new BorderLayout());
        pnlTotal.setOpaque(false);
        pnlTotal.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        pnlTotal.setBorder(new EmptyBorder(40, 0, 40, 0));
        
        JLabel lblTotalText = new JLabel("Tổng tiền:");
        lblTotalText.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTotalText.setForeground(Color.GRAY);
        
        lblTongTien = new JLabel("0 VND");
        lblTongTien.setFont(new Font("Segoe UI", Font.BOLD, 30));
        lblTongTien.setForeground(mainPink); 
        pnlTotal.add(lblTotalText, BorderLayout.WEST);
        pnlTotal.add(lblTongTien, BorderLayout.EAST);
        
        // NÚT ĐẶT HÀNG MẬP MẠP
        JButton btnThanhToan = new JButton("ĐẶT HÀNG NGAY") {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Boolean.TRUE.equals(getClientProperty("hover")) ? hoverPink : mainPink);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 40, 40);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btnThanhToan.setOpaque(false); btnThanhToan.setContentAreaFilled(false); btnThanhToan.setBorderPainted(false);
        btnThanhToan.setForeground(Color.WHITE);
        btnThanhToan.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btnThanhToan.setMaximumSize(new Dimension(Integer.MAX_VALUE, 55));
        btnThanhToan.setPreferredSize(new Dimension(Integer.MAX_VALUE, 55));
        btnThanhToan.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnThanhToan.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnThanhToan.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btnThanhToan.putClientProperty("hover", true); btnThanhToan.repaint(); }
            public void mouseExited(MouseEvent e) { btnThanhToan.putClientProperty("hover", false); btnThanhToan.repaint(); }
        });
        btnThanhToan.addActionListener(e -> {
            if(gioHangBUS.getDanhSach().isEmpty()){
                ThongBao.show(this, "Giỏ hàng của bạn đang trống!"); // SỬ DỤNG CLASS THÔNG BÁO XỊN
                return;
            }
            ClientMainFrame parentFrame = (ClientMainFrame) SwingUtilities.getWindowAncestor(this);
            parentFrame.switchPanel(new PanelThanhToan());
        });
        
        pnlRight.add(lblSummaryTitle);
        pnlRight.add(pnlTotal);
        pnlRight.add(btnThanhToan);
        pnlRight.add(Box.createVerticalGlue()); 
        pnlRightContainer.add(pnlRight, BorderLayout.NORTH);

        mainContent.add(pnlLeft, BorderLayout.CENTER);
        mainContent.add(pnlRightContainer, BorderLayout.EAST);
        add(mainContent, BorderLayout.CENTER);
        
        loadData();
    }

    private void loadData() {
        pnlListItems.removeAll();
        ArrayList<SachDTO> dsCart = gioHangBUS.getDanhSachUnique();
        
        int totalQty = 0;
        for(SachDTO s : dsCart) totalQty += s.getSoLuongTon();
        lblCount.setText(totalQty + " sản phẩm");

        if (dsCart.isEmpty()) {
            renderEmptyCart();
        } else {
            for (SachDTO sach : dsCart) {
                pnlListItems.add(createCartItemCard(sach));
                pnlListItems.add(Box.createVerticalStrut(20)); // Khoảng cách giữa các Card sách
            }
            lblTongTien.setText(df.format(gioHangBUS.tinhTongTien()) + " VND");
        }
        pnlListItems.revalidate();
        pnlListItems.repaint();
    }

    // --- CARD UI CHO TỪNG SẢN PHẨM TRONG GIỎ ---
    // --- CARD UI CHO TỪNG SẢN PHẨM TRONG GIỎ (BẢN FIX LỖI RỚT DÒNG) ---
    private JPanel createCartItemCard(SachDTO sach) {
        JPanel card = new JPanel(new BorderLayout(15, 0)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20); // Bo góc 20px
                g2.setColor(Color.decode("#E0E0E0"));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
                g2.dispose();
            }
        };
        card.setOpaque(false);
        card.setBorder(new EmptyBorder(15, 20, 15, 20));
        
        // Khóa cứng chiều cao Card là 130px, chiều ngang tự do co giãn
        card.setPreferredSize(new Dimension(0, 130));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 130));

        // 1. ẢNH
        JLabel lblImg = new JLabel();
        lblImg.setPreferredSize(new Dimension(75, 100));
        try {
            String tenAnh = sach.getHinhAnh() != null ? sach.getHinhAnh().trim() : "";
            java.net.URL imgURL = getClass().getResource("/images/" + tenAnh);
            if (imgURL != null) {
                ImageIcon icon = new ImageIcon(imgURL);
                Image img = icon.getImage().getScaledInstance(75, 100, Image.SCALE_SMOOTH);
                lblImg.setIcon(new ImageIcon(img));
            }
        } catch (Exception e) {}
        card.add(lblImg, BorderLayout.WEST);

        // 2. THÔNG TIN (Tên + Giá)
        JPanel pnlCenter = new JPanel();
        pnlCenter.setLayout(new BoxLayout(pnlCenter, BoxLayout.Y_AXIS));
        pnlCenter.setOpaque(false);
        pnlCenter.setBorder(new EmptyBorder(15, 0, 0, 0)); // Đẩy chữ xích xuống cho cân giữa ảnh

        JLabel name = new JLabel(sach.getTenSach());
        name.setFont(new Font("Segoe UI", Font.BOLD, 18));
        name.setForeground(Color.decode("#5A4664"));
        
        JLabel price = new JLabel(df.format(sach.getGiaBan()) + " VND");
        price.setFont(new Font("Segoe UI", Font.BOLD, 16));
        price.setForeground(Color.GRAY);
        
        pnlCenter.add(name);
        pnlCenter.add(Box.createVerticalStrut(8)); // Khoảng cách nhỏ giữa tên và giá
        pnlCenter.add(price);
        card.add(pnlCenter, BorderLayout.CENTER);

        // 3. ĐIỀU KHIỂN (Số lượng, Tổng tiền, Xóa) - Dùng GridBagLayout để thẳng hàng tuyệt đối
        // 3. ĐIỀU KHIỂN (Số lượng, Tổng tiền, Xóa) - Dùng GridBagLayout để thẳng hàng tuyệt đối
        JPanel pnlRight = new JPanel(new GridBagLayout());
        pnlRight.setOpaque(false);
        GridBagConstraints gbcCtrl = new GridBagConstraints();
        gbcCtrl.fill = GridBagConstraints.VERTICAL;
        gbcCtrl.insets = new Insets(0, 15, 0, 0); // Khoảng cách đều nhau giữa các món

        // 3.1 Viên thuốc số lượng
        JPanel pnlQty = new JPanel(new GridBagLayout()) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 30, 30);
                g2.setColor(Color.LIGHT_GRAY);
                g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 30, 30);
                g2.dispose();
            }
        };
        pnlQty.setOpaque(false); 
        pnlQty.setPreferredSize(new Dimension(110, 35));
        
        GridBagConstraints gbcQty = new GridBagConstraints();
        gbcQty.fill = GridBagConstraints.BOTH; gbcQty.gridy = 0; gbcQty.weighty = 1.0;

        // ================= XỬ LÝ NÚT TRỪ KÈM THÔNG BÁO =================
        JButton btnMinus = createQtyBtn("-");
        gbcQty.gridx = 0; gbcQty.weightx = 0.3; pnlQty.add(btnMinus, gbcQty);
        btnMinus.addActionListener(e -> { 
            // Nếu số lượng đang là 1 mà bấm trừ -> Hỏi xác nhận xóa
            if (sach.getSoLuongTon() == 1) {
                boolean isConfirmed = ThongBao.showConfirm(this, "Bạn có muốn xóa sản phẩm này ra khỏi giỏ hàng?", "Đồng ý", "Hủy");
                if (isConfirmed) {
                    gioHangBUS.xoaSanPham(sach.getMaSach());
                    loadData();
                }
            } else {
                // Nếu số lượng > 1 thì giảm bình thường
                gioHangBUS.giamSoLuong(sach.getMaSach()); 
                loadData(); 
            }
        });

        JLabel qty = new JLabel(String.valueOf(sach.getSoLuongTon()), SwingConstants.CENTER);
        qty.setFont(new Font("Segoe UI", Font.BOLD, 16));
        gbcQty.gridx = 1; gbcQty.weightx = 0.4; pnlQty.add(qty, gbcQty);

        JButton btnPlus = createQtyBtn("+");
        gbcQty.gridx = 2; gbcQty.weightx = 0.3; pnlQty.add(btnPlus, gbcQty);
        btnPlus.addActionListener(e -> { gioHangBUS.themSoLuong(sach.getMaSach()); loadData(); });

        gbcCtrl.gridx = 0; pnlRight.add(pnlQty, gbcCtrl);

        // 3.2 Tổng tiền món
        JLabel totalItem = new JLabel(df.format(sach.getGiaBan() * sach.getSoLuongTon()) + " VND");
        totalItem.setFont(new Font("Segoe UI", Font.BOLD, 18));
        totalItem.setForeground(mainPink);
        totalItem.setHorizontalAlignment(SwingConstants.RIGHT);
        totalItem.setPreferredSize(new Dimension(130, 35)); 
        
        gbcCtrl.gridx = 1; pnlRight.add(totalItem, gbcCtrl);

        // ================= XỬ LÝ NÚT XÓA KÈM THÔNG BÁO =================
        // 3.3 Nút Xóa (Màu đỏ xịn)
        JButton btnDel = new JButton("Xóa");
        btnDel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnDel.setForeground(Color.decode("#FF4757")); // Màu đỏ san hô hiện đại
        btnDel.setBorderPainted(false); btnDel.setContentAreaFilled(false); btnDel.setFocusPainted(false);
        btnDel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDel.addActionListener(e -> { 
            boolean isConfirmed = ThongBao.showConfirm(this, "Bạn có muốn xóa sản phẩm này ra khỏi giỏ hàng?", "Đồng ý", "Hủy");
            if (isConfirmed) {
                gioHangBUS.xoaSanPham(sach.getMaSach()); 
                loadData(); 
            }
        });
        
        gbcCtrl.gridx = 2; pnlRight.add(btnDel, gbcCtrl);

        card.add(pnlRight, BorderLayout.EAST);

        return card;
    }

    private JButton createQtyBtn(String text) {
        JButton b = new JButton(text);
        b.setFont(new Font("Segoe UI", Font.BOLD, 18));
        b.setMargin(new Insets(0, 0, 0, 0)); b.setBorder(null);
        b.setContentAreaFilled(false); b.setFocusPainted(false); b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return b;
    }

    // --- GIAO DIỆN KHI GIỎ HÀNG TRỐNG ---
    private void renderEmptyCart() {
        pnlListItems.removeAll();
        pnlListItems.setLayout(new GridBagLayout()); 
        pnlListItems.setOpaque(false);

        JPanel pnlEmpty = new JPanel();
        pnlEmpty.setLayout(new BoxLayout(pnlEmpty, BoxLayout.Y_AXIS));
        pnlEmpty.setOpaque(false);

        JLabel lblMsg = new JLabel("Giỏ hàng của bạn đang trống!");
        lblMsg.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblMsg.setForeground(Color.GRAY);
        lblMsg.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnGoHome = new JButton("TIẾP TỤC MUA SẮM") {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Boolean.TRUE.equals(getClientProperty("hover")) ? hoverPink : mainPink);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 40, 40);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btnGoHome.setOpaque(false); btnGoHome.setContentAreaFilled(false); btnGoHome.setBorderPainted(false);
        btnGoHome.setForeground(Color.WHITE);
        btnGoHome.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnGoHome.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnGoHome.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnGoHome.setMaximumSize(new Dimension(250, 50));
        btnGoHome.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btnGoHome.putClientProperty("hover", true); btnGoHome.repaint(); }
            public void mouseExited(MouseEvent e) { btnGoHome.putClientProperty("hover", false); btnGoHome.repaint(); }
        });
        btnGoHome.addActionListener(e -> {
            ClientMainFrame parentFrame = (ClientMainFrame) SwingUtilities.getWindowAncestor(this);
            parentFrame.switchPanel(new PanelTrangChu(parentFrame));
        });

        pnlEmpty.add(lblMsg);
        pnlEmpty.add(Box.createVerticalStrut(30));
        pnlEmpty.add(btnGoHome);
        pnlListItems.add(pnlEmpty);
        
        if (lblTongTien != null) lblTongTien.setText("0 VND");
    }
}