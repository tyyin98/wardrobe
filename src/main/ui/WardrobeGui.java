package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.Apparel;
import model.Wardrobe;
import model.Date;
import persistence.JsonWriter;
import persistence.JsonReader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class WardrobeGui extends JFrame implements ActionListener {

    private static final String JSON_STORE = "./data/wardrobe.json";
    private Wardrobe wardrobe;

    private JsonReader jsonReader;

    private JList<String> apparelJList;
    private DefaultListModel<String> model;
    private JPanel bottomPanel;
    private JButton loadButton;
    private JButton saveButton;


    public WardrobeGui() {

        super("Wardrobe");
        initializeFrame();
        initializeJList();
        initializeBottomPanel();

        jsonReader = new JsonReader(JSON_STORE);
        wardrobe = null;

        pack();
        setVisible(true);
    }

    public void initializeJList() {
        model = new DefaultListModel<>();
        apparelJList = new JList<>(model);
        add(apparelJList, BorderLayout.NORTH);
    }

    public void initializeFrame() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1024, 792));
        setLayout(new BorderLayout());
        setResizable(true);
    }


    public void initializeBottomPanel() {
        bottomPanel = new JPanel();
        loadButton = new JButton("load");
        saveButton = new JButton("save");
        bottomPanel.add(loadButton);
        bottomPanel.add(saveButton);
        loadButton.addActionListener(this);
        this.add(bottomPanel, BorderLayout.SOUTH);
    }


    @Override
    //This is the method that is called when the JButton btn is clicked
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loadButton) {
            System.out.println("LOADING!");
            loadApparels();
        }
    }

    public void loadApparels() {
        model.clear();
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
}
