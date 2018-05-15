package animal.predator;

import animal.prey.Rabbit;
import field.Field;
import field.Location;
import field.Randomizer;
import java.util.List;
import java.util.Iterator;
import animal.Animal;

/**
 * A simple model of a fox.
 * Foxes age, move, eat rabbits, and die.
 * 
 * @author David J. Barnes and Michael Kolling
 * @version 2008.03.30
 */
public class Fox extends PredatorManager
{
    // Characteristics shared by all foxes (static fields).
    
    // The age at which a fox can start to breed.
    private static final int BREEDING_AGE = 10;
    // The age to which a fox can live.
    private static final int MAX_AGE = 150;
    // The likelihood of a fox breeding.
    private static final double BREEDING_PROBABILITY = 0.35;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 5;
    // The food value of a single rabbit. In effect, this is the
    // number of steps a fox can go before it has to eat again.
    private static final int RABBIT_FOOD_VALUE = 7;

    /**
     * Create a fox. A fox can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     * 
     * @param randomAge If true, the fox will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Fox(boolean randomAge, Field field, Location location)
    {
        super(0, field, location);
        if(randomAge) {
            setAge(Randomizer.getRandom().nextInt(MAX_AGE));
            setFoodLevel(Randomizer.getRandom().nextInt(RABBIT_FOOD_VALUE));
        }
        else {
            setFoodLevel(RABBIT_FOOD_VALUE);
        }
    }
    
    /**
     * This is what the fox does most of the time: it hunts for
     * rabbits. In the process, it might breed, die of hunger,
     * or die of old age.
     * @param newFoxes A list to add newly born foxes to.
     */
    @Override
    public void act(List<Animal> newFoxes)
    {
        incrementAge();
        incrementHunger();
        if(isAlive()) {
            giveBirth(newFoxes);            
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
    
    @Override
    public Animal createAnimal(boolean randomAge, Field field, Location location)
    {
        return new Fox(randomAge, field, location);
    }

    /**
     * Tell the fox to look for rabbits adjacent to its current location.
     * Only the first live rabbit is eaten.
     * @param location Where in the field it is located.
     * @return Where food was found, or null if it wasn't.
     */
    @Override
    public Location findFood(Location location)
    {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object animal = field.getObjectAt(where);
            if(animal instanceof Rabbit) {
                Rabbit rabbit = (Rabbit) animal;
                if(rabbit.isAlive()) { 
                    rabbit.setDead();
                    setFoodLevel(RABBIT_FOOD_VALUE);
                    // Remove the dead rabbit from the field.
                    return where;
                }
            }
        }
        return null;
    }

    /**
     * Return the breeding age of this fox.
     * @return The breeding age of this fox.
     */
    @Override
    public int getBreedingAge()
    {
        return BREEDING_AGE;
    }
    
    /**
     * Return the breeding probability of this fox.
     * @return The breeding probability of this fox.
     */
    @Override
    public double getBreedingProbability()
    {
        return BREEDING_PROBABILITY;
    }
    
    /**
     * Return the maximum litter size of this fox.
     * @return The maximum litter size of this fox.
     */
    @Override
    public int getMaxLitterSize()
    {
        return MAX_LITTER_SIZE;
    }
    
    /**
     * Return the maximum age of this fox
     * @return The maximum age of this fox
     */
    @Override
    public int getMaxAge()
    {
        return MAX_AGE;
    }
    
    @Override
    public int getFoodValue(){
        return RABBIT_FOOD_VALUE;
    }
    
    /**
     * Return the name of this object
     * @return The name of the object
     */
    @Override
    public String toString() {
        return "Fox";
    }
}
