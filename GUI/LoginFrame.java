package GUI;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class LoginFrame extends JFrame {

    // ==========================================
    // 1. ĐỊNH NGHĨA HẰNG SỐ MÀU 
    // ==========================================
    private final Color COL_PRIMARY = new Color(232, 60, 145);     
    private final Color COL_GRADIENT_1 = new Color(67, 51, 76);    
    private final Color COL_GRADIENT_2 = new Color(45, 35, 50);    
    private final Color COL_BORDER = new Color(200, 180, 200);     
    private final Color COL_BG_CREAM = new Color(248, 244, 236);   
    private final Color COL_TEXT_DARK = new Color(67, 51, 76);     

    private final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 28);
    private final Font FONT_LABEL = new Font("Segoe UI", Font.BOLD, 14);
    private final Font FONT_INPUT = new Font("Segoe UI", Font.PLAIN, 15);
    
    private final Dimension BOX_DIMENSION = new Dimension(300, 42);

    public LoginFrame() {
        setTitle("Đăng nhập hệ thống - Bookstore");
        setSize(850, 500);
        setLocationRelativeTo(null); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(1, 2)); 

        buildLeftPanel();
        buildRightPanel();
    }

    private void buildLeftPanel() {
        JPanel pnlLeft = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, COL_GRADIENT_1, 0, getHeight(), COL_GRADIENT_2);
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        pnlLeft.setLayout(new GridBagLayout()); 
        
        JPanel pnlBrand = new JPanel();
        pnlBrand.setLayout(new BoxLayout(pnlBrand, BoxLayout.Y_AXIS));
        pnlBrand.setOpaque(false);
        
        JLabel lblIcon = new JLabel("📚"); 
        lblIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 60));
        lblIcon.setForeground(new Color(255, 143, 183)); 
        lblIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblBrandName = new JLabel("BOOKSTORE");
        lblBrandName.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblBrandName.setForeground(Color.WHITE);
        lblBrandName.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblSlogan = new JLabel("Hệ thống quản lý nhà sách hiện đại");
        lblSlogan.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        lblSlogan.setForeground(new Color(255, 143, 183)); 
        lblSlogan.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        pnlBrand.add(lblIcon);
        pnlBrand.add(Box.createRigidArea(new Dimension(0, 10)));
        pnlBrand.add(lblBrandName);
        pnlBrand.add(lblSlogan);
        
        pnlLeft.add(pnlBrand); 
        this.add(pnlLeft);
    }

    private void buildRightPanel() {
        JPanel pnlRight = new JPanel(new GridBagLayout());
        pnlRight.setBackground(COL_BG_CREAM); 
        
        JPanel pnlForm = new JPanel();
        pnlForm.setLayout(new BoxLayout(pnlForm, BoxLayout.Y_AXIS));
        pnlForm.setOpaque(false); 
        
        JLabel lblTitle = new JLabel("Chào mừng trở lại!", SwingConstants.CENTER);
        lblTitle.setFont(FONT_TITLE);
        lblTitle.setForeground(COL_TEXT_DARK); 
        lblTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblTitle.setMaximumSize(new Dimension(300, 40));
        
        JLabel lblSubTitle = new JLabel("Vui lòng đăng nhập để tiếp tục", SwingConstants.CENTER);
        lblSubTitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblSubTitle.setForeground(new Color(120, 100, 120)); 
        lblSubTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblSubTitle.setMaximumSize(new Dimension(300, 40));

        JLabel lblUser = createLabelWithIcon(" Tên đăng nhập", "/GUI/icons/user.png");
        lblUser.setForeground(COL_TEXT_DARK);
        RoundedTextField txtUser = new RoundedTextField(20); 
        
        JLabel lblPass = createLabelWithIcon(" Mật khẩu", "/GUI/icons/lock.png");
        lblPass.setForeground(COL_TEXT_DARK);
        RoundedPasswordBox pnlPassBox = new RoundedPasswordBox(20); 
        
        RoundedButton btnLogin = new RoundedButton("ĐĂNG NHẬP", 40); 

        ActionListener actionLogin = e -> {
            String user = txtUser.getText();
            String pass = new String(pnlPassBox.getPassword());
            
            // 1. KẾT NỐI DATABASE VÀ TÌM TÀI KHOẢN
            BUS.TaiKhoanNhanVienBUS tkBus = new BUS.TaiKhoanNhanVienBUS();
            DTO.TaiKhoanNhanVienDTO taiKhoanHopLe = null;
            
            for (DTO.TaiKhoanNhanVienDTO tk : tkBus.getDanhSach()) {
                if (tk.getTenDangNhap().equals(user) && tk.getMatKhau().equals(pass)) {
                    if (tk.getTrangThai().equals("Khoa")) {
                        JOptionPane.showMessageDialog(this, "Tài khoản này đã bị khóa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                        return; 
                    }
                    taiKhoanHopLe = tk;
                    break;
                }
            }
            
            // 2. XỬ LÝ KHI TÌM THẤY TÀI KHOẢN (ĐĂNG NHẬP THÀNH CÔNG)
            if (taiKhoanHopLe != null) {
                Utils.SharedData.currentUser = taiKhoanHopLe;
                
                BUS.PhanQuyenBUS pqBus = new BUS.PhanQuyenBUS();
                // VÁ LỖI 1: Thay pqBus.getDanhSach() thành pqBus.getAll()
                for (DTO.PhanQuyenDTO pq : pqBus.getAll()) {
                    if (pq.getMaID() == taiKhoanHopLe.getMaQuyen()) {
                        Utils.SharedData.userPermission = pq; 
                        break;
                    }
                }
                
                if (Utils.SharedData.userPermission == null) {
                    Utils.SharedData.userPermission = new DTO.PhanQuyenDTO(); 
                    Utils.SharedData.userPermission.setTenQuyen("Nhân viên"); // Chống Null
                }

                // VÁ LỖI 2: Chống văng app khi getTenQuyen bị Null
                String roleForMainFrame = Utils.SharedData.userPermission.getTenQuyen();
                if (roleForMainFrame == null) {
                    roleForMainFrame = "Nhân viên";
                }
                
                // NẾU LÀ TÀI KHOẢN CÓ MÃ QUYỀN = 1 (QUẢN TRỊ HỆ THỐNG) -> ÉP THÀNH CHỮ "Admin"
                if (taiKhoanHopLe.getMaQuyen() == 1) {
                    roleForMainFrame = "Admin"; 
                }

                // 4. MỞ MAINFRAME CỦA SẾP
                dispose(); 
                new MainFrame(roleForMainFrame).setVisible(true); 
                
            } else {
                JOptionPane.showMessageDialog(this, "Sai tài khoản hoặc mật khẩu!", "Lỗi Xác Thực", JOptionPane.ERROR_MESSAGE);
            }
        };
        
        btnLogin.addActionListener(actionLogin);
        pnlPassBox.addEnterListener(actionLogin);
        txtUser.addActionListener(actionLogin);

        pnlForm.add(lblTitle);
        pnlForm.add(Box.createRigidArea(new Dimension(0, 5))); 
        pnlForm.add(lblSubTitle);
        pnlForm.add(Box.createRigidArea(new Dimension(0, 30))); 
        pnlForm.add(lblUser);
        pnlForm.add(Box.createRigidArea(new Dimension(0, 5)));
        pnlForm.add(txtUser);
        pnlForm.add(Box.createRigidArea(new Dimension(0, 20))); 
        pnlForm.add(lblPass);
        pnlForm.add(Box.createRigidArea(new Dimension(0, 5)));
        pnlForm.add(pnlPassBox); 
        pnlForm.add(Box.createRigidArea(new Dimension(0, 35))); 
        pnlForm.add(btnLogin);

        pnlRight.add(pnlForm); 
        this.add(pnlRight);
    }

    private JLabel createLabelWithIcon(String text, String iconPath) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(FONT_LABEL);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource(iconPath));
            Image img = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            lbl.setIcon(new ImageIcon(img));
        } catch (Exception e) {}
        return lbl;
    }

    class RoundedTextField extends JTextField {
        private int radius;
        private Color currentBorderColor = COL_BORDER;

        public RoundedTextField(int radius) {
            this.radius = radius;
            setOpaque(false); 
            setFont(FONT_INPUT);
            setBorder(new EmptyBorder(5, 15, 5, 15)); 
            setPreferredSize(BOX_DIMENSION);
            setMaximumSize(BOX_DIMENSION);
            setAlignmentX(Component.LEFT_ALIGNMENT);

            addFocusListener(new FocusAdapter() {
                @Override public void focusGained(FocusEvent e) { currentBorderColor = COL_PRIMARY; repaint(); }
                @Override public void focusLost(FocusEvent e) { currentBorderColor = COL_BORDER; repaint(); }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.WHITE); 
            g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
            g2.dispose();
            super.paintComponent(g);
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g); 
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setStroke(new BasicStroke(1.2f));
            g2.setColor(currentBorderColor);
            g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, radius, radius);
            g2.dispose();
        }
    }

    class RoundedPasswordBox extends JPanel {
        private int radius;
        private Color currentBorderColor = COL_BORDER;
        private JPasswordField txtPass;
        private JToggleButton btnEye;

        public RoundedPasswordBox(int radius) {
            this.radius = radius;
            setLayout(new BorderLayout());
            setOpaque(false);
            setPreferredSize(BOX_DIMENSION);
            setMaximumSize(BOX_DIMENSION);
            setAlignmentX(Component.LEFT_ALIGNMENT);

            txtPass = new JPasswordField();
            txtPass.setOpaque(false);
            txtPass.setFont(FONT_INPUT);
            txtPass.setBorder(new EmptyBorder(5, 15, 5, 0)); 
            
            btnEye = new JToggleButton("👁");
            btnEye.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 20)); 
            btnEye.setForeground(new Color(150, 150, 150)); 
            btnEye.setBorder(new EmptyBorder(0, 5, 0, 15));
            btnEye.setContentAreaFilled(false); 
            btnEye.setFocusPainted(false); 
            btnEye.setCursor(new Cursor(Cursor.HAND_CURSOR));

            btnEye.addActionListener(e -> {
                if (btnEye.isSelected()) {
                    txtPass.setEchoChar((char) 0); 
                    btnEye.setForeground(COL_PRIMARY); 
                } else {
                    txtPass.setEchoChar('•'); 
                    btnEye.setForeground(new Color(150, 150, 150)); 
                }
            });

            FocusAdapter focusHandler = new FocusAdapter() {
                @Override public void focusGained(FocusEvent e) { currentBorderColor = COL_PRIMARY; repaint(); }
                @Override public void focusLost(FocusEvent e) {
                    if (!txtPass.isFocusOwner() && !btnEye.isFocusOwner()) {
                        currentBorderColor = COL_BORDER; repaint();
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

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.WHITE);
            g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
            g2.dispose();
            super.paintComponent(g);
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g); 
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setStroke(new BasicStroke(1.2f));
            g2.setColor(currentBorderColor);
            g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, radius, radius);
            g2.dispose();
        }
    }

    class RoundedButton extends JButton {
        private int radius;
        public RoundedButton(String text, int radius) {
            super(text);
            this.radius = radius;
            setFont(new Font("Segoe UI", Font.BOLD, 15));
            setForeground(Color.WHITE);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
            setPreferredSize(BOX_DIMENSION);
            setMaximumSize(BOX_DIMENSION);
            setAlignmentX(Component.LEFT_ALIGNMENT);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            if (getModel().isRollover()) g2.setColor(new Color(255, 143, 183)); 
            else g2.setColor(COL_PRIMARY);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } 
        catch (Exception e) {}
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}