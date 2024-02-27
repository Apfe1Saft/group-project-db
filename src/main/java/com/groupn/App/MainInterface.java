package com.groupn.App;

import com.groupn.database.DBManager;
import com.groupn.entities.Event;
import com.groupn.entities.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
    private JButton helpButton;
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
    private JButton eventTypes;
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
    private UpdateUI updateUI;
    private Add addUI;
    private final DBManager dbManager;

    public MainInterface(DBManager dbManager) {
        this.dbManager = dbManager;
        setContentPane(rootPanel);
        setTitle("MainInterface");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 500);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);


        tabbedPane1.addChangeListener(e -> {
            int selectedTabIndex = tabbedPane1.getSelectedIndex();
            // Call appropriate methods based on the selected tab index
            if (selectedTabIndex == 1) {
                showAuthors(false);
            } else if (selectedTabIndex == 2) {
                showArtObjects(false);
            } else if (selectedTabIndex == 3) {
                showEvents(false);
            } else if (selectedTabIndex == 4) {
                showLocations(false);
            } else if (selectedTabIndex == 5) {
                showOwners(false);
            } else if (selectedTabIndex == 6) {
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
                            DefaultTableModel tableModel = (DefaultTableModel) AuthorTable.getModel();
                            tableModel.setRowCount(0);
                            Object[] rowData = new Object[]{author.getId(), author.getName(), author.getDescription(), author.getDateOfBirth()};
                            tableModel.addRow(rowData);
                            tableModel.fireTableDataChanged();

                            break;
                        }
                    } catch (Exception r) {
                        AuthorTF.setText("");
                        JOptionPane.showMessageDialog(rootPanel, "Please, input integer value.", "Inappropriate input", JOptionPane.WARNING_MESSAGE);
                        break;
                    }
                case 1:
                    if (AuthorTF.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(rootPanel, "Please, input Art object id into a text field to execute search.", "No input", JOptionPane.WARNING_MESSAGE);
                    }
                    try {
                        if (Integer.parseInt(AuthorTF.getText()) > 0) {

                            int searchId = Integer.parseInt(AuthorTF.getText());
                            Author author = dbManager.getAuthorByArtObjectId(searchId);
                            DefaultTableModel tableModel = (DefaultTableModel) AuthorTable.getModel();
                            tableModel.setRowCount(0);
                            Object[] rowData = new Object[]{author.getId(), author.getName(), author.getDescription(), author.getDateOfBirth()};
                            tableModel.addRow(rowData);
                            tableModel.fireTableDataChanged();
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
            if (ArtTF.getText().isEmpty()) {
                JOptionPane.showMessageDialog(rootPanel, "Please, input id into a text field to execute search.", "No input", JOptionPane.WARNING_MESSAGE);
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
                        case 2: {
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
                        case 3: {
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
//                        case 4:
//                            TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(ArtObjectsTable.getModel());
//                            ArtObjectsTable.setRowSorter(rowSorter);
//                            updateFilter(rowSorter);

                }} catch (Exception a) {
                    ArtTF.setText("");
                    JOptionPane.showMessageDialog(rootPanel, "Please, input integer value.", "Inappropriate input", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        searchEvent.addActionListener(e -> {
            if (EventTF.getText().isEmpty()) {
                JOptionPane.showMessageDialog(rootPanel, "Please, input event id into a text field to execute search.", "No input", JOptionPane.WARNING_MESSAGE);
            } else {
                try {
                    if (Integer.parseInt(EventTF.getText()) > 0) {

                        int searchId = Integer.parseInt(EventTF.getText());
                        Event event = dbManager.getEventById(searchId);
                        if (event == null) {
                            JOptionPane.showMessageDialog(rootPanel, "Event not found.", "Not found", JOptionPane.WARNING_MESSAGE);
                        } else {
                            DefaultTableModel tableModel = (DefaultTableModel) EventTable.getModel();
                            tableModel.setRowCount(0);
                            Object[] rowData = new Object[]{event.getId(), event.getName(), event.getType(), event.getDescription(), event.getStartDateOfEvent(), event.getLocation(), event.getPrice()};
                            tableModel.addRow(rowData);
                            tableModel.fireTableDataChanged();
                        }
                    }
                } catch (Exception a) {
                    EventTF.setText("");
                    JOptionPane.showMessageDialog(rootPanel, "Please, input integer value.", "Inappropriate input", JOptionPane.WARNING_MESSAGE);
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
            if (LocationTF.getText().isEmpty()) {
                JOptionPane.showMessageDialog(rootPanel, "Please, input location id into a text field to execute search.", "No input", JOptionPane.WARNING_MESSAGE);
            } else {
                try {
                    if (Integer.parseInt(LocationTF.getText()) > 0) {

                        int searchId = Integer.parseInt(LocationTF.getText());
                        Location location = dbManager.getLocationById(searchId);
                        if (location == null) {
                            JOptionPane.showMessageDialog(rootPanel, "Location not found.", "Not found", JOptionPane.WARNING_MESSAGE);
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
                }
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
            if (OwnerTF.getText().isEmpty()) {
                JOptionPane.showMessageDialog(rootPanel, "Please, input owner's id into a text field to execute search.", "No input", JOptionPane.WARNING_MESSAGE);
            } else {
                try {
                    if (Integer.parseInt(OwnerTF.getText()) > 0) {

                        int searchId = Integer.parseInt(OwnerTF.getText());
                        Owner owner = dbManager.getOwnerById(searchId);
                        if (owner == null) {
                            JOptionPane.showMessageDialog(rootPanel, "Owner not found.", "Not found", JOptionPane.WARNING_MESSAGE);
                        } else {
                            DefaultTableModel tableModel = (DefaultTableModel) OwnerTable.getModel();
                            tableModel.setRowCount(0);
                            Object[] rowData = new Object[]{owner.getId(), owner.getName(), owner.getDescription()};
                            tableModel.addRow(rowData);
                            tableModel.fireTableDataChanged();
                        }
                    }
                } catch (Exception a) {
                    EventTF.setText("");
                    JOptionPane.showMessageDialog(rootPanel, "Please, input integer value.", "Inappropriate input", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        searchPurchase.addActionListener(e -> {
            if (PurchaseTF.getText().isEmpty()) {
                JOptionPane.showMessageDialog(rootPanel, "Please, input purchase id into a text field to execute search.", "No input", JOptionPane.WARNING_MESSAGE);
            } else {
                try {
                    if (Integer.parseInt(PurchaseTF.getText()) > 0) {

                        int searchId = Integer.parseInt(PurchaseTF.getText());
                        Purchase purchase = dbManager.getPurchaseId(searchId, dbManager);
                        if (purchase == null) {
                            JOptionPane.showMessageDialog(rootPanel, "Purchase not found.", "Not found", JOptionPane.WARNING_MESSAGE);
                        } else {
                            DefaultTableModel tableModel = (DefaultTableModel) PurchaseTable.getModel();
                            tableModel.setRowCount(0);
                            Object[] rowData = new Object[]{purchase.getId(), purchase.getDateOfPurchase(), purchase.getPrice(), purchase.getArtObject().getName(), purchase.getSeller().getName(), purchase.getBuyer().getName()};
                            tableModel.addRow(rowData);
                            tableModel.fireTableDataChanged();
                        }
                    }
                } catch (Exception a) {
                    EventTF.setText("");
                    JOptionPane.showMessageDialog(rootPanel, "Please, input integer value.", "Inappropriate input", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        // update handlers
        updateAuthor.addActionListener(e -> {
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
        });
        updateArt.addActionListener(e -> {
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
        });
        updateEvent.addActionListener(e -> {
            updateUI = new UpdateUI(this, dbManager, new String[]{"ID", "Name", "Type", "Description", "Start Date", "Location", "Price"}, 3, rootPanel);
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
        });
        updateLocation.addActionListener(e -> {
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
        });
        updateOwner.addActionListener(e -> {
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
        });
        updatePurchase.addActionListener(e -> {
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
        });
        // add handlers
        addAuthor.addActionListener(e -> {
            addUI = new Add(this, dbManager, new String[]{"Name", "Description", "Date of Birth"}, 1, rootPanel);
            addUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    addUI.setAlwaysOnTop(false);
                    setFocusableWindowState(true);
                    setAutoRequestFocus(true);
                    setEnabled(true);
                    requestFocus();
                }
            });
        }); // Need to set date feature unnecessary
        addArt.addActionListener(e -> {
            addUI = new Add(this, dbManager, new String[]{"Name", "Description", "Creation date", "Author", "Current owner", "Location"}, 2, rootPanel);
            addUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    addUI.setAlwaysOnTop(false);
                    setFocusableWindowState(true);
                    setAutoRequestFocus(true);
                    setEnabled(true);
                    requestFocus();
                }
            });
        }); // Need to set date feature unnecessary
        addEvent.addActionListener(e -> {
//          //addUI = new Add(this, dbManager, new String[]{"Name", "Type", "Description", "Start Date", "End Date", "Location", "Price"}, 3, rootPanel);
            addUI = new Add(this, dbManager, new String[]{"Name", "Type", "Description", "Start Date", "Location", "Price"}, 3, rootPanel);
            addUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    addUI.setAlwaysOnTop(false);
                    setFocusableWindowState(true);
                    setAutoRequestFocus(true);
                    setEnabled(true);
                    requestFocus();
                }
            });
        });
        addLocation.addActionListener(e -> {
            addUI = new Add(this, dbManager, new String[]{"Name", "Description", "Opening date", "Placement", "Type"}, 4, rootPanel);
            addUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    addUI.setAlwaysOnTop(false);
                    setFocusableWindowState(true);
                    setAutoRequestFocus(true);
                    setEnabled(true);
                    requestFocus();
                }
            });
        });
        addOwner.addActionListener(e -> {
            addUI = new Add(this, dbManager, new String[]{"Name", "Description"}, 5, rootPanel);
            addUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    addUI.setAlwaysOnTop(false);
                    setFocusableWindowState(true);
                    setAutoRequestFocus(true);
                    setEnabled(true);
                    requestFocus();
                }
            });
        });
        addPurchase.addActionListener(e -> {
            addUI = new Add(this, dbManager, new String[]{"Date of purchase", "Price", "Art object", "Seller", "Buyer"}, 6, rootPanel);
            addUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    addUI.setAlwaysOnTop(false);
                    setFocusableWindowState(true);
                    setAutoRequestFocus(true);
                    setEnabled(true);
                    requestFocus();
                }
            });
        });
        // delete handlers
        deleteAuthor.addActionListener(e -> {
            int selectedRow = AuthorTable.getSelectedRow();
            if (selectedRow != -1) {
                int idData = Integer.parseInt(AuthorTable.getValueAt(selectedRow, 0).toString());
                int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure to delete author with the following id: " + idData, "Confirm", JOptionPane.YES_NO_OPTION);
                if (dialogResult == JOptionPane.YES_OPTION) {
//                    dbManager.removeAuthor(idData);
                    showAuthors(true);
                    AuthorTable.clearSelection();
                } else {
                    AuthorTable.clearSelection();
                }
            }
        }); // Delete Author feature - waiting
        deleteArt.addActionListener(e -> {
            int selectedRow = ArtObjectsTable.getSelectedRow();
            if (selectedRow != -1) {
                int idData = Integer.parseInt(ArtObjectsTable.getValueAt(selectedRow, 0).toString());
                int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure to delete art object with the following id: " + idData, "Confirm", JOptionPane.YES_NO_OPTION);
                if (dialogResult == JOptionPane.YES_OPTION) {
                    dbManager.removeArtObjectById(idData);
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
//                    dbManager.removeEvent(idData);
                    showEvents(true);
                    EventTable.clearSelection();
                } else {
                    EventTable.clearSelection();
                }
            }
        }); // Delete Event feature - waiting
        deleteLocation.addActionListener(e -> {
            int selectedRow = LocationTable.getSelectedRow();
            if (selectedRow != -1) {
                int idData = Integer.parseInt(LocationTable.getValueAt(selectedRow, 0).toString());
                int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure to delete location with the following id: " + idData, "Confirm", JOptionPane.YES_NO_OPTION);
                if (dialogResult == JOptionPane.YES_OPTION) {
//                    dbManager.removeLocation(idData);
                    showLocations(true);
                } else {
                    LocationTable.clearSelection();
                }
            }
        }); // Delete Location feature - waiting
        deleteOwner.addActionListener(e -> {
            int selectedRow = OwnerTable.getSelectedRow();
            if (selectedRow != -1) {
                int idData = Integer.parseInt(OwnerTable.getValueAt(selectedRow, 0).toString());
                int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure to delete owner with the following id: " + idData, "Confirm", JOptionPane.YES_NO_OPTION);
                if (dialogResult == JOptionPane.YES_OPTION) {
//                    dbManager.removeOwner(idData);
                    showOwners(true);
                } else {
                    OwnerTable.clearSelection();
                }
            }
        }); // Delete owner feature - waiting
        deletePurchase.addActionListener(e -> {
            int selectedRow = PurchaseTable.getSelectedRow();
            if (selectedRow != -1) {
                int idData = Integer.parseInt(PurchaseTable.getValueAt(selectedRow, 0).toString());
                int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure to delete purchase with the following id: " + idData, "Confirm", JOptionPane.YES_NO_OPTION);
                if (dialogResult == JOptionPane.YES_OPTION) {
//                    dbManager.removePurchase(idData);
                    showPurchases(true);
                } else {
                    PurchaseTable.clearSelection();
                }
            }
        }); // Remove purchase feature - waiting
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
        // Row sorter reset (if there would be a row sorter eventually)
//        if (ArtObjectsTable.getRowSorter() != null) {
//            System.out.println("Row sorter found");
//            ArtObjectsTable.setRowSorter(null);
//        }

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
            String[] columnNames = {"ID", "Name", "Type", "Description", "Start Date", "Location", "Price"};
            tableModel.setColumnIdentifiers(columnNames);
            for (int i = 0; i < showEvents.size(); i++) {
                Event event = showEvents.get(i);
                Object[] rowData = new Object[]{event.getId(), event.getName(), event.getType(), event.getDescription(), event.getStartDateOfEvent(), event.getLocation().getName(), "€ "+event.getPrice()};
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
                Object[] rowData = new Object[]{purchase.getId(), purchase.getDateOfPurchase(), "€ "+purchase.getPrice(), purchase.getArtObject().getName(), purchase.getSeller().getName(), purchase.getBuyer().getName()};
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
    public void scrollableAreaAO(DefaultTableModel tableModel, JTable table) {
        int rowCount = tableModel.getRowCount();
        int rowHeight = ArtObjectsTable.getRowHeight();
        int preferredHeight = rowCount * rowHeight;
        table.setPreferredSize(new Dimension(table.getPreferredSize().width, preferredHeight));
        tableModel.fireTableDataChanged();
    }

    // Next is the filter on column, but for now let's leave it as it is
//    private void updateFilter(TableRowSorter<TableModel> rowSorter) {
//        String text = ArtTF.getText();
//        if (text.trim().length() == 0) {
//            rowSorter.setRowFilter(null);
//            JOptionPane.showMessageDialog(rootPanel, "Please, input word to find.", "Inappropriate input", JOptionPane.WARNING_MESSAGE);
//        } else {
//            try {
//                Integer.parseInt(text);
//                JOptionPane.showMessageDialog(rootPanel, "Please, input word to find.", "Inappropriate input", JOptionPane.WARNING_MESSAGE);
//            } catch (Exception e) {
//                int[] columnsForFilter = {1,2,4,5,6};
//                RowFilter<Object, Object>
//                rowSorter.setRowFilter(RowFilter.regexFilter("(?i)"+text));
//            }
//        }
//    }
}