/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ertektar.db;

import ertektar.model.Dao;
import ertektar.model.DbButton;
import ertektar.model.Ertek;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Blob;
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
import javafx.scene.image.Image;
import javax.imageio.ImageIO;

/**
 *
 * @author Cz.Csaba
 */
public class Db implements Dao {

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
    private PreparedStatement pstmGetButtonByErtekID;

    Connection conn = null;
    Statement crStment = null;
    DatabaseMetaData dbmd = null;

    public Db() throws SQLException {
        openConn();

        if (conn != null) {

            pstmtGetAllErtek = conn.prepareStatement("SELECT * FROM ertek");
            pstmtRemoveErtek = conn.prepareStatement("DELETE FROM ertek WHERE id=?");
            pstmGetErtekById = conn.prepareStatement("SELECT * FROM ertek where id=?");
            pstmtUpdateErtek = conn.prepareStatement("UPDATE ertek SET name=?,title=?,text=?,category=?,x=?,y=?,img1=? WHERE id=?");
            pstmtAddErtek = conn.prepareStatement("INSERT INTO ertek (name,title,text,category,x,y,img1) VALUES (?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

        }

    }

    private void openConn() {
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
                        rs.getString("category"),
                        rs.getDouble("x"),
                        rs.getDouble("y"),
                        getImageFromBolb(rs.getBlob("img1")));
                erteks.add(e);

            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(Db.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("baj van a kiolvasáskor " + ex);
        }

        return erteks;

    }

    public Ertek pstmGetErtekById(int i) {

        try {
            pstmGetErtekById.setInt(1, i);
            ResultSet rs = pstmGetErtekById.executeQuery();

            while (rs.next()) {
                Ertek e = new Ertek(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("title"),
                        rs.getString("text"),
                        rs.getString("category"),
                        rs.getDouble("x"),
                        rs.getDouble("y"),
                        getImageFromBolb(rs.getBlob("img1")));
                return e;
            }
            rs.close();

        } catch (SQLException ex) {
            Logger.getLogger(Db.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("getErtekById problem,... ID=" + i);
        }
        return null;
    }

    public int addErtek(Ertek e) throws SQLException {
        FileInputStream fin = null;
        ResultSet rs=null;
        try {
            pstmtAddErtek.setString(1, e.getName());
            pstmtAddErtek.setString(2, e.getTitle());
            pstmtAddErtek.setString(3, e.getText());
            pstmtAddErtek.setString(4, e.getCategory());
            pstmtAddErtek.setDouble(5, e.getX());
            pstmtAddErtek.setDouble(6, e.getY());
            fin = new FileInputStream(e.getImgFile1());
            pstmtAddErtek.setBinaryStream(7, fin, (int) e.getImgFile1().length());

            pstmtAddErtek.executeUpdate();
            rs = pstmtAddErtek.getGeneratedKeys();

            System.out.println("Mentve az új Érték");
            System.out.println(rs.toString());
            int id=-1 ;
            while (rs.next()) {

                id = rs.getInt(1);
                

            }
                return id;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Db.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Gond az img1 Streamelésénél " + ex);
            return -1;
        } finally {

            try {
                rs.close();
                fin.close();
            } catch (IOException ex) {
                System.out.println("fin==null");
                
            }
        }
    }

    @Override
    public int removeErtek(Ertek e) throws SQLException {
        pstmtRemoveErtek.setInt(1, e.getId());

        return pstmtRemoveErtek.executeUpdate();
    }

    @Override
    public int updatertek(Ertek e) {
        FileInputStream fin;
        try {
            pstmtUpdateErtek.setString(1, e.getName());
            pstmtUpdateErtek.setString(2, e.getTitle());
            pstmtUpdateErtek.setString(3, e.getText());
            pstmtUpdateErtek.setString(4, e.getCategory());
            pstmtUpdateErtek.setDouble(5, e.getX());
            pstmtUpdateErtek.setDouble(6, e.getY());
            pstmtUpdateErtek.setInt(7, e.getId());
            fin = new FileInputStream(e.getImgFile1());
            pstmtAddErtek.setBinaryStream(7, fin, (int) e.getImgFile1().length());
            return pstmtUpdateErtek.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Db.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Baj van az érték Update-jével: " + ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Db.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Baj az upDateNél");
        }
        return -1;

    }

    private Image getImageFromBolb(Blob b) {
        try {

            return new Image(b.getBinaryStream());
        } catch (SQLException ex) {
            Logger.getLogger(Db.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Baj a Kép importálásával");
            return null;
        }
    }

}
