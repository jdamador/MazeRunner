/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pk.codeapp.model;

/**
 * This class is the in charge of make vértices del grafo
 *
 * @author Daniel Amador
 */
public class Frame {

    private Frame nextFrame;
    private Link nextLink;
    private String name;
    private boolean mark;
    private boolean allow;
    private int row, column;
    private Bonus bonus;

    /**
     * Default constructor, that make a instance of this class
     *
     * @param name Name of the Frame
     * @param mark to navigate the graph
     * @param row row in the graphics matrix
     * @param column column in the graphics matrix
     */
    public Frame(String name, boolean mark, int row, int column) {
        this.name = name;
        this.mark = mark;
        this.row = row;
        this.column = column;
        this.nextFrame = null;
        this.nextLink = null;
        this.allow = false;
    }

    //<editor-fold  defaultstate="collapsed" desc="All getter and setter here">
    public Frame getNextFrame() {
        return nextFrame;
    }

    public void setNextFrame(Frame nextFrame) {
        this.nextFrame = nextFrame;
    }

    public Link getNextLink() {
        return nextLink;
    }

    public void setNextLink(Link nextLink) {
        this.nextLink = nextLink;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isMark() {
        return mark;
    }

    public void setMark(boolean mark) {
        this.mark = mark;
    }

    public boolean isAllow() {
        return allow;
    }

    public void setAllow(boolean allow) {
        this.allow = allow;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }
     public Bonus getBonus() {
        return bonus;
    }

    public void setBonus(Bonus bonus) {
        this.bonus = bonus;
    }
    //</editor-fold>

   
}
