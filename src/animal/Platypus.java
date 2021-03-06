package animal;

import field.Field;
import field.Location;

/**
 * This class was based on the Rabbit class from Fox and Rabbits v2 project from the Objects First with BlueJ book
 * 
 * @author David Pereira
 * @author Gabriel Davi
 * @author Gabriel Mendes
 */
public class Platypus extends AbstractPrey
{
    // Characteristics shared by all platypuses (static fields).

    // The age at which a platypus can start to breed.
    private static final int BREEDING_AGE = 10;
    // The age to which a platypus can live.
    private static final int MAX_AGE = 20;
    // The likelihood of a platypus breeding.
    private static final double BREEDING_PROBABILITY = 0.1;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 3;
    // The food value of a platypus.
    private static final int FOOD_VALUE = 6;
    
    /**
     * Create a new platypus. A platypus may be created with age
     * zero (a new born) or with a random age.
     * 
     * @param randomAge If true, the platypus will have a random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Platypus(boolean randomAge, Field field, Location location)
    {
        super(randomAge, field, location);
    }
    
    @Override
    public AbstractAnimal createAnimal(boolean randomAge, Field field, Location location)
    {
        return new Platypus(randomAge, field, location);
    }
    
    @Override
    protected int getBreedingAge()
    {
        return BREEDING_AGE;
    }
    
    @Override
    protected double getBreedingProbability()
    {
        return BREEDING_PROBABILITY;
    }
    
    @Override
    protected int getMaxLitterSize()
    {
        return MAX_LITTER_SIZE;
    }
    
    @Override
    protected int getMaxAge()
    {
        return MAX_AGE;
    }
    
    @Override
    protected int getFoodValue()
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
        return "Platypus";
    }
}
