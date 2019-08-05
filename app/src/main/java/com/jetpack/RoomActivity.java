package com.jetpack;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.ilifesmart.weather.R;
import com.jetpack.demo.User;
import com.jetpack.demo.UserViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RoomActivity extends AppCompatActivity {

    @BindView(R.id.user_cont)
    RecyclerView mUserCont;
    private UserViewModel userViewModel;

    private UsersAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        ButterKnife.bind(this);

        adapter = new UsersAdapter();
        mUserCont.setLayoutManager(new LinearLayoutManager(this));
        mUserCont.setAdapter(adapter);

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        userViewModel.getAllUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                adapter.setUsers(users);
            }
        });

    }

    private class UserHolder extends RecyclerView.ViewHolder {
        private User user;
        public UserHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void onBind(User user) {
            this.user = user;
            ((TextView)itemView).setText(user.firstName+","+user.lastName);
        }
    }

    private class UsersAdapter extends RecyclerView.Adapter<UserHolder> {

        public List<User> users = new ArrayList<>();

        public void setUsers(List<User> users) {
            this.users = users;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);

            return new UserHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull UserHolder holder, int position) {
            holder.onBind(users.get(position));
        }

        @Override
        public int getItemCount() {
            return users.size();
        }
    }

    private AtomicInteger itemCount = new AtomicInteger(1);
    @OnClick({R.id.room_delete, R.id.room_update, R.id.room_select, R.id.room_insert})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.room_delete:
                userViewModel.delete();
                break;
            case R.id.room_update:
                break;
            case R.id.room_select:
                break;
            case R.id.room_insert:
                userViewModel.creatAndInsert();
                break;
        }
    }
}
