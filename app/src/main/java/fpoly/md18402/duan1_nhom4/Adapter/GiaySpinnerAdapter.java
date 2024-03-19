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

import fpoly.md18402.duan1_nhom4.Model.Giay;
import fpoly.md18402.duan1_nhom4.R;

public class GiaySpinnerAdapter extends ArrayAdapter<Giay> {
    private Context context;
    private ArrayList<Giay> list;
    TextView tvMaGiay, tvTenGiay;

    public GiaySpinnerAdapter(@NonNull Context context, ArrayList<Giay> list) {
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
            v = inflater.inflate(R.layout.item_spinner_giay_hdct, null);
        }
        final Giay item = list.get(position);
        if (item != null) {
            tvMaGiay = v.findViewById(R.id.tvMaGiay_spinner);
            tvTenGiay = v.findViewById(R.id.tvTenGiay_spinner);
            tvMaGiay.setText(item.getMaGiay()+". ");
            tvTenGiay.setText(item.getTenGiay());
        }
        return v;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_spinner_giay_hdct, null);
        }
        Giay item = list.get(position);
        if (item != null) {
            tvMaGiay = view.findViewById(R.id.tvMaGiay_spinner);
            tvTenGiay = view.findViewById(R.id.tvTenGiay_spinner);
            tvMaGiay.setText(item.getMaGiay()+". ");
            tvTenGiay.setText(item.getTenGiay());
        }
        return view;
    }
}
