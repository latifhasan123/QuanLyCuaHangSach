package GUI;

import BUS.NhaXuatBanBUS;
import DTO.NhaXuatBanDTO;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class NhaXuatBanDetailDialog extends JDialog {
    
    private NhaXuatBanDTO nxb;
    private JTextField txtMa, txtTen, txtDiaChi;
    private final NhaXuatBanBUS bus = new NhaXuatBanBUS();

    // Bộ màu đồng bộ hệ thống
    private final Color COL_PRIMARY = new Color(232, 60, 145);
    private final Color COL_DARK = new Color(67, 51, 76);

    public NhaXuatBanDetailDialog(JFrame parent, NhaXuatBanDTO nxb){
        super(parent, true); 
        this.nxb = nxb;
        
        setTitle("Chi Tiết Nhà Xuất Bản"); 
        setSize(450, 350); 
        setLocationRelativeTo(parent); 
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        add(createHeader(), BorderLayout.NORTH);
        add(createForm(), BorderLayout.CENTER);
        add(createFooter(), BorderLayout.SOUTH);
        
        loadData();
    }

    private JPanel createHeader() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(COL_DARK); 
        panel.setBorder(new EmptyBorder(15, 0, 15, 0)); 

        JLabel title = new JLabel("CẬP NHẬT NHÀ XUẤT BẢN", SwingConstants.CENTER); 
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(Color.WHITE); 
        panel.add(title, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createForm(){
        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 20)); 
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(30, 45, 20, 45));

        txtMa = createStyledTextField(); 
        txtTen = createStyledTextField(); 
        txtDiaChi = createStyledTextField(); 
        
        // Khóa mã NXB
        txtMa.setEditable(false);
        txtMa.setBackground(new Color(245, 245, 245));
        txtMa.setForeground(new Color(120, 120, 120));

        panel.add(createRow("Mã NXB:", txtMa)); 
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
            public void focusGained(java.awt.event.FocusEvent evt) { if(txt.isEditable()){ txt.setBorder(focusBorder); txt.setBackground(new Color(255, 250, 252)); } }
            public void focusLost(java.awt.event.FocusEvent evt) { if(txt.isEditable()){ txt.setBorder(defaultBorder); txt.setBackground(Color.WHITE); } }
        });
        return txt;
    }

    private JPanel createFooter(){
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10)); 
        panel.setBackground(new Color(245, 245, 245)); 
        panel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(220, 220, 220)));

        JButton btnCancel = createStyledButton("Đóng", new Color(150, 150, 150));
        JButton btnSave = createStyledButton("Lưu Thay Đổi", COL_PRIMARY); 
        
        panel.add(btnCancel); panel.add(btnSave);
        btnCancel.addActionListener(e -> dispose());
        btnSave.addActionListener(e -> saveNXB());
        return panel;
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton btn = new JButton(text); 
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14)); btn.setForeground(Color.WHITE);
        btn.setBackground(bgColor); btn.setFocusPainted(false); btn.setBorderPainted(false); btn.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
        btn.setPreferredSize(new Dimension(130, 40)); 
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) { btn.setBackground(bgColor.brighter()); }
            public void mouseExited(java.awt.event.MouseEvent evt) { btn.setBackground(bgColor); }
        }); return btn;
    }

    private void loadData(){ 
        txtMa.setText(nxb.getMaNXB()); 
        txtTen.setText(nxb.getTenNXB()); 
        txtDiaChi.setText(nxb.getDiaChi()); 
    }
    
    private void saveNXB(){
        String ten = txtTen.getText().trim();
        if(ten.isEmpty()){
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên Nhà xuất bản!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try{
            nxb.setTenNXB(ten); 
            nxb.setDiaChi(txtDiaChi.getText().trim());
            
            boolean ok = bus.updateNXB(nxb); // Đã khớp hàm của Sếp
            
            if(ok){ 
                JOptionPane.showMessageDialog(this, "Cập nhật thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE); 
                dispose(); 
            } else { 
                JOptionPane.showMessageDialog(this, "Cập nhật thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE); 
            }
        }catch(Exception ex){ 
            JOptionPane.showMessageDialog(this, "Dữ liệu không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE); 
        }
    }
}