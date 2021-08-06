package vn.poly.goodfood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import vn.poly.goodfood.Adapter.AdapterDoUong;
import vn.poly.goodfood.Adapter.AdapterKFC;
import vn.poly.goodfood.model.DoUong;
import vn.poly.goodfood.model.KFC;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ActivityKFC extends AppCompatActivity {
    FirebaseDatabase data;
    DatabaseReference data2;
    ArrayList<KFC> list = new ArrayList<KFC>();
    AdapterKFC  adapter;
    ListView lvDanhSach;
    List<String> name = new ArrayList<>();
    ArrayAdapter adapter2;
    ImageView imgBack,imgGioHang,imgLoad;
    AutoCompleteTextView edtSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_k_f_c);
        lvDanhSach = findViewById(R.id.list_kfc);
        imgBack = findViewById(R.id.back_kfc);
        imgGioHang = findViewById(R.id.image_shopKfc);
        imgLoad = findViewById(R.id.img_loadKFC);
        edtSearch = findViewById(R.id.search_kfc);
        Toast.makeText(this, "Please waiting when load data...", Toast.LENGTH_LONG).show();

        data = FirebaseDatabase.getInstance();
        data2 =data.getReference("COMBOKFC");

       //LOAD ẢNH
        loadData();
        //SEARCH
        search();
        //BACK
        Back();
        //GIỎ HÀNG
        gioHang();
    }

    public void loadData(){
        data2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> iterable = snapshot.getChildren();
                for (DataSnapshot children : iterable){
                    KFC kfc = children.getValue(KFC.class);
                    list.add(kfc);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        adapter = new AdapterKFC(ActivityKFC.this,list);
        lvDanhSach.setAdapter(adapter);
    }

    public void search(){
        data2.orderByValue().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> conver = snapshot.getChildren();
                for(DataSnapshot children : conver){
                    KFC kfc = children.getValue(KFC.class);
                    name.add(kfc.getTenAnh());
                    adapter2.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        adapter2 = new ArrayAdapter(ActivityKFC.this, android.R.layout.simple_dropdown_item_1line,name);
        edtSearch.setAdapter(adapter2);

        edtSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                list.clear();
                data2.orderByChild("tenAnh").equalTo(adapter2.getItem(position).toString()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Iterable<DataSnapshot> conver = snapshot.getChildren();
                        for(DataSnapshot children : conver){
                            KFC kfc = children.getValue(KFC.class);
                            list.add(kfc);
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                adapter = new AdapterKFC(ActivityKFC.this,list);
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
                Intent intent = new Intent(ActivityKFC.this,ActivityGioHang.class);
                startActivity(intent);
            }
        });
    }
}