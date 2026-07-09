package GUI;

import BUS.LichSuKhoBUS;
import DTO.LichSuKhoDTO;
import com.toedter.calendar.JDateChooser;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class LichSuKhoGUI extends JPanel {

    // BỘ MÀU LUXURY
    private final Color COLOR_CREAM = new Color(248, 244, 236);
    private final Color COLOR_PRIMARY = new Color(232, 60, 145);
    private final Color COLOR_DARK = new Color(67, 51, 76);
    private final Color COLOR_LIGHT_PINK = new Color(255, 143, 183);

    // COMPONENTS TÌM KIẾM
    private JTextField txtBasicSearch;
    private JTextField txtAdvMaLSK, txtAdvMaSach;
    private JComboBox<String> cbxLoaiGD;
    private JDateChooser txtTuNgay, txtDenNgay;
    private JButton btnLamMoi;

    // COMPONENTS BẢNG
    private JTable tblLichSu;
    private DefaultTableModel tableModel;

    // DATA
    private LichSuKhoBUS lsBus = new LichSuKhoBUS();
    private ArrayList<LichSuKhoDTO> allList = new ArrayList<>();
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public LichSuKhoGUI() {
        setLayout(new BorderLayout(10, 10));
        setBackground(COLOR_CREAM);
        setBorder(new EmptyBorder(10, 10, 10, 10));

        initNorthPanel();
        initCenterPanel();

        allList = lsBus.getAll();
        loadDataToTable(allList);
        
        addEvents();
    }

    // ==========================================
    // 1. PHẦN TRÊN: TIÊU ĐỀ & BỘ LỌC TÌM KIẾM
    // ==========================================
    private void initNorthPanel() {
        JPanel pnlNorth = new JPanel(new BorderLayout(10, 10));
        pnlNorth.setBackground(COLOR_CREAM);

        JLabel lblTitle = new JLabel("LỊCH SỬ BIẾN ĐỘNG KHO", JLabel.CENTER);
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

        // TÌM KIẾM CƠ BẢN
        JPanel pnlBasicSearch = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        pnlBasicSearch.setBackground(COLOR_CREAM);
        txtBasicSearch = createStyledTextField();
        txtBasicSearch.setPreferredSize(new Dimension(400, 35));
        pnlBasicSearch.add(createLabel("Từ khóa chung (Ưu tiên):"));
        pnlBasicSearch.add(txtBasicSearch);

        JSeparator separator = new JSeparator();
        separator.setMaximumSize(new Dimension(1100, 2));
        separator.setForeground(new Color(200, 180, 200));

        // TÌM KIẾM NÂNG CAO
        JPanel pnlAdvSearch = new JPanel(new GridLayout(2, 6, 10, 15));
        pnlAdvSearch.setBackground(COLOR_CREAM);
        pnlAdvSearch.setBorder(new EmptyBorder(5, 20, 10, 20));

        txtAdvMaLSK = createStyledTextField();
        txtAdvMaSach = createStyledTextField();
        
        cbxLoaiGD = new JComboBox<>(new String[]{"Tất cả", "Nhập Hàng", "Bán Hàng", "Khách Trả", "Trả NCC", "Kiểm Kê Kho", "Phân Loại Lỗi"});
        cbxLoaiGD.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cbxLoaiGD.setBackground(Color.WHITE);
        
        // ĐÃ SỬA FONT CHO JDATECHOOSER TO RÕ RÀNG HƠN
        Font fontDate = new Font("Segoe UI", Font.PLAIN, 15);
        txtTuNgay = new JDateChooser(); 
        txtTuNgay.setDateFormatString("dd/MM/yyyy");
        txtTuNgay.setFont(fontDate);
        ((JTextField)txtTuNgay.getDateEditor().getUiComponent()).setFont(fontDate);

        txtDenNgay = new JDateChooser(); 
        txtDenNgay.setDateFormatString("dd/MM/yyyy");
        txtDenNgay.setFont(fontDate);
        ((JTextField)txtDenNgay.getDateEditor().getUiComponent()).setFont(fontDate);

        // ĐÃ ĐỔI HÀM TẠO NÚT ĐỂ ÉP MÀU HIỂN THỊ CHUẨN
        btnLamMoi = createButton("Làm Mới Bộ Lọc", COLOR_DARK);

        // ROW 1
        pnlAdvSearch.add(createLabel("Mã LSK:"));        pnlAdvSearch.add(txtAdvMaLSK);
        pnlAdvSearch.add(createLabel("Mã Sách:"));       pnlAdvSearch.add(txtAdvMaSach);
        pnlAdvSearch.add(createLabel("Loại Giao Dịch:"));pnlAdvSearch.add(cbxLoaiGD);

        // ROW 2
        pnlAdvSearch.add(createLabel("Từ ngày:"));       pnlAdvSearch.add(txtTuNgay);
        pnlAdvSearch.add(createLabel("Đến ngày:"));      pnlAdvSearch.add(txtDenNgay);
        pnlAdvSearch.add(new JLabel(""));                pnlAdvSearch.add(btnLamMoi); 

        pnlSearchContainer.add(pnlBasicSearch);
        pnlSearchContainer.add(Box.createRigidArea(new Dimension(0, 5)));
        pnlSearchContainer.add(separator);
        pnlSearchContainer.add(Box.createRigidArea(new Dimension(0, 5)));
        pnlSearchContainer.add(pnlAdvSearch);

        pnlNorth.add(pnlSearchContainer, BorderLayout.CENTER);
        add(pnlNorth, BorderLayout.NORTH);
    }

    // ==========================================
    // 2. PHẦN GIỮA: BẢNG DỮ LIỆU
    // ==========================================
    private void initCenterPanel() {
        JPanel pnlCenter = new JPanel(new BorderLayout(10, 10));
        pnlCenter.setBackground(COLOR_CREAM);

        String[] columnNames = {"Mã LSK", "Thời Gian", "Mã Sách", "Tên Sách", "Giao Dịch", "Thay Đổi", "Ghi Chú"};
        tableModel = new DefaultTableModel(columnNames, 0) { @Override public boolean isCellEditable(int r, int c) { return false; } };
        tblLichSu = new JTable(tableModel);
        tblLichSu.setRowHeight(40);
        tblLichSu.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tblLichSu.setSelectionBackground(COLOR_LIGHT_PINK);
        tblLichSu.setSelectionForeground(COLOR_DARK);
        tblLichSu.setShowGrid(false); 

        JTableHeader tableHeader = tblLichSu.getTableHeader();
        tableHeader.setPreferredSize(new Dimension(0, 45));
        tableHeader.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                label.setBackground(COLOR_DARK); label.setForeground(Color.WHITE); 
                label.setFont(new Font("Segoe UI", Font.BOLD, 14)); label.setHorizontalAlignment(SwingConstants.CENTER); 
                label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, new Color(255,255,255,50))); 
                label.setOpaque(true); return label;
            }
        });

        DefaultTableCellRenderer customRenderer = new DefaultTableCellRenderer() {
            @Override public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(245, 245, 250));
                    c.setForeground(Color.DARK_GRAY);
                }
                
                setHorizontalAlignment(column == 3 || column == 6 ? SwingConstants.LEFT : SwingConstants.CENTER);
                
                if (column == 5) {
                    String valStr = value != null ? value.toString() : "";
                    c.setFont(new Font("Segoe UI", Font.BOLD, 16));
                    if (valStr.startsWith("+")) c.setForeground(new Color(40, 167, 69)); 
                    else if (valStr.startsWith("-")) c.setForeground(new Color(220, 53, 69)); 
                    else c.setForeground(Color.DARK_GRAY);
                } else {
                    c.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                }
                
                ((JLabel) c).setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)));
                return c;
            }
        };

        for (int i = 0; i < tblLichSu.getColumnCount(); i++) {
            tblLichSu.getColumnModel().getColumn(i).setCellRenderer(customRenderer);
        }
        
        tblLichSu.getColumnModel().getColumn(0).setMaxWidth(100); 
        tblLichSu.getColumnModel().getColumn(1).setMaxWidth(150); 
        tblLichSu.getColumnModel().getColumn(2).setMaxWidth(100); 
        tblLichSu.getColumnModel().getColumn(5).setMaxWidth(100); 

        JScrollPane scrollPane = new JScrollPane(tblLichSu);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createLineBorder(COLOR_DARK, 1));

        pnlCenter.add(scrollPane, BorderLayout.CENTER);
        add(pnlCenter, BorderLayout.CENTER);
    }

    // ==========================================
    // HÀM TIỆN ÍCH
    // ==========================================
    private String dichGiaoDich(String dbCode) {
        if (dbCode == null) return "Khác";
        if (dbCode.equals("NHAP_HANG")) return "Nhập Hàng";
        if (dbCode.equals("BAN_HANG")) return "Bán Hàng";
        if (dbCode.equals("KHACH_TRA")) return "Khách Trả";
        if (dbCode.equals("TRA_NCC")) return "Trả NCC";
        if (dbCode.equals("KIEM_KE")) return "Kiểm Kê Kho";
        if (dbCode.equals("PHAN_LOAI_LOI")) return "Phân Loại Lỗi";
        return dbCode;
    }
    
    private JLabel createLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lbl.setForeground(COLOR_DARK);
        return lbl;
    }

    private JTextField createStyledTextField() {
        JTextField txt = new JTextField();
        txt.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        Border defaultBorder = BorderFactory.createCompoundBorder(new LineBorder(new Color(200, 200, 200), 1, true), new EmptyBorder(4, 8, 4, 8));
        Border focusBorder = BorderFactory.createCompoundBorder(new LineBorder(COLOR_PRIMARY, 2, true), new EmptyBorder(3, 7, 3, 7));
        txt.setBorder(defaultBorder);
        txt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) { txt.setBorder(focusBorder); txt.setBackground(Color.WHITE); }
            public void focusLost(java.awt.event.FocusEvent evt) { txt.setBorder(defaultBorder); txt.setBackground(Color.WHITE); }
        });
        return txt;
    }
    
    // NÚT BẤM CUSTOM ĐỂ ÉP GIAO DIỆN KHÔNG BỊ TRẮNG
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
        btn.setPreferredSize(new Dimension(150, 35));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void loadDataToTable(ArrayList<LichSuKhoDTO> list) {
        tableModel.setRowCount(0);
        for (LichSuKhoDTO ls : list) {
            String time = ls.getNgayGioTao() != null ? ls.getNgayGioTao().format(dtf) : "";
            
            int sl = ls.getSoLuongThayDoi();
            String slHienThi = (sl > 0) ? "+" + sl : String.valueOf(sl);
            
            tableModel.addRow(new Object[]{
                ls.getMaLSK(), time, ls.getMaSach_Chu(), ls.getTenSach(), 
                dichGiaoDich(ls.getLoaiGiaoDich()), slHienThi, ls.getGhiChu()
            });
        }
    }

    private void addEvents() {
        txtBasicSearch.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { searchData(); }
            public void removeUpdate(DocumentEvent e) { searchData(); }
            public void changedUpdate(DocumentEvent e) { searchData(); }
        });
        
        txtAdvMaLSK.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { searchData(); }
            public void removeUpdate(DocumentEvent e) { searchData(); }
            public void changedUpdate(DocumentEvent e) { searchData(); }
        });
        
        txtAdvMaSach.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { searchData(); }
            public void removeUpdate(DocumentEvent e) { searchData(); }
            public void changedUpdate(DocumentEvent e) { searchData(); }
        });

        cbxLoaiGD.addActionListener(e -> searchData());

        btnLamMoi.addActionListener(e -> {
            txtBasicSearch.setText("");
            txtAdvMaLSK.setText("");
            txtAdvMaSach.setText("");
            txtTuNgay.setDate(null);
            txtDenNgay.setDate(null);
            cbxLoaiGD.setSelectedIndex(0);
            
            allList = lsBus.getAll(); 
            loadDataToTable(allList);
        });
        
        txtTuNgay.getDateEditor().addPropertyChangeListener(e -> { if ("date".equals(e.getPropertyName())) searchData(); });
        txtDenNgay.getDateEditor().addPropertyChangeListener(e -> { if ("date".equals(e.getPropertyName())) searchData(); });
    }

    private void searchData() {
        String basic = txtBasicSearch.getText().trim().toLowerCase();
        String advMaLSK = txtAdvMaLSK.getText().trim().toLowerCase();
        String advMaSach = txtAdvMaSach.getText().trim().toLowerCase();
        String loaiGD = cbxLoaiGD.getSelectedItem().toString();
        
        Date tuNgay = txtTuNgay.getDate();
        Date denNgay = txtDenNgay.getDate();
        LocalDate fromDate = (tuNgay != null) ? tuNgay.toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null;
        LocalDate toDate = (denNgay != null) ? denNgay.toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null;

        ArrayList<LichSuKhoDTO> filtered = new ArrayList<>();
        for (LichSuKhoDTO ls : allList) {
            boolean match = true;

            // ========================================================
            // LOGIC MỚI: NẾU CÓ GÕ TÌM KIẾM CƠ BẢN -> ƯU TIÊN CHẠY MÌNH NÓ
            // ========================================================
            if (!basic.isEmpty()) {
                String tenS = ls.getTenSach() != null ? ls.getTenSach().toLowerCase() : "";
                String ghiChu = ls.getGhiChu() != null ? ls.getGhiChu().toLowerCase() : "";
                String thoiGian = ls.getNgayGioTao() != null ? ls.getNgayGioTao().format(dtf).toLowerCase() : "";
                
                if (!(ls.getMaLSK().toLowerCase().contains(basic) || 
                      ls.getMaSach_Chu().toLowerCase().contains(basic) || 
                      tenS.contains(basic) || ghiChu.contains(basic) ||
                      thoiGian.contains(basic))) {
                    match = false;
                }
            } 
            // ========================================================
            // NẾU Ô CƠ BẢN BỎ TRỐNG -> MỚI CHẠY BỘ LỌC NÂNG CAO
            // ========================================================
            else {
                if (!advMaLSK.isEmpty() && !ls.getMaLSK().toLowerCase().contains(advMaLSK)) match = false;
                if (!advMaSach.isEmpty() && !ls.getMaSach_Chu().toLowerCase().contains(advMaSach)) match = false;

                if (!loaiGD.equals("Tất cả")) {
                    if (!dichGiaoDich(ls.getLoaiGiaoDich()).equalsIgnoreCase(loaiGD)) match = false;
                }

                LocalDate logDate = ls.getNgayGioTao() != null ? ls.getNgayGioTao().toLocalDate() : null;
                if (logDate != null) {
                    if (fromDate != null && logDate.isBefore(fromDate)) match = false;
                    if (toDate != null && logDate.isAfter(toDate)) match = false;
                }
            }

            if (match) filtered.add(ls);
        }
        loadDataToTable(filtered);
    }
}