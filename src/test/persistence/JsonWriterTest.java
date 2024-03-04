package persistence;

import model.Apparel;
import model.Date;
import model.Wardrobe;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;


public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            Wardrobe wd = new Wardrobe();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWardrobe() {
        try {
            Wardrobe wd = new Wardrobe();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyWardrobe.json");
            writer.open();
            writer.write(wd);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyWardrobe.json");
            wd = reader.read();
            assertEquals(0, wd.getNumItems());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWardrobe() {
        try {
            Wardrobe wd = new Wardrobe();
            Apparel apparelA = new Apparel("Dries", "Bomber Jacket", "Tops", "S", 1000, "Best bomber");
            Apparel apparelB = new Apparel("FOG", "Brown Raw Edge Denim", "Pants", "30", 300, "not my type");
            apparelB.sellItem(302, new Date(3, 4, 2023));
            wd.addAnItem(apparelA);
            wd.addAnItem(apparelB);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralWardrobe.json");
            writer.open();
            writer.write(wd);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralWardrobe.json");
            wd = reader.read();
            List<Apparel> apparels = wd.getApparels();
            assertEquals(2, apparels.size());
            Date purchaseDate = new Date(1, 2, 2024);
            checkItem("Dries", "Bomber Jacket", "Tops", "S", 1000,
                    "Best bomber", purchaseDate, false, apparels.get(0) );
            checkSoldItem(true, 3, 4, 2023, 302, apparels.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

//    public void initWardrobe(){
//        Apparel apparelA = new Apparel("Dries", "Bomber Jacket", "Tops", "S", 1000, "Best bomber");
//        Apparel apparelB = new Apparel("Maison", "Flared Jeans", "Pants", "29", 300, "My go-to");
//        Apparel apparelC = new Apparel("Rick", "Ramone Sneakers", "Shoes", "41.5", 400, "Another go-to");
//        Apparel apparelD = new Apparel("Yeezy", "Bomber Jacket", "Tops", "S", 350, "GOAT");
//        Apparel apparelE = new Apparel("Vujade", "Flared Cargo", "Pants", "S", 450, "Grail!");
//        Apparel apparelF = new Apparel("FOG", "Wool Overcoat", "Tops", "46", 750, "Cool");
//        Apparel apparelG = new Apparel("FOG", "Brown Raw Edge Denim", "Pants", "30", 300, "not my type");
//        apparelG.sellItem(302, new Date(3, 4, 2023));
//        apparelB.setPurchaseDate(new Date(12, 30, 2025));
//
//        wardrobe = new Wardrobe();
//        wardrobe.addAnItem(apparelA);
//        wardrobe.addAnItem(apparelB);
//        wardrobe.addAnItem(apparelC);
//        wardrobe.addAnItem(apparelD);
//        wardrobe.addAnItem(apparelE);
//        wardrobe.addAnItem(apparelF);
//        wardrobe.addAnItem(apparelG);
//    }




}
