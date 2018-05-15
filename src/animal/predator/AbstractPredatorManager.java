package animal.predator;

import animal.AbstractAnimalManager;
import field.Field;
import field.Location;

/**
 * A class representing shared characteristics of predators.
 * @author David Pereira
 * @author Gabriel Davi
 */
public abstract class AbstractPredatorManager extends AbstractAnimalManager
{   
    // The predator's food level, which is increased by eating other animals.
    private int foodLevel;
    
    public AbstractPredatorManager(int age, Field field, Location location) {
        super(age, field, location);
    }
    
    /**
     * Make this dragon more hungry. This could result in the dragon's death.
     */
    public void incrementHunger()
    {
        foodLevel--;
        if(foodLevel <= 0) {
            setDead();
        }
    }
    
    public void setFoodLevel(int newLevel){
        foodLevel = newLevel;
    }
    
    public int getFoodLevel(){
        return foodLevel;
    }
    
    abstract public Location findFood(Location location);
    
    abstract public int getFoodValue();
}
