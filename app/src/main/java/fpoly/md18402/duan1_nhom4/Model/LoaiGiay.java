package fpoly.md18402.duan1_nhom4.Model;

public class LoaiGiay {
    private int maLoai;
    private String tenLoai;
    private String loaiHang;

    public LoaiGiay() {
    }

    public LoaiGiay(int maLoai, String tenLoai, String loaiHang) {
        this.maLoai = maLoai;
        this.tenLoai = tenLoai;
        this.loaiHang = loaiHang;
    }

    public LoaiGiay(String tenLoai, String loaiHang) {
        this.tenLoai = tenLoai;
        this.loaiHang = loaiHang;
    }

    public int getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(int maLoai) {
        this.maLoai = maLoai;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }

    public String getLoaiHang() {
        return loaiHang;
    }

    public void setLoaiHang(String loaiHang) {
        this.loaiHang = loaiHang;
    }
}
