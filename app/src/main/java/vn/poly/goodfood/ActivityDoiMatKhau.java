package vn.poly.goodfood;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import vn.poly.goodfood.dao.UserDao;
import vn.poly.goodfood.model.User;

public class ActivityDoiMatKhau extends AppCompatActivity {
    ImageView imgBack;
    UserDao userDao;
    EditText edPassBefor,edPassAfter,edPassfinal;
    Button btnChange;
    List<User> list = new ArrayList<>();
    String user,pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doi_mat_khau);
        imgBack = findViewById(R.id.img_backDoiMatKhau);
        edPassBefor = findViewById(R.id.ed_pass_befor);
        edPassAfter = findViewById(R.id.ed_pass_after);
        edPassfinal = findViewById(R.id.ed_pass_final);
        btnChange = findViewById(R.id.btn_change_pass);
        userDao = new UserDao(ActivityDoiMatKhau.this);
        user = userDao.username();
        pass = userDao.password();
        Toast.makeText(this, pass+"-"+user, Toast.LENGTH_SHORT).show();

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mkCu = edPassBefor.getText().toString();
                String mkMoi = edPassAfter.getText().toString();
                String mkMoi2 = edPassfinal.getText().toString();
                int check = mkMoi.compareTo(mkMoi2);
                if (! mkCu.equals(pass)){
                    Toast.makeText(ActivityDoiMatKhau.this, "Vui lòng nhập đúng pass trước !", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (check != 0){
                    Toast.makeText(ActivityDoiMatKhau.this, "Vui lòng nhập Mật khẩu mới giống nhau !", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (userDao.changePassword(user,mkMoi)){
                    Toast.makeText(ActivityDoiMatKhau.this, "Đổi mật khẩu thành công !!", Toast.LENGTH_SHORT).show();
                    edPassBefor.setText("");
                    edPassAfter.setText("");
                    edPassfinal.setText("");
                }else {
                    Toast.makeText(ActivityDoiMatKhau.this, "Đổi mật khẩu thất bại !!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        BackDoiMatKhau();
    }

    public void BackDoiMatKhau(){
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}