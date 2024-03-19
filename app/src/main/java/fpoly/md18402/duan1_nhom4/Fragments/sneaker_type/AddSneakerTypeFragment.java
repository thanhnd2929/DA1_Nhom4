package fpoly.md18402.duan1_nhom4.Fragments.sneaker_type;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;
import java.util.stream.Collectors;

import fpoly.md18402.duan1_nhom4.DAO.GiayDAO;
import fpoly.md18402.duan1_nhom4.DAO.LoaiGiayDAO;
import fpoly.md18402.duan1_nhom4.Fragments.sneaker.SneakerManagementFragment;
import fpoly.md18402.duan1_nhom4.Model.Giay;
import fpoly.md18402.duan1_nhom4.Model.LoaiGiay;
import fpoly.md18402.duan1_nhom4.R;

public class AddSneakerTypeFragment extends Fragment {
    private EditText mEdtID, mEdtName, mEdtType;

    private Button mBtnSave, mBtnCancel;
    private LoaiGiayDAO loaiGiayDAO;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add_sneaker_type, container, false);
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
    }

    public void loadElements(View root) {
        mEdtID = root.findViewById(R.id.edt_sneaker_type_id);
        mEdtName = root.findViewById(R.id.edt_sneaker_type_name);
        mEdtType = root.findViewById(R.id.edt_sneaker_type_cate);
        mBtnSave = root.findViewById(R.id.btn_save_sneaker_type);
        mBtnCancel = root.findViewById(R.id.btn_cancel_save_sneaker_type);
    }

    public void addSneaker() {
        if (mEdtID.getText().toString().equals("") || mEdtName.getText().toString().equals("") || mEdtType.getText().toString().equals("")) {
            Toast.makeText(getContext(), "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        int ID = Integer.parseInt(mEdtID.getText().toString());
        String name = String.valueOf(mEdtName.getText());
        String type = String.valueOf(mEdtType.getText());
        LoaiGiay loaiGiay = new LoaiGiay(ID, name, type);
        loaiGiayDAO.addLoaiGiay(loaiGiay);
        // show toast
        Toast.makeText(getContext(), "Add new sneaker type success", Toast.LENGTH_SHORT).show();
        replaceFragment(new ListTypeSneakerFragment());
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fm = requireActivity().getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.frame, fragment).commit();
    }
}