package GUI; // Đã sửa thành GUI

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class SidebarPanel extends JPanel {

    public SidebarPanel(MainFrame frame) {
        setPreferredSize(new Dimension(240, 0));
        setBackground(new Color(72, 54, 81));
        setLayout(new BorderLayout());

        JLabel logo = new JLabel("NovaBook", SwingConstants.CENTER);
        logo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        logo.setForeground(new Color(255,150,180));
        logo.setBorder(new EmptyBorder(30,10,30,10));
        add(logo, BorderLayout.NORTH);

        JPanel menu = new JPanel();
        menu.setOpaque(false);
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        menu.setBorder(new EmptyBorder(10,20,10,20));

        // ĐÃ SỬA: Dùng hàm switchPanel của MainFrame và gọi đúng tên GUI
        menu.add(createMenuButton(" Quản lý sách", () -> frame.switchPanel(new SachPanel())));
        menu.add(Box.createVerticalStrut(15));
        menu.add(createMenuButton(" Tác giả", () -> frame.switchPanel(new TacGiaPanel())));
        menu.add(Box.createVerticalStrut(15));
        menu.add(createMenuButton(" Thể loại", () -> frame.switchPanel(new TheLoaiPanel())));
        menu.add(Box.createVerticalStrut(15));
        menu.add(createMenuButton(" Nhà xuất bản", () -> frame.switchPanel(new NhaXuatBanPanel())));
        menu.add(Box.createVerticalStrut(15));
        menu.add(createMenuButton(" Nhà cung cấp", () -> frame.switchPanel(new NhaCungCapGUI()))); 

        add(menu, BorderLayout.CENTER);
    }

    private JButton createMenuButton(String text, Runnable action) {
        JButton btn = new JButton(text); // Đã bỏ RoundedButton bị lỗi
        btn.setMaximumSize(new Dimension(200,45));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false); 
        btn.setOpaque(true); 
        btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(226, 56, 133));
        btn.setBorder(new EmptyBorder(10,15,10,15));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addActionListener(e -> action.run());

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) { btn.setBackground(new Color(240,80,150)); }
            public void mouseExited(java.awt.event.MouseEvent evt) { btn.setBackground(new Color(226, 56, 133)); }
        });
        return btn;
    }
}