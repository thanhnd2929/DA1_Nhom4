package fpoly.md18402.duan1_nhom4.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import java.util.ArrayList;

import fpoly.md18402.duan1_nhom4.DAO.KhachHangDAO;
import fpoly.md18402.duan1_nhom4.Model.KhachHang;
import fpoly.md18402.duan1_nhom4.R;

public class KhachHangAdapter extends RecyclerView.Adapter<KhachHangAdapter.viewholer> {
    private final Context context;
    private final ArrayList<KhachHang> listKh;
    KhachHangDAO khachHangDAO;

    public KhachHangAdapter(Context context, ArrayList<KhachHang> listKh) {
        this.context = context;
        this.listKh = listKh;
        khachHangDAO = new KhachHangDAO(context);
    }


    @NonNull
    @Override
    public viewholer onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_khach_hang, null);
        return new viewholer(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholer holder, int position) {
        holder.khMaKH_itemKhachHang.setText(String.valueOf(listKh.get(position).getMaKH()));
        holder.khHoTen_itemKhachHang.setText(listKh.get(position).getHoTen());
        holder.khSdt_itemKhachHang.setText(listKh.get(position).getSdt()+"");
        KhachHang kh = listKh.get(position);
        holder.btnDelete_ThanhVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Cảnh báo");//set tiêu đề
                builder.setIcon(R.drawable.baseline_warning_24);//set icon
                builder.setMessage("Bạn có chắc chắn muốn xóa không?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (khachHangDAO.deleteKh(Integer.parseInt(String.valueOf(kh.getMaKH())))) {
                            listKh.clear();
                            listKh.addAll(khachHangDAO.getAll());
                            notifyDataSetChanged();
                            Toast.makeText(context, "Delete Succ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "Bạn đã thoát xoá", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                View view = inflater.inflate(R.layout.item_update_khachhang, null);
                builder.setView(view);
                Dialog dialog = builder.create();
                dialog.show();
                EditText edtMakh = view.findViewById(R.id.edtMaKH_itemUpKhachHang);
                EditText edtTenkh = view.findViewById(R.id.edtTenKH_itemUpKhachHang);
                EditText edtSdt = view.findViewById(R.id.edtSdt_itemUpKhachHang);
                ImageView btnSave = view.findViewById(R.id.btnSave_itemUpKhachHang);
                ImageView btnHuy = view.findViewById(R.id.btnHuy_itemUpKhachHang);
                //gán dl
                edtMakh.setText(String.valueOf(kh.getMaKH()));
                edtTenkh.setText(kh.getHoTen());
                edtSdt.setText(kh.getSdt() + "");
                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String ten = edtTenkh.getText().toString();
                        String sdt = edtSdt.getText().toString();
                        if (ten.isEmpty() || sdt.isEmpty()) {
                            Toast.makeText(context, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        try {
                           int phoneNumber = Integer.parseInt(sdt);
                           if(sdt.length()!=10){
                            Toast.makeText(context, "Số điện thoại phải là 10 số", Toast.LENGTH_SHORT).show();
                            return;
                            }
                            kh.setHoTen(edtTenkh.getText().toString());
                            kh.setSdt(String.valueOf(phoneNumber));
                            if (khachHangDAO.updateKh(kh)) {
                                listKh.clear();
                                listKh.addAll(khachHangDAO.getAll());
                                notifyDataSetChanged();
                                dialog.dismiss();
                                Toast.makeText(context, "Update Succ", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Update Fail", Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){
                            Toast.makeText(context, "Số điện thoại không đúng định dạng", Toast.LENGTH_SHORT).show();
                            return;
                        }

                    }
                });
                btnHuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, "Huỷ Update", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return listKh.size();
    }

    public class viewholer extends RecyclerView.ViewHolder {
        TextView khMaKH_itemKhachHang, khHoTen_itemKhachHang, khSdt_itemKhachHang;
        ImageView btnDelete_ThanhVien, btnUpdate;

        public viewholer(@NonNull View itemView) {
            super(itemView);
            khMaKH_itemKhachHang = itemView.findViewById(R.id.tvMaKH_itemKhachHang);
            khHoTen_itemKhachHang = itemView.findViewById(R.id.tvHoTen_itemKhachHang);
            khSdt_itemKhachHang = itemView.findViewById(R.id.tvSdt_itemKhachHang);
            btnDelete_ThanhVien = itemView.findViewById(R.id.btnDelete_KhachHang);
            btnUpdate = itemView.findViewById(R.id.btnUpdate_KhachHang);
        }
    }

}