package DTO;

public class DanhMucDTO {
    private int maID;
    private String maDanhMuc;
    private String tenDanhMuc;

    public DanhMucDTO() {}

    public DanhMucDTO(int maID, String maDanhMuc, String tenDanhMuc) {
        this.maID = maID;
        this.maDanhMuc = maDanhMuc;
        this.tenDanhMuc = tenDanhMuc;
    }

    public int getMaID() { return maID; }
    public void setMaID(int maID) { this.maID = maID; }

    public String getMaDanhMuc() { return maDanhMuc; }
    public void setMaDanhMuc(String maDanhMuc) { this.maDanhMuc = maDanhMuc; }

    public String getTenDanhMuc() { return tenDanhMuc; }
    public void setTenDanhMuc(String tenDanhMuc) { this.tenDanhMuc = tenDanhMuc; }

    @Override
    public String toString() { return tenDanhMuc; }
}