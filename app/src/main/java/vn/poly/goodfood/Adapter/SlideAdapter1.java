package vn.poly.goodfood.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import vn.poly.goodfood.ActivityGioHang;
import vn.poly.goodfood.ActivitySignup;
import vn.poly.goodfood.R;
import vn.poly.goodfood.Slide.SlideItem1;
import vn.poly.goodfood.dao.FoodDao;
import vn.poly.goodfood.model.OrderFood;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class SlideAdapter1 extends RecyclerView.Adapter<SlideAdapter1.SlideViewHolder> {
    private List<SlideItem1>list;
    private ViewPager2 viewPager2;
    View view;
    Context context;
    double tongTien=1;
    FoodDao foodDao;
    String foodId="";
    SlideItem1 slideItem1;


    public SlideAdapter1(Context context,List<SlideItem1> list, ViewPager2 viewPager2) {
        this.context = context;
        this.list = list;
        this.viewPager2 = viewPager2;
    }

    @NonNull
    @Override
    public SlideViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.silde_item_container,parent,false);
        SlideViewHolder holder1 = new SlideViewHolder(view);
        return holder1;
    }

    @Override
    public void onBindViewHolder(@NonNull SlideViewHolder holder, int position) {
        holder.setImage(list.get(position));
        view.setOnClickListener(new View.OnClickListener() {
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

                SlideItem1 slideItem1 =list.get(position);
                imgAnh.setImageResource(slideItem1.getImage());
                txtTen.setText(slideItem1.getTen());
                txtGia.setText("Giá: "+String.valueOf(slideItem1.getGia())+"VNĐ");

                tongTien = 1;
                tongTien = slideItem1.getGia() * Integer.parseInt(soLuong.getNumber());
                txtTong.setText(tongTien+"VNĐ");
                soLuong.setOnClickListener(new ElegantNumberButton.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tongTien = 1;
                        tongTien = slideItem1.getGia() * Integer.parseInt(soLuong.getNumber());
                        txtTong.setText(tongTien+"VNĐ");
                    }
                });
                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        foodDao = new FoodDao(context);
                        OrderFood orderFood = new OrderFood(txtTen.getText().toString(),Integer.parseInt(soLuong.getNumber()),slideItem1.getGia());
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
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class SlideViewHolder extends RecyclerView.ViewHolder{

        private RoundedImageView imageView;
        public SlideViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageSlide);
        }

        void setImage(SlideItem1 slideItem1){
            imageView.setImageResource(slideItem1.getImage());
        }
    }

}
