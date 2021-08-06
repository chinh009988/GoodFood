package vn.poly.goodfood.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import vn.poly.goodfood.model.OrderFood;
import vn.poly.goodfood.model.User;
import vn.poly.goodfood.mysqlite.MySql;

public class FoodDao {
    private MySql mySql;
    private SQLiteDatabase sql;
    public FoodDao(Context context){
        mySql = new MySql(context);
        sql = mySql.getWritableDatabase();
    }
    public static final String TABLE_ORDER = "orderFood";
    public static final String COLUMN_NAME = "nameFood";
    public static final String COLUMN_NUMBER = "numberFood";
    public static final String COLUMN_PRICE = "priceFood";
    public static final String COLUMN_THANHTIEN = "thanhTien";
    public static final String ORDER_FOOD_SQL ="Create table "+TABLE_ORDER+" (" +

            COLUMN_NAME+" text , " +
            COLUMN_NUMBER+" int ,"+
            COLUMN_PRICE+" double ,"+
            COLUMN_THANHTIEN+" double );";
    public int insertOderFood(OrderFood f){
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME,f.getNameFood());
        values.put(COLUMN_NUMBER,f.getNumberFood());
        values.put(COLUMN_PRICE,f.getPriceFood());
        if (sql.insert(TABLE_ORDER,null,values)<0){
            return -1;
        }else {
            return 1;
        }
    }
    public int deleteOderFood(String name){
        if( sql.delete(TABLE_ORDER,COLUMN_NAME+"=?",new String[]{name})<0){
            return -1;
        }else {
            return 1;
        }
    }

    public List<OrderFood> getAllFoodsList(){
        List<OrderFood> list = new ArrayList<>();
        String sq = "Select * from "+TABLE_ORDER;
        Cursor cursor =sql.rawQuery(sq,null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false){
            OrderFood orderFood = new OrderFood();
            orderFood.setNameFood(cursor.getString(0));
            orderFood.setNumberFood(cursor.getInt(1));
            orderFood.setPriceFood(cursor.getDouble(2));
            list.add(orderFood);
            cursor.moveToNext();
        }
        return list;
    }
    public List<OrderFood> deleteAllFoods(){
        List<OrderFood> list = new ArrayList<>();
        String sq = "Delete  from "+TABLE_ORDER;
        Cursor cursor =sql.rawQuery(sq,null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false){
            OrderFood orderFood = new OrderFood();
            orderFood.setNameFood(cursor.getString(0));
            orderFood.setNumberFood(cursor.getInt(1));
            orderFood.setPriceFood(cursor.getDouble(2));
            list.add(orderFood);
            cursor.moveToNext();
        }
        return list;
    }
    public double tongGia(){
        double gia =0;
        List<OrderFood> list = new ArrayList<>();
        String sq = "Select sum(numberFood * priceFood) from "+TABLE_ORDER;
        Cursor  cursor = sql.rawQuery(sq, null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false){
            gia =cursor.getDouble(0);
            cursor.moveToNext();
        }
        cursor.close();
        return gia;
    }
}
