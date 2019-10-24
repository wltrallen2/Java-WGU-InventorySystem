# Java-WGU-InventorySystem
Java application created as student project for Western Governor's University's C492 - Software I

For this assignment, the student was required to implement the following:

**I. User Interface**
Create a JavaFX application with a graphical user interface (GUI) based on the attached “GUI Mock-Up”. Write code to display each  of the following screens in the GUI: 

- [x] A main screen, showing the following controls:
  * buttons for “Add”, “Modify”, “Delete”, “Search” for parts and products, and “Exit”
  * lists for parts and products
  * text boxes for searching for parts and products
  * title labels for parts, products, and the application title 

- [x] An add part screen, showing the following controls:
  * radio buttons for “In-House” and “Outsourced” parts
  * buttons for “Save” and “Cancel”
  * text fields for ID, name, inventory level, price, max and min values, and company name or machine ID
  * labels for ID, name, inventory level, price/cost, max and min values, the application title, and company name or machine ID
 
- [x] A modify part screen, with fields that populate with presaved data, showing the following controls:
  * radio buttons for “In-House” and “Outsourced” parts
  * buttons for “Save” and “Cancel”
  * text fields for ID, name, inventory level, price, max and min values, and company name or machine ID
  * labels for ID, name, inventory level, price, max and min values, the application title, and company name or machine ID

- [x] An add product screen, showing the following controls:
  * buttons for “Save”, “Cancel”, “Add” part, and “Delete” part
  * text fields for ID, name, inventory level, price, and max and min values
  * labels for ID, name, inventory level, price, max and min values, and the application
  * a list for associated parts and their products
  * a “Search” button and a text field with an associated list for displaying the results of the search

- [x] A modify product screen, with fields that populate with presaved data, showing the following controls:
  * buttons for “Save”, “Cancel”, “Add” part, and “Delete” part
  * text fields for ID, name, inventory level, price, and max and min values
  * labels for ID, name, inventory level, price, max and min values, and the application
  * a list for associated parts and their products
  * a “Search” button and a text field with associated list for displaying the results of the search

**II. Application**
Write code to create the class structure provided in the attached “UML (unified modeling language) Class Diagram”. Enable each  of the following capabilities in the application:
 
- [x] Using the attached “UML Class Diagram”, create appropriate classes and instance variables with the following criteria:
  * five classes with the 16 associated instance variables
  * variables are only accessible through getter methods
  * variables are only modifiable through setter methods

- [x] Add the following functionalities to the main screen, using the methods provided in the attached “UML Class Diagram”:
  * redirect the user to the “Add Part”, “Modify Part”, “Add Product”, or “Modify Product” screens
  * delete a selected part or product from the list
  * search for a part or product and display matching results
  * exit the main screen
 
- [x] Add the following functionalities to the part screens, using the methods provided in the attached “UML Class Diagram”:
  1.  “Add Part” screen
    * select “In-House” or “Outsourced”
    * enter name, inventory level, price, max and min values, and company name or machine ID
    * save the data and then redirect to the main screen
    * cancel or exit out of this screen and go back to the main screen
  1.  “Modify Part” screen
    * select “In-House” or “Outsourced”
    * modify or change data values
    * save modifications to the data and then redirect to the main screen
    * cancel or exit out of this screen and go back to the main screen

- [x] Add the following functionalities to the product screens, using the methods provided in the attached “UML Class Diagram”:
 1.  “Add Product” screen
  * enter name, inventory level, price, and max and min values
  * save the data and then redirect to the main screen
  * associate one or more parts with a product
  * remove or disassociate a part from a product
  * cancel or exit out of this screen and go back to the main screen
 1.  “Modify Product” screen
  * modify or change data values
  * save modifications to the data and then redirect to the main screen
  * associate one or more parts with a product
  * remove or disassociate a part from a product
  * cancel or exit out of this screen and go back to the main screen

- [x] Write code to implement exception controls with custom error messages for one requirement out of each of the following sets (pick one from each):
 1.  Set 1
  * entering an inventory value that exceeds the minimum or maximum value for that part or product
  * preventing the minimum field from having a value above the maximum field
  * preventing the maximum field from having a value below the minimum field
  * (SELECTED) ensuring that a product must always have at least one part
 1.  Set 2
  * including a confirm dialogue for all “Delete” and “Cancel” buttons
  * (SELECTED) ensuring that the price of a product cannot be less than the cost of the parts
  * ensuring that a product must have a name, price, category, and inventory level (default 0)
