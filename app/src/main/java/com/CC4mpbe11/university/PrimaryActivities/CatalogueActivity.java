package com.CC4mpbe11.university.PrimaryActivities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.CC4mpbe11.university.Database.SchoolRepository;
import com.CC4mpbe11.university.Entities.CourseEntity;
import com.CC4mpbe11.university.R;
import com.CC4mpbe11.university.RecyclerViewAdapters.CoursesRecAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class CatalogueActivity extends AppCompatActivity {
    //private CourseViewModel mCourseViewModel;
    private SchoolRepository schoolRepository;
    private String searchWords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogue);
        schoolRepository = new SchoolRepository(getApplication());
        List<CourseEntity> allCourses = schoolRepository.getAllCourses();

        //mCourseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(CatalogueActivity.this, SchedulerActivity.class);
            intent.putExtra("courseID",/*mCourseViewModel.lastID()+1*/ schoolRepository.getNewCourseID());
            intent.putExtra("type", "newCourse");
            startActivity(intent);
            finish();
        });

        EditText courseSearchTextview = findViewById(R.id.courseSearchTextview);
        Button courseSearchButton = findViewById(R.id.courseSearchButton);
        courseSearchButton.setOnClickListener(view -> {
            searchWords = courseSearchTextview.getText().toString();
            Intent intent = new Intent(CatalogueActivity.this, CatalogueActivity.class);
            intent.putExtra("searchWords", searchWords);
            startActivity(intent);
            finish();
        });


        if(getIntent().getStringExtra("searchWords") != null){
            allCourses.clear();
            for(CourseEntity course : schoolRepository.getAllCourses()){
                if(course.getCourseName().equals(getIntent().getStringExtra("searchWords"))){
                    allCourses.add(course);
                }
            }
            RecyclerView recyclerView = findViewById(R.id.catalogueRecycler);
            final CoursesRecAdapter adapter = new CoursesRecAdapter(this, "searchCourses", getIntent().getStringExtra("searchWords"));
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter.setCourseWords(allCourses);
        }
        else {
            RecyclerView recyclerView = findViewById(R.id.catalogueRecycler);
            final CoursesRecAdapter adapter = new CoursesRecAdapter(this, "showAllCourses", getIntent().getIntExtra("termID",0));
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter.setCourseWords(allCourses);
        }


        //LiveData<List<CourseEntity>> allCourses = mCourseViewModel.getAllCourses();
        /*mCourseViewModel.getAllCourses().observe(this, new Observer<List<CourseEntity>>() {
            @Override
            public void onChanged(@Nullable final List<CourseEntity> words) {
                // Update the cached copy of the words in the adapter.
                adapter.setCourseWords(words);
            }
        });*/

    }
}