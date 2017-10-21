/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pk.codeapp.model;

import javax.swing.ImageIcon;

/**
 *This class is in charge of the create sheet to the tree
 * @author amador
 */
public class Bonus {
    private int  weight;
    private int id;
    private String name;
    private ImageIcon icon;
    private Bonus sigLeft,sigRight;
    /**
     * Create the instance of the class with:
     * @param weight
     * @param id 
     * @param name
     * @param path 
     */
     public Bonus(int weight, int id, String name,String path) {
        this.weight = weight;
        this.id = id;
        this.name = name;
        this.icon = new ImageIcon(path);
    }
    //<editor-fold desc="All getter and setter here" defaultstate="collapsed">

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }

    public Bonus getSigLeft() {
        return sigLeft;
    }

    public void setSigLeft(Bonus sigLeft) {
        this.sigLeft = sigLeft;
    }

    public Bonus getSigRight() {
        return sigRight;
    }

    public void setSigRight(Bonus sigRight) {
        this.sigRight = sigRight;
    }
     //</editor-fold>
}
