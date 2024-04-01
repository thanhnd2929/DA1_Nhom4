package fpoly.md18402.duan1_nhom4.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import fpoly.md18402.duan1_nhom4.DAO.CTHoaDonDAO;
import fpoly.md18402.duan1_nhom4.DAO.GiayDAO;
import fpoly.md18402.duan1_nhom4.Model.CTHD;
import fpoly.md18402.duan1_nhom4.Model.Giay;
import fpoly.md18402.duan1_nhom4.R;

public class HoaDonCtAdapter extends ArrayAdapter<CTHD> {
    private Context context;
    private ArrayList<CTHD> list;
    TextView tvGiay, tvSL, tvThanhTien;
    ImageView btnDelete;
    GiayDAO giayDAO;
    CTHoaDonDAO ctHoaDonDAO;
    private OnDeleteSuccessListener onDeleteSuccessListener;

    public HoaDonCtAdapter(@NonNull Context context, ArrayList<CTHD> list) {
        super(context, 0, list);
        this.context = context;
        this.list = list;
        ctHoaDonDAO = new CTHoaDonDAO(context);
    }

    public interface OnDeleteSuccessListener {
        void onDeleteSuccess();
    }

    public void setOnDeleteSuccessListener(OnDeleteSuccessListener listener) {
        onDeleteSuccessListener = listener;
    }

    public int tongTien() {
        int tong = 0;
        for (int i = 0; i < getCount(); i++) {
            CTHD item = getItem(i);
            tong += item.getGiaMua() * item.getSoLuong();
        }
        return tong;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_hdct, null);
        }
        final CTHD item = list.get(position);
        if (item!=null) {
            tvGiay = view.findViewById(R.id.tvTenGiay_itemHDCT);
            tvSL = view.findViewById(R.id.tvSoLuong_itemHDCT);
            tvThanhTien = view.findViewById(R.id.tvGia_itemHDCT);
            btnDelete = view.findViewById(R.id.btnDelete_HDCT);

            giayDAO = new GiayDAO(context);
            Giay giay = giayDAO.getID(String.valueOf(item.getMaGiay()));
            tvGiay.setText("Tên Giày:" + giay.getTenGiay());
            tvSL.setText("Số Lượng: "+item.getSoLuong());
            tvThanhTien.setText("Giá: "+item.getGiaMua()*item.getSoLuong()+" VND");

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Delete");
                    builder.setMessage("Bạn có chắc chắn muốn xóa không");
                    builder.setCancelable(true);
                    builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (ctHoaDonDAO.delete(String.valueOf(item.getMaCTHD())) > 0) {
                                list.clear();
                                list.addAll(ctHoaDonDAO.getAll(item.getMaHD()));
                                notifyDataSetChanged();
                                Toast.makeText(context, "Xoá thành công", Toast.LENGTH_SHORT).show();
                                if (onDeleteSuccessListener != null) {
                                    onDeleteSuccessListener.onDeleteSuccess();
                                }
                            }
                        }
                    });

                    builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });
        }
        return view;
    }
}
