package com.CC4mpbe11.university.PrimaryActivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.CC4mpbe11.university.Database.SchoolRepository;
import com.CC4mpbe11.university.Entities.AssessmentEntity;
import com.CC4mpbe11.university.Entities.CourseEntity;
import com.CC4mpbe11.university.R;
import com.CC4mpbe11.university.RecyclerViewAdapters.AssessmentRecAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class AssessmentActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    //private AssessmentViewModel mAssessmentViewModel;
    private SchoolRepository schoolRepository;
    private List<AssessmentEntity> allAssessments =  new ArrayList<>();
    private final List<AssessmentEntity> filteredAssessments = new ArrayList<>();
    private AssessmentRecAdapter adapter;
    private List<CourseEntity> allCourses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment);
        schoolRepository = new SchoolRepository(getApplication());
        allAssessments = schoolRepository.getAllAssessments();
        allCourses = schoolRepository.getAllCourses();

        //mAssessmentViewModel = new ViewModelProvider(this).get(AssessmentViewModel.class);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(AssessmentActivity.this, SchedulerActivity.class);
            intent.putExtra("assessmentID",/*mAssessmentViewModel.lastID()+1*/ schoolRepository.getNewAssessmentID());
            intent.putExtra("type", "newAssessment");
            startActivity(intent);
            finish();
        });

        List<String> allCourseStrings = new ArrayList<>();
        allCourseStrings.add("All Assessments");
        for (CourseEntity course: allCourses) {
            allCourseStrings.add(course.getCourseName());
        }

        Spinner sortByCourseSpinner = findViewById(R.id.sortByCourseSpinner);
        ArrayAdapter<String> courseAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, allCourseStrings);
        courseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortByCourseSpinner.setAdapter(courseAdapter);
        sortByCourseSpinner.setOnItemSelectedListener(this);

        RecyclerView recyclerView = findViewById(R.id.assessmentRecycler);

        adapter = new AssessmentRecAdapter(this);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setAssessmentWords(allAssessments);
        adapter.setCourseWords(allCourses);

        //LiveData<List<CourseEntity>> allCourses = mCourseViewModel.getAllCourses();
        /*mAssessmentViewModel.getAllAssessments().observe(this, new Observer<List<AssessmentEntity>>() {
            @Override
            public void onChanged(@Nullable final List<AssessmentEntity> words) {
                // Update the cached copy of the words in the adapter.
                adapter.setAssessmentWords(words);
            }
        });*/

    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        whichList();
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    private void whichList(){
        Spinner sortByCourseSpinner = findViewById(R.id.sortByCourseSpinner);
        int courseID = -1;
        allAssessments.clear();
        allAssessments = schoolRepository.getAllAssessments();
        filteredAssessments.clear();
        if (sortByCourseSpinner.getSelectedItem().toString().equals("All Assessments")){
            adapter.setAssessmentWords(allAssessments);
        }
        else{
            for (CourseEntity course : allCourses){
                if (course.getCourseName().equals(sortByCourseSpinner.getSelectedItem().toString())){
                    courseID = course.getCourseID();
                }
            }
            for (AssessmentEntity assessment : allAssessments) {
                if (assessment.getCourseID() == courseID) {
                    filteredAssessments.add(assessment);
                }
            }
            allAssessments.clear();
            allAssessments.addAll(filteredAssessments);
            adapter.setAssessmentWords(filteredAssessments);
        }
    }
}
