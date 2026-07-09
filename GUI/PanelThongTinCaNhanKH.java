package gui;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import BUS.KhachHangBUS;
import DTO.KhachHangDTO;

public class PanelThongTinCaNhanKH extends JPanel {
    private Color mainPink = Color.decode("#E889A9");
    private Color hoverPink = Color.decode("#D67897");
    private Color darkPurple = Color.decode("#5A4664");
    
    private JTextField txtHoTen, txtSdt, txtEmail, txtDiaChi;
    private JButton btnEdit, btnSave, btnCancel;
    private JPanel pnlButtons;
    private JPanel pnlCard;

    public PanelThongTinCaNhanKH() {
        setLayout(new BorderLayout());
        setBackground(Color.decode("#F5F5F5"));
        setBorder(new EmptyBorder(20, 70, 40, 70));

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

        JPanel pnlCenterWrapper = new JPanel(new GridBagLayout());
        pnlCenterWrapper.setOpaque(false);

        JPanel pnlContainer = new JPanel();
        pnlContainer.setLayout(new BoxLayout(pnlContainer, BoxLayout.Y_AXIS));
        pnlContainer.setOpaque(false);

        pnlCard = new JPanel() {
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
        pnlCard.setLayout(new BoxLayout(pnlCard, BoxLayout.Y_AXIS));
        pnlCard.setOpaque(false);
        pnlCard.setPreferredSize(new Dimension(850, 480));
        pnlCard.setMinimumSize(new Dimension(850, 480));
        pnlCard.setMaximumSize(new Dimension(850, 480));
        pnlCard.setBorder(new EmptyBorder(40, 60, 40, 60));

        KhachHangDTO current = KhachHangBUS.currentCustomer;
        if (current == null) current = new KhachHangDTO();

        JLabel lblTitle = new JLabel("Hồ Sơ Của Bạn");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitle.setForeground(darkPurple);
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        pnlCard.add(lblTitle);
        pnlCard.add(Box.createVerticalStrut(25));

        JSeparator sep = new JSeparator();
        sep.setForeground(Color.decode("#EEEEEE"));
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        pnlCard.add(sep);
        pnlCard.add(Box.createVerticalStrut(30));

        createReadOnlyField(pnlCard, "Mã khách hàng", current.getMaKH());
        txtHoTen = createInputField(pnlCard, "Họ và tên", current.getHoTen());
        txtSdt = createInputField(pnlCard, "Số điện thoại", current.getSoDienThoai());
        txtEmail = createInputField(pnlCard, "Email", current.getEmail());
        txtDiaChi = createInputField(pnlCard, "Địa chỉ giao hàng", current.getDiaChi());
        createReadOnlyField(pnlCard, "Điểm tích lũy", current.getDiemTichLuy() + " điểm");

        pnlContainer.add(pnlCard);
        pnlContainer.add(Box.createVerticalStrut(25)); 

        pnlButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        pnlButtons.setOpaque(false);
        pnlButtons.setMaximumSize(new Dimension(850, 60));

        btnEdit = createButton("SỬA THÔNG TIN", mainPink, Color.WHITE);
        btnSave = createButton("LƯU THAY ĐỔI", mainPink, Color.WHITE);
        btnCancel = createButton("HỦY", Color.decode("#E0E0E0"), darkPurple);

        btnEdit.addActionListener(e -> setEditMode(true));
        btnCancel.addActionListener(e -> setEditMode(false));
        btnSave.addActionListener(e -> saveInformation());

        pnlButtons.add(btnEdit);
        pnlButtons.add(btnCancel);
        pnlButtons.add(btnSave);

        pnlContainer.add(pnlButtons);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 1; gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        pnlCenterWrapper.add(pnlContainer, gbc);

        add(pnlCenterWrapper, BorderLayout.CENTER);
        
        setEditMode(false);
    }

    private void setEditMode(boolean edit) {
        txtHoTen.setEditable(edit);
        txtSdt.setEditable(edit);
        txtEmail.setEditable(edit);
        txtDiaChi.setEditable(edit);

        btnEdit.setVisible(!edit);
        btnSave.setVisible(edit);
        btnCancel.setVisible(edit);

        if (!edit && KhachHangBUS.currentCustomer != null) {
            txtHoTen.setText(KhachHangBUS.currentCustomer.getHoTen());
            txtSdt.setText(KhachHangBUS.currentCustomer.getSoDienThoai());
            txtEmail.setText(KhachHangBUS.currentCustomer.getEmail());
            txtDiaChi.setText(KhachHangBUS.currentCustomer.getDiaChi());
        }

        pnlButtons.revalidate();
        pnlButtons.repaint();
        pnlCard.repaint();
    }

    private void saveInformation() {
        KhachHangDTO updateDTO = new KhachHangDTO();
        updateDTO.setMaID(KhachHangBUS.currentCustomer.getMaID());
        updateDTO.setHoTen(txtHoTen.getText().trim());
        updateDTO.setSoDienThoai(txtSdt.getText().trim());
        updateDTO.setEmail(txtEmail.getText().trim());
        updateDTO.setDiaChi(txtDiaChi.getText().trim());
        
        KhachHangBUS bus = new KhachHangBUS();
        String result = bus.updateKhachHang(updateDTO);
        
        if (result.equals("Cập nhật thông tin thành công!")) {
            KhachHangBUS.currentCustomer.setHoTen(updateDTO.getHoTen());
            KhachHangBUS.currentCustomer.setSoDienThoai(updateDTO.getSoDienThoai());
            KhachHangBUS.currentCustomer.setEmail(updateDTO.getEmail());
            KhachHangBUS.currentCustomer.setDiaChi(updateDTO.getDiaChi());
            ClientMainFrame.currentUser = KhachHangBUS.currentCustomer;
            
            ThongBao.show(this, "Cập nhật hồ sơ thành công!");
            setEditMode(false);
            
            ClientMainFrame parentFrame = (ClientMainFrame) SwingUtilities.getWindowAncestor(this);
            parentFrame.switchPanel(new PanelThongTinCaNhanKH());
        } else {
            ThongBao.show(this, result);
        }
    }

    private JButton createButton(String text, Color bg, Color fg) {
        JButton btn = new JButton(text) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (Boolean.TRUE.equals(getClientProperty("hover"))) {
                    g2.setColor(bg.equals(mainPink) ? hoverPink : Color.decode("#D0D0D0"));
                } else {
                    g2.setColor(bg);
                }
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 35, 35);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btn.setForeground(fg);
        btn.setPreferredSize(new Dimension(200, 45));
        btn.setFocusPainted(false); btn.setContentAreaFilled(false); btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.putClientProperty("hover", true); btn.repaint(); }
            public void mouseExited(MouseEvent e) { btn.putClientProperty("hover", false); btn.repaint(); }
        });
        return btn;
    }

    private void createReadOnlyField(JPanel parent, String labelText, String value) {
        JPanel row = new JPanel(new BorderLayout(20, 0));
        row.setOpaque(false);
        row.setPreferredSize(new Dimension(750, 40));
        row.setMaximumSize(new Dimension(750, 40));
        row.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lbl = new JLabel(labelText);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lbl.setForeground(Color.GRAY);
        lbl.setPreferredSize(new Dimension(220, 40));

        JLabel val = new JLabel(value);
        val.setFont(new Font("Segoe UI", Font.BOLD, 17));
        val.setForeground(darkPurple);
        val.setHorizontalAlignment(SwingConstants.LEFT);

        row.add(lbl, BorderLayout.WEST);
        row.add(val, BorderLayout.CENTER);
        parent.add(row);
        parent.add(Box.createVerticalStrut(15));
    }

    private JTextField createInputField(JPanel parent, String labelText, String value) {
        JPanel row = new JPanel(new BorderLayout(20, 0));
        row.setOpaque(false);
        row.setPreferredSize(new Dimension(750, 45));
        row.setMaximumSize(new Dimension(750, 45));
        row.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lbl = new JLabel(labelText);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lbl.setForeground(Color.DARK_GRAY);
        lbl.setPreferredSize(new Dimension(220, 45));

        JTextField txt = new JTextField(value);
        txt.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txt.setBorder(null); txt.setOpaque(false);
        txt.setForeground(darkPurple);

        JPanel wrapper = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(txt.isEditable() ? Color.WHITE : Color.decode("#F9F9F9"));
                g2.fillRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 15, 15);
                g2.setColor(txt.isEditable() ? mainPink : Color.decode("#E0E0E0"));
                g2.setStroke(new BasicStroke(1.5f));
                g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 15, 15);
                g2.dispose();
            }
        };
        wrapper.setOpaque(false);
        wrapper.setBorder(new EmptyBorder(5, 15, 5, 15));
        wrapper.add(txt, BorderLayout.CENTER);

        row.add(lbl, BorderLayout.WEST);
        row.add(wrapper, BorderLayout.CENTER);
        parent.add(row);
        parent.add(Box.createVerticalStrut(15));
        
        return txt;
    }
}