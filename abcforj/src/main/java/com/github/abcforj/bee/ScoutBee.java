package com.github.abcforj.bee;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.github.abcforj.function.Function;

public class ScoutBee
{
    private double[] currentPosition;
    private List<OnlookerBee> allocatedOnlookerBees;

    public ScoutBee(double bottomDomainLimit, double topDomainLimit, int dimension)
    {
	this.currentPosition = new double[dimension];

	for (int i = 0; i < dimension; i++)
	{
	    this.currentPosition[i] = bottomDomainLimit + Math.random() * (topDomainLimit - bottomDomainLimit);
	}

	this.allocatedOnlookerBees = new ArrayList<OnlookerBee>();
    }

    public ScoutBee()
    {

    }

    public void updateAllocatedOnlookerBeesPositions(Function function)
    {
	int allocatedOnlookerBeesSize = this.allocatedOnlookerBees.size();

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
			* (currentOnlookerBee.getCurrentPosition()[j] - neighborOnlookerBee.getCurrentPosition()[j]);
	    }

	    double newFitness = function.fitness(currentOnlookerBee.getCurrentPosition());

	    if (function.compareFitness(newFitness, currentFitness))
	    {
		currentOnlookerBee.setBestPosition(currentOnlookerBee.getCurrentPosition());
	    }

	}
    }

    public double[] getCurrentPosition()
    {
	return currentPosition;
    }

    public void setCurrentPosition(double[] currentPosition)
    {
	this.currentPosition = currentPosition;
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
