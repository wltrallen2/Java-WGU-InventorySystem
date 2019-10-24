/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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

    @FXML
    private Button searchPartsButton;
    @FXML
    private TextField searchPartsTextField;
    @FXML
    private TableView<Part> partsTable;
    @FXML
    private TableColumn<Part, Integer> partsPartIDColumn;
    @FXML
    private TableColumn<Part, String> partsPartNameColumn;
    @FXML
    private TableColumn<Part, Integer> partsInventoryLevelColumn;
    @FXML
    private TableColumn<Part, Double> partsPricePerUnitColumn;
    @FXML
    private Button addPartsButton;
    @FXML
    private Button modifyPartsButton;
    @FXML
    private Button deletePartsButton;
    @FXML
    private Button exitButton;
    @FXML
    private Button searchProductsButton;
    @FXML
    private TextField searchProductsTextField;
    @FXML
    private TableView<Product> productsTable;
    @FXML
    private TableColumn<Product, Integer> productsProductIDColumn;
    @FXML
    private TableColumn<Product, String> productsProductNameColumn;
    @FXML
    private TableColumn<Product, Integer> productsInventoryLevelColumn;
    @FXML
    private TableColumn<Product, Double> productsPricePerUnitColumn;
    @FXML
    private Button addProductsButton;
    @FXML
    private Button modifyProductsButton;
    @FXML
    private Button deleteProductsButton;
    
    private final Inventory inventory = new Inventory();
        

    /**
     * Initializes the controller class.
     * TODO: Remove sample data
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        populateSampleData();
        populatePartsTable();
        populateProductsTable();
        resetView();
     }    

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

    @FXML
    private void addModifyHandler(ActionEvent event) throws IOException {        
        Button sourceButton = (Button) event.getSource();
        String resourceName;
        
        if(sourceButton.equals(addPartsButton) || sourceButton.equals(modifyPartsButton)) {
            resourceName = "AddModifyPart.fxml";
        } else {
            resourceName = "AddModifyProduct.fxml";
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource(resourceName));
        Stage stage = new Stage();
        Stage owner = (Stage) sourceButton.getScene().getWindow();
        stage.initOwner(owner);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        
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
        
        if(controller instanceof AddModifyProductController) {
            ((AddModifyProductController)controller).populateAddTable();
        }
        
        stage.setScene(scene);
        owner.hide();
        stage.showAndWait();
        
        resetView();
        owner.show();
    }

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

    @FXML
    private void exitHandler(ActionEvent event) {
        System.exit(0);
    }
    
    /*****************************************************
    /* GETTERS/SETTERS
    ******************************************************/

    public Inventory getInventory() {
        return inventory;
    }
    
    /*****************************************************
    /* HELPER METHODS
    ******************************************************/
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
    
    private void populatePartsTable() {
        partsPartIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partsPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partsInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partsPricePerUnitColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
    
    private void populateProductsTable() {
        productsProductIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productsProductNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productsInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productsPricePerUnitColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
    
    private void resetView() {
        partsTable.setItems(inventory.getAllParts());
        productsTable.setItems(inventory.getAllProducts());
        searchPartsTextField.setText("");
        searchProductsTextField.setText("");
    }
}
