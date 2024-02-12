package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ApparelTest {

    private Apparel testApparelA;


    @BeforeEach
    public void setup() {
        testApparelA = new Apparel("Dries", "Bomber Jacket", "Outwear", "S", 1000);

    }

    @Test
    public void testConstructor() {
        assertEquals("Dries", testApparelA.getBrandName());
        assertEquals("Bomber Jacket", testApparelA.getItemName());
        assertEquals("Outwear", testApparelA.getCategory());
        assertEquals("S", testApparelA.getSize());
        assertEquals(1000, testApparelA.getPricePaid());
        assertEquals("", testApparelA.getBoughtFrom());
        assertEquals("", testApparelA.getDescription());
        assertEquals(2024, testApparelA.getPurchaseDate().getYear());
        assertEquals(1, testApparelA.getPurchaseDate().getMonth());
        assertEquals(2, testApparelA.getPurchaseDate().getDay());
        assertFalse(testApparelA.getIsSold());
    }

    @Test
    public void testSellItem(){
        Date soldDate = new Date(2, 11, 2024);
        testApparelA.sellItem(1200, soldDate);
        assertTrue(testApparelA.getIsSold());
        assertEquals(1200, testApparelA.getPriceSold());
        assertEquals(soldDate, testApparelA.getSoldDate());
    }

}
