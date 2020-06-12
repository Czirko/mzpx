/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ertektar.ui.adminmap;

import ertektar.db.Db;
import ertektar.model.DbButton;
import ertektar.model.Ertek;
import ertektar.ui.admin.AdminController;
import ertektar.util.ButtonBuilder;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author Cz.Csaba
 */
public class AdminmapController implements Initializable {

    @FXML
    private StackPane basePaneOnAdmin;
    @FXML
    private AnchorPane anchorOnAdmin;
    Ertek selErtek;
    
    Db db;
    List<DbButton> buttons;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            db= new Db();
            buttons=db.getAllButton();
            System.out.println(selErtek.getId()+" adminmapCont ");
        } catch (SQLException ex) {
            Logger.getLogger(AdminmapController.class.getName()).log(Level.SEVERE, null, ex);
        }
        addNewButton();
    }    
    
    public void setErtek(Ertek e ){
        selErtek=e;
        
    }
    
    private void addNewButton() {
        ButtonBuilder btnBuilder = new ButtonBuilder(basePaneOnAdmin, selErtek, db, buttons, anchorOnAdmin);
        btnBuilder.takeButtonOnMap();
    }
    
}
