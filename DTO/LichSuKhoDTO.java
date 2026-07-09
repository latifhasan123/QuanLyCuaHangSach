package DTO;

import java.time.LocalDateTime;

public class LichSuKhoDTO {
    private int maID;
    private String maLSK; // Ví dụ: LSK000001
    private int maSachID;
    private String maSach_Chu; // Ví dụ: S000001
    private String tenSach;
    private String loaiGiaoDich;
    private int maChungTu;
    private int soLuongThayDoi;
    private LocalDateTime ngayGioTao;
    private String ghiChu;

    public LichSuKhoDTO() {}

    public int getMaID() { return maID; }
    public void setMaID(int maID) { this.maID = maID; }

    public String getMaLSK() { return maLSK; }
    public void setMaLSK(String maLSK) { this.maLSK = maLSK; }

    public int getMaSachID() { return maSachID; }
    public void setMaSachID(int maSachID) { this.maSachID = maSachID; }

    public String getMaSach_Chu() { return maSach_Chu; }
    public void setMaSach_Chu(String maSach_Chu) { this.maSach_Chu = maSach_Chu; }

    public String getTenSach() { return tenSach; }
    public void setTenSach(String tenSach) { this.tenSach = tenSach; }

    public String getLoaiGiaoDich() { return loaiGiaoDich; }
    public void setLoaiGiaoDich(String loaiGiaoDich) { this.loaiGiaoDich = loaiGiaoDich; }

    public int getMaChungTu() { return maChungTu; }
    public void setMaChungTu(int maChungTu) { this.maChungTu = maChungTu; }

    public int getSoLuongThayDoi() { return soLuongThayDoi; }
    public void setSoLuongThayDoi(int soLuongThayDoi) { this.soLuongThayDoi = soLuongThayDoi; }

    public LocalDateTime getNgayGioTao() { return ngayGioTao; }
    public void setNgayGioTao(LocalDateTime ngayGioTao) { this.ngayGioTao = ngayGioTao; }

    public String getGhiChu() { return ghiChu; }
    public void setGhiChu(String ghiChu) { this.ghiChu = ghiChu; }
}