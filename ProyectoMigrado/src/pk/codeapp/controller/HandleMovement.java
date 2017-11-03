/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pk.codeapp.controller;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JLabel;
import pk.codeapp.model.Bonus;
import pk.codeapp.model.Frame;

/**
 *
 * @author Daniel Amador
 */
public class HandleMovement implements Runnable {

    /*Bonus controller*/
    private BonusController bonusController = new BonusController();
    private int sleep = 2000;
    private ArrayList<Frame> walk;
    private JLabel lblImage;
    private String name;
    private NewMazeController master;
    private Frame character;
    public boolean running = true;

    boolean isTeleporting = false;

    /**
     * Default constructor
     *
     * @param walk
     * @param lblImage
     */
    public HandleMovement(ArrayList<Frame> walk, JLabel lblImage, String name, NewMazeController master, Frame character) {
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
    public void mover() throws InterruptedException {
        int index = 0;
        while (running) {
            if (index < walk.size()) {

                character = walk.get(index);
                if (name.equals("Character 1")) {
                    NewMazeController.positionCharacter1 = walk.get(index);
                } else if (name.equals("Character 2")) {
                    NewMazeController.positionCharacter2 = walk.get(index);
                } else if (name.equals("Character 3")) {
                    NewMazeController.positionCharacter3 = walk.get(index);
                }
                lblImage.setLocation(walk.get(index).getRow() * 80, walk.get(index).getColumn() * 80);
                if (walk.get(index).getBonus() != null) {
                    Bonus bonus = walk.get(index).getBonus();
                    validate(bonus, walk.get(index));
                }

                Thread.sleep(sleep);
                index++;
            } else {
                break;
            }
        }
    }

    /**
     * This method is handle of verify each kind of bonus is in the walk
     *
     * @param bonus
     * @param frame
     * @throws InterruptedException
     */
    public void validate(Bonus bonus, Frame frame) throws InterruptedException {
        URL u = null;
        try {
            u = new File(bonus.getSound()).toURL();
        } catch (Exception ex) {

        }
        AudioClip sound = Applet.newAudioClip(u);
         sound.play();
        if /*Acceleration*/ (bonus.getName().equals("Acceleration")) {
            sleep -= 700;
           
            frame.setBonus(null);
        } else /*Teleportation*/ if (bonus.getName().equals("Teleportation")) {
            frame.setBonus(null);
            if (isTeleporting) {
                stop();
            }
            isTeleporting=true;
        } else /*Wait N seconds*/ if (bonus.getName().equals("Wait N seconds")) {
            
            int timeSleep = getRandom(3000, 4000);
            Thread.sleep(timeSleep - sleep);
        
            frame.setBonus(null);
        } else /*Slow down the other players*/ if (bonus.getName().equals("Slow down the other players")) {
         
            /*If character #1*/
            if (name.equals("Character 1")) {
                NewMazeController.move2.setSleep(NewMazeController.move2.sleep += 300);

                if (NewMazeController.move3 != null) {
                    NewMazeController.move3.setSleep(NewMazeController.move3.sleep += 300);
                }

            } else /*If character #2*/ if (name.equals("Character 2")) {
                NewMazeController.move1.setSleep(NewMazeController.move1.sleep += 300);
                if (NewMazeController.move3 != null) {
                    NewMazeController.move3.setSleep(NewMazeController.move3.sleep += 300);
                }
            } else /*If character #3*/ if (name.equals("Character 3")) {
                NewMazeController.move1.setSleep(NewMazeController.move1.sleep += 300);
                NewMazeController.move2.setSleep(NewMazeController.move2.sleep += 300);
            }
            frame.setBonus(null);
        } else /*Change location target*/ if (bonus.getName().equals("Change location target")) {
            frame.setBonus(null);
            master.setObjectiveLocation();
            stop();
        } else /*Random*/ if (bonus.getName().equals("Random")) {
          
            Bonus randomBonus = getBonus();
            frame.setBonus(randomBonus);
            Thread.sleep(500);
            validate(randomBonus, frame);
        }

    }

    public Bonus getBonus() {
        while (true) {
            int numBonus = getRandom(0, 5);
            Bonus bonus = bonusController.searchInTree(numBonus);
            if (!bonus.getName().equals("Teleportation")) {
                return bonus;
            }
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
    public int getRandom(int minimum, int maximum) {
        Random rand = new Random();
        int randomNum = rand.nextInt((maximum - minimum) + 1) + minimum;
        return randomNum;
    }

    public void setSleep(int sleep) {
        this.sleep = sleep;
    }

    @Override
    public void run() {
        try {

            mover();
        } catch (Exception e) {
        }
    }

    public void stop() {
        NewMazeController.move1.running = false;
        NewMazeController.move2.running = false;
        if (NewMazeController.move3 != null) {
            NewMazeController.move3.running = false;
        }
        master.startMovement();
    }

}
