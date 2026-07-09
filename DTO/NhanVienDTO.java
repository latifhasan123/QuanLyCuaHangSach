package DTO;

public class NhanVienDTO {
    private int maID;           
    private String maNV;        
    private int maChucVu;
    private int maTaiKhoanNV;
    private String hoTen;
    private String email;
    private String soDienThoai;
    private String maChucVu_Chu;
    private String maTaiKhoan_Chu;

    public NhanVienDTO() {}

    public NhanVienDTO(int maChucVu, int maTaiKhoanNV, String hoTen, String email, String soDienThoai) {
        this.maChucVu = maChucVu;
        this.maTaiKhoanNV = maTaiKhoanNV;
        this.hoTen = hoTen;
        this.email = email;
        this.soDienThoai = soDienThoai;
    }

    public NhanVienDTO(int maID, String maNV, int maChucVu, int maTaiKhoanNV, String hoTen, String email, String soDienThoai) {
        this.maID = maID;
        this.maNV = maNV;
        this.maChucVu = maChucVu;
        this.maTaiKhoanNV = maTaiKhoanNV;
        this.hoTen = hoTen;
        this.email = email;
        this.soDienThoai = soDienThoai;
    }

    public int getMaID() { return maID; }
    public void setMaID(int maID) { this.maID = maID; }
    public String getMaNV() { return maNV; }
    public void setMaNV(String maNV) { this.maNV = maNV; }
    public int getMaChucVu() { return maChucVu; }
    public void setMaChucVu(int maChucVu) { this.maChucVu = maChucVu; }
    public int getMaTaiKhoanNV() { return maTaiKhoanNV; }
    public void setMaTaiKhoanNV(int maTaiKhoanNV) { this.maTaiKhoanNV = maTaiKhoanNV; }
    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getSoDienThoai() { return soDienThoai; }
    public void setSoDienThoai(String soDienThoai) { this.soDienThoai = soDienThoai; }
    public String getMaChucVu_Chu() { return maChucVu_Chu; }
    public void setMaChucVu_Chu(String maChucVu_Chu) { this.maChucVu_Chu = maChucVu_Chu; }
    public String getMaTaiKhoan_Chu() { return maTaiKhoan_Chu; }
    public void setMaTaiKhoan_Chu(String maTaiKhoan_Chu) { this.maTaiKhoan_Chu = maTaiKhoan_Chu; }
}