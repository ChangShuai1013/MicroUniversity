package com.example.changshuai.microuniversity.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.changshuai.microuniversity.entity.PeripheralShop;
import com.example.changshuai.microuniversity.utils.MySQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class PeripheralShopDao {
    private MySQLiteOpenHelper mySQLiteOpenHelper;

    public PeripheralShopDao(Context context) {
        mySQLiteOpenHelper = new MySQLiteOpenHelper(context);
    }

    public boolean add(String name , String address, String context, String telephone){
        SQLiteDatabase db = mySQLiteOpenHelper.getWritableDatabase();
        //db.execSQL("insert into info (name,phone) values (?,?)", new Object[]{name,phone});
        //一个map用来存放数据 key 列名  values 值
        ContentValues values = new ContentValues();
        values.put("name",name);
        values.put("context", context);
        values.put("address", address);
        values.put("telephone",telephone);
        //组拼sql语句实现的
        long result = db.insert("peripheralshop", null, values);
        db.close();
        if(result==-1){
            return false;
        }else{
            return true;
        }
    }

    public int delete(String name){
        SQLiteDatabase db = mySQLiteOpenHelper.getWritableDatabase();
        //db.execSQL("delete from info where name = ?", new Object[]{name});
        int result = db.delete("peripheralshop", "name = ?", new String[]{name});
        db.close();
        return result;
    }

//    public int update(String name , String newphone){
//        SQLiteDatabase db = mySQLiteOpenHelper.getWritableDatabase();
//        //db.execSQL("update info set phone=? where name=?", new Object[]{newphone,name});
//        ContentValues values = new ContentValues();
//        values.put("phone", newphone);
//        int result = db.update("info", values, " name=?", new String[]{name});
//        db.close();
//        return result;
//    }


    public String find(String name){
        String phone = null;
        SQLiteDatabase db = mySQLiteOpenHelper.getReadableDatabase();
        //Cursor  cursor = db.rawQuery("select phone from info where name=?", new String[]{name});
        Cursor cursor = db.query("info", new String[]{"phone"}, "name=?", new String[]{name}, null, null, null);
        if(cursor.moveToNext()){
            phone = cursor.getString(0);
        }
        cursor.close();//释放内存。
        db.close();
        return phone;
    }

    public List<PeripheralShop> findAll(){
        List<PeripheralShop> ps = new ArrayList<PeripheralShop>();
        SQLiteDatabase db = mySQLiteOpenHelper.getReadableDatabase();
        Cursor cursor =  db.query("peripheralshop", null, null, null, null, null, null);
        while(cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String context = cursor.getString(2);
            String address = cursor.getString(3);
            String phone = cursor.getString(4);
            PeripheralShop peripheralShop = new PeripheralShop();
            peripheralShop.setId(id);
            peripheralShop.setName(name);
            peripheralShop.setAddress(address);
            peripheralShop.setContext(context);
            peripheralShop.setTelephone(phone);
            ps.add(peripheralShop);
            peripheralShop = null;
        }
        db.close();
        return ps;
    }
}
