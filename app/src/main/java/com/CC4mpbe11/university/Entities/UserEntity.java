package com.CC4mpbe11.university.Entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName="user_table")
public class UserEntity {
    @PrimaryKey
    private int userID;
    private final String username;
    private final String password;

    @NonNull
    @Override
    public String toString(){
        return "UserEntity{" +
                "userID=" + userID +
                ", username=" + username +
                ", password=" + password +
                "}";
    }

    public UserEntity(int userID, String username, String password) {
        this.userID = userID;
        this.username = username;
        this.password = password;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
