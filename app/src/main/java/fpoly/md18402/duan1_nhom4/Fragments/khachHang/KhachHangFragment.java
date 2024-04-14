package fpoly.md18402.duan1_nhom4.Fragments.khachHang;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import fpoly.md18402.duan1_nhom4.Adapter.KhachHangAdapter;
import fpoly.md18402.duan1_nhom4.DAO.KhachHangDAO;
import fpoly.md18402.duan1_nhom4.Model.KhachHang;
import fpoly.md18402.duan1_nhom4.R;


public class KhachHangFragment extends Fragment {


    RecyclerView rycKhachHang;
    FloatingActionButton fltAddKhachHang;
    KhachHangDAO khachHangDao;
    KhachHangAdapter adapter;
    EditText edtTim;
    ArrayList<KhachHang> tempList;
    private ArrayList<KhachHang> list = new ArrayList<>();

    public KhachHangFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_khach_hang, container, false);
        rycKhachHang = view.findViewById(R.id.rycKhachHang);
        fltAddKhachHang = view.findViewById(R.id.fltAddKhachHang);
        edtTim = view.findViewById(R.id.edtTimKiemKh);
        khachHangDao = new KhachHangDAO(getActivity());
        list = (ArrayList<KhachHang>) khachHangDao.getAll();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        tempList = (ArrayList<KhachHang>) khachHangDao.getAll();
        rycKhachHang.setLayoutManager(layoutManager);
        adapter = new KhachHangAdapter(getActivity(), list);
        rycKhachHang.setAdapter(adapter);

        fltAddKhachHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opendialogAdd();
            }
        });
        edtTim.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                list.clear();
                for (KhachHang kh : tempList) {
                    if (kh.getHoTen().contains(charSequence.toString())) {
                        list.add(kh);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        return view;
    }

    private void opendialogAdd() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.item_add_khachhang, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();
        EditText edtTenKH_itemAddKhachHang = view.findViewById(R.id.edtTenKH_itemAddKhachHang);
        EditText edtSdt_itemAddKhachHang = view.findViewById(R.id.edtSdt_itemAddKhachHang);
        ImageView btnSave_itemAddKhachHang = view.findViewById(R.id.btnSave_itemAddKhachHang);
        ImageView btnHuy_itemAddKhachHang = view.findViewById(R.id.btnHuy_itemAddKhachHang);
        btnSave_itemAddKhachHang.setOnClickListener(new View.OnClickListener() {
            private String hoTen = null;

            @Override
            public void onClick(View v) {

                String tenKh = edtTenKH_itemAddKhachHang.getText().toString();
                String sdt = edtSdt_itemAddKhachHang.getText().toString();
                if (tenKh.isEmpty() || sdt.isEmpty()) {
                    Toast.makeText(getActivity(), "Nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (sdt.length() != 10 || !sdt.matches("\\d+")) {
                    Toast.makeText(getActivity(), "Số điện thoại không hợp lệ", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {

                    KhachHang kh = new KhachHang(tenKh, sdt);
                    if (khachHangDao.insertKh(kh)) {
                        list.clear();
                        list.addAll(khachHangDao.getAll());
                        adapter.notifyDataSetChanged();
                        dialog.dismiss();
                        Toast.makeText(getActivity(), "Add Thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "Add Fail", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "Số điện thoại không hợp lệ", Toast.LENGTH_SHORT).show();
                }


//

            }
        });
        btnHuy_itemAddKhachHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Thoát Add", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }
}