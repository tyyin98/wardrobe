package model;

import org.json.JSONObject;
import persistence.Writable;

// Works like a calendar; helps track dat, month, year
public class Date implements Writable {
    private final int month;
    private final int day;
    private final int year;

    //Requires: 1<= month <= 12; 1<=day<=31
    //Effects: create a date
    public Date(int month, int day, int year) {
        this.month = month;
        this.day = day;
        this.year = year;
    }

    // EFFECTS: make a Date copy of JSONObject and return it
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("month", month);
        json.put("day", day);
        json.put("year", year);
        return json;
    }

    // EFFECTS: Return date in a short format
    public String getDateShort() {
        return month + "-" + day + "-" + year;
    }

    // EFFECTS: Return a string of date with month spelled out
    @SuppressWarnings("methodlength")
    public String getDateLong() {
        String monthStr;
        if (month == 1) {
            monthStr = "January";
        } else if (month == 2) {
            monthStr = "February";
        } else if (month == 3) {
            monthStr = "March";
        } else if (month == 4) {
            monthStr = "April";
        } else if (month == 5) {
            monthStr = "May";
        } else if (month == 6) {
            monthStr = "June";
        } else if (month == 7) {
            monthStr = "July";
        } else if (month == 8) {
            monthStr = "August";
        } else if (month == 9) {
            monthStr = "September";
        } else if (month == 10) {
            monthStr = "October";
        } else if (month == 11) {
            monthStr = "November";
        } else {
            monthStr = "December";
        }
        return monthStr + " " + day + ", " + year;
    }

    public int getMonth() {
        return this.month;
    }

    public int getDay() {
        return this.day;
    }

    public int getYear() {
        return this.year;
    }


}
