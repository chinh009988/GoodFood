package vn.poly.goodfood.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import vn.poly.goodfood.model.Bill;
import vn.poly.goodfood.model.OrderFood;
import vn.poly.goodfood.mysqlite.MySql;

public class BillDao {
    private MySql mySql;
    private SQLiteDatabase sql;
    public BillDao(Context context){
        mySql = new MySql(context);
        sql = mySql.getWritableDatabase();
    }
    public static final String TABLE_BILL = "billFood";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_PRICE = "priceFood";
    public static final String COLUMN_ADDRESS = "address";
    public static final String BILL_SQL ="Create table "+TABLE_BILL+" (" +
            COLUMN_NAME+" text , " +
            COLUMN_PHONE+" phone ,"+
            COLUMN_ADDRESS+" text ,"+
            COLUMN_PRICE+" double );";

    public int insertBillFood(Bill b){
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME,b.getHoTen());
        values.put(COLUMN_PHONE,b.getPhone());
        values.put(COLUMN_ADDRESS,b.getAddress());
        values.put(COLUMN_PRICE,b.getGiaBill());
        if (sql.insert(TABLE_BILL,null,values)<0){
            return -1;
        }else {
            return 1;
        }
    }
    public int deleteBillFood(String name){
        if( sql.delete(TABLE_BILL,COLUMN_NAME+"=?",new String[]{name})<0){
            return -1;
        }else {
            return 1;
        }
    }
    public List<Bill> getAllBillsList(){
        List<Bill> list = new ArrayList<>();
        String sq = "Select * from "+TABLE_BILL;
        Cursor cursor =sql.rawQuery(sq,null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false){
            Bill bill = new Bill();
            bill.setHoTen(cursor.getString(0));
            bill.setPhone(cursor.getInt(1));
            bill.setAddress(cursor.getString(2));
            bill.setGiaBill(cursor.getDouble(3));
            list.add(bill);
            cursor.moveToNext();
        }
        return list;
    }

    public int updateBill(Bill bill,String id) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, bill.getHoTen());
        contentValues.put(COLUMN_PHONE, bill.getPhone());
        contentValues.put(COLUMN_ADDRESS, bill.getAddress());
        contentValues.put(COLUMN_PRICE, bill.getGiaBill());


        return sql.update(TABLE_BILL, contentValues, COLUMN_NAME+"=?", new String[]{id});
    }
}
