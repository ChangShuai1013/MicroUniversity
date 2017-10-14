package com.example.changshuai.microuniversity.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.changshuai.microuniversity.entity.GwcInfoBean;
import com.example.changshuai.microuniversity.utils.Tool;

import java.util.ArrayList;
import java.util.List;

public class DbControl {


    private static final String CREATE_TABLE_NOTE = "create table if not exists gwc ( id integer primary key autoincrement, "
            + "shop_name text not null , price text not null , seat text not null , order_date text not null  );";


    private static final String DATABASE_NAME = "b_shop";

    private static final String TABLE_NAME = "gwc";


    private SQLiteDatabase db;


    public DbControl(Context ctx) {

        db = ctx.openOrCreateDatabase(DATABASE_NAME, 0, null);

//		db.execSQL(" drop table " + TABLE_NAME );
        db.execSQL(CREATE_TABLE_NOTE);

        int test = db.getVersion();
        Log.i("===db version===", String.valueOf(test));

        db.close();
    }

    public boolean addGwc(Context ctx, String shop_name, String seat, float price) {

        db = ctx.openOrCreateDatabase(DATABASE_NAME, 0, null);
        ContentValues values = new ContentValues();
//		shop_name ,  seat , order_date

        values.put("shop_name", shop_name);
        values.put("seat", seat);
        values.put("price", String.valueOf(price));
        values.put("order_date", Tool.getNowTime());

        boolean returnValue = db.insert(TABLE_NAME, null, values) > 0;
        db.close();
        return (returnValue);
    }


    /**
     * 删除
     *
     * @param ctx
     * @param id
     * @return
     */
    public boolean deleteGwc(Context ctx, long id) {

        db = ctx.openOrCreateDatabase(DATABASE_NAME, 0, null);

        boolean returnValue = false;

        int result = 0;
        result = db.delete(TABLE_NAME, "id=" + id, null);
        db.close();

        if (result == 1) {
            returnValue = true;
        }

        return returnValue;
    }


    public boolean deleteAllGwc(Context ctx) {

        db = ctx.openOrCreateDatabase(DATABASE_NAME, 0, null);

        boolean returnValue = false;

        int result = 0;
        result = db.delete(TABLE_NAME, null, null);
        db.close();

        if (result == 1) {
            returnValue = true;
        }

        return returnValue;
    }


    /**
     * 获取所有购物车
     *
     * @param ctx
     * @return
     */
    public List<GwcInfoBean> getAllGwc(Context ctx) {

        db = ctx.openOrCreateDatabase(DATABASE_NAME, 0, null);

        List<GwcInfoBean> list = new ArrayList();
//		shop_name ,  seat , order_date
        Cursor c = db.query(TABLE_NAME, new String[]{"id", "shop_name", "seat", "order_date", "price"}, null, null, null, null, null);

        int numRows = c.getCount();
        c.moveToFirst();
        for (int i = 0; i < numRows; i++) {

            GwcInfoBean object = new GwcInfoBean();
            object.setId(c.getInt(0));
            object.setShop_name(c.getString(1));
            object.setSeat(c.getString(2));
            object.setOrder_date(c.getString(3));
            object.setPrice(c.getString(4));

            list.add(i, object);
            c.moveToNext();
        }
        c.close();
        db.close();
        return list;
    }


}

