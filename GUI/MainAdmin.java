package GUI; // Nếu Sếp để ở package khác thì sửa chữ 'main' này lại cho đúng nhé

import GUI.LoginFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class MainAdmin {
    public static void main(String[] args) {
        // 1. Cài đặt giao diện (Look and Feel) cho giống với hệ điều hành Windows/Mac
        try { 
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); 
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // 2. Khởi chạy màn hình Đăng Nhập (LoginFrame) đầu tiên
        SwingUtilities.invokeLater(() -> {
            LoginFrame login = new LoginFrame();
            login.setVisible(true);
        });
    }
}