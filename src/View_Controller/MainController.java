/*
 * Built as part of the class requirements for
 * Western Governor's University Software I - C482.
 *
 */
package View_Controller;

import Model.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author walterallen
 */
public class MainController implements Initializable {

    @FXML private Button searchPartsButton;
    @FXML private TextField searchPartsTextField;
    
    @FXML private TableView<Part> partsTable;
    @FXML private TableColumn<Part, Integer> partsPartIDColumn;
    @FXML private TableColumn<Part, String> partsPartNameColumn;
    @FXML private TableColumn<Part, Integer> partsInventoryLevelColumn;
    @FXML private TableColumn<Part, Double> partsPricePerUnitColumn;
    
    @FXML private Button addPartsButton;
    @FXML private Button modifyPartsButton;
    @FXML private Button deletePartsButton;
    @FXML private Button exitButton;
    
    @FXML private Button searchProductsButton;
    @FXML private TextField searchProductsTextField;
    
    @FXML private TableView<Product> productsTable;
    @FXML private TableColumn<Product, Integer> productsProductIDColumn;
    @FXML private TableColumn<Product, String> productsProductNameColumn;
    @FXML private TableColumn<Product, Integer> productsInventoryLevelColumn;
    @FXML private TableColumn<Product, Double> productsPricePerUnitColumn;
    
    @FXML private Button addProductsButton;
    @FXML private Button modifyProductsButton;
    @FXML private Button deleteProductsButton;
    
    private final Inventory inventory = new Inventory();
        

    /**
     * Called to initialize a controller after its root element has been 
     * completely processed. As part of this class, the initializer populates
     * the model with sample data (currently hard coded into the class per assignment
     * instructions), populates the Parts table, populates the products table,
     * and calls the resetView() method.
     * 
     * @param url The location used to resolve relative paths for the 
     * root object, or null if the location is not known.
     * @param rb The resources used to localize the root object, 
     * or null if the root object was not localized. 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        populateSampleData();
        populatePartsTable();
        populateProductsTable();
        resetView();
     }    

    /**
     * Searches the data for a part or product that contains the search string
     * provided in the corresponding search text field. This method differentiates
     * between events allowing it to respond to both a search for a part or
     * a search for a product. Additionally, the search is case insensitive.
     * 
     * Finally, the search is completed by setting the items in the parts table
     * or the products table with the results of the search.
     * 
     * @param event the ActionEvent that called the method, in this case, the user
     * clicking either the Search Parts or the Search Products button.
     */
    @FXML
    private void searchHandler(ActionEvent event) {
        if(event.getSource() == searchPartsButton) {
            FilteredList<Part> filteredParts = new FilteredList<>(inventory.getAllParts(), p -> true);
            String lowerCaseSearchString = searchPartsTextField.getText().toLowerCase();
            filteredParts.setPredicate(part -> {
                return part.toString().toLowerCase().contains(lowerCaseSearchString);
            });
            
            SortedList<Part> sortedParts = new SortedList<>(filteredParts);
            sortedParts.comparatorProperty().bind(partsTable.comparatorProperty());
            partsTable.setItems(sortedParts);
        } else {
            FilteredList<Product> filteredProducts = new FilteredList<>(inventory.getAllProducts(), p -> true);
            String lowerCaseSearchString = searchProductsTextField.getText().toLowerCase();
            filteredProducts.setPredicate(product -> {
                Boolean test = product.toString().toLowerCase().contains(lowerCaseSearchString);
                return test;
            });
            
            SortedList<Product> sortedProducts = new SortedList<>(filteredProducts);
            sortedProducts.comparatorProperty().bind(productsTable.comparatorProperty());
            productsTable.setItems(sortedProducts);
        }
    }

