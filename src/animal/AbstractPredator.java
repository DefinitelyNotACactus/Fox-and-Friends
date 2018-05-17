package animal;

import field.Field;
import field.Location;
import field.Randomizer;
import java.util.List;

/**
 * A class representing shared characteristics of predators.
 * @author David Pereira
 * @author Gabriel Davi
 */
public abstract class AbstractPredator extends AbstractAnimal
{   
    // The predator's food level, which is increased by eating other animals.
    private int foodLevel;
    
    /**
     * Create a new predator at location in field.
     * 
     * @param randomAge If true, the predator will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public AbstractPredator(boolean randomAge, Field field, Location location) 
    {
        super(0, field, location);
        if(randomAge) {
            setAge(Randomizer.getRandom().nextInt(getMaxAge()));
            setFoodLevel(Randomizer.getRandom().nextInt(getMainPreyFoodValue()));
        } else {
            foodLevel = getMainPreyFoodValue();
        }
    }
    
    /**
     * Make this predator more hungry. This could result in the predator's death.
     */
    protected void incrementHunger()
    {
        foodLevel--;
        if(foodLevel <= 0) {
            setDead();
        }
    }
   
    @Override
    public void act(List<AbstractAnimal> newPredators)
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
    protected void setFoodLevel(int newLevel)
    {
        foodLevel = newLevel;
    }
    
    /**
     * Gives the current food level of this predator.
     * @return The food level of this predator.
     */
    protected int getFoodLevel(){
        return foodLevel;
    }
    
    /**
     * Tell the predator to look for prey adjacent to its current location.
     * Only the first live prey is eaten.
     * @param location Where in the field it is located.
     * @return Where food was found, or null if it wasn't.
     */
    abstract protected Location findFood(Location location);
      
    /**
     * Gives the food value of the main prey of this predator.
     * @return The food value of the predator's main prey.
     */
    abstract protected int getMainPreyFoodValue();   
}
