/*
 * Built as part of the class requirements for
 * Western Governor's University Software I - C482.
 *
 */
package View_Controller;

import Model.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

/**
 * FXML Controller class, a subclass of SecondaryController,
 * that allows the user to add a new part or modify the
 * information for an existing part.
 *
 * @author walterallen
 */
public class AddModifyPartController extends SecondaryController implements Initializable {

    @FXML private Label screenLabel;
    
    @FXML private ToggleGroup Source;
    @FXML private RadioButton sourceRadioInHouse;
    @FXML private RadioButton sourceRadioOutSourced;
    
    @FXML private Label idLabel;
    @FXML private TextField nameTextField;
    @FXML private TextField invTextField;
    @FXML private TextField priceTextField;
    @FXML private TextField maxTextField;
    @FXML private TextField minTextField;
    @FXML private TextField extraTextField;
    @FXML private Label extraLabel;
    
    @FXML private Button saveButton;
    @FXML private Button cancelButton;
    
    private Part currentPart;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    /**
     * Saves the form's part or product into the inventory. The method identifies
     * whether the user has selected the In-House or Outsources radio button and
     * creates a new Part or Product from the data in the user fields. Then, if the
     * user navigated to this scene by selecting a current part, the method
     * overwrites the part in the inventory with the new information. Else,
     * the method saves the new part to the inventory. Finally, the method
     * closes (hides) the scene.
     * 
     * @param event the ActionEvent that called the method
     */
    @FXML
    private void saveHandler(ActionEvent event) {
        String name = nameTextField.getText();
        int stock = new Integer(invTextField.getText());
        double price = new Double(priceTextField.getText());
        int min = new Integer(minTextField.getText());
        int max = new Integer(maxTextField.getText());
        
        
        Part part;
        if(Source.getSelectedToggle() == sourceRadioInHouse) {
            int machineId = new Integer(extraTextField.getText());
            part = new InHouse(0, name, price, stock, min, max, machineId);
        } else {
            String companyName = extraTextField.getText();
            part = new Outsourced(0, name, price, stock, min, max, companyName);
        }
        
        Inventory inventory = parentController.getInventory();
        if(currentPart == null) {
            int id = inventory.getNextIdNumberForPart();
            part.setId(id);
            inventory.addPart(part);
        } else {
            part.setId(currentPart.getId());
            int index = inventory.getAllParts().indexOf(currentPart);
            inventory.updatePart(index, part);
        }
        
        cancelButton.getScene().getWindow().hide();
    }

    /**
     * Closes (hides) the scene.
     * 
     * @param event the ActionEvent that called the method
     */
    @FXML
    private void cancelHandler(ActionEvent event) {
        cancelButton.getScene().getWindow().hide();
    }
    
    /**
     * Updates the GUI to display the correct fields for either a in-house or
     * outsourced part.
     * 
     * @param event the ActionEvent that called the method
     */
    @FXML
    private void radioHandler(ActionEvent event) {
        updateDisplayForRadioHandler();
    }
    
    /********************************************************
     * GETTERS/SETTERS
     *********************************************************/

    /**
     * Sets the current part for the form and populates the user fields with
     * the information from the Part instance.
     * 
     * @param part a Part instance representing the part which the user would
     * like to modify.
     */
    public void setCurrentPart(Part part) {
        this.currentPart = part;
        populateTextFieldsWithCurrentPart();
    }
    
    /**
     * Sets the label at the top of the scene to identify the purpose of the
     * scene to the user. Typically used to identify whether the screen is meant
     * to modify an existing part or add a new part.
     * 
     * @param text a String representing the text to display in the label
     */
    public void setScreenLabel(String text) {
        screenLabel.setText(text);
    }
        
    /********************************************************
     * HELPER METHODS
     *********************************************************/
        
    /**
     * Updates the text for the extraLabel, which indicates whether the form is
     * displaying the company name for an outsourced part or a machine id for an
     * in-house part.
     */
    private void updateDisplayForRadioHandler() {
        if(Source.getSelectedToggle().equals(sourceRadioOutSourced)) {
            extraLabel.setText("Company Name");
        } else {
            extraLabel.setText("Machine ID");
        }
    }
    
    /**
     * Populates the text fields with data from the Part stored in the currentPart
     * instance variable. First, the method identifies whether the currentPart
     * is outsourced or in-house and adjusts the display accordingly. Then the
     * method sets the labels and text fields with the appropriate information.
     */
    public void populateTextFieldsWithCurrentPart() {
        if(currentPart != null) {
            if(currentPart instanceof Outsourced) {
                Source.selectToggle(sourceRadioOutSourced);
                updateDisplayForRadioHandler();
                
                Outsourced outsourced = (Outsourced) currentPart;
                extraTextField.setText(outsourced.getCompanyName());
            } else {
                InHouse inhouse = (InHouse) currentPart;
                extraTextField.setText("" + inhouse.getMachineId());
            }
            
            idLabel.setText("" + currentPart.getId());
            nameTextField.setText(currentPart.getName());
            invTextField.setText("" + currentPart.getStock());
            priceTextField.setText("" + currentPart.getPrice());
            maxTextField.setText("" + currentPart.getMax());
            minTextField.setText("" + currentPart.getMin());
        }
    }
}
