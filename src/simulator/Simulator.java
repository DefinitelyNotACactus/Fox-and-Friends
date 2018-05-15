package simulator;

import animal.predator.Fox;
import animal.predator.KomodoDragon;
import animal.prey.Rabbit;
import field.Field;
import field.Location;
import field.Randomizer;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.Color;
import java.util.Timer;
import java.util.TimerTask;
import animal.Animal;

/**
 * A simple predator-prey simulator, based on a rectangular field
 * containing rabbits and foxes.
 * 
 * @author David J. Barnes and Michael Kolling
 * @version 2008.03.30
 */
public class Simulator
{
    // Constants representing configuration information for the simulation.
    // The default width for the grid.
    private static final int DEFAULT_WIDTH = 60;//was 50
    // The default depth of the grid.
    private static final int DEFAULT_DEPTH = 60;//was 50
    // The probability that a fox will be created in any given grid position.
    private static final double DRAGON_CREATION_PROBABILITY = 0.01;
    // The probability that a fox will be created in any given grid position.
    private static final double FOX_CREATION_PROBABILITY = 0.02;
    // The probability that a rabbit will be created in any given grid position.
    private static final double RABBIT_CREATION_PROBABILITY = 0.08;   
    
    //Timer Fields
    private Timer time;
    private boolean skip = false;
    
    // List of animals in the field.
    private List<Animal> animals;
    // The current state of the field.
    private Field field;
    // The current step of the simulation.
    private int step;
    // A graphical view of the simulation.
    private SimulatorView view;
    
    /**
     * Construct a simulation field with default size.
     */
    public Simulator()
    {
        this(DEFAULT_DEPTH, DEFAULT_WIDTH);
        time = new Timer();
    }
    
    /**
     * Create a simulation field with the given size.
     * @param depth Depth of the field. Must be greater than zero.
     * @param width Width of the field. Must be greater than zero.
     */
    public Simulator(int depth, int width)
    {
        if(width <= 0 || depth <= 0) {
            System.out.println("The dimensions must be greater than zero.");
            System.out.println("Using default values.");
            depth = DEFAULT_DEPTH;
            width = DEFAULT_WIDTH;
        }
        
        animals = new ArrayList<Animal>();
        field = new Field(depth, width);

        // Create a view of the state of each location in the field.
        view = new SimulatorView(depth, width);
        view.setColor(Rabbit.class, Color.orange);
        view.setColor(Fox.class, Color.blue);
        view.setColor(KomodoDragon.class, Color.red);
        
        // Setup a valid starting point.
        reset();
    }
    
    /**
     * Run the simulation from its current state for a reasonably long period,
     * e.g. 500 steps.
     * @param timer Is the timer is enabled?
     */
    public void runLongSimulation(boolean timer)
    {
        if(timer){
            simulate(500);
        } else {
            simulateWithNoTimer(500);
        }
    }
    
    /**
     * Run the simulation from its current state for the given number of steps with a timer.
     * Stop before the given number of steps if it ceases to be viable.
     * @param numSteps The number of steps to run for.
     */
    public void simulate(int numSteps)
    {
        time.schedule(new TimerTask() {
            int i = 0;
            @Override
            public void run(){
              if(view.isViable(field) && !Launcher.getPressed() && !getSkip()){
                 simulateOneStep();
                 i++;
              } else {
                  cancel();
                  time.purge();
                  if(getSkip()){
                    simulateWithNoTimer(i);
                  }
              }
            }
        }, 0, 200);
    }
    
    /**
     * Run the simulation from its current state for the given number of steps without a timer.
     * Stop before the given number of steps if it ceases to be viable.
     * @param steps The number of steps to run for.
     */
    public void simulateWithNoTimer(int steps)
    {
        for(int step = 1; step <= 500 && view.isViable(field); step++) {
            simulateOneStep();
        }
    }
    
    /**
     * Run the simulation from its current state for a single step.
     * Iterate over the whole field updating the state of each
     * fox and rabbit.
     */
    public void simulateOneStep()
    {
        step++;

        // Provide space for newborn animals.
        List<Animal> newAnimals = new ArrayList<Animal>();        
        // Let all rabbits act.
        for(Iterator<Animal> it = animals.iterator(); it.hasNext(); ) {
            Animal animal = it.next();
            animal.act(newAnimals);
            if(!animal.isAlive()) {
                it.remove();
            }
        }
               
        // Add the newly born foxes and rabbits to the main lists.
        animals.addAll(newAnimals);

        view.showStatus(step, field);
    }
        
    /**
     * Reset the simulation to a starting position.
     */
    public void reset()
    {
        step = 0;
        animals.clear();
        populate();
        setSkip(false);
        // Show the starting state in the view.
        view.showStatus(step, field);
    }
    
    /**
     * Gets the value of the skip field
     * @return boolean if the simulation is to be skipped.
     */
    public boolean getSkip()
    {
        return skip;
    }
    
    public void setSkip(boolean skip)
    {
        this.skip = skip;
    }
    
    /**
     * Randomly populate the field with foxes, dragons and rabbits.
     */
    private void populate()
    {
        Random rand = Randomizer.getRandom();
        field.clear();
        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                if(rand.nextDouble() <= DRAGON_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    KomodoDragon dragon = new KomodoDragon(true, field, location);
                    animals.add(dragon);
                } 
                else if(rand.nextDouble() <= FOX_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Fox fox = new Fox(true, field, location);
                    animals.add(fox);
                }
                else if(rand.nextDouble() <= RABBIT_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Rabbit rabbit = new Rabbit(true, field, location);
                    animals.add(rabbit);
                }
                // else leave the location empty.
            }
        }
    }
}
