package fpoly.md18402.duan1_nhom4.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fpoly.md18402.duan1_nhom4.DAO.LoaiGiayDAO;
import fpoly.md18402.duan1_nhom4.Model.Giay;
import fpoly.md18402.duan1_nhom4.Model.LoaiGiay;
import fpoly.md18402.duan1_nhom4.R;

public class TopSneakerAdapter extends RecyclerView.Adapter<TopSneakerAdapter.TopSneakerViewHolder> {
    private List<Giay> data;
    public static LoaiGiayDAO loaiGiayDAO;


    public TopSneakerAdapter(Context context) {
        this.data = new ArrayList<>();
        loaiGiayDAO = new LoaiGiayDAO(context);
    }

    public void setData(List<Giay> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TopSneakerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.top_sneaker_item, parent, false);
        return new TopSneakerAdapter.TopSneakerViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull TopSneakerViewHolder holder, int position) {
        holder.onBind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class TopSneakerViewHolder extends RecyclerView.ViewHolder {
        private final TextView mTxtID;
        private final TextView mTxtName;
        private final TextView mTxtType;
        private final TextView mTxtPrice;
        private final TextView mTxtNote;

        public TopSneakerViewHolder(View viewItem) {
            super(viewItem);
            mTxtID = itemView.findViewById(R.id.txt_sneaker_id);
            mTxtName = itemView.findViewById(R.id.txt_sneaker_name);
            mTxtNote = itemView.findViewById(R.id.txt_sneaker_note);
            mTxtPrice = itemView.findViewById(R.id.txt_sneaker_price);
            mTxtType = itemView.findViewById(R.id.txt_sneaker_type);
        }

        public void onBind(Giay dataItem) {
            // bind image using glide or picasso ...
            mTxtID.setText(String.valueOf("Mã giày : " + dataItem.getMaGiay()));
            mTxtName.setText("Tên giày  : " + dataItem.getTenGiay());
            mTxtNote.setText("Ghi chú : " + dataItem.getMoTa());
            mTxtPrice.setText("Giá : " + String.valueOf(dataItem.getGiaMua()));
            // can get loai bang ma sau do show len ten loai hoac
            LoaiGiay loaiGiay = loaiGiayDAO.getID(String.valueOf(dataItem.getMaLoai()));
            mTxtType.setText("Thể loại : " + loaiGiay.getTenLoai());
            // reg click action
        }
    }
}
