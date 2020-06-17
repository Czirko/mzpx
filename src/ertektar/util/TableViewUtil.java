/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ertektar.util;

import ertektar.model.DbButton;
import ertektar.model.Ertek;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

/**
 *
 * @author CzirkO
 */
public class TableViewUtil {

    private TableView tbv;
    private List<Ertek> erteks;

    public TableViewUtil(TableView tbv, List<Ertek> erteks) {
        this.tbv = tbv;
        this.erteks = erteks;
    }

    public TableViewUtil(TableView tbv) {
        this.tbv = tbv;
        
    }
    public void refreshData(List<Ertek> erteks){
        this.erteks=erteks;
        setTableData();
    }
    

    public void setTableData() {

        TableColumn nameColumn = new TableColumn("Vezetéknév");
        nameColumn.setMinWidth(100);
        nameColumn.setCellValueFactory(new PropertyValueFactory<Ertek, String>("name"));
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        TableColumn catColumn = new TableColumn("Vezetéknév");
        catColumn.setMinWidth(100);
        catColumn.setCellValueFactory(new PropertyValueFactory<Ertek, String>("category"));
        catColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        catColumn.setStyle("-fx-text-fill: red;");

        tbv.getColumns().addAll(nameColumn, catColumn);
        tbv.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tbv.getSelectionModel().setCellSelectionEnabled(true);

        tbv.setItems(getData(erteks));

    }

    private ObservableList<Ertek> getData(List<Ertek> erteks) {
        ObservableList<Ertek> data
                = FXCollections.observableArrayList();
        data.addAll(erteks);

        return data;
    }

}
