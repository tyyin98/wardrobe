package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DateTest {

    private Date date1;
    private Date date2;
    private Date date3;

    @BeforeEach
    public void setup() {
        date1 = new Date (1, 30, 2000);
        date2 = new Date (2, 20, 2010);
        date3 = new Date (12, 11, 2024);
    }

    @Test
    public void testGetDate() {
        assertEquals("1-30-2000", date1.getDateShort());
        assertEquals("2-20-2010", date2.getDateShort());
        assertEquals("December 11, 2024", date3.getDateLong());
        assertEquals("February 20, 2010", date2.getDateLong());

    }



}
