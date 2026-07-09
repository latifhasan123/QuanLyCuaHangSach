package GUI;

import BUS.SachBUS;
import DTO.SachDTO;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.text.DecimalFormat;

public class SachDetailDialog extends JDialog {
    private SachDTO sach;
    private JFrame parentFrame;
    private DecimalFormat df = new DecimalFormat("#,### đ");

    private Color darkPurple = Color.decode("#5A4664");
    private Color bgContent = Color.decode("#F5F6FA");
    
    private JPanel mainCard; 

    public SachDetailDialog(JFrame parent, SachDTO sach){
        super(parent, true); 
        this.parentFrame = parent;
        this.sach = sach;
        setTitle("Chi Tiết Sách - " + sach.getMaSach()); 
        setSize(850, 620); 
        setLocationRelativeTo(parent); 
        setLayout(new BorderLayout());
        getContentPane().setBackground(bgContent);

        mainCard = new JPanel(new BorderLayout());
        mainCard.setOpaque(false);
        mainCard.setBorder(new EmptyBorder(15, 20, 10, 20));

        mainCard.add(createHeader(), BorderLayout.NORTH); 
        mainCard.add(createTabs(), BorderLayout.CENTER);
        
        add(mainCard, BorderLayout.CENTER);
        add(createFooter(), BorderLayout.SOUTH); 
    }

    private void refreshData() {
        SachDTO newData = new SachBUS().getSachByID(sach.getMaID());
        if (newData != null) {
            this.sach = newData;
            setTitle("Chi Tiết Sách - " + sach.getMaSach());
            
            mainCard.removeAll(); 
            mainCard.add(createHeader(), BorderLayout.NORTH); 
            mainCard.add(createTabs(), BorderLayout.CENTER); 
            
            mainCard.revalidate();
            mainCard.repaint(); 
            
            // Xóa Footer cũ vẽ Footer mới để Cập nhật lại màu của Nút Ngừng Bán/Mở Bán
            BorderLayout layout = (BorderLayout) getContentPane().getLayout();
            getContentPane().remove(layout.getLayoutComponent(BorderLayout.SOUTH));
            add(createFooter(), BorderLayout.SOUTH);
            getContentPane().revalidate();
            getContentPane().repaint();
        }
    }

