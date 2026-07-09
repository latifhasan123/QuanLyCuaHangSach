package GUI; 

import BUS.TacGiaBUS;
import DTO.TacGiaDTO;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.Date;

public class TacGiaDetailDialog extends JDialog {

    private TacGiaDTO tacGia;
    private JTextField txtMa, txtTen, txtQuocTich;
    private JDateChooser dateChooser, dateChooserMat;
    private JButton btnSave, btnCancel;
    private TacGiaBUS bus = new TacGiaBUS();

    // Bộ màu đồng bộ
    private final Color COL_PRIMARY = new Color(232, 60, 145);
    private final Color COL_DARK = new Color(67, 51, 76);

    public TacGiaDetailDialog(JFrame parent, TacGiaDTO tg){
        super(parent, true);
        this.tacGia = tg;

        setTitle("Cập Nhật Thông Tin Tác Giả");
        setSize(450, 450); // Nới form rộng ra cho đẹp
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
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(15, 20, 10, 20));

        JLabel title = new JLabel("Chi Tiết Tác Giả");
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

    private JPanel createForm(){
        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(20, 30, 10, 30));

        txtMa = createStyledTextField(); 
        txtTen = createStyledTextField(); 
        txtQuocTich = createStyledTextField();
        
        dateChooser = new JDateChooser(); 
        dateChooser.setDateFormatString("yyyy-MM-dd");
        dateChooser.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        dateChooserMat = new JDateChooser(); 
        dateChooserMat.setDateFormatString("yyyy-MM-dd");
        dateChooserMat.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        txtMa.setEditable(false);
        txtMa.setBackground(new Color(240, 240, 240));
        txtMa.setForeground(new Color(100, 100, 100));

        panel.add(createRow("Mã tác giả:", txtMa));
        panel.add(createRow("Tên tác giả:", txtTen));
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
        lbl.setPreferredSize(new Dimension(100, 35));
        
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
        txt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) { if(txt.isEditable()) { txt.setBorder(focusBorder); txt.setBackground(new Color(255, 250, 252)); } }
            public void focusLost(java.awt.event.FocusEvent evt) { if(txt.isEditable()) { txt.setBorder(defaultBorder); txt.setBackground(Color.WHITE); } }
        });
        return txt;
    }

    private JPanel createFooter(){
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        panel.setBackground(new Color(248, 244, 236)); // Nền xám nhẹ cho phần footer
        panel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(220, 220, 220)));

        btnCancel = createStyledButton("Đóng", new Color(150, 150, 150));
        btnSave = createStyledButton("Lưu Thay Đổi", COL_PRIMARY);
        
        panel.add(btnCancel); 
        panel.add(btnSave);

        btnSave.addActionListener(e -> saveTacGia()); 
        btnCancel.addActionListener(e -> dispose());
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
        btn.setPreferredSize(new Dimension(130, 40));
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) { btn.setBackground(bgColor.brighter()); }
            public void mouseExited(java.awt.event.MouseEvent evt) { btn.setBackground(bgColor); }
        }); return btn;
    }


    private void loadData(){
        txtMa.setText(tacGia.getMaTG());
        txtTen.setText(tacGia.getTenTacGia());
        txtQuocTich.setText(tacGia.getQuocTich());
        Date ns = tacGia.getNgaySinh();
        if(ns != null) dateChooser.setDate(ns);
        Date nm = tacGia.getNgayMat();
        if(nm != null) dateChooserMat.setDate(nm);
    }

    private void saveTacGia(){
        try{
            tacGia.setTenTacGia(txtTen.getText());
            tacGia.setQuocTich(txtQuocTich.getText());
            tacGia.setNgaySinh(dateChooser.getDate());
            tacGia.setNgayMat(dateChooserMat.getDate());

            boolean ok = bus.updateTacGia(tacGia);
            if(ok){
                JOptionPane.showMessageDialog(this, "Cập nhật thông tin tác giả thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            }else{
                JOptionPane.showMessageDialog(this, "Cập nhật thất bại. Vui lòng thử lại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }catch(Exception ex){
            JOptionPane.showMessageDialog(this, "Dữ liệu nhập vào không hợp lệ!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
        }
    }
}