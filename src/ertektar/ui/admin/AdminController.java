/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ertektar.ui.admin;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import ertektar.db.Db;
import ertektar.model.DbButton;
import ertektar.model.Ertek;

import ertektar.util.ButtonBuilder;
import ertektar.util.DocumentBuilder;
import ertektar.util.ErtektarUtil;
import ertektar.util.TableViewUtil;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
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
    ButtonBuilder bBuilder;
    TableViewUtil tbUtil;
    List<DbButton> buttons;
    ObservableList catList = FXCollections.observableArrayList();
   

    public Ertek selErtek;
    Db db;
    @FXML
    private Pane basePane;
    @FXML
    private JFXTextField tfErtekCim;
    @FXML
    private Pane mapPane;
    @FXML
    private AnchorPane builderPane;
    @FXML
    private JFXButton btnOpenDocx;
    @FXML
    private JFXButton btnTovább;
    @FXML
    private JFXButton btnMégse;
    @FXML
    private JFXButton btn4ErtekSelecting;
    @FXML
    private TableView<?> tblErtek;
    @FXML
    private ChoiceBox<?> categoryChooser;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            db = new Db();
            buttons = db.getAllButton();
        } catch (SQLException ex) {
            System.out.println("Baj a db-vel: " + ex.toString());
        }
        tbUtil=new TableViewUtil(tblErtek, buttons);
        tbUtil.setTableData();
        
    }

    @FXML
    public void goButtonMake(ActionEvent event) {
        if (selErtek != null) {
            saveErtek();
            builderPane.setVisible(false);
            bBuilder=new ButtonBuilder(mapPane, selErtek, db, buttons, mapPane);
            bBuilder.takeButtonOnMap();
            
        }else{
            Alert alert = new Alert(AlertType.NONE,  
                              "Válassz Értéket!",ButtonType.APPLY); 
            alert.showAndWait();
        }

    }

    @FXML
    public void openDocX(ActionEvent event) {
        docBuilder = new DocumentBuilder(basePane, selErtek);
        File f = docBuilder.getFileChooser();
        if (f != null) {
            selErtek = docBuilder.filePrcess(f);
            
        }
        if (selErtek.getName() != null) {
            tfErtekCim.setText(selErtek.getName());
        }

    }

    private void loadChooserData() {
        catList.clear();
        List<String> list = new ArrayList<>(Arrays.asList("Természeti Értékek", "Agrár-és Élelmiszergazdaság", "Épített Kulturális Örökség", "Kulturális Örökség", "Sport", "Ipari és Műszaki Megoldások"));
        catList.addAll(list);
        categoryChooser.setItems(catList);
        categoryChooser.valueProperty().addListener(
        new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {

                if(selErtek!=null)
                    selErtek.setCategory(newValue);
            }

           
        });

    }

    private void saveErtek() {
       try {
                int selId = db.addErtek(selErtek);
                if (selId >= 0) {
                    selErtek.setId(selId);
                    System.out.println("érték idja" + selErtek.getId()
                    );

                }
            } catch (SQLException ex) {
                Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Baj az érték mentésével : " + ex.toString());
            }
    }

    @FXML
    private void showButtons4elect(ActionEvent event) {
    }

}
