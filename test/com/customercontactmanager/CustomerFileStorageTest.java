// Developer: Joshua Allgood
// Date: September 14, 2026
// Purpose: Tests saving and loading customer records.

package com.customercontactmanager;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomerFileStorageTest {

    // Use a temporary folder so the real customers.csv is not changed.
    @TempDir
    Path temporaryDirectory;

    // Verify that saved customers can be loaded again.
    @Test
    void saveAndLoadCustomers() {
        Path testFile =
                temporaryDirectory.resolve("customers-test.csv");

        CustomerFileStorage storage =
                new CustomerFileStorage(testFile.toString());

        List<Customer> customers = List.of(
                new Customer(
                        1,
                        "John Smith",
                        "john.smith@email.com",
                        "555-0101"
                ),
                new Customer(
                        2,
                        "Jane Doe",
                        "jane.doe@email.com",
                        "555-0102"
                )
        );

        storage.saveCustomers(customers);

        List<Customer> loadedCustomers =
                storage.loadCustomers();

        assertEquals(2, loadedCustomers.size());
        assertEquals(
                "John Smith",
                loadedCustomers.get(0).getName()
        );
        assertEquals(
                "jane.doe@email.com",
                loadedCustomers.get(1).getEmail()
        );
    }
}