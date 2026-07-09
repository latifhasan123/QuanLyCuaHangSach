package GUI;

import BUS.TaiKhoanNhanVienBUS;
import DTO.TaiKhoanNhanVienDTO;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class TaiKhoanNhanVienGUI extends JPanel {

    // =========================================================
    // 1. BẢNG MÀU TONE BERRY & CREAM LUXURY
    // =========================================================
    private final Color COLOR_CREAM = new Color(248, 244, 236);
    private final Color COLOR_PRIMARY = new Color(232, 60, 145);
    private final Color COLOR_DARK = new Color(67, 51, 76);
    private final Color COLOR_LIGHT_PINK = new Color(255, 143, 183);

    // COMPONENTS FORM
    private JTextField txtMaTKNV, txtTenDangNhap, txtMatKhau, txtNgayTao;
    private JComboBox<String> cbxQuyen, cbxTrangThai;
    private JButton btnThem, btnSua, btnXoa, btnLamMoi;

    // COMPONENTS TÌM KIẾM
    private JTextField txtBasicSearch;
    private JTextField txtAdvMaTK, txtAdvTenDN, txtAdvQuyen, txtAdvTrangThai, txtAdvNgayTao;

    // COMPONENTS BẢNG
    private JTable tblTaiKhoan;
    private DefaultTableModel tableModel;

    // DATA
    private TaiKhoanNhanVienBUS tkBus = new TaiKhoanNhanVienBUS();
    private ArrayList<TaiKhoanNhanVienDTO> allList = new ArrayList<>();
    private ArrayList<TaiKhoanNhanVienDTO> currentList = new ArrayList<>();
    private int currentSelectedId = -1;

    public TaiKhoanNhanVienGUI() {
        setLayout(new BorderLayout(10, 10));
        setBackground(COLOR_CREAM);
        setBorder(new EmptyBorder(10, 10, 10, 10));

        initNorthPanel();
        initCenterPanel();
        initSouthPanel();
        
        allList = tkBus.getDanhSach();
        loadDataToTable(allList);
        
        addEvents();
    }

    // =========================================================
    // KHU VỰC PHÍA TRÊN: TIÊU ĐỀ & BỘ LỌC TÌM KIẾM
    // =========================================================
    private void initNorthPanel() {
        JPanel pnlNorth = new JPanel(new BorderLayout(10, 10));
        pnlNorth.setBackground(COLOR_CREAM);

        // 1. TIÊU ĐỀ
        JLabel lblTitle = new JLabel("QUẢN LÝ TÀI KHOẢN NHÂN VIÊN", JLabel.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(COLOR_CREAM);
        lblTitle.setOpaque(true);
        lblTitle.setBackground(COLOR_PRIMARY);
        lblTitle.setBorder(new EmptyBorder(15, 0, 15, 0));
        pnlNorth.add(lblTitle, BorderLayout.NORTH);

        // 2. KHU VỰC TÌM KIẾM 
        JPanel pnlSearchContainer = new JPanel();
        pnlSearchContainer.setLayout(new BoxLayout(pnlSearchContainer, BoxLayout.Y_AXIS));
        pnlSearchContainer.setBackground(COLOR_CREAM);
        pnlSearchContainer.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(COLOR_DARK, 1), 
                " BỘ LỌC TÌM KIẾM ", 
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, 
                javax.swing.border.TitledBorder.DEFAULT_POSITION, 
                new Font("Segoe UI", Font.BOLD, 14), COLOR_DARK));

        // --- TẦNG 1: TÌM KIẾM CƠ BẢN ---
        JPanel pnlBasicSearch = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        pnlBasicSearch.setBackground(COLOR_CREAM);
        txtBasicSearch = createStyledTextField(); 
        txtBasicSearch.setPreferredSize(new Dimension(400, 35));

        pnlBasicSearch.add(createLabel("Từ khóa chung (Ưu tiên):"));
        pnlBasicSearch.add(txtBasicSearch);

        JSeparator separator = new JSeparator();
        separator.setMaximumSize(new Dimension(1100, 2));
        separator.setForeground(new Color(200, 180, 200));

        // --- TẦNG 2: TÌM KIẾM NÂNG CAO ---
        JPanel pnlAdvSearch = new JPanel(new GridLayout(2, 6, 10, 10)); 
        pnlAdvSearch.setBackground(COLOR_CREAM);
        pnlAdvSearch.setBorder(new EmptyBorder(5, 15, 10, 15));

        txtAdvMaTK = createStyledTextField();
        txtAdvTenDN = createStyledTextField();
        txtAdvQuyen = createStyledTextField();
        txtAdvTrangThai = createStyledTextField();
        txtAdvNgayTao = createStyledTextField(); 

        // Dòng 1: Mã, Tên ĐN, Quyền
        pnlAdvSearch.add(createLabel("Mã TK:"));       pnlAdvSearch.add(txtAdvMaTK);
        pnlAdvSearch.add(createLabel("Tên Đăng Nhập:"));pnlAdvSearch.add(txtAdvTenDN);
        pnlAdvSearch.add(createLabel("Quyền:"));       pnlAdvSearch.add(txtAdvQuyen);
        
        // Dòng 2: Trạng thái, Ngày Tạo, (Khu vực trống cho cân bằng)
        pnlAdvSearch.add(createLabel("Trạng thái:"));  pnlAdvSearch.add(txtAdvTrangThai);
        pnlAdvSearch.add(createLabel("Ngày Tạo:"));    pnlAdvSearch.add(txtAdvNgayTao);
        pnlAdvSearch.add(new JLabel(""));              pnlAdvSearch.add(new JLabel("")); 

        pnlSearchContainer.add(pnlBasicSearch);
        pnlSearchContainer.add(Box.createRigidArea(new Dimension(0, 5)));
        pnlSearchContainer.add(separator);
        pnlSearchContainer.add(Box.createRigidArea(new Dimension(0, 5)));
        pnlSearchContainer.add(pnlAdvSearch);

        pnlNorth.add(pnlSearchContainer, BorderLayout.CENTER);
        add(pnlNorth, BorderLayout.NORTH);
    }

    // =========================================================
    // KHU VỰC Ở GIỮA: FORM NHẬP LIỆU & BẢNG DANH SÁCH
    // =========================================================
    private void initCenterPanel() {
        JPanel pnlCenter = new JPanel(new BorderLayout(10, 10));
        pnlCenter.setBackground(COLOR_CREAM);
        
        JPanel pnlForm = new JPanel(new GridLayout(3, 4, 15, 15));
        pnlForm.setBackground(COLOR_CREAM);
        pnlForm.setBorder(new EmptyBorder(10, 0, 10, 0));

        txtMaTKNV = createStyledTextField(); txtMaTKNV.setEnabled(false);
        txtNgayTao = createStyledTextField(); txtNgayTao.setEnabled(false);
        txtTenDangNhap = createStyledTextField();
        txtMatKhau = createStyledTextField();
        
        cbxQuyen = new JComboBox<>(new String[]{"Nhân viên bán hàng", "Nhân viên nhập hàng", "Thủ kho"});
        cbxQuyen.setFont(new Font("Segoe UI", Font.PLAIN, 14)); cbxQuyen.setBackground(Color.WHITE);
        
        // --- ĐÃ ĐỔI THÀNH TIẾNG VIỆT CÓ DẤU ---
        cbxTrangThai = new JComboBox<>(new String[]{"Hoạt động", "Khóa"});
        cbxTrangThai.setFont(new Font("Segoe UI", Font.PLAIN, 14)); cbxTrangThai.setBackground(Color.WHITE);

        pnlForm.add(createLabel("Mã Tài Khoản:"));     pnlForm.add(txtMaTKNV);
        pnlForm.add(createLabel("Tên Đăng Nhập (*):"));pnlForm.add(txtTenDangNhap);
        pnlForm.add(createLabel("Mật Khẩu (*):"));     pnlForm.add(txtMatKhau);
        pnlForm.add(createLabel("Phân Quyền:"));       pnlForm.add(cbxQuyen);
        pnlForm.add(createLabel("Trạng Thái:"));       pnlForm.add(cbxTrangThai);
        pnlForm.add(createLabel("Ngày Tạo:"));         pnlForm.add(txtNgayTao);

        // --- BẢNG DỮ LIỆU ---
        String[] columnNames = {"ID", "Mã TKNV", "Tên Đăng Nhập", "Mật Khẩu", "Quyền", "Trạng Thái", "Ngày Tạo"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        
        tblTaiKhoan = new JTable(tableModel);
        tblTaiKhoan.setRowHeight(35);
        tblTaiKhoan.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tblTaiKhoan.setSelectionBackground(COLOR_LIGHT_PINK);
        tblTaiKhoan.setSelectionForeground(COLOR_DARK);
        tblTaiKhoan.setShowGrid(false); 
        tblTaiKhoan.setIntercellSpacing(new Dimension(0, 0));
        
        JTableHeader tableHeader = tblTaiKhoan.getTableHeader();
        tableHeader.setPreferredSize(new Dimension(0, 40));
        tableHeader.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override 
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                label.setBackground(COLOR_DARK); 
                label.setForeground(Color.WHITE); 
                label.setFont(new Font("Segoe UI", Font.BOLD, 14));
                label.setHorizontalAlignment(SwingConstants.CENTER); 
                label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, new Color(255,255,255,50))); 
                label.setOpaque(true);
                return label;
            }
        });

        DefaultTableCellRenderer customRenderer = new DefaultTableCellRenderer() {
            @Override public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(245, 245, 250));
                    c.setForeground(Color.DARK_GRAY);
                }
                
                if(column == 2 || column == 3 || column == 4 || column == 6) {
                    setHorizontalAlignment(SwingConstants.LEFT);
                } else {
                    setHorizontalAlignment(SwingConstants.CENTER);
                }
                
                ((JLabel) c).setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)));
                return c;
            }
        };

        for (int i = 0; i < tblTaiKhoan.getColumnCount(); i++) {
            tblTaiKhoan.getColumnModel().getColumn(i).setCellRenderer(customRenderer);
        }

        tblTaiKhoan.getColumnModel().getColumn(0).setMinWidth(0);
        tblTaiKhoan.getColumnModel().getColumn(0).setMaxWidth(0);
        tblTaiKhoan.getColumnModel().getColumn(0).setWidth(0);
        tblTaiKhoan.getColumnModel().getColumn(1).setMaxWidth(100);

        JScrollPane scrollPane = new JScrollPane(tblTaiKhoan);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createLineBorder(COLOR_DARK, 1));

        pnlCenter.add(pnlForm, BorderLayout.NORTH);
        pnlCenter.add(scrollPane, BorderLayout.CENTER);
        add(pnlCenter, BorderLayout.CENTER);
    }

    // =========================================================
    // KHU VỰC PHÍA DƯỚI: CÁC NÚT THAO TÁC (THÊM, SỬA, XÓA)
    // =========================================================
    private void initSouthPanel() {
        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        pnlButtons.setBackground(COLOR_CREAM);

        btnThem = createButton("Thêm Mới", COLOR_PRIMARY);
        btnSua = createButton("Cập Nhật", COLOR_DARK);
        btnXoa = createButton("Xóa", new Color(220, 53, 69));
        btnLamMoi = createButton("Làm Mới Form", new Color(100, 100, 100));

        pnlButtons.add(btnThem);
        pnlButtons.add(btnSua);
        pnlButtons.add(btnXoa);
        pnlButtons.add(btnLamMoi);
        add(pnlButtons, BorderLayout.SOUTH);
    }

    // ==========================================
    // HÀM TIỆN ÍCH UI
    // ==========================================
    private JLabel createLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lbl.setForeground(COLOR_DARK);
        return lbl;
    }

    private JButton createButton(String text, Color bg) {
        JButton btn = new JButton(text) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isRollover() ? bg.darker() : bg);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setForeground(Color.WHITE); 
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(150, 40));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private JTextField createStyledTextField() {
        JTextField txt = new JTextField();
        txt.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        Border defaultBorder = BorderFactory.createCompoundBorder(new LineBorder(new Color(200, 200, 200), 1, true), new EmptyBorder(4, 8, 4, 8));
        Border focusBorder = BorderFactory.createCompoundBorder(new LineBorder(COLOR_PRIMARY, 2, true), new EmptyBorder(3, 7, 3, 7));
        txt.setBorder(defaultBorder);
        txt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) { if(txt.isEnabled()) { txt.setBorder(focusBorder); txt.setBackground(Color.WHITE); } }
            public void focusLost(java.awt.event.FocusEvent evt) { if(txt.isEnabled()) { txt.setBorder(defaultBorder); txt.setBackground(Color.WHITE); } }
        });
        return txt;
    }

    // ==========================================
    // LOGIC & SỰ KIỆN
    // ==========================================
    private void loadDataToTable(ArrayList<TaiKhoanNhanVienDTO> danhSach) {
        currentList = danhSach;
        tableModel.setRowCount(0);
        for (TaiKhoanNhanVienDTO tk : danhSach) {
            
            // --- ĐÃ ĐỔI THÀNH TIẾNG VIỆT VÀ TÔ MÀU HTML TRONG BẢNG ---
            String htmlStatus = tk.getTrangThai().equals("HoatDong") 
                    ? "<html><font color='green'><b>Hoạt động</b></font></html>" 
                    : "<html><font color='red'><b>Khóa</b></font></html>";
            
            tableModel.addRow(new Object[]{
                tk.getMaID(),
                tk.getMaTKNV(),
                tk.getTenDangNhap(),
                tk.getMatKhau(),
                tkBus.dichSoSangQuyen(tk.getMaQuyen()), 
                htmlStatus, // Hiện chữ có màu lên bảng
                tk.getNgayTao()
            });
        }
    }

    private void addEvents() {
        // --- BẤM CHỌN BẢNG ---
        tblTaiKhoan.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = tblTaiKhoan.getSelectedRow();
                if (row >= 0) {
                    // Lấy dữ liệu trực tiếp từ currentList thay vì lấy từ Bảng để tránh lỗi đọc HTML
                    TaiKhoanNhanVienDTO tk = currentList.get(row);
                    currentSelectedId = tk.getMaID();
                    
                    txtMaTKNV.setText(tk.getMaTKNV());
                    txtTenDangNhap.setText(tk.getTenDangNhap());
                    txtMatKhau.setText(tk.getMatKhau());
                    
                    String tenQuyen = tkBus.dichSoSangQuyen(tk.getMaQuyen());
                    cbxQuyen.setSelectedItem(tenQuyen);
                    
                    // Set lại trạng thái cho ComboBox
                    String trangThai = tk.getTrangThai().equals("HoatDong") ? "Hoạt động" : "Khóa";
                    cbxTrangThai.setSelectedItem(trangThai);
                    
                    txtNgayTao.setText(tk.getNgayTao() != null ? tk.getNgayTao().toString() : "");
                    
                    txtTenDangNhap.setEnabled(false); // Không cho sửa tên đăng nhập
                }
            }
        });

        // ================= LỌC REALTIME =================
        DocumentListener dl = new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { searchRealtime(); }
            public void removeUpdate(DocumentEvent e) { searchRealtime(); }
            public void changedUpdate(DocumentEvent e) { searchRealtime(); }
        };
        
        txtBasicSearch.getDocument().addDocumentListener(dl);
        txtAdvMaTK.getDocument().addDocumentListener(dl);
        txtAdvTenDN.getDocument().addDocumentListener(dl);
        txtAdvQuyen.getDocument().addDocumentListener(dl);
        txtAdvTrangThai.getDocument().addDocumentListener(dl);
        txtAdvNgayTao.getDocument().addDocumentListener(dl);

        // NÚT THÊM
        btnThem.addActionListener(e -> {
            try {
                String user = txtTenDangNhap.getText();
                String pass = txtMatKhau.getText();
                String quyenChu = cbxQuyen.getSelectedItem().toString();
                
                // Lấy chữ tiếng Việt dịch về lại chuẩn DB để đưa xuống DAO
                String trangThaiChu = cbxTrangThai.getSelectedItem().toString();
                String trangThaiDB = trangThaiChu.equals("Hoạt động") ? "HoatDong" : "Khoa";
                
                int maQuyen = tkBus.dichQuyenSangSo(quyenChu);
                
                TaiKhoanNhanVienDTO tkMoi = new TaiKhoanNhanVienDTO(maQuyen, user, pass, trangThaiDB);
                String thongBao = tkBus.themTaiKhoan(tkMoi);
                JOptionPane.showMessageDialog(this, thongBao);
                
                if (thongBao.equals("Tạo tài khoản thành công!")) {
                    btnLamMoi.doClick();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi: Kiểm tra lại dữ liệu nhập!");
            }
        });

        // NÚT SỬA
        btnSua.addActionListener(e -> {
            if (currentSelectedId == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn tài khoản cần sửa!");
                return;
            }

            try {
                String pass = txtMatKhau.getText();
                String quyenChu = cbxQuyen.getSelectedItem().toString();
                
                // Lấy chữ tiếng Việt dịch về lại chuẩn DB để đưa xuống DAO
                String trangThaiChu = cbxTrangThai.getSelectedItem().toString();
                String trangThaiDB = trangThaiChu.equals("Hoạt động") ? "HoatDong" : "Khoa";
                
                int maQuyen = tkBus.dichQuyenSangSo(quyenChu);
                
                TaiKhoanNhanVienDTO tkSua = new TaiKhoanNhanVienDTO();
                tkSua.setMaID(currentSelectedId);
                tkSua.setMaQuyen(maQuyen);
                tkSua.setMatKhau(pass);
                tkSua.setTrangThai(trangThaiDB);
                
                String thongBao = tkBus.suaTaiKhoan(tkSua);
                JOptionPane.showMessageDialog(this, thongBao);
                
                if (thongBao.equals("Cập nhật tài khoản thành công!")) {
                    btnLamMoi.doClick();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi cập nhật!");
            }
        });

        // NÚT XÓA
        btnXoa.addActionListener(e -> {
            if (currentSelectedId == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn tài khoản để xóa!");
                return;
            }

            int row = tblTaiKhoan.getSelectedRow();
            String user = tblTaiKhoan.getValueAt(row, 2).toString();

            int xacNhan = JOptionPane.showConfirmDialog(this,
                    "CẢNH BÁO: Bạn có chắc chắn muốn xóa tài khoản '" + user + "' không?\nNếu tài khoản này đã gán cho nhân viên thì sẽ không xóa được!",
                    "Xác nhận xóa", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

            if (xacNhan == JOptionPane.YES_OPTION) {
                String thongBao = tkBus.xoaTaiKhoan(currentSelectedId);
                JOptionPane.showMessageDialog(this, thongBao);

                if (thongBao.equals("Đã xóa tài khoản!")) {
                    btnLamMoi.doClick();
                }
            }
        });

        // NÚT LÀM MỚI
        btnLamMoi.addActionListener(e -> {
            txtMaTKNV.setText("");
            txtTenDangNhap.setText(""); txtTenDangNhap.setEnabled(true); 
            txtMatKhau.setText("");
            cbxQuyen.setSelectedIndex(0);
            cbxTrangThai.setSelectedIndex(0);
            txtNgayTao.setText("");
            
            txtBasicSearch.setText("");
            txtAdvMaTK.setText(""); txtAdvTenDN.setText(""); 
            txtAdvQuyen.setText(""); txtAdvTrangThai.setText("");
            txtAdvNgayTao.setText("");
            
            currentSelectedId = -1;
            tblTaiKhoan.clearSelection();
            
            allList = tkBus.getDanhSach();
            loadDataToTable(allList);
        });
    }

    private void searchRealtime() {
        String basic = txtBasicSearch.getText().trim().toLowerCase();
        String ma = txtAdvMaTK.getText().trim().toLowerCase();
        String tenDN = txtAdvTenDN.getText().trim().toLowerCase();
        String quyen = txtAdvQuyen.getText().trim().toLowerCase();
        String trangThai = txtAdvTrangThai.getText().trim().toLowerCase();
        String ngayTaoStr = txtAdvNgayTao.getText().trim().toLowerCase();

        ArrayList<TaiKhoanNhanVienDTO> filtered = new ArrayList<>();
        
        for (TaiKhoanNhanVienDTO tk : allList) {
            boolean match = true;
            
            String tenQuyen = tkBus.dichSoSangQuyen(tk.getMaQuyen()).toLowerCase();
            
            // --- ĐÃ ĐỔI: Dịch về Tiếng Việt để so sánh tìm kiếm ---
            String tThai = tk.getTrangThai().equals("HoatDong") ? "hoạt động" : "khóa";
            String ngayTaoTK = tk.getNgayTao() != null ? tk.getNgayTao().toString().toLowerCase() : "";

            if (!basic.isEmpty()) {
                if (!(tk.getMaTKNV().toLowerCase().contains(basic) || 
                      tk.getTenDangNhap().toLowerCase().contains(basic) || 
                      tenQuyen.contains(basic) || 
                      tThai.contains(basic) || // Có thể tìm bằng chữ "hoạt động"
                      ngayTaoTK.contains(basic))) {
                    match = false;
                }
            }
            
            if (!ma.isEmpty() && !tk.getMaTKNV().toLowerCase().contains(ma)) match = false;
            if (!tenDN.isEmpty() && !tk.getTenDangNhap().toLowerCase().contains(tenDN)) match = false;
            if (!quyen.isEmpty() && !tenQuyen.contains(quyen)) match = false;
            if (!trangThai.isEmpty() && !tThai.contains(trangThai)) match = false; // Tìm theo "hoạt động"
            if (!ngayTaoStr.isEmpty() && !ngayTaoTK.contains(ngayTaoStr)) match = false;

            if (match) filtered.add(tk);
        }
        
        loadDataToTable(filtered);
    }
}