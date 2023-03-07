package com.CC4mpbe11.university.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.CC4mpbe11.university.Entities.CourseEntity;

import java.util.List;

@Dao
public interface CourseDAO {
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insert(CourseEntity course);

    @Delete
    void delete(CourseEntity course);

    @Update
    void update(CourseEntity course);

    @Query("DELETE FROM course_table")
    void deleteAllCourses();

    @Query("SELECT * FROM course_table ORDER BY courseID ASC")
    List<CourseEntity> getAllCourses();
    //LiveData<List<CourseEntity>> getAllCourses();

    @Query("SELECT * FROM course_table WHERE termID= :termID ORDER BY courseID ASC")
    List<CourseEntity> getAssociatedCourses(int termID);
    //LiveData<List<CourseEntity>> getAssociatedCourses(int termID);

    @Query("SELECT * FROM course_table WHERE termID<> :termID ORDER BY courseID ASC")
    List<CourseEntity> getUnassociatedCourses(int termID);
    //LiveData<List<CourseEntity>> getAssociatedCourses(int termID);

    //@Query("SELECT * FROM course_table ORDER BY courseID LIMIT 1")
    //LiveData<List<CourseEntity>> loadlastTask();
}
