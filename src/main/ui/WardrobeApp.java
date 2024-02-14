package ui;

import model.Apparel;
import model.Wardrobe;
import model.Date;

import java.util.ArrayList;
import java.util.Scanner;

// Wardrobe application
public class WardrobeApp {
    private Wardrobe wardrobe;
    private Scanner input;

    // EFFECTS: Run the app
    public WardrobeApp() {
        runWardrobe();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runWardrobe() {
        boolean keepGoing = true;
        String command;

        init();

        while (keepGoing) {
            displayMainMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processMainMenuCommand(command);
            }
        }

        System.out.println("\nGoodbye!");
    }

    // EFFECTS: processes user input
    private void processMainMenuCommand(String command) {

        if (command.equals("v")) {
            viewWardrobe();
        } else if (command.equals("e")) {
            editWardrobe();
        } else if (command.equals("s")) {
            viewStats();
        } else {
            System.out.println("Selection not valid...");
        }
    }


    // EFFECTS: display all the apparel items and processes user input
    private void viewWardrobe() {

        boolean keepGoingView = true;

        while (keepGoingView) {
            printWardrobe();
            displayViewWardrobeMenu();
            String command;
            command = input.next();

            try {
                if (command.equals("f")) {
                    viewFilters();
                } else if (command.equals("r")) {
                    keepGoingView = false;
                } else {
                    printItemDetails(wardrobe.getApparels(), Integer.parseInt(command));
                }
            } catch (Exception e) {
                System.out.println("Invalid Input, please try again");
//                e.printStackTrace();
            }
        }
    }

    // EFFECTS: display all the Filter options items and processes user input
    private void viewFilters() {
        boolean keepGoingFilter = true;
        while (keepGoingFilter) {
            displayFilterMenu();
            String command = input.next();
            command = command.toLowerCase();

            if (command.equals("r")) {
                keepGoingFilter = false;
            } else if (command.equals("c")
                    || command.equals("b")
                    || command.equals("p")
                    || command.equals("s")) {
                processFilterCommand(command);
            } else {
                System.out.println("Invalid Input. Please choose again.");
            }
        }
    }

    // EFFECTS: processes user command for filter
    private void processFilterCommand(String command) {
        if (command.equals("c")) {
            processCategoryCommand();
        } else if (command.equals("b")) {
            processBrandCommand();
        } else if (command.equals("p")) {
            processPriceRangeCommand();
        } else if (command.equals("s")) {
            viewSoldItems();
        }
    }

    // EFFECTS: Displays all the sold items with sold price and date
    private void viewSoldItems() {
        ArrayList<Apparel> targetList = wardrobe.getSoldItems();

        for (Apparel item : targetList) {
            System.out.println((targetList.indexOf(item) + 1) + ". " + item.getBrandName() + " " + item.getItemName()
                    + " $" + item.getPricePaid()
                    + " sold for $" + item.getPriceSold() + " on " + item.getSoldDate().getDateLong());
        }
    }

    // EFFECTS: processes user input for price range and display the results
    private void processPriceRangeCommand() {

        int lowerBound;
        int higherBound;
        System.out.println("Type price lower bound");
        lowerBound = Integer.parseInt(input.next());
        System.out.println("Type price higher bound");
        higherBound = Integer.parseInt(input.next());

        ArrayList<Apparel> targetList;
        targetList = wardrobe.selectByPriceRange(lowerBound, higherBound);
        printWardrobe(targetList);
    }

    // EFFECTS: processes user input for brands and display the results
    private void processBrandCommand() {
        boolean keepGoingBrand = true;
        String command;

        while (keepGoingBrand) {
            System.out.println("\t[Brand Name] -> View items of this brand");
            System.out.println("\t[     r    ] -> Return");
            printBrandList();
            command = input.next();
            if (command.equals("r")) {
                keepGoingBrand = false;
            } else {
                ArrayList<Apparel> targetList;
                targetList = wardrobe.selectByBrand(command);

                if (targetList.size() == 0) {
                    System.out.println("Brand not found");
                } else {
                    printWardrobe(targetList);
                }
            }
        }
    }

