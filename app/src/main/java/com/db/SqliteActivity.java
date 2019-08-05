package com.db;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.ilifesmart.utils.PersistentMgr;
import com.ilifesmart.weather.R;

public class SqliteActivity extends AppCompatActivity {

    public static final String TAG = "SqliteActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.sqlite_add, R.id.sqlite_delete, R.id.sqlite_update, R.id.sqlite_query, R.id.sqlite_upgrade})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sqlite_add:
                DBManager.getInstance().add();
                break;
            case R.id.sqlite_delete:
                DBManager.getInstance().delete();
                break;
            case R.id.sqlite_update:
                DBManager.getInstance().update();
                break;
            case R.id.sqlite_query:
                String result = DBManager.getInstance().select();
                Log.d(TAG, "onViewClicked: Result " + result);
                break;
            case R.id.sqlite_upgrade:
                int version = PersistentMgr.readKV("SQLITE_VERSION", 1);
                PersistentMgr.putKV("SQLITE_VERSION", version+1);
                break;
        }
    }
}
