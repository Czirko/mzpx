/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ertektar.util;

import ertektar.db.Db;
import ertektar.model.DbButton;
import ertektar.model.Ertek;
import java.sql.SQLException;
import java.util.List;
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
    List<DbButton> buttons;
    Pane pane;
    Pane adminPane;
    Stage stage;
    Ertek ertek;
    Db db;

    public ButtonBuilder(Pane pane, Ertek ertek, Db db, List<DbButton> buttons,Pane adminPane) {
        this.pane = pane;
        this.ertek = ertek;
        this.db = db;
        this.adminPane=adminPane;
    }

    public void loadButtonsFromDb() {
        buttons = db.getAllButton();

        for (DbButton b : buttons) {
            Button mapbutton = new Button();
            mapbutton.setLayoutX(b.getX());
            mapbutton.setLayoutX(b.getX());
            pane.getChildren().add(mapbutton);

        }


    }

    public void takeButtonOnMap() {
        Button newB = new Button();
        newB.setText("DragMe");
        pane.getChildren().add(newB);
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
       // b.setId();

        DbButton dbButton = new DbButton();
        dbButton.setX(b.getLayoutX());
        dbButton.setY(b.getLayoutY());
        dbButton.setErtek(ertek);
        System.out.println(ertek.getName()+"   "+ertek.getId());

        try {
            db.addButton(dbButton);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        pane.setVisible(false);
        adminPane.setVisible(true);

    }

    private void openErtek(ActionEvent actionEvent) {
        System.out.println();
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
             System.out.println("setOnmousedraggwed");
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            button.setLayoutX(event.getSceneX());

            button.setLayoutY(event.getSceneY());

            // System.out.println("newX:" +event.getSceneX());
            // System.out.println("newY: "+event.getSceneY());


        });


    }
    
}
