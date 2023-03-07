package com.CC4mpbe11.university.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.CC4mpbe11.university.Database.SchoolRepository;
import com.CC4mpbe11.university.Entities.AssessmentEntity;
import com.CC4mpbe11.university.Entities.CourseEntity;

import java.util.List;

public class AssessmentViewModel extends AndroidViewModel{

    int courseID;
    private SchoolRepository mRepository;
    private List<CourseEntity> mAssociatedCourse;
    private List<AssessmentEntity> mAllAssessments;

    public AssessmentViewModel(Application application, int courseID) {
        super(application);
        mRepository = new SchoolRepository(application);
        mAssociatedCourse = mRepository.getAssociatedCourses(courseID);
    }

    public AssessmentViewModel(Application application){
        super(application);
        mRepository = new SchoolRepository(application);
        mAllAssessments = mRepository.getAllAssessments();
        mAssociatedCourse = mRepository.getAssociatedCourses(courseID);
    }

    public List<CourseEntity> getAssociatedCourse(int assessmentID) {
        return mRepository.getAssociatedCourse(assessmentID);
    }
    //public LiveData<List<CourseEntity>> getAssociatedCourse(int assessmentID) {
        //return mRepository.getAssociatedCourse(assessmentID);
    //}


    public List<AssessmentEntity> getAllAssessments() {
        return mAllAssessments;
    }
    //public LiveData<List<AssessmentEntity>> getAllAssessments() {
        //return mAllAssessments;
    //}

    public void insert(AssessmentEntity assessmentEntity) {mRepository.insert(assessmentEntity);}

    //public int lastID() {return mAllAssessments.getValue().size();}
    public int lastID() {return mAllAssessments.size();}

}
