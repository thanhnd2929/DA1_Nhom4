package fpoly.md18402.duan1_nhom4.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import fpoly.md18402.duan1_nhom4.Database.DbHelper;
import fpoly.md18402.duan1_nhom4.Model.CTHD;
import fpoly.md18402.duan1_nhom4.Model.HoaDon;
import fpoly.md18402.duan1_nhom4.Model.NhanVien;

public class CTHoaDonDAO {
    private DbHelper dbHelper;
    private SQLiteDatabase db;

    public CTHoaDonDAO(Context context) {
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long addCTHD(CTHD cthd) {
        ContentValues values = new ContentValues();

        values.put("maHD", cthd.getMaHD());
        values.put("maGiay", cthd.getMaGiay());
        values.put("soLuong", cthd.getSoLuong());
        values.put("tongTien", cthd.getGiaMua());
        return db.insert("CTHD", null, values);
    }

    public long updateCTHD(CTHD cthd) {
        ContentValues values = new ContentValues();
        values.put("maHD", cthd.getMaHD());
        values.put("maGiay", cthd.getMaGiay());
        values.put("soLuong", cthd.getSoLuong());
        values.put("tongTien", cthd.getGiaMua());
        String[] dk = new String[]{String.valueOf(cthd.getMaCTHD())};
        return db.update("CTHD", values,"maCTHD=?", dk);
    }

    public int delete(String id) {
        return db.delete("CTHD", "maCTHD=?", new String[]{id});
    }

    private List<CTHD> getData(String sql, String... selectionArgs) {
        List<CTHD> list = new ArrayList<CTHD>();
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        while (cursor.moveToNext()) {
            CTHD hdct = new CTHD();
            hdct.setMaCTHD(cursor.getInt(0));
            hdct.setMaHD(cursor.getInt(1));
            hdct.setMaGiay(cursor.getInt(2));
            hdct.setSoLuong(cursor.getInt(3));
            hdct.setGiaMua(cursor.getInt(4));

            list.add(hdct);
        }
        return list;
    }

    public List<CTHD> getAll(int maHD) {
        String sql = "SELECT * FROM CTHD WHERE maHD="+maHD;
        return getData(sql);
    }

    public CTHD getID(String id) {
        String sql = "SELECT * FROM CTHD WHERE maCTHD=?";
        List<CTHD> list = getData(sql, id);
        return list.get(0);
    }

}
