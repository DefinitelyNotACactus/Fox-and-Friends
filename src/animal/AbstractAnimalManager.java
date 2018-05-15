package animal;

import field.Field;
import field.Location;
import field.Randomizer;
import java.util.List;
import java.util.Random;

/**
 * A class that implements the Animal methods.
 * 
 * @author David J. Barnes and Michael Kolling
 * @version 2008.03.30
 */
public abstract class AbstractAnimalManager implements Animal
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
    public AbstractAnimalManager(int age, Field field, Location location)
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
    abstract public void act(List<Animal> newAnimals);

    /**
     * Check whether the animal is alive or not.
     * @return true if the animal is still alive.
     */
    @Override
    public boolean isAlive()
    {
        return alive;
    }

    /**
     * Indicate that the animal is no longer alive.
     * It is removed from the field.
     */
    @Override
    public void setDead()
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
    @Override
    public Location getLocation()
    {
        return location;
    }
    
    /**
     * Return the animal's field.
     * @return The animal's field.
     */
    @Override
    public Field getField()
    {
        return field;
    }
    
    /**
     * Place the animal at the new location in the given field.
     * @param newLocation The animal's new location.
     */
    @Override
    public void setLocation(Location newLocation)
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
    @Override
    public void setAge(int newAge)
    {
        age = newAge;
    }
    
    /**
     * Return the animal's age
     * @return The animal's age
     */
    @Override
    public int getAge()
    {
        return age;
    }
    
    /**
     * Increase the age.
     * This could result in the animal's death.
     */
    @Override
    public void incrementAge()
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
    abstract public int getMaxAge();
    
    @Override
    public void giveBirth(List<Animal> newAnimals)
    {
        // New rabbits are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Animal young = createAnimal(false, field, loc);
            newAnimals.add(young);
        }
    }
    
    abstract public Animal createAnimal(boolean randomAge, Field field, Location location);
        
    /**
     * An animal can breed if it has reached the breeding age.
     * @return true if the animal can breed, false otherwise.
     */
    @Override
    public boolean canBreed()
    {
        return age >= getBreedingAge();
    }
    
    /**
     * Generate a number representing the number of births,
     * if it can breed.
     * @return The number of births (may be zero).
     */
    @Override
    public int breed()
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
    abstract public double getBreedingProbability();
    
    /**
     * Return the maximum litter size of this animal.
     * @return The maximum litter size of this animal.
     */
    abstract public int getMaxLitterSize();
    
    /**
     * Return the breeding age of this animal.
     * @return The breeding age of this animal.
     */
    abstract public int getBreedingAge();
    
}
