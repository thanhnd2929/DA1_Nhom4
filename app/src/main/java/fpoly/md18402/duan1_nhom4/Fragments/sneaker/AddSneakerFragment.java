package fpoly.md18402.duan1_nhom4.Fragments.sneaker;

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
import fpoly.md18402.duan1_nhom4.Model.Giay;
import fpoly.md18402.duan1_nhom4.Model.LoaiGiay;
import fpoly.md18402.duan1_nhom4.R;


public class AddSneakerFragment extends Fragment {
    private EditText mEdtID, mEdtName, mEdtPrice, mEdtNote;
    private Spinner mSpnType;

    private Button mBtnSave, mBtnCancel;
    private LoaiGiayDAO loaiGiayDAO;

    private String selectedType = null;
    private GiayDAO giayDAO;
    private List<String> loaiGiays;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add_sneaker, container, false);
        init(root);
        return root;
    }

    public void regAction() {
        mSpnType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedType = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // handle nothing selected
            }
        });
        mBtnSave.setOnClickListener((r) -> {
            addSneaker();
        });
        mBtnCancel.setOnClickListener((r) -> {
            cancel();
        });
    }

    public void cancel() {
        replaceFragment(new SneakerManagementFragment());
    }

    public void init(View root) {
        loaiGiayDAO = new LoaiGiayDAO(getContext());
        loaiGiays = loaiGiayDAO.getAll().stream().map(LoaiGiay::getTenLoai).collect(Collectors.toList());
        giayDAO = new GiayDAO(getContext());
        loadElements(root);
        regAction();
        loadSpinner();
    }

    public void loadElements(View root) {
        mEdtID = root.findViewById(R.id.edt_sneaker_id);
        mEdtName = root.findViewById(R.id.edt_sneaker_name);
        mEdtPrice = root.findViewById(R.id.edt_sneaker_price);
        mSpnType = root.findViewById(R.id.spn_sneaker_type);
        mBtnSave = root.findViewById(R.id.btn_save_sneaker);
        mBtnCancel = root.findViewById(R.id.btn_cancel_save_sneaker);
        mEdtNote = root.findViewById(R.id.edt_sneaker_note);
    }

    public void addSneaker() {
        if (mEdtID.getText().toString().equals("") || mEdtName.getText().toString().equals("") || mEdtPrice.getText().toString().equals("") || mEdtNote.getText().toString().equals("")) {
            Toast.makeText(getContext(), "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        int ID = Integer.parseInt(mEdtID.getText().toString());
        String name = String.valueOf(mEdtName.getText());
        int price = Integer.parseInt(mEdtPrice.getText().toString());
        String description = String.valueOf(mEdtNote.getText());
        // find type by name to get sneaker type and get id;

        if (selectedType == null) {
            selectedType = loaiGiays.get(0);
        }
        LoaiGiay loaiGiay = loaiGiayDAO.getByName(selectedType);
        Giay giay = new Giay(ID, name, description, price, loaiGiay.getMaLoai());
        // save sneaker
        giayDAO.addGiay(giay);
        // show toast
        Toast.makeText(getContext(), "Add new sneaker success", Toast.LENGTH_SHORT).show();
        replaceFragment(new SneakerManagementFragment());
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fm = requireActivity().getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.frame, fragment).commit();
    }

    public void loadSpinner() {

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, loaiGiays);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpnType.setAdapter(arrayAdapter);
    }
}