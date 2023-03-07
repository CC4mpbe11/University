package com.CC4mpbe11.university.Entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Calendar;

// Example of Encapsulation in this class (and others)
// Class initializes private variables and hides functionality, making that which is happening
// accessible through public methods.

@Entity(tableName="assessment_table")
public class AssessmentEntity {
    @PrimaryKey
    private int assessmentID;
    private final String assessmentCode;
    private final String assessmentName;
    private final String assessmentType;
    private final String assessmentVendor;
    private final Long assessmentCalendar;
    private int courseID;

    @NonNull
    @Override
    public String toString(){
        return "AssessmentEntity{" +
                "assessmentID=" + assessmentID +
                ", assessmentCode=" + assessmentCode +
                ", assessmentName=" + assessmentName +
                ", assessmentType=" + assessmentType +
                ", assessmentVendor=" + assessmentVendor +
                ", assessmentCalendar=" + assessmentCalendar +
                "}";
    }

    public AssessmentEntity(int assessmentID, String assessmentCode, String assessmentName, String assessmentType, String assessmentVendor, Long assessmentCalendar, int courseID) {
        this.assessmentID = assessmentID;
        this.assessmentCode = assessmentCode;
        this.assessmentName = assessmentName;
        this.assessmentType = assessmentType;
        this.assessmentVendor = assessmentVendor;
        this.assessmentCalendar = assessmentCalendar;
        this.courseID = courseID;
    }

    public int getAssessmentID() {
        return assessmentID;
    }

    public void setAssessmentID(int assessmentID) {
        this.assessmentID = assessmentID;
    }

    public String getAssessmentCode() {
        return assessmentCode;
    }

    public String getAssessmentName() {
        return assessmentName;
    }

    public String getAssessmentType() {
        return assessmentType;
    }

    public String getAssessmentVendor() {
        return assessmentVendor;
    }

    public Long getAssessmentCalendar() {
        return assessmentCalendar;
    }

    public Calendar getAssessmentCalendarObj() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(assessmentCalendar);
        return calendar;
    }

    public String getAssessmentDateMmDdYy() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(assessmentCalendar);
        String yy = addLeadingZero(calendar.get(Calendar.YEAR) % 100);
        String mm = addLeadingZero(calendar.get(Calendar.MONTH) + 1);
        String dd = addLeadingZero(calendar.get(Calendar.DAY_OF_MONTH));
        return mm + "/" + dd + "/" + yy;
    }

    public String getAssessmentStartHhMm() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(assessmentCalendar);
        String hH = addLeadingZero(calendar.get(Calendar.HOUR));
        String mM = addLeadingZero(calendar.get(Calendar.MINUTE));
        return hH + ":" + mM;
    }

    /*public String getAssessmentStartyyyymmddggghhmmaaa() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(assessmentCalendar);
        String yyyy = addLeadingZero(calendar.get(Calendar.YEAR));
        String mm = addLeadingZero(calendar.get(Calendar.MONTH) + 1);
        String dd = addLeadingZero(calendar.get(Calendar.DAY_OF_MONTH));
        String hH = addLeadingZero(calendar.get(Calendar.HOUR));
        String mM = addLeadingZero(calendar.get(Calendar.MINUTE));
        String aMpM = "";
        int amPm = calendar.get(Calendar.AM_PM);
        if (amPm == 1){
            aMpM = "PM";
        }
        else{
            aMpM = "AM";
        }
        String yyyymmddggghhmmaaa = yyyy + "." + mm + "." + dd + " AD " + hH + ":" + mM + " " + aMpM;
        System.out.println(yyyymmddggghhmmaaa);
        return yyyymmddggghhmmaaa;
    }*/

    private String addLeadingZero(int num) {
        String result = String.valueOf(num);
        if(num < 10){
            result = "0" + result;
        }
        return result;
    }

    public int getCourseID() { return courseID; }

    public void setCourseID(int courseID) { this.courseID = courseID; }
}
