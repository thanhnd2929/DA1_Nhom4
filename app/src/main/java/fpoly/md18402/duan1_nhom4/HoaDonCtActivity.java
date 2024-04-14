package fpoly.md18402.duan1_nhom4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import fpoly.md18402.duan1_nhom4.Adapter.GiaySpinnerAdapter;
import fpoly.md18402.duan1_nhom4.Adapter.HoaDonCtAdapter;
import fpoly.md18402.duan1_nhom4.DAO.CTHoaDonDAO;
import fpoly.md18402.duan1_nhom4.DAO.GiayDAO;
import fpoly.md18402.duan1_nhom4.Model.CTHD;
import fpoly.md18402.duan1_nhom4.Model.Giay;

public class HoaDonCtActivity extends AppCompatActivity {
    ListView lvHDCT;
    EditText edtMaHD, edtSoLuong;
    Spinner spinnerGiay;
    TextView tvTongTien;
    ImageView btnBack;
    Button btnSave;
    GiaySpinnerAdapter giaySpinnerAdapter;
    ArrayList<Giay> listGiay;
    CTHoaDonDAO ctHoaDonDAO;
    ArrayList<CTHD> list;
    HoaDonCtAdapter hoaDonCtAdapter;
    CTHD cthd;
    GiayDAO giayDAO;
    String tenGiay;
    int maGiay, giaTien;
    int soHD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoa_don_ct);

        edtMaHD = findViewById(R.id.edtMaHD);
        edtSoLuong = findViewById(R.id.edtSoLuong);
        lvHDCT = findViewById(R.id.lvHDCT);
        tvTongTien = findViewById(R.id.tvTongTien);
        btnSave = findViewById(R.id.btnSaveHDCT);
        spinnerGiay = findViewById(R.id.spGiay);
        btnBack = findViewById(R.id.btnBack);

        ctHoaDonDAO = new CTHoaDonDAO(this);
// lấy mã hoá đơn từ hoa don fragment
        soHD = getIntent().getIntExtra("soHD", 0);
        edtMaHD.setText(String.valueOf(soHD));

        capNhatLv();
//        spinner
        giayDAO = new GiayDAO(this);
        listGiay = new ArrayList<>();
        listGiay = (ArrayList<Giay>) giayDAO.getAll();
        giaySpinnerAdapter = new GiaySpinnerAdapter(this, listGiay);
        spinnerGiay.setAdapter(giaySpinnerAdapter);

        spinnerGiay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maGiay = listGiay.get(position).getMaGiay();
                giaTien = listGiay.get(position).getGiaMua();
                tenGiay = listGiay.get(position).getTenGiay();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String soLuong = edtSoLuong.getText().toString().trim();
                if (soLuong.isEmpty()) {
                    Toast.makeText(HoaDonCtActivity.this, "Nhập số lượng", Toast.LENGTH_SHORT).show();
                    edtSoLuong.requestFocus(); // cho phép người dùng nhập mà ko cần click edt
                    return;
                } else {
                    cthd = new CTHD();
                    cthd.setSoLuong(Integer.parseInt(soLuong));

                    cthd.setMaHD(soHD);
                    cthd.setMaGiay(maGiay);
                    cthd.setGiaMua(giaTien);

                    boolean isExist = false; // Biến để kiểm tra xem sản phẩm đã tồn tại trong danh sách hay không
                    for (CTHD item : list) {
                        if (item.getMaGiay() == maGiay) {
                            // Nếu sản phẩm đã tồn tại trong danh sách, chỉ cần tăng số lượng
                            item.setSoLuong(item.getSoLuong() + Integer.parseInt(soLuong));
                            ctHoaDonDAO.updateCTHD(item);
                            edtSoLuong.setText("");
                            isExist = true;
                            break;
                        }
                    }

                    if (!isExist) {
                        // Nếu sản phẩm chưa tồn tại trong danh sách, thêm mới vào
                        if (ctHoaDonDAO.addCTHD(cthd) > 0) {
                            Toast.makeText(HoaDonCtActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                            edtSoLuong.setText("");
                            list.clear();
                            list.addAll(ctHoaDonDAO.getAll(soHD));
                            hoaDonCtAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(HoaDonCtActivity.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Nếu sản phẩm đã tồn tại trong danh sách, chỉ cần cập nhật lại danh sách và hiển thị
                        hoaDonCtAdapter.notifyDataSetChanged();
                    }
                }
                capNhatLv();
            }
        });

    }

    public void capNhatLv() {
        list = (ArrayList<CTHD>) ctHoaDonDAO.getAll(soHD);
//        Log.d("List: "+(ArrayList<CTHD>) ctHoaDonDAO.getAll(soHD).toString());
        hoaDonCtAdapter = new HoaDonCtAdapter(this,list);
        hoaDonCtAdapter.setOnDeleteSuccessListener(new HoaDonCtAdapter.OnDeleteSuccessListener() {
            @Override
            public void onDeleteSuccess() {
                int tongTien = hoaDonCtAdapter.tongTien();
                tvTongTien.setText(tongTien + " VND");
            }
        });
        lvHDCT.setAdapter(hoaDonCtAdapter);
        int tongTien = hoaDonCtAdapter.tongTien();
        tvTongTien.setText("Tổng Tiền: "+tongTien+" VND");
    }
}