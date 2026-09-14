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
    private final Path filePath;

    public CustomerFileStorage(String fileName) {
        filePath = Paths.get(fileName);
    }

    public List<Customer> loadCustomers() {
        List<Customer> customers = new ArrayList<>();

        if (!Files.exists(filePath)) {
            return customers;
        }

        try {
            List<String> lines = Files.readAllLines(filePath);

            for (String line : lines) {
                String[] data = line.split(",", -1);

                if (data.length != 4) {
                    continue;
                }

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

    public void saveCustomers(List<Customer> customers) {
        List<String> lines = new ArrayList<>();

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