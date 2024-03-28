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

import fpoly.md18402.duan1_nhom4.DAO.GiayDAO;
import fpoly.md18402.duan1_nhom4.DAO.LoaiGiayDAO;
import fpoly.md18402.duan1_nhom4.Model.Giay;
import fpoly.md18402.duan1_nhom4.Model.LoaiGiay;
import fpoly.md18402.duan1_nhom4.R;

public class SneakerAdapter extends RecyclerView.Adapter<SneakerAdapter.SneakerViewHolder> {
    private List<Giay> mData;
    public static LoaiGiayDAO loaiGiayDAO;
    private ISneakerItemAction action;

    public SneakerAdapter(List<Giay> mData, ISneakerItemAction action, Context context) {
        this.mData = mData;
        this.action = action;
        loaiGiayDAO = new LoaiGiayDAO(context);
    }

    public SneakerAdapter(ISneakerItemAction action, Context context) {
        this(new ArrayList<>(), action, context);
    }

    public void setData(List<Giay> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SneakerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.sneaker_item, parent, false);
        return new SneakerViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull SneakerViewHolder holder, int position) {
        holder.onBind(action, mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class SneakerViewHolder extends RecyclerView.ViewHolder {
        private TextView mTxtID;
        private TextView mTxtName;
        private TextView mTxtType;
        private TextView mTxtPrice;
        private TextView mTxtNote;
        private ImageView mImvImage;

        private ImageView mBtnDelete;

        public SneakerViewHolder(@NonNull View itemView) {
            super(itemView);
            mTxtID = itemView.findViewById(R.id.txt_sneaker_id);
            mTxtName = itemView.findViewById(R.id.txt_sneaker_name);
            mTxtNote = itemView.findViewById(R.id.txt_sneaker_note);
            mTxtPrice = itemView.findViewById(R.id.txt_sneaker_price);
            mTxtType = itemView.findViewById(R.id.txt_sneaker_type);
            mImvImage = itemView.findViewById(R.id.imv_image);
            mBtnDelete = itemView.findViewById(R.id.btn_delete);
        }

        public void onBind(ISneakerItemAction action, Giay dataItem) {
            // bind image using glide or picasso ...
            mTxtID.setText(String.valueOf("Mã giày : " + dataItem.getMaGiay()));
            mTxtName.setText("Tên giày  : " + dataItem.getTenGiay());
            mTxtNote.setText("Ghi chú : " + dataItem.getMoTa());
            mTxtPrice.setText("Giá : " + String.valueOf(dataItem.getGiaMua()));
            // can get loai bang ma sau do show len ten loai hoac
            LoaiGiay loaiGiay = loaiGiayDAO.getID(String.valueOf(dataItem.getMaLoai()));
            mTxtType.setText("Thể loại : " + loaiGiay.getTenLoai());
            // reg click action
            mBtnDelete.setOnClickListener((e) -> {
                action.onDeleteButtonClick(dataItem.getMaGiay());
            });
            itemView.setOnClickListener((it) -> {
                action.onItemClick(dataItem.getMaGiay());
            });
        }
    }

    public interface ISneakerItemAction {
        void onDeleteButtonClick(Integer ID);

        void onItemClick(Integer ID);

        void onEditButtonClick(Integer ID);
    }
}
