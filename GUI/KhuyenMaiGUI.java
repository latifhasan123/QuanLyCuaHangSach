package GUI;

import bus.KhuyenMaiBUS;
import dto.KhuyenMaiDTO;
import enums.TrangThaiKhuyenMai;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class KhuyenMaiGUI extends JPanel {

    private KhuyenMaiBUS kmBUS = new KhuyenMaiBUS();
    private JTable tblKM;
    private DefaultTableModel modelKM;
    private JTextField txtMaCode, txtTenKM, txtPhanTram, txtSoTienGiam, txtDonHangMin, txtNgayBD, txtNgayKT;
    private JComboBox<TrangThaiKhuyenMai> cbxTrangThai;
    private int selectedMaKM = -1;

    // Bảng màu chuẩn của app
    private final Color COL_PRIMARY = Color.decode("#E889A9"); // Hồng đậm
    private final Color COL_SIDEBAR = Color.decode("#5A4664"); // Tím than
    private final Color COL_BG = Color.decode("#F8F9FA");      // Xám trắng
    private final Color COL_STRIPE = Color.decode("#FFF5F8");  // Hồng cực nhạt (dành cho dòng xen kẽ)

    public KhuyenMaiGUI() {
        initComponents();
        loadData();
    }

    private void initComponents() {
        setLayout(new BorderLayout(20, 20));
        setBackground(COL_BG);
        setBorder(new EmptyBorder(25, 30, 25, 30));

        // ================= PHẦN TRÊN: FORM NHẬP LIỆU =================
        JPanel pnlNorth = new JPanel(new BorderLayout(0, 20));
        pnlNorth.setOpaque(false);

        JLabel lblTitle = new JLabel("QUẢN LÝ CHƯƠNG TRÌNH KHUYẾN MÃI");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitle.setForeground(COL_SIDEBAR);
        pnlNorth.add(lblTitle, BorderLayout.NORTH);

        JPanel pnlInput = new JPanel(new GridLayout(4, 4, 20, 15));
        pnlInput.setBackground(Color.WHITE);
        pnlInput.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Color.decode("#E0D7CE"), 1, true), 
                new EmptyBorder(20, 25, 20, 25)));

        pnlInput.add(createLabel("Mã Code (Tự tạo):")); 
        txtMaCode = createTextField("(Hệ thống tự động sinh)"); 
        txtMaCode.setEditable(false);
        txtMaCode.setBackground(Color.decode("#F5F5F5"));
        pnlInput.add(txtMaCode);
        
        pnlInput.add(createLabel("Tên Chương Trình (*):")); txtTenKM = createTextField(""); pnlInput.add(txtTenKM);
        pnlInput.add(createLabel("% Giảm Giá (0-100):")); txtPhanTram = createTextField("0"); pnlInput.add(txtPhanTram);
        pnlInput.add(createLabel("Số Tiền Giảm (VNĐ):")); txtSoTienGiam = createTextField("0"); pnlInput.add(txtSoTienGiam);
        pnlInput.add(createLabel("Đơn Tối Thiểu (VNĐ):")); txtDonHangMin = createTextField("0"); pnlInput.add(txtDonHangMin);
        pnlInput.add(createLabel("Ngày Bắt Đầu (YYYY-MM-DD):")); txtNgayBD = createTextField(LocalDate.now().toString()); pnlInput.add(txtNgayBD);
        pnlInput.add(createLabel("Ngày Kết Thúc (YYYY-MM-DD):")); txtNgayKT = createTextField(LocalDate.now().plusMonths(1).toString()); pnlInput.add(txtNgayKT);
        
        pnlInput.add(createLabel("Trạng Thái:")); 
        cbxTrangThai = new JComboBox<>(TrangThaiKhuyenMai.values());
        cbxTrangThai.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        cbxTrangThai.setBackground(Color.WHITE);
        pnlInput.add(cbxTrangThai);

        JPanel pnlBtns = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        pnlBtns.setOpaque(false);
        JButton btnAdd = createRoundedButton("THÊM MỚI", COL_PRIMARY);
        JButton btnUpdate = createRoundedButton("CẬP NHẬT", COL_SIDEBAR);
        JButton btnReset = createRoundedButton("LÀM MỚI", Color.GRAY);
        pnlBtns.add(btnAdd); pnlBtns.add(btnUpdate); pnlBtns.add(btnReset);

        JPanel pnlForm = new JPanel(new BorderLayout(0, 15));
        pnlForm.setOpaque(false);
        pnlForm.add(pnlInput, BorderLayout.CENTER);
        pnlForm.add(pnlBtns, BorderLayout.SOUTH);
        pnlNorth.add(pnlForm, BorderLayout.CENTER);

        // ================= PHẦN DƯỚI: BẢNG DỮ LIỆU CÓ MÀU XEN KẼ =================
        String[] cols = {"ID", "Mã KM", "Tên Chương Trình", "% Giảm", "Tiền Giảm (đ)", "Đơn Tối Thiểu (đ)", "Hết Hạn", "Trạng Thái"};
        modelKM = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        
        // Custom Table để tạo hiệu ứng xen kẽ màu (Zebra Striping)
        tblKM = new JTable(modelKM) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    // Dòng chẵn màu trắng, dòng lẻ màu hồng cực nhạt -> Rất dễ nhìn
                    c.setBackground(row % 2 == 0 ? Color.WHITE : COL_STRIPE);
                } else {
                    c.setBackground(Color.decode("#F5E6EC")); // Màu hồng nhạt khi click chọn
                }
                return c;
            }
        };
        styleTable(tblKM);

        JScrollPane scrollPane = new JScrollPane(tblKM);
        scrollPane.setBorder(new LineBorder(Color.decode("#E0D7CE"), 1, true));
        scrollPane.getViewport().setBackground(Color.WHITE);

        add(pnlNorth, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        btnAdd.addActionListener(e -> handlingAdd());
        btnUpdate.addActionListener(e -> handlingUpdate());
        btnReset.addActionListener(e -> resetForm());
        tblKM.getSelectionModel().addListSelectionListener(e -> fillForm());
    }

    private void loadData() {
        modelKM.setRowCount(0);
        List<KhuyenMaiDTO> list = kmBUS.getAll();
        for (KhuyenMaiDTO km : list) {
            modelKM.addRow(new Object[]{
                    km.getMaKM(), km.getMaCode(), km.getTenKM(),
                    km.getPhanTramGiam() + "%",
                    String.format("%,.0f", km.getSoTienGiam()),
                    String.format("%,.0f", km.getDonHangToiThieu()),
                    km.getNgayKetThuc(), 
                    km.getTrangThai().name() 
            });
        }
    }

    private void fillForm() {
        int row = tblKM.getSelectedRow();
        if (row != -1) {
            selectedMaKM = (int) tblKM.getValueAt(row, 0);
            txtMaCode.setText(tblKM.getValueAt(row, 1) != null ? tblKM.getValueAt(row, 1).toString() : "");
            txtTenKM.setText(tblKM.getValueAt(row, 2) != null ? tblKM.getValueAt(row, 2).toString() : "");
            txtPhanTram.setText(tblKM.getValueAt(row, 3).toString().replace("%", ""));
            txtSoTienGiam.setText(tblKM.getValueAt(row, 4).toString().replace(",", ""));
            txtDonHangMin.setText(tblKM.getValueAt(row, 5).toString().replace(",", ""));
            
            KhuyenMaiDTO km = kmBUS.getAll().get(row);
            txtNgayBD.setText(km.getNgayBatDau() != null ? km.getNgayBatDau().toString() : "");
            txtNgayKT.setText(km.getNgayKetThuc() != null ? km.getNgayKetThuc().toString() : "");
            
            String status = tblKM.getValueAt(row, 7).toString();
            cbxTrangThai.setSelectedItem(TrangThaiKhuyenMai.valueOf(status));
        }
    }

    private void handlingAdd() {
        KhuyenMaiDTO km = getDTOFromForm();
        if (km == null) return;
        JOptionPane.showMessageDialog(this, kmBUS.addKhuyenMai(km));
        loadData();
    }

    private void handlingUpdate() {
        if (selectedMaKM == -1) { JOptionPane.showMessageDialog(this, "Vui lòng chọn một chương trình để sửa!"); return; }
        KhuyenMaiDTO km = getDTOFromForm();
        if (km == null) return;
        km.setMaKM(selectedMaKM);
        JOptionPane.showMessageDialog(this, kmBUS.updateKhuyenMai(km));
        loadData();
    }

    private KhuyenMaiDTO getDTOFromForm() {
        try {
            KhuyenMaiDTO km = new KhuyenMaiDTO();
            km.setTenKM(txtTenKM.getText());
            km.setPhanTramGiam(new BigDecimal(txtPhanTram.getText()));
            km.setSoTienGiam(new BigDecimal(txtSoTienGiam.getText()));
            km.setDonHangToiThieu(new BigDecimal(txtDonHangMin.getText()));
            km.setNgayBatDau(LocalDate.parse(txtNgayBD.getText()));
            km.setNgayKetThuc(LocalDate.parse(txtNgayKT.getText()));
            km.setTrangThai((TrangThaiKhuyenMai) cbxTrangThai.getSelectedItem());
            return km;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Dữ liệu không hợp lệ! Vui lòng kiểm tra lại định dạng Số và Ngày (YYYY-MM-DD)");
            return null;
        }
    }

    private void resetForm() {
        txtMaCode.setText("(Hệ thống tự động sinh)"); 
        txtTenKM.setText(""); txtPhanTram.setText("0");
        txtSoTienGiam.setText("0"); txtDonHangMin.setText("0");
        txtNgayBD.setText(LocalDate.now().toString());
        txtNgayKT.setText(LocalDate.now().plusMonths(1).toString());
        cbxTrangThai.setSelectedIndex(0);
        selectedMaKM = -1;
        tblKM.clearSelection();
    }

    private JLabel createLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lbl.setForeground(COL_SIDEBAR);
        return lbl;
    }
    private JTextField createTextField() {
        return createTextField("");
    }

    private JTextField createTextField(String text) {
        JTextField txt = new JTextField(text);
        txt.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        txt.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Color.LIGHT_GRAY), new EmptyBorder(5, 10, 5, 10)));
        return txt;
    }

    // ================= NÚT BẤM BO GÓC =================
    private JButton createRoundedButton(String text, Color bg) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isRollover()) g2.setColor(bg.brighter());
                else g2.setColor(bg);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                super.paintComponent(g);
            }
        };
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setForeground(Color.WHITE);
        btn.setPreferredSize(new Dimension(140, 45));
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    // ================= TRỊ BỆNH TÀNG HÌNH CHO TABLE HEADER =================
    private void styleTable(JTable table) {
        table.setRowHeight(40);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setSelectionForeground(Color.BLACK);
        table.setShowVerticalLines(false);
        table.setShowHorizontalLines(true);
        table.setGridColor(Color.decode("#EEEEEE"));

        JTableHeader header = table.getTableHeader();
        header.setPreferredSize(new Dimension(header.getWidth(), 45));
        
        // Ép màu nền và chữ cho Header bằng Renderer tùy chỉnh (Khắc phục lỗi trắng bóc)
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                label.setBackground(COL_SIDEBAR);
                label.setForeground(Color.WHITE);
                label.setFont(new Font("Segoe UI", Font.BOLD, 14));
                label.setHorizontalAlignment(JLabel.CENTER);
                label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.LIGHT_GRAY));
                return label;
            }
        });
        
        // Căn giữa nội dung các cột
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for(int i = 0; i < table.getColumnCount(); i++){
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }
}