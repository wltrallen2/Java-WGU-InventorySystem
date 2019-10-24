/*
 * Built as part of the class requirements for
 * Western Governor's University Software I - C482.
 *
 */
package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Represents the company inventory. Consists of two lists: a lists of parts
 * (which can be either in-house parts or outsourced parts) and a list of
 * products (which are made of of the company's parts).
 * 
 * @author walterallen
 */
public class Inventory {
    private final ObservableList<Part> allParts = FXCollections.observableArrayList();
    private final ObservableList<Product> allProducts = FXCollections.observableArrayList();
        
    /**
     * Adds a part to the inventory.
     * 
     * @param part a Part instance representing a part to be tracked in inventory.
     */
    public void addPart(Part part) {
        allParts.add(part);
    }
    
    /**
     * Adds a product to the inventory.
     * 
     * @param product a Product instance representing a part to be tracked in inventory.
     */
    public void addProduct(Product product) {
        allProducts.add(product);
    }
    
    /**
     * Retrieves a part from the inventory using the part ID number.
     * 
     * @param partId an int representing the part ID number
     * @return a Part instance representing a part that is tracked in the inventory,
     * or null if the part id does not exist.
     */
    public Part lookupPart(int partId) {
        for(Part part : allParts) {
            if(part.getId() == partId) {
                return part;
            }
        }
        
        return null;
    }
    
    /**
     * Retrieves a product from the inventory using the product ID number.
     * 
     * @param productId an int representing the product ID number
     * @return a Product instance representing a product tracked by the inventory,
     * or null if the product ID does not exist.
     */
    public Product lookupProduct(int productId) {
        for(Product product : allProducts) {
            if(product.getId() == productId) {
                return product;
            }
        }
        
        return null;
    }
    
    /**
     * Retrieves a part from the inventory using the part name.
     * 
     * @param partName a String representing the name of a part tracked in inventory.
     * @return a Part instance representing a part that is tracked in the inventory,
     * or null if the part name does not exist.
     */
    public Part lookupPart(String partName) {
        for(Part part : allParts) {
            if(part.getName().equals(partName)) {
                return part;
            }
        }
        
        return null;
    }
    
    /**
     * Retrieves a product from the inventory using the produce name.
     * 
     * @param productName a String representing the name of a produce tracked in inventory.
     * @return a Produce instance representing a produce that is tracked in the inventory,
     * or null if the produce name does not exist.
     */
    public Product lookupProduct(String productName) {
        for(Product product : allProducts) {
            if(product.getName().equals(productName)) {
                return product;
            }
        }
        
        return null;
    }
    
    /**
     * Replaces the part at the given index in the parts array with the Part instance provided.
     * 
     * @param index an int representing the index of the Part instance to be replaced.
     * @param part a Part instance representing the part to be stored at the given
     * index of the parts array.
     */
    public void updatePart(int index, Part part) {
        allParts.set(index, part);
    }
    
    /**
     * Replaces the product at the given index in the products array with the Product
     * instance provided.
     * 
     * @param index an int representing the index of the Product instance to be replaced.
     * @param product a Product instance representing the product to be stored at
     * the given index of the products array.
     */
    public void updateProduct(int index, Product product) {
        allProducts.set(index, product);
    }
    
    /**
     * Removes the part from the inventory.
     * 
     * @param part an instance of the Part to be removed.
     */
    public void deletPart(Part part) {
        allParts.remove(part);
    }
    
    /**
     * Removes the product from the inventory.
     * 
     * @param product an instance of the Product to be removed.
     */
    public void deleteProduct(Product product) {
        allProducts.remove(product);
    }
    
    /**
     * Retrieves a list of all parts in the inventory list.
     * 
     * @return an ObservableList of Part objects representing all parts
     * in the inventory list
     */
    public ObservableList<Part> getAllParts() {
        return allParts;
    }
    
    /**
     * Retrieves a list of all products in the inventory list.
     * 
     * @return an ObservableList of Product objects representing all products in
     * the inventory list
     */
    public ObservableList<Product> getAllProducts() {
        return allProducts;
    }
    
    /**
     * Determines the next available id number for a new part by identifying the
     * maximum value assigned to a part ID and adding 1.
     * 
     * @return an int representing the next available id number for a new part
     */
    public int getNextIdNumberForPart() {
        int maxId = 0;
        for(Part part : allParts) {
            if(part.getId() > maxId) {
                maxId = part.getId();
            }
        }
        
        return maxId + 1;
    }
    
    /**
     * Determines the next available id number for a new product by identifying the
     * maximum value assigned to a product ID and adding 1.
     * 
     * @return an int representing the next available id number for a new product
     */
    public int getNextIdNumberForProduct() {
        Integer maxId = 0;
        for(Product product : allProducts) {
            if(product.getId() > maxId) {
                maxId = product.getId();
            }
        }
        
        return maxId + 1;
    }
}
