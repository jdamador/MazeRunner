/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pk.codeapp.view.tools;

import pk.codeapp.controller.MazeController;

/**
 *
 * @author amador
 */
public class RunApp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        MazeController maze= new MazeController();
        maze.generateMap();
    }
    
}
