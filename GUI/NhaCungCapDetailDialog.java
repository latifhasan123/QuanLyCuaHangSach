package GUI;

import BUS.NhaCungCapBUS;
import DTO.NhaCungCapDTO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class NhaCungCapDetailDialog extends JDialog {

    private JTextField txtMa, txtTen, txtSDT, txtDiaChi, txtEmail;
    private JButton btnSave, btnCancel;
    private NhaCungCapDTO ncc;
    private NhaCungCapBUS bus = new NhaCungCapBUS();

    public NhaCungCapDetailDialog(JFrame parent, NhaCungCapDTO ncc){
        super(parent,true);
        this.ncc = ncc;
        setTitle("Chi tiết nhà cung cấp");
        setSize(400,320); 
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        add(createForm(),BorderLayout.CENTER);
        add(createFooter(),BorderLayout.SOUTH);
        loadData();
    }

    private JPanel createForm(){
        JPanel panel = new JPanel(new GridLayout(5,2,10,10));
        panel.setBorder(new EmptyBorder(20,20,20,20));

        txtMa = new JTextField(); txtMa.setEditable(false);
        txtTen = new JTextField(); txtSDT = new JTextField();
        txtEmail = new JTextField(); txtDiaChi = new JTextField();
        
        panel.add(new JLabel("Mã ID")); panel.add(txtMa);
        panel.add(new JLabel("Tên NCC")); panel.add(txtTen);
        panel.add(new JLabel("SĐT")); panel.add(txtSDT);
        panel.add(new JLabel("Email")); panel.add(txtEmail);
        panel.add(new JLabel("Địa chỉ")); panel.add(txtDiaChi);
        return panel;
    }

    private JPanel createFooter(){
        JPanel panel = new JPanel();
        btnSave = new JButton("Lưu"); btnCancel = new JButton("Đóng");
        panel.add(btnSave); panel.add(btnCancel);
        btnSave.addActionListener(e -> saveNCC());
        btnCancel.addActionListener(e -> dispose());
        return panel;
    }

    private void loadData(){
        txtMa.setText(String.valueOf(ncc.getMaID())); 
        txtTen.setText(ncc.getTenNCC());
        txtSDT.setText(ncc.getSoDienThoai());
        txtEmail.setText(ncc.getEmail());
        txtDiaChi.setText(ncc.getDiaChi());
    }

    private void saveNCC(){
        try{
            ncc.setTenNCC(txtTen.getText());
            ncc.setSoDienThoai(txtSDT.getText());
            ncc.setEmail(txtEmail.getText());
            ncc.setDiaChi(txtDiaChi.getText());

            // ĐÃ SỬA CHUẨN CÚ PHÁP BOOLEAN
            boolean ok = bus.suaNCC(ncc); 
            if(ok){
                JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi! Không thể cập nhật.");
            }
        }catch(Exception ex){
            JOptionPane.showMessageDialog(this,"Dữ liệu không hợp lệ");
        }
    }
}