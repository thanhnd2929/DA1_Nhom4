package fpoly.md18402.duan1_nhom4.Fragments.nhanVien;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import fpoly.md18402.duan1_nhom4.Adapter.NhanVienAdapter;
import fpoly.md18402.duan1_nhom4.DAO.NhanVienDAO;
import fpoly.md18402.duan1_nhom4.Model.NhanVien;
import fpoly.md18402.duan1_nhom4.R;


public class NhanVienFragment extends Fragment {
    ListView lvNhanVien;
    ArrayList<NhanVien> listNv;
    ArrayList<NhanVien> tempList;
    Dialog dialog;
    FloatingActionButton flab;
    EditText edtMaNv, edtHoTen, edtPass, edtSdt, editTextTimKiem;
    Spinner spinner;
    ImageView btnSave, btnHuy;
    static NhanVienDAO nhanVienDao;
    NhanVienAdapter adapter;


    private String generateMaNV() {
        // Lấy thời gian hiện tại
        long timestamp = System.currentTimeMillis();
        // Chuyển đổi thời gian thành mã nhân viên
        // Ví dụ: chuyển đổi timestamp thành chuỗi và thêm vào một tiền tố nào đó
        return "NV_" + timestamp;
    }


    public NhanVienFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_nhan_vien, container, false);
        lvNhanVien = v.findViewById(R.id.lvNhanVien);
        flab = v.findViewById(R.id.fltAddNhanVien);
        editTextTimKiem = v.findViewById(R.id.edtTimKiemNv);
        nhanVienDao = new NhanVienDAO(getActivity());
        listNv = (ArrayList<NhanVien>) nhanVienDao.getAll();
        tempList = (ArrayList<NhanVien>) nhanVienDao.getAll();
        adapter = new NhanVienAdapter(getActivity(), this, listNv);
        lvNhanVien.setAdapter(adapter);
        editTextTimKiem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                listNv.clear();
                for (NhanVien nv : tempList
                ) {
                    if (nv.getHoTen().contains(charSequence.toString())) {
                        listNv.add(nv);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        flab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });
        lvNhanVien.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getContext(), "Không thể sửa thông tin nhân viên", Toast.LENGTH_SHORT).show();
                return true; // Thay đổi từ false thành true để ngăn việc xử lý sự kiện tiếp theo
            }
        });

        // hien thi mat khau nhan vien
        lvNhanVien.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NhanVien nhanVien = listNv.get(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Mật khẩu của " + nhanVien.getHoTen());
                builder.setMessage("Mật khẩu: " + nhanVien.getMatKhau());
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });

        return v;
    }

    public void onItemClick(NhanVien nhanVien) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Mật khẩu của " + nhanVien.getHoTen());
        builder.setMessage("Mật khẩu: " + nhanVien.getMatKhau());
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();

        // Đặt màu nền mờ cho dialog
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        // Đặt cho dialog có thể bị hủy bằng cách chạm ra ngoài
        dialog.setCancelable(true);

        dialog.show();
    }


    public void capNhatLv() {
        listNv = (ArrayList<NhanVien>) nhanVienDao.getAll();
        adapter = new NhanVienAdapter(getActivity(), this, listNv);
        lvNhanVien.setAdapter(adapter);
    }

    public void openDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.item_dialog_nhanvien, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();
        edtMaNv = dialog.findViewById(R.id.edtMaNv_itemNhanVien);
        edtHoTen = dialog.findViewById(R.id.edtHoTen_itemNhanVien);
        edtPass = dialog.findViewById(R.id.edtPassword_itemNhanVien);
        edtSdt = dialog.findViewById(R.id.edtSdt_itemNhanVien);
        btnSave = dialog.findViewById(R.id.Add_NhanVien);
        btnHuy = dialog.findViewById(R.id.huyAdd_NhanVien);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String maNv = edtMaNv.getText().toString().trim();
                if (maNv.equals("")) {
                    // Tạo mã nhân viên mới dựa trên thời gian hiện tại hoặc các phương pháp tương tự
                    maNv = generateMaNV(); // Phương thức để tạo mã nhân viên mới
                    edtMaNv.setText(maNv); // Đặt giá trị mã nhân viên mới vào EditText
                }

                String hoTen = edtHoTen.getText().toString().trim();
                String pass = edtPass.getText().toString().trim();
                String sdt = edtSdt.getText().toString().trim();

                if (hoTen.equals("")) {
                    Toast.makeText(getActivity(), "Vui lòng nhập họ tên", Toast.LENGTH_SHORT).show();
                    edtHoTen.requestFocus();
                    return;
                }
                if (pass.equals("")) {
                    Toast.makeText(getActivity(), "Vui lòng nhập pass", Toast.LENGTH_SHORT).show();
                    edtPass.requestFocus();
                    return;
                }
                if (sdt.equals("")) {
                    Toast.makeText(getActivity(), "Vui lòng nhập sđt", Toast.LENGTH_SHORT).show();
                    edtSdt.requestFocus();
                    return;
                }
                if (sdt.length() != 10 || !sdt.matches("\\d+")) {
                    Toast.makeText(getActivity(), "Số điện thoại không hợp lệ", Toast.LENGTH_SHORT).show();
                    return;
                }
                NhanVien nv = new NhanVien(maNv, hoTen, pass, sdt);
                if (nhanVienDao.addNhanVien(nv)) {
                    listNv.clear();
                    listNv.addAll(nhanVienDao.getAll());
                    adapter.notifyDataSetChanged();
                    Toast.makeText(getActivity(), "Add Succ", Toast.LENGTH_SHORT).show();
                    capNhatLv();
                    dialog.dismiss();
                } else {
                    Toast.makeText(getActivity(), "Add fail", Toast.LENGTH_SHORT).show();
                }
            }
        });


        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    public void xoa(String Id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Cảnh báo");
        builder.setIcon(R.drawable.baseline_warning_24);
        builder.setMessage("Bạn có chắc chắn muốn xóa không");
        builder.setCancelable(true);
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                nhanVienDao.delete(Id);
                capNhatLv();
                dialog.dismiss();
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