package fpoly.md18402.duan1_nhom4.Model;

public class TopNV {
    String maNV;
    int tongTien;

    public TopNV() {
    }

    public TopNV(String maNV, int tongTien) {
        this.maNV = maNV;
        this.tongTien = tongTien;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public int getTongTien() {
        return tongTien;
    }

    public void setTongTien(int tongTien) {
        this.tongTien = tongTien;
    }
}
