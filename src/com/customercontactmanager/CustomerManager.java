package com.customercontactmanager;

import java.util.ArrayList;
import java.util.List;

public class CustomerManager {
    private final List<Customer> customers = new ArrayList<>();

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public Customer findCustomerById(int id) {
        for (Customer customer : customers) {
            if (customer.getId() == id) {
                return customer;
            }
        }
        return null;
    }

    public boolean removeCustomerById(int id) {
        Customer customer = findCustomerById(id);

        if (customer != null) {
            customers.remove(customer);
            return true;
        }

        return false;
    }
}
