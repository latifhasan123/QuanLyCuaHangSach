package GUI;

import BUS.SachBUS;
import DAO.NhaXuatBanDAO;
import DAO.TacGiaDAO;
import DAO.TheLoaiDAO;
import DTO.NhaXuatBanDTO;
import DTO.SachDTO;
import DTO.TacGiaDTO;
import DTO.TheLoaiDTO;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SachFormDialog extends JDialog {
    private SachDTO sach;
    private JTextField txtTenSach, txtISBN, txtGiaNhap, txtGiaBan, txtTonKho, txtNgayDoiTra;
    private JTextField txtLoaiDisplay, txtTacGiaDisplay; 
    private JButton btnSelectLoai, btnSelectTacGia;
    private JComboBox<NhaXuatBanDTO> cbNXB;
    
    private JTextArea txtMoTa;
    private JLabel imageLabel;
    private String imagePath;
    private JButton btnSave, btnCancel, btnChooseImage;

    private List<TheLoaiDTO> allLoai = new ArrayList<>();
    private List<TheLoaiDTO> selectedLoai = new ArrayList<>();
    private List<TacGiaDTO> allTacGia = new ArrayList<>();
    private List<TacGiaDTO> selectedTacGia = new ArrayList<>();

    private Color darkPurple = Color.decode("#5A4664");
    private Color bgContent = Color.decode("#F5F6FA");

    public SachFormDialog(JFrame parent){
        super(parent, true);
        this.sach = new SachDTO(); 
        setTitle("Thêm Sách Mới");
        setSize(850, 620);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        getContentPane().setBackground(bgContent);

        JPanel mainCard = new JPanel(new BorderLayout());
        mainCard.setOpaque(false);
        mainCard.setBorder(new EmptyBorder(15, 20, 10, 20));

        mainCard.add(createHeader(), BorderLayout.NORTH);
        mainCard.add(createTabs(), BorderLayout.CENTER);

        add(mainCard, BorderLayout.CENTER);
        add(createFooter(), BorderLayout.SOUTH);

        loadDataFromDB(); 
    }

    private JPanel createHeader(){
        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT)); 
        header.setOpaque(false);
        header.setBorder(new EmptyBorder(0, 0, 10, 0));
        JLabel title = new JLabel("KHAI BÁO SÁCH MỚI"); 
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

        JPanel infoPanel = new JPanel(new GridLayout(5, 2, 15, 10));
        infoPanel.setBackground(Color.WHITE);

        txtTenSach = new JTextField(); txtISBN = new JTextField(); 
        txtGiaNhap = new JTextField("0"); txtGiaBan = new JTextField(); 
        txtTonKho = new JTextField("0"); txtNgayDoiTra = new JTextField("7");

        // KHÓA CỨNG GIÁ NHẬP & TỒN KHO LÚC TẠO MỚI
        txtGiaNhap.setEditable(false);
        txtGiaNhap.setBackground(Color.decode("#EEEEEE"));
        txtGiaNhap.setToolTipText("Giá nhập được tự động cập nhật từ Phiếu Nhập Kho");

        txtTonKho.setEditable(false);
        txtTonKho.setBackground(Color.decode("#EEEEEE"));
        txtTonKho.setToolTipText("Tồn kho được quản lý tự động qua Phiếu Nhập Kho");

        txtLoaiDisplay = new JTextField(); 
        btnSelectLoai = createSmallButton("CHỌN"); 
        btnSelectLoai.addActionListener(e -> openMultiSelectLoai());

        txtTacGiaDisplay = new JTextField(); 
        btnSelectTacGia = createSmallButton("CHỌN");
        btnSelectTacGia.addActionListener(e -> openMultiSelectTacGia());

        cbNXB = new JComboBox<>();

        infoPanel.add(createField("Tên Sách (*)", txtTenSach)); 
        infoPanel.add(createField("Mã Vạch ISBN (*)", txtISBN));
        infoPanel.add(createField("Giá Nhập (đ)", txtGiaNhap)); 
        infoPanel.add(createField("Giá Bán (đ)", txtGiaBan));
        infoPanel.add(createField("Tồn Kho (Cuốn)", txtTonKho)); 
        infoPanel.add(createField("Ngày Đổi Trả", txtNgayDoiTra));
        
        infoPanel.add(createMultiSelectField("Thể Loại (Nhiều)", txtLoaiDisplay, btnSelectLoai)); 
        infoPanel.add(createMultiSelectField("Tác Giả (Nhiều)", txtTacGiaDisplay, btnSelectTacGia));
        infoPanel.add(createComboField("Nhà Xuất Bản", cbNXB)); 
        
        JPanel emptySpace = new JPanel();
        emptySpace.setBackground(Color.WHITE);
        infoPanel.add(emptySpace);

        panel.add(infoPanel, BorderLayout.CENTER); 
        panel.add(createImagePanel(), BorderLayout.EAST);
        return panel;
    }

    private JPanel createField(String label, JTextField txt){
        JPanel panel = new JPanel(new BorderLayout(0, 5));
        panel.setBackground(Color.WHITE);
        
        JLabel lb = new JLabel(label); 
        lb.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lb.setForeground(darkPurple);
        
        txt.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        txt.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.decode("#DCDDE1"), 1),
            new EmptyBorder(2, 10, 2, 10) 
        ));
        
        panel.add(lb, BorderLayout.NORTH); 
        panel.add(txt, BorderLayout.CENTER);
        
        JPanel topAlign = new JPanel(new BorderLayout());
        topAlign.setBackground(Color.WHITE);
        topAlign.add(panel, BorderLayout.NORTH);
        return topAlign;
    }

    private JPanel createMultiSelectField(String label, JTextField txtDisplay, JButton btnChoose){
        JPanel panel = new JPanel(new BorderLayout(0, 5));
        panel.setBackground(Color.WHITE);

        JLabel lb = new JLabel(label);
        lb.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lb.setForeground(darkPurple);

        txtDisplay.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtDisplay.setEditable(false);
        txtDisplay.setBackground(Color.decode("#F8F9FA"));
        txtDisplay.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.decode("#DCDDE1"), 1),
            new EmptyBorder(4, 8, 4, 8)
        ));

        JPanel right = new JPanel(new BorderLayout(5, 0));
        right.setBackground(Color.WHITE);
        right.add(txtDisplay, BorderLayout.CENTER);
        
        btnChoose.setPreferredSize(new Dimension(80, 28)); 
        right.add(btnChoose, BorderLayout.EAST); 

        panel.add(lb, BorderLayout.NORTH);
        panel.add(right, BorderLayout.CENTER);

        JPanel topAlign = new JPanel(new BorderLayout());
        topAlign.setBackground(Color.WHITE);
        topAlign.add(panel, BorderLayout.NORTH);
        return topAlign;
    }

    private JPanel createComboField(String label, JComboBox<?> combo){
        JPanel panel = new JPanel(new BorderLayout(0, 5));
        panel.setBackground(Color.WHITE);
        
        JLabel lb = new JLabel(label); 
        lb.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lb.setForeground(darkPurple);
        
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        combo.setBackground(Color.WHITE);
        
        panel.add(lb, BorderLayout.NORTH); 
        panel.add(combo, BorderLayout.CENTER);
        
        JPanel topAlign = new JPanel(new BorderLayout());
        topAlign.setBackground(Color.WHITE);
        topAlign.add(panel, BorderLayout.NORTH);
        return topAlign;
    }

    private JPanel createImagePanel(){
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        
        imageLabel = new JLabel("Chưa cập nhật ảnh"); 
        imageLabel.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        imageLabel.setPreferredSize(new Dimension(200, 280));
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER); 
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.decode("#DCDDE1"), 1));
        
        btnChooseImage = createButton("CHỌN ẢNH BÌA", darkPurple); 
        btnChooseImage.setPreferredSize(new Dimension(200, 40));
        btnChooseImage.addActionListener(e -> chooseImage());
        
        JPanel imgContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        imgContainer.setBackground(Color.WHITE);
        imgContainer.add(imageLabel);
        
        panel.add(imgContainer, BorderLayout.CENTER); 
        panel.add(btnChooseImage, BorderLayout.SOUTH);
        return panel;
    }

    private void chooseImage(){
        JFileChooser chooser = new JFileChooser();
        int result = chooser.showOpenDialog(this);
        if(result == JFileChooser.APPROVE_OPTION){
            File file = chooser.getSelectedFile();
            imagePath = file.getAbsolutePath();
            ImageIcon icon = new ImageIcon(imagePath); 
            Image img = icon.getImage().getScaledInstance(200, 280, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(img));
        }
    }

    private JPanel createDescriptionTab(){
        JPanel panel = new JPanel(new BorderLayout()); 
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(25, 25, 25, 25));
        
        txtMoTa = new JTextArea(); 
        txtMoTa.setLineWrap(true); 
        txtMoTa.setWrapStyleWord(true);
        txtMoTa.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtMoTa.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        JScrollPane scroll = new JScrollPane(txtMoTa);
        scroll.setBorder(BorderFactory.createLineBorder(Color.decode("#DCDDE1"), 1));
        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createFooter(){
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        panel.setBackground(bgContent);
        panel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.decode("#DCDDE1")));
        
        btnSave = createButton("LƯU SÁCH", new Color(40, 167, 69)); 
        btnCancel = createButton("HỦY BỎ", new Color(220, 53, 69));
        
        btnSave.addActionListener(e -> saveBook()); 
        btnCancel.addActionListener(e -> dispose());
        
        panel.add(btnCancel); 
        panel.add(btnSave); 
        return panel;
    }

    private JButton createButton(String text, Color bg) {
        JButton btn = new JButton(text) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isRollover() ? bg.darker() : bg);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setForeground(Color.WHITE);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private JButton createSmallButton(String text) {
        JButton btn = new JButton(text) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isRollover() ? Color.decode("#34495E") : darkPurple);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 4, 4);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setForeground(Color.WHITE);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void openMultiSelectLoai() {
        JDialog dialog = new JDialog(this, "Chọn Các Thể Loại", true);
        dialog.setSize(350, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JPanel pnlList = new JPanel();
        pnlList.setLayout(new BoxLayout(pnlList, BoxLayout.Y_AXIS));
        pnlList.setBackground(Color.WHITE);

        List<JCheckBox> checkBoxes = new ArrayList<>();
        for (TheLoaiDTO tl : allLoai) {
            JCheckBox cb = new JCheckBox(tl.getTenLoai());
            cb.setFont(new Font("Segoe UI", Font.PLAIN, 15));
            cb.setBackground(Color.WHITE);
            cb.setFocusPainted(false);
            
            if (selectedLoai.stream().anyMatch(s -> s.getMaID() == tl.getMaID())) {
                cb.setSelected(true);
            }
            checkBoxes.add(cb);
            pnlList.add(cb);
        }

        JScrollPane scroll = new JScrollPane(pnlList);
        scroll.setBorder(new EmptyBorder(10, 10, 10, 10));
        scroll.getVerticalScrollBar().setUnitIncrement(16); 
        dialog.add(scroll, BorderLayout.CENTER);

        JButton btnOK = createButton("XÁC NHẬN", new Color(40, 167, 69));
        btnOK.setPreferredSize(new Dimension(150, 40));
        btnOK.addActionListener(e -> {
            selectedLoai.clear();
            for (int i = 0; i < allLoai.size(); i++) {
                if (checkBoxes.get(i).isSelected()) {
                    selectedLoai.add(allLoai.get(i));
                }
            }
            String text = selectedLoai.stream().map(TheLoaiDTO::getTenLoai).reduce((a, b) -> a + ", " + b).orElse("");
            txtLoaiDisplay.setText(text);
            dialog.dispose();
        });

        JPanel pnlBottom = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlBottom.setBackground(Color.WHITE);
        pnlBottom.add(btnOK);
        dialog.add(pnlBottom, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    private void openMultiSelectTacGia() {
        JDialog dialog = new JDialog(this, "Chọn Các Tác Giả", true);
        dialog.setSize(350, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JPanel pnlList = new JPanel();
        pnlList.setLayout(new BoxLayout(pnlList, BoxLayout.Y_AXIS));
        pnlList.setBackground(Color.WHITE);

        List<JCheckBox> checkBoxes = new ArrayList<>();
        for (TacGiaDTO tg : allTacGia) {
            JCheckBox cb = new JCheckBox(tg.getTenTacGia());
            cb.setFont(new Font("Segoe UI", Font.PLAIN, 15));
            cb.setBackground(Color.WHITE);
            cb.setFocusPainted(false);
            
            if (selectedTacGia.stream().anyMatch(s -> s.getMaID() == tg.getMaID())) {
                cb.setSelected(true);
            }
            checkBoxes.add(cb);
            pnlList.add(cb);
        }

        JScrollPane scroll = new JScrollPane(pnlList);
        scroll.setBorder(new EmptyBorder(10, 10, 10, 10));
        scroll.getVerticalScrollBar().setUnitIncrement(16); 
        dialog.add(scroll, BorderLayout.CENTER);

        JButton btnOK = createButton("XÁC NHẬN", new Color(40, 167, 69));
        btnOK.setPreferredSize(new Dimension(150, 40));
        btnOK.addActionListener(e -> {
            selectedTacGia.clear();
            for (int i = 0; i < allTacGia.size(); i++) {
                if (checkBoxes.get(i).isSelected()) {
                    selectedTacGia.add(allTacGia.get(i));
                }
            }
            String text = selectedTacGia.stream().map(TacGiaDTO::getTenTacGia).reduce((a, b) -> a + ", " + b).orElse("");
            txtTacGiaDisplay.setText(text);
            dialog.dispose();
        });

        JPanel pnlBottom = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlBottom.setBackground(Color.WHITE);
        pnlBottom.add(btnOK);
        dialog.add(pnlBottom, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    private void loadDataFromDB(){
        allLoai = new TheLoaiDAO().getAll();
        allTacGia = new TacGiaDAO().getAll();
        for(NhaXuatBanDTO nxb : new NhaXuatBanDAO().getAll()) cbNXB.addItem(nxb);
    }

    private void saveBook(){
        try{
            String tenSach = txtTenSach.getText().trim();
            String isbnMoi = txtISBN.getText().trim();
            String strGiaBan = txtGiaBan.getText().trim();
            String strNgayDoiTra = txtNgayDoiTra.getText().trim();

            if(tenSach.isEmpty()){
                JOptionPane.showMessageDialog(this, "Vui lòng nhập Tên sách!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                txtTenSach.requestFocus();
                return;
            }

            if(isbnMoi.isEmpty()){
                JOptionPane.showMessageDialog(this, "Vui lòng nhập Mã vạch (ISBN)!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                txtISBN.requestFocus();
                return;
            }
            
            if(isbnMoi.length() != 13 || !isbnMoi.matches("\\d+")){ 
                JOptionPane.showMessageDialog(this, "Mã vạch (ISBN) phải bao gồm đúng 13 chữ số!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                txtISBN.requestFocus();
                return;
            }

            if(strGiaBan.isEmpty()){
                JOptionPane.showMessageDialog(this, "Vui lòng nhập Giá bán!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                txtGiaBan.requestFocus();
                return;
            }

            double giaBan = 0;
            try {
                giaBan = Double.parseDouble(strGiaBan);
                if (giaBan < 0) {
                    JOptionPane.showMessageDialog(this, "Giá bán không được là số âm!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                    txtGiaBan.requestFocus();
                    return;
                }
            } catch(Exception e) {
                JOptionPane.showMessageDialog(this, "Giá bán phải là số hợp lệ!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                txtGiaBan.requestFocus();
                return;
            }

            int ngayDoiTra = 0;
            if (!strNgayDoiTra.isEmpty()) {
                try {
                    ngayDoiTra = Integer.parseInt(strNgayDoiTra);
                    if (ngayDoiTra < 0) {
                        JOptionPane.showMessageDialog(this, "Ngày đổi trả không được là số âm!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                        txtNgayDoiTra.requestFocus();
                        return;
                    }
                } catch(Exception e) {
                    JOptionPane.showMessageDialog(this, "Ngày đổi trả phải là số nguyên hợp lệ!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                    txtNgayDoiTra.requestFocus();
                    return;
                }
            }

            NhaXuatBanDTO nxb = (NhaXuatBanDTO) cbNXB.getSelectedItem();
            if(nxb == null){
                JOptionPane.showMessageDialog(this, "Vui lòng chọn Nhà xuất bản!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                cbNXB.requestFocus();
                return;
            }

            SachBUS bus = new SachBUS();
            if (bus.checkTrungISBN(isbnMoi, -1)) {
                JOptionPane.showMessageDialog(this, "Mã vạch (ISBN) này đã tồn tại trong hệ thống!", "Lỗi trùng mã", JOptionPane.WARNING_MESSAGE);
                txtISBN.requestFocus(); 
                return; 
            }
            
            sach.setTenSach(tenSach); 
            sach.setIsbn(isbnMoi);
            sach.setGiaNhap(0); 
            sach.setSoLuongTon(0); 
            sach.setGiaBan(giaBan);
            sach.setSoNgayDoiTra(ngayDoiTra);
            sach.setMaNXB(nxb.getMaID());
            
            sach.setMoTa(txtMoTa.getText() != null ? txtMoTa.getText().trim() : "");
            sach.setHinhAnh(imagePath != null ? imagePath : ""); 

            List<Integer> listMaLoai = selectedLoai.stream().map(TheLoaiDTO::getMaID).collect(Collectors.toList());
            sach.setListMaLoai(listMaLoai);

            List<Integer> listMaTacGia = selectedTacGia.stream().map(TacGiaDTO::getMaID).collect(Collectors.toList());
            sach.setListMaTacGia(listMaTacGia);

            boolean ok = bus.addSach(sach);
            if(ok){ 
                JOptionPane.showMessageDialog(this, "Đã thêm sách mới thành công!", "Hoàn tất", JOptionPane.INFORMATION_MESSAGE); 
                dispose(); 
            } else { 
                JOptionPane.showMessageDialog(this, "Lưu thất bại! Vui lòng kiểm tra lại hệ thống.", "Lỗi", JOptionPane.ERROR_MESSAGE); 
            }
        } catch(Exception ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi không xác định!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}