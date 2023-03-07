package com.CC4mpbe11.university.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.CC4mpbe11.university.Database.SchoolRepository;
import com.CC4mpbe11.university.Entities.TermEntity;

import java.util.List;

public class TermViewModel extends AndroidViewModel {
    private SchoolRepository mRepository;
    //private LiveData<List<TermEntity>> mAllTerms;
    private List<TermEntity> mAllTerms;
    public TermViewModel(Application application){
        super(application);
        mRepository = new SchoolRepository(application);
        mAllTerms = mRepository.getAllTerms();
    }
    //public LiveData<List<TermEntity>> getAllTerms(){ return mAllTerms; }
    public List<TermEntity> getAllTerms(){ return mAllTerms; }
    public void insert(TermEntity termEntity){ mRepository.insert(termEntity); }
    public void delete(TermEntity termEntity){
        mRepository.delete(termEntity);
    }
    //public int lastID(){return mAllTerms.getValue().size();}
     public int lastID(){ return mAllTerms.size();}
}
