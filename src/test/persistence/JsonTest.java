package persistence;

import model.Apparel;
import model.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {

    protected void checkItem(String brandName, String itemName, String category, String size,
                             int pricePaid, String description, Date purchaseDate, boolean isSold,
                             Apparel item) {

        assertEquals(brandName, item.getBrandName());
        assertEquals(itemName, item.getItemName());
        assertEquals(category, item.getCategory());
        assertEquals(size, item.getSize());
        assertEquals(pricePaid, item.getPricePaid());
        assertEquals(description, item.getDescription());
        assertEquals(purchaseDate.getMonth(), item.getPurchaseDate().getMonth());
        assertEquals(purchaseDate.getDay(), item.getPurchaseDate().getDay());
        assertEquals(purchaseDate.getYear(), item.getPurchaseDate().getYear());

        assertEquals(isSold, item.getIsSold());
    }
    protected void checkSoldItem(boolean isSold, int soldMonth, int soldDay, int soldYear, int soldPrice, Apparel item) {
        assertEquals(isSold ,item.getIsSold());
        assertEquals(soldMonth,item.getSoldDate().getMonth());
        assertEquals(soldDay,item.getSoldDate().getDay());
        assertEquals(soldYear,item.getSoldDate().getYear());
        assertEquals(soldPrice,item.getPriceSold());
    }

}
