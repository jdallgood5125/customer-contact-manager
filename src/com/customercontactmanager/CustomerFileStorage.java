// Developer: Joshua Allgood
// Date: September 13, 2026
// Purpose: Stores customer information for persistence.

package com.customercontactmanager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CustomerFileStorage {
    // Store the path to the customer data file.
    private final Path filePath;

    // Create storage using the supplied file name.
    public CustomerFileStorage(String fileName) {
        filePath = Paths.get(fileName);
    }

    // Check whether the customer data file already exists.
    public boolean dataFileExists() {
        return Files.exists(filePath);
    }

    // Load customer records from the CSV file.
    public List<Customer> loadCustomers() {
        List<Customer> customers = new ArrayList<>();

        if (!Files.exists(filePath)) {
            return customers;
        }

        // Read each CSV line and convert it into a Customer object.
        try {
            List<String> lines = Files.readAllLines(filePath);

            for (String line : lines) {
                String[] data = line.split(",", -1);

                if (data.length != 4) {
                    continue;
                }
                // Convert the saved ID from text to an integer.
                try {
                    int id = Integer.parseInt(data[0]);

                    customers.add(new Customer(
                            id,
                            data[1],
                            data[2],
                            data[3]
                    ));
                } catch (NumberFormatException e) {
                    System.out.println(
                            "Skipping invalid customer record."
                    );
                }
            }
        } catch (IOException e) {
            System.out.println("Could not load customer data.");
        }

        return customers;
    }

    // Save all customers to the CSV file.
    public void saveCustomers(List<Customer> customers) {
        List<String> lines = new ArrayList<>();

        // Convert each customer into one CSV record.
        for (Customer customer : customers) {
            lines.add(
                    customer.getId() + ","
                            + customer.getName() + ","
                            + customer.getEmail() + ","
                            + customer.getPhone()
            );
        }

        try {
            Files.write(filePath, lines);
        } catch (IOException e) {
            System.out.println("Could not save customer data.");
        }
    }
}