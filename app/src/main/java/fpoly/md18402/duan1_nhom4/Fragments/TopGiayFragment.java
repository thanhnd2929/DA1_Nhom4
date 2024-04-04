package fpoly.md18402.duan1_nhom4.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import fpoly.md18402.duan1_nhom4.Adapter.TopSneakerAdapter;
import fpoly.md18402.duan1_nhom4.DAO.GiayDAO;
import fpoly.md18402.duan1_nhom4.DAO.ThongKeDAO;
import fpoly.md18402.duan1_nhom4.Model.Giay;
import fpoly.md18402.duan1_nhom4.Model.Top;
import fpoly.md18402.duan1_nhom4.R;


public class TopGiayFragment extends Fragment {
    private RecyclerView recyclerView;
    private TopSneakerAdapter adapter;

    private ThongKeDAO thongKeDAO;

    private GiayDAO giayDAO;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_top_giay, container, false);
        recyclerView = root.findViewById(R.id.rcy_sneaker_list);
        adapter = new TopSneakerAdapter(requireContext());
        thongKeDAO = new ThongKeDAO(requireContext());
        giayDAO = new GiayDAO(requireContext());
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        List<Top> topList = thongKeDAO.getTop();
        List<Giay> giayList = new ArrayList<>();
        for (Top top : topList) {
            giayList.add(giayDAO.getID(top.getMaGiay()));
        }
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false));
        adapter.setData(giayList);
    }
}