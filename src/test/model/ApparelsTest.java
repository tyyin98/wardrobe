package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApparelsTest {

    private Apparel testApparelA;
    private Apparel testApparelB;
    private Apparel testApparelC;
    private Apparel testApparelD;
    private Apparel testApparelE;
    private Apparels testApparels;
    private Apparels testEmptyApparels;

    @BeforeEach
    public void setup() {
        testApparelA = new Apparel("Dries", "Bomber Jacket", "Outwear", "S", 1000);
        testApparelB = new Apparel("Maison", "Flared Jeans", "Jeans", "29", 300);
        testApparelC = new Apparel("Rick", "Ramone Sneakers", "Sneakers", "41.5", 400);
        testApparelD = new Apparel("Yeezy", "Bomber Jacket", "Outwear", "S", 350);
        testApparelE = new Apparel("Vujade", "Flared Cargo", "Pants", "S", 450);

        testApparels = new Apparels();
        testApparels.addAnItem(testApparelA);
        testApparels.addAnItem(testApparelB);
        testApparels.addAnItem(testApparelC);
        testApparels.addAnItem(testApparelD);
        testApparels.addAnItem(testApparelE);

        testEmptyApparels = new Apparels();
    }

    @Test
    public void testConstructor() {
        assertEquals(0, testEmptyApparels.getApparels().size());
    }

    @Test
    public void testRemoveAnItem() {
        assertEquals(5, testApparels.getApparels().size());
        testApparels.removeAnItem(2);
        assertEquals(4, testApparels.getApparels().size());
    }

    @Test
    public void testCalcTotalValue() {
        assertEquals(0, testEmptyApparels.calcTotalValue());
        assertEquals(2500, testApparels.calcTotalValue());
    }

    @Test
    public void testCalcTotalValueInRange(){
        Date date2000 = new Date(1,1, 2000);
        Date date201x = new Date(1,1, 2010);
        Date date201y = new Date(1,1, 2011);
        Date date201z = new Date(1,1, 2012);
        Date date2024 = new Date(1,1, 2024);

        testApparelA.setPurchaseDate(date2000);
        testApparelB.setPurchaseDate(date201x);
        testApparelC.setPurchaseDate(date201y);
        testApparelD.setPurchaseDate(date201z);
        testApparelE.setPurchaseDate(date2024);

        assertEquals(1050, testApparels.calcTotalValue(2010, 2012));
    }

    @Test
    public void testCalcRevenue() {
        Date date201y = new Date(1,1, 2011);
        Date date201z = new Date(1,1, 2012);
        Date date2024 = new Date(1,1, 2024);
        testApparelB.sellItem(400, date201y);
        testApparelC.sellItem(500, date201z);
        assertEquals(200, testApparels.calcRevenue());
        testApparelA.sellItem(200, date2024);
        assertEquals(-600, testApparels.calcRevenue());
    }



}
