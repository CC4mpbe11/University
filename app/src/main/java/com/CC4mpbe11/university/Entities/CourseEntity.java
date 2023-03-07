package com.CC4mpbe11.university.Entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Calendar;

@Entity(tableName="course_table")
public class CourseEntity {
    @PrimaryKey
    private int courseID;

    private final String courseCode;
    private String courseName;
    private String courseDescription;
    private final String courseInstructor;
    private final String courseInstructorPhone;
    private final String courseInstructorEmail;
    private String courseStatus;
    private Long courseStart;
    private Long courseEnd;
    private final String courseNotes;
    private int termID;

    @NonNull
    @Override
    public String toString() {
        return "CourseEntity{" +
                "courseID=" + courseID +
                ", courseCode=" + courseCode +
                ", courseName=" + courseName +
                ", courseDescription=" + courseDescription +
                ", courseInstructor=" + courseInstructor +
                ", courseInstructorPhone=" + courseInstructorPhone +
                ", courseInstructorEmail=" + courseInstructorEmail +
                ", courseStatus=" + courseStatus +
                ", courseStart=" + courseStart +
                ", courseEnd=" + courseEnd +
                ", courseNotes=" + courseNotes +
                "termID=" + termID +
                "}";
    }

    public CourseEntity(int courseID, String courseCode, String courseName, String courseDescription, String courseInstructor, String courseInstructorPhone, String courseInstructorEmail, String courseStatus, Long courseStart, Long courseEnd, String courseNotes, int termID) {
        this.courseID = courseID;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.courseDescription = courseDescription;
        this.courseInstructor = courseInstructor;
        this.courseInstructorPhone = courseInstructorPhone;
        this.courseInstructorEmail = courseInstructorEmail;
        this.courseStatus = courseStatus;
        this.courseStart = courseStart;
        this.courseEnd = courseEnd;
        this.courseNotes = courseNotes;
        this.termID = termID;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) { this.courseDescription = courseDescription; }

    public String getCourseInstructor() {
        return courseInstructor;
    }

    public String getCourseInstructorPhone() { return courseInstructorPhone; }

    public String getCourseInstructorEmail() { return courseInstructorEmail; }

    public String getCourseStatus() { return courseStatus; }

    public void setCourseStatus(String courseStatus) { this.courseStatus = courseStatus; }

    public Long getCourseStart() { return courseStart; }

    public void setCourseStart(Long courseStart) { this.courseStart = courseStart; }

    public String getCourseNotes() { return courseNotes; }

    /*public Calendar getCourseStartObj() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(courseStart);
        return calendar;
    }*/

    public Long getCourseEnd() {
        return courseEnd;
    }

    public void setCourseEnd(Long courseEnd) { this.courseEnd = courseEnd; }

    public String getCourseStartMmDdYy() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(courseStart);
        String yy = addLeadingZero(calendar.get(Calendar.YEAR) % 100);
        String mm = addLeadingZero(calendar.get(Calendar.MONTH) + 1);
        String dd = addLeadingZero(calendar.get(Calendar.DAY_OF_MONTH));
        return mm + "/" + dd + "/" + yy;
    }

    public String getCourseEndMmDdYy() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(courseEnd);
        String yy = addLeadingZero(calendar.get(Calendar.YEAR) % 100);
        String mm = addLeadingZero(calendar.get(Calendar.MONTH) + 1);
        String dd = addLeadingZero(calendar.get(Calendar.DAY_OF_MONTH));
        return mm + "/" + dd + "/" + yy;
    }

    private String addLeadingZero(int num) {
        String result = String.valueOf(num);
        if(num < 10){
            result = "0" + result;
        }
        return result;
    }

    public int getTermID() { return termID; }

    public void setTermID(int termID) { this.termID = termID; }
}
