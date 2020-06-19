/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ertektar.ui.showertek;

import com.jfoenix.controls.JFXButton;
import ertektar.db.Db;
import ertektar.model.Ertek;
import ertektar.util.ButtonBuilder;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * FXML Controller class
 *
 * @author Cz.Csaba
 */
public class Show_ertekController implements Initializable {

    ButtonBuilder btnBuilder;
    Db db;
    List<Ertek> erteks;

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
   /* @FXML
    private Text txtMain;*/
    @FXML
    private Label lblContent;
    @FXML
    private ImageView imv1;
    @FXML
    private ImageView imv2;
    @FXML
    private ImageView imv3;
    @FXML
    private Pane basePane;
    @FXML
    private JFXButton btnBack;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("init");
        try {
            db = new Db();
            erteks = db.getAllErtek();
            System.out.println("betöltve:"+erteks.size());
            loadButtonsFromDb();
        } catch (SQLException ex) {
            System.out.println("miaf");
            Logger.getLogger(Show_ertekController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void loadButtonsFromDb() {
        erteks = db.getAllErtek();

        for (Ertek b : erteks) {
            Button mapbutton = new Button();
            mapbutton.setId(b.getId() + "");
            mapbutton.setLayoutX(b.getX());
            mapbutton.setLayoutY(b.getY());
            mapbutton.setMinSize(props.buttonWidth, 0);
            baseMapPane.getChildren().add(mapbutton);
            System.out.println("Hozzáadva");
            mapbutton.setOnAction((actionEvent) -> this.openErtek(actionEvent));

        }

    }

    private void openErtek(ActionEvent actionEvent) {
        Ertek e = getErtekByBtn(actionEvent);
        lblErtekTitle.setText(e.getTitle());
        //txtMain.setText(e.getText());
        //txtMain.;
        lblContent.setText(e.getText());
        lblContent.setTextAlignment(TextAlignment.JUSTIFY);
        ertekPane.setVisible(true);
        baseMapPane.setVisible(false);
        
        
    }

    private Ertek getErtekByBtn(ActionEvent event) {
        Button b = (Button) event.getSource();
        try {
            Ertek e = db.pstmGetErtekById(Integer.parseInt(b.getId()));
            return e;
        } catch (Exception e) {
            System.out.println("Ban van a button Idnél, id:");
            return null;
        }

    }

    @FXML
    private void backToMap(ActionEvent event) {
         ertekPane.setVisible(false);
        baseMapPane.setVisible(true);
    }

}
