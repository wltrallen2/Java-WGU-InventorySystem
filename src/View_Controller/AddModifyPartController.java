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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

/**
 * FXML Controller class
 *
 * @author walterallen
 */
public class AddModifyPartController extends SecondaryController implements Initializable {

    @FXML
    private Label screenLabel;
    @FXML
    private RadioButton sourceRadioInHouse;
    @FXML
    private ToggleGroup Source;
    @FXML
    private RadioButton sourceRadioOutSourced;
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
    private TextField extraTextField;
    @FXML
    private Label extraLabel;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    
    private Part currentPart;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void saveHandler(ActionEvent event) throws IOException {
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

    @FXML
    private void cancelHandler(ActionEvent event) throws IOException {
        cancelButton.getScene().getWindow().hide();
    }
    
    @FXML
    private void radioHandler(ActionEvent event) {
        updateDisplayForRadioHandler();
    }
    
    /********************************************************
     * GETTERS/SETTERS
     *********************************************************/

    public void setCurrentPart(Part part) {
        this.currentPart = part;
        populateTextFieldsWithCurrentPart();
    }
    
    public void setScreenLabel(String text) {
        screenLabel.setText(text);
    }
        
    /********************************************************
     * HELPER METHODS
     *********************************************************/
        
    private void updateDisplayForRadioHandler() {
        if(Source.getSelectedToggle().equals(sourceRadioOutSourced)) {
            extraLabel.setText("Company Name");
        } else {
            extraLabel.setText("Machine ID");
        }
    }
    
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
