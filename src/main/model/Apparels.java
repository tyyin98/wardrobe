package model;

import java.util.ArrayList;

public class Apparels {
    private ArrayList<Apparel> apparels;

    // EFFECTS: create a new Apparels instance
    public Apparels() {
        apparels = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: create a new apparel item and add it to the list
    public void addAnItem(Apparel item) {
        apparels.add(item);
    }

    // REQUIRES: index < apparels.size()
    // MODIFIES: this
    // EFFECTS: remove an item by index
    public void removeAnItem(int index) {
        apparels.remove(index);
    }


    // EFFECTS: calculate the total value of items on the list
    public int calcTotalValue() {
        int sum = 0;
        for (Apparel item: apparels) {
            sum = sum + item.getPricePaid();
        }
        return sum;
    }

    // EFFECTS: calculate the total value of items on the list bought in the given year
    public int calcTotalValue(int startYear, int endYear) {
        int sum = 0;
        for (Apparel item: apparels) {
            int purchaseYear = item.getPurchaseDate().getYear();
            if (purchaseYear >= startYear & purchaseYear <= endYear) {
                sum = sum + item.getPricePaid();
            }
        }
        return sum;
    }

    // EFFECTS: calculate income made by selling apparels
    public int calcRevenue() {
        int revenue = 0;
        for (Apparel item: apparels) {
            if (item.getIsSold()) {
                revenue = revenue + item.getPriceSold() - item.getPricePaid();
            }
        }
        return revenue;
    }

    public ArrayList<Apparel> getApparels() {
        return this.apparels;
    }
}
