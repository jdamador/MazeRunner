/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pk.codeapp.controller;

import java.util.Random;
import pk.codeapp.model.Bonus;
import pk.codeapp.model.Dupla;
import pk.codeapp.model.Frame;
import pk.codeapp.model.Link;
import pk.codeapp.view.GameWindows;

/**
 * This class is in charge of the all methods to edit different things in the
 * maze
 *
 * @author amador
 */
public class MazeController {

    static Frame startMaze;
    private Random randomGenerator = new Random();
    private BonusController bonusController= new BonusController();
    private Dupla positionCharacter1;
    private Dupla positionCharacter2;
    private Dupla positionCharacter3;
    private GameWindows window;
    /**
     * Iniitialize the root in null
     */
    public MazeController(GameWindows window) {
        this.startMaze = null;
        this.window=window;
  
        generateMap();
    }

    /**
     * This method create a new frame and add this in the graph
     *
     * @param name
     * @param token
     * @param row
     * @param column
     * @param allow
     */
    public void addFrame(String name, boolean token, int row, int column, boolean allow) {
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
    public void generateMap() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                addFrame(i + "," + j, false, i, j, false);
            }
        }
        makeLinks();
        mark();
        initializeBonus();
        setCharactersInMap();
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
     * This method help the developer, when it want to show the matrix
     */
    public void imprimir() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Frame reco = startMaze;
                while (reco != null) {

                    if (reco.getRow() == i && reco.getColumn() == j) {
                        if (reco.isAllow()) {
                            System.out.print("(" + reco.getName() + ")-");
                        } else {
                            System.out.print("(" + "x-x" + ")-");
                        }
                        break;
                    } else {
                        reco = reco.getNextFrame();
                    }
                }
            }
            System.out.println();
        }
    }

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

    /**
     * make marks in if move is allow or if is denied
     */
    public void mark() {
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

    public Frame search(String name) {
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

    public void makeLinks() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Frame actual = search(i + "," + j);
                Frame right = search(i + "," + (j + 1));
                addLink(actual, right, 45);
                if (j != 9) {
                    addLink(right, actual, i);
                }

                Frame down = search((i + 1) + "," + j);
                addLink(actual, down, 45);
                if (i != 9) {
                    addLink(down, actual, i);
                }

            }
        }
    }

    public void initializeBonus() {
        int quantityBonus= getRandom(3,6);
        String[] beforePositions= new String[quantityBonus];
        
        for (int i = 0; i < quantityBonus; i++) {
           boolean running=true;
           
           while(running){
               
               int row=getRandom(10);
               int column=getRandom(10);
               
               if(!exist(row+""+column,beforePositions)){
                   Frame found=search(row+ "," + column);
                   if(found.isAllow()){
                       Bonus bonus=getBonus();               
                       found.setBonus(bonus);
                       beforePositions[i] = row + "" + column;
                       running = false; 
                   }
               }
           }
          
        }
    }
    public boolean exist(String search,String[] beforePositions ){
         boolean flag=false;
            for (int i = 0; i < beforePositions.length; i++) {
           
                if(beforePositions[i]!=null){
                    if(search.equals(beforePositions[i])){
                        flag=true;
                    }
                };
            
        }
        return flag;
    }
    
    public int getRandom(int minimum, int maximum) {
        int randomInt = minimum + (int) (Math.random() * maximum);
        return randomInt;
    }

    private Bonus getBonus() {
        int numBonus=getRandom(6);
        return bonusController.searchInTree(numBonus);
    }
    public void setCharactersInMap(){
        int numCharacters= getRandom(2,3);
        if(numCharacters!=3){
            window.getImageCharacter3().setVisible(false);
        }
     
    }

 
}
