package DTO; // ĐÃ SỬA: Viết hoa

public class TheLoaiDTO {

    private int maID;
    private String maTL;
    private String tenLoai;
    private int maDanhMuc;
    private String tenDanhMuc;

    // ĐÃ THÊM: Các hàm khởi tạo (Constructor)
    public TheLoaiDTO() {
    }

    public TheLoaiDTO(int maID, String maTL, String tenLoai, int maDanhMuc) {
        this.maID = maID;
        this.maTL = maTL;
        this.tenLoai = tenLoai;
        this.maDanhMuc = maDanhMuc;
    }

    public int getMaID() {
        return maID;
    }

    public void setMaID(int maID) {
        this.maID = maID;
    }

    public String getMaTL() {
        return maTL;
    }

    public void setMaTL(String maTL) {
        this.maTL = maTL;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }

    public int getMaDanhMuc() {
        return maDanhMuc;
    }

    public void setMaDanhMuc(int maDanhMuc) {
        this.maDanhMuc = maDanhMuc;
    }

    @Override
    public String toString(){
        return tenLoai;
    }
    public String getTenDanhMuc() {
        return tenDanhMuc;
    }

    public void setTenDanhMuc(String tenDanhMuc) {
        this.tenDanhMuc = tenDanhMuc;
    }
}