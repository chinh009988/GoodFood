package vn.poly.goodfood;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import vn.poly.goodfood.Adapter.AdapterBill;
import vn.poly.goodfood.dao.BillDao;
import vn.poly.goodfood.model.Bill;

public class ActivityDonHang extends AppCompatActivity {
    ImageView imgBack;
    List<Bill> list = new ArrayList<>();
    AdapterBill adapter;
    BillDao billDao;
    ListView lvHoaDon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_don_hang);
        imgBack = findViewById(R.id.img_BackDonHang);
        lvHoaDon = findViewById(R.id.lv_don_hang);
        loatData();
        BackDonHang();
    }
    public void BackDonHang(){
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void loatData(){
        billDao = new BillDao(ActivityDonHang.this);
        list = billDao.getAllBillsList();
        adapter = new AdapterBill(ActivityDonHang.this,list);
        lvHoaDon.setAdapter(adapter);
    }
}