package call.genetics.main;

/**
 * Created by Callum on 22/06/2015.
 */
public class Individual
{
    public int[] genome;

    public Individual(int[] genome)
    {
        this.genome = genome;
    }

    public double getFitness()
    {
        double fitness = 0;

        for(int i = 0; i < genome.length; i++)
        {
            fitness += genome[i];
        }

        return fitness;
    }
}
