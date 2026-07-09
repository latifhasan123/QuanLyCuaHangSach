package gui;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import Utils.JDBCConnection;
import BUS.DonHangBUS;

public class PanelChiTietDonHang extends JPanel {
    private DecimalFormat df = new DecimalFormat("#,###");
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    private int maDH;
    private boolean isFromHistory;
    
    // Bảng màu chuẩn
    private Color darkPurple = Color.decode("#5A4664");
    private Color mainPink = Color.decode("#E889A9");
    private Color hoverPink = Color.decode("#D67897");
    private Color dangerRed = Color.decode("#FF4757");
    private Color hoverRed = Color.decode("#FF6B7B");

    public PanelChiTietDonHang(int maDH, boolean isFromHistory) {
        this.maDH = maDH;
        this.isFromHistory = isFromHistory;
        setLayout(new BorderLayout(0, 20));
        setBackground(Color.decode("#F5F5F5")); // Nền xám nhạt đồng bộ
        setBorder(new EmptyBorder(30, 70, 40, 70)); // Căn lề chuẩn như trang Thanh Toán

        // ================= TIÊU ĐỀ TRANG =================
        JLabel lblTitle = new JLabel("Chi tiết đơn hàng #" + maDH, SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblTitle.setForeground(darkPurple);
        lblTitle.setBorder(new EmptyBorder(0, 0, 10, 0));
        add(lblTitle, BorderLayout.NORTH);

        // ================= KHUNG CHIA 2 CỘT =================
        JPanel pnlMain = new JPanel(new BorderLayout(30, 0));
        pnlMain.setOpaque(false);

        // ================= KHU VỰC NÚT BẤM DƯỚI CÙNG =================
        JPanel pnlBottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
        pnlBottom.setOpaque(false);
        pnlBottom.setBorder(new EmptyBorder(10, 0, 0, 0));

        loadOrderDetails(pnlMain, pnlBottom);

        add(pnlMain, BorderLayout.CENTER);
        add(pnlBottom, BorderLayout.SOUTH);
    }

    private void loadOrderDetails(JPanel pnlMain, JPanel pnlBottom) {
        try (Connection con = JDBCConnection.getConnection()) {
            String sqlDH = "SELECT * FROM DonHang WHERE MaID = ?";
            PreparedStatement psDH = con.prepareStatement(sqlDH);
            psDH.setInt(1, maDH);
            ResultSet rsDH = psDH.executeQuery();
            
            if (rsDH.next()) {
                String ngayTao = rsDH.getTimestamp("NgayTao") != null ? sdf.format(rsDH.getTimestamp("NgayTao")) : "";
                String rawStatus = rsDH.getString("TrangThaiDon"); 
                String trangThai = formatTrangThai(rawStatus);

                // ================= CỘT TRÁI: CARD UI =================
                JPanel pnlLeft = new JPanel() {
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
                pnlLeft.setLayout(new BoxLayout(pnlLeft, BoxLayout.Y_AXIS));
                pnlLeft.setOpaque(false);
                pnlLeft.setBorder(new EmptyBorder(30, 40, 30, 40));
                pnlLeft.setPreferredSize(new Dimension(420, 0)); 

                pnlLeft.add(createLabel("1. THÔNG TIN ĐƠN HÀNG", 20, darkPurple, true));
                pnlLeft.add(Box.createVerticalStrut(20));
                pnlLeft.add(createLabel("Ngày đặt: " + ngayTao, 16, Color.DARK_GRAY, false));
                pnlLeft.add(Box.createVerticalStrut(10));
                
                JLabel lblStatus = new JLabel("Trạng thái: " + trangThai);
                lblStatus.setFont(new Font("Segoe UI", Font.BOLD, 18));
                if (rawStatus.equals("HoanThanh")) lblStatus.setForeground(Color.decode("#34C759"));
                else if (rawStatus.equals("DaHuy")) lblStatus.setForeground(dangerRed);
                else lblStatus.setForeground(Color.decode("#FF9500"));
                lblStatus.setAlignmentX(Component.LEFT_ALIGNMENT);
                pnlLeft.add(lblStatus);

                pnlLeft.add(Box.createVerticalStrut(30));
                JSeparator sepLeft = new JSeparator();
                sepLeft.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
                sepLeft.setForeground(Color.decode("#E0E0E0"));
                pnlLeft.add(sepLeft);
                pnlLeft.add(Box.createVerticalStrut(30));

                pnlLeft.add(createLabel("2. THÔNG TIN NHẬN HÀNG", 20, darkPurple, true));
                pnlLeft.add(Box.createVerticalStrut(20));
                pnlLeft.add(createLabel(rsDH.getString("tenNguoiNhan"), 18, Color.BLACK, true));
                pnlLeft.add(Box.createVerticalStrut(10));
                pnlLeft.add(createLabel("Điện thoại: " + rsDH.getString("sdtNhan"), 16, Color.DARK_GRAY, false));
                pnlLeft.add(Box.createVerticalStrut(10));
                pnlLeft.add(createLabel("<html><div style='width:300px; line-height:1.5;'>Địa chỉ: " + rsDH.getString("DiaChiGiao") + "</div></html>", 16, Color.DARK_GRAY, false));

                pnlLeft.add(Box.createVerticalGlue()); 
                pnlMain.add(pnlLeft, BorderLayout.WEST);

                // ================= CỘT PHẢI: CARD UI =================
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
                pnlRight.setBorder(new EmptyBorder(30, 40, 30, 40));

                JLabel lblItemsTitle = new JLabel("3. SẢN PHẨM ĐÃ MUA");
                lblItemsTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
                lblItemsTitle.setForeground(darkPurple);
                lblItemsTitle.setBorder(new EmptyBorder(0, 0, 20, 0));
                pnlRight.add(lblItemsTitle, BorderLayout.NORTH);

                JPanel pnlItems = new JPanel();
                pnlItems.setLayout(new BoxLayout(pnlItems, BoxLayout.Y_AXIS));
                pnlItems.setBackground(Color.WHITE);

                String sqlCT = "SELECT s.TenSach, s.HinhAnh, ct.SoLuong, ct.DonGia FROM ChiTietDonHang ct JOIN Sach s ON ct.MaSach = s.MaID WHERE ct.MaDH = ?";
                PreparedStatement psCT = con.prepareStatement(sqlCT);
                psCT.setInt(1, maDH);
                ResultSet rsCT = psCT.executeQuery();

                while (rsCT.next()) {
                    JPanel rowItem = new JPanel(new BorderLayout(20, 0));
                    rowItem.setBackground(Color.WHITE);
                    rowItem.setAlignmentX(Component.LEFT_ALIGNMENT);
                    rowItem.setBorder(new EmptyBorder(10, 0, 10, 0));
                    
                    JLabel lblImg = new JLabel();
                    lblImg.setPreferredSize(new Dimension(65, 90));
                    try {
                        String tenAnh = rsCT.getString("HinhAnh") != null ? rsCT.getString("HinhAnh").trim() : "";
                        java.net.URL imgURL = getClass().getResource("/images/" + tenAnh);
                        if (imgURL != null) {
                            ImageIcon icon = new ImageIcon(imgURL);
                            Image img = icon.getImage().getScaledInstance(65, 90, Image.SCALE_SMOOTH);
                            lblImg.setIcon(new ImageIcon(img));
                        }
                    } catch (Exception e) {}
                    
                    JPanel pnlNameQty = new JPanel();
                    pnlNameQty.setLayout(new BoxLayout(pnlNameQty, BoxLayout.Y_AXIS));
                    pnlNameQty.setOpaque(false);
                    pnlNameQty.setBorder(new EmptyBorder(10, 0, 0, 0));
                    
                    JLabel lblName = new JLabel("<html><div style='width:250px;'>" + rsCT.getString("TenSach") + "</div></html>");
                    lblName.setFont(new Font("Segoe UI", Font.BOLD, 16));
                    lblName.setForeground(Color.DARK_GRAY);
                    
                    JLabel lblQty = new JLabel("Số lượng: " + rsCT.getInt("SoLuong"));
                    lblQty.setFont(new Font("Segoe UI", Font.BOLD, 14));
                    lblQty.setForeground(Color.GRAY);
                    
                    pnlNameQty.add(lblName);
                    pnlNameQty.add(Box.createVerticalStrut(8));
                    pnlNameQty.add(lblQty);
                    
                    double thanhTienItem = rsCT.getInt("SoLuong") * rsCT.getDouble("DonGia");
                    JLabel lblPrice = new JLabel(df.format(thanhTienItem) + "đ");
                    lblPrice.setFont(new Font("Segoe UI", Font.BOLD, 18));
                    lblPrice.setForeground(darkPurple);
                    
                    rowItem.add(lblImg, BorderLayout.WEST);
                    rowItem.add(pnlNameQty, BorderLayout.CENTER);
                    rowItem.add(lblPrice, BorderLayout.EAST);
                    
                    pnlItems.add(rowItem);
                    
                    JSeparator sepItem = new JSeparator();
                    sepItem.setForeground(Color.decode("#F0F0F0"));
                    pnlItems.add(sepItem);
                }
                
                // Giấu và tăng tốc thanh cuộn
                JScrollPane scrollItems = new JScrollPane(pnlItems);
                scrollItems.setBorder(null);
                scrollItems.getViewport().setBackground(Color.WHITE);
                scrollItems.getVerticalScrollBar().setUnitIncrement(20);
                scrollItems.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                scrollItems.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                scrollItems.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
                pnlRight.add(scrollItems, BorderLayout.CENTER);

                JPanel pnlCardBottom = new JPanel();
                pnlCardBottom.setLayout(new BoxLayout(pnlCardBottom, BoxLayout.Y_AXIS));
                pnlCardBottom.setBackground(Color.WHITE);

                pnlCardBottom.add(Box.createVerticalStrut(15));
                JSeparator sep2 = new JSeparator();
                sep2.setForeground(Color.decode("#E0E0E0"));
                pnlCardBottom.add(sep2);
                pnlCardBottom.add(Box.createVerticalStrut(20));
                
                JPanel pnlTotal = new JPanel(new BorderLayout());
                pnlTotal.setOpaque(false);
                pnlTotal.setAlignmentX(Component.LEFT_ALIGNMENT);
                
                JLabel lblTotalText = new JLabel("Tổng thanh toán:");
                lblTotalText.setFont(new Font("Segoe UI", Font.BOLD, 22));
                lblTotalText.setForeground(Color.GRAY);
                
                JLabel lblTotalVal = new JLabel(df.format(rsDH.getDouble("ThanhTien")) + "đ");
                lblTotalVal.setFont(new Font("Segoe UI", Font.BOLD, 28));
                lblTotalVal.setForeground(mainPink); 
                
                pnlTotal.add(lblTotalText, BorderLayout.WEST);
                pnlTotal.add(lblTotalVal, BorderLayout.EAST);
                
                pnlCardBottom.add(pnlTotal);
                pnlRight.add(pnlCardBottom, BorderLayout.SOUTH);
                pnlMain.add(pnlRight, BorderLayout.CENTER);

                // ================= NÚT BẤM CÓ HOVER =================
                if (isFromHistory) {
                    JButton btnBackHistory = new JButton("QUAY LẠI LỊCH SỬ") {
                        @Override protected void paintComponent(Graphics g) {
                            Graphics2D g2 = (Graphics2D) g.create();
                            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                            g2.setColor(Boolean.TRUE.equals(getClientProperty("hover")) ? hoverPink : mainPink);
                            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 35, 35); 
                            g2.dispose(); super.paintComponent(g);
                        }
                    };
                    btnBackHistory.setPreferredSize(new Dimension(280, 50));
                    btnBackHistory.setContentAreaFilled(false); btnBackHistory.setBorderPainted(false); btnBackHistory.setFocusPainted(false);
                    btnBackHistory.setForeground(Color.WHITE); btnBackHistory.setFont(new Font("Segoe UI", Font.BOLD, 18));
                    btnBackHistory.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    btnBackHistory.addMouseListener(new MouseAdapter() {
                        public void mouseEntered(MouseEvent e) { btnBackHistory.putClientProperty("hover", true); btnBackHistory.repaint(); }
                        public void mouseExited(MouseEvent e) { btnBackHistory.putClientProperty("hover", false); btnBackHistory.repaint(); }
                    });
                    btnBackHistory.addActionListener(e -> {
                        ClientMainFrame parent = (ClientMainFrame) SwingUtilities.getWindowAncestor(this);
                        parent.switchPanel(new PanelLichSuMuaHang()); 
                    });
                    pnlBottom.add(btnBackHistory);
                } else {
                    JButton btnContinue = new JButton("TIẾP TỤC MUA SẮM") {
                        @Override protected void paintComponent(Graphics g) {
                            Graphics2D g2 = (Graphics2D) g.create();
                            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                            g2.setColor(Boolean.TRUE.equals(getClientProperty("hover")) ? hoverPink : mainPink);
                            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 35, 35); 
                            g2.dispose(); super.paintComponent(g);
                        }
                    };
                    btnContinue.setPreferredSize(new Dimension(280, 50));
                    btnContinue.setContentAreaFilled(false); btnContinue.setBorderPainted(false); btnContinue.setFocusPainted(false);
                    btnContinue.setForeground(Color.WHITE); btnContinue.setFont(new Font("Segoe UI", Font.BOLD, 18));
                    btnContinue.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    btnContinue.addMouseListener(new MouseAdapter() {
                        public void mouseEntered(MouseEvent e) { btnContinue.putClientProperty("hover", true); btnContinue.repaint(); }
                        public void mouseExited(MouseEvent e) { btnContinue.putClientProperty("hover", false); btnContinue.repaint(); }
                    });
                    btnContinue.addActionListener(e -> {
                        ClientMainFrame parent = (ClientMainFrame) SwingUtilities.getWindowAncestor(this);
                        parent.switchPanel(new PanelTrangChu(parent));
                    });
                    pnlBottom.add(btnContinue);
                }

                if (rawStatus.equals("ChoXuLy")) {
                    JButton btnCancel = new JButton("HỦY ĐƠN HÀNG") {
                        @Override protected void paintComponent(Graphics g) {
                            Graphics2D g2 = (Graphics2D) g.create();
                            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                            g2.setColor(Boolean.TRUE.equals(getClientProperty("hover")) ? hoverRed : dangerRed);
                            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 35, 35); 
                            g2.dispose(); super.paintComponent(g);
                        }
                    };
                    btnCancel.setPreferredSize(new Dimension(280, 50));
                    btnCancel.setContentAreaFilled(false); btnCancel.setBorderPainted(false); btnCancel.setFocusPainted(false);
                    btnCancel.setForeground(Color.WHITE); btnCancel.setFont(new Font("Segoe UI", Font.BOLD, 18));
                    btnCancel.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    btnCancel.addMouseListener(new MouseAdapter() {
                        public void mouseEntered(MouseEvent e) { btnCancel.putClientProperty("hover", true); btnCancel.repaint(); }
                        public void mouseExited(MouseEvent e) { btnCancel.putClientProperty("hover", false); btnCancel.repaint(); }
                    });
                    
                    btnCancel.addActionListener(e -> {
                        // SỬ DỤNG BẢNG THÔNG BÁO XỊN XÒ VỪA TẠO
                        boolean isConfirmed = ThongBao.showConfirm(this, "Bạn có chắc chắn muốn hủy đơn hàng này không?", "Đồng ý", "Không");
                        
                        if (isConfirmed) {
                            if (new DonHangBUS().huyDonHang(maDH)) {
                                ThongBao.show(this, "Đã hủy đơn hàng thành công!");
                                ClientMainFrame parent = (ClientMainFrame) SwingUtilities.getWindowAncestor(this);
                                parent.switchPanel(new PanelChiTietDonHang(maDH, isFromHistory));
                            } else {
                                ThongBao.show(this, "Có lỗi xảy ra khi hủy đơn!"); 
                            }
                        }
                    });
                    pnlBottom.add(btnCancel);
                }
                
            }
        } catch (Exception e) {
            e.printStackTrace();
            pnlMain.add(new JLabel("Lỗi tải dữ liệu đơn hàng!", SwingConstants.CENTER), BorderLayout.CENTER);
        }
    }

    private JLabel createLabel(String text, int size, Color color, boolean isBold) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", isBold ? Font.BOLD : Font.PLAIN, size));
        lbl.setForeground(color);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        return lbl;
    }

    private String formatTrangThai(String raw) {
        if (raw == null) return "Không rõ";
        switch (raw) {
            case "ChoXuLy": return "Đang chờ xử lý";
            case "DaXacNhan": return "Đang giao hàng";
            case "HoanThanh": return "Đã giao thành công";
            case "DaHuy": return "Đã hủy";
            default: return raw;
        }
    }
}