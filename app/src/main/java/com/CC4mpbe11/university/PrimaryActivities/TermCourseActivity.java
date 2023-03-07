package com.CC4mpbe11.university.PrimaryActivities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.CC4mpbe11.university.Database.SchoolRepository;
import com.CC4mpbe11.university.Entities.CourseEntity;
import com.CC4mpbe11.university.Entities.TermEntity;
import com.CC4mpbe11.university.R;
import com.CC4mpbe11.university.RecyclerViewAdapters.CoursesRecAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class TermCourseActivity extends AppCompatActivity {

    //private TermViewModel mTermViewModel;
    //private CourseViewModel mCourseViewModel;
    private SchoolRepository schoolRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_course);

        RecyclerView courseRecycler = findViewById(R.id.courseRecycler);

        schoolRepository = new SchoolRepository(getApplication());

        //mTermViewModel = new ViewModelProvider(this).get(TermViewModel.class);
        //mCourseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);

        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(TermCourseActivity.this, SchedulerActivity.class);
            intent.putExtra("courseID", /*mCourseViewModel.lastID()+1*/ schoolRepository.getNewCourseID());
            intent.putExtra("type", "newCourse");
            startActivity(intent);
            finish();
        });

        final CoursesRecAdapter adapter = new CoursesRecAdapter(this, "associatedWithTerm", getIntent().getIntExtra("termID",0));
        courseRecycler.setAdapter(adapter);
        courseRecycler.setLayoutManager(new LinearLayoutManager(this));


        List<TermEntity> allTerms = schoolRepository.getAllTerms();

        for(TermEntity term : allTerms) {
            if(term.getTermID() == getIntent().getIntExtra("termID",0)){
                List<TermEntity> myTermList=new ArrayList<>();
                myTermList.add(term);
                adapter.setTermWords(myTermList);
            }
        }

        List<CourseEntity> associatedCourses = schoolRepository.getAssociatedCourses(getIntent().getIntExtra("termID", 0));
        adapter.setCourseWords(associatedCourses);


        /*for(CourseEntity course : allCourses){
            if(course.getTermID()==currentTerm.getTermID()) {
                currentCourse=course;
            }
        }*/


        /*mTermViewModel.getAllTerms().observe(this, new Observer<List<TermEntity>>(){
            @Override
            public void onChanged(@Nullable final List<TermEntity> termWords) {
                List<TermEntity> myTermList=new ArrayList<>();
                for(TermEntity t:termWords)if(t.getTermID()==getIntent().getIntExtra("termID",0))myTermList.add(t);
                adapter.setTermWords(myTermList);
                numTerms=myTermList.size();
            }
        });

        mCourseViewModel.getAllCourses().observe(this, new Observer<List<CourseEntity>>(){
            @Override
            public void onChanged(@Nullable final List<CourseEntity> courseWords) {
                List<CourseEntity> myCourseList=new ArrayList<>();
                for(CourseEntity c:courseWords)if(c.getTermID()==getIntent().getIntExtra("termID",0))myCourseList.add(c);
                adapter.setCourseWords(myCourseList);
                numCourses=myCourseList.size();
                *//*ArrayList<CourseEntity> myOtherList = new ArrayList();
                myOtherList.addAll(courseWords);
                adapter.setCourseWords(myOtherList);
                numCourses = myOtherList.size();*//*
            }
        });*/


    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}