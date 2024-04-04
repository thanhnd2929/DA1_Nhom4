package fpoly.md18402.duan1_nhom4.Database;

public class Insert_Data {
    public static final String Insert_Loai_Giay = "INSERT INTO LoaiGiay(tenLoai, loaiHang)\n" +
            "Values ('Giày chạy bộ', 'Hàng xách tay'),\n" +
            "('Giày bóng rổ', 'Hàng like new');";

    public static final String Insert_Giay = "INSERT INTO Giay(tenGiay, moTa, giaMua, maLoai)\n" +
            "Values ('Puma Palero', 'Phù hợp với nam giới', 2000, 1),\n" +
            "('Puma Carina', 'Phù hợp với nữ giới', 3000, 2);";

    public static final String Insert_Khach_Hang = "INSERT INTO KhachHang(hoTen, sdt)\n" +
            "Values ('Nguyễn Nam', '0123456789'),\n" +
            "('Nguyễn Long', '0987654321');";

    public static final String Insert_Nhan_Vien = "INSERT INTO NhanVien(maNV, hoTen, matKhau, sdt)\n" +
            "Values ('nv01', 'Vũ Hùng', '12345', '0982345678'),\n" +
            "('nv02', 'Vũ Tiến', '12345', '0982345345');";

    public static final String Insert_Hoa_Don = "INSERT INTO HoaDon(ngayMua, thanhToan, maKH, maNV)\n" +
            "Values ('2024/09/03', 0, 1, 'nv01'),\n" +
            "('2024/09/04', 1, 2, 'nv01');";

    public static final String Insert_CTHD = "INSERT INTO CTHD(maHD, maGiay, soLuong, tongTien)\n" +
            "Values (1, 1, 2, 4000),\n" +
            "(2, 2, 1, 3000);";

}
