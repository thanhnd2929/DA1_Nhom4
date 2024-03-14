package fpoly.md18402.duan1_nhom4.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import fpoly.md18402.duan1_nhom4.Database.DbHelper;
import fpoly.md18402.duan1_nhom4.Model.Giay;
import fpoly.md18402.duan1_nhom4.Model.KhachHang;

public class KhachHangDAO {

    private DbHelper dbHelper;
    private SQLiteDatabase db;

    public KhachHangDAO(Context context) {
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long addKhachHang(KhachHang khachHang) {
        ContentValues values = new ContentValues();
        values.put("hoTen", khachHang.getHoTen());
        values.put("sdt", khachHang.getSdt());
        return db.insert("KhachHang", null, values);
    }

    public long updateKhachHang(KhachHang khachHang) {
        ContentValues values = new ContentValues();
        values.put("hoTen", khachHang.getHoTen());
        values.put("sdt", khachHang.getSdt());
        String[] dk = new String[]{String.valueOf(khachHang.getMaKH())};
        return db.update("KhachHang", values, "maKH=?", dk);
    }

    public int delete(String id) {
        return db.delete("KhachHang", "maKH=?", new String[]{id});
    }

    public List<KhachHang> getData(String sql, String...selectionArgs) {
        List<KhachHang> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql,selectionArgs);
        if(c.getCount()>0){
            c.moveToFirst();
            do {
                list.add(new KhachHang(
                        c.getInt(0),
                        c.getString(1),
                        c.getString(2)
                ));
            }while (c.moveToNext());
        }
        return list;
    }

    public List<KhachHang> getAll() {
        String sql = "SELECT * FROM KhachHang";
        return getData(sql);
    }

    public KhachHang getID(String id) {
        String sql = "SELECT * FROM KhachHang WHERE maKH=?";
        List<KhachHang> list = getData(sql, id);
        return list.get(0);
    }
}
