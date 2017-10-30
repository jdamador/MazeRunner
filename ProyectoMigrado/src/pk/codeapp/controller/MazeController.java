/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pk.codeapp.controller;

import java.util.ArrayList;
import java.util.Random;
import pk.codeapp.model.Bonus;
import pk.codeapp.model.Frame;
import pk.codeapp.model.Link;
import pk.codeapp.view.GameWindows;

/**
 *
 * @author Daniel Amador
 */
public class MazeController
{

    static Frame startMaze;
    private Random randomGenerator = new Random();
    private BonusController bonusController = new BonusController();
    private Frame positionCharacter1;
    private Frame positionCharacter2;
    private Frame positionCharacter3;
    private Frame cup;

    public MazeController()
    {
        this.startMaze = null;
        generateMap();

    }

    public void addFrame(String name, boolean token, int row, int column, boolean allow)
    {
        Frame square = new Frame(name, token, row, column);
        square.setAllow(allow);
        square.setNextFrame(null);
        if (startMaze == null) {
            startMaze = square;
        } else {
            square.setNextFrame(startMaze);
            startMaze = square;
        }
    }

    /**
     * This method call all functions that create the map
     */
    public void generateMap()
    {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                addFrame(i + "," + j, false, i, j, false);
            }
        }
        makeLinks();
        mark();
        initializeBonus();
        InitializeCharacters();
        setObjectiveLocation();
        characterRunning();

        // imprimir();
    }

    /**
     * This method create a link and make the connexion between two Frames
     *
     * @param origen
     * @param destiny
     * @param weight
     * @return true or false if is attache succesfull
     */
    public boolean addLink(Frame origen, Frame destiny, int weight)
    {
        Link link = new Link(weight);
        link.setDestiny(destiny);
        if (origen.getNextLink() == null) {
            origen.setNextLink(link);
            return true;
        } else {
            Link temp = origen.getNextLink();
            while (temp != null) {
                if (temp.getDestiny() == destiny) {
                    return false;
                }
                temp = temp.getNextLink();
            }
            link.setNextLink(origen.getNextLink());
            origen.setNextLink(link);
            return true;
        }
    }

    public int getRandom(int limit)
    {
        int randomInt = randomGenerator.nextInt(limit);
        return randomInt;
    }
    /**
     * make marks in if move is allow or if is denied
     */
    public void mark()
    {
        int walk = 1;
        for (int i = 0; i < 10; i++) {
            if (i == 0) {
                search(i + "," + i).setAllow(true);
            } else {
                if (i % 2 != 0) {

                    int numRandomX = getRandom(i);
                    int numRandomY = getRandom(i);
                    if (walk == 1) {
                        int numSpecialRandom = getRandom(2);
                        if (numSpecialRandom == 0) {
                            search(0 + "," + 1).setAllow(true);
                        } else {
                            search(1 + "," + 0).setAllow(true);
                        }
                    } else {
                        // Horizontal
                        for (int j = 0; j <= i; j++) {
                            if (numRandomY == j) {
                                search(i + "," + j).setAllow(true);
                            }
                        }
                        for (int j = i; j >= 0; j--) {
                            if (numRandomX == j) {
                                search(j + "," + i).setAllow(true);
                            }
                        }
                    }
                    walk++;
                } else {
                    // Horizontal
                    for (int j = 0; j <= i; j++) {
                        search(i + "," + j).setAllow(true);
                    }
                    //Vertical
                    for (int j = i; j >= 0; j--) {
                        search(j + "," + i).setAllow(true);
                    }
                }
            }

        }
    }

    public Frame search(String name)
    {
        if (startMaze == null) {
            return null;
        }
        Frame reco = startMaze;
        while (reco != null) {
            if (reco.getName().equals(name)) {
                return reco;
            }
            reco = reco.getNextFrame();
        }
        return null;
    }

    public void makeLinks()
    {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Frame actual = search(i + "," + j);
                Frame right = search(i + "," + (j + 1));
                if (right != null) {
                    addLink(actual, right, 1);
                    addLink(right, actual, 1);
                }
                Frame down = search((i + 1) + "," + j);
                if (down != null) {
                    addLink(actual, down, 1);
                    addLink(down, actual, 1);
                }

            }
        }

    }
    public void initializeBonus()
    {
        int quantityBonus = getRandom(3, 6);
        String[] beforePositions = new String[quantityBonus];

        for (int i = 0; i < quantityBonus; i++) {
            boolean running = true;

            while (running) {

                int row = getRandom(9);
                int column = getRandom(9);

                if (!exist(row + "" + column, beforePositions)) {
                    Frame found = search(row + "," + column);
                    if (found.isAllow()) {
                        Bonus bonus = getBonus();
                        found.setBonus(bonus);
                        beforePositions[i] = row + "" + column;
                        running = false;

                    }
                }
            }

        }
    }
     public boolean exist(String search, String[] beforePositions)
    {
        boolean flag = false;
        for (int i = 0; i < beforePositions.length; i++) {
            if (beforePositions[i] != null) {
                if (search.equals(beforePositions[i])) {
                    flag = true;
                }
            };

        }
        return flag;
    }

    public int getRandom(int minimum, int maximum)
    {
        Random rand = new Random();
        int randomNum = rand.nextInt((maximum - minimum) + 1) + minimum;
        return randomNum;
    }
    private Bonus getBonus()
    {
        int numBonus = getRandom(5);
        return bonusController.searchInTree(numBonus);
    }

    public void InitializeCharacters()
    {
        int numCharacters = getRandom(2, 3);

        if (numCharacters != 3) {
            GameWindows.imageCharacter3.setVisible(false);
        }
        setCharactersInMaze(numCharacters);
    }
 private void setCharactersInMaze(int limit)
    {
        String[] beforePositions = new String[limit];
        for (int i = 0; i < limit; i++) {
            boolean running = true;
            while (running) {

                int row = getRandom(9);
                int column = getRandom(9);

                if (!exist(row + "" + column, beforePositions)) {
                    Frame found = search(row + "," + column);
                    if (found.isAllow()) {

                        if (found.getBonus() == null) {
                            setChacterLocation(i, row, column, found);
                            beforePositions[i] = row + "" + column;
                            running = false;
                        }

                    }
                }
            }
        }
        
    }
 public void setChacterLocation(int option, int row, int column, Frame found)
    {
        
        switch (option) {
            case 0: {

                positionCharacter1 = found;
                GameWindows.imageCharacter1.setLocation(row * 80, column * 80);

                break;
            }
            case 1: {

                positionCharacter2 = found;
                GameWindows.imageCharacter2.setLocation(row * 80, column * 80);
                break;
            }
            case 2: {

                positionCharacter3 = found;
                GameWindows.imageCharacter3.setLocation(row * 80, column * 80);
                break;
            }
        }
    }

    public void setObjectiveLocation()
    {
        while (true) {

            int row = getRandom(9);
            int column = getRandom(9);
            Frame auxFrame = search(row + "," + column);
            if (auxFrame.getBonus() == null && auxFrame != positionCharacter1 && auxFrame != positionCharacter2 && auxFrame != positionCharacter3 && auxFrame.isAllow() == true) {
                GameWindows.objective.setLocation(row * 80, column * 80);
                cup = auxFrame;
                break;
            }
        }
    }

    public void characterRunning()
    {
        ArrayList<Frame> walkCharacter1;
        ArrayList<Frame> walkCharacter2;
        clean_Mark();
        CharacterController element1 = new CharacterController(positionCharacter1, startMaze, cup);
        walkCharacter1 = element1.getArray();
        
//        clean_Mark();
//        CharacterController element2 = new CharacterController(positionCharacter2, startMaze, cup);
//        walkCharacter2 = element2.createGraphForCharacter();
//        if (positionCharacter3!=null) {
//            clean_Mark();
//            ArrayList<Frame> walkCharacter3;
//            CharacterController element3 = new CharacterController(positionCharacter3, startMaze, cup);
//            walkCharacter3 = element3.createGraphForCharacter();
//            new Thread(new HandleMovement(walkCharacter3, GameWindows.imageCharacter3)).start();
//        }
          new Thread(new HandleMovement(walkCharacter1, GameWindows.imageCharacter1)).start();
//        new Thread(new HandleMovement(walkCharacter2, GameWindows.imageCharacter2)).start();

    }

    public void clean_Mark()
    {
        Frame temp = startMaze;
        if (startMaze == null) {
            return;
        }
        while (temp != null) {
            temp.setMark(false);
            temp = temp.getNextFrame();
        }
    }
}