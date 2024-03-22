package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.Apparel;
import model.Date;
import model.Wardrobe;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class WardrobeGui extends JFrame  {

    private static final String JSON_STORE = "./data/wardrobe.json";

    private JList<Apparel> apparelJList;
    private DefaultListModel<Apparel> model;

    private DisplayPanel displayPanel;
    private AddItemPanel addItemPanel;
    private FiltersPanel filtersPanel;
    private BottomPanel bottomPanel;

    private Wardrobe wardrobe;
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;


    public WardrobeGui() {

        super("Wardrobe");
        initializeFrame();
        initializeJList();
        initializeDisplayPanel();
        initializeAddItemPanel();
        initializeBottomPanel();
        filtersPanel = new FiltersPanel();

        wardrobe = null;
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        pack();
        setVisible(true);
    }

    public void initializeAddItemPanel() {
        addItemPanel = new AddItemPanel();
    }

    // EFFECTS:  creates DisplayPanel
    public void initializeDisplayPanel() {
        displayPanel = new DisplayPanel();
    }

    // MODIFIES: this
    // EFFECTS:  adds DisplayPanel to the center of the Frame
    public void displayItemDetails(Apparel item) {
        remove(filtersPanel);
        remove(addItemPanel);
        displayPanel.setLayout(new GridLayout(11, 1));
        displayPanel.fillDisplayPanelWithContentPanel(item);
        add(displayPanel, BorderLayout.CENTER);
        displayPanel.setVisible(true);
        revalidate();
        repaint();
    }

    public void renderAddItemPanel() {
        remove(displayPanel);
        remove(filtersPanel);
        add(addItemPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }


    // MODIFIES: this
    // EFFECTS: creates and adds the BottomPanel to the bottom
    public void initializeBottomPanel() {
        bottomPanel = new BottomPanel();
        add(bottomPanel, BorderLayout.SOUTH);
    }

    // EFFECTS: creates list, adds listener to it and render it onto the frame
    public void initializeJList() {
        model = new DefaultListModel<>();
        apparelJList = new JList<>(model);
//        apparelJList.setPreferredSize(new Dimension(1000, 200));
        JScrollPane scrollPane = new JScrollPane(apparelJList);

        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Check for double-click
                if (e.getClickCount() == 2) {
                    // Get the index of the clicked item
                    int index = apparelJList.locationToIndex(e.getPoint());
                    // Get the selected item
                    Apparel selectedItem = apparelJList.getModel().getElementAt(index);
//                    JOptionPane.showMessageDialog(null, "Double-clicked on: " + selectedItem); // Trigger your event
                    displayItemDetails(selectedItem);
                }
            }
        };
        apparelJList.addMouseListener(mouseAdapter);
        add(scrollPane, BorderLayout.NORTH);
    }

    public void initializeFrame() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1024, 792));
        setLayout(new BorderLayout());
        setResizable(true);
    }

    // EFFECTS: load apparels from JSON file to model
    public void loadApparels() {
        model.clear();
        try {
            wardrobe = jsonReader.read();
            ArrayList<Apparel> apparels = wardrobe.getApparels();
            for (Apparel item: apparels) {
                model.addElement(item);
            }
            bottomPanel.filtersButton.setEnabled(true);
            bottomPanel.statsButton.setEnabled(true);


        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    public void saveWardrobeToJson() {
        try {
            jsonWriter.open();
            jsonWriter.write(wardrobe);
            jsonWriter.close();
            System.out.println("Saved to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // represents the bottom (button) panel of the app
    //
    public class BottomPanel extends JPanel implements ActionListener {
        protected JButton loadButton =  new JButton("load");
        protected JButton saveButton = new JButton("save");
        protected JButton addButton = new JButton("add");
        protected JButton deleteButton = new JButton("delete");
        protected JButton filtersButton = new JButton("filters");
        protected JButton statsButton = new JButton("stats");

        public BottomPanel() {
            super();
            initializeBottomPanel();
            setVisible(true);
        }

        public void initializeBottomPanel() {

            loadButton.addActionListener(this);
            addButton.addActionListener(this);
            saveButton.addActionListener(this);
            deleteButton.addActionListener(this);
            filtersButton.addActionListener(this);
            filtersButton.setEnabled(false);
            statsButton.addActionListener(this);
            statsButton.setEnabled(false);

            this.add(loadButton);
            this.add(saveButton);
            this.add(addButton);
            this.add(deleteButton);
            this.add(filtersButton);
            this.add(statsButton);
        }

        @Override
        //This is the method that is called when the JButton btn is clicked
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == loadButton) {
                loadApparels();
            }
            if (e.getSource() == saveButton) {
                System.out.println("saving");
                saveWardrobeToJson();
            }
            if (e.getSource() == addButton) {
                renderAddItemPanel();
            }
            if (e.getSource() == deleteButton) {
                System.out.println("delete");
                deleteItem(apparelJList.getSelectedValue());
            }
            if (e.getSource() == statsButton) {
                displayStats();
            }
            if (e.getSource() == filtersButton) {
                renderFilters();
            }

        }
    }

    public void renderFilters() {
        remove(addItemPanel);
        remove(displayPanel);
        add(filtersPanel, BorderLayout.CENTER);
        filtersPanel.setVisible(true);
        revalidate();
        repaint();
    }

    public void displayStats() {
        remove(addItemPanel);
        displayPanel.removeAll();
        displayPanel.setLayout(new GridLayout(3, 1));
        JLabel numItemsBoughtLabel = new JLabel();
        JLabel favBrandLabel = new JLabel();
        JLabel numItemsSoldLabel = new JLabel();
        numItemsBoughtLabel.setText("You bought " + wardrobe.getNumItems()
                + " items worth a total of $" + wardrobe.calcTotalValue());
        favBrandLabel.setText(wardrobe.getFavBrand() + " is your favourite brand. You have "
                + wardrobe.selectByBrand(wardrobe.getFavBrand()).size() + " items of this brand.");
        numItemsSoldLabel.setText("You sold " + wardrobe.getNumItemsSold() + " in total and made $"
                        + wardrobe.calcRevenue() + " by selling them.");
        displayPanel.add(numItemsBoughtLabel);
        displayPanel.add(favBrandLabel);
        displayPanel.add(numItemsSoldLabel);
        add(displayPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public void deleteItem(Apparel item) {
        model.removeElement(item);
        wardrobe.getApparels().remove(item);
    }

    public class DisplayPanel extends JPanel {

        // EFFECTS: creates a new panel, and sets LayOut
        DisplayPanel() {
            super();

//            setBackground(new Color(238, 238,228));
        }


        // EFFECTS: put given piece of item info into a panel with its description
        // EFFECTS: and return the panel
        public JPanel createContentPanel(String desc, String info) {
            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(2,1));
            panel.add(new JLabel(desc));
            panel.add(new JLabel(info));
            return panel;
        }

        public void fillDisplayPanelWithContentPanel(Apparel item) {
            removeAll();
            add(createContentPanel("Designer: ", item.getBrandName()));
            add(createContentPanel("Item: ", item.getItemName()));
            add(createContentPanel("Category: ", item.getCategory()));
            add(createContentPanel("Size: ", item.getSize()));
            add(createContentPanel("Price: $", String.valueOf(item.getPricePaid())));
            add(createContentPanel("Description: ", item.getDescription()));
            add(createContentPanel("Date of Purchase: ", item.getPurchaseDate().getDateLong()));
            if (item.getIsSold()) {
                add(createContentPanel("Sold for: $", String.valueOf(item.getPriceSold())));
                add(createContentPanel("Date of Sale: ", item.getSoldDate().getDateLong()));
            }
        }
    }

    public class AddItemPanel extends JPanel implements ActionListener {

        JTextField itemNameField = new JTextField(15);
        JTextField brandField = new JTextField(15);
        JTextField categoryField = new JTextField(15);
        JTextField sizeField = new JTextField(15);
        JTextField pricePaidField = new JTextField(15);
        JTextField descriptionField = new JTextField(15);
        JTextField yearPurchasedField = new JTextField(15);
        JTextField monthPurchasedField = new JTextField(15);
        JTextField dayPurchasedField = new JTextField(15);
        JCheckBox isSoldCheckBox = new JCheckBox("It is sold.");
        JTextField yearSoldField = new JTextField(15);
        JTextField monthSoldField = new JTextField(15);
        JTextField daySoldField = new JTextField(15);
        JTextField priceSoldField = new JTextField(15);
        JButton submitButton = new JButton("submit");

        public AddItemPanel() {
            this.setLayout(new GridLayout(16, 1));
            initializeAddItemPanel();
        }

        public void initializeAddItemPanel() {

            putElementIntoPanel(itemNameField, "Item name: ");
            putElementIntoPanel(brandField, "Designer: ");
            putElementIntoPanel(categoryField, "Category: ");
            putElementIntoPanel(sizeField, "Size: ");
            putElementIntoPanel(pricePaidField, "Amount paid: ");
            putElementIntoPanel(descriptionField, "Description: ");
            putElementIntoPanel(yearPurchasedField, "Year of purchase: ");
            putElementIntoPanel(monthPurchasedField, "Month of purchase: ");
            putElementIntoPanel(dayPurchasedField, "Day of purchase: ");
            isSoldCheckBox.addActionListener(this);
            putElementIntoPanel(isSoldCheckBox, "");
            yearSoldField.setEnabled(false);
            putElementIntoPanel(yearSoldField, "Year of sale: ");
            monthSoldField.setEnabled(false);
            putElementIntoPanel(monthSoldField, "Month of sale: ");
            daySoldField.setEnabled(false);
            putElementIntoPanel(daySoldField, "Day of sale: ");
            priceSoldField.setEnabled(false);
            putElementIntoPanel(priceSoldField, "Sold price: ");
            submitButton.addActionListener(this);
            putElementIntoPanel(submitButton, "");
            this.setVisible(true);
        }

        public void putElementIntoPanel(JComponent component, String labelText) {
            JPanel panel = new JPanel();
            panel.setLayout(new FlowLayout(FlowLayout.LEADING, 10, 10));
            JLabel label = new JLabel(labelText);
            panel.add(label);
            panel.add(component);
            add(panel);
        }


        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == isSoldCheckBox) {
                if (isSoldCheckBox.isSelected()) {
                    yearSoldField.setEnabled(true);
                    monthSoldField.setEnabled(true);
                    daySoldField.setEnabled(true);
                    priceSoldField.setEnabled(true);
                } else {
                    yearSoldField.setEnabled(false);
                    monthSoldField.setEnabled(false);
                    daySoldField.setEnabled(false);
                    priceSoldField.setEnabled(false);
                }
            }
            if (e.getSource() == submitButton) {
                Apparel item = createApparel();
                wardrobe.addAnItem(item);
                model.addElement(item);
                bottomPanel.filtersButton.setEnabled(true);
                bottomPanel.statsButton.setEnabled(true);
                resetTextFields();
            }
            repaint();
            revalidate();
        }

        public void resetTextFields() {
            itemNameField.setText("");
            brandField.setText("");
            categoryField.setText("");
            sizeField.setText("");
            pricePaidField.setText("");
            descriptionField.setText("");
            yearPurchasedField.setText("");
            monthPurchasedField.setText("");
            dayPurchasedField.setText("");
            isSoldCheckBox.setSelected(false);
            yearSoldField.setText("");
            monthSoldField.setText("");
            daySoldField.setText("");
            priceSoldField.setText("");
        }

        // EFFECTS: creates and returns an apparel with info from textfield
        public Apparel createApparel() {
            Date purchaseDate = new Date(Integer.parseInt(monthPurchasedField.getText()),
                    Integer.parseInt(dayPurchasedField.getText()),
                    Integer.parseInt(yearPurchasedField.getText()));

            Apparel newApparel = new Apparel(brandField.getText(), itemNameField.getText(),
                        categoryField.getText(), sizeField.getText(),
                        Integer.parseInt(pricePaidField.getText()), descriptionField.getText());
            newApparel.setPurchaseDate(purchaseDate);

            if (isSoldCheckBox.isSelected()) {
                newApparel.setIsSold(true);
                Date soldDate = new Date(Integer.parseInt(monthSoldField.getText()),
                        Integer.parseInt(daySoldField.getText()),
                        Integer.parseInt(yearSoldField.getText()));
                newApparel.setSoldDate(soldDate);
                newApparel.setPriceSold(Integer.parseInt(priceSoldField.getText()));
            }

            return newApparel;
        }
    }



    public class FiltersPanel extends JPanel implements ActionListener {
        JCheckBox priceRangeCheckBox = new JCheckBox("Select by price range: ");
        JLabel priceRangeLabel = new JLabel("to");
        JTextField priceLowerBoundField = new JTextField(10);
        JTextField priceHigherBoundField = new JTextField(10);

        JCheckBox brandCheckBox = new JCheckBox("Select by brand: ");
        JLabel brandLabel = new JLabel("Brand name: ");
        JTextField brandNameField = new JTextField(15);

        JCheckBox soldCheckBox = new JCheckBox("Select sold items");

        JButton submitFilerButton = new JButton("Submit Filter");

        // EFFECTS: creates FiltersPanel, and sets layout
        public FiltersPanel() {
            super(new GridLayout(4,1));
            setLayout(null);
            initPriceRangePanel();
            initBrandNamePanel();
            initSoldPanel();
            initSubmitButtonPanel();
        }

        public void initPriceRangePanel() {
            JPanel priceRangePanel = new JPanel(new GridLayout(1,2));
            priceRangeCheckBox.addActionListener(this);
            priceRangePanel.add(priceRangeCheckBox);
            JPanel priceFieldPanel = new JPanel(new GridLayout(1,3));
            priceFieldPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 10, 10));
            priceFieldPanel.add(priceLowerBoundField);
            priceFieldPanel.add(priceRangeLabel);
            priceFieldPanel.add(priceHigherBoundField);
            priceRangePanel.add(priceFieldPanel);
            priceRangePanel.setBounds(0, 0, 1000, 40);
            this.add(priceRangePanel);
        }

        public void initBrandNamePanel() {
            JPanel brandNamePanel = new JPanel(new GridLayout(1,2));
            brandCheckBox.addActionListener(this);
            brandNamePanel.add(brandCheckBox);
            JPanel brandFieldPanel = new JPanel(new GridLayout(1,2));
            brandFieldPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 10, 10));
            brandFieldPanel.add(brandLabel);
            brandFieldPanel.add(brandNameField);
            brandNamePanel.add(brandFieldPanel);
            brandNamePanel.setBounds(0, 50, 1000, 40);
            this.add(brandNamePanel);
        }

        public void initSoldPanel() {
            JPanel soldPanel = new JPanel(new GridLayout(1,1));
            soldCheckBox.addActionListener(this);
            soldPanel.add(soldCheckBox);
            soldPanel.setBounds(0, 100, 1000, 40);
            this.add(soldPanel);
        }

        public void initSubmitButtonPanel() {
            JPanel submitBtnPanel = new JPanel(new GridLayout(1,1));
            submitBtnPanel.setBounds(0, 140, 1000, 40);
            submitFilerButton.addActionListener(this);
            submitBtnPanel.add(submitFilerButton);
            this.add(submitBtnPanel);
        }


        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == submitFilerButton) {
                handleFilter();
            } else {
                toggleCheckBoxes();
            }
        }

        public void handleFilter() {
            if (priceRangeCheckBox.isSelected()) {
                //
            }
            if (brandCheckBox.isSelected()) {
                //
            }
            if (soldCheckBox.isSelected()) {
                //
            }

        }

        public void toggleCheckBoxes() {
            if (!priceRangeCheckBox.isSelected()
                    && !brandCheckBox.isSelected()
                    && !soldCheckBox.isSelected()) {
                priceRangeCheckBox.setEnabled(true);
                brandCheckBox.setEnabled(true);
                soldCheckBox.setEnabled(true);
            }
            if (priceRangeCheckBox.isSelected()) {
                brandCheckBox.setEnabled(false);
                soldCheckBox.setEnabled(false);
            }
            if (brandCheckBox.isSelected()) {
                priceRangeCheckBox.setEnabled(false);
                soldCheckBox.setEnabled(false);
            }
            if (soldCheckBox.isSelected()) {
                priceRangeCheckBox.setEnabled(false);
                brandCheckBox.setEnabled(false);
            }
        }
    }
}