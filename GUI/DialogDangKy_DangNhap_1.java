package gui;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import BUS.KhachHangBUS;
import DTO.KhachHangDTO;

public class DialogDangKy_DangNhap_1 extends JDialog {
    private Color mainPink = Color.decode("#E889A9");
    private Color lightPink = Color.decode("#FFF0F5");
    private Color darkPurple = Color.decode("#5A4664");
    private JFrame parent;
    
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private JButton btnLoginTab;
    private JButton btnRegTab;
    private JTextField txtUserLogin;

    public DialogDangKy_DangNhap_1(JFrame parent) {
        super(parent, "Tài khoản khách hàng", true);
        this.parent = parent;
        setSize(450, 500);
        setLocationRelativeTo(parent);
        
        setUndecorated(true); 
        setBackground(new Color(0, 0, 0, 0));
        
        JPanel pnlMain = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(lightPink); 
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30); 
                g2.setColor(Color.decode("#E889A9"));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);
                g2.dispose();
            }
        };
        pnlMain.setOpaque(false);
        pnlMain.setBorder(new EmptyBorder(5, 5, 5, 5));

        JPanel pnlTop = new JPanel(new BorderLayout());
        pnlTop.setOpaque(false);

        JButton btnClose = new JButton("X");
        btnClose.setFont(new Font("Segoe UI", Font.BOLD, 20));
        btnClose.setForeground(darkPurple);
        btnClose.setContentAreaFilled(false);
        btnClose.setBorderPainted(false);
        btnClose.setFocusPainted(false);
        btnClose.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnClose.setMargin(new Insets(0, 0, 0, 0)); 
        btnClose.setPreferredSize(new Dimension(60, 40)); 
        btnClose.addActionListener(e -> dispose());
        btnClose.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) { btnClose.setForeground(mainPink); }
            @Override
            public void mouseExited(MouseEvent e) { btnClose.setForeground(darkPurple); }
        });
        
        JPanel pnlClose = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        pnlClose.setOpaque(false);
        pnlClose.add(btnClose);
        pnlTop.add(pnlClose, BorderLayout.NORTH);

        JPanel pnlTabs = new JPanel(new GridLayout(1, 2));
        pnlTabs.setOpaque(false);
        pnlTabs.setBorder(new EmptyBorder(0, 20, 0, 20));

        btnLoginTab = createTabButton("ĐĂNG NHẬP", true);
        btnRegTab = createTabButton("ĐĂNG KÝ", false);

        btnLoginTab.addActionListener(e -> switchTab(true));
        btnRegTab.addActionListener(e -> switchTab(false));

        pnlTabs.add(btnLoginTab);
        pnlTabs.add(btnRegTab);
        pnlTop.add(pnlTabs, BorderLayout.CENTER);

        pnlMain.add(pnlTop, BorderLayout.NORTH);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setOpaque(false);

        cardPanel.add(createLoginPanel(), "LOGIN");
        cardPanel.add(createRegisterPanel(), "REGISTER");

        pnlMain.add(cardPanel, BorderLayout.CENTER);
        
        add(pnlMain);
    }

    private JButton createTabButton(String text, boolean isActive) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (getForeground().equals(mainPink)) {
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setColor(mainPink);
                    g2.fillRect(0, getHeight() - 3, getWidth(), 3);
                }
            }
        };
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setForeground(isActive ? mainPink : Color.GRAY);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void switchTab(boolean isLogin) {
        cardLayout.show(cardPanel, isLogin ? "LOGIN" : "REGISTER");
        btnLoginTab.setForeground(isLogin ? mainPink : Color.GRAY);
        btnRegTab.setForeground(!isLogin ? mainPink : Color.GRAY);
        btnLoginTab.repaint();
        btnRegTab.repaint();
    }

    private JTextField createCustomTextField(String hint) {
        JTextField txt = new JTextField() {
            private boolean isFocused = false;
            {
                addFocusListener(new FocusAdapter() {
                    public void focusGained(FocusEvent e) { isFocused = true; repaint(); }
                    public void focusLost(FocusEvent e) { isFocused = false; repaint(); }
                });
            }
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 15, 15);
                g2.dispose();
                super.paintComponent(g);
            }
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (isFocused) {
                    g2.setColor(mainPink);
                    g2.setStroke(new BasicStroke(2f));
                } else {
                    g2.setColor(Color.decode("#CCCCCC"));
                    g2.setStroke(new BasicStroke(1f));
                }
                g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 15, 15);
                
                if (getText().isEmpty() && !isFocused) {
                    g2.setColor(Color.GRAY);
                    g2.setFont(new Font("Segoe UI", Font.ITALIC, 14));
                    FontMetrics fm = g2.getFontMetrics();
                    g2.drawString(hint, 15, (getHeight() + fm.getAscent() - fm.getDescent()) / 2);
                }
                g2.dispose();
            }
        };
        txt.setOpaque(false);
        txt.setBorder(new EmptyBorder(5, 15, 5, 15));
        txt.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        return txt;
    }

    class RoundedPasswordBox extends JPanel {
        private int radius = 15;
        private Color currentBorderColor = Color.decode("#CCCCCC");
        private JPasswordField txtPass;
        private JToggleButton btnEye;
        private String hint;

        public RoundedPasswordBox(String hint) {
            this.hint = hint;
            setLayout(new BorderLayout());
            setOpaque(false);

            txtPass = new JPasswordField();
            txtPass.setOpaque(false);
            txtPass.setFont(new Font("Segoe UI", Font.PLAIN, 15));
            txtPass.setBorder(new EmptyBorder(5, 15, 5, 0)); 
            txtPass.setEchoChar('•');
            
            btnEye = new JToggleButton("👁");
            btnEye.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 18)); 
            btnEye.setForeground(Color.GRAY); 
            btnEye.setBorder(new EmptyBorder(0, 5, 0, 15));
            btnEye.setContentAreaFilled(false); 
            btnEye.setFocusPainted(false); 
            btnEye.setCursor(new Cursor(Cursor.HAND_CURSOR));

            btnEye.addActionListener(e -> {
                if (btnEye.isSelected()) {
                    txtPass.setEchoChar((char) 0); 
                    btnEye.setForeground(mainPink); 
                } else {
                    txtPass.setEchoChar('•'); 
                    btnEye.setForeground(Color.GRAY); 
                }
            });

            FocusAdapter focusHandler = new FocusAdapter() {
                @Override public void focusGained(FocusEvent e) { currentBorderColor = mainPink; repaint(); }
                @Override public void focusLost(FocusEvent e) {
                    if (!txtPass.isFocusOwner() && !btnEye.isFocusOwner()) {
                        currentBorderColor = Color.decode("#CCCCCC"); repaint();
                    }
                }
            };
            txtPass.addFocusListener(focusHandler);
            btnEye.addFocusListener(focusHandler);

            add(txtPass, BorderLayout.CENTER);
            add(btnEye, BorderLayout.EAST);
        }

        public char[] getPassword() { return txtPass.getPassword(); }
        public void addEnterListener(ActionListener a) { txtPass.addActionListener(a); }
        public void requestFocusField() { txtPass.requestFocus(); }
        public void setText(String t) { txtPass.setText(t); }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.WHITE);
            g2.fillRoundRect(1, 1, getWidth() - 3, getHeight() - 3, radius, radius);
            g2.dispose();
            super.paintComponent(g);
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g); 
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setStroke(new BasicStroke(currentBorderColor.equals(mainPink) ? 2f : 1f));
            g2.setColor(currentBorderColor);
            g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, radius, radius);
            
            if (new String(txtPass.getPassword()).isEmpty() && !txtPass.isFocusOwner()) {
                g2.setColor(Color.GRAY);
                g2.setFont(new Font("Segoe UI", Font.ITALIC, 14));
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(hint, 15, (getHeight() + fm.getAscent() - fm.getDescent()) / 2);
            }
            g2.dispose();
        }
    }

    private JButton createActionButton(String text, Color bg, Color fg) {
        JButton btn = new JButton(text) {
            private boolean isHovered = false;
            {
                addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) { isHovered = true; repaint(); }
                    public void mouseExited(MouseEvent e) { isHovered = false; repaint(); }
                });
            }
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (bg.equals(Color.WHITE)) {
                    g2.setColor(isHovered ? Color.decode("#F5F5F5") : bg);
                    g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 25, 25);
                    g2.setColor(Color.decode("#CCCCCC"));
                    g2.setStroke(new BasicStroke(1f));
                    g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 25, 25);
                } else {
                    g2.setColor(isHovered ? bg.darker() : bg);
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
                }
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setForeground(fg);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(null);
        panel.setOpaque(false);

        JLabel lblTitle = new JLabel("Mừng bạn trở lại!", SwingConstants.CENTER);
        lblTitle.setBounds(0, 40, 450, 40);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitle.setForeground(darkPurple);
        panel.add(lblTitle);

        txtUserLogin = createCustomTextField("Email / Số điện thoại");
        txtUserLogin.setBounds(50, 110, 350, 45);
        panel.add(txtUserLogin);

        RoundedPasswordBox txtPass = new RoundedPasswordBox("Mật khẩu");
        txtPass.setBounds(50, 175, 350, 45);
        panel.add(txtPass);

        JButton btnLogin = createActionButton("Đăng nhập", mainPink, Color.WHITE);
        btnLogin.setBounds(50, 255, 350, 45);

        txtUserLogin.addActionListener(e -> txtPass.requestFocusField());
        txtPass.addEnterListener(e -> btnLogin.doClick());

        btnLogin.addActionListener(e -> {
            String user = txtUserLogin.getText().trim();  
            String pass = new String(txtPass.getPassword());

            KhachHangBUS bus = new KhachHangBUS();
            KhachHangDTO kh = bus.dangNhapTraVeDTO(user, pass);

            if (kh != null) {
                KhachHangBUS.currentCustomer = kh;
                ClientMainFrame.currentUser = kh;
                
                new BUS.ClientGioHangBUS().loadGioHangFromDB();
                
                ThongBao.show(this, "Đăng nhập thành công!");
                ((ClientMainFrame) parent).refreshAfterLogin();
                dispose();
            } else {
                ThongBao.show(this, "Sai tài khoản hoặc mật khẩu!");
            }
        });
        panel.add(btnLogin);

        return panel;
    }

    private JPanel createRegisterPanel() {
        JPanel panel = new JPanel(null);
        panel.setOpaque(false);

        JLabel lblTitle = new JLabel("Tạo tài khoản mới", SwingConstants.CENTER);
        lblTitle.setBounds(0, 10, 450, 40);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(darkPurple);
        panel.add(lblTitle);
        
        JTextField txtName = createCustomTextField("Họ và tên");
        txtName.setBounds(50, 70, 350, 45);
        panel.add(txtName);

        JTextField txtEmail = createCustomTextField("Email");
        txtEmail.setBounds(50, 130, 350, 45);
        panel.add(txtEmail);

        JTextField txtSdt = createCustomTextField("Số điện thoại");
        txtSdt.setBounds(50, 190, 350, 45);
        panel.add(txtSdt);

        RoundedPasswordBox txtPassReg = new RoundedPasswordBox("Mật khẩu");
        txtPassReg.setBounds(50, 250, 350, 45);
        panel.add(txtPassReg);

        JButton btnReg = createActionButton("Đăng ký", darkPurple, Color.WHITE);
        btnReg.setBounds(50, 325, 350, 45);
        panel.add(btnReg);

        txtName.addActionListener(e -> txtEmail.requestFocus());
        txtEmail.addActionListener(e -> txtSdt.requestFocus());
        txtSdt.addActionListener(e -> txtPassReg.requestFocusField());
        txtPassReg.addEnterListener(e -> btnReg.doClick());

        btnReg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String hoTen = txtName.getText().trim();
                String email = txtEmail.getText().trim();
                String sdt = txtSdt.getText().trim();
                String pass = new String(txtPassReg.getPassword());

                if (!email.endsWith("@gmail.com")) {
                    ThongBao.show(panel, "Email phải có @gmail.com");
                    return;
                }

                if (sdt.length() != 10 || !sdt.matches("\\d+")) {
                    ThongBao.show(panel, "Số điện thoại phải có 10 chữ số!");
                    return;
                }

                KhachHangBUS bus = new KhachHangBUS();
                boolean result = bus.dangKy(hoTen, email, sdt, pass);

                if (result) {
                    ThongBao.show(panel, "Đăng ký thành công!\nVui lòng đăng nhập để tiếp tục!");
                    txtUserLogin.setText(sdt);
                    btnLoginTab.doClick();
                    txtName.setText("");
                    txtEmail.setText("");
                    txtSdt.setText("");
                    txtPassReg.setText("");
                } else {
                    if (bus.isExists(email, sdt)) {
                        ThongBao.show(panel, "Email hoặc SĐT đã tồn tại!");
                    } else {
                        ThongBao.show(panel, "Đăng ký thất bại! Kiểm tra kết nối DB hoặc thử lại!");
                    }
                }    
            }
        });
        return panel;    
    } 
}