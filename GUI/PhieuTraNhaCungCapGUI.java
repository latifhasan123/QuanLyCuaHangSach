package GUI;

import BUS.ChiTietTraNhaCungCapBUS;
import BUS.PhieuTraNhaCungCapBUS;
import DTO.ChiTietTraNhaCungCapDTO;
import DTO.PhieuTraNhaCungCapDTO;

import java.awt.*;
import java.awt.Color;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

// Thư viện PDF & Excel
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class PhieuTraNhaCungCapGUI extends JPanel {

    private Color colorBg = Color.decode("#F8F4EC");
    private Color colorText = Color.decode("#43334C");
    private Color colorPrimary = Color.decode("#E83C91");
    private Color colorBtnBg = Color.decode("#FF8FB7");
    
    private java.awt.Font fontChuan = new java.awt.Font("Arial", java.awt.Font.PLAIN, 14);
    private java.awt.Font fontBold = new java.awt.Font("Arial", java.awt.Font.BOLD, 14);

    private JTable tblPhieuTra, tblChiTiet;
    private DefaultTableModel modelPT, modelCT;
    private JButton btnThem, btnXoa, btnSua, btnLamMoi, btnXuatPDF, btnXuatExcel;
    private JTextField txtTimMa, txtTuGia, txtDenGia;

    private PhieuTraNhaCungCapBUS ptnBUS = new PhieuTraNhaCungCapBUS();
    private ChiTietTraNhaCungCapBUS ctBUS = new ChiTietTraNhaCungCapBUS();

    public PhieuTraNhaCungCapGUI() {
        initComponent();
        ptnBUS.docDanhSach();
        loadDataToTablePT(ptnBUS.getListPTN());
    }

    private void initComponent() {
        this.setLayout(new BorderLayout(10, 10));
        this.setBackground(colorBg);

        // ================= KHU VỰC PHÍA BẮC =================
        JPanel pnlNorth = new JPanel(new BorderLayout());
        pnlNorth.setBackground(colorBg);

        JLabel lblTitle = new JLabel("HỆ THỐNG QUẢN LÝ PHIẾU TRẢ NHÀ CUNG CẤP", JLabel.CENTER);
        lblTitle.setOpaque(true);
        lblTitle.setBackground(colorPrimary);
        lblTitle.setForeground(colorBg);
        lblTitle.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 24));
        lblTitle.setBorder(new EmptyBorder(15, 10, 15, 10));
        pnlNorth.add(lblTitle, BorderLayout.NORTH);

        JPanel pnlSearch = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        pnlSearch.setBackground(colorBg);
        pnlSearch.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.LIGHT_GRAY));

        pnlSearch.add(createLabel("Mã Phiếu Trả:"));
        txtTimMa = createTextField(18); // Tăng lên 18
        pnlSearch.add(txtTimMa);

        pnlSearch.add(createLabel("Hoàn từ (VNĐ):"));
        txtTuGia = createTextField(18); // Tăng lên 18
        pnlSearch.add(txtTuGia);

        pnlSearch.add(createLabel("Đến giá (VNĐ):"));
        txtDenGia = createTextField(18); // Tăng lên 18
        pnlSearch.add(txtDenGia);
        
        pnlNorth.add(pnlSearch, BorderLayout.CENTER);
        this.add(pnlNorth, BorderLayout.NORTH);

        // ================= PHẦN THÂN =================
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setResizeWeight(0.5);
        splitPane.setBorder(null);

        JPanel pnlPT = new JPanel(new BorderLayout()); 
        pnlPT.setBackground(colorBg);
        pnlPT.setBorder(createTitledBorder("DANH SÁCH PHIẾU TRẢ HÀNG"));
        
        String[] colPT = {"ID", "MÃ PHIẾU TRẢ", "MÃ NHÂN VIÊN", "MÃ NHÀ CUNG CẤP", "LÝ DO TRẢ", "TỔNG TIỀN HOÀN", "NGÀY TẠO"};
        modelPT = new DefaultTableModel(colPT, 0) { @Override public boolean isCellEditable(int r, int c) { return false; } };
        tblPhieuTra = createTable(modelPT);
        tblPhieuTra.getColumnModel().getColumn(0).setMinWidth(0);
        tblPhieuTra.getColumnModel().getColumn(0).setMaxWidth(0);
        pnlPT.add(new JScrollPane(tblPhieuTra), BorderLayout.CENTER);

        JPanel pnlCT = new JPanel(new BorderLayout()); 
        pnlCT.setBackground(colorBg);
        pnlCT.setBorder(createTitledBorder("CHI TIẾT MẶT HÀNG TRẢ"));
        
        String[] colCT = {"MÃ CHI TIẾT", "MÃ PHIẾU TRẢ", "MÃ SÁCH", "SỐ LƯỢNG TRẢ"};
        modelCT = new DefaultTableModel(colCT, 0) { @Override public boolean isCellEditable(int r, int c) { return false; } };
        tblChiTiet = createTable(modelCT);
        pnlCT.add(new JScrollPane(tblChiTiet), BorderLayout.CENTER);

        splitPane.setTopComponent(pnlPT);
        splitPane.setBottomComponent(pnlCT);
        this.add(splitPane, BorderLayout.CENTER);

        // ================= NÚT BẤM =================
        JPanel pnlBottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        pnlBottom.setBackground(colorBg);

        btnThem = createButton("THÊM", colorText);
        btnXoa = createButton("XÓA", colorText);
        btnSua = createButton("SỬA", colorText);
        btnLamMoi = createButton("LÀM MỚI", colorText);
        btnXuatPDF = createButton("XUẤT PDF", Color.RED);
        btnXuatExcel = createButton("XUẤT EXCEL", Color.decode("#008000"));

        pnlBottom.add(btnThem);
        pnlBottom.add(btnXoa);
        pnlBottom.add(btnSua);
        pnlBottom.add(btnLamMoi);
        pnlBottom.add(btnXuatPDF);
        pnlBottom.add(btnXuatExcel);
        this.add(pnlBottom, BorderLayout.SOUTH);

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
        txt.setBorder(BorderFactory.createLineBorder(colorText, 1));
        return txt;
    }

    private JTable createTable(DefaultTableModel model) {
        JTable tbl = new JTable(model);
        tbl.setRowHeight(35);
        tbl.setFont(fontChuan);
        tbl.setSelectionBackground(colorBtnBg);
        JTableHeader header = tbl.getTableHeader();
        header.setFont(fontBold);
        header.setForeground(colorText);
        return tbl;
    }

    private TitledBorder createTitledBorder(String title) {
        TitledBorder border = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(colorText), title);
        border.setTitleColor(colorText);
        border.setTitleFont(fontBold);
        return border;
    }

    private JButton createButton(String text, Color fgColor) {
        JButton btn = new JButton(text);
        btn.setFont(fontBold);
        btn.setBackground(colorBtnBg);
        btn.setForeground(fgColor);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setFocusPainted(false);
        return btn;
    }

    private void loadDataToTablePT(ArrayList<PhieuTraNhaCungCapDTO> list) {
        modelPT.setRowCount(0);
        for (PhieuTraNhaCungCapDTO pt : list) {
            modelPT.addRow(new Object[]{ 
                pt.getMaID(), pt.getMaPTN(), pt.getMaNV(), pt.getMaNCC(), pt.getLyDo(), 
                String.format("%,.0f VNĐ", pt.getTongTienHoan()), pt.getNgayTao() 
            });
        }
    }

    private void addEvents() {
        tblPhieuTra.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tblPhieuTra.getSelectedRow();
                if (row >= 0) {
                    int maID = Integer.parseInt(tblPhieuTra.getValueAt(row, 0).toString());
                    ArrayList<ChiTietTraNhaCungCapDTO> listCT = ctBUS.getListCTPTN(maID);
                    modelCT.setRowCount(0);
                    for (ChiTietTraNhaCungCapDTO ct : listCT) {
                        modelCT.addRow(new Object[]{ ct.getMaCTPTN(), ct.getMaPTN(), ct.getMaSach(), ct.getSoLuong() });
                    }
                }
            }
        });

        // TÌM KIẾM REAL-TIME
        DocumentListener filter = new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { filterRealTime(); }
            @Override public void removeUpdate(DocumentEvent e) { filterRealTime(); }
            @Override public void changedUpdate(DocumentEvent e) { filterRealTime(); }
        };
        txtTimMa.getDocument().addDocumentListener(filter);
        txtTuGia.getDocument().addDocumentListener(filter);
        txtDenGia.getDocument().addDocumentListener(filter);

        btnLamMoi.addActionListener(e -> {
            txtTimMa.setText(""); txtTuGia.setText(""); txtDenGia.setText("");
            ptnBUS.docDanhSach();
            loadDataToTablePT(ptnBUS.getListPTN());
            modelCT.setRowCount(0);
        });
        
        btnThem.addActionListener(e -> {
            TaoPhieuTraGUI form = new TaoPhieuTraGUI();
            form.setVisible(true);
            form.addWindowListener(new WindowAdapter() {
                @Override public void windowClosed(WindowEvent e) { btnLamMoi.doClick(); }
            });
        });

        btnXoa.addActionListener(e -> {
            int row = tblPhieuTra.getSelectedRow();
            if (row < 0) { JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 Phiếu Trả để xóa!"); return; }
            
            int maID = Integer.parseInt(tblPhieuTra.getValueAt(row, 0).toString());
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa Phiếu này? Hệ thống sẽ CỘNG LẠI số lượng sách vào kho!", "Cảnh báo", JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                if(ptnBUS.xoaPhieuTra(maID)) {
                    JOptionPane.showMessageDialog(this, "Xóa phiếu thành công! Đã phục hồi Tồn kho.");
                    loadDataToTablePT(ptnBUS.getListPTN());
                    modelCT.setRowCount(0);
                } else {
                    JOptionPane.showMessageDialog(this, "Lỗi khi xóa phiếu!");
                }
            }
        });

        btnSua.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Phiếu Trả là chứng từ kế toán. Vui lòng XÓA phiếu sai (để kho tự phục hồi) và TẠO MỚI phiếu khác!");
        });

        btnXuatPDF.addActionListener(e -> {
            int row = tblPhieuTra.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 Phiếu Trả trong bảng để in!");
                return;
            }
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                try {
                    String path = fileChooser.getSelectedFile().getAbsolutePath();
                    if (!path.endsWith(".pdf")) path += ".pdf";

                    Document document = new Document();
                    PdfWriter.getInstance(document, new java.io.FileOutputStream(path));
                    document.open();

                    String fontPath = "C:\\Windows\\Fonts\\Arial.ttf";
                    if (!new File(fontPath).exists()) fontPath = "C:\\Windows\\Fonts\\arial.ttf"; 
                    BaseFont bf = BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
                    com.itextpdf.text.Font fontTitle = new com.itextpdf.text.Font(bf, 18, com.itextpdf.text.Font.BOLD);
                    com.itextpdf.text.Font fontInfo = new com.itextpdf.text.Font(bf, 12);

                    // --- LOGIC TÌM TÊN NCC ---
                    String maNCC = tblPhieuTra.getValueAt(row, 3).toString();
                    String tenNCC = "Không xác định";
                    BUS.NhaCungCapBUS nccBUS = new BUS.NhaCungCapBUS(); 
                    for(DTO.NhaCungCapDTO ncc : nccBUS.getListNCC()) {
                        if(String.valueOf(ncc.getMaID()).equals(maNCC)) {
                            tenNCC = ncc.getTenNCC(); break;
                        }
                    }

                    document.add(new Paragraph("BIÊN BẢN TRẢ HÀNG NHÀ CUNG CẤP", fontTitle));
                    document.add(new Paragraph("Mã phiếu trả: " + tblPhieuTra.getValueAt(row, 1), fontInfo));
                    document.add(new Paragraph("Nhân viên lập: " + tblPhieuTra.getValueAt(row, 2), fontInfo));
                    // ĐÃ CẬP NHẬT HIỂN THỊ TÊN NCC
                    document.add(new Paragraph("Nhà cung cấp: " + maNCC + " - " + tenNCC, fontInfo)); 
                    document.add(new Paragraph("Lý do trả: " + tblPhieuTra.getValueAt(row, 4), fontInfo));
                    document.add(new Paragraph("Ngày lập: " + tblPhieuTra.getValueAt(row, 6), fontInfo));
                    document.add(new Paragraph("----------------------------------------------------------------", fontInfo));

                    PdfPTable pdfTable = new PdfPTable(tblChiTiet.getColumnCount());
                    pdfTable.setWidthPercentage(100);
                    pdfTable.setSpacingBefore(10);

                    for (int i = 0; i < tblChiTiet.getColumnCount(); i++) {
                        pdfTable.addCell(new Phrase(tblChiTiet.getColumnName(i), fontInfo));
                    }
                    for (int i = 0; i < tblChiTiet.getRowCount(); i++) {
                        for (int j = 0; j < tblChiTiet.getColumnCount(); j++) {
                            Object val = tblChiTiet.getValueAt(i, j);
                            pdfTable.addCell(new Phrase(val != null ? val.toString() : "", fontInfo));
                        }
                    }
                    document.add(pdfTable);
                    document.add(new Paragraph("\nTỔNG TIỀN NHẬN HOÀN LẠI: " + tblPhieuTra.getValueAt(row, 5), fontTitle));
                    document.close();
                    
                    JOptionPane.showMessageDialog(this, "Xuất PDF Phiếu Trả thành công!");
                } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Lỗi xuất PDF: " + ex.getMessage()); }
            }
        });

        btnXuatExcel.addActionListener(e -> { /* Tương tự Phiếu Nhập */ });
        btnXuatPDF.addActionListener(e -> { /* Tương tự Phiếu Nhập */ });
    }

    private void filterRealTime() {
        String ma = txtTimMa.getText().trim().toLowerCase();
        Double min = null, max = null;
        try { if(!txtTuGia.getText().isEmpty()) min = Double.parseDouble(txtTuGia.getText()); } catch(Exception e){}
        try { if(!txtDenGia.getText().isEmpty()) max = Double.parseDouble(txtDenGia.getText()); } catch(Exception e){}

        ArrayList<PhieuTraNhaCungCapDTO> dsLoc = new ArrayList<>();
        for(PhieuTraNhaCungCapDTO pt : ptnBUS.getListPTN()) {
            String idStr = pt.getMaPTN() != null ? pt.getMaPTN().toLowerCase() : "";
            boolean mMa = ma.isEmpty() || idStr.contains(ma);
            boolean mMin = min == null || pt.getTongTienHoan() >= min;
            boolean mMax = max == null || pt.getTongTienHoan() <= max;
            if(mMa && mMin && mMax) dsLoc.add(pt);
        }
        loadDataToTablePT(dsLoc);
    }
}