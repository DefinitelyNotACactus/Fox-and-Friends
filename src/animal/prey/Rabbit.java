package animal.prey;

import field.Field;
import field.Location;
import field.Randomizer;
import animal.Animal;

/**
 * A simple model of a rabbit.
 * Rabbits age, move, breed, and die.
 * 
 * @author David J. Barnes and Michael Kolling
 * @version 2008.03.30
 */
public class Rabbit extends AbstractPreyManager
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
    // The food value of a rabbit.
    private static final int FOOD_VALUE = 7;
    
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
    
    @Override
    public Animal createAnimal(boolean randomAge, Field field, Location location)
    {
        return new Rabbit(randomAge, field, location);
    }
    
    @Override
    public int getBreedingAge()
    {
        return BREEDING_AGE;
    }
    
    @Override
    public double getBreedingProbability()
    {
        return BREEDING_PROBABILITY;
    }
    
    @Override
    public int getMaxLitterSize()
    {
        return MAX_LITTER_SIZE;
    }
    
    @Override
    public int getMaxAge()
    {
        return MAX_AGE;
    }
    
    @Override
    public int getFoodValue()
    {
        return FOOD_VALUE;
    }
    
    /**
     * Return the name of this object
     * @return The name of the object
     */
    @Override
    public String toString() 
    {
        return "Rabbit";
    }
}
