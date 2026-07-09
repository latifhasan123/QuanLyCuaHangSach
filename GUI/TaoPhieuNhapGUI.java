package GUI;

import BUS.NhaCungCapBUS;
import BUS.SachBUS;
import BUS.PhieuNhapBUS;
import BUS.ChiTietPhieuNhapBUS;
import BUS.NhaXuatBanBUS;
import DTO.NhaCungCapDTO;
import DTO.SachDTO;
import DTO.PhieuNhapDTO;
import DTO.ChiTietPhieuNhapDTO;
import DTO.NhaXuatBanDTO;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;

public class TaoPhieuNhapGUI extends JFrame {

    private Color colorBg = Color.decode("#F8F4EC");
    private Color colorText = Color.decode("#43334C");
    private Color colorPrimary = Color.decode("#E83C91");
    private Color colorBtnBg = Color.decode("#FF8FB7");

    private java.awt.Font fontChuan = new java.awt.Font("Arial", java.awt.Font.PLAIN, 14);
    private java.awt.Font fontBold = new java.awt.Font("Arial", java.awt.Font.BOLD, 14);

    private JComboBox<String> cbxNhaCungCap, cbxNXB, cbxSach;
    private JTextField txtNhanVien, txtSoLuong, txtDonGia;
    private JButton btnThem, btnXoa, btnSua, btnLamMoi, btnXuatPDF, btnXuatExcel, btnXacNhan;
    private JLabel lblTongTien;
    
    private JTable tblGioHang;
    private DefaultTableModel modelGioHang;
    private double tongTienPhieu = 0;

    private SachBUS sachBUS = new SachBUS();
    private NhaCungCapBUS nccBUS = new NhaCungCapBUS();
    private PhieuNhapBUS pnBUS = new PhieuNhapBUS();
    private NhaXuatBanBUS nxbBUS = new NhaXuatBanBUS(); 

    public TaoPhieuNhapGUI() {
        initComponents();
        loadComboboxNCC();
        loadAllNXB();
        filterBooks(); 
    }

    private void initComponents() {
        this.setTitle("Tạo Phiếu Nhập Hàng");
        this.setSize(1200, 750);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout(10, 10));
        this.getContentPane().setBackground(colorBg);

