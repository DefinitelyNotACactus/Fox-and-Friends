package animal.prey;

import field.Field;
import field.Location;
import field.Randomizer;
import java.util.List;
import animal.Animal;
import animal.AnimalManager;

/**
 * A simple model of a rabbit.
 * Rabbits age, move, breed, and die.
 * 
 * @author David J. Barnes and Michael Kolling
 * @version 2008.03.30
 */
public class Rabbit extends AnimalManager
{
    // Characteristics shared by all rabbits (static fields).

    // The age at which a rabbit can start to breed.
    private static final int BREEDING_AGE = 5;
    // The age to which a rabbit can live.
    private static final int MAX_AGE = 40;
    // The likelihood of a rabbit breeding.
    private static final double BREEDING_PROBABILITY = 0.15;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 4;

    /**
     * Create a new rabbit. A rabbit may be created with age
     * zero (a new born) or with a random age.
     * 
     * @param randomAge If true, the rabbit will have a random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Rabbit(boolean randomAge, Field field, Location location)
    {
        super(0, field, location);
        if(randomAge) {
            setAge(Randomizer.getRandom().nextInt(MAX_AGE));
        }
    }
    
    /**
     * This is what the rabbit does most of the time - it runs 
     * around. Sometimes it will breed or die of old age.
     * @param newRabbits A list to add newly born rabbits to.
     */
    @Override
    public void act(List<Animal> newRabbits)
    {
        incrementAge();
        if(isAlive()) {
            giveBirth(newRabbits);            
            // Try to move into a free location.
            Location newLocation = getField().freeAdjacentLocation(getLocation());
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
     *
     * @param randomAge
     * @param field
     * @param location
     * @return
     */
    @Override
    public Animal createAnimal(boolean randomAge, Field field, Location location)
    {
        return new Rabbit(randomAge, field, location);
    }
    
    /**
     * Return the breeding age of this rabbit.
     * @return The breeding age of this rabbit.
     */
    @Override
    public int getBreedingAge()
    {
        return BREEDING_AGE;
    }
    
    /**
     * Return the breeding probability of this rabbit.
     * @return The breeding probability of this rabbit.
     */
    @Override
    public double getBreedingProbability()
    {
        return BREEDING_PROBABILITY;
    }
    
    /**
     * Return the maximum litter size of this rabbit.
     * @return The maximum litter size of this rabbit.
     */
    @Override
    public int getMaxLitterSize()
    {
        return MAX_LITTER_SIZE;
    }
    
    /**
     * Return the maximum age of this rabbit
     * @return The maximum age of this rabbit
     */
    @Override
    public int getMaxAge()
    {
        return MAX_AGE;
    }
    
    /**
     * Return the name of this object
     * @return The name of the object
     */
    @Override
    public String toString() {
        return "Rabbit";
    }
}
