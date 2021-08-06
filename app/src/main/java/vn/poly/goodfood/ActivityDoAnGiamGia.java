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
import vn.poly.goodfood.Adapter.AdapterDoAnGiamGia;
import vn.poly.goodfood.model.DoAnGiamGia;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class ActivityDoAnGiamGia extends AppCompatActivity {
    FirebaseDatabase data;
    DatabaseReference data2;
    ArrayList<DoAnGiamGia> list = new ArrayList<DoAnGiamGia>();
    AdapterDoAnGiamGia adpter;
    ListView lv;
    AutoCompleteTextView edtSearch;
    List<String> name = new ArrayList<>();
    ArrayAdapter adapter2;
    ImageView btnBack,imgShop,imgLoad;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_an_giam_gia);
        lv = findViewById(R.id.list_danhSachDoAnGiamGia);
        edtSearch = findViewById(R.id.search_doAnGiamGia);
        btnBack = findViewById(R.id.back_doAnGiamGia);
        imgShop = findViewById(R.id.image_shopDoAnGiamGia);
        imgLoad = findViewById(R.id.img_loadDoAnGiamGia);
        Toast.makeText(this, "Please waiting when load data...", Toast.LENGTH_LONG).show();

        //THAM CHIẾU FIREBASE
        data = FirebaseDatabase.getInstance();
        data2 =data.getReference("DOANGIAMGIA");
        //SEARCH
        Search();
        //LOAD ẢNH
        loadData();
        //Back
        Back();
        //GIỎ HÀNG
        gioHang();
    }

    public void Search(){
        data2.orderByValue().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> convert =snapshot.getChildren();
                for (DataSnapshot children : convert){
                    DoAnGiamGia doAnGiamGia =children.getValue(DoAnGiamGia.class);
                    name.add(doAnGiamGia.getTenAnh());
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
                            DoAnGiamGia doAnGiamGia =children.getValue(DoAnGiamGia.class);
                            list.add(doAnGiamGia);
                            adpter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                adpter = new AdapterDoAnGiamGia(ActivityDoAnGiamGia.this,list);
                lv.setAdapter(adpter);
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

    public void loadData(){
        data2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> convert =snapshot.getChildren();
                for (DataSnapshot children : convert){
                    DoAnGiamGia doAnGiamGia =children.getValue(DoAnGiamGia.class);
                    list.add(doAnGiamGia);
                    adpter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        adpter = new AdapterDoAnGiamGia(ActivityDoAnGiamGia.this,list);
        lv.setAdapter(adpter);
    }

    private void Back(){
        btnBack.setOnClickListener(new View.OnClickListener() {
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
                Intent intent = new Intent(ActivityDoAnGiamGia.this,ActivityGioHang.class);
                startActivity(intent);
            }
        });
    }
}