package GUI;

import BUS.PhieuNhapBUS;
import DAO.ChiTietPhieuNhapDAO; // Đã đổi sang DAO để đồng bộ
import DTO.PhieuNhapDTO;
import DTO.ChiTietPhieuNhapDTO;

import java.awt.*;
import java.awt.Color;
import java.awt.event.*;
import java.io.File;
import java.io.FileOutputStream;
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
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class PhieuNhapGUI extends JPanel {

    private Color colorBg = Color.decode("#F8F4EC");      
    private Color colorText = Color.decode("#43334C");    
    private Color colorPrimary = Color.decode("#E83C91"); 
    private Color colorBtnBg = Color.decode("#FF8FB7");   

    private java.awt.Font fontChuan = new java.awt.Font("Arial", java.awt.Font.PLAIN, 14);
    private java.awt.Font fontBold = new java.awt.Font("Arial", java.awt.Font.BOLD, 14);

    private JTable tblPhieuNhap, tblChiTiet;
    private DefaultTableModel modelPN, modelCT;
    private JButton btnThem, btnXoa, btnSua, btnLamMoi, btnXuatPDF, btnXuatExcel;
    private JTextField txtTimMa, txtTuGia, txtDenGia;

    private PhieuNhapBUS pnBUS = new PhieuNhapBUS();
    private ChiTietPhieuNhapDAO ctpnDAO = new ChiTietPhieuNhapDAO(); // Gọi trực tiếp DAO cho an toàn

    public PhieuNhapGUI() {
        initComponent();
        pnBUS.docDanhSach();
        loadDataToTablePN(pnBUS.getListPN());
    }

    private void initComponent() {
        this.setLayout(new BorderLayout(10, 10));
        this.setBackground(colorBg);

        // ================= KHU VỰC PHÍA BẮC =================
        JPanel pnlNorth = new JPanel(new BorderLayout());
        pnlNorth.setBackground(colorBg);

        JLabel lblTitle = new JLabel("HỆ THỐNG QUẢN LÝ PHIẾU NHẬP HÀNG", JLabel.CENTER);
        lblTitle.setOpaque(true);
        lblTitle.setBackground(colorPrimary);
        lblTitle.setForeground(colorBg);
        lblTitle.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 24));
        lblTitle.setBorder(new EmptyBorder(15, 10, 15, 10));
        pnlNorth.add(lblTitle, BorderLayout.NORTH);

        // BỘ LỌC REAL-TIME (Giao diện ngang đẹp mắt)
        JPanel pnlSearch = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        pnlSearch.setBackground(colorBg);
        pnlSearch.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.LIGHT_GRAY));

        pnlSearch.add(createLabel("Mã Phiếu Nhập:"));
        txtTimMa = createTextField(15);
        pnlSearch.add(txtTimMa);

        pnlSearch.add(createLabel("Từ Giá (VNĐ):"));
        txtTuGia = createTextField(15);
        pnlSearch.add(txtTuGia);

        pnlSearch.add(createLabel("Đến Giá (VNĐ):"));
        txtDenGia = createTextField(15);
        pnlSearch.add(txtDenGia);
        
        pnlNorth.add(pnlSearch, BorderLayout.CENTER);
        this.add(pnlNorth, BorderLayout.NORTH);

        // ================= BẢNG DỮ LIỆU =================
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setResizeWeight(0.5);
        splitPane.setBorder(null);

        // BẢNG PHIẾU NHẬP
        JPanel pnlPN = new JPanel(new BorderLayout());
        pnlPN.setBackground(colorBg);
        pnlPN.setBorder(createTitledBorder("DANH SÁCH PHIẾU NHẬP"));
        
        String[] colPN = {"ID", "MÃ PHIẾU NHẬP", "MÃ NHÂN VIÊN", "MÃ NHÀ CUNG CẤP", "TỔNG TIỀN", "TRẠNG THÁI", "NGÀY TẠO"};
        modelPN = new DefaultTableModel(colPN, 0) { @Override public boolean isCellEditable(int r, int c) { return false; } };
        tblPhieuNhap = createTable(modelPN);
        tblPhieuNhap.getColumnModel().getColumn(0).setMinWidth(0);
        tblPhieuNhap.getColumnModel().getColumn(0).setMaxWidth(0);
        pnlPN.add(new JScrollPane(tblPhieuNhap), BorderLayout.CENTER);

        // BẢNG CHI TIẾT
        JPanel pnlCT = new JPanel(new BorderLayout());
        pnlCT.setBackground(colorBg);
        pnlCT.setBorder(createTitledBorder("CHI TIẾT PHIẾU NHẬP"));
        
        String[] colCT = {"MÃ CHI TIẾT", "MÃ PHIẾU NHẬP", "MÃ SÁCH", "SỐ LƯỢNG", "ĐƠN GIÁ NHẬP", "THÀNH TIỀN"};
        modelCT = new DefaultTableModel(colCT, 0) { @Override public boolean isCellEditable(int r, int c) { return false; } };
        tblChiTiet = createTable(modelCT);
        pnlCT.add(new JScrollPane(tblChiTiet), BorderLayout.CENTER);

        splitPane.setTopComponent(pnlPN);
        splitPane.setBottomComponent(pnlCT);
        this.add(splitPane, BorderLayout.CENTER);

        // ================= NÚT BẤM =================
        JPanel pnlBottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 15));
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

    private JTextField createTextField(int columns) {
        JTextField txt = new JTextField(columns);
        txt.setFont(fontChuan);
        txt.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(colorText, 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        return txt;
    }

    private JTable createTable(DefaultTableModel model) {
        JTable tbl = new JTable(model);
        tbl.setRowHeight(35);
        tbl.setFont(fontChuan);
        tbl.setForeground(colorText);
        tbl.setSelectionBackground(colorBtnBg);
        tbl.setSelectionForeground(colorText);
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

    private void loadDataToTablePN(ArrayList<PhieuNhapDTO> list) {
        modelPN.setRowCount(0);
        for (PhieuNhapDTO pn : list) {
            modelPN.addRow(new Object[]{
                pn.getMaID(), pn.getMaPN(), pn.getMaNV(), pn.getMaNCC(), 
                String.format("%,.0f VNĐ", pn.getTongTien()), pn.getTrangThai(), pn.getNgayTao()
            });
        }
    }

    private void addEvents() {
        tblPhieuNhap.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tblPhieuNhap.getSelectedRow();
                if (row >= 0) {
                    String maIDString = tblPhieuNhap.getValueAt(row, 0).toString();
                    // ĐÃ SỬA: Gọi từ ctpnDAO thay vì ctpnBUS
                    ArrayList<ChiTietPhieuNhapDTO> listCT = ctpnDAO.selectAll(maIDString);
                    modelCT.setRowCount(0);
                    for (ChiTietPhieuNhapDTO ct : listCT) {
                        modelCT.addRow(new Object[]{
                            ct.getMaCTPN(), ct.getMaPN(), ct.getMaSach(), ct.getSoLuong(), 
                            String.format("%,.0f", ct.getGiaNhap()), // ĐÃ SỬA: getGiaNhap() thay vì getDonGia()
                            String.format("%,.0f", ct.getThanhTien())
                        });
                    }
                }
            }
        });

        // BỘ LỌC REAL-TIME
        DocumentListener filterListener = new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { filterRealTime(); }
            @Override public void removeUpdate(DocumentEvent e) { filterRealTime(); }
            @Override public void changedUpdate(DocumentEvent e) { filterRealTime(); }
        };
        txtTimMa.getDocument().addDocumentListener(filterListener);
        txtTuGia.getDocument().addDocumentListener(filterListener);
        txtDenGia.getDocument().addDocumentListener(filterListener);

        btnLamMoi.addActionListener(e -> {
            txtTimMa.setText(""); txtTuGia.setText(""); txtDenGia.setText("");
            pnBUS.docDanhSach();
            loadDataToTablePN(pnBUS.getListPN());
            modelCT.setRowCount(0);
        });

        btnThem.addActionListener(e -> {
            TaoPhieuNhapGUI gui = new TaoPhieuNhapGUI();
            gui.setVisible(true);
            gui.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    btnLamMoi.doClick();
                }
            });
        });

        btnXoa.addActionListener(e -> {
            int row = tblPhieuNhap.getSelectedRow();
            if (row < 0) { JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 Phiếu Nhập để xóa!"); return; }
            
            int maID = Integer.parseInt(tblPhieuNhap.getValueAt(row, 0).toString());
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa? Hệ thống sẽ TRỪ lại số lượng sách trong kho!", "Cảnh báo", JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                if(pnBUS.xoaPhieuNhap(maID)) {
                    JOptionPane.showMessageDialog(this, "Xóa phiếu nhập thành công! Đã trừ kho.");
                    loadDataToTablePN(pnBUS.getListPN()); // Load lại bảng
                    modelCT.setRowCount(0); // Xóa trắng bảng chi tiết
                } else {
                    JOptionPane.showMessageDialog(this, "Lỗi khi xóa phiếu!");
                }
            }
        });

        btnSua.addActionListener(e -> {
             JOptionPane.showMessageDialog(this, "Để đảm bảo tính toàn vẹn của Tồn Kho, vui lòng XÓA phiếu nhập bị sai và TẠO MỚI phiếu khác!");
        });

        // --- XUẤT EXCEL ---
        btnXuatExcel.addActionListener(e -> {
            if (tblPhieuNhap.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "Không có dữ liệu để xuất!");
                return;
            }
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                try {
                    String path = fileChooser.getSelectedFile().getAbsolutePath();
                    if (!path.endsWith(".xlsx")) path += ".xlsx";
                    Workbook workbook = new XSSFWorkbook();
                    Sheet sheet = workbook.createSheet("DanhSachPhieuNhap");
                    Row headerRow = sheet.createRow(0);
                    // Bỏ cột ID ẩn (cột 0)
                    for (int i = 1; i < tblPhieuNhap.getColumnCount(); i++) {
                        headerRow.createCell(i-1).setCellValue(tblPhieuNhap.getColumnName(i));
                    }
                    for (int i = 0; i < tblPhieuNhap.getRowCount(); i++) {
                        Row row = sheet.createRow(i + 1);
                        for (int j = 1; j < tblPhieuNhap.getColumnCount(); j++) {
                            Object val = tblPhieuNhap.getValueAt(i, j);
                            row.createCell(j-1).setCellValue(val != null ? val.toString() : "");
                        }
                    }
                    java.io.FileOutputStream out = new java.io.FileOutputStream(path);
                    workbook.write(out);
                    out.close();
                    workbook.close();
                    JOptionPane.showMessageDialog(this, "Xuất Excel thành công!");
                } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Lỗi xuất Excel: " + ex.getMessage()); }
            }
        });

        // --- XUẤT PDF ---
        btnXuatPDF.addActionListener(e -> {
            int row = tblPhieuNhap.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 Phiếu Nhập trong bảng để in!");
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

                    // Cài đặt Font Arial hỗ trợ Tiếng Việt
                    String fontPath = "C:\\Windows\\Fonts\\Arial.ttf";
                    if (!new File(fontPath).exists()) fontPath = "C:\\Windows\\Fonts\\arial.ttf"; 
                    BaseFont bf;
                    if (new File(fontPath).exists()) {
                        bf = BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
                    } else {
                        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);
                    }
                    com.itextpdf.text.Font fontTitle = new com.itextpdf.text.Font(bf, 18, com.itextpdf.text.Font.BOLD);
                    com.itextpdf.text.Font fontInfo = new com.itextpdf.text.Font(bf, 12);

                    // Tìm tên nhà cung cấp
                    String maNCC = tblPhieuNhap.getValueAt(row, 3).toString();
                    String tenNCC = "Không xác định";
                    BUS.NhaCungCapBUS nccBUS = new BUS.NhaCungCapBUS(); 
                    for(DTO.NhaCungCapDTO ncc : nccBUS.getListNCC()) {
                        if(String.valueOf(ncc.getMaID()).equals(maNCC)) {
                            tenNCC = ncc.getTenNCC();
                            break;
                        }
                    }

                    document.add(new Paragraph("BIÊN BẢN PHIẾU NHẬP HÀNG", fontTitle));
                    document.add(new Paragraph("Mã phiếu: " + tblPhieuNhap.getValueAt(row, 1), fontInfo));
                    document.add(new Paragraph("Nhân viên lập: " + tblPhieuNhap.getValueAt(row, 2), fontInfo));
                    document.add(new Paragraph("Nhà cung cấp: " + maNCC + " - " + tenNCC, fontInfo)); 
                    document.add(new Paragraph("Ngày lập: " + tblPhieuNhap.getValueAt(row, 6), fontInfo));
                    document.add(new Paragraph("----------------------------------------------------------------", fontInfo));

                    PdfPTable pdfTable = new PdfPTable(tblChiTiet.getColumnCount());
                    pdfTable.setWidthPercentage(100);
                    pdfTable.setSpacingBefore(10);

                    // Header cho PDF Table
                    for (int i = 0; i < tblChiTiet.getColumnCount(); i++) {
                        pdfTable.addCell(new Phrase(tblChiTiet.getColumnName(i), fontInfo));
                    }
                    // Data cho PDF Table
                    for (int i = 0; i < tblChiTiet.getRowCount(); i++) {
                        for (int j = 0; j < tblChiTiet.getColumnCount(); j++) {
                            Object val = tblChiTiet.getValueAt(i, j);
                            pdfTable.addCell(new Phrase(val != null ? val.toString() : "", fontInfo));
                        }
                    }
                    document.add(pdfTable);
                    document.add(new Paragraph("\nTỔNG TIỀN THANH TOÁN: " + tblPhieuNhap.getValueAt(row, 4), fontTitle));
                    document.close();
                    
                    JOptionPane.showMessageDialog(this, "Xuất PDF Phiếu Nhập thành công!");
                } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Lỗi xuất PDF: " + ex.getMessage()); }
            }
        });
    }

    private void filterRealTime() {
        String ma = txtTimMa.getText().trim().toLowerCase();
        Double min = null;
        Double max = null;
        
        try { if (!txtTuGia.getText().trim().isEmpty()) min = Double.parseDouble(txtTuGia.getText().trim()); } catch (Exception ex) {}
        try { if (!txtDenGia.getText().trim().isEmpty()) max = Double.parseDouble(txtDenGia.getText().trim()); } catch (Exception ex) {}

        ArrayList<PhieuNhapDTO> dsLoc = new ArrayList<>();
        for (PhieuNhapDTO pn : pnBUS.getListPN()) {
            boolean matchMa = ma.isEmpty() || pn.getMaPN().toLowerCase().contains(ma);
            boolean matchMin = (min == null) || (pn.getTongTien() >= min);
            boolean matchMax = (max == null) || (pn.getTongTien() <= max);

            if (matchMa && matchMin && matchMax) {
                dsLoc.add(pn);
            }
        }
        loadDataToTablePN(dsLoc);
    }
}