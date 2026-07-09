package gui;

import gui.DialogDangKy_DangNhap_1;
import gui.PanelGioHang;
import gui.PanelTrangChu;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import DTO.KhachHangDTO;
import BUS.KhachHangBUS;

public class ClientMainFrame extends JFrame {
    
    public static KhachHangDTO currentUser = null;
    private JPanel mainContent;
    private JButton userBtn;
    private PanelTrangChu panelTrangChu;
    private JMenuBar menuBar;

    public ClientMainFrame() {
        setTitle("Trạm Đọc - Trang chủ");
        setSize(1400, 850);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        panelTrangChu = new PanelTrangChu(this);  
        
        JPanel topContainer = new JPanel(new BorderLayout());
        topContainer.add(createHeader(), BorderLayout.NORTH);
        topContainer.add(createMenu(), BorderLayout.SOUTH);
        this.menuBar = createMenu(); 
        topContainer.add(this.menuBar, BorderLayout.SOUTH);
        add(topContainer, BorderLayout.NORTH);
        
        mainContent = new JPanel(new BorderLayout());
        mainContent.add(panelTrangChu, BorderLayout.CENTER);
        add(mainContent, BorderLayout.CENTER);

        setVisible(true);
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.decode("#5A4664"));
        header.setPreferredSize(new Dimension(100, 100));

