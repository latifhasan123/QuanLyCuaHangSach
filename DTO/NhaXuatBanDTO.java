package DTO; // ĐÃ SỬA: Viết hoa

public class NhaXuatBanDTO {

    private int maID;
    private String maNXB;
    private String tenNXB;
    private String diaChi;

    // ĐÃ THÊM: Các hàm khởi tạo (Constructor rỗng và đầy đủ)
    public NhaXuatBanDTO() {
    }

    public NhaXuatBanDTO(int maID, String maNXB, String tenNXB, String diaChi) {
        this.maID = maID;
        this.maNXB = maNXB;
        this.tenNXB = tenNXB;
        this.diaChi = diaChi;
    }

    public int getMaID() {
        return maID;
    }

    public void setMaID(int maID) {
        this.maID = maID;
    }

    public String getMaNXB() {
        return maNXB;
    }

    public void setMaNXB(String maNXB) {
        this.maNXB = maNXB;
    }

    public String getTenNXB() {
        return tenNXB;
    }

    public void setTenNXB(String tenNXB) {
        this.tenNXB = tenNXB;
    }
    
    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }
    
    @Override
    public String toString(){
        return tenNXB;
    }
}