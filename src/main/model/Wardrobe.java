package model;

import java.util.ArrayList;

public class Wardrobe {
    private final ArrayList<Apparel> apparels;


    // EFFECTS: create a new Apparels instance
    public Wardrobe() {
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

    // EFFECTS: return the size of the wardrobe
    public int getNumItems() {
        return apparels.size();
    }

    // EFFECTS: return the num of items bought in the given time frame
    public int getNumItems(int startYear, int endYear) {
        int num = 0;
        for (Apparel item : apparels) {
            int purchaseYear = item.getPurchaseDate().getYear();
            if (purchaseYear >= startYear & purchaseYear <= endYear) {
                num++;
            }
        }
        return num;
    }

    // EFFECTS: return a list of apparels of the given category
    public ArrayList<Apparel> selectByCategory(String category) {
        ArrayList<Apparel> resultList = new ArrayList<>();
        for (Apparel item : apparels) {
            if (item.getCategory().equals(category)) {
                resultList.add(item);
            }
        }
        return resultList;
    }

    // EFFECTS: return a list of apparels of the given brand
    public ArrayList<Apparel> selectByBrand(String brand) {
        ArrayList<Apparel> resultList = new ArrayList<>();
        for (Apparel item : apparels) {
            if (item.getBrandName().equals(brand)) {
                resultList.add(item);
            }
        }
        return resultList;
    }

    // EFFECTS: return a list of items whose price is within the given range
    public ArrayList<Apparel> selectByPriceRange(int lowerBound, int higherBound) {
        ArrayList<Apparel> resultList = new ArrayList<>();
        for (Apparel item : apparels) {
            int price = item.getPricePaid();
            if (price >= lowerBound & price <= higherBound) {
                resultList.add(item);
            }
        }
        return resultList;
    }


    // EFFECTS: calculate the total value of items on the list
    public int calcTotalValue() {
        int sum = 0;
        for (Apparel item : apparels) {
            sum = sum + item.getPricePaid();
        }
        return sum;
    }

    // EFFECTS: calculate the total value of items on the list bought in the given year
    public int calcTotalValue(int startYear, int endYear) {
        int sum = 0;
        for (Apparel item : apparels) {
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
        for (Apparel item : apparels) {
            if (item.getIsSold()) {
                revenue = revenue + item.getPriceSold() - item.getPricePaid();
            }
        }
        return revenue;
    }

    // EFFECTS: return the num of items sold
    public int getNumItemsSold() {
        int num = 0;
        for (Apparel item : apparels) {
            if (item.getIsSold()) {
                num++;
            }
        }
        return num;
    }

    // EFFECTS: Return the apparel list in the wardrobe
    public ArrayList<Apparel> getApparels() {
        return this.apparels;
    }

    // EFFECTS: return a list of brand names with no duplicates
    public ArrayList<String> getBrandList() {
        ArrayList<String> brandListWithDuplicates = new ArrayList<>();
        ArrayList<String> brandListNoDuplicates = new ArrayList<>();
        for (Apparel item : apparels) {
            brandListWithDuplicates.add(item.getBrandName());
        }
        for (String str: brandListWithDuplicates) {
            int duplicates = 0;
            for (String string: brandListNoDuplicates) {
                if (string.equals(str)) {
                    duplicates++;
                }
            }
            if (duplicates == 0) {
                brandListNoDuplicates.add(str);
            }
        }
        return brandListNoDuplicates;

    }


}
