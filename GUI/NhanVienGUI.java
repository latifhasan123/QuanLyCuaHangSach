package GUI;

import BUS.NhanVienBUS;
import DTO.NhanVienDTO;
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

public class NhanVienGUI extends JPanel {

    // BỘ MÀU LUXURY ĐỒNG BỘ
    private final Color COLOR_CREAM = new Color(248, 244, 236);
    private final Color COLOR_PRIMARY = new Color(232, 60, 145);
    private final Color COLOR_DARK = new Color(67, 51, 76);
    private final Color COLOR_LIGHT_PINK = new Color(255, 143, 183);

    // COMPONENTS FORM NHẬP LIỆU
    private JTextField txtMaNV, txtHoTen, txtSdt, txtEmail, txtChucVu;
    private JComboBox<String> cbxTaiKhoan;
    private JButton btnThem, btnSua, btnXoa, btnLamMoi;

    // COMPONENTS TÌM KIẾM
    private JTextField txtBasicSearch;
    private JTextField txtAdvMa, txtAdvTen, txtAdvSdt, txtAdvEmail, txtAdvCV, txtAdvTK;

    // COMPONENTS BẢNG
    private JTable tblNhanVien;
    private DefaultTableModel tableModel;

    // DATA
    private NhanVienBUS nvBus = new NhanVienBUS();
    private ArrayList<NhanVienDTO> allList = new ArrayList<>();
    private ArrayList<NhanVienDTO> currentList = new ArrayList<>();
    private ArrayList<DTO.TaiKhoanNhanVienDTO> listTaiKhoanRanh = new ArrayList<>();

    public NhanVienGUI() {
        setLayout(new BorderLayout(10, 10));
        setBackground(COLOR_CREAM);
        setBorder(new EmptyBorder(10, 10, 10, 10));

        initNorthPanel();
        initCenterPanel();
        initSouthPanel();

        loadTaiKhoanLenComboBox(""); 
        
        allList = nvBus.getDanhSach();
        loadDataToTable(allList);
        
        checkPermission();
        addEvents();
    }

    private void checkPermission() {
        DTO.PhanQuyenDTO p = Utils.SharedData.userPermission;
        if (p == null) return;
        if (p.getQlNhanVien() == 0) {
            btnThem.setVisible(false); 
            btnSua.setVisible(false);  
            btnXoa.setVisible(false);  
        }
    }

    // ==========================================
    // BỘ PHIÊN DỊCH MÃ CHỨC VỤ SANG CHỮ
    // ==========================================
    private String formatChucVu(String rawCV) {
        if (rawCV == null || rawCV.trim().isEmpty() || rawCV.equals("0")) return "Chưa phân quyền";
        if (rawCV.contains("1") || rawCV.equalsIgnoreCase("Quản trị")) return "Quản trị";
        if (rawCV.contains("2") || rawCV.equalsIgnoreCase("Nhân viên bán hàng")) return "Nhân viên bán hàng";
        if (rawCV.contains("3") || rawCV.equalsIgnoreCase("Thủ kho")) return "Thủ kho";
        if (rawCV.contains("4") || rawCV.equalsIgnoreCase("Nhân viên nhập hàng")) return "Nhân viên nhập hàng";
        return rawCV; // Trả về nguyên gốc những chữ Sếp tự gõ tay vào
    }

    // ==========================================
    // 1. PHẦN TRÊN: TIÊU ĐỀ & BỘ LỌC TÌM KIẾM
    // ==========================================
    private void initNorthPanel() {
        JPanel pnlNorth = new JPanel(new BorderLayout(10, 10));
        pnlNorth.setBackground(COLOR_CREAM);

        JLabel lblTitle = new JLabel("QUẢN LÝ NHÂN VIÊN", JLabel.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(COLOR_CREAM);
        lblTitle.setOpaque(true);
        lblTitle.setBackground(COLOR_PRIMARY);
        lblTitle.setBorder(new EmptyBorder(15, 0, 15, 0));
        pnlNorth.add(lblTitle, BorderLayout.NORTH);

        JPanel pnlSearchContainer = new JPanel();
        pnlSearchContainer.setLayout(new BoxLayout(pnlSearchContainer, BoxLayout.Y_AXIS));
        pnlSearchContainer.setBackground(COLOR_CREAM);
        pnlSearchContainer.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(COLOR_DARK, 1), 
                " BỘ LỌC TÌM KIẾM ", 
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, 
                javax.swing.border.TitledBorder.DEFAULT_POSITION, 
                new Font("Segoe UI", Font.BOLD, 14), COLOR_DARK));

