package vn.poly.goodfood.mysqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import vn.poly.goodfood.dao.BillDao;
import vn.poly.goodfood.dao.FoodDao;
import vn.poly.goodfood.dao.UserDao;

public class MySql extends SQLiteOpenHelper {
    public MySql(Context context) {
        super(context, "user.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UserDao.USER_SQL);
        db.execSQL(FoodDao.ORDER_FOOD_SQL);
        db.execSQL(BillDao.BILL_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("Drop table if exists " + UserDao.TABLE_USER);
        db.execSQL("Drop table if exists "+ FoodDao.TABLE_ORDER);
        db.execSQL("Drop table if exists "+ BillDao.TABLE_BILL);
    }
}
