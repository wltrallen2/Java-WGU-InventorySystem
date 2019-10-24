/*
 * Built as part of the class requirements for
 * Western Governor's University Software I - C482.
 *
 */
package Model;

/**
 * An abstract class representing a part in inventory.
 * 
 * @author walterallen
 */
public abstract class Part {
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    
    /**
     * A String representing the Part instance is required in all implementing
     * classes
     * 
     * @return a String representation of the Part
     */
    @Override
    abstract public String toString();
    
    /**
     * Class constructor
     * 
     * @param id an int representing the part id
     * @param name a String representing the name of the part
     * @param price a double representing the price of the part
     * @param stock an int representing the number of the part currently in stock
     * @param min an int representing the minimum number of the part kept in stock
     * @param max an int representing the maximum number of the part kept in stock
     */
    public Part(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }
    
    /**
     * Sets the part id
     * 
     * @param id an int representing the part id
     */
    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * Sets the part name
     * 
     * @param name a String representing the name of the part
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Sets the part price
     * 
     * @param price a double representing the price of the part
     */
    public void setPrice(double price) {
        this.price = price;
    }
    
    /**
     * Sets the number of part items in stock
     * 
     * @param stock an int representing the number of parts currently in stock
     */
    public void setStock(int stock) {
        this.stock = stock;
    }
    
    /**
     * Sets the minimum number of part items kept in stock
     * 
     * @param min an int representing the minimum number of part items kept in stock
     */
    public void setMin(int min) {
        this.min = min;
    }
    
    /**
     * Sets the maximum number of part items kept in stock
     * 
     * @param max an int representing the maximum number of part items kept in stock
     */
    public void setMax(int max) {
        this.max = max;
    }
    
    /**
     * 
     * @return an int representing the part id number
     */
    public int getId() {
        return id;
    }
    
    /**
     * 
     * @return a String representing the name of the part
     */
    public String getName() {
        return name;
    }
    
    /**
     * 
     * @return a double representing the price of the part
     */
    public double getPrice() {
        return price;
    }
    
    /**
     * 
     * @return an int representing the number of part items currently in stock
     */
    public int getStock() {
        return stock;
    }
    
    /**
     * 
     * @return an int representing the minimum number of part items kept in inventory
     */
    public int getMin() {
        return min;
    }
    
    /**
     * 
     * @return an int representing the maximum number of part items kept in inventory
     */
    public int getMax() {
        return max;
    }
}
