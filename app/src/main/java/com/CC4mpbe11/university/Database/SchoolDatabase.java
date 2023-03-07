package com.CC4mpbe11.university.Database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.CC4mpbe11.university.DAO.AssessmentDAO;
import com.CC4mpbe11.university.DAO.CourseDAO;
import com.CC4mpbe11.university.DAO.TermDAO;
import com.CC4mpbe11.university.DAO.UserDAO;
import com.CC4mpbe11.university.Entities.AssessmentEntity;
import com.CC4mpbe11.university.Entities.CourseEntity;
import com.CC4mpbe11.university.Entities.TermEntity;
import com.CC4mpbe11.university.Entities.UserEntity;

@androidx.room.Database(entities = {AssessmentEntity.class, CourseEntity.class, TermEntity.class, UserEntity.class}, version = 1)

public abstract class SchoolDatabase extends RoomDatabase{
    public abstract AssessmentDAO assessmentDAO();
    public abstract TermDAO termDAO();
    public abstract CourseDAO courseDAO();
    public abstract UserDAO userDAO();

    private static volatile SchoolDatabase INSTANCE;

    static SchoolDatabase getDatabase(final Context context) {
        if(INSTANCE == null) {
            synchronized (SchoolDatabase.class) {
                if(INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), SchoolDatabase.class, "school_database")
                            .fallbackToDestructiveMigration()
                            //.addCallback(sRoomDatabaseCallback)
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            AssessmentDAO mAssessmentDao = INSTANCE.assessmentDAO();
            CourseDAO mCourseDao = INSTANCE.courseDAO();
            TermDAO mTermDao = INSTANCE.termDAO();
            UserDAO mUserDao = INSTANCE.userDAO();

            UserEntity user = new UserEntity(1, "Admin", "Admin");
            mUserDao.insert(user);
        }
    };


/*
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final TermDAO lTermDAO;
        private final AssessmentDAO lAssessmentDAO;
        private final CourseDAO lCourseDAO;

        PopulateDbAsync(SchoolDatabase db) {
            lAssessmentDAO = db.assessmentDAO();
            lTermDAO = db.termDAO();
            lCourseDAO = db.courseDAO();
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected Void doInBackground(final Void... params) {
            return null;
        }
    }*/
}
