package fpoly.md18402.duan1_nhom4.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import fpoly.md18402.duan1_nhom4.Adapter.TopNVAdapter;
import fpoly.md18402.duan1_nhom4.DAO.ThongKeDAO;
import fpoly.md18402.duan1_nhom4.Model.TopNV;
import fpoly.md18402.duan1_nhom4.R;


public class TopNvFragment extends Fragment {


    ListView lvTopNV;
    ArrayList<TopNV> list;
    TopNVAdapter topNVAdapter;
    ThongKeDAO thongKeDAO;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top_nv, container, false);
        lvTopNV = view.findViewById(R.id.lvTopnv);
        thongKeDAO = new ThongKeDAO(getActivity());
        list = (ArrayList<TopNV>) thongKeDAO.getTop10NhanVienDoanhThu();
        topNVAdapter = new TopNVAdapter(getActivity(), this, list);
        lvTopNV.setAdapter(topNVAdapter);
        return view;
    }
}