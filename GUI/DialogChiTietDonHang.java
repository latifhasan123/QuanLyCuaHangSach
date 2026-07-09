package GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;

public class DialogChiTietDonHang extends JDialog {

    private JTable table;
    private DefaultTableModel tableModel;
    private DecimalFormat df = new DecimalFormat("#,### đ");
    
    private Color darkPurple = Color.decode("#5A4664");
    private Color bgContent = Color.decode("#F5F6FA");

    public DialogChiTietDonHang(Frame parent, int maIDDonHang, String maDHDisplay) {
        super(parent, "Chi Tiết Đơn Hàng - " + maDHDisplay, true);
        setSize(800, 500);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(0, 0));
        getContentPane().setBackground(bgContent);

        JPanel pnlTop = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlTop.setOpaque(false);
        pnlTop.setBorder(new EmptyBorder(15, 0, 15, 0));
        JLabel lblTitle = new JLabel("CHI TIẾT ĐƠN HÀNG: " + maDHDisplay);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(darkPurple);
        pnlTop.add(lblTitle);
        add(pnlTop, BorderLayout.NORTH);

        String[] cols = {"Mã Sách", "Tên Sách", "Số Lượng", "Đơn Giá", "Thành Tiền"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(tableModel);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        table.setRowHeight(40);
        table.setShowGrid(false);
        table.setSelectionBackground(Color.decode("#FFF0F5"));
        table.setSelectionForeground(darkPurple);

        JTableHeader header = table.getTableHeader();
        header.setPreferredSize(new Dimension(0, 45));
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                label.setBackground(darkPurple);
                label.setForeground(Color.WHITE);
                label.setFont(new Font("Segoe UI", Font.BOLD, 15));
                label.setHorizontalAlignment(SwingConstants.CENTER);
                return label;
            }
        });

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
        
        table.getColumnModel().getColumn(1).setPreferredWidth(300);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(new EmptyBorder(0, 20, 0, 20));
        add(scrollPane, BorderLayout.CENTER);

        JPanel pnlBottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 15));
        pnlBottom.setOpaque(false);

        JButton btnClose = new JButton("ĐÓNG") {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isRollover() ? darkPurple.darker() : darkPurple);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btnClose.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnClose.setForeground(Color.WHITE);
        btnClose.setContentAreaFilled(false);
        btnClose.setBorderPainted(false);
        btnClose.setFocusPainted(false);
        btnClose.setPreferredSize(new Dimension(120, 40));
        btnClose.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnClose.addActionListener(e -> dispose());

        pnlBottom.add(btnClose);
        add(pnlBottom, BorderLayout.SOUTH);

        loadData(maIDDonHang);
    }

    private void loadData(int maIDDonHang) {
        String sql = "SELECT s.MaSach, s.TenSach, ct.SoLuong, ct.DonGia, ct.ThanhTien "
                   + "FROM ChiTietDonHang ct "
                   + "JOIN Sach s ON ct.MaSach = s.MaID "
                   + "WHERE ct.MaDH = ?";
        
        try (Connection con = Utils.JDBCConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
             
            ps.setInt(1, maIDDonHang);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                    rs.getString("MaSach"),
                    rs.getString("TenSach"),
                    rs.getInt("SoLuong"),
                    df.format(rs.getDouble("DonGia")),
                    df.format(rs.getDouble("ThanhTien"))
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}