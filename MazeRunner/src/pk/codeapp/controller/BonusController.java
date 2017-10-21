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
 * This class is in charge of the all methods to edit different things
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
    public BonusController(Bonus bonusRoot) {
        this.bonusRoot = bonusRoot;
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
        for (int i = 0; i < 6; i++) {
            int id = getRandom(50);
            int weight = getRandom(100);
            Bonus aux = new Bonus(weight, id, names[i], "");
            if (searchInTree(aux, bonusRoot) != true) {
                addBonusInTree(aux, bonusRoot);
            }
        }
    }

    /**
     * Search in the tree to find if is already exist Receive this parameters
     *
     * @param aux
     * @param reco
     * @return true if exist and false if not
     */
    public boolean searchInTree(Bonus aux, Bonus reco) {
        if (reco.equals(aux)) {
            return true;
        } else {
            if (aux.getId() < reco.getId()) {
                searchInTree(aux, reco.getSigLeft());
            } else {
                searchInTree(aux, reco.getSigRight());
            }
        }
        return false;
    }
    
    /**
     * Add a new element in their specific place
     * @param reco
     * @param father 
     */
    public void addBonusInTree(Bonus reco, Bonus father) {
        if (bonusRoot == null) {
            bonusRoot = reco;
        } else {
            if (reco.getId() < bonusRoot.getId()) {
                if (father.getSigLeft() == null) {
                    father.setSigLeft(reco);
                } else {
                    addBonusInTree(reco, father.getSigLeft());
                }
            } else {
                if (father.getSigRight() == null) {
                    father.setSigRight(reco);
                } else {
                    addBonusInTree(reco, father.getSigRight());
                }
            }
        }
    }
    
    /**
     * Get a random num between 0 and the limit
     * @param limit
     * @return random num
     */
      public int getRandom(int limit) {
        int randomInt = randomGenerator.nextInt(limit);
        return randomInt;
    }
}
