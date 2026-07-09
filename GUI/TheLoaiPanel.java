package GUI; 

import BUS.TheLoaiBUS; 
import DTO.TheLoaiDTO; 

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class TheLoaiPanel extends JPanel {

    private JTable table;
    private DefaultTableModel model;
    private TheLoaiBUS bus = new TheLoaiBUS();

    private JTextField txtSearch, txtMa, txtTen, txtMaDM;
    private JButton btnReset, btnAdd, btnDelete;

    private List<TheLoaiDTO> allTheLoai = new ArrayList<>();
    private List<TheLoaiDTO> currentList = new ArrayList<>();

    private Color darkPurple = Color.decode("#5A4664");
    private Color bgContent = Color.decode("#F5F6FA");

    public TheLoaiPanel() {
        setLayout(new BorderLayout(20, 20));
        setBackground(bgContent);
        setBorder(new EmptyBorder(25, 30, 25, 30));
        initUI();
        loadTable();
        initEvents();
    }

    private void initUI() {
        JPanel pnlTop = new JPanel(new BorderLayout(0, 20));
        pnlTop.setOpaque(false);

        JLabel lblTitle = new JLabel("QUẢN LÝ DANH MỤC THỂ LOẠI");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitle.setForeground(darkPurple);
        pnlTop.add(lblTitle, BorderLayout.NORTH);

        JPanel pnlFilterCard = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE); g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.setColor(Color.decode("#DCDDE1")); g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20); g2.dispose();
            }
        };
        pnlFilterCard.setLayout(new BoxLayout(pnlFilterCard, BoxLayout.Y_AXIS));
        pnlFilterCard.setOpaque(false); pnlFilterCard.setBorder(new EmptyBorder(15, 20, 15, 20));

        JPanel pnlRow1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5)); pnlRow1.setOpaque(false);
        txtSearch = new JTextField(35);
        pnlRow1.add(createFilterField("TÌM KIẾM NHANH", txtSearch, Color.WHITE, Color.decode("#DCDDE1")));

        JPanel pnlRow2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5)); pnlRow2.setOpaque(false);
        txtMa = new JTextField(12); txtTen = new JTextField(20); txtMaDM = new JTextField(15);
        
        pnlRow2.add(createFilterField("Mã Thể Loại", txtMa, Color.WHITE, Color.decode("#DCDDE1")));
        pnlRow2.add(createFilterField("Tên Thể Loại", txtTen, Color.WHITE, Color.decode("#DCDDE1")));
        pnlRow2.add(createFilterField("Tên Danh Mục", txtMaDM, Color.WHITE, Color.decode("#DCDDE1")));

        pnlFilterCard.add(pnlRow1); pnlFilterCard.add(Box.createVerticalStrut(5)); pnlFilterCard.add(pnlRow2);
        pnlTop.add(pnlFilterCard, BorderLayout.CENTER); add(pnlTop, BorderLayout.NORTH);

        String[] cols = {"Mã thể loại", "Tên thể loại", "Tên danh mục"}; 
        model = new DefaultTableModel(cols, 0) { @Override public boolean isCellEditable(int r, int c) { return false; } };
        
        table = new JTable(model); table.setFont(new Font("Segoe UI", Font.PLAIN, 15)); table.setRowHeight(50);
        table.setShowGrid(false); table.setIntercellSpacing(new Dimension(0, 0));
        table.setSelectionBackground(Color.decode("#FFF0F5")); table.setSelectionForeground(darkPurple);

        JTableHeader header = table.getTableHeader(); header.setPreferredSize(new Dimension(0, 50));
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                label.setBackground(darkPurple); label.setForeground(Color.WHITE); label.setFont(new Font("Segoe UI", Font.BOLD, 15));
                label.setHorizontalAlignment(SwingConstants.CENTER); label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.decode("#413249"))); return label;
            }
        });

        DefaultTableCellRenderer customRenderer = new DefaultTableCellRenderer() {
            @Override public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setHorizontalAlignment(SwingConstants.CENTER);
                if (!isSelected) { c.setBackground(row % 2 == 0 ? Color.WHITE : Color.decode("#F9F9FB")); c.setForeground(Color.DARK_GRAY); }
                if (column == 0) { c.setForeground(darkPurple); setFont(new Font("Segoe UI", Font.BOLD, 15)); } 
                else if (column == 1) { setHorizontalAlignment(SwingConstants.LEFT); setFont(new Font("Segoe UI", Font.BOLD, 14)); } 
                else { setFont(new Font("Segoe UI", Font.PLAIN, 15)); }
                ((JLabel) c).setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.decode("#EEEEEE"))); return c;
            }
        };

        for (int i = 0; i < table.getColumnCount(); i++) table.getColumnModel().getColumn(i).setCellRenderer(customRenderer);
        table.getColumnModel().getColumn(1).setPreferredWidth(250);

        JScrollPane scrollPane = new JScrollPane(table); scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.decode("#DCDDE1"), 1)); add(scrollPane, BorderLayout.CENTER);

        JPanel pnlBottom = new JPanel(new BorderLayout()); pnlBottom.setOpaque(false); pnlBottom.setBorder(new EmptyBorder(15, 0, 0, 0));

        JPanel pnlLeft = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0)); pnlLeft.setOpaque(false);
        btnAdd = createButton("THÊM THỂ LOẠI", new Color(40, 167, 69));
        btnDelete = createButton("XÓA THỂ LOẠI", new Color(220, 53, 69));
        pnlLeft.add(btnAdd); pnlLeft.add(btnDelete); 

        JPanel pnlRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0)); pnlRight.setOpaque(false);
        btnReset = createButton("LÀM MỚI FORM", new Color(46, 204, 113)); pnlRight.add(btnReset);

        pnlBottom.add(pnlLeft, BorderLayout.WEST); pnlBottom.add(pnlRight, BorderLayout.EAST); add(pnlBottom, BorderLayout.SOUTH);
    }

    private JPanel createFilterField(String title, JComponent comp, Color bgColor, Color borderColor) {
        JPanel pnl = new JPanel(new BorderLayout(0, 8)); pnl.setOpaque(false);
        JLabel lbl = new JLabel(title); lbl.setFont(new Font("Segoe UI", Font.BOLD, 13)); lbl.setForeground(darkPurple);
        pnl.add(lbl, BorderLayout.NORTH);

        JPanel wrapper = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create(); g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(bgColor); g2.fillRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 10, 10);
                g2.setColor(borderColor); g2.setStroke(new BasicStroke(1.2f)); g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 10, 10); g2.dispose();
            }
        };
        wrapper.setOpaque(false); wrapper.setBorder(new EmptyBorder(6, 15, 6, 15));
        JTextField txt = (JTextField) comp; txt.setFont(new Font("Segoe UI", Font.PLAIN, 15)); txt.setBorder(null); txt.setOpaque(false);
        wrapper.add(txt, BorderLayout.CENTER); pnl.add(wrapper, BorderLayout.CENTER); return pnl;
    }

    private JButton createButton(String text, Color bg) {
        JButton btn = new JButton(text) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create(); g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isRollover() ? bg.darker() : bg); g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10); g2.dispose(); super.paintComponent(g);
            }
        };
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14)); btn.setForeground(Color.WHITE); btn.setContentAreaFilled(false);
        btn.setBorderPainted(false); btn.setFocusPainted(false); btn.setPreferredSize(new Dimension(160, 42)); btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void loadTable(){ allTheLoai = bus.getAllTheLoai(); showTable(allTheLoai); }
    
    private void showTable(List<TheLoaiDTO> list){
        currentList = list; model.setRowCount(0);
        for(TheLoaiDTO t : list) model.addRow(new Object[]{ t.getMaTL(), t.getTenLoai(), t.getTenDanhMuc() });
    }

    private void addRealtimeSearchListener(JTextField txt) {
        txt.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e){ searchAdvanced(); }
            public void removeUpdate(DocumentEvent e){ searchAdvanced(); }
            public void changedUpdate(DocumentEvent e){ searchAdvanced(); }
        });
    }

    private void initEvents(){
        addRealtimeSearchListener(txtSearch); addRealtimeSearchListener(txtMa);
        addRealtimeSearchListener(txtTen); addRealtimeSearchListener(txtMaDM);
        
        btnReset.addActionListener(e -> resetFilter()); 
        
        btnAdd.addActionListener(e -> {
            // ĐÃ SỬA: Thêm TheLoaiPanel.this để không bị lỗi 
            JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(TheLoaiPanel.this);
            new TheLoaiFormDialog(parent).setVisible(true); 
            loadTable();
        });

        btnDelete.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) { JOptionPane.showMessageDialog(this, "Vui lòng chọn một thể loại để xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE); return; }
            int confirm = JOptionPane.showConfirmDialog(this, "Xác nhận xóa thể loại này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                boolean ok = bus.deleteTheLoai(currentList.get(row).getMaTL());
                if(ok) {
                    JOptionPane.showMessageDialog(this, "Xóa thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE); loadTable();
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa thất bại! Thể loại này đang được liên kết với sách.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        table.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                if(e.getClickCount()==2){
                    int row = table.getSelectedRow(); if(row == -1) return;
                    TheLoaiDTO tl = currentList.get(row);
                    JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(TheLoaiPanel.this);
                    new TheLoaiDetailDialog(parent, tl).setVisible(true); loadTable();
                }
            }
        });
    }

    private void searchAdvanced(){
        String key = txtSearch.getText().trim().toLowerCase();
        String ma = txtMa.getText().trim().toLowerCase(); 
        String ten = txtTen.getText().trim().toLowerCase(); 
        String dm = txtMaDM.getText().trim().toLowerCase();
        
        List<TheLoaiDTO> filtered = new ArrayList<>();
        for(TheLoaiDTO t : allTheLoai){
            boolean match = true; 
            String tenDMStr = t.getTenDanhMuc() != null ? t.getTenDanhMuc().toLowerCase() : "";
            
            if(!key.isEmpty()) {
                if(!(t.getMaTL().toLowerCase().contains(key) || t.getTenLoai().toLowerCase().contains(key) || tenDMStr.contains(key))) match = false;
            }
            if(!ma.isEmpty() && !t.getMaTL().toLowerCase().contains(ma)) match = false;
            if(!ten.isEmpty() && !t.getTenLoai().toLowerCase().contains(ten)) match = false;
            if(!dm.isEmpty() && !tenDMStr.contains(dm)) match = false;
            
            if(match) filtered.add(t);
        }
        showTable(filtered);
    }

    private void resetFilter(){
        txtSearch.setText(""); txtMa.setText(""); txtTen.setText(""); txtMaDM.setText(""); loadTable();
    }
}