/*
 * Built as part of the class requirements for
 * Western Governor's University Software I - C482.
 *
 */
package View_Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class, a subclass of Secondary Controller,
 * that allows the user to add a new product or modify
 * the information for an existing product.
 *
 * @author walterallen
 */
public class AddModifyProductController extends SecondaryController implements Initializable {

    @FXML private Label screenLabel;
    
    @FXML private Label idLabel;
    @FXML private TextField nameTextField;
    @FXML private TextField invTextField;
    @FXML private TextField priceTextField;
    @FXML private TextField maxTextField;
    @FXML private TextField minTextField;
    
    @FXML private TableView<Part> addTable;
    @FXML private TableColumn<Part, Integer> addPartIDColumn;
    @FXML private TableColumn<Part, String> addPartNameColumn;
    @FXML private TableColumn<Part, Integer> addInventoryLevelColumn;
    @FXML private TableColumn<Part, Double> addPricePerUnitColumn;
    
    @FXML private TableView<Part> deleteTable;
    @FXML private TableColumn<Part, Integer> deletePartIDColumn;
    @FXML private TableColumn<Part, String> deletePartNameColumn;
    @FXML private TableColumn<Part, Integer> deleteInventoryLevelColumn;
    @FXML private TableColumn<Part, Double> deletePricePerUnitColumn;
    
    @FXML private Button searchButton;
    @FXML private TextField searchTextField;
    
    @FXML private Button addButton;
    @FXML private Button deleteButton;
    @FXML private Button cancelButton;
    @FXML private Button saveButton;
    
    private Product currentProduct;
    private ObservableList<Part> partsToDelete;
    private ObservableList<Part> partsToAdd;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    /**
     * Allows the user to search the inventory for parts that contain the search
     * string (in the search text field), and then sets the items in the "Add Parts"
     * table to the results of the search.
     * 
     * @param event the ActionEvent that called the method, in this case, the user
     * clicking the search button.
     */
    @FXML
    private void searchHandler(ActionEvent event) {
        FilteredList<Part> filteredList = new FilteredList<>(parentController.getInventory().getAllParts(), p -> true);
        String searchString = searchTextField.getText();
        filteredList.setPredicate(part -> {
            return part.toString().toLowerCase().contains(searchString.toLowerCase());
        });
        
        SortedList<Part> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(addTable.comparatorProperty());
        addTable.setItems(sortedList);
    }

    /**
     * Allows the user to select a part from the "Add Part" table and add it 
     * to the current product. Additionally, this method updates the "Delete Part"
     * table to show all parts that make up the current product including the
     * newly added part.
     * 
     * @param event the ActionEvent that called the method, in this case, the user
     * clicking the Add Part button.
     */
    @FXML
    private void addHandler(ActionEvent event) {
        Part partToAdd = addTable.getSelectionModel().getSelectedItem();
        ObservableList<Part> tableItems = deleteTable.getItems();
        if(tableItems.isEmpty()) {
            associateDataToColumnsForDeleteTable();
        }
        tableItems.add(partToAdd);
        deleteTable.refresh();
    }

    /**
     * Deletes the selected part from the current product and refreshes the list
     * of parts in the "Delete Part" table.
     * 
     * @param event the ActionEvent that called the method, in this case, the user
     * clicking the Delete Part button.
     */
    @FXML
    private void deleteHandler(ActionEvent event) {
        if(deleteTable.getItems().size() > 0) {
            Part partToDelete = deleteTable.getSelectionModel().getSelectedItem();
            ObservableList<Part> tableItems = deleteTable.getItems();
            tableItems.remove(partToDelete);
            deleteTable.refresh();
        }
    }

    /**
     * Closes (Hides) the current scene.
     * 
     * @param event the ActionEvent that called the method, in this case, the user
     * clicking the Cancel button.
     */
    @FXML
    private void cancelHandler(ActionEvent event) {
        cancelButton.getScene().getWindow().hide();
    }

