package call.genetics.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by Callum on 22/06/2015.
 */
public class Genetics
{
    public static final int GENOME_LENGTH = 1000;
    private static final int BASE_POPULATION_SIZE = 20;

    public static Random random = new Random();

    public static List<Individual> population = new ArrayList<Individual>();

    public static void main(String[] args)
    {
        start();
    }

    private static void start()
    {
        int i = 0;

        generateRandomPopulation();

        while(true)
        {
            loop();

            if(getFittestIndividuals()[0].getFitness() == 10 && getFittestIndividuals()[1].getFitness() == 10)
            {
                System.out.println("Solution found, Generations: " + i);
                break;
            }

            i++;
        }

        printIndividual(getFittestIndividual());
    }

    public static void loop()
    {
        Individual[] best = getFittestIndividuals();

        produceOffspring(best);
        
        mutate();

        //printIndividual(getFittestIndividual());
    }

    private static void mutate()
    {
        for(int i = 0; i < population.size(); i++)
        {
            Individual individual = population.get(i);

            int pos = random.nextInt(GENOME_LENGTH);
            int value = random.nextInt(2);

            individual.genome[pos] = value;
        }
    }

    private static void produceOffspring(Individual[] parents)
    {
        int breakpoint = random.nextInt(GENOME_LENGTH);

        //TODO: randomize offspring amount
        int[] genome1 = crossover(breakpoint, parents[0].genome, parents[1].genome);
        int[] genome2 = crossover(breakpoint, parents[1].genome, parents[0].genome);

        Individual child1 = new Individual(genome1);
        Individual child2 = new Individual(genome2);

        population.add(child1);

        population.add(child2);
    }

    public static int[] crossover(int breakpoint, int[] genome1, int[] genome2)
    {
        int[] genome = new int[GENOME_LENGTH];

        for(int i = 0; i < GENOME_LENGTH; i++)
        {
            if(i < breakpoint)
            {
                genome[i] = genome1[i];
            }
            else
            {
                genome[i] = genome2[i];
            }
        }

        return genome;
    }

    private static void printPopulation()
    {
        for(int i = 0; i < population.size(); i++)
        {
            Individual individual = population.get(i);

            printIndividual(individual);
        }
    }

    private static void printIndividual(Individual i)
    {
        System.out.println("Gnome: " + Arrays.toString(i.genome) + ", Fitness: " + i.getFitness());
    }

    private static void generateRandomPopulation()
    {
        for(int i = 0; i < BASE_POPULATION_SIZE; i++)
        {
           population.add(new Individual(generateRandomGnome()));
        }

        System.out.println("Generated random population with " + population.size() + " individuals");
    }

    private static int[] generateRandomGnome()
    {
        int[] genome = new int[GENOME_LENGTH];

        for(int i = 0; i < genome.length; i++)
        {
            genome[i] = random.nextInt(2);
        }

        return genome;
    }

    public static Individual[] getFittestIndividuals()
    {
        Individual[] best2Individuals = new Individual[2];

        best2Individuals[0] = getFittestIndividual();

        int index = population.indexOf(best2Individuals[0]);

        population.set(index, null);

        best2Individuals[1] = getFittestIndividual();

        population.set(index, best2Individuals[0]);

        return best2Individuals;
    }

    public static Individual getFittestIndividual()
    {
        double maxFitness = 0.0;
        Individual fittest = null;

        for(int i = 0; i < population.size(); i++)
        {
            Individual individual = population.get(i);

            if(individual != null)
            {
                if(individual.getFitness() > maxFitness)
                {
                    maxFitness = individual.getFitness();
                    fittest = individual;
                }
            }
        }

        return fittest;
    }
}
