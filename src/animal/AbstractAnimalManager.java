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
    
    @Override
    abstract public void act(List<Animal> newAnimals);

    @Override
    public boolean isAlive()
    {
        return alive;
    }

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

    @Override
    public Location getLocation()
    {
        return location;
    }
    
    @Override
    public Field getField()
    {
        return field;
    }
    
    @Override
    public void setLocation(Location newLocation)
    {
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }
    
    @Override
    public void setAge(int newAge)
    {
        age = newAge;
    }
    
    @Override
    public int getAge()
    {
        return age;
    }
    
    @Override
    public void incrementAge()
    {
        age++;
        if(age > getMaxAge()) {
            setDead();
        }
    }
    
    @Override
    abstract public int getMaxAge();
    
    @Override
    public void giveBirth(List<Animal> newAnimals)
    {
        // New animals are born into adjacent locations.
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
    
    @Override
    abstract public Animal createAnimal(boolean randomAge, Field field, Location location);
        
    @Override
    public boolean canBreed()
    {
        return age >= getBreedingAge();
    }
    
    @Override
    public int breed()
    {
        int births = 0;
        if(canBreed() && rand.nextDouble() <= getBreedingProbability()) {
            births = rand.nextInt(getMaxLitterSize()) + 1;
        }
        return births;
    }

    @Override
    abstract public double getBreedingProbability();
    
    @Override
    abstract public int getMaxLitterSize();
    
    @Override
    abstract public int getBreedingAge();
    
    @Override
    abstract public int getFoodValue();  
}