        JLabel logo = new JLabel("Trạm Đọc");
        logo.setForeground(Color.decode("#F29BC1"));
        logo.setFont(new Font("Segoe UI", Font.BOLD, 32));
        logo.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 20));
        logo.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                switchPanel(new PanelTrangChu(ClientMainFrame.this));
            }
        });
        header.add(logo, BorderLayout.WEST);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 25));
        searchPanel.setOpaque(false);

        JPanel searchBox = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 45, 45);
                super.paintComponent(g);
            }
        };
        searchBox.setOpaque(false);
        searchBox.setPreferredSize(new Dimension(550, 45));
        searchBox.setBorder(new EmptyBorder(0, 20, 0, 0));

        JTextField searchField = new JTextField();
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        searchField.setBorder(null);
        searchField.setOpaque(false);

        JLabel lblSearchIcon = new JLabel() {
            private Image searchIcon;
            {
                try {
                    java.net.URL imgURL = getClass().getResource("/GUI/icons/kinhlup.png");
                    if (imgURL == null) {
                        imgURL = getClass().getResource("/gui/icons/kinhlup.png");
                    }
                    if (imgURL != null) {
                        searchIcon = new ImageIcon(imgURL).getImage();
                    }
                } catch (Exception e) {}
            }
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (searchIcon != null) {
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                    int iconSize = 22;
                    int x = (getWidth() - iconSize) / 2;
                    int y = (getHeight() - iconSize) / 2;
                    g2.drawImage(searchIcon, x, y, iconSize, iconSize, this);
                }
            }
        };
        lblSearchIcon.setPreferredSize(new Dimension(55, 45));

        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { performSearch(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { performSearch(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { performSearch(); }
            
            private void performSearch() {
                SwingUtilities.invokeLater(() -> {
                    String keyword = searchField.getText().trim();
                    boolean isHome = mainContent.getComponentCount() > 0 && mainContent.getComponent(0) instanceof PanelTrangChu;
                    
                    if (isHome) {
                        panelTrangChu.loadSach(keyword, "");
                    } else {
                        PanelTrangChu ptc = new PanelTrangChu(ClientMainFrame.this);
                        ptc.loadSach(keyword, "");
                        switchPanel(ptc);
                        searchField.requestFocus(); 
                    }
                });
            }
        });

        searchBox.add(searchField, BorderLayout.CENTER);
        searchBox.add(lblSearchIcon, BorderLayout.EAST);
        searchPanel.add(searchBox);
        header.add(searchPanel, BorderLayout.CENTER);

        JPanel iconPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 25));
        iconPanel.setOpaque(false);
        iconPanel.setBorder(new EmptyBorder(0, 0, 0, 30));

        userBtn = createPillIconBtn("user.png");
        JButton cartBtn = createPillIconBtn("cart.png");
        
        cartBtn.addActionListener(e -> { switchPanel(new PanelGioHang()); });

        userBtn.addActionListener(e -> {
            if (currentUser == null) {
                DialogDangKy_DangNhap_1 loginDialog = new DialogDangKy_DangNhap_1(this);
                loginDialog.setVisible(true);
            } else {
                long lastCloseTime = userBtn.getClientProperty("lastCloseTime") != null ? (long) userBtn.getClientProperty("lastCloseTime") : 0;
                if (System.currentTimeMillis() - lastCloseTime < 200) {
                    return;
                }
                showUserPopup();
            }
        });

        iconPanel.add(userBtn);
        iconPanel.add(cartBtn);
        header.add(iconPanel, BorderLayout.EAST);
       
        return header;
    }

    private JButton createPillIconBtn(String iconName) {
        JButton btn = new JButton() {
            private boolean isHovered = false;
            private Image iconImg;
            {
                try {
                    java.net.URL imgURL = getClass().getResource("/GUI/icons/" + iconName);
                    if (imgURL == null) {
                        imgURL = getClass().getResource("/gui/icons/" + iconName);
                    }
                    if (imgURL != null) {
                        iconImg = new ImageIcon(imgURL).getImage();
                    }
                } catch (Exception e) {}

                addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) { isHovered = true; repaint(); }
                    public void mouseExited(MouseEvent e) { isHovered = false; repaint(); }
                });
            }
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (isHovered) g2.setColor(Color.decode("#D67897"));
                else g2.setColor(Color.decode("#E889A9"));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 45, 45);
                
                if (iconImg != null) {
                    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                    int iconSize = 24;
                    int x = (getWidth() - iconSize) / 2;
                    int y = (getHeight() - iconSize) / 2;
                    g2.drawImage(iconImg, x, y, iconSize, iconSize, this);
                }
                
                super.paintComponent(g);
            }
        };
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(80, 45));
        return btn;
    }

    // ================= POPUP USER (CHUẨN WEB DROPDOWN) =================
    private void showUserPopup() {
        JPopupMenu popup = new JPopupMenu();
        popup.setOpaque(false);
        popup.setBackground(new Color(0, 0, 0, 0));
        popup.setBorder(BorderFactory.createEmptyBorder()); 

        popup.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent e) {}
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent e) {
                userBtn.putClientProperty("lastCloseTime", System.currentTimeMillis());
            }
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent e) {}
        });

        JPanel pnlWrapper = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 15, 15);
                g2.setColor(Color.decode("#E889A9")); 
                g2.setStroke(new BasicStroke(2.0f));
                g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 15, 15);
                g2.dispose();
            }
        };
        pnlWrapper.setOpaque(false);
        pnlWrapper.setLayout(new BoxLayout(pnlWrapper, BoxLayout.Y_AXIS));
        pnlWrapper.setBorder(new EmptyBorder(15, 0, 15, 0)); 
        pnlWrapper.setPreferredSize(new Dimension(250, 260));

        JPanel pnlHeader = new JPanel();
        pnlHeader.setLayout(new BoxLayout(pnlHeader, BoxLayout.Y_AXIS));
        pnlHeader.setOpaque(false);
        pnlHeader.setBorder(new EmptyBorder(0, 20, 15, 20)); 
        
        JLabel lblName = new JLabel(currentUser != null ? currentUser.getHoTen() : "Khách");
        lblName.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblName.setForeground(Color.decode("#5A4664"));
        
        JLabel lblRank = new JLabel("Thành viên Trạm Đọc");
        lblRank.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        lblRank.setForeground(Color.GRAY);
        
        pnlHeader.add(lblName);
        pnlHeader.add(Box.createVerticalStrut(5));
        pnlHeader.add(lblRank);
        
        JSeparator sep = new JSeparator();
        sep.setForeground(Color.decode("#F0F0F0"));
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        
        JButton btnProfile = createDropdownMenuItem("Thông tin cá nhân", Color.decode("#5A4664"));
        btnProfile.addActionListener(e -> {
            popup.setVisible(false);
            switchPanel(new PanelThongTinCaNhanKH()); 
        });

        JButton btnHistory = createDropdownMenuItem("Lịch sử mua hàng", Color.decode("#5A4664"));
        btnHistory.addActionListener(e -> {
            popup.setVisible(false); 
            switchPanel(new PanelLichSuMuaHang()); 
        });

        JButton btnLogout = createDropdownMenuItem("Đăng xuất", Color.decode("#FF4757"));
        btnLogout.addActionListener(e -> {
            popup.setVisible(false);
            currentUser = null;
            KhachHangBUS.currentCustomer = null; 
            
            new BUS.ClientGioHangBUS().clearRamOnly();
            
            ThongBao.show(this,"Đã đăng xuất");
            switchPanel(new PanelTrangChu(this));
        });

        pnlWrapper.add(pnlHeader);
        pnlWrapper.add(sep);
        pnlWrapper.add(Box.createVerticalStrut(5));
        pnlWrapper.add(btnProfile);
        pnlWrapper.add(btnHistory);
        pnlWrapper.add(btnLogout);

        popup.add(pnlWrapper);
        popup.show(userBtn, -145, userBtn.getHeight() + 8); 
    }

    private JButton createDropdownMenuItem(String text, Color defaultColor) {
        JButton btn = new JButton(text) {
            @Override protected void paintComponent(Graphics g) {
                if (getModel().isRollover()) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setColor(Color.decode("#E889A9")); 
                    g2.fillRect(0, 0, getWidth(), getHeight());
                    g2.dispose();
                }
                super.paintComponent(g);
            }
        };
        btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btn.setForeground(defaultColor);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBorder(new EmptyBorder(12, 20, 12, 20)); 
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45)); 
        
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setForeground(Color.WHITE); }
            public void mouseExited(MouseEvent e) { btn.setForeground(defaultColor); }
        });

        return btn;
    }
    private JMenuBar createMenu() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(Color.WHITE);
        menuBar.setBorder(new MatteBorder(0, 0, 1, 0, Color.decode("#EFEFEF")));
        menuBar.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 5)); 

        loadDynamicMenu(menuBar);
        
        return menuBar;
    }

    private void loadDynamicMenu(JMenuBar menuBar) {
        String sql = "SELECT DISTINCT dm.MaID AS MaDM, dm.TenDanhMuc, tl.MaID AS MaTL, tl.TenLoai " +
                     "FROM DanhMuc dm " +
                     "INNER JOIN TheLoai tl ON dm.MaID = tl.MaDanhMuc " +
                     "INNER JOIN Sach s ON s.MaLoai = tl.MaID " +
                     "WHERE s.TrangThai = 'DangBan' " + 
                     "ORDER BY dm.MaID, tl.MaID";

        try (java.sql.Connection con = Utils.JDBCConnection.getConnection();
             java.sql.PreparedStatement ps = con.prepareStatement(sql);
             java.sql.ResultSet rs = ps.executeQuery()) {

            int currentMaDM = -1;
            JMenu currentMenu = null;

            while (rs.next()) {
                int maDM = rs.getInt("MaDM");
                String tenDM = rs.getString("TenDanhMuc");
                String tenTL = rs.getString("TenLoai");

                if (maDM != currentMaDM) {
                    currentMenu = new JMenu(tenDM) {
                        private Timer hideTimer;
                        {
                            hideTimer = new Timer(0, e -> {
                                if (isShowing() && getPopupMenu().isShowing()) {
                                    PointerInfo pi = MouseInfo.getPointerInfo();
                                    if (pi != null) {
                                        Point p = pi.getLocation();
                                        Rectangle menuRect = new Rectangle(getLocationOnScreen(), getSize());
                                        Rectangle popupRect = new Rectangle(getPopupMenu().getLocationOnScreen(), getPopupMenu().getSize());
                                        if (!menuRect.contains(p) && !popupRect.contains(p)) {
                                            setPopupMenuVisible(false);
                                            setSelected(false);
                                            setForeground(Color.decode("#2D3436"));
                                            repaint();
                                        } else {
                                            hideTimer.restart();
                                        }
                                    }
                                } else {
                                    setForeground(Color.decode("#2D3436"));
                                    repaint();
                                }
                            });
                            hideTimer.setRepeats(false);

                            addMouseListener(new MouseAdapter() {
                                public void mouseEntered(MouseEvent e) { 
                                    setForeground(Color.decode("#E889A9")); 
                                    hideTimer.stop();
                                    setPopupMenuVisible(true);
                                    repaint(); 
                                }
                                public void mouseExited(MouseEvent e) { 
                                    hideTimer.restart();
                                }
                            });
                        }
                        
                        @Override
                        protected void paintComponent(Graphics g) {
                            super.paintComponent(g);
                            if (isPopupMenuVisible() || getMousePosition() != null) {
                                Graphics2D g2 = (Graphics2D) g;
                                g2.setColor(Color.decode("#E889A9"));
                                g2.fillRect(15, getHeight() - 5, getWidth() - 30, 2);
                            }
                        }
                    };
                    
                    currentMenu.setUI(new javax.swing.plaf.basic.BasicMenuUI());
                    currentMenu.setFont(new Font("Segoe UI", Font.BOLD, 15));
                    currentMenu.setForeground(Color.decode("#2D3436"));
                    currentMenu.setBackground(Color.WHITE);
                    currentMenu.setOpaque(true);
                    currentMenu.setBorder(new EmptyBorder(10, 20, 10, 20));
                    currentMenu.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    
                    JPopupMenu popup = currentMenu.getPopupMenu();
                    popup.setOpaque(false);
                    popup.setBackground(new Color(0, 0, 0, 0));
                    popup.setBorder(new Border() {
                        @Override
                        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                            Graphics2D g2 = (Graphics2D) g.create();
                            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                            g2.setColor(Color.WHITE);
                            g2.fillRoundRect(x + 1, y + 1, width - 3, height - 3, 15, 15);
                            g2.setColor(Color.decode("#E889A9"));
                            g2.setStroke(new BasicStroke(1.5f));
                            g2.drawRoundRect(x + 1, y + 1, width - 3, height - 3, 15, 15);
                            g2.dispose();
                        }
                        @Override
                        public Insets getBorderInsets(Component c) {
                            return new Insets(10, 5, 10, 5);
                        }
                        @Override
                        public boolean isBorderOpaque() {
                            return false;
                        }
                    });

                    menuBar.add(currentMenu);
                    currentMaDM = maDM;
                }

                if (tenTL != null && currentMenu != null) {
                    JMenuItem item = new JMenuItem(tenTL);
                    
                    item.setUI(new javax.swing.plaf.basic.BasicMenuItemUI());
                    item.setPreferredSize(new Dimension(350, 45)); 
                    item.setFont(new Font("Segoe UI", Font.PLAIN, 15));
                    item.setBackground(Color.WHITE); 
                    item.setForeground(Color.decode("#2D3436")); 
                    item.setOpaque(true); 
                    item.setBorder(new EmptyBorder(5, 20, 5, 20));
                    item.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    
                    item.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseEntered(MouseEvent e) {
                            item.setBackground(Color.decode("#FFF0F5"));
                            item.setForeground(Color.decode("#D67897"));
                        }
                        @Override
                        public void mouseExited(MouseEvent e) {
                            item.setBackground(Color.WHITE);
                            item.setForeground(Color.decode("#2D3436"));
                        }
                    });
                    
                    item.addActionListener(e -> {
                        PanelTrangChu ptc = new PanelTrangChu(ClientMainFrame.this);
                        ptc.loadSach("", tenTL);
                        switchPanel(ptc);
                    });
                    currentMenu.add(item);
                }
            }
        } catch (Exception e) {}
    }

    public JPanel createBreadcrumb(String currentPage) {
        JPanel pnlBreadcrumb = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        pnlBreadcrumb.setBackground(new Color(240, 242, 245)); 

        JLabel lblHome = new JLabel("Trang chủ");
        lblHome.setForeground(Color.GRAY);
        lblHome.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblHome.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblHome.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                switchPanel(new PanelTrangChu(ClientMainFrame.this));
            }
        });

        JLabel lblSeparator = new JLabel(" / ");
        JLabel lblCurrent = new JLabel(currentPage);
        lblCurrent.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblCurrent.setForeground(Color.DARK_GRAY);

        pnlBreadcrumb.add(lblHome);
        pnlBreadcrumb.add(lblSeparator);
        pnlBreadcrumb.add(lblCurrent);

        return pnlBreadcrumb;
    }

    public void refreshAfterLogin() {
        if (KhachHangBUS.currentCustomer != null) {
            if (panelTrangChu != null) {
                panelTrangChu.loadSach();
            }
        }
    }

    public void switchPanel(JPanel newPanel) {
        boolean isRestrictedArea = (newPanel instanceof PanelGioHang) 
                                || (newPanel instanceof PanelThanhToan) 
                                || (newPanel instanceof PanelLichSuMuaHang);
                                
        if (isRestrictedArea) {
            if (BUS.KhachHangBUS.currentCustomer == null) {
                showLoginWarning();
                return; 
            }
        }
        mainContent.removeAll();

        if (menuBar != null) {
            boolean isTrangChu = (newPanel instanceof PanelTrangChu);
            menuBar.setVisible(isTrangChu);
        }

        if (!(newPanel instanceof PanelTrangChu)) {
            String title = "Sách";
            if (newPanel instanceof PanelGioHang) title = "Giỏ hàng";
            if (newPanel instanceof PanelThanhToan) title = "Thanh toán"; 
            if (newPanel instanceof PanelChiTietSach) title = "Chi tiết sản phẩm";
            if (newPanel instanceof PanelLichSuMuaHang) title = "Lịch sử mua hàng";
            
            mainContent.add(createBreadcrumb(title), BorderLayout.NORTH);
        } else {
            this.panelTrangChu = (PanelTrangChu) newPanel;
        }

        mainContent.add(newPanel, BorderLayout.CENTER);
        this.validate(); 
        mainContent.revalidate();
        mainContent.repaint();
    }

    public void showLoginWarning() {
        JDialog dialog = new JDialog(this, true);
        dialog.setUndecorated(true);
        dialog.setBackground(new Color(0, 0, 0, 0)); 

        JPanel pnlBg = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20); 
                g2.setColor(Color.decode("#E889A9")); 
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
                super.paintComponent(g);
            }
        };
        pnlBg.setOpaque(false);
        pnlBg.setPreferredSize(new Dimension(350, 150)); 
        pnlBg.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel pnlContent = new JPanel();
        pnlContent.setLayout(new BoxLayout(pnlContent, BoxLayout.Y_AXIS));
        pnlContent.setOpaque(false);
        
        JLabel msg = new JLabel("Vui lòng đăng nhập");
        msg.setFont(new Font("Segoe UI", Font.BOLD, 22));
        msg.setForeground(Color.decode("#5A4664")); 
        msg.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnOK = new JButton("ĐÓNG") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.decode("#E889A9")); 
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                super.paintComponent(g);
            }
        };
        btnOK.setContentAreaFilled(false);
        btnOK.setBorderPainted(false);
        btnOK.setFocusPainted(false); 
        btnOK.setFocusable(false);    
        
        btnOK.setForeground(Color.WHITE);
        btnOK.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnOK.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnOK.setPreferredSize(new Dimension(120, 40));
        btnOK.setMaximumSize(new Dimension(120, 40));
        btnOK.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnOK.addActionListener(e -> dialog.dispose());

        pnlContent.add(Box.createVerticalGlue()); 
        pnlContent.add(msg); 
        pnlContent.add(Box.createVerticalStrut(25)); 
        pnlContent.add(btnOK);
        pnlContent.add(Box.createVerticalGlue());

        pnlBg.add(pnlContent, BorderLayout.CENTER);

        dialog.add(pnlBg);
        dialog.pack();
        dialog.setLocationRelativeTo(this); 
        dialog.setVisible(true);
    }
}