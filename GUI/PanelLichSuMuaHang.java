package gui;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import DTO.DonHangDTO;
import BUS.DonHangBUS;
import BUS.KhachHangBUS;

public class PanelLichSuMuaHang extends JPanel {
    private JPanel pnlList;
    private DecimalFormat df = new DecimalFormat("#,###");
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    private Color mainPink = Color.decode("#E889A9");
    private Color darkPurple = Color.decode("#5A4664");

    public PanelLichSuMuaHang() {
        setLayout(new BorderLayout());
        setBackground(Color.decode("#F5F6FA"));
        setBorder(new EmptyBorder(30, 80, 40, 80));

        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setOpaque(false);
        pnlHeader.setBorder(new EmptyBorder(0, 0, 20, 0));

        JButton btnBack = new JButton("VỀ TRANG CHỦ") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(darkPurple);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                super.paintComponent(g);
            }
        };
        btnBack.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnBack.setForeground(Color.WHITE);
        btnBack.setContentAreaFilled(false);
        btnBack.setBorderPainted(false);
        btnBack.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnBack.setPreferredSize(new Dimension(140, 40));
        
        btnBack.addActionListener(e -> {
            ClientMainFrame parent = (ClientMainFrame) SwingUtilities.getWindowAncestor(this);
            parent.switchPanel(new PanelTrangChu(parent));
        });

        JLabel lblTitle = new JLabel("Lịch sử đơn hàng của bạn", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitle.setForeground(darkPurple);

        JLabel lblSpacer = new JLabel();
        lblSpacer.setPreferredSize(btnBack.getPreferredSize());

        pnlHeader.add(btnBack, BorderLayout.WEST);
        pnlHeader.add(lblTitle, BorderLayout.CENTER);
        pnlHeader.add(lblSpacer, BorderLayout.EAST);

        add(pnlHeader, BorderLayout.NORTH);

        pnlList = new JPanel();
        pnlList.setLayout(new BoxLayout(pnlList, BoxLayout.Y_AXIS));
        pnlList.setBackground(Color.decode("#F5F6FA"));

        JScrollPane scroll = new JScrollPane(pnlList);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        add(scroll, BorderLayout.CENTER);

        loadOrderHistory();
    }

    private void loadOrderHistory() {
        pnlList.removeAll();
        if (KhachHangBUS.currentCustomer == null) return;
        
        DonHangBUS bus = new DonHangBUS();
        ArrayList<DonHangDTO> dsDonHang = bus.getLichSuDonHang(KhachHangBUS.currentCustomer.getMaID());

        if (dsDonHang.isEmpty()) {
            JPanel pnlEmpty = new JPanel(new FlowLayout(FlowLayout.CENTER));
            pnlEmpty.setOpaque(false);
            JLabel lblEmpty = new JLabel("Bạn chưa có đơn hàng nào.");
            lblEmpty.setFont(new Font("Segoe UI", Font.ITALIC, 18));
            lblEmpty.setForeground(Color.GRAY);
            pnlEmpty.add(lblEmpty);
            pnlList.add(pnlEmpty);
        } else {
            for (DonHangDTO dh : dsDonHang) {
                pnlList.add(createOrderCard(dh));
                pnlList.add(Box.createVerticalStrut(20)); 
            }
        }
        pnlList.revalidate();
        pnlList.repaint();
    }

    private JPanel createOrderCard(DonHangDTO dh) {
        JPanel card = new JPanel(new BorderLayout(15, 15)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20); 
                g2.setColor(Color.decode("#DCDDE1"));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20); 
                super.paintComponent(g);
            }
        };
        card.setOpaque(false);
        card.setBorder(new EmptyBorder(20, 30, 20, 30));
        card.setMaximumSize(new Dimension(850, 140));

        JPanel pnlTop = new JPanel(new BorderLayout());
        pnlTop.setOpaque(false);
        
        JLabel lblMaDH = new JLabel("Đơn hàng #" + dh.getMaDH());
        lblMaDH.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblMaDH.setForeground(darkPurple);
        
        JLabel lblTrangThai = new JLabel(formatTrangThai(dh.getTrangThaiDon()));
        lblTrangThai.setFont(new Font("Segoe UI", Font.BOLD, 15));
        
        if(dh.getTrangThaiDon().equals("HoanThanh")) lblTrangThai.setForeground(Color.decode("#34C759")); 
        else if(dh.getTrangThaiDon().equals("DaHuy")) lblTrangThai.setForeground(Color.decode("#FF4757"));
        else lblTrangThai.setForeground(Color.decode("#FF9500")); 
        
        pnlTop.add(lblMaDH, BorderLayout.WEST);
        pnlTop.add(lblTrangThai, BorderLayout.EAST);

        JPanel pnlBottom = new JPanel(new BorderLayout());
        pnlBottom.setOpaque(false);
        pnlBottom.setBorder(new CompoundBorder(
            new MatteBorder(1, 0, 0, 0, Color.decode("#EEEEEE")), 
            new EmptyBorder(15, 0, 0, 0)
        ));

        JPanel pnlInfo = new JPanel(new GridLayout(2, 1, 0, 5));
        pnlInfo.setOpaque(false);
        String ngayStr = dh.getNgayTao() != null ? sdf.format(dh.getNgayTao()) : "N/A";
        JLabel lblNgay = new JLabel("Ngày đặt: " + ngayStr);
        lblNgay.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblNgay.setForeground(Color.GRAY);
        
        JLabel lblTien = new JLabel("Tổng tiền: " + df.format(dh.getThanhTien()) + "đ");
        lblTien.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTien.setForeground(mainPink);
        
        pnlInfo.add(lblNgay);
        pnlInfo.add(lblTien);

        JButton btnDetail = new JButton("XEM CHI TIẾT") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(mainPink); 
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                super.paintComponent(g);
            }
        };
        btnDetail.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnDetail.setForeground(Color.WHITE); 
        btnDetail.setContentAreaFilled(false);
        btnDetail.setBorderPainted(false);
        btnDetail.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDetail.setPreferredSize(new Dimension(140, 40));
        
        btnDetail.addActionListener(e -> {
            try {
                String soMaDH = dh.getMaDH().replaceAll("[^0-9]", ""); 
                int maDonHangInt = Integer.parseInt(soMaDH);
                ClientMainFrame parent = (ClientMainFrame) SwingUtilities.getWindowAncestor(this);
                parent.switchPanel(new PanelChiTietDonHang(maDonHangInt, true));
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi khi mở chi tiết đơn hàng!");
            }
        });

        JPanel pnlBtnWrapper = new JPanel(new GridBagLayout());
        pnlBtnWrapper.setOpaque(false);
        pnlBtnWrapper.add(btnDetail);

        pnlBottom.add(pnlInfo, BorderLayout.WEST);
        pnlBottom.add(pnlBtnWrapper, BorderLayout.EAST);

        card.add(pnlTop, BorderLayout.NORTH);
        card.add(pnlBottom, BorderLayout.CENTER);

        return card;
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