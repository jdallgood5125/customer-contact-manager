package com.customercontactmanager;

import java.util.ArrayList;
import java.util.List;

public class CustomerManager {
    private final List<Customer> customers = new ArrayList<>();

    public boolean addCustomer(Customer customer){
        if (findCustomerById(customer.getId()) != null){
            return false;
        }

        customers.add(customer);
        return true;
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
