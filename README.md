
# Wardrobe 
## What is it and what will it do?
Wardrobe is a desktop app that models the user's wardrobe. It records one's apparel collection, 
including clothes, pants, shoes, and accessories all in one place.
It allows users to keep track many features of their apparel collection, including：

- category
- item name
- brand/designer
- size
- price paid
- sold price (if applicable)
- date of purchase
- more to come...

Beyond simply keeping track of the wardrobe collection, the app also provides insightful 
statistics. Users can explore changes in the amount of money spent from year to year, 
identify their favorite brands and more.

## Who will use it?
From fashion enthusiasts like me to professionals working 
in the fashion industry, it appeals to a broad audience.

## Why is this project of interest to you? 
I have a big collection of clothes from various designer labels and purchased from different places. 
However, I never know **how many** items I own in my collection, 
nor do I know **how much money** I have spent on them. 
Therefore, I am interested in building an app to track my wardrobe collection.

## User Stories
- I want to be able to view all of all my apparel items in one page.
- I want to be able to add an item to my collection and specify its category, name, brand, size, price paid, date of purchase and sold price (if applicable).
- I want to be able to delete items from my collection.
- I want to be able to view the items by categories, brands, or price range.
- I want to be able to know how much I spend on apparel every year.
- I want to know my favorite brand.

- I want to be able to save my wardrobe to file
- I want to be able to load my wardrobe from file


# Instructions for Grader

**To generate the first required action related to the user story "adding multiple Xs to a Y":**
- Click the button labelled "add", then type in information about the item to add
- Decide if you want to mark this item as "sold"
- Optionally, you can choose an image for the item
- Hit the submit button
- You will see the added item at the bottom of the list, which is at the top of the GUI.

**To generate the second required action related to the user story "adding multiple Xs to a Y":**
- Click the button labelled "filters"
- Pick a filter from the three options
- Click the button labelled "submit filter"
- You will see the items kept by the filter you selected

**To locate my visual component:**
- Click any of the item in the list
- A picture of the item will be displayed in the UI
- If the user did not assign an image to the item, the app will display a default image

**To save the state of my application:**
- Click the button labelled as "save"

**To reload the state of my application:**
- Click the button labelled as "load"

# Phase 4: Task 2
Program is exiting. Printing event log:
Tue Apr 02 20:44:37 PDT 2024
Dries Van Noten | Bomber Jacket | $1000 added to the wardrobe
Tue Apr 02 20:44:37 PDT 2024
Maison Margiela | Flared Jeans | $300 added to the wardrobe
Tue Apr 02 20:44:37 PDT 2024
FOG | Brown Raw Edge Denim | $200 added to the wardrobe
Tue Apr 02 20:44:37 PDT 2024
Rick Owens | Ramone Sneakers | $400 added to the wardrobe
Tue Apr 02 20:44:37 PDT 2024
Yeezy | Black Panther Jacket | $350 added to the wardrobe
Tue Apr 02 20:44:37 PDT 2024
Vujade | Flared Cargo | $450 added to the wardrobe
Tue Apr 02 20:44:37 PDT 2024
FOG | Wool Overcoat | $750 added to the wardrobe
Tue Apr 02 20:44:37 PDT 2024
FOG | Canvas Jacket | $400 added to the wardrobe
Tue Apr 02 20:44:37 PDT 2024
FOG | Black Nylon Bomber | $500 added to the wardrobe
Tue Apr 02 20:44:37 PDT 2024
FOG | Varsity Jacket | $1100 added to the wardrobe
Tue Apr 02 20:44:37 PDT 2024
FOG | Snap Button Trouser | $350 added to the wardrobe
Tue Apr 02 20:44:37 PDT 2024
FOG | 101 Sneakers B&W | $400 added to the wardrobe
Tue Apr 02 20:44:37 PDT 2024
FOG | 101 Sneakers Beige | $150 added to the wardrobe
Tue Apr 02 20:44:37 PDT 2024
Balenciaga | Tractor Chelsea Boots | $550 added to the wardrobe
Tue Apr 02 20:44:37 PDT 2024
FOG | Canvas Jeans | $300 added to the wardrobe
Tue Apr 02 20:44:54 PDT 2024
All items from $100 to $800 selected
Tue Apr 02 20:45:03 PDT 2024
All items of FOG selected
Tue Apr 02 20:45:11 PDT 2024
All sold items selected
Tue Apr 02 20:45:52 PDT 2024
Adidas | A sports hoodie | $100 added to the wardrobe
Tue Apr 02 20:45:57 PDT 2024
Adidas | A sports hoodie | $100 deleted from the wardrobe

Process finished with exit code 0

# Phase 4: Task 3 -- Reflection

**Nested Classes in WardrobeGUI**

For the GUI of my program, I created a class called WardrobeGUI with a lot of nested classes in it. 
The nested pattern allows the inner classes to have direct access to the fields and methods of the enclosing class, 
so that I do not have to create and call certain getter or setter methods for passing and getting references.
However, the nested classes also made my WardrobeGUI class very lengthy. The readability of code in 
this class is somehow affected. 
To enhance readability, I could consider separating all nested classes from the WardrobeGUI class 
and making them into individual classes. This would help make the code more readable and maintainable. 
However, there are also tradeoffs for this refactoring, such as introducing a lot of coupling.

**Enum for the category field**

The Apparel class includes a category field that currently accepts any string value. 
Given that there are a fixed set of desirable values ("Tops", "Bottoms", "Shoes"), 
it would be more appropriate to define an enum named Category and use it in the Apparel 
class. This change would ensure type safety and restrict the category values to the 
predefined set.

