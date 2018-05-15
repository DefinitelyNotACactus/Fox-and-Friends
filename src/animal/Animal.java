package animal;

import field.Field;
import field.Location;
import java.util.List;

/**
 * An interface representing shared characteristics of animals.
 * 
 * @author David J. Barnes and Michael Kolling
 * @version 2008.03.30
 */
public interface Animal
{
    
    /**
     * Make this animal act - that is: make it do
     * whatever it wants/needs to do.
     * @param newAnimals A list to add newly born animals to.
     */
    void act(List<Animal> newAnimals);
    
    /**
     * Check whether the animal is alive or not.
     * @return true if the animal is still alive.
     */
    boolean isAlive();
    
    /**
     * Indicate that the animal is no longer alive.
     * It is removed from the field.
     */
    void setDead();
    
    /**
     * Return the animal's location.
     * @return The animal's location.
     */
    Location getLocation();
    
    /**
     * Return the animal's field.
     * @return The animal's field.
     */
    Field getField();
    
    /**
     * Place the animal at the new location in the given field.
     * @param newLocation The animal's new location.
     */
    void setLocation(Location newLocation);
    
    /**
     * Changes the animal age.
     * @param newAge The animal's new age
     */
    void setAge(int newAge);
    
    /**
     * Return the animal's age
     * @return The animal's age
     */
    int getAge();
    
    /**
     * Increase the age.
     * This could result in the animal's death.
     */
    void incrementAge();
    
    /**
     * Return the maximum age of this animal
     * @return The maximum age of this animal
     */
    int getMaxAge();
    
    /**
     * Check whether or not this rabbit is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newAnimals A list to add newly born rabbits to.
     */
    void giveBirth(List<Animal> newAnimals);
    
    /**
     * Creates a new Animal
     * @param randomAge If true, the animal will have random age and, if a predator, hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     * @return A new Animal.
     */
    Animal createAnimal(boolean randomAge, Field field, Location location);
    
    /**
     * An animal can breed if it has reached the breeding age.
     * @return true if the animal can breed, false otherwise.
     */
    boolean canBreed();
    
    /**
     * Generate a number representing the number of births,
     * if it can breed.
     * @return The number of births (may be zero).
     */
    int breed();
    
    /**
     * Return the breeding probability of this animal.
     * @return The breeding probability of this animal.
     */
    double getBreedingProbability();
    
    /**
     * Return the maximum litter size of this animal.
     * @return The maximum litter size of this animal.
     */
    int getMaxLitterSize();
    
    /**
     * Return the breeding age of this animal.
     * @return The breeding age of this animal.
     */
    int getBreedingAge();
    
    /**
     * Return the food value of this animal
     * @return The food value of this animal
     */
    int getFoodValue();
}