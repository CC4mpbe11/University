package com.CC4mpbe11.university.DetailsScreens;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.CC4mpbe11.university.Database.SchoolRepository;
import com.CC4mpbe11.university.Entities.CourseEntity;
import com.CC4mpbe11.university.R;
import com.CC4mpbe11.university.RecyclerViewAdapters.CoursesRecAdapter;

import java.util.List;

public class AttachRemoveCourse extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attach_remove_course);
        //mCourseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
        SchoolRepository schoolRepository = new SchoolRepository(getApplication());

        RecyclerView addRemoveCourseRecycler = findViewById(R.id.addRemoveCourseRecycler);
        TextView addRemoveTextView = findViewById(R.id.addRemoveTextView);

        String giveRecVersion = "attachToTerm";
        if(getIntent().getStringExtra("attachOrRemove").equals("remove")){
            giveRecVersion = "removeFromTerm";
        }

        final CoursesRecAdapter adapter = new CoursesRecAdapter(this, giveRecVersion, getIntent().getIntExtra("termID",-1));
        addRemoveCourseRecycler.setAdapter(adapter);
        addRemoveCourseRecycler.setLayoutManager(new LinearLayoutManager(this));

        if(getIntent().getStringExtra("attachOrRemove").equals("remove")){
            addRemoveTextView.setText(R.string.choose_course_detach);
            //private CourseViewModel mCourseViewModel;
            List<CourseEntity> associatedCourses = schoolRepository.getAssociatedCourses(getIntent().getIntExtra("termID", -1));
            adapter.setCourseWords(associatedCourses);
            /*mCourseViewModel.getAllCourses().observe(this, new Observer<List<CourseEntity>>(){
                @Override
                public void onChanged(@Nullable final List<CourseEntity> courseWords) {
                    List<CourseEntity> myCourseList=new ArrayList<>();
                    for(CourseEntity c:courseWords)if(c.getTermID()==getIntent().getIntExtra("termID",0))myCourseList.add(c);
                    adapter.setCourseWords(myCourseList);
                }
            });*/
        }
        else{
            addRemoveTextView.setText(R.string.choose_course_attach);
            List<CourseEntity> unassociatedCourses = schoolRepository.getUnassociatedCourses(getIntent().getIntExtra("termID", -1));
            adapter.setCourseWords(unassociatedCourses);
            /*mCourseViewModel.getAllCourses().observe(this, new Observer<List<CourseEntity>>(){
                @Override
                public void onChanged(@Nullable final List<CourseEntity> courseWords) {
                    List<CourseEntity> myCourseList=new ArrayList<>();
                    for(CourseEntity c:courseWords)if(c.getTermID()!=getIntent().getIntExtra("termID",0))myCourseList.add(c);
                    adapter.setCourseWords(myCourseList);
                }
            });*/
        }
    }
}