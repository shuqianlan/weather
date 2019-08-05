package com.jetpack.demo;

import androidx.lifecycle.LiveData;
import androidx.room.*;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user" )
    List<User> getAll();

    @Query("SELECT * FROM user" )
    LiveData<List<User>> getAllUsers();

    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    List<User> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM user WHERE first_name LIKE :first AND last_name LIKE :last")
    User findByName(String first, String last);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertUser(User ... users);

    @Delete
    void delete(User user);

}
