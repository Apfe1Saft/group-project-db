package com.groupn.App;

import com.groupn.database.DBManager;
import com.groupn.entities.Event;
import com.groupn.entities.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.text.html.HTMLDocument;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EventObject;
import java.util.List;

public class MainInterface extends JFrame {

    private JPanel rootPanel;
    private JButton closeConn;
    private JTabbedPane tabbedPane1;
    private JTable AuthorTable;
    private JButton refreshAuthor;
    private JComboBox AuthorCB;
    private JTextField AuthorTF;
    private JButton searchAuthor;
    private JTable ArtObjectsTable;
    private JButton refreshArt;
    private JTextField ArtTF;
    private JComboBox ArtCB;
    private JButton searchArt;
    private JButton searchEvent;
    private JButton refreshEvent;
    private JTable EventTable;
    private JTextField EventTF;
    private JComboBox EventCB;
    private JTable LocationTable;
    private JButton searchLocation;
    private JComboBox LocationCB;
    private JTextField LocationTF;
    private JButton refreshLocation;
    private JTable OwnerTable;
    private JComboBox OwnerCB;
    private JButton searchOwner;
    private JButton refreshOwner;
    private JTextField OwnerTF;
    private JTable PurchaseTable;
    private JButton searchPurchase;
    private JComboBox PurchaseCB;
    private JButton refreshPurchase;
    private JTextField PurchaseTF;
    private JButton aboutButton;
    private JButton deleteAuthor;
    private JButton updateAuthor;
    private JButton updateArt;
    private JButton deleteArt;
    private JButton deleteEvent;
    private JButton updateEvent;
    private JButton updateLocation;
    private JButton deleteLocation;
    private JButton updateOwner;
    private JButton deleteOwner;
    private JButton updatePurchase;
    private JButton deletePurchase;
    private JButton addEvent;
    private JButton addArt;
    private JButton addAuthor;
    private JButton addLocation;
    private JButton addOwner;
    private JButton addPurchase;
    private JScrollPane AuthorScroll;
    private JButton searchEventType;
    private JTextField EventTypeTF;
    private JTextField LocationTypeTF;
    private JButton searchLocationType;
    private JPanel picturePanel;
    private JButton eventObjectsButton;
    private UpdateUI updateUI;
    private Add addUI;
    private final DBManager dbManager;
    private static final String[] eventCBValues = {"By event id", "By word in description"};

    public MainInterface(DBManager dbManager) {
        this.dbManager = dbManager;
        setContentPane(rootPanel);
        this.setIconImage((new ImageIcon("src/main/java/com/groupn/App/Logo/Icon.png")).getImage());
        setTitle("Art catalog");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 500);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        String aboutText = "<html>\n" +
                "<body>\n" +
                "  <h1 style=\"font-size: 1.3em;\">About \"Art catalog\"</h1>\n" +
                "  <p style=\"font-size: 1em;\">This comprehensive database system tracks information on:</p>\n" +
                "  <ul>\n" +
                "    <li><b>Authors</b>: Names and some information about each of them.</li>\n" +
                "    <li><b>Artworks</b>: Paintings, sculptures, writings, music, etc. by authors.</li>\n" +
                "    <li><b>Ownership</b>: Current owners information.</li>\n" +
                "    <li><b>Purchases</b>: Price, date, and buyer information.</li>\n" +
                "    <li><b>Locations</b>: Museums, collections, or exhibitions of artworks.</li>\n" +
                "    <li><b>Events</b>: Exhibitions, or restorations related to artworks.</li>\n" +
                "  </ul>\n" +
                "  <p style=\"font-size: 1em;\">Benefits:</p>\n" +
                "  <ul>\n" +
                "    <li>Accurate and accessible data.</li>\n" +
                "    <li>Efficient management of management of the data.</li>\n" +
                "    <li>Multiple search options to find the most relevant data about the object/person/event you need!</li>\n" +
                "  </ul>\n" +
                "  <p style=\"font-size: 1em;\">A valuable tool for such enterprises that require management of data that is presented above!<br><br><br><br><br></p>\n" +
                "</body>\n" +
                "</html>";
        aboutButton.addActionListener(e -> {
            JDialog aboutDialog = new JDialog(new JFrame(), "Art catalog", true); // Set modal
            aboutDialog.setPreferredSize(new Dimension(600, 400));
            JEditorPane aboutPane = new JEditorPane();
            aboutPane.setContentType("text/html");
            aboutPane.setText(aboutText);
            aboutPane.setCaretPosition(0);
            Font font = new Font("Verdana", Font.PLAIN, 14);
            String bodyRule = "body { font-family: " + font.getFamily() + "; " +
                    "font-size: " + font.getSize() + "pt; }";
            ((HTMLDocument)aboutPane.getDocument()).getStyleSheet().addRule(bodyRule);
            aboutPane.setEditable(false);
            aboutPane.setFocusable(false);
            JScrollPane scrollPane = new JScrollPane(aboutPane);
            aboutDialog.getContentPane().add(scrollPane);
            aboutDialog.pack();
            aboutDialog.setLocationRelativeTo(null);
            aboutDialog.setIconImage((new ImageIcon("src/main/java/com/groupn/App/Logo/Icon.png")).getImage());
            aboutDialog.setVisible(true);
        });
        picturePanel.add(new imagePanel());
        eventObjectsButton.addActionListener(e -> {
            showEventObjects(true);
            if (EventCB.getItemCount() != 1) {
                EventCB.setSelectedIndex(-1);
                EventCB.removeAllItems();
                EventCB.addItem(eventCBValues[0]);
            }
            EventCB.setSelectedIndex(0);
            eventObjectsButton.setText("Refresh event objects");
        });

        tabbedPane1.addChangeListener(e -> {
            int selectedTabIndex = tabbedPane1.getSelectedIndex();
            // Call appropriate methods based on the selected tab index
            if (selectedTabIndex == 1) {
                AuthorTF.setText("");
                showAuthors(false);
            } else if (selectedTabIndex == 2) {
                ArtTF.setText("");
                showArtObjects(false);
            } else if (selectedTabIndex == 3) {
                EventTF.setText("");
                showEvents(false);
            } else if (selectedTabIndex == 4) {
                LocationTF.setText("");
                showLocations(false);
            } else if (selectedTabIndex == 5) {
                OwnerTF.setText("");
                showOwners(false);
            } else if (selectedTabIndex == 6) {
                PurchaseTF.setText("");
                showPurchases(false);
            }
        });

        closeConn.addActionListener(e -> {
            dbManager.closeConnection();
            System.exit(0);
        });

