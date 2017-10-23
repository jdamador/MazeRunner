/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pk.codeapp.controller;

import java.util.Random;

/**
 * This method is charge of all character's functions
 *
 * @author amador
 */
public class CharacterController {

    private int numberCharacters;

    /**
     * This method calc a random number
     *
     * @param limit
     * @return
     */
    public int getRandom(int minimum, int maximum) {
        int randomInt = minimum + (int) (Math.random() * maximum);
        return randomInt;
    }
    
    public void numbreCharacters() {
        numberCharacters=getRandom(2, 3);
    }

}
