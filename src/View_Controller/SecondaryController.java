/*
 * Built as part of the class requirements for
 * Western Governor's University Software I - C482.
 *
 */
package View_Controller;

/**
 * An abstract class which allows implementing subclasses to access one public
 * instance variable, a parent controller.
 * 
 * @author walterallen
 */
public abstract class SecondaryController {
    MainController parentController;
    
    /**
     * Sets the parent controller for this Secondary Controller instance.
     * 
     * @param controller a MainController instance representing the parent controller
     * that will be presenting this SecondaryController instance.
     */
    public void setParentController(MainController controller) {
        parentController = controller;
    }
}
