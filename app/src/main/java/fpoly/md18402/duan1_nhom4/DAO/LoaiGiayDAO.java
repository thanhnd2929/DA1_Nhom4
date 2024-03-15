package fpoly.md18402.duan1_nhom4.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import fpoly.md18402.duan1_nhom4.Database.DbHelper;
import fpoly.md18402.duan1_nhom4.Model.LoaiGiay;

public class LoaiGiayDAO {
    private DbHelper dbHelper;
    private SQLiteDatabase db;

    public LoaiGiayDAO(Context context) {
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public LoaiGiay getByName(String name) {
        String sql = "SELECT * FROM LoaiGiay WHERE tenLoai=?";
        List<LoaiGiay> list = getData(sql, name);
        return list.get(0);
    }

    public long addLoaiGiay(LoaiGiay loaiGiay) {
        ContentValues values = new ContentValues();
        values.put("tenLoai", loaiGiay.getTenLoai());
        values.put("loaiHang", loaiGiay.getLoaiHang());
        return db.insert("LoaiGiay", null, values);
    }

    public long updateLoaiGiay(LoaiGiay loaiGiay) {
        ContentValues values = new ContentValues();
        values.put("tenLoai", loaiGiay.getTenLoai());
        values.put("loaiHang", loaiGiay.getLoaiHang());
        String[] dk = new String[]{String.valueOf(loaiGiay.getMaLoai())};
        return db.update("LoaiGiay", values, "maLoai=?", dk);
    }

    public int delete(String id) {
        return db.delete("LoaiGiay", "maLoai=?", new String[]{id});
    }

    public List<LoaiGiay> getData(String sql, String... selectionArgs) {
        List<LoaiGiay> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql, selectionArgs);
        if (c.getCount() > 0) {
            c.moveToFirst();
            do {
                list.add(new LoaiGiay(
                        c.getInt(0),
                        c.getString(1),
                        c.getString(2))
                );
            } while (c.moveToNext());
        }
        return list;
    }

    public List<LoaiGiay> getAll() {
        String sql = "SELECT * FROM LoaiGiay";
        return getData(sql);
    }

    public LoaiGiay getID(String id) {
        String sql = "SELECT * FROM LoaiGiay WHERE maLoai=?";
        List<LoaiGiay> list = getData(sql, id);
        return list.get(0);
    }
}
