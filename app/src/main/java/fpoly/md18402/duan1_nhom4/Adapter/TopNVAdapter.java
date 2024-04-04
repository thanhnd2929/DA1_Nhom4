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

import fpoly.md18402.duan1_nhom4.Fragments.TopNvFragment;
import fpoly.md18402.duan1_nhom4.Model.TopNV;
import fpoly.md18402.duan1_nhom4.R;

public class TopNVAdapter extends ArrayAdapter<TopNV> {
    Context context;
    TopNvFragment topNvFragment;
    ArrayList<TopNV> list;
    TextView tvTenNV, tvDoanhThu;

    public TopNVAdapter(@NonNull Context context,  ArrayList<TopNV> list) {
        super(context, 0, list);
        this.context = context;
//        this.topNvFragment = topNvFragment;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_topnv, null);
        }
        final  TopNV item = list.get(position);
        if (item != null) {
            tvTenNV = v.findViewById(R.id.tvTenTopNV);
            tvDoanhThu = v.findViewById(R.id.tvDoanhThuTopNV);

            tvTenNV.setText(item.getMaNV());
            tvDoanhThu.setText(item.getTongTien()+" VND");
        }

        return v;
    }
}
