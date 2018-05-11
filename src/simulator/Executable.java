/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulator;

import java.util.Scanner;

/**
 *
 * @author David
 */
public class Executable {
    
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        Simulator simulator = new Simulator();
        while(sc.nextInt() != 0){
            simulator.simulate(sc.nextInt());
        }
    }
}

/**Notes:
 * Before doing ex 10.44 the simulation was resulting in 372 foxes and 87 rabbits 
 * After 10.44 the simulation result is the same as before.
 * After 10.45 same shit.
 * After 10.46 same.
 * At 10.47 the breed method can be moved to Animal, and the change won't affect the simulation.
 * At 10.48 we could change the setDead() getLocation() getField() methods to protected
 * For 10.49 it suggets that the Animal class and it's subclasses share most of their traits, 
 * so it's possible to move most of the code from the subclasses to the superclass without making an impact in another classes
 */