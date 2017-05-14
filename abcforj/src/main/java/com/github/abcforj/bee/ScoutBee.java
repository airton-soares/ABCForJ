package com.github.abcforj.bee;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.github.abcforj.function.Function;

public class ScoutBee
{
    private double[] currentPosition;
    private double[] bestFoodSourcePosition;
    private List<OnlookerBee> allocatedOnlookerBees;

    public ScoutBee(double bottomDomainLimit, double topDomainLimit, int dimension)
    {
	this.currentPosition = new double[dimension];

	for (int i = 0; i < dimension; i++)
	{
	    this.currentPosition[i] = bottomDomainLimit + Math.random() * (topDomainLimit - bottomDomainLimit);
	}

	this.bestFoodSourcePosition = this.currentPosition;
	this.allocatedOnlookerBees = new ArrayList<OnlookerBee>();
    }

    public ScoutBee()
    {

    }

    public void updateAllocatedOnlookerBeesPositions(Function function, int limitOfExploitationCycles)
    {
	int allocatedOnlookerBeesSize = this.allocatedOnlookerBees.size();
	int limitOfExploitationCyclesAux = limitOfExploitationCycles;
	
	while (limitOfExploitationCycles > 0)
	{
	    for (int i = 0; i < allocatedOnlookerBeesSize; i++)
	    {
		OnlookerBee currentOnlookerBee = this.allocatedOnlookerBees.get(i);
		double currentFitness = function.fitness(currentOnlookerBee.getCurrentPosition());

		int neighborIndex = 0;

		do
		{
		    neighborIndex = ThreadLocalRandom.current().nextInt(0, allocatedOnlookerBeesSize);
		} while (neighborIndex == i);

		OnlookerBee neighborOnlookerBee = this.allocatedOnlookerBees.get(neighborIndex);

		for (int j = 0; j < currentOnlookerBee.getCurrentPosition().length; j++)
		{
		    currentOnlookerBee.getCurrentPosition()[j] += ThreadLocalRandom.current().nextInt(-1, 2)
			    * (currentOnlookerBee.getCurrentPosition()[j]
				    - neighborOnlookerBee.getCurrentPosition()[j]);
		}

		double newFitness = function.fitness(currentOnlookerBee.getCurrentPosition());

		if (function.compareFitness(newFitness, currentFitness))
		{
		    currentOnlookerBee.setBestPosition(currentOnlookerBee.getCurrentPosition());
		}

	    }
	    
	    double[] bestOnlookerBeePosition = this.getBestOnlookerBeePosition(function);
	    
	    double bestOnlookerBeePositionFitness = function.fitness(bestOnlookerBeePosition);
	    double bestFoodSourcePosition = function.fitness(this.bestFoodSourcePosition);
	    
	    if(function.compareFitness(bestOnlookerBeePositionFitness, bestFoodSourcePosition))
	    {
		this.bestFoodSourcePosition = bestOnlookerBeePosition;
		limitOfExploitationCycles = limitOfExploitationCyclesAux;
	    }
	    else
	    {
		limitOfExploitationCycles--;
	    }
	}
    }

    private double[] getBestOnlookerBeePosition(Function function)
    {
	double[] bestPosition = null;

	for (int i = 0; i < this.allocatedOnlookerBees.size(); i++)
	{
	    double[] bestPositionCandidate = this.allocatedOnlookerBees.get(i).getBestPosition();

	    if (bestPosition == null)
	    {
		bestPosition = bestPositionCandidate;
		continue;
	    }
	    else
	    {
		double bestPositionCandidateFitness = function.fitness(bestPositionCandidate);
		double bestPositionFitness = function.fitness(bestPosition);

		if (function.compareFitness(bestPositionCandidateFitness, bestPositionFitness))
		{
		    bestPosition = bestPositionCandidate;
		}
	    }

	}

	return bestPosition;
    }

    public double[] getCurrentPosition()
    {
	return currentPosition;
    }

    public void setCurrentPosition(double[] currentPosition)
    {
	this.currentPosition = currentPosition;
    }

    public double[] getBestFoodSourcePosition()
    {
	return bestFoodSourcePosition;
    }

    public void setBestFoodSourcePosition(double[] bestFoodSourcePosition)
    {
	this.bestFoodSourcePosition = bestFoodSourcePosition;
    }

    public List<OnlookerBee> getAllocatedOnlookerBees()
    {
	return allocatedOnlookerBees;
    }

    public void setAllocatedOnlookerBees(List<OnlookerBee> allocatedOnlookerBees)
    {
	this.allocatedOnlookerBees = allocatedOnlookerBees;
    }
}
