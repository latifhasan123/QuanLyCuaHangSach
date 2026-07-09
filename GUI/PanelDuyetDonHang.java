package GUI;

import BUS.DonHangBUS;
import DTO.DonHangDTO;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

public class PanelDuyetDonHang extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private DonHangBUS bus = new DonHangBUS();
    
    private JTextField txtSearchBasic;
    private JTextField txtMaDH, txtTenKH, txtSDT;
    private JComboBox<String> cmbFilter;
    private JDateChooser txtTuNgay, txtDenNgay;
    
    private Color primaryPink = Color.decode("#E889A9");
    private Color darkPurple = Color.decode("#5A4664");
    private Color bgContent = Color.decode("#F5F6FA");
    private DecimalFormat df = new DecimalFormat("#,### đ");
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    public PanelDuyetDonHang() {
        setLayout(new BorderLayout(20, 20));
        setBackground(bgContent);
        setBorder(new EmptyBorder(25, 30, 25, 30));

        JPanel pnlTop = new JPanel(new BorderLayout(0, 20));
        pnlTop.setOpaque(false);
        
        JLabel lblTitle = new JLabel("QUẢN LÝ ĐƠN HÀNG ONLINE");
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
        
        txtSearchBasic = new JTextField(30);
        pnlRow1.add(createFilterField("TÌM KIẾM NHANH", txtSearchBasic, Color.WHITE, Color.decode("#DCDDE1")));

        txtTuNgay = new JDateChooser();
        txtTuNgay.setDateFormatString("dd/MM/yyyy");
        pnlRow1.add(createFilterField("Từ Ngày", txtTuNgay, Color.WHITE, Color.decode("#DCDDE1")));

        txtDenNgay = new JDateChooser();
        txtDenNgay.setDateFormatString("dd/MM/yyyy");
        pnlRow1.add(createFilterField("Đến Ngày", txtDenNgay, Color.WHITE, Color.decode("#DCDDE1")));

        JPanel pnlRow2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        pnlRow2.setOpaque(false);
        txtMaDH = new JTextField(12);
        txtTenKH = new JTextField(15);
        txtSDT = new JTextField(12);
        cmbFilter = new JComboBox<>(new String[]{"Tất cả", "ChoXuLy", "DaXacNhan", "HoanThanh", "DaHuy"});
        
        pnlRow2.add(createFilterField("Mã Đơn Hàng", txtMaDH, Color.WHITE, Color.decode("#DCDDE1")));
        pnlRow2.add(createFilterField("Tên Khách Hàng", txtTenKH, Color.WHITE, Color.decode("#DCDDE1")));
        pnlRow2.add(createFilterField("Số Điện Thoại", txtSDT, Color.WHITE, Color.decode("#DCDDE1")));
        pnlRow2.add(createFilterField("Trạng Thái Đơn", cmbFilter, Color.decode("#F8F9FA"), Color.decode("#DCDDE1")));

        pnlFilterCard.add(pnlRow1);
        pnlFilterCard.add(Box.createVerticalStrut(5));
        pnlFilterCard.add(pnlRow2);
        
        KeyAdapter searchAdapter = new KeyAdapter() {
            @Override public void keyReleased(KeyEvent e) { loadDataToTable(); }
        };
        txtSearchBasic.addKeyListener(searchAdapter);
        txtMaDH.addKeyListener(searchAdapter);
        txtTenKH.addKeyListener(searchAdapter);
        txtSDT.addKeyListener(searchAdapter);
        cmbFilter.addActionListener(e -> loadDataToTable());
        txtTuNgay.addPropertyChangeListener("date", e -> loadDataToTable());
        txtDenNgay.addPropertyChangeListener("date", e -> loadDataToTable());

        pnlTop.add(pnlFilterCard, BorderLayout.CENTER);
        add(pnlTop, BorderLayout.NORTH);

        String[] cols = {"Mã ID", "Mã ĐH", "Ngày đặt", "Khách hàng", "Số điện thoại", "Tổng tiền", "Trạng thái", "Giao hàng"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(tableModel);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        table.setRowHeight(45);
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

        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setWidth(0);
        table.getColumnModel().getColumn(0).setPreferredWidth(0);

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
                
                if (column == 6) {
                    String val = value.toString();
                    if (val.equals("HoanThanh")) c.setForeground(new Color(40, 167, 69));
                    else if (val.equals("DaHuy")) c.setForeground(new Color(220, 53, 69));
                    else if (val.equals("ChoXuLy")) c.setForeground(new Color(255, 153, 0));
                    else c.setForeground(primaryPink);
                    setFont(new Font("Segoe UI", Font.BOLD, 14));
                } else if (column == 5) {
                    c.setForeground(darkPurple);
                    setFont(new Font("Segoe UI", Font.BOLD, 15));
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
        table.getColumnModel().getColumn(3).setPreferredWidth(200);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = table.getSelectedRow();
                    if (row != -1) {
                        int maID = (int) table.getValueAt(row, 0);
                        String maDHDisplay = table.getValueAt(row, 1).toString();
                        Frame owner = (Frame) SwingUtilities.getWindowAncestor(PanelDuyetDonHang.this);
                        DialogChiTietDonHang dialog = new DialogChiTietDonHang(owner, maID, maDHDisplay);
                        dialog.setVisible(true);
                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.decode("#DCDDE1"), 1));
        add(scrollPane, BorderLayout.CENTER);

        JPanel pnlBottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        pnlBottom.setOpaque(false);
        pnlBottom.setBorder(new EmptyBorder(15, 0, 0, 0));

        JButton btnLamMoi = createButton("LÀM MỚI BẢNG", new Color(46, 204, 113));
        JButton btnDuyet = createButton("XÁC NHẬN ĐƠN", primaryPink);
        JButton btnHoanThanh = createButton("GIAO THÀNH CÔNG", new Color(41, 128, 185));
        JButton btnHuy = createButton("HỦY ĐƠN", new Color(220, 53, 69));

        btnLamMoi.addActionListener(e -> {
            txtSearchBasic.setText("");
            txtMaDH.setText("");
            txtTenKH.setText("");
            txtSDT.setText("");
            cmbFilter.setSelectedIndex(0);
            txtTuNgay.setDate(null);
            txtDenNgay.setDate(null);
            loadDataToTable();
        });

        btnDuyet.addActionListener(e -> changeStatus("ChoXuLy", 1));
        btnHoanThanh.addActionListener(e -> changeStatus("DaXacNhan", 2));
        btnHuy.addActionListener(e -> changeStatus("", 3));

        pnlBottom.add(btnLamMoi);
        pnlBottom.add(btnDuyet);
        pnlBottom.add(btnHoanThanh);
        pnlBottom.add(btnHuy);
        
        add(pnlBottom, BorderLayout.SOUTH);

        loadDataToTable();
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

    private void loadDataToTable() {
        tableModel.setRowCount(0);
        String status = cmbFilter.getSelectedItem().toString();
        String basicKw = txtSearchBasic.getText();
        String maDH = txtMaDH.getText();
        String tenKH = txtTenKH.getText();
        String sdt = txtSDT.getText();
        
        Date tuNgay = txtTuNgay.getDate();
        Date denNgay = txtDenNgay.getDate();
        LocalDate fromDate = (tuNgay != null) ? tuNgay.toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null;
        LocalDate toDate = (denNgay != null) ? denNgay.toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null;
        
        ArrayList<DonHangDTO> list = bus.searchDonHangOnlineUnified(basicKw, maDH, tenKH, sdt, status);
        
        for (DonHangDTO dh : list) {
            LocalDate logDate = dh.getNgayTao() != null ? dh.getNgayTao().toLocalDateTime().toLocalDate() : null;
            if (fromDate != null && logDate != null && logDate.isBefore(fromDate)) continue;
            if (toDate != null && logDate != null && logDate.isAfter(toDate)) continue;

            tableModel.addRow(new Object[]{
                dh.getMaID(),
                dh.getMaDH(),
                dh.getNgayTao() != null ? sdf.format(dh.getNgayTao()) : "",
                dh.getTenNguoiNhan(),
                dh.getSdtNhan(),
                df.format(dh.getThanhTien()),
                dh.getTrangThaiDon(),
                dh.getTrangThaiGiaoHang()
            });
        }
    }

    private void changeStatus(String requiredStatus, int actionType) {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một đơn hàng!");
            return;
        }
        
        String currentStatus = table.getValueAt(row, 6).toString();
        int maID = (int) table.getValueAt(row, 0);

        if (actionType == 1) { 
            if (!currentStatus.equals("ChoXuLy")) {
                JOptionPane.showMessageDialog(this, "Chỉ được xác nhận đơn đang ở trạng thái 'ChoXuLy'!");
                return;
            }
            if (bus.xacNhanDon(maID)) JOptionPane.showMessageDialog(this, "Đã xác nhận đơn hàng!");
        } 
        else if (actionType == 2) { 
            if (!currentStatus.equals("DaXacNhan")) {
                JOptionPane.showMessageDialog(this, "Chỉ được hoàn thành đơn đang ở trạng thái 'DaXacNhan'!");
                return;
            }
            if (bus.hoanThanhDon(maID)) JOptionPane.showMessageDialog(this, "Đã cập nhật giao hàng thành công!");
        } 
        else if (actionType == 3) { 
            if (currentStatus.equals("HoanThanh") || currentStatus.equals("DaHuy")) {
                JOptionPane.showMessageDialog(this, "Đơn hàng này không thể hủy được nữa!");
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn hủy đơn này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (bus.huyDonAdmin(maID)) JOptionPane.showMessageDialog(this, "Đã hủy đơn hàng!");
            } else {
                return;
            }
        }
        loadDataToTable();
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
}