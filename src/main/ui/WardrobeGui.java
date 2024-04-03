package ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;


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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Wardrobe app with a GUI
public class WardrobeGui extends JFrame  {

    private static final String JSON_STORE = "./data/wardrobe.json";
    private static final String IMG_SRC = "./data/img/";
    private static final String DEFAULT_IMG = "wardrobeLogo.png";


    private JList<Apparel> apparelJList;
    private DefaultListModel<Apparel> model;

    private DisplayPanel displayPanel;
    private AddItemPanel addItemPanel;
    private FiltersPanel filtersPanel;
    private BottomPanel bottomPanel;

    private Wardrobe wardrobe;
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;

    // EFFECTS: initialize the wardrobe GUI
    public WardrobeGui() {

        super("Wardrobe");
        initializeFrame();
        initializeJList();
        displayPanel = new DisplayPanel();
        addItemPanel = new AddItemPanel();
        initializeBottomPanel();
        filtersPanel = new FiltersPanel();

        wardrobe = new Wardrobe();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        pack();
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: synchronize the content in DefaultListModel with the one in wardrobe
    public void syncJListWithWardrobe() {
        model.clear();
        for (Apparel item: wardrobe.getApparels()) {
            model.addElement(item);
        }
    }


    // MODIFIES: this
    // EFFECTS:  removes everything in the center of the screen
    //           and adds DisplayPanel to the center of the Frame
    public void displayItemDetails(Apparel item) {
        remove(filtersPanel);
        remove(addItemPanel);
        displayPanel.setLayout(new GridLayout(11, 1));
        displayPanel.fillDisplayPanelWithItemDetails(item);
        add(displayPanel, BorderLayout.CENTER);
        displayPanel.setVisible(true);
        revalidate();
        repaint();
    }

    // MODIFIES: this
    // EFFECTS: removes everything in the center of the screen and renders AddItemPanel
    public void renderAddItemPanel() {
        remove(displayPanel);
        remove(filtersPanel);
        syncJListWithWardrobe();
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

    // EFFECTS: creates list, adds listener to it and renders it onto the frame
    public void initializeJList() {
        model = new DefaultListModel<>();
        apparelJList = new JList<>(model);
        JScrollPane scrollPane = new JScrollPane(apparelJList);
        scrollPane.setPreferredSize(new Dimension(512, 200));

//        MouseAdapter mouseAdapter = new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                if (e.getClickCount() == 2) { // Check for double-click
//                    // Get the index of the clicked item
//                    int index = apparelJList.locationToIndex(e.getPoint());
//                    // Get the selected item
//                    Apparel selectedItem = apparelJList.getModel().getElementAt(index);
////                    JOptionPane.showMessageDialog(null, "Double-clicked on: " + selectedItem); // Trigger your event
//                    displayItemDetails(selectedItem);
//                }
//            }
//        };
//        apparelJList.addMouseListener(mouseAdapter);

        // Add a ListSelectionListener to the JList
        apparelJList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) { // This check prevents double events
                    int index = apparelJList.getSelectedIndex();
                    if (index >= 0) {
                        Apparel selectedItem = apparelJList.getModel().getElementAt(index);
                        displayItemDetails(selectedItem);
                    }
                }
            }
        });
        add(scrollPane, BorderLayout.NORTH);
    }

    // EFFECTS: initialize the Frame of the GUI
    public void initializeFrame() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(512, 792));
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

    // EFFECTS: save apparels from current wardrobe (not from JList) to JSON
    public void saveWardrobeToJson() {
        try {
            jsonWriter.open();
            jsonWriter.write(wardrobe);
            jsonWriter.close();
//            System.out.println("Saved to " + JSON_STORE);
        } catch (FileNotFoundException e) {
//            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // represents the bottom panel with buttons of the app
    public class BottomPanel extends JPanel implements ActionListener {
        protected JButton loadButton =  new JButton("load");
        protected JButton saveButton = new JButton("save");
        protected JButton addButton = new JButton("add");
        protected JButton deleteButton = new JButton("delete");
        protected JButton filtersButton = new JButton("filters");
        protected JButton statsButton = new JButton("stats");

        // EFFECTS: creates the bottom panel
        public BottomPanel() {
            super();
            initializeBottomPanel();
            setVisible(true);
        }

        // EFFECTS: initialize the bottom panel
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
//                System.out.println("saving");
                saveWardrobeToJson();
            }
            if (e.getSource() == addButton) {
                renderAddItemPanel();
            }
            if (e.getSource() == deleteButton) {
//                System.out.println("delete");
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

    // EFFECTS: removes everything in central frame and renders the FiltersPanel
    public void renderFilters() {
        remove(addItemPanel);
        remove(displayPanel);
        add(filtersPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    // EFFECTS: removes everything in central frame and display wardrobe stats there.
    public void displayStats() {
        syncJListWithWardrobe();
        remove(filtersPanel);
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

    // EFFECTS: deletes the given item from the wardrobe and JList as well.
    public void deleteItem(Apparel item) {
        model.removeElement(item);
        wardrobe.removeAnItem(item);
    }

    // represents a panel for displaying info (no user interaction) in central frame
    // is used for displaying 1. item info or 2. wardrobe stats
    public class DisplayPanel extends JPanel {

        // EFFECTS: creates a new panel, and sets LayOut
        DisplayPanel() {
            super();
        }

        // EFFECTS: puts given piece of item info into a panel with its description
        //          and returns the panel
        public JPanel createContentPanel(String desc, String info) {
            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(2,1));
            panel.add(new JLabel(desc));
            panel.add(new JLabel(info));
            return panel;
        }

        //EFFECTS: fill the displayPanel with single info panels
        public void fillDisplayPanelWithItemDetails(Apparel item) {
            removeAll();
            setLayout(new GridLayout(1,2));
            JPanel textPanel = new JPanel();
            textPanel.setLayout(new GridLayout(9,1));
            textPanel.add(createContentPanel("Designer: ", item.getBrandName()));
            textPanel.add(createContentPanel("Item: ", item.getItemName()));
            textPanel.add(createContentPanel("Category: ", item.getCategory()));
            textPanel.add(createContentPanel("Size: ", item.getSize()));
            textPanel.add(createContentPanel("Price: $", String.valueOf(item.getPricePaid())));
            textPanel.add(createContentPanel("Description: ", item.getDescription()));
            textPanel.add(createContentPanel("Date of Purchase: ", item.getPurchaseDate().getDateLong()));
            if (item.getIsSold()) {
                textPanel.add(createContentPanel("Sold for: $", String.valueOf(item.getPriceSold())));
                textPanel.add(createContentPanel("Date of Sale: ", item.getSoldDate().getDateLong()));
            }
            add(textPanel);
            if (item.getImgSrc().length() != 0) {
                ImageIcon image = new ImageIcon(IMG_SRC + item.getImgSrc() + ".jpg");
                add(new JLabel(image));
            } else {
                ImageIcon image = new ImageIcon(IMG_SRC + DEFAULT_IMG);
                add(new JLabel(image));
            }
        }
    }

    // represents a panel with action listeners; used for adding items
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

        JFileChooser fileChooser = new JFileChooser();

        JButton choosePhotoButton = new JButton("Select a File");
        JButton submitButton = new JButton("submit");

//        String selectedPhotoPath = new String();

        // EFFECTS: creates the panel and initializes it
        public AddItemPanel() {
            this.setLayout(new GridLayout(16, 1));
            initializeAddItemPanel();
            this.setVisible(true);
        }

        //EFFECTS: initializes the panel
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
            choosePhotoButton.addActionListener(this);
            putElementIntoPanel(choosePhotoButton, "Choose a photo");
            submitButton.addActionListener(this);
            putElementIntoPanel(submitButton, "");
        }

        // EFFECTS: a helper function that adds the arguments into a new panel
        //          and returns the panel
        public void putElementIntoPanel(JComponent component, String labelText) {
            JPanel panel = new JPanel();
            panel.setLayout(new FlowLayout(FlowLayout.LEADING, 10, 10));
            JLabel label = new JLabel(labelText);
            panel.add(label);
            panel.add(component);
            add(panel);
        }

        @Override
        // EFFECTS: handles the events in AddItemPanel
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == isSoldCheckBox) {
                handleIsSoldCheckBox();
            }
            if (e.getSource() == choosePhotoButton) {
                handleChoosePhotoBtn();
            }
            if (e.getSource() == submitButton) {
                handleSubmitBtn();
            }
            repaint();
            revalidate();
        }

        // EFFECTS: handle states for IsSoldCheckBox
        public void handleIsSoldCheckBox() {
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

        // EFFECTS: handle states for ChoosePhotoBtn
        public void handleChoosePhotoBtn() {
            fileChooser.showOpenDialog(WardrobeGui.this);
        }

        // EFFECTS: creates a resized photo from originalImagePath
        //          saves it under "./data/img"
        //          names it with fileName
        public void createResizedPhoto(String originalImagePath, String fileName) {
            try {
                // Read the original image
                BufferedImage originalImage = ImageIO.read(new File(originalImagePath));

                // Calculate the new height to maintain the aspect ratio
                double aspectRatio = (double) originalImage.getWidth() / originalImage.getHeight();
                int newHeight = (int) (300 / aspectRatio);

                // Resize the image
                Image resizedImage = originalImage.getScaledInstance(300, newHeight, Image.SCALE_SMOOTH);
                BufferedImage outputImage = new BufferedImage(300, newHeight, BufferedImage.TYPE_INT_RGB);
                outputImage.getGraphics().drawImage(resizedImage, 0, 0, null);

                // Write the resized image to a new file
                File outputFile = new File("./data/img/" + fileName + ".jpg");
                outputFile.getParentFile().mkdirs(); // Create the output directory if it doesn't exist
                ImageIO.write(outputImage, "jpg", outputFile); // Change "jpg" to your desired image format

//                System.out.println("Image resized and saved to " + "./data/img");
            } catch (IOException e) {
//                System.err.println("Error occurred while resizing and saving the image: " + e.getMessage());
            }
        }

        // EFFECTS: generate a string of current time
        //          which will be used as the resized image's fileName
        public String generateTimeString() {
            // Get the current date and time
            LocalDateTime now = LocalDateTime.now();

            // Define a pattern for the date and time format with no spaces
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

            // Format the current date and time according to the pattern
            String formattedDateTime = now.format(formatter);
            return formattedDateTime;
        }

        // EFFECTS: generate an Apparel using info typed in by the user;
        //          if the user selected a photo,
        //          create a resized photo and setImgSrc for the newly created Apparel
        public void handleSubmitBtn() {
            Apparel item = createApparel();

            if (fileChooser.getSelectedFile() != null) {
                String resizedImgSrc = generateTimeString();
                createResizedPhoto(fileChooser.getSelectedFile().getAbsolutePath(), resizedImgSrc);
                item.setImgSrc(resizedImgSrc);
            }
            wardrobe.addAnItem(item);
            model.addElement(item);
            bottomPanel.filtersButton.setEnabled(true);
            bottomPanel.statsButton.setEnabled(true);
            resetTextFields();
        }

        // EFFECTS: resets the textField (called after the user finishes adding an item)
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
            fileChooser.setSelectedFile(null);
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

    // represents: A Panel with event listener; used for displaying Filters
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
        JButton removeFilterButton = new JButton("Remove Filter");

        // EFFECTS: creates FiltersPanel, and sets layout
        public FiltersPanel() {
            super(new GridLayout(5,1));
            this.setVisible(true);
            setLayout(null);
            initPriceRangePanel();
            initBrandNamePanel();
            initSoldPanel();
            initSubmitButtonPanel();
            initRemoveButtonPanel();
        }

        // EFFECTS: initialize the priceRangePanel
        public void initPriceRangePanel() {
            JPanel priceRangePanel = new JPanel(new GridLayout(2,1));
            priceRangeCheckBox.addActionListener(this);
            priceRangePanel.add(priceRangeCheckBox);
            JPanel priceFieldPanel = new JPanel(new GridLayout(1,3));
            priceFieldPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 10, 10));
            priceFieldPanel.add(priceLowerBoundField);
            priceFieldPanel.add(priceRangeLabel);
            priceFieldPanel.add(priceHigherBoundField);
            priceRangePanel.add(priceFieldPanel);
            priceRangePanel.setBounds(0, 0, 600, 80);
            this.add(priceRangePanel);
        }

        // EFFECTS: initialize the brandNamePanel
        public void initBrandNamePanel() {
            JPanel brandNamePanel = new JPanel(new GridLayout(2,1));
            brandCheckBox.addActionListener(this);
            brandNamePanel.add(brandCheckBox);
            JPanel brandFieldPanel = new JPanel(new GridLayout(1,2));
            brandFieldPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 10, 10));
            brandFieldPanel.add(brandLabel);
            brandFieldPanel.add(brandNameField);
            brandNamePanel.add(brandFieldPanel);
            brandNamePanel.setBounds(0, 90, 600, 80);
            this.add(brandNamePanel);
        }

        // EFFECTS: initialize the soldPanel
        public void initSoldPanel() {
            JPanel soldPanel = new JPanel(new GridLayout(1,1));
            soldCheckBox.addActionListener(this);
            soldPanel.add(soldCheckBox);
            soldPanel.setBounds(0, 180, 300, 40);
            this.add(soldPanel);
        }

        // EFFECTS: initialize the submitButtonPanel
        public void initSubmitButtonPanel() {
            JPanel submitBtnPanel = new JPanel(new GridLayout(1,1));
            submitBtnPanel.setBounds(0, 230, 300, 40);
            submitFilerButton.addActionListener(this);
            submitBtnPanel.add(submitFilerButton);
            this.add(submitBtnPanel);
        }

        // EFFECTS: initialize the removeButtonPanel
        public void initRemoveButtonPanel() {
            JPanel removeBtnPanel = new JPanel(new GridLayout(1,1));
            removeBtnPanel.setBounds(0, 280, 300, 40);
            removeFilterButton.addActionListener(this);
            removeBtnPanel.add(removeFilterButton);
            this.add(removeBtnPanel);
        }


        @Override
        // EFFECTS: handles events happened in this panel
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == submitFilerButton) {
                handleFilter();
            } else if (e.getSource() == removeFilterButton) {
                syncJListWithWardrobe();
            } else {
                toggleCheckBoxes();
            }
        }

        // EFFECTS: handles Filter Commands
        public void handleFilter() {
            if (priceRangeCheckBox.isSelected()) {
                filterByPriceRange(Integer.parseInt(priceLowerBoundField.getText()),
                        Integer.parseInt(priceHigherBoundField.getText()));
            }
            if (brandCheckBox.isSelected()) {
                filterByBrand(brandNameField.getText());
            }
            if (soldCheckBox.isSelected()) {
                filterBySold();
            }
        }

        // EFFECTS: selects from the wardrobe and displays the items asked by the user
        //          WILL NOT affect the wardrobe field
        public void filterByPriceRange(int lower, int higher) {
            model.clear();
            ArrayList<Apparel> targetList = wardrobe.selectByPriceRange(lower, higher);
            for (Apparel item: targetList) {
                model.addElement(item);
            }
        }

        // EFFECTS: selects from the wardrobe and displays the items asked by the user
        //          WILL NOT affect the wardrobe field
        private void filterByBrand(String brandName) {
            model.clear();
            ArrayList<Apparel> targetList = wardrobe.selectByBrand(brandName);
            for (Apparel item: targetList) {
                model.addElement(item);
            }
        }

        // EFFECTS: selects from the wardrobe and displays the items asked by the user
        //          WILL NOT affect the wardrobe field
        private void filterBySold() {
            model.clear();
            ArrayList<Apparel> targetList = wardrobe.getSoldItems();
            for (Apparel item: targetList) {
                model.addElement(item);
            }
        }

        // EFFECTS: toggle the checkBoxes so that there is always
        //          up to one box can be selected
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