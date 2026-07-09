package GUI; 

import BUS.NhaXuatBanBUS;
import DTO.NhaXuatBanDTO;

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

public class NhaXuatBanPanel extends JPanel {

    private JTable table;
    private DefaultTableModel model;
    private NhaXuatBanBUS bus = new NhaXuatBanBUS();

    private JTextField txtSearch, txtMa, txtTen, txtDiaChi;
    private JButton btnReset, btnAdd, btnDelete;

    private List<NhaXuatBanDTO> allList = new ArrayList<>();
    private List<NhaXuatBanDTO> currentList = new ArrayList<>();

    private Color darkPurple = Color.decode("#5A4664");
    private Color bgContent = Color.decode("#F5F6FA");

    public NhaXuatBanPanel() {
        setLayout(new BorderLayout(20, 20));
        setBackground(bgContent);
        setBorder(new EmptyBorder(25, 30, 25, 30));
        initUI();
        loadTable();
        initEvents();
    }

    private void initUI() {
        JPanel pnlTop = new JPanel(new BorderLayout(0, 20)); pnlTop.setOpaque(false);

        JLabel lblTitle = new JLabel("QUẢN LÝ NHÀ XUẤT BẢN");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 28)); lblTitle.setForeground(darkPurple);
        pnlTop.add(lblTitle, BorderLayout.NORTH);

        JPanel pnlFilterCard = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create(); g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
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
        txtMa = new JTextField(12); txtTen = new JTextField(20); txtDiaChi = new JTextField(20);
        
        pnlRow2.add(createFilterField("Mã NXB", txtMa, Color.WHITE, Color.decode("#DCDDE1")));
        pnlRow2.add(createFilterField("Tên NXB", txtTen, Color.WHITE, Color.decode("#DCDDE1")));
        pnlRow2.add(createFilterField("Địa chỉ", txtDiaChi, Color.WHITE, Color.decode("#DCDDE1")));

        pnlFilterCard.add(pnlRow1); pnlFilterCard.add(Box.createVerticalStrut(5)); pnlFilterCard.add(pnlRow2);
        pnlTop.add(pnlFilterCard, BorderLayout.CENTER); add(pnlTop, BorderLayout.NORTH);

        String[] cols = {"Mã NXB", "Tên nhà xuất bản", "Địa chỉ"}; 
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
                else if (column == 1 || column == 2) { setHorizontalAlignment(SwingConstants.LEFT); setFont(new Font("Segoe UI", Font.BOLD, 14)); } 
                else { setFont(new Font("Segoe UI", Font.PLAIN, 15)); }
                ((JLabel) c).setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.decode("#EEEEEE"))); return c;
            }
        };

        for (int i = 0; i < table.getColumnCount(); i++) table.getColumnModel().getColumn(i).setCellRenderer(customRenderer);
        table.getColumnModel().getColumn(1).setPreferredWidth(200);
        table.getColumnModel().getColumn(2).setPreferredWidth(250);

        JScrollPane scrollPane = new JScrollPane(table); scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.decode("#DCDDE1"), 1)); add(scrollPane, BorderLayout.CENTER);

        JPanel pnlBottom = new JPanel(new BorderLayout()); pnlBottom.setOpaque(false); pnlBottom.setBorder(new EmptyBorder(15, 0, 0, 0));

        JPanel pnlLeft = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0)); pnlLeft.setOpaque(false);
        btnAdd = createButton("THÊM NXB", new Color(40, 167, 69));
        btnDelete = createButton("XÓA NXB", new Color(220, 53, 69));
        pnlLeft.add(btnAdd); pnlLeft.add(btnDelete); 

        JPanel pnlRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0)); pnlRight.setOpaque(false);
        btnReset = createButton("LÀM MỚI BẢNG", new Color(46, 204, 113)); pnlRight.add(btnReset);

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
        btn.setBorderPainted(false); btn.setFocusPainted(false); btn.setPreferredSize(new Dimension(150, 42)); btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void loadTable(){ allList = bus.getAllNXB(); showTable(allList); }
    
    private void showTable(List<NhaXuatBanDTO> list){
        currentList = list; model.setRowCount(0);
        for(NhaXuatBanDTO n : list) model.addRow(new Object[]{ n.getMaNXB(), n.getTenNXB(), n.getDiaChi() });
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
        addRealtimeSearchListener(txtTen); addRealtimeSearchListener(txtDiaChi);
        
        btnReset.addActionListener(e -> resetFilter()); 
        
        btnAdd.addActionListener(e -> {
            JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(this);
            new NhaXuatBanFormDialog(parent).setVisible(true); loadTable();
        });

        btnDelete.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) { JOptionPane.showMessageDialog(this, "Vui lòng chọn một NXB để xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE); return; }
            int confirm = JOptionPane.showConfirmDialog(this, "Xác nhận xóa NXB này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                boolean ok = bus.deleteNXB(currentList.get(row).getMaNXB());
                if(ok) {
                    JOptionPane.showMessageDialog(this, "Xóa thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE); loadTable();
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa thất bại! NXB đang liên kết với Sách.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        table.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                if(e.getClickCount()==2){
                    int row = table.getSelectedRow(); if(row == -1) return;
                    NhaXuatBanDTO nxb = currentList.get(row);
                    JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(NhaXuatBanPanel.this);
                    new NhaXuatBanDetailDialog(parent, nxb).setVisible(true); loadTable();
                }
            }
        });
    }

    private void searchAdvanced(){
        String key = txtSearch.getText().trim().toLowerCase();
        String ma = txtMa.getText().trim().toLowerCase(); 
        String ten = txtTen.getText().trim().toLowerCase(); 
        String diachi = txtDiaChi.getText().trim().toLowerCase();
        
        List<NhaXuatBanDTO> filtered = new ArrayList<>();
        for(NhaXuatBanDTO n : allList){
            boolean match = true; 
            String dcStr = n.getDiaChi() != null ? n.getDiaChi().toLowerCase() : "";
            
            if(!key.isEmpty()) {
                if(!(n.getMaNXB().toLowerCase().contains(key) || n.getTenNXB().toLowerCase().contains(key) || dcStr.contains(key))) match = false;
            }
            if(!ma.isEmpty() && !n.getMaNXB().toLowerCase().contains(ma)) match = false;
            if(!ten.isEmpty() && !n.getTenNXB().toLowerCase().contains(ten)) match = false;
            if(!diachi.isEmpty() && !dcStr.contains(diachi)) match = false;
            
            if(match) filtered.add(n);
        }
        showTable(filtered);
    }

    private void resetFilter(){
        txtSearch.setText(""); txtMa.setText(""); txtTen.setText(""); txtDiaChi.setText(""); loadTable();
    }
}