package com.CC4mpbe11.university.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.CC4mpbe11.university.Database.SchoolRepository;
import com.CC4mpbe11.university.Entities.CourseEntity;

import java.util.List;

public class CourseViewModel extends AndroidViewModel {

    int termID;
    private SchoolRepository mRepository;
    //private LiveData<List<CourseEntity>> mAssociatedCourses;
    //private LiveData<List<CourseEntity>> mAllCourses;
    private List<CourseEntity> mAssociatedCourses;
    private List<CourseEntity> mAllCourses;

    public CourseViewModel(Application application, int termID) {
        super(application);
        mRepository = new SchoolRepository(application);
        mAssociatedCourses = mRepository.getAssociatedCourses(termID);
    }

    public CourseViewModel(Application application){
        super(application);
        mRepository = new SchoolRepository(application);
        mAllCourses = mRepository.getAllCourses();
        mAssociatedCourses = mRepository.getAssociatedCourses(termID);
    }

    //public LiveData<List<CourseEntity>> getAssociatedCourses(int termID) {return mRepository.getAssociatedCourses(termID);}
    public List<CourseEntity> getAssociatedCourses(int termID) {
        return mRepository.getAssociatedCourses(termID);
    }

    public List<CourseEntity> getAllCourses() {
        return mAllCourses;
    }
    //public LiveData<List<CourseEntity>> getAllCourses() {return mAllCourses;}

    public void insert(CourseEntity courseEntity) {mRepository.insert(courseEntity);}
    public void delete(CourseEntity courseEntity){ mRepository.delete(courseEntity);}
    public int lastID() {return mAllCourses.size();}
}
