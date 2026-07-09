package DTO;

public class KhachHangDTO {
    private int maID;
    private String maKH;
    private int maTaiKhoanKH; // Có thể null (nếu khách mua tại quầy chưa có TK)
    private String hoTen;
    private String soDienThoai;
    private String email;
    private String diaChi;
    private int diemTichLuy;
    private String trangThai;

    public KhachHangDTO() {
    }

    // Constructor đầy đủ để lấy từ DB lên
    public KhachHangDTO(int maID, String maKH, int maTaiKhoanKH, String hoTen, String soDienThoai, String email, String diaChi, int diemTichLuy, String trangThai) {
        this.maID = maID;
        this.maKH = maKH;
        this.maTaiKhoanKH = maTaiKhoanKH;
        this.hoTen = hoTen;
        this.soDienThoai = soDienThoai;
        this.email = email;
        this.diaChi = diaChi;
        this.diemTichLuy = diemTichLuy;
        this.trangThai = trangThai;
    }

    // Constructor rút gọn để Thêm mới (Không cần truyền MaID, MaKH, Diem, TrangThai vì CSDL tự lo)
    public KhachHangDTO(String hoTen, String soDienThoai, String email, String diaChi) {
        this.hoTen = hoTen;
        this.soDienThoai = soDienThoai;
        this.email = email;
        this.diaChi = diaChi;
    }

    // --- GETTER & SETTER ---
    public int getMaID() { return maID; }
    public void setMaID(int maID) { this.maID = maID; }

    public String getMaKH() { return maKH; }
    public void setMaKH(String maKH) { this.maKH = maKH; }

    public int getMaTaiKhoanKH() { return maTaiKhoanKH; }
    public void setMaTaiKhoanKH(int maTaiKhoanKH) { this.maTaiKhoanKH = maTaiKhoanKH; }

    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }

    public String getSoDienThoai() { return soDienThoai; }
    public void setSoDienThoai(String soDienThoai) { this.soDienThoai = soDienThoai; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }

    public int getDiemTichLuy() { return diemTichLuy; }
    public void setDiemTichLuy(int diemTichLuy) { this.diemTichLuy = diemTichLuy; }

    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
}