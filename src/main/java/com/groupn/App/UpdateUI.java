package com.groupn.App;

import com.groupn.database.DBManager;
import com.groupn.entities.Event;
import com.groupn.entities.*;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class UpdateUI extends JDialog {
    private final MainInterface mainInterface;
    private final DBManager dbManager;
    private final String[] columnNames;
    private final int selectedTabIndex;
    private final JPanel rootPane;

    private JPanel contentPane;
    private JButton confirmUpdate;
    private JButton cancelUpdate;
    private JPanel buttons;
    private JPanel TFPanel;
    private static ArrayList<JLabel> labels = null;

    public UpdateUI(MainInterface mainInterface, DBManager dbManager, String[] columnNames, int selectedTabIndex, JPanel rootPane) {
        this.mainInterface = mainInterface;
        this.dbManager = dbManager;
        this.columnNames = columnNames;
        this.selectedTabIndex = selectedTabIndex;
        this.rootPane = rootPane;
        labels = new ArrayList<>();
        setContentPane(contentPane);
        setTitle("Update data");
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // Close dialog on window close
        TFPanel.setLayout(new GridLayout(0, 3));
        ArrayList<JTextField> textFields = createFields(columnNames);
        setResizable(false);
        pack();
        setAlwaysOnTop(true);
        mainInterface.setFocusableWindowState(false);
        mainInterface.setAutoRequestFocus(false);
        mainInterface.setEnabled(false);
        confirmUpdate.addActionListener(e -> {
            boolean allFilled = false;
            for (JTextField textField : textFields) {
                if (textField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(TFPanel, "Please, do not leave text fields empty. Those are required to update data.\n" +
                            "To refresh data push \"Find\" button and continue editing.", "Lack of data", JOptionPane.WARNING_MESSAGE);
                    allFilled = false;
                    break;
                } else {
                    allFilled = true;
                }
            }
            if (allFilled) {
                switch (selectedTabIndex) {
                    case 2:
                        ArtObject artObject = new ArtObject();
                        try {
                            artObject.setId(Integer.parseInt(textFields.get(0).getText()));
                        } catch (Exception i) {
                            JOptionPane.showMessageDialog(TFPanel, "Please, input id as integer value.", "Incorrect format", JOptionPane.WARNING_MESSAGE);
                            break;
                        }
                        artObject.setName(textFields.get(1).getText());
                        artObject.setDescription(textFields.get(2).getText());
                        String dateString = textFields.get(3).getText();
                        try {
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                            LocalDate dateOfCreation = LocalDate.parse(dateString, formatter);
                            artObject.setDateOfCreation(dateOfCreation);
                        } catch (DateTimeParseException p) {
                            JOptionPane.showMessageDialog(TFPanel, "Please, input date in the form YYYY-MM-DD", "Incorrect format", JOptionPane.WARNING_MESSAGE);
                            break;
                        }
                        artObject.setAuthor(dbManager.getAuthorById(Integer.parseInt(textFields.get(4).getText())));
                        artObject.setCurrentOwner(dbManager.getOwnerById(Integer.parseInt(textFields.get(5).getText())));
                        artObject.setCurrentLocation(dbManager.getLocationById(Integer.parseInt(textFields.get(6).getText())));
                        dbManager.updateArtObject(artObject);
                        mainInterface.showArtObjects(true);
                }
            }

        });

    }

    public ArrayList<JTextField> createFields(String[] columnNames) {
        boolean isFirstField = true;
        ArrayList<JTextField> textFields= new ArrayList<>();
        for (String columnName : columnNames) {
            JTextField textField = new JTextField();
            textField.setSize(100, 50);
            textField.setPreferredSize(new Dimension(100, 30));
            textField.setMaximumSize(new Dimension(100, 30));
            textField.setName(columnName);
            textField.setAlignmentX(JTextField.LEFT_ALIGNMENT);
            textField.setHorizontalAlignment(JTextField.LEFT);
            JLabel label = new JLabel("<html>"+columnName+"<html>");
            label.setMinimumSize(new Dimension(200, 30));
            label.setMaximumSize(new Dimension(200, 30));
            if (columnName.equals("Author") || columnName.equals("Current owner") || columnName.equals("Location")) {
                if (columnName.equals("Author")) {
                    label.setText("<html>Initial author:<html>");
                } else if (columnName.equals("Current owner")) {
                    label.setText("<html>Initial owner:<html>");
                } else {
                    label.setText("<html>Initial location:<html>");
                }
                labels.add(label);
            }
            TFPanel.add(textField);
            TFPanel.add(label);
            textFields.add(textField);

            if (isFirstField) {
                JButton find = new JButton();
                find.setText("Find");
                find.setMaximumSize(new Dimension(100, 30));
                find.setMinimumSize(new Dimension(100, 30));
                TFPanel.add(find);
                isFirstField = false;
                find.addActionListener(e -> {
                    JTextField idTextField = (JTextField) TFPanel.getComponent(0);
                    try {
                        int id = Integer.parseInt(idTextField.getText());
                        inputFoundData(id, idTextField, textFields);
                    } catch (Exception a){
                        JOptionPane.showMessageDialog(TFPanel, "Please, input author id into a text field to execute search.", "No input", JOptionPane.WARNING_MESSAGE);
                    }
                });
            } else {
                TFPanel.add(new JPanel());
            }
        }
        return textFields;
    }

    public void inputFoundData(int id, JTextField idField, ArrayList<JTextField> textFields) {
        try {
            switch (selectedTabIndex) {
                case 1:
                    Author author = new Author(); // not reduntant
                    author = dbManager.getAuthorById(id);

                    if (author.getName() == null) {
                        idField.setText("ID not found.");
                    } else {
                        ArrayList<Method> methods = new ArrayList<>();
                        methods.add(author.getClass().getMethod("getName"));
                        methods.add(author.getClass().getMethod("getDescription"));
                        methods.add(author.getClass().getMethod("getDateOfBirth"));
                        int i = 0;
                        while (i < 3) {
                            JTextField textField = textFields.get(i+1);
                            Method method = methods.get(i);
                            Object value = method.invoke(author);
                            textField.setText(value == null ? "" : value.toString());
                            textField.setCaretPosition(0);
                            i++;
                        }
                    }
                    break;
                case 2:
                    ArtObject artObject = new ArtObject(); // not reduntant
                    artObject = dbManager.getArtObjectById(id);

                    if (artObject.getName() == null) {
                        idField.setText("ID not found.");
                    } else {
                        ArrayList<Method> methods = new ArrayList<>();
                        methods.add(artObject.getClass().getMethod("getId"));
                        methods.add(artObject.getClass().getMethod("getName"));
                        methods.add(artObject.getClass().getMethod("getDescription"));
                        methods.add(artObject.getClass().getMethod("getDateOfCreation"));
                        methods.add(artObject.getClass().getMethod("getAuthor"));
                        methods.add(artObject.getClass().getMethod("getCurrentOwner"));
                        methods.add(artObject.getClass().getMethod("getCurrentLocation"));
                        int i = 1;
                        int j = 0;
                        String labelAdd;
                        while (i < 7) {
                            JTextField textField = textFields.get(i);
                            Method method = methods.get(i);
                            Object value = method.invoke(artObject);
                            if (value != null) {
                                if (method.getName().equals("getAuthor") || method.getName().equals("getCurrentOwner") || method.getName().equals("getCurrentLocation")) {
                                    Method getIdMethod = value.getClass().getMethod("getId");
                                    Method getNameMethod = value.getClass().getMethod("getName");
                                    int id2 = (int) getIdMethod.invoke(value);
                                    textField.setText(Integer.toString(id2));
                                    switch (j) {
                                        case 0:
                                            labelAdd = (String) getNameMethod.invoke(value);
                                            labels.get(j).setText("<html>Initial author: "+labelAdd+"<html>");
                                            break;
                                        case 1:
                                            labelAdd = (String) getNameMethod.invoke(value);
                                            labels.get(j).setText("<html>Initial owner: "+labelAdd+"<html>");
                                            break;
                                        case 2:
                                            labelAdd = (String) getNameMethod.invoke(value);
                                            labels.get(j).setText("<html>Initial location: "+labelAdd+"<html>");
                                            break;
                                    }
                                    j++;
                                } else {
                                    textField.setText(value.toString());
                                }
                                textField.setCaretPosition(0);
                            } else {
                                textField.setText("");
                            }
                            i++;
                        }
                    }
                    break;
                case 3:
                    Event event = new Event(); // not reduntant
                    event = dbManager.getEventById(id);

                    if (event.getName() == null) {
                        idField.setText("ID not found.");
                    } else {
                        ArrayList<Method> methods = new ArrayList<>();
                        methods.add(event.getClass().getMethod("getId"));
                        methods.add(event.getClass().getMethod("getName"));
                        methods.add(event.getClass().getMethod("getType"));
                        methods.add(event.getClass().getMethod("getDescription"));
                        methods.add(event.getClass().getMethod("getStartDateOfEvent"));
                        methods.add(event.getClass().getMethod("getLocation"));
                        methods.add(event.getClass().getMethod("getPrice"));
                        int i = 1;
                        while (i < 7) {
                            JTextField textField = textFields.get(i+1);
                            Method method = methods.get(i);
                            Object value = method.invoke(event);
                            if (value != null) {
                                if (method.getName().equals("getCurrentLocation")) {
                                    // Get the nested object's name
                                    Method getNameMethod = value.getClass().getMethod("getId");
                                    int id2 = (int) getNameMethod.invoke(value);
                                    textField.setText(Integer.toString(id2));
                                } else {
                                    // Handle other data types (e.g., DateOfCreation)
                                    textField.setText(value.toString());
                                }
                                textField.setCaretPosition(0);
                            } else {
                                textField.setText("");
                            }
                            i++;
                        }
                    }
                    break;
                case 4:
                    Location location = new Location(); // not reduntant
                    location = dbManager.getLocationById(id);
                    if (location.getName() == null) {
                        idField.setText("ID not found.");
                    } else {
                        ArrayList<Method> methods = new ArrayList<>();
                        methods.add(location.getClass().getMethod("getName"));
                        methods.add(location.getClass().getMethod("getDescription"));
                        methods.add(location.getClass().getMethod("getDateOfOpening"));
                        methods.add(location.getClass().getMethod("getPlacement"));
                        methods.add(location.getClass().getMethod("getType"));
                        int i = 0;
                        while (i < 5) {
                            JTextField textField = textFields.get(i+1);
                            Method method = methods.get(i);
                            Object value = method.invoke(location);
                            if (value != null) {
                                textField.setText(value.toString());
                                textField.setCaretPosition(0);
                            } else {
                                textField.setText("");
                            }
                            i++;
                        }
                    }
                    break;
                case 5:
                    Owner owner = new Owner(); // not reduntant
                    owner = dbManager.getOwnerById(id);

                    if (owner.getName() == null) {
                        idField.setText("ID not found.");
                    } else {
                        ArrayList<Method> methods = new ArrayList<>();
                        methods.add(owner.getClass().getMethod("getName"));
                        methods.add(owner.getClass().getMethod("getDescription"));
                        int i = 0;
                        while (i < 2) {
                            JTextField textField = textFields.get(i+1);
                            Method method = methods.get(i);
                            Object value = method.invoke(owner);
                            if (value != null) {
                                textField.setText(value.toString());
                                textField.setCaretPosition(0);
                            } else {
                                textField.setText("");
                            }
                            i++;
                        }
                    }
                    break;
                case 6:
                    Purchase purchase = new Purchase(); // not reduntant
                    purchase = dbManager.getPurchaseId(id, dbManager);

                    if (purchase.getDateOfPurchase() == null) {
                        idField.setText("ID not found.");
                    } else {
                        ArrayList<Method> methods = new ArrayList<>();
                        methods.add(purchase.getClass().getMethod("getId"));
                        methods.add(purchase.getClass().getMethod("getDateOfPurchase"));
                        methods.add(purchase.getClass().getMethod("getPrice"));
                        methods.add(purchase.getClass().getMethod("getArtObject"));
                        methods.add(purchase.getClass().getMethod("getSeller"));
                        methods.add(purchase.getClass().getMethod("getBuyer"));
                        int i = 1;
                        while (i < 6) {
                            JTextField textField = textFields.get(i+1);
                            Method method = methods.get(i);
                            Object value = method.invoke(purchase);
                            if (value != null) {
                                if (method.getName().equals("getArtObject") || method.getName().equals("getSeller") || method.getName().equals("getBuyer")) {
                                    // Get the nested object's name
                                    Method getNameMethod = value.getClass().getMethod("getId");
                                    int id2 = (int) getNameMethod.invoke(value);
                                    textField.setText(Integer.toString(id2));
                                } else {
                                    // Handle other data types (e.g., DateOfCreation)
                                    textField.setText(value.toString());
                                }
                                textField.setCaretPosition(0);
                            } else {
                                textField.setText("");
                            }
                            i++;
                        }
                    }
                    break;
            }
        } catch (Exception e) {
            for(JTextField textField : textFields) {
                textField.setText("");
            }
            switch (selectedTabIndex) {
                case 1:
                    idField.setText("Author ID not found...");
                    break;
                case 2:
                    idField.setText("Art ID not found...");
                    break;
                case 3:
                    idField.setText("Event ID not found...");
                    break;
                case 4:
                    idField.setText("Location ID not found...");
                    break;
                case 5:
                    idField.setText("Owner ID not found...");
                    break;
                case 6:
                    idField.setText("Purchase ID not found...");
                    break;
            }
        }
    }
}

