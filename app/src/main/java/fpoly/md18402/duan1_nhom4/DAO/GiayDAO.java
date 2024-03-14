package fpoly.md18402.duan1_nhom4.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import fpoly.md18402.duan1_nhom4.Database.DbHelper;
import fpoly.md18402.duan1_nhom4.Model.Giay;
import fpoly.md18402.duan1_nhom4.Model.LoaiGiay;

public class GiayDAO {
    private DbHelper dbHelper;
    private SQLiteDatabase db;

    public GiayDAO(Context context) {
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long addGiay(Giay giay) {
        ContentValues values = new ContentValues();
        values.put("tenGiay", giay.getTenGiay());
        values.put("moTa", giay.getMoTa());
        values.put("giaMua", giay.getGiaMua());
        values.put("maLoai", giay.getMaLoai());
        return db.insert("Giay", null, values);
    }

    public long updateGiay(Giay giay) {
        ContentValues values = new ContentValues();
        values.put("tenGiay", giay.getTenGiay());
        values.put("moTa", giay.getMoTa());
        values.put("giaMua", giay.getGiaMua());
        values.put("maLoai", giay.getMaLoai());
        String[] dk = new String[]{String.valueOf(giay.getMaGiay())};
        return db.update("Giay", values, "maGiay=?", dk);
    }

    public int delete(String id) {
        return db.delete("Giay", "maGiay=?", new String[]{id});
    }

    public List<Giay> getData(String sql, String...selectionArgs) {
        List<Giay> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql,selectionArgs);
        if(c.getCount()>0){
            c.moveToFirst();
            do {
                list.add(new Giay(
                        c.getInt(0),
                        c.getString(1),
                        c.getString(2),
                        c.getInt(3),
                        c.getInt(4)
                ));
            }while (c.moveToNext());
        }
        return list;
    }

    public List<Giay> getAll() {
        String sql = "SELECT * FROM Giay";
        return getData(sql);
    }

    public Giay getID(String id) {
        String sql = "SELECT * FROM Giay WHERE maGiay=?";
        List<Giay> list = getData(sql, id);
        return list.get(0);
    }


}
