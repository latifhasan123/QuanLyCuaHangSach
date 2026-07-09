package GUI; 

import BUS.TacGiaBUS;
import DTO.TacGiaDTO;
import com.toedter.calendar.JDateChooser; 

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class TacGiaFormDialog extends JDialog {

    private JTextField txtTen;
    private JTextField txtQuocTich; 
    private JDateChooser dateChooser; 
    private JDateChooser dateChooserMat;
    
    private TacGiaBUS bus = new TacGiaBUS();
    private TacGiaDTO result = null;

    // Bộ màu đồng bộ với MainFrame
    private final Color COL_PRIMARY = new Color(232, 60, 145);
    private final Color COL_DARK = new Color(67, 51, 76);
    private final Color COL_SUCCESS = new Color(40, 167, 69);

    public TacGiaFormDialog(JFrame parent){
        super(parent, true);
        setTitle("Thêm Tác Giả Mới");
        setSize(450, 450); // Nới rộng form cho thoáng
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE); // Đổi nền xám thành trắng

        add(createHeader(), BorderLayout.NORTH);
        add(createForm(), BorderLayout.CENTER);
        add(createFooter(), BorderLayout.SOUTH);
    }

    // ================= HEADER =================
    private JPanel createHeader() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(15, 20, 10, 20));

        JLabel title = new JLabel("Thêm Tác Giả Mới");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(COL_DARK);
        
        // Đường gạch dưới màu hồng tạo điểm nhấn
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 2, 0, COL_PRIMARY),
            new EmptyBorder(15, 20, 10, 20)
        ));

        panel.add(title, BorderLayout.WEST);
        return panel;
    }

    // ================= FORM NHẬP LIỆU =================
    private JPanel createForm(){
        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 20)); 
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(20, 30, 20, 30));

        txtTen = createStyledTextField();
        txtQuocTich = createStyledTextField();
        
        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("yyyy-MM-dd");
        dateChooser.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        dateChooserMat = new JDateChooser();
        dateChooserMat.setDateFormatString("yyyy-MM-dd");
        dateChooserMat.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        panel.add(createRow("Tên tác giả (*):", txtTen));
        panel.add(createRow("Ngày sinh:", dateChooser));
        panel.add(createRow("Ngày mất:", dateChooserMat));
        panel.add(createRow("Quốc tịch:", txtQuocTich));
        
        return panel;
    }

    private JPanel createRow(String labelText, JComponent input) {
        JPanel row = new JPanel(new BorderLayout(15, 0));
        row.setBackground(Color.WHITE);
        
        JLabel lbl = new JLabel(labelText);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lbl.setForeground(COL_DARK);
        lbl.setPreferredSize(new Dimension(110, 35));
        
        input.setPreferredSize(new Dimension(200, 35));
        
        row.add(lbl, BorderLayout.WEST);
        row.add(input, BorderLayout.CENTER);
        return row;
    }

    private JTextField createStyledTextField() {
        JTextField txt = new JTextField();
        txt.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txt.setForeground(new Color(50, 50, 50));
        Border defaultBorder = BorderFactory.createCompoundBorder(new LineBorder(new Color(180, 180, 180), 1, true), new EmptyBorder(5, 10, 5, 10));
        Border focusBorder = BorderFactory.createCompoundBorder(new LineBorder(COL_PRIMARY, 2, true), new EmptyBorder(4, 9, 4, 9));
        txt.setBorder(defaultBorder);
        
        // Hiệu ứng nháy sáng viền hồng khi click vào
        txt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) { txt.setBorder(focusBorder); txt.setBackground(new Color(255, 250, 252)); }
            public void focusLost(java.awt.event.FocusEvent evt) { txt.setBorder(defaultBorder); txt.setBackground(Color.WHITE); }
        });
        return txt;
    }

    // ================= FOOTER (NÚT BẤM) =================
    private JPanel createFooter(){
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        panel.setBackground(new Color(248, 244, 236)); 
        panel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(220, 220, 220)));

        JButton btnCancel = createStyledButton("Hủy", new Color(150, 150, 150));
        JButton btnSave = createStyledButton("Thêm Tác Giả", COL_SUCCESS); // Nút Thêm màu Xanh lá
        
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
        btn.setPreferredSize(new Dimension(135, 40));
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) { btn.setBackground(bgColor.brighter()); }
            public void mouseExited(java.awt.event.MouseEvent evt) { btn.setBackground(bgColor); }
        }); return btn;
    }

    // ================= XỬ LÝ LƯU =================
    private void save(){
        String ten = txtTen.getText().trim();
        if(ten.isEmpty()){
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên tác giả!", "Thiếu thông tin", JOptionPane.WARNING_MESSAGE);
            return;
        }

        TacGiaDTO tg = new TacGiaDTO();
        tg.setTenTacGia(ten);
        tg.setNgaySinh(dateChooser.getDate()); 
        
        // ĐÃ THÊM: Thu thập Ngày Mất từ giao diện
        if (dateChooserMat != null) {
            tg.setNgayMat(dateChooserMat.getDate());
        }
        
        tg.setQuocTich(txtQuocTich.getText().trim()); 

        boolean ok = bus.addTacGia(tg);

        if(ok){
            result = tg;
            JOptionPane.showMessageDialog(this, "Đã thêm tác giả thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        }
        else {
            JOptionPane.showMessageDialog(this, "Thêm thất bại do lỗi hệ thống! Vui lòng xem dòng chữ đỏ ở màn hình Console.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public TacGiaDTO getResult(){
        return result;
    }
}