    private JPanel createHeader(){
        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT)); 
        header.setOpaque(false);
        header.setBorder(new EmptyBorder(0, 0, 10, 0));
        JLabel title = new JLabel("CHI TIẾT SÁCH: " + sach.getTenSach().toUpperCase()); 
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(darkPurple);
        header.add(title); 
        return header;
    }

    private JTabbedPane createTabs(){
        JTabbedPane tabs = new JTabbedPane(); 
        tabs.setFont(new Font("Segoe UI", Font.BOLD, 15));
        tabs.setBackground(Color.WHITE);
        tabs.setForeground(darkPurple);
        tabs.setFocusable(false);
        
        tabs.add("THÔNG TIN CHUNG", createInfoTab()); 
        tabs.add("MÔ TẢ NỘI DUNG", createDescriptionTab()); 
        return tabs;
    }

    private JPanel createInfoTab(){
        JPanel panel = new JPanel(new BorderLayout(25, 20)); 
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(20, 25, 20, 25));
        
        JPanel infoPanel = new JPanel(new GridLayout(6, 2, 15, 10));
        infoPanel.setBackground(Color.WHITE);
        
        infoPanel.add(createField("Mã Sách", sach.getMaSach())); 
        infoPanel.add(createField("Mã Vạch (ISBN)", sach.getIsbn()));
        
        infoPanel.add(createField("Giá Nhập", df.format(sach.getGiaNhap()))); 
        infoPanel.add(createField("Giá Bán", df.format(sach.getGiaBan())));
        
        infoPanel.add(createField("Tồn Kho", sach.getSoLuongTon() + " cuốn")); 
        infoPanel.add(createField("Thời Gian Đổi Trả", sach.getSoNgayDoiTra() + " ngày"));
        
        String trangThai = (sach.getTrangThai() != null && sach.getTrangThai().equals("DangBan")) ? "Đang Bán" : "Ngừng Bán";
        infoPanel.add(createField("Trạng Thái", trangThai)); 
        infoPanel.add(createField("Thể Loại", sach.getTenLoai()));
        
        infoPanel.add(createField("Tác Giả", sach.getTenTacGia())); 
        infoPanel.add(createField("Nhà Xuất Bản", sach.getTenNXB()));
        
        JPanel imageWrapper = new JPanel(new BorderLayout());
        imageWrapper.setBackground(Color.WHITE);
        
        JLabel imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(200, 280)); 
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.decode("#DCDDE1"), 1));
        
        String hinhAnh = sach.getHinhAnh();
        if(hinhAnh != null && !hinhAnh.trim().isEmpty()){
            try {
                ImageIcon icon = null;
                java.net.URL imgURL = getClass().getResource("/images/" + hinhAnh);
                if (imgURL != null) icon = new ImageIcon(imgURL);
                else {
                    File imgFile = new File(hinhAnh);
                    if (!imgFile.exists()) imgFile = new File("images/" + hinhAnh);
                    if (!imgFile.exists()) imgFile = new File("src/images/" + hinhAnh);
                    if (imgFile.exists()) icon = new ImageIcon(imgFile.getAbsolutePath());
                }
                if (icon != null) {
                    Image img = icon.getImage().getScaledInstance(200, 280, Image.SCALE_SMOOTH); 
                    imageLabel.setIcon(new ImageIcon(img));
                } else {
                    imageLabel.setText("Không tìm thấy file ảnh");
                    imageLabel.setFont(new Font("Segoe UI", Font.ITALIC, 13));
                }
            } catch (Exception e) { imageLabel.setText("Lỗi định dạng ảnh"); }
        } else {
            imageLabel.setText("Chưa cập nhật ảnh");
            imageLabel.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        }
        
        JPanel imgContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        imgContainer.setBackground(Color.WHITE);
        imgContainer.add(imageLabel);
        
        imageWrapper.add(imgContainer, BorderLayout.NORTH); 
        
        panel.add(infoPanel, BorderLayout.CENTER); 
        panel.add(imageWrapper, BorderLayout.EAST); 
        return panel;
    }

    private JPanel createDescriptionTab(){
        JPanel panel = new JPanel(new BorderLayout()); 
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(25, 25, 25, 25));
        
        JTextArea txtMoTa = new JTextArea(); 
        if(sach.getMoTa() != null && !sach.getMoTa().trim().isEmpty()) {
            txtMoTa.setText(sach.getMoTa());
        } else {
            txtMoTa.setText("Chưa có mô tả cho sách này.");
        }
        
        txtMoTa.setLineWrap(true); 
        txtMoTa.setWrapStyleWord(true); 
        txtMoTa.setEditable(false);
        txtMoTa.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtMoTa.setForeground(Color.DARK_GRAY);
        txtMoTa.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        JScrollPane scroll = new JScrollPane(txtMoTa);
        scroll.setBorder(BorderFactory.createLineBorder(Color.decode("#DCDDE1"), 1));
        scroll.getViewport().setBackground(Color.WHITE);
        
        panel.add(scroll, BorderLayout.CENTER); 
        return panel;
    }

    private JPanel createField(String label, String value){
        JPanel panel = new JPanel(new GridLayout(2, 1, 0, 2)); 
        panel.setBackground(Color.WHITE);
        
        JLabel lb = new JLabel(label); 
        lb.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lb.setForeground(darkPurple);
        
        JLabel val = new JLabel(value != null && !value.trim().isEmpty() ? value : "Chưa cập nhật"); 
        val.setFont(new Font("Segoe UI", Font.BOLD, 15));
        
        if (label.equals("Trạng Thái")) {
            val.setForeground(value.equals("Đang Bán") ? new Color(40, 167, 69) : new Color(220, 53, 69));
        } else {
            val.setForeground(Color.decode("#2D3436"));
        }
        
        panel.add(lb); 
        panel.add(val); 
        
        JPanel borderPanel = new JPanel(new BorderLayout());
        borderPanel.setBackground(Color.WHITE);
        borderPanel.add(panel, BorderLayout.CENTER);
        borderPanel.setBorder(new CompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, Color.decode("#EEEEEE")),
            new EmptyBorder(0, 0, 5, 0)
        ));
        
        return borderPanel;
    }

    private JPanel createFooter() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        panel.setBackground(bgContent);
        panel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.decode("#DCDDE1")));
        
        JButton btnEdit = createButton("SỬA THÔNG TIN", new Color(41, 128, 185));
        
        // ĐÃ NÂNG CẤP NÚT BẤM THÔNG MINH
        boolean isDangBan = sach.getTrangThai() != null && sach.getTrangThai().equals("DangBan");
        JButton btnToggleStatus = createButton(isDangBan ? "NGỪNG BÁN" : "MỞ BÁN LẠI", 
                                               isDangBan ? new Color(220, 53, 69) : new Color(230, 126, 34)); // Đỏ hoặc Cam
        
        JButton btnClose = createButton("ĐÓNG", darkPurple);

        btnEdit.addActionListener(e -> {
            new SachEditDialog(parentFrame, sach).setVisible(true);
            refreshData(); 
        });

        // XỬ LÝ LẬT TRẠNG THÁI NGƯỢC LẠI
        btnToggleStatus.addActionListener(e -> {
            String title = isDangBan ? "Xác nhận Ngừng Bán" : "Xác nhận Mở Bán Lại";
            String msg = isDangBan ? "Bạn có chắc chắn muốn chuyển sách này sang trạng thái NGỪNG BÁN?" 
                                   : "Bạn có chắc chắn muốn MỞ BÁN LẠI sách này để tiếp tục kinh doanh?";
                                   
            int confirm = JOptionPane.showConfirmDialog(this, msg, title, JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                sach.setTrangThai(isDangBan ? "NgungBan" : "DangBan"); // Lật ngược trạng thái
                if (new SachBUS().updateSach(sach)) {
                    JOptionPane.showMessageDialog(this, "Đã cập nhật trạng thái thành công!");
                    refreshData(); // F5 lại giao diện để chớp chớp nút
                } else {
                    JOptionPane.showMessageDialog(this, "Cập nhật thất bại!");
                }
            }
        });
        
        btnClose.addActionListener(e -> dispose());

        panel.add(btnEdit);
        panel.add(btnToggleStatus);
        panel.add(btnClose);
        return panel;
    }

    private JButton createButton(String text, Color bg) {
        JButton btn = new JButton(text) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // ĐÃ SỬA: Thay vì nhận màu cứng, nút sẽ nhận màu nền (getBackground) để tự động đổi màu khi bị ấn
                g2.setColor(getModel().isRollover() ? getBackground().darker() : getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setBackground(bg); // Quan trọng để nút tự lật màu
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setForeground(Color.WHITE);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(160, 40)); 
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
}