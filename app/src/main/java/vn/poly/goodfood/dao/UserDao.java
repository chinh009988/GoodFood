package vn.poly.goodfood.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import vn.poly.goodfood.model.User;
import vn.poly.goodfood.mysqlite.MySql;

import java.util.ArrayList;
import java.util.List;

public class UserDao  {
    private MySql mySql;
    private SQLiteDatabase sql;
    public UserDao(Context context){
        mySql = new MySql(context);
        sql = mySql.getWritableDatabase();
    }
    public static final String COLUMN_NAME = "ten";
    public static final String COLUMN_PASS = "pass";
    public static final String COLUMN_EMAIL = "email";
    public static final String TABLE_USER = "nguoiDung";
    public static final String USER_SQL ="Create table "+TABLE_USER+" (" +
            COLUMN_NAME+" text primary key, " +
            COLUMN_PASS+" text, " +
            COLUMN_EMAIL+" text )";

    public int insertUser(User u){
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME,u.getUsername());
        values.put(COLUMN_PASS,u.getPassword());
        values.put(COLUMN_EMAIL,u.getEmail());
        if (sql.insert(TABLE_USER,null,values) <0){
            return -1;
        }else {
            return 1;
        }
    }
    public boolean CheckSignIn(String use,String pass){
        String c = "Select * from "+TABLE_USER+" where "+COLUMN_NAME+" = '"+use+"' and "+COLUMN_PASS+" = '"+pass+"'";
        Cursor cursor = sql.rawQuery(c, null);
        if (cursor.getCount() !=0 ){
            return true;
        }else {
            return false;
        }
    }
    public String password(){
        String pass = null;
        String sq="Select pass from "+TABLE_USER;
        Cursor cursor = sql.rawQuery(sq, null);
        cursor.moveToFirst();
        while (cursor.isAfterLast()==false){
            pass = cursor.getString(0);
            cursor.moveToNext();
        }
        return pass;
    }
    public String username(){
        String user = null;
        String sq="Select ten from "+TABLE_USER;
        Cursor cursor = sql.rawQuery(sq, null);
        cursor.moveToFirst();
        while (cursor.isAfterLast()==false){
            user = cursor.getString(0);
            cursor.moveToNext();

        }
        return user;
    }
    public long updatePasswordUser(User user){
        ContentValues contentValues  = new ContentValues();
        contentValues.put(COLUMN_PASS,user.getPassword());

        String selection = COLUMN_NAME+"=?";
        String[] selectionArgs = {user.getPassword()};

        int count = sql.update(TABLE_USER,contentValues,selection,selectionArgs);

        return count;

    }
    public boolean changePassword(String user,String mkMoi){
        ContentValues values = new ContentValues();
        values.put(COLUMN_PASS,mkMoi);
        int row = sql.update(TABLE_USER, values, COLUMN_NAME+"=?",new String[]{user} );
        if (row > 0){
            return true;
        }else {
            return false;
        }

    }
}
