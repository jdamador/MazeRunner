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
    private int sleep = 1650;
    private ArrayList<Frame> walk;
    private JLabel lblImage;
    private String name;
    private MazeController master;
    private Frame character;
    public boolean running = true;

    boolean isTeleporting = false;

    /**
     * Default constructor
     *
     * @param walk
     * @param lblImage
     */
    public HandleMovement(ArrayList<Frame> walk, JLabel lblImage, String name, MazeController master, Frame character) {
        this.walk = walk;
        this.lblImage = lblImage;
        this.name = name;
        this.master = master;
        this.character = character;
    }

    public void mover() throws InterruptedException {
        int index = 0;
        while (running) {
            if (index < walk.size()) {
                /*Change the location*/
                character = walk.get(index);
                setNewPosition(character);
                lblImage.setLocation(walk.get(index).getRow() * 80, walk.get(index).getColumn() * 80);
                /*Check if is a bonus*/
                if (walk.get(index).getBonus() != null) {
                    Bonus bonus = walk.get(index).getBonus();
                    validate(bonus, walk.get(index));
                    walk.get(index).setBonus(null);
                } else {
                    /*make a sleep*/
                    Thread.sleep(sleep);
                    index++;
                }
            } else {
                stop();
                break;
            }
        }
    }

    public void validate(Bonus bonus, Frame frame) throws InterruptedException {
        URL u = null;
        try {
            u = new File(bonus.getSound()).toURL();
        } catch (Exception ex) {
        }
        AudioClip sound = Applet.newAudioClip(u);
        sound.play();
        if (bonus.getName().equals("Acceleration")) {
            sleep -= 700;
        } else if (bonus.getName().equals("Wait N seconds")) {
            Thread.sleep(getRandom(3000, 4000));
        } else if (bonus.getName().equals("Slow down the other players")) {
            slowOtherCharacter(); //increase the sleep in the other thread
        } else if (bonus.getName().equals("Change location target")) {
            changeLocationTarge();
        } else if (bonus.getName().equals("Random")) {
            Bonus b = getBonus();
            Thread.sleep(500);
            frame.setBonus(b);
            validate(b, frame);
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

    public void setSleep() {
        this.sleep += 700;
    }

    @Override
    public void run() {
        try {

            mover();
        } catch (Exception e) {
        }
    }

    public void stop() {
        MazeController.move1.running = false;
        MazeController.move2.running = false;
        if (MazeController.move3 != null) {
            MazeController.move3.running = false;
        }
    }

    public void reload() {
        master.startMovement();
    }

    private void slowOtherCharacter() {
        if (name.equals("Character 1")) {
            MazeController.move2.setSleep();
            if (MazeController.move3 != null) {
                MazeController.move3.setSleep();
            }
        } else if (name.equals("Character 2")) {
            MazeController.move1.setSleep();
            if (MazeController.move3 != null) {
                MazeController.move3.setSleep();
            }
        } else if (name.equals("Character 3")) {
            MazeController.move1.setSleep();
            MazeController.move2.setSleep();
        }
    }

    private void changeLocationTarge() {
        master.setObjectiveLocation();
        master.startMovement();
        stop();
    }

    private void setNewPosition(Frame frame) {
        if (name.equals("Character 1")) {
            MazeController.positionCharacter1=frame;
        } else if (name.equals("Character 2")) {
            MazeController.positionCharacter2=frame;
        } else if (name.equals("Character 3")) {
            MazeController.positionCharacter3=frame;
        }
    }

}
