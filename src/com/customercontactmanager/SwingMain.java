// Developer: Joshua Allgood
// Date: September 13, 2026
// Purpose: Provides a graphical interface for managing customers.

package com.customercontactmanager;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.util.List;

public class SwingMain {
    // Manage customer records and save them to the CSV file.
    private final CustomerManager manager;
    private final CustomerFileStorage storage;
    private final JTable customerTable;

    // Build the window and connect the buttons to their actions.
    public SwingMain() {
        storage = new CustomerFileStorage("customers.csv");
        manager = new CustomerManager();

        // Load existing customers when the program starts.
        loadCustomers();

        // Create the customer table.
        customerTable = new JTable();
        refreshTable();

        // Create the action buttons.
        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton removeButton = new JButton("Remove");
        JButton refreshButton = new JButton("Refresh");

        // Connect each button to its method.
        addButton.addActionListener(event -> addCustomer());
        updateButton.addActionListener(event -> updateCustomer());
        removeButton.addActionListener(event -> removeCustomer());
        refreshButton.addActionListener(event -> refreshTable());

        // Place the buttons in a panel.
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(refreshButton);

        // Create and display the application window.
        JFrame frame = new JFrame("Customer Contact Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(new JScrollPane(customerTable), BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setSize(750, 400);
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

    // Refresh the table using the current customer list.
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
    }

    // Add a new customer after validating the input.
    private void addCustomer() {
        String idInput = readRequired(
                "Enter customer ID:",
                null
        );

        if (idInput == null) {
            return;
        }

        int id;

        try {
            id = Integer.parseInt(idInput);
        } catch (NumberFormatException e) {
            showMessage("Please enter a valid numeric ID.");
            return;
        }

        if (manager.findCustomerById(id) != null) {
            showMessage("That customer ID already exists.");
            return;
        }

        String name = readRequired(
                "Enter customer name:",
                null
        );

        if (name == null) {
            return;
        }

        String email = readEmail(
                "Enter customer email:",
                null
        );

        if (email == null) {
            return;
        }

        String phone = readPhone(
                "Enter customer phone:",
                null
        );

        if (phone == null) {
            return;
        }

        boolean added = manager.addCustomer(
                new Customer(id, name, email, phone)
        );

        if (added) {
            storage.saveCustomers(manager.getCustomers());
            refreshTable();
            showMessage("Customer added.");
        }
    }

    // Update the selected customer after validating the input.
    private void updateCustomer() {
        int row = customerTable.getSelectedRow();

        if (row == -1) {
            showMessage("Select a customer first.");
            return;
        }

        int id = (int) customerTable.getValueAt(row, 0);
        Customer customer = manager.findCustomerById(id);

        String name = readRequired(
                "Enter new name:",
                customer.getName()
        );

        if (name == null) {
            return;
        }

        String email = readEmail(
                "Enter new email:",
                customer.getEmail()
        );

        if (email == null) {
            return;
        }

        String phone = readPhone(
                "Enter new phone:",
                customer.getPhone()
        );

        if (phone == null) {
            return;
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
    }

    // Remove the selected customer after confirmation.
    private void removeCustomer() {
        int row = customerTable.getSelectedRow();

        if (row == -1) {
            showMessage("Select a customer first.");
            return;
        }

        int id = (int) customerTable.getValueAt(row, 0);

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

    // Read and validate an email address.
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

    // Read and validate a phone number.
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

    // Display a message to the user.
    private void showMessage(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    // Start the Swing interface on the event-dispatch thread.
    public static void main(String[] args) {
        SwingUtilities.invokeLater(SwingMain::new);
    }
}