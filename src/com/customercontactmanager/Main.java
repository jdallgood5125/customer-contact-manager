package com.customercontactmanager;

public class Main {
    public static void main(String[] args) {
        CustomerManager manager = new CustomerManager();

        manager.addCustomer( new Customer(1, "John Smith", "john.smith@email.com", "555-0101"));

        manager.addCustomer(new Customer(2, "Jane Doe", "jane.doe@email.com", "555-0102"));

        System.out.println("All customers:");

        for (Customer customer : manager.getCustomers()) {
            System.out.println(customer);
        }

        System.out.println("\nCustomer with ID 1:");
        System.out.println(manager.findCustomerById(1));
    }
}