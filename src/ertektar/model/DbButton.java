/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ertektar.model;

/**
 *
 * @author Cz.Csaba
 */
public class DbButton {
    private int id;
    private double X;
    private double y;
    private Ertek ertek;

    public DbButton() {
    }

    public DbButton(int id, double x, double y, Ertek ertek) {
        this.id = id;
        X = x;
        this.y = y;
        this.ertek = ertek;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getX() {
        return X;
    }

    public void setX(double x) {
        X = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Ertek getErtek() {
        return ertek;
    }

    public void setErtek(Ertek ertek) {
        this.ertek = ertek;
    }
}
