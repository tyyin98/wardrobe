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

    @Test
    public void testSetItemName() {
        testApparelA.setItemName("Wool Bomber Jacket");
        assertEquals("Wool Bomber Jacket", testApparelA.getItemName());
    }

    @Test
    public void testSetItemBrand() {
        testApparelA.setBrandName("Dries Van Noten");
        assertEquals("Dries Van Noten", testApparelA.getBrandName());
    }

    @Test
    public void testSetCategory() {
        testApparelA.setCategory("Tops");
        assertEquals("Tops", testApparelA.getCategory());
    }

    @Test
    public void testSetSize() {
        testApparelA.setSize("M");
        assertEquals("M", testApparelA.getSize());
    }

    @Test
    public void testSetPrice(){
        testApparelA.setPricePaid(9999);
        assertEquals(9999, testApparelA.getPricePaid());
    }

    @Test
    public void testSetDescription() {
        testApparelA.setDescription("It is cool");
        assertEquals("It is cool", testApparelA.getDescription());
    }

    @Test
    public void testSetBoughFrom() {
        testApparelA.setBoughtFrom("SSENSE");
        assertEquals("SSENSE", testApparelA.getBoughtFrom());
    }

    @Test
    public void testSetIsSold() {
        testApparelA.setIsSold(true);
        assertTrue(testApparelA.getIsSold());
    }



}
