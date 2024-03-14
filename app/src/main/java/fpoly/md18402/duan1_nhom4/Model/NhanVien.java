package fpoly.md18402.duan1_nhom4.Model;

public class NhanVien {
    private String maNV;
    private String hoTen;
    private String matKhau;
    private String sdt;

    public NhanVien() {
    }

    public NhanVien(String maNV, String hoTen, String matKhau, String sdt) {
        this.maNV = maNV;
        this.hoTen = hoTen;
        this.matKhau = matKhau;
        this.sdt = sdt;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }
}
