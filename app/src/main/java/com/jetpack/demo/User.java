package com.jetpack.demo;

import androidx.room.*;

// SQLite 表名不区分大小写
// @Index 索引
@Entity(indices = {@Index("first_name"), @Index("last_name")})
public class User {

    @PrimaryKey
    public int uid;

    @ColumnInfo(name = "first_name")
    public String firstName;

    @ColumnInfo(name = "last_name")
    public String lastName;

    @Embedded // 意思就是表user中含有属性uid,first_name,last_name,state,street,city,post_code
    public Address address;

    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address=" + address +
                '}';
    }
}