    /**
     * Saves the information for the given product to the inventory, including
     * all text in the text fields as well as all parts in the parts list (currently
     * listed in the "Delete Part" table). To do this, the method first checks to
     * make sure that product has at least one product associated with it,and if not,
     * displays a warning. Additionally, the product checks to ensure that the price
     * of the product is greater than the cumulative price of all parts tha make
     * up the product, and if not, displays a warning. Finally, the method then
     * overwrites the original product if the user navigated to this scene by
     * selecting a product to modify or adds the new product to the inventory
     * database if the user navigated to this scene by choosing to add a product.
     * 
     * @param event the ActionEvent that called the method, in this case, the
     * user clicking the Save button.
     */
    @FXML
    private void saveHandler(ActionEvent event) {
        if(deleteTable.getItems().size() <= 0) {
            Alert alert = new Alert(AlertType.WARNING, "All products must have at least one associated part. Please add a part to this product.");
            alert.showAndWait();
        } else if(!productPriceGreaterThanPartsPrice()) {
            Alert alert = new Alert(AlertType.WARNING, "The cost of the product must be greater than the cost of the associated parts.");
            alert.showAndWait();
        } else {
            Inventory inventory = parentController.getInventory();

            int id = inventory.getNextIdNumberForProduct();
            String name = nameTextField.getText();
            int stock = new Integer(invTextField.getText());
            int min = new Integer(minTextField.getText());
            int max = new Integer(maxTextField.getText());
            double price = new Double(priceTextField.getText());

            Product newProduct = new Product(id, name, price, stock, min, max);
            deleteTable.getItems().forEach((part) -> {
                newProduct.addAssociatedPart(part);
            });

            if(currentProduct != null) {
                int index = inventory.getAllProducts().indexOf(currentProduct);
                inventory.updateProduct(index, newProduct);
            } else {
                inventory.addProduct(newProduct);
            }

            saveButton.getScene().getWindow().hide();
        }
    }
    
    /*******************************************************
     * HELPER METHODS
     *******************************************************/
    
    /**
     * Sets the top label for the scene, which indicates whether this scene
     * was meant to add a product or modify a product.
     * 
     * @param text the String that represents the text to be displayed in the label.
     */
    public void setScreenLabel(String text) {
        screenLabel.setText(text);
    }
    
    /**
     * Sets the current product for the scene and populates all text fields, the
     * product id label, and the "Delete Parts" table, which includes all of the
     * parts that make up the product.
     * 
     * @param product a Product that represents the product to be modified.
     */
    public void setCurrentProduct(Product product) {
        this.currentProduct = product;
        
        idLabel.setText("" + product.getId());
        nameTextField.setText(product.getName());
        invTextField.setText("" + product.getStock());
        priceTextField.setText("" + product.getPrice());
        minTextField.setText("" + product.getMin());
        maxTextField.setText("" + product.getMax());
        
        associateDataToColumnsForDeleteTable();
        ObservableList<Part> associatedParts = FXCollections.observableArrayList(product.getAllAssociatedParts());
        deleteTable.setItems(associatedParts);
    }
    
    /**
     * Associates the variables for a Part instance with the appropriate columns
     * in the "Add Part" table.
     */
    private void associateDataToColumnsForAddTable() {
        addPartIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        addPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        addInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addPricePerUnitColumn.setCellValueFactory(new PropertyValueFactory("price"));
    }
    
    /**
     * Associates the variables for a Part instance with the appropriate columns
     * in the "Delete Part" table.
     */
    private void associateDataToColumnsForDeleteTable() {
        deletePartIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        deletePartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        deleteInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        deletePricePerUnitColumn.setCellValueFactory(new PropertyValueFactory("price"));

    }
    
    /**
     * Populates the "Add Parts" table with all of the parts in the inventory.
     */
    public void populateAddTable() {
        associateDataToColumnsForAddTable();
        ObservableList<Part> allParts = parentController.getInventory().getAllParts();
        addTable.setItems(allParts);
    }
    
    /**
     * Checks to see if the price of the product is set greater than the cumulative
     * prices of all parts that make up the product.
     * 
     * @return true if the product price is greater that the sum of its parts, and
     * false otherwise.
     */
    private Boolean productPriceGreaterThanPartsPrice() {
        ObservableList<Part> associatedParts = deleteTable.getItems();
        double totalPriceOfParts = 0;
        totalPriceOfParts = associatedParts.stream().map((part) -> part.getPrice())
                .reduce(totalPriceOfParts, (accumulator, _item) -> accumulator + _item);
        
        return(totalPriceOfParts < new Double(priceTextField.getText()));
    }
}
