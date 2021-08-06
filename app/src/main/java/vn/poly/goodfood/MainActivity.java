package vn.poly.goodfood;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import vn.poly.goodfood.CheckConectsion.CheckConectSion;
import vn.poly.goodfood.dao.UserDao;
import vn.poly.goodfood.model.User;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText edtUsername,edtPassword;
    TextView txtSignup,txtForgotPass;
    Button btnSignin;
    CheckBox chkRememberme;
    UserDao userDao;
    String user,pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        edtUsername=findViewById(R.id.edUsername);
        edtPassword=findViewById(R.id.edPassword);
        btnSignin=findViewById(R.id.btn_signin);
        chkRememberme=findViewById(R.id.chk_checkBox);
        txtSignup = findViewById(R.id.txt_Signup);
        txtForgotPass = findViewById(R.id.txt_ForgotPass);
        userDao = new UserDao(this);


        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtUsername.getText().toString().equals("") || edtPassword.getText().toString().equals("")){
                    Toast.makeText(MainActivity.this,"Vui lòng điền đầy đủ thông tin!",Toast.LENGTH_SHORT).show();
                    return;
                }
                boolean kiemTra = userDao.CheckSignIn(edtUsername.getText().toString(),edtPassword.getText().toString());

                if (kiemTra){
                    if (CheckConectSion.haveNetworkConnection(getApplicationContext())){
                        rememberUser(edtUsername.getText().toString(),edtPassword.getText().toString(),chkRememberme.isChecked());
                        finish();
                    }else {
                        CheckConectSion.showToast_Short(getApplicationContext(),"Không có kết nối internet!!");
                    }
                }else {
                    Toast.makeText(MainActivity.this,"Usename hoặc Password không đúng!",Toast.LENGTH_SHORT).show();

                }
            }


        });

        txtSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckConectSion.haveNetworkConnection(getApplicationContext())){
                    Intent intent = new Intent(MainActivity.this,ActivitySignup.class);
                    startActivity(intent);
                }else {
                    CheckConectSion.showToast_Short(getApplicationContext(),"Không có kết nối internet!!");
                }
            }
        });

        txtForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(MainActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_forgot_password);
                EditText edUsername = dialog.findViewById(R.id.ed_forgot_user);
                EditText edpass = dialog.findViewById(R.id.edt_forgot_pass);
                EditText edfinal = dialog.findViewById(R.id.edt_forgot_pass_final);
                Button btnForgot = dialog.findViewById(R.id.btn_forgot_change);
                Button btnCancle = dialog.findViewById(R.id.btn_DialogCancle);
                userDao = new UserDao(MainActivity.this);
                user = userDao.username();
                pass = userDao.password();
                btnForgot.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String username = edUsername.getText().toString();
                        String password = edpass.getText().toString();
                        String passFinal = edfinal.getText().toString();
                        int check = password.compareTo(passFinal);
                        if (!username.equals(user)){
                            Toast.makeText(MainActivity.this, "Sai tài khoản !!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (check !=0){
                            Toast.makeText(MainActivity.this, "Vui lòng nhập mật khẩu giống nhau !", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (userDao.changePassword(user,password)){
                            Toast.makeText(MainActivity.this, "Reset mật khẩu thành công !!", Toast.LENGTH_SHORT).show();
                            edUsername.setText("");
                            edpass.setText("");
                            edfinal.setText("");
                        }else {
                            Toast.makeText(MainActivity.this, "Reset mật khẩu thất bại !!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                btnCancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }

    public void rememberUser(String usename, String pass, boolean status) {
        SharedPreferences sharedPreferences = getSharedPreferences("Use_File.txt",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (!status){
            editor.clear();
        }else {
            editor.putString("USENAME",usename);
            editor.putString("PASS",pass);
            editor.putBoolean("REMEMBER",status);
        }
        editor.commit();
    }

    @Override
    public void onBackPressed() {

    }
}



