package GUI;

import BUS.TonKhoBUS;
import DTO.TonKhoDTO;
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

public class TonKhoGUI extends JPanel {

    // BỘ MÀU LUXURY ĐỒNG BỘ
    private final Color COLOR_CREAM = new Color(248, 244, 236);
    private final Color COLOR_PRIMARY = new Color(232, 60, 145);
    private final Color COLOR_DARK = new Color(67, 51, 76);
    private final Color COLOR_LIGHT_PINK = new Color(255, 143, 183);

    // COMPONENTS
    private JTextField txtMaSach, txtTenSach, txtTonKhoHT, txtTonKhoTT, txtLyDo;
    private JButton btnKiemKe, btnLamMoi;

    private JTextField txtBasicSearch;
    private JTextField txtAdvMa, txtAdvTen, txtAdvTonTu, txtAdvTonDen;

    private JTable tblTonKho;
    private DefaultTableModel tableModel;

    // DATA
    private TonKhoBUS tkBus = new TonKhoBUS();
    private ArrayList<TonKhoDTO> allList = new ArrayList<>();
    private ArrayList<TonKhoDTO> currentList = new ArrayList<>();

    public TonKhoGUI() {
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

    private void initNorthPanel() {
        JPanel pnlNorth = new JPanel(new BorderLayout(10, 10));
        pnlNorth.setBackground(COLOR_CREAM);

        JLabel lblTitle = new JLabel("QUẢN LÝ TỒN KHO & KIỂM KÊ", JLabel.CENTER);
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
        pnlBasicSearch.add(createLabel("Từ khóa chung (Mã, Tên):"));
        pnlBasicSearch.add(txtBasicSearch);

        JSeparator separator = new JSeparator();
        separator.setMaximumSize(new Dimension(1100, 2));
        separator.setForeground(new Color(200, 180, 200));

        JPanel pnlAdvSearch = new JPanel(new GridLayout(1, 8, 10, 10));
        pnlAdvSearch.setBackground(COLOR_CREAM);
        pnlAdvSearch.setBorder(new EmptyBorder(5, 15, 10, 15));

        txtAdvMa = createStyledTextField();
        txtAdvTen = createStyledTextField();
        txtAdvTonTu = createStyledTextField();
        txtAdvTonDen = createStyledTextField();

        pnlAdvSearch.add(createLabel("Mã Sách:"));   pnlAdvSearch.add(txtAdvMa);
        pnlAdvSearch.add(createLabel("Tên Sách:"));  pnlAdvSearch.add(txtAdvTen);
        pnlAdvSearch.add(createLabel("Tồn Kho TỪ:")); pnlAdvSearch.add(txtAdvTonTu);
        pnlAdvSearch.add(createLabel("Tồn Kho ĐẾN:"));pnlAdvSearch.add(txtAdvTonDen);

        pnlSearchContainer.add(pnlBasicSearch);
        pnlSearchContainer.add(Box.createRigidArea(new Dimension(0, 5)));
        pnlSearchContainer.add(separator);
        pnlSearchContainer.add(Box.createRigidArea(new Dimension(0, 5)));
        pnlSearchContainer.add(pnlAdvSearch);

        pnlNorth.add(pnlSearchContainer, BorderLayout.CENTER);
        add(pnlNorth, BorderLayout.NORTH);
    }

    private void initCenterPanel() {
        JPanel pnlCenter = new JPanel(new BorderLayout(10, 10));
        pnlCenter.setBackground(COLOR_CREAM);

        // FORM KIỂM KÊ
        JPanel pnlForm = new JPanel(new GridLayout(2, 6, 15, 15));
        pnlForm.setBackground(COLOR_CREAM);
        pnlForm.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(COLOR_DARK, 1), 
                " NGHIỆP VỤ KIỂM KÊ KHO ", 
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, 
                javax.swing.border.TitledBorder.DEFAULT_POSITION, 
                new Font("Segoe UI", Font.BOLD, 14), COLOR_PRIMARY));

        txtMaSach = createStyledTextField(); txtMaSach.setEnabled(false);
        txtTenSach = createStyledTextField(); txtTenSach.setEnabled(false);
        txtTonKhoHT = createStyledTextField(); txtTonKhoHT.setEnabled(false);
        txtTonKhoHT.setFont(new Font("Segoe UI", Font.BOLD, 16));
        txtTonKhoHT.setDisabledTextColor(Color.RED);
        
