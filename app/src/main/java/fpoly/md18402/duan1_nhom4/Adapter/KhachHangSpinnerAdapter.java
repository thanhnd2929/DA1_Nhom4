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
import java.util.List;

import fpoly.md18402.duan1_nhom4.Model.KhachHang;
import fpoly.md18402.duan1_nhom4.R;

public class KhachHangSpinnerAdapter extends ArrayAdapter<KhachHang> {

    private Context context;
    private ArrayList<KhachHang> list;
    TextView tvMaKH, tvTenKH;

    public KhachHangSpinnerAdapter(@NonNull Context context, ArrayList<KhachHang> list) {
        super(context, 0, list);
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_spiner_khachhang, null);
        }
        final KhachHang item = list.get(position);
        if (item != null) {
            tvMaKH = v.findViewById(R.id.tvMaKH_spinner);
            tvMaKH.setText(item.getMaKH() + ". ");
            tvTenKH = v.findViewById(R.id.tvTenKH_spinner);
            tvTenKH.setText(item.getHoTen());
        }
        return v;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_spiner_khachhang, null);
        }
        KhachHang item = list.get(position);
        if (item != null) {
            tvMaKH = v.findViewById(R.id.tvMaKH_spinner);
            tvMaKH.setText(item.getMaKH() + ". ");
            tvTenKH = v.findViewById(R.id.tvTenKH_spinner);
            tvTenKH.setText(item.getHoTen());
        }
        return v;
    }
}
