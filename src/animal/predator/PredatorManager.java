/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animal.predator;

import animal.AnimalManager;
import field.Field;
import field.Location;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author David
 */
public abstract class PredatorManager extends AnimalManager implements Predator
{   
    // The predator's food level, which is increased by eating other animals.
    private int foodLevel;
    
    public PredatorManager(int age, Field field, Location location) {
        super(age, field, location);
    }
    
    /**
     * Make this dragon more hungry. This could result in the dragon's death.
     */
    @Override
    public void incrementHunger()
    {
        foodLevel--;
        if(foodLevel <= 0) {
            setDead();
        }
    }
    
    @Override
    public void setFoodLevel(int newLevel){
        foodLevel = newLevel;
    }
    
    @Override
    public int getFoodLevel(){
        return foodLevel;
    }
    
    @Override
    abstract public Location findFood(Location location);
    
    abstract public int getFoodValue();
}
