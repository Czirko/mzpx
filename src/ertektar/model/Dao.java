/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ertektar.model;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Cz.Csaba
 */
public interface Dao {
    List<Ertek> getAllErtek() throws SQLException;
    int addErtek(Ertek e) throws SQLException;
    int removeErtek(Ertek e) throws SQLException;
    int updatertek(Ertek e) throws SQLException;


    List<DbButton> getAllButton() throws SQLException;
    int addButton(DbButton b) throws SQLException;
    int removeButton(DbButton b) throws SQLException;
    int updateButton(DbButton b) throws SQLException;
}
