package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.Apparel;
import model.Date;
import model.Wardrobe;
import persistence.JsonReader;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;

public class WardrobeGui extends JFrame  {

    private static final String JSON_STORE = "./data/wardrobe.json";


    private JList<String> apparelJList;
    private DefaultListModel<String> model;


    private DisplayPanel displayPanel;
    private AddItemPanel addItemPanel;
    private BottomPanel bottomPanel;


    private Wardrobe wardrobe;
    private JsonReader jsonReader;


    public WardrobeGui() {

        super("Wardrobe");
        initializeFrame();
        initializeJList();
        initializeDisplayPanel();
        initializeAddItemPanel();
        initializeBottomPanel();

        wardrobe = null;
        jsonReader = new JsonReader(JSON_STORE);


        pack();
        setVisible(true);
    }

    public void initializeAddItemPanel() {
        addItemPanel = new AddItemPanel();
    }

    // EFFECTS:  creates DisplayPanel to the center of the Frame
    public void initializeDisplayPanel() {
        displayPanel = new DisplayPanel();
    }

    // MODIFIES: this
    // EFFECTS:  adds DisplayPanel to the center of the Frame
    public void renderDisplayPanel() {
        remove(addItemPanel);
        add(displayPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public void renderAddItemPanel() {
        remove(displayPanel);
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

        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Check for double-click
                if (e.getClickCount() == 2) {

                    // Get the index of the clicked item
                    int index = apparelJList.locationToIndex(e.getPoint());

                    // Get the selected item
                    String selectedItem = apparelJList.getModel().getElementAt(index);
//                    JOptionPane.showMessageDialog(null, "Double-clicked on: " + selectedItem); // Trigger your event

                    displayPanel.displayItem(index);
                    renderDisplayPanel();
                    displayPanel.setVisible(true);
                }
            }
        };
        apparelJList.addMouseListener(mouseAdapter);
        add(apparelJList, BorderLayout.NORTH);
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
        displayPanel.setVisible(false);
        try {
            wardrobe = jsonReader.read();
            ArrayList<Apparel> apparels = wardrobe.getApparels();
            for (Apparel item: apparels) {
                model.addElement(item.getBrandName() + " ｜ " + item.getItemName() + " ｜ $" + item.getPricePaid());
            }
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // represents the bottom (button) panel of the app
    //
    public class BottomPanel extends JPanel implements ActionListener {
        private JButton loadButton;
        private JButton saveButton;
        private JButton addButton;
        private JButton deleteButton;

        public BottomPanel() {
            super();
            initializeBottomPanel();
            setVisible(true);
        }

        public void initializeBottomPanel() {
            loadButton = new JButton("load");
            saveButton = new JButton("save");
            addButton = new JButton("add");
            deleteButton = new JButton("delete");
            loadButton.addActionListener(this);
            addButton.addActionListener(this);
            saveButton.addActionListener(this);
            deleteButton.addActionListener(this);
            this.add(loadButton);
            this.add(saveButton);
            this.add(addButton);
            this.add(deleteButton);
        }

        @Override
        //This is the method that is called when the JButton btn is clicked
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == loadButton) {
                loadApparels();
            }
            if (e.getSource() == saveButton) {
                System.out.println("saved");
            }
            if (e.getSource() == addButton) {
                renderAddItemPanel();
            }
            if (e.getSource() == deleteButton) {
                System.out.println("delete");
            }
        }
    }

    public class DisplayPanel extends JPanel {
        private JLabel brandLabel;
        private JLabel itemLabel;

        DisplayPanel() {
            super();
            brandLabel = new JLabel();
            add(brandLabel);

            itemLabel = new JLabel();
            add(itemLabel);

            setBackground(new Color(238, 238,228));
            setVisible(false);
        }

        public void displayItem(int index) {
            Apparel item = wardrobe.getApparels().get(index);
            String brandName = item.getBrandName();
            String itemName = item.getItemName();
            brandLabel.setText(brandName);
            itemLabel.setText(itemName);
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
            this.setLayout(new GridLayout(8, 2));
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
            putElementIntoPanel(submitButton, "Sold price: ");
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
                model.addElement(item.getBrandName() + " ｜ " + item.getItemName() + " ｜ $" + item.getPricePaid());
                wardrobe.addAnItem(item);
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


}
