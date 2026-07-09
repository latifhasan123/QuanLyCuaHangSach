package GUI;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class MainFrame extends JFrame {
    final Color COL_SIDEBAR_START = new Color(67, 51, 76);
    final Color COL_SIDEBAR_END = new Color(45, 35, 50);      
    final Color COL_ACTIVE_MENU = new Color(255, 255, 255, 30); 
    final Color COL_SUB_MENU_BG = new Color(0, 0, 0, 60);       
    final Color COL_BG_CONTENT = new Color(248, 244, 236);
    final Color COL_TEXT_MENU = Color.WHITE;
    final Color COL_PRIMARY = new Color(232, 60, 145);  
    final Color COL_ACCENT_PINK = new Color(255, 143, 183); 

    final Font FONT_LOGO = new Font("Segoe UI", Font.BOLD, 24);
    final Font FONT_GROUP = new Font("Segoe UI", Font.BOLD, 15); 
    final Font FONT_ITEM = new Font("Segoe UI", Font.PLAIN, 14);
    final Font FONT_VALUE = new Font("Segoe UI", Font.BOLD, 26);
    final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 15);

    private int hoverBarIndex = -1; 
    private String currentRole;     
    private JPanel mainContentPanel;
    private JPopupMenu glassMenu;
    
    public MainFrame(String role) {
        this.currentRole = role; 
        initUI();
    }

    private void initUI() {
        setTitle("QUẢN LÝ NHÀ SÁCH - " + currentRole.toUpperCase());
        setSize(1366, 768);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane(createSidebar());
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setPreferredSize(new Dimension(250, 0)); 
        this.add(scrollPane, BorderLayout.WEST);

        mainContentPanel = new JPanel(new BorderLayout());
        mainContentPanel.add(createDashboardBody(), BorderLayout.CENTER);
        this.add(mainContentPanel, BorderLayout.CENTER);
    }
    
    public void switchPanel(JPanel newPanel) {
        mainContentPanel.removeAll();
        mainContentPanel.add(newPanel, BorderLayout.CENTER);
        mainContentPanel.revalidate();
        mainContentPanel.repaint();
    }

    private JPanel createMockPanel(String title, Color bgColor) {
        JPanel pnl = new JPanel(new BorderLayout());
        pnl.setBackground(bgColor);
        JLabel lbl = new JLabel(title, SwingConstants.CENTER);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 40));
        lbl.setForeground(Color.WHITE);
        pnl.add(lbl, BorderLayout.CENTER);
        return pnl;
    }
    
    private JPanel createSidebar() {
        JPanel pnlSidebar = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, COL_SIDEBAR_START, 0, getHeight(), COL_SIDEBAR_END);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        pnlSidebar.setLayout(new BoxLayout(pnlSidebar, BoxLayout.Y_AXIS));
        pnlSidebar.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel pnlHeader = new JPanel();
        pnlHeader.setLayout(new BoxLayout(pnlHeader, BoxLayout.Y_AXIS));
        pnlHeader.setOpaque(false);
        pnlHeader.setAlignmentX(Component.LEFT_ALIGNMENT);
        pnlHeader.setBorder(new EmptyBorder(25, 25, 25, 25));

        JLabel lblLogo = new JLabel("BOOKSTORE");
        lblLogo.setFont(FONT_LOGO); lblLogo.setForeground(Color.WHITE);
        JSeparator sep = new JSeparator();
        sep.setMaximumSize(new Dimension(100, 2));
        sep.setForeground(new Color(255,255,255,100));
        JLabel lblHello = new JLabel("System Management");
        lblHello.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblHello.setForeground(COL_ACCENT_PINK);
        
        pnlHeader.add(lblLogo); pnlHeader.add(Box.createRigidArea(new Dimension(0, 10)));
        pnlHeader.add(sep); pnlHeader.add(Box.createRigidArea(new Dimension(0, 10)));
        pnlHeader.add(lblHello);
        pnlSidebar.add(pnlHeader); pnlSidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        
        DTO.PhanQuyenDTO p = Utils.SharedData.userPermission;
        if (p == null) return pnlSidebar; 

        pnlSidebar.add(createSingleMenu("TRANG CHỦ"));

        java.util.ArrayList<String> hangHoa = new java.util.ArrayList<>();
        if (p.getQlSach() > 0) { hangHoa.add("Danh sách Sách"); }
        if (p.getQlThuocTinh() > 0) { hangHoa.add("Danh mục"); hangHoa.add("Thể loại"); hangHoa.add("Tác giả"); hangHoa.add("NXB"); }
        if (!hangHoa.isEmpty()) {
            pnlSidebar.add(createMenuGroup("HÀNG HÓA", hangHoa.toArray(new String[0])));
        }

        java.util.ArrayList<String> giaoDich = new java.util.ArrayList<>();
        if (p.getQlBanHang() > 0) { giaoDich.add("Bán hàng tại quầy"); giaoDich.add("Duyệt đơn Online"); }
        if (p.getQlKhuyenMai() > 0) { giaoDich.add("Khuyến mãi"); }
        if (p.getQlHoaDon() > 0) { giaoDich.add("Hóa đơn"); }
        if (p.getQlPhieuDoiTra() > 0) { giaoDich.add("Đổi trả hàng"); giaoDich.add("Phiếu đổi trả hàng"); }
        
        if (p.getQlNhapHang() > 0) { giaoDich.add("Nhập hàng"); }
        if (p.getQlPhieuNhap() > 0) { giaoDich.add("Phiếu nhập"); }
        if (p.getQlPhieuDoiTra() > 0 || p.getQlNhapHang() > 0) { 
            giaoDich.add("Đổi trả NCC"); giaoDich.add("Phiếu đổi trả NCC"); 
        }
        
        if (!giaoDich.isEmpty()) {
            pnlSidebar.add(createMenuGroup("GIAO DỊCH", giaoDich.toArray(new String[0])));
        }

        java.util.ArrayList<String> doiTac = new java.util.ArrayList<>();
        if (p.getQlKhachHang() > 0) { doiTac.add("Khách hàng"); doiTac.add("Tài khoản khách hàng");}
        if (p.getQlNCC() > 0) { doiTac.add("Nhà cung cấp"); }
        if (p.getQlNhanVien() > 0) { doiTac.add("Nhân viên"); }
        if (!doiTac.isEmpty()) {
            pnlSidebar.add(createMenuGroup("ĐỐI TÁC", doiTac.toArray(new String[0])));
        }
        
        java.util.ArrayList<String> heThong = new java.util.ArrayList<>();
        if (p.getQlTaiKhoan() > 0) { heThong.add("Tài khoản"); }
        if (p.getQlPhanQuyen() > 0) { heThong.add("Phân quyền"); } 
        if (!heThong.isEmpty()) {
            pnlSidebar.add(createMenuGroup("HỆ THỐNG", heThong.toArray(new String[0])));
        }

        java.util.ArrayList<String> baoCao = new java.util.ArrayList<>();
        if (p.getQlThongKe() > 0) { 
            baoCao.add("Doanh thu"); 
            baoCao.add("Lợi nhuận"); 
            
            // ĐÃ BỔ SUNG 2 MENU NÀY VÀO CHUYÊN MỤC BÁO CÁO
            baoCao.add("Thống kê nhập hàng");
            baoCao.add("Thống kê trả hàng");
            
            baoCao.add("Tồn kho"); 
            baoCao.add("Lịch sử kho"); 
        }
        if (!baoCao.isEmpty()) {
            pnlSidebar.add(createMenuGroup("BÁO CÁO", baoCao.toArray(new String[0])));
        }

        pnlSidebar.add(Box.createVerticalGlue());
        return pnlSidebar;
    }
    
    private JPanel createMenuGroup(String title, String[] subItems) {
        JPanel pnlGroup = new JPanel();
        pnlGroup.setLayout(new BoxLayout(pnlGroup, BoxLayout.Y_AXIS));
        pnlGroup.setOpaque(false);
        
        JButton btnHeader = createSidebarButton(title, true);
        
        JPanel pnlSubItems = new JPanel() {
            @Override protected void paintComponent(Graphics g) { g.setColor(COL_SUB_MENU_BG); g.fillRect(0, 0, getWidth(), getHeight()); super.paintComponent(g); }
        };
        pnlSubItems.setLayout(new BoxLayout(pnlSubItems, BoxLayout.Y_AXIS));
        pnlSubItems.setOpaque(false); pnlSubItems.setVisible(false);
        
        for (String item : subItems) {
            JButton btnSub = createSidebarButton("   •  " + item, false);

            btnSub.addActionListener(e -> {
                if(item.equals("Nhân viên")) {
                    switchPanel(new NhanVienGUI());
                } 
                else if (item.equals("Tài khoản")) {
                    switchPanel(new TaiKhoanNhanVienGUI());
                } 
                else if (item.equals("Phân quyền")) {
                    switchPanel(new PhanQuyenGUI());
                } else if(item.equals("Doanh thu")){
                    switchPanel(new ThongKeGUI());
                }else if (item.equals("Khách hàng")) {
                    switchPanel(new KhachHangGUI());
                }else if (item.equals("Tài khoản khách hàng")) {
                    switchPanel(new TaiKhoanKhachHangGUI());
                }else if (item.equals("Nhập hàng")) {
                    switchPanel(new NhapHangGUI());
                }
                // ĐÃ MÓC NỐI PHIẾU NHẬP
                else if (item.equals("Phiếu nhập")) {
                    switchPanel(new PhieuNhapGUI());
                }
                else if (item.equals("Đổi trả hàng") || item.equals("Phiếu đổi trả hàng")) {
                    switchPanel(new PhieuTraKhachHangGUI());
                }
                else if (item.equals("Đổi trả NCC") || item.equals("Phiếu đổi trả NCC")) {
                    switchPanel(new PhieuTraNhaCungCapGUI());
                }
                else if (item.equals("Nhà cung cấp")) {
                    switchPanel(new NhaCungCapGUI());
                }else if (item.equals("Danh sách Sách")) {
                    switchPanel(new SachPanel());
                }
                else if (item.equals("Danh mục")) { 
                    switchPanel(new DanhMucPanel());
                }
                else if (item.equals("Tác giả")) {
                    switchPanel(new TacGiaPanel());
                }
                else if (item.equals("Thể loại")) {
                    switchPanel(new TheLoaiPanel());
                }
                else if (item.equals("NXB")) {
                    switchPanel(new NhaXuatBanPanel());
                }
                else if (item.equals("Bán hàng tại quầy")) {
                    switchPanel(new BanHangGUI(null)); 
                }
                else if (item.equals("Hóa đơn")) {
                    switchPanel(new HoaDonGUI(null)); 
                }
                else if (item.equals("Khuyến mãi")) {
                    switchPanel(new KhuyenMaiGUI()); 
                }
                else if (item.equals("Duyệt đơn Online")) {
                    switchPanel(new PanelDuyetDonHang());
                }
                else if (item.equals("Tồn kho")) {
                    switchPanel(new TonKhoGUI());
                }
                else if (item.equals("Lịch sử kho")) {
                    switchPanel(new LichSuKhoGUI());
                }
                else if (item.equals("Lợi nhuận")) {
                    switchPanel(new LoiNhuanGUI());
                }
                // ĐÃ MÓC NỐI THỐNG KÊ NHẬP VÀ TRẢ
                else if (item.equals("Thống kê nhập hàng")) {
                    switchPanel(new ThongKeNhapHangGUI());
                }
                else if (item.equals("Thống kê trả hàng")) {
                    switchPanel(new ThongKeTraHangGUI());
                }
                else {
                    switchPanel(createMockPanel("MÀN HÌNH: " + item.toUpperCase(), COL_PRIMARY));
                }
            });

            pnlSubItems.add(btnSub);
        }
        btnHeader.addActionListener(e -> { pnlSubItems.setVisible(!pnlSubItems.isVisible()); pnlGroup.revalidate(); SwingUtilities.getWindowAncestor(pnlGroup).repaint(); });
        pnlGroup.add(btnHeader); pnlGroup.add(pnlSubItems);
        return pnlGroup;
    }

    private JButton createSingleMenu(String title) {
        JButton btn = createSidebarButton(title, true);
        btn.addActionListener(e -> { if (title.contains("TRANG CHỦ")) switchPanel(createDashboardBody()); });
        return btn;
    }

    private JButton createSidebarButton(String text, boolean isHeader) {
        JButton btn = new JButton(text) {
            @Override protected void paintComponent(Graphics g) {
                if (getModel().isRollover() || getModel().isPressed()) { g.setColor(COL_ACTIVE_MENU); g.fillRect(0, 0, getWidth(), getHeight()); }
                super.paintComponent(g);
            }
        };
        btn.setAlignmentX(Component.LEFT_ALIGNMENT); btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setForeground(COL_TEXT_MENU); btn.setContentAreaFilled(false); btn.setFocusPainted(false);
        btn.setBorderPainted(false); btn.setOpaque(false); btn.setBorder(new EmptyBorder(12, 25, 12, 10));
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45)); btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        if (isHeader) btn.setFont(FONT_GROUP); else btn.setFont(FONT_ITEM);
        return btn;
    }
    
    private JPanel createDashboardBody() {
        if (!currentRole.equals("Admin")) {
            JPanel pnlWelcome = new JPanel(new GridBagLayout()); pnlWelcome.setBackground(COL_BG_CONTENT);
            JLabel lblHello = new JLabel("Xin chào " + currentRole + "! Vui lòng chọn chức năng làm việc.");
            lblHello.setFont(new Font("Segoe UI", Font.BOLD, 22)); lblHello.setForeground(new Color(120, 120, 120));
            pnlWelcome.add(lblHello); return pnlWelcome;
        }

        JPanel panel = new JPanel(new BorderLayout(15, 15)); 
        panel.setBackground(COL_BG_CONTENT);
        panel.setBorder(new EmptyBorder(15, 20, 20, 20)); 

        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setOpaque(false);
        JLabel lblTitle = new JLabel("Tổng quan hôm nay");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22)); lblTitle.setForeground(new Color(50, 50, 50));
        
        JPanel pnlAdminInfo = createAdminProfilePanel("Admin", "ADMIN_01", "Quản trị viên");
        pnlHeader.add(lblTitle, BorderLayout.WEST);
        pnlHeader.add(pnlAdminInfo, BorderLayout.EAST);

        DAO.TrangChuDAO dashboardDB = new DAO.TrangChuDAO();
        double doanhThu = dashboardDB.getDoanhThuHomNay();
        int soDon = dashboardDB.getSoDonThanhCongHomNay();
        int donMoi = dashboardDB.getDonHangMoi();
        int sapHet = dashboardDB.getSachSapHet();
        double loiNhuan = dashboardDB.getLoiNhuanHomNay();
        
        double tySuat = (doanhThu > 0) ? (loiNhuan / doanhThu) * 100 : 0;
        String tySuatStr = String.format("%.1f", tySuat);
        String textDonMoi = (donMoi > 0) ? "Cần xử lý ngay" : "Không có đơn tồn";
        String textSapHet = (sapHet > 0) ? "Cần nhập thêm hàng" : "An toàn";
        JPanel pnlCards = new JPanel(new GridLayout(1, 4, 12, 0));
        pnlCards.setBackground(COL_BG_CONTENT);
        pnlCards.setPreferredSize(new Dimension(0, 140));
        
        pnlCards.add(createShadowCard("Doanh thu thực tế", formatMoneyShort(doanhThu), soDon + " đơn hàng thành công", COL_PRIMARY,"/GUI/icons/money.png")); 
        pnlCards.add(createShadowCard("Đơn hàng chờ duyệt", String.valueOf(donMoi), textDonMoi, new Color(255, 153, 0),"/GUI/icons/cart.png")); 
        pnlCards.add(createShadowCard("Sách sắp hết tồn kho", String.valueOf(sapHet), textSapHet, new Color(220, 53, 69),"/GUI/icons/warning.png")); 
        pnlCards.add(createShadowCard("Lợi nhuận gộp", formatMoneyShort(loiNhuan), "Tỷ suất lợi nhuận " + tySuatStr + "%", new Color(40, 167, 69),"/GUI/icons/profit.png"));

        JPanel pnlMainContent = new JPanel(new BorderLayout(15, 0));
        pnlMainContent.setBackground(COL_BG_CONTENT);
        pnlMainContent.setPreferredSize(new Dimension(0, 500));
        double[] doanhThuTuan = dashboardDB.getDoanhThuTheoTuanTrongThang();
        JPanel pnlChart = createChartPanel(doanhThuTuan);
        pnlChart.setPreferredSize(new Dimension(550, 0));
        
        java.util.Map<String, Integer> topBooks = dashboardDB.getTop5Sach();
        JPanel pnlTopBooks = createTopListPanel("Top 5 Sách bán chạy tháng này", topBooks);
        
        pnlMainContent.add(pnlChart, BorderLayout.WEST);
        pnlMainContent.add(pnlTopBooks, BorderLayout.CENTER);

        Box boxContainer = Box.createVerticalBox();
        boxContainer.add(pnlHeader); 
        boxContainer.add(Box.createRigidArea(new Dimension(0, 15)));
        boxContainer.add(pnlCards);
        boxContainer.add(Box.createRigidArea(new Dimension(0, 20)));
        boxContainer.add(pnlMainContent);
        
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(COL_BG_CONTENT);
        wrapper.add(boxContainer, BorderLayout.NORTH);
        return wrapper;
    }
    
    private JPanel createAdminProfilePanel(String name, String id, String role) {
        final boolean[] isProfileHovered = {false};
        final long[] lastHiddenTime = {0}; 
        
        JPanel pnlProfile = new JPanel(new BorderLayout(12, 0)) {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g); 
                if (isProfileHovered[0] || (glassMenu != null && glassMenu.isVisible())) { 
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(new Color(232, 60, 145, 20)); 
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                    g2.dispose();
                }
            }
        };
        pnlProfile.setOpaque(false); 
        pnlProfile.setCursor(new Cursor(Cursor.HAND_CURSOR));
        pnlProfile.setBorder(new EmptyBorder(5, 10, 5, 10));

        JLabel lblAvatar = new JLabel(String.valueOf(name.charAt(0)).toUpperCase()) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(COL_PRIMARY); g2.fillOval(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };
        lblAvatar.setFont(new Font("Segoe UI", Font.BOLD, 16)); lblAvatar.setForeground(Color.WHITE);
        lblAvatar.setHorizontalAlignment(SwingConstants.CENTER); lblAvatar.setPreferredSize(new Dimension(42, 42));

        JPanel pnlText = new JPanel(); pnlText.setLayout(new BoxLayout(pnlText, BoxLayout.Y_AXIS)); pnlText.setOpaque(false);
        JLabel lblN = new JLabel(name); lblN.setFont(new Font("Segoe UI", Font.BOLD, 14)); lblN.setForeground(COL_SIDEBAR_START); 
        JLabel lblSub = new JLabel(role + " | " + id); lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 11)); lblSub.setForeground(new Color(130, 130, 130));
        pnlText.add(Box.createVerticalGlue()); pnlText.add(lblN); pnlText.add(lblSub); pnlText.add(Box.createVerticalGlue());

        JLabel lblArrow = new JLabel(" ▼ "); lblArrow.setFont(new Font("Segoe UI", Font.PLAIN, 9));
        pnlProfile.add(lblAvatar, BorderLayout.WEST); pnlProfile.add(pnlText, BorderLayout.CENTER); pnlProfile.add(lblArrow, BorderLayout.EAST);

        int shadowSize = 8; 
        
        glassMenu = new JPopupMenu() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                for (int i = 0; i < shadowSize; i++) {
                    g2.setColor(new Color(0, 0, 0, 4)); 
                    g2.fillRoundRect(i, i, getWidth() - (i * 2), getHeight() - (i * 2), 20 + (shadowSize - i), 20 + (shadowSize - i));
                }
                
                g2.setColor(Color.WHITE); 
                g2.fillRoundRect(shadowSize, shadowSize, getWidth() - (shadowSize * 2), getHeight() - (shadowSize * 2), 15, 15);
                g2.dispose();
            }
        };
        glassMenu.setOpaque(false);
        glassMenu.setBorder(new EmptyBorder(shadowSize, shadowSize, shadowSize, shadowSize)); 

        glassMenu.add(createMenuRow("Họ tên: ", name, "/GUI/icons/user_tag.png", false, false, glassMenu));
        glassMenu.add(createMenuRow("Tài khoản: ", id, "/GUI/icons/id_card.png", false, false, glassMenu));
        glassMenu.addSeparator();
        
        glassMenu.add(createMenuRow("Thông tin cá nhân", "", "/GUI/icons/user.png", true, false, glassMenu));
        glassMenu.add(createMenuRow("Đổi mật khẩu", "", "/GUI/icons/lock.png", true, false, glassMenu));
        glassMenu.addSeparator();
        glassMenu.add(createMenuRow("Đăng xuất", "", "/GUI/icons/logout.png", true, true, glassMenu));

        glassMenu.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            @Override public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent e) {}
            @Override public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent e) {
                lastHiddenTime[0] = System.currentTimeMillis();
                pnlProfile.repaint();
            }
            @Override public void popupMenuCanceled(javax.swing.event.PopupMenuEvent e) {}
        });

        pnlProfile.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { isProfileHovered[0] = true; pnlProfile.repaint(); }
            @Override public void mouseExited(MouseEvent e) { isProfileHovered[0] = false; pnlProfile.repaint(); }
            @Override public void mousePressed(MouseEvent e) {
                if (System.currentTimeMillis() - lastHiddenTime[0] < 100) return; 
                
                int desiredWidth = pnlProfile.getWidth() + (shadowSize * 2);
                glassMenu.setPreferredSize(null);
                glassMenu.setPreferredSize(new Dimension(desiredWidth, glassMenu.getPreferredSize().height));
                glassMenu.show(pnlProfile, -shadowSize, pnlProfile.getHeight() + 2);
                pnlProfile.repaint();
            }
        });

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);
        wrapper.setBorder(new EmptyBorder(0, 0, 0, 20)); 
        wrapper.add(pnlProfile, BorderLayout.CENTER);

        return wrapper; 
    }

    private JPanel createMenuRow(String label, String value, String iconPath, boolean isAction, boolean isLogout, JPopupMenu parentMenu) {
        final boolean[] isRowHovered = {false};
        
        JPanel row = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g); 
                if (isRowHovered[0] && isAction) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(new Color(232, 60, 145, 35)); 
                    g2.fillRect(0, 0, getWidth(), getHeight()); 
                    g2.dispose();
                }
            }
        };
        row.setOpaque(false); 
        row.setBorder(new EmptyBorder(10, 15, 10, 15)); 

        String text = value.isEmpty() ? label : "<html>" + label + "<b>" + value + "</b></html>";
        JLabel lbl = new JLabel(text);
        lbl.setIcon(getResizedIcon(iconPath));
        lbl.setIconTextGap(15); 
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lbl.setForeground(isLogout ? Color.RED : COL_SIDEBAR_START);
        
        row.add(lbl, BorderLayout.WEST);

        if (isAction) {
            row.setCursor(new Cursor(Cursor.HAND_CURSOR));
            row.addMouseListener(new MouseAdapter() {
                @Override public void mouseEntered(MouseEvent e) { isRowHovered[0] = true; row.repaint(); }
                @Override public void mouseExited(MouseEvent e) { isRowHovered[0] = false; row.repaint(); }
                @Override public void mousePressed(MouseEvent e) {
                    parentMenu.setVisible(false); 
                    if (isLogout) { dispose(); new LoginFrame().setVisible(true); }
                }
            });
        }
        return row;
    }

    private ImageIcon getResizedIcon(String path) {
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource(path));
            Image img = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        } catch (Exception e) { return null; }
    }
    
    private JPanel createShadowCard(String title, String value, String sub, Color accentColor, String iconPath) {
        JPanel pnlShadow = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g); Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(220, 220, 220)); g2.fillRoundRect(3, 3, getWidth()-6, getHeight()-6, 20, 20);
                g2.setColor(Color.WHITE); g2.fillRoundRect(0, 0, getWidth()-5, getHeight()-5, 20, 20);
            }
        };
        pnlShadow.setBackground(COL_BG_CONTENT); pnlShadow.setBorder(new EmptyBorder(15, 20, 15, 20)); 
        JPanel pnlText = new JPanel(new GridLayout(3, 1, 0, 5)); pnlText.setOpaque(false);
        JLabel lblTitle = new JLabel(title); lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 14)); lblTitle.setForeground(new Color(120, 120, 120));
        JLabel lblValue = new JLabel(value); lblValue.setFont(new Font("Segoe UI", Font.BOLD, 28)); lblValue.setForeground(new Color(50, 50, 50));
        JLabel lblSub = new JLabel(sub); lblSub.setFont(new Font("Segoe UI", Font.BOLD, 12)); lblSub.setForeground(accentColor);
        pnlText.add(lblTitle); pnlText.add(lblValue); pnlText.add(lblSub);
        
        JLabel lblIcon = new JLabel();
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource(iconPath));
            Image img = icon.getImage().getScaledInstance(48, 48, Image.SCALE_SMOOTH);
            lblIcon.setIcon(new ImageIcon(img));
        } catch (Exception e) {}
        lblIcon.setVerticalAlignment(SwingConstants.TOP); 
        
        JPanel content = new JPanel(new BorderLayout(10, 0)); content.setOpaque(false);
        content.add(pnlText, BorderLayout.CENTER); content.add(lblIcon, BorderLayout.EAST);
        pnlShadow.add(content, BorderLayout.CENTER);
        
        JPanel line = new JPanel(); line.setPreferredSize(new Dimension(6, 0)); line.setBackground(accentColor);
        JPanel lineContainer = new JPanel(new BorderLayout()); lineContainer.setOpaque(false);
        lineContainer.add(line, BorderLayout.CENTER); lineContainer.setBorder(new EmptyBorder(5, 0, 5, 15)); 
        pnlShadow.add(lineContainer, BorderLayout.WEST); 
        return pnlShadow;
    }
    private String formatMoneyShort(double amount) {
        if (amount >= 1_000_000_000) return String.format("%.1f tỷ", amount / 1_000_000_000);
        else if (amount >= 1_000_000) return String.format("%.1f tr", amount / 1_000_000);
        else if (amount >= 1_000) return String.format("%.0f k", amount / 1_000);
        else return String.format("%.0f đ", amount);
    }
    
    private JPanel createChartPanel(final double[] revenueData) {
        JPanel pnlShadow = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g); Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(210, 210, 210)); g2.fillRoundRect(2, 2, getWidth()-4, getHeight()-4, 15, 15);
                g2.setColor(Color.WHITE); g2.fillRoundRect(0, 0, getWidth()-5, getHeight()-5, 15, 15);
            }
        };
        pnlShadow.setBackground(COL_BG_CONTENT); pnlShadow.setBorder(new EmptyBorder(10, 20, 10, 20));
        JLabel lbl = new JLabel("Doanh thu thực tháng này"); lbl.setFont(FONT_TITLE); lbl.setBorder(new EmptyBorder(10, 0, 10, 0));
        
        JPanel pnlChartDrawing = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g); Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int w = getWidth(); int h = getHeight(); 
                int bottomPadding = 40; int leftPadding = 30; int topPadding = 30;    
                String[] labels = {"Tuần 1", "Tuần 2", "Tuần 3", "Tuần 4"}; 
                
                double maxVal = 0; for(double d : revenueData) maxVal = Math.max(maxVal, d); 
                if (maxVal == 0) maxVal = 1; 
                maxVal = maxVal * 1.2; 
                
                g2.setColor(new Color(245, 245, 245)); 
                for(int i=0; i<5; i++) { 
                    int yLine = h - bottomPadding - (int)((h - bottomPadding - topPadding) * i / 4.0);
                    g2.drawLine(leftPadding, yLine, w - 20, yLine); 
                }
                g2.setColor(Color.LIGHT_GRAY); g2.drawLine(leftPadding, h-bottomPadding, w-20, h-bottomPadding);

                int colWidth = 50; int availableWidth = w - leftPadding - 20;
                int gap = (availableWidth - (revenueData.length * colWidth)) / (revenueData.length + 1);
                
                for(int i=0; i<revenueData.length; i++) {
                    int x = leftPadding + gap + (i * (colWidth + gap));
                    int barH = (int)((revenueData[i] / maxVal) * (h - bottomPadding - topPadding));
                    int y = h - bottomPadding - barH;
                    
                    if (barH > 0) { 
                        g2.setColor(new Color(0, 0, 0, 15)); g2.fillRoundRect(x + 3, y + 3, colWidth, barH, 10, 10);
                        if (i == hoverBarIndex) g2.setPaint(new GradientPaint(x, y, COL_ACCENT_PINK, x, y+barH, COL_PRIMARY));
                        else g2.setPaint(new GradientPaint(x, y, COL_PRIMARY, x, y+barH, COL_SIDEBAR_START));
                        g2.fillRoundRect(x, y, colWidth, barH, 10, 10);
                    }
                    
                    g2.setColor(new Color(100, 100, 100)); g2.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                    g2.drawString(labels[i], x + (colWidth - g2.getFontMetrics().stringWidth(labels[i]))/2, h - 15); 
                    String valText = formatMoneyShort(revenueData[i]); 
                    g2.setFont(new Font("Segoe UI", Font.BOLD, i == hoverBarIndex ? 12 : 11));
                    g2.setColor(i == hoverBarIndex ? new Color(255, 100, 0) : new Color(80, 80, 80));
                    g2.drawString(valText, x + (colWidth - g2.getFontMetrics().stringWidth(valText))/2, y - 5); 
                }
            }
        };
        
        pnlChartDrawing.addMouseMotionListener(new MouseMotionAdapter() {
            @Override public void mouseMoved(MouseEvent e) {
                int w = pnlChartDrawing.getWidth(); int h = pnlChartDrawing.getHeight();
                int bottomPadding = 40; int topPadding = 30; int leftPadding = 30; int colWidth = 50;
                double maxVal = 0; for(double d : revenueData) maxVal = Math.max(maxVal, d); 
                if(maxVal == 0) maxVal = 1; maxVal = maxVal * 1.2;
                int gap = (w - leftPadding - 20 - (revenueData.length * colWidth)) / (revenueData.length + 1);
                int foundIndex = -1;
                for(int i=0; i<revenueData.length; i++) {
                    int x = leftPadding + gap + (i * (colWidth + gap));
                    int barH = (int)((revenueData[i] / maxVal) * (h - bottomPadding - topPadding));
                    int y = h - bottomPadding - barH;
                    if (e.getX() >= x && e.getX() <= x + colWidth && e.getY() >= y && e.getY() <= y + barH) { foundIndex = i; break; }
                }
                if (foundIndex != hoverBarIndex) { hoverBarIndex = foundIndex; pnlChartDrawing.repaint(); }
            }
        });
        pnlChartDrawing.setOpaque(false); pnlShadow.add(lbl, BorderLayout.NORTH); pnlShadow.add(pnlChartDrawing, BorderLayout.CENTER); 
        return pnlShadow;
    }

    private JPanel createTopListPanel(String title, java.util.Map<String, Integer> topBooks) {
        JPanel pnlShadow = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g); Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(210, 210, 210)); g2.fillRoundRect(2, 2,getWidth()-4, getHeight()-4, 15, 15);
                g2.setColor(Color.WHITE); g2.fillRoundRect(0, 0, getWidth()-5, getHeight()-5, 15, 15);
            }
        };
        pnlShadow.setBackground(COL_BG_CONTENT); pnlShadow.setBorder(new EmptyBorder(10, 20, 10, 20));
        JLabel lbl = new JLabel(title); lbl.setFont(FONT_TITLE); lbl.setBorder(new EmptyBorder(10, 0, 10, 0)); pnlShadow.add(lbl, BorderLayout.NORTH);
        
        JPanel list = new JPanel(); list.setLayout(new BoxLayout(list, BoxLayout.Y_AXIS)); list.setOpaque(false);
        
        if (topBooks.isEmpty()) {
            JLabel noData = new JLabel("Chưa có dữ liệu bán hàng trong tháng này.");
            noData.setForeground(Color.GRAY); noData.setFont(new Font("Segoe UI", Font.ITALIC, 14));
            list.add(Box.createRigidArea(new Dimension(0, 20))); list.add(noData);
        } else {
            String[] items = topBooks.keySet().toArray(new String[0]);
            int[] quantities = topBooks.values().stream().mapToInt(Integer::intValue).toArray();
            int maxQty = quantities.length > 0 ? quantities[0] : 1; 

            for (int i = 0; i < items.length; i++) {
                JPanel row = new JPanel(new BorderLayout(10, 0)); row.setOpaque(false); row.setBorder(new EmptyBorder(10, 0, 10, 0));
                JLabel lblName = new JLabel((i+1) + ". " + items[i]); lblName.setFont(new Font("Segoe UI", Font.PLAIN, 13)); lblName.setPreferredSize(new Dimension(160, 0)); 
                JLabel lblCount = new JLabel(quantities[i] + " cuốn"); lblCount.setFont(new Font("Segoe UI", Font.BOLD, 12));
                lblCount.setForeground(new Color(100, 100, 100)); lblCount.setPreferredSize(new Dimension(60, 0)); lblCount.setHorizontalAlignment(SwingConstants.RIGHT);
                
                final int currentQty = quantities[i];
                JPanel pnlBarArea = new JPanel() {
                    @Override protected void paintComponent(Graphics g) {
                        super.paintComponent(g); Graphics2D g2 = (Graphics2D) g;
                        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                        g2.setColor(new Color(250, 220, 230)); 
                        int barWidth = (int) (((double) currentQty / maxQty) * getWidth());
                        g2.fillRoundRect(0, (getHeight() - 12) / 2, barWidth, 12, 10, 10);
                    }
                };
                pnlBarArea.setOpaque(false); pnlBarArea.setPreferredSize(new Dimension(250, 30)); 
                row.add(lblName, BorderLayout.WEST); row.add(pnlBarArea, BorderLayout.CENTER); row.add(lblCount, BorderLayout.EAST);
                list.add(row);
            }
        }
        list.add(Box.createVerticalGlue()); pnlShadow.add(list, BorderLayout.CENTER); 
        return pnlShadow;
    }
}