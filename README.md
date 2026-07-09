# Phần Mềm Quản Lý Cửa Hàng Sách 📚

Đây là đồ án ứng dụng desktop được viết bằng Java, hỗ trợ quản lý các hoạt động cơ bản của một cửa hàng sách bao gồm quản lý doanh thu, kho hàng, nhập xuất sách và xử lý đơn hàng.

## 🛠️ Công nghệ sử dụng
* **Ngôn ngữ lập trình:** Java
* **IDE (Môi trường phát triển):** Apache NetBeans
* **Cơ sở dữ liệu:** MySQL / SQL Server (sử dụng JDBC)
* **Mô hình kiến trúc:** 3-Tier (GUI - BUS - DAO)

## 📁 Cấu trúc dự án
Dự án được phân chia thành các package chính để dễ dàng quản lý mã nguồn:
* `GUI / DTO`: Chứa các đối tượng dữ liệu và giao diện người dùng.
* `BUS`: Tầng xử lý nghiệp vụ (Business Logic).
* `DAO`: Tầng giao tiếp và truy vấn trực tiếp với Cơ sở dữ liệu (Data Access Object).

## 🚀 Hướng dẫn cài đặt và khởi chạy

1.  **Tải mã nguồn về máy (Clone project):**
    ```bash
    git clone [https://github.com/latifhasan123/QuanLyCuaHangSach.git](https://github.com/latifhasan123/QuanLyCuaHangSach.git)
    ```
2.  **Thiết lập Cơ sở dữ liệu (Database):**
    * Import file script SQL (nếu có) vào hệ quản trị cơ sở dữ liệu của bạn.
    * Mở file cấu hình kết nối database (thường là `JDBCConnection.java` hoặc file cấu hình tương đương) và thay đổi thông tin `username`, `password`, `URL` cho khớp với máy của bạn.

3.  **Mở dự án trên NetBeans:**
    * Mở Apache NetBeans.
    * Chọn `File` > `Open Project` và trỏ tới thư mục `QuanLyCuaHangSach`.

4.  **Chạy ứng dụng:**
    * Tìm đến file **`MainUser.java`** trong cây thư mục mã nguồn.
    * Nhấp chuột phải vào file **`MainUser.java`** và chọn **Run File** (hoặc nhấn `Shift + F6`) để khởi động phần mềm.

## 👥 Tác giả
* Phát triển bởi: [Hasan]
