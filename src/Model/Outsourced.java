/*
 * Built as part of the class requirements for
 * Western Governor's University Software I - C482.
 *
 */
package Model;

/**
 * Represents an outsourced part in the company's inventory.
 * 
 * @author walterallen
 */
public class Outsourced extends Part {
    private String companyName;

    /**
     * Class constructor.
     * 
     * @param id an int representing the part id
     * @param name a String representing the part name
     * @param price a double representing the price of the part
     * @param stock an int representing the number of parts in stock
     * @param min an int representing the minimum number of parts that can be 
     * on hand in inventory
     * @param max an int representing the maximum number of parts that can be on
     * hand in inventory
     * @param companyName a String representing the company name from which the
     * part is sourced
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }
    
    /**
     * sets the name of the company from which the product is sourced.
     * 
     * @param companyName a String representing the name of the company.
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    
    /**
     * 
     * @return a String representing the name of the company from which the
     * product is sourced.
     */
    public String getCompanyName() {
        return companyName;
    }
    
    /**
     * Returns a string representation of the outsourced part that includes the
     * part id, the part name, the price, the number of parts in stock, the minimum
     * number of parts on hand, the maximum number of parts on hand, and the
     * company name from which the product is sourced.
     * 
     * @return a String representation of the Part Instance
     */
    @Override
    public String toString() {
        return (getId() + " " + getName() + " " + getPrice() + " " + getStock() 
                + " " + getMin() + " " + getMax() + " " + getCompanyName());
    }
}
