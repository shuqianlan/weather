package com.ilifesmart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.snackbar.Snackbar;
import com.ilifesmart.weather.R;

import java.util.ArrayList;
import java.util.List;

public class AppBarActivity extends AppCompatActivity {

    public static final String TAG = "AppBarActivity";
    private static List<String> datas = new ArrayList();

    static {
        for (int index = 0; index < 40; index++) {
            datas.add("Item-"+(index+1));
        }
    }

    private RecyclerView appbar_items;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_bar);

        appbar_items = findViewById(R.id.appbar_items);
        ItemAdapter adapter = new ItemAdapter().setOnItemClickListener(new ItemAdapter.OnItemClickListener() {
            @Override
            public void onChildItemClick(View view, int position) {
                Log.d(TAG, "onChildItemClick: position " + position);

                Snackbar.make(view, datas.get(position), Snackbar.LENGTH_SHORT)
                        .setAction("ahahah", null)
                        .setActionTextColor(getResources().getColor(R.color.colorAccent))
                        .show();
            }
        });
        appbar_items.setAdapter(adapter);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "点击了导航按钮", Snackbar.LENGTH_SHORT)
                        .setActionTextColor(getResources().getColor(R.color.colorAccent))
                        .show();
            }
        });
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Snackbar.make(item.getActionView(), "点击了"+item.getTitle(), Snackbar.LENGTH_SHORT)
                        .setActionTextColor(getResources().getColor(R.color.colorAccent))
                        .show();
                return false;
            }
        });
// toolbar.setOverflowIcon(getResources().getDrawable(R.drawable.back)); // 设置自定义图标替换'三个点'
// 点击三个点后的样式可以通过popupTheme来设定.

        ((CollapsingToolbarLayout)findViewById(R.id.collapsingToolbarLayout)).setTitle("wuzhenghua");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appbar_menu, menu);
        return true;
    }

    private static class ItemHolder extends RecyclerView.ViewHolder {

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    private static class ItemAdapter extends RecyclerView.Adapter<ItemHolder> {

        private OnItemClickListener listener;

        @NonNull
        @Override
        public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
            return new ItemHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull ItemHolder holder, final int position) {
            ((TextView)holder.itemView).setText(datas.get(position));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onChildItemClick(v, position);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }

        public ItemAdapter setOnItemClickListener(OnItemClickListener listener) {
            this.listener = listener;
            return this;
        }

        public static interface OnItemClickListener {
            void onChildItemClick(View view, int position);
        }
    }
}
