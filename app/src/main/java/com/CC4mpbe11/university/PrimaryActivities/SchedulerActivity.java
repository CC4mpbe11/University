package com.CC4mpbe11.university.PrimaryActivities;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.CC4mpbe11.university.R;
import com.CC4mpbe11.university.SchedulerFragments.ScheduleAssessmentFrag;
import com.CC4mpbe11.university.SchedulerFragments.ScheduleCourseFrag;
import com.CC4mpbe11.university.SchedulerFragments.ScheduleTermFrag;

public class SchedulerActivity extends AppCompatActivity {

    ScheduleCourseFrag scheduleCourseFragment = new ScheduleCourseFrag();
    ScheduleAssessmentFrag scheduleAssessmentFragment = new ScheduleAssessmentFrag();
    ScheduleTermFrag scheduleTermFragment = new ScheduleTermFrag();

    FragmentManager fm = getSupportFragmentManager();
    FragmentTransaction transaction = fm.beginTransaction();

    private boolean newFlag = false;
    private final int id = -1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduler);

        try{
            if (getIntent().getStringExtra("type").equals("newTerm") || getIntent().getStringExtra("type").equals("newCourse") || getIntent().getStringExtra("type").equals("newAssessment")){
                newFlag = true;
            }
            if(getIntent().getStringExtra("type").equals("course") || getIntent().getStringExtra("type").equals("newCourse")) {
                openCourseFrag();
            }
            else if (getIntent().getStringExtra("type").equals("term") || getIntent().getStringExtra("type").equals("newTerm")){
                openTermFrag();
            }
            else {
                openAssessmentFrag();
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        final Button buttonAssessment = findViewById(R.id.buttonAssessment);
        buttonAssessment.setOnClickListener(v -> {
            newFlag = true;
            openAssessmentFrag();
        });

        final Button buttonCourse= findViewById(R.id.buttonCourse);
        buttonCourse.setOnClickListener(v -> {
            newFlag = true;
            openCourseFrag();
        });

        final Button buttonTerm = findViewById(R.id.buttonTerm);
        buttonTerm.setOnClickListener(v -> {
            newFlag = true;
            openTermFrag();
        });

    }

    public void openAssessmentFrag() {
        if (!newFlag) {
            try {
                    int assessmentID = getIntent().getIntExtra("assessmentID", 1);
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction transaction = fm.beginTransaction();
                    Bundle bundle = new Bundle();
                    bundle.putInt("assessmentID", assessmentID);
                    bundle.putString("assessmentName", getIntent().getStringExtra("assessmentName"));
                    bundle.putString("assessmentCode", getIntent().getStringExtra("assessmentCode"));
                    bundle.putString("assessmentVendor", getIntent().getStringExtra("assessmentVendor"));
                    bundle.putString("assessmentType", getIntent().getStringExtra("assessmentType"));
                    bundle.putInt("associatedCourse", getIntent().getIntExtra("associatedCourse", -1));
                    bundle.putString("assessmentTime", getIntent().getStringExtra("assessmentTime"));
                    bundle.putString("assessmentDate", getIntent().getStringExtra("assessmentDate"));
                    bundle.putString("associatedCourseName", getIntent().getStringExtra("associatedCourseName"));
                    bundle.putString("type", "existingAssessment");
                    ScheduleAssessmentFrag scheduleAssessmentFrag = new ScheduleAssessmentFrag();
                    scheduleAssessmentFrag.setArguments(bundle);
                    transaction.replace(R.id.flFragment, scheduleAssessmentFrag);
                    transaction.commit();
                } catch (Exception e) {
                    Context context = getApplicationContext();
                    CharSequence text = "Oops, Something went wrong!";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }

            }
        else{
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            Bundle bundle = new Bundle();
            try {
                bundle.putInt("assessmentID", id);
                bundle.putString("type", "newAssessment");
            } catch (Exception e) {
                e.printStackTrace();
            }
            ScheduleAssessmentFrag scheduleAssessmentFrag = new ScheduleAssessmentFrag();
            scheduleAssessmentFrag.setArguments(bundle);
            transaction.replace(R.id.flFragment, scheduleAssessmentFrag);
            transaction.commit();
        }
    }

    public void openCourseFrag() {

        if (!newFlag) {
            try {
                int courseID = getIntent().getIntExtra("courseID", -1);
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putInt("courseID", courseID);
                bundle.putString("courseStart", getIntent().getStringExtra("courseStart"));
                bundle.putString("courseEnd", getIntent().getStringExtra("courseEnd"));
                bundle.putString("courseName", getIntent().getStringExtra("courseName"));
                bundle.putString("courseCode", getIntent().getStringExtra("courseCode"));
                bundle.putString("courseDescription", getIntent().getStringExtra("courseDescription"));
                bundle.putString("courseStatus", getIntent().getStringExtra("courseStatus"));
                bundle.putString("instructorName", getIntent().getStringExtra("instructorName"));
                bundle.putString("instructorEmail", getIntent().getStringExtra("instructorEmail"));
                bundle.putString("instructorPhone", getIntent().getStringExtra("instructorPhone"));
                bundle.putInt("associatedTerm", getIntent().getIntExtra("associatedTerm", -1));
                bundle.putString("courseNotes", getIntent().getStringExtra("courseNotes"));
                bundle.putString("type", "existingCourse");
                ScheduleCourseFrag scheduleCourseFrag = new ScheduleCourseFrag();
                scheduleCourseFrag.setArguments(bundle);
                transaction.replace(R.id.flFragment, scheduleCourseFrag);
                transaction.commit();
            } catch (Exception e) {
                Context context = getApplicationContext();
                CharSequence text = "Oops, Something went wrong!";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        }
        else {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            Bundle bundle = new Bundle();
            try {
                bundle.putInt("courseID", id);
                bundle.putString("type", "newCourse");
            } catch (Exception e) {
                e.printStackTrace();
            }
            ScheduleCourseFrag scheduleCourseFrag = new ScheduleCourseFrag();
            scheduleCourseFrag.setArguments(bundle);
            transaction.replace(R.id.flFragment, scheduleCourseFrag);
            transaction.commit();
        }
    }

    public void openTermFrag() {

        if (!newFlag) {
            Bundle bundle = new Bundle();
            bundle.putString("type", "term");
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.flFragment, scheduleTermFragment);
            transaction.commit();
        }
        else {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            Bundle bundle = new Bundle();
            try {
                bundle.putInt("termID", id);
                bundle.putString("type", "newTerm");
            } catch (Exception e) {
                e.printStackTrace();
            }
            ScheduleTermFrag scheduleTermFrag = new ScheduleTermFrag();
            scheduleTermFrag.setArguments(bundle);
            transaction.replace(R.id.flFragment, scheduleTermFragment);
            transaction.commit();
        }
    }
}
