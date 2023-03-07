package com.CC4mpbe11.university.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.CC4mpbe11.university.Entities.AssessmentEntity;
import com.CC4mpbe11.university.Entities.CourseEntity;

import java.util.List;

@Dao
public interface AssessmentDAO {
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insert(AssessmentEntity assessment);

    @Update
    void update(AssessmentEntity assessment);

    @Delete
    void delete(AssessmentEntity assessment);

    @Query("DELETE FROM assessment_table")
    void deleteAllAssessments();

    @Query("SELECT * FROM assessment_table ORDER BY assessmentID ASC")
    List<AssessmentEntity> getAllAssessments();
    //LiveData<List<AssessmentEntity>> getAllAssessments();

    @Query("SELECT * FROM course_table WHERE courseID= :assessmentID")
    List<CourseEntity> getAssociatedCourse(int assessmentID);
    //LiveData<List<CourseEntity>> getAssociatedCourse(int assessmentID);
}
