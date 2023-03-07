package com.CC4mpbe11.university.Entities;

import junit.framework.TestCase;

import org.junit.Test;

import java.util.Calendar;

public class CourseEntityTest extends TestCase {

    public void testTestToString() {
    }

    public void testGetCourseID() {
    }

    public void testSetCourseID() {
    }

    public void testGetCourseCode() {
    }

    public void testGetCourseName() {
    }

    public void testSetCourseName() {
    }

    public void testGetCourseDescription() {
    }

    public void testSetCourseDescription() {
    }

    public void testGetCourseInstructor() {
    }

    public void testGetCourseInstructorPhone() {
    }

    public void testGetCourseInstructorEmail() {
    }

    public void testGetCourseStatus() {
    }

    public void testSetCourseStatus() {
    }

    public void testGetCourseStart() {
    }

    public void testSetCourseStart() {
    }

    public void testGetCourseNotes() {
    }

    public void testGetCourseEnd() {
    }

    public void testSetCourseEnd() {
    }

    @Test
    public void testGetCourseStartMmDdYy() {
        CourseEntity input = new CourseEntity(1,
                "test",
                "test",
                "test",
                "test",
                "1234567890",
                "test@test.com",
                "Completed",
                1646028426L,
                1646028426L,
                "test",
                1);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(input.getCourseStart());
        String yy = zeros(calendar.get(Calendar.YEAR) % 100);
        String mm = zeros(calendar.get(Calendar.MONTH) + 1);
        String dd = zeros(calendar.get(Calendar.DAY_OF_MONTH));
        String output = mm + "/" + dd + "/" + yy;
        String expected = "01/19/70";
        assertEquals(expected, output);
    }

    private String zeros(int num) {
        String result = String.valueOf(num);
        if(num < 10){
            result = "0" + result;
        }
        return result;
    }

    public void testGetCourseEndMmDdYy() {
    }

    public void testGetTermID() {
    }

    public void testSetTermID() {
    }
}