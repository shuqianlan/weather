package com.ilifesmart.appbarlayoutdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedListAdapterCallback;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.adapters.BottomRefreshAdapter;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.snackbar.Snackbar;
import com.ilifesmart.ArticleActivity;
import com.ilifesmart.utils.Utils;
import com.ilifesmart.weather.R;

import java.util.ArrayList;
import java.util.List;

public class AppBarActivity extends AppCompatActivity {

    public static final String TAG = "AppBarActivity";
    private static List<String> datas = new ArrayList();

    private static int MAX = 10;
    static {
        for (int index = 0; index < MAX; index++) {
            datas.add("Item-"+(index+1));
        }
    }

    private RecyclerView appbar_items;
    private Toolbar toolbar;
    private AppBarLayout app_bar;
    private SwipeRefreshLayout refresh_cont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_bar);

        appbar_items = findViewById(R.id.appbar_items);

        BottomRefreshAdapter adapter = new BottomRefreshAdapter.Builder<String>(new BottomRefreshAdapter.OnCreateViewListener() {
            @Override
            public View onCreatView(ViewGroup parent, int viewType) {
                return LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
            }
        }, new BottomRefreshAdapter.OnBindViewListener<String>() {
            @Override
            public void onBindView(View view, String bean) {
                ((TextView) view).setText(bean);
            }
        }, String.class, new BottomRefreshAdapter.InstanceBeansCallBack<String>() {
            @Override
            public SortedListAdapterCallback<String> instance(BottomRefreshAdapter<String> adapter) {
                return new SortedListAdapterCallback<String>(adapter) {
                    @Override
                    public int compare(String o1, String o2) {
                        int post1 = Integer.parseInt(o1.substring("Item-".length()));
                        int post2 = Integer.parseInt(o2.substring("Item-".length()));
                        return post1 - post2;
                    }

                    @Override
                    public boolean areContentsTheSame(String oldItem, String newItem) {
                        return oldItem.equals(newItem);
                    }

                    @Override
                    public boolean areItemsTheSame(String item1, String item2) {
                        return item1 == item2;
                    }
                };
            }
        })
        .setOnViewtypeGetListener(new BottomRefreshAdapter.OnGetViewTypeListener<String>() {
            @Override
            public int getViewType(String bean) {
                return 0;
            }
        })
        .setOnItemViewClickListener(new BottomRefreshAdapter.OnItemViewClickListener<String>() {
            @Override
            public void onClickItemView(View view, String bean) {
                Snackbar.make(app_bar, bean, Snackbar.LENGTH_SHORT)
                        .show();
            }
        })
        .setOnLoadMoreDataListener(new BottomRefreshAdapter.OnLoadMoreDataListener<String>() {
            @Override
            public void onLoadData(BottomRefreshAdapter<String> adapter) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        List<String> beans = new ArrayList<>();
                        for (int i = MAX; i < MAX+20; i++) {
                            beans.add("Item-"+(i+1));
                        }

                        MAX += 20;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.extendDatas(beans);
                            }
                        });
                    }
                }).start();
            }
        })
        .setDatas(datas)
        .build();

        appbar_items.setHasFixedSize(true);
        appbar_items.setAdapter(adapter);

        refresh_cont = findViewById(R.id.swipe_refresh_cont);
        refresh_cont.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d(TAG, "onRefresh: >>>>> ");
                refresh_cont.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refresh_cont.setRefreshing(false);
                    }
                }, 2000);
            }
        });

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
                Snackbar.make(app_bar, "点击了"+item.getTitle(), Snackbar.LENGTH_SHORT)
                        .setActionTextColor(getResources().getColor(R.color.colorAccent))
                        .show();

                if (item.getItemId() == R.id.action_template) {
                    Utils.startActivity(AppBarActivity.this, ArticleActivity.class);
                }
                return false;
            }
        });
        toolbar.setOverflowIcon(getResources().getDrawable(R.drawable.appbar_menu)); // 设置自定义图标替换'三个点'
// 点击三个点后的样式可以通过popupTheme来设定.

        ((CollapsingToolbarLayout)findViewById(R.id.collapsingToolbarLayout)).setTitle("wuzhenghua");

        app_bar = findViewById(R.id.app_bar);
        app_bar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                appBarLayout.setAlpha(1.0f);
            }
        });
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
