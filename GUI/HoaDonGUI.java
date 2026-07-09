package GUI;

import dto.HoaDonDTO;
import DTO.TaiKhoanNhanVienDTO;
import bus.HoaDonBUS;
import enums.TrangThaiGiaoDich;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import com.toedter.calendar.JDateChooser;

public class HoaDonGUI extends JPanel {

    private TaiKhoanNhanVienDTO currentUser; 
    private HoaDonBUS hdBUS = new HoaDonBUS();

    private JTable tblHoaDon;
    private DefaultTableModel modelHoaDon;

    private JTextField txtTimKiem, txtMaHD, txtMaNV, txtMaKH;
    private JComboBox<String> cbxTrangThai;
    private JDateChooser txtTuNgay, txtDenNgay;
    private JButton btnLamMoi, btnHuyDon;

    private DecimalFormat df = new DecimalFormat("#,### đ");
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private Color primaryPink = Color.decode("#E889A9");
    private Color darkPurple = Color.decode("#5A4664");
    private Color bgContent = Color.decode("#F5F6FA");

    public HoaDonGUI(TaiKhoanNhanVienDTO user) { 
        this.currentUser = user;
        initUI();
        loadDataToTable();
        initEvents();
    }

    private void initUI() {
        setLayout(new BorderLayout(20, 20));
        setBackground(bgContent);
        setBorder(new EmptyBorder(25, 30, 25, 30));

        JPanel pnlTop = new JPanel(new BorderLayout(0, 20));
        pnlTop.setOpaque(false);

        JLabel lblTitle = new JLabel("QUẢN LÝ HÓA ĐƠN");
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

        JPanel pnlRow1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 5));
        pnlRow1.setOpaque(false);
        
        txtTimKiem = new JTextField(40);
        pnlRow1.add(createFilterField("TÌM KIẾM NHANH", txtTimKiem, Color.WHITE, Color.decode("#DCDDE1")));

        txtTuNgay = new JDateChooser();
        txtTuNgay.setDateFormatString("dd/MM/yyyy");
        pnlRow1.add(createFilterField("Từ Ngày", txtTuNgay, Color.WHITE, Color.decode("#DCDDE1")));

        txtDenNgay = new JDateChooser();
        txtDenNgay.setDateFormatString("dd/MM/yyyy");
        pnlRow1.add(createFilterField("Đến Ngày", txtDenNgay, Color.WHITE, Color.decode("#DCDDE1")));

        JPanel pnlRow2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        pnlRow2.setOpaque(false);
        
        txtMaHD = new JTextField(12);
        txtMaNV = new JTextField(12);
        txtMaKH = new JTextField(12);
        
        // ĐÃ SỬA: Chỉ để lại 2 trạng thái Hợp lệ của Hóa đơn
        cbxTrangThai = new JComboBox<>(new String[]{"Tất cả", "Hoàn Thành", "Đã Hủy"});

        pnlRow2.add(createFilterField("Mã Hóa Đơn", txtMaHD, Color.WHITE, Color.decode("#DCDDE1")));
        pnlRow2.add(createFilterField("Mã Nhân Viên", txtMaNV, Color.WHITE, Color.decode("#DCDDE1")));
        pnlRow2.add(createFilterField("Mã Khách Hàng", txtMaKH, Color.WHITE, Color.decode("#DCDDE1")));
        pnlRow2.add(createFilterField("Trạng Thái Hóa Đơn", cbxTrangThai, Color.decode("#F8F9FA"), Color.decode("#DCDDE1")));

        pnlFilterCard.add(pnlRow1);
        pnlFilterCard.add(Box.createVerticalStrut(5));
        pnlFilterCard.add(pnlRow2);
        
        pnlTop.add(pnlFilterCard, BorderLayout.CENTER);
        add(pnlTop, BorderLayout.NORTH);

        String[] cols = {"Mã HD", "Nhân Viên", "Khách Hàng", "Ngày Tạo", "Tổng Tiền", "Tiền Giảm", "Thành Tiền", "Trạng Thái"};
        modelHoaDon = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        tblHoaDon = new JTable(modelHoaDon);
        tblHoaDon.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        tblHoaDon.setRowHeight(45);
        tblHoaDon.setShowGrid(false);
        tblHoaDon.setIntercellSpacing(new Dimension(0, 0));
        tblHoaDon.setSelectionBackground(Color.decode("#FFF0F5"));
        tblHoaDon.setSelectionForeground(darkPurple);

        JTableHeader header = tblHoaDon.getTableHeader();
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
                    if (row % 2 == 0) {
                        c.setBackground(Color.WHITE);
                    } else {
                        c.setBackground(Color.decode("#F9F9FB"));
                    }
                    c.setForeground(Color.DARK_GRAY);
                }
                
                if (column == 7) {
                    String val = value != null ? value.toString() : "";
                    if (val.equals("Hoàn Thành")) c.setForeground(new Color(40, 167, 69));
                    else if (val.equals("Đã Hủy")) c.setForeground(new Color(220, 53, 69));
                    else c.setForeground(primaryPink);
                    setFont(new Font("Segoe UI", Font.BOLD, 14));
                } else if (column == 0) {
                    c.setForeground(darkPurple);
                    setFont(new Font("Segoe UI", Font.BOLD, 15));
                } else {
                    setFont(new Font("Segoe UI", Font.PLAIN, 15));
                }
                
                ((JLabel) c).setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.decode("#EEEEEE")));
                return c;
            }
        };

        for (int i = 0; i < tblHoaDon.getColumnCount(); i++) {
            tblHoaDon.getColumnModel().getColumn(i).setCellRenderer(customRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(tblHoaDon);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.decode("#DCDDE1"), 1));
        add(scrollPane, BorderLayout.CENTER);

        JPanel pnlBottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        pnlBottom.setOpaque(false);
        pnlBottom.setBorder(new EmptyBorder(15, 0, 0, 0));

        btnLamMoi = createButton("LÀM MỚI BẢNG", new Color(46, 204, 113));
        btnHuyDon = createButton("HỦY HÓA ĐƠN", new Color(231, 76, 60));

        pnlBottom.add(btnLamMoi);
        pnlBottom.add(btnHuyDon);
        
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
            cmb.setFont(new Font("Segoe UI", Font.PLAIN, 15));
            cmb.setBorder(null);
            cmb.setBackground(bgColor);
            wrapper.add(cmb, BorderLayout.CENTER);
        } else if (comp instanceof JDateChooser) {
            comp.setFont(new Font("Segoe UI", Font.PLAIN, 15));
            comp.setPreferredSize(new Dimension(140, 28));
            wrapper.add(comp, BorderLayout.CENTER);
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
        btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btn.setForeground(Color.WHITE);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(190, 45)); 
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    public void loadDataToTable() {
        modelHoaDon.setRowCount(0);
        
        String keyword = txtTimKiem.getText().trim().toLowerCase();
        String maHDStrSearch = txtMaHD.getText().trim().toLowerCase();
        String maNVStrSearch = txtMaNV.getText().trim().toLowerCase();
        String maKHStrSearch = txtMaKH.getText().trim().toLowerCase();
        String trangThaiLoc = cbxTrangThai.getSelectedItem().toString();

        Date tuNgay = txtTuNgay.getDate();
        Date denNgay = txtDenNgay.getDate();

        LocalDate fromDate = (tuNgay != null) ? tuNgay.toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null;
        LocalDate toDate = (denNgay != null) ? denNgay.toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null;

        List<HoaDonDTO> list = hdBUS.getAll();
        if (list != null) {
            for (HoaDonDTO hd : list) {
                
                String strTrangThai = "";
                if (hd.getTrangThai() != null) {
                    if (hd.getTrangThai() == TrangThaiGiaoDich.HoanThanh) strTrangThai = "Hoàn Thành";
                    else if (hd.getTrangThai() == TrangThaiGiaoDich.DaHuy) strTrangThai = "Đã Hủy";
                    else continue; // CHỐT CHẶN: Vô tình móc lên đơn "Chờ xử lý" thì bỏ qua ngay không vẽ lên bảng
                } else {
                    continue;
                }

                String maHDDisplay = "HD" + String.format("%07d", hd.getMaHD());
                String maNVDisplay = (hd.getStrMaNV() != null) ? hd.getStrMaNV() : "Hệ thống";
                String maKHDisplay = (hd.getStrMaKH() != null) ? hd.getStrMaKH() : "Khách Lẻ";
                String dateStr = hd.getNgayTao() != null ? hd.getNgayTao().format(dtf) : "";

                boolean matchBasic = keyword.isEmpty() || 
                                     maHDDisplay.toLowerCase().contains(keyword) || 
                                     maNVDisplay.toLowerCase().contains(keyword) || 
                                     maKHDisplay.toLowerCase().contains(keyword) ||
                                     dateStr.contains(keyword);

                boolean matchAdv = true;
                if (!maHDStrSearch.isEmpty() && !maHDDisplay.toLowerCase().contains(maHDStrSearch)) matchAdv = false;
                if (!maNVStrSearch.isEmpty() && !maNVDisplay.toLowerCase().contains(maNVStrSearch)) matchAdv = false;
                if (!maKHStrSearch.isEmpty() && !maKHDisplay.toLowerCase().contains(maKHStrSearch)) matchAdv = false;

                if (!trangThaiLoc.equals("Tất cả") && !strTrangThai.equals(trangThaiLoc)) matchAdv = false;
                
                LocalDate logDate = hd.getNgayTao() != null ? hd.getNgayTao().toLocalDate() : null;
                if (fromDate != null && logDate != null && logDate.isBefore(fromDate)) matchAdv = false;
                if (toDate != null && logDate != null && logDate.isAfter(toDate)) matchAdv = false;

                if (matchBasic && matchAdv) {
                    addDtoToTable(hd, strTrangThai);
                }
            }
        }
    }

    private void addDtoToTable(HoaDonDTO hd, String strTrangThai) {
        String maNVDisplay = (hd.getStrMaNV() != null) ? hd.getStrMaNV() : "Hệ thống";
        String maKHDisplay = (hd.getStrMaKH() != null) ? hd.getStrMaKH() : "Khách Lẻ";
        String maHDDisplay = "HD" + String.format("%07d", hd.getMaHD());

        modelHoaDon.addRow(new Object[]{
                maHDDisplay, 
                maNVDisplay, 
                maKHDisplay,
                hd.getNgayTao() != null ? hd.getNgayTao().format(dtf) : "",
                df.format(hd.getTongTien() != null ? hd.getTongTien() : 0),
                df.format(hd.getTienGiam() != null ? hd.getTienGiam() : 0),
                df.format(hd.getThanhTien() != null ? hd.getThanhTien() : 0),
                strTrangThai
        });
    }

    private void initEvents() {
        KeyAdapter ka = new KeyAdapter() {
            @Override public void keyReleased(KeyEvent e) { loadDataToTable(); }
        };
        txtTimKiem.addKeyListener(ka);
        txtMaHD.addKeyListener(ka);
        txtMaNV.addKeyListener(ka);
        txtMaKH.addKeyListener(ka);
        cbxTrangThai.addActionListener(e -> loadDataToTable());
        txtTuNgay.addPropertyChangeListener("date", e -> loadDataToTable());
        txtDenNgay.addPropertyChangeListener("date", e -> loadDataToTable());

        btnLamMoi.addActionListener(e -> {
            txtTimKiem.setText("");
            txtMaHD.setText("");
            txtMaNV.setText("");
            txtMaKH.setText("");
            cbxTrangThai.setSelectedIndex(0);
            txtTuNgay.setDate(null);
            txtDenNgay.setDate(null);
            loadDataToTable();
        });

        tblHoaDon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { 
                    int row = tblHoaDon.getSelectedRow();
                    if (row >= 0) {
                        String maHDStr = tblHoaDon.getValueAt(row, 0).toString();
                        int maHD = Integer.parseInt(maHDStr.replaceAll("[^0-9]", "")); 
                        
                        Frame owner = (Frame) SwingUtilities.getWindowAncestor(HoaDonGUI.this);
                        ChiTietHoaDonDialog dialog = new ChiTietHoaDonDialog(owner, String.valueOf(maHD)); 
                        dialog.setVisible(true);
                    }
                }
            }
        });

        btnHuyDon.addActionListener(e -> {
            int row = tblHoaDon.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn cần hủy!");
                return;
            }
            String maHDStr = tblHoaDon.getValueAt(row, 0).toString();
            String trangThaiHienTai = tblHoaDon.getValueAt(row, 7).toString();

            if (trangThaiHienTai.equals("Đã Hủy")) {
                JOptionPane.showMessageDialog(this, "Hóa đơn này đã được hủy trước đó!");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this,
                    "Bạn có chắc chắn muốn hủy hóa đơn " + maHDStr + " này?\nSách sẽ được hoàn lại vào kho.",
                    "Cảnh báo", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

            if (confirm == JOptionPane.YES_OPTION) {
                int maHD = Integer.parseInt(maHDStr.replaceAll("[^0-9]", ""));
                String result = hdBUS.deleteHoaDon(maHD);
                JOptionPane.showMessageDialog(this, result);
                loadDataToTable();
            }
        });
    }
}