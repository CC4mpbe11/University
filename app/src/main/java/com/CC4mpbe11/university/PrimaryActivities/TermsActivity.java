package com.CC4mpbe11.university.PrimaryActivities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.CC4mpbe11.university.Database.SchoolRepository;
import com.CC4mpbe11.university.DetailsScreens.EditTermActivity;
import com.CC4mpbe11.university.Entities.CourseEntity;
import com.CC4mpbe11.university.Entities.TermEntity;
import com.CC4mpbe11.university.R;
import com.CC4mpbe11.university.RecyclerViewAdapters.TermsRecAdapter;

import java.util.List;

public class  TermsActivity extends AppCompatActivity {

    private SchoolRepository schoolRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);

        //mTermViewModel = new ViewModelProvider(this).get(TermViewModel.class);
        //mCourseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);

        schoolRepository = new SchoolRepository(getApplication());

        Button editTermButton = findViewById(R.id.editTermButton);
        editTermButton.setOnClickListener(
                view -> {
                    Intent intent = new Intent(TermsActivity.this, EditTermActivity.class);
                    startActivity(intent);
                    finish();
                }
        );

        Button newTermButton = findViewById(R.id.newTermButton);
        newTermButton.setOnClickListener(
                view -> {
                    Intent intent = new Intent(TermsActivity.this, SchedulerActivity.class);
                    intent.putExtra("termID", schoolRepository.getNewTermID());
                    //startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
                    intent.putExtra("type", "newTerm");
                    startActivity(intent);
                    finish();
                }
        );

        /*Button newTermButton = (Button)findViewById(R.id.newTermButton);
        newTermButton.setOnClickListener(
                new Button.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(TermsActivity.this, SchedulerActivity.class);
                        intent.putExtra("termID",mTermViewModel.lastID()+1);
                        //startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
                        intent.putExtra("type", "newTerm");
                        startActivity(intent);
                    }
                }
        );*/

        String recVersion = "Non Edit";
        RecyclerView termsRecycler = findViewById(R.id.termsRecycler);
        final TermsRecAdapter adapter = new TermsRecAdapter(this, recVersion);
        termsRecycler.setAdapter(adapter);
        termsRecycler.setLayoutManager(new LinearLayoutManager(this));
        List<TermEntity> allTerms = schoolRepository.getAllTerms();
        //private TermViewModel mTermViewModel;
        //private CourseViewModel mCourseViewModel;
        List<CourseEntity> associatedCourses = schoolRepository.getAllCourses();
        adapter.setTermWords(allTerms);
        adapter.setCourseWords(associatedCourses);

        /*mTermViewModel.getAllTerms().observe(this, new Observer<List<TermEntity>>(){
            @Override
            public void onChanged(@Nullable final List<TermEntity> termWords) {
                ArrayList<TermEntity> myList = new ArrayList();
                myList.addAll(termWords);
                adapter.setTermWords(myList);
            }
        });

        mCourseViewModel.getAllCourses().observe(this, new Observer<List<CourseEntity>>(){
            @Override
            public void onChanged(@Nullable final List<CourseEntity> courseWords) {
                ArrayList<CourseEntity> myOtherList = new ArrayList();
                myOtherList.addAll(courseWords);
                adapter.setCourseWords(myOtherList);
            }
        });*/
    }

   /* public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            TermEntity term = new TermEntity(mTermViewModel.lastID()+1, data.getIntExtra("termNumber",1), 1639505717740L, 1639505717740L);
            mTermViewModel.insert(term);
        }
    }*/
}