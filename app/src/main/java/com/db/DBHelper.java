package com.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    private static final String TAG = "DBHelper";
    private static final String DB_NAME ="test.db";

    public DBHelper(@Nullable Context context, int version) {
        super(context, DB_NAME, null, version);
    }

    /*
    * 创建数据库的时候调用，若数据库已经存在，则不再调用
    * */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table table1(" +
                "_byte byte," +
                "_long long," +
                "_text text," +
                "_short short," +
                "_int int," +
                "_float float," +
                "_double double," +
                "_boolean boolean," +
                "_blob blob" +
                ")"
                );
    }

    /*
    * oldversion:旧数据库版本,
    * newVersion:新数据库版本.
    * */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 数据库更新，添加字段什么的..
        // 不设置break，是确保用户当前版本和以后的版本兼容.
        Log.d(TAG, "onUpgrade: oldVersion " + oldVersion);
        Log.d(TAG, "onUpgrade: newVersion " + newVersion);

        switch (newVersion) {
            case 1:
            case 2:
                db.execSQL("create table table2(_text text)");
            case 3:
                db.execSQL("create table table3(_text text)");
            case 4:
                db.execSQL("alter table table2 ADD _long long"); // 新增字段
            case 5:
                db.execSQL("drop table table3"); // 删表
                break;
        }
    }
}
