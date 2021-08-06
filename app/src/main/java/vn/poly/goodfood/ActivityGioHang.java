package vn.poly.goodfood;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import vn.poly.goodfood.Adapter.AdapterCart;
import vn.poly.goodfood.dao.BillDao;
import vn.poly.goodfood.dao.FoodDao;
import vn.poly.goodfood.model.Bill;
import vn.poly.goodfood.model.OrderFood;
import vn.poly.goodfood.utils.SpacingItemDecorator;

import static vn.poly.goodfood.R.drawable.boder;

public class ActivityGioHang extends AppCompatActivity {
    ImageView imgBack;
    TextView tvCartPrice;
    Button btnGioHang;
    RecyclerView lvCart;
    FoodDao foodDao;
    List<OrderFood> list = new ArrayList<>();
    AdapterCart adapter;
    BillDao billDao;
    FoodDao foodDa;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        imgBack = findViewById(R.id.img_BackGioHang);
        btnGioHang = findViewById(R.id.btn_gioHang);
        tvCartPrice = findViewById(R.id.tv_cart_price);
        lvCart = findViewById(R.id.lv_cart);

        loadList();
        BackGH();
        gioHang();
    }

    public void BackGH(){
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void loadList(){
        foodDao = new FoodDao(ActivityGioHang.this);
        list = foodDao.getAllFoodsList();
        adapter = new AdapterCart(ActivityGioHang.this,list);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        lvCart.setLayoutManager(layoutManager);
        SpacingItemDecorator item = new SpacingItemDecorator(20);
        lvCart.addItemDecoration(item);
        lvCart.setAdapter(adapter);
        tvCartPrice.setText(foodDao.tongGia()+" VNĐ");
    }
    public  void gioHang(){
        btnGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(ActivityGioHang.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_thanh_toan);
                EditText edTen =dialog.findViewById(R.id.ed_thanhToan_ten);
                EditText edSdt =dialog.findViewById(R.id.ed_thanhToan_sdt);
                EditText edDc =dialog.findViewById(R.id.ed_thanhToan_address);
                TextView tvGia = dialog.findViewById(R.id.tv_diaLog_thanhToan_gia);
                Button btnAdd = dialog.findViewById(R.id.btn_thanhToan_add);
                Button btnCancel = dialog.findViewById(R.id.btn_thanhToan_huy);

                tvGia.setText(String.valueOf(foodDao.tongGia()));
                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        billDao = new BillDao(ActivityGioHang.this);
                        Bill bill = new Bill(edTen.getText().toString(),Integer.parseInt(edSdt.getText().toString()),edDc.getText().toString(),Double.parseDouble(tvGia.getText().toString()));
                        if (billDao.insertBillFood(bill)>0){
                            Toast.makeText(ActivityGioHang.this, "Đặt hàng thành công. Đơn sẽ được vận chuyển đi. Bạn có thể xem đơn hàng của mình trong mục 'Đơn hàng'", Toast.LENGTH_SHORT).show();
                            foodDao = new FoodDao(ActivityGioHang.this);
                            foodDao.deleteAllFoods();
                            list.clear();
                            adapter = new AdapterCart(ActivityGioHang.this,list);
                            lvCart.setAdapter(adapter);

                        }else {
                            Toast.makeText(ActivityGioHang.this, "Thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }
}