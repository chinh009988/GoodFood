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

import vn.poly.goodfood.Adapter.AdapterAnVat;
import vn.poly.goodfood.Adapter.AdapterMonPhu;
import vn.poly.goodfood.model.AnVat;
import vn.poly.goodfood.model.MonPhu;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ActivityAnVat extends AppCompatActivity {
    FirebaseDatabase data;
    DatabaseReference data2;
    List<AnVat> list = new ArrayList<AnVat>();
    ListView lvDanhSach;
    AdapterAnVat adapter;
    List<String>name = new ArrayList<>();
    ArrayAdapter adapter2;
    AutoCompleteTextView edtSearch;
    ImageView imgBack,imgGioHang,imgLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_an_vat);
        Toast.makeText(this, "Please waiting when load data...", Toast.LENGTH_LONG).show();
        lvDanhSach = findViewById(R.id.list_danhSachanVat);
        edtSearch = findViewById(R.id.edt_searchAnVat);
        imgBack = findViewById(R.id.back_AnVat);
        imgGioHang = findViewById(R.id.image_shopAnVat);
        imgLoad = findViewById(R.id.img_loadAnVat);

        data = FirebaseDatabase.getInstance();
        data2 = data.getReference("ANVAT");
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
                    AnVat anVat =children.getValue(AnVat.class);
                    list.add(anVat);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        adapter = new AdapterAnVat(ActivityAnVat.this,list);
        lvDanhSach.setAdapter(adapter);
    }

    public void search(){
        data2.orderByValue().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> convert =snapshot.getChildren();
                for (DataSnapshot children : convert){
                    AnVat anVat =children.getValue(AnVat.class);
                    name.add(anVat.getNameAnh());
                    adapter2.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        adapter2 = new ArrayAdapter(ActivityAnVat.this, android.R.layout.simple_dropdown_item_1line,name);
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
                            AnVat anVat =children.getValue(AnVat.class);
                            list.add(anVat);
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                adapter = new AdapterAnVat(ActivityAnVat.this,list);
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
                Intent intent = new Intent(ActivityAnVat.this,ActivityGioHang.class);
                startActivity(intent);
            }
        });
    }
}