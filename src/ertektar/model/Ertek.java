/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ertektar.model;

import java.io.File;
import javafx.scene.image.Image;

/**
 *
 * @author Cz.Csaba
 */
public class Ertek {
    private int id;
    private String name;
    private String title;
    private String text;
    private String category;
     private double X;
    private double y;
    
    private File imgFile1;
    private Image img1;

    public Ertek() {
    }

    public Ertek(int id, String name, String title, String text, String category, double X, double y, File imgFile1) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.text = text;
        this.category = category;
        this.X = X;
        this.y = y;
        this.imgFile1 = imgFile1;
    }

    public Ertek(int id, String name, String title, String text, String category, double X, double y, Image img1) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.text = text;
        this.category = category;
        this.X = X;
        this.y = y;
        this.img1 = img1;
    }
    
    

   
    
    

    public Ertek(int id, String name, String title, String text, String category) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.text = text;
        this.category = category;
    }

    public Ertek(int id, String name, String title, String text, String category, double X, double y) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.text = text;
        this.category = category;
        this.X = X;
        this.y = y;
    }
    
    

    public Ertek(String name, String title, String text, String category) {
        this.name = name;
        this.title = title;
        this.text = text;
        this.category = category;
    }

    public Ertek(String name, String title, String text) {
        this.name = name;
        this.title = title;
        this.text = text;
    }

    public Ertek(int id, String title, String text) {
        this.id = id;
        this.title = title;
        this.text = text;
    }

    public Ertek(String title, String text) {
        this.title = title;
        this.text = text;
    }

    public int getId() {

        return id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getX() {
        return X;
    }

    public void setX(double X) {
        this.X = X;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public File getImgFile1() {
        return imgFile1;
    }

    public void setImgFile1(File imgFile1) {
        this.imgFile1 = imgFile1;
    }

    public Image getImg1() {
        return img1;
    }

    public void setImg1(Image img1) {
        this.img1 = img1;
    }

   
    
    
    
    
}
