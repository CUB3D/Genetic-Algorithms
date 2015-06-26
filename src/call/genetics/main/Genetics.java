package call.genetics.main;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by Callum on 22/06/2015.
 */
public class Genetics
{
    public static final int GENOME_LENGTH = 10;

    public static Random random = new Random();

    public static Individual[] population = new Individual[10];

    public static void main(String[] args)
    {
        start();
    }

    private static void start()
    {
        int i = 0;

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
        printIndividual(population[getFittestIndividualPosition()]);
    }

    public static void loop()
    {
        generateRandomPopulation();

        Individual[] best = getFittestIndividuals();

        produceOffspring(best);
        
        mutate();

        printIndividual(population[getFittestIndividualPosition()]);
    }

    private static void mutate()
    {
        for(int i = 0; i < population.length; i++)
        {
            Individual individual = population[i];

            int pos = random.nextInt(GENOME_LENGTH);
            int value = random.nextInt(2);

            individual.genome[pos] = value;
        }
    }

    private static void produceOffspring(Individual[] parents)
    {
        int breakpoint = 1;//random.nextInt(GENOME_LENGTH);

        //TODO: randomize offspring amount
        int[] genome1 = crossover(breakpoint, parents[0].genome, parents[1].genome);
        int[] genome2 = crossover(breakpoint, parents[1].genome, parents[0].genome);

        Individual child1 = new Individual(genome1);
        Individual child2 = new Individual(genome2);

        for(int i = 0; i < population.length; i++)
        {
            if(population[i] == null)
            {
                population[i] = child1;
            }
        }

        for(int i = 0; i < population.length; i++)
        {
            if(population[i] == null)
            {
                population[i] = child2;
            }
        }
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
        for(int i = 0; i < population.length; i++)
        {
            Individual individual = population[i];

            printIndividual(individual);
        }
    }

    private static void printIndividual(Individual i)
    {
        System.out.println("Gnome: " + Arrays.toString(i.genome) + ", Fitness: " + i.getFitness());
    }

    private static void generateRandomPopulation()
    {
        for(int i = 0; i < population.length; i++)
        {
           population[i] = new Individual(generateRandomGnome());
        }
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

        int individual1 = getFittestIndividualPosition();

        best2Individuals[0] = population[individual1];

        population[individual1] = null;

        best2Individuals[1] = population[getFittestIndividualPosition()];

        return best2Individuals;
    }

    public static int getFittestIndividualPosition()
    {
        double maxFitness = 0.0;
        int pos = 0;

        for(int i = 0; i < population.length; i++)
        {
            Individual individual = population[i];

            if(individual != null)
            {
                if(individual.getFitness() > maxFitness)
                {
                    maxFitness = individual.getFitness();
                    pos = i;
                }
            }
        }

        return pos;
    }
}
