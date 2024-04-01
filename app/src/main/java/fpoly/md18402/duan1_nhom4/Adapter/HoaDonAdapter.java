package fpoly.md18402.duan1_nhom4.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import fpoly.md18402.duan1_nhom4.DAO.KhachHangDAO;
import fpoly.md18402.duan1_nhom4.Fragments.HoaDonFragment;
import fpoly.md18402.duan1_nhom4.Model.HoaDon;
import fpoly.md18402.duan1_nhom4.Model.KhachHang;
import fpoly.md18402.duan1_nhom4.R;

public class HoaDonAdapter extends ArrayAdapter {

    private Context context;
    private ArrayList<HoaDon> list;
    HoaDonFragment fragment;



    public HoaDonAdapter(@NonNull Context context, HoaDonFragment fragment, ArrayList<HoaDon> list) {
        super(context, 0, list);
        this.context = context;
        this.list = list;
        this.fragment = fragment;
        khachHangDAO = new KhachHangDAO(context);
    }

    TextView tvMaHD, tvMaNV, tvTenKH, tvThanhToan, tvNgay, tvSoHD;
    ImageView btnDelete;
    KhachHangDAO khachHangDAO;
    boolean isAdmin = false;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_hoadon, null);
        }

        final HoaDon item = list.get(position);

        if (item!=null) {
            tvMaHD = v.findViewById(R.id.tvMaHD);
            tvNgay = v.findViewById(R.id.tvNgay);
            tvMaNV = v.findViewById(R.id.tvMaNV);
            tvTenKH = v.findViewById(R.id.tvTenKH);
            tvSoHD = v.findViewById(R.id.tvSoHD);
            tvThanhToan = v.findViewById(R.id.tvThanhToan);
            btnDelete = v.findViewById(R.id.btn_delete);

            tvMaHD.setText(item.getMaHD()+"");
            tvMaNV.setText(item.getMaNV());
            tvSoHD.setText(item.getSoHD());

            khachHangDAO = new KhachHangDAO(context);
            KhachHang kh = khachHangDAO.getID(String.valueOf(item.getMaKH()));
            if (item!=null && kh != null) {
                tvTenKH.setText(kh.getHoTen());
            }

            try {
                tvNgay.setText("Ngày: "+sdf.format(item.getNgayMua()));
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (item.getThanhToan() == 0) {
                tvThanhToan.setText(" Tiền mặt");
            } else {
                tvThanhToan.setText(" Chuyển khoản");
            }

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences sharedPreferences = context.getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
                    String userDeleteRole = sharedPreferences.getString("USER", "");

                    if (userDeleteRole.equals("admin")) {
                        isAdmin = true;
//                        chưa làm nút xoá
                        fragment.xoa(String.valueOf(item.getMaHD()));
                    } else {
                        Toast.makeText(context, "Bạn không có quyền xoá hoá đơn", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        return v;

    }
}
