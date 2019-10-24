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
     * 
     * @param part 
     */
    public void addPart(Part part) {
        allParts.add(part);
    }
    
    public void addProduct(Product product) {
        allProducts.add(product);
    }
    
    public Part lookupPart(int partId) {
        for(Part part : allParts) {
            if(part.getId() == partId) {
                return part;
            }
        }
        
        return null;
    }
    
    public Product lookupProduct(int productId) {
        for(Product product : allProducts) {
            if(product.getId() == productId) {
                return product;
            }
        }
        
        return null;
    }
    
    public Part lookupPart(String partName) {
        for(Part part : allParts) {
            if(part.getName().equals(partName)) {
                return part;
            }
        }
        
        return null;
    }
    
    public Product lookupProduct(String productName) {
        for(Product product : allProducts) {
            if(product.getName().equals(productName)) {
                return product;
            }
        }
        
        return null;
    }
    
    public void updatePart(int index, Part part) {
        allParts.set(index, part);
    }
    
    public void updateProduct(int index, Product product) {
        allProducts.set(index, product);
    }
    
    public void deletPart(Part part) {
        allParts.remove(part);
    }
    
    public void deleteProduct(Product product) {
        allProducts.remove(product);
    }
    
    public ObservableList<Part> getAllParts() {
        return allParts;
    }
    
    public ObservableList<Product> getAllProducts() {
        return allProducts;
    }
    
    public int getNextIdNumberForPart() {
        int maxId = 0;
        for(Part part : allParts) {
            if(part.getId() > maxId) {
                maxId = part.getId();
            }
        }
        
        return maxId + 1;
    }
    
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
