package GUI;

import BUS.NhaCungCapBUS;
import DTO.NhaCungCapDTO;

import javax.swing.*;
import java.awt.*;

public class NhaCungCapFormDialog extends JDialog {

    private JTextField txtTen, txtSDT, txtDiaChi, txtEmail;
    private NhaCungCapBUS bus = new NhaCungCapBUS();

    public NhaCungCapFormDialog(JFrame parent){
        super(parent,true);
        setTitle("Thêm nhà cung cấp");
        setSize(400,300);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10,10));

        JPanel form = new JPanel(new GridLayout(8,1,5,5));
        form.setBorder(BorderFactory.createEmptyBorder(15,15,10,15));

        form.add(new JLabel("Tên NCC")); txtTen = new JTextField(); form.add(txtTen);
        form.add(new JLabel("SĐT")); txtSDT = new JTextField(); form.add(txtSDT);
        form.add(new JLabel("Email")); txtEmail = new JTextField(); form.add(txtEmail);
        form.add(new JLabel("Địa chỉ")); txtDiaChi = new JTextField(); form.add(txtDiaChi);

        add(form,BorderLayout.CENTER);

        JPanel footer = new JPanel();
        JButton btnSave = new JButton("Thêm"); JButton btnCancel = new JButton("Hủy");
        footer.add(btnCancel); footer.add(btnSave);
        add(footer,BorderLayout.SOUTH);

        btnCancel.addActionListener(e -> dispose());
        btnSave.addActionListener(e -> save());
    }

    private void save(){
        NhaCungCapDTO n = new NhaCungCapDTO();
        n.setTenNCC(txtTen.getText());
        n.setSoDienThoai(txtSDT.getText());
        n.setEmail(txtEmail.getText());
        n.setDiaChi(txtDiaChi.getText());

        // ĐÃ SỬA CHUẨN CÚ PHÁP BOOLEAN
        boolean ok = bus.themNCC(n); 

        if(ok){
            JOptionPane.showMessageDialog(this, "Thêm nhà cung cấp thành công!");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Lỗi! Dữ liệu không hợp lệ hoặc trùng lặp.");
        }
    }
}