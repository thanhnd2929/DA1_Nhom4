package fpoly.md18402.duan1_nhom4.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import fpoly.md18402.duan1_nhom4.Database.DbHelper;
import fpoly.md18402.duan1_nhom4.Model.Giay;
import fpoly.md18402.duan1_nhom4.Model.NhanVien;

public class NhanVienDAO {
    private DbHelper dbHelper;
    private SQLiteDatabase db;

    public NhanVienDAO(Context context) {
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long addNhanVien(NhanVien nhanVien) {
        ContentValues values = new ContentValues();
        values.put("maNV", nhanVien.getMaNV());
        values.put("hoTen", nhanVien.getHoTen());
        values.put("matKhau", nhanVien.getMatKhau());
        values.put("sdt", nhanVien.getSdt());
        return  db.insert("NhanVien", null, values);
    }

    public long updateNhanVien(NhanVien nhanVien) {
        ContentValues values = new ContentValues();
        values.put("maNV", nhanVien.getMaNV());
        values.put("hoTen", nhanVien.getHoTen());
        values.put("matKhau", nhanVien.getMatKhau());
        values.put("sdt", nhanVien.getSdt());
        String[] dk = new String[]{String.valueOf(nhanVien.getMaNV())};
        return  db.update("NhanVien", values, "maNV=?", dk);
    }

    public int delete(String id) {
        return db.delete("NhanVien", "maNV=?", new String[]{id});
    }

    public List<NhanVien> getData(String sql, String...selectionArgs) {
        List<NhanVien> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql,selectionArgs);
        if(c.getCount()>0){
            c.moveToFirst();
            do {
                list.add(new NhanVien(
                        c.getString(0),
                        c.getString(1),
                        c.getString(2),
                        c.getString(3)
                ));
            }while (c.moveToNext());
        }
        return list;
    }

    public List<NhanVien> getAll() {
        String sql = "SELECT * FROM NhanVien";
        return getData(sql);
    }

    public NhanVien getID(String id) {
        String sql = "SELECT * FROM NhanVien WHERE maNV=?";
        List<NhanVien> list = getData(sql, id);
        return list.get(0);
    }

    public int CheckLogin(String id,String password){
        String sql = "SELECT * FROM NhanVien WHERE maNV=? and matKhau=?";
        List<NhanVien> list=getData(sql,id,password);
        if (list.size()==0){
            return -1;
        }return 1;
    }
}
