package GUI; 

import BUS.TheLoaiBUS; 
import DTO.TheLoaiDTO; 

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class TheLoaiDetailDialog extends JDialog {

    private TheLoaiDTO theLoai;
    private JTextField txtMa, txtTen; 
    private JComboBox<DanhMucItem> cbDanhMuc;
    private final TheLoaiBUS bus = new TheLoaiBUS();

    // Bộ màu Luxury đồng bộ hệ thống
    private final Color COL_PRIMARY = new Color(232, 60, 145);
    private final Color COL_DARK = new Color(67, 51, 76);
    private final Color COL_SUCCESS = new Color(40, 167, 69);

    public TheLoaiDetailDialog(JFrame parent, TheLoaiDTO tl){
        super(parent, true);
        this.theLoai = tl;

        setTitle("Cập Nhật Thông Tin Thể Loại");
        setSize(450, 330); // Đã nới form rộng ra và cao lên cho thoáng
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE); 

        add(createHeader(), BorderLayout.NORTH);
        add(createForm(), BorderLayout.CENTER);
        add(createFooter(), BorderLayout.SOUTH);

        loadData();
    }

    // ================= HEADER =================
    private JPanel createHeader() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 2, 0, COL_PRIMARY),
            new EmptyBorder(15, 20, 10, 20)
        ));

        JLabel title = new JLabel("Chi Tiết Thể Loại");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(COL_DARK);
        panel.add(title, BorderLayout.WEST);
        return panel;
    }

    // ================= FORM NHẬP LIỆU =================
    private JPanel createForm(){
        // Chỉnh lại khoảng cách giữa các dòng (từ 20 xuống 15) cho đỡ loãng
        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 15)); 
        panel.setBackground(Color.WHITE);
        
        // Tăng lề 2 bên (trái, phải lên 40) để form nhìn gom vào giữa đẹp hơn
        panel.setBorder(new EmptyBorder(20, 40, 10, 40));

        txtMa = createStyledTextField();
        txtTen = createStyledTextField();
        // ĐÃ SỬA: ComboBox cho form Chi tiết
        cbDanhMuc = new JComboBox<>();
        cbDanhMuc.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cbDanhMuc.setBackground(Color.WHITE);
        loadDanhMuc();

        // Khóa mã thể loại, bôi xám tinh tế
        txtMa.setEditable(false);
        txtMa.setBackground(new Color(245, 245, 245));
        txtMa.setForeground(new Color(120, 120, 120));

        panel.add(createRow("Mã thể loại:", txtMa));
        panel.add(createRow("Tên thể loại (*):", txtTen));
        panel.add(createRow("Mã danh mục (*):", cbDanhMuc)); 
        
        return panel;
    }

    private JPanel createRow(String labelText, JComponent input) {
        JPanel row = new JPanel(new BorderLayout(10, 0)); // Giảm khoảng cách chữ và ô nhập
        row.setBackground(Color.WHITE);
        
        JLabel lbl = new JLabel(labelText);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lbl.setForeground(COL_DARK);
        lbl.setPreferredSize(new Dimension(140, 35)); // Chữ rộng ra xíu để không bị dính
        
        // Tăng chiều cao ô nhập liệu lên 38px cho "mập mạp", dễ nhìn hơn
        input.setPreferredSize(new Dimension(200, 38)); 
        
        row.add(lbl, BorderLayout.WEST); 
        row.add(input, BorderLayout.CENTER);
        return row;
    }

    private JTextField createStyledTextField() {
        JTextField txt = new JTextField();
        txt.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txt.setForeground(new Color(50, 50, 50));
        
        // Viền thanh mảnh, bo góc nhẹ
        Border defaultBorder = BorderFactory.createCompoundBorder(
            new LineBorder(new Color(180, 180, 180), 1, true), 
            new EmptyBorder(5, 12, 5, 12)
        );
        Border focusBorder = BorderFactory.createCompoundBorder(
            new LineBorder(COL_PRIMARY, 2, true), 
            new EmptyBorder(4, 11, 4, 11)
        );
        
        txt.setBorder(defaultBorder);
        txt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) { 
                if(txt.isEditable()){ 
                    txt.setBorder(focusBorder); 
                    txt.setBackground(new Color(255, 250, 252)); 
                } 
            }
            public void focusLost(java.awt.event.FocusEvent evt) { 
                if(txt.isEditable()){ 
                    txt.setBorder(defaultBorder); 
                    txt.setBackground(Color.WHITE); 
                } 
            }
        });
        return txt;
    }

    // ================= FOOTER =================
    private JPanel createFooter(){
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        panel.setBackground(new Color(248, 244, 236)); 
        panel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(220, 220, 220)));

        JButton btnCancel = createStyledButton("Đóng", new Color(150, 150, 150));
        JButton btnSave = createStyledButton("Lưu Thay Đổi", COL_PRIMARY); 
        
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
        
        // Thu nhỏ chiều cao nút bấm lại một xíu (còn 38px) cho cân đối
        btn.setPreferredSize(new Dimension(130, 38)); 
        
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) { btn.setBackground(bgColor.brighter()); }
            public void mouseExited(java.awt.event.MouseEvent evt) { btn.setBackground(bgColor); }
        }); 
        return btn;
    }

    // ================= LOGIC XỬ LÝ =================
    private void loadData() {
        txtMa.setText(theLoai.getMaTL());
        txtTen.setText(theLoai.getTenLoai());
        
        // Vòng lặp tìm và chọn đúng danh mục của thể loại này
        for (int i = 0; i < cbDanhMuc.getItemCount(); i++) {
            DanhMucItem item = cbDanhMuc.getItemAt(i);
            if (item.id == theLoai.getMaDanhMuc()) {
                cbDanhMuc.setSelectedIndex(i);
                break;
            }
        }
    }

    private void save(){
        String ten = txtTen.getText().trim();
        DanhMucItem dm = (DanhMucItem) cbDanhMuc.getSelectedItem();

        if(ten.isEmpty() || dm == null){
            JOptionPane.showMessageDialog(this, "Vui lòng nhập Tên và Chọn danh mục!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        theLoai.setTenLoai(ten);
        theLoai.setMaDanhMuc(dm.id);

        boolean ok = bus.updateTheLoai(theLoai);
        if(ok){
            JOptionPane.showMessageDialog(this, "Đã cập nhật thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Cập nhật thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    // ================= HÀM BỔ TRỢ COMBOBOX =================
    private void loadDanhMuc() {
        String sql = "SELECT MaID, TenDanhMuc FROM DanhMuc WHERE TrangThai = 1";
        try (java.sql.Connection con = Utils.JDBCConnection.getConnection();
             java.sql.Statement st = con.createStatement();
             java.sql.ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                cbDanhMuc.addItem(new DanhMucItem(rs.getInt("MaID"), rs.getString("TenDanhMuc")));
            }
        } catch (Exception e) { 
            e.printStackTrace(); 
        }
    }

    class DanhMucItem {
        int id; 
        String name;
        public DanhMucItem(int id, String name) { 
            this.id = id; 
            this.name = name; 
        }
        @Override 
        public String toString() { 
            return name; 
        }
    }
}