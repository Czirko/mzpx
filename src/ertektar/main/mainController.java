/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ertektar.main;

import ertektar.util.ErtektarUtil;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Cz.Csaba
 */
public class mainController implements Initializable {

    @FXML
    private Button btnstartEnter;
    @FXML
    private Button btnAdminOnStart;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void enterToMainMap(ActionEvent event) {
        ErtektarUtil.loadWindow(getClass().getResource("/ertektar/mainmap/mainMap.fxml"), "Notify Users", null,false);
        
    }

    @FXML
    private void toAdmin(ActionEvent event) {
        ErtektarUtil.loadWindow(getClass().getResource("/ertektar/ui/admin/admin.fxml"), "Notify Users", null,false);
        
    }
    
}
