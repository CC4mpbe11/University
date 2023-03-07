package com.CC4mpbe11.university.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Calendar;

@Entity(tableName="term_table")

public class TermEntity {

    @PrimaryKey
    private int termID;
    private final int termNumber;
    private Long termStart;
    private Long termEnd;

    public TermEntity(int termID, int termNumber, Long termStart, Long termEnd){
        this.termID = termID;
        this.termNumber = termNumber;
        this.termStart = termStart;
        this.termEnd = termEnd;
    }

    public int getTermID() {
        return termID;
    }

    public void setTermID(int termID) {
        this.termID = termID;
    }

    public int getTermNumber() {
        return termNumber;
    }

    public Long getTermStart() {
        return termStart;
    }

    public void setTermStart(Long termStart) {
        this.termStart = termStart;
    }

    public Long getTermEnd() {
        return termEnd;
    }

    public void setTermEnd(Long termEnd) {
        this.termEnd = termEnd;
    }

    public String getTermStartMmDdYy() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(termStart);
        String yy = addLeadingZero(calendar.get(Calendar.YEAR) % 100);
        String mm = addLeadingZero(calendar.get(Calendar.MONTH) + 1);
        String dd = addLeadingZero(calendar.get(Calendar.DAY_OF_MONTH));
        return mm + "/" + dd + "/" + yy;
    }

    public String getTermEndMmDdYy() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(termEnd);
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

}
