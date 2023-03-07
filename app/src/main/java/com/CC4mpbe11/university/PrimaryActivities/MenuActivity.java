package com.CC4mpbe11.university.PrimaryActivities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.CC4mpbe11.university.R;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ImageButton termsButtonId = findViewById(R.id.termsButtonId);
        termsButtonId.setOnClickListener(
                v -> openTermsActivity()
        );

        ImageButton schedulerButtonId = findViewById(R.id.schedulerButtonId);
        schedulerButtonId.setOnClickListener(
                v -> openSchedulerActivity()
        );

        ImageButton coursesButtonId = findViewById(R.id.coursesButtonId);
        coursesButtonId.setOnClickListener(
                v -> openCourseActivity()
        );

        ImageButton assessmentButtonId = findViewById(R.id.assessmentButtonId);
        assessmentButtonId.setOnClickListener(
                v -> openAssessmentActivity()
        );

    }

    public void openTermsActivity() {
        Intent intent = new Intent(this, TermsActivity.class);
        startActivity(intent);
    }

    public void openCourseActivity() {
        Intent intent = new Intent(this, CatalogueActivity.class);
        startActivity(intent);
    }

    public void openSchedulerActivity() {
        Intent intent = new Intent(this, SchedulerActivity.class);
        startActivity(intent);
    }

    public void openAssessmentActivity() {
        Intent intent = new Intent(this, AssessmentActivity.class);
        startActivity(intent);
    }

}