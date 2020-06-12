/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ertektar.db;

import ertektar.model.Dao;
import ertektar.model.DbButton;
import ertektar.model.Ertek;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Cz.Csaba
 */
public class Db implements Dao{

    final String URL = "jdbc:mysql://localhost:3306/ertektar";
    final String USERNAME = "root";
    final String PASSWORD = "1234";

    private PreparedStatement pstmtGetAllErtek;
    private PreparedStatement pstmtRemoveErtek;
    private PreparedStatement pstmtUpdateErtek;
    private PreparedStatement pstmtAddErtek;
    private PreparedStatement pstmGetErtekById;

    private PreparedStatement pstmtGetAllButton;
    private PreparedStatement pstmtRemoveButton;
    private PreparedStatement pstmtUpdateButton;
    private PreparedStatement pstmtAddButton;

    Connection conn = null;
    Statement crStment = null;
    DatabaseMetaData dbmd = null;

    public Db() throws SQLException
    
    {
        openConn();

        if (conn != null) {
       
               
           
            pstmtGetAllErtek = conn.prepareStatement("SELECT * FROM ertek");
            pstmtRemoveErtek = conn.prepareStatement("DELETE FROM ertek WHERE id=?");
            pstmGetErtekById = conn.prepareStatement("SELECT FROM ertek where id=?");
            pstmtUpdateErtek = conn.prepareStatement("UPDATE ertek SET name=?,title=?,text=?,category=? WHERE id=?");
            pstmtAddErtek = conn.prepareStatement("INSERT INTO ertek (name,title,text,category) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

            pstmtGetAllButton = conn.prepareStatement("SELECT * FROM mapbutton");
            pstmtRemoveButton = conn.prepareStatement("DELETE FROM mapbuton WHERE id=?");
            pstmtUpdateButton = conn.prepareStatement("UPDATE mapbutton SET x=?,y=?,ertekid=? WHERE id=?");
            pstmtAddButton = conn.prepareStatement("INSERT INTO mapbutton (x,y,ertekid) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);


        }


    }
   private void openConn(){
        try {

            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException ex) {
            //Logger.getLogger(Db.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("gáz van a connectionnel " + ex);
        }
       
   }

    public ArrayList<Ertek> getAllErtek() {
        //String sql = "SELECT * FROM ertek";
        ArrayList<Ertek> erteks = new ArrayList<>();
        try {
            ResultSet rs = pstmtGetAllErtek.executeQuery();
            while (rs.next()) {
                Ertek e = new Ertek(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("title"),
                        rs.getString("text"),
                        rs.getString("category"));
                erteks.add(e);

            }
        } catch (SQLException ex) {
            Logger.getLogger(Db.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("baj van a kiolvasáskor " + ex);
        }
        return erteks;

    }

    public Ertek pstmGetErtekById(int i) throws SQLException {
        pstmGetErtekById.setInt(1, i);
        ResultSet rs = pstmtGetAllErtek.executeQuery();


        Ertek e = new Ertek(rs.getInt("id"),
                rs.getString("name"),
                rs.getString("title"),
                rs.getString("text"),
                rs.getString("category"));


        return e;
    }

    public int addErtek(Ertek e) throws SQLException {

        pstmtAddErtek.setString(1, e.getName());
        pstmtAddErtek.setString(2, e.getTitle());
        pstmtAddErtek.setString(3, e.getText());
        pstmtAddErtek.setString(4, e.getCategory());
        //pstmtAddErtek.set
        pstmtAddErtek.executeUpdate();
        ResultSet rs = pstmtAddErtek.getGeneratedKeys();

        System.out.println("Mentve az új Érték");
        if (rs.next()) {
            return rs.getInt(1);
        } else
            return -1;

    }

    @Override
    public int removeErtek(Ertek e) throws SQLException {
        pstmtRemoveErtek.setInt(1, e.getId());

        return pstmtRemoveErtek.executeUpdate();
    }

    @Override
    public int updatertek(Ertek e) throws SQLException {
        pstmtUpdateErtek.setString(1, e.getName());
        pstmtUpdateErtek.setString(2, e.getTitle());
        pstmtUpdateErtek.setString(3, e.getText());
        pstmtUpdateErtek.setString(4, e.getCategory());
        pstmtUpdateErtek.setInt(5, e.getId());
        return pstmtUpdateErtek.executeUpdate();


    }

    @Override
    public List<DbButton> getAllButton()  {
            List<DbButton> btns = new ArrayList<>();
        try {
            
            ResultSet rs = pstmtGetAllButton.executeQuery();
            
            while (rs.next()) {
                DbButton dbb = new DbButton(rs.getInt("id"),
                        rs.getDouble("x"),
                        rs.getDouble("y"),
                        pstmGetErtekById(rs.getInt("ertekid")));
                btns.add(dbb);
                
                
            }
            System.out.println("Buttonok betöltve: " +btns.size());
        } catch (SQLException ex) {
            System.out.println("Baj a buttonok betöltésével");
            Logger.getLogger(Db.class.getName()).log(Level.SEVERE, null, ex);
        }
            return btns;
        
    }



    @Override
    public int addButton(DbButton b) throws SQLException {
        pstmtAddButton.setDouble(1,b.getX());
        pstmtAddButton.setDouble(2,b.getY());
        pstmtAddButton.setInt(3,b.getErtek().getId());
        pstmtAddButton.executeUpdate();
        ResultSet rs = pstmtAddButton.getGeneratedKeys();

        System.out.println("Mentve az új Button");
        if (rs.next()) {
            return rs.getInt(1);
        }
        else
            return -1;


    }

    @Override
    public int removeButton(DbButton b) throws SQLException {
        pstmtRemoveButton.setInt(1,b.getId());

        return pstmtRemoveButton.executeUpdate();
    }

    @Override
    public int updateButton(DbButton b) throws SQLException {

        pstmtUpdateButton.setDouble(1,b.getX());
        pstmtUpdateButton.setDouble(2,b.getY());
        pstmtUpdateButton.setInt(3,b.getErtek().getId());
        pstmtUpdateButton.setInt(4,b.getId());
        return pstmtAddButton.executeUpdate();

    }
    
}
