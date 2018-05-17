package animal;

import field.Field;
import field.Location;
import field.Randomizer;
import java.util.List;

/**
 * A class representing shared characteristics of preys.
 * @author David Pereira
 * @author Gabriel Davi
 */
public abstract class AbstractPrey extends AbstractAnimal
{
    /**
     * Create a new prey at location in field.
     * 
     * @param randomAge If true, the prey will have random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public AbstractPrey(boolean randomAge, Field field, Location location) {
        super(0, field, location);
        if(randomAge) {
            setAge(Randomizer.getRandom().nextInt(getMaxAge()));
        }
    }
    
    @Override
    public void act(List<AbstractAnimal> newPreys)
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
