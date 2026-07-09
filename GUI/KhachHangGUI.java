package GUI;

import BUS.KhachHangBUS;
import DTO.KhachHangDTO;
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

public class KhachHangGUI extends JPanel {

    // BỘ MÀU LUXURY
    private final Color COLOR_CREAM = new Color(248, 244, 236);
    private final Color COLOR_PRIMARY = new Color(232, 60, 145);
    private final Color COLOR_DARK = new Color(67, 51, 76);
    private final Color COLOR_LIGHT_PINK = new Color(255, 143, 183);

    // COMPONENTS FORM NHẬP LIỆU
    private JTextField txtMaKH, txtHoTen, txtSDT, txtEmail, txtDiaChi, txtDiem;
    private JButton btnThem, btnSua, btnXoa, btnLamMoi;

    // COMPONENTS TÌM KIẾM
    private JTextField txtBasicSearch;
    private JTextField txtAdvMa, txtAdvTen, txtAdvSdt, txtTuDiem, txtDenDiem, txtAdvDiaChi;

    // COMPONENTS BẢNG
    private JTable tblKhachHang;
    private DefaultTableModel tableModel;

    // DATA
    private KhachHangBUS khBus = new KhachHangBUS();
    private ArrayList<KhachHangDTO> allList = new ArrayList<>();
    private ArrayList<KhachHangDTO> currentList = new ArrayList<>();
    private int currentSelectedId = -1;

    public KhachHangGUI() {
        setLayout(new BorderLayout(10, 10));
        setBackground(COLOR_CREAM);
        setBorder(new EmptyBorder(10, 10, 10, 10));

        initNorthPanel();   
        initCenterPanel();  
        initSouthPanel();   

        allList = khBus.getAllKhachHang();
        loadDataToTable(allList);

        addEvents();
    }

    // ==========================================
    // 1. PHẦN TRÊN: TIÊU ĐỀ & BỘ LỌC TÌM KIẾM
    // ==========================================
    private void initNorthPanel() {
        JPanel pnlNorth = new JPanel(new BorderLayout(10, 10));
        pnlNorth.setBackground(COLOR_CREAM);

        // -- Tiêu đề --
        JLabel lblTitle = new JLabel("QUẢN LÝ KHÁCH HÀNG", JLabel.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(COLOR_CREAM);
        lblTitle.setOpaque(true);
        lblTitle.setBackground(COLOR_PRIMARY);
        lblTitle.setBorder(new EmptyBorder(15, 0, 15, 0));
        pnlNorth.add(lblTitle, BorderLayout.NORTH);

        // -- Khung Tìm Kiếm --
        JPanel pnlSearchContainer = new JPanel();
        pnlSearchContainer.setLayout(new BoxLayout(pnlSearchContainer, BoxLayout.Y_AXIS));
        pnlSearchContainer.setBackground(COLOR_CREAM);
        pnlSearchContainer.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(COLOR_DARK, 1), 
                " BỘ LỌC TÌM KIẾM ", 
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, 
                javax.swing.border.TitledBorder.DEFAULT_POSITION, 
                new Font("Segoe UI", Font.BOLD, 14), COLOR_DARK));

        // 1. Tìm kiếm cơ bản (Nửa trên)
        JPanel pnlBasicSearch = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        pnlBasicSearch.setBackground(COLOR_CREAM);
        txtBasicSearch = createStyledTextField();
        txtBasicSearch.setPreferredSize(new Dimension(400, 35));

        pnlBasicSearch.add(createLabel("Từ khóa chung (Ưu tiên):"));
        pnlBasicSearch.add(txtBasicSearch);

        // Đường kẻ phân cách
        JSeparator separator = new JSeparator();
        separator.setMaximumSize(new Dimension(1100, 2));
        separator.setForeground(new Color(200, 180, 200));

        // 2. Tìm kiếm nâng cao (Nửa dưới)
        JPanel pnlAdvSearch = new JPanel(new GridLayout(2, 6, 10, 10));
        pnlAdvSearch.setBackground(COLOR_CREAM);
        pnlAdvSearch.setBorder(new EmptyBorder(5, 15, 10, 15));

        txtAdvMa = createStyledTextField();
        txtAdvTen = createStyledTextField();
        txtAdvSdt = createStyledTextField();
        txtTuDiem = createStyledTextField();
        txtDenDiem = createStyledTextField();
        txtAdvDiaChi = createStyledTextField(); // Đã thêm ô tìm Địa chỉ

        // Dòng 1: Mã, Tên, SĐT
        pnlAdvSearch.add(createLabel("Mã KH:"));   pnlAdvSearch.add(txtAdvMa);
        pnlAdvSearch.add(createLabel("Họ Tên:"));  pnlAdvSearch.add(txtAdvTen);
        pnlAdvSearch.add(createLabel("SĐT:"));     pnlAdvSearch.add(txtAdvSdt);
        
