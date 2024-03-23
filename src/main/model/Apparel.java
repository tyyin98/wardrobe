package model;


import org.json.JSONObject;
import persistence.Writable;

// Represents an apparel item that has name, a brand, a category,
// a size, a price, a description added by the user, a purchase date,
// a sold date, a sold price.
public class Apparel implements Writable {

    public static final Date DEFAULT_DATE = new Date(1, 2, 2024);

    private String itemName;
    private String brandName;
    private String category;
    private String size;
    private int pricePaid;
    private String description;
    private Date purchaseDate;
    private boolean isSold;
    private int priceSold;
    private Date soldDate;
    private String imgSrc;

    // REQUIRES: brandName, itemName, category, size not empty. pricePaid not negative
    // EFFECTS: create a new apparel item
    public Apparel(String brandName, String itemName, String category, String size, int pricePaid, String description) {
        this.brandName = brandName;
        this.itemName = itemName;
        this.category = category;
        this.size = size;
        this.pricePaid = pricePaid;
        this.description = description;
        this.purchaseDate = DEFAULT_DATE;
        this.isSold = false;
        this.imgSrc = "";
    }

    // REQUIRES: isSold == false
    // MODIFIES: this
    // EFFECTS: Mark this item as sold, update its priceSold and soldDate
    public void sellItem(int priceSold, Date soldDate) {
        this.isSold = true;
        setPriceSold(priceSold);
        setSoldDate(soldDate);
    }

    // EFFECTS: represents how an apparel will be printed as a string
    @Override
    public String toString() {
        return this.getBrandName() + " | " + this.getItemName() + " | $" + this.getPricePaid();
    }

    // EFFECTS: make an apparel copy of JSONObject and return it
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("itemName", itemName);
        json.put("brandName", brandName);
        json.put("category", category);
        json.put("size", size);
        json.put("pricePaid", pricePaid);
        json.put("description", description);
        json.put("purchaseDate", purchaseDate.toJson());
        json.put("isSold", isSold);
        json.put("imgSrc", imgSrc);

        if (isSold) {
            json.put("isSold", true);
            json.put("priceSold", priceSold);
            json.put("soldDate", soldDate.toJson());
        }
        return json;
    }

    public String getItemName() {
        return this.itemName;
    }

    public String getBrandName() {
        return this.brandName;
    }

    public String getCategory() {
        return this.category;
    }

    public String getSize() {
        return this.size;
    }

    public int getPricePaid() {
        return this.pricePaid;
    }

    public String getDescription() {
        return this.description;
    }

    public Date getPurchaseDate() {
        return this.purchaseDate;
    }

    public boolean getIsSold() {
        return this.isSold;
    }

    public Date getSoldDate() {
        return this.soldDate;
    }

    public int getPriceSold() {
        return this.priceSold;
    }

    public String getImgSrc() {
        return this.imgSrc;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setPricePaid(int pricePaid) {
        this.pricePaid = pricePaid;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public void setIsSold(boolean isSold) {
        this.isSold = isSold;
    }

    public void setSoldDate(Date soldDate) {
        this.soldDate = soldDate;
    }

    public void setPriceSold(int priceSold) {
        this.priceSold = priceSold;
    }

    public void setImgSrc(String src) {
        this.imgSrc = src;
    }




}
