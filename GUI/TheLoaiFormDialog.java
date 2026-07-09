package GUI; 

import BUS.TheLoaiBUS; 
import DTO.TheLoaiDTO; 

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class TheLoaiFormDialog extends JDialog {

    private JTextField txtTen; 
    private final TheLoaiBUS bus = new TheLoaiBUS();
    private TheLoaiDTO result = null;
    private JComboBox<DanhMucItem> cbDanhMuc;

    // Bộ màu đồng bộ
    private final Color COL_PRIMARY = new Color(232, 60, 145);
    private final Color COL_DARK = new Color(67, 51, 76);
    private final Color COL_SUCCESS = new Color(40, 167, 69);

    public TheLoaiFormDialog(JFrame parent){
        super(parent, true);
        setTitle("Thêm Thể Loại Mới");
        setSize(450, 300); // Chiều cao nới lên 300 cho tỷ lệ đẹp
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE); 

        add(createHeader(), BorderLayout.NORTH);
        add(createForm(), BorderLayout.CENTER);
        add(createFooter(), BorderLayout.SOUTH);
    }

    // ================= HEADER NGUYÊN KHỐI =================
    private JPanel createHeader() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(COL_DARK); // Đổi nền thành màu tối nguyên khối
        panel.setBorder(new EmptyBorder(15, 0, 15, 0)); // Tạo độ dày cho thanh header

        JLabel title = new JLabel("THÊM THỂ LOẠI MỚI", SwingConstants.CENTER); // Căn giữa chữ
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(Color.WHITE); // Chữ màu trắng nổi bật
        panel.add(title, BorderLayout.CENTER);
        
        return panel;
    }

  
    private JPanel createForm(){
        JPanel panel = new JPanel(new GridLayout(2, 1, 10, 20)); 
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(30, 45, 20, 45)); 

        txtTen = createStyledTextField();
        
        // ĐÃ SỬA: Tạo ComboBox và Load dữ liệu từ DB lên
        cbDanhMuc = new JComboBox<>();
        cbDanhMuc.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cbDanhMuc.setBackground(Color.WHITE);
        loadDanhMuc(); 

        panel.add(createRow("Tên thể loại (*):", txtTen));
        panel.add(createRow("Tên danh mục (*):", cbDanhMuc)); 
        
        return panel;
    }

    private JPanel createRow(String labelText, JComponent input) {
        JPanel row = new JPanel(new BorderLayout(15, 0)); // Chỉnh lại khoảng cách chữ và ô nhập
        row.setBackground(Color.WHITE);
        
        JLabel lbl = new JLabel(labelText);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lbl.setForeground(COL_DARK);
        lbl.setPreferredSize(new Dimension(130, 40)); 
        
        // Form cao 40px nhìn chắc chắn và xịn hơn
        input.setPreferredSize(new Dimension(200, 40)); 
        
        row.add(lbl, BorderLayout.WEST);
        row.add(input, BorderLayout.CENTER);
        return row;
    }

    private JTextField createStyledTextField() {
        JTextField txt = new JTextField();
        txt.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txt.setForeground(new Color(50, 50, 50));
        
        Border defaultBorder = BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 200, 200), 1, true), 
            new EmptyBorder(5, 12, 5, 12)
        );
        Border focusBorder = BorderFactory.createCompoundBorder(
            new LineBorder(COL_PRIMARY, 2, true), 
            new EmptyBorder(4, 11, 4, 11)
        );
        
        txt.setBorder(defaultBorder);
        txt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) { txt.setBorder(focusBorder); txt.setBackground(new Color(255, 250, 252)); }
            public void focusLost(java.awt.event.FocusEvent evt) { txt.setBorder(defaultBorder); txt.setBackground(Color.WHITE); }
        });
        return txt;
    }

    // ================= FOOTER NÚT BẤM =================
    private JPanel createFooter(){
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10)); // Giảm margin dưới cho cân bằng
        panel.setBackground(new Color(245, 245, 245)); // Nền xám nhạt tinh tế
        panel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(220, 220, 220)));

        JButton btnCancel = createStyledButton("Hủy", new Color(150, 150, 150));
        JButton btnSave = createStyledButton("Thêm Mới", COL_SUCCESS); 
        
        panel.add(btnCancel); 
        panel.add(btnSave);
        btnCancel.addActionListener(e -> dispose());
        btnSave.addActionListener(e -> save());
        return panel;
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton btn = new JButton(text); 
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14)); 
        btn.setForeground(Color.WHITE);
        btn.setBackground(bgColor); 
        btn.setFocusPainted(false); 
        btn.setBorderPainted(false); 
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
        btn.setPreferredSize(new Dimension(120, 40)); // Nút bấm tròn trịa, vuông vức
        
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) { btn.setBackground(bgColor.brighter()); }
            public void mouseExited(java.awt.event.MouseEvent evt) { btn.setBackground(bgColor); }
        }); 
        return btn;
    }

    private void save(){
        String ten = txtTen.getText().trim();
        DanhMucItem dm = (DanhMucItem) cbDanhMuc.getSelectedItem(); // Lấy mục đang được chọn

        if(ten.isEmpty() || dm == null){
            JOptionPane.showMessageDialog(this, "Vui lòng nhập Tên thể loại và Chọn danh mục!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        TheLoaiDTO tl = new TheLoaiDTO();
        tl.setTenLoai(ten);
        tl.setMaDanhMuc(dm.id); // Lấy cái ID ngầm bên dưới

        boolean ok = bus.addTheLoai(tl);
        if(ok){
            result = tl;
            JOptionPane.showMessageDialog(this, "Đã thêm thể loại thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Thêm thất bại do lỗi CSDL!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public TheLoaiDTO getResult(){
        return result;
    }
    // Hàm kết nối CSDL lôi tên Danh Mục lên ComboBox
    private void loadDanhMuc() {
        String sql = "SELECT MaID, TenDanhMuc FROM DanhMuc WHERE TrangThai = 1";
        try (java.sql.Connection con = Utils.JDBCConnection.getConnection();
             java.sql.Statement st = con.createStatement();
             java.sql.ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                cbDanhMuc.addItem(new DanhMucItem(rs.getInt("MaID"), rs.getString("TenDanhMuc")));
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    // Lớp phụ trợ gói cả ID và Tên vào 1 cục để bỏ vào ComboBox
    class DanhMucItem {
        int id; String name;
        public DanhMucItem(int id, String name) { this.id = id; this.name = name; }
        @Override public String toString() { return name; }
    }
}