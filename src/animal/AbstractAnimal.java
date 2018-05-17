package animal;

import field.Field;
import field.Location;
import field.Randomizer;
import java.util.List;
import java.util.Random;

/**
 * An abstract class that implements the Animal methods.
 * 
 * @author David J. Barnes and Michael Kolling
 * @version 2008.03.30
 */
public abstract class AbstractAnimal
{
    //The animal's age.
    private int age;
    // Whether the animal is alive or not.
    private boolean alive;
    // The animal's field.
    private Field field;
    // The animal's position in the field.
    private Location location;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    
    /**
     * Create a new animal at location in field.
     * 
     * @param age The animal's age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public AbstractAnimal(int age, Field field, Location location)
    {
        this.age = age;
        alive = true;
        this.field = field;
        setLocation(location);
    }
    
    /**
     * Make this animal act - that is: make it do
     * whatever it wants/needs to do.
     * @param newAnimals A list to add newly born animals to.
     */
    abstract public void act(List<AbstractAnimal> newAnimals);

    /**
     * Check whether the animal is alive or not.
     * @return true if the animal is still alive.
     */
    public boolean isAlive()
    {
        return alive;
    }

    /**
     * Indicate that the animal is no longer alive.
     * It is removed from the field.
     */
    protected void setDead()
    {
        alive = false;
        if(location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }

    /**
     * Return the animal's location.
     * @return The animal's location.
     */
    protected Location getLocation()
    {
        return location;
    }
    
    /**
     * Return the animal's field.
     * @return The animal's field.
     */
    protected Field getField()
    {
        return field;
    }
    
    /**
     * Place the animal at the new location in the given field.
     * @param newLocation The animal's new location.
     */
    protected void setLocation(Location newLocation)
    {
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }
    
    /**
     * Changes the animal age.
     * @param newAge The animal's new age
     */
    protected void setAge(int newAge)
    {
        age = newAge;
    }
    
    /**
     * Return the animal's age
     * @return The animal's age
     */
    protected int getAge()
    {
        return age;
    }
    
    /**
     * Increase the age.
     * This could result in the animal's death.
     */
    protected void incrementAge()
    {
        age++;
        if(age > getMaxAge()) {
            setDead();
        }
    }
    
    /**
     * Return the maximum age of this animal
     * @return The maximum age of this animal
     */
    abstract protected int getMaxAge();
    
    /**
     * Check whether or not this rabbit is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newAnimals A list to add newly born rabbits to.
     */
    protected void giveBirth(List<AbstractAnimal> newAnimals)
    {
        // New animals are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            AbstractAnimal young = createAnimal(false, field, loc);
            newAnimals.add(young);
        }
    }
    
    /**
     * Creates a new Animal
     * @param randomAge If true, the animal will have random age and, if a predator, hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     * @return A new Animal.
     */
    abstract protected AbstractAnimal createAnimal(boolean randomAge, Field field, Location location);
        
    /**
     * An animal can breed if it has reached the breeding age.
     * @return true if the animal can breed, false otherwise.
     */
    protected boolean canBreed()
    {
        return age >= getBreedingAge();
    }
    
    /**
     * Generate a number representing the number of births,
     * if it can breed.
     * @return The number of births (may be zero).
     */
    protected int breed()
    {
        int births = 0;
        if(canBreed() && rand.nextDouble() <= getBreedingProbability()) {
            births = rand.nextInt(getMaxLitterSize()) + 1;
        }
        return births;
    }

    /**
     * Return the breeding probability of this animal.
     * @return The breeding probability of this animal.
     */
    abstract protected double getBreedingProbability();
    
    /**
     * Return the maximum litter size of this animal.
     * @return The maximum litter size of this animal.
     */
    abstract protected int getMaxLitterSize();
    
    /**
     * Return the breeding age of this animal.
     * @return The breeding age of this animal.
     */
    abstract protected int getBreedingAge();
    
    /**
     * Return the food value of this animal
     * @return The food value of this animal
     */
    abstract protected int getFoodValue();  
}
