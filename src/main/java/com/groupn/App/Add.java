package com.groupn.App;

import com.groupn.database.DBManager;
import com.groupn.entities.Event;
import com.groupn.entities.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;

public class Add extends JDialog {
    private final MainInterface mainInterface;
    private final DBManager dbManager;
    private final String[] columnNames;
    private final int selectedTabIndex;
    private final JPanel rootPane;

    private JPanel contentPane;
    private JButton confirmAdd;
    private JButton cancelAdd;
    private JPanel buttons;
    private JPanel TFPanel;
    private static ArrayList<JLabel> labels = null;

    public Add(MainInterface mainInterface, DBManager dbManager, String[] columnNames, int selectedTabIndex, JPanel rootPane) {
        this.mainInterface = mainInterface;
        this.dbManager = dbManager;
        this.columnNames = columnNames;
        this.selectedTabIndex = selectedTabIndex;
        this.rootPane = rootPane;
        labels = new ArrayList<>();
        setContentPane(contentPane);
        setTitle("Data addition");
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // Close dialog on window close
        TFPanel.setLayout(new GridLayout(0, 3));
        ArrayList<JTextField> textFields = createFields(columnNames);
        setResizable(true);
        pack();
        setAlwaysOnTop(true);
//        mainInterface.setFocusableWindowState(true);
        mainInterface.setAutoRequestFocus(false);
//        mainInterface.setEnabled(true);
        confirmAdd.addActionListener(e -> {
            boolean allFilled = false;
            String[] unnecessaryFields = {"Date of Birth", "Creation date", "Start Date", "End Date", "Opening date", "Price"};
            int i = 0;
            for (JTextField textField : textFields) {
                if (textField.getText().isEmpty() && !Arrays.asList(unnecessaryFields).contains(labels.get(i).getText())) {
                    JOptionPane.showMessageDialog(TFPanel, "Please, do not leave mandatory fields empty. Those are required to add data.\n", "Lack of data", JOptionPane.WARNING_MESSAGE);
                    allFilled = false;
                    break;
                } else if (!dbManager.checkInjectionSafety(textField.getText())) {
                    JOptionPane.showMessageDialog(TFPanel, "The input data is unsafe to add.\nPlease, don't use symbols such as double/single quotes or round brackets", "Lack of data", JOptionPane.WARNING_MESSAGE);
                    allFilled = false;
                    break;
                } else {
                    allFilled = true;
                }
                i++;
            }
            if (allFilled) {
                switch (selectedTabIndex) {
                    case 1: {
                        Author author = new Author();
                        author.setName(textFields.get(0).getText());
                        if (textFields.get(0).getText().matches(".*\\d.*")) {
                            JOptionPane.showMessageDialog(TFPanel, "Please, input full name without digits.", "Incorrect format", JOptionPane.WARNING_MESSAGE);
                            break;
                        }
                        author.setDescription(textFields.get(1).getText());
                        String birthDate = textFields.get(2).getText();
//                        author.setDateOfBirth(birthDate);
                        // "try catch" block with date formatting shall be deleted after date becomes string
                        try {
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                            LocalDate dateOfBirth = LocalDate.parse(birthDate, formatter);
                            author.setDateOfBirth(dateOfBirth);
                        } catch (DateTimeParseException p) {
                            JOptionPane.showMessageDialog(TFPanel, "Please, input date in the form YYYY-MM-DD", "Incorrect format", JOptionPane.WARNING_MESSAGE);
                            break;
                        }
                        dbManager.addAuthor(author);
                        dispose();
                        break;
                    } // Author
                    case 2: {
                        ArtObject artObject = new ArtObject();
                        artObject.setName(textFields.get(0).getText());
                        if (textFields.get(0).getText().matches(".*\\d.*")) {
                            JOptionPane.showMessageDialog(TFPanel, "Please, input name without digits.", "Incorrect format", JOptionPane.WARNING_MESSAGE);
                            break;
                        }
                        artObject.setDescription(textFields.get(1).getText());
                        String dateString = textFields.get(2).getText();
                        // artObject.setDateOfCreation(dateString);
                        // Same "try catch" block deletion after date becomes string
                        try {
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                            LocalDate dateOfCreation = LocalDate.parse(dateString, formatter);
                            artObject.setDateOfCreation(dateOfCreation);
                        } catch (DateTimeParseException p) {
                            JOptionPane.showMessageDialog(TFPanel, "Please, input date in the form YYYY-MM-DD", "Incorrect format", JOptionPane.WARNING_MESSAGE);
                            break;
                        }
                        //
                        Author authorArt = new Author(); // not redundant
                        try {
                            authorArt = dbManager.getAuthorById(Integer.parseInt(textFields.get(3).getText()));
                            artObject.setAuthor(authorArt);
                        } catch (Exception p) {
                            JOptionPane.showMessageDialog(TFPanel, "Author not found. Please, input existing author id as integer.", "Incorrect format", JOptionPane.WARNING_MESSAGE);
                            break;
                        }
                        Owner ownerArt = new Owner(); // not redundant
                        try {
                            ownerArt = dbManager.getOwnerById(Integer.parseInt(textFields.get(4).getText()));
                            artObject.setCurrentOwner(ownerArt);
                        } catch (Exception p) {
                            JOptionPane.showMessageDialog(TFPanel, "Owner not found. Please, input existing owner id as integer.", "Incorrect format", JOptionPane.WARNING_MESSAGE);
                            break;
                        }
                        Location locationArt = new Location(); // not redundant
                        try {
                            locationArt = dbManager.getLocationById(Integer.parseInt(textFields.get(5).getText()));
                            artObject.setCurrentLocation(locationArt);
                        } catch (Exception p) {
                            JOptionPane.showMessageDialog(TFPanel, "Location not found. Please, input existing owner id as integer.", "Incorrect format", JOptionPane.WARNING_MESSAGE);
                            break;
                        }
                        dbManager.addArtObject(artObject);
                        dispose();
                        break;
                    } // Art
                    case 3: {
                        Event event = new Event();
                        event.setName(textFields.get(0).getText());
                        if (textFields.get(0).getText().matches(".*\\d.*")) {
                            JOptionPane.showMessageDialog(TFPanel, "Please, input name without digits.", "Incorrect format", JOptionPane.WARNING_MESSAGE);
                            break;
                        }
                        event.setType(dbManager.getEventTypeById(Integer.parseInt(textFields.get(1).getText())));
                        if (event.getType() == null) {
                            JOptionPane.showMessageDialog(TFPanel, "Event type id not found", "Not found", JOptionPane.WARNING_MESSAGE);
                            break;
                        }
                        event.setDescription(textFields.get(2).getText());
                        String startDate = textFields.get(3).getText();
                        try {
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                            LocalDate dateOfStart = LocalDate.parse(startDate, formatter);
                            event.setStartDateOfEvent(dateOfStart);
                        } catch (DateTimeParseException p) {
                            JOptionPane.showMessageDialog(TFPanel, "Please, input start date in the form YYYY-MM-DD", "Incorrect format", JOptionPane.WARNING_MESSAGE);
                            break;
                        }
                        String endDate = textFields.get(4).getText();
                        try {
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                            LocalDate dateOfEnd = LocalDate.parse(endDate, formatter);
                            event.setEndDateOfEvent(dateOfEnd);
                        } catch (DateTimeParseException p) {
                            JOptionPane.showMessageDialog(TFPanel, "Please, input end date in the form YYYY-MM-DD", "Incorrect format", JOptionPane.WARNING_MESSAGE);
                            break;
                        }
                        event.setLocation(dbManager.getLocationById(Integer.parseInt(textFields.get(5).getText())));
                        if (event.getLocation() == null) {
                            JOptionPane.showMessageDialog(TFPanel, "Location id not found.", "Not found", JOptionPane.WARNING_MESSAGE);
                            break;
                        }
                        try {
                            event.setPrice(Integer.parseInt(textFields.get(6).getText()));
                        } catch (Exception p) {
                            JOptionPane.showMessageDialog(TFPanel, "Please, input price integer value", "Incorrect format", JOptionPane.WARNING_MESSAGE);
                            break;
                        }
                        dbManager.addEvent(event);
                        dispose();
                        break;
                    } // Event
                    case 4: {
                        Location location = new Location();
                        location.setName(textFields.get(0).getText());
                        if (textFields.get(0).getText().matches(".*\\d.*")) {
                            JOptionPane.showMessageDialog(TFPanel, "Please, input location name without digits.", "Incorrect format", JOptionPane.WARNING_MESSAGE);
                            break;
                        }
                        location.setDescription(textFields.get(1).getText());
                        String openingDate = textFields.get(2).getText();
                        try {
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                            LocalDate dateOfOpening = LocalDate.parse(openingDate, formatter);
                            location.setDateOfOpening(dateOfOpening);
                        } catch (DateTimeParseException p) {
                            JOptionPane.showMessageDialog(TFPanel, "Please, input date in the form YYYY-MM-DD", "Incorrect format", JOptionPane.WARNING_MESSAGE);
                            break;
                        }
                        location.setPlacement(textFields.get(3).getText());
                        location.setType(dbManager.getLocationTypeById(Integer.parseInt(textFields.get(4).getText())));
                        if (location.getType() == null) {
                            JOptionPane.showMessageDialog(TFPanel, "Location type id not found.", "Not found", JOptionPane.WARNING_MESSAGE);
                            break;
                        }
//                        dbManager.addLocation(location);
                        dispose();
                        break;
                    } // Location
                    case 5: {
                        Owner owner = new Owner();
                        owner.setName(textFields.get(0).getText());
                        if (textFields.get(0).getText().matches(".*\\d.*")) {
                            JOptionPane.showMessageDialog(TFPanel, "Please, input owner's name without digits.", "Incorrect format", JOptionPane.WARNING_MESSAGE);
                            break;
                        }
                        owner.setDescription(textFields.get(1).getText());
                        if (owner.getName() == null) {
                            JOptionPane.showMessageDialog(TFPanel, "Please, input owner's name.", "Lack of data", JOptionPane.WARNING_MESSAGE);
                            break;
                        }
                        if (owner.getDescription() == null) {
                            JOptionPane.showMessageDialog(TFPanel, "Please, input owner's description.", "Lack of data", JOptionPane.WARNING_MESSAGE);
                            break;
                        }
//                        dbManager.addOwner(owner);
                        dispose();
                        break;
                    } // Owner
                    case 6: {
                        Purchase purchase = new Purchase();
                        String purchaseDate = textFields.get(0).getText();
                        try {
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                            LocalDate purchaseDate2 = LocalDate.parse(purchaseDate, formatter);
                            purchase.setDateOfPurchase(purchaseDate2);
                        } catch (DateTimeParseException p) {
                            JOptionPane.showMessageDialog(TFPanel, "Please, input purchase date in the form YYYY-MM-DD", "Incorrect format", JOptionPane.WARNING_MESSAGE);
                            break;
                        }
                        try {
                            purchase.setPrice(Integer.parseInt(textFields.get(1).getText()));
                        } catch (Exception price) {
                            JOptionPane.showMessageDialog(TFPanel, "Please, input price as integer value", "Incorrect format", JOptionPane.WARNING_MESSAGE);
                            break;
                        }
                        purchase.setArtObject(dbManager.getArtObjectById(Integer.parseInt(textFields.get(2).getText())));
                        if (purchase.getArtObject() == null) {
                            JOptionPane.showMessageDialog(TFPanel, "Art object not found.", "Not found", JOptionPane.WARNING_MESSAGE);
                            break;
                        }
                        purchase.setBuyer(dbManager.getOwnerById(Integer.parseInt(textFields.get(3).getText())));
                        if (purchase.getBuyer() == null) {
                            JOptionPane.showMessageDialog(TFPanel, "Buyer (next owner) id not found.", "Not found", JOptionPane.WARNING_MESSAGE);
                            break;
                        }
                        purchase.setSeller(dbManager.getOwnerById(Integer.parseInt(textFields.get(4).getText())));
                        if (purchase.getSeller() == null) {
                            JOptionPane.showMessageDialog(TFPanel, "Seller (previous owner) id not found.", "Not found", JOptionPane.WARNING_MESSAGE);
                            break;
                        }
                        dbManager.addPurchase(purchase);
                        dispose();
                        break;
                    } // Purchase
//                    case 7: EventTypeAdd
//                    case 8: LocationTypeAdd
                }
            }

        });
        cancelAdd.addActionListener(e -> dispose());
    }

