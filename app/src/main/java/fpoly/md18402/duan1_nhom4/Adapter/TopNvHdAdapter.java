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

import fpoly.md18402.duan1_nhom4.Fragments.TopNvHdFragment;
import fpoly.md18402.duan1_nhom4.Model.TopNvHD;
import fpoly.md18402.duan1_nhom4.R;

public class TopNvHdAdapter extends ArrayAdapter<TopNvHD> {
    Context context;
    TopNvHdFragment topNvHdFragment;
    ArrayList<TopNvHD> list;
    TextView tvTenNV, tvSoLuongHD;

    public TopNvHdAdapter(@NonNull Context context, ArrayList<TopNvHD> list) {
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
            v = inflater.inflate(R.layout.item_top_nv_hd, null);
        }

        final  TopNvHD item = list.get(position);
        if (item != null) {
            tvTenNV = v.findViewById(R.id.tvTenTopNvHD);
            tvSoLuongHD = v.findViewById(R.id.tvSoLuongHD);

            tvTenNV.setText(item.getMaNV());
            tvSoLuongHD.setText(item.getSoLuongHD()+"");
        }

        return v;
    }
}
