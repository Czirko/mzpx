/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ertektar.util;

import ertektar.db.Db;
import ertektar.model.DbButton;
import ertektar.model.Ertek;
import ertektar.ui.admin.AdminController;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author Cz.Csaba
 */
public class ButtonBuilder {

    private double x = 0, y = 0;
    List<Ertek> erteks;
    Pane mapPane;
    Pane adminPane;
    Stage stage;
    Ertek ertek;
    Db db;

    public ButtonBuilder(Pane mapPane, Ertek ertek, Db db, Pane adminPane) {
        this.mapPane = mapPane;
        this.ertek = ertek;
        this.db = db;
        this.adminPane = adminPane;
    }

    public void setErtek(Ertek ertek) {
        this.ertek = ertek;
    }

    public void loadButtonsFromDb() {
        erteks = db.getAllErtek();

        for (Ertek b : erteks) {
            Button mapbutton = new Button();
            mapbutton.setId(b.getId()+"");
            mapbutton.setLayoutX(b.getX());
            mapbutton.setLayoutY(b.getY());
            mapPane.getChildren().add(mapbutton);
            mapbutton.setOnAction((actionEvent) -> this.openErtek(actionEvent));

        }

    }

    public void takeButtonOnMap() {
        Button newB = new Button();
        newB.setText("DragMe");
        mapPane.getChildren().add(newB);
        makeDragable(newB);

        newB.setOnAction(this::saveButtonData);

    }

    @FXML
    public void saveButtonData(ActionEvent event) {
        Button b = (Button) event.getSource();
        //b.setText(tfButtonName.getText());

        b.setOnMousePressed((e) -> {
        });
        b.setOnMouseDragged((ee) -> {
        });
        b.setOnAction(this::openErtek);
        b.setId(ertek.getId() + "");

        ertek.setX(b.getLayoutX());
        ertek.setY(b.getLayoutY());

        //db.updatertek(ertek);
        try {
            int selId = db.addErtek(ertek);
            if (selId >= 0) {
                ertek.setId(selId);
                System.out.println("érték mentve, id-ja" + ertek.getId()
                );

            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Baj az érték mentésével : " + ex.toString());
        }
        // db.addButton(dbButton);
        System.out.println("Button Mentve");

        mapPane.setVisible(false);
        adminPane.setVisible(true);

    }

    private void openErtek(ActionEvent event) {
        Button b = (Button) event.getSource();
        try {
            int id = Integer.parseInt(b.getId());
            System.out.println(id);
        } catch (Exception e) {
            System.out.println(b.getId());
        }
    }

    private void makeDragable(Button button) {

        button.setOnMousePressed(((event) -> {
            System.out.println("setOnmousePressed");
            x = event.getSceneX();
            y = event.getSceneY();
            //System.out.println("oldX:" +x);
            //  System.out.println("oldY: "+y);

        }));

        button.setOnMouseDragged((event) -> {
            // System.out.println("setOnmousedraggwed");
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            button.setLayoutX(event.getSceneX());

            button.setLayoutY(event.getSceneY());

            // System.out.println("newX:" +event.getSceneX());
            // System.out.println("newY: "+event.getSceneY());
        });

    }

}
