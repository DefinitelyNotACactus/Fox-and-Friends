package animal.prey;

import animal.Animal;
import field.Field;
import field.Location;
import java.util.List;

/**
 * A class representing shared characteristics of preys.
 * @author David Pereira
 * @author Gabriel Davi
 */
public abstract class AbstractPrey extends Animal
{
    /**
     * Create a new prey at location in field.
     * 
     * @param age The prey's age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public AbstractPrey(int age, Field field, Location location) {
        super(age, field, location);
    }
    
    @Override
    public void act(List<Animal> newPreys)
    {
        incrementAge();
        if(isAlive()) {
            giveBirth(newPreys);            
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
}
