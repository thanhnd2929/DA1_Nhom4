package fpoly.md18402.duan1_nhom4.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import java.util.ArrayList;

import fpoly.md18402.duan1_nhom4.Model.NhanVien;
import fpoly.md18402.duan1_nhom4.R;

public class NhanVienSpinerAdapter extends ArrayAdapter<NhanVien> {
    private Context context;
    private ArrayList<NhanVien> list;
    TextView tvMaNv;
    public NhanVienSpinerAdapter(@NonNull Context context, ArrayList<NhanVien> list) {
        super(context, 0,list);
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_spiner_nhanvien, null);
        }
        final NhanVien item = list.get(position);
        if (item != null) {
            tvMaNv = v.findViewById(R.id.tvMaNv_spinner);
            tvMaNv.setText(item.getHoTen());
        }
        return v;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_spiner_nhanvien, null);
        }
        NhanVien item = list.get(position);
        if (item != null) {
            tvMaNv = v.findViewById(R.id.tvMaNv_spinner);
            tvMaNv.setText(item.getHoTen());
        }
        return v;
    }
}
