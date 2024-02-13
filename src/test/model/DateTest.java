package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DateTest {

    private Date date1;
    private Date date2;
    private Date date3;
    private Date date4;
    private Date date5;
    private Date date6;
    private Date date7;
    private Date date8;
    private Date date9;
    private Date date10;
    private Date date11;
    private Date date12;



    @BeforeEach
    public void setup() {
        date1 = new Date (1, 30, 2000);
        date2 = new Date (2, 20, 2010);
        date3 = new Date (3, 11, 2024);
        date4 = new Date (4, 11, 2024);
        date5 = new Date (5, 11, 2024);
        date6 = new Date (6, 11, 2024);
        date7 = new Date (7, 11, 2024);
        date8 = new Date (8, 11, 2024);
        date9 = new Date (9, 11, 2024);
        date10 = new Date (10, 11, 2024);
        date11 = new Date (11, 11, 2024);
        date12 = new Date (12, 11, 2024);
    }

    @Test
    public void testGetDate() {
        assertEquals("1-30-2000", date1.getDateShort());
        assertEquals("2-20-2010", date2.getDateShort());

        assertEquals("January 30, 2000", date1.getDateLong());
        assertEquals("February 20, 2010", date2.getDateLong());
        assertEquals("March 11, 2024", date3.getDateLong());
        assertEquals("April 11, 2024", date4.getDateLong());
        assertEquals("May 11, 2024", date5.getDateLong());
        assertEquals("June 11, 2024", date6.getDateLong());
        assertEquals("July 11, 2024", date7.getDateLong());
        assertEquals("August 11, 2024", date8.getDateLong());
        assertEquals("September 11, 2024", date9.getDateLong());
        assertEquals("October 11, 2024", date10.getDateLong());
        assertEquals("November 11, 2024", date11.getDateLong());
        assertEquals("December 11, 2024", date12.getDateLong());
    }



}
