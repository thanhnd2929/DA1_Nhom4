package fpoly.md18402.duan1_nhom4.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import fpoly.md18402.duan1_nhom4.DAO.NhanVienDAO;
import fpoly.md18402.duan1_nhom4.Model.NhanVien;
import fpoly.md18402.duan1_nhom4.R;


public class ChangePassFragment extends Fragment {

    EditText edtOldPass, edtNewPass, edtRePass;
    Button btnSavePass, btnCancelpass;
    NhanVienDAO nhanVienDAO;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_change_pass, container, false);
        nhanVienDAO = new NhanVienDAO(getActivity());
        edtOldPass = v.findViewById(R.id.edtOldPass);
        edtNewPass = v.findViewById(R.id.edtNewPass);
        edtRePass = v.findViewById(R.id.edtRePass);
        btnSavePass = v.findViewById(R.id.btnSavePass);
        btnCancelpass = v.findViewById(R.id.btnCancelPass);

        btnCancelpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtNewPass.setText("");
                edtOldPass.setText("");
                edtRePass.setText("");
            }
        });

        btnSavePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
                String user = sharedPreferences.getString("USER","");
                if (validate() > 0) {
                    NhanVien nhanVien = nhanVienDAO.getID(user);
                    nhanVien.setMatKhau(edtNewPass.getText().toString());
                    if (nhanVienDAO.updateNhanVien(nhanVien) > 0) {
                        Toast.makeText(getContext(), "Thay đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                        edtRePass.setText("");
                        edtOldPass.setText("");
                        edtNewPass.setText("");
                    } else  {
                        Toast.makeText(getContext(), "Thay đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        return v;
    }

    public int validate() {
        int check = 1;
        String passOld = edtOldPass.getText().toString().trim();
        String passNew = edtNewPass.getText().toString().trim();
        String rePass = edtRePass.getText().toString().trim();
        if (passNew.isEmpty() || passOld.isEmpty() || rePass.isEmpty()) {
            Toast.makeText(getContext(), "Bạn phải nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            check = -1;
        } else {
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
            String pass = sharedPreferences.getString("PASS","");
            if (!pass.equals(passOld)) {
                Toast.makeText(getContext(), "Mật khẩu cũ sai", Toast.LENGTH_SHORT).show();
                check = -1;
            }
            if (!passNew.equals(rePass)) {
                Toast.makeText(getContext(), "Mật khẩu mới không trùng khớp", Toast.LENGTH_SHORT).show();
                check = -1;
            }
        }
        return check;
    }
}