        JPanel pnlBasicSearch = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        pnlBasicSearch.setBackground(COLOR_CREAM);
        txtBasicSearch = createStyledTextField();
        txtBasicSearch.setPreferredSize(new Dimension(400, 35));

        pnlBasicSearch.add(createLabel("Từ khóa chung (Ưu tiên):"));
        pnlBasicSearch.add(txtBasicSearch);

        JSeparator separator = new JSeparator();
        separator.setMaximumSize(new Dimension(1100, 2));
        separator.setForeground(new Color(200, 180, 200));

        JPanel pnlAdvSearch = new JPanel(new GridLayout(2, 6, 10, 10));
        pnlAdvSearch.setBackground(COLOR_CREAM);
        pnlAdvSearch.setBorder(new EmptyBorder(5, 15, 10, 15));

        txtAdvMa = createStyledTextField();
        txtAdvTen = createStyledTextField();
        txtAdvSdt = createStyledTextField();
        txtAdvEmail = createStyledTextField();
        txtAdvCV = createStyledTextField();
        txtAdvTK = createStyledTextField();

        pnlAdvSearch.add(createLabel("Mã NV:"));   pnlAdvSearch.add(txtAdvMa);
        pnlAdvSearch.add(createLabel("Họ Tên:"));  pnlAdvSearch.add(txtAdvTen);
        pnlAdvSearch.add(createLabel("SĐT:"));     pnlAdvSearch.add(txtAdvSdt);
        pnlAdvSearch.add(createLabel("Email:"));   pnlAdvSearch.add(txtAdvEmail);
        pnlAdvSearch.add(createLabel("Chức vụ:")); pnlAdvSearch.add(txtAdvCV);
        pnlAdvSearch.add(createLabel("Tài khoản:")); pnlAdvSearch.add(txtAdvTK); 

        pnlSearchContainer.add(pnlBasicSearch);
        pnlSearchContainer.add(Box.createRigidArea(new Dimension(0, 5)));
        pnlSearchContainer.add(separator);
        pnlSearchContainer.add(Box.createRigidArea(new Dimension(0, 5)));
        pnlSearchContainer.add(pnlAdvSearch);

