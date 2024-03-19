package fpoly.md18402.duan1_nhom4.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import fpoly.md18402.duan1_nhom4.Adapter.HoaDonAdapter;
import fpoly.md18402.duan1_nhom4.Adapter.KhachHangSpinnerAdapter;
import fpoly.md18402.duan1_nhom4.DAO.HoaDonDAO;
import fpoly.md18402.duan1_nhom4.DAO.KhachHangDAO;
import fpoly.md18402.duan1_nhom4.HoaDonCtActivity;
import fpoly.md18402.duan1_nhom4.Model.HoaDon;
import fpoly.md18402.duan1_nhom4.Model.KhachHang;
import fpoly.md18402.duan1_nhom4.R;


public class HoaDonFragment extends Fragment {

    ListView lvHoaDon;
    ArrayList<HoaDon> list;
    ArrayList<HoaDon> subList;
    HoaDonDAO hoaDonDAO;
    SimpleDateFormat sdf;
    HoaDonAdapter hoaDonAdapter;
    HoaDon item;
    FloatingActionButton fab;
    Dialog dialog;
    EditText edtMaHD, edtSoHD;
    Spinner spKhachHang;
    TextView tvNgay, tvNV;
    RadioGroup radioGroup;
    RadioButton rdoTienMat, rdoChuyenKhoan;
    Button btnSave, btnCancel;
    KhachHangSpinnerAdapter khachHangSpinnerAdapter;
    ArrayList<KhachHang> listKH;
    KhachHangDAO khachHangDAO;
    int maKH;
    int positionKH;
    int soHD;
    String tenNv = "";
    SimpleDateFormat sfd = new SimpleDateFormat("yyyy/MM/dd");
    SearchView searchView;

    public HoaDonFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_hoa_don, container, false);
        lvHoaDon = v.findViewById(R.id.lvHoaDon);
        fab = v.findViewById(R.id.fl_addHoaDon);
        searchView = v.findViewById(R.id.searchView);
        hoaDonDAO = new HoaDonDAO(getActivity());
        list = (ArrayList<HoaDon>) hoaDonDAO.getAll();
        subList = (ArrayList<HoaDon>) hoaDonDAO.getAll();
        hoaDonAdapter = new HoaDonAdapter(getActivity(), this, list);
        lvHoaDon.setAdapter(hoaDonAdapter);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              openDialog(getActivity(), 0);  // add
            }
        });

        lvHoaDon.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
          @Override
          public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            item = list.get(position);
            openDialog(getActivity(), 1); // update
            return false;
          }
        });

        lvHoaDon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), HoaDonCtActivity.class);
                soHD = list.get(position).getMaHD();
                intent.putExtra("soHD", soHD);
                startActivity(intent);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<HoaDon> newList = new ArrayList<>();
                if (!newText.isEmpty()) {
                    for (HoaDon hd : list) {
                        String maHD = String.valueOf(hd.getMaHD());
                        if (maHD.toLowerCase().contains(newText.toLowerCase())) {
                            newList.add(hd);
                        }
                    }
                    hoaDonAdapter = new HoaDonAdapter(getActivity(), HoaDonFragment.this, newList);
                    lvHoaDon.setAdapter(hoaDonAdapter);
                } else {
                    capNhatLv();
                }
                return false;
            }
        });
        return v;
    }

    public void capNhatLv() {
        list = (ArrayList<HoaDon>) hoaDonDAO.getAll();
        hoaDonAdapter = new HoaDonAdapter(getActivity(), this, list);
        lvHoaDon.setAdapter(hoaDonAdapter);
    }

    public void openDialog(Context context, int type) {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_hoa_don);
        edtMaHD = dialog.findViewById(R.id.edtMaHD);
        edtSoHD = dialog.findViewById(R.id.edtSoHD);
        tvNgay = dialog.findViewById(R.id.tvNgayMua);
        spKhachHang = dialog.findViewById(R.id.sp_khach_hang);
        tvNV = dialog.findViewById(R.id.tvMaNV);
        radioGroup = dialog.findViewById(R.id.radioGroup);
        rdoTienMat = dialog.findViewById(R.id.rdoTienMat);
        rdoChuyenKhoan = dialog.findViewById(R.id.rdoChuyenKhoan);
        btnSave = dialog.findViewById(R.id.btnSaveHD);
        btnCancel = dialog.findViewById(R.id.btnCancelHD);

        sfd = new SimpleDateFormat("yyyy/MM/dd");
        tvNgay.setText("Ngày mua: " + sfd.format(new Date()));

//    spiner
        khachHangDAO = new KhachHangDAO(context);
        listKH = new ArrayList<KhachHang>();
        listKH = (ArrayList<KhachHang>) khachHangDAO.getAll();
        khachHangSpinnerAdapter = new KhachHangSpinnerAdapter(context, listKH);
        spKhachHang.setAdapter(khachHangSpinnerAdapter);

        if (listKH.isEmpty()) {
            Toast.makeText(context, "Vui lòng thêm Khách hàng trước", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
            return;
        }

        spKhachHang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maKH = listKH.get(position).getMaKH();
                Toast.makeText(context, "Chọn:" + listKH.get(position).getHoTen(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (type != 0) {
            edtMaHD.setText(String.valueOf(item.getMaHD()));
            edtSoHD.setText(item.getSoHD());
            for (int i = 0; i < listKH.size(); i++) {
                if (item.getMaKH() == (listKH.get(i).getMaKH())) {
                    positionKH = i;
                }
                spKhachHang.setSelection(positionKH);
            }


            tvNgay.setText(sfd.format(item.getNgayMua()));

            if (item.getThanhToan() == 0) {
                rdoTienMat.setChecked(true);
            } else {
                rdoChuyenKhoan.setChecked(false);
            }
        }


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        SharedPreferences preferences = context.getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
        tenNv = preferences.getString("USER", "");
        tvNV.setText( tenNv);

        btnSave.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            item = new HoaDon();
            item.setMaNV(tvNV.getText().toString());
            item.setMaKH(maKH);
            item.setNgayMua(new Date());
            item.setSoHD(edtSoHD.getText().toString());

            if (rdoTienMat.isChecked()) {
              item.setThanhToan(0);
            } else {
              item.setThanhToan(1);
            }

            if (type == 0) {
              //type==0 insert
              if (hoaDonDAO.addHoaDon(item) > 0) {
                Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
                capNhatLv();
              } else {
                Toast.makeText(context, "Thêm thất bại", Toast.LENGTH_SHORT).show();
              }
            } else {
              //type ==1 Update
              item.setMaHD(Integer.parseInt(edtMaHD.getText().toString()));
              if (hoaDonDAO.updateHoaDon(item) > 0) {
                Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                capNhatLv();
              } else {
                Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
              }
            }
            capNhatLv();
            dialog.dismiss();
          }
        });
        dialog.show();

    }

    public void xoa(String id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Cảnh báo");
        builder.setMessage("Bạn có muốn xóa không");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                hoaDonDAO.delete(id);
                capNhatLv();
                dialog.cancel();
                Toast.makeText(getContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        builder.show();
    }


}