    public ArrayList<JTextField> createFields(String[] columnNames) {
        ArrayList<JTextField> textFields= new ArrayList<>();
        for (String columnName : columnNames) {
            JTextField textField = new JTextField();
            textField.setSize(100, 50);
            textField.setPreferredSize(new Dimension(100, 30));
            textField.setMaximumSize(new Dimension(100, 30));
            textField.setName(columnName);
            textField.setAlignmentX(JTextField.LEFT_ALIGNMENT);
            textField.setHorizontalAlignment(JTextField.LEFT);
            JLabel label = new JLabel(columnName);
            label.setMinimumSize(new Dimension(100, 30));
            label.setMaximumSize(new Dimension(100, 30));
            String[] integerFields = {"Author", "Current owner", "Location", "Type", "Seller", "Buyer", "Art object"};
            if (Arrays.asList(integerFields).contains(columnName)) {
               label.setText(label.getText()+" (integer id)*");
            }
            if (columnName.equals("Price")) {
                label.setText(label.getText()+" (integer)*");
            }
            String[] dateFields = {"Start Date", "End Date", "Opening date", "Date of purchase"};
            if (Arrays.asList(dateFields).contains(columnName)) {
                label.setText("<html>"+label.getText()+" (YYYY-MM-DD)*<html>");
            }
            String[] mandatoryFields = {"Name", "Description", "Placement"};
            if (Arrays.asList(mandatoryFields).contains(columnName)) {
                label.setText(label.getText()+"*");
            }
            labels.add(label);
            TFPanel.add(textField);
            TFPanel.add(label);
            textFields.add(textField);
            TFPanel.add(new JPanel());
        }
        switch (selectedTabIndex) {
            case 1:
                setPreferredSize(new Dimension(400, 175));
                setMinimumSize(new Dimension(400, 175));
                setMaximumSize(new Dimension(400, 175));
                break;
            case 2:
                setPreferredSize(new Dimension(600, 270));
                setMinimumSize(new Dimension(600, 270));
                setMaximumSize(new Dimension(600, 270));
                break;
            case 3:
                setPreferredSize(new Dimension(600, 295));
                setMinimumSize(new Dimension(600, 295));
                setMaximumSize(new Dimension(600, 295));
                break;
            case 4:
                setPreferredSize(new Dimension(500, 235));
                setMinimumSize(new Dimension(500, 235));
                setMaximumSize(new Dimension(500, 235));
                break;
            case 5:
                setPreferredSize(new Dimension(400, 150));
                setMinimumSize(new Dimension(300, 135));
                setMaximumSize(new Dimension(600, 200));
                break;
            case 6:
                setPreferredSize(new Dimension(500, 235));
                setMinimumSize(new Dimension(500, 235));
                setMaximumSize(new Dimension(500, 235));
                break;
        }
        return textFields;
    }
}

