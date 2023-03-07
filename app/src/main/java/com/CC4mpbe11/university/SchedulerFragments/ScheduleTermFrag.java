package com.CC4mpbe11.university.SchedulerFragments;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.CC4mpbe11.university.Database.SchoolRepository;
import com.CC4mpbe11.university.Entities.TermEntity;
import com.CC4mpbe11.university.R;

import java.util.Calendar;
import java.util.List;

public class ScheduleTermFrag extends Fragment {

    private final Calendar termStartCalendar = Calendar.getInstance();
    private final Calendar termEndCalendar = Calendar.getInstance();
    private SchoolRepository schoolRepository;

    private void termStartCalendarChanger(int year, int month, int day){
        termStartCalendar.set(Calendar.YEAR, year);
        termStartCalendar.set(Calendar.MONTH, month - 1);
        termStartCalendar.set(Calendar.DAY_OF_MONTH, day);
    }
    private void termEndCalendarChanger(int year, int month, int day){
        termEndCalendar.set(Calendar.YEAR, year);
        termEndCalendar.set(Calendar.MONTH, month - 1);
        termEndCalendar.set(Calendar.DAY_OF_MONTH, day);
    }


    public ScheduleTermFrag() {
        // Required empty public constructor
    }

  /*  public static ScheduleTermFrag newInstance() {
        ScheduleTermFrag fragment = new ScheduleTermFrag();
        return fragment;
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule_term, container, false);

        schoolRepository = new SchoolRepository(requireActivity().getApplication());
        List<TermEntity> allTerms = schoolRepository.getAllTerms();

        try {
            if (this.getArguments() != null && this.getArguments().getString("type").equals("existingTerm")) {
                for (TermEntity term : allTerms) {
                    if (term.getTermID() == this.getArguments().getInt("termID")) {
                        termStartCalendar.setTimeInMillis(term.getTermStart());
                        termEndCalendar.setTimeInMillis(term.getTermEnd());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        CalendarView startTermCalendarView = view.findViewById(R.id.startTermCalendarView);
        CalendarView endTermCalendarView = view.findViewById(R.id.endTermCalendarView);
        Button scheduleTermButton = view.findViewById(R.id.scheduleTermButton);
        EditText termNameEditText = view.findViewById(R.id.termName);

        ScrollView termScrollView = view.findViewById(R.id.termScrollView);
        termScrollView.setOnTouchListener((v, event) -> {
           if (termNameEditText.hasFocus()) {
               termNameEditText.clearFocus();
           }
           return false;
       });

        CalendarView.OnDateChangeListener startTermCalendarListener = (view1, year, month, day) -> {
            month = month + 1;
            termStartCalendarChanger(year,month,day);
            String newDate = year+"-"+month+"-"+day;
            Log.d("NEW_DATE", newDate);
        };
        CalendarView.OnDateChangeListener endTermCalendarListener = (view12, year, month, day) -> {
            month = month + 1;
            termEndCalendarChanger(year,month,day);
            String newDate = year+"-"+month+"-"+day;
            Log.d("NEW_DATE", newDate);
        };

        startTermCalendarView.setOnDateChangeListener(startTermCalendarListener);
        endTermCalendarView.setOnDateChangeListener(endTermCalendarListener);

        scheduleTermButton.setOnClickListener(view13 -> {
            int termID = schoolRepository.getNewTermID();
            int termNumber = 1;
            boolean problemWithEntry = false;

            Calendar comparisonStartCalendar = Calendar.getInstance();

            if(termStartCalendar.getTimeInMillis() > termEndCalendar.getTimeInMillis()) {
                problemWithEntry = true;
                Toast calendarToast = Toast.makeText(requireView().getContext(), "The term must start before it can end.", Toast.LENGTH_LONG);
                calendarToast.show();
            }
            if(termStartCalendar.getTimeInMillis() < comparisonStartCalendar.getTimeInMillis()){
                problemWithEntry = true;
                Toast calendarToast = Toast.makeText(requireView().getContext(), "Term must be Scheduled for future dates.", Toast.LENGTH_LONG);
                calendarToast.show();
            }

            boolean isNumber = true;
            try {
                for (char c : termNameEditText.getText().toString().toCharArray()) {
                    if (!Character.isDigit(c)) {
                        isNumber = false;
                    }
                }
                //Integer.parseInt(termNameEditText.getText().toString());
                if (termNameEditText.getText().toString().equals("")) {
                    termNameEditText.setText("");
                    termNameEditText.setHint("Enter term number");
                    termNameEditText.setHintTextColor(Color.RED);
                    termNameEditText.requestFocus();
                    problemWithEntry = true;
                }
                else if(!isNumber) {
                   // termNameEditText.getText().toString().equals("");
                    termNameEditText.setText("");
                    termNameEditText.setHint("Enter positive number");
                    termNameEditText.setHintTextColor(Color.RED);
                    termNameEditText.requestFocus();
                    problemWithEntry = true;
                }
                else if(Integer.parseInt(termNameEditText.getText().toString()) <= 0) {
                    //termNameEditText.getText().toString().equals("");
                    termNameEditText.setText("");
                    termNameEditText.setHint("Enter positive number");
                    termNameEditText.setHintTextColor(Color.RED);
                    termNameEditText.requestFocus();
                    problemWithEntry = true;
                } else {
                    termNumber = Integer.parseInt(termNameEditText.getText().toString());
                }

            } catch (Exception e) {
                e.printStackTrace();
                termNameEditText.setText(R.string.must_be_number);
            }
            if (termID == -1){
                problemWithEntry = true;
            }
            if (!problemWithEntry) {
                TermEntity termEntity = new TermEntity(termID,
                        termNumber,
                        termStartCalendar.getTimeInMillis(),
                        termEndCalendar.getTimeInMillis()
                        );
                schoolRepository.insert(termEntity);
                requireActivity().finish();
            }else {
                Toast entryToast = Toast.makeText(requireView().getContext(), "Problem with entered information.", Toast.LENGTH_LONG);
                entryToast.show();
            }
        });
        return view;
    }
}