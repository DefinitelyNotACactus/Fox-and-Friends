/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animal.predator;

import animal.Animal;
import field.Location;

public interface Predator extends Animal
{
    Location findFood(Location Location);
    
    void incrementHunger();
    
    void setFoodLevel(int newLevel);
    
    int getFoodLevel();
}
