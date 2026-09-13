package com.customercontactmanager;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CustomerManager manager = new CustomerManager();

        manager.addCustomer(new Customer( 1, "John Smith", "john.smith@email.com", "555-0101"));

        manager.addCustomer(new Customer(2, "Jane Doe", "jane.doe@email.com", "555-0102"));

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n Customer Manager");

            System.out.println("1. List Customers");
            System.out.println("2. Find Customer");
            System.out.println("3. Add Customer");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    for (Customer customer : manager.getCustomers()) {
                        System.out.println(customer);
                    }
                    break;

                case "2":
                    System.out.print("Enter Customer ID: ");
                    int id = Integer.parseInt(scanner.nextLine());

                    System.out.println(manager.findCustomerById(id));
                    break;

                case "3":
                    System.out.print("Enter Customer ID: ");
                    int newId = Integer.parseInt(scanner.nextLine());

                    System.out.print("Enter customer name: ");
                    String name = scanner.nextLine();

                    System.out.print("Enter customer email: ");
                    String email = scanner.nextLine();

                    System.out.print("Enter customer phone: ");
                    String phone = scanner.nextLine();

                    manager.addCustomer(new Customer(newId, name, email, phone));
                    System.out.println("Customer added.");
                    break;

                case "4":
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }

        scanner.close();
    }
}