        // Dòng 2: Từ điểm, Đến điểm, ĐỊA CHỈ
        pnlAdvSearch.add(createLabel("Từ Điểm:")); pnlAdvSearch.add(txtTuDiem);
        pnlAdvSearch.add(createLabel("Đến Điểm:"));pnlAdvSearch.add(txtDenDiem);
        pnlAdvSearch.add(createLabel("Địa Chỉ:")); pnlAdvSearch.add(txtAdvDiaChi); // Gắn vô chỗ trống cực đẹp

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

        // -- Form Nhập Liệu --
        JPanel pnlForm = new JPanel(new GridLayout(3, 4, 15, 15));
        pnlForm.setBackground(COLOR_CREAM);
        pnlForm.setBorder(new EmptyBorder(10, 0, 10, 0));

        txtMaKH = createStyledTextField(); txtMaKH.setEnabled(false);
        txtHoTen = createStyledTextField();
        txtSDT = createStyledTextField();
        txtEmail = createStyledTextField();
        txtDiaChi = createStyledTextField();
        txtDiem = createStyledTextField(); txtDiem.setEnabled(false);

        pnlForm.add(createLabel("Mã Khách Hàng:")); pnlForm.add(txtMaKH);
        pnlForm.add(createLabel("Họ và Tên (*):")); pnlForm.add(txtHoTen);
        
        pnlForm.add(createLabel("Số Điện Thoại (*):")); pnlForm.add(txtSDT);
        pnlForm.add(createLabel("Email:")); pnlForm.add(txtEmail);
        
        pnlForm.add(createLabel("Địa Chỉ:")); pnlForm.add(txtDiaChi);
        pnlForm.add(createLabel("Điểm Tích Lũy:")); pnlForm.add(txtDiem);

        // -- Bảng Dữ Liệu --
        String[] cols = {"STT", "Mã KH", "Họ Tên", "Số Điện Thoại", "Email", "Địa Chỉ", "Điểm TL"};
        tableModel = new DefaultTableModel(cols, 0) { @Override public boolean isCellEditable(int r, int c) { return false; } };
        tblKhachHang = new JTable(tableModel);
        tblKhachHang.setRowHeight(35);
        tblKhachHang.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tblKhachHang.setSelectionBackground(COLOR_LIGHT_PINK);
        tblKhachHang.setSelectionForeground(COLOR_DARK);
        tblKhachHang.setShowGrid(false); 
        tblKhachHang.setIntercellSpacing(new Dimension(0, 0));

        // Format Tiêu Đề
        JTableHeader tableHeader = tblKhachHang.getTableHeader();
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

