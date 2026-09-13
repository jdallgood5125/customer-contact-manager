// Developer: Joshua Allgood
// Date: September 13, 2026
// Purpose: Runs the customer contact manager and handles user input.

package com.customercontactmanager;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Create the manager and load the initial sample customers
        CustomerManager manager = new CustomerManager();

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

        // Set up user input and the menu loop.
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        // Continue displaying the menu until the user chooses Exit.
        while (running) {
            System.out.println("\nCustomer Contact Manager");
            System.out.println("1. List Customers");
            System.out.println("2. Find Customer");
            System.out.println("3. Add Customer");
            System.out.println("4. Update Customer");
            System.out.println("5. Remove Customer");
            System.out.println("6. Exit");

            String choice = readText(scanner, "Choose an option: ");

            switch (choice) {
                // Display all stored customers.
                case "1":
                    if (manager.getCustomers().isEmpty()) {
                        System.out.println("No customers found.");
                    } else {
                        for (Customer customer : manager.getCustomers()) {
                            System.out.println(customer);
                        }
                    }
                    break;
                // Find and display one customer by ID.
                case "2":
                    int findId = readInt(
                            scanner,
                            "Enter customer ID: "
                    );

                    Customer foundCustomer =
                            manager.findCustomerById(findId);

                    if (foundCustomer != null) {
                        System.out.println(foundCustomer);
                    } else {
                        System.out.println("Customer not found.");
                    }
                    break;

                // Collect information and add a new customer.
                case "3":
                    int newId;

                    while (true) {
                        newId = readInt(
                                scanner,
                                "Enter customer ID: "
                        );

                        if (manager.findCustomerById(newId) == null) {
                            break;
                        }

                        System.out.println(
                                "A customer with that ID already exists. "
                                        + "Try again."
                        );
                    }

                    String name = readText(
                            scanner,
                            "Enter customer name: "
                    );

                    String email = readEmail(
                            scanner,
                            "Enter customer email: "
                    );

                    String phone = readPhone(
                            scanner,
                            "Enter customer phone: "
                    );

                    manager.addCustomer(
                            new Customer(newId, name, email, phone)
                    );

                    System.out.println("Customer added.");
                    break;

                // Find an existing customer and update its information.
                case "4":
                    int updateId = readInt(
                            scanner,
                            "Enter customer ID to update: "
                    );

                    Customer customerToUpdate =
                            manager.findCustomerById(updateId);

                    if (customerToUpdate == null) {
                        System.out.println("Customer not found.");
                        break;
                    }

                    String updatedName = readText(
                            scanner,
                            "Enter new name: "
                    );

                    String updatedEmail = readEmail(
                            scanner,
                            "Enter new email: "
                    );

                    String updatedPhone = readPhone(
                            scanner,
                            "Enter new phone: "
                    );

                    manager.updateCustomer(
                            updateId,
                            updatedName,
                            updatedEmail,
                            updatedPhone
                    );

                    System.out.println("Customer updated.");
                    break;

                // Remove a customer by ID.
                case "5":
                    int removeId = readInt(
                            scanner,
                            "Enter customer ID to remove: "
                    );

                    boolean removed =
                            manager.removeCustomerById(removeId);

                    if (removed) {
                        System.out.println("Customer removed.");
                    } else {
                        System.out.println("Customer not found.");
                    }
                    break;

                // End the application.
                case "6":
                    running = false;
                    System.out.println("Goodbye!");
                    break;

                default:
                    System.out.println(
                            "Invalid option. Please choose 1 through 6."
                    );
            }
        }

        scanner.close();
    }

    // Read and validate numeric input
    private static int readInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    // Read text text and reject blank values.
    private static String readText(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            if (!input.isEmpty()) {
                return input;
            }

            System.out.println("This field cannot be blank.");
        }
    }

    // Validate that an email contains basic email characters.
    private static String readEmail(Scanner scanner, String prompt) {
        while (true) {
            String email = readText(scanner, prompt);

            if (email.contains("@") && email.contains(".")) {
                return email;
            }

            System.out.println("Please enter a valid email address.");
        }
    }

    // Validate that a phone number contains at least seven digits.
    private static String readPhone(Scanner scanner, String prompt) {
        while (true) {
            String phone = readText(scanner, prompt);
            String digitsOnly = phone.replaceAll("\\D", "");

            if (digitsOnly.length() >= 7) {
                return phone;
            }

            System.out.println("Please enter a valid phone number.");
        }
    }
}