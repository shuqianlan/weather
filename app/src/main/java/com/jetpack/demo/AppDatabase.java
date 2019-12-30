package com.jetpack.demo;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/* @Database需满足的条件
/ 1. 扩展RoomDatabase的抽象类
/ 2. 注释中添加数据库关联的实体列表
/ 3. 包含具有0个参数且返回使用@Dao注解的抽象方法
/
/ Entity: 数据表
/ DAO: 访问数据库的方法
*/
@Database(version = 1, entities = {User.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}
