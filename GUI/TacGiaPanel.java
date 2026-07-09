package GUI; 

import BUS.TacGiaBUS;
import DTO.TacGiaDTO;
import com.toedter.calendar.JDateChooser;

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
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TacGiaPanel extends JPanel {

    private JTable table;
    private DefaultTableModel model;
    private TacGiaBUS bus = new TacGiaBUS();

    private JTextField txtSearch, txtMa, txtTen, txtQuocTich;
    private JDateChooser dateChooser, dateChooserMat;
    private JButton btnReset, btnAdd, btnDelete;

    private List<TacGiaDTO> allTacGia = new ArrayList<>();
    private List<TacGiaDTO> currentList = new ArrayList<>();
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private Color darkPurple = Color.decode("#5A4664");
    private Color bgContent = Color.decode("#F5F6FA");

    public TacGiaPanel() {
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

        JLabel lblTitle = new JLabel("QUẢN LÝ DANH MỤC TÁC GIẢ");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitle.setForeground(darkPurple);
        pnlTop.add(lblTitle, BorderLayout.NORTH);

        JPanel pnlFilterCard = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.setColor(Color.decode("#DCDDE1"));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
                g2.dispose();
            }
        };
        pnlFilterCard.setLayout(new BoxLayout(pnlFilterCard, BoxLayout.Y_AXIS));
        pnlFilterCard.setOpaque(false);
        pnlFilterCard.setBorder(new EmptyBorder(15, 20, 15, 20));

        JPanel pnlRow1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
        pnlRow1.setOpaque(false);
        txtSearch = new JTextField(25);
        
        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("yyyy-MM-dd");
        dateChooser.setOpaque(false);
        
        dateChooserMat = new JDateChooser();
        dateChooserMat.setDateFormatString("yyyy-MM-dd");
        dateChooserMat.setOpaque(false);
        
        pnlRow1.add(createFilterField("TÌM KIẾM NHANH", txtSearch, Color.WHITE, Color.decode("#DCDDE1")));
        pnlRow1.add(createFilterField("Ngày Sinh", dateChooser, Color.WHITE, Color.decode("#DCDDE1")));
        pnlRow1.add(createFilterField("Ngày Mất", dateChooserMat, Color.WHITE, Color.decode("#DCDDE1")));

        JPanel pnlRow2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
        pnlRow2.setOpaque(false);
        txtMa = new JTextField(12);
        txtTen = new JTextField(20);
        txtQuocTich = new JTextField(15);
        
        pnlRow2.add(createFilterField("Mã Tác Giả", txtMa, Color.WHITE, Color.decode("#DCDDE1")));
        pnlRow2.add(createFilterField("Tên Tác Giả", txtTen, Color.WHITE, Color.decode("#DCDDE1")));
        pnlRow2.add(createFilterField("Quốc Tịch", txtQuocTich, Color.WHITE, Color.decode("#DCDDE1")));

        pnlFilterCard.add(pnlRow1);
        pnlFilterCard.add(Box.createVerticalStrut(5));
        pnlFilterCard.add(pnlRow2);
        
        pnlTop.add(pnlFilterCard, BorderLayout.CENTER);
        add(pnlTop, BorderLayout.NORTH);

        String[] cols = {"Mã tác giả", "Tên tác giả", "Ngày sinh", "Ngày mất", "Quốc tịch"};
        model = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        
        table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        table.setRowHeight(50);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setSelectionBackground(Color.decode("#FFF0F5"));
        table.setSelectionForeground(darkPurple);

        JTableHeader header = table.getTableHeader();
        header.setPreferredSize(new Dimension(0, 50));
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                label.setBackground(darkPurple);
                label.setForeground(Color.WHITE);
                label.setFont(new Font("Segoe UI", Font.BOLD, 15));
                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.decode("#413249")));
                return label;
            }
        });

        DefaultTableCellRenderer customRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setHorizontalAlignment(SwingConstants.CENTER);
                
                if (!isSelected) {
                    if (row % 2 == 0) c.setBackground(Color.WHITE);
                    else c.setBackground(Color.decode("#F9F9FB"));
                    c.setForeground(Color.DARK_GRAY);
                }
                
                if (column == 0) {
                    c.setForeground(darkPurple);
                    setFont(new Font("Segoe UI", Font.BOLD, 15));
                } else if (column == 1) {
                    setHorizontalAlignment(SwingConstants.LEFT);
                    setFont(new Font("Segoe UI", Font.BOLD, 14));
                } else {
                    setFont(new Font("Segoe UI", Font.PLAIN, 15));
                }
                
                ((JLabel) c).setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.decode("#EEEEEE")));
                return c;
            }
        };

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(customRenderer);
        }

        table.getColumnModel().getColumn(1).setPreferredWidth(250);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.decode("#DCDDE1"), 1));
        add(scrollPane, BorderLayout.CENTER);

        JPanel pnlBottom = new JPanel(new BorderLayout());
        pnlBottom.setOpaque(false);
        pnlBottom.setBorder(new EmptyBorder(15, 0, 0, 0));

        JPanel pnlLeft = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        pnlLeft.setOpaque(false);
        btnAdd = createButton("THÊM TÁC GIẢ", new Color(40, 167, 69));
        btnDelete = createButton("XÓA TÁC GIẢ", new Color(220, 53, 69));
        pnlLeft.add(btnAdd); 
        pnlLeft.add(btnDelete); 

        JPanel pnlRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        pnlRight.setOpaque(false);
        btnReset = createButton("LÀM MỚI FORM", new Color(46, 204, 113));
        pnlRight.add(btnReset);

        pnlBottom.add(pnlLeft, BorderLayout.WEST);
        pnlBottom.add(pnlRight, BorderLayout.EAST);
        add(pnlBottom, BorderLayout.SOUTH);
    }

    private JPanel createFilterField(String title, JComponent comp, Color bgColor, Color borderColor) {
        JPanel pnl = new JPanel(new BorderLayout(0, 8));
        pnl.setOpaque(false);
        JLabel lbl = new JLabel(title);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lbl.setForeground(darkPurple);
        pnl.add(lbl, BorderLayout.NORTH);

        JPanel wrapper = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(bgColor);
                g2.fillRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 10, 10);
                g2.setColor(borderColor);
                g2.setStroke(new BasicStroke(1.2f));
                g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 10, 10);
                g2.dispose();
            }
        };
        wrapper.setOpaque(false);
        wrapper.setBorder(new EmptyBorder(6, 15, 6, 15));

        if (comp instanceof JTextField) {
            JTextField txt = (JTextField) comp;
            txt.setFont(new Font("Segoe UI", Font.PLAIN, 15));
            txt.setBorder(null);
            txt.setOpaque(false);
            wrapper.add(txt, BorderLayout.CENTER);
        } else if (comp instanceof JDateChooser) {
            JDateChooser jd = (JDateChooser) comp;
            jd.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            jd.setOpaque(false);
            for (Component c : jd.getComponents()) {
                if (c instanceof JTextField) {
                    ((JTextField) c).setBorder(null);
                    ((JTextField) c).setOpaque(false);
                }
            }
            comp.setPreferredSize(new Dimension(145, 28)); 
            wrapper.add(jd, BorderLayout.CENTER);
        }

        pnl.add(wrapper, BorderLayout.CENTER);
        return pnl;
    }

    private JButton createButton(String text, Color bg) {
        JButton btn = new JButton(text) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isRollover() ? bg.darker() : bg);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setForeground(Color.WHITE);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(160, 42)); 
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void loadTable(){ 
        allTacGia = bus.getAllTacGia(); 
        showTable(allTacGia); 
    }
    
    private void showTable(List<TacGiaDTO> list){
        currentList = list; 
        model.setRowCount(0);
        for(TacGiaDTO t : list) {
            String ns = t.getNgaySinh() != null ? sdf.format(t.getNgaySinh()) : "Chưa cập nhật";
            String nm = t.getNgayMat() != null ? sdf.format(t.getNgayMat()) : "-";
            String qt = t.getQuocTich() != null && !t.getQuocTich().isEmpty() ? t.getQuocTich() : "Chưa cập nhật";
            model.addRow(new Object[]{ t.getMaTG(), t.getTenTacGia(), ns, nm, qt });
        }
    }

    private void addRealtimeSearchListener(JTextField txt) {
        txt.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e){ searchAdvanced(); }
            public void removeUpdate(DocumentEvent e){ searchAdvanced(); }
            public void changedUpdate(DocumentEvent e){ searchAdvanced(); }
        });
    }

    private void initEvents(){
        addRealtimeSearchListener(txtSearch);
        addRealtimeSearchListener(txtMa);
        addRealtimeSearchListener(txtTen);
        addRealtimeSearchListener(txtQuocTich);
        
        dateChooserMat.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent e) {
                if ("date".equals(e.getPropertyName())) {
                    searchAdvanced();
                }
            }
        });

        btnReset.addActionListener(e -> resetFilter()); 
        
        btnAdd.addActionListener(e -> {
            JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(TacGiaPanel.this);
            TacGiaFormDialog dialog = new TacGiaFormDialog(parent); 
            dialog.setVisible(true); 
            loadTable();
        });

        btnDelete.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) { 
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một tác giả để xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE); 
                return; 
            }
            
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa tác giả này không?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                TacGiaDTO tg = currentList.get(row);
                boolean ok = bus.deleteTacGia(tg.getMaTG()); 
                if (ok) {
                    JOptionPane.showMessageDialog(this, "Đã xóa tác giả thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    loadTable(); 
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa thất bại! Tác giả này đang có sách lưu trong hệ thống, không thể xóa.", "Lỗi CSDL", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        table.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                if(e.getClickCount()==2){
                    int row = table.getSelectedRow();
                    if(row == -1) return;
                    TacGiaDTO tg = currentList.get(row);
                    JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(TacGiaPanel.this);
                    new TacGiaDetailDialog(parent,tg).setVisible(true);
                    loadTable();
                }
            }
        });
    }

    private void searchAdvanced(){
        String key = txtSearch.getText().trim().toLowerCase();
        String ma = txtMa.getText().trim().toLowerCase(); 
        String ten = txtTen.getText().trim().toLowerCase(); 
        String qt = txtQuocTich.getText().trim().toLowerCase();
        Date selectedDate = dateChooser.getDate(); 
        Date selectedDateMat = dateChooserMat.getDate(); 
        
        List<TacGiaDTO> filtered = new ArrayList<>();
        for(TacGiaDTO t : allTacGia){
            boolean match = true; 
            String ngaySinh = t.getNgaySinh() != null ? sdf.format(t.getNgaySinh()) : "";
            String ngayMat = t.getNgayMat() != null ? sdf.format(t.getNgayMat()) : "";
            
            if(!key.isEmpty()) {
                if(!(t.getMaTG().toLowerCase().contains(key) ||
                     t.getTenTacGia().toLowerCase().contains(key) ||
                     ngaySinh.contains(key) ||
                     ngayMat.contains(key) ||
                     (t.getQuocTich() != null && t.getQuocTich().toLowerCase().contains(key)))) {
                    match = false;
                }
            }
            
            if(!ma.isEmpty() && !t.getMaTG().toLowerCase().contains(ma)) match = false;
            if(!ten.isEmpty() && !t.getTenTacGia().toLowerCase().contains(ten)) match = false;
            if(!qt.isEmpty() && (t.getQuocTich()==null || !t.getQuocTich().toLowerCase().contains(qt))) match = false;
            
            if(selectedDate != null){ 
                String selected = sdf.format(selectedDate); 
                if(!ngaySinh.equals(selected)) match = false; 
            }

            if(selectedDateMat != null){ 
                String selectedMat = sdf.format(selectedDateMat); 
                if(!ngayMat.equals(selectedMat)) match = false; 
            }
            
            if(match) filtered.add(t);
        }
        showTable(filtered);
    }

    private void resetFilter(){
        txtSearch.setText(""); 
        txtMa.setText(""); 
        txtTen.setText(""); 
        txtQuocTich.setText(""); 
        dateChooser.setDate(null); 
        dateChooserMat.setDate(null);
        loadTable();
    }
}