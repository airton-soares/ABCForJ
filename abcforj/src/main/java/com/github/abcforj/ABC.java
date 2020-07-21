package com.github.abcforj;

import com.github.abcforj.bee.ScoutBee;
import com.github.abcforj.beehive.Beehive;
import com.github.abcforj.function.Function;

public class ABC
{
    private Beehive beehive;
    private int numberOfExplorationCycles;
    private int limitOfExploitationCycles;
    private Function function;
    private double[] bestSolutions;

    public ABC(int beehiveSize, float scoutBeesProportion, int numberOfExplorationCycles, int limitExploitationCycles,
               Function function, int dimension)
    {
        this.beehive = new Beehive(beehiveSize, scoutBeesProportion, dimension);
        this.numberOfExplorationCycles = numberOfExplorationCycles;
        this.limitOfExploitationCycles = limitExploitationCycles;
        this.function = function;
        this.bestSolutions = new double[this.numberOfExplorationCycles];
    }

    public void doFoodSearch()
    {
        int remainingExplorationCycles = this.numberOfExplorationCycles;

        while (remainingExplorationCycles > 0)
        {
            this.beehive.initializeBeehive(this.function.getBottomDomainLimit(), this.function.getTopDomainLimit());
            this.beehive.allocateOnlookerBees(this.function);
            double bestResult = 0;

            for (int i = 0; i < this.beehive.getScoutBees().size(); i++)
            {
                ScoutBee scoutBee = this.beehive.getScoutBees().get(i);
                scoutBee.updateAllocatedOnlookerBeesPositions(this.function, this.limitOfExploitationCycles);

                if (i == 0)
                {
                    bestResult = this.function.fitness(scoutBee.getCurrentPosition());
                }
                else
                {
                    double scoutBeeBestFitness = this.function.fitness(scoutBee.getCurrentPosition());

                    if (this.function.compareFitness(scoutBeeBestFitness, bestResult))
                    {
                        bestResult = scoutBeeBestFitness;
                    }
                }
            }

            this.bestSolutions[this.numberOfExplorationCycles - remainingExplorationCycles] = bestResult;

            remainingExplorationCycles--;
        }
    }

    public Beehive getBeehive()
    {
        return beehive;
    }

    public void setBeehive(Beehive beehive)
    {
        this.beehive = beehive;
    }

    public int getNumberOfExplorationCycles()
    {
        return numberOfExplorationCycles;
    }

    public void setNumberOfExplorationCycles(int numberOfExplorationCycles)
    {
        this.numberOfExplorationCycles = numberOfExplorationCycles;
    }

    public int getLimitOfExploitationCycles()
    {
        return limitOfExploitationCycles;
    }

    public void setLimitOfExploitationCycles(int limitOfExploitationCycles)
    {
        this.limitOfExploitationCycles = limitOfExploitationCycles;
    }

    public Function getFunction()
    {
        return function;
    }

    public void setFunction(Function function)
    {
        this.function = function;
    }

    public double[] getBestSolutions()
    {
        return bestSolutions;
    }

    public void setBestSolutions(double[] bestSolutions)
    {
        this.bestSolutions = bestSolutions;
    }
}
