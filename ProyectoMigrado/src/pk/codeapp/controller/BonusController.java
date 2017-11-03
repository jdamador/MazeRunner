/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pk.codeapp.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Random;
import pk.codeapp.model.Bonus;

/**
 * This class is in charge of the all methods to edit different things in the
 * tree
 *
 * @author amador
 */
public class BonusController
{

    private File bonusFile = new File("src/pk/codeapp/model/data/bonusFile.ser");
    private Bonus bonusRoot;
    private Random randomGenerator = new Random();
    private ArrayList<Bonus> listBonus = new ArrayList();

    /**
     * Default constructor initialize the root in null
     *
     * @param bonusRoot
     */
    public BonusController()
    {
        bonusRoot = null;
        
//        createBonus();
//        readTreeInPostOrden();
//        writeBinaryFile();
        chargeBonus();

    }

    /**
     * Receive the node to write the information in binary file
     *
     * @param aux
     */
    
    //<editor-fold  desc="Write and read from binary file" defaultstate="collapsed">
    
    /**
     * Write in binary file all elements from tree
     */
    public void writeBinaryFile()
    {
        if (bonusFile.exists()) {
            bonusFile.delete();
        }
        FileOutputStream file = null;
        ObjectOutputStream output = null;
        try {
            for (int i = 0; i < listBonus.size(); i++) {
                file = new FileOutputStream(bonusFile);

                output = new ObjectOutputStream(file);
                if (listBonus.get(i) != null) {
                    output.writeObject(listBonus.get(i));
                }
            }
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
     * Read from binary file the tree's  root
     */
    public void chargeBonus()
    {
        //Read user from binary file
        try {
            FileInputStream saveFile = new FileInputStream(bonusFile);
            ObjectInputStream save= new ObjectInputStream(saveFile);
            bonusRoot=(Bonus)save.readObject();
            saveFile.close();  
        } catch (Exception ex) {
        }
    }
    //</editor-fold>
    
    /**
     * This method is in charge of read each element of the tree;
     *
     * @param reco
     */
    private void readTreeInPostOrden(Bonus reco)
    {
        if (reco != null) {
            readTreeInPostOrden(reco.getSigLeft());
            readTreeInPostOrden(reco.getSigRight());
            listBonus.add(reco);
        }
    }

    /**
     * Method aux to call the private
     */
    public void readTreeInPostOrden()
    {
        readTreeInPostOrden(bonusRoot);
    }
    
    /**
     * Method that create elements in the tree
     */
    public void createBonus()
    {
        String[] names = {"Acceleration", "Teleportation", "Wait N seconds", "Slow down the other players", "Change location target", "Random"};
        String[] path = {"src/pk/codeapp/view/tools/Acceleration.png", "src/pk/codeapp/view/tools/teletransport.png", "src/pk/codeapp/view/tools/wait.png",
            "src/pk/codeapp/view/tools/slow.png", "src/pk/codeapp/view/tools/change.png", "src/pk/codeapp/view/tools/random.png"};
        String[] sounds = {"src/pk/codeapp/view/tools/acceleration.wav", "src/pk/codeapp/view/tools/teleport.wav", "src/pk/codeapp/view/tools/timesleep.wav",
            "src/pk/codeapp/view/tools/slow.wav", "src/pk/codeapp/view/tools/change.wav", "src/pk/codeapp/view/tools/random.wav"};
        for (int i = 0; i < 6; i++) {
            int weight = getRandom(100);
            if (searchInTree(i) == null) {
                insertIntoTree(weight, i, names[i], path[i], sounds[i]);
            }
        }
    }
    

    /**
     * Search in the tree to find if is already exist Receive this parameters
     *
     * @param id
     * @return true if exist and false if not
     */
    public Bonus searchInTree(int id)
    {
        return exist(id);
    }

    /**
     * Get a random num between 0 and the limit
     *
     * @param limit
     * @return random num
     */
    public int getRandom(int limit)
    {
        int randomInt = randomGenerator.nextInt(limit);
        return randomInt;
    }
    /**
     * Read tree and return bonus if this exist and  null if isn't
     * @param id
     * @return 
     */
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
    /**
     * Add  new element in the tree, receive this parameters
     * @param weight
     * @param id
     * @param name
     * @param path
     * @param sound 
     */
    public void insertIntoTree(int weight, int id, String name, String path, String sound)
    {
        Bonus bonus = new Bonus(weight, id, name, path, sound);
        if (bonus.getName().equals("Wait N seconds")) {
            bonus.setIsGood(false);
        } else {
            bonus.setIsGood(true);
        }

        if (exist(bonus.getId()) == null) {
            if (bonusRoot == null) {
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
        }
    }

}
