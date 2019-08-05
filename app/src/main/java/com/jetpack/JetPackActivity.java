package com.jetpack;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.ilifesmart.utils.Utils;
import com.ilifesmart.weather.R;
import com.jetpack.dagger2.WuKong;
import com.jetpack.model.LiveDataTimerViewModel;
import com.jetpack.model.MainViewModel;
import com.jetpack.model.RecyclerViewModel;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class JetPackActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

    private TextView scoreA;
    private TextView scoreB;
    private RecyclerView mRecycler;

    private MainViewModel mMainViewModel;
    private LiveDataTimerViewModel mLiveDataModel;
    private RecyclerViewModel mViewModel;

    @Inject
    WuKong mWuKong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jetpack);

        Log.d(TAG, "onCreate: CanonicalName: " + MainViewModel.class.getCanonicalName());
        Log.d(TAG, "onCreate: isAssignableFrom: " + AndroidViewModel.class.isAssignableFrom(MainViewModel.class));

        /*
         * 此处通过追踪源码发现是在Activity.onDestroy中调用onCleared来清除内存
         *
         * */
        mMainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mLiveDataModel = ViewModelProviders.of(this).get(LiveDataTimerViewModel.class);
        mViewModel = ViewModelProviders.of(this).get(RecyclerViewModel.class);

        initView();
    }

    private void initView() {
        scoreA = findViewById(R.id.score_A);
        scoreB = findViewById(R.id.score_B);
        mRecycler = findViewById(R.id.recycler);

        setScore(scoreA, mMainViewModel.getInitialCountA());
        setScore(scoreB, mMainViewModel.getInitialCountB());

        findViewById(R.id.player_A).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setScore(scoreA, mMainViewModel.getCurrentCountA());
            }
        });

        findViewById(R.id.player_B).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setScore(scoreB, mMainViewModel.getCurrentCountB());
            }
        });

        findViewById(R.id.room).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.startActivity(JetPackActivity.this, RoomActivity.class);
            }
        });

        final Observer<Long> observer = new Observer<Long>() {
            @Override
            public void onChanged(Long aLong) {
                Log.d(TAG, "onChanged: update " + aLong);
            }
        };

        mLiveDataModel.getElapsedTime().observe(this, observer);

        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        BeanAdapter adapter = new BeanAdapter();
        mViewModel.getDatas().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                adapter.setBeans(strings);
            }
        });
        mRecycler.setAdapter(adapter);

        // Dagger2:依赖注入框架 TODO:此处有问题，待追究.
//        XiYouComponent xiYouComponent = DaggerXiYouComponent.builder()
//                .xiYouModule(new XiYouModule())
//                .build();
//        xiYouComponent.inject(this);
//        Log.d(TAG, "initView: wukong.useGinGuBang " + mWuKong.useGinGuBang());
    }

    private void setScore(TextView view, int score) {
        view.setText(String.valueOf(score));
    }

    private class BeanHolder extends RecyclerView.ViewHolder {
        public BeanHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    private class BeanAdapter extends RecyclerView.Adapter<BeanHolder> {

        private List<String> beans = new ArrayList<>();
        public void setBeans(List<String> beans) {
            this.beans = beans;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public BeanHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
            return new BeanHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull BeanHolder holder, int position) {
            ((TextView)holder.itemView).setText(beans.get(position));
        }

        @Override
        public int getItemCount() {
            return beans.size();
        }

    }
}
