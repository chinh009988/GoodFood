package vn.poly.goodfood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import vn.poly.goodfood.CheckConectsion.CheckConectSion;
import vn.poly.goodfood.dao.UserDao;
import vn.poly.goodfood.model.User;

public class ActivitySignup extends AppCompatActivity {
    EditText edName, edPass,edMail;
    Button btnSignup;
    UserDao userDao;
    ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        edMail = findViewById(R.id.edt_Signup_Email);
        edName = findViewById(R.id.edt_Signup_Usename);
        edPass = findViewById(R.id.edt_Signup_Pass);
        btnSignup = findViewById(R.id.btn_Signup);
        imgBack = findViewById(R.id.img_backSignUp);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ten = edName.getText().toString();
                String pas = edPass.getText().toString();
                String emai = edMail.getText().toString();
                String regex = "^\\w{1,}@\\w{1,}\\.\\w{1,}$";
                if (ten.equals("") || pas.equals("")|| emai.equals("") ){
                    Toast.makeText(ActivitySignup.this, "Vui lòng điền đầy đủ thông tin !!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!edMail.getText().toString().matches(regex)) {
                    Toast.makeText(ActivitySignup.this, "Email không đúng định dạng", Toast.LENGTH_LONG).show();
                    return;
                }
                userDao = new UserDao(ActivitySignup.this);
                User user = new User();
                user.setUsername(edName.getText().toString());
                user.setPassword(edPass.getText().toString());
                user.setEmail(edMail.getText().toString());
                if (CheckConectSion.haveNetworkConnection(getApplicationContext())){
                    if(userDao.insertUser(user) >0){
                        Toast.makeText(ActivitySignup.this, "Đăng kí thành công !!", Toast.LENGTH_SHORT).show();
                        edMail.setText("");
                        edName.setText("");
                        edPass.setText("");
                        finish();
                    }else {
                        Toast.makeText(ActivitySignup.this, "Đăng kí không thành công !!", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    CheckConectSion.showToast_Short(getApplicationContext(),"Không có kết nối internet!!");
                }
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        finish();
    }

}