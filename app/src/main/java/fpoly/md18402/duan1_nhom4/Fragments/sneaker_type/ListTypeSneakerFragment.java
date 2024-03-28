package fpoly.md18402.duan1_nhom4.Fragments.sneaker_type;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import fpoly.md18402.duan1_nhom4.Adapter.SneakerAdapter;
import fpoly.md18402.duan1_nhom4.Adapter.SneakerTypeAdapter;
import fpoly.md18402.duan1_nhom4.DAO.GiayDAO;
import fpoly.md18402.duan1_nhom4.DAO.LoaiGiayDAO;
import fpoly.md18402.duan1_nhom4.Fragments.sneaker.AddSneakerFragment;
import fpoly.md18402.duan1_nhom4.Model.Giay;
import fpoly.md18402.duan1_nhom4.Model.LoaiGiay;
import fpoly.md18402.duan1_nhom4.R;

public class ListTypeSneakerFragment extends Fragment {
    private RecyclerView mRcySneaker;
    private FloatingActionButton mBtnAdd;

    private TextInputEditText mEdtSearch;
    private SneakerTypeAdapter sneakerAdapter;

    private LoaiGiayDAO loaiGiayDAO;
    private List<LoaiGiay> loaiGiays;

    public void replaceFragment(Fragment fragment) {
        FragmentManager fm = requireActivity().getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.frame, fragment).commit();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_list_type_sneaker, container, false);
        init(root);
        return root;
    }

    public void init(View root) {
        loaiGiayDAO = new LoaiGiayDAO(getContext());
        loaiGiays = loaiGiayDAO.getAll();
        loadElements(root);
        regAction();
        loadDataToView();
    }

    public void search(String query) {
        query = query.toLowerCase();
        if (query.trim().equals("")) {
            sneakerAdapter.setData(loaiGiays);
            return;
        }
        List<LoaiGiay> searchResult = new ArrayList<>();
        for (LoaiGiay loaiGiay : loaiGiays) {
            if (loaiGiay.getTenLoai().toLowerCase().contains(query) || String.valueOf(loaiGiay.getMaLoai()).equals(query)) {
                searchResult.add(loaiGiay);
            }
        }
        sneakerAdapter.setData(searchResult);
    }

    public void loadElements(View root) {
        mRcySneaker = root.findViewById(R.id.rcy_sneaker_type_list);
        mBtnAdd = root.findViewById(R.id.btn_add_sneaker_type);
        mEdtSearch = root.findViewById(R.id.edt_search_sneaker_type);
    }

    public void regAction() {
        mBtnAdd.setOnClickListener((e) -> {
            // handle go to add fragment
            replaceFragment(new AddSneakerTypeFragment());
        });
        mEdtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // do smthing before change
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                search(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // do smthing after change
            }
        });
    }

    public void loadDataToView() {
        SneakerTypeAdapter.ISneakerTypeItemAction action = new SneakerTypeAdapter.ISneakerTypeItemAction() {
            @Override
            public void onDeleteButtonClick(Integer ID) {
                LoaiGiay giay = loaiGiayDAO.getID(String.valueOf(ID));
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Xác nhận");
                builder.setMessage("Bạn thật sự muốn xóa loại giày " + giay.getTenLoai());
                builder.setPositiveButton("Xóa", (dialog, which) -> {
                    loaiGiayDAO.delete(String.valueOf(ID));
                    sneakerAdapter.setData(loaiGiayDAO.getAll());
                });
                builder.setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());
                builder.show();
            }

            @Override
            public void onEditButtonClick(Integer ID) {
                SneakerTypeEditFragment editFragment = new SneakerTypeEditFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("ID", ID);
                editFragment.setArguments(bundle);
                replaceFragment(editFragment);
            }
        };
        // handle to get all Giay
        List<LoaiGiay> giayList = loaiGiayDAO.getAll();
        // end handle
        sneakerAdapter = new SneakerTypeAdapter(action);
        mRcySneaker.setAdapter(sneakerAdapter);
        mRcySneaker.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        sneakerAdapter.setData(giayList);
    }
}