        // Format Các Dòng Trong Bảng
        DefaultTableCellRenderer customRenderer = new DefaultTableCellRenderer() {
            @Override public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(245, 245, 250));
                    c.setForeground(Color.DARK_GRAY);
                }
                
                // Căn lề
                if(column == 2 || column == 4 || column == 5) {
                    setHorizontalAlignment(SwingConstants.LEFT);
                } else {
                    setHorizontalAlignment(SwingConstants.CENTER);
                }
                
                // Thêm viền xám nhạt dưới mỗi dòng
                ((JLabel) c).setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)));
                return c;
            }
        };

        for (int i = 0; i < tblKhachHang.getColumnCount(); i++) {
            tblKhachHang.getColumnModel().getColumn(i).setCellRenderer(customRenderer);
        }
        
        tblKhachHang.getColumnModel().getColumn(0).setMaxWidth(50);
        tblKhachHang.getColumnModel().getColumn(1).setMaxWidth(100);
        tblKhachHang.getColumnModel().getColumn(2).setPreferredWidth(180);

        JScrollPane scrollPane = new JScrollPane(tblKhachHang);
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
        btnXoa = createButton("Xóa (Ngừng HĐ)", new Color(220, 53, 69)); 
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
    private void loadDataToTable(ArrayList<KhachHangDTO> list) {
        currentList = list;
        tableModel.setRowCount(0);
        int stt = 1;
        for (KhachHangDTO kh : list) {
            tableModel.addRow(new Object[]{ stt++, kh.getMaKH(), kh.getHoTen(), kh.getSoDienThoai(), kh.getEmail(), kh.getDiaChi(), kh.getDiemTichLuy() });
        }
    }

    private void addEvents() {
        // Click Bảng -> Đổ dữ liệu lên Form
        tblKhachHang.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                int row = tblKhachHang.getSelectedRow();
                if (row >= 0) {
                    KhachHangDTO kh = currentList.get(row);
                    currentSelectedId = kh.getMaID();
                    
                    txtMaKH.setText(kh.getMaKH());
                    txtHoTen.setText(kh.getHoTen());
                    txtSDT.setText(kh.getSoDienThoai());
                    txtEmail.setText(kh.getEmail() != null ? kh.getEmail() : "");
                    txtDiaChi.setText(kh.getDiaChi() != null ? kh.getDiaChi() : "");
                    txtDiem.setText(String.valueOf(kh.getDiemTichLuy()));
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
        txtAdvMa.getDocument().addDocumentListener(dl);
        txtAdvTen.getDocument().addDocumentListener(dl);
        txtAdvSdt.getDocument().addDocumentListener(dl);
        txtTuDiem.getDocument().addDocumentListener(dl);
        txtDenDiem.getDocument().addDocumentListener(dl);
        txtAdvDiaChi.getDocument().addDocumentListener(dl); // Đã gắn sự kiện cho ô Địa chỉ

        // NÚT THÊM
        btnThem.addActionListener(e -> {
            KhachHangDTO kh = new KhachHangDTO(txtHoTen.getText(), txtSDT.getText(), txtEmail.getText(), txtDiaChi.getText());
            String msg = khBus.addKhachHang(kh);
            JOptionPane.showMessageDialog(this, msg);
            if (msg.contains("thành công")) btnLamMoi.doClick();
        });

        // NÚT SỬA
        btnSua.addActionListener(e -> {
            if (currentSelectedId == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng trên bảng để sửa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            KhachHangDTO kh = new KhachHangDTO();
            kh.setMaID(currentSelectedId);
            kh.setHoTen(txtHoTen.getText());
            kh.setSoDienThoai(txtSDT.getText());
            kh.setEmail(txtEmail.getText());
            kh.setDiaChi(txtDiaChi.getText());

            String msg = khBus.updateKhachHang(kh);
            JOptionPane.showMessageDialog(this, msg);
            if (msg.contains("thành công")) btnLamMoi.doClick();
        });

        // NÚT XÓA
        btnXoa.addActionListener(e -> {
            if (currentSelectedId == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng cần xóa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (JOptionPane.showConfirmDialog(this, "Chuyển khách hàng này sang Ngừng Hoạt Động?", "Xác nhận", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                String msg = khBus.deleteKhachHang(currentSelectedId);
                JOptionPane.showMessageDialog(this, msg);
                btnLamMoi.doClick();
            }
        });

        // NÚT LÀM MỚI
        btnLamMoi.addActionListener(e -> {
            txtMaKH.setText(""); txtHoTen.setText(""); txtSDT.setText("");
            txtEmail.setText(""); txtDiaChi.setText(""); txtDiem.setText("");
            
            txtBasicSearch.setText("");
            txtAdvMa.setText(""); txtAdvTen.setText(""); txtAdvSdt.setText("");
            txtTuDiem.setText(""); txtDenDiem.setText("");
            txtAdvDiaChi.setText(""); // Xóa rỗng luôn ô địa chỉ
            
            currentSelectedId = -1;
            tblKhachHang.clearSelection();
            
            allList = khBus.getAllKhachHang();
            loadDataToTable(allList);
        });
    }

    private void searchRealtime() {
        String basic = txtBasicSearch.getText().trim().toLowerCase();
        String ma = txtAdvMa.getText().trim().toLowerCase();
        String ten = txtAdvTen.getText().trim().toLowerCase();
        String sdt = txtAdvSdt.getText().trim().toLowerCase();
        String diachi = txtAdvDiaChi.getText().trim().toLowerCase(); // Lấy giá trị địa chỉ
        
        int tu = 0; int den = Integer.MAX_VALUE;
        try { if(!txtTuDiem.getText().isEmpty()) tu = Integer.parseInt(txtTuDiem.getText().trim()); } catch(Exception e){}
        try { if(!txtDenDiem.getText().isEmpty()) den = Integer.parseInt(txtDenDiem.getText().trim()); } catch(Exception e){}

        ArrayList<KhachHangDTO> filtered = new ArrayList<>();
        
        for (KhachHangDTO k : allList) {
            boolean match = true;
            
            // Lấy chuỗi an toàn chống lỗi NullPointer
            String khEmail = k.getEmail() != null ? k.getEmail().toLowerCase() : "";
            String khDiaChi = k.getDiaChi() != null ? k.getDiaChi().toLowerCase() : "";
            String khDiem = String.valueOf(k.getDiemTichLuy());
            
            // 1. Nếu có nhập Tìm Nhanh thì quét TOÀN BỘ 6 CỘT
            if (!basic.isEmpty()) {
                if (!(k.getMaKH().toLowerCase().contains(basic) || 
                      k.getHoTen().toLowerCase().contains(basic) || 
                      k.getSoDienThoai().contains(basic) || 
                      khEmail.contains(basic) ||
                      khDiaChi.contains(basic) ||
                      khDiem.contains(basic))) {
                    match = false;
                }
            }
            
            // 2. Lọc theo ô nhập Nâng Cao
            if (!ma.isEmpty() && !k.getMaKH().toLowerCase().contains(ma)) match = false;
            if (!ten.isEmpty() && !k.getHoTen().toLowerCase().contains(ten)) match = false;
            if (!sdt.isEmpty() && !k.getSoDienThoai().contains(sdt)) match = false;
            if (!diachi.isEmpty() && !khDiaChi.contains(diachi)) match = false; // Lọc địa chỉ
            if (k.getDiemTichLuy() < tu || k.getDiemTichLuy() > den) match = false;

            if (match) filtered.add(k);
        }
        
        loadDataToTable(filtered);
    }
}