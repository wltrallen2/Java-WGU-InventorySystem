/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
import java.io.IOException;
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
 * FXML Controller class
 *
 * @author walterallen
 */
public class AddModifyProductController extends SecondaryController implements Initializable {

    @FXML
    private Label screenLabel;
    @FXML
    private Label idLabel;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField invTextField;
    @FXML
    private TextField priceTextField;
    @FXML
    private TextField maxTextField;
    @FXML
    private TextField minTextField;
    @FXML
    private TableView<Part> addTable;
    @FXML
    private TableColumn<Part, Integer> addPartIDColumn;
    @FXML
    private TableColumn<Part, String> addPartNameColumn;
    @FXML
    private TableColumn<Part, Integer> addInventoryLevelColumn;
    @FXML
    private TableColumn<Part, Double> addPricePerUnitColumn;
    @FXML
    private TableView<Part> deleteTable;
    @FXML
    private TableColumn<Part, Integer> deletePartIDColumn;
    @FXML
    private TableColumn<Part, String> deletePartNameColumn;
    @FXML
    private TableColumn<Part, Integer> deleteInventoryLevelColumn;
    @FXML
    private TableColumn<Part, Double> deletePricePerUnitColumn;
    @FXML
    private Button searchButton;
    @FXML
    private TextField searchTextField;
    @FXML
    private Button addButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Button saveButton;
    
    private Product currentProduct;
    private ObservableList<Part> partsToDelete;
    private ObservableList<Part> partsToAdd;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

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

    @FXML
    private void deleteHandler(ActionEvent event) {
        if(deleteTable.getItems().size() > 0) {
            Part partToDelete = deleteTable.getSelectionModel().getSelectedItem();
            ObservableList<Part> tableItems = deleteTable.getItems();
            tableItems.remove(partToDelete);
            deleteTable.refresh();
        }
    }

    @FXML
    private void cancelHandler(ActionEvent event) throws IOException {
        cancelButton.getScene().getWindow().hide();
    }

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
    
    public void setScreenLabel(String text) {
        screenLabel.setText(text);
    }
    
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
    
    public void associateDataToColumnsForAddTable() {
        addPartIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        addPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        addInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addPricePerUnitColumn.setCellValueFactory(new PropertyValueFactory("price"));
    }
    
    public void associateDataToColumnsForDeleteTable() {
        deletePartIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        deletePartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        deleteInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        deletePricePerUnitColumn.setCellValueFactory(new PropertyValueFactory("price"));

    }
    
    public void populateAddTable() {
        associateDataToColumnsForAddTable();
        ObservableList<Part> allParts = parentController.getInventory().getAllParts();
        addTable.setItems(allParts);
    }
    
    private Boolean productPriceGreaterThanPartsPrice() {
        ObservableList<Part> associatedParts = deleteTable.getItems();
        double totalPriceOfParts = 0;
        totalPriceOfParts = associatedParts.stream().map((part) -> part.getPrice())
                .reduce(totalPriceOfParts, (accumulator, _item) -> accumulator + _item);
        
        return(totalPriceOfParts < new Double(priceTextField.getText()));
    }
}