        txtTonKhoTT = createStyledTextField(); 
        txtLyDo = createStyledTextField(); 

        pnlForm.add(createLabel("Mã Sách:")); pnlForm.add(txtMaSach);
        pnlForm.add(createLabel("Tên Sách:")); pnlForm.add(txtTenSach);
        pnlForm.add(createLabel("Tồn Kho Hệ Thống:")); pnlForm.add(txtTonKhoHT);
        
        pnlForm.add(createLabel("Tồn Kho Thực Tế (*):")); pnlForm.add(txtTonKhoTT);
        pnlForm.add(createLabel("Lý do chênh lệch (*):")); pnlForm.add(txtLyDo);
        pnlForm.add(new JLabel("")); pnlForm.add(new JLabel("")); // Cột trống cho cân

        // BẢNG
        String[] columnNames = {"ID", "Mã Sách", "Tên Sách", "Giá Vốn (VND)", "Tồn Kho Hiện Tại", "Tình Trạng"};
        tableModel = new DefaultTableModel(columnNames, 0) { @Override public boolean isCellEditable(int r, int c) { return false; } };
        tblTonKho = new JTable(tableModel);
        tblTonKho.setRowHeight(35);
        tblTonKho.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tblTonKho.setSelectionBackground(COLOR_LIGHT_PINK);
        tblTonKho.setSelectionForeground(COLOR_DARK);
        tblTonKho.setShowGrid(false); 

        JTableHeader tableHeader = tblTonKho.getTableHeader();
        tableHeader.setPreferredSize(new Dimension(0, 40));
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
                
                setHorizontalAlignment(column == 2 ? SwingConstants.LEFT : SwingConstants.CENTER);
                
                // Đổi màu thông minh theo tình trạng
                if (column == 5) {
                    String tt = value.toString();
                    if (tt.equals("Hết hàng")) c.setForeground(new Color(220, 53, 69)); // Đỏ
                    else if (tt.equals("Sắp hết")) c.setForeground(new Color(255, 153, 0)); // Cam
                    else c.setForeground(new Color(40, 167, 69)); // Xanh lá
                    c.setFont(new Font("Segoe UI", Font.BOLD, 14));
                } else if (column == 4) { // Cột tồn kho in đậm
                    c.setFont(new Font("Segoe UI", Font.BOLD, 15));
                } else {
                    c.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                }
                
