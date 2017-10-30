/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pk.codeapp.controller;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import pk.codeapp.model.Frame;

/**
 *
 * @author Daniel Amador
 */
public class HandleMovement implements Runnable
{

    private ArrayList<Frame> walk;
    private JLabel lblImage;

    public HandleMovement(ArrayList<Frame> walk, JLabel lblImage)
    {
        this.walk = walk;
        this.lblImage = lblImage;
    }

    public void mover() throws InterruptedException
    {
        for (int i = 0; i < walk.size(); i++) {
            lblImage.setLocation(walk.get(i).getRow()*80, walk.get(i).getColumn()*80);
            Thread.sleep(1650);
        }
    }

    @Override
    public void run()
    {
        
        try {

            mover();
        } catch (Exception e) {
        }
    }

}
