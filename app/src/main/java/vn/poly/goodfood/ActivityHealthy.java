package vn.poly.goodfood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import vn.poly.goodfood.Adapter.AdapterHealthy;
import vn.poly.goodfood.Adapter.AdapterKhaiVi;
import vn.poly.goodfood.model.HealThy;
import vn.poly.goodfood.model.KhaiVi;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ActivityHealthy extends AppCompatActivity {
    FirebaseDatabase data;
    DatabaseReference data2;
    List<HealThy> list = new ArrayList<HealThy>();
    ListView lvDanhSach;
    AdapterHealthy adapter;
    List<String>name = new ArrayList<>();
    ArrayAdapter adapter2;
    AutoCompleteTextView edtSearch;
    ImageView imgBack,imgGioHang,imgLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_healthy);
        Toast.makeText(this, "Please waiting when load data...", Toast.LENGTH_LONG).show();
        lvDanhSach = findViewById(R.id.list_danhSachHealThy);
        edtSearch = findViewById(R.id.edt_searchHealThy);
        imgBack = findViewById(R.id.back_healthy);
        imgGioHang = findViewById(R.id.image_shopHealThy);
        imgLoad = findViewById(R.id.img_loadHealThy);

        data = FirebaseDatabase.getInstance();
        data2 = data.getReference("HEALTHY");
        //LOAD ẢNH
        loadData();
        //SEARCH
        search();
        //BACK
        back();
        //GIỎ HÀNG
        gioHang();
    }

    public void loadData(){
        data2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> convert =snapshot.getChildren();
                for (DataSnapshot children : convert){
                    HealThy healThy =children.getValue(HealThy.class);
                    list.add(healThy);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        adapter = new AdapterHealthy(ActivityHealthy.this,list);
        lvDanhSach.setAdapter(adapter);
    }

    public void search(){
        data2.orderByValue().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> convert =snapshot.getChildren();
                for (DataSnapshot children : convert){
                    HealThy healThy =children.getValue(HealThy.class);
                    name.add(healThy.getNameAnh());
                    adapter2.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        adapter2 = new ArrayAdapter(ActivityHealthy.this, android.R.layout.simple_dropdown_item_1line,name);
        edtSearch.setAdapter(adapter2);

        edtSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                list.clear();
                data2.orderByChild("nameAnh").equalTo(adapter2.getItem(position).toString()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Iterable<DataSnapshot> convert =snapshot.getChildren();
                        for (DataSnapshot children : convert){
                            HealThy healThy =children.getValue(HealThy.class);
                            list.add(healThy);
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                adapter = new AdapterHealthy(ActivityHealthy.this,list);
                lvDanhSach.setAdapter(adapter);
                list.clear();

                imgLoad.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        edtSearch.setText("");
                        list.clear();
                        loadData();
                    }
                });
            }
        });
    }

    private void back(){
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void gioHang(){
        imgGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityHealthy.this,ActivityGioHang.class);
                startActivity(intent);
            }
        });
    }
}