/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pk.codeapp.controller;

import java.util.Random;

/**
 *This method is charge of all character's functions
 * @author amador
 */
public class CharacterController {
    private Random randomGenerator = new Random();
    /**
     * This method calc a random number
     * @param limit
     * @return 
     */
    public int getRandom(int limit){
          int randomInt = randomGenerator.nextInt(limit);
        return randomInt;
    }
    
}