        // refresh handlers
        refreshAuthor.addActionListener(e -> showAuthors(true));
        refreshArt.addActionListener(e -> showArtObjects(true));
        refreshEvent.addActionListener(e -> showEvents(true));
        refreshLocation.addActionListener(e -> showLocations(true));
        refreshOwner.addActionListener(e -> showOwners(true));
        refreshPurchase.addActionListener(e -> showPurchases(true));
        // search handlers
        searchAuthor.addActionListener(e -> {
            int searchOptionAuthor = AuthorCB.getSelectedIndex();
            System.out.println(searchOptionAuthor);
            DefaultTableModel tableModel = (DefaultTableModel) AuthorTable.getModel();
            switch (searchOptionAuthor) {
                case 0:
                    if (AuthorTF.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(rootPanel, "Please, input author id into a text field to execute search.", "No input", JOptionPane.WARNING_MESSAGE);
                        break;
                    }
                    try {
                        if (Integer.parseInt(AuthorTF.getText()) > 0) {
                            Author author = dbManager.getAuthorById(Integer.parseInt(AuthorTF.getText()));
                            if (author == null) {
                                JOptionPane.showMessageDialog(rootPanel, "Author not found.", "Not found", JOptionPane.WARNING_MESSAGE);
                                break;
                            }
                            tableModel.setRowCount(0);
                            Object[] rowData = new Object[]{author.getId(), author.getName(), author.getDescription(), author.getDateOfBirth()};
                            tableModel.addRow(rowData);
                            tableModel.fireTableDataChanged();
                            break;
                        } else {
                            JOptionPane.showMessageDialog(rootPanel, "Author not found.", "No input", JOptionPane.WARNING_MESSAGE);
                            ArtTF.setText("");
                            break;
                        }
                    } catch (Exception r) {
                        AuthorTF.setText("");
                        JOptionPane.showMessageDialog(rootPanel, "Please, input integer value.", "Inappropriate input", JOptionPane.WARNING_MESSAGE);
                        break;
                    }
                case 1:
                    if (AuthorTF.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(rootPanel, "Please, input word into a text field to execute search.", "No input", JOptionPane.WARNING_MESSAGE);
                        ArtTF.setText("");
                        break;
                    }
                    List<Author> authors = dbManager.getAuthorsByFilter(AuthorTF.getText());
                    if (authors.isEmpty()) {
                        JOptionPane.showMessageDialog(rootPanel, "Authors not found by word: \""+AuthorTF+"\".", "Not found", JOptionPane.WARNING_MESSAGE);
                        AuthorTF.setText("");
                        break;
                    }
                    tableModel.setRowCount(0);
                    for (Author author : authors) {
                        Object[] rowData = new Object[]{author.getId(), author.getName(), author.getDescription(), author.getDateOfBirth()};
                        tableModel.addRow(rowData);
                    }
                    tableModel.fireTableDataChanged();
                    break;
                case 2:
                    if (AuthorTF.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(rootPanel, "Please, input art object id into a text field to execute search.", "No input", JOptionPane.WARNING_MESSAGE);
                    }
                    try {
                        if (Integer.parseInt(AuthorTF.getText()) > 0) {

                            int searchId = Integer.parseInt(AuthorTF.getText());
                            Author author = dbManager.getAuthorByArtObjectId(searchId);
                            tableModel.setRowCount(0);
                            Object[] rowData = new Object[]{author.getId(), author.getName(), author.getDescription(), author.getDateOfBirth()};
                            tableModel.addRow(rowData);
                            tableModel.fireTableDataChanged();
                            break;
                        } else {
                            JOptionPane.showMessageDialog(rootPanel, "Author by art object not found.", "No input", JOptionPane.WARNING_MESSAGE);
                            ArtTF.setText("");
                            break;
                        }
                    } catch (Exception b) {
                        AuthorTF.setText("");
                        JOptionPane.showMessageDialog(rootPanel, "Please, input integer value.", "Inappropriate input", JOptionPane.WARNING_MESSAGE);
                    }
                    break;
            }
        });
        searchArt.addActionListener(e -> {
            int searchOptionArt = ArtCB.getSelectedIndex();
            Integer[] idSearches= {0, 2, 3, 4};
            if (Arrays.asList(idSearches).contains(searchOptionArt) && ArtTF.getText().isEmpty()) {
                JOptionPane.showMessageDialog(rootPanel, "Please, input id into a text field to execute search.", "No input", JOptionPane.WARNING_MESSAGE);
                ArtTF.setText("");
            } else {
                try {
                    switch (searchOptionArt) {
                        case 0: {
                            try {
                                if (Integer.parseInt(ArtTF.getText()) > 0) {
                                    int searchId = Integer.parseInt(ArtTF.getText());
                                    ArtObject artObject = dbManager.getArtObjectById(searchId);
                                    if (artObject == null) {
                                        JOptionPane.showMessageDialog(rootPanel, "Art object not found.", "Not found", JOptionPane.WARNING_MESSAGE);
                                        break;
                                    }
                                    DefaultTableModel tableModel = (DefaultTableModel) ArtObjectsTable.getModel();
                                    tableModel.setRowCount(0);
                                    Object[] rowData = new Object[]{artObject.getId(), artObject.getName(), artObject.getDescription(), artObject.getDateOfCreation(), artObject.getAuthor().getName(), artObject.getCurrentOwner().getName(), artObject.getCurrentLocation().getName()};
                                    tableModel.addRow(rowData);
                                    tableModel.fireTableDataChanged();
                                    break;
                                }
                            } catch (Exception r) {
                                JOptionPane.showMessageDialog(rootPanel, "Please, input integer value.", "Inappropriate input", JOptionPane.WARNING_MESSAGE);
                                break;
                            }
                            break;
                        }
                        case 1: {
                            if (ArtTF.getText().isEmpty()) {
                                JOptionPane.showMessageDialog(rootPanel, "Please, input word into a text field to execute search.", "No input", JOptionPane.WARNING_MESSAGE);
                                ArtTF.setText("");
                                break;
                            } else {
                                List<ArtObject> artObjects = dbManager.getArtObjectsByFilter(ArtTF.getText());
                                if (artObjects == null) {
                                    JOptionPane.showMessageDialog(rootPanel, "Art objects not found by word: \"" + ArtTF.getText() + "\".", "Not found", JOptionPane.WARNING_MESSAGE);
                                    ArtTF.setText("");
                                    break;
                                }
                                DefaultTableModel tableModel = (DefaultTableModel) ArtObjectsTable.getModel();
                                tableModel.setRowCount(0);
                                for (ArtObject artObject : artObjects) {
                                    Object[] rowData = new Object[]{artObject.getId(), artObject.getName(), artObject.getDescription(), artObject.getDateOfCreation(), artObject.getAuthor().getName(), artObject.getCurrentOwner().getName(), artObject.getCurrentLocation().getName()};
                                    tableModel.addRow(rowData);
                                }
                                tableModel.fireTableDataChanged();
                                break;
                            }
                        }
                        case 2: {
                            if (ArtTF.getText().isEmpty()) {
                                JOptionPane.showMessageDialog(rootPanel, "Please, input id into a text field to execute search.", "No input", JOptionPane.WARNING_MESSAGE);
                            } else {
                                try {
                                    if (Integer.parseInt(ArtTF.getText()) > 0) {
                                        int searchId = Integer.parseInt(ArtTF.getText());
                                        List<ArtObject> artObject = dbManager.getArtObjectByAuthor(searchId);
                                        if (artObject.isEmpty()) {
                                            JOptionPane.showMessageDialog(rootPanel, "Author not found to find art object", "Not found", JOptionPane.WARNING_MESSAGE);
                                            ArtTF.setText("");
                                            break;
                                        }
                                        DefaultTableModel tableModel = (DefaultTableModel) ArtObjectsTable.getModel();
                                        tableModel.setRowCount(0);
                                        int i = 0;
                                        while (i < artObject.size()) {
                                            Object[] rowData = new Object[]{artObject.get(i).getId(), artObject.get(i).getName(), artObject.get(i).getDescription(), artObject.get(i).getDateOfCreation(), artObject.get(i).getAuthor().getName(), artObject.get(i).getCurrentOwner().getName(), artObject.get(i).getCurrentLocation().getName()};
                                            tableModel.addRow(rowData);
                                            tableModel.fireTableDataChanged();
                                            i++;
                                        }
                                        break;
                                    }
                                } catch (Exception r) {
                                    JOptionPane.showMessageDialog(rootPanel, "Please, input integer value.", "Inappropriate input", JOptionPane.WARNING_MESSAGE);
                                    break;
                                }
                            }
                        }
                        case 3: {
                            if (ArtTF.getText().isEmpty()) {
                                JOptionPane.showMessageDialog(rootPanel, "Please, input id into a text field to execute search.", "No input", JOptionPane.WARNING_MESSAGE);
                            } else {
                                try {
                                if (Integer.parseInt(ArtTF.getText()) > 0) {
                                    int searchId = Integer.parseInt(ArtTF.getText());
                                    List<ArtObject> artObject = dbManager.getArtObjectByOwner(searchId);
                                    if (artObject.isEmpty()) {
                                        JOptionPane.showMessageDialog(rootPanel, "Owner not found to find art object", "Not found", JOptionPane.WARNING_MESSAGE);
                                        ArtTF.setText("");
                                        break;
                                    }
                                    DefaultTableModel tableModel = (DefaultTableModel) ArtObjectsTable.getModel();
                                    tableModel.setRowCount(0);
                                    int i = 0;
                                    while (i < artObject.size()) {
                                        Object[] rowData = new Object[]{artObject.get(i).getId(), artObject.get(i).getName(), artObject.get(i).getDescription(), artObject.get(i).getDateOfCreation(), artObject.get(i).getAuthor().getName(), artObject.get(i).getCurrentOwner().getName(), artObject.get(i).getCurrentLocation().getName()};
                                        tableModel.addRow(rowData);
                                        tableModel.fireTableDataChanged();
                                        i++;
                                    }
                                    break;
                                    }
                                } catch (Exception r) {
                                    JOptionPane.showMessageDialog(rootPanel, "Please, input integer value.", "Inappropriate input", JOptionPane.WARNING_MESSAGE);
                                    break;
                                }
                            }
                        }
                        case 4: {
                            if (ArtTF.getText().isEmpty()) {
                                JOptionPane.showMessageDialog(rootPanel, "Please, input id into a text field to execute search.", "No input", JOptionPane.WARNING_MESSAGE);
                            } else {
                                try {
                                    if (Integer.parseInt(ArtTF.getText()) > 0) {
                                        int searchId = Integer.parseInt(ArtTF.getText());
                                        List<ArtObject> artObject = dbManager.getArtObjectByLocation(searchId);
                                        if (artObject.isEmpty()) {
                                            JOptionPane.showMessageDialog(rootPanel, "Location not found to find art object", "Not found", JOptionPane.WARNING_MESSAGE);
                                            ArtTF.setText("");
                                            break;
                                        }
                                        DefaultTableModel tableModel = (DefaultTableModel) ArtObjectsTable.getModel();
                                        tableModel.setRowCount(0);
                                        int i = 0;
                                        while (i < artObject.size()) {
                                            Object[] rowData = new Object[]{artObject.get(i).getId(), artObject.get(i).getName(), artObject.get(i).getDescription(), artObject.get(i).getDateOfCreation(), artObject.get(i).getAuthor().getName(), artObject.get(i).getCurrentOwner().getName(), artObject.get(i).getCurrentLocation().getName()};
                                            tableModel.addRow(rowData);
                                            tableModel.fireTableDataChanged();
                                            i++;
                                        }
                                        break;
                                    }
                                } catch (Exception r) {
                                    JOptionPane.showMessageDialog(rootPanel, "Please, input integer value.", "Inappropriate input", JOptionPane.WARNING_MESSAGE);
                                    break;
                                }
                            }
                        }
                        case 5: {
                            if (ArtTF.getText().isEmpty()) {
                                JOptionPane.showMessageDialog(rootPanel, "Please, input id into a text field to execute search.", "No input", JOptionPane.WARNING_MESSAGE);
                            } else {
                                try {
                                    if (Integer.parseInt(ArtTF.getText()) > 0) {
                                        int searchId = Integer.parseInt(ArtTF.getText());
                                        List<ArtObject> artObject;
                                        try {
                                            artObject = dbManager.getArtObjectsByEventId(searchId, dbManager);
                                        } catch (Exception k){
                                            JOptionPane.showMessageDialog(rootPanel, "Event not found.", "Not found", JOptionPane.WARNING_MESSAGE);
                                            break;
                                        }
                                        if (artObject.isEmpty()) {
                                            JOptionPane.showMessageDialog(rootPanel, "No art objects are found for the event.", "Not found", JOptionPane.WARNING_MESSAGE);
                                            ArtTF.setText("");
                                            break;
                                        }
                                        DefaultTableModel tableModel = (DefaultTableModel) ArtObjectsTable.getModel();
                                        tableModel.setRowCount(0);
                                        int i = 0;
                                        while (i < artObject.size()) {
                                            Object[] rowData = new Object[]{artObject.get(i).getId(), artObject.get(i).getName(), artObject.get(i).getDescription(), artObject.get(i).getDateOfCreation(), artObject.get(i).getAuthor().getName(), artObject.get(i).getCurrentOwner().getName(), artObject.get(i).getCurrentLocation().getName()};
                                            tableModel.addRow(rowData);
                                            tableModel.fireTableDataChanged();
                                            i++;
                                        }
                                        break;
                                    }
                                } catch (Exception r) {
                                    JOptionPane.showMessageDialog(rootPanel, "Please, input integer value.", "Inappropriate input", JOptionPane.WARNING_MESSAGE);
                                    break;
                                }
                            }
                        }

                }} catch (Exception a) {
                    ArtTF.setText("");
                    JOptionPane.showMessageDialog(rootPanel, "Please, input integer value.", "Inappropriate input", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        searchEvent.addActionListener(e -> {
            int searchOptionEvent = EventCB.getSelectedIndex();
            System.out.println(searchOptionEvent +"\n"+ EventCB.getItemCount());
            if (EventCB.getItemCount() != 1) {
                switch (searchOptionEvent) {
                    case 0:
                        if (EventTF.getText().isEmpty()) {
                            JOptionPane.showMessageDialog(rootPanel, "Please, input event id into a text field to execute search.", "No input", JOptionPane.WARNING_MESSAGE);
                            EventTF.setText("");
                            break;
                        } else {
                            try {
                                if (Integer.parseInt(EventTF.getText()) > 0) {

                                    int searchId = Integer.parseInt(EventTF.getText());
                                    Event event = dbManager.getEventById(searchId);
                                    if (event == null) {
                                        JOptionPane.showMessageDialog(rootPanel, "Event not found.", "Not found", JOptionPane.WARNING_MESSAGE);
                                        EventTF.setText("");
                                        break;
                                    } else {
                                        DefaultTableModel tableModel = (DefaultTableModel) EventTable.getModel();
                                        tableModel.setRowCount(0);
                                        Object[] rowData = new Object[]{event.getId(), event.getName(), event.getType(), event.getDescription(), event.getStartDateOfEvent(), event.getLocation(), event.getPrice()};
                                        tableModel.addRow(rowData);
                                        tableModel.fireTableDataChanged();
                                    }
                                    break;
                                }
                            } catch (Exception a) {
                                EventTF.setText("");
                                JOptionPane.showMessageDialog(rootPanel, "Please, input integer value.", "Inappropriate input", JOptionPane.WARNING_MESSAGE);
                                break;
                            }
                        }
                    case 1:
                        if (EventTF.getText().isEmpty()) {
                            JOptionPane.showMessageDialog(rootPanel, "Please, input word into a text field to execute search.", "No input", JOptionPane.WARNING_MESSAGE);
                            EventTF.setText("");
                            break;
                        } else {
                            List<Event> events = dbManager.getEventsByFilter(EventTF.getText());
                            DefaultTableModel tableModel = (DefaultTableModel) EventTable.getModel();
                            if (events.isEmpty()) {
                                JOptionPane.showMessageDialog(rootPanel, "Events not found by word in description: \"" + EventTF.getText() + "\".", "Not found", JOptionPane.WARNING_MESSAGE);
                                EventTF.setText("");
                                break;
                            } else {
                                tableModel.setRowCount(0);
                                for (Event event : events) {
                                    Object[] rowData = new Object[]{event.getId(), event.getName(), event.getType(), event.getDescription(), event.getStartDateOfEvent(), event.getEndDateOfEvent(), event.getLocation().getName(), event.getPrice()};
                                    tableModel.addRow(rowData);
                                }
                                tableModel.fireTableDataChanged();
                            }
                            break;
                        }

                }
            } else {
                if (EventTF.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(rootPanel, "Please, input event id into a text field to execute search.", "No input", JOptionPane.WARNING_MESSAGE);
                    EventTF.setText("");
                } else {
                    try {
                        if (Integer.parseInt(EventTF.getText()) > 0) {

                            int searchId = Integer.parseInt(EventTF.getText());
                            List<EventObjects> eventObjects = dbManager.getAllEventObjects();
                            List<EventObjects> foundEventObjects = new ArrayList<>();
                            for (EventObjects eventObject : eventObjects) {
                                if (eventObject.getEventId() == searchId) {
                                    foundEventObjects.add(eventObject);
                                }
                            }
                            if (foundEventObjects.isEmpty()) {
                                JOptionPane.showMessageDialog(rootPanel, "Event not found.", "Not found", JOptionPane.WARNING_MESSAGE);
                                EventTF.setText("");
                            } else {
                                DefaultTableModel tableModel = (DefaultTableModel) EventTable.getModel();
                                tableModel.setRowCount(0);
                                for (EventObjects eventObject : foundEventObjects) {
                                    Event event = dbManager.getEventById(eventObject.getEventId());
                                    ArtObject artObject = dbManager.getArtObjectById(eventObject.getArtObjectId());
                                    Object[] rowData = new Object[]{event.getId(), event.getName(), event.getType(), event.getDescription(), artObject.getId(), artObject.getName()};
                                    tableModel.addRow(rowData);
                                }
                                tableModel.fireTableDataChanged();
                            }
                        }
                    } catch (Exception a) {
                        EventTF.setText("");
                        JOptionPane.showMessageDialog(rootPanel, "Please, input integer value.", "Inappropriate input", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }


        });
        searchEventType.addActionListener(e -> {
            if (EventTypeTF.getText().isEmpty()) {
                JOptionPane.showMessageDialog(rootPanel, "Please, input event type into a text field below first.", "No input", JOptionPane.WARNING_MESSAGE);
            } else {
                int EventTypeId = 0;
                try {
                    EventTypeId = Integer.parseInt(EventTypeTF.getText());
                } catch (Exception p) {JOptionPane.showMessageDialog(rootPanel, "Please, input integer value.", "Inappropriate format", JOptionPane.WARNING_MESSAGE);
                }
                EventType eventType = dbManager.getEventTypeById(EventTypeId);
                if (eventType != null) {
                    EventTypeTF.setText(eventType.name());
                } else {
                    EventTypeTF.setText("Type not found...");
                }
            }
        });
        searchLocation.addActionListener(e -> {
            int searchOptionLocation = LocationCB.getSelectedIndex();
            switch (searchOptionLocation) {
                case 0:
                    if (LocationTF.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(rootPanel, "Please, input location id into a text field to execute search.", "No input", JOptionPane.WARNING_MESSAGE);
                        break;
                    } else {
                        try {
                            if (Integer.parseInt(LocationTF.getText()) > 0) {

                                int searchId = Integer.parseInt(LocationTF.getText());
                                Location location = dbManager.getLocationById(searchId);
                                if (location == null) {
                                    JOptionPane.showMessageDialog(rootPanel, "Location not found.", "Not found", JOptionPane.WARNING_MESSAGE);
                                    LocationTF.setText("");
                                    break;
                                } else {
                                    DefaultTableModel tableModel = (DefaultTableModel) LocationTable.getModel();
                                    tableModel.setRowCount(0);
                                    Object[] rowData = new Object[]{location.getId(), location.getName(), location.getDescription(), location.getDateOfOpening(), location.getPlacement(), location.getType()};
                                    tableModel.addRow(rowData);
                                    tableModel.fireTableDataChanged();
                                }
                            }
                        } catch (Exception a) {
                            EventTF.setText("");
                            JOptionPane.showMessageDialog(rootPanel, "Please, input integer value.", "Inappropriate input", JOptionPane.WARNING_MESSAGE);
                            break;
                        }
                    }
                case 1:
                    if (LocationTF.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(rootPanel, "Please, input word into a text field to execute search.", "No input", JOptionPane.WARNING_MESSAGE);
                        break;
                    }
                    List<Location> locations = dbManager.getLocationsByFilter(LocationTF.getText());
                    if (locations.isEmpty()) {
                        JOptionPane.showMessageDialog(rootPanel, "Locations not found by name or word in description: \""+LocationTF.getText()+"\".", "Not found", JOptionPane.WARNING_MESSAGE);
                        LocationTF.setText("");
                        break;
                    }
                    DefaultTableModel tableModel = (DefaultTableModel) LocationTable.getModel();
                    tableModel.setRowCount(0);
                    for (Location location : locations) {
                        Object[] rowData = new Object[]{location.getId(), location.getName(), location.getDescription(), location.getDateOfOpening(), location.getPlacement(), location.getType()};
                        tableModel.addRow(rowData);
                    }
                    tableModel.fireTableDataChanged();
                    break;
            }
        });
        searchLocationType.addActionListener(e -> {
            if (LocationTypeTF.getText().isEmpty()) {
                JOptionPane.showMessageDialog(rootPanel, "Please, input location type into a text field below first.", "No input", JOptionPane.WARNING_MESSAGE);
            } else {
                int LocationTypeId = 0;
                try {
                    LocationTypeId = Integer.parseInt(LocationTypeTF.getText());
                } catch (Exception p) {JOptionPane.showMessageDialog(rootPanel, "Please, input integer value.", "Inappropriate format", JOptionPane.WARNING_MESSAGE);
                }
                LocationType locationType = dbManager.getLocationTypeById(LocationTypeId);
                if (locationType != null) {
                    LocationTypeTF.setText(locationType.name());
                } else {
                    LocationTypeTF.setText("Type not found...");
                }
            }
        });
        searchOwner.addActionListener(e -> {
            int searchOptionOwner = OwnerCB.getSelectedIndex();
            DefaultTableModel tableModel = (DefaultTableModel) OwnerTable.getModel();
            switch (searchOptionOwner) {
                case 0:
                    if (OwnerTF.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(rootPanel, "Please, input owner's id into a text field to execute search.", "No input", JOptionPane.WARNING_MESSAGE);
                        break;
                    } else {
                        try {
                            if (Integer.parseInt(OwnerTF.getText()) > 0) {

                                int searchId = Integer.parseInt(OwnerTF.getText());
                                Owner owner = dbManager.getOwnerById(searchId);
                                if (owner == null) {
                                    JOptionPane.showMessageDialog(rootPanel, "Owner not found.", "Not found", JOptionPane.WARNING_MESSAGE);
                                    OwnerTF.setText("");
                                    break;
                                } else {
                                    tableModel.setRowCount(0);
                                    Object[] rowData = new Object[]{owner.getId(), owner.getName(), owner.getDescription()};
                                    tableModel.addRow(rowData);
                                    tableModel.fireTableDataChanged();
                                }
                            }
                        } catch (Exception a) {
                            EventTF.setText("");
                            JOptionPane.showMessageDialog(rootPanel, "Please, input integer value.", "Inappropriate input", JOptionPane.WARNING_MESSAGE);
                            break;
                        }
                    }
                case 1:
                    if (OwnerTF.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(rootPanel, "Please, input owner's name or word in description into a text field to execute search.", "No input", JOptionPane.WARNING_MESSAGE);
                        break;
                    }
                    List<Owner> owners = dbManager.getOwnersByFilter(OwnerTF.getText());
                    if (owners.isEmpty()) {
                        JOptionPane.showMessageDialog(rootPanel, "Owners not found by name or word in description: \""+OwnerTF.getText()+"\".", "Not found", JOptionPane.WARNING_MESSAGE);
                        OwnerTF.setText("");
                        break;
                    }
                    tableModel.setRowCount(0);
                    for (Owner owner : owners) {
                        Object[] rowData = new Object[]{owner.getId(), owner.getName(), owner.getDescription()};
                        tableModel.addRow(rowData);
                    }
                    tableModel.fireTableDataChanged();
                    break;

            }
        });
        searchPurchase.addActionListener(e -> {
            int searchOptionPurchase = PurchaseCB.getSelectedIndex();
            DefaultTableModel tableModel = (DefaultTableModel) PurchaseTable.getModel();
            switch (searchOptionPurchase) {
                case 0:
                    if (PurchaseTF.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(rootPanel, "Please, input purchase id into a text field to execute search.", "No input", JOptionPane.WARNING_MESSAGE);
                        break;
                    } else {
                        try {
                            if (Integer.parseInt(PurchaseTF.getText()) > 0) {

                                int searchId = Integer.parseInt(PurchaseTF.getText());
                                Purchase purchase = dbManager.getPurchaseId(searchId, dbManager);
                                if (purchase == null) {
                                    JOptionPane.showMessageDialog(rootPanel, "Purchase not found.", "Not found", JOptionPane.WARNING_MESSAGE);
                                    EventTF.setText("");
                                    break;
                                } else {
                                    tableModel.setRowCount(0);
                                    Object[] rowData = new Object[]{purchase.getId(), purchase.getDateOfPurchase(), "€ "+purchase.getPrice(), purchase.getArtObject().getName(), purchase.getSeller().getName(), purchase.getBuyer().getName()};
                                    tableModel.addRow(rowData);
                                    tableModel.fireTableDataChanged();
                                }
                            }
                        } catch (Exception a) {
                            EventTF.setText("");
                            JOptionPane.showMessageDialog(rootPanel, "Please, input integer value.", "Inappropriate input", JOptionPane.WARNING_MESSAGE);
                            break;
                        }
                    }
                case 1:
                    if (PurchaseTF.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(rootPanel, "Please, input approximate date (year, day, month) into a text field to execute search.", "No input", JOptionPane.WARNING_MESSAGE);
                        break;
                    }
                    List<Purchase> purchases = dbManager.getPurchasesByFilter(PurchaseTF.getText());
                    if (purchases.isEmpty()) {
                        JOptionPane.showMessageDialog(rootPanel, "Purchases not found approximate date: \""+PurchaseTF.getText()+"\".", "Not found", JOptionPane.WARNING_MESSAGE);
                        PurchaseTF.setText("");
                        break;
                    }
                    tableModel.setRowCount(0);
                    for (Purchase purchase : purchases) {
                        Object[] rowData = new Object[]{purchase.getId(), purchase.getDateOfPurchase(), "€ "+purchase.getPrice(), purchase.getArtObject().getName(), purchase.getSeller().getName(), purchase.getBuyer().getName()};
                        tableModel.addRow(rowData);
                    }
                    tableModel.fireTableDataChanged();
                    break;
            }
        });
        // update handlers
        updateAuthor.addActionListener(e -> {
            if ((addUI == null || !addUI.isVisible()) & (updateUI == null || !updateUI.isVisible())) {
                updateUI = new UpdateUI(this, dbManager, new String[]{"ID", "Name", "Description", "Date of Birth"}, 1, rootPanel);
                updateUI.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        updateUI.setAlwaysOnTop(false);
                        setFocusableWindowState(true);
                        setAutoRequestFocus(true);
                        setEnabled(true);
                        requestFocus();
                    }
                });
            } else {
                JOptionPane.showMessageDialog(rootPanel, "Another window is already opened", "Some operation is in process already", JOptionPane.WARNING_MESSAGE);
                LocationTF.setText("");
            }
        });
        updateArt.addActionListener(e -> {
            if ((addUI == null || !addUI.isVisible()) & (updateUI == null || !updateUI.isVisible())) {
                updateUI = new UpdateUI(this, dbManager, new String[]{"ID", "Name", "Description", "Creation date", "Author", "Current owner", "Location"}, 2, rootPanel);
                updateUI.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        updateUI.setAlwaysOnTop(false);
                        setFocusableWindowState(true);
                        setAutoRequestFocus(true);
                        setEnabled(true);
                        requestFocus();
                    }
                });
            } else {
                JOptionPane.showMessageDialog(rootPanel, "Another window is already opened", "Some operation is in process already", JOptionPane.WARNING_MESSAGE);
                LocationTF.setText("");
            }
        });
        updateEvent.addActionListener(e -> {
            if ((addUI == null || !addUI.isVisible()) & (updateUI == null || !updateUI.isVisible())) {
                updateUI = new UpdateUI(this, dbManager, new String[]{"ID", "Name", "Type", "Description", "Start Date", "Event Date", "Location", "Price"}, 3, rootPanel);
                updateUI.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        updateUI.setAlwaysOnTop(false);
                        setFocusableWindowState(true);
                        setAutoRequestFocus(true);
                        setEnabled(true);
                        requestFocus();
                    }
                });
            } else {
                JOptionPane.showMessageDialog(rootPanel, "Another window is already opened", "Some operation is in process already", JOptionPane.WARNING_MESSAGE);
                LocationTF.setText("");
            }
        });
        updateLocation.addActionListener(e -> {
            if ((addUI == null || !addUI.isVisible()) & (updateUI == null || !updateUI.isVisible())) {
                updateUI = new UpdateUI(this, dbManager, new String[]{"ID", "Name", "Description", "Opening date", "Placement", "Type"}, 4, rootPanel);
                updateUI.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        updateUI.setAlwaysOnTop(false);
                        setFocusableWindowState(true);
                        setAutoRequestFocus(true);
                        setEnabled(true);
                        requestFocus();
                    }
                });
            } else {
                JOptionPane.showMessageDialog(rootPanel, "Another window is already opened", "Some operation is in process already", JOptionPane.WARNING_MESSAGE);
                LocationTF.setText("");
            }
        });
        updateOwner.addActionListener(e -> {
            if ((addUI == null || !addUI.isVisible()) & (updateUI == null || !updateUI.isVisible())) {
                updateUI = new UpdateUI(this, dbManager, new String[]{"ID", "Name", "Description"}, 5, rootPanel);
                updateUI.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        updateUI.setAlwaysOnTop(false);
                        setFocusableWindowState(true);
                        setAutoRequestFocus(true);
                        setEnabled(true);
                        requestFocus();
                    }
                });
            } else {
                JOptionPane.showMessageDialog(rootPanel, "Another window is already opened", "Some operation is in process already", JOptionPane.WARNING_MESSAGE);
                LocationTF.setText("");
            }
        });
        updatePurchase.addActionListener(e -> {
            if ((addUI == null || !addUI.isVisible()) & (updateUI == null || !updateUI.isVisible())) {
                updateUI = new UpdateUI(this, dbManager, new String[]{"ID", "Date of purchase", "Price", "Art object", "Seller", "Buyer"}, 6, rootPanel);
                updateUI.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        updateUI.setAlwaysOnTop(false);
                        setFocusableWindowState(true);
                        setAutoRequestFocus(true);
                        setEnabled(true);
                        requestFocus();
                    }
                });
            } else {
                JOptionPane.showMessageDialog(rootPanel, "Another window is already opened", "Some operation is in process already", JOptionPane.WARNING_MESSAGE);
                LocationTF.setText("");
            }
        });
        // add handlers
        addAuthor.addActionListener(e -> {
            if ((addUI == null || !addUI.isVisible()) & (updateUI == null || !updateUI.isVisible())) {
                addUI = new Add(this, dbManager, new String[]{"Name", "Description", "Date of Birth"}, 1, rootPanel);
                addUI.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        addUI.setAlwaysOnTop(false);
                        setFocusableWindowState(true);
                        setAutoRequestFocus(true);
                        setEnabled(true);
                        requestFocus();
                        showAuthors(true);
                    }
                });
            } else {
                JOptionPane.showMessageDialog(rootPanel, "Another window is already opened", "Some operation is in process already", JOptionPane.WARNING_MESSAGE);
                LocationTF.setText("");
            }
        });
        addArt.addActionListener(e -> {
            if ((addUI == null || !addUI.isVisible()) & (updateUI == null || !updateUI.isVisible())) {
                addUI = new Add(this, dbManager, new String[]{"Name", "Description", "Creation date", "Author", "Current owner", "Location"}, 2, rootPanel);
                addUI.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        addUI.setAlwaysOnTop(false);
                        setFocusableWindowState(true);
                        setAutoRequestFocus(true);
                        setEnabled(true);
                        requestFocus();
                        showArtObjects(true);
                    }
                });
            } else {
                JOptionPane.showMessageDialog(rootPanel, "Another window is already opened", "Some operation is in process already", JOptionPane.WARNING_MESSAGE);
                LocationTF.setText("");
            }
        });
        addEvent.addActionListener(e -> {
            if ((addUI == null || !addUI.isVisible()) & (updateUI == null || !updateUI.isVisible())) {
                addUI = new Add(this, dbManager, new String[]{"Name", "Type", "Description", "Start Date", "End Date", "Location", "Price"}, 3, rootPanel);
                addUI.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        addUI.setAlwaysOnTop(false);
                        setFocusableWindowState(true);
                        setAutoRequestFocus(true);
                        setEnabled(true);
                        requestFocus();
                        showEvents(true);
                    }
                });
            } else {
                JOptionPane.showMessageDialog(rootPanel, "Another window is already opened", "Some operation is in process already", JOptionPane.WARNING_MESSAGE);
                LocationTF.setText("");
            }
        });
        addLocation.addActionListener(e -> {
            if ((addUI == null || !addUI.isVisible()) & (updateUI == null || !updateUI.isVisible())) {
                addUI = new Add(this, dbManager, new String[]{"Name", "Description", "Opening date", "Placement", "Type"}, 4, rootPanel);
                addUI.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        addUI.setAlwaysOnTop(false);
                        setFocusableWindowState(true);
                        setAutoRequestFocus(true);
                        setEnabled(true);
                        requestFocus();
                        showLocations(true);
                    }
                });
            } else {
                JOptionPane.showMessageDialog(rootPanel, "Another window is already opened", "Some operation is in process already", JOptionPane.WARNING_MESSAGE);
                LocationTF.setText("");
            }
        });
        addOwner.addActionListener(e -> {
            if ((addUI == null || !addUI.isVisible()) & (updateUI == null || !updateUI.isVisible())) {
                addUI = new Add(this, dbManager, new String[]{"Name", "Description"}, 5, rootPanel);
                addUI.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        addUI.setAlwaysOnTop(false);
                        setFocusableWindowState(true);
                        setAutoRequestFocus(true);
                        setEnabled(true);
                        requestFocus();
                        showOwners(true);
                    }
                });
            } else {
                JOptionPane.showMessageDialog(rootPanel, "Another window is already opened", "Some operation is in process already", JOptionPane.WARNING_MESSAGE);
                LocationTF.setText("");
            }
        });
        addPurchase.addActionListener(e -> {
            if ((addUI == null || !addUI.isVisible()) & (updateUI == null || !updateUI.isVisible())) {
                addUI = new Add(this, dbManager, new String[]{"Date of purchase", "Price", "Art object", "Seller", "Buyer"}, 6, rootPanel);
                addUI.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        addUI.setAlwaysOnTop(false);
                        setFocusableWindowState(true);
                        setAutoRequestFocus(true);
                        setEnabled(true);
                        requestFocus();
                        showPurchases(true);
                    }
                });
            } else {
                JOptionPane.showMessageDialog(rootPanel, "Another window is already opened", "Some operation is in process already", JOptionPane.WARNING_MESSAGE);
                LocationTF.setText("");
            }
        });
        // delete handlers
        deleteAuthor.addActionListener(e -> {
            int selectedRow = AuthorTable.getSelectedRow();
            if (selectedRow != -1) {
                int idData = Integer.parseInt(AuthorTable.getValueAt(selectedRow, 0).toString());
                int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure to delete author with the following id: " + idData, "Confirm", JOptionPane.YES_NO_OPTION);
                if (dialogResult == JOptionPane.YES_OPTION) {
                    try {
                        dbManager.removeAuthor(idData);
                    } catch (Exception deletion) {
                        JOptionPane.showMessageDialog(rootPanel, "Object doesn't exist and the deletion could not be complete", "Not found", JOptionPane.WARNING_MESSAGE);
                        AuthorTable.clearSelection();
                    }
                    showAuthors(true);
                    AuthorTable.clearSelection();

                } else {
                    AuthorTable.clearSelection();
                }
            }
        });
        deleteArt.addActionListener(e -> {
            int selectedRow = ArtObjectsTable.getSelectedRow();
            if (selectedRow != -1) {
                int idData = Integer.parseInt(ArtObjectsTable.getValueAt(selectedRow, 0).toString());
                int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure to delete art object with the following id: " + idData, "Confirm", JOptionPane.YES_NO_OPTION);
                if (dialogResult == JOptionPane.YES_OPTION) {
                    try {
                        dbManager.removeArtObjectById(idData);
                    } catch (Exception deletion) {
                        JOptionPane.showMessageDialog(rootPanel, "Object doesn't exist and the deletion could not be complete", "Not found", JOptionPane.WARNING_MESSAGE);
                        AuthorTable.clearSelection();
                    }
                    ArtObjectsTable.clearSelection();
                    showArtObjects(true);
                } else {
                    ArtObjectsTable.clearSelection();
                }
            }
        });
        deleteEvent.addActionListener(e -> {
            int selectedRow = EventTable.getSelectedRow();
            if (selectedRow != -1) {
                int idData = Integer.parseInt(EventTable.getValueAt(selectedRow, 0).toString());
                int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure to delete event with the following id: " + idData, "Confirm", JOptionPane.YES_NO_OPTION);
                if (dialogResult == JOptionPane.YES_OPTION) {
                    try {
                        dbManager.removeEvent(idData);
                    } catch (Exception deletion) {
                        JOptionPane.showMessageDialog(rootPanel, "Object doesn't exist and the deletion could not be complete", "Not found", JOptionPane.WARNING_MESSAGE);
                        AuthorTable.clearSelection();
                    }
                    showEvents(true);
                    EventTable.clearSelection();
                } else {
                    EventTable.clearSelection();
                }
            }
        });
        deleteLocation.addActionListener(e -> {
            int selectedRow = LocationTable.getSelectedRow();
            if (selectedRow != -1) {
                int idData = Integer.parseInt(LocationTable.getValueAt(selectedRow, 0).toString());
                int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure to delete location with the following id: " + idData, "Confirm", JOptionPane.YES_NO_OPTION);
                if (dialogResult == JOptionPane.YES_OPTION) {
                    try {
                        dbManager.removeLocation(idData);
                    } catch (Exception deletion) {
                        JOptionPane.showMessageDialog(rootPanel, "Object doesn't exist and the deletion could not be complete", "Not found", JOptionPane.WARNING_MESSAGE);
                        AuthorTable.clearSelection();
                    }
                    showLocations(true);
                } else {
                    LocationTable.clearSelection();
                }
            }
        });
        deleteOwner.addActionListener(e -> {
            int selectedRow = OwnerTable.getSelectedRow();
            if (selectedRow != -1) {
                int idData = Integer.parseInt(OwnerTable.getValueAt(selectedRow, 0).toString());
                int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure to delete owner with the following id: " + idData, "Confirm", JOptionPane.YES_NO_OPTION);
                if (dialogResult == JOptionPane.YES_OPTION) {
                    try {
                        dbManager.removeOwner(idData);
                    } catch (Exception deletion) {
                        JOptionPane.showMessageDialog(rootPanel, "Object doesn't exist and the deletion could not be complete", "Not found", JOptionPane.WARNING_MESSAGE);
                        AuthorTable.clearSelection();
                    }
                    showOwners(true);
                } else {
                    OwnerTable.clearSelection();
                }
            }
        });
        deletePurchase.addActionListener(e -> {
            int selectedRow = PurchaseTable.getSelectedRow();
            if (selectedRow != -1) {
                int idData = Integer.parseInt(PurchaseTable.getValueAt(selectedRow, 0).toString());
                int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure to delete purchase with the following id: " + idData, "Confirm", JOptionPane.YES_NO_OPTION);
                if (dialogResult == JOptionPane.YES_OPTION) {
                    try {
                        dbManager.removePurchase(idData);
                    } catch (Exception deletion) {
                        JOptionPane.showMessageDialog(rootPanel, "Object doesn't exist and the deletion could not be complete", "Not found", JOptionPane.WARNING_MESSAGE);
                        AuthorTable.clearSelection();
                    }
                    showPurchases(true);
                } else {
                    PurchaseTable.clearSelection();
                }
            }
        });
    }

    // table handlers
    public void showAuthors(boolean refresh) {
        DefaultTableModel tableModel = (DefaultTableModel) AuthorTable.getModel();
        if (tableModel.getRowCount() == 0 | refresh) {
            tableModel.setRowCount(0);
            List<Author> showAuthors = dbManager.getAllAuthors();
            String[] columnNames = {"ID", "Name", "Description", "Date of birth"};
            tableModel.setColumnIdentifiers(columnNames);
            for (int i = 0; i < showAuthors.size(); i++) {
                Author author = showAuthors.get(i);
                Object[] rowData = new Object[]{author.getId(), author.getName(), author.getDescription(), author.getDateOfBirth()};
                tableModel.addRow(rowData);
            }
            tableModel.fireTableDataChanged();
        }
        TableColumnModel columnModel = AuthorTable.getColumnModel();
        TableColumn column = columnModel.getColumn(0);
        column.setMinWidth(40);
        column.setMaxWidth(40);
        AuthorTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrollableAreaAO(tableModel, AuthorTable);
    }
    public void showArtObjects(boolean refresh) {
    DefaultTableModel tableModel = (DefaultTableModel) ArtObjectsTable.getModel();

    if (tableModel.getRowCount() == 0 | refresh) {
        tableModel.setRowCount(0);

        List<ArtObject> artObjects = dbManager.getAllArtObjects();
        String[] columnNames = {"ID", "Name", "Description", "Creation date", "Author", "Current owner", "Location"};
        tableModel.setColumnIdentifiers(columnNames);
        for (int i = 0; i < artObjects.size(); i++) {
            ArtObject artObject = artObjects.get(i);
            Object[] rowData = new Object[]{artObject.getId(), artObject.getName(), artObject.getDescription(), artObject.getDateOfCreation(), artObject.getAuthor().getName(), artObject.getCurrentOwner().getName(), artObject.getCurrentLocation().getName()};
            tableModel.addRow(rowData);
        }
        TableColumnModel columnModel = ArtObjectsTable.getColumnModel();
        for (int i = 0; i < 5; i++) {
            TableColumn column = columnModel.getColumn(i);
            switch (i) {
                case 0: column.setMinWidth(30);
                        column.setMaxWidth(30);
                    break;
                case 1: column.setMinWidth(80);
                        column.setMaxWidth(80);
                    break;
                case 2: column.setMinWidth(150);
                        column.setMaxWidth(150);
                    break;
                case 3: column.setMinWidth(75);
                        column.setMaxWidth(75);
                    break;
                case 4: column.setMinWidth(120);
                        column.setMaxWidth(120);
                    break;
            }
        }

        tableModel.fireTableDataChanged();
        ArtObjectsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrollableAreaAO(tableModel, ArtObjectsTable);
    }
}
    public void showEvents(boolean refresh) {
        DefaultTableModel tableModel = (DefaultTableModel) EventTable.getModel();
        if (tableModel.getRowCount() == 0 | refresh) {
            tableModel.setRowCount(0);
            List<Event> showEvents = dbManager.getAllEvents();
            String[] columnNames = {"ID", "Name", "Type", "Description", "Start Date", "End Date", "Location", "Price"};
            tableModel.setColumnIdentifiers(columnNames);
            for (int i = 0; i < showEvents.size(); i++) {
                Event event = showEvents.get(i);
                Object[] rowData = new Object[]{event.getId(), event.getName(), event.getType(), event.getDescription(), event.getStartDateOfEvent(), event.getEndDateOfEvent(), event.getLocation().getName(), "€ "+event.getPrice()};
                tableModel.addRow(rowData);
            }
            tableModel.fireTableDataChanged();
        }
        TableColumnModel columnModel = EventTable.getColumnModel();
        TableColumn column = columnModel.getColumn(0);
        column.setMinWidth(30);
        column.setMaxWidth(30);
        column = columnModel.getColumn(4);
        column.setMinWidth(75);
        column.setMaxWidth(75);
        EventTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrollableAreaAO(tableModel, EventTable);

    }
    public void showLocations(boolean refresh) {
        DefaultTableModel tableModel = (DefaultTableModel) LocationTable.getModel();
        if (tableModel.getRowCount() == 0 | refresh) {
            tableModel.setRowCount(0);
            List<Location> showLocations = dbManager.getAllLocations();
            String[] columnNames = {"ID", "Name", "Description", "Opening date", "Placement", "Type"};
            tableModel.setColumnIdentifiers(columnNames);
            for (int i = 0; i < showLocations.size(); i++) {
                Location location = showLocations.get(i);
                Object[] rowData = new Object[]{location.getId(), location.getName(), location.getDescription(), location.getDateOfOpening(), location.getPlacement(), location.getType()};
                tableModel.addRow(rowData);
            }
            tableModel.fireTableDataChanged();
        }
        TableColumnModel columnModel = LocationTable.getColumnModel();
        TableColumn column = columnModel.getColumn(0);
        column.setMinWidth(30);
        column.setMaxWidth(30);
        column = columnModel.getColumn(3);
        column.setMinWidth(90);
        column.setMaxWidth(90);
        LocationTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrollableAreaAO(tableModel, LocationTable);
    }
    public void showOwners(boolean refresh) {
        DefaultTableModel tableModel = (DefaultTableModel) OwnerTable.getModel();
        if (tableModel.getRowCount() == 0 | refresh) {
            tableModel.setRowCount(0);
            List<Owner> showOwners = dbManager.getAllOwners();
            String[] columnNames = {"ID", "Name", "Description"};
            tableModel.setColumnIdentifiers(columnNames);
            for (int i = 0; i < showOwners.size(); i++) {
                Owner owner = showOwners.get(i);
                Object[] rowData = new Object[]{owner.getId(), owner.getName(), owner.getDescription()};
                tableModel.addRow(rowData);
            }
            tableModel.fireTableDataChanged();
        }
        TableColumnModel columnModel = OwnerTable.getColumnModel();
        TableColumn column = columnModel.getColumn(0);
        column.setMinWidth(30);
        column.setMaxWidth(30);
        OwnerTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrollableAreaAO(tableModel, OwnerTable);
    }
    public void showPurchases(boolean refresh) {
        DefaultTableModel tableModel = (DefaultTableModel) PurchaseTable.getModel();
        if (tableModel.getRowCount() == 0 | refresh) {
            tableModel.setRowCount(0);
            List<Purchase> showPurchases = dbManager.getAllPurchases();
            String[] columnNames = {"ID", "Date of purchase", "Price", "Art object", "Seller", "Buyer"};
            tableModel.setColumnIdentifiers(columnNames);
            for (int i = 0; i < showPurchases.size(); i++) {
                Purchase purchase = showPurchases.get(i);
                Object[] rowData = new Object[]{purchase.getId(),
                        purchase.getDateOfPurchase(), "€ "+purchase.getPrice(),
                        purchase.getArtObject() != null ? purchase.getArtObject().getName() : "",
                        purchase.getSeller() != null ? purchase.getSeller().getName() : "",
                        purchase.getBuyer() != null ? purchase.getBuyer().getName() : ""};
                tableModel.addRow(rowData);
            }
            tableModel.fireTableDataChanged();
        }
        TableColumnModel columnModel = PurchaseTable.getColumnModel();
        TableColumn column = columnModel.getColumn(0);
        column.setMinWidth(30);
        column.setMaxWidth(30);
        column = columnModel.getColumn(1);
        column.setMinWidth(90);
        column.setMaxWidth(90);
        PurchaseTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrollableAreaAO(tableModel, PurchaseTable);
    }
    public void showEventObjects(boolean refresh) {
        DefaultTableModel tableModel = (DefaultTableModel) EventTable.getModel();
        if (tableModel.getRowCount() == 0 | refresh) {
            tableModel.setRowCount(0);
            List<EventObjects> eventObjects = dbManager.getAllEventObjects();
            String[] columnNames = {"Event ID", "Name", "Type", "Description", "Art object id", "Art object name"};
            tableModel.setColumnIdentifiers(columnNames);
            int i = 0;
            for (EventObjects eventObject : eventObjects) {
                Event event = dbManager.getEventById(eventObject.getEventId());
                ArtObject artObject = dbManager.getArtObjectById(eventObject.getArtObjectId());
                Object[] rowData = new Object[]{event.getId(), event.getName(), event.getType(), event.getDescription(), artObject.getId(), artObject.getName()};
                tableModel.addRow(rowData);
            }
            tableModel.fireTableDataChanged();
        }
        refreshEvent.setText("Get back to events");
        refreshEvent.addActionListener(e -> {
            showEvents(true);
            refreshEvent.setText("Refresh table");
            EventCB.removeAllItems();
            EventCB.addItem(eventCBValues[0]);
            EventCB.addItem(eventCBValues[1]);
            eventObjectsButton.setText("Show event objects");
            deleteEvent.addActionListener(d -> {
                int selectedRow = EventTable.getSelectedRow();
                if (selectedRow != -1) {
                    int idData = Integer.parseInt(EventTable.getValueAt(selectedRow, 0).toString());
                    int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure to delete event with the following id: " + idData, "Confirm", JOptionPane.YES_NO_OPTION);
                    if (dialogResult == JOptionPane.YES_OPTION) {
                        try {
                            dbManager.removeEvent(idData);
                        } catch (Exception deletion) {
                            JOptionPane.showMessageDialog(rootPanel, "Object doesn't exist and the deletion could not be complete", "Not found", JOptionPane.WARNING_MESSAGE);
                            AuthorTable.clearSelection();
                        }
                        showEvents(true);
                        EventTable.clearSelection();
                    } else {
                        EventTable.clearSelection();
                    }
                }
            });
        });
        deleteEvent.addActionListener(d -> {
            int selectedRow = EventTable.getSelectedRow();
            if (selectedRow != -1) {
                int eventId = Integer.parseInt(EventTable.getValueAt(selectedRow, 0).toString());
                int artObjectId = Integer.parseInt(EventTable.getValueAt(selectedRow, 4).toString());
                int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure to delete event object with the following id: " + artObjectId, "Confirm", JOptionPane.YES_NO_OPTION);
                if (dialogResult == JOptionPane.YES_OPTION) {
                    try {
                        dbManager.deleteEventObject(eventId, artObjectId);
                    } catch (Exception deletion) {
                        JOptionPane.showMessageDialog(rootPanel, "Object doesn't exist and the deletion could not be complete", "Not found", JOptionPane.WARNING_MESSAGE);
                        EventTable.clearSelection();
                    }
                    showEventObjects(true);
                    EventTable.clearSelection();

                } else {
                    EventTable.clearSelection();
                }
            }
        });
        TableColumnModel columnModel = EventTable.getColumnModel();
        TableColumn column = columnModel.getColumn(0);
        column.setMinWidth(30);
        column.setMaxWidth(30);
        column = columnModel.getColumn(4);
        column.setMinWidth(75);
        column.setMaxWidth(75);
        EventTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrollableAreaAO(tableModel, EventTable);
    }
    public void scrollableAreaAO(DefaultTableModel tableModel, JTable table) {
        int rowCount = tableModel.getRowCount();
        int rowHeight = ArtObjectsTable.getRowHeight();
        int preferredHeight = rowCount * rowHeight;
        table.setPreferredSize(new Dimension(table.getPreferredSize().width, preferredHeight));
        tableModel.fireTableDataChanged();
    }
}
