package DTO;

import java.sql.Timestamp;

public class TaiKhoanNhanVienDTO {
    private int maID;
    private String maTKNV; // Mã chữ tự sinh (VD: TKNV0001)
    private int maQuyen;
    private String tenDangNhap;
    private String matKhau;
    private String trangThai;
    private Timestamp ngayTao;
    
    // Biến phụ để hiển thị lên GUI cho đẹp (Không lưu vào DB bảng TaiKhoan)
    private String tenQuyen; 

    public TaiKhoanNhanVienDTO() {
    }

    // Constructor dùng cho lúc Thêm Mới (Không cần ID, Mã TKNV, NgayTao vì DB tự sinh)
    public TaiKhoanNhanVienDTO(int maQuyen, String tenDangNhap, String matKhau, String trangThai) {
        this.maQuyen = maQuyen;
        this.tenDangNhap = tenDangNhap;
        this.matKhau = matKhau;
        this.trangThai = trangThai;
    }

    // --- GETTER & SETTER ---
    public int getMaID() { return maID; }
    public void setMaID(int maID) { this.maID = maID; }

    public String getMaTKNV() { return maTKNV; }
    public void setMaTKNV(String maTKNV) { this.maTKNV = maTKNV; }

    public int getMaQuyen() { return maQuyen; }
    public void setMaQuyen(int maQuyen) { this.maQuyen = maQuyen; }

    public String getTenDangNhap() { return tenDangNhap; }
    public void setTenDangNhap(String tenDangNhap) { this.tenDangNhap = tenDangNhap; }

    public String getMatKhau() { return matKhau; }
    public void setMatKhau(String matKhau) { this.matKhau = matKhau; }

    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }

    public Timestamp getNgayTao() { return ngayTao; }
    public void setNgayTao(Timestamp ngayTao) { this.ngayTao = ngayTao; }

    public String getTenQuyen() { return tenQuyen; }
    public void setTenQuyen(String tenQuyen) { this.tenQuyen = tenQuyen; }
}