    // EFFECTS: processes user input for category and display the results
    private void processCategoryCommand() {
        boolean keepGoingCategory = true;
        String categoryCommand;

        while (keepGoingCategory) {
            displayCategoryMenu();
            categoryCommand = input.next();
            categoryCommand = categoryCommand.toLowerCase();

            if (categoryCommand.equals("t")) {
                displaySelectedCategory("Tops");
            } else if (categoryCommand.equals("p")) {
                displaySelectedCategory("Pants");
            } else if (categoryCommand.equals("s")) {
                displaySelectedCategory("Shoes");
            } else if (categoryCommand.equals("a")) {
                displaySelectedCategory("Accessories");
            } else if (categoryCommand.equals("r")) {
                keepGoingCategory = false;
            } else {
                System.out.println("Invalid input...");
            }
        }
    }

    // EFFECTS: Display the items in the selected category
    private void displaySelectedCategory(String category) {
        ArrayList<Apparel> targetList;
        targetList = wardrobe.selectByCategory(category);
        printWardrobe(targetList);
    }

    // MODIFIES: this
    // EFFECTS: Asks for and processes user input
    private void editWardrobe() {
        boolean keepGoingEdit = true;
        String command;
        while (keepGoingEdit) {
            displayEditMenu();
            command = input.next();
            command = command.toLowerCase();
            if (command.equals("r")) {
                keepGoingEdit = false;
            } else if (command.equals("a")) {
                addItem();
            } else if (command.equals("d")) {
                deleteItem();
            } else if (command.equals("m")) {
                markItemAsSold();
            } else {
                System.out.println("Invalid Input. Please try again.");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: adds an item to the wardrobe
    private void addItem() {

        System.out.println("Type the brand name of the item:");
        String brandName = input.next();
        System.out.println("Type the item name:");
        String itemName = input.next();
        System.out.println("Choose its category:");
        System.out.println("[t] -> Tops [p] -> Pants [s] -> Shoes [a] -> Accessories");
        String category = letterToCategory(input.next());
        System.out.println("Type the size of the item:");
        String size = input.next();
        System.out.println("Type the price of the item:");
        int pricePaid = Integer.parseInt(input.next());
        System.out.println("In which year did you get it?");
        int year = Integer.parseInt(input.next());
        System.out.println("In which month did you get it?");
        int month = Integer.parseInt(input.next());
        System.out.println("On which day did you get it?");
        int day = Integer.parseInt(input.next());
        Apparel item = new Apparel(brandName, itemName, category, size, pricePaid);
        Date purchaseDate = new Date(month, day, year);
        item.setPurchaseDate(purchaseDate);
        wardrobe.addAnItem(item);
        printWardrobe();
    }

    // EFFECTS: Convert the letter to a string of the category
    private String letterToCategory(String letter) {
        String category = "Apparel";
        if (letter.equals("t")) {
            category = "Tops";
        } else if (letter.equals("p")) {
            category = "Pants";
        } else if (letter.equals("s")) {
            category = "Shoes";
        } else if (letter.equals("a")) {
            category = "Accessories";
        }
        return category;

    }

    // MODIFIES: this
    // EFFECTS: deletes the item selected by user
    private void deleteItem() {
        int index;
        System.out.println("Type the index of the item to delete");
        printWardrobe();
        index = Integer.parseInt(input.next()) - 1;
        wardrobe.removeAnItem(index);
        printWardrobe();
    }

    // MODIFIES: this
    // EFFECTS: marks the item selected by user as sold.
    private void markItemAsSold() {
        int index;
        int day;
        int month;
        int year;
        int price;
        Apparel targetItem;
        System.out.println("Type the index of the item to mark as sold:");
        printWardrobe();
        index = Integer.parseInt(input.next()) - 1;
        targetItem = wardrobe.getApparels().get(index);
        System.out.println("In which year did you sell it?");
        day = Integer.parseInt(input.next());
        System.out.println("In which month did you sell it?");
        month = Integer.parseInt(input.next());
        System.out.println("On which day did you sell it?");
        year = Integer.parseInt(input.next());
        System.out.println("How much did you get for this item?");
        price = Integer.parseInt(input.next());
        Date soldDate = new Date(month, day, year);
        targetItem.sellItem(price, soldDate);
    }

    // EFFECTS: displays VIEW WARDROBE Menu options to the user
    private void displayViewWardrobeMenu() {
        System.out.println("\t[Item Index] -> Item details");
        System.out.println("\t[     f    ] -> FILTERS");
        System.out.println("\t[     r    ] -> RETURN to the HomePage");
    }

    // EFFECTS: displays EDIT WARDROBE Menu options to the user
    private void displayEditMenu() {
        System.out.println("\tSelect an operation:");
        System.out.println("[   a   ] -> Add an item ");
        System.out.println("[   d   ] -> Delete an item ");
        System.out.println("[   m   ] -> Mark an item as sold ");
        System.out.println("[   r   ] -> Return to Previous Page ");
    }

    // EFFECTS: display wardrobe stats and processes user input
    private void viewStats() {
        boolean keepGoingStat = true;
        String command;
        while (keepGoingStat) {
            printGeneralStats();
            command = input.next();
            if (command.equals("r")) {
                keepGoingStat = false;
            } else if (command.equals("f")) {
                System.out.println("Type START year");
                int startYear = Integer.parseInt(input.next());
                System.out.println("Type START year");
                int endYear = Integer.parseInt(input.next());
                int totalNumItemsYear = wardrobe.getNumItems(startYear, endYear);
                int totalValueYear = wardrobe.calcTotalValue(startYear, endYear);
                printSpecifiedStats(startYear, endYear, totalNumItemsYear, totalValueYear);
            }
        }
    }


    // EFFECTS: display general wardrobe stats
    private void printGeneralStats() {
        int totalNumItems = wardrobe.getNumItems();
        int totalValue = wardrobe.calcTotalValue();
        int numItemsSold = wardrobe.getNumItemsSold();
        int revenue = wardrobe.calcRevenue();
        String favBrand = wardrobe.getFavBrand();
        int numFavBrandItems = wardrobe.selectByBrand(favBrand).size();

        System.out.println("You bought " + totalNumItems + " items worth a total of $" + totalValue);
        System.out.println(favBrand + " is your favourite brand. You have "
                + numFavBrandItems + " items of this brand.");
        System.out.println();

        System.out.println("You sold " + numItemsSold + " in total and made $" + revenue + " by selling them.\n");
        System.out.println("[   r   ]   ->   Return ");
        System.out.println("[   f   ]   ->   Filter data by time");
    }

    // EFFECTS: displays stats in the given time frame
    private void printSpecifiedStats(int startYear, int endYear, int totalNumItemsYear, int totalValueYear) {
        System.out.println("From " + startYear + " to " + endYear);
        System.out.println("You bought " + totalNumItemsYear + " items worth a total of $" + totalValueYear);
        System.out.println("Type anything to return");
        input.next();
    }


    // EFFECTS: displays the brand list with no duplicates
    private void printBrandList() {
        ArrayList<String> targetList = wardrobe.getBrandList();

        for (String str: targetList) {
            System.out.println(str);
        }
    }

    // EFFECTS: displays item details for the selected item
    private void printItemDetails(ArrayList<Apparel> targetList, int index) {
        Apparel targetItem = targetList.get(index - 1);
        System.out.println("\tDesigner: " + targetItem.getBrandName());
        System.out.println("\tItem Name: " + targetItem.getItemName());
        System.out.println("\tSize: " + targetItem.getSize());
        System.out.println("\tPrice: $" + targetItem.getPricePaid());
        System.out.println("\tBought on: " + targetItem.getPurchaseDate().getDateLong());

        if (targetItem.getIsSold()) {
            System.out.println("\tSold on: "
                    + targetItem.getSoldDate().getDateLong()
                    + " for $" + targetItem.getPriceSold() + "\n");
        }
        System.out.println("Press any button to return:");
        input.next();
    }

    private void displayMainMenu() {
        System.out.println("Welcome to Ty's wardrobe collections!");
        System.out.println("Select from:");
        System.out.println("[   v   ] -> VIEW Wardrobe");
        System.out.println("[   e   ] -> EDIT Wardrobe");
        System.out.println("[   s   ] -> STATS");
        System.out.println("[   q   ] -> QUIT");
    }

    private void displayFilterMenu() {
        System.out.println("Select from:");
        System.out.println("[   c   ] -> View by Categories");
        System.out.println("[   b   ] -> View by Brands");
        System.out.println("[   p   ] -> View by Price Ranges");
        System.out.println("[   s   ] -> View sold items");
        System.out.println("[   r   ] -> Return ");
    }

    private void displayCategoryMenu() {
        System.out.println("Select a category:");
        System.out.println("[   t   ] -> Tops ");
        System.out.println("[   p   ] -> Pants ");
        System.out.println("[   s   ] -> Shoes ");
        System.out.println("[   a   ] -> Accessories ");
        System.out.println("[   r   ] -> Return to Previous Page ");
    }

    // EFFECTS: Display all the items in the wardrobe one by one
    private void printWardrobe() {
        ArrayList<Apparel> apparelList = wardrobe.getApparels();
        for (Apparel item : apparelList) {
            System.out.println((apparelList.indexOf(item) + 1) + ". " + item.getBrandName() + " " + item.getItemName()
                    + " $" + item.getPricePaid()
                    + " bought on " + item.getPurchaseDate().getDateLong());
        }
        System.out.println();
    }

    // EFFECTS: Display all the items in the targetList one by one
    private void printWardrobe(ArrayList<Apparel> targetList) {
        for (Apparel item : targetList) {
            System.out.println((targetList.indexOf(item) + 1) + ". " + item.getBrandName() + " " + item.getItemName()
                    + " $" + item.getPricePaid()
                    + " bought on " + item.getPurchaseDate().getDateLong());
        }
    }

    // MODIFIES: this
    // EFFECTS: initialize the wardrobe with some default items for display
    private void init() {
        Apparel apparelA = new Apparel("Dries", "Bomber Jacket", "Tops", "S", 1000);
        Apparel apparelB = new Apparel("Maison", "Flared Jeans", "Pants", "29", 300);
        Apparel apparelC = new Apparel("Rick", "Ramone Sneakers", "Shoes", "41.5", 400);
        Apparel apparelD = new Apparel("Yeezy", "Bomber Jacket", "Tops", "S", 350);
        Apparel apparelE = new Apparel("Vujade", "Flared Cargo", "Pants", "S", 450);
        Apparel apparelF = new Apparel("FOG", "Wool Overcoat", "Tops", "46", 750);
        Apparel apparelG = new Apparel("FOG", "Brown Raw Edge Denim", "Pants", "30", 300);
        apparelG.sellItem(302, new Date(3, 4, 2023));
        apparelB.setPurchaseDate(new Date(12, 30, 2025));

        wardrobe = new Wardrobe();
        wardrobe.addAnItem(apparelA);
        wardrobe.addAnItem(apparelB);
        wardrobe.addAnItem(apparelC);
        wardrobe.addAnItem(apparelD);
        wardrobe.addAnItem(apparelE);
        wardrobe.addAnItem(apparelF);
        wardrobe.addAnItem(apparelG);

        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }


}
