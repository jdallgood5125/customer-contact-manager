// Developer: Joshua Allgood
// Date: September 14, 2026
// Purpose: Tests customer management operations.

package com.customercontactmanager;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CustomerManagerTest {

    // Verify that a customer can be added and found by ID.
    @Test
    void addAndFindCustomer() {
        CustomerManager manager = new CustomerManager();
        Customer customer = new Customer(
                1,
                "John Smith",
                "john.smith@email.com",
                "555-0101"
        );

        assertTrue(manager.addCustomer(customer));
        assertNotNull(manager.findCustomerById(1));
        assertEquals("John Smith",
                manager.findCustomerById(1).getName());
    }

    // Verify that duplicate IDs are rejected.
    @Test
    void rejectDuplicateCustomerId() {
        CustomerManager manager = new CustomerManager();

        assertTrue(manager.addCustomer(
                new Customer(1, "John Smith",
                        "john.smith@email.com", "555-0101")
        ));

        assertFalse(manager.addCustomer(
                new Customer(1, "Jane Doe",
                        "jane.doe@email.com", "555-0102")
        ));
    }

    // Verify that customer information can be updated.
    @Test
    void updateCustomer() {
        CustomerManager manager = new CustomerManager();
        manager.addCustomer(new Customer(
                1,
                "John Smith",
                "john.smith@email.com",
                "555-0101"
        ));

        assertTrue(manager.updateCustomer(
                1,
                "John Updated",
                "updated@email.com",
                "555-0199"
        ));

        assertEquals(
                "John Updated",
                manager.findCustomerById(1).getName()
        );
    }

    // Verify that a customer can be removed.
    @Test
    void removeCustomer() {
        CustomerManager manager = new CustomerManager();
        manager.addCustomer(new Customer(
                1,
                "John Smith",
                "john.smith@email.com",
                "555-0101"
        ));

        assertTrue(manager.removeCustomerById(1));
        assertNull(manager.findCustomerById(1));
    }
}