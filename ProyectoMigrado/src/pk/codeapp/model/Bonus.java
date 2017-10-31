/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pk.codeapp.model;

import java.applet.AudioClip;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *This class is in charge of the create sheet to the tree
 * @author amador
 */
public class Bonus implements Serializable{
    private int  weight;
    private int id;
    private String sound;
    private String name;
    private String bonusImage;
    private Bonus sigLeft,sigRight,father;
    /**
     * Create the instance of the class with:
     * @param weight
     * @param id 
     * @param name
     * @param path 
     */
     public Bonus(int weight, int id, String name,String path,String sound) {
        this.weight = weight;
        this.id = id;
        this.name = name;
        this.sound=sound;
        this.bonusImage=path;
        sigLeft=null;
        sigRight=null;
        }
    //<editor-fold desc="All getter and setter here" defaultstate="collapsed">

    public String getSound()
    {
        return sound;
    }
    
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

    public String getBonusImage()
    {
        return bonusImage;
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

    public void setFather(Bonus previous) {
       this.father=previous;
    }
    
}
