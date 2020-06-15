/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ertektar.ui.showertek;

import ertektar.db.Db;
import ertektar.util.ButtonBuilder;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Cz.Csaba
 */
public class Show_ertekController implements Initializable {
    
    ButtonBuilder btnBuilder;
    Db db;
    

    @FXML
    private Pane baseMapPane;
    @FXML
    private AnchorPane ertekPane;
    @FXML
    private HBox headerHbox;
    @FXML
    private Label lblCathegory;
    @FXML
    private ImageView imvCategory;
    @FXML
    private Label lblErtekTitle;
    @FXML
    private HBox contentHbox;
    @FXML
    private Text txtMain;
    @FXML
    private ImageView imv1;
    @FXML
    private ImageView imv2;
    @FXML
    private ImageView imv3;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            db= new Db();
        } catch (SQLException ex) {
            Logger.getLogger(Show_ertekController.class.getName()).log(Level.SEVERE, null, ex);
        }
        btnBuilder= new ButtonBuilder(baseMapPane, null, db, ertekPane);
        btnBuilder.loadButtonsFromDb();
    }    
    
    
    
    
}
