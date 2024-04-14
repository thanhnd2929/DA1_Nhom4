package fpoly.md18402.duan1_nhom4.DAO;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import fpoly.md18402.duan1_nhom4.Database.DbHelper;
import fpoly.md18402.duan1_nhom4.Model.Top;
import fpoly.md18402.duan1_nhom4.Model.TopNV;
import fpoly.md18402.duan1_nhom4.Model.TopNvHD;

public class ThongKeDAO {
    private SQLiteDatabase db;
    private Context context;
    DbHelper dbHelper;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");

    public ThongKeDAO(Context context) {
        this.context = context;
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

//    top 10 mặt hàng bán chạy nhất
    @SuppressLint("Range")
    public List<Top> getTop() {
        String sqlTop = "SELECT Giay.tenGiay,Giay.maGiay, SUM(CTHD.soLuong) AS soLuongBan\n" +
                "FROM CTHD\n" +
                "JOIN Giay ON CTHD.maGiay = Giay.maGiay\n" +
                "GROUP BY CTHD.maGiay, Giay.tenGiay\n" +
                "ORDER BY soLuongBan DESC\n" +
                "LIMIT 10;";
        List<Top> list = new ArrayList<Top>();
        Cursor c = db.rawQuery(sqlTop, null);
        while (c.moveToNext()) {
            Top top = new Top();
            top.setMaGiay(c.getString(c.getColumnIndex("maGiay")));
            top.setSoLuong(Integer.parseInt(c.getString(c.getColumnIndex("soLuongBan"))));
            list.add(top);
        }
        return list;
    }

    // thống kê doanh thu
    @SuppressLint("Range")
    public int getDoanhThu(String tuNgay, String denNgay) {
        String sqlDoanhThu = "SELECT SUM(tongTien * soLuong) as doanhThu FROM CTHD INNER JOIN HoaDon ON CTHD.maHD = HoaDon.maHD WHERE HoaDon.ngayMua BETWEEN ? AND ?";
        List<Integer> list = new ArrayList<Integer>();
        Cursor cursor = db.rawQuery(sqlDoanhThu, new String[]{tuNgay, denNgay});
        while (cursor.moveToNext()) {
            try {
                list.add(Integer.parseInt(cursor.getString(cursor.getColumnIndex("doanhThu"))));
            } catch (Exception e) {
                list.add(0);
            }
        }
        return list.get(0);
    }


    @SuppressLint("Range")
    public List<TopNV> getTop10NhanVienDoanhThu() {

        String query = "SELECT NhanVien.maNV, SUM(CTHD.tongTien * CTHD.soLuong) AS tienBan " +
                "FROM NhanVien " +
                "INNER JOIN HoaDon ON NhanVien.maNV = HoaDon.maNV " +
                "INNER JOIN CTHD ON HoaDon.maHD = CTHD.maHD " +
                "GROUP BY NhanVien.maNV " +
                "ORDER BY tienBan DESC " +
                "LIMIT 10";
        List<TopNV> list = new ArrayList<TopNV>();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            TopNV top = new TopNV();
            top.setMaNV(cursor.getString(cursor.getColumnIndex("maNV")));
            top.setTongTien(Integer.parseInt(cursor.getString(cursor.getColumnIndex("tienBan"))));
            list.add(top);
        }
        return list;
    }


    @SuppressLint("Range")
    public List<TopNV> getTop10NhanVienDoanhThuTheoNgay(String fromDate, String toDate) {
        String query = "SELECT NhanVien.maNV, SUM(CTHD.tongTien * CTHD.soLuong) AS tienBan " +
                "FROM NhanVien " +
                "INNER JOIN HoaDon ON NhanVien.maNV = HoaDon.maNV " +
                "INNER JOIN CTHD ON HoaDon.maHD = CTHD.maHD " +
                "WHERE HoaDon.ngayMua BETWEEN ? AND ? " + // Thêm điều kiện khoảng thời gian vào câu truy vấn
                "GROUP BY NhanVien.maNV " +
                "ORDER BY tienBan DESC " +
                "LIMIT 10";
        List<TopNV> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(query, new String[]{fromDate, toDate});
        while (cursor.moveToNext()) {
            TopNV top = new TopNV();
            top.setMaNV(cursor.getString(cursor.getColumnIndex("maNV")));
            top.setTongTien(Integer.parseInt(cursor.getString(cursor.getColumnIndex("tienBan"))));
            list.add(top);
        }
        return list;
    }

    @SuppressLint("Range")
    public List<TopNvHD> getTop10NhanVienBySoLuongHD(String fromDate, String toDate) {
        String query = "SELECT maNV, COUNT(maHD) AS soLuongHD " +
                "FROM HoaDon " +
                "WHERE ngayMua BETWEEN ? AND ? " +
                "GROUP BY maNV " +
                "ORDER BY soLuongHD DESC " +
                "LIMIT 10";

        List<TopNvHD> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(query, new String[]{fromDate, toDate});

        while (cursor.moveToNext()) {
            TopNvHD topNvHD = new TopNvHD();

            topNvHD.setMaNV(cursor.getString(cursor.getColumnIndex("maNV")));
            topNvHD.setSoLuongHD(cursor.getInt(cursor.getColumnIndex("soLuongHD")));

            list.add(topNvHD);
        }

        cursor.close();
        return list;
    }



}
