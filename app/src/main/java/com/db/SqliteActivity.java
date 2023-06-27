package com.db;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.ilifesmart.utils.PersistentMgr;
import com.ilifesmart.weather.R;

public class SqliteActivity extends AppCompatActivity {

    public static final String TAG = "SqliteActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);
    }

    public void onViewClicked(View view) {
        if (view.getId() == R.id.sqlite_add) {
            DBManager.getInstance().add();
        } else if (view.getId() == R.id.sqlite_delete) {
            DBManager.getInstance().delete();
        } else if (view.getId() == R.id.sqlite_update) {
            DBManager.getInstance().update();
        } else if (view.getId() == R.id.sqlite_query) {
            String result = DBManager.getInstance().select();
            Log.d(TAG, "onViewClicked: Result " + result);
        } else if (view.getId() == R.id.sqlite_upgrade) {
            int version = PersistentMgr.readKV("SQLITE_VERSION", 1);
            PersistentMgr.putKV("SQLITE_VERSION", version+1);
        }
    }
}