                ((JLabel) c).setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)));
                return c;
            }
        };

        for (int i = 0; i < tblTonKho.getColumnCount(); i++) {
            tblTonKho.getColumnModel().getColumn(i).setCellRenderer(customRenderer);
        }
        
        tblTonKho.getColumnModel().getColumn(0).setMinWidth(0); tblTonKho.getColumnModel().getColumn(0).setMaxWidth(0);
        tblTonKho.getColumnModel().getColumn(1).setMaxWidth(100); tblTonKho.getColumnModel().getColumn(4).setMaxWidth(150);

        JScrollPane scrollPane = new JScrollPane(tblTonKho);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createLineBorder(COLOR_DARK, 1));

        pnlCenter.add(pnlForm, BorderLayout.NORTH);
        pnlCenter.add(scrollPane, BorderLayout.CENTER);
        add(pnlCenter, BorderLayout.CENTER);
    }

    private void initSouthPanel() {
        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        pnlButtons.setBackground(COLOR_CREAM);

        btnKiemKe = createButton("XÁC NHẬN KIỂM KÊ", COLOR_PRIMARY);
        btnLamMoi = createButton("Làm Mới Danh Sách", COLOR_DARK);

        pnlButtons.add(btnKiemKe);
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
                g2.dispose(); super.paintComponent(g);
            }
        };
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14)); btn.setForeground(Color.WHITE); 
        btn.setContentAreaFilled(false); btn.setBorderPainted(false); btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(200, 40)); btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
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
    // LOGIC DỮ LIỆU & SỰ KIỆN
    // ==========================================
    private void loadDataToTable(ArrayList<TonKhoDTO> danhSach) {
        currentList = danhSach;
        tableModel.setRowCount(0);
        for (TonKhoDTO tk : danhSach) {
            String tinhTrang = "Còn hàng";
            if (tk.getSoLuongTon() == 0) tinhTrang = "Hết hàng";
            else if (tk.getSoLuongTon() <= 5) tinhTrang = "Sắp hết";
            
            tableModel.addRow(new Object[]{
                tk.getMaID(), tk.getMaSach(), tk.getTenSach(), 
                String.format("%,.0f", tk.getGiaNhap()), tk.getSoLuongTon(), tinhTrang
            });
        }
    }

    private void addEvents() {
        // Click Bảng
        tblTonKho.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                int row = tblTonKho.getSelectedRow();
                if (row >= 0) {
                    TonKhoDTO tk = currentList.get(row);
                    txtMaSach.setText(tk.getMaSach());
                    txtTenSach.setText(tk.getTenSach());
                    txtTonKhoHT.setText(String.valueOf(tk.getSoLuongTon()));
                    txtTonKhoTT.setText(""); // Reset cho người dùng nhập mới
                    txtLyDo.setText("");
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
        txtAdvTonTu.getDocument().addDocumentListener(dl);
        txtAdvTonDen.getDocument().addDocumentListener(dl);

        // Nút Kiểm kê
        btnKiemKe.addActionListener(e -> {
            int row = tblTonKho.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một cuốn sách trên bảng để kiểm kê!");
                return;
            }
            
            int maID = Integer.parseInt(tblTonKho.getValueAt(row, 0).toString());
            int slHeThong = Integer.parseInt(txtTonKhoHT.getText());
            String slThucTe = txtTonKhoTT.getText();
            String lyDo = txtLyDo.getText();
            
            int confirm = JOptionPane.showConfirmDialog(this, "Hệ thống sẽ ghi nhận lịch sử kiểm kê cho sách này. Bạn có chắc chắn không?", "Xác nhận kiểm kê", JOptionPane.YES_NO_OPTION);
            if(confirm == JOptionPane.YES_OPTION) {
                String msg = tkBus.kiemKe(maID, slHeThong, slThucTe, lyDo);
                JOptionPane.showMessageDialog(this, msg);
                if(msg.contains("thành công")) {
                    btnLamMoi.doClick(); // Tải lại bảng ngay lập tức
                }
            }
        });

        // Nút Làm mới
        btnLamMoi.addActionListener(e -> {
            txtMaSach.setText(""); txtTenSach.setText(""); txtTonKhoHT.setText("");
            txtTonKhoTT.setText(""); txtLyDo.setText("");
            
            txtBasicSearch.setText(""); txtAdvMa.setText(""); txtAdvTen.setText("");
            txtAdvTonTu.setText(""); txtAdvTonDen.setText("");
            
            tblTonKho.clearSelection();
            allList = tkBus.getDanhSach();
            loadDataToTable(allList);
        });
    }

    private void searchRealtime() {
        String basic = txtBasicSearch.getText().trim().toLowerCase();
        String ma = txtAdvMa.getText().trim().toLowerCase();
        String ten = txtAdvTen.getText().trim().toLowerCase();
        
        int tonTu = -1, tonDen = -1;
        try { if (!txtAdvTonTu.getText().trim().isEmpty()) tonTu = Integer.parseInt(txtAdvTonTu.getText().trim()); } catch (Exception e) {}
        try { if (!txtAdvTonDen.getText().trim().isEmpty()) tonDen = Integer.parseInt(txtAdvTonDen.getText().trim()); } catch (Exception e) {}

        ArrayList<TonKhoDTO> filtered = new ArrayList<>();
        for (TonKhoDTO tk : allList) {
            boolean match = true;

            if (!basic.isEmpty()) {
                if (!(tk.getMaSach().toLowerCase().contains(basic) || tk.getTenSach().toLowerCase().contains(basic))) match = false;
            }
            if (!ma.isEmpty() && !tk.getMaSach().toLowerCase().contains(ma)) match = false;
            if (!ten.isEmpty() && !tk.getTenSach().toLowerCase().contains(ten)) match = false;
            if (tonTu >= 0 && tk.getSoLuongTon() < tonTu) match = false;
            if (tonDen >= 0 && tk.getSoLuongTon() > tonDen) match = false;

            if (match) filtered.add(tk);
        }
        loadDataToTable(filtered);
    }
}