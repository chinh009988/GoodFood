package vn.poly.goodfood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import vn.poly.goodfood.Adapter.AdapterDoAnYeuThich;
import vn.poly.goodfood.model.DoAnGiamGia;
import vn.poly.goodfood.model.DoAnYeuThich;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ActivityDoAnYeuThich extends AppCompatActivity {
    FirebaseDatabase data;
    DatabaseReference data2;
    ArrayList<DoAnYeuThich> list = new ArrayList<DoAnYeuThich>();
    AdapterDoAnYeuThich adapter;
    ListView lv;
    List<String>name = new ArrayList<>();
    ArrayAdapter adapter2;
    AutoCompleteTextView edtSearch;
    ImageView imgBack,imgShop,imgLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_an_yeu_thich);
        lv = findViewById(R.id.list_DoAnYeuThich);
        edtSearch = findViewById(R.id.search_doAnYeuThich);
        imgBack = findViewById(R.id.back_doAnYeuThich);
        imgShop = findViewById(R.id.image_shopDoAnYeuThich);
        imgLoad = findViewById(R.id.img_loadDoAnYeuThich);
        Toast.makeText(this, "Please waiting when load data...", Toast.LENGTH_LONG).show();

        data = FirebaseDatabase.getInstance();
        data2 =data.getReference("DOANYEUTHICH");
        //LOAD ẢNH
        loadData();
        //BACK
        Back();
        //SEARCH
        search();
        //GIỎ HÀNG
        gioHang();
    }

    public void loadData(){
        data2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> convert =snapshot.getChildren();
                for (DataSnapshot children : convert){
                    DoAnYeuThich doAnYeuThich =children.getValue(DoAnYeuThich.class);
                    list.add(doAnYeuThich);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        adapter = new AdapterDoAnYeuThich(ActivityDoAnYeuThich.this,list);
        lv.setAdapter(adapter);
    }

    public void search(){
        data2.orderByValue().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> convert =snapshot.getChildren();
                for (DataSnapshot children : convert){
                    DoAnYeuThich doAnYeuThich =children.getValue(DoAnYeuThich.class);
                    name.add(doAnYeuThich.getTenAnh());
                    adapter2.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        adapter2 = new ArrayAdapter(ActivityDoAnYeuThich.this, android.R.layout.simple_dropdown_item_1line,name);
        edtSearch.setAdapter(adapter2);

        edtSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                list.clear();
                data2.orderByChild("tenAnh").equalTo(adapter2.getItem(position).toString()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Iterable<DataSnapshot> convert =snapshot.getChildren();
                        for (DataSnapshot children : convert){
                            DoAnYeuThich doAnYeuThich =children.getValue(DoAnYeuThich.class);
                            list.add(doAnYeuThich);
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                adapter = new AdapterDoAnYeuThich(ActivityDoAnYeuThich.this,list);
                lv.setAdapter(adapter);
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

    private void Back(){
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    finish();
            }
        });

    }

    public void gioHang(){
        imgShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityDoAnYeuThich.this,ActivityGioHang.class);
                startActivity(intent);
            }
        });
    }
}