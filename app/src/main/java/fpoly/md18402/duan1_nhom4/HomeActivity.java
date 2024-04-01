package fpoly.md18402.duan1_nhom4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import fpoly.md18402.duan1_nhom4.DAO.NhanVienDAO;
import fpoly.md18402.duan1_nhom4.Fragments.ChangePassFragment;
import fpoly.md18402.duan1_nhom4.Fragments.DoanhThuFragment;
import fpoly.md18402.duan1_nhom4.Fragments.HoaDonFragment;

import fpoly.md18402.duan1_nhom4.Fragments.TopNvFragment;

import fpoly.md18402.duan1_nhom4.Fragments.khachHang.KhachHangFragment;
import fpoly.md18402.duan1_nhom4.Fragments.nhanVien.NhanVienFragment;

import fpoly.md18402.duan1_nhom4.Fragments.sneaker.SneakerManagementFragment;
import fpoly.md18402.duan1_nhom4.Fragments.sneaker_type.ListTypeSneakerFragment;
import fpoly.md18402.duan1_nhom4.Model.NhanVien;

public class HomeActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView nav;
    BottomNavigationView bottomNavigationView;
    View view;
    TextView tvuser;
    NhanVienDAO nhanVienDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        drawerLayout = findViewById(R.id.drawerLayout);
        toolbar = findViewById(R.id.toolBar);
        nav = findViewById(R.id.nav);
        bottomNavigationView = findViewById(R.id.bottomMenu);

//        gan toolbar
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        view = nav.getHeaderView(0);
        tvuser = view.findViewById(R.id.tvWelcome);
        Intent intent = getIntent();
        String user = intent.getStringExtra("USER");
        if (user.equalsIgnoreCase("admin")) {
            nav.getMenu().findItem(R.id.QuanLyNhanVien).setVisible(true);
            Toast.makeText(this, "Wellcome Admin", Toast.LENGTH_SHORT).show();
        } else {
            nhanVienDao = new NhanVienDAO(this);
            NhanVien nv = nhanVienDao.getID(user);
            String userName = nv.getHoTen();
            tvuser.setText("Wellcome: " + userName);
            nav.getMenu().findItem(R.id.DoiMatKhau).setVisible(true);
            bottomNavigationView.getMenu().findItem(R.id.TopNV).setVisible(false);
            bottomNavigationView.getMenu().findItem(R.id.TopBanGiay).setVisible(false);
            bottomNavigationView.getMenu().findItem(R.id.DoanhThu).setVisible(false);
            Toast.makeText(this, "Wellcome nhân viên", Toast.LENGTH_SHORT).show();
        }

        HoaDonFragment fragment = new HoaDonFragment();
        replaceFragment(fragment);

        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();

                if (id == R.id.QuanLyHoaDon) {
                    HoaDonFragment hoaDonFragment = new HoaDonFragment();
                    replaceFragment(hoaDonFragment);
                } else if (id == R.id.QuanLyLoaiGiay) {
                    replaceFragment(new ListTypeSneakerFragment());
                } else if (id == R.id.QuanLyGiay) {
                    replaceFragment(new SneakerManagementFragment());
                } else if (id == R.id.QuanLyKhachHang) {
                    KhachHangFragment khachHangFragment = new KhachHangFragment();
                    replaceFragment(khachHangFragment);
                } else if (id == R.id.DoiMatKhau) {
                    ChangePassFragment changePassFragment = new ChangePassFragment();
                    replaceFragment(changePassFragment);
                } else if (id == R.id.QuanLyNhanVien) {
                    NhanVienFragment nhanVienFragment = new NhanVienFragment();
                    replaceFragment(nhanVienFragment);
                } else if (id == R.id.DangXuat) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                    builder.setTitle("Thông báo");
                    builder.setMessage("Bạn có muốn đăng xuất không?");
                    builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(HomeActivity.this, "Đăng xuất Succ", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                    });
                    builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.show();
                }
                getSupportActionBar().setTitle(item.getTitle());
                drawerLayout.close();
                return true;
            }
        });

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int idBottom = item.getItemId();

                if (idBottom == R.id.TopBanGiay) {

                } else if (idBottom == R.id.TopNV) {
                    TopNvFragment topNvFragment = new TopNvFragment();
                    replaceFragment(topNvFragment);
                } else if (idBottom == R.id.DoanhThu) {
                    DoanhThuFragment doanhThuFragment = new DoanhThuFragment();
                    replaceFragment(doanhThuFragment);
                }
                getSupportActionBar().setTitle(item.getTitle());
                return true;
            }
        });


    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.frame, fragment).commit();
    }
}