package com.CC4mpbe11.university.Database;

import android.app.Application;

import com.CC4mpbe11.university.DAO.AssessmentDAO;
import com.CC4mpbe11.university.DAO.CourseDAO;
import com.CC4mpbe11.university.DAO.TermDAO;
import com.CC4mpbe11.university.DAO.UserDAO;
import com.CC4mpbe11.university.Entities.AssessmentEntity;
import com.CC4mpbe11.university.Entities.CourseEntity;
import com.CC4mpbe11.university.Entities.TermEntity;
import com.CC4mpbe11.university.Entities.UserEntity;

import java.util.List;

public class SchoolRepository {

    private final AssessmentDAO mAssessmentDAO;
    private final TermDAO mTermDAO;
    private final CourseDAO mCourseDAO;
    private final UserDAO mUserDAO;
    //private LiveData<List<CourseEntity>> mAssociatedCourses;
    //private LiveData<List<CourseEntity>> mAssociatedCourse;
    //private LiveData<List<CourseEntity>> mAllCourses;

    public SchoolRepository(Application application) {
        SchoolDatabase db = SchoolDatabase.getDatabase(application);
        mAssessmentDAO = db.assessmentDAO();
        mTermDAO = db.termDAO();
        mCourseDAO = db.courseDAO();
        mUserDAO = db.userDAO();
        //mAllAssessments = mAssessmentDAO.getAllAssessments();
        //mAllTerms = mTermDAO.getAllTerms();
        //mAllCourses = mCourseDAO.getAllCourses();
        //mAssociatedCourses = mCourseDAO.getAssociatedCourses(termID);
        //mUnassociatedCourses = mCourseDAO.getUnassociatedCourses(termID);
        //mAssociatedCourse = mAssessmentDAO.getAssociatedCourse(assessmentID);
    }

    //public LiveData<List<AssessmentEntity>> getAllAssessments () {return mAllAssessments;}
    //public LiveData<List<TermEntity>> getAllTerms () {return mAllTerms;}


    public int getNewUserID(){
        int newIDtoBeat = 0;
        for(UserEntity user : getAllUsers()) {
            if (user.getUserID() > newIDtoBeat) {
                newIDtoBeat = user.getUserID();
                System.out.println("The new Id to beat is: " + newIDtoBeat);
            }
        }
        return newIDtoBeat + 1;
    }

    public int getNewCourseID(){
        int newIDtoBeat = 0;
        for(CourseEntity course : getAllCourses()) {
            if (course.getCourseID() > newIDtoBeat) {
                newIDtoBeat = course.getCourseID();
                System.out.println("The new Id to beat is: " + newIDtoBeat);
            }
        }
        return newIDtoBeat + 1;
    }

    public int getNewTermID(){
        int newIDtoBeat = 0;
        for(TermEntity term: getAllTerms()) {
            if (term.getTermID() > newIDtoBeat) {
                newIDtoBeat = term.getTermID();
            }
        }
        return newIDtoBeat + 1;
    }

    public int getNewAssessmentID(){
        int newIDtoBeat = 0;
        for(AssessmentEntity assessment : getAllAssessments()) {
            if (assessment.getAssessmentID() > newIDtoBeat) {
                newIDtoBeat = assessment.getAssessmentID();
            }
        }
        return newIDtoBeat + 1;
    }

    public List<UserEntity> getAllUsers () {
        return mUserDAO.getAllUsers();}

    public Boolean areThereUsers(){
        List<UserEntity> allUsers = mUserDAO.getAllUsers();
        if(allUsers.isEmpty()){
            return false;
        }
        else{return true;}
    }

    public List<AssessmentEntity> getAllAssessments () {
        return mAssessmentDAO.getAllAssessments();}

    public List<TermEntity> getAllTerms () {
        return mTermDAO.getAllTerms();}

    public List<CourseEntity> getAllCourses(){
        return mCourseDAO.getAllCourses(); }

    //public LiveData<List<CourseEntity>> getAllCourses() {return mAllCourses;}
    //public LiveData<List<CourseEntity>> getAssociatedCourses (int termID) {return mAssociatedCourses;}
    //public LiveData<List<CourseEntity>> getAssociatedCourse (int assessmentID) {return mAssociatedCourse;}

    public UserEntity getParticularUser (String username) {
        //private LiveData<List<AssessmentEntity>> mAllAssessments;
        //private LiveData<List<TermEntity>> mAllTerms;
        return mUserDAO.getSpecificUser(username);}

    public List<CourseEntity> getAssociatedCourses (int termID) {
        //private LiveData<List<AssessmentEntity>> mAllAssessments;
        //private LiveData<List<TermEntity>> mAllTerms;
        return mCourseDAO.getAssociatedCourses(termID);}

