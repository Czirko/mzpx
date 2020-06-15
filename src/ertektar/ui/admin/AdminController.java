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
    List<Ertek> erteks;
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
    private ChoiceBox<String> categoryChooser;
    @FXML
    private JFXButton btnEdit;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            db = new Db();
            erteks = db.getAllErtek();
            bBuilder = new ButtonBuilder(mapPane, selErtek, db,  builderPane);
        } catch (SQLException ex) {
            System.out.println("Baj a db-vel: " + ex.toString());
        }
        loadChooserData();
        tbUtil = new TableViewUtil(tblErtek, erteks);
        tbUtil.setTableData();

    }

    @FXML
    public void goButtonMake(ActionEvent event) {
        if (selErtek != null) {
            bBuilder.setErtek(selErtek);
            saveErtek();
            builderPane.setVisible(false);
            
            bBuilder.takeButtonOnMap();

        } else {
            makeAleret("Válassz Értéket!");
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
        categoryChooser.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (selErtek != null) {
                    selErtek.setCategory(newValue);
                }else{
                    makeAleret("Előbb válassz értéket!");
                }
            }
        });

        
    }
    
    private void makeAleret(String msg){
        Alert alert = new Alert(AlertType.NONE,
                    msg, ButtonType.APPLY);
            alert.showAndWait();
        
    }

    private void saveErtek() {
       
    }

    @FXML
    private void showButtons4select(ActionEvent event) {
        builderPane.setVisible(false);
        bBuilder.loadButtonsFromDb();
    }

    @FXML
    private void ErtekEditFromTable(ActionEvent event) {
        Ertek e =  (Ertek) tblErtek.getSelectionModel().getSelectedItem();
        if(e!=null){
            System.out.println(e.getName());
        }else{
            System.out.println("baj a kiválasztáskor");
        }

        
        
    }

}
