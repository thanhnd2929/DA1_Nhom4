package fpoly.md18402.duan1_nhom4.Fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import fpoly.md18402.duan1_nhom4.Adapter.TopNvHdAdapter;
import fpoly.md18402.duan1_nhom4.DAO.ThongKeDAO;
import fpoly.md18402.duan1_nhom4.Model.TopNvHD;
import fpoly.md18402.duan1_nhom4.R;


public class TopNvHdFragment extends Fragment {

    ListView lvTopNvHd;
    ArrayList<TopNvHD> list;
    TopNvHdAdapter topNvHdAdapter;
    ThongKeDAO thongKeDAO;
    EditText edtStartDate, edtEndDate;
    Button btnSubmit;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    int mYear, mMonth, mDay;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_top_nv_hd, container, false);
        lvTopNvHd = view.findViewById(R.id.lvTopNvHd);

        edtStartDate = view.findViewById(R.id.edtStartDate);
        edtEndDate = view.findViewById(R.id.edtEndDate);
        btnSubmit = view.findViewById(R.id.btnTopHD);

        edtStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog d = new DatePickerDialog(getActivity(), 0, mDateTuNgay, mYear, mMonth, mDay);
                d.show();
            }
        });

        edtEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog d = new DatePickerDialog(getActivity(), 0, mDateDenNgay, mYear, mMonth, mDay);
                d.show();
            }
        });

        list = new ArrayList<>();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tuNgay = edtStartDate.getText().toString();
                String denNgay = edtEndDate.getText().toString();
                thongKeDAO = new ThongKeDAO(getActivity());
                ArrayList<TopNvHD> newData = (ArrayList<TopNvHD>) thongKeDAO.getTop10NhanVienBySoLuongHD(tuNgay, denNgay);
                if (!newData.isEmpty()) {
                    list.clear();
                    list.addAll(newData);
                    if (topNvHdAdapter == null) {
                        topNvHdAdapter = new TopNvHdAdapter(getActivity(), list);
                        lvTopNvHd.setAdapter(topNvHdAdapter);
                    } else {
                        topNvHdAdapter.notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(getActivity(), "Khoảng thời gian bạn chọn không có hoá đơn", Toast.LENGTH_SHORT).show();
                    Log.d("zzzz", "Không có dữ liệu trả về từ cơ sở dữ liệu");
                    list.clear();
                    if (topNvHdAdapter != null) {
                        topNvHdAdapter.notifyDataSetChanged();
                    }
                }
            }
        });

        return view;
    }


    DatePickerDialog.OnDateSetListener mDateTuNgay = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            mYear = year;
            mMonth = month;
            mDay = dayOfMonth;
            GregorianCalendar c = new GregorianCalendar(mYear, mMonth, mDay);
            edtStartDate.setText(sdf.format(c.getTime()));
        }
    };

    DatePickerDialog.OnDateSetListener mDateDenNgay = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            mYear = year;
            mMonth = month;
            mDay = dayOfMonth;
            GregorianCalendar c = new GregorianCalendar(mYear, mMonth, mDay);
            edtEndDate.setText(sdf.format(c.getTime()));
        }
    };
}