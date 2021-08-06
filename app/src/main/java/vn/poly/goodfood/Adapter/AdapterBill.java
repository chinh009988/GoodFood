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

import java.util.List;

import vn.poly.goodfood.R;
import vn.poly.goodfood.dao.BillDao;
import vn.poly.goodfood.dao.FoodDao;
import vn.poly.goodfood.model.Bill;
import vn.poly.goodfood.model.OrderFood;

public class AdapterBill extends BaseAdapter {
    Context context;
    List<Bill> list;
    BillDao billDao;
    Bill bill;

    public AdapterBill(Context context, List<Bill> list){
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
    public static class ViewHolder{
        TextView tvTen,tvPhone,tvPrice,tvAddress;
        ImageView imgDelete;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView== null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_don_hang, null, false);
            viewHolder.tvTen = convertView.findViewById(R.id.tv_adapter_don_hang_ten);
            viewHolder.tvPhone = convertView.findViewById(R.id.tv_adapter_don_hang_sdt);
            viewHolder.tvPrice = convertView.findViewById(R.id.tv_adapter_don_hang_price);
            viewHolder.tvAddress = convertView.findViewById(R.id.tv_adapter_don_hang_diaChi);
            viewHolder.imgDelete = convertView.findViewById(R.id.img_delete_adapter_don_hang);
            billDao = new BillDao(context);
            viewHolder.imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    billDao.deleteBillFood(list.get(position).getHoTen());
                    Bill bill = list.get(position);
                    list.remove(bill);
                    notifyDataSetChanged();
                }
            });
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Bill bill = list.get(position);
        viewHolder.tvTen.setText(bill.getHoTen());
        viewHolder.tvPhone.setText(String.valueOf(bill.getPhone()));
        viewHolder.tvAddress.setText(bill.getAddress());
        viewHolder.tvPrice.setText(String.valueOf(bill.getGiaBill()));
        return convertView;
    }
}