    public List<CourseEntity> getUnassociatedCourses (int termID) {
        return mCourseDAO.getUnassociatedCourses(termID);}

    public List<CourseEntity> getAssociatedCourse (int assessmentID) {
        return mAssessmentDAO.getAssociatedCourse(assessmentID);}

    public void insert (UserEntity userEntity) {
        //new insertAsyncTask1(mCourseDAO).execute(courseEntity);
        mUserDAO.insert(userEntity);
    }

    public void insert (CourseEntity courseEntity) {
        //new insertAsyncTask1(mCourseDAO).execute(courseEntity);
        mCourseDAO.insert(courseEntity);
    }

    public void insert (TermEntity termEntity) {
        //new insertAsyncTask1(mCourseDAO).execute(courseEntity);
        mTermDAO.insert(termEntity);
    }

    public void insert (AssessmentEntity assessmentEntity) {
        //new insertAsyncTask1(mCourseDAO).execute(courseEntity);
        mAssessmentDAO.insert(assessmentEntity);
    }
/*
    public void insert (AssessmentEntity assessmentEntity) {
        new insertAsyncTask2(mAssessmentDAO).execute(assessmentEntity);
    }

    public void insert (TermEntity termEntity) {
        new insertAsyncTask(mTermDAO).execute(termEntity);
    }*/

    public void update (UserEntity userEntity) {
        mUserDAO.update(userEntity);
    }

    public void update (CourseEntity courseEntity) {
        mCourseDAO.update(courseEntity);
    }

    public void update (TermEntity termEntity) {
        mTermDAO.update(termEntity);
    }

    public void update (AssessmentEntity assessmentEntity) { mAssessmentDAO.update(assessmentEntity); }

    public void delete (UserEntity userEntity) {
        mUserDAO.delete(userEntity);
        //new deleteAsyncTask(mTermDAO).execute(termEntity);
    }
    public void delete (TermEntity termEntity) {
        mTermDAO.delete(termEntity);
        //new deleteAsyncTask(mTermDAO).execute(termEntity);
    }
    public void delete (CourseEntity courseEntity) {
        //new deleteAsyncTask1(mCourseDAO).execute(courseEntity);
        mCourseDAO.delete(courseEntity);
    }
    public void delete (AssessmentEntity assessmentEntity) {
        mAssessmentDAO.delete(assessmentEntity);
        //new deleteAsyncTask2(mAssessmentDAO).execute(assessmentEntity);
    }

  /*  private static class insertAsyncTask extends AsyncTask<TermEntity, Void, Void> {
        private TermDAO mAsyncTaskDao;
        insertAsyncTask(TermDAO dao) {
            mAsyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(final TermEntity... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class insertAsyncTask1 extends  AsyncTask<CourseEntity, Void, Void> {
        private CourseDAO mAsyncTaskDAO;
        insertAsyncTask1(CourseDAO dao) {mAsyncTaskDAO = dao;}
        @Override
        protected Void doInBackground(final CourseEntity... params) {
            mAsyncTaskDAO.insert(params[0]);
            return null;
        }
    }

    private static class insertAsyncTask2 extends  AsyncTask<AssessmentEntity, Void, Void> {
        private AssessmentDAO mAsyncTaskDAO;
        insertAsyncTask2(AssessmentDAO dao) {mAsyncTaskDAO = dao;}
        @Override
        protected Void doInBackground(final AssessmentEntity... params) {
            mAsyncTaskDAO.insert(params[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<TermEntity, Void, Void> {
        private TermDAO mAsyncTaskDAO;
        deleteAsyncTask(TermDAO dao) {mAsyncTaskDAO = dao;}
        @Override
        protected Void doInBackground(final TermEntity... params) {
            mAsyncTaskDAO.delete(params[0]);
            return null;
        }
    }

    private static class deleteAsyncTask1 extends AsyncTask<CourseEntity, Void, Void> {
        private CourseDAO mAsyncTaskDAO;
        deleteAsyncTask1(CourseDAO dao) {mAsyncTaskDAO = dao;}
        @Override
        protected Void doInBackground(final CourseEntity... params) {
            mAsyncTaskDAO.delete(params[0]);
            return null;
        }
    }

    private static class deleteAsyncTask2 extends AsyncTask<AssessmentEntity, Void, Void> {
        private AssessmentDAO mAsyncTaskDAO;
        deleteAsyncTask2(AssessmentDAO dao) {mAsyncTaskDAO = dao;}
        @Override
        protected Void doInBackground(final AssessmentEntity... params) {
            mAsyncTaskDAO.delete(params[0]);
            return null;
        }
    }*/
}