    /**
     * Loads, configures, and shows a secondary scene (either the Add/Modify
     * Parts scene or the Add/Modify Products scene). The secondary scene should
     * be controlled by a subclass of SecondaryController.
     * 
     * The method first determines which button was clicked (Add Parts, Modify Parts,
     * Add Products, Modify Products) and responds accordingly. This includes loading
     * the correct resource, populating the tables in the presenting scene with
     * the correct data, and setting the current part of product (if "modify" was
     * clicked).
     * 
     * @param event the ActionEvent that calls the method, in this case, the user
     * clicking one of the add or modify buttons.
     */
    @FXML
    private void addModifyHandler(ActionEvent event) throws IOException {        
        Button sourceButton = (Button) event.getSource();
        String resourceName;
        
        // Sets the resource name dependant on whether the user clicked a "Parts"
        // button or a "Modify" button.
        if(sourceButton.equals(addPartsButton) || sourceButton.equals(modifyPartsButton)) {
            resourceName = "AddModifyPart.fxml";
        } else {
            resourceName = "AddModifyProduct.fxml";
        }

        // Loads the resource and sets the scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource(resourceName));
        Stage stage = new Stage();
        Stage owner = (Stage) sourceButton.getScene().getWindow();
        stage.initOwner(owner);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        
        /* Configures the instance of a SecondaryController subclass
         * by setting this MainController instance as the parent controller.
         * Additionally, if the user clicked one of the two modify buttons,
         * the method sets the data in the tables, sets the scene's
         * label to "Modify" as opposed to "Add," and sets the current part or product. 
         */
        SecondaryController controller = loader.getController();
        controller.setParentController(this);
        if(sourceButton.equals(modifyPartsButton) && controller instanceof AddModifyPartController) {
            AddModifyPartController addController = (AddModifyPartController)controller;
            Part currentPart = partsTable.getSelectionModel().getSelectedItem();
            addController.setCurrentPart(currentPart);
            addController.setScreenLabel("Modify Part");
        } else if(sourceButton.equals(modifyProductsButton) && controller instanceof AddModifyProductController) {
            AddModifyProductController addController = (AddModifyProductController)controller;
            Product currentProduct = productsTable.getSelectionModel().getSelectedItem();
            addController.setScreenLabel("Modify Product");
            addController.setCurrentProduct(currentProduct);
        }
        
        // Populates the "Add Parts" table if the user clicked the Add or Modify
        // Products button.
        if(controller instanceof AddModifyProductController) {
            ((AddModifyProductController)controller).populateAddTable();
        }
        
        // Hides the current scene and shows the presenting scene.
        stage.setScene(scene);
        owner.hide();
        stage.showAndWait();
        
        // Resets the current scene and shows the current scene (this instance
        // of MainController) after the presenting scene is dismissed/hidden.
        resetView();
        owner.show();
    }

    /**
     * Deletes the selected part or product from the inventory and resets the
     * view to indicate that the deleted item is no longer in the inventory.
     * 
     * @param event the ActionEvent that called the method, in this case, the
     * user clicking on one of the Delete buttons.
     */
    @FXML
    private void deleteHandler(ActionEvent event) {
        if(event.getSource().equals(deletePartsButton)) {
            Part part = partsTable.getSelectionModel().getSelectedItem();
            inventory.deletPart(part);
        } else {
            Product product = productsTable.getSelectionModel().getSelectedItem();
            inventory.deleteProduct(product);
        }
        
        resetView();
    }

    /**
     * Exits the application.
     * 
     * @param event the ActionEvent that called the method, in this case, the
     * user clicking the Exit button.
     */
    @FXML
    private void exitHandler(ActionEvent event) {
        System.exit(0);
    }
    
    /*****************************************************
    /* GETTERS/SETTERS
    ******************************************************/

    /**
     * 
     * @return an Inventory instance representing the current inventory.
     */
    public Inventory getInventory() {
        return inventory;
    }
    
    /*****************************************************
    /* HELPER METHODS
    ******************************************************/
    
    /**
     * Populates the inventory with sample data, currently hard coded into the
     * application due to assignment instructions.
     */
    private void populateSampleData() {
        InHouse inhouse1 = new InHouse(1, "Black & White Ball", 3.99, 4, 1, 10, 123456);
        InHouse inhouse2 = new InHouse(2, "Primary-Colored Ball", 4.99, 6, 1, 20, 234567);
        InHouse inhouse3 = new InHouse(3, "Secondary-Colored Ball", 5.99, 15, 2, 40, 345678);
        Outsourced outsourced1 = new Outsourced(4, "Cloud Rattle", 14.99, 3, 3, 14, "Lovevery");
        Outsourced outsourced2 = new Outsourced(5, "Blue Wooden Rattle", 21.99, 9, 1, 10, "Lovevery");
        Outsourced outsourced3 = new Outsourced(6, "Light-Up Rattle", 13.99, 5, 2, 21, "Newton");
        Product product1 = new Product(11, "Ball Gift Set", 24.99, 4, 1, 10);
        Product product2 = new Product(12, "Rattle Gift Set", 29.99, 3, 1, 10);
        
        product1.addAssociatedPart(inhouse1);
        product1.addAssociatedPart(inhouse2);
        product2.addAssociatedPart(outsourced1);
        product2.addAssociatedPart(outsourced2);
        
        inventory.addPart(inhouse1);
        inventory.addPart(inhouse2);
        inventory.addPart(inhouse3);
        inventory.addPart(outsourced1);
        inventory.addPart(outsourced2);
        inventory.addPart(outsourced3);
        inventory.addProduct(product1);
        inventory.addProduct(product2);
    }
    
    /**
     * Populates the Parts table by associating the variables of Parts objects
     * with the appropriate columns.
     */
    private void populatePartsTable() {
        partsPartIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partsPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partsInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partsPricePerUnitColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
    
    /**
     * Populates the Products table by associating the variables of Products
     * objects with the appropriate columns.
     */
    private void populateProductsTable() {
        productsProductIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productsProductNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productsInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productsPricePerUnitColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
    
    /**
     * Resets the view of this MainController instance by reloading all parts
     * and products into their respective tables and resetting the search fields
     * to the empty string ("").
     */
    private void resetView() {
        partsTable.setItems(inventory.getAllParts());
        productsTable.setItems(inventory.getAllProducts());
        searchPartsTextField.setText("");
        searchProductsTextField.setText("");
    }
}
