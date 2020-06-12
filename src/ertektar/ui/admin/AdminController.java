/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ertektar.ui.admin;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import ertektar.db.Db;
import ertektar.model.DbButton;
import ertektar.model.Ertek;
import ertektar.ui.adminmap.AdminmapController;
import ertektar.util.ButtonBuilder;
import ertektar.util.DocumentBuilder;
import ertektar.util.ErtektarUtil;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Cz.Csaba
 */
public class AdminController implements Initializable {

    
    DocumentBuilder docBuilder;
    List<DbButton> buttons ;
    
    public Ertek selErtek;
    Db db;
    @FXML
    private AnchorPane basePane;
    @FXML
    private JFXTextField tfErtekCim;
    @FXML
    private StackPane basePaneOnAdmin;
    @FXML
    private AnchorPane anchorOnAdmin;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
             db = new Db();
             buttons = db.getAllButton();
        } catch (SQLException ex) {
            System.out.println("Baj a db-vel: "+ex.toString());
        }
    }    

    public void goButtonMake(ActionEvent event) {
        if(selErtek!=null){
            try {
                int selId=db.addErtek(selErtek);
                if(selId>=0){
                selErtek.setId(selId);
                    System.out.println("érték idja"+selErtek.getId()
                    
                    );
               // ErtektarUtil.loadWindow(getClass().getResource("/ertektar/ui/adminmap/adminmap.fxml"), "Notify Users", null,false);
                openAdminMap();
            }
            } catch (SQLException ex) {
                Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Baj az érték mentésével : "+ex.toString());
            }
            }
        
       
        
        
    }

    public void openDocX(ActionEvent event) {
        selErtek = new Ertek();
        
        docBuilder = new DocumentBuilder(basePane, selErtek);
        docBuilder.filePrcess(docBuilder.getFileChooser());
         if (selErtek.getName() != null) {
            tfErtekCim.setText(selErtek.getName());
        }
        
        
    }
    
    public Ertek getSelErtek(){
        return selErtek;
    }

    private void openAdminMap() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ertektar/ui/adminmap/adminmap.fxml"));
            Parent parent = loader.load();
            AdminmapController con = loader.getController();
            con.setErtek(selErtek);
            Stage stage = new Stage(StageStyle.DECORATED);;
            
            
            stage.setTitle("");
            stage.setScene(new Scene(parent,1800,800));
            stage.setFullScreen(false);
            stage.show();
            //setStageIcon(stage);
        } catch (IOException ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    
}
