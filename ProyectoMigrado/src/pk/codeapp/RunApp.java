package pk.codeapp;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import pk.codeapp.controller.MazeController;
import pk.codeapp.view.GameWindows;

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

        GameWindows windowGame = new GameWindows();
        windowGame.setVisible(true);
    }
    
}
