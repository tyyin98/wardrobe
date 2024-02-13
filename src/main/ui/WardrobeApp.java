package ui;

import model.Apparel;
import model.Wardrobe;
import model.Date;

import java.util.ArrayList;
import java.util.Scanner;

public class WardrobeApp {
    private Wardrobe wardrobe;
    private Scanner input;

    public WardrobeApp() {
        runWardrobe();
    }

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

    private void displayViewWardrobeMenu() {
        System.out.println("\tType item Index for its details:");
        System.out.println("\tType f for FILTERS");
        System.out.println("\tType r to RETURN to the HomePage");
    }

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
                processViewMenuCommand(command);
            } else {
                System.out.println("Invalid Input. Please choose again.");
            }
        }
    }

    private void processViewMenuCommand(String command) {
        if (command.equals("c")) {
            viewCategory();
        } else if (command.equals("b")) {
            viewBrand();
        } else if (command.equals("p")) {
            viewPriceRange();
        } else if (command.equals("s")) {
            viewSoldItems();
        }
    }

    private void viewSoldItems() {
        ArrayList<Apparel> targetList = new ArrayList<>();
        for (Apparel item : wardrobe.getApparels()) {
            if (item.getIsSold()) {
                targetList.add(item);
            }
        }
        for (Apparel item : targetList) {
            System.out.println((targetList.indexOf(item) + 1) + ". " + item.getBrandName() + " " + item.getItemName()
                    + " $" + item.getPricePaid()
                    + " sold for $" + item.getPriceSold() + " on " + item.getSoldDate().getDateLong());
        }
    }

    private void viewPriceRange() {

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

    private void viewBrand() {
        boolean keepGoingBrand = true;
        String command;

        while (keepGoingBrand) {
            System.out.println("\tType a brand:");
            System.out.println("\tOr r to return");
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

    private void viewCategory() {
        boolean keepGoingCategory = true;
        String categoryCommand;

        while (keepGoingCategory) {
            displayCategoryMenu();
            categoryCommand = input.next();
            categoryCommand = categoryCommand.toLowerCase();
            ArrayList<Apparel> targetList;



            if (categoryCommand.equals("t")) {
                targetList = wardrobe.selectByCategory("Tops");
                printWardrobe(targetList);
            } else if (categoryCommand.equals("p")) {
                targetList = wardrobe.selectByCategory("Pants");
                printWardrobe(targetList);
            } else if (categoryCommand.equals("s")) {
                targetList = wardrobe.selectByCategory("Shoes");
                printWardrobe(targetList);
            } else if (categoryCommand.equals("r")) {
                keepGoingCategory = false;
            } else {
                System.out.println("Invalid input...");
            }
        }
    }

    private void printWardrobe() {
        ArrayList<Apparel> apparelList = wardrobe.getApparels();
        for (Apparel item : apparelList) {
            System.out.println((apparelList.indexOf(item) + 1) + ". " + item.getBrandName() + " " + item.getItemName()
                    + " $" + item.getPricePaid()
                    + " bought on " + item.getPurchaseDate().getDateLong());
        }
    }

    private void printWardrobe(ArrayList<Apparel> targetList) {
        for (Apparel item : targetList) {
            System.out.println((targetList.indexOf(item) + 1) + ". " + item.getBrandName() + " " + item.getItemName()
                    + " $" + item.getPricePaid()
                    + " bought on " + item.getPurchaseDate().getDateLong());
        }
    }

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

    private void deleteItem() {
        int index;
        System.out.println("Type the index of the item to delete");
        printWardrobe();
        index = Integer.parseInt(input.next()) - 1;
        wardrobe.removeAnItem(index);
        printWardrobe();
    }

    private void addItem() {

        System.out.println("Type the brand name of the item:");
        String brandName = input.next();
        System.out.println("Type the item name:");
        String itemName = input.next();
        System.out.println("Type the category of the item:");
        String category = input.next();
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

    private void displayEditMenu() {
        System.out.println("\tSelect an operation:");
        System.out.println("\ta -> Add an item ");
        System.out.println("\td -> Delete an item ");
        System.out.println("\tm -> Mark an item as sold ");
        System.out.println("\tr -> Return to Previous Page ");
    }

    private void viewStats() {
        boolean keepGoingStat = true;
        String command;
        while (keepGoingStat) {
            printGeneralStats();
            command = input.next();
            if (command.equals("r")) {
                keepGoingStat = false;
            } else if (command.equals("p")) {
                System.out.println("Type START year");
                int startYear = Integer.parseInt(input.next());
                System.out.println("Type START year");
                int endYear = Integer.parseInt(input.next());
                int totalNumItemsYear = wardrobe.getNumItems(startYear, endYear);
                int totalValueYear = wardrobe.calcTotalValue(startYear, endYear);
                System.out.println("From " + startYear + " to " + endYear);
                System.out.println("You bought " + totalNumItemsYear + " items with a total of $" + totalValueYear);
                System.out.println("type anything to return");
                input.next();
            }
        }
    }

    private void printGeneralStats() {
        int totalNumItems = wardrobe.getNumItems();
        int totalValue = wardrobe.calcTotalValue();
        int numItemsSold = wardrobe.getNumItemsSold();
        int revenue = wardrobe.calcRevenue();
        String favBrand = wardrobe.getFavBrand();
        int numFavBrandItems = wardrobe.selectByBrand(favBrand).size();


        System.out.println("You bought " + totalNumItems + " items with a total of $" + totalValue);
        System.out.println(favBrand + " is your favourite brand. You have "
                + numFavBrandItems + " items of this brand.");
        System.out.println();

        System.out.println("You sold " + numItemsSold + " in total and made $" + revenue + " by selling them.\n");
        System.out.println("Type r to return ");
        System.out.println("OR type p to obtain data for a certain period");
    }

    // EFFECTS: initialize the wardrobe with some default items
    private void init() {
        Apparel apparelA = new Apparel("Dries", "Bomber Jacket", "Tops", "S", 1000);
        Apparel apparelB = new Apparel("Maison", "Flared Jeans", "Pants", "29", 300);
        Apparel apparelC = new Apparel("Rick", "Ramone Sneakers", "Shoes", "41.5", 400);
        Apparel apparelD = new Apparel("Yeezy", "Bomber Jacket", "Tops", "S", 350);
        Apparel apparelE = new Apparel("Vujade", "Flared Cargo", "Pants", "S", 450);
        Apparel apparelF = new Apparel("FOG", "Wool Overcoat", "Tops", "46", 750);
        Apparel apparelG = new Apparel("FOG", "Brown Raw Edge", "Pants", "30", 300);
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

    private void printBrandList() {
        ArrayList<String> targetList = wardrobe.getBrandList();

        for (String str: targetList) {
            System.out.println(str);
        }

    }

    private void printItemDetails(ArrayList<Apparel> targetList, int index) {
        Apparel targetItem = targetList.get(index - 1);
        System.out.println("Designer : " + targetItem.getBrandName());
        System.out.println("Item Name: " + targetItem.getItemName());
        System.out.println("Size : " + targetItem.getSize());
        System.out.println("Price : " + targetItem.getPricePaid());
        System.out.println("Bought on : " + targetItem.getPurchaseDate().getDateLong());
        if (targetItem.getIsSold()) {
            System.out.println("Sold on "
                    + targetItem.getSoldDate().getDateLong()
                    + "for " + targetItem.getPriceSold());
        }
    }

    private void displayMainMenu() {
        System.out.println("Welcome to Ty's wardrobe collections!");
        System.out.println("Select from:");
        System.out.println("\tv -> View Wardrobe");
        System.out.println("\te -> Edit Wardrobe");
        System.out.println("\ts -> Stats");
        System.out.println("\tq -> quit");
    }

    private void displayFilterMenu() {
        System.out.println("Select from:");
        System.out.println("\tc -> View by Categories");
        System.out.println("\tb -> View by Brands");
        System.out.println("\tp -> View by Price Ranges");
        System.out.println("\ts -> View sold items");
        System.out.println("\tr -> Return ");
    }

    private void displayCategoryMenu() {
        System.out.println("Select a category:");
        System.out.println("\tt -> Tops ");
        System.out.println("\tp -> Pants ");
        System.out.println("\ts -> Shoes ");
        System.out.println("\tr -> Return to Previous Page ");
    }

}
