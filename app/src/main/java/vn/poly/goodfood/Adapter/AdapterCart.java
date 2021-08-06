package vn.poly.goodfood.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.poly.goodfood.ActivityGioHang;
import vn.poly.goodfood.R;
import vn.poly.goodfood.dao.FoodDao;
import vn.poly.goodfood.model.OrderFood;

public class AdapterCart extends RecyclerView.Adapter<AdapterCart.ViewHolder> {

    Context context;
    List<OrderFood> list;
    FoodDao foodDao;
    OrderFood orderFood;
    LayoutInflater inflate;
    View view1;

    public AdapterCart(Context context,List<OrderFood> list){
        this.context = context;
        this.list = list;
        this.inflate =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view1 =inflate.inflate(R.layout.item_gio_hang,null);
        ViewHolder view =new ViewHolder(view1);
        return view;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        orderFood =list.get(position);
        holder.tvTen.setText(orderFood.getNameFood());
        holder.tvGia.setText(orderFood.getNumberFood()*orderFood.getPriceFood()+"VNĐ");
        holder.txtSoLuong.setText("Số lượng: "+orderFood.getNumberFood());


        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foodDao = new FoodDao(context);
                foodDao.deleteOderFood(list.get(position).getNameFood());
                OrderFood orderFood = list.get(position);
                list.remove(orderFood);
                notifyDataSetChanged();
                foodDao.tongGia();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvTen,tvGia,txtSoLuong;
        ImageView imgDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTen = itemView.findViewById(R.id.tv_adapter_cart_tenMon);
            tvGia = itemView.findViewById(R.id.tv_adapter_cart_ThanhTien);
            imgDelete = itemView.findViewById(R.id.img_delete_adapter_cart);
            txtSoLuong = itemView.findViewById(R.id.txt_soLuong_gioHang);

        }
    }
}
