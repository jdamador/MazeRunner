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
public class MazeController {

    private Random randomGenerator = new Random();
    static Frame startMaze, endMaze;
    static Frame positionCharacter1;
    static Frame positionCharacter2;
    static Frame positionCharacter3;
    private Frame cup;
    /*Bonus controller*/
    private BonusController bonusController = new BonusController();

    /**
     * Defaullt constructor
     */
    public MazeController() {
        this.startMaze = null;
        /*Generate Frames*/
        generateMap();
        /*Make walk*/
        mark();
        /*Add link*/
        makeLinks();
        //depth(endMaze);

        /*initicialize characters in their positions*/
        InitializeCharacters();
        /*Set bonus positions*/
        initializeBonus();
        /*Set objective*/
        setObjectiveLocation();
       
        /*Call character movement method*/
        startMovement();

    }

    /**
     * Add Frames into the graph
     *
     * @param name
     * @param token
     * @param row
     * @param column
     */
    public void addFrame(String name, boolean token, int row, int column) {
        Frame frame = new Frame(name, token, row, column);
        frame.setAllow(false);
        if (startMaze == null) {
            startMaze = endMaze = frame;
        } else {
            if (search(name) == null) {
                frame.setNextFrame(startMaze);
                startMaze = frame;
            }
        }
    }

