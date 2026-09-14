# Customer Contact Manager

This is a Java console program I built to practice managing customer contact information.

The program currently allows me to:

- View all customers
- Find a customer by ID
- Add a customer
- Update a customer
- Remove a customer
- Check for duplicate IDs
- Validate email, phone, and numeric input
- Save customer information to a CSV file

## Running the Program

This project can be run in IntelliJ IDEA or another Java-compatible IDE.

In IntelliJ IDEA:

1. Open the project.
2. Open `src/com/customercontactmanager/Main.java`.
3. Click the green Run button next to `main`.
4. Use the menu in the Run window.

## Testing the Program

Try each menu option:

1. List the existing customers.
2. Find a customer using an existing ID.
3. Add a customer with a unique ID.
4. Try adding a duplicate ID and confirm the program asks for another ID.
5. Update an existing customer.
6. Remove a customer.
7. Exit and run the program again to confirm saved customers remain.

Also test invalid input:

- Enter letters when an ID is requested.
- Enter an invalid email such as `hello`.
- Enter a short phone number such as `123`.
- Enter a blank name, email, or phone number.

```text
src/com/customercontactmanager/Main.java