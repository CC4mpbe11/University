package com.CC4mpbe11.university.DetailsScreens;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.CC4mpbe11.university.Database.SchoolRepository;
import com.CC4mpbe11.university.Entities.CourseEntity;
import com.CC4mpbe11.university.PrimaryActivities.SchedulerActivity;
import com.CC4mpbe11.university.R;

public class CourseDetails extends AppCompatActivity {
    private String attachOrDetach = "neither";
    private SchoolRepository schoolRepository;
    private int courseID;
    private boolean areYouSure = false;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_course_details);

        schoolRepository = new SchoolRepository(getApplication());

        TextView courseStartEndTextview = findViewById(R.id.courseStartEndTextview);
        TextView instructorNameTextview = findViewById(R.id.instructorNameTextview);
        TextView termAssignTextview = findViewById(R.id.termAssignTextview);
        TextView courseCodeTextview = findViewById(R.id.courseCodeTextview);
        TextView courseNotesTextview = findViewById(R.id.courseNotesTextview);
        TextView courseStatusTextview = findViewById(R.id.courseStatusTextview);
        TextView instructorEmailTextview = findViewById(R.id.instructorEmailTextview);
        TextView courseNameTextview = findViewById(R.id.courseNameTextview);
        TextView instructorPhoneTextview = findViewById(R.id.instructorPhoneTextview);
        TextView courseDescriptionTextview = findViewById(R.id.courseDescriptionTextview);
        ImageView teacherImageview = findViewById(R.id.teacherImageview);


        try{
            courseID = getIntent().getIntExtra("courseID", -1);
            String courseStartEnd = getIntent().getStringExtra("courseStart") + " - " + getIntent().getStringExtra("courseEnd");
            String term = "";
            if(getIntent().getIntExtra("associatedTerm", -1) > 0) {
                term = "Term" + getIntent().getIntExtra("associatedTerm", -1);
            }
            termAssignTextview.setText(term);
            courseStartEndTextview.setText(courseStartEnd);
            courseNameTextview.setText(getIntent().getStringExtra("courseName"));
            courseCodeTextview.setText(getIntent().getStringExtra("courseCode"));
            courseDescriptionTextview.setText(getIntent().getStringExtra("courseDescription"));
            courseStatusTextview.setText(getIntent().getStringExtra("courseStatus"));
            instructorNameTextview.setText(getIntent().getStringExtra("instructorName"));
            instructorEmailTextview.setText(getIntent().getStringExtra("instructorEmail"));
            instructorPhoneTextview.setText(getIntent().getStringExtra("instructorPhone"));
            courseNotesTextview.setText(getIntent().getStringExtra("courseNotes"));
            attachOrDetach = getIntent().getStringExtra("attachOrDetach");
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        int teacherPicture = getIntent().getIntExtra("courseID", -1);
        switch (teacherPicture) {
            case 1: teacherImageview.setImageResource(R.drawable.person2);
            break;
            case 2: teacherImageview.setImageResource(R.drawable.person3);
            break;
            case 3: teacherImageview.setImageResource(R.drawable.person4);
            break;
            case 4: teacherImageview.setImageResource(R.drawable.person5);
            break;
            case 5: teacherImageview.setImageResource(R.drawable.person6);
            break;
            case 6: teacherImageview.setImageResource(R.drawable.person7);
            break;
            case 7: teacherImageview.setImageResource(R.drawable.person8);
            break;
            case 8: teacherImageview.setImageResource(R.drawable.person9);
            break;
            case 9: teacherImageview.setImageResource(R.drawable.person10);
            break;
            case 10: teacherImageview.setImageResource(R.drawable.person11);
            break;

        }

        Button editCourse = findViewById(R.id.editCourse);
        Button deleteCourse = findViewById(R.id.deleteCourse);

        switch (attachOrDetach) {
            case "attach":
                courseNotesTextview.setText("Are you sure you would like to attach " + getIntent().getStringExtra("courseName") + " to this term?");
                courseNotesTextview.setTextColor(Color.RED);
                termAssignTextview.setText("?");
                editCourse.setText("Confirm");
                deleteCourse.setText("Cancel");
                editCourse.setOnClickListener(
                        view -> {
                            for (CourseEntity course : schoolRepository.getAllCourses()){
                                if (course.getCourseID()==getIntent().getIntExtra("courseID", -1)) {
                                    course.setTermID(getIntent().getIntExtra("associatedTerm", -1));
                                    schoolRepository.update(course);
                                    finish();
                                }
                            }
                        });
                deleteCourse.setOnClickListener(
                        view -> finish());
                break;
            case "detach":
                courseNotesTextview.setText("Are you sure you would like to detach " + getIntent().getStringExtra("courseName") + " from it's term?");
                courseNotesTextview.setTextColor(Color.RED);
                termAssignTextview.setText("?");
                editCourse.setText("Confirm");
                deleteCourse.setText("Cancel");
                editCourse.setOnClickListener(
                        view -> {
                            for (CourseEntity course : schoolRepository.getAllCourses()){
                                if (course.getCourseID()==getIntent().getIntExtra("courseID", -1)) {
                                    course.setTermID(-1);
                                    schoolRepository.update(course);
                                    finish();
                                }
                            }
                        });
                deleteCourse.setOnClickListener(
                        view -> finish());
                break;
            case "neither":

                /*List<CourseEntity> allCourses = schoolRepository.getAllCourses();
                for(CourseEntity course : allCourses){
                    if(course.getCourseID()==courseID){
                    }
                }*/
                schoolRepository = new SchoolRepository(getApplication());

                editCourse.setOnClickListener(
                        view -> {

                            if(areYouSure) {
                                deleteCourse.setText("Delete");
                                editCourse.setText("Edit");
                                courseNotesTextview.setText("");
                                courseNotesTextview.setTextColor(Color.BLACK);
                                areYouSure = false;
                            }
                            else {
                                Intent intent = new Intent(CourseDetails.this, SchedulerActivity.class);
                                intent.putExtra("courseID", courseID);
                                intent.putExtra("courseStart", getIntent().getStringExtra("courseStart"));
                                intent.putExtra("courseEnd", getIntent().getStringExtra("courseEnd"));
                                intent.putExtra("courseName", getIntent().getStringExtra("courseName"));
                                intent.putExtra("courseCode", getIntent().getStringExtra("courseCode"));
                                intent.putExtra("courseDescription", getIntent().getStringExtra("courseDescription"));
                                intent.putExtra("courseStatus", getIntent().getStringExtra("courseStatus"));
                                intent.putExtra("instructorName", getIntent().getStringExtra("instructorName"));
                                intent.putExtra("instructorEmail", getIntent().getStringExtra("instructorEmail"));
                                intent.putExtra("instructorPhone", getIntent().getStringExtra("instructorPhone"));
                                intent.putExtra("associatedTerm", getIntent().getIntExtra("associatedTerm", -1));
                                intent.putExtra("courseNotes", getIntent().getStringExtra("courseNotes"));
                                intent.putExtra("type", "course");
                                startActivity(intent);
                                finish();
                            }
                        });
                deleteCourse.setOnClickListener(
                        view -> {
                            if (areYouSure){
                                for (CourseEntity course : schoolRepository.getAllCourses()){
                                    if (course.getCourseID()==getIntent().getIntExtra("courseID", -1)) {
                                        schoolRepository.delete(course);
                                        finish();
                                    }
                                }
                            }
                            else {
                                areYouSure = true;
                                deleteCourse.setText("Confirm");
                                editCourse.setText("Cancel");
                                courseNotesTextview.setText("Are you sure you would like to delete this course?");
                                courseNotesTextview.setTextColor(Color.RED);
                            }
                        });
                break;
        }
    }
}