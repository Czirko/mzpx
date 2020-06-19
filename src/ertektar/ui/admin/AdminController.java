/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ertektar.ui.admin;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;

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
import java.nio.file.FileSystems;
import java.nio.file.Path;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
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
    @FXML
    private Button btnImg1;
    @FXML
    private VBox vboxImg;
    @FXML
    private AnchorPane editPane;
    @FXML
    private JFXTextArea txaContent;
    @FXML
    private JFXButton btn1;
    @FXML
    private JFXButton btn2;
     @FXML
    private ImageView btnimg2;
    @FXML
    private ImageView btnimg2;
    @FXML
    private JFXButton btnimg3;
    @FXML
    private Label lblTitle;
    @FXML
    private Label lblCat;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            db = new Db();
            erteks = db.getAllErtek();
            bBuilder = new ButtonBuilder(mapPane, selErtek, db,  builderPane,tblErtek);
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
            bBuilder.setErtek(e);
            bBuilder.setButton(e, true);
            builderPane.setVisible(false);
            
        }else{
            System.out.println("baj a kiválasztáskor");
        }

        
        
    }

    @FXML
    private void setImage(ActionEvent event) {
        if(selErtek!=null){
            editPane.setVisible(true);
            builderPane.setVisible(false);
            
        Image img;
        File f ;
        try {
            f = getFileChooser();
            Path p = FileSystems.getDefault().getPath(f.getAbsolutePath());
            img = createImage(p, 300, 40);
            ImageView imv = new ImageView(img);
            if(img!=null){
            
            vboxImg.getChildren().add(0, imv);
            vboxImg.getChildren().remove(1);
            selErtek.setImgFile1(f);
            selErtek.setImg1(img);
             }
        } catch (Exception ex) {
           // Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Gond a changerben "+ex);
        }
        
        }else{
            makeAleret("Előbb válassz értéket!");
        }
        
        
    }
    
     public Image createImage(Path imagePath,double maxWidth, double maxHight) throws Exception{
        return new Image(imagePath.toFile().toURI().toURL().toExternalForm(),0d,maxHight*2,true,false,true);

    }
    
    public File getFileChooser() {
        try {
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Válaszd ki a képet");
            
            Stage stage = (Stage) basePane.getScene().getWindow();
            File file = chooser.showOpenDialog(stage);
            Path p = FileSystems.getDefault().getPath(file.getAbsolutePath());
            System.out.println("Path: "+ p.toString());
            System.out.println("cannonical "+file.getCanonicalPath());
            System.out.println("Absolute "+file.getAbsolutePath());
            return file;
        } catch (IOException ex) {
            System.out.println("gond a kép feldolgozásánál"+ ex);
        return null;
        }
        
    }

    @FXML
    private void selectImage(ActionEvent event) {
        
        Button b = (Button) event.getSource();
        int id = Integer.parseInt(b.getId());
        ImageView imv = (ImageView) b.getGraphic();
        Image img;
        File f ;
        try {
            f = getFileChooser();
            Path p = FileSystems.getDefault().getPath(f.getAbsolutePath());
            img = createImage(p, 300, 40);
            
            if(img!=null){
        
                imv.setImage(img);
        
        
               
                
                
        
    }
        }catch(Exception ex){
            System.out.println(ex);
        }

}}
