package com.CC4mpbe11.university.DetailsScreens;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.CC4mpbe11.university.Database.SchoolRepository;
import com.CC4mpbe11.university.Entities.AssessmentEntity;
import com.CC4mpbe11.university.Entities.CourseEntity;
import com.CC4mpbe11.university.PrimaryActivities.SchedulerActivity;
import com.CC4mpbe11.university.R;

import java.util.List;

public class AssessmentDetails extends AppCompatActivity {
    private String associatedCourseName;
    private SchoolRepository schoolRepository;
    private Boolean areYouSure = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //mCourseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
        setContentView(R.layout.activity_assessment_details);

        schoolRepository = new SchoolRepository(getApplication());
        //private CourseViewModel mCourseViewModel;
        List<CourseEntity> allCourses = schoolRepository.getAllCourses();


        TextView assessmentNameTextview = findViewById(R.id.assessmentNameTextview);
        TextView assessmentCodeTextview = findViewById(R.id.assessmentCodeTextview);
        TextView assessmentVendorTextview = findViewById(R.id.assessmentVendorTextview);
        TextView assessmentTypeTextview = findViewById(R.id.assessmentTypeTextview);
        TextView associatedCourseTextview = findViewById(R.id.associatedCourseTextview);
        TextView assessmentTimeTextview = findViewById(R.id.assessmentTimeTextview);
        TextView assessmentDateTextview = findViewById(R.id.assessmentDateTextview);
        TextView assessmentNotesTextview = findViewById(R.id.assessmentNotesTextview);

        try{
            for (CourseEntity course : allCourses) {
                if (course.getCourseID() == getIntent().getIntExtra("associatedCourse", -1)){
                    associatedCourseName = course.getCourseName();
                }
            }
            assessmentNameTextview.setText(getIntent().getStringExtra("assessmentName"));
            assessmentCodeTextview.setText(getIntent().getStringExtra("assessmentCode"));
            assessmentTypeTextview.setText(getIntent().getStringExtra("assessmentType"));
            assessmentVendorTextview.setText(getIntent().getStringExtra("assessmentVendor"));
            assessmentTimeTextview.setText(getIntent().getStringExtra("assessmentTime"));
            assessmentDateTextview.setText(getIntent().getStringExtra("assessmentDate"));
            associatedCourseTextview.setText(associatedCourseName);
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        Button editAssessment = findViewById(R.id.editAssessment);
        Button deleteAssessment = findViewById(R.id.deleteAssessment);
        editAssessment.setOnClickListener(
                view -> {
                    if(areYouSure) {
                        deleteAssessment.setText(R.string.delete);
                        editAssessment.setText(R.string.edit);
                        assessmentNotesTextview.setText("");
                        assessmentNotesTextview.setTextColor(Color.BLACK);
                        areYouSure = false;
                    }
                    else {
                        Intent intent = new Intent(AssessmentDetails.this, SchedulerActivity.class);
                        intent.putExtra("assessmentID", getIntent().getIntExtra("assessmentID", -1));
                        intent.putExtra("assessmentName", getIntent().getStringExtra("assessmentName"));
                        intent.putExtra("assessmentCode", getIntent().getStringExtra("assessmentCode"));
                        intent.putExtra("assessmentType", getIntent().getStringExtra("assessmentType"));
                        intent.putExtra("assessmentVendor", getIntent().getStringExtra("assessmentVendor"));
                        intent.putExtra("assessmentTime", getIntent().getStringExtra("assessmentTime"));
                        intent.putExtra("assessmentDate", getIntent().getStringExtra("assessmentDate"));
                        intent.putExtra("associatedCourse", getIntent().getIntExtra("associatedCourse", -1));
                        intent.putExtra("associatedCourseName", associatedCourseName);
                        intent.putExtra("type", "existingAssessment");
                        startActivity(intent);
                        finish();
                    }
                }
        );
        deleteAssessment.setOnClickListener(
                view -> {
                    if (areYouSure){
                        for (AssessmentEntity assessment : schoolRepository.getAllAssessments()){
                            if (assessment.getAssessmentID()==getIntent().getIntExtra("assessmentID", -1)) {
                                schoolRepository.delete(assessment);
                                finish();
                            }
                        }
                    }
                    else {
                        areYouSure = true;
                        deleteAssessment.setText(R.string.confirm);
                        editAssessment.setText(R.string.cancel);
                        assessmentNotesTextview.setText(R.string.sure_delete);
                        assessmentNotesTextview.setTextColor(Color.RED);
                    }
                }
        );
    }
}