package com.jetpack.demo;

import androidx.room.Room;
import com.ilifesmart.ToolsApplication;

public class DatabaseManager {
    private static Object lock = new Object();
    private static volatile DatabaseManager instance;

    public static DatabaseManager getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new DatabaseManager();
                }
            }
        }
        return instance;
    }

    private AppDatabase database;
    private DatabaseManager() {
        database = Room.databaseBuilder(ToolsApplication.getContext(), AppDatabase.class, "user_db").build();
    }

    public AppDatabase getDatabase() {
        return database;
    }
}
