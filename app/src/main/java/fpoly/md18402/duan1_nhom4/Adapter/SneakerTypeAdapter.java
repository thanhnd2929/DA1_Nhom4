package fpoly.md18402.duan1_nhom4.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fpoly.md18402.duan1_nhom4.Model.LoaiGiay;
import fpoly.md18402.duan1_nhom4.R;

public class SneakerTypeAdapter extends RecyclerView.Adapter<SneakerTypeAdapter.SneakerTypeViewHolder> {
    private List<LoaiGiay> mData;
    private SneakerTypeAdapter.ISneakerTypeItemAction action;

    public SneakerTypeAdapter(List<LoaiGiay> mData, SneakerTypeAdapter.ISneakerTypeItemAction action) {
        this.mData = mData;
        this.action = action;
    }

    public SneakerTypeAdapter(SneakerTypeAdapter.ISneakerTypeItemAction action) {
        this(new ArrayList<>(), action);
    }

    public void setData(List<LoaiGiay> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SneakerTypeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.sneaker_type_item, parent, false);
        return new SneakerTypeAdapter.SneakerTypeViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull SneakerTypeViewHolder holder, int position) {
        holder.onBind(action, mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class SneakerTypeViewHolder extends RecyclerView.ViewHolder {
        private TextView mTxtID;
        private TextView mTxtName;
        private TextView mTxtType;
        private ImageView mBtnDelete;
        private ImageView mBtnEdit;


        public SneakerTypeViewHolder(@NonNull View itemView) {
            super(itemView);
            mTxtID = itemView.findViewById(R.id.txt_sneaker_type_id);
            mTxtName = itemView.findViewById(R.id.txt_sneaker_type_name);
            mTxtType = itemView.findViewById(R.id.txt_sneaker_type_cat);
            mBtnDelete = itemView.findViewById(R.id.btn_delete_type);
            mBtnEdit = itemView.findViewById(R.id.btn_edit_type);
        }

        public void onBind(SneakerTypeAdapter.ISneakerTypeItemAction action, LoaiGiay dataItem) {
            // bind image using glide or picasso ...
            mTxtID.setText(String.valueOf("Mã loại : " + dataItem.getMaLoai()));
            mTxtName.setText("Tên loại  : " + dataItem.getTenLoai());
            // can get loai bang ma sau do show len ten loai hoac
            mTxtType.setText("Loại hàng : " + String.valueOf(dataItem.getLoaiHang()));
            // reg click action
            mBtnDelete.setOnClickListener((e) -> {
                action.onDeleteButtonClick(dataItem.getMaLoai());
            });
            mBtnEdit.setOnClickListener((e) ->
                    action.onEditButtonClick(dataItem.getMaLoai()));
        }
    }

    public interface ISneakerTypeItemAction {
        void onDeleteButtonClick(Integer ID);

        void onEditButtonClick(Integer ID);
    }
}
