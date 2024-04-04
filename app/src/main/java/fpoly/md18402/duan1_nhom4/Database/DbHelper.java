package fpoly.md18402.duan1_nhom4.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "NHOM4";
    public static final int DB_VERSION = 12;

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

//        tạo bảng loại giày
        String createTableLoaiGiay = "CREATE TABLE LoaiGiay (\n" +
                "    maLoai   INTEGER PRIMARY KEY AUTOINCREMENT\n" +
                "                     NOT NULL,\n" +
                "    tenLoai  TEXT    NOT NULL,\n" +
                "    loaiHang TEXT    NOT NULL\n" +
                ");\n";
        db.execSQL(createTableLoaiGiay);

//        tạo bảng giày
        String createTableGiay = "CREATE TABLE Giay (\n" +
                "    maGiay  INTEGER PRIMARY KEY AUTOINCREMENT\n" +
                "                    NOT NULL,\n" +
                "    tenGiay TEXT    NOT NULL,\n" +
                "    moTa    TEXT    NOT NULL,\n" +
                "    giaMua  INTEGER NOT NULL,\n" +
                "    maLoai  INTEGER REFERENCES LoaiGiay (maLoai) \n" +
                ");";
        db.execSQL(createTableGiay);

//        taọ bảng khách hàng
        String createTableKhachHang = "CREATE TABLE KhachHang (\n" +
                "    maKH  INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    hoTen TEXT    NOT NULL,\n" +
                "    sdt   TEXT NOT NULL\n" +
                ");\n";
        db.execSQL(createTableKhachHang);

//        tạo bảng nhân viên
        String createTableNhanVien = "CREATE TABLE NhanVien (\n" +
                "    maNV    TEXT    PRIMARY KEY \n" +
                "                    NOT NULL,\n" +
                "    hoTen   TEXT    NOT NULL,\n" +
                "    matKhau TEXT    NOT NULL,\n" +
                "    sdt     TEXT NOT NULL\n" +
                ");";
        db.execSQL(createTableNhanVien);

//        tạo bảng hoá đơn
        String createTableHoaDon = "CREATE TABLE HoaDon (\n" +
                "    maHD      INTEGER PRIMARY KEY AUTOINCREMENT\n" +
                "                      NOT NULL,\n" +
                "    ngayMua   DATE    NOT NULL,\n" +
                "    thanhToan INTEGER NOT NULL,\n" +
                "    maKH      INTEGER NOT NULL\n" +
                "                      REFERENCES KhachHang (maKH),\n" +
                "    maNV      TEXT    REFERENCES NhanVien (maNV) \n" +
                "                      NOT NULL,\n" +
                "    soHD TEXT NOT NULL\n" +
                ");\n";
        db.execSQL(createTableHoaDon);

//        tạo bảng chi tiết hoá đơn
        String createTableCTHD = "CREATE TABLE CTHD (\n" +
                "    maCTHD   INTEGER PRIMARY KEY AUTOINCREMENT\n" +
                "                     NOT NULL,\n" +
                "    maHD     INTEGER NOT NULL\n" +
                "                     REFERENCES HoaDon (maHD),\n" +
                "    maGiay   INTEGER NOT NULL\n" +
                "                     REFERENCES Giay (maGiay),\n" +
                "    soLuong  INTEGER NOT NULL,\n" +
                "    tongTien INTEGER NOT NULL\n" +
                ");\n";
        db.execSQL(createTableCTHD);

//        Thêm dữ liệu

        db.execSQL(Insert_Data.Insert_Loai_Giay);
        db.execSQL(Insert_Data.Insert_Giay);
        db.execSQL(Insert_Data.Insert_Khach_Hang);
        db.execSQL(Insert_Data.Insert_Nhan_Vien);
//        db.execSQL(Insert_Data.Insert_Hoa_Don);
//        db.execSQL(Insert_Data.Insert_CTHD);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String dropTableLoaiGiay = "drop table if exists LoaiGiay";
        db.execSQL(dropTableLoaiGiay);

        String dropTableGiay = "drop table if exists Giay";
        db.execSQL(dropTableGiay);

        String dropTableKhachHang = "drop table if exists KhachHang";
        db.execSQL(dropTableKhachHang);

        String dropTableNhanVien = "drop table if exists NhanVien";
        db.execSQL(dropTableNhanVien);

        String dropTableHoaDon = "drop table if exists HoaDon";
        db.execSQL(dropTableHoaDon);

        String dropTableCTHD = "drop table if exists CTHD";
        db.execSQL(dropTableCTHD);

        onCreate(db);
    }
}