    /**
     * Generate Frames
     */
    public void generateMap() {
        /*Adding element in the matrix*/
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                addFrame(i + "" + j, false, i, j);
            }
        }
    }

    /**
     * Print the matrix in console
     */
    public void printMatrix() {
        Frame reco = startMaze;
        while (reco != null) {
            if (reco.isAllow()) {
                System.out.println(reco.getName());
            }
            reco = reco.getNextFrame();
        }
    }

    /**
     * generate positions to make links between two frames
     */
    public void makeLinks() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Frame actual = search(i + "" + j);
                if (actual.isAllow() == true) {
                    Frame right = search(i + "" + (j + 1));
                    if (right != null) {
                        if (right.isAllow() == true) {
                            addLink(actual, right, 1);
                            addLink(right, actual, 1);
                        }
                    }
                    Frame down = search((i + 1) + "" + j);
                    if (down != null) {
                        if (down.isAllow() == true) {
                            addLink(actual, down, 1);
                            addLink(down, actual, 1);
                        }
                    }
                }
            }
        }
    }

    /**
     * Search frame into the graph
     *
     * @param name
     * @return
     */
    public Frame search(String name) {
        Frame reco = startMaze;
        while (reco != null) {
            if (reco.getName().equals(name)) {
                return reco;
            }
            reco = reco.getNextFrame();
        }
        return null;
    }

    /**
     * Create link bewteen two frames
     *
     * @param origen
     * @param destiny
     * @param weight
     * @return
     */
    public boolean addLink(Frame origen, Frame destiny, int weight) {
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

    /**
     * Set bonus in random position in the graph
     */
    public void initializeBonus() {
        int quantityBonus = getRandom(3, 5);
        boolean cantTele = true;
        for (int i = 0; i < quantityBonus; i++) {
            boolean running = true;
            while (running) {
                /*Get new position*/

                int row = getRandom(0, 9);
                int column = getRandom(0, 9);
                /*Search position*/
                Frame found = search(row + "" + column);
                if (found.getBonus() == null) {
                    if (found.isAllow() == true && found != positionCharacter1 && found != positionCharacter2 && found != positionCharacter3) {
                        Bonus bonus = getBonus();
                        if (bonus.getName().equals("Teleportation")) {
                            if (cantTele) {
                                found.setBonus(bonus);
                                System.out.println("First row: "+row+" Column" +column );
                                boolean searching = true;
                                while (searching) {
                                    int x = getRandom(0, 9);
                                    int y = getRandom(0, 9);
                                    Frame newFound = search(x + "" + y);
                                    if (newFound.getBonus() == null) {
                                        if (newFound.isAllow() == true && found != positionCharacter1 && found != positionCharacter2 && found != positionCharacter3) {
                                            newFound.setBonus(bonus);
                                            System.out.println("Second row: "+x+" Column " +y );
                                            searching = false;
                                            cantTele = false;
                                        }
                                    }
                                }

                            }

                        } else {
                            found.setBonus(bonus);
                            running = false;
                        }

                    }

                }
            }
        }
    }

    public int getRandom(int minimum, int maximum) {
        Random rand = new Random();
        int randomNum = rand.nextInt((maximum - minimum) + 1) + minimum;
        return randomNum;
    }

    public static void main(String[] args) {
        new MazeController().generateMap();
    }

    public Bonus getBonus() {
        int numBonus = getRandom(0, 5);
        return bonusController.searchInTree(numBonus);
    }

    /**
     * Set Characters in their appropriate position
     */
    public void InitializeCharacters() {

        int numCharacters = getRandom(2, 3);

        if (numCharacters != 3) {
            GameWindows.imageCharacter3.setVisible(false);
        }

        setCharactersInMaze(numCharacters);
    }

    /**
     * Seach one position to characters
     *
     * @param limit
     */
    private void setCharactersInMaze(int limit) {
        ArrayList<String> beforePositions = new ArrayList();

        for (int i = 0; i < limit; i++) {
            boolean running = true;
            while (running) {
                int row = getRandom(0, 9);
                int column = getRandom(0, 9);
                Frame found = search(row + "" + column);
                if (found.getBonus() == null) {
                    if (found.isAllow() && found != positionCharacter1 && found != positionCharacter2 && found != positionCharacter3) {
                        if (found.getBonus() == null) {
                            setChacterLocation(i, row, column, found);
                            beforePositions.add(row + "" + column);
                            running = false;
                        }
                    }
                }
            }
        }
    }

    /**
     * Set location for each character in the graph
     *
     * @param option
     * @param row
     * @param column
     * @param found
     */
    public void setChacterLocation(int option, int row, int column, Frame found) {
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

    public void setObjectiveLocation() {
        while (true) {
            int row = getRandom(0, 9);
            int column = getRandom(0, 9);
            Frame auxFrame = search(row + "" + column);
            if (auxFrame.getBonus() == null && auxFrame != positionCharacter1 && auxFrame != positionCharacter2 && auxFrame != positionCharacter3 && auxFrame.isAllow() == true) {
                GameWindows.objective.setLocation(row * 80, column * 80);
                cup = auxFrame;
                break;
            }
        }
    }

    public void depth(Frame reco) {

        if (reco == null | reco.isMark()) {
            return;
        }
        reco.setMark(true);
        Link aux = reco.getNextLink();
        while (aux != null) {
            depth(aux.getDestiny());
            aux = aux.getNextLink();

        }
    }

    //<editor-fold defaultstate="collapsed" desc="Generate marks">
    public int getRandom(int limit) {
        int randomInt = randomGenerator.nextInt(limit);
        return randomInt;
    }

    /**
     * make marks in if move is allow or if is denied
     */
    public void mark() {
        int walk = 1;
        for (int i = 0; i < 10; i++) {
            if (i == 0) {
                search(i + "" + i).setAllow(true);
            } else {
                if (i % 2 != 0) {

                    int numRandomX = getRandom(i);
                    int numRandomY = getRandom(i);
                    if (walk == 1) {
                        int numSpecialRandom = getRandom(2);
                        if (numSpecialRandom == 0) {
                            search(0 + "" + 1).setAllow(true);
                        } else {
                            search(1 + "" + 0).setAllow(true);
                        }
                    } else {
                        // Horizontal
                        for (int j = 0; j <= i; j++) {
                            if (numRandomY == j) {
                                search(i + "" + j).setAllow(true);
                            }
                        }
                        for (int j = i; j >= 0; j--) {
                            if (numRandomX == j) {
                                search(j + "" + i).setAllow(true);
                            }
                        }
                    }
                    walk++;
                } else {
                    // Horizontal
                    for (int j = 0; j <= i; j++) {
                        search(i + "" + j).setAllow(true);
                    }
                    //Vertical
                    for (int j = i; j >= 0; j--) {
                        search(j + "" + i).setAllow(true);
                    }
                }
            }

        }
    }

    //</editor-fold>
    static HandleMovement move1, move2, move3;
    private CharacterController controler, controler1, controler2;

    public void startMovement() {
        controler = new CharacterController(positionCharacter1, startMaze, cup);
        move1 = new HandleMovement(controler.getListRouteShort(), GameWindows.imageCharacter1, "Character 1", this, positionCharacter1);
        new Thread(move1).start();
        controler1 = new CharacterController(positionCharacter2, startMaze, cup);
        move2 = new HandleMovement(controler1.getListRouteShort(), GameWindows.imageCharacter2, "Character 2", this, positionCharacter2);
        new Thread(move2).start();
        if (positionCharacter3 != null) {
            controler2 = new CharacterController(positionCharacter3, startMaze, cup);
            move3 = new HandleMovement(controler2.getListRouteShort(), GameWindows.imageCharacter3, "Character 3", this, positionCharacter3);
            new Thread(move3).start();
        }

    }


}
