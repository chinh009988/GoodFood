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

import vn.poly.goodfood.Adapter.AdapterCombo;
import vn.poly.goodfood.Adapter.AdapterDoAnGiamGia;
import vn.poly.goodfood.Adapter.AdapterDoUong;
import vn.poly.goodfood.model.Combo;
import vn.poly.goodfood.model.DoAnGiamGia;
import vn.poly.goodfood.model.DoUong;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ActivityComBo extends AppCompatActivity {
    FirebaseDatabase data ;
    DatabaseReference data2;
    ArrayList<Combo> list = new ArrayList<Combo>();
    AdapterCombo adapter;
    ListView lvDanhSach;
    List<String> name = new ArrayList<>();
    ArrayAdapter adapter2;
    AutoCompleteTextView edtSearch;
    ImageView imgBack,imgGioHang,imgLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_com_bo);
        lvDanhSach = findViewById(R.id.list_danhSachCombo);
        edtSearch = findViewById(R.id.search_Combo);
        imgBack = findViewById(R.id.back_Combo);
        imgGioHang = findViewById(R.id.image_shopCombo);
        imgLoad = findViewById(R.id.img_loadCombo);
        Toast.makeText(this, "Please waiting when load data...", Toast.LENGTH_LONG).show();

        data = FirebaseDatabase.getInstance();
        data2 = data.getReference("COMBO");

        //LOAD ẢNH
        loadData();
        //SEARCH
        Search();
        //BACK
        Back();
        //GIỎ HÀNG
        gioHang();
    }

    public void loadData(){
        data2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> conver = snapshot.getChildren();
                for(DataSnapshot children : conver){
                    Combo combo = children.getValue(Combo.class);
                    list.add(combo);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        adapter = new AdapterCombo(ActivityComBo.this,list);
        lvDanhSach.setAdapter(adapter);
    }

    public void Search(){
        data2.orderByValue().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> convert =snapshot.getChildren();
                for (DataSnapshot children : convert){
                    Combo combo =children.getValue(Combo.class);
                    name.add(combo.getTenAnh());
                    adapter2.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        adapter2 = new ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line,name);
        edtSearch.setAdapter(adapter2);
        //ITEM CLICK
        edtSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                list.clear();
                data2.orderByChild("tenAnh").equalTo(adapter2.getItem(position).toString()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Iterable<DataSnapshot> convert =snapshot.getChildren();
                        for (DataSnapshot children : convert){
                            Combo doAnGiamGia =children.getValue(Combo.class);
                            list.add(doAnGiamGia);
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                adapter = new AdapterCombo(ActivityComBo.this,list);
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

    private void Back(){
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
                Intent intent = new Intent(ActivityComBo.this,ActivityGioHang.class);
                startActivity(intent);
            }
        });
    }
}