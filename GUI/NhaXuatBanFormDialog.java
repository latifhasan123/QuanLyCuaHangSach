package GUI;

import BUS.NhaXuatBanBUS;
import DTO.NhaXuatBanDTO;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class NhaXuatBanFormDialog extends JDialog {

    private JTextField txtTen, txtDiaChi;
    private final NhaXuatBanBUS bus = new NhaXuatBanBUS();
    private NhaXuatBanDTO result = null;

    // Bộ màu đồng bộ hệ thống
    private final Color COL_PRIMARY = new Color(232, 60, 145);
    private final Color COL_DARK = new Color(67, 51, 76);
    private final Color COL_SUCCESS = new Color(40, 167, 69);

    public NhaXuatBanFormDialog(JFrame parent){
        super(parent, true);
        setTitle("Thêm Nhà Xuất Bản Mới");
        setSize(450, 300); // Nới rộng và cao form ra
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
        panel.setBackground(COL_DARK); 
        panel.setBorder(new EmptyBorder(15, 0, 15, 0)); 

        JLabel title = new JLabel("THÊM NHÀ XUẤT BẢN MỚI", SwingConstants.CENTER); 
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(Color.WHITE); 
        panel.add(title, BorderLayout.CENTER);
        return panel;
    }

    // ================= FORM NHẬP LIỆU =================
    private JPanel createForm(){
        JPanel panel = new JPanel(new GridLayout(2, 1, 10, 20)); 
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(30, 45, 20, 45)); 

        txtTen = createStyledTextField();
        txtDiaChi = createStyledTextField(); // Bổ sung ô Địa chỉ cho chuẩn DB

        panel.add(createRow("Tên NXB (*):", txtTen));
        panel.add(createRow("Địa chỉ:", txtDiaChi)); 
        return panel;
    }

    private JPanel createRow(String labelText, JComponent input) {
        JPanel row = new JPanel(new BorderLayout(15, 0));
        row.setBackground(Color.WHITE);
        
        JLabel lbl = new JLabel(labelText);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lbl.setForeground(COL_DARK);
        lbl.setPreferredSize(new Dimension(100, 40)); 
        
        input.setPreferredSize(new Dimension(200, 40)); 
        row.add(lbl, BorderLayout.WEST);
        row.add(input, BorderLayout.CENTER);
        return row;
    }

    private JTextField createStyledTextField() {
        JTextField txt = new JTextField();
        txt.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txt.setForeground(new Color(50, 50, 50));
        
        Border defaultBorder = BorderFactory.createCompoundBorder(new LineBorder(new Color(200, 200, 200), 1, true), new EmptyBorder(5, 12, 5, 12));
        Border focusBorder = BorderFactory.createCompoundBorder(new LineBorder(COL_PRIMARY, 2, true), new EmptyBorder(4, 11, 4, 11));
        
        txt.setBorder(defaultBorder);
        txt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) { txt.setBorder(focusBorder); txt.setBackground(new Color(255, 250, 252)); }
            public void focusLost(java.awt.event.FocusEvent evt) { txt.setBorder(defaultBorder); txt.setBackground(Color.WHITE); }
        });
        return txt;
    }

    // ================= FOOTER NÚT BẤM =================
    private JPanel createFooter(){
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10)); 
        panel.setBackground(new Color(245, 245, 245)); 
        panel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(220, 220, 220)));

        JButton btnCancel = createStyledButton("Hủy", new Color(150, 150, 150));
        JButton btnSave = createStyledButton("Thêm Mới", COL_SUCCESS); 
        
        panel.add(btnCancel); panel.add(btnSave);
        btnCancel.addActionListener(e -> dispose());
        btnSave.addActionListener(e -> save());
        return panel;
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton btn = new JButton(text); 
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14)); btn.setForeground(Color.WHITE);
        btn.setBackground(bgColor); btn.setFocusPainted(false); btn.setBorderPainted(false); btn.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
        btn.setPreferredSize(new Dimension(120, 40)); 
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) { btn.setBackground(bgColor.brighter()); }
            public void mouseExited(java.awt.event.MouseEvent evt) { btn.setBackground(bgColor); }
        }); return btn;
    }

    // ================= XỬ LÝ LƯU =================
    private void save(){
        String ten = txtTen.getText().trim();
        String diachi = txtDiaChi.getText().trim();

        if(ten.isEmpty()){
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên Nhà xuất bản!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        NhaXuatBanDTO nxb = new NhaXuatBanDTO();
        nxb.setTenNXB(ten);
        nxb.setDiaChi(diachi); // Bổ sung lưu địa chỉ

        boolean ok = bus.addNXB(nxb); // Đã khớp hàm của Sếp

        if(ok){
            result = nxb;
            JOptionPane.showMessageDialog(this, "Đã thêm Nhà xuất bản thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Thêm thất bại do lỗi hệ thống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public NhaXuatBanDTO getResult(){
        return result;
    }
}