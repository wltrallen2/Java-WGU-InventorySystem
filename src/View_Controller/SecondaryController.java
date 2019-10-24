/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

/**
 *
 * @author walterallen
 */
public abstract class SecondaryController {
    MainController parentController;
    
    public void setParentController(MainController controller) {
        parentController = controller;
    }
}
