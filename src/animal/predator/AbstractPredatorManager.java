package animal.predator;

import animal.AbstractAnimalManager;
import animal.Animal;
import field.Field;
import field.Location;
import java.util.List;

/**
 * A class representing shared characteristics of predators.
 * @author David Pereira
 * @author Gabriel Davi
 */
public abstract class AbstractPredatorManager extends AbstractAnimalManager
{   
    // The predator's food level, which is increased by eating other animals.
    private int foodLevel;
    
    /**
     * Create a new predator at location in field.
     * 
     * @param age The predator's age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public AbstractPredatorManager(int age, Field field, Location location) 
    {
        super(age, field, location);
        foodLevel = getMainPreyFoodValue();
    }
    
    /**
     * Make this predator more hungry. This could result in the predator's death.
     */
    public void incrementHunger()
    {
        foodLevel--;
        if(foodLevel <= 0) {
            setDead();
        }
    }
   
    @Override
    public void act(List<Animal> newPredators)
    {
        incrementAge();
        incrementHunger();
        if(isAlive()) {
            giveBirth(newPredators);            
            // Move towards a source of food if found.
            Location location = getLocation();
            Location newLocation = findFood(location);
            if(newLocation == null) { 
                // No food found - try to move to a free location.
                newLocation = getField().freeAdjacentLocation(location);
            }
            // See if it was possible to move.
            if(newLocation != null) {
                setLocation(newLocation);
            }
            else {
                // Overcrowding.
                setDead();
            }
        }
    }
    
    /**
     * Sets the predator food level to the given parameter.
     * @param newLevel The new food level.
     */
    public void setFoodLevel(int newLevel)
    {
        foodLevel = newLevel;
    }
    
    /**
     * Gives the current food level of this predator.
     * @return The food level of this predator.
     */
    public int getFoodLevel(){
        return foodLevel;
    }
    
    /**
     * Tell the predator to look for prey adjacent to its current location.
     * Only the first live prey is eaten.
     * @param location Where in the field it is located.
     * @return Where food was found, or null if it wasn't.
     */
    abstract public Location findFood(Location location);
      
    /**
     * Gives the food value of the main prey of this predator.
     * @return The food value of the predator's main prey.
     */
    abstract public int getMainPreyFoodValue();
    
}
