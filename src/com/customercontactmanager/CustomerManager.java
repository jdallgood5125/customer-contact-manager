// Developer: Joshua Allgood
// Date: September 13, 2026
// Purpose: Manages the collection of customers and provides CRUD operations.
package com.customercontactmanager;

import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

public class CustomerManager {
    // Store customers in an in-memory list.
    private final List<Customer> customers = new ArrayList<>();

    // Add a customer only when the ID is not already in use.
    public boolean addCustomer(Customer customer) {
        if (findCustomerById(customer.getId()) != null) {
            return false;
        }
        customers.add(customer);
        return true;
    }

    // Return the current customer list.
    public List<Customer> getCustomers() {
        customers.sort(Comparator.comparingInt(Customer::getId));
        return customers;
    }

    // Find a customer by ID.
    public Customer findCustomerById(int id) {
        for (Customer customer : customers) {
            if (customer.getId() == id) {
                return customer;
            }
        }
        return null;
    }

    // Remove a customer by ID.
    public boolean removeCustomerById(int id) {
        Customer customer = findCustomerById(id);

        if (customer != null) {
            customers.remove(customer);
            return true;
        }
        return false;
    }

    // Update the contact information for an existing customer.
    public boolean updateCustomer(int id, String name, String email, String phone) {
        Customer customer = findCustomerById(id);

        if (customer != null) {
            customer.setName(name);
            customer.setEmail(email);
            customer.setPhone(phone);
            return true;
        }
        return false;
    }
}
