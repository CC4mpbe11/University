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
import com.CC4mpbe11.university.Entities.AssessmentEntity;
import com.CC4mpbe11.university.Entities.CourseEntity;
import com.CC4mpbe11.university.R;
import com.CC4mpbe11.university.Receivers.TheReceiver;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ScheduleAssessmentFrag extends Fragment implements OnItemSelectedListener {

    //private CourseViewModel mCourseViewModel;
    //private AssessmentViewModel mAssessmentViewModel;
    private final Calendar assessmentCalendar = Calendar.getInstance();
    boolean selectedFlag = true;
    private SchoolRepository schoolRepository;
    private AssessmentEntity theAssessment;
    private List<CourseEntity> allCourses;
    private boolean existingAssessment = false;
    private int assessmentID = -1;

    private void assessmentCalendarChanger(int year, int month, int day, int hourOfDay, int minute){
        assessmentCalendar.set(Calendar.YEAR, year);
        assessmentCalendar.set(Calendar.MONTH, month - 1);
        assessmentCalendar.set(Calendar.DAY_OF_MONTH, day);
        assessmentCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        assessmentCalendar.set(Calendar.MINUTE, minute - 1);
        assessmentCalendar.set(Calendar.SECOND, 0);
        assessmentCalendar.set(Calendar.MILLISECOND, 0);
    }

    public ScheduleAssessmentFrag() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mAssessmentViewModel= new ViewModelProvider(this).get(AssessmentViewModel.class);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule_assessment, container, false);
        schoolRepository = new SchoolRepository(requireActivity().getApplication());
        allCourses = schoolRepository.getAllCourses();
        List<AssessmentEntity> allAssessments = schoolRepository.getAllAssessments();



        EditText assessmentName = view.findViewById(R.id.assessmentName);
        EditText assessmentCode = view.findViewById(R.id.assessmentCode);
        EditText assessmentVendor = view.findViewById(R.id.assessmentVendor);
        Spinner assessmentTypeSpinner = view.findViewById(R.id.assessmentTypeSpinner);
        Spinner associatedCourseSpinner = view.findViewById(R.id.associatedCourseSpinner);
        Spinner assessmentHoursSpinner = view.findViewById(R.id.assessmentHoursSpinner);
        Spinner assessmentMinutesSpinner = view.findViewById(R.id.assessmentMinutesSpinner);
        Spinner assessmentAMPMSpinner = view.findViewById(R.id.assessmentAMPMSpinner);
        CalendarView assessmentCalendarView = view.findViewById(R.id.assessmentDateCalendar);
        Switch remindMeAssessmentSwitch = view.findViewById(R.id.remindMeAssessmentSwitch);
        boolean notifyMe = false;
        remindMeAssessmentSwitch.setChecked(notifyMe);
        ScrollView assessmentScrollView = view.findViewById(R.id.assessmentScrollView);

        try {
            if (this.getArguments() != null) {
                if (this.getArguments().getString("type").equals("existingAssessment")){
                    existingAssessment = true;
                    assessmentID = this.getArguments().getInt("assessmentID");
                    for (AssessmentEntity assessment : allAssessments) {
                        if (assessment.getAssessmentID() == assessmentID){
                            theAssessment = assessment;
                        }
                    }
                    assessmentName.setText(theAssessment.getAssessmentName());
                    assessmentCode.setText(theAssessment.getAssessmentCode());
                    assessmentVendor.setText(theAssessment.getAssessmentVendor());
                    assessmentCalendar.setTimeInMillis(theAssessment.getAssessmentCalendar());
                }
                else {
                    theAssessment = new AssessmentEntity(-1, "", "", "", "", assessmentCalendar.getTimeInMillis(), -1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        assessmentScrollView.setOnTouchListener((v, event) -> {
            assessmentName.clearFocus();
            assessmentCode.clearFocus();
            assessmentVendor.clearFocus();
            return false;
        });

        List<String> assessmentTypes = new ArrayList<>();
        assessmentTypes.add("Objective");
        assessmentTypes.add("Performance");

        final List<String> associatedCourses = new ArrayList<>();
        associatedCourses.add("Choose Course");
        for (CourseEntity course : allCourses) {
            associatedCourses.add(course.getCourseName());
        }

        List<String> assessmentHours = new ArrayList<>();
        assessmentHours.add("01");
        assessmentHours.add("02");
        assessmentHours.add("03");
        assessmentHours.add("04");
        assessmentHours.add("05");
        assessmentHours.add("06");
        assessmentHours.add("07");
        assessmentHours.add("08");
        assessmentHours.add("09");
        assessmentHours.add("10");
        assessmentHours.add("11");
        assessmentHours.add("12");

        List<String> assessmentMinutes = new ArrayList<>();
        assessmentMinutes.add("00");
        assessmentMinutes.add("15");
        assessmentMinutes.add("30");
        assessmentMinutes.add("45");

        List<String> assessmentAMPM = new ArrayList<>();
        assessmentAMPM.add("AM");
        assessmentAMPM.add("PM");

        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, assessmentTypes);
        ArrayAdapter<String> associationAdapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, associatedCourses);
        ArrayAdapter<String> hoursAdapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, assessmentHours);
        ArrayAdapter<String> minutesAdapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, assessmentMinutes);
        ArrayAdapter<String> AMPMAdapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, assessmentAMPM);

        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        associationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hoursAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        minutesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        AMPMAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        int associationPosition = 0;
        int typePosition = 0;
        int hourPosition = 0;
        int minutePosition = 0;
        int amPmPosition = 0;
        try{
            if (theAssessment.getAssessmentID() != -1) {
                if (this.getArguments() != null && this.getArguments().getString("type").equals("existingAssessment")) {
                    associationPosition = associationAdapter.getPosition(this.getArguments().getString("associatedCourseName"));
                    typePosition = typeAdapter.getPosition(theAssessment.getAssessmentType());
                    hourPosition = hoursAdapter.getPosition(theAssessment.getAssessmentStartHhMm().substring(0, 2));
                    minutePosition = minutesAdapter.getPosition(theAssessment.getAssessmentStartHhMm().substring(3, 5));
                    amPmPosition = theAssessment.getAssessmentCalendarObj().get(Calendar.AM_PM);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        assessmentTypeSpinner.setAdapter(typeAdapter);
        associatedCourseSpinner.setAdapter(associationAdapter);
        assessmentHoursSpinner.setAdapter(hoursAdapter);
        assessmentMinutesSpinner.setAdapter(minutesAdapter);
        assessmentAMPMSpinner.setAdapter(AMPMAdapter);

        assessmentTypeSpinner.setSelection(typePosition);
        associatedCourseSpinner.setSelection(associationPosition);
        //int amPMPosition = AMPMAdapter.getPosition();
        assessmentHoursSpinner.setSelection(hourPosition);
        assessmentMinutesSpinner.setSelection(minutePosition);
        assessmentAMPMSpinner.setSelection(amPmPosition);

        assessmentTypeSpinner.setOnItemSelectedListener(this);
        associatedCourseSpinner.setOnItemSelectedListener(this);
        assessmentHoursSpinner.setOnItemSelectedListener(this);
        assessmentMinutesSpinner.setOnItemSelectedListener(this);
        assessmentAMPMSpinner.setOnItemSelectedListener(this);

        //mCourseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
        /*mCourseViewModel.getAllCourses().observe(this.getActivity(), new Observer<List<CourseEntity>>(){
            @Override
            public void onChanged(@Nullable final List<CourseEntity> courseWords) {
                for(CourseEntity c:courseWords) {
                    if(!associatedCourses.contains(c.getCourseName())) {associatedCourses.add(c.getCourseName());}
                }
            }
        });*/

        try {
            assessmentCalendarView.setDate(theAssessment.getAssessmentCalendar());
        } catch (Exception e) {
            e.printStackTrace();
        }
        CalendarView.OnDateChangeListener assessmentCalendarListener = (view1, year, month, day) -> {
            month = month + 1;
            if (theAssessment.getAssessmentID() != -1) {
                int hourOfDay = theAssessment.getAssessmentCalendarObj().get(Calendar.HOUR_OF_DAY);
                int minute = theAssessment.getAssessmentCalendarObj().get(Calendar.MINUTE);
                assessmentCalendarChanger(year, month, day, hourOfDay, minute);
            } else {
                assessmentCalendarChanger(year, month, day, 12, 0);
            }
            String newDate = year+"-"+month+"-"+day;
            Log.d("NEW_DATE", newDate);
        };
        assessmentCalendarView.setOnDateChangeListener(assessmentCalendarListener);

        Button assessmentScheduleButton = view.findViewById(R.id.assessmentScheduleButton);
        assessmentScheduleButton.setOnClickListener(view12 -> {
            Intent replyIntent = new Intent();
            String assessmentName1 = "null";
            String assessmentCode1 = "null";
            String assessmentType = "null";
            String assessmentVendor1 = "null";
            String associatedCourse = "null";
            String assessmentHours1 = "null";
            String assessmentMinutes1 = "null";
            int courseID = -1;

            EditText assessmentNameText = requireView().findViewById(R.id.assessmentName);
            EditText assessmentCodeText = requireView().findViewById(R.id.assessmentCode);
            EditText assessmentVendorText = requireView().findViewById(R.id.assessmentVendor);
            Spinner assessmentTypeSpinner1 = requireView().findViewById(R.id.assessmentTypeSpinner);
            Spinner associatedCourseSpinner1 = requireView().findViewById(R.id.associatedCourseSpinner);
            Spinner assessmentHoursSpinner1 = requireView().findViewById(R.id.assessmentHoursSpinner);
            Spinner assessmentMinutesSpinner1 = requireView().findViewById(R.id.assessmentMinutesSpinner);
            Spinner assessmentAMPMSpinner1 = requireView().findViewById(R.id.assessmentAMPMSpinner);



            boolean problemWithEntry = false;
            if (assessmentNameText.getText().toString().equals("")) {
                assessmentNameText.setHint("Enter Assessment Name");
                assessmentNameText.setHintTextColor(Color.RED);
                assessmentNameText.requestFocus();
                problemWithEntry = true;
            }
            else{
                assessmentName1 = assessmentNameText.getText().toString();
            }

            if (assessmentCodeText.getText().toString().equals("")) {
                assessmentCodeText.setHint("Enter Assessment Code");
                assessmentCodeText.setHintTextColor(Color.RED);
                assessmentCodeText.requestFocus();
                problemWithEntry = true;
            }
            else{
                assessmentCode1 = assessmentCodeText.getText().toString();
            }

            if (assessmentVendorText.getText().toString().equals("")) {
                assessmentVendorText.setHint("Enter Assessment Vendor");
                assessmentVendorText.setHintTextColor(Color.RED);
                assessmentVendorText.requestFocus();
                problemWithEntry = true;
            }
            else{
                assessmentVendor1 = assessmentVendorText.getText().toString();
            }

            if (assessmentTypeSpinner1.getSelectedItem().toString().equals("")) {
                assessmentTypeSpinner1.setSelection(0);
                problemWithEntry = true;
            }
            else{
                assessmentType = assessmentTypeSpinner1.getSelectedItem().toString();
            }

            if (associatedCourseSpinner1.getCount()==0) {
                problemWithEntry = true;
            }
            else{
                for (CourseEntity course : allCourses) {
                    if (course.getCourseName().equals(associatedCourseSpinner1.getSelectedItem().toString())){
                        courseID = course.getCourseID();
                    }
                }
            }

            if (assessmentHoursSpinner1.getSelectedItem().toString().equals("")) {
                assessmentHoursSpinner1.setSelection(0);
                problemWithEntry = true;
            }
            else{
                assessmentHours1 = assessmentHoursSpinner1.getSelectedItem().toString();
            }

            if (assessmentMinutesSpinner1.getSelectedItem().toString().equals("")) {
                assessmentMinutesSpinner1.setSelection(0);
                problemWithEntry = true;
            }
            else{
                assessmentMinutes1 = assessmentMinutesSpinner1.getSelectedItem().toString();
            }

            if (assessmentAMPMSpinner1.getSelectedItem().toString().equals("")) {
                assessmentAMPMSpinner1.setSelection(0);
                problemWithEntry = true;
            }

            Calendar calendar = assessmentCalendar;
            calendar.set(Calendar.SECOND, 0);
            int assessmentHoursInteger;
            if (Integer.parseInt(assessmentHours1) == 12){
                assessmentHoursInteger = 0;
            }
            else{
                assessmentHoursInteger = Integer.parseInt(assessmentHours1);
            }
            calendar.set(Calendar.HOUR, assessmentHoursInteger);
            calendar.set(Calendar.MINUTE,Integer.parseInt(assessmentMinutes1));
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            calendar.set(Calendar.AM_PM, assessmentAMPMSpinner1.getSelectedItemPosition());

            if (!existingAssessment) {
                assessmentID = schoolRepository.getNewAssessmentID();
            }

            for (CourseEntity course : allCourses){
                if (course.getCourseName().equals(associatedCourse)) {
                    courseID = course.getCourseID();
                }
            }

            Calendar comparisonStartCalendar = Calendar.getInstance();

            if(calendar.getTimeInMillis() < comparisonStartCalendar.getTimeInMillis()){
                problemWithEntry = true;
                Toast calendarToast = Toast.makeText(requireView().getContext(), "Assessment must be Scheduled for a future date.", Toast.LENGTH_LONG);
                calendarToast.show();
            }

            if(!problemWithEntry) {
                if (existingAssessment){
                    AssessmentEntity assessmentEntity = new AssessmentEntity(assessmentID,
                            assessmentCode1,
                            assessmentName1,
                            assessmentType,
                            assessmentVendor1,
                            calendar.getTimeInMillis(),
                            courseID);
                    schoolRepository.update(assessmentEntity);
                    theAssessment=assessmentEntity;
                    if (remindMeAssessmentSwitch.isChecked()){
                        startAlarm(calendar, assessmentName1);
                    }
                    requireActivity().finish();
                }
                if (!existingAssessment){
                    AssessmentEntity assessmentEntity = new AssessmentEntity(assessmentID,
                            assessmentCode1,
                            assessmentName1,
                            assessmentType,
                            assessmentVendor1,
                            calendar.getTimeInMillis(),
                            courseID);
                    schoolRepository.insert(assessmentEntity);
                    theAssessment=assessmentEntity;
                    if (remindMeAssessmentSwitch.isChecked()){
                        startAlarm(calendar, assessmentName1);
                    }
                    requireActivity().finish();
                }
                //mAssessmentViewModel.insert(assessmentEntity);
            }
            else{
                Toast entryToast = Toast.makeText(requireView().getContext(), "Problem with entered information.", Toast.LENGTH_LONG);
                entryToast.show();
            }
        });

       // final CoursesRecAdapter adapter = new CoursesRecAdapter(mContext);

        // mCourseViewModel = new ViewModelProvider(this).get(CourseViewModel .class);

        /*mCourseViewModel.getAllCourses().observe((LifecycleOwner) mContext, new Observer<List<CourseEntity>>() {
            @Override
            public void onChanged(@Nullable final List<CourseEntity> words) {
                List<CourseEntity> filteredWords = new ArrayList<>();
                for(CourseEntity p:words)if(p.getCourseID()==getActivity().getIntent().getIntExtra("courseID",0))filteredWords.add(p);
                adapter.setCourseWords(filteredWords);
                numCourses=filteredWords.size();
            }
        });*/

        /*if(mContext != null) {
            try {
                Spinner spinner = (Spinner) view.findViewById(R.id.spinner1);
                ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(mContext,
                        R.array.Courses, android.R.layout.simple_spinner_item);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(arrayAdapter);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Spinner failed");
            }
        }

        if(mContext != null) {
            try {
                Spinner spinner = (Spinner) view.findViewById(R.id.spinner1);


                ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(mContext,
                        R.array.Courses, android.R.layout.simple_spinner_item);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(arrayAdapter);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Spinner failed");
            }
        }*/
        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if (selectedFlag) {
            Spinner assessmentTypeSpinner = requireView().findViewById(R.id.assessmentTypeSpinner);
            Spinner associatedCourseSpinner = requireView().findViewById(R.id.associatedCourseSpinner);
            Spinner assessmentHoursSpinner = requireView().findViewById(R.id.assessmentHoursSpinner);
            Spinner assessmentMinutesSpinner = requireView().findViewById(R.id.assessmentMinutesSpinner);
            Spinner assessmentAMPMSpinner = requireView().findViewById(R.id.assessmentAMPMSpinner);

            List<String> assessmentTypes = new ArrayList<>();
            assessmentTypes.add("Objective");
            assessmentTypes.add("Performance");

            List<String> assessmentHours = new ArrayList<>();
            assessmentHours.add("01");
            assessmentHours.add("02");
            assessmentHours.add("03");
            assessmentHours.add("04");
            assessmentHours.add("05");
            assessmentHours.add("06");
            assessmentHours.add("07");
            assessmentHours.add("08");
            assessmentHours.add("09");
            assessmentHours.add("10");
            assessmentHours.add("11");
            assessmentHours.add("12");

            List<String> assessmentMinutes = new ArrayList<>();
            assessmentMinutes.add("00");
            assessmentMinutes.add("15");
            assessmentMinutes.add("30");
            assessmentMinutes.add("45");

            List<String> assessmentAMPM = new ArrayList<>();
            assessmentAMPM.add("AM");
            assessmentAMPM.add("PM");

            //mCourseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
            final List<String> associatedCourses = new ArrayList<>();
            for (CourseEntity course : allCourses) {
                associatedCourses.add(course.getCourseName());
            }

            ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, assessmentTypes);
            ArrayAdapter<String> associationAdapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, associatedCourses);
            ArrayAdapter<String> hoursAdapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, assessmentHours);
            ArrayAdapter<String> minutesAdapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, assessmentMinutes);
            ArrayAdapter<String> AMPMAdapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, assessmentAMPM);

            int associationPosition = 0;
            if (this.getArguments() != null) {
                associationPosition = associationAdapter.getPosition(this.getArguments().getString("associatedCourseName"));
            }
            int typePosition = 0;
            int hourPosition = 0;
            int minutePosition = 0;
            try {
                if (theAssessment.getAssessmentID() != -1) {
                    typePosition = typeAdapter.getPosition(theAssessment.getAssessmentType());
                    hourPosition = hoursAdapter.getPosition(theAssessment.getAssessmentStartHhMm().substring(0,2));
                    minutePosition = minutesAdapter.getPosition(theAssessment.getAssessmentStartHhMm().substring(3,5));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            associationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            hoursAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            minutesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            AMPMAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            assessmentTypeSpinner.setAdapter(typeAdapter);
            associatedCourseSpinner.setAdapter(associationAdapter);
            assessmentHoursSpinner.setAdapter(hoursAdapter);
            assessmentMinutesSpinner.setAdapter(minutesAdapter);
            assessmentAMPMSpinner.setAdapter(AMPMAdapter);

            assessmentTypeSpinner.setSelection(typePosition);
            associatedCourseSpinner.setSelection(associationPosition);
            assessmentHoursSpinner.setSelection(hourPosition);
            assessmentMinutesSpinner.setSelection(minutePosition);
            try {
                assessmentAMPMSpinner.setSelection(theAssessment.getAssessmentCalendarObj().get(Calendar.AM_PM));
            } catch (Exception e) {
                e.printStackTrace();
            }

            associatedCourseSpinner.setOnItemSelectedListener(this);
            assessmentTypeSpinner.setOnItemSelectedListener(this);
            assessmentHoursSpinner.setOnItemSelectedListener(this);
            assessmentMinutesSpinner.setOnItemSelectedListener(this);
            assessmentAMPMSpinner.setOnItemSelectedListener(this);
        }
        selectedFlag = false;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    private void startAlarm(Calendar calendar, String whichAssessment) {
        AlarmManager alarmManager = (AlarmManager) requireActivity().getApplication().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(requireActivity().getApplication(), TheReceiver.class);
        intent.putExtra("alarmType", "assessmentStart");
        intent.putExtra("whichAssessment", whichAssessment);
        intent.setAction(Long.toString(System.currentTimeMillis()));
        @SuppressLint("UnspecifiedImmutableFlag") PendingIntent pendingIntent = PendingIntent.getBroadcast(requireActivity().getApplication(), 1, intent, 0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }
}