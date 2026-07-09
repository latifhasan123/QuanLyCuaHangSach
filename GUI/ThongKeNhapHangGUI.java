package GUI;

import BUS.NhaCungCapBUS;
import BUS.PhieuNhapBUS;
import DTO.NhaCungCapDTO;
import DTO.PhieuNhapDTO;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ThongKeNhapHangGUI extends JPanel {

    private Color colorBg = Color.decode("#F8F4EC");
    private Color colorText = Color.decode("#43334C");
    private Color colorPrimary = Color.decode("#E83C91");
    private Color colorBtnBg = Color.decode("#FF8FB7");

    private Font fontChuan = new Font("Arial", Font.PLAIN, 14);
    private Font fontBold = new Font("Arial", Font.BOLD, 14);

    private JTable tblThongKe;
    private DefaultTableModel modelTK;
    private JLabel lblTongPhieu, lblTongTien, lblSoDoiTac;
    private JTextField txtTimKiem, txtTuGia, txtDenGia;
    private JButton btnLamMoi, btnXuatPDF, btnXuatExcel;

    private PhieuNhapBUS pnBUS = new PhieuNhapBUS();
    private NhaCungCapBUS nccBUS = new NhaCungCapBUS();

    public ThongKeNhapHangGUI() {
        initComponents();
        loadDataAndCalculate("", null, null); 
    }

    private void initComponents() {
        // Bỏ viền ở Panel chính để Tiêu đề tràn mép
        this.setLayout(new BorderLayout());
        this.setBackground(colorBg);

        JLabel lblTitle = new JLabel("BÁO CÁO THỐNG KÊ NHẬP HÀNG", JLabel.CENTER);
        lblTitle.setOpaque(true);
        lblTitle.setBackground(colorPrimary);
        lblTitle.setForeground(colorBg);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setBorder(new EmptyBorder(15, 10, 15, 10));
        
        // Đưa tiêu đề lên sát đỉnh màn hình
        this.add(lblTitle, BorderLayout.NORTH);

        // Tạo Panel chứa nội dung bên trong có viền 10px cho đẹp
        JPanel pnlContent = new JPanel(new BorderLayout(10, 10));
        pnlContent.setBackground(colorBg);
        pnlContent.setBorder(new EmptyBorder(10, 10, 10, 10));

        // ============ BỘ LỌC TÌM KIẾM NHANH ============
        JPanel pnlFilter = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        pnlFilter.setBackground(colorBg);
        pnlFilter.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.LIGHT_GRAY));

        txtTimKiem = createTextField(15); 
        txtTuGia = createTextField(12); 
        txtDenGia = createTextField(12); 
        
        pnlFilter.add(createLabel("Tìm Mã/Tên NCC:"));
        pnlFilter.add(txtTimKiem); 
        pnlFilter.add(createLabel("Từ Giá (VNĐ):"));
        pnlFilter.add(txtTuGia); 
        pnlFilter.add(createLabel("Đến Giá (VNĐ):"));
        pnlFilter.add(txtDenGia);

        // ============ THẺ THỐNG KÊ ============
        JPanel pnlCards = new JPanel(new GridLayout(1, 3, 20, 0));
        pnlCards.setBackground(colorBg);
        pnlCards.setBorder(new EmptyBorder(15, 0, 10, 0));

        lblTongPhieu = createCardLabel("TỔNG SỐ PHIẾU NHẬP", "0", Color.decode("#FF8FB7"));
        lblTongTien = createCardLabel("TỔNG TIỀN ĐÃ CHI", "0 VNĐ", Color.decode("#90CAF9"));
        lblSoDoiTac = createCardLabel("ĐỐI TÁC CUNG CẤP", "0", Color.decode("#A5D6A7"));

        pnlCards.add(lblTongPhieu.getParent());
        pnlCards.add(lblTongTien.getParent());
        pnlCards.add(lblSoDoiTac.getParent());

        JPanel pnlTop = new JPanel(new BorderLayout());
        pnlTop.setBackground(colorBg);
        pnlTop.add(pnlFilter, BorderLayout.NORTH);
        pnlTop.add(pnlCards, BorderLayout.SOUTH);
        pnlContent.add(pnlTop, BorderLayout.NORTH);

        // ============ BẢNG CHI TIẾT ============
        String[] cols = {"MÃ NHÀ CUNG CẤP", "TÊN NHÀ CUNG CẤP", "SỐ LẦN NHẬP", "TỔNG TIỀN ĐÃ NHẬP"};
        modelTK = new DefaultTableModel(cols, 0) { @Override public boolean isCellEditable(int r, int c) { return false; } };
        tblThongKe = new JTable(modelTK);
        tblThongKe.setRowHeight(35);
        tblThongKe.setFont(fontChuan);
        tblThongKe.setForeground(colorText); 
        JTableHeader header = tblThongKe.getTableHeader();
        header.setFont(fontBold);
        header.setForeground(colorText);

        JPanel pnlTable = new JPanel(new BorderLayout());
        pnlTable.setBackground(colorBg);
        pnlTable.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(colorText), "PHÂN TÍCH CHI PHÍ THEO NHÀ CUNG CẤP"));
        ((TitledBorder) pnlTable.getBorder()).setTitleFont(fontBold);
        ((TitledBorder) pnlTable.getBorder()).setTitleColor(colorText);
        pnlTable.add(new JScrollPane(tblThongKe), BorderLayout.CENTER);
        pnlContent.add(pnlTable, BorderLayout.CENTER);

        // ============ NÚT BẤM XUẤT FILE ============
        JPanel pnlBottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        pnlBottom.setBackground(colorBg);
        
        btnLamMoi = createButton("LÀM MỚI", colorText);
        btnXuatPDF = createButton("XUẤT PDF", Color.RED);
        btnXuatExcel = createButton("XUẤT EXCEL", Color.decode("#008000")); 
        
        pnlBottom.add(btnLamMoi);
        pnlBottom.add(btnXuatPDF);
        pnlBottom.add(btnXuatExcel);
        pnlContent.add(pnlBottom, BorderLayout.SOUTH);

        // Gắn khối nội dung vào giữa
        this.add(pnlContent, BorderLayout.CENTER);

        addEvents();
    }

    private JLabel createLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(fontBold);
        lbl.setForeground(colorText);
        return lbl;
    }

    private JTextField createTextField(int cols) {
        JTextField txt = new JTextField(cols);
        txt.setFont(fontChuan);
        txt.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(colorText, 1), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        return txt;
    }

    private JLabel createCardLabel(String title, String value, Color bgColor) {
        JPanel pnl = new JPanel(new GridLayout(2, 1));
        pnl.setBackground(bgColor);
        pnl.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        JLabel lblT = new JLabel(title, JLabel.CENTER);
        lblT.setFont(fontBold); lblT.setForeground(colorText);
        JLabel lblV = new JLabel(value, JLabel.CENTER);
        lblV.setFont(new Font("Arial", Font.BOLD, 22)); lblV.setForeground(Color.BLACK);
        pnl.add(lblT); pnl.add(lblV);
        return lblV;
    }

    private JButton createButton(String text, Color fgColor) {
        JButton btn = new JButton(text);
        btn.setFont(fontBold); 
        btn.setForeground(fgColor); 
        btn.setBackground(colorBtnBg);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
        btn.setFocusPainted(false);
        return btn;
    }

    private void loadDataAndCalculate(String keyword, Double minPrice, Double maxPrice) {
        modelTK.setRowCount(0);
        double tongTienTatCa = 0; int tongSoPhieu = 0; int soDoiTac = 0;
        pnBUS.docDanhSach(); 

        String kw = keyword.toLowerCase().trim();

        for (NhaCungCapDTO ncc : nccBUS.getListNCC()) {
            String ma = ncc.getMaNCC() != null ? ncc.getMaNCC().toLowerCase() : "";
            String ten = ncc.getTenNCC() != null ? ncc.getTenNCC().toLowerCase() : "";
            if (!kw.isEmpty() && !ma.contains(kw) && !ten.contains(kw)) continue;

            int soLan = 0; double tongTienNCC = 0;
            for (PhieuNhapDTO pn : pnBUS.getListPN()) {
                if (pn.getMaNCC() == ncc.getMaID()) {
                    soLan++;
                    tongTienNCC += pn.getTongTien();
                }
            }

            boolean matchMin = (minPrice == null) || (tongTienNCC >= minPrice);
            boolean matchMax = (maxPrice == null) || (tongTienNCC <= maxPrice);

            if (soLan > 0 && matchMin && matchMax) {
                modelTK.addRow(new Object[]{ncc.getMaNCC(), ncc.getTenNCC(), soLan + " lần", String.format("%,.0f VNĐ", tongTienNCC)});
                tongTienTatCa += tongTienNCC; tongSoPhieu += soLan; soDoiTac++;
            }
        }
        lblTongPhieu.setText(String.valueOf(tongSoPhieu));
        lblTongTien.setText(String.format("%,.0f VNĐ", tongTienTatCa));
        lblSoDoiTac.setText(String.valueOf(soDoiTac));
    }

    private void filterRealTime() {
        String kw = txtTimKiem.getText();
        Double min = null, max = null;
        try { if(!txtTuGia.getText().isEmpty()) min = Double.parseDouble(txtTuGia.getText().replace(",", "")); } catch(Exception e){}
        try { if(!txtDenGia.getText().isEmpty()) max = Double.parseDouble(txtDenGia.getText().replace(",", "")); } catch(Exception e){}
        loadDataAndCalculate(kw, min, max);
    }

    private void addEvents() {
        DocumentListener filter = new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { filterRealTime(); }
            @Override public void removeUpdate(DocumentEvent e) { filterRealTime(); }
            @Override public void changedUpdate(DocumentEvent e) { filterRealTime(); }
        };
        txtTimKiem.getDocument().addDocumentListener(filter);
        txtTuGia.getDocument().addDocumentListener(filter);
        txtDenGia.getDocument().addDocumentListener(filter);

        btnLamMoi.addActionListener(e -> {
            txtTimKiem.setText(""); txtTuGia.setText(""); txtDenGia.setText("");
            txtTimKiem.requestFocus();
        });

        btnXuatExcel.addActionListener(e -> {
            if (modelTK.getRowCount() == 0) { JOptionPane.showMessageDialog(this, "Không có dữ liệu!"); return; }
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                try {
                    String path = fileChooser.getSelectedFile().getAbsolutePath();
                    if (!path.endsWith(".xlsx")) path += ".xlsx";
                    Workbook workbook = new XSSFWorkbook();
                    Sheet sheet = workbook.createSheet("ThongKeNhap");
                    Row headerRow = sheet.createRow(0);
                    for (int i = 0; i < tblThongKe.getColumnCount(); i++) headerRow.createCell(i).setCellValue(tblThongKe.getColumnName(i));
                    for (int i = 0; i < tblThongKe.getRowCount(); i++) {
                        Row row = sheet.createRow(i + 1);
                        for (int j = 0; j < tblThongKe.getColumnCount(); j++) row.createCell(j).setCellValue(tblThongKe.getValueAt(i, j).toString());
                    }
                    FileOutputStream out = new FileOutputStream(path);
                    workbook.write(out); out.close(); workbook.close();
                    JOptionPane.showMessageDialog(this, "Xuất Excel thành công!");
                } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage()); }
            }
        });

        btnXuatPDF.addActionListener(e -> {
            if (modelTK.getRowCount() == 0) { JOptionPane.showMessageDialog(this, "Không có dữ liệu!"); return; }
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                try {
                    String path = fileChooser.getSelectedFile().getAbsolutePath();
                    if (!path.endsWith(".pdf")) path += ".pdf";
                    Document document = new Document();
                    PdfWriter.getInstance(document, new FileOutputStream(path));
                    document.open();
                    String fontPath = "C:\\Windows\\Fonts\\Arial.ttf";
                    if (!new File(fontPath).exists()) fontPath = "C:\\Windows\\Fonts\\arial.ttf"; 
                    BaseFont bf = BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
                    com.itextpdf.text.Font fontTitle = new com.itextpdf.text.Font(bf, 18, com.itextpdf.text.Font.BOLD);
                    com.itextpdf.text.Font fontInfo = new com.itextpdf.text.Font(bf, 12);

                    document.add(new Paragraph("BÁO CÁO THỐNG KÊ NHẬP HÀNG", fontTitle));
                    document.add(new Paragraph("Tổng số phiếu nhập: " + lblTongPhieu.getText(), fontInfo));
                    document.add(new Paragraph("Tổng tiền đã chi: " + lblTongTien.getText(), fontInfo));
                    document.add(new Paragraph("----------------------------------------------------------------", fontInfo));

                    PdfPTable pdfTable = new PdfPTable(tblThongKe.getColumnCount());
                    pdfTable.setWidthPercentage(100); pdfTable.setSpacingBefore(10);
                    for (int i = 0; i < tblThongKe.getColumnCount(); i++) pdfTable.addCell(new Phrase(tblThongKe.getColumnName(i), fontInfo));
                    for (int i = 0; i < tblThongKe.getRowCount(); i++) {
                        for (int j = 0; j < tblThongKe.getColumnCount(); j++) pdfTable.addCell(new Phrase(tblThongKe.getValueAt(i, j).toString(), fontInfo));
                    }
                    document.add(pdfTable); document.close();
                    JOptionPane.showMessageDialog(this, "Xuất PDF thành công!");
                } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Lỗi xuất PDF: " + ex.getMessage()); }
            }
        });
    }
}