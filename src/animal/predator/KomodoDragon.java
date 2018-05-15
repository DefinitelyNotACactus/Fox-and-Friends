package animal.predator;

import field.Field;
import field.Location;
import field.Randomizer;
import java.util.Iterator;
import java.util.List;
import animal.Animal;

/** 
 * This class was based on the Fox and Rabbits v2 project from the Objects First with BlueJ book
 * The Komodo Dragon does the same thing as a fox, but instead of eating rabbits, it eats foxes. (Challenge Exercise 10.50)
 *
 * @author David Pereira
 * @author Gabriel Davi
 */
public class KomodoDragon extends AbstractPredatorManager
{
    // Characteristics shared by all dragons (static fields).
    
    // The age at which a dragon can start to breed.
    private static final int BREEDING_AGE = 30;
    // The age to which a dragon can live.
    private static final int MAX_AGE = 300;
    // The likelihood of a dragon breeding.
    private static final double BREEDING_PROBABILITY = 0.02;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 20;
    // The food value of a dragon.
    private static final int FOOD_VALUE = 56;
    // The food value of a fox.
    private static final int FOX_FOOD_VALUE = 28;
    
    public KomodoDragon(boolean randomAge, Field field, Location location){
        super(0, field, location);
        if(randomAge) {
            setAge(Randomizer.getRandom().nextInt(MAX_AGE));
            setFoodLevel(Randomizer.getRandom().nextInt(FOX_FOOD_VALUE));
        }
    }
    
    @Override
    public Animal createAnimal(boolean randomAge, Field field, Location location)
    {
        return new KomodoDragon(randomAge, field, location);
    }
 
    /**
     * Tell the dragon to look for foxes adjacent to its current location.
     * Only the first live fox is eaten.
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
            if(animal instanceof Fox) {
                Fox fox = (Fox) animal;
                if(fox.isAlive()) { 
                    fox.setDead();
                    setFoodLevel(fox.getFoodValue());
                    // Remove the dead fox from the field.
                    return where;
                }
            }
        }
        return null;
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
      
    @Override
    public int getMainPreyFoodValue()
    {
        return FOX_FOOD_VALUE;
    }
    /**
     * Return the name of this object
     * @return The name of the object
     */
    @Override
    public String toString() 
    {
        return "Komodo Dragon";
    }
    
}

