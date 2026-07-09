package DTO;

import java.sql.Timestamp;

public class TaiKhoanKhachHangDTO {
    private int maID;
    private String maTKKH;
    private String tenDangNhap;
    private String matKhau;
    private String trangThai;
    private Timestamp ngayTao;

    public TaiKhoanKhachHangDTO() {}

    public TaiKhoanKhachHangDTO(int maID, String maTKKH, String tenDangNhap, String matKhau, String trangThai, Timestamp ngayTao) {
        this.maID = maID;
        this.maTKKH = maTKKH;
        this.tenDangNhap = tenDangNhap;
        this.matKhau = matKhau;
        this.trangThai = trangThai;
        this.ngayTao = ngayTao;
    }

    // --- GETTER & SETTER ---
    public int getMaID() { return maID; }
    public void setMaID(int maID) { this.maID = maID; }

    public String getMaTKKH() { return maTKKH; }
    public void setMaTKKH(String maTKKH) { this.maTKKH = maTKKH; }

    public String getTenDangNhap() { return tenDangNhap; }
    public void setTenDangNhap(String tenDangNhap) { this.tenDangNhap = tenDangNhap; }

    public String getMatKhau() { return matKhau; }
    public void setMatKhau(String matKhau) { this.matKhau = matKhau; }

    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }

    public Timestamp getNgayTao() { return ngayTao; }
    public void setNgayTao(Timestamp ngayTao) { this.ngayTao = ngayTao; }
}