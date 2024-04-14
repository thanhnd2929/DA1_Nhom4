package fpoly.md18402.duan1_nhom4.Model;

public class TopNvHD {
    String maNV;
    int soLuongHD;

    public TopNvHD() {
    }

    public TopNvHD(String maNV, int soLuongHD) {
        this.maNV = maNV;
        this.soLuongHD = soLuongHD;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public int getSoLuongHD() {
        return soLuongHD;
    }

    public void setSoLuongHD(int soLuongHD) {
        this.soLuongHD = soLuongHD;
    }
}
