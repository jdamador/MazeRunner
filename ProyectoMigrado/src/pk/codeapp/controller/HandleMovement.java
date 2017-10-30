/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pk.codeapp.controller;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import pk.codeapp.model.Bonus;
import pk.codeapp.model.Frame;

/**
 *
 * @author Daniel Amador
 */
public class HandleMovement implements Runnable
{

    private int sleep = 1650;
    private ArrayList<Frame> walk;
    private JLabel lblImage;
    private String name;
    private NewMazeController master;
    private Frame character;

    /**
     * Default
     *
     * @param walk
     * @param lblImage
     */
    public HandleMovement(ArrayList<Frame> walk, JLabel lblImage, String name, NewMazeController master, Frame character)
    {
        this.walk = walk;
        this.lblImage = lblImage;
        this.name = name;
        this.master = master;
        this.character = character;
    }

    /**
     *
     * Throws time sleep exception
     *
     * @throws InterruptedException
     */
    public void mover() throws InterruptedException
    {
        for (int i = 0; i < walk.size(); i++) {
            character = walk.get(i);
            lblImage.setLocation(walk.get(i).getRow() * 80, walk.get(i).getColumn() * 80);
            if (walk.get(i).getBonus() != null) {
                Bonus bonus = walk.get(i).getBonus();
                validate(bonus, walk.get(i));
            }
            Thread.sleep(sleep);
        }
    }

    public void validate(Bonus bonus, Frame frame) throws InterruptedException
    {
        if /*Acceleration*/ (bonus.getName().equals("Acceleration")) {
            sleep-=300;                                                                                                                              ;
            frame.getBonus().getSound().play();
            frame.setBonus(null);
        } else /*Teleportation*/ if (bonus.getName().equals("Teleportation")) {
           frame.getBonus().getSound().play();
            frame.setBonus(null);
        } else /*Wait N seconds*/ if (bonus.getName().equals("Wait N seconds")) {
            frame.getBonus().getSound().play();
            int timeSleep = getRandom(3000, 4000);
            Thread.sleep(timeSleep - sleep);
            frame.getBonus().getSound().stop();
            frame.setBonus(null);
        } else /*Slow down the other players*/ if (bonus.getName().equals("Slow down the other players")) {
            frame.getBonus().getSound().play();
            /*If character #1*/
            if (name.equals("Character 1")) {
                NewMazeController.move2.setSleep(3000);
                if (NewMazeController.move3 != null) {
                    NewMazeController.move3.setSleep(3000);
                }
            } else /*If character #2*/ if (name.equals("Character 2")) {
                NewMazeController.move1.setSleep(3000);
                if (NewMazeController.move3 != null) {
                    NewMazeController.move3.setSleep(3000);
                }
            } else /*If character #3*/ if (name.equals("Character 3")) {
                NewMazeController.move1.setSleep(3000);
                NewMazeController.move2.setSleep(3000);
            }
           frame.setBonus(null);
        } else /*Change location target*/ if (bonus.getName().equals("Change location target")) {
            //master.stopAndRestar();
        } else /*Random*/ if (bonus.getName().equals("Random")) {
            frame.getBonus().getSound().play();
            frame.setBonus(null);
        }

    }

    /**
     *
     * Get number random
     *
     * @param minimum
     * @param maximum
     * @return
     */
    public int getRandom(int minimum, int maximum)
    {
        Random rand = new Random();
        int randomNum = rand.nextInt((maximum - minimum) + 1) + minimum;
        return randomNum;
    }

    public void setSleep(int sleep)
    {
        this.sleep = sleep;
    }

    @Override
    public void run()
    {
        try {

            mover();
        } catch (Exception e) {
        }
    }

    public void stop()
    {
        this.stop();
    }

}
