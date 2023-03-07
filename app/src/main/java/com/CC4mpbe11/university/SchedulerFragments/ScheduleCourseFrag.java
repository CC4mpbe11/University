package com.CC4mpbe11.university.SchedulerFragments;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.CC4mpbe11.university.Database.SchoolRepository;
import com.CC4mpbe11.university.Entities.CourseEntity;
import com.CC4mpbe11.university.Entities.TermEntity;
import com.CC4mpbe11.university.R;
import com.CC4mpbe11.university.Receivers.TheReceiver;

import org.apache.commons.validator.routines.EmailValidator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ScheduleCourseFrag extends Fragment implements OnItemSelectedListener {

    //private CourseViewModel mCourseViewModel;
    //private TermViewModel mTermViewModel;
    private final Calendar comparisonStartCalendar = Calendar.getInstance();
    private final Calendar courseStartCalendar = Calendar.getInstance();
    private final Calendar courseEndCalendar = Calendar.getInstance();
    protected List<String> allTermsStrings = new ArrayList<>();
    boolean selectedFlag = true;
    private SchoolRepository schoolRepository;
    private List<TermEntity> allTerms;
    private boolean existingCourse = false;
    private int courseID = -1;
    private TermEntity selectedTerm = new TermEntity(-1, -1, 0L, 0L);
    private CourseEntity selectedCourse = new CourseEntity(-1, "", "", "", "", "", "", "Plan to take", 0L, 0L, "", -1);

    private void courseStartCalendarChanger(int year, int month, int day){
        courseStartCalendar.set(Calendar.YEAR, year);
        courseStartCalendar.set(Calendar.MONTH, month - 1);
        courseStartCalendar.set(Calendar.DAY_OF_MONTH, day);
        courseStartCalendar.set(Calendar.HOUR_OF_DAY, 6);
        courseStartCalendar.set(Calendar.MINUTE, 0);
        courseStartCalendar.set(Calendar.SECOND, 0);
        courseStartCalendar.set(Calendar.MILLISECOND, 0);
    }
    private void courseEndCalendarChanger(int year, int month, int day){
        courseEndCalendar.set(Calendar.YEAR, year);
        courseEndCalendar.set(Calendar.MONTH, month - 1);
        courseEndCalendar.set(Calendar.DAY_OF_MONTH, day);
        courseEndCalendar.set(Calendar.HOUR_OF_DAY, 6);
        courseEndCalendar.set(Calendar.MINUTE, 0);
        courseEndCalendar.set(Calendar.SECOND, 0);
        courseEndCalendar.set(Calendar.MILLISECOND, 0);
    }

    public ScheduleCourseFrag() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Calendar tempCalendar = Calendar.getInstance();
        selectedCourse.setCourseStart(tempCalendar.getTimeInMillis());
        selectedCourse.setCourseEnd(tempCalendar.getTimeInMillis());

        //mCourseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
        //mTermViewModel = new ViewModelProvider(this).get(TermViewModel.class);

        /*mTermViewModel.getAllTerms().observe(this.getActivity(), new Observer<List<TermEntity>>(){
            @Override
            public void onChanged(@Nullable final List<TermEntity> termWords) {
                for(TermEntity t:termWords) {
                    if(!allTerms.contains(Integer.toString(t.getTermNumber()))) {
                        allTerms.add(Integer.toString(t.getTermNumber()));
                    }
                }
            }
        });*/
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule_course, container, false);

        schoolRepository = new SchoolRepository(requireActivity().getApplication());
        allTerms = schoolRepository.getAllTerms();
        for(TermEntity term : allTerms) {
            if(term.getTermID() != -1) {
                allTermsStrings.add(Integer.toString(term.getTermNumber()));
            }
        }
        List<CourseEntity> allCourses = schoolRepository.getAllCourses();
        try {
            if (this.getArguments() != null) {
                if (this.getArguments().getString("type").equals("existingCourse")){
                    existingCourse = true;
                    for (CourseEntity course : allCourses){
                        if (course.getCourseID() == this.getArguments().getInt("courseID")){
                            courseStartCalendar.setTimeInMillis(course.getCourseStart());
                            courseEndCalendar.setTimeInMillis(course.getCourseEnd());
                        }
                    }
                }
                else{
                    existingCourse = false;
                }
            }
            if (this.getArguments() != null) {
                courseID = this.getArguments().getInt("courseID", -1);
            }
            for (CourseEntity course : allCourses) {
                if (course.getCourseID() == courseID && course.getCourseID() != -1){
                    selectedCourse = course;
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        if (courseID == -1) {
            courseID = schoolRepository.getNewCourseID();
        }

        EditText courseName = view.findViewById(R.id.courseName);
        EditText courseDescription = view.findViewById(R.id.courseDescription);
        EditText instructorName = view.findViewById(R.id.instructorName);
        EditText instructorPhone = view.findViewById(R.id.instructorPhone);
        EditText instructorEmail = view.findViewById(R.id.instructorEmail);
        EditText courseCode = view.findViewById(R.id.courseCode);
        Spinner courseStatusSpinner = view.findViewById(R.id.statusSpinner);
        Spinner termSpinner = view.findViewById(R.id.termSpinner);
        CalendarView endCalendarView = view.findViewById(R.id.endCalendar);
        CalendarView startCalendarView = view.findViewById(R.id.startCalendar);
        EditText courseNotes = view.findViewById(R.id.courseNotes);
        Switch remindMeCourseSwitch = view.findViewById(R.id.remindMeCourseSwitch);
        //Switch testMeCourseSwitch = view.findViewById(R.id.testMeCourseSwitch);
        boolean notifyMe = false;
        remindMeCourseSwitch.setChecked(notifyMe);

        ScrollView courseScrollView = view.findViewById(R.id.courseScrollView);
        courseScrollView.setOnTouchListener((v, event) -> {
            courseName.clearFocus();
            courseDescription.clearFocus();
            instructorName.clearFocus();
            instructorPhone.clearFocus();
            instructorEmail.clearFocus();
            courseCode.clearFocus();
            courseNotes.clearFocus();
            return false;
        });

        startCalendarView.setDate(selectedCourse.getCourseStart());
        endCalendarView.setDate(selectedCourse.getCourseEnd());

        List<String> courseStatus = new ArrayList<>();
        courseStatus.add("In Progress");
        courseStatus.add("Completed");
        courseStatus.add("Dropped");
        courseStatus.add("Plan to Take");

        ArrayAdapter<String> statusAdapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, courseStatus);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseStatusSpinner.setAdapter(statusAdapter);
        courseStatusSpinner.setOnItemSelectedListener(this);
        courseStatusSpinner.setSelection(0);

        ArrayAdapter<String> termAdapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, allTermsStrings);
        termAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        termSpinner.setAdapter(termAdapter);
        termSpinner.setOnItemSelectedListener(this);
        termSpinner.setSelection(0);

        CalendarView.OnDateChangeListener startCalendarListener = (view1, year, month, day) -> {
            month = month + 1;
            courseStartCalendarChanger(year, month , day);
            String newDate = year+"-"+month+"-"+day;
            Log.d("NEW_DATE", newDate);
        };

        CalendarView.OnDateChangeListener endCalendarListener = (view12, year, month, day) -> {
            month = month + 1;
            courseEndCalendarChanger(year, month , day);
            String newDate = year+"-"+month+"-"+day;
            Log.d("NEW_DATE", newDate);
        };

        startCalendarView.setOnDateChangeListener(startCalendarListener);
        endCalendarView.setOnDateChangeListener(endCalendarListener);

        try {
            if (this.getArguments() != null) {
                courseName.setText(this.getArguments().getString("courseName"));
                courseDescription.setText(this.getArguments().getString("courseDescription"));
                instructorName.setText(this.getArguments().getString("instructorName"));
                instructorPhone.setText(this.getArguments().getString("instructorPhone"));
                instructorEmail.setText(this.getArguments().getString("instructorEmail"));
                courseCode.setText(this.getArguments().getString("courseCode"));
                courseNotes.setText(this.getArguments().getString("courseNotes"));
                courseStatusSpinner.setSelection(statusAdapter.getPosition(this.getArguments().getString("courseStatus")));
            }
            } catch (Exception e) {
                e.printStackTrace();
            }

        Button emailCourseNotes = view.findViewById(R.id.emailCourseNotesButton);
        emailCourseNotes.setOnClickListener(
                view13 -> {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, courseNotes.getText());
                    sendIntent.putExtra(Intent.EXTRA_TITLE, courseName.getText() + "'s notes");
                    sendIntent.setType("text/plain");
                    Intent shareIntent = Intent.createChooser(sendIntent, null);
                    startActivity(shareIntent);
                }
        );

        Button acceptTermButton = view.findViewById(R.id.scheduleCourseButton);
        acceptTermButton.setOnClickListener(view14 -> {
            String courseCode1 = "null";
            String courseName1 = "null";
            String courseDescription1 = "null";
            String courseInstructor = "null";
            String courseInstructorPhone = "null";
            String courseInstructorEmail = "null";
            String courseStatus1;
            String courseNotes1;
            int termID = -1;

            EditText courseNameText = requireView().findViewById(R.id.courseName);
            EditText courseDescriptionText = requireView().findViewById(R.id.courseDescription);
            EditText instructorNameText = requireView().findViewById(R.id.instructorName);
            EditText instructorPhoneText = requireView().findViewById(R.id.instructorPhone);
            EditText instructorEmailText = requireView().findViewById(R.id.instructorEmail);
            EditText courseCodeText = requireView().findViewById(R.id.courseCode);
            Spinner courseStatusSpinner1 = requireView().findViewById(R.id.statusSpinner);
            Spinner termSpinner1 = requireView().findViewById(R.id.termSpinner);
            EditText courseNotesText = requireView().findViewById(R.id.courseNotes);


            boolean problemWithEntry = false;

            if (courseNameText.getText().toString().equals("")) {
                courseNameText.setHint("Enter Course Name");
                courseNameText.setHintTextColor(Color.RED);
                courseNameText.requestFocus();
                problemWithEntry = true;
            }
            else{
                courseName1 = courseNameText.getText().toString();
            }

            if (courseDescriptionText.getText().toString().equals("")) {
                courseDescriptionText.setHint("Enter a description");
                courseDescriptionText.setHintTextColor(Color.RED);
                courseDescriptionText.requestFocus();
                problemWithEntry = true;
            }
            else{
                courseDescription1 = courseDescriptionText.getText().toString();
            }

            if (instructorNameText.getText().toString().equals("")) {
                instructorNameText.setHint("Enter Instructor Name");
                instructorNameText.setHintTextColor(Color.RED);
                instructorNameText.requestFocus();
                problemWithEntry = true;
            }
            else{
                courseInstructor = instructorNameText.getText().toString();
            }

            if (!isPhoneNumber(instructorPhoneText.getText().toString())) {
                instructorPhoneText.setText("");
                instructorPhoneText.setHint("Enter 10 Digits");
                instructorPhoneText.setHintTextColor(Color.RED);
                instructorPhoneText.requestFocus();
                problemWithEntry = true;
            }
            else{
                courseInstructorPhone = instructorPhoneText.getText().toString();
            }

            EmailValidator emailValidator = EmailValidator.getInstance();
            if (!emailValidator.isValid(instructorEmailText.getText().toString())) {
                instructorEmailText.setText("");
                instructorEmailText.setHint("Enter Valid Email");
                instructorEmailText.setHintTextColor(Color.RED);
                instructorEmailText.requestFocus();
                problemWithEntry = true;
            }
            else{
                courseInstructorEmail = instructorEmailText.getText().toString();
            }

            if (courseCodeText.getText().toString().equals("")) {
                courseCodeText.setHint("Enter Course Code");
                courseCodeText.setHintTextColor(Color.RED);
                courseCodeText.requestFocus();
                problemWithEntry = true;
            }
            else{
                courseCode1 = courseCodeText.getText().toString();
            }

            for (TermEntity term : allTerms) {
                if(term.getTermNumber() == Integer.parseInt(termSpinner1.getSelectedItem().toString())){
                    selectedTerm = term;
                    termID = term.getTermID();
                }
            }

            if(courseStartCalendar.getTimeInMillis() > courseEndCalendar.getTimeInMillis()) {
                problemWithEntry = true;
                Toast calendarToast = Toast.makeText(requireView().getContext(), "The course must start before it can end.", Toast.LENGTH_LONG);
                calendarToast.show();
            }
            if(courseStartCalendar.getTimeInMillis() < comparisonStartCalendar.getTimeInMillis()){
                problemWithEntry = true;
                Toast calendarToast = Toast.makeText(requireView().getContext(), "Course must be Scheduled for future dates.", Toast.LENGTH_LONG);
                calendarToast.show();
            }
            if (selectedTerm.getTermStart() > courseStartCalendar.getTimeInMillis() || selectedTerm.getTermEnd() < courseEndCalendar.getTimeInMillis()){
                problemWithEntry = true;
                Toast calendarToast = Toast.makeText(requireView().getContext(), "Course must be Scheduled within term dates.", Toast.LENGTH_LONG);
                calendarToast.show();
            }

            courseNotes1 = courseNotesText.getText().toString();
            courseStatus1 = courseStatusSpinner1.getSelectedItem().toString();

            if(!problemWithEntry) {

                if (existingCourse){
                    CourseEntity courseEntity = new CourseEntity(courseID,
                            courseCode1,
                            courseName1,
                            courseDescription1,
                            courseInstructor,
                            courseInstructorPhone,
                            courseInstructorEmail,
                            courseStatus1,
                            courseStartCalendar.getTimeInMillis(),
                            courseEndCalendar.getTimeInMillis(),
                            courseNotes1,
                            termID);
                    schoolRepository.update(courseEntity);
                    if (remindMeCourseSwitch.isChecked()){
                        startAlarm(courseStartCalendar, courseEndCalendar, courseName1);
                    }
                    /*if (testMeCourseSwitch.isChecked()){
                        Toast testNotifToast = Toast.makeText(requireView().getContext(), "Course start and end notifications will show in 20 and 30 seconds respectively.", Toast.LENGTH_LONG);
                        testNotifToast.show();
                        Calendar startTestCalendar = Calendar.getInstance();
                        startTestCalendar.add(Calendar.SECOND, 20);
                        Calendar endTestCalendar = Calendar.getInstance();
                        endTestCalendar.add(Calendar.SECOND, 30);
                        startAlarm(startTestCalendar, endTestCalendar, courseName1);
                    }*/
                    requireActivity().finish();
                }
                if (!existingCourse){
                    CourseEntity courseEntity = new CourseEntity(courseID,
                            courseCode1,
                            courseName1,
                            courseDescription1,
                            courseInstructor,
                            courseInstructorPhone,
                            courseInstructorEmail,
                            courseStatus1,
                            courseStartCalendar.getTimeInMillis(),
                            courseEndCalendar.getTimeInMillis(),
                            courseNotes1,
                            termID);
                    schoolRepository.insert(courseEntity);
                    if (remindMeCourseSwitch.isChecked()){
                        startAlarm(courseStartCalendar, courseEndCalendar, courseName1);
                    }
                    /*if (testMeCourseSwitch.isChecked()){
                        Toast testNotifToast = Toast.makeText(requireView().getContext(), "Course start and end notifications will show in 20 and 30 seconds respectively.", Toast.LENGTH_LONG);
                        testNotifToast.show();
                        Calendar startTestCalendar = Calendar.getInstance();
                        startTestCalendar.add(Calendar.SECOND, 20);
                        Calendar endTestCalendar = Calendar.getInstance();
                        endTestCalendar.add(Calendar.SECOND, 30);
                        startAlarm(startTestCalendar, endTestCalendar, courseName1);
                    }*/
                    requireActivity().finish();
                }
                //mCourseViewModel.insert(course);
            }
            else{
                Toast entryToast = Toast.makeText(requireView().getContext(), "Problem with entered information.", Toast.LENGTH_LONG);
                entryToast.show();
            }
        });

        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (selectedFlag) {
            try {
                if (this.getArguments() != null && this.getArguments().getString("type").equals("course")) {
                    Spinner termSpinner = requireView().findViewById(R.id.termSpinner);
                    ArrayAdapter<String> termAdapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, allTermsStrings);
                    termAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    termSpinner.setAdapter(termAdapter);
                    termSpinner.setOnItemSelectedListener(this);
                    termSpinner.setSelection(termAdapter.getPosition(this.getArguments().getString("associatedTerm")));
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            selectedFlag = false;
        }
    }

    private void startAlarm(Calendar calendar, Calendar calendar2, String whichCourse) {
        AlarmManager alarmManager1 = (AlarmManager) requireActivity().getApplication().getSystemService(Context.ALARM_SERVICE);
        Intent intent1 = new Intent(requireActivity().getApplication(), TheReceiver.class);
        intent1.putExtra("alarmType", "courseStart");
        intent1.putExtra("whichCourse", whichCourse);
        intent1.setAction(Long.toString(System.currentTimeMillis()));
        @SuppressLint("UnspecifiedImmutableFlag") PendingIntent pendingIntent1 = PendingIntent.getBroadcast(requireActivity().getApplication(), 1, intent1, 0 /*PendingIntent.FLAG_UPDATE_CURRENT*/);
        alarmManager1.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent1);

        AlarmManager alarmManager2 = (AlarmManager) requireActivity().getApplication().getSystemService(Context.ALARM_SERVICE);
        Intent intent2 = new Intent(requireActivity().getApplication(), TheReceiver.class);
        intent2.putExtra("alarmType", "courseEnd");
        intent2.putExtra("whichCourse", whichCourse);
        intent2.setAction(Long.toString(System.currentTimeMillis()));
        @SuppressLint("UnspecifiedImmutableFlag") PendingIntent pendingIntent2 = PendingIntent.getBroadcast(requireActivity().getApplication(), 1, intent2, 0/*PendingIntent.FLAG_UPDATE_CURRENT*/);
        alarmManager2.setExact(AlarmManager.RTC_WAKEUP, calendar2.getTimeInMillis(), pendingIntent2);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    static boolean isPhoneNumber(String phoneNo) {
        if (null == phoneNo || phoneNo.length() != 10) {
            return false;
        }
        for (char c : phoneNo.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
}