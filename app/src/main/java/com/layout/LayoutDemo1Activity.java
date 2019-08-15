package com.layout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.android.material.snackbar.Snackbar;
import com.ilifesmart.weather.R;

import java.util.ArrayList;
import java.util.List;

public class LayoutDemo1Activity extends AppCompatActivity {

    @BindView(R.id.rvToDoList)
    RecyclerView mRvToDoList;
    @BindView(R.id.main_content)
    CoordinatorLayout mMainContent;

    @BindView(R.id.toolbar)
    Toolbar mToolBar;

    private ItemAdapter adapter;
    private static List<String> beans = new ArrayList<>();
    static {
        for (int i = 0; i < 20; i++) {
            beans.add("Item-" + (i+1));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_demo1);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        adapter = new ItemAdapter();
        adapter.setOnItemClickListener(new ItemAdapter.OnItemClicker() {
            @Override
            public void onItemClick(View view, int position) {
                Snackbar.make(mMainContent, beans.get(position), Snackbar.LENGTH_SHORT)
                        .show();
            }
        });
        mRvToDoList.setAdapter(adapter);

        mToolBar.setTitle("");
        setSupportActionBar(mToolBar);
    }

    private static class ItemHolder extends RecyclerView.ViewHolder {
        public ItemHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    private static class ItemAdapter extends RecyclerView.Adapter<ItemHolder> {

        private OnItemClicker listener;

        @NonNull
        @Override
        public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ItemHolder(LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
            String content = beans.get(position);
            ((TextView)(holder.itemView)).setText(content);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClick(holder.itemView, position);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return beans.size();
        }

        public void setOnItemClickListener(OnItemClicker listener) {
            this.listener = listener;
        }

        public static interface OnItemClicker {
            public void onItemClick(View view, int position);
        }
    }

}