        pnlNorth.add(pnlSearchContainer, BorderLayout.CENTER);
        add(pnlNorth, BorderLayout.NORTH);
    }

    // ==========================================
    // 2. PHẦN GIỮA: FORM NHẬP LIỆU & BẢNG
    // ==========================================
    private void initCenterPanel() {
        JPanel pnlCenter = new JPanel(new BorderLayout(10, 10));
        pnlCenter.setBackground(COLOR_CREAM);

        JPanel pnlForm = new JPanel(new GridLayout(3, 4, 15, 15));
        pnlForm.setBackground(COLOR_CREAM);
        pnlForm.setBorder(new EmptyBorder(10, 0, 10, 0));

        txtMaNV = createStyledTextField(); txtMaNV.setEnabled(false);
        txtHoTen = createStyledTextField();
        txtSdt = createStyledTextField();
        txtEmail = createStyledTextField();
        
        txtChucVu = createStyledTextField(); 
        
        cbxTaiKhoan = new JComboBox<>(); 
        cbxTaiKhoan.setFont(new Font("Segoe UI", Font.PLAIN, 14)); cbxTaiKhoan.setBackground(Color.WHITE);
        
        pnlForm.add(createLabel("Mã Nhân Viên:")); pnlForm.add(txtMaNV);
        pnlForm.add(createLabel("Họ và Tên (*):")); pnlForm.add(txtHoTen);
        pnlForm.add(createLabel("Số Điện Thoại (*):")); pnlForm.add(txtSdt);
        pnlForm.add(createLabel("Email:")); pnlForm.add(txtEmail);
        pnlForm.add(createLabel("Chức Vụ:")); pnlForm.add(txtChucVu);
        pnlForm.add(createLabel("Tài Khoản:")); pnlForm.add(cbxTaiKhoan);

        String[] columnNames = {"ID", "Mã NV", "Họ Tên", "SĐT", "Email", "Chức Vụ", "Tài Khoản"};
        tableModel = new DefaultTableModel(columnNames, 0) { @Override public boolean isCellEditable(int r, int c) { return false; } };
        tblNhanVien = new JTable(tableModel);
        tblNhanVien.setRowHeight(35);
        tblNhanVien.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tblNhanVien.setSelectionBackground(COLOR_LIGHT_PINK);
        tblNhanVien.setSelectionForeground(COLOR_DARK);
        tblNhanVien.setShowGrid(false); 
        tblNhanVien.setIntercellSpacing(new Dimension(0, 0));

        JTableHeader tableHeader = tblNhanVien.getTableHeader();
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
                
                if(column == 2 || column == 4 || column == 5 || column == 6) {
                    setHorizontalAlignment(SwingConstants.LEFT);
                } else {
                    setHorizontalAlignment(SwingConstants.CENTER);
                }
                
                if (column == 5) {
                    String cv = value != null ? value.toString() : "";
                    if (cv.equals("Quản trị")) c.setForeground(COLOR_PRIMARY); 
                    else if (cv.equals("Nhân viên bán hàng")) c.setForeground(new Color(40, 167, 69)); 
                    else if (cv.equals("Nhân viên nhập hàng")) c.setForeground(new Color(0, 123, 255)); 
                    else if (cv.equals("Thủ kho")) c.setForeground(new Color(111, 66, 193)); 
                    else c.setForeground(Color.DARK_GRAY); 
                    c.setFont(new Font("Segoe UI", Font.BOLD, 14));
                } else {
                    c.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                }
                
                ((JLabel) c).setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)));
                return c;
            }
        };

        for (int i = 0; i < tblNhanVien.getColumnCount(); i++) {
            tblNhanVien.getColumnModel().getColumn(i).setCellRenderer(customRenderer);
        }
        
        tblNhanVien.getColumnModel().getColumn(0).setMinWidth(0);
        tblNhanVien.getColumnModel().getColumn(0).setMaxWidth(0);
        tblNhanVien.getColumnModel().getColumn(0).setWidth(0);
        tblNhanVien.getColumnModel().getColumn(1).setMaxWidth(100);
        tblNhanVien.getColumnModel().getColumn(2).setPreferredWidth(180);

        JScrollPane scrollPane = new JScrollPane(tblNhanVien);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createLineBorder(COLOR_DARK, 1));

        pnlCenter.add(pnlForm, BorderLayout.NORTH);
        pnlCenter.add(scrollPane, BorderLayout.CENTER);
        add(pnlCenter, BorderLayout.CENTER);
    }

    // ==========================================
    // 3. PHẦN DƯỚI: CÁC NÚT THAO TÁC
    // ==========================================
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
    // LOGIC DỮ LIỆU
    // ==========================================
    private void loadTaiKhoanLenComboBox(String maTKNV_NgoaiLe) {
        cbxTaiKhoan.removeAllItems();
        cbxTaiKhoan.addItem("Không cấp tài khoản"); 
        
        listTaiKhoanRanh = nvBus.getDanhSachTaiKhoanRanh(maTKNV_NgoaiLe); 
        if (listTaiKhoanRanh != null) {
            for (DTO.TaiKhoanNhanVienDTO tk : listTaiKhoanRanh) {
                cbxTaiKhoan.addItem(tk.getMaTKNV() + " - " + tk.getTenDangNhap());
            }
        }
    }

    private void loadDataToTable(ArrayList<NhanVienDTO> danhSach) {
        currentList = danhSach;
        tableModel.setRowCount(0);
        for (NhanVienDTO nv : danhSach) {
            String tenCV = formatChucVu(nv.getMaChucVu_Chu());
            String taiKhoan = (nv.getMaTaiKhoan_Chu() == null || nv.getMaTaiKhoan_Chu().isEmpty() || nv.getMaTaiKhoan_Chu().equals("0")) ? "Không có" : nv.getMaTaiKhoan_Chu();
            
            tableModel.addRow(new Object[]{
                nv.getMaID(), nv.getMaNV(), nv.getHoTen(), nv.getSoDienThoai(),
                nv.getEmail(), tenCV, taiKhoan
            });
        }
    }

    private void addEvents() {
        
        cbxTaiKhoan.addActionListener(e -> {
            int index = cbxTaiKhoan.getSelectedIndex();
            if (index > 0 && listTaiKhoanRanh != null && (index - 1) < listTaiKhoanRanh.size()) {
                int maQuyen = listTaiKhoanRanh.get(index - 1).getMaQuyen();
                if (maQuyen == 1) txtChucVu.setText("Quản trị");
                else if (maQuyen == 2) txtChucVu.setText("Nhân viên bán hàng");
                else if (maQuyen == 3) txtChucVu.setText("Thủ kho");
                else if (maQuyen == 4) txtChucVu.setText("Nhân viên nhập hàng");
                
                txtChucVu.setEnabled(false); 
            } else {
                txtChucVu.setText(""); 
                txtChucVu.setEnabled(true); 
            }
        });

        // Click Bảng
        tblNhanVien.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                int row = tblNhanVien.getSelectedRow();
                if (row >= 0) {
                    NhanVienDTO nv = currentList.get(row);
                    txtMaNV.setText(nv.getMaNV());
                    txtHoTen.setText(nv.getHoTen());
                    txtSdt.setText(nv.getSoDienThoai());
                    txtEmail.setText(nv.getEmail() != null ? nv.getEmail() : "");
                    
                    // BƯỚC 1: XỬ LÝ COMBOBOX TÀI KHOẢN TRƯỚC (Nó sẽ tự động kích hoạt sự kiện xóa rỗng ô chức vụ)
                    String maTK_HienThi = nv.getMaTaiKhoan_Chu() != null ? nv.getMaTaiKhoan_Chu() : "";
                    loadTaiKhoanLenComboBox(maTK_HienThi); 

                    if (maTK_HienThi.isEmpty() || maTK_HienThi.equals("0")) {
                        cbxTaiKhoan.setSelectedIndex(0); 
                    } else {
                        for (int i = 0; i < cbxTaiKhoan.getItemCount(); i++) {
                            if (cbxTaiKhoan.getItemAt(i).startsWith(maTK_HienThi + " -")) {
                                cbxTaiKhoan.setSelectedIndex(i);
                                break;
                            }
                        }
                    }

                    // BƯỚC 2: SAU KHI COMBOBOX CHẠY XONG, MÌNH MỚI ĐIỀN TÊN CHỨC VỤ VÀO ĐỂ KHÔNG BỊ XÓA MẤT
                    String tenCV = formatChucVu(nv.getMaChucVu_Chu());
                    txtChucVu.setText(tenCV);
                }
            }
        });

        // Lọc Realtime
        DocumentListener dl = new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { searchRealtime(); }
            public void removeUpdate(DocumentEvent e) { searchRealtime(); }
            public void changedUpdate(DocumentEvent e) { searchRealtime(); }
        };
        
        txtBasicSearch.getDocument().addDocumentListener(dl);
        txtAdvMa.getDocument().addDocumentListener(dl);
        txtAdvTen.getDocument().addDocumentListener(dl);
        txtAdvSdt.getDocument().addDocumentListener(dl);
        txtAdvEmail.getDocument().addDocumentListener(dl);
        txtAdvCV.getDocument().addDocumentListener(dl);
        txtAdvTK.getDocument().addDocumentListener(dl);

        // ==========================================
        // NÚT THÊM (ĐÃ CHÈN DÒNG LẤY TÊN CHỨC VỤ)
        // ==========================================
        btnThem.addActionListener(e -> {
            try {
                String ten = txtHoTen.getText();
                String sdt = txtSdt.getText();
                String email = txtEmail.getText();
                String chucVuDuocChon = txtChucVu.getText().trim();
                int maCV = nvBus.dichChucVuSangSo(chucVuDuocChon);
                
                String taiKhoanDuocChon = cbxTaiKhoan.getSelectedItem() != null ? cbxTaiKhoan.getSelectedItem().toString() : "";
                int maTK = 0; 
                if (!taiKhoanDuocChon.equals("Không cấp tài khoản") && !taiKhoanDuocChon.isEmpty()) {
                    maTK = nvBus.dichTaiKhoanSangSo(taiKhoanDuocChon);
                }
                
                NhanVienDTO nvMoi = new NhanVienDTO(maCV, maTK, ten, email, sdt);
                
                // --- ĐÂY LÀ DÒNG QUAN TRỌNG NHẤT VỪA ĐƯỢC THÊM VÀO ---
                nvMoi.setMaChucVu_Chu(chucVuDuocChon); 
                
                String thongBao = nvBus.themNhanVien(nvMoi);
                JOptionPane.showMessageDialog(this, thongBao);
                
                if (thongBao.equals("Thêm nhân viên thành công!")) {
                    btnLamMoi.doClick(); 
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi: Vui lòng kiểm tra lại dữ liệu nhập!");
            }
        });

        // ==========================================
        // NÚT SỬA (ĐÃ CHÈN DÒNG LẤY TÊN CHỨC VỤ)
        // ==========================================
        btnSua.addActionListener(e -> {
            int row = tblNhanVien.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Vui lòng click chọn một nhân viên trên bảng để sửa!");
                return;
            }
            try {
                int maID = Integer.parseInt(tblNhanVien.getValueAt(row, 0).toString());
                String maNV = txtMaNV.getText();
                String ten = txtHoTen.getText();
                String sdt = txtSdt.getText();
                String email = txtEmail.getText();
                String chucVuDuocChon = txtChucVu.getText().trim();
                int maCV = nvBus.dichChucVuSangSo(chucVuDuocChon);
                
                String taiKhoanDuocChon = cbxTaiKhoan.getSelectedItem() != null ? cbxTaiKhoan.getSelectedItem().toString() : "";
                int maTK = 0;
                if (!taiKhoanDuocChon.equals("Không cấp tài khoản") && !taiKhoanDuocChon.isEmpty()) {
                    maTK = nvBus.dichTaiKhoanSangSo(taiKhoanDuocChon);
                }
                
                NhanVienDTO nvSua = new NhanVienDTO(maID, maNV, maCV, maTK, ten, email, sdt);
                
                // --- ĐÂY LÀ DÒNG QUAN TRỌNG NHẤT VỪA ĐƯỢC THÊM VÀO ---
                nvSua.setMaChucVu_Chu(chucVuDuocChon); 
                
                String thongBao = nvBus.suaNhanVien(nvSua);
                JOptionPane.showMessageDialog(this, thongBao);
                
                if (thongBao.equals("Cập nhật thông tin thành công!")) {
                    btnLamMoi.doClick();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi: Vui lòng kiểm tra lại dữ liệu nhập!");
            }
        });

        // NÚT XÓA
        btnXoa.addActionListener(e -> {
            int row = tblNhanVien.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Vui lòng click chọn một nhân viên trên bảng để xóa!");
                return;
            }
            int maID = Integer.parseInt(tblNhanVien.getValueAt(row, 0).toString());
            String tenNV = currentList.get(row).getHoTen(); 

            if (JOptionPane.showConfirmDialog(this, "Bạn có CHẮC CHẮN muốn xóa nhân viên: " + tenNV + " không?", "Xác nhận", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                String thongBao = nvBus.xoaNhanVien(maID);
                JOptionPane.showMessageDialog(this, thongBao);
                if (thongBao.equals("Đã xóa nhân viên khỏi hệ thống!")) btnLamMoi.doClick();
            }
        });

        // NÚT LÀM MỚI
        btnLamMoi.addActionListener(e -> {
            txtMaNV.setText(""); txtHoTen.setText(""); txtSdt.setText(""); txtEmail.setText("");
            txtBasicSearch.setText("");
            txtAdvMa.setText(""); txtAdvTen.setText(""); txtAdvSdt.setText("");
            txtAdvEmail.setText(""); txtAdvCV.setText(""); txtAdvTK.setText("");
            
            loadTaiKhoanLenComboBox(""); 
            cbxTaiKhoan.setSelectedIndex(0); 
            txtChucVu.setText(""); 
            txtChucVu.setEnabled(true);
            tblNhanVien.clearSelection();
            
            allList = nvBus.getDanhSach();
            loadDataToTable(allList);
        });
    }

    private void searchRealtime() {
        String basic = txtBasicSearch.getText().trim().toLowerCase();
        String ma = txtAdvMa.getText().trim().toLowerCase();
        String ten = txtAdvTen.getText().trim().toLowerCase();
        String sdt = txtAdvSdt.getText().trim().toLowerCase();
        String email = txtAdvEmail.getText().trim().toLowerCase();
        String cv = txtAdvCV.getText().trim().toLowerCase();
        String tk = txtAdvTK.getText().trim().toLowerCase();

        ArrayList<NhanVienDTO> filtered = new ArrayList<>();
        for (NhanVienDTO nv : allList) {
            boolean match = true;
            String nvEmail = nv.getEmail() != null ? nv.getEmail().toLowerCase() : "";
            String nvCV = formatChucVu(nv.getMaChucVu_Chu()).toLowerCase(); 
            String nvTK = nv.getMaTaiKhoan_Chu() != null ? nv.getMaTaiKhoan_Chu().toLowerCase() : "";

            if (!basic.isEmpty()) {
                if (!(nv.getMaNV().toLowerCase().contains(basic) || nv.getHoTen().toLowerCase().contains(basic) || 
                      nv.getSoDienThoai().contains(basic) || nvEmail.contains(basic) || nvCV.contains(basic) || nvTK.contains(basic))) {
                    match = false;
                }
            }
            if (!ma.isEmpty() && !nv.getMaNV().toLowerCase().contains(ma)) match = false;
            if (!ten.isEmpty() && !nv.getHoTen().toLowerCase().contains(ten)) match = false;
            if (!sdt.isEmpty() && !nv.getSoDienThoai().contains(sdt)) match = false;
            if (!email.isEmpty() && !nvEmail.contains(email)) match = false;
            if (!cv.isEmpty() && !nvCV.contains(cv)) match = false;
            if (!tk.isEmpty() && !nvTK.contains(tk)) match = false;

            if (match) filtered.add(nv);
        }
        loadDataToTable(filtered);
    }
}