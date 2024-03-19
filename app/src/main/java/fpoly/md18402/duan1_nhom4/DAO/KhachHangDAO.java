package fpoly.md18402.duan1_nhom4.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import fpoly.md18402.duan1_nhom4.Database.DbHelper;
import fpoly.md18402.duan1_nhom4.Model.KhachHang;

public class KhachHangDAO {

    private DbHelper dbHelper;


    public KhachHangDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    public ArrayList<KhachHang> getAll() {
        ArrayList<KhachHang> listTv = new ArrayList<KhachHang>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery("SELECT * FROM KhachHang", null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    KhachHang kh = new KhachHang();
                    kh.setMaKH(cursor.getInt(0));
                    kh.setHoTen(cursor.getString(1));
                    kh.setSdt(cursor.getString(2));
                    listTv.add(kh);
                    cursor.moveToNext();
                }
            }
        } catch (Exception e) {
            Log.e("zzzzzzzzzzzzzzzzzzzz", "Lỗiiiiii");
        }
        return listTv;
    }

    public boolean insertKh(KhachHang tv) {
        if (tv == null) return false; // Kiểm tra đối tượng tv không null
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("hoTen", tv.getHoTen());
        values.put("sdt", tv.getSdt());
        long row = db.insert("KhachHang", null, values);
        return (row > 0);
    }

    public boolean updateKh(KhachHang kh) {
        if (kh == null) return false; // Kiểm tra đối tượng kh không null
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("hoTen", kh.getHoTen());
        values.put("sdt", kh.getSdt());
        long row = db.update("KhachHang", values, "maKh=?", new String[]{String.valueOf(kh.getMaKH())});
        return (row > 0);
    }

    public boolean deleteKh(int maKh) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long row = db.delete("KhachHang", "maKh=?", new String[]{String.valueOf(maKh)});
        return (row > 0);
    }

    private List<KhachHang> getData(String sql, String... selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        List<KhachHang> list = new ArrayList<KhachHang>();
        Cursor c = db.rawQuery(sql, selectionArgs);
        while (c.moveToNext()) {
            KhachHang obj = new KhachHang();
            obj.setMaKH(c.getInt(0));
            obj.setHoTen(c.getString(1));
            obj.setSdt(c.getString(2));
            list.add(obj);
        }
        return list;
    }

    public KhachHang getID(String id) {
        String sql = "select * from KhachHang where maKh=?";
        List<KhachHang> list = getData(sql, id);
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }
}

