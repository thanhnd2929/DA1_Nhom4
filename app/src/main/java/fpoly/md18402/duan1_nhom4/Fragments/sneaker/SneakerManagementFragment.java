package fpoly.md18402.duan1_nhom4.Fragments.sneaker;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import fpoly.md18402.duan1_nhom4.DAO.GiayDAO;
import fpoly.md18402.duan1_nhom4.Model.Giay;
import fpoly.md18402.duan1_nhom4.R;

public class SneakerManagementFragment extends Fragment {

    private RecyclerView mRcySneaker;
    private FloatingActionButton mBtnAdd;

    private TextInputEditText mEdtSearch;
    private SneakerAdapter sneakerAdapter;

    private GiayDAO giayDAO;

    private List<Giay> giayList;

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
        View root = inflater.inflate(R.layout.fragment_sneaker_management, container, false);
        init(root);
        return root;
    }

    public void init(View root) {
        giayDAO = new GiayDAO(getContext());
        giayList = giayDAO.getAll();
        loadElements(root);
        regAction();
        loadDataToView();
    }

    public void search(String query) {
        query = query.toLowerCase();
        if (query.trim().equals("")) {
            sneakerAdapter.setData(giayList);
            return;
        }
        List<Giay> searchResult = new ArrayList<>();
        for (Giay giay : giayList) {
            if (giay.getTenGiay().toLowerCase().contains(query) || String.valueOf(giay.getMaGiay()).equals(query)) {
                searchResult.add(giay);
            }
        }
        sneakerAdapter.setData(searchResult);
    }

    public void loadElements(View root) {
        mRcySneaker = root.findViewById(R.id.rcy_sneaker_list);
        mBtnAdd = root.findViewById(R.id.btn_add_sneaker);
        mEdtSearch = root.findViewById(R.id.edt_search_sneaker);
    }

    public void regAction() {
        mBtnAdd.setOnClickListener((e) -> {
            // handle go to add fragment
            replaceFragment(new AddSneakerFragment());
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
        SneakerAdapter.ISneakerItemAction action = new SneakerAdapter.ISneakerItemAction() {
            @Override
            public void onDeleteButtonClick(Integer ID) {
                Giay giay = giayDAO.getID(String.valueOf(ID));
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Xác nhận");
                builder.setMessage("Bạn thật sự muốn xóa sản giày " + giay.getTenGiay());
                builder.setPositiveButton("Xóa", (dialog, which) -> {
                    giayDAO.delete(String.valueOf(ID));
                    sneakerAdapter.setData(giayDAO.getAll());
                });
                builder.setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());
                builder.show();
            }

            @Override
            public void onItemClick(Integer ID) {
                Bundle bundle = new Bundle();
                bundle.putInt("id", ID);
                EditSnakerFragment editSnakerFragment = new EditSnakerFragment();
                editSnakerFragment.setArguments(bundle);
                replaceFragment(editSnakerFragment);
            }

            @Override
            public void onEditButtonClick(Integer ID) {

            }
        };
        // handle to get all Giay
        List<Giay> giayList = giayDAO.getAll();
        // end handle
        sneakerAdapter = new SneakerAdapter(action, getContext());
        mRcySneaker.setAdapter(sneakerAdapter);
        mRcySneaker.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        sneakerAdapter.setData(giayList);
    }
}