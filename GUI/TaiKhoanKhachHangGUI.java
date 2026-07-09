package GUI;

import BUS.TaiKhoanKhachHangBUS;
import DTO.TaiKhoanKhachHangDTO;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TaiKhoanKhachHangGUI extends JPanel {

    private JTable table;
    private DefaultTableModel model;
    private TaiKhoanKhachHangBUS tkBus = new TaiKhoanKhachHangBUS();

    // Thêm đầy đủ các ô tìm kiếm nâng cao
    private JTextField txtSearch, txtMa, txtTenDangNhap;
    private JComboBox<String> cbxTrangThai;
    private JDateChooser dateNgayTao;
    private JButton btnReset, btnToggle, btnResetPass;

    private ArrayList<TaiKhoanKhachHangDTO> allList = new ArrayList<>();
    private ArrayList<TaiKhoanKhachHangDTO> currentList = new ArrayList<>();

    private Color darkPurple = Color.decode("#5A4664");
    private Color bgContent = Color.decode("#F5F6FA");
    private SimpleDateFormat sdfFull = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private SimpleDateFormat sdfShort = new SimpleDateFormat("yyyy-MM-dd");

    public TaiKhoanKhachHangGUI() {
        setLayout(new BorderLayout(20, 20));
        setBackground(bgContent);
        setBorder(new EmptyBorder(25, 30, 25, 30));
        initUI();
        loadTable();
        initEvents();
    }

    private void initUI() {
        // ================= HEADER & FILTER =================
        JPanel pnlTop = new JPanel(new BorderLayout(0, 20)); 
        pnlTop.setOpaque(false);
        JLabel lblTitle = new JLabel("QUẢN LÝ TÀI KHOẢN KHÁCH HÀNG");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 28)); 
        lblTitle.setForeground(darkPurple);
        pnlTop.add(lblTitle, BorderLayout.NORTH);

        JPanel pnlFilterCard = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create(); g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE); g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.setColor(Color.decode("#DCDDE1")); g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20); g2.dispose();
            }
        };
        pnlFilterCard.setLayout(new BoxLayout(pnlFilterCard, BoxLayout.Y_AXIS)); 
        pnlFilterCard.setOpaque(false); 
        pnlFilterCard.setBorder(new EmptyBorder(15, 20, 15, 20));

        // Dòng 1: Tìm Nhanh + Trạng Thái
        JPanel pnlRow1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5)); 
        pnlRow1.setOpaque(false);
        txtSearch = new JTextField(25);
        cbxTrangThai = new JComboBox<>(new String[]{"Tất cả", "Hoạt Động", "Đã Khóa"});
        
        pnlRow1.add(createFilterField("TÌM KIẾM NHANH", txtSearch, Color.WHITE, Color.decode("#DCDDE1")));
        pnlRow1.add(createFilterField("Trạng Thái", cbxTrangThai, Color.WHITE, Color.decode("#DCDDE1")));

        // Dòng 2: Mã + Tên + Ngày Tạo
        JPanel pnlRow2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5)); 
        pnlRow2.setOpaque(false);
        txtMa = new JTextField(12);
        txtTenDangNhap = new JTextField(20);
        
        dateNgayTao = new JDateChooser();
        dateNgayTao.setDateFormatString("yyyy-MM-dd");
        dateNgayTao.setOpaque(false);

        pnlRow2.add(createFilterField("Mã TKKH", txtMa, Color.WHITE, Color.decode("#DCDDE1")));
        pnlRow2.add(createFilterField("Tên Đăng Nhập", txtTenDangNhap, Color.WHITE, Color.decode("#DCDDE1")));
        pnlRow2.add(createFilterField("Ngày Tạo", dateNgayTao, Color.WHITE, Color.decode("#DCDDE1")));

        pnlFilterCard.add(pnlRow1); 
        pnlFilterCard.add(Box.createVerticalStrut(5));
        pnlFilterCard.add(pnlRow2);
        
        pnlTop.add(pnlFilterCard, BorderLayout.CENTER); 
        add(pnlTop, BorderLayout.NORTH);

        // ================= TABLE =================
        String[] cols = {"STT", "Mã TKKH", "Tên Đăng Nhập", "Trạng Thái", "Ngày Tạo"}; 
        model = new DefaultTableModel(cols, 0) { @Override public boolean isCellEditable(int r, int c) { return false; } };
        
        table = new JTable(model); 
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14)); 
        table.setRowHeight(45);
        table.setShowGrid(false); 
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setSelectionBackground(Color.decode("#FFF0F5")); 
        table.setSelectionForeground(darkPurple);

        JTableHeader header = table.getTableHeader(); 
        header.setPreferredSize(new Dimension(0, 45));
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                label.setBackground(darkPurple); label.setForeground(Color.WHITE); label.setFont(new Font("Segoe UI", Font.BOLD, 14));
                label.setHorizontalAlignment(SwingConstants.CENTER); label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.decode("#413249"))); return label;
            }
        });

        DefaultTableCellRenderer customRenderer = new DefaultTableCellRenderer() {
            @Override public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setHorizontalAlignment(SwingConstants.CENTER);
                if (!isSelected) { 
                    c.setBackground(row % 2 == 0 ? Color.WHITE : Color.decode("#F9F9FB")); 
                    c.setForeground(Color.DARK_GRAY); 
                }
                
                if (column == 1) { 
                    c.setForeground(darkPurple); setFont(new Font("Segoe UI", Font.BOLD, 14)); 
                } else if (column == 2) { 
                    setHorizontalAlignment(SwingConstants.LEFT); 
                    setFont(new Font("Segoe UI", Font.BOLD, 14));
                } else if (column == 3) {
                    String val = value != null ? value.toString() : "";
                    if(val.contains("Hoạt Động")) c.setForeground(new Color(40, 167, 69));
                    else c.setForeground(new Color(220, 53, 69));
                    setFont(new Font("Segoe UI", Font.BOLD, 14));
                }
                
                ((JLabel) c).setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.decode("#EEEEEE"))); return c;
            }
        };

        for (int i = 0; i < table.getColumnCount(); i++) table.getColumnModel().getColumn(i).setCellRenderer(customRenderer);
        table.getColumnModel().getColumn(0).setMaxWidth(60);
        table.getColumnModel().getColumn(1).setPreferredWidth(120); 
        table.getColumnModel().getColumn(2).setPreferredWidth(250);

        JScrollPane scrollPane = new JScrollPane(table); 
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.decode("#DCDDE1"), 1)); 
        add(scrollPane, BorderLayout.CENTER);

        // ================= BOTTOM BUTTONS =================
        JPanel pnlBottom = new JPanel(new BorderLayout()); 
        pnlBottom.setOpaque(false); 
        pnlBottom.setBorder(new EmptyBorder(15, 0, 0, 0));

        JPanel pnlLeft = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0)); 
        pnlLeft.setOpaque(false);
        btnToggle = createButton("KHÓA / MỞ KHÓA", new Color(220, 53, 69));
        btnResetPass = createButton("CẤP LẠI MẬT KHẨU", new Color(0, 153, 255));
        pnlLeft.add(btnToggle); 
        pnlLeft.add(btnResetPass); 

        JPanel pnlRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0)); 
        pnlRight.setOpaque(false);
        btnReset = createButton("LÀM MỚI BẢNG", new Color(46, 204, 113)); 
        pnlRight.add(btnReset);

        pnlBottom.add(pnlLeft, BorderLayout.WEST); 
        pnlBottom.add(pnlRight, BorderLayout.EAST); 
        add(pnlBottom, BorderLayout.SOUTH);
    }

    // Hỗ trợ hiển thị JDateChooser chuẩn đẹp
    private JPanel createFilterField(String title, JComponent comp, Color bgColor, Color borderColor) {
        JPanel pnl = new JPanel(new BorderLayout(0, 8)); pnl.setOpaque(false);
        JLabel lbl = new JLabel(title); lbl.setFont(new Font("Segoe UI", Font.BOLD, 12)); lbl.setForeground(darkPurple); pnl.add(lbl, BorderLayout.NORTH);
        JPanel wrapper = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create(); g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(bgColor); g2.fillRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 10, 10);
                g2.setColor(borderColor); g2.setStroke(new BasicStroke(1.2f)); g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 10, 10); g2.dispose();
            }
        };
        wrapper.setOpaque(false); wrapper.setBorder(new EmptyBorder(6, 12, 6, 12));
        
        if (comp instanceof JTextField) {
            JTextField txt = (JTextField) comp; txt.setFont(new Font("Segoe UI", Font.PLAIN, 14)); txt.setBorder(null); txt.setOpaque(false);
            wrapper.add(txt, BorderLayout.CENTER);
        } else if (comp instanceof JComboBox) {
            JComboBox<?> cbx = (JComboBox<?>) comp; cbx.setFont(new Font("Segoe UI", Font.PLAIN, 14)); cbx.setBorder(null); cbx.setBackground(bgColor);
            comp.setPreferredSize(new Dimension(150, 28)); wrapper.add(cbx, BorderLayout.CENTER);
        } else if (comp instanceof JDateChooser) {
            JDateChooser jd = (JDateChooser) comp; jd.setFont(new Font("Segoe UI", Font.PLAIN, 14)); jd.setOpaque(false);
            for (Component c : jd.getComponents()) { if (c instanceof JTextField) { ((JTextField) c).setBorder(null); ((JTextField) c).setOpaque(false); } }
            comp.setPreferredSize(new Dimension(145, 28)); wrapper.add(jd, BorderLayout.CENTER);
        }
        pnl.add(wrapper, BorderLayout.CENTER); return pnl;
    }

    private JButton createButton(String text, Color bg) {
        JButton btn = new JButton(text) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create(); g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isRollover() ? bg.darker() : bg); g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10); g2.dispose(); super.paintComponent(g);
            }
        };
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13)); btn.setForeground(Color.WHITE); btn.setContentAreaFilled(false);
        btn.setBorderPainted(false); btn.setFocusPainted(false); btn.setPreferredSize(new Dimension(170, 40)); btn.setCursor(new Cursor(Cursor.HAND_CURSOR)); return btn;
    }

    private void loadTable(){ 
        allList = tkBus.getAll(); 
        showTable(allList); 
    }
    
    private void showTable(ArrayList<TaiKhoanKhachHangDTO> list){
        currentList = list; 
        model.setRowCount(0);
        int stt = 1;
        for(TaiKhoanKhachHangDTO tk : list) {
            String status = tk.getTrangThai().equals("HoatDong") ? "Hoạt Động" : "Đã Khóa";
            String dateStr = tk.getNgayTao() != null ? sdfFull.format(tk.getNgayTao()) : "";
            model.addRow(new Object[]{ stt++, tk.getMaTKKH(), tk.getTenDangNhap(), status, dateStr });
        }
    }

    private void initEvents(){
        DocumentListener dl = new DocumentListener() {
            public void insertUpdate(DocumentEvent e){ searchAdvanced(); }
            public void removeUpdate(DocumentEvent e){ searchAdvanced(); }
            public void changedUpdate(DocumentEvent e){ searchAdvanced(); }
        };
        
        txtSearch.getDocument().addDocumentListener(dl);
        txtMa.getDocument().addDocumentListener(dl);
        txtTenDangNhap.getDocument().addDocumentListener(dl);
        cbxTrangThai.addActionListener(e -> searchAdvanced());
        
        // Bắt sự kiện chọn lịch
        dateNgayTao.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {
            @Override public void propertyChange(PropertyChangeEvent e) {
                if ("date".equals(e.getPropertyName())) searchAdvanced();
            }
        });
        
        btnReset.addActionListener(e -> { 
            txtSearch.setText(""); txtMa.setText(""); txtTenDangNhap.setText(""); 
            cbxTrangThai.setSelectedIndex(0); dateNgayTao.setDate(null); 
            loadTable(); 
        }); 

        btnToggle.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) { JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 tài khoản trên bảng để thao tác!", "Cảnh báo", JOptionPane.WARNING_MESSAGE); return; }
            
            TaiKhoanKhachHangDTO tk = currentList.get(row);
            String action = tk.getTrangThai().equals("HoatDong") ? "KHÓA" : "MỞ KHÓA";
            
            if (JOptionPane.showConfirmDialog(this, "Sếp có chắc chắn muốn " + action + " tài khoản [" + tk.getTenDangNhap() + "] không?", "Xác nhận", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(this, tkBus.toggleStatus(tk));
                loadTable();
            }
        });

        btnResetPass.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) { JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 tài khoản để cấp lại mật khẩu!", "Cảnh báo", JOptionPane.WARNING_MESSAGE); return; }
            
            TaiKhoanKhachHangDTO tk = currentList.get(row);
            if (JOptionPane.showConfirmDialog(this, "Xác nhận RESET mật khẩu của [" + tk.getTenDangNhap() + "] về mặc định (123456)?", "Xác nhận", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(this, tkBus.resetPass(tk.getMaID()));
            }
        });
    }

    private void searchAdvanced(){
        String key = txtSearch.getText().trim().toLowerCase(); 
        String ma = txtMa.getText().trim().toLowerCase();
        String ten = txtTenDangNhap.getText().trim().toLowerCase();
        int statusIdx = cbxTrangThai.getSelectedIndex();
        Date selectedDate = dateNgayTao.getDate();
        String dateSearch = selectedDate != null ? sdfShort.format(selectedDate) : "";
        
        ArrayList<TaiKhoanKhachHangDTO> filtered = new ArrayList<>();
        for(TaiKhoanKhachHangDTO tk : allList){
            boolean match = true; 
            
            if(!key.isEmpty()) {
                String dateStr = tk.getNgayTao() != null ? sdfShort.format(tk.getNgayTao()) : "";
                if(!(tk.getMaTKKH().toLowerCase().contains(key) || 
                     tk.getTenDangNhap().toLowerCase().contains(key) || 
                     dateStr.contains(key))) {
                    match = false;
                }
            }
            
            if(!ma.isEmpty() && !tk.getMaTKKH().toLowerCase().contains(ma)) match = false;
            if(!ten.isEmpty() && !tk.getTenDangNhap().toLowerCase().contains(ten)) match = false;
            
            if(statusIdx == 1 && !tk.getTrangThai().equals("HoatDong")) match = false;
            if(statusIdx == 2 && !tk.getTrangThai().equals("Khoa")) match = false;
            
            if(selectedDate != null) {
                String tkDate = tk.getNgayTao() != null ? sdfShort.format(tk.getNgayTao()) : "";
                if(!tkDate.equals(dateSearch)) match = false;
            }
            
            if(match) filtered.add(tk);
        }
        showTable(filtered);
    }
}