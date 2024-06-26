package persistence;

import model.Apparel;
import model.Date;
import model.Wardrobe;

import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Reads JSON files and load the wardrobe from it
// Inspired by JsonReader from workroom app:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads wardrobe from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Wardrobe read() throws IOException {
        String jsonData = readFile(source);
//        System.out.println(jsonData);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseWardrobe(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses wardrobe from JSON object and returns it
    private Wardrobe parseWardrobe(JSONObject jsonObject) {
        Wardrobe wd = new Wardrobe();
        addApparels(wd, jsonObject);
        return wd;
    }

    // MODIFIES: wd
    // EFFECTS: parses items from JSON object and adds them to workroom
    private void addApparels(Wardrobe wd, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("apparels");
        for (Object json : jsonArray) {
            JSONObject nextItem = (JSONObject) json;
            addApparel(wd, nextItem);
        }
    }

    // MODIFIES: wd
    // EFFECTS: parses item from JSON object and adds it to wardrobe
    private void addApparel(Wardrobe wd, JSONObject jsonObject) {
        String itemName = jsonObject.getString("itemName");
        String brandName = jsonObject.getString("brandName");
        String category = jsonObject.getString("category");
        String size = jsonObject.getString("size");
        int pricePaid = jsonObject.getInt("pricePaid");
        String description = jsonObject.getString("description");
        boolean isSold = jsonObject.getBoolean("isSold");
        JSONObject purchaseDateJson = jsonObject.getJSONObject("purchaseDate");
        int purchaseMonth = purchaseDateJson.getInt("month");
        int purchaseDay = purchaseDateJson.getInt("day");
        int purchaseYear = purchaseDateJson.getInt("year");
        Apparel item = new Apparel(brandName, itemName, category, size, pricePaid, description);
        item.setPurchaseDate(new Date(purchaseMonth, purchaseDay, purchaseYear));
        item.setImgSrc(jsonObject.getString("imgSrc"));
        if (isSold) {
            JSONObject soldDateJson = jsonObject.getJSONObject("soldDate");
            int soldMonth = soldDateJson.getInt("month");
            int soldDay = soldDateJson.getInt("day");
            int soldYear = soldDateJson.getInt("year");
            int priceSold = jsonObject.getInt("priceSold");
            item.sellItem(priceSold, new Date(soldMonth, soldDay, soldYear));
        }
        wd.addAnItem(item);
    }








}
