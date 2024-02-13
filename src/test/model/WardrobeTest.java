package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WardrobeTest {

    private Apparel testApparelA;
    private Apparel testApparelB;
    private Apparel testApparelC;
    private Apparel testApparelD;
    private Apparel testApparelE;
    private Wardrobe testWardrobe;
    private Wardrobe testEmptyWardrobe;

    @BeforeEach
    public void setup() {
        testApparelA = new Apparel("Dries", "Bomber Jacket", "Outwear", "S", 1000);
        testApparelB = new Apparel("Maison", "Flared Jeans", "Jeans", "29", 300);
        testApparelC = new Apparel("Rick", "Ramone Sneakers", "Sneakers", "41.5", 400);
        testApparelD = new Apparel("Yeezy", "Bomber Jacket", "Outwear", "S", 350);
        testApparelE = new Apparel("Vujade", "Flared Cargo", "Pants", "S", 450);

        testWardrobe = new Wardrobe();
        testWardrobe.addAnItem(testApparelA);
        testWardrobe.addAnItem(testApparelB);
        testWardrobe.addAnItem(testApparelC);
        testWardrobe.addAnItem(testApparelD);
        testWardrobe.addAnItem(testApparelE);

        testEmptyWardrobe = new Wardrobe();
    }

    @Test
    public void testConstructor() {
        assertEquals(0, testEmptyWardrobe.getApparels().size());
    }

    @Test
    public void testRemoveAnItem() {
        assertEquals(5, testWardrobe.getApparels().size());
        testWardrobe.removeAnItem(2);
        assertEquals(4, testWardrobe.getApparels().size());
    }

    @Test
    public void testGetNumItems() {
        assertEquals(0, testEmptyWardrobe.getNumItems());
        assertEquals(5, testWardrobe.getNumItems());
    }

    @Test
    public void testSelectByCategory() {
        assertEquals(2, testWardrobe.selectByCategory("Outwear").size());
        assertEquals(0, testWardrobe.selectByCategory("Shoes").size());
    }

    @Test
    public void testSelectByBrand() {
        assertEquals(1, testWardrobe.selectByBrand("Dries").size());
        assertEquals(0, testWardrobe.selectByBrand("abc").size());
    }

    @Test
    public void testSelectByPriceRange() {
        assertEquals(0, testWardrobe.selectByPriceRange(1001, 1002).size());
        assertEquals(4, testWardrobe.selectByPriceRange(300, 450).size());
    }


    @Test
    public void testCalcTotalValue() {
        assertEquals(0, testEmptyWardrobe.calcTotalValue());
        assertEquals(2500, testWardrobe.calcTotalValue());
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

        assertEquals(1050, testWardrobe.calcTotalValue(2010, 2012));
    }

    @Test
    public void testCalcRevenue() {
        Date date201y = new Date(1,1, 2011);
        Date date201z = new Date(1,1, 2012);
        Date date2024 = new Date(1,1, 2024);
        assertEquals(0, testWardrobe.getNumItemsSold());
        testApparelB.sellItem(400, date201y);
        testApparelC.sellItem(500, date201z);
        assertEquals(200, testWardrobe.calcRevenue());
        testApparelA.sellItem(200, date2024);
        assertEquals(-600, testWardrobe.calcRevenue());
        assertEquals(3, testWardrobe.getNumItemsSold());
    }

    @Test
    public void testGetNumItemsByYear() {
        assertEquals(0, testWardrobe.getNumItems(2025, 2025));
        assertEquals(5, testWardrobe.getNumItems(2024, 2025));
        Apparel testApparelZ = new Apparel("Vujade", "Flared Cargo", "Pants", "S", 450);
        testApparelZ.setPurchaseDate(new Date(1,1, 2099));
        testWardrobe.addAnItem(testApparelZ);
        assertEquals(5, testWardrobe.getNumItems(2024, 2098));

    }

    @Test
    public void testGetBrandList() {
        assertEquals(5, testWardrobe.getBrandList().size());
        testWardrobe.addAnItem(testApparelA);
        assertEquals(5, testWardrobe.getBrandList().size());

    }





}
