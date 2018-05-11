package animal;

import field.Field;
import field.Location;
import field.Randomizer;
import java.util.Iterator;
import java.util.List;

/** 
 * This class was based on the Fox and Rabbits v2 project from the Objects First with BlueJ book
 * The Komodo Dragon does the same thing as a fox, but instead of eating rabbits, it eats foxes. (Challenge Exercise 10.50)
 *
 * @author David Pereira
 * @author Gabriel Davi
 */
public class KomodoDragon extends Animal {
    
    private static final int BREEDING_AGE = 30;
    // The age to which a dragon can live.
    private static final int MAX_AGE = 300;
    // The likelihood of a dragon breeding.
    private static final double BREEDING_PROBABILITY = 0.01;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 20;
    // The food value of a single fox. In effect, this is the
    // number of steps a dragon can go before it has to eat again.
    private static final int FOX_FOOD_VALUE = 14;
    
    // Individual characteristics (instance fields).
    
    // The dragon's food level, which is increased by eating foxes.
    private int foodLevel;
    
    public KomodoDragon(boolean randomAge, Field field, Location location){
        super(0, field, location);
        if(randomAge) {
            setAge(Randomizer.getRandom().nextInt(MAX_AGE));
            foodLevel = Randomizer.getRandom().nextInt(FOX_FOOD_VALUE);
        }
        else {
            foodLevel = FOX_FOOD_VALUE;
        }
    }
    
    /**
     * This is what the dragon does most of the time: it hunts for
     * fox. In the process, it might breed, die of hunger,
     * or die of old age.
     * @param newKomodoDragons A list to add newly born dragons to.
     */
    @Override
    public void act(List<Animal> newKomodoDragons)
    {
        incrementAge();
        incrementHunger();
        if(isAlive()) {
            giveBirth(newKomodoDragons);            
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
     * Make this dragon more hungry. This could result in the dragon's death.
     */
    private void incrementHunger()
    {
        foodLevel--;
        if(foodLevel <= 0) {
            setDead();
        }
    }
    
    /**
     * Tell the dragon to look for foxes adjacent to its current location.
     * Only the first live fox is eaten.
     * @param location Where in the field it is located.
     * @return Where food was found, or null if it wasn't.
     */
    private Location findFood(Location location)
    {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object animal = field.getObjectAt(where);
            if(animal instanceof Fox) {
                Fox fox = (Fox) animal;
                if(fox.isAlive()) { 
                    fox.setDead();
                    foodLevel = FOX_FOOD_VALUE;
                    // Remove the dead rabbit from the field.
                    return where;
                }
            }
        }
        return null;
    }
    
     /**
     * Check whether or not this fox is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newDragons A list to add newly born foxes to.
     */
    private void giveBirth(List<Animal> newKomodoDragons)
    {
        // New dragons are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            KomodoDragon young = new KomodoDragon(false, field, loc);
            newKomodoDragons.add(young);
        }
    }
    
    /**
     * Return the breeding age of this dragon.
     * @return The breeding age of this dragon.
     */
    @Override
    protected int getBreedingAge()
    {
        return BREEDING_AGE;
    }
    
    /**
     * Return the breeding probability of this dragon.
     * @return The breeding probability of this dragon.
     */
    @Override
    protected double getBreedingProbability()
    {
        return BREEDING_PROBABILITY;
    }
    
    /**
     * Return the maximum litter size of this dragon.
     * @return The maximum litter size of this dragon.
     */
    @Override
    protected int getMaxLitterSize()
    {
        return MAX_LITTER_SIZE;
    }
    
    /**
     * Return the maximum age of this dragon
     * @return The maximum age of this dragon
     */
    @Override
    protected int getMaxAge()
    {
        return MAX_AGE;
    }
    
    /**
     * Return the name of this object
     * @return The name of the object
     */
    @Override
    public String toString() {
        return "Komodo Dragon";
    }
}

