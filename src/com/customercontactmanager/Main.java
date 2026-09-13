package com.customercontactmanager;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
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

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

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
                case "1":
                    if (manager.getCustomers().isEmpty()) {
                        System.out.println("No customers found.");
                    } else {
                        for (Customer customer : manager.getCustomers()) {
                            System.out.println(customer);
                        }
                    }
                    break;

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

    private static String readEmail(Scanner scanner, String prompt) {
        while (true) {
            String email = readText(scanner, prompt);

            if (email.contains("@") && email.contains(".")) {
                return email;
            }

            System.out.println("Please enter a valid email address.");
        }
    }

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