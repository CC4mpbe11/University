package com.CC4mpbe11.university.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.CC4mpbe11.university.Entities.UserEntity;

import java.util.List;

@Dao
public interface UserDAO {
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insert(UserEntity user);

    @Update
    void update(UserEntity user);

    @Delete
    void delete(UserEntity user);

    @Query("DELETE FROM user_table")
    void deleteAllUsers();

    @Query("SELECT * FROM user_table ORDER BY userID ASC")
    List<UserEntity> getAllUsers();
    //LiveData<List<AssessmentEntity>> getAllAssessments();

    @Query("SELECT * FROM user_table WHERE username= :username")
    UserEntity getSpecificUser(String username);
    //LiveData<List<CourseEntity>> getAssociatedCourse(int assessmentID);

    @Query("SELECT * FROM user_table ORDER BY userID LIMIT 1")
    LiveData<List<UserEntity>> loadlastTask();

    //SELECT count(*) FROM (select 0 from employee limit 1);

}
