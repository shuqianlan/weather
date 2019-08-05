package com.jetpack.demo;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class UserRepository {

    public static final String TAG = "UserRepository";

    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    // We want at least 2 threads and at most 4 threads in the core pool,
    // preferring to have 1 less than the CPU count to avoid saturating
    // the CPU with background work
    private static final int CORE_POOL_SIZE = Math.max(2, Math.min(CPU_COUNT - 1, 4));

    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private AtomicInteger mCount = new AtomicInteger(1);
        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "Nature #"+mCount.getAndIncrement());
        }
    };
    private static final RejectedExecutionHandler NatureHandler = new NewThreadPolicy();
    public static final Executor THREAD_POOL_EXECUTOR;
    static {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                CORE_POOL_SIZE,
                6,
                60L,
                TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>(),
                sThreadFactory,
                NatureHandler
        );
        executor.allowCoreThreadTimeOut(true);
        THREAD_POOL_EXECUTOR = executor;
    }

    private static class NewThreadPolicy implements RejectedExecutionHandler {
        public NewThreadPolicy() {}

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            if (!executor.isShutdown()) {
                new Thread(r);
            }
        }
    }

    private AppDatabase db;
    private UserDao mUserDao;
    private LiveData<List<User>> mUsers;

    public UserRepository() {
        db = DatabaseManager.getInstance().getDatabase();
        mUserDao = db.userDao();

        // 查看UserDao_Impl.java,返回为RoomTrackingLiveData.java,在APP声明周期触发onActive的时候触发查询，并将结果postValue(),也就通知界面刷新了.
        mUsers = mUserDao.getAllUsers();
    }

    public LiveData<List<User>> getUsers() {
        return mUsers;
    }

    public void insert(User user) {
        THREAD_POOL_EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                mUserDao.insertUser(user);
            }
        });
    }

    public void delete(User user) {
        THREAD_POOL_EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                mUserDao.delete(user);
            }
        });
    }

}
