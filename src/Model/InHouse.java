/*
 * Built as part of the class requirements for
 * Western Governor's University Software I - C482.
 *
 */
package Model;

/**
 * Represents an in-house item in the inventory. 
 * 
 * @author walterallen
 */
public class InHouse extends Part {
    
    private int machineId;

    /**
     * Class constructor.
     * 
     * @param id        an integer representing the part id number
     * @param name      a String object representing the name of the part
     * @param price     a double representing the price of the part
     * @param stock     an integer representing the number of these parts in stock
     * @param min       an integer representing the minimum number of this part kept on hand
     * @param max       an integer representing the maximum number of this part kept on hand
     * @param machineId an integer representing the machine id number for this part
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }
    
    /**
     * Set the machine id number for this part
     * 
     * @param machineId an integer representing the new machine id number
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
    
    /**
     * Returns the associated machine id for this part
     * 
     * @return the integer representing the associated machine id number
     */
    public int getMachineId() {
        return machineId;
    }
    
    /**
     * Returns a string representation of this in-house part that includes the
     * part&#39s id number, name, price, stock, minimum quantity on hand, maximum
     * quanitity on hand, and machine id number.
     * 
     * @return a string representation of this in-house part.
     */
    @Override
    public String toString() {
        return (getId() + " " + getName() + " " + getPrice() + " " + getStock() 
                + " " + getMin() + " " + getMax() + " " + getMachineId());
    }
}
