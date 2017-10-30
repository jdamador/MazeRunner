/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pk.codeapp.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Random;
import pk.codeapp.model.Bonus;

/**
 * This class is in charge of the all methods to edit different things in the
 * tree
 *
 * @author amador
 */
public class BonusController {

    private File bonusFile = new File("src/pk/codeapp/model/data/bonusFile.ser");
    private Bonus bonusRoot;
    private Random randomGenerator = new Random();

    /**
     * Default constructor initialize the root in null
     *
     * @param bonusRoot
     */
    public BonusController() {
        this.bonusRoot = bonusRoot;
        createBonus();
    }

    /**
     * Receive the node to write the information in binary file
     *
     * @param aux
     */
    public void writeBinaryFile(Bonus aux) {
        FileOutputStream file = null;
        ObjectOutputStream output = null;
        try {
            file = new FileOutputStream(bonusFile, true);
            output = new ObjectOutputStream(file);
            output.writeObject(aux);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (file != null) {
                try {
                    file.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * This method is in charge of read each element of the tree;
     *
     * @param reco
     */
    private void readTreeInPostOrden(Bonus reco) {
        if (reco == null) {
            return;
        } else {
            readTreeInPostOrden(reco.getSigLeft());
            readTreeInPostOrden(reco.getSigRight());
            writeBinaryFile(reco);
        }
    }

    /**
     * Method aux to call the private
     */
    public void readTreeInPostOrden() {
        readTreeInPostOrden(bonusRoot);
    }

    /**
     * Method that create elements in the tree
     */
    public void createBonus() {
        String[] names = {"Acceleration", "Teleportation", "Wait N seconds ", "Slow down the other players", "Change location target", "Random"};
        String[] path = {"src/pk/codeapp/view/tools/Acceleration.png", "src/pk/codeapp/view/tools/teletransport.png", "src/pk/codeapp/view/tools/wait.png",
             "src/pk/codeapp/view/tools/slow.png", "src/pk/codeapp/view/tools/slow.png","src/pk/codeapp/view/tools/change.png"};
        for (int i = 0; i < 6; i++) {
            int id = getRandom(6);
            int weight = getRandom(100);
            if (searchInTree(id) == null) {
            insertIntoTree(weight,id,names[i], path[i]);
            }
        }
    }

    /**
     * Search in the tree to find if is already exist Receive this parameters
     *
     * @param aux
     * @param id
     * @return true if exist and false if not
     */

    public Bonus searchInTree(int id) {
        return exist(id);
    }

    /**
     * Add a new element in their specific place
     *
     * @param newBonus
     * @param root
     */
    
    
    /**
     * Get a random num between 0 and the limit
     *
     * @param limit
     * @return random num
     */
    public int getRandom(int limit) {
        int randomInt = randomGenerator.nextInt(limit);
        return randomInt;
    }
    
    
    public Bonus exist(int id)
    {
        Bonus reco = bonusRoot;
        while (reco != null) {
            
            if (reco.getId() == id) {
                return reco;
            } else {
                if (id > reco.getId()) {
                    reco = reco.getSigRight();
                } else {
                    reco = reco.getSigLeft();
                }
            }
        }
        return null;
    }
    public void insertIntoTree(int weight,int id,String name, String path)
    {
        Bonus bonus = new Bonus(weight, id, name, path);
        if (exist(bonus.getId()) == null) {
            if (bonusRoot== null) { 
                bonusRoot = bonus;
            } else {
                Bonus previous = null, reco; 
                reco = bonusRoot;
                while (reco != null) {
                    previous = reco;
                    if (bonus.getId() < reco.getId()) {
                        reco = reco.getSigLeft();
                    } else {
                        reco = reco.getSigRight();
                    }
                }
                if (bonus.getId() < previous.getId()) {
                    previous.setSigLeft(bonus); 
                    bonus.setFather(previous);
                } else {
                    previous.setSigRight(bonus);
                  bonus.setFather(previous);
                }
            }
        } else {

        }

    }
}