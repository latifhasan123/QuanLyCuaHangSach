package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ThongBao {
    
    // Hàm static để gọi trực tiếp ở mọi nơi giống như JOptionPane
    public static void show(Component parent, String message) {
        JDialog dialog = new JDialog();
        dialog.setUndecorated(true); // Tắt thanh tiêu đề mặc định của Windows
        dialog.setBackground(new Color(0, 0, 0, 0)); // Làm nền trong suốt để vẽ bo tròn
        dialog.setModal(true); // Khóa màn hình nền khi pop-up hiện lên

        // --- KHUNG POP-UP BO GÓC ---
        JPanel content = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // NỀN TRẮNG TINH KHIẾT (Sang trọng, sạch sẽ, không phèn)
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);
                
                // VIỀN MÀU HỒNG ĐẬM (Dày 2.5px cho nổi bật trên nền trắng)
                g2.setColor(Color.decode("#E889A9"));
                g2.setStroke(new BasicStroke(2.5f));
                g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 30, 30); 
                
                g2.dispose();
            }
        };
        content.setOpaque(false);
        content.setLayout(new BorderLayout(0, 20));
        content.setBorder(BorderFactory.createEmptyBorder(30, 30, 20, 30));

        // --- NỘI DUNG THÔNG BÁO ---
        JLabel lblMsg = new JLabel("<html><div style='text-align: center;'>" + message + "</div></html>", SwingConstants.CENTER);
        lblMsg.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblMsg.setForeground(Color.decode("#5A4664"));
        
        // --- NÚT ĐỒNG Ý MÀU HỒNG MẬP MẠP ---
        JButton btnOk = new JButton("ĐỒNG Ý") {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Boolean.TRUE.equals(getClientProperty("hover")) ? Color.decode("#D67897") : Color.decode("#E889A9"));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 35, 35);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btnOk.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnOk.setForeground(Color.WHITE);
        btnOk.setFocusPainted(false); btnOk.setContentAreaFilled(false); btnOk.setBorderPainted(false);
        btnOk.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnOk.setPreferredSize(new Dimension(120, 40));
        btnOk.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btnOk.putClientProperty("hover", true); btnOk.repaint(); }
            public void mouseExited(MouseEvent e) { btnOk.putClientProperty("hover", false); btnOk.repaint(); }
        });
        btnOk.addActionListener(e -> dialog.dispose()); // Tắt pop-up khi bấm

        JPanel pnlBtn = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        pnlBtn.setOpaque(false);
        pnlBtn.add(btnOk);

        content.add(lblMsg, BorderLayout.CENTER);
        content.add(pnlBtn, BorderLayout.SOUTH);

        dialog.add(content);
        dialog.setSize(350, 180);
        
        // Căn giữa màn hình hiện tại
        Window window = SwingUtilities.getWindowAncestor(parent);
        dialog.setLocationRelativeTo(window != null ? window : null);
        dialog.setVisible(true);
    }
    // ================= POPUP ĐẶT HÀNG THÀNH CÔNG (2 NÚT) =================
    public static void showDatHangThanhCong(Component parent, int maDH, int diemC) {
        Window window = SwingUtilities.getWindowAncestor(parent);
        JDialog dialog = new JDialog((Frame) window, true);
        dialog.setUndecorated(true);
        dialog.setBackground(new Color(0, 0, 0, 0));

        Color mainPink = Color.decode("#E889A9");
        Color hoverPink = Color.decode("#D67897");
        Color darkPurple = Color.decode("#5A4664");

        JPanel pnlBg = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);
                g2.setColor(mainPink);
                g2.setStroke(new BasicStroke(2.5f));
                g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 30, 30);
                super.paintComponent(g);
            }
        };
        pnlBg.setOpaque(false);
        pnlBg.setBorder(BorderFactory.createEmptyBorder(40, 40, 30, 40));

        JPanel pnlText = new JPanel();
        pnlText.setLayout(new BoxLayout(pnlText, BoxLayout.Y_AXIS));
        pnlText.setOpaque(false);
        
        JLabel title = new JLabel("ĐẶT HÀNG THÀNH CÔNG!");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(Color.decode("#34C759")); 
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel msg = new JLabel("Bạn được cộng " + diemC + " điểm tích lũy");
        msg.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        msg.setForeground(Color.GRAY);
        msg.setAlignmentX(Component.CENTER_ALIGNMENT);

        pnlText.add(title); pnlText.add(Box.createVerticalStrut(10));
        pnlText.add(msg); pnlText.add(Box.createVerticalStrut(35)); 

        JPanel pnlBtn = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        pnlBtn.setOpaque(false);

        JButton btnHome = new JButton("Tiếp tục mua sắm") {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Boolean.TRUE.equals(getClientProperty("hover")) ? Color.decode("#E0E0E0") : Color.decode("#F0F0F0"));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btnHome.setPreferredSize(new Dimension(160, 40));
        btnHome.setForeground(darkPurple);
        btnHome.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnHome.setContentAreaFilled(false); btnHome.setBorderPainted(false); btnHome.setFocusPainted(false);
        btnHome.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnHome.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btnHome.putClientProperty("hover", true); btnHome.repaint(); }
            public void mouseExited(MouseEvent e) { btnHome.putClientProperty("hover", false); btnHome.repaint(); }
        });
        btnHome.addActionListener(e -> {
            dialog.dispose();
            ((ClientMainFrame) window).switchPanel(new PanelTrangChu((ClientMainFrame) window));
        });

        JButton btnDetail = new JButton("Xem đơn hàng") {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Boolean.TRUE.equals(getClientProperty("hover")) ? hoverPink : mainPink);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btnDetail.setPreferredSize(new Dimension(160, 40));
        btnDetail.setForeground(Color.WHITE);
        btnDetail.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnDetail.setContentAreaFilled(false); btnDetail.setBorderPainted(false); btnDetail.setFocusPainted(false);
        btnDetail.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDetail.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btnDetail.putClientProperty("hover", true); btnDetail.repaint(); }
            public void mouseExited(MouseEvent e) { btnDetail.putClientProperty("hover", false); btnDetail.repaint(); }
        });
        btnDetail.addActionListener(e -> {
            dialog.dispose();
            ((ClientMainFrame) window).switchPanel(new PanelChiTietDonHang(maDH,false));
        });

        pnlBtn.add(btnHome); pnlBtn.add(btnDetail);
        pnlBg.add(pnlText, BorderLayout.CENTER);
        pnlBg.add(pnlBtn, BorderLayout.SOUTH);

        dialog.add(pnlBg); dialog.pack();
        dialog.setLocationRelativeTo(window);
        dialog.setVisible(true);
    }
    // ================= POPUP XÁC NHẬN (CÓ/KHÔNG) =================
    public static boolean showConfirm(Component parent, String message, String yesText, String noText) {
        Window window = SwingUtilities.getWindowAncestor(parent);
        JDialog dialog = new JDialog((Frame) window, true);
        dialog.setUndecorated(true);
        dialog.setBackground(new Color(0, 0, 0, 0));

        final boolean[] result = {false};

        JPanel content = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);
                
                // Viền màu đỏ san hô cảnh báo hủy
                g2.setColor(Color.decode("#FF4757")); 
                g2.setStroke(new BasicStroke(2.5f));
                g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 30, 30);
                g2.dispose();
            }
        };
        content.setOpaque(false);
        content.setLayout(new BorderLayout(0, 20));
        content.setBorder(BorderFactory.createEmptyBorder(30, 30, 20, 30));

        JLabel lblMsg = new JLabel("<html><div style='text-align: center; width: 250px;'>" + message + "</div></html>", SwingConstants.CENTER);
        lblMsg.setFont(new Font("Segoe UI", Font.BOLD, 17));
        lblMsg.setForeground(Color.decode("#5A4664"));

        JPanel pnlBtn = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        pnlBtn.setOpaque(false);

        // NÚT KHÔNG (Hủy thao tác)
        JButton btnNo = new JButton(noText) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Boolean.TRUE.equals(getClientProperty("hover")) ? Color.decode("#E0E0E0") : Color.decode("#F0F0F0"));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
                g2.dispose(); super.paintComponent(g);
            }
        };
        btnNo.setPreferredSize(new Dimension(110, 40));
        btnNo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnNo.setForeground(Color.decode("#5A4664"));
        btnNo.setContentAreaFilled(false); btnNo.setBorderPainted(false); btnNo.setFocusPainted(false);
        btnNo.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnNo.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btnNo.putClientProperty("hover", true); btnNo.repaint(); }
            public void mouseExited(MouseEvent e) { btnNo.putClientProperty("hover", false); btnNo.repaint(); }
        });
        btnNo.addActionListener(e -> { result[0] = false; dialog.dispose(); });

        // NÚT CÓ (Xác nhận đỏ)
        JButton btnYes = new JButton(yesText) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Boolean.TRUE.equals(getClientProperty("hover")) ? Color.decode("#FF6B7B") : Color.decode("#FF4757"));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
                g2.dispose(); super.paintComponent(g);
            }
        };
        btnYes.setPreferredSize(new Dimension(110, 40));
        btnYes.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnYes.setForeground(Color.WHITE);
        btnYes.setContentAreaFilled(false); btnYes.setBorderPainted(false); btnYes.setFocusPainted(false);
        btnYes.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnYes.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btnYes.putClientProperty("hover", true); btnYes.repaint(); }
            public void mouseExited(MouseEvent e) { btnYes.putClientProperty("hover", false); btnYes.repaint(); }
        });
        btnYes.addActionListener(e -> { result[0] = true; dialog.dispose(); });

        pnlBtn.add(btnNo); pnlBtn.add(btnYes);

        content.add(lblMsg, BorderLayout.CENTER);
        content.add(pnlBtn, BorderLayout.SOUTH);

        dialog.add(content);
        dialog.pack();
        dialog.setLocationRelativeTo(window);
        dialog.setVisible(true);

        return result[0]; // Trả về true nếu chọn CÓ, false nếu chọn KHÔNG
    }
}