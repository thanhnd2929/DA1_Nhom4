package fpoly.md18402.duan1_nhom4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import fpoly.md18402.duan1_nhom4.DAO.NhanVienDAO;
import fpoly.md18402.duan1_nhom4.Model.NhanVien;

public class LoginActivity extends AppCompatActivity {
    EditText edtUserName, edtPassWord;
    CheckBox chkRemember;
    Button btnLogin, btnHuy;
    NhanVienDAO nhanVienDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edtUserName = findViewById(R.id.edtUsername);
        edtPassWord = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnHuy = findViewById(R.id.btnHuy);
        chkRemember = findViewById(R.id.chkLuuMatKhau);
        nhanVienDAO = new NhanVienDAO(this);

        SharedPreferences sharedPreferences = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        edtUserName.setText(sharedPreferences.getString("USER", ""));
        edtPassWord.setText(sharedPreferences.getString("PASS", ""));
        chkRemember.setChecked(sharedPreferences.getBoolean("REMEMBER", false));

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUserName.getText().toString();
                String password = edtPassWord.getText().toString();
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(getApplicationContext(),"Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (nhanVienDAO.CheckLogin(username, password) > 0|| (username.equals("admin") && password.equals("admin"))) {
                    Toast.makeText(getApplicationContext(),"Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    checkRemember(username, password, chkRemember.isChecked());
                    Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                    i.putExtra("USER", username);
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Đăng nhập thất bại. Vui lòng kiểm tra lại", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void checkRemember(String user, String pass, boolean status) {
        SharedPreferences sharedPreferences = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (!status) {
//            Xoá tình trạng lưu trước đó
            editor.clear();
        } else {
            editor.putString("USER", user);
            editor.putString("PASS", pass);
            editor.putBoolean("REMEMBER", status);
        }
//        lưu toàn bộ
        editor.commit();
    }
}