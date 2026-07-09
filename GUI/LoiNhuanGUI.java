package GUI;

import BUS.LoiNhuanBUS;
import DTO.LoiNhuanDTO;
import com.toedter.calendar.JDateChooser;

import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class LoiNhuanGUI extends JPanel {

    // BỘ MÀU LUXURY
    private final Color COLOR_CREAM = new Color(248, 244, 236);
    private final Color COLOR_PRIMARY = new Color(232, 60, 145);
    private final Color COLOR_DARK = new Color(67, 51, 76);
    private final Color COLOR_GREEN = new Color(40, 167, 69);
    private final Color COLOR_RED = new Color(220, 53, 69);
    // ĐÃ BỔ SUNG MÀU HỒNG NHẠT CHO BẢNG CHỌN
    private final Color COLOR_LIGHT_PINK = new Color(255, 143, 183); 

    private JDateChooser txtTuNgay, txtDenNgay;
    private JButton btnLoc, btnLamMoi;
    private JLabel lblTongDoanhThu, lblTongChiPhi, lblTongLoiNhuan;

    private JTable tblLoiNhuan;
    private DefaultTableModel tableModel;

    private LoiNhuanBUS lnBus = new LoiNhuanBUS();
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public LoiNhuanGUI() {
        setLayout(new BorderLayout(15, 15));
        setBackground(COLOR_CREAM);
        setBorder(new EmptyBorder(15, 15, 15, 15));

        initNorthPanel();
        initCenterPanel();

        // Load dữ liệu ban đầu
        loadData(null, null);
        addEvents();
    }

    private void initNorthPanel() {
        JPanel pnlNorth = new JPanel(new BorderLayout(10, 10));
        pnlNorth.setOpaque(false);

        JLabel lblTitle = new JLabel("BÁO CÁO LỢI NHUẬN", JLabel.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(COLOR_CREAM);
        lblTitle.setOpaque(true);
        lblTitle.setBackground(COLOR_PRIMARY);
        lblTitle.setBorder(new EmptyBorder(15, 0, 15, 0));
        pnlNorth.add(lblTitle, BorderLayout.NORTH);

        // --- KHU VỰC BỘ LỌC ---
        JPanel pnlFilter = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        pnlFilter.setBackground(Color.WHITE);
        pnlFilter.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

        Font fontDate = new Font("Segoe UI", Font.PLAIN, 15);
        txtTuNgay = new JDateChooser(); txtTuNgay.setDateFormatString("dd/MM/yyyy");
        txtTuNgay.setFont(fontDate); ((JTextField)txtTuNgay.getDateEditor().getUiComponent()).setFont(fontDate);
        txtTuNgay.setPreferredSize(new Dimension(160, 35));

        txtDenNgay = new JDateChooser(); txtDenNgay.setDateFormatString("dd/MM/yyyy");
        txtDenNgay.setFont(fontDate); ((JTextField)txtDenNgay.getDateEditor().getUiComponent()).setFont(fontDate);
        txtDenNgay.setPreferredSize(new Dimension(160, 35));

        btnLoc = createButton("Thống Kê", COLOR_PRIMARY);
        btnLamMoi = createButton("Làm Mới", COLOR_DARK);

        pnlFilter.add(createLabel("Từ ngày:")); pnlFilter.add(txtTuNgay);
        pnlFilter.add(createLabel("Đến ngày:")); pnlFilter.add(txtDenNgay);
        pnlFilter.add(btnLoc); pnlFilter.add(btnLamMoi);

        pnlNorth.add(pnlFilter, BorderLayout.CENTER);
        add(pnlNorth, BorderLayout.NORTH);
    }

    private void initCenterPanel() {
        JPanel pnlCenter = new JPanel(new BorderLayout(15, 15));
        pnlCenter.setOpaque(false);

        // --- KHU VỰC 3 THẺ CARD TỔNG KẾT ---
        JPanel pnlCards = new JPanel(new GridLayout(1, 3, 20, 0));
        pnlCards.setOpaque(false);
        pnlCards.setPreferredSize(new Dimension(0, 120));

        lblTongDoanhThu = new JLabel("0 đ");
        lblTongChiPhi = new JLabel("0 đ");
        lblTongLoiNhuan = new JLabel("0 đ");

        pnlCards.add(createSummaryCard("Tổng Doanh Thu", lblTongDoanhThu, new Color(41, 128, 185)));
        pnlCards.add(createSummaryCard("Tổng Tiền Vốn", lblTongChiPhi, new Color(230, 126, 34)));
        pnlCards.add(createSummaryCard("LỢI NHUẬN GỘP", lblTongLoiNhuan, COLOR_GREEN));

        // --- KHU VỰC BẢNG DỮ LIỆU ---
        String[] columnNames = {"Mã Đơn Hàng", "Ngày Tạo", "Doanh Thu (Tiền Bán)", "Chi Phí (Tiền Vốn)", "Lợi Nhuận"};
        tableModel = new DefaultTableModel(columnNames, 0) { @Override public boolean isCellEditable(int r, int c) { return false; } };
        tblLoiNhuan = new JTable(tableModel);
        tblLoiNhuan.setRowHeight(40);
        tblLoiNhuan.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        tblLoiNhuan.setSelectionBackground(COLOR_LIGHT_PINK);
        tblLoiNhuan.setSelectionForeground(COLOR_DARK);
        tblLoiNhuan.setShowGrid(false);

        JTableHeader header = tblLoiNhuan.getTableHeader();
        header.setPreferredSize(new Dimension(0, 45));
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                label.setBackground(COLOR_DARK); label.setForeground(Color.WHITE);
                label.setFont(new Font("Segoe UI", Font.BOLD, 15)); label.setHorizontalAlignment(SwingConstants.CENTER);
                return label;
            }
        });

        DefaultTableCellRenderer customRenderer = new DefaultTableCellRenderer() {
            @Override public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(245, 245, 250));
                }
                setHorizontalAlignment(SwingConstants.CENTER);
                
                // Đổi màu Lợi Nhuận (Âm thì màu đỏ, Dương màu xanh)
                if (column == 4) {
                    c.setFont(new Font("Segoe UI", Font.BOLD, 15));
                    if (value.toString().contains("-")) c.setForeground(COLOR_RED);
                    else c.setForeground(COLOR_GREEN);
                } else if (column >= 2) {
                    c.setFont(new Font("Segoe UI", Font.BOLD, 15));
                    c.setForeground(Color.DARK_GRAY);
                } else {
                    c.setFont(new Font("Segoe UI", Font.PLAIN, 15));
                    c.setForeground(Color.BLACK);
                }
                return c;
            }
        };

        for (int i = 0; i < tblLoiNhuan.getColumnCount(); i++) {
            tblLoiNhuan.getColumnModel().getColumn(i).setCellRenderer(customRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(tblLoiNhuan);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

        pnlCenter.add(pnlCards, BorderLayout.NORTH);
        pnlCenter.add(scrollPane, BorderLayout.CENTER);
        add(pnlCenter, BorderLayout.CENTER);
    }

    // --- HÀM TẠO THẺ CARD SANG TRỌNG ---
    private JPanel createSummaryCard(String title, JLabel lblValue, Color accentColor) {
        JPanel pnlCard = new JPanel(new BorderLayout(10, 10)) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE); 
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                
                // Viền màu trang trí bên trái
                g2.setColor(accentColor);
                g2.fillRoundRect(0, 0, 8, getHeight(), 15, 15);
                g2.dispose();
            }
        };
        pnlCard.setOpaque(false);
        pnlCard.setBorder(new EmptyBorder(15, 25, 15, 15));

        JLabel lblT = new JLabel(title);
        lblT.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblT.setForeground(Color.GRAY);

        lblValue.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblValue.setForeground(accentColor);

        pnlCard.add(lblT, BorderLayout.NORTH);
        pnlCard.add(lblValue, BorderLayout.CENTER);

        return pnlCard;
    }

    private JLabel createLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lbl.setForeground(COLOR_DARK);
        return lbl;
    }

    private JButton createButton(String text, Color bg) {
        JButton btn = new JButton(text) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isRollover() ? bg.darker() : bg);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.dispose(); super.paintComponent(g);
            }
        };
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14)); btn.setForeground(Color.WHITE);
        btn.setContentAreaFilled(false); btn.setBorderPainted(false); btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(130, 35)); btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void loadData(Date tuNgay, Date denNgay) {
        ArrayList<LoiNhuanDTO> list = lnBus.getLoiNhuan(tuNgay, denNgay);
        tableModel.setRowCount(0);

        double sumDoanhThu = 0;
        double sumChiPhi = 0;
        double sumLoiNhuan = 0;

        for (LoiNhuanDTO ln : list) {
            String time = ln.getNgayTao() != null ? ln.getNgayTao().format(dtf) : "";
            
            tableModel.addRow(new Object[]{
                ln.getMaDonHang(), 
                time,
                String.format("%,.0f", ln.getDoanhThu()),
                String.format("%,.0f", ln.getChiPhi()),
                String.format("%,.0f", ln.getLoiNhuan())
            });

            sumDoanhThu += ln.getDoanhThu();
            sumChiPhi += ln.getChiPhi();
            sumLoiNhuan += ln.getLoiNhuan();
        }

        // Cập nhật lại 3 thẻ Card
        lblTongDoanhThu.setText(String.format("%,.0f đ", sumDoanhThu));
        lblTongChiPhi.setText(String.format("%,.0f đ", sumChiPhi));
        lblTongLoiNhuan.setText(String.format("%,.0f đ", sumLoiNhuan));
        
        // Nếu lỗ thì thẻ lợi nhuận tự động chuyển sang màu đỏ cảnh báo
        if(sumLoiNhuan < 0) lblTongLoiNhuan.setForeground(COLOR_RED);
        else lblTongLoiNhuan.setForeground(COLOR_GREEN);
    }

    private void addEvents() {
        btnLoc.addActionListener(e -> {
            Date from = txtTuNgay.getDate();
            Date to = txtDenNgay.getDate();
            if (from != null && to != null && from.after(to)) {
                JOptionPane.showMessageDialog(this, "Từ ngày không được lớn hơn Đến ngày!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            loadData(from, to);
        });

        btnLamMoi.addActionListener(e -> {
            txtTuNgay.setDate(null);
            txtDenNgay.setDate(null);
            loadData(null, null);
        });
    }
}