package fpoly.md18402.duan1_nhom4.Fragments.sneaker_type;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import fpoly.md18402.duan1_nhom4.DAO.LoaiGiayDAO;
import fpoly.md18402.duan1_nhom4.Model.LoaiGiay;
import fpoly.md18402.duan1_nhom4.R;


public class SneakerTypeEditFragment extends Fragment {

    private int id;
    private TextView mTxtID;

    private EditText mEdtName, mEdtType;

    private Button mBtnSave, mBtnCancel;
    private LoaiGiayDAO loaiGiayDAO;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getInt("ID");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_sneaker_type_edit, container, false);
        init(root);
        return root;
    }

    public void regAction() {

        mBtnSave.setOnClickListener((r) -> {
            addSneaker();
        });
        mBtnCancel.setOnClickListener((r) -> {
            cancel();
        });
    }

    public void cancel() {
        replaceFragment(new ListTypeSneakerFragment());
    }

    public void init(View root) {
        loaiGiayDAO = new LoaiGiayDAO(getContext());
        loadElements(root);
        regAction();

        LoaiGiay loaiGiay = loaiGiayDAO.getID(String.valueOf(id));
        mTxtID.setText("EDIT : " + loaiGiay.getTenLoai());
        mEdtName.setText(loaiGiay.getTenLoai());
        mEdtType.setText(loaiGiay.getLoaiHang());
    }

    public void loadElements(View root) {
        mTxtID = root.findViewById(R.id.txt_type_sk_name);
        mEdtName = root.findViewById(R.id.edt_sneaker_type_name_edit);
        mEdtType = root.findViewById(R.id.edt_sneaker_type_cate_edit);
        mBtnSave = root.findViewById(R.id.btn_save_sneaker_type_edit);
        mBtnCancel = root.findViewById(R.id.btn_cancel_save_sneaker_type_edit);
    }

    public void addSneaker() {
        if (mEdtName.getText().toString().equals("") || mEdtType.getText().toString().equals("")) {
            Toast.makeText(getContext(), "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        String name = String.valueOf(mEdtName.getText());
        String type = String.valueOf(mEdtType.getText());
        LoaiGiay loaiGiay = loaiGiayDAO.getID(String.valueOf(id));
        loaiGiay.setTenLoai(name);
        loaiGiay.setLoaiHang(type);
        loaiGiay.setMaLoai(id);
        loaiGiayDAO.updateLoaiGiay(loaiGiay);
        // show toast
        Toast.makeText(getContext(), "Edit sneaker type success", Toast.LENGTH_SHORT).show();
        replaceFragment(new ListTypeSneakerFragment());
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fm = requireActivity().getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.frame, fragment).commit();
    }
}