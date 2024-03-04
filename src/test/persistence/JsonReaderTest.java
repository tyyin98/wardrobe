package persistence;

import model.Apparel;
import model.Date;
import model.Wardrobe;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Wardrobe wd = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWardrobe() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyWardrobe.json");
        try {
            Wardrobe wd = reader.read();
            assertEquals(0, wd.getNumItems());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWardrobe() {
        try {
            JsonReader reader = new JsonReader("./data/testWriterGeneralWardrobe.json");
            Wardrobe wd = reader.read();
            List<Apparel> apparels = wd.getApparels();
            assertEquals(2, apparels.size());
            Date purchaseDate = new Date(1, 2, 2024);
            checkItem("FOG", "Brown Raw Edge Denim", "Pants", "30", 300,
                    "not my type", purchaseDate, true, apparels.get(1) );
            checkSoldItem(true, 3, 4, 2023, 302, apparels.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }







}
