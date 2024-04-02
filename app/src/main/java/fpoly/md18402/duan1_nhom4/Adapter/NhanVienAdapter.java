package fpoly.md18402.duan1_nhom4.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import fpoly.md18402.duan1_nhom4.Fragments.nhanVien.NhanVienFragment;
import fpoly.md18402.duan1_nhom4.Model.NhanVien;
import fpoly.md18402.duan1_nhom4.R;

public class NhanVienAdapter extends ArrayAdapter<NhanVien> {
    private Context context;
    private ArrayList<NhanVien> list;
    NhanVienFragment fragment;
    TextView tvMaNv, tvHoTen, tvSdt, tvCoSo;
    ImageView btnDelete;



    public NhanVienAdapter(@NonNull Context context, NhanVienFragment fragment, ArrayList<NhanVien> list) {
        super(context, 0, list);
        this.context = context;
        this.list = list;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_nhanvien, null);
        }
        NhanVien item = list.get(position);
        if (item != null) {

            tvMaNv = v.findViewById(R.id.tvMaNv_item);
            tvHoTen = v.findViewById(R.id.tvHoTen_item);
            tvSdt = v.findViewById(R.id.tvSdt_item);
            btnDelete=v.findViewById(R.id.btnDelete_NhanVien);

            tvMaNv.setText(item.getMaNV());
            tvHoTen.setText(item.getHoTen());

            tvSdt.setText(item.getSdt() + "");

        }



        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment.xoa(item.getMaNV());
            }
        });
        return v;
    }
}
