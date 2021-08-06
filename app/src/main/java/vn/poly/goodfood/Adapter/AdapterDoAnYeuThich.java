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
import vn.poly.goodfood.model.DoAnYeuThich;
import vn.poly.goodfood.model.MonChinh;
import vn.poly.goodfood.model.OrderFood;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class AdapterDoAnYeuThich extends BaseAdapter {
    Context context;
    List<DoAnYeuThich>list;
    double tongTien=1;
    FoodDao foodDao;
    DoAnYeuThich doAnYeuThich;
    DatabaseReference reference;
    FirebaseDatabase database;
    String foodId ="";

    public AdapterDoAnYeuThich(Context context, List<DoAnYeuThich> list) {
        this.context = context;
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
        TextView txtTen,txtLuotThich,txtDonGia;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView==null){
            holder = new  ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_do_an_yeuthich,parent,false);
            holder.imgAnh =(ImageView)convertView.findViewById(R.id.image_anhDoAnYeuThich);
            holder.txtTen =(TextView)convertView.findViewById(R.id.txt_tenDoAnYeuThich);
            holder.txtLuotThich =(TextView)convertView.findViewById(R.id.txt_luotThich);
            holder.txtDonGia =(TextView)convertView.findViewById(R.id.txt_giaDoAnYeuThich);
            convertView.setTag(holder);
        }else {
            holder =(ViewHolder)convertView.getTag();
        }
        Locale locale = new Locale("en","US");
        NumberFormat fmt =NumberFormat.getCurrencyInstance(locale);

        Picasso.with(context).load(list.get(position).getLinkAnh()).into(holder.imgAnh);
        holder.txtTen.setText(list.get(position).getTenAnh());
        holder.txtLuotThich.setText(String.valueOf(list.get(position).getLuotThich()));
        holder.txtDonGia.setText(fmt.format(list.get(position).getDonGia()/100)+" VNĐ");
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

                DoAnYeuThich doAnYeuThich =list.get(position);
                Picasso.with(context).load(doAnYeuThich.getLinkAnh()).into(imgAnh);
                txtTen.setText(doAnYeuThich.getTenAnh());
                txtGia.setText("Giá: "+String.valueOf(doAnYeuThich.getDonGia())+"VNĐ");

                tongTien = 1;
                tongTien = doAnYeuThich.getDonGia() * Integer.parseInt(soLuong.getNumber());
                txtTong.setText(tongTien+"VNĐ");
                soLuong.setOnClickListener(new ElegantNumberButton.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tongTien = 0;
                        tongTien = doAnYeuThich.getDonGia() * Integer.parseInt(soLuong.getNumber());
                        txtTong.setText(tongTien+"VNĐ");
                    }
                });
                database = FirebaseDatabase.getInstance();
                reference =database.getReference("DOANYEUTHICH");
                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        foodDao = new FoodDao(context);
                        OrderFood orderFood = new OrderFood(txtTen.getText().toString(),Integer.parseInt(soLuong.getNumber()),doAnYeuThich.getDonGia());
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