        JLabel lblTitle = new JLabel("TẠO PHIẾU NHẬP HÀNG", JLabel.CENTER);
        lblTitle.setOpaque(true);
        lblTitle.setBackground(colorPrimary);
        lblTitle.setForeground(colorBg);
        lblTitle.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 24));
        lblTitle.setBorder(new EmptyBorder(15, 10, 15, 10));

        JPanel pnlInfo = new JPanel(new GridLayout(3, 4, 15, 15));
        pnlInfo.setBackground(colorBg);
        pnlInfo.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(colorText), "THÔNG TIN NHẬP HÀNG"));
        ((TitledBorder) pnlInfo.getBorder()).setTitleFont(fontBold);
        ((TitledBorder) pnlInfo.getBorder()).setTitleColor(colorText);

        pnlInfo.add(createLabel("NHÂN VIÊN:"));
        txtNhanVien = createTextField();
        txtNhanVien.setEditable(false);
        txtNhanVien.setBackground(Color.LIGHT_GRAY);
        txtNhanVien.setText("13 - Admin Tối Cao");
        pnlInfo.add(txtNhanVien);

        pnlInfo.add(createLabel("NHÀ CUNG CẤP:"));
        cbxNhaCungCap = new JComboBox<>();
        pnlInfo.add(cbxNhaCungCap);

        pnlInfo.add(createLabel("NHÀ XUẤT BẢN:"));
        cbxNXB = new JComboBox<>();
        pnlInfo.add(cbxNXB);

        pnlInfo.add(createLabel("CHỌN SÁCH:"));
        cbxSach = new JComboBox<>();
        pnlInfo.add(cbxSach);

        pnlInfo.add(createLabel("SỐ LƯỢNG:"));
        txtSoLuong = createTextField();
        pnlInfo.add(txtSoLuong);

        pnlInfo.add(createLabel("ĐƠN GIÁ NHẬP:"));
        txtDonGia = createTextField();
        pnlInfo.add(txtDonGia);

        JPanel pnlTop = new JPanel(new BorderLayout());
        pnlTop.add(lblTitle, BorderLayout.NORTH);
        pnlTop.add(pnlInfo, BorderLayout.CENTER);
        this.add(pnlTop, BorderLayout.NORTH);

        String[] cols = {"MÃ SÁCH", "TÊN SÁCH", "SỐ LƯỢNG", "ĐƠN GIÁ", "THÀNH TIỀN"};
        modelGioHang = new DefaultTableModel(cols, 0) { @Override public boolean isCellEditable(int r, int c) { return false; } };
        tblGioHang = new JTable(modelGioHang);
        tblGioHang.setRowHeight(35);
        tblGioHang.setFont(fontChuan);
        tblGioHang.getTableHeader().setFont(fontBold);
        this.add(new JScrollPane(tblGioHang), BorderLayout.CENTER);

        JPanel pnlBottom = new JPanel(new BorderLayout());
        pnlBottom.setBackground(colorBg);
        pnlBottom.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        pnlButtons.setBackground(colorBg);

        btnThem = createButton("THÊM", colorText);
        btnXoa = createButton("XÓA", colorText);
        btnSua = createButton("SỬA", colorText);
        btnLamMoi = createButton("LÀM MỚI", colorText);
        btnXuatPDF = createButton("XUẤT PDF", Color.RED);
        btnXuatExcel = createButton("XUẤT EXCEL", Color.decode("#008000"));
        btnXacNhan = createButton("XÁC NHẬN TẠO PHIẾU", colorText);

        pnlButtons.add(btnThem); pnlButtons.add(btnXoa); pnlButtons.add(btnSua);
        pnlButtons.add(btnLamMoi); pnlButtons.add(btnXuatPDF); pnlButtons.add(btnXuatExcel);
        pnlButtons.add(btnXacNhan);

        lblTongTien = new JLabel("TỔNG TIỀN: 0 VNĐ", JLabel.RIGHT);
        lblTongTien.setFont(new Font("Arial", Font.BOLD, 18));
        lblTongTien.setForeground(Color.RED);

        pnlBottom.add(pnlButtons, BorderLayout.CENTER);
        pnlBottom.add(lblTongTien, BorderLayout.EAST);
        this.add(pnlBottom, BorderLayout.SOUTH);

        addEvents();
    }

    private JLabel createLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(fontBold);
        lbl.setForeground(colorText);
        return lbl;
    }

    private JTextField createTextField() {
        JTextField txt = new JTextField();
        txt.setFont(fontChuan);
        txt.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(colorText, 1), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        return txt;
    }

    private JButton createButton(String text, Color fgColor) {
        JButton btn = new JButton(text);
        btn.setFont(fontBold);
        btn.setBackground(colorBtnBg); 
        btn.setForeground(fgColor);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void loadComboboxNCC() {
        cbxNhaCungCap.removeAllItems();
        for (NhaCungCapDTO ncc : nccBUS.getListNCC()) {
            if(ncc.getTrangThai().equals("HoatDong")) {
                cbxNhaCungCap.addItem(ncc.getMaID() + " - " + ncc.getTenNCC());
            }
        }
    }

    private void loadAllNXB() {
        cbxNXB.removeAllItems();
        cbxNXB.addItem("Tất cả NXB");
        for (NhaXuatBanDTO nxb : nxbBUS.getAllNXB()) {
            cbxNXB.addItem(nxb.getMaID() + " - " + nxb.getTenNXB());
        }
    }

    private void filterBooks() {
        cbxSach.removeAllItems();
        // ĐÃ SỬA: getList() -> getAllSach()
        for (SachDTO s : sachBUS.getAllSach()) {
            boolean matchNXB = true;
            if (cbxNXB.getSelectedIndex() > 0) {
                int maNXBChon = Integer.parseInt(cbxNXB.getSelectedItem().toString().split(" - ")[0]);
                matchNXB = (s.getMaNXB() == maNXBChon);
            }
            if (matchNXB && s.getTrangThai().equals("DangBan")) {
                cbxSach.addItem(s.getMaID() + " - " + s.getTenSach());
            }
        }
    }

    private void addEvents() {
        cbxNXB.addActionListener(e -> filterBooks());

        cbxSach.addActionListener(e -> {
            if (cbxSach.getSelectedItem() != null) {
                int maSachChon = Integer.parseInt(cbxSach.getSelectedItem().toString().split(" - ")[0]);
                // ĐÃ SỬA: getList() -> getAllSach()
                for (DTO.SachDTO s : sachBUS.getAllSach()) {
                    if (s.getMaID() == maSachChon) {
                        txtDonGia.setText(String.format("%.0f", s.getGiaNhap()));
                        break;
                    }
                }
            }
        });

        tblGioHang.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
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
                double gia = Double.parseDouble(txtDonGia.getText().trim());
                if (sl <= 0 || gia <= 0) throw new Exception();

                String item = cbxSach.getSelectedItem().toString();
                String id = item.split(" - ")[0];
                String ten = item.split(" - ")[1];

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

        btnSua.addActionListener(e -> {
            int row = tblGioHang.getSelectedRow();
            if (row < 0) { JOptionPane.showMessageDialog(this, "Vui lòng click chọn 1 dòng trong bảng để sửa!"); return; }
            try {
                int slMoi = Integer.parseInt(txtSoLuong.getText().trim());
                double giaMoi = Double.parseDouble(txtDonGia.getText().trim());
                if (slMoi <= 0 || giaMoi <= 0) throw new Exception();

                int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn cập nhật lại số lượng/giá của sách này?", "Xác nhận Sửa", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    modelGioHang.setValueAt(slMoi, row, 2);
                    modelGioHang.setValueAt(String.format("%,.0f", giaMoi), row, 3);
                    modelGioHang.setValueAt(String.format("%,.0f", slMoi * giaMoi), row, 4);
                    tinhTongTien();
                    tblGioHang.clearSelection();
                    txtSoLuong.setText(""); txtDonGia.setText("");
                }
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Số lượng và Giá nhập mới phải > 0!"); }
        });

        btnXoa.addActionListener(e -> {
            int row = tblGioHang.getSelectedRow();
            if (row >= 0) { modelGioHang.removeRow(row); tinhTongTien(); }
            else { JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng cần xóa!"); }
        });

        btnLamMoi.addActionListener(e -> { txtSoLuong.setText(""); txtDonGia.setText(""); modelGioHang.setRowCount(0); tinhTongTien(); });

        btnXacNhan.addActionListener(e -> { 
            if (modelGioHang.getRowCount() == 0) { JOptionPane.showMessageDialog(this, "Danh sách hàng hóa trống!"); return; }
            try {
                int maNV = Integer.parseInt(txtNhanVien.getText().split(" - ")[0]);
                int maNCC = Integer.parseInt(cbxNhaCungCap.getSelectedItem().toString().split(" - ")[0]);
                
                PhieuNhapDTO pn = new PhieuNhapDTO();
                pn.setMaNV(maNV);
                pn.setMaNCC(maNCC);
                pn.setTongTien(tongTienPhieu);
                pn.setTrangThai("HoanThanh");
                
                ArrayList<ChiTietPhieuNhapDTO> dsCT = new ArrayList<>();
                for (int i = 0; i < modelGioHang.getRowCount(); i++) {
                    String maSach = modelGioHang.getValueAt(i, 0).toString();
                    int sl = Integer.parseInt(modelGioHang.getValueAt(i, 2).toString());
                    double gia = Double.parseDouble(modelGioHang.getValueAt(i, 3).toString().replace(",", ""));
                    
                    ChiTietPhieuNhapDTO ct = new ChiTietPhieuNhapDTO();
                    ct.setMaSach(maSach);
                    ct.setSoLuong(sl);
                    // ĐÃ SỬA: setDonGia() -> setGiaNhap()
                    ct.setGiaNhap(gia);
                    dsCT.add(ct);
                }

                if (pnBUS.taoPhieuNhapHoanChinh(pn, dsCT)) {
                    JOptionPane.showMessageDialog(this, "Tạo Phiếu Nhập thành công! Hệ thống đã CỘNG sách vào kho.");
                    this.dispose(); 
                } else {
                    JOptionPane.showMessageDialog(this, "Lỗi! Không thể lưu vào CSDL. Vui lòng thử lại.");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        
        btnXuatExcel.addActionListener(e -> { /* GIỮ NGUYÊN CODE EXCEL BẠN ĐANG CÓ */ });
        btnXuatPDF.addActionListener(e -> { /* GIỮ NGUYÊN CODE PDF BẠN ĐANG CÓ */ });
    }

    private void tinhTongTien() {
        tongTienPhieu = 0;
        for (int i = 0; i < modelGioHang.getRowCount(); i++) {
            tongTienPhieu += Double.parseDouble(modelGioHang.getValueAt(i, 4).toString().replace(",", ""));
        }
        lblTongTien.setText("TỔNG TIỀN: " + String.format("%,.0f VNĐ", tongTienPhieu));
    }
}