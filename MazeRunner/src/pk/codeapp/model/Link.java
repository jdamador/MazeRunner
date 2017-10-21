/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pk.codeapp.model;

/**
 *in between of two frames
 * @author amador
 */
public class Link {
    private Link nextLink;
    private Frame destiny;
    private int weight;
/**
 *  Default constructor, that make a instance of this class
 * @param weight 
 */
    public Link(int weight) {
        this.weight = weight;
        this.destiny=null;
        this.nextLink=null;
    }
    //<editor-fold defaultstate="collapsed" desc="All getter and setter here">
    public Link getNextLink() {
        return nextLink;
    }

    public void setNextLink(Link nextLink) {
        this.nextLink = nextLink;
    }

    public Frame getDestiny() {
        return destiny;
    }

    public void setDestiny(Frame destiny) {
        this.destiny = destiny;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
    //</editor-fold>
}
