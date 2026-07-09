package GUI;

import BUS.SachBUS;
import DAO.NhaXuatBanDAO;
import DAO.TacGiaDAO;
import DAO.TheLoaiDAO;
import DTO.NhaXuatBanDTO;
import DTO.SachDTO;
import DTO.TacGiaDTO;
import DTO.TheLoaiDTO;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.BaseColor;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

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
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class SachPanel extends JPanel {

    private JTable table;
    private DefaultTableModel model;
    private SachBUS sachBUS = new SachBUS();

    private JTextField txtSearch, txtMaSach, txtTenSach, txtGiaFrom, txtGiaTo;
    private JComboBox<TheLoaiDTO> cbLoai;
    private JComboBox<TacGiaDTO> cbTacGia;
    private JComboBox<NhaXuatBanDTO> cbNXB;
    
    private JButton btnReset, btnAdd, btnDelete, btnExport, btnImport;

    private List<SachDTO> allSach = new ArrayList<>();
    private List<SachDTO> currentList = new ArrayList<>();

    private Color darkPurple = Color.decode("#5A4664");
    private Color bgContent = Color.decode("#F5F6FA");
    private DecimalFormat df = new DecimalFormat("#,### đ");

    public SachPanel() {
        setLayout(new BorderLayout(20, 20));
        setBackground(bgContent);
        setBorder(new EmptyBorder(25, 30, 25, 30));

        initUI();
        loadComboBox();
        loadTable();
        initEvents();
    }

    private void initUI() {
        JPanel pnlTop = new JPanel(new BorderLayout(0, 20));
        pnlTop.setOpaque(false);

        JLabel lblTitle = new JLabel("QUẢN LÝ DANH MỤC SÁCH");
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
        txtGiaFrom = new JTextField(12);
        txtGiaTo = new JTextField(12);
        
        pnlRow1.add(createFilterField("TÌM KIẾM NHANH", txtSearch, Color.WHITE, Color.decode("#DCDDE1")));
        pnlRow1.add(createFilterField("Giá Từ", txtGiaFrom, Color.WHITE, Color.decode("#DCDDE1")));
        pnlRow1.add(createFilterField("Đến Giá", txtGiaTo, Color.WHITE, Color.decode("#DCDDE1")));

        JPanel pnlRow2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
        pnlRow2.setOpaque(false);
        txtMaSach = new JTextField(10);
        txtTenSach = new JTextField(18);
        cbLoai = new JComboBox<>();
        cbTacGia = new JComboBox<>();
        cbNXB = new JComboBox<>();
        
        pnlRow2.add(createFilterField("Mã Sách", txtMaSach, Color.WHITE, Color.decode("#DCDDE1")));
        pnlRow2.add(createFilterField("Tên Sách", txtTenSach, Color.WHITE, Color.decode("#DCDDE1")));
        pnlRow2.add(createFilterField("Thể Loại", cbLoai, Color.WHITE, Color.decode("#DCDDE1")));
        pnlRow2.add(createFilterField("Tác Giả", cbTacGia, Color.WHITE, Color.decode("#DCDDE1")));
        pnlRow2.add(createFilterField("Nhà Xuất Bản", cbNXB, Color.WHITE, Color.decode("#DCDDE1")));

        pnlFilterCard.add(pnlRow1);
        pnlFilterCard.add(Box.createVerticalStrut(5));
        pnlFilterCard.add(pnlRow2);
        
        pnlTop.add(pnlFilterCard, BorderLayout.CENTER);
        add(pnlTop, BorderLayout.NORTH);

        String[] cols = {"Ảnh", "Mã sách", "Tên sách", "Thể loại", "Tác giả", "NXB", "Giá bán", "Tồn kho", "Trạng thái"};
        model = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
            @Override public Class<?> getColumnClass(int column) {
                if (column == 0) return ImageIcon.class;
                return Object.class;
            }
        };
        
        table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(55);
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
                
                String trangThai = table.getValueAt(row, 8).toString();
                boolean isNgungBan = trangThai.equals("Ngừng Bán");

                if (!isSelected) {
                    if (row % 2 == 0) c.setBackground(Color.WHITE);
                    else c.setBackground(Color.decode("#F9F9FB"));
                    
                    if (isNgungBan) c.setForeground(new Color(170, 170, 170));
                    else c.setForeground(Color.DARK_GRAY);
                }
                
                if (column == 8) {
                    if (!isNgungBan) c.setForeground(new Color(40, 167, 69)); 
                    else c.setForeground(new Color(220, 53, 69)); 
                    setFont(new Font("Segoe UI", Font.BOLD, 14));
                } else if (column == 1) {
                    if (!isSelected && !isNgungBan) c.setForeground(darkPurple);
                    setFont(new Font("Segoe UI", Font.BOLD, 15));
                } else if (column == 2) {
                    setHorizontalAlignment(SwingConstants.LEFT);
                    setFont(new Font("Segoe UI", Font.BOLD, 14));
                } else if (column == 3 || column == 4) {
                    setHorizontalAlignment(SwingConstants.LEFT);
                    setFont(new Font("Segoe UI", Font.PLAIN, 14));
                } else {
                    setFont(new Font("Segoe UI", Font.PLAIN, 15));
                }
                
                ((JLabel) c).setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.decode("#EEEEEE")));
                return c;
            }
        };

        for (int i = 1; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(customRenderer);
        }

        table.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel lbl = new JLabel();
                if (value instanceof ImageIcon) lbl.setIcon((ImageIcon) value);
                lbl.setHorizontalAlignment(SwingConstants.CENTER);
                lbl.setOpaque(true);
                if (isSelected) lbl.setBackground(Color.decode("#FFF0F5"));
                else lbl.setBackground(row % 2 == 0 ? Color.WHITE : Color.decode("#F9F9FB"));
                lbl.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.decode("#EEEEEE")));
                return lbl;
            }
        });

        table.getColumnModel().getColumn(0).setPreferredWidth(60);
        table.getColumnModel().getColumn(2).setPreferredWidth(230);
        table.getColumnModel().getColumn(3).setPreferredWidth(150);
        table.getColumnModel().getColumn(4).setPreferredWidth(150);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.decode("#DCDDE1"), 1));
        add(scrollPane, BorderLayout.CENTER);

        JPanel pnlBottom = new JPanel(new BorderLayout());
        pnlBottom.setOpaque(false);
        pnlBottom.setBorder(new EmptyBorder(15, 0, 0, 0));

        JPanel pnlLeft = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        pnlLeft.setOpaque(false);
        btnAdd = createButton("THÊM MỚI", new Color(40, 167, 69));
        btnDelete = createButton("XÓA SÁCH", new Color(220, 53, 69));
        btnImport = createButton("NHẬP EXCEL", darkPurple);
        btnExport = createButton("XUẤT PDF", darkPurple);
        
        pnlLeft.add(btnAdd); 
        pnlLeft.add(btnDelete);
        pnlLeft.add(btnImport); 
        pnlLeft.add(btnExport);

        JPanel pnlRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        pnlRight.setOpaque(false);
        btnReset = createButton("LÀM MỚI BẢNG", new Color(46, 204, 113));
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
        } else if (comp instanceof JComboBox) {
            JComboBox<?> cmb = (JComboBox<?>) comp;
            cmb.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            cmb.setBorder(null);
            cmb.setBackground(bgColor);
            comp.setPreferredSize(new Dimension(145, 28)); 
            wrapper.add(cmb, BorderLayout.CENTER);
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
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setForeground(Color.WHITE);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(140, 42)); 
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void loadComboBox() {
        cbLoai.addItem(null); cbTacGia.addItem(null); cbNXB.addItem(null);
        for(TheLoaiDTO tl : new TheLoaiDAO().getAll()) cbLoai.addItem(tl);
        for(TacGiaDTO tg : new TacGiaDAO().getAll()) cbTacGia.addItem(tg);
        for(NhaXuatBanDTO nxb : new NhaXuatBanDAO().getAll()) cbNXB.addItem(nxb);
    }

    public void loadTable() {
        allSach = sachBUS.getAllSach();
        showTable(allSach);
    }

    private void showTable(List<SachDTO> list) {
        currentList = list;
        model.setRowCount(0);
        for(SachDTO s : list) {
            ImageIcon icon = null;
            String hinhAnh = s.getHinhAnh();
            if(hinhAnh != null && !hinhAnh.trim().isEmpty()) {
                try {
                    java.net.URL imgURL = getClass().getResource("/images/" + hinhAnh);
                    if (imgURL != null) {
                        icon = new ImageIcon(new ImageIcon(imgURL).getImage().getScaledInstance(40, 50, Image.SCALE_SMOOTH));
                    } else {
                        File imgFile = new File(hinhAnh);
                        if (!imgFile.exists()) imgFile = new File("images/" + hinhAnh);
                        if (!imgFile.exists()) imgFile = new File("src/images/" + hinhAnh);
                        if (imgFile.exists()) {
                            icon = new ImageIcon(new ImageIcon(imgFile.getAbsolutePath()).getImage().getScaledInstance(40, 50, Image.SCALE_SMOOTH));
                        }
                    }
                } catch (Exception e) {}
            }
            
            String trangThai = s.getTrangThai().equals("DangBan") ? "Đang Bán" : "Ngừng Bán";
            model.addRow(new Object[]{
                icon, s.getMaSach(), s.getTenSach(), s.getTenLoai(), s.getTenTacGia(), s.getTenNXB(), 
                df.format(s.getGiaBan()), s.getSoLuongTon(), trangThai
            });
        }
    }

    private void addRealtimeSearchListener(JTextField txt) {
        txt.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e){ searchAdvanced(); }
            public void removeUpdate(DocumentEvent e){ searchAdvanced(); }
            public void changedUpdate(DocumentEvent e){ searchAdvanced(); }
        });
    }

    private void initEvents() {
        addRealtimeSearchListener(txtSearch);
        addRealtimeSearchListener(txtMaSach);
        addRealtimeSearchListener(txtTenSach);
        addRealtimeSearchListener(txtGiaFrom);
        addRealtimeSearchListener(txtGiaTo);

        cbLoai.addActionListener(e -> searchAdvanced());
        cbTacGia.addActionListener(e -> searchAdvanced());
        cbNXB.addActionListener(e -> searchAdvanced());

        btnReset.addActionListener(e -> resetFilter());
        btnExport.addActionListener(e -> exportPDF());
        btnImport.addActionListener(e -> importExcel());

        btnAdd.addActionListener(e -> {
            JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(SachPanel.this);
            new SachFormDialog(parent).setVisible(true);
            loadTable(); 
        });
        
        btnDelete.addActionListener(e -> {
            int row = table.getSelectedRow();
            if(row == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một cuốn sách để xóa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(this, "Xác nhận XÓA sách này (sẽ biến mất khỏi màn hình)?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if(confirm == JOptionPane.YES_OPTION) {
                SachDTO s = currentList.get(row);
                if(sachBUS.deleteSach(s.getMaID())) {
                    JOptionPane.showMessageDialog(this, "Đã xóa sách thành công!", "Hoàn tất", JOptionPane.INFORMATION_MESSAGE);
                    loadTable();
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa sách thất bại do lỗi hệ thống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        table.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                if(e.getClickCount() == 2){
                    int row = table.getSelectedRow();
                    if(row == -1) return;
                    
                    SachDTO sachTable = currentList.get(row);
                    SachDTO fullSach = sachBUS.getSachByID(sachTable.getMaID());
                    
                    JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(SachPanel.this);
                    new SachDetailDialog(parent, fullSach).setVisible(true);
                    
                    loadTable(); 
                }
            }
        });
    }

    private void searchAdvanced() {
        String key = txtSearch.getText().trim().toLowerCase();
        String kwMa = txtMaSach.getText().trim().toLowerCase();
        String kwTen = txtTenSach.getText().trim().toLowerCase();
        Double giaFrom = parseDouble(txtGiaFrom.getText());
        Double giaTo = parseDouble(txtGiaTo.getText());
        TheLoaiDTO loai = (TheLoaiDTO) cbLoai.getSelectedItem();
        TacGiaDTO tg = (TacGiaDTO) cbTacGia.getSelectedItem();
        NhaXuatBanDTO nxb = (NhaXuatBanDTO) cbNXB.getSelectedItem();

        List<SachDTO> filtered = new ArrayList<>();
        for(SachDTO s : allSach) {
            boolean match = true;
            
            if(!key.isEmpty()) {
                String strGia = String.valueOf((long)s.getGiaBan());
                if(!(s.getTenSach().toLowerCase().contains(key) ||
                     s.getIsbn().toLowerCase().contains(key) ||
                     s.getMaSach().toLowerCase().contains(key) ||
                     strGia.contains(key))) {
                    match = false;
                }
            }
            
            if(!kwMa.isEmpty() && !s.getMaSach().toLowerCase().contains(kwMa)) match = false;
            if(!kwTen.isEmpty() && !s.getTenSach().toLowerCase().contains(kwTen)) match = false;
            
            if(loai != null && (s.getTenLoai() == null || !s.getTenLoai().contains(loai.getTenLoai()))) match = false;
            if(tg != null && (s.getTenTacGia() == null || !s.getTenTacGia().contains(tg.getTenTacGia()))) match = false;
            
            if(nxb != null && (s.getTenNXB() == null || !s.getTenNXB().equals(nxb.getTenNXB()))) match = false;
            if(giaFrom != null && s.getGiaBan() < giaFrom) match = false;
            if(giaTo != null && s.getGiaBan() > giaTo) match = false;
            
            if(match) filtered.add(s);
        }
        showTable(filtered);
    }

    private void resetFilter() {
        txtSearch.setText(""); 
        txtMaSach.setText(""); 
        txtTenSach.setText("");
        txtGiaFrom.setText(""); 
        txtGiaTo.setText("");
        cbLoai.setSelectedIndex(0); cbTacGia.setSelectedIndex(0); cbNXB.setSelectedIndex(0);
        loadTable();
    }

    private Double parseDouble(String s) {
        try{ return Double.parseDouble(s); } catch(Exception e){ return null; }
    }
    
    private void exportPDF() {
        JFileChooser chooser = new JFileChooser();
        if(chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION){
            try{
                Document document = new Document(PageSize.A4.rotate());
                PdfWriter.getInstance(document, new FileOutputStream(chooser.getSelectedFile() + ".pdf"));
                document.open();
                BaseFont bf = BaseFont.createFont("C:/Windows/Fonts/arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
                com.itextpdf.text.Font font = new com.itextpdf.text.Font(bf, 11);
                com.itextpdf.text.Font fontHeader = new com.itextpdf.text.Font(bf, 12, Font.BOLD);
                Paragraph title = new Paragraph("DANH SÁCH SÁCH", fontHeader);
                title.setAlignment(Element.ALIGN_CENTER);
                document.add(title); document.add(new Paragraph(" "));

                PdfPTable pdfTable = new PdfPTable(table.getColumnCount()-1);
                pdfTable.setWidthPercentage(100);
                float[] columnWidths = {3f, 6f, 4f, 5f, 4f, 3f, 2f, 3f};
                pdfTable.setWidths(columnWidths);

                for(int i=1; i<table.getColumnCount(); i++){
                    PdfPCell header = new PdfPCell(new Phrase(table.getColumnName(i), fontHeader));
                    header.setHorizontalAlignment(Element.ALIGN_CENTER);
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setPadding(6);
                    pdfTable.addCell(header);
                }

                for(int i=0; i<table.getRowCount(); i++){
                    for(int j=1; j<table.getColumnCount(); j++){
                        Object value = table.getValueAt(i,j);
                        if(value instanceof ImageIcon){
                            pdfTable.addCell("Image");
                        }else{
                            PdfPCell cell = new PdfPCell(new Phrase(value != null ? value.toString() : "", font));
                            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            pdfTable.addCell(cell);
                        }
                    }
                }
                document.add(pdfTable); document.close();
                JOptionPane.showMessageDialog(this,"Xuất PDF thành công");
            }catch(Exception ex){ JOptionPane.showMessageDialog(this,"Xuất file thất bại"); }
        }
    }
    
    private void importExcel() {
        JFileChooser chooser = new JFileChooser();
        if(chooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) return;
        File file = chooser.getSelectedFile();
        try{
            FileInputStream fis = new FileInputStream(file);
            Workbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheetAt(0);
            for(int i=1; i<=sheet.getLastRowNum(); i++){
                Row row = sheet.getRow(i);
                if(row == null) continue;
                SachDTO s = new SachDTO();
                s.setMaSach(row.getCell(0).getStringCellValue());
                s.setTenSach(row.getCell(1).getStringCellValue());
                s.setIsbn(row.getCell(2).getStringCellValue());
                s.setGiaNhap(row.getCell(3).getNumericCellValue());
                s.setGiaBan(row.getCell(4).getNumericCellValue());
                s.setSoLuongTon((int)row.getCell(5).getNumericCellValue());
                s.setTrangThai(row.getCell(6).getStringCellValue());
                sachBUS.addSach(s); 
            }
            workbook.close();
            JOptionPane.showMessageDialog(this,"Import thành công");
            loadTable();
        }catch(Exception ex){ JOptionPane.showMessageDialog(this,"Import thất bại"); }
    }
}