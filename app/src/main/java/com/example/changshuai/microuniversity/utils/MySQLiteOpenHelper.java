package com.example.changshuai.microuniversity.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {
    public MySQLiteOpenHelper(Context context) {
        super(context, "MicroUniversity.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table peripheralshop (_id integer primary key autoincrement, name varchar(50), context varchar(100), address varchar(100), telephone varchar(20))");
        db.execSQL("create table peripheralfood (_id integer primary key autoincrement, name varchar(50), context varchar(100), address varchar(100), telephone varchar(20))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
