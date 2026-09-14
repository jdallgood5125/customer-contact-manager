// Developer: Joshua Allgood
// Date: September 14, 2026
// Purpose: Provides a graphical interface for managing customers.

package com.customercontactmanager;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;


public class SwingMain {
    // Manage customers and connect the table to saved data.
    private final CustomerManager manager;
    private final CustomerFileStorage storage;
    private final JTable customerTable;
    private final JTextField searchField;
    private TableRowSorter<DefaultTableModel> sorter;

    // Build the window and connect the buttons.
    public SwingMain() {
        storage = new CustomerFileStorage("customers.csv");
        manager = new CustomerManager();

        loadCustomers();

        customerTable = new JTable();
        customerTable.setSelectionMode(
                javax.swing.ListSelectionModel.SINGLE_SELECTION
        );
        customerTable.setRowHeight(28);
        refreshTable();

        searchField = new JTextField(25);

        // Filter the table whenever the search text changes.
        searchField.getDocument().addDocumentListener(
                new DocumentListener() {
                    public void insertUpdate(DocumentEvent event) {
                        filterTable();
                    }

                    public void removeUpdate(DocumentEvent event) {
                        filterTable();
                    }

                    public void changedUpdate(DocumentEvent event) {
                        filterTable();
                    }
                }
        );

        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton removeButton = new JButton("Remove");
        JButton refreshButton = new JButton("Refresh");

        addButton.addActionListener(event -> addCustomer());
        updateButton.addActionListener(event -> updateCustomer());
        removeButton.addActionListener(event -> removeCustomer());
        refreshButton.addActionListener(event -> refreshTable());

        JPanel searchPanel = new JPanel();
        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(refreshButton);

        JFrame frame = new JFrame("Customer Contact Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(searchPanel, BorderLayout.NORTH);
        frame.add(new JScrollPane(customerTable), BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setSize(850, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // Load saved customers and create starter data on the first run.
    private void loadCustomers() {
        List<Customer> savedCustomers = storage.loadCustomers();

        for (Customer customer : savedCustomers) {
            manager.addCustomer(customer);
        }

        if (!storage.dataFileExists()) {
            manager.addCustomer(new Customer(
                    1,
                    "John Smith",
                    "john.smith@email.com",
                    "555-0101"
            ));

            manager.addCustomer(new Customer(
                    2,
                    "Jane Doe",
                    "jane.doe@email.com",
                    "555-0102"
            ));

            storage.saveCustomers(manager.getCustomers());
        }
    }

    // Rebuild the table using the current customer list.
    private void refreshTable() {
        String[] columns = {"ID", "Name", "Email", "Phone"};
        DefaultTableModel model =
                new DefaultTableModel(columns, 0);

        for (Customer customer : manager.getCustomers()) {
            model.addRow(new Object[]{
                    customer.getId(),
                    customer.getName(),
                    customer.getEmail(),
                    customer.getPhone()
            });
        }

        customerTable.setModel(model);

        sorter = new TableRowSorter<>(model);
        customerTable.setRowSorter(sorter);
    }

    // Filter rows based on the search field.
    private void filterTable() {
        String text = searchField.getText().trim();

        if (text.isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(
                    RowFilter.regexFilter(
                            "(?i)" + java.util.regex.Pattern.quote(text)
                    )
            );
        }
    }

    // Display a structured form for adding a customer.
    private void addCustomer() {
        JTextField idField = new JTextField(25);
        JTextField nameField = new JTextField(25);
        JTextField emailField = new JTextField(25);
        JTextField phoneField = new JTextField(25);

        JPanel formPanel = new JPanel(new GridLayout(0, 2, 8, 8));

        formPanel.add(new JLabel("Customer ID:"));
        formPanel.add(idField);

        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameField);

        formPanel.add(new JLabel("Email:"));
        formPanel.add(emailField);

        formPanel.add(new JLabel("Phone:"));
        formPanel.add(phoneField);

        while (true) {
            int result = JOptionPane.showConfirmDialog(
                    null,
                    formPanel,
                    "Add Customer",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );

            if (result != JOptionPane.OK_OPTION) {
                return;
            }

            try {
                int id = Integer.parseInt(idField.getText().trim());
                String name = nameField.getText().trim();
                String email = emailField.getText().trim();
                String phone = phoneField.getText().trim();

                if (name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
                    showMessage("All fields are required.");
                    continue;
                }

                if (!isValidEmail(email)) {
                    showMessage("Please enter a valid email address.");
                    continue;
                }

                if (!isValidPhone(phone)) {
                    showMessage("Please enter a valid phone number.");
                    continue;
                }

                if (manager.findCustomerById(id) != null) {
                    showMessage("That customer ID already exists.");
                    continue;
                }

                boolean added = manager.addCustomer(
                        new Customer(id, name, email, phone)
                );

                if (added) {
                    storage.saveCustomers(manager.getCustomers());
                    refreshTable();
                    showMessage("Customer added.");
                }

                return;

            } catch (NumberFormatException e) {
                showMessage("Customer ID must be a number.");
            }
        }
    }
    // Check basic email formatting.
    private boolean isValidEmail(String email) {
        return email.contains("@") && email.contains(".");
    }

    // Check that the phone number contains at least seven digits.
    private boolean isValidPhone(String phone) {
        String digitsOnly = phone.replaceAll("\\D", "");
        return digitsOnly.length() >= 7;
    }

    // Display a structured form for updating a customer.
    private void updateCustomer() {
        int row = customerTable.getSelectedRow();

        if (row == -1) {
            showMessage("Select a customer first.");
            return;
        }

        int modelRow = customerTable.convertRowIndexToModel(row);
        int id = (int) customerTable.getModel().getValueAt(modelRow, 0);
        Customer customer = manager.findCustomerById(id);

        JTextField nameField = new JTextField(customer.getName(), 25);
        JTextField emailField = new JTextField(customer.getEmail(), 25);
        JTextField phoneField = new JTextField(customer.getPhone(), 25);

        JPanel formPanel = new JPanel(new GridLayout(0, 2, 8, 8));

        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameField);

        formPanel.add(new JLabel("Email:"));
        formPanel.add(emailField);

        formPanel.add(new JLabel("Phone:"));
        formPanel.add(phoneField);

        while (true) {
            int result = JOptionPane.showConfirmDialog(
                    null,
                    formPanel,
                    "Update Customer",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );

            if (result != JOptionPane.OK_OPTION) {
                return;
            }

            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String phone = phoneField.getText().trim();

            if (name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
                showMessage("All fields are required.");
                continue;
            }

            if (!isValidEmail(email)) {
                showMessage("Please enter a valid email address.");
                continue;
            }

            if (!isValidPhone(phone)) {
                showMessage("Please enter a valid phone number.");
                continue;
            }

            boolean updated = manager.updateCustomer(
                    id,
                    name,
                    email,
                    phone
            );

            if (updated) {
                storage.saveCustomers(manager.getCustomers());
                refreshTable();
                showMessage("Customer updated.");
            }

            return;
        }
    }

    // Remove the selected customer after confirmation.
    private void removeCustomer() {
        int row = customerTable.getSelectedRow();

        if (row == -1) {
            showMessage("Select a customer first.");
            return;
        }

        int modelRow = customerTable.convertRowIndexToModel(row);
        int id = (int) customerTable.getModel().getValueAt(modelRow, 0);

        int answer = JOptionPane.showConfirmDialog(
                null,
                "Remove this customer?",
                "Confirm Removal",
                JOptionPane.YES_NO_OPTION
        );

        if (answer == JOptionPane.YES_OPTION) {
            boolean removed = manager.removeCustomerById(id);

            if (removed) {
                storage.saveCustomers(manager.getCustomers());
                refreshTable();
                showMessage("Customer removed.");
            }
        }
    }

    // Read required text and reject blank input.
    private String readRequired(
            String prompt,
            String initialValue
    ) {
        while (true) {
            String input = JOptionPane.showInputDialog(
                    null,
                    prompt,
                    initialValue
            );

            if (input == null) {
                return null;
            }

            input = input.trim();

            if (!input.isEmpty()) {
                return input;
            }

            showMessage("This field cannot be blank.");
        }
    }

    // Validate an email address.
    private String readEmail(
            String prompt,
            String initialValue
    ) {
        while (true) {
            String email = readRequired(prompt, initialValue);

            if (email == null) {
                return null;
            }

            if (email.contains("@") && email.contains(".")) {
                return email;
            }

            showMessage("Please enter a valid email address.");
            initialValue = email;
        }
    }

    // Validate a phone number.
    private String readPhone(
            String prompt,
            String initialValue
    ) {
        while (true) {
            String phone = readRequired(prompt, initialValue);

            if (phone == null) {
                return null;
            }

            String digitsOnly = phone.replaceAll("\\D", "");

            if (digitsOnly.length() >= 7) {
                return phone;
            }

            showMessage("Please enter a valid phone number.");
            initialValue = phone;
        }
    }

    // Display a message dialog.
    private void showMessage(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    // Start the Swing application.
    public static void main(String[] args) {
        SwingUtilities.invokeLater(SwingMain::new);
    }
}