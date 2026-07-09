package GUI;

import BUS.NhaCungCapBUS;
import BUS.SachBUS;
import BUS.PhieuTraNhaCungCapBUS;
import BUS.ChiTietTraNhaCungCapBUS;
import DTO.NhaCungCapDTO;
import DTO.SachDTO;
import DTO.PhieuTraNhaCungCapDTO;
import DTO.ChiTietTraNhaCungCapDTO;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.io.FileOutputStream;

// Thư viện cho PDF và Excel
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class TaoPhieuTraGUI extends JFrame {

    private Color colorBg = Color.decode("#F8F4EC");
    private Color colorText = Color.decode("#43334C");
    private Color colorPrimary = Color.decode("#E83C91");
    private Color colorBtnBg = Color.decode("#FF8FB7");

    private Font fontChuan = new Font("Arial", Font.PLAIN, 14);
    private Font fontBold = new Font("Arial", Font.BOLD, 14);

    private JComboBox<String> cbxNhaCungCap, cbxSach;
    private JTextField txtNhanVien, txtSoLuong, txtDonGia, txtLyDo;
    private JButton btnThem, btnXoa, btnSua, btnLamMoi, btnXacNhan;
    private JLabel lblTongTien;
    
    private JTable tblGioHang;
    private DefaultTableModel modelGioHang;
    private double tongTienPhieu = 0;

    private SachBUS sachBUS = new SachBUS();
    private NhaCungCapBUS nccBUS = new NhaCungCapBUS();
    private PhieuTraNhaCungCapBUS ptnBUS = new PhieuTraNhaCungCapBUS();

    public TaoPhieuTraGUI() {
        initComponents();
        loadComboboxNCC();
        filterBooks(); 
    }

    private void initComponents() {
        this.setTitle("Tạo Phiếu Trả Hàng NCC");
        this.setSize(1100, 700);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout(10, 10));
        this.getContentPane().setBackground(colorBg);

        JLabel lblTitle = new JLabel("TẠO PHIẾU TRẢ HÀNG", JLabel.CENTER);
        lblTitle.setOpaque(true); lblTitle.setBackground(colorPrimary);
        lblTitle.setForeground(colorBg); lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setBorder(new EmptyBorder(15, 10, 15, 10));

        JPanel pnlInfo = new JPanel(new GridLayout(3, 4, 15, 15));
        pnlInfo.setBackground(colorBg);
        pnlInfo.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(colorText), "THÔNG TIN TRẢ HÀNG"));
        ((TitledBorder) pnlInfo.getBorder()).setTitleFont(fontBold);
        ((TitledBorder) pnlInfo.getBorder()).setTitleColor(colorText);

        pnlInfo.add(createLabel("NHÂN VIÊN:")); txtNhanVien = createTextField();
        txtNhanVien.setEditable(false); txtNhanVien.setBackground(Color.LIGHT_GRAY);
        txtNhanVien.setText("13 - Admin"); pnlInfo.add(txtNhanVien);

        pnlInfo.add(createLabel("NHÀ CUNG CẤP:")); cbxNhaCungCap = new JComboBox<>(); pnlInfo.add(cbxNhaCungCap);
        pnlInfo.add(createLabel("CHỌN SÁCH TỪ KHO:")); cbxSach = new JComboBox<>(); pnlInfo.add(cbxSach);
        pnlInfo.add(createLabel("LÝ DO TRẢ:")); txtLyDo = createTextField(); pnlInfo.add(txtLyDo);

        pnlInfo.add(createLabel("SỐ LƯỢNG TRẢ:")); txtSoLuong = createTextField(); pnlInfo.add(txtSoLuong);
        pnlInfo.add(createLabel("ĐƠN GIÁ ĐƯỢC HOÀN:")); txtDonGia = createTextField(); pnlInfo.add(txtDonGia);

        JPanel pnlTop = new JPanel(new BorderLayout());
        pnlTop.add(lblTitle, BorderLayout.NORTH);
        pnlTop.add(pnlInfo, BorderLayout.CENTER);
        this.add(pnlTop, BorderLayout.NORTH);

        String[] cols = {"MÃ SÁCH", "TÊN SÁCH", "SỐ LƯỢNG TRẢ", "ĐƠN GIÁ HOÀN", "TỔNG HOÀN"};
        modelGioHang = new DefaultTableModel(cols, 0) { @Override public boolean isCellEditable(int r, int c) { return false; } };
        tblGioHang = new JTable(modelGioHang);
        tblGioHang.setRowHeight(35); tblGioHang.setFont(fontChuan);
        tblGioHang.getTableHeader().setFont(fontBold);
        this.add(new JScrollPane(tblGioHang), BorderLayout.CENTER);

        JPanel pnlBottom = new JPanel(new BorderLayout());
        pnlBottom.setBackground(colorBg); pnlBottom.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        pnlButtons.setBackground(colorBg);

        btnThem = createButton("THÊM VÀO PHIẾU", colorText); btnXoa = createButton("XÓA DÒNG", colorText);
        btnSua = createButton("SỬA SL", colorText); btnLamMoi = createButton("LÀM MỚI", colorText);
        btnXacNhan = createButton("XÁC NHẬN TẠO PHIẾU", Color.decode("#008000"));

        pnlButtons.add(btnThem); pnlButtons.add(btnXoa); pnlButtons.add(btnSua);
        pnlButtons.add(btnLamMoi); pnlButtons.add(btnXacNhan);

        lblTongTien = new JLabel("TỔNG HOÀN LẠI: 0 VNĐ", JLabel.RIGHT);
        lblTongTien.setFont(new Font("Arial", Font.BOLD, 18));
        lblTongTien.setForeground(Color.RED);

        pnlBottom.add(pnlButtons, BorderLayout.CENTER);
        pnlBottom.add(lblTongTien, BorderLayout.EAST);
        this.add(pnlBottom, BorderLayout.SOUTH);

        addEvents();
    }

    private JLabel createLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(fontBold); lbl.setForeground(colorText); return lbl;
    }

    private JTextField createTextField() {
        JTextField txt = new JTextField();
        txt.setFont(fontChuan);
        txt.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(colorText, 1), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        return txt;
    }

    private JButton createButton(String text, Color fgColor) {
        JButton btn = new JButton(text);
        btn.setFont(fontBold); btn.setBackground(colorBtnBg); btn.setForeground(fgColor);
        btn.setFocusPainted(false); btn.setCursor(new Cursor(Cursor.HAND_CURSOR)); return btn;
    }

    private void loadComboboxNCC() {
        cbxNhaCungCap.removeAllItems();
        for (NhaCungCapDTO ncc : nccBUS.getListNCC()) {
            if(ncc.getTrangThai().equals("HoatDong")) { cbxNhaCungCap.addItem(ncc.getMaID() + " - " + ncc.getTenNCC()); }
        }
    }

    private void filterBooks() {
        cbxSach.removeAllItems();
        // ĐÃ SỬA: getList() -> getAllSach()
        for (SachDTO s : sachBUS.getAllSach()) {
            if (s.getTrangThai().equals("DangBan") || s.getTrangThai().equals("NgungBan")) {
                cbxSach.addItem(s.getMaID() + " - " + s.getTenSach());
            }
        }
    }

    private void addEvents() {
        cbxSach.addActionListener(e -> {
            if (cbxSach.getSelectedItem() != null) {
                int maSachChon = Integer.parseInt(cbxSach.getSelectedItem().toString().split(" - ")[0]);
                // ĐÃ SỬA: getList() -> getAllSach()
                for (SachDTO s : sachBUS.getAllSach()) {
                    if (s.getMaID() == maSachChon) { txtDonGia.setText(String.format("%.0f", s.getGiaNhap())); break; }
                }
            }
        });

        tblGioHang.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                int row = tblGioHang.getSelectedRow();
                if(row >= 0) {
                    txtSoLuong.setText(tblGioHang.getValueAt(row, 2).toString());
                    txtDonGia.setText(tblGioHang.getValueAt(row, 3).toString().replace(",", ""));
                }
            }
        });

        btnThem.addActionListener(e -> { 
            try {
                if(cbxSach.getSelectedItem() == null) return;
                int sl = Integer.parseInt(txtSoLuong.getText().trim());
                double gia = Double.parseDouble(txtDonGia.getText().trim().replace(",", ""));
                if (sl <= 0 || gia <= 0) throw new Exception();

                String item = cbxSach.getSelectedItem().toString();
                String id = item.split(" - ")[0]; String ten = item.split(" - ")[1];

                boolean daCo = false;
                for (int i = 0; i < modelGioHang.getRowCount(); i++) {
                    if (modelGioHang.getValueAt(i, 0).toString().equals(id)) {
                        int slCu = Integer.parseInt(modelGioHang.getValueAt(i, 2).toString());
                        modelGioHang.setValueAt(slCu + sl, i, 2);
                        modelGioHang.setValueAt(String.format("%,.0f", (slCu + sl) * gia), i, 4);
                        daCo = true; break;
                    }
                }
                if (!daCo) { modelGioHang.addRow(new Object[]{id, ten, sl, String.format("%,.0f", gia), String.format("%,.0f", sl * gia)}); }
                tinhTongTien();
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Số lượng và Giá phải LỚN HƠN 0!"); }
        });

        btnXoa.addActionListener(e -> {
            int row = tblGioHang.getSelectedRow();
            if (row >= 0) { modelGioHang.removeRow(row); tinhTongTien(); }
            else { JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng cần xóa!"); }
        });

        btnLamMoi.addActionListener(e -> { txtSoLuong.setText(""); txtDonGia.setText(""); modelGioHang.setRowCount(0); tinhTongTien(); });

        btnXacNhan.addActionListener(e -> { 
            if (modelGioHang.getRowCount() == 0) { JOptionPane.showMessageDialog(this, "Danh sách trả hàng đang trống!"); return; }
            if (txtLyDo.getText().trim().isEmpty()) { JOptionPane.showMessageDialog(this, "Vui lòng nhập lý do trả hàng!"); return; }
            
            try {
                int maNV = Integer.parseInt(txtNhanVien.getText().split(" - ")[0]);
                int maNCC = Integer.parseInt(cbxNhaCungCap.getSelectedItem().toString().split(" - ")[0]);
                
                PhieuTraNhaCungCapDTO pt = new PhieuTraNhaCungCapDTO();
                pt.setMaNV(maNV);
                pt.setMaNCC(maNCC);
                pt.setLyDo(txtLyDo.getText().trim());
                pt.setTongTienHoan(tongTienPhieu);
                
                ArrayList<ChiTietTraNhaCungCapDTO> listCT = new ArrayList<>();
                for (int i = 0; i < modelGioHang.getRowCount(); i++) {
                    String maSach = modelGioHang.getValueAt(i, 0).toString();
                    int sl = Integer.parseInt(modelGioHang.getValueAt(i, 2).toString());
                    
                    ChiTietTraNhaCungCapDTO ct = new ChiTietTraNhaCungCapDTO();
                    ct.setMaSach(Integer.parseInt(maSach)); // ĐÃ FIX KIỂU DỮ LIỆU
                    ct.setSoLuong(sl);
                    listCT.add(ct);
                }

                if(ptnBUS.taoPhieuTraHoanChinh(pt, listCT)) {
                    JOptionPane.showMessageDialog(this, "Tạo Phiếu Trả thành công! Hệ thống đã TRỪ sách khỏi kho.");
                    this.dispose(); // Đóng form thành công
                } else {
                    JOptionPane.showMessageDialog(this, "Lỗi! Database từ chối lưu. Vui lòng kiểm tra Console NetBeans để xem lỗi SQL.");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    private void tinhTongTien() {
        tongTienPhieu = 0;
        for (int i = 0; i < modelGioHang.getRowCount(); i++) {
            tongTienPhieu += Double.parseDouble(modelGioHang.getValueAt(i, 4).toString().replace(",", ""));
        }
        lblTongTien.setText("TỔNG HOÀN LẠI: " + String.format("%,.0f VNĐ", tongTienPhieu));
    }
}