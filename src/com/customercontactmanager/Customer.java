// Developer: Joshua Allgood
// Date: September 13, 2026
// Purpose: Represents a customer and stores contact information.
package com.customercontactmanager;


public class Customer {
    // Store the customer's identifying and contact information.
    private int id;
    private String name;
    private String email;
    private String phone;

    // Create a customer with an ID, name, email, and phone number.
    public Customer(int id, String name, String email, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    // Return the customer's ID.
    public int getId() {
        return id;
    }

    // Return the customer's name.
    public String getName() {
        return name;
    }

    // Return the customer's email address.
    public String getEmail() {
        return email;
    }

    // Return the customer's phone number.
    public String getPhone() {
        return phone;
    }

    // Update the customer's name.
    public void setName(String name) {
        this.name = name;
    }

    // Update the customer's email address.
    public void setEmail(String email) {
        this.email = email;
    }

    // Update the customer's phone number.
    public void setPhone(String phone) {
        this.phone = phone;
    }

    // Return a readable summary of the customer's information.
    @Override
    public String toString() {
        return id + " | " + name + " | " + email + " | " + phone;
    }
}
