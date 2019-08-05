package com.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.ilifesmart.ToolsApplication;
import com.ilifesmart.utils.PersistentMgr;

public class DBManager {
    public static final String TAG = "DBManager";

    private static Object mLock = new Object();
    private Context context;
    private static DBManager instance;
    private SQLiteDatabase writeableDatabase;

    private DBManager(Context context) {
        this.context = context;
        int version = PersistentMgr.readKV("SQLITE_VERSION", 1);
        DBHelper dbHelper = new DBHelper(context, version);
        writeableDatabase = dbHelper.getWritableDatabase();
    }

    public static DBManager getInstance() {
        if (instance == null) {
            synchronized (mLock) {
                if (instance == null) {
                    instance = new DBManager(ToolsApplication.getContext());
                }
            }
        }
        return instance;
    }

    public void add() {
        ContentValues contentValues = new ContentValues();

        byte _byte = Byte.MAX_VALUE;
        contentValues.put("_byte", _byte);

        long _long = Long.MAX_VALUE;
        contentValues.put("_long", _long);

        String _text = "字符串";
        contentValues.put("_text", _text);

        short _short = Short.MAX_VALUE;
        contentValues.put("_short", _short);

        int _int = Integer.MAX_VALUE;
        contentValues.put("_int", _int);

        float _float = Float.MAX_VALUE;
        contentValues.put("_float", _float);

        double _double = Double.MAX_VALUE;
        contentValues.put("_double", _double);

        boolean _boolean = true;
        contentValues.put("_boolean", _boolean);

        byte[] _byteArr = {Byte.MIN_VALUE, Byte.MAX_VALUE};
        contentValues.put("_blob", _byteArr);


        long result = writeableDatabase.insert("table1", null, contentValues);
        Log.d(TAG, "add: result " + result);

    }

    public void delete() {
        writeableDatabase.delete("table1", "_int = ?", new String[] {Integer.MAX_VALUE + ""});
    }

    public void update() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("_text", "修改后的字符串");
        writeableDatabase.update("table1", contentValues, "_text = ?", new String[] {"字符串"});
    }

    public String select() {
        Cursor cursor = writeableDatabase.query("table1", null, null, null, null, null, null);
        int position = cursor.getPosition();
        Log.d(TAG, "select: init " + position);

        String result = "";
        while (cursor.moveToNext()) {
            byte _byte = (byte) cursor.getShort(cursor.getColumnIndex("_byte"));
            long _long = cursor.getLong(cursor.getColumnIndex("_long"));
            String _text = cursor.getString(cursor.getColumnIndex("_text"));
            short _short = cursor.getShort(cursor.getColumnIndex("_short"));
            int _int = cursor.getInt(cursor.getColumnIndex("_int"));
            float _float = cursor.getFloat(cursor.getColumnIndex("_float"));
            double _double = cursor.getDouble(cursor.getColumnIndex("_double"));
            boolean _boolean = cursor.getInt(cursor.getColumnIndex("_boolean")) == 1 ? true : false;
            byte[] _byteArr = cursor.getBlob(cursor.getColumnIndex("_blob"));

            result += String.format("_byte = %s, _long = %s, _text = %s, _short = %s, _int = %s, _float = %s, _double = %s, _boolean = %s, _byteArr = %s",
                    _byte, _long, _text, _short, _int, _float, _double, _boolean, _byteArr) + "\n";
        }

        return result;
    }
}
