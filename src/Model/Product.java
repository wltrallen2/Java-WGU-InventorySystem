/*
 * Built as part of the class requirements for
 * Western Governor's University Software I - C482.
 *
 */
package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Represents a product in the company inventory
 * 
 * @author walterallen
 */
public class Product {
    
    private final ObservableList<Part> associatedParts;
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    
    /**
     * Class constructor
     * 
     * @param id an int representing the product id number
     * @param name a String representing the product name
     * @param price a double representing the price of the product
     * @param stock an int representing the number of product items in stock
     * @param min an int representing the minimum number of product items kept
     * in stock
     * @param max an int representing the maximum number of product items kept
     * in stock
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
        associatedParts = FXCollections.observableArrayList();
    }
    
    /**
     * Sets the product id number
     * 
     * @param id an int representing the product id number
     */
    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * Sets the product name
     * 
     * @param name a String representing the product name
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Sets the product price
     * 
     * @param price a double representing the price of the product
     */
    public void setPrice(double price) {
        this.price = price;
    }
    
    /**
     * Sets the number of product items in stock
     * 
     * @param stock an int representing the number of product items in stock
     */
    public void setStock(int stock) {
        this.stock = stock;
    }
    
    /**
     * Sets the minimum number of product items kept in stock
     * 
     * @param min an int representing the minimum number of product items kept
     * in stock
     */
    public void setMin(int min) {
        this.min = min;
    }
    
    /**
     * Sets the maximum number of product items kept in stock
     * 
     * @param max an int representing the maximum number of product items kept
     * in stock
     */
    public void setMax(int max) {
        this.max = max;
    }
    
    /**
     * Accepts an int value as the price of the product and converts it to a
     * double value for storage
     * 
     * @param price an int representing the price of the product
     */
    public void setPrice(int price) {
        this.price = (double)price;
    }
    
    /**
     * 
     * @return an int representing the product id
     */
    public int getId() {
        return id;
    }
    
    /**
     * 
     * @return a String representing the name of the product
     */
    public String getName() {
        return name;
    }
    
    /**
     * 
     * @return a double representing the price of the product
     */
    public double getPrice() {
        return price;
    }
    
    /**
     * 
     * @return an int representing the number of product items currently in stock
     */
    public int getStock() {
        return stock;
    }
    
    /**
     * 
     * @return an int representing the minimum number of product items kept in stock
     */
    public int getMin() {
        return min;
    }
    
    /**
     * 
     * @return an int representing the maximum number of product items kept in stock
     */
    public int getMax() {
        return max;
    }
    
    /**
     * Adds a part to the product.
     * 
     * @param part a Part representing the part to be added to this product.
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }
    
    /**
     * Removes a part from the product.
     * 
     * @param part a Part representing the part to be removed from the product.
     */
    public void deleteAssociatedPart(Part part) {
        associatedParts.remove(part);
    }
    
    /**
     * Retrieves a list of all parts that make up the product.
     * 
     * @return an ObservableList of Part instances that make up the given product.
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }
    
    /**
     * A String representation of the product that includes the product id number,
     * the name, the price, the number of items in stock, the minimum number of items
     * kept in stock, and the maximum number of items kept in stock.
     * 
     * @return a String representing of the Product instance.
     */
    @Override
        public String toString() {
        return (getId() + " " + getName() + " " + getPrice() + " " + getStock() 
                + " " + getMin() + " " + getMax());
    }
}
