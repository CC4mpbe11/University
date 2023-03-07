package com.CC4mpbe11.university.DetailsScreens;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.CC4mpbe11.university.Database.SchoolRepository;
import com.CC4mpbe11.university.Entities.CourseEntity;
import com.CC4mpbe11.university.Entities.TermEntity;
import com.CC4mpbe11.university.R;
import com.CC4mpbe11.university.RecyclerViewAdapters.CoursesRecAdapter;

import java.util.Calendar;
import java.util.List;

public class TermDetails extends AppCompatActivity {
    //private CourseViewModel mCourseViewModel;
    //private TermViewModel mTermViewModel;
    private SchoolRepository schoolRepository;
    private TermEntity selectedTerm = new TermEntity(0, 0, 0L, 0L);
    private final Calendar startCalendar = Calendar.getInstance();
    private final Calendar endCalendar = Calendar.getInstance();

    private void changeStartCalendar(int year, int month, int day){
        startCalendar.set(Calendar.YEAR, year);
        startCalendar.set(Calendar.MONTH, month -1);
        startCalendar.set(Calendar.DAY_OF_MONTH, day);
        selectedTerm.setTermStart(startCalendar.getTimeInMillis());
    }

    private void changeEndCalendar(int year, int month, int day){
        endCalendar.set(Calendar.YEAR, year);
        endCalendar.set(Calendar.MONTH, month -1);
        endCalendar.set(Calendar.DAY_OF_MONTH, day);
        selectedTerm.setTermEnd(endCalendar.getTimeInMillis());
    }


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_term_details);
        TextView termNumber = findViewById(R.id.termNumber);
        CalendarView termStartCalendar = findViewById(R.id.termStartCalendar);
        CalendarView termEndCalendar = findViewById(R.id.termEndCalendar);
        Button deleteCourse = findViewById(R.id.deleteCourse);
        Button attachCourse = findViewById(R.id.attachCourse);
        Button deleteTerm = findViewById(R.id.deleteTerm);
        Button updateTerm = findViewById(R.id.updateTerm);
        RecyclerView addRemoveCourseRecycler = findViewById(R.id.addRemoveCourseRecycler);

        //mCourseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
        //mTermViewModel = new ViewModelProvider(this).get(TermViewModel.class);

        final CoursesRecAdapter adapter = new CoursesRecAdapter(this, "associatedWithTerm", getIntent().getIntExtra("termID",-1));
        addRemoveCourseRecycler.setAdapter(adapter);
        addRemoveCourseRecycler.setLayoutManager(new LinearLayoutManager(this));


        schoolRepository = new SchoolRepository(getApplication());
        List<TermEntity> allTerms = schoolRepository.getAllTerms();
        List<TermEntity> chosenTerm = schoolRepository.getAllTerms();
        List<CourseEntity> associatedCourses = schoolRepository.getAssociatedCourses(getIntent().getIntExtra("termID",-1));
        List<CourseEntity> allCourses = schoolRepository.getAllCourses();
        chosenTerm.removeAll(allTerms);

        for (TermEntity term : allTerms) {
            if (term.getTermID()==getIntent().getIntExtra("termID", 0)){
                chosenTerm.add(term);
                selectedTerm = term;
            }
        }

        adapter.setCourseWords(associatedCourses);
        adapter.setTermWords(chosenTerm);

        termStartCalendar.setDate(selectedTerm.getTermStart());
        termEndCalendar.setDate(selectedTerm.getTermEnd());
        termNumber.setText(getString(R.string.term_space) + " " + selectedTerm.getTermNumber());

        /*mTermViewModel.getAllTerms().observe(this, new Observer<List<TermEntity>>(){
            @Override
            public void onChanged(@Nullable final List<TermEntity> termWords) {
                for(TermEntity t:termWords) {
                    if(!allTerms.contains(Integer.toString(t.getTermNumber()))) {
                        allTerms.add(Integer.toString(t.getTermNumber()));
                        startCalendar.setTimeInMillis(t.getTermStart());
                        endCalendar.setTimeInMillis(t.getTermEnd());

                        termStartCalendar.setDate(startCalendar.getTimeInMillis());
                        termEndCalendar.setDate(endCalendar.getTimeInMillis());
                    }
                }
            }
        });

        mCourseViewModel.getAllCourses().observe(this, new Observer<List<CourseEntity>>(){
            @Override
            public void onChanged(@Nullable final List<CourseEntity> courseWords) {
                List<CourseEntity> myCourseList=new ArrayList<>();
                for(CourseEntity c:courseWords)if(c.getTermID()==getIntent().getIntExtra("termID",0))myCourseList.add(c);
                adapter.setCourseWords(myCourseList);
                numCourses=myCourseList.size();
            }
        });*/

        CalendarView.OnDateChangeListener startCalendarListener = (view, year, month, day) -> {
            month = month + 1;
            changeStartCalendar(year, month, day);
            String newDate = year+"-"+month+"-"+day;
            Log.d("NEW_DATE", newDate);
        };

        CalendarView.OnDateChangeListener endCalendarListener = (view, year, month, day) -> {
            month = month + 1;
            changeEndCalendar(year, month, day);
            String newDate = year+"-"+month+"-"+day;
            Log.d("NEW_DATE", newDate);
        };

        termStartCalendar.setOnDateChangeListener(startCalendarListener);
        termEndCalendar.setOnDateChangeListener(endCalendarListener);
        TermEntity finalSelectedTerm = selectedTerm;

        deleteCourse.setOnClickListener(
                view -> {
                    Intent intent = new Intent(TermDetails.this, AttachRemoveCourse.class);
                    intent.putExtra("termID",getIntent().getIntExtra("termID",-1));
                    intent.putExtra("attachOrRemove", "remove");
                    startActivity(intent);
                    finish();
                }
        );

        attachCourse.setOnClickListener(
                view -> {
                    Intent intent = new Intent(TermDetails.this, AttachRemoveCourse.class);
                    intent.putExtra("termID",getIntent().getIntExtra("termID",-1));
                    intent.putExtra("attachOrRemove", "attach");
                    startActivity(intent);
                    finish();
                }
        );

        updateTerm.setOnClickListener(
                view -> {
                    schoolRepository.update(finalSelectedTerm);
                    finish();
                }
        );

        deleteTerm.setOnClickListener(
                view -> {
                    boolean allowDelete = true;
                    for (CourseEntity course : allCourses) {
                        if(course.getTermID() == getIntent().getIntExtra("termID",-1)){
                            allowDelete = false;
                        }
                    }
                    if (allowDelete) {
                        schoolRepository.delete(finalSelectedTerm);
                        finish();
                    }
                    else{
                        Toast toast = Toast.makeText(getApplicationContext(), R.string.cant_delete_term_toast,Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
        );
    }
}