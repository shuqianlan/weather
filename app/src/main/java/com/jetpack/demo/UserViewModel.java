package com.jetpack.demo;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class UserViewModel extends ViewModel {

    public static final String TAG = "UserViewModel";
    private UserRepository mRepository;
    private LiveData<List<User>> mAllUsers;

    /*
    * UserDao生成的实现类为UserDao_Impl.java.
    * 实现和前面的LiveDataTimerViewModel思路一致，
    * 先创建LiveData实例，然后在onActive时候出发数据库查询并将结果post到主线程执行.
    *
    * LiveData是如此，其余数据更新仍是在线程池中进行，避免UI线程阻塞.
    * JetPack架构:ViewModel包含Repository，仓库中包含数据来源(Retrofit，Room)
    * */
    public UserViewModel() {
        mRepository = new UserRepository();
        mAllUsers = mRepository.getUsers();
    }

    public LiveData<List<User>> getAllUsers() {
        return mAllUsers;
    }

    public void creatAndInsert() {
        int index = mAllUsers.getValue().size() + 1;
        User user = new User();
        user.lastName = "Last_Name";
        user.firstName = "Item-"+index;
        user.uid = index;

        Address address = new Address();
        address.city = "杭州";
        address.postCode = 310000 + index;
        address.state = "Online";
        address.street = "长河街道";
        user.address = address;

        Log.d(TAG, "insert: user " + user);
        mRepository.insert(user);
    }

    public void delete() {
        int index = mAllUsers.getValue().size()-1;
        if (index < 0) {
            return;
        }
        User user = mAllUsers.getValue().get(index);
        Log.d(TAG, "delete: user " + user);
        mRepository.delete(user);
    }
}
