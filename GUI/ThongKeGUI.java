package GUI;

import BUS.ThongKeBUS;
import DTO.ThongKeDTO;
import DTO.TopSachDTO;
import java.awt.*;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class ThongKeGUI extends JPanel {

    private final Color COLOR_CREAM = new Color(248, 244, 236);
    private final Color COLOR_PRIMARY = new Color(232, 60, 145);
    private final Color COLOR_DARK = new Color(67, 51, 76);
    
    private JTextField txtTuNgay, txtDenNgay;
    private JLabel lblTongDoanhThu, lblTongDonHang;
    
    private JTable tblThongKe, tblTopSach;
    private DefaultTableModel tableModel, topSachModel;
    private ChartPanel pnlChart;
    
    private ThongKeBUS tkBus = new ThongKeBUS();
    private ArrayList<ThongKeDTO> currentData = new ArrayList<>();
    private DecimalFormat formatter = new DecimalFormat("###,###,### VNĐ");

    public ThongKeGUI() {
        setLayout(new BorderLayout(15, 15));
        setBackground(COLOR_CREAM);
        setBorder(new EmptyBorder(15, 15, 15, 15));

        initTopPanel();
        initCenterPanel();
        initBottomPanel();
        
        String today = LocalDate.now().toString();
        String firstDay = LocalDate.now().withDayOfMonth(1).toString();
        txtTuNgay.setText(firstDay);
        txtDenNgay.setText(today);
        loadThongKe(firstDay, today);
    }

    private void initTopPanel() {
        JPanel pnlTop = new JPanel(new BorderLayout(20, 0));
        pnlTop.setBackground(COLOR_CREAM);

        JPanel pnlFilter = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
        pnlFilter.setBackground(COLOR_CREAM);
        pnlFilter.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(COLOR_DARK), " Lọc Doanh Thu ", 
                0, 0, new Font("Segoe UI", Font.BOLD, 14), COLOR_DARK));

        txtTuNgay = new JTextField(10);
        txtDenNgay = new JTextField(10);
        Font fontInput = new Font("Segoe UI", Font.PLAIN, 14);
        txtTuNgay.setFont(fontInput); 
        txtDenNgay.setFont(fontInput);

        JButton btnLoc = new JButton("Thống Kê");
        btnLoc.setBackground(COLOR_PRIMARY);
        btnLoc.setForeground(Color.WHITE);
        btnLoc.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLoc.setFocusPainted(false);
        btnLoc.setBorderPainted(false); 
        btnLoc.setOpaque(true);         
        btnLoc.setCursor(new Cursor(Cursor.HAND_CURSOR));

        pnlFilter.add(new JLabel("Từ ngày (YYYY-MM-DD):"));
        pnlFilter.add(txtTuNgay);
        pnlFilter.add(new JLabel("Đến ngày:"));
        pnlFilter.add(txtDenNgay);
        pnlFilter.add(btnLoc);

        JPanel pnlCards = new JPanel(new GridLayout(1, 2, 15, 0));
        pnlCards.setBackground(COLOR_CREAM);
        
        lblTongDoanhThu = new JLabel("0 VNĐ", SwingConstants.CENTER);
        lblTongDonHang = new JLabel("0 Đơn", SwingConstants.CENTER);
        
        pnlCards.add(createSummaryCard("TỔNG DOANH THU", lblTongDoanhThu, COLOR_PRIMARY));
        pnlCards.add(createSummaryCard("TỔNG ĐƠN HÀNG BÁN", lblTongDonHang, COLOR_DARK));

        pnlTop.add(pnlFilter, BorderLayout.WEST);
        pnlTop.add(pnlCards, BorderLayout.CENTER);
        add(pnlTop, BorderLayout.NORTH);

        btnLoc.addActionListener(e -> {
            loadThongKe(txtTuNgay.getText().trim(), txtDenNgay.getText().trim());
        });
    }

    private JPanel createSummaryCard(String title, JLabel lblValue, Color accentColor) {
        JPanel pnl = new JPanel(new BorderLayout());
        pnl.setBackground(Color.WHITE);
        pnl.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(accentColor, 2, true),
                new EmptyBorder(10, 10, 10, 10)));
        JLabel lblTitle = new JLabel(title, SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTitle.setForeground(Color.GRAY);
        lblValue.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblValue.setForeground(accentColor);
        pnl.add(lblTitle, BorderLayout.NORTH);
        pnl.add(lblValue, BorderLayout.CENTER);
        return pnl;
    }

    private void initCenterPanel() {
        pnlChart = new ChartPanel();
        pnlChart.setBackground(Color.WHITE);
        pnlChart.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(COLOR_DARK), " Biểu Đồ Doanh Thu Theo Ngày ", 
                0, 0, new Font("Segoe UI", Font.BOLD, 14), COLOR_DARK));
        pnlChart.setPreferredSize(new Dimension(0, 300));
        add(pnlChart, BorderLayout.CENTER);
    }

    private void initBottomPanel() {
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tabbedPane.setForeground(COLOR_DARK);
        
        tabbedPane.setPreferredSize(new Dimension(0, 220)); 

        String[] colDoanhThu = {"STT", "Thời Gian (Ngày)", "Doanh Thu (VNĐ)", "Số Đơn Hàng"};
        tableModel = new DefaultTableModel(colDoanhThu, 0) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
        };
        tblThongKe = new JTable(tableModel);
        setupTable(tblThongKe);
        tabbedPane.addTab("Chi Tiết Doanh Thu", new JScrollPane(tblThongKe));

        String[] colTopSach = {"TOP", "Tên Cuốn Sách", "Số Lượng Đã Bán", "Tổng Thu Về (VNĐ)"};
        topSachModel = new DefaultTableModel(colTopSach, 0) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
        };
        tblTopSach = new JTable(topSachModel);
        setupTable(tblTopSach);
        // ĐÃ FIX LỖI FONT: Thay Emoji bằng chữ bình thường
        tabbedPane.addTab("Top Sách Bán Chạy Nhất", new JScrollPane(tblTopSach));

        add(tabbedPane, BorderLayout.SOUTH);
    }

    private void setupTable(JTable table) {
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setSelectionBackground(new Color(255, 143, 183));
        table.setSelectionForeground(COLOR_DARK);
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setForeground(COLOR_DARK);
        header.setPreferredSize(new Dimension(0, 35));
    }

    private void loadThongKe(String tuNgay, String denNgay) {
        currentData = tkBus.getDoanhThuTheoNgay(tuNgay, denNgay);
        tableModel.setRowCount(0);
        double tongTien = 0; int tongDon = 0; int stt = 1;
        for (ThongKeDTO tk : currentData) {
            tableModel.addRow(new Object[]{ stt++, tk.getThoiGian(), formatter.format(tk.getDoanhThu()), tk.getSoDonHang() });
            tongTien += tk.getDoanhThu();
            tongDon += tk.getSoDonHang();
        }
        lblTongDoanhThu.setText(formatter.format(tongTien));
        lblTongDonHang.setText(tongDon + " Đơn");
        pnlChart.repaint();

        ArrayList<TopSachDTO> topSachList = tkBus.getTopSachBanChay(tuNgay, denNgay);
        topSachModel.setRowCount(0);
        int top = 1;
        for (TopSachDTO ts : topSachList) {
            topSachModel.addRow(new Object[]{
                "Top " + (top++), 
                ts.getTenSach(), 
                ts.getSoLuongBan() + " cuốn", 
                formatter.format(ts.getDoanhThu())
            });
        }
    }

    // ĐÃ FIX: Làm chuỗi hiển thị tiền gọn gàng hơn (2.0 tr -> 2 tr)
    private String formatMoneyShort(double amount) {
        if (amount >= 1_000_000_000) return String.format("%.1f tỷ", amount / 1_000_000_000).replace(".0", "");
        else if (amount >= 1_000_000) return String.format("%.1f tr", amount / 1_000_000).replace(".0", "");
        else if (amount >= 1_000) return String.format("%.0f k", amount / 1_000);
        else return String.format("%.0f đ", amount);
    }

    class ChartPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (currentData == null || currentData.isEmpty()) return;

            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            int w = getWidth(); int h = getHeight();
            // ĐÃ FIX: Thu hẹp lề trái lại vì mốc trục Y giờ đã viết gọn (VD: 800k thay vì 800,000 VNĐ)
            int paddingB = 40, paddingT = 40, paddingL = 60, paddingR = 30; 
            
            // ========================================================
            // THUẬT TOÁN MỚI: TÍNH TOÁN TRỤC Y THÔNG MINH (TRÒN SỐ)
            // ========================================================
            double maxRealValue = 0;
            for (ThongKeDTO tk : currentData) {
                if (tk.getDoanhThu() > maxRealValue) maxRealValue = tk.getDoanhThu();
            }
            
            double maxVal = 400000; // Mặc định nếu biểu đồ trống
            if (maxRealValue > 0) {
                // Phân tích độ lớn (Magnitude: hàng chục ngàn, hàng trăm ngàn hay hàng triệu...)
                double magnitude = Math.pow(10, Math.floor(Math.log10(maxRealValue)));
                double fraction = maxRealValue / magnitude;
                
                // Chọn các mốc làm tròn sao cho chia hết cho 4 mượt mà nhất
                double ceilFraction;
                if (fraction <= 1.2) ceilFraction = 1.2;
                else if (fraction <= 2) ceilFraction = 2;
                else if (fraction <= 4) ceilFraction = 4;
                else if (fraction <= 5) ceilFraction = 5;
                else if (fraction <= 8) ceilFraction = 8;
                else ceilFraction = 10;
                
                maxVal = ceilFraction * magnitude; 
            }
            // ========================================================
            
            // VẼ TRỤC Y VÀ CÁC ĐƯỜNG KẺ NGANG
            for (int i = 0; i <= 4; i++) {
                int yLine = h - paddingB - (int)((h - paddingB - paddingT) * i / 4.0);
                
                g2.setColor(new Color(230, 230, 230));
                g2.drawLine(paddingL - 10, yLine, w - paddingR, yLine);
                
                // Trục Y hiển thị số dạng ngắn gọn: 200k, 400k, 1 tr...
                String yLabel = formatMoneyShort((maxVal / 4) * i);
                g2.setColor(Color.GRAY);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 12));
                g2.drawString(yLabel, 10, yLine + 5);
            }

            int soCot = currentData.size();
            int availableWidth = w - paddingL - paddingR;
            int colWidth = Math.min(60, availableWidth / (soCot * 2)); 
            int gap = (availableWidth - (soCot * colWidth)) / (soCot + 1);

            // VẼ CÁC CỘT BIỂU ĐỒ
            for (int i = 0; i < soCot; i++) {
                ThongKeDTO tk = currentData.get(i);
                int x = paddingL + gap + (i * (colWidth + gap));
                int barH = (int)((tk.getDoanhThu() / maxVal) * (h - paddingB - paddingT));
                int y = h - paddingB - barH;

                // Đổ bóng cột
                g2.setColor(new Color(0, 0, 0, 20));
                g2.fillRoundRect(x + 3, y + 3, colWidth, barH, 5, 5);
                
                // Vẽ cột Gradient
                GradientPaint gp = new GradientPaint(x, y, COLOR_PRIMARY, x, y + barH, COLOR_DARK);
                g2.setPaint(gp);
                g2.fillRoundRect(x, y, colWidth, barH, 5, 5);
                
                // ĐÃ FIX: Tiền trên đỉnh cột sẽ hiển thị sắc nét hơn, không bị dính vào nhau
                if (tk.getDoanhThu() > 0) {
                    g2.setColor(COLOR_PRIMARY);
                    g2.setFont(new Font("Segoe UI", Font.BOLD, 11));
                    String valStr = formatMoneyShort(tk.getDoanhThu());
                    int valX = x + (colWidth - g2.getFontMetrics().stringWidth(valStr)) / 2;
                    g2.drawString(valStr, valX, y - 8);
                }
                
                // ĐÃ FIX: Format Ngày/Tháng (Từ "2026-03-31" thành "31/03")
                g2.setColor(COLOR_DARK);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 12));
                String[] dateParts = tk.getThoiGian().split("-");
                String dateStr = dateParts[2] + "/" + dateParts[1]; 
                int textX = x + (colWidth - g2.getFontMetrics().stringWidth(dateStr)) / 2;
                g2.drawString(dateStr, textX, h - 15);
            }
        }
    }
}