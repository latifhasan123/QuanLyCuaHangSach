package DTO;

public class NhaCungCapDTO {
    private int maID;
    private String maNCC;
    private String tenNCC;
    private String diaChi;
    private String soDienThoai;
    private String email;
    private String trangThai;

    public NhaCungCapDTO() {
        this.trangThai = "HoatDong"; // Mặc định khi tạo mới
    }

    public NhaCungCapDTO(int maID, String tenNCC, String diaChi, String soDienThoai, String email) {
        this.maID = maID;
        this.tenNCC = tenNCC;
        this.diaChi = diaChi;
        this.soDienThoai = soDienThoai;
        this.email = email;
        this.trangThai = "HoatDong";
    }

    public int getMaID() { return maID; }
    public void setMaID(int maID) { this.maID = maID; }

    public String getMaNCC() { return maNCC; }
    public void setMaNCC(String maNCC) { this.maNCC = maNCC; }

    public String getTenNCC() { return tenNCC; }
    public void setTenNCC(String tenNCC) { this.tenNCC = tenNCC; }

    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }

    public String getSoDienThoai() { return soDienThoai; }
    public void setSoDienThoai(String soDienThoai) { this.soDienThoai = soDienThoai; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
}