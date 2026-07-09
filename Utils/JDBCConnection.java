 package Utils; // Đặt trong package DAO hoặc tương tự của bạn

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCConnection {
    
    // 1. Khai báo các thông tin kết nối cơ bản
    private static final String SERVER_NAME = "localhost"; // Hoặc địa chỉ IP máy chủ của bạn
    private static final String PORT = "1433";             // Cổng mặc định của SQL Server
    private static final String DATABASE_NAME = "QuanLyCuaHangSach"; // Sửa lại thành tên DB của bạn
    private static final String USERNAME = "sa";           // Tài khoản đăng nhập SQL Server
    private static final String PASSWORD = "123"; // Mật khẩu của tài khoản sa

    /**
     * 2. Chuỗi kết nối URL
     * LƯU Ý QUAN TRỌNG: Với driver mssql bản 10.0 trở lên (bạn đang dùng 13.2.1), 
     * bắt buộc phải có thêm "encrypt=true;trustServerCertificate=true;" 
     * nếu bạn đang chạy ở môi trường localhost (chưa có chứng chỉ SSL chính thức).
     */
    private static final String URL = "jdbc:sqlserver://" + SERVER_NAME + ":" + PORT 
            + ";databaseName=" + DATABASE_NAME 
            + ";encrypt=true;trustServerCertificate=true;";

    // 3. Hàm tạo và trả về Connection
    public static Connection getConnection() {
        Connection conn = null;
        try {
            // Đăng ký Driver với DriverManager (Thường tự động nhưng nên viết để tránh lỗi)
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            
            // Thiết lập kết nối
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("✅ Kết nối đến SQL Server thành công!");
            
        } catch (ClassNotFoundException e) {
            System.err.println("❌ Lỗi: Không tìm thấy thư viện JDBC Driver!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("❌ Lỗi: Kết nối thất bại. Vui lòng kiểm tra lại thông tin Server, Database, User hoặc Mật khẩu.");
            e.printStackTrace();
        }
        return conn;
    }
    
    // Hàm main dùng để chạy test thử xem kết nối có thành công không
    public static void main(String[] args) {
        getConnection();
    }
}