package DTO; // ĐÃ SỬA: Viết hoa chữ DTO cho đồng bộ với hệ thống

import java.util.Date;

public class TacGiaDTO {

    private int maID;
    private String maTG;
    private String tenTacGia;
    private Date ngaySinh;
    private String quocTich;
    private Date ngayMat;

    // ĐÃ THÊM: Constructor rỗng và đầy đủ tham số cho chuẩn form DTO
    public TacGiaDTO() {
    }

    public TacGiaDTO(int maID, String maTG, String tenTacGia, Date ngaySinh, Date ngayMat, String quocTich) {
        this.maID = maID;
        this.maTG = maTG;
        this.tenTacGia = tenTacGia;
        this.ngaySinh = ngaySinh;
        this.ngayMat = ngayMat;
        this.quocTich = quocTich;
    }

    public int getMaID() {
        return maID;
    }

    public void setMaID(int maID) {
        this.maID = maID;
    }

    public String getMaTG() {
        return maTG;
    }

    public void setMaTG(String maTG) {
        this.maTG = maTG;
    }

    public String getTenTacGia() {
        return tenTacGia;
    }

    public void setTenTacGia(String tenTacGia) {
        this.tenTacGia = tenTacGia;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getQuocTich() {
        return quocTich;
    }

    public void setQuocTich(String quocTich) {
        this.quocTich = quocTich;
    }

    @Override
    public String toString(){
        return tenTacGia;
    }
    public Date getNgayMat() {
        return ngayMat;
    }

    public void setNgayMat(Date ngayMat) {
        this.ngayMat = ngayMat;
    }
}