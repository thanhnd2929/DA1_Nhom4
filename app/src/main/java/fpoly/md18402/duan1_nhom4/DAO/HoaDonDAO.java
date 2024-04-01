package fpoly.md18402.duan1_nhom4.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import fpoly.md18402.duan1_nhom4.Database.DbHelper;
import fpoly.md18402.duan1_nhom4.Model.HoaDon;
import fpoly.md18402.duan1_nhom4.Model.NhanVien;

public class HoaDonDAO {
    private SQLiteDatabase db;
    private Context context;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

    public HoaDonDAO(Context context) {
        this.context = context;
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long addHoaDon(HoaDon hoaDon) {
        ContentValues values = new ContentValues();
        values.put("ngayMua", sdf.format(hoaDon.getNgayMua()));
        values.put("thanhToan", hoaDon.getThanhToan());
        values.put("maKH", hoaDon.getMaKH());
        values.put("maNV", hoaDon.getMaNV());
        values.put("soHD", hoaDon.getSoHD());
        return db.insert("HoaDon", null, values);
    }

    public long updateHoaDon(HoaDon hoaDon) {
        ContentValues values = new ContentValues();
        values.put("ngayMua", sdf.format(hoaDon.getNgayMua()));
        values.put("thanhToan", hoaDon.getThanhToan());
        values.put("maKH", hoaDon.getMaKH());
        values.put("maNV", hoaDon.getMaNV());
        values.put("soHD", hoaDon.getSoHD());
        String[] dk = new String[]{String.valueOf(hoaDon.getMaHD())};
        return db.update("HoaDon", values, "maHD=?", dk);
    }

    public int delete(String id) {
        return db.delete("HoaDon", "maHD=?", new String[]{id});
    }


    @SuppressLint("Range")
    public List<HoaDon> getData(String sql, String... selectionArgs) {
        List<HoaDon> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        while (cursor.moveToNext()) {
            HoaDon obj = new HoaDon();
            obj.setMaHD(Integer.parseInt(cursor.getString(cursor.getColumnIndex("maHD"))));
            obj.setMaKH(Integer.parseInt(cursor.getString(cursor.getColumnIndex("maKH"))));
            obj.setMaNV(cursor.getString(cursor.getColumnIndex("maNV")));
            obj.setSoHD(cursor.getString(cursor.getColumnIndex("soHD")));
            try {
                obj.setNgayMua(sdf.parse(cursor.getString(cursor.getColumnIndex("ngayMua"))));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            obj.setThanhToan(Integer.parseInt(cursor.getString(cursor.getColumnIndex("thanhToan"))));
            list.add(obj);
        }
        return list;
    }

    public List<HoaDon> getAll() {
        String sql = "SELECT * FROM HoaDon";
        return getData(sql);
    }

    public HoaDon getID(String id) {
        String sql = "SELECT * FROM HoaDon WHERE maHD=?";
        List<HoaDon> list = getData(sql, id);
        return list.get(0);
    }




}
