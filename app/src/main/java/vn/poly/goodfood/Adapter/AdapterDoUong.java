package vn.poly.goodfood.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import vn.poly.goodfood.R;
import vn.poly.goodfood.dao.FoodDao;
import vn.poly.goodfood.model.DoUong;
import vn.poly.goodfood.model.MonChinh;
import vn.poly.goodfood.model.OrderFood;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class AdapterDoUong extends BaseAdapter {
    Context context;
    List<DoUong> list;
    double tongTien=1;
    String foodId ="";
    DatabaseReference reference;
    FirebaseDatabase database;
    FoodDao foodDao;
    DoUong doUong;

    public AdapterDoUong(Context context, List<DoUong> list){
        this.context= context;
        this.list = list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    public class ViewHolder{
        ImageView imgAnh;
        TextView tvTen,tvGia;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_do_uong,parent,false);
            viewHolder.imgAnh = convertView.findViewById(R.id.img_anhDoUong);
            viewHolder.tvTen = convertView.findViewById(R.id.txt_tenDoUong);
            viewHolder.tvGia = convertView.findViewById(R.id.txt_giaDoUong);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Locale locale = new Locale("en","US");
        NumberFormat fmt =NumberFormat.getCurrencyInstance(locale);

        Picasso.with(context).load(list.get(position).getUrlAnh()).into(viewHolder.imgAnh);
        viewHolder.tvTen.setText(list.get(position).getTenAnh());
        viewHolder.tvGia.setText("Giá: "+fmt.format(list.get(position).getDonGia()/100)+" VND");
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_them_vao_giohang);

                ImageView imgDimiss =dialog.findViewById(R.id.img_Dialog_dimiss);
                ImageView imgAnh = dialog.findViewById(R.id.img_Dialog_anhFood);
                TextView txtTen = dialog.findViewById(R.id.txt_Dialog_tenMon);
                TextView txtGia = dialog.findViewById(R.id.txt_Dialog_donGia);
                ElegantNumberButton soLuong = dialog.findViewById(R.id.numberButton_SoLuong);
                TextView txtTong = dialog.findViewById(R.id.txt_Dialog_TongTien);
                Button btnAdd = dialog.findViewById(R.id.btn_Dialog_ADD);

                DoUong doUong =list.get(position);
                Picasso.with(context).load(doUong.getUrlAnh()).into(imgAnh);
                txtTen.setText(doUong.getTenAnh());
                txtGia.setText("Giá: "+String.valueOf(doUong.getDonGia())+"VNĐ");

                tongTien = 1;
                tongTien = doUong.getDonGia() * Integer.parseInt(soLuong.getNumber());
                txtTong.setText(tongTien+"VNĐ");
                soLuong.setOnClickListener(new ElegantNumberButton.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tongTien = 0;
                        tongTien = doUong.getDonGia() * Integer.parseInt(soLuong.getNumber());
                        txtTong.setText(tongTien+"VNĐ");
                    }
                });
                database = FirebaseDatabase.getInstance();
                reference =database.getReference("DOUONG");
                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        foodDao = new FoodDao(context);
                        OrderFood orderFood = new OrderFood(txtTen.getText().toString(),Integer.parseInt(soLuong.getNumber()),doUong.getDonGia());
                        if (foodDao.insertOderFood(orderFood) >0){
                            Toast.makeText(context, "Thêm giỏ hàng thành công !!", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(context, "Thêm thất bại ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                imgDimiss.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        return convertView;